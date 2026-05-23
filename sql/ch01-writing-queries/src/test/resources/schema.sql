-- Schema for SQL Chapter 1: Writing Queries
-- This mirrors the shape of real Mark43 tables (simplified).

CREATE TABLE personnel_record (
    id BIGINT NOT NULL PRIMARY KEY,
    department_id BIGINT NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    badge_number VARCHAR(50) NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE case_entity (
    id BIGINT NOT NULL PRIMARY KEY,
    department_id BIGINT NOT NULL,
    case_number VARCHAR(50) NOT NULL,
    case_title VARCHAR(255) NULL,
    status VARCHAR(20) NOT NULL,
    assigned_officer_id BIGINT NULL,
    created_date_utc DATETIME(3) NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE report (
    id BIGINT NOT NULL PRIMARY KEY,
    department_id BIGINT NOT NULL,
    report_number VARCHAR(50) NOT NULL,
    report_title VARCHAR(255) NULL,
    approval_status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    reporting_officer_id BIGINT NOT NULL,
    created_date_utc DATETIME(3) NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT false
);
