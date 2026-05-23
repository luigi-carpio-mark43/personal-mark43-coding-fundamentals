package com.mark43.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportNotFoundExceptionTest {

    @Test
    void messageIncludesTheReportId() {
        ReportNotFoundException ex = new ReportNotFoundException(42L);
        assertEquals("Report not found: 42", ex.getMessage());
    }

    @Test
    void getReportIdReturnsTheIdPassedIn() {
        ReportNotFoundException ex = new ReportNotFoundException(7L);
        assertEquals(7L, ex.getReportId());
    }

    @Test
    void isARuntimeException() {
        ReportNotFoundException ex = new ReportNotFoundException(1L);
        assertTrue(ex instanceof RuntimeException,
                "ReportNotFoundException should extend RuntimeException so it doesn't need 'throws' declarations");
    }
}
