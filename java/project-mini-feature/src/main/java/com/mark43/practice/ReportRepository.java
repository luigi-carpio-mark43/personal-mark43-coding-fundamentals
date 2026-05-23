package com.mark43.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Pre-built. Do not edit.
 */
public class ReportRepository {

    private final List<Report> data = new ArrayList<>();

    public Report save(Report report) {
        data.add(report);
        return report;
    }

    public Optional<Report> findById(Long id) {
        return data.stream()
                .filter(report -> report.getId().equals(id))
                .findFirst();
    }

    public List<Report> findByDepartmentId(Long departmentId) {
        return data.stream()
                .filter(report -> report.getDepartmentId().equals(departmentId))
                .collect(Collectors.toList());
    }
}
