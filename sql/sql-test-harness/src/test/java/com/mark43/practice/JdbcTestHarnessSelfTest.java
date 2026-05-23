package com.mark43.practice;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Self-test for {@link JdbcTestHarness}. If these pass, the SQL chapter
 * infrastructure (H2 in MySQL mode + JDBC plumbing) works end-to-end.
 */
class JdbcTestHarnessSelfTest {

    @Test
    void createsTableInsertsAndQueries() {
        try (JdbcTestHarness db = new JdbcTestHarness()) {
            db.executeUpdate("CREATE TABLE report (id BIGINT NOT NULL PRIMARY KEY, title VARCHAR(255) NOT NULL)");
            db.executeUpdate("INSERT INTO report VALUES (1, 'Theft from Vehicle')");
            db.executeUpdate("INSERT INTO report VALUES (2, 'Assault')");

            List<Map<String, Object>> rows = db.executeQuery("SELECT id, title FROM report ORDER BY id");

            assertEquals(2, rows.size());
            assertEquals(1L, rows.get(0).get("id"));
            assertEquals("Theft from Vehicle", rows.get(0).get("title"));
            assertEquals(2L, rows.get(1).get("id"));
            assertEquals("Assault", rows.get(1).get("title"));
        }
    }

    @Test
    void mysqlModeAcceptsBooleanAndAutoIncrement() {
        try (JdbcTestHarness db = new JdbcTestHarness()) {
            db.executeUpdate(
                    "CREATE TABLE incident ("
                            + "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                            + "is_active BOOLEAN NOT NULL DEFAULT true)"
            );
            db.executeUpdate("INSERT INTO incident (is_active) VALUES (true), (false)");

            List<Map<String, Object>> rows = db.executeQuery("SELECT id, is_active FROM incident ORDER BY id");

            assertEquals(2, rows.size());
            assertEquals(Boolean.TRUE, rows.get(0).get("is_active"));
            assertEquals(Boolean.FALSE, rows.get(1).get("is_active"));
        }
    }

    @Test
    void describeTableReturnsColumnMetadata() {
        try (JdbcTestHarness db = new JdbcTestHarness()) {
            db.executeUpdate(
                    "CREATE TABLE report ("
                            + "id BIGINT NOT NULL PRIMARY KEY, "
                            + "title VARCHAR(255) NULL, "
                            + "status VARCHAR(20) NOT NULL DEFAULT 'DRAFT')"
            );

            List<JdbcTestHarness.ColumnInfo> columns = db.describeTable("report");

            assertEquals(3, columns.size());
            assertEquals("id", columns.get(0).name);
            assertFalse(columns.get(0).nullable);
            assertEquals("title", columns.get(1).name);
            assertTrue(columns.get(1).nullable);
            assertEquals("status", columns.get(2).name);
            assertTrue(columns.get(2).defaultValue.contains("DRAFT"));
        }
    }

    @Test
    void isolatedDatabasesPerInstance() {
        try (JdbcTestHarness db1 = new JdbcTestHarness();
             JdbcTestHarness db2 = new JdbcTestHarness()) {
            db1.executeUpdate("CREATE TABLE foo (id INT)");
            db1.executeUpdate("INSERT INTO foo VALUES (1)");

            assertThrows(RuntimeException.class, () -> db2.executeQuery("SELECT * FROM foo"));
        }
    }
}
