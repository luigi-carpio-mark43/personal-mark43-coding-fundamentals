package com.mark43.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportRepositoryTest {

    private ReportRepository repository;

    private static final Report DEPT_100_UNSEALED_1 = new Report(1L, 100L, "RPT-001", "Theft", false, Instant.parse("2024-01-01T00:00:00Z"));
    private static final Report DEPT_100_UNSEALED_2 = new Report(2L, 100L, "RPT-002", "Assault", false, Instant.parse("2024-01-02T00:00:00Z"));
    private static final Report DEPT_100_SEALED = new Report(3L, 100L, "RPT-003", "Sensitive", true, Instant.parse("2024-01-03T00:00:00Z"));
    private static final Report DEPT_200_UNSEALED = new Report(4L, 200L, "RPT-004", "Burglary", false, Instant.parse("2024-01-04T00:00:00Z"));

    @BeforeEach
    void setUp() {
        repository = new ReportRepository();
        repository.save(DEPT_100_UNSEALED_1);
        repository.save(DEPT_100_UNSEALED_2);
        repository.save(DEPT_100_SEALED);
        repository.save(DEPT_200_UNSEALED);
    }

    @Test
    void saveReturnsTheReport() {
        ReportRepository empty = new ReportRepository();
        Report report = new Report(99L, 999L, "RPT-099", "New", false, Instant.now());

        Report result = empty.save(report);

        assertEquals(report, result);
    }

    @Test
    void savedReportCanBeFoundById() {
        Optional<Report> result = repository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(DEPT_100_UNSEALED_1, result.get());
    }

    @Test
    void findByIdReturnsEmptyWhenNotFound() {
        Optional<Report> result = repository.findById(999L);
        assertFalse(result.isPresent());
    }

    @Test
    void findByDepartmentIdReturnsAllReportsForThatDept() {
        List<Report> result = repository.findByDepartmentId(100L);
        assertEquals(3, result.size());
        assertTrue(result.contains(DEPT_100_UNSEALED_1));
        assertTrue(result.contains(DEPT_100_UNSEALED_2));
        assertTrue(result.contains(DEPT_100_SEALED));
    }

    @Test
    void findByDepartmentIdReturnsEmptyListForUnknownDept() {
        List<Report> result = repository.findByDepartmentId(999L);
        assertEquals(0, result.size());
    }

    @Test
    void findByDepartmentIdAndUnsealedExcludesSealed() {
        List<Report> result = repository.findByDepartmentIdAndUnsealed(100L);
        assertEquals(2, result.size());
        assertTrue(result.contains(DEPT_100_UNSEALED_1));
        assertTrue(result.contains(DEPT_100_UNSEALED_2));
        assertFalse(result.contains(DEPT_100_SEALED), "Sealed report should NOT be in unsealed query results");
    }

    @Test
    void findByDepartmentIdAndUnsealedExcludesOtherDepts() {
        List<Report> result = repository.findByDepartmentIdAndUnsealed(100L);
        assertFalse(result.contains(DEPT_200_UNSEALED), "Report from different dept should not appear");
    }

    @Test
    void countByDepartmentIdMatchesActualCount() {
        assertEquals(3L, repository.countByDepartmentId(100L));
        assertEquals(1L, repository.countByDepartmentId(200L));
        assertEquals(0L, repository.countByDepartmentId(999L));
    }
}
