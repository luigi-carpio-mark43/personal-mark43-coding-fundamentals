# Capstone Project: Wire Up a Mini Feature

> **Goal**: Tie together everything from ch01-ch06. Build the Service and Resource layers of a small Reports CRUD feature using pre-built building blocks (User, Abilities, Repository, Exceptions).

---

## Scenario

You've learned each layer in isolation. Real Mark43 features are the layers **stacked together**:

```
HTTP request                                              HTTP response
     │                                                          ▲
     ▼                                                          │
ReportResource (ch02)   ←── JAX-RS annotations, delegates       │
     │                                                          │
     ▼                                                          │
ReportService (ch03 + ch04)   ←── validates input, checks perms │
     │                                                          │
     ▼                                                          │
ReportRepository (ch05)   ←── queries the data store           │
     │                                                          │
     ▼                                                          │
   data
```

Exceptions from any layer bubble up to the Resource and become HTTP error responses.

You'll build the **Service** and **Resource**. Everything else is pre-built.

## What's Pre-Built (Don't Edit)

| File | Purpose |
|------|---------|
| `Report.java` | The entity |
| `User.java` + `Abilities.java` | Current user + ability constants |
| `PermissionChecker.java` | `ensurePermission(user, ability)` |
| `PermissionDeniedException.java` | Thrown when permission check fails |
| `ReportNotFoundException.java` | Thrown when a report id has no match |
| `ReportRepository.java` | In-memory repo with `save`, `findById`, `findByDepartmentId` |

## What You'll Build

### 1. `ReportService.java`

Three methods, each combining permission + validation + repository access.

**`createReport(User user, Report report)` — returns `Report`**

1. Check `Abilities.EDIT_REPORTS` permission for `user`
2. Validate input:
   - `report` not null → else `IllegalArgumentException("report cannot be null")`
   - `report.getReportTitle()` not null/empty (after trim) → else `IllegalArgumentException("reportTitle cannot be empty")`
   - `report.getDepartmentId()` not null → else `IllegalArgumentException("departmentId is required")`
3. Call `repository.save(report)` and return the result

**`getReport(User user, Long id)` — returns `Report`**

1. Check `Abilities.VIEW_REPORTS` permission for `user`
2. Call `repository.findById(id)`
3. If the Optional is empty, throw `new ReportNotFoundException(id)`
4. Otherwise return the report (use `.get()` on the Optional)

**`listReportsForDepartment(User user, Long departmentId)` — returns `List<Report>`**

1. Check `Abilities.VIEW_REPORTS` permission for `user`
2. Return `repository.findByDepartmentId(departmentId)`

### 2. `ReportResource.java`

JAX-RS resource that delegates to the service. The Resource holds the current `User` and the `ReportService`.

Add annotations:
- Class-level: `@Path("/reports")`
- `getById(Long id)` → `@GET @Path("/{id})"` + parameter `@PathParam("id")` + delegate to `service.getReport(currentUser, id)`
- `listByDepartment(Long departmentId)` → `@GET` + parameter `@QueryParam("departmentId")` + delegate to `service.listReportsForDepartment(currentUser, departmentId)`
- `createReport(Report report)` → `@POST` + delegate to `service.createReport(currentUser, report)`

## Order of Operations Matters

In every Service method, the **permission check must come first**. If permission fails, validation and repository calls must NOT run.

In every Resource method, the only job is **delegation**. The Resource doesn't validate, doesn't check permissions — it just passes the call through. All real logic lives in the Service.

## How to Run

From this directory:

```
mvn test
```

## What This Teaches

This project IS the pattern Mark43 features follow. Every Resource → Service → Repository → Database call you'll ever trace in RMS has this shape. When you finish this, you can read any RMS feature.

## In Mark43 Terms

When a customer says "I can see reports but can't create them," you can confidently say:
1. They have `VIEW_REPORTS` (because reading works)
2. They lack `EDIT_REPORTS` (because writing fails with 403)
3. The check is in `ReportService.createReport(...)` — the first line of the method body

That diagnostic chain is what these chapters have been building toward.

## Stuck?

- Read the test name first — it tells you which path/scenario it's exercising
- The Service tests are easier to debug than the Resource tests — start there
- If a test says "expected ReportNotFoundException but got PermissionDeniedException," your service is checking permissions before throwing — that's correct order, but the test is supplying a user with permission, so look at why it's denied
