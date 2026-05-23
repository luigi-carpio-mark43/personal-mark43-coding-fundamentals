# Chapter 1: Build a Report Class

> **Goal**: Build a `Report` class from scratch. You'll learn how Java classes are structured by writing one — fields, constructor, getters, and a method that changes state.

---

## Scenario

In Mark43, a **Report** is the core record for any incident a department handles — theft, assault, traffic accident, etc. Every report:
- Has a unique numeric `id`
- Belongs to a `department`
- Has a human-readable `reportNumber` (like "RPT-2024-00193")
- Has a `reportTitle` (like "Theft from Vehicle")
- Can be `sealed` (locked so only privileged users can view it)
- Has a `createdDateUtc` timestamp

You're going to build the `Report` class that holds this data.

## What to Build

Open `src/main/java/com/mark43/practice/Report.java`. It has TODO markers showing exactly what to add. You need:

1. **Six private fields** — see the TODOs for the names and types
2. **A constructor** that takes all six values and assigns them to the fields
3. **A getter for each field** — `getId()`, `getDepartmentId()`, etc.
   - Note: the boolean getter is conventionally named `isSealed()`, not `getIsSealed()`
4. **A `seal()` method** that flips `isSealed` to `true`

## How to Run

From this directory (`java/ch01-classes`):

```
mvn test
```

- If you see `BUILD SUCCESS` and `Tests run: 4, Failures: 0`, you're done.
- If you see `BUILD FAILURE`, read the error message — it tells you exactly what went wrong.

## What This Teaches

- **Class structure** — fields hold data, methods do things
- **Encapsulation** — fields are `private`, getters are `public`. The outside world can read the data but not corrupt it directly.
- **Constructor pattern** — how an object gets its initial state
- **Naming conventions** — camelCase for fields, `is` prefix for boolean getters

## In Mark43 Terms

This is the same shape as the real `Report.java` in the RMS codebase. The real class has more fields (maybe 30+) and is generated from a database schema, but the pattern — private fields, a constructor, getters — is identical. Once you can build this, you can read any entity class in RMS.

## Stuck?

Ask your tutor. Try to make sense of the test failure first, then ask for a hint. The tutor won't write the answer for you, but they'll point at the right spot to look.
