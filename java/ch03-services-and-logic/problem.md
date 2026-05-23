# Chapter 3: Services and Logic

> **Goal**: Build a service method — the layer where business rules live. You'll learn how Mark43 validates input, rejects bad data, and delegates the actual saving to a repository.

---

## Scenario

In ch02, the Resource was the front door — it handled the HTTP request. But Resources don't usually contain business logic. They delegate to **Services**.

A service is where you ask "is this allowed?" and "is this data valid?" before doing the real work. Mark43 has dozens of services: `ReportService`, `ArrestService`, `PersonService`, `CaseService` — each one owns the rules for its domain.

You're building `ReportService.createReport(...)`. It must:
1. **Validate the input** — reject bad data before it touches the database
2. **Delegate to the repository** — only the repo talks to the database
3. **Return the saved report**

## What to Build

Open `src/main/java/com/mark43/practice/ReportService.java`. The pre-built `Report.java` and `ReportRepository.java` are next to it — **do not edit those**.

In `ReportService.createReport(Report report)`, add these checks in order:

1. **If `report` is null** → throw `IllegalArgumentException("report cannot be null")`
2. **If `report.getReportTitle()` is null OR empty (after trimming whitespace)** → throw `IllegalArgumentException("reportTitle cannot be empty")`
3. **If `report.getDepartmentId()` is null** → throw `IllegalArgumentException("departmentId is required")`
4. **If `report.getReportNumber()` does not start with `"RPT-"`** → throw `IllegalArgumentException("reportNumber must start with RPT-")`
5. **If all checks pass** → call `repository.save(report)` and return its result

## How to Run

From this directory:

```
mvn test
```

## What This Teaches

- **Validation is a guard clause pattern** — check inputs first, throw if invalid, only proceed if everything is OK
- **Separation of concerns** — the service decides "should this happen?"; the repository handles "how does it get saved?"
- **Fail fast with clear errors** — `IllegalArgumentException` with a message is how Mark43 surfaces "your input was wrong" back to the user

## In Mark43 Terms

When you see this in an RMS service:

```java
if (StringUtils.isEmpty(report.getReportTitle())) {
    throw new IllegalArgumentException("reportTitle is required");
}
```

…that's exactly the pattern you're building. When a customer says "I got a 400 error trying to create a report", it usually means a service-layer validation rejected their input. Knowing this pattern lets you find the rule in the code.

## Stuck?

Test failures will say things like `Expected exception IllegalArgumentException to be thrown, but nothing was thrown` — that means you forgot to add the check, or the check's condition is wrong. Read the test name to see which rule it's enforcing.
