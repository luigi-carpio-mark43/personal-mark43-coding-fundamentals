package com.mark43.practice;

import java.util.List;
import java.util.Optional;

/**
 * The Service layer - validates input, checks permissions, delegates to repository.
 *
 * Build the three methods below by following the TODOs.
 */
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    /**
     * Create a new report.
     *
     * TODO 1: Permission check first - call
     *           PermissionChecker.ensurePermission(user, Abilities.EDIT_REPORTS)
     *
     * TODO 2: Validate input (in this order):
     *           - if (report == null) throw new IllegalArgumentException("report cannot be null");
     *           - if title is null OR title.trim().isEmpty(), throw IllegalArgumentException("reportTitle cannot be empty");
     *           - if (report.getDepartmentId() == null) throw new IllegalArgumentException("departmentId is required");
     *
     * TODO 3: Return repository.save(report);
     */
    public Report createReport(User user, Report report) {
        return null;
    }

    /**
     * Fetch one report by id.
     *
     * TODO 4: Permission check first - call
     *           PermissionChecker.ensurePermission(user, Abilities.VIEW_REPORTS)
     *
     * TODO 5: Call repository.findById(id) and store in a local Optional<Report>.
     *
     * TODO 6: If the Optional is empty (use !found.isPresent() or found.isEmpty()),
     *         throw new ReportNotFoundException(id).
     *
     * TODO 7: Otherwise return found.get();
     */
    public Report getReport(User user, Long id) {
        return null;
    }

    /**
     * List all reports for a department.
     *
     * TODO 8: Permission check first - call
     *           PermissionChecker.ensurePermission(user, Abilities.VIEW_REPORTS)
     *
     * TODO 9: Return repository.findByDepartmentId(departmentId).
     */
    public List<Report> listReportsForDepartment(User user, Long departmentId) {
        return null;
    }
}
