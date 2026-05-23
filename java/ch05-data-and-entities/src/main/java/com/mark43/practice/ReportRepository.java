package com.mark43.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * In-memory repository for reports.
 *
 * Implement the five query methods below. Use the `data` field to store reports.
 */
public class ReportRepository {

    private final List<Report> data = new ArrayList<>();

    /**
     * TODO 1: Add `report` to the `data` list, then return `report`.
     */
    public Report save(Report report) {
        return null;
    }

    /**
     * TODO 2: Return Optional<Report> containing the report whose id matches.
     *         Use data.stream().filter(...).findFirst().
     *
     *         To compare Longs: report.getId().equals(id) -- NOT == !
     */
    public Optional<Report> findById(Long id) {
        return Optional.empty();
    }

    /**
     * TODO 3: Return List<Report> of all reports whose departmentId matches.
     *         Use data.stream().filter(...).collect(Collectors.toList()).
     */
    public List<Report> findByDepartmentId(Long departmentId) {
        return new ArrayList<>();
    }

    /**
     * TODO 4: Return List<Report> of reports where:
     *           - departmentId matches, AND
     *           - isSealed() is false
     *         Use .filter(...) with BOTH conditions chained with && inside one lambda,
     *         or use two .filter(...) calls.
     */
    public List<Report> findByDepartmentIdAndUnsealed(Long departmentId) {
        return new ArrayList<>();
    }

    /**
     * TODO 5: Return how many reports are in the given department as a long.
     *         Use data.stream().filter(...).count().
     */
    public long countByDepartmentId(Long departmentId) {
        return 0;
    }
}
