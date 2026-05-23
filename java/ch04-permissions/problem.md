# Chapter 4: Permissions

> **Goal**: Build the permission-check pattern that gates every sensitive operation in Mark43. You'll write the helper that decides "can this user do this thing?" and apply it to a real method.

---

## Scenario

Mark43 uses **abilities** to control what users can do. An ability is a string like `"VIEW_REPORTS"` or `"SEAL_REPORT"`. Every user has a set of abilities (granted via their role). Before a sensitive operation runs, the code checks: does this user have the right ability? If not, throw a permission error.

In RMS this looks like:

```java
ensurePermission(currentUser, Abilities.SEAL_REPORT);
// ... only reached if the user has SEAL_REPORT
report.setSealed(true);
```

That's what you're building.

## What to Build

Two files:

### 1. `PermissionChecker.java` (the helper)

Implement `ensurePermission(User user, String ability)`. Rules:
- **If `user` is null** → throw `PermissionDeniedException("user is required")`
- **If `ability` is null or empty** → throw `IllegalArgumentException("ability is required")`
- **If the user's abilities do NOT contain `ability`** → throw `PermissionDeniedException("User " + user.getName() + " is missing ability: " + ability)`
- **If the user has the ability** → return normally (no return value, no exception)

### 2. `ReportFacade.java` (the gated method)

Implement `sealReport(User currentUser, Long reportId)`:
1. Call `PermissionChecker.ensurePermission(currentUser, Abilities.SEAL_REPORT)` first
2. Then increment `sealCallCount` (tests use this to verify the seal happened)
3. Return reportId (so callers know which report was sealed)

The order matters: **permission check FIRST**, then the work. If the user lacks the ability, the work must not happen.

## What's Pre-Built (Don't Edit)

- `User.java` — has an id, name, and `Set<String>` of abilities, with `hasAbility(String)`
- `Abilities.java` — constants for `VIEW_REPORTS`, `EDIT_REPORTS`, `SEAL_REPORT`
- `PermissionDeniedException.java` — the exception to throw
- `Report.java` — basic Report class

## How to Run

From this directory:

```
mvn test
```

## What This Teaches

- **Permission-as-string** — Mark43 doesn't use Java's built-in permission API; it uses strings checked against a Set
- **Gate before action** — the check must happen *before* the operation, never after
- **Two flavors of exception** — `IllegalArgumentException` (programmer error: you passed bad input) vs `PermissionDeniedException` (user error: you can't do this)

## In Mark43 Terms

When you see this in an RMS service:

```java
ensurePermission(currentUser, Abilities.EDIT_ARREST_REPORT);
```

…that's RMS's `ensurePermission` helper doing exactly what you're building. When a customer says "I'm getting a 403 Forbidden", it almost always means an `ensurePermission` call threw. You can find the rule by searching for `ensurePermission` in the service class for that feature.

> **Pattern differs by repo**: JMS uses `@PreAuthorize` annotations instead. Security-alerts uses `@Authorization`. But the *concept* — gate sensitive operations behind a permission check — is universal.

## Stuck?

The test names tell you which rule is being checked. Read them carefully — `nullUserThrowsPermissionDenied` means: when user is null, the call should throw `PermissionDeniedException`.
