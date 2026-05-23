package com.mark43.practice;

/**
 * Demonstrates using the PermissionChecker to gate a sensitive operation.
 *
 * Build `sealReport` by following the TODOs.
 */
public class ReportFacade {

    private int sealCallCount = 0;

    /**
     * Seal a report - requires SEAL_REPORT ability.
     * Tests verify both that permission is checked AND that the seal happens.
     */
    public Long sealReport(User currentUser, Long reportId) {
        // TODO 1: Call PermissionChecker.ensurePermission(currentUser, Abilities.SEAL_REPORT)
        //         This MUST be the first line - if it throws, the rest of the method
        //         must not run.

        // TODO 2: Increment sealCallCount by 1 (sealCallCount++ or sealCallCount += 1)

        // TODO 3: Return reportId
        return null;
    }

    public int getSealCallCount() {
        return sealCallCount;
    }
}
