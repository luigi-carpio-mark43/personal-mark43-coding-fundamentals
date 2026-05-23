package com.mark43.practice;

/**
 * Pre-built. Do not edit.
 */
public class ReportNotFoundException extends RuntimeException {

    private final Long reportId;

    public ReportNotFoundException(Long id) {
        super("Report not found: " + id);
        this.reportId = id;
    }

    public Long getReportId() {
        return reportId;
    }
}
