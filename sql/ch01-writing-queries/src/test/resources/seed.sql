-- Seed data for SQL Chapter 1: Writing Queries
-- Designed to exercise: department scoping, soft delete, NULL handling,
-- date ranges, status filtering, joins, and pagination.

-- Personnel: officers in two departments (42 and 43)
INSERT INTO personnel_record (id, department_id, first_name, last_name, badge_number) VALUES
    (100, 42, 'John',  'Smith', 'B-001'),
    (101, 42, 'Jane',  'Doe',   'B-002'),
    (102, 42, 'Bob',   'Jones', 'B-003'),
    (200, 43, 'Alice', 'Brown', 'B-100');

-- Cases in dept 42 (id 1-8) and dept 43 (id 9). Case 7 is soft-deleted.
-- Case 4 has no assigned officer (tests JOIN vs LEFT JOIN behavior).
INSERT INTO case_entity (id, department_id, case_number, case_title, status, assigned_officer_id, created_date_utc, is_deleted) VALUES
    (1, 42, 'CS-2024-001', 'Vehicle Theft',        'OPEN',    100,  '2024-03-01 10:00:00', false),
    (2, 42, 'CS-2024-002', 'Assault',              'OPEN',    101,  '2024-02-15 14:30:00', false),
    (3, 42, 'CS-2024-003', 'Burglary',             'CLOSED',  100,  '2024-01-10 09:00:00', false),
    (4, 42, 'CS-2024-004', 'Vandalism',            'OPEN',    NULL, '2024-03-15 16:00:00', false),
    (5, 42, 'CS-2024-005', 'Fraud Investigation',  'PENDING', 102,  '2024-02-01 11:00:00', false),
    (6, 42, 'CS-2024-006', 'Public Disturbance',   'OPEN',    101,  '2024-03-20 13:00:00', false),
    (7, 42, 'CS-2024-007', 'Stolen Bicycle',       'CLOSED',  100,  '2023-12-01 08:00:00', true),
    (8, 42, 'CS-2024-008', 'Trespassing',          'OPEN',    102,  '2024-03-25 15:00:00', false),
    (9, 43, 'CS-2024-009', 'Stalking',             'OPEN',    200,  '2024-03-15 12:00:00', false);

-- Reports in dept 42 (10-15) and dept 43 (20). Report 15 is soft-deleted.
INSERT INTO report (id, department_id, report_number, report_title, approval_status, reporting_officer_id, created_date_utc, is_deleted) VALUES
    (10, 42, 'RPT-2024-001', 'Theft from Vehicle', 'SUBMITTED', 100, '2024-03-01 10:00:00', false),
    (11, 42, 'RPT-2024-002', 'Assault Report',     'DRAFT',     101, '2024-02-15 14:30:00', false),
    (12, 42, 'RPT-2024-003', 'Burglary Report',    'APPROVED',  100, '2024-03-10 09:00:00', false),
    (13, 42, 'RPT-2024-004', 'Vandalism Report',   'SUBMITTED', 102, '2024-03-25 15:00:00', false),
    (14, 42, 'RPT-2024-005', 'Robbery Report',     'DRAFT',     101, '2024-04-01 11:00:00', false),
    (15, 42, 'RPT-2023-015', 'Old Investigation',  'SUBMITTED', 100, '2023-12-15 08:00:00', true),
    (20, 43, 'RPT-2024-100', 'Theft (Other Dept)', 'SUBMITTED', 200, '2024-03-15 12:00:00', false);
