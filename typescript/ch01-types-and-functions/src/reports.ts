/**
 * Mark43-style types and functions for Reports.
 *
 * Build this file by filling in the TODOs below.
 * When `npm test` passes, you're done.
 */

// TODO 1: Define an exported union type called `ApprovalStatus` that allows only
//         these five string values:
//         'DRAFT' | 'SUBMITTED' | 'APPROVED' | 'REJECTED' | 'RECALLED'
//
// Example shape (replace this comment with the actual `export type` line):
//   export type ApprovalStatus = '...' | '...' | ...;
export type ApprovalStatus = 'DRAFT' | 'SUBMITTED' | 'APPROVED' | 'REJECTED' | 'RECALLED';


// TODO 2: Define an exported interface called `ReportResponse` with these fields:
//         id: number
//         reportNumber: string
//         reportTitle: string | null      (note: can be null)
//         approvalStatus: ApprovalStatus  (uses the union type from TODO 1)
//         assignedOfficerId: number | null
//         createdDateUtc: string          (ISO string like '2024-03-15T14:30:00Z')
//         isSealed: boolean
export interface ReportResponse {
    id: number;
    reportNumber: string;
    reportTitle: string | null;
    approvalStatus: ApprovalStatus;
    assignedOfficerId: number | null;
    createdDateUtc: string;
    isSealed: boolean;
}


// TODO 3: Define an exported interface called `Officer` with these fields:
//         id: number
//         firstName: string
//         lastName: string
//         badgeNumber: string
export interface Officer {
    id: number;
    firstName: string;
    lastName: string;
    badgeNumber: string;
}


// TODO 4: Export a function `formatReportNumber(departmentId: number, sequence: number): string`
//         that returns: "RPT-{departmentId}-{sequence padded to 6 digits with leading zeros}"
//
//         Examples:
//           formatReportNumber(42, 7)       -> "RPT-42-000007"
//           formatReportNumber(100, 12345)  -> "RPT-100-012345"
//
//         Tip: String(n).padStart(6, '0') gives you "000007" from 7.
export function formatReportNumber(departmentId: number, sequence: number): string {
    return ''; // TODO -- replace with your implementation
}


// TODO 5: Export a function `isEditableStatus(status: ApprovalStatus): boolean`
//         that returns `true` only when the report is in a status that allows editing.
//         In Mark43, that means DRAFT, REJECTED, or RECALLED. Everything else is `false`.
export function isEditableStatus(status: ApprovalStatus): boolean {
    return false; // TODO -- replace with your implementation
}


// TODO 6: Export a function `getReportTitle(report: ReportResponse): string`
//         that returns the report's title, falling back to "Untitled Report" when null.
//
//         Tip: the `??` (nullish coalescing) operator does exactly this in one line:
//              `return value ?? 'fallback';`
export function getReportTitle(report: ReportResponse): string {
    return ''; // TODO -- replace with your implementation
}


// TODO 7: Export a function `getOfficerName(officer: Officer | null): string`
//         that returns "FirstName LastName" when an officer is given, or "Unassigned" when null.
//
//         Tip: an `if (officer === null) { ... }` guard at the top handles the null case
//         and TypeScript will know `officer` is non-null after that point.
export function getOfficerName(officer: Officer | null): string {
    return ''; // TODO -- replace with your implementation
}


// TODO 8: Export a function
//         `getReportsByStatus(reports: ReportResponse[], status: ApprovalStatus): ReportResponse[]`
//         that returns ONLY the reports whose approvalStatus matches `status`.
//
//         Tip: `reports.filter(r => r.approvalStatus === status)` is the idiomatic one-liner.
export function getReportsByStatus(reports: ReportResponse[], status: ApprovalStatus): ReportResponse[] {
    return []; // TODO -- replace with your implementation
}
