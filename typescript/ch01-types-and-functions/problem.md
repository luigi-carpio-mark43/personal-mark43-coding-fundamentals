# TypeScript Chapter 1: Types and Functions

> **Goal**: Define TypeScript types and write functions that operate on them. You'll learn how to model Mark43 data shapes (`Report`, `Officer`) and write pure functions that filter, format, and handle nulls — the same patterns you read every day in RMS, CAD, and JMS frontends.

---

## Scenario

In Mark43 frontends, almost every component starts by defining the **shape** of the data it receives — an `interface` like `ReportResponse` that describes what comes back from the API. The frontend then writes small **functions** that transform or filter that data: format a report number for display, check whether a report is still editable, replace a missing title with "Untitled Report", and so on.

In this chapter you'll build:

- A **union type** for approval statuses
- Two **interfaces** (`ReportResponse`, `Officer`)
- Five **functions** that operate on those types, covering string formatting, predicates, null handling with `??`, null guards with `if`, and array filtering

## What to Build

Open `src/reports.ts`. There are 8 numbered TODOs:

1. **`ApprovalStatus`** — a union type of 5 string literals
2. **`ReportResponse`** — an interface with 7 fields (note `string | null` and `number | null`)
3. **`Officer`** — an interface with 4 fields
4. **`formatReportNumber(deptId, sequence)`** — zero-pads the sequence to 6 digits: `RPT-42-000007`
5. **`isEditableStatus(status)`** — `true` only for DRAFT / REJECTED / RECALLED
6. **`getReportTitle(report)`** — returns the title or `"Untitled Report"` if null (use `??`)
7. **`getOfficerName(officer)`** — `"FirstName LastName"` or `"Unassigned"` if null (use an `if` guard)
8. **`getReportsByStatus(reports, status)`** — filter the array

Each TODO has a hint about the idiomatic TS pattern to use.

## How to Run

The first time, install the dependencies:

```
cd typescript/ch01-types-and-functions
npm install
```

Then run the tests:

```
npm test
```

- If you see `Tests: N passed`, you're done.
- If you see a failure like *"Expected 'RPT-42-000007' but received 'RPT-42-7'"*, your function is missing the zero-padding step — go back and check the hint in the TODO.

You can also run `npm run typecheck` to ask TypeScript whether your types are valid (without running the tests). Type errors usually mean an interface is missing a field or a function signature is wrong.

## What This Teaches

- **Union types** — `'DRAFT' | 'SUBMITTED' | ...` constrains a string to a fixed set of values
- **Interfaces** — define the shape of an object (what fields exist, what types they have)
- **`string | null` types** — a value that's either a string OR null, and the compiler forces you to handle both
- **Nullish coalescing (`??`)** — a one-liner default for `null`/`undefined` values
- **Null guards** — `if (x === null) return ...` lets TypeScript narrow the type so the rest of the function sees a non-null value
- **`.filter()`** — return a new array of items that match a condition

## In Mark43 Terms

This file is structured exactly like the shared `types.ts` files in RMS and CAD frontends. The functions are the kind of small pure helpers you'll see in `client/src/scripts/legacy-redux/selectors/` and similar utility folders. The naming conventions (`getXxx`, `isXxx`, `formatXxx`) are how every Mark43 frontend names its helpers.

## Stuck?

Ask your tutor. Read the test failure first — Vitest prints the line, the expected value, and the actual value, which usually points right at the problem.
