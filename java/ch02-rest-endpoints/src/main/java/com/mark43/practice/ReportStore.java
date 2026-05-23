package com.mark43.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Pre-built in-memory store for ch02. Do not edit.
 *
 * Stands in for a real database. The resource you build will delegate to this.
 */
public class ReportStore {

    private final Map<Long, Report> data = new ConcurrentHashMap<>();

    public Report findById(Long id) {
        return data.get(id);
    }

    public List<Report> findByDepartmentId(Long departmentId) {
        List<Report> result = new ArrayList<>();
        for (Report report : data.values()) {
            if (report.getDepartmentId().equals(departmentId)) {
                result.add(report);
            }
        }
        return result;
    }

    public Report save(Report report) {
        data.put(report.getId(), report);
        return report;
    }

    public void clear() {
        data.clear();
    }
}
