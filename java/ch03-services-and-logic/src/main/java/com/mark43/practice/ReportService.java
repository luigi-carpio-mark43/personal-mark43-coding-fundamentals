package com.mark43.practice;

/**
 * Business logic for reports.
 *
 * Build the createReport method by following the TODOs.
 */
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public Report createReport(Report report) {
        // TODO 1: If `report` is null, throw new IllegalArgumentException("report cannot be null")

        // TODO 2: If report.getReportTitle() is null OR (after trimming whitespace) is empty,
        //         throw new IllegalArgumentException("reportTitle cannot be empty")
        //         Hint: String has a .trim() method and .isEmpty() method.

        // TODO 3: If report.getDepartmentId() is null,
        //         throw new IllegalArgumentException("departmentId is required")

        // TODO 4: If report.getReportNumber() does NOT start with "RPT-",
        //         throw new IllegalArgumentException("reportNumber must start with RPT-")
        //         Hint: String has a .startsWith(String) method.

        // TODO 5: If all checks passed, call repository.save(report) and return its result.
        return null;
    }
}
