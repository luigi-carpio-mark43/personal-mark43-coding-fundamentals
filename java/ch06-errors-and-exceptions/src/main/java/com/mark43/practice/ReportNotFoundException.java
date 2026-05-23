package com.mark43.practice;

/**
 * Thrown when a report ID does not match any record.
 *
 * Build this exception class by following the TODOs.
 */
public class ReportNotFoundException extends RuntimeException {

    // TODO 1: Add a private final field of type Long called `reportId`.


    // TODO 2: Add a public constructor that takes a Long parameter named `id`.
    //         Inside the constructor body:
    //           - First line: super("Report not found: " + id);
    //           - Second line: this.reportId = id;


    // TODO 3: Add a public getter `getReportId()` that returns the Long field.

}
