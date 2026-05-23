package com.mark43.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportLookupServiceTest {

    private ReportStore store;
    private ReportLookupService service;

    private static final Report REPORT_42 = new Report(42L, 100L, "RPT-042", "Theft", false, Instant.parse("2024-01-01T00:00:00Z"));

    @BeforeEach
    void setUp() {
        store = new ReportStore();
        store.put(REPORT_42);
        service = new ReportLookupService(store);
    }

    @Test
    void getOrThrowReturnsReportWhenPresent() {
        Report result = service.getOrThrow(42L);
        assertEquals(REPORT_42, result);
    }

    @Test
    void getOrThrowThrowsWhenMissing() {
        ReportNotFoundException ex = assertThrows(ReportNotFoundException.class,
                () -> service.getOrThrow(999L));
        assertEquals(999L, ex.getReportId());
        assertEquals("Report not found: 999", ex.getMessage());
    }

    @Test
    void tryGetReturnsPresentOptionalWhenFound() {
        Optional<Report> result = service.tryGet(42L);
        assertTrue(result.isPresent());
        assertEquals(REPORT_42, result.get());
    }

    @Test
    void tryGetReturnsEmptyOptionalWhenMissing() {
        Optional<Report> result = service.tryGet(999L);
        assertFalse(result.isPresent(), "Missing report should yield Optional.empty(), not throw");
    }
}
