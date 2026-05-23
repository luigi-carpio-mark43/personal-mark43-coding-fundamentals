package com.mark43.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportServiceTest {

    private ReportRepository repository;
    private ReportService service;

    @BeforeEach
    void setUp() {
        repository = new ReportRepository();
        service = new ReportService(repository);
    }

    private static User userWith(String... abilities) {
        Set<String> set = new HashSet<>();
        Collections.addAll(set, abilities);
        return new User(1L, "Alice", set);
    }

    private static Report sampleReport() {
        return new Report(1L, 100L, "RPT-001", "Theft", false, Instant.parse("2024-01-01T00:00:00Z"));
    }

    // ----- createReport -----

    @Test
    void createReport_validUserAndInput_saves() {
        User user = userWith(Abilities.EDIT_REPORTS);
        Report report = sampleReport();

        Report result = service.createReport(user, report);

        assertSame(report, result);
        assertTrue(repository.findById(1L).isPresent());
    }

    @Test
    void createReport_userMissingEditAbility_throwsPermissionDenied() {
        User user = userWith(Abilities.VIEW_REPORTS);
        Report report = sampleReport();

        assertThrows(PermissionDeniedException.class, () -> service.createReport(user, report));
    }

    @Test
    void createReport_permissionCheckedBeforeValidation() {
        User user = userWith();
        Report invalid = new Report(1L, null, "INC-001", null, false, null);

        // With no abilities, the FIRST thing to fail should be the permission check,
        // not validation. So we expect PermissionDeniedException, not IllegalArgumentException.
        assertThrows(PermissionDeniedException.class, () -> service.createReport(user, invalid));
    }

    @Test
    void createReport_nullReport_throwsIllegalArgument() {
        User user = userWith(Abilities.EDIT_REPORTS);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createReport(user, null));
        assertEquals("report cannot be null", ex.getMessage());
    }

    @Test
    void createReport_emptyTitle_throwsIllegalArgument() {
        User user = userWith(Abilities.EDIT_REPORTS);
        Report bad = new Report(1L, 100L, "RPT-001", "   ", false, Instant.now());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createReport(user, bad));
        assertEquals("reportTitle cannot be empty", ex.getMessage());
    }

    @Test
    void createReport_nullDepartmentId_throwsIllegalArgument() {
        User user = userWith(Abilities.EDIT_REPORTS);
        Report bad = new Report(1L, null, "RPT-001", "Theft", false, Instant.now());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createReport(user, bad));
        assertEquals("departmentId is required", ex.getMessage());
    }

    // ----- getReport -----

    @Test
    void getReport_existingId_returnsReport() {
        User user = userWith(Abilities.VIEW_REPORTS);
        Report report = sampleReport();
        repository.save(report);

        Report result = service.getReport(user, 1L);

        assertSame(report, result);
    }

    @Test
    void getReport_missingId_throwsReportNotFound() {
        User user = userWith(Abilities.VIEW_REPORTS);

        ReportNotFoundException ex = assertThrows(ReportNotFoundException.class,
                () -> service.getReport(user, 999L));
        assertEquals(999L, ex.getReportId());
    }

    @Test
    void getReport_userMissingViewAbility_throwsPermissionDenied() {
        User user = userWith(Abilities.EDIT_REPORTS);
        repository.save(sampleReport());

        assertThrows(PermissionDeniedException.class, () -> service.getReport(user, 1L));
    }

    // ----- listReportsForDepartment -----

    @Test
    void listReports_returnsAllForDepartment() {
        User user = userWith(Abilities.VIEW_REPORTS);
        Report r1 = new Report(1L, 100L, "RPT-001", "T", false, Instant.now());
        Report r2 = new Report(2L, 100L, "RPT-002", "A", false, Instant.now());
        Report r3 = new Report(3L, 200L, "RPT-003", "B", false, Instant.now());
        repository.save(r1);
        repository.save(r2);
        repository.save(r3);

        List<Report> result = service.listReportsForDepartment(user, 100L);

        assertEquals(2, result.size());
        assertTrue(result.contains(r1));
        assertTrue(result.contains(r2));
    }

    @Test
    void listReports_userMissingViewAbility_throwsPermissionDenied() {
        User user = userWith(Abilities.EDIT_REPORTS);

        assertThrows(PermissionDeniedException.class,
                () -> service.listReportsForDepartment(user, 100L));
    }
}
