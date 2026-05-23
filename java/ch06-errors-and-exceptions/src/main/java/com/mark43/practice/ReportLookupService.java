package com.mark43.practice;

import java.util.Optional;

/**
 * Two patterns for "look up a report by id":
 *   - getOrThrow: strict, throws if missing
 *   - tryGet: lenient, returns Optional.empty() if missing
 */
public class ReportLookupService {

    private final ReportStore store;

    public ReportLookupService(ReportStore store) {
        this.store = store;
    }

    /**
     * TODO 4: Look up a report by id.
     *           - Call store.get(id) and assign it to a local variable.
     *           - If the local variable is null, throw new ReportNotFoundException(id).
     *           - Otherwise return the local variable.
     */
    public Report getOrThrow(Long id) {
        return null;
    }

    /**
     * TODO 5: Look up a report by id without throwing.
     *         Use a try / catch block:
     *           try {
     *               Report report = getOrThrow(id);
     *               return Optional.of(report);
     *           } catch (ReportNotFoundException e) {
     *               return Optional.empty();
     *           }
     */
    public Optional<Report> tryGet(Long id) {
        return Optional.empty();
    }
}
