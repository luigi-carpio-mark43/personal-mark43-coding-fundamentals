# SQL Chapter 1: Writing Queries

> **Goal**: Write SELECT queries against a Mark43-style schema. You'll learn how to filter, join, group, and paginate data — the same patterns you read every day in support tickets and DB clients.

---

## Scenario

In Mark43, almost every Support investigation starts with "what does the data actually say?" The answer comes from a SQL query — usually written by an engineer or run by you in a DB client like DBeaver. In this chapter you'll write six queries against a small Mark43-shaped schema (departments, cases, reports, personnel).

The schema and seed data are pre-loaded for you. The tables are:

- **`personnel_record`** — officers (id, department, name, badge_number)
- **`case_entity`** — cases (id, department, case_number, title, status, assigned_officer_id, created_date_utc, is_deleted)
- **`report`** — reports (id, department, report_number, title, approval_status, reporting_officer_id, created_date_utc, is_deleted)

The seed has officers and records in two departments (42 and 43), a mix of statuses (OPEN, CLOSED, PENDING for cases; DRAFT, SUBMITTED, APPROVED for reports), some soft-deleted rows, and one case with no assigned officer. That variety is what makes each exercise testable.

## What to Build

Open `src/main/resources/queries/`. There are six `.sql` files, one per exercise. Each file has a TODO header explaining what the query should return and a placeholder query you replace with your real query.

| # | File | Skill |
|---|------|-------|
| 1 | `01_reports_by_department.sql` | Basic `SELECT` + `WHERE` + `ORDER BY` |
| 2 | `02_q1_submitted_reports.sql` | Multi-condition `WHERE` + date range filter |
| 3 | `03_cases_with_officers.sql` | `JOIN` two tables with table aliases (`c`, `p`) |
| 4 | `04_cases_with_optional_officer.sql` | `LEFT JOIN` to include rows without matches |
| 5 | `05_cases_by_status.sql` | `GROUP BY` + `COUNT(*)` aggregation |
| 6 | `06_top_open_cases.sql` | `ORDER BY ... DESC` + `LIMIT N` (pagination) |

## How to Run

From this directory (`sql/ch01-writing-queries`):

```
mvn test
```

- If you see `BUILD SUCCESS` and `Tests run: 6, Failures: 0`, you're done.
- If you see a failure like *"Expected 5 active reports in department 42 but was 0"*, your query is returning the wrong rows — go back and check the filters and column order.

## What This Teaches

- **`SELECT` shape** — column list, `FROM`, `WHERE`, `ORDER BY`, `LIMIT`
- **Filtering patterns** — `=`, `AND`/`OR`, date range with `>=` and `<`, soft-delete filter
- **Joining tables** — `INNER JOIN` vs `LEFT JOIN`, table aliases, qualified columns
- **Aggregation** — `GROUP BY` collapses rows; `COUNT(*)` counts each group
- **Pagination** — `LIMIT` to cap rows; `OFFSET` shifts the starting point

## In Mark43 Terms

These are the same query shapes you'll see in real Mark43 production code — Hibernate queries in JMS, raw JDBC in older RMS code, and the queries DBAs run when investigating customer tickets. The conventions baked into the exercises (`is_deleted = false`, `department_id = X`, date half-open ranges) are how every Mark43 table is queried in practice.

## Stuck?

Ask your tutor. Try to make sense of the test failure first ("expected N rows, got M" usually means the filters are wrong). The tutor won't write the answer, but they'll point at the right concept.
