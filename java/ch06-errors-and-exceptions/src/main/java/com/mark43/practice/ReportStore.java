package com.mark43.practice;

import java.util.HashMap;
import java.util.Map;

/**
 * Pre-built for ch06. Do not edit.
 *
 * Simple map-backed store. get(id) returns Report or null.
 */
public class ReportStore {

    private final Map<Long, Report> data = new HashMap<>();

    public void put(Report report) {
        data.put(report.getId(), report);
    }

    public Report get(Long id) {
        return data.get(id);
    }
}
