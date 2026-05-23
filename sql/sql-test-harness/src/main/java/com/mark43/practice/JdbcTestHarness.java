package com.mark43.practice;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Test harness for the SQL chapters. Spins up an in-memory H2 database in
 * MySQL compatibility mode, lets tests load schema/seed scripts, and provides
 * helpers for running queries and inspecting schema.
 *
 * Each instance owns its own isolated database, so tests do not interfere
 * with each other. Always wrap usage in try-with-resources so the connection
 * is closed.
 *
 * <pre>
 * try (JdbcTestHarness db = new JdbcTestHarness()) {
 *     db.executeScript("/schema.sql");
 *     db.executeScript("/seed.sql");
 *     List&lt;Map&lt;String, Object&gt;&gt; rows = db.executeQueryFromResource("/queries/find_reports.sql");
 *     assertEquals(3, rows.size());
 * }
 * </pre>
 */
public class JdbcTestHarness implements AutoCloseable {

    private final Connection connection;

    public JdbcTestHarness() {
        String dbName = "testdb_" + UUID.randomUUID().toString().replace("-", "");
        String url = "jdbc:h2:mem:" + dbName
                + ";MODE=MYSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1";
        try {
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to open H2 connection", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    /** Load and execute a SQL script from the classpath (e.g. "/schema.sql"). */
    public void executeScript(String resourcePath) {
        executeBatch(readResource(resourcePath));
    }

    /** Load and execute a SQL script from a file on disk. */
    public void executeScriptFile(Path path) {
        try {
            executeBatch(Files.readString(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read " + path, e);
        }
    }

    /** Execute a query and return rows as a list of column-name -&gt; value maps. */
    public List<Map<String, Object>> executeQuery(String sql) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return resultSetToList(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Query failed: " + sql, e);
        }
    }

    public List<Map<String, Object>> executeQueryFromResource(String resourcePath) {
        return executeQuery(readResource(resourcePath));
    }

    public List<Map<String, Object>> executeQueryFromFile(Path path) {
        try {
            return executeQuery(Files.readString(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read " + path, e);
        }
    }

    /** Execute INSERT / UPDATE / DELETE / DDL. Returns affected row count. */
    public int executeUpdate(String sql) {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Update failed: " + sql, e);
        }
    }

    /** Returns column metadata for a table from information_schema. */
    public List<ColumnInfo> describeTable(String tableName) {
        String sql = "SELECT column_name, data_type, is_nullable, column_default "
                + "FROM information_schema.columns "
                + "WHERE LOWER(table_name) = LOWER('" + tableName + "') "
                + "ORDER BY ordinal_position";
        List<ColumnInfo> result = new ArrayList<>();
        for (Map<String, Object> row : executeQuery(sql)) {
            result.add(new ColumnInfo(
                    String.valueOf(row.get("column_name")).toLowerCase(),
                    String.valueOf(row.get("data_type")),
                    "YES".equalsIgnoreCase(String.valueOf(row.get("is_nullable"))),
                    row.get("column_default") == null ? null : String.valueOf(row.get("column_default"))
            ));
        }
        return result;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            // best effort
        }
    }

    private void executeBatch(String script) {
        String cleaned = stripLineComments(script);
        try (Statement stmt = connection.createStatement()) {
            for (String raw : cleaned.split(";")) {
                String stmtText = raw.trim();
                if (!stmtText.isEmpty()) {
                    stmt.execute(stmtText);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Batch execution failed", e);
        }
    }

    private static String stripLineComments(String script) {
        StringBuilder out = new StringBuilder();
        for (String line : script.split("\n")) {
            int idx = line.indexOf("--");
            if (idx >= 0) {
                out.append(line, 0, idx);
            } else {
                out.append(line);
            }
            out.append('\n');
        }
        return out.toString();
    }

    private static List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                row.put(md.getColumnLabel(i).toLowerCase(), rs.getObject(i));
            }
            rows.add(row);
        }
        return rows;
    }

    private static String readResource(String path) {
        try (InputStream in = JdbcTestHarness.class.getResourceAsStream(path)) {
            if (in == null) {
                throw new RuntimeException("Resource not found on classpath: " + path);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource " + path, e);
        }
    }

    /** Column metadata returned by {@link #describeTable(String)}. */
    public static class ColumnInfo {
        public final String name;
        public final String dataType;
        public final boolean nullable;
        public final String defaultValue;

        public ColumnInfo(String name, String dataType, boolean nullable, String defaultValue) {
            this.name = name;
            this.dataType = dataType;
            this.nullable = nullable;
            this.defaultValue = defaultValue;
        }
    }
}
