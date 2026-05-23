package com.mark43.practice;

import java.time.Instant;
import java.util.Objects;

/**
 * Pre-built for ch06. Do not edit.
 */
public class Report {

    private final Long id;
    private final Long departmentId;
    private final String reportNumber;
    private final String reportTitle;
    private final boolean isSealed;
    private final Instant createdDateUtc;

    public Report(Long id, Long departmentId, String reportNumber, String reportTitle,
                  boolean isSealed, Instant createdDateUtc) {
        this.id = id;
        this.departmentId = departmentId;
        this.reportNumber = reportNumber;
        this.reportTitle = reportTitle;
        this.isSealed = isSealed;
        this.createdDateUtc = createdDateUtc;
    }

    public Long getId() { return id; }
    public Long getDepartmentId() { return departmentId; }
    public String getReportNumber() { return reportNumber; }
    public String getReportTitle() { return reportTitle; }
    public boolean isSealed() { return isSealed; }
    public Instant getCreatedDateUtc() { return createdDateUtc; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return isSealed == report.isSealed
                && Objects.equals(id, report.id)
                && Objects.equals(departmentId, report.departmentId)
                && Objects.equals(reportNumber, report.reportNumber)
                && Objects.equals(reportTitle, report.reportTitle)
                && Objects.equals(createdDateUtc, report.createdDateUtc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departmentId, reportNumber, reportTitle, isSealed, createdDateUtc);
    }
}
