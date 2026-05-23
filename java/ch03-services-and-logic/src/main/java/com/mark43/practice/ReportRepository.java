package com.mark43.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * Pre-built stub repository for ch03. Do not edit.
 *
 * Stands in for the real database layer. The service you build will delegate to this.
 * Records every save so tests can verify the service called it.
 */
public class ReportRepository {

    private final List<Report> saved = new ArrayList<>();

    public Report save(Report report) {
        saved.add(report);
        return report;
    }

    public List<Report> getSavedReports() {
        return saved;
    }

    public int saveCount() {
        return saved.size();
    }
}
