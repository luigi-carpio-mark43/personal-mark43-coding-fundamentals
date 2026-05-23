# Chapter 6: Errors and Exceptions

> **Goal**: Build a custom exception, throw it from one method, then catch it in another. You'll learn the two ways Mark43 handles "thing not found" — strict (throw) and lenient (return Optional).

---

## Scenario

When a customer tries to view report #42, what happens if report #42 doesn't exist? The code has a choice:

1. **Throw an exception** — "report not found" bubbles up to the caller. Loud, unmissable. Used when "not found" means something is genuinely wrong.
2. **Return Optional.empty()** — caller checks `.isPresent()`. Quiet, expected. Used when "not found" is a normal possibility.

Mark43 uses both. You're going to build one method of each kind, and one custom exception to power them.

## What to Build

Three files, two of which are skeletons you fill in:

### 1. `ReportNotFoundException.java`

Build this exception class. It needs:
- A `private final Long reportId` field
- A constructor that takes a `Long id`, calls `super("Report not found: " + id)`, and assigns `this.reportId = id`
- A getter `getReportId()` returning the Long

### 2. `ReportLookupService.java`

Two methods:

**`getOrThrow(Long id)` (strict)**
- Call `store.get(id)`
- If the result is `null`, throw `new ReportNotFoundException(id)`
- Otherwise return the report

**`tryGet(Long id)` (lenient)**
- Inside a `try` block, call `this.getOrThrow(id)` and return `Optional.of(...)` with the result
- Inside a `catch (ReportNotFoundException e)` block, return `Optional.empty()`

The point: `tryGet` uses `getOrThrow` internally but converts the exception into an Optional. Both behaviors, different surface.

## What's Pre-Built (Don't Edit)

- `Report.java`
- `ReportStore.java` — a simple Map-backed store with `put(report)` and `get(id)` (returns Report or null)

## How to Run

From this directory:

```
mvn test
```

## What This Teaches

- **Custom exceptions are just classes** — extend `RuntimeException`, add a constructor, you're done
- **`super(...)` in a constructor** — calls the parent class's constructor (here, sets the exception message)
- **try / catch** — surround risky code, handle specific exception types in catch blocks
- **The two "not found" patterns** — when to throw vs when to return Optional

## In Mark43 Terms

RMS has dozens of `*NotFoundException` classes (`ReportNotFoundException`, `PersonNotFoundException`, `CaseNotFoundException`). Each one carries the missing ID so logs can pinpoint exactly what was missing. When a customer reports "I clicked the link and got a 404", you can search the logs for `ReportNotFoundException` to confirm the report ID they tried to open.

The `getOrThrow` vs `tryGet` split shows up everywhere:
- REST resources usually call `getOrThrow` — if the report doesn't exist, surface a 404 to the user
- Service-internal helpers usually use `tryGet` or Optional — they want to handle "missing" without unwinding the stack

## Stuck?

- "Why `RuntimeException` and not `Exception`?" — `RuntimeException` is **unchecked**: you don't have to declare it in `throws`. Mark43 uses unchecked exceptions almost exclusively. Checked exceptions (extending `Exception` directly) force every caller to either catch or declare — too noisy.
- "Why call `super(...)`?" — without it, the exception has no message. `super("...")` invokes `RuntimeException`'s constructor, which stores the message. Then `getMessage()` returns it.
