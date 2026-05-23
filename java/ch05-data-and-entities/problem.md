# Chapter 5: Data and Entities

> **Goal**: Build a repository — the layer that talks to the data store. You'll write the query methods that filter reports by department and seal status using Java Streams.

---

## Scenario

In ch03 you wrote a service that *called* a repository. Now you're writing the repository itself. The repository's job is to store and retrieve entities — it doesn't make business decisions, it just answers questions like "give me all reports for department 100" or "is report 42 in the database?"

In real Mark43 RMS, repositories talk to MySQL via JOOQ (a query builder). Here, you'll use an in-memory list — but the *shape* of the methods is identical. Once you can write these in-memory queries, the JOOQ ones in RMS will look familiar.

## What to Build

Open `src/main/java/com/mark43/practice/ReportRepository.java`. Use the internal `List<Report> data` field to store reports. Implement these methods:

1. **`save(Report report)`** — add `report` to the `data` list and return it.
2. **`findById(Long id)`** — return `Optional<Report>` containing the report whose id matches, or `Optional.empty()` if not found.
3. **`findByDepartmentId(Long departmentId)`** — return a `List<Report>` of all reports in that department.
4. **`findByDepartmentIdAndUnsealed(Long departmentId)`** — return a `List<Report>` of reports in that department that are NOT sealed (`isSealed() == false`).
5. **`countByDepartmentId(Long departmentId)`** — return how many reports are in that department, as a `long`.

You'll use **Java Streams** for the queries. The pattern is:

```java
return data.stream()
    .filter(report -> /* condition */)
    .collect(Collectors.toList());
```

For counting:
```java
return data.stream()
    .filter(report -> /* condition */)
    .count();
```

For `findById`, the `.findFirst()` terminal operation returns an `Optional` already:
```java
return data.stream()
    .filter(report -> report.getId().equals(id))
    .findFirst();
```

## How to Run

From this directory:

```
mvn test
```

## What This Teaches

- **Streams** — Java's modern way to filter, transform, and aggregate collections. You'll see them everywhere in Mark43.
- **`Optional<T>`** — Java's way of saying "this might or might not be there." Forces callers to handle the missing case instead of getting a surprise null.
- **Repository pattern** — the data layer is its own object so services can be tested without a real database.

## In Mark43 Terms

This is what real RMS query methods look like (simplified):

```java
public List<Report> findByDepartmentId(Long departmentId) {
    return dsl.selectFrom(REPORT)
        .where(REPORT.DEPARTMENT_ID.eq(departmentId))
        .fetchInto(Report.class);
}
```

The syntax is JOOQ, not Streams — but the intent is identical: filter by department ID, return a list. Once you understand the filter-by-criteria pattern in Streams, JOOQ queries are easy to follow.

## Stuck?

- Forgot the imports? Streams need `java.util.stream.Collectors`. `Optional` is `java.util.Optional`. Both are already imported in the skeleton.
- `Long.equals` vs `==`: always use `.equals()` for boxed types like `Long`. `==` compares object identity, not value.
- Boolean negation: `!report.isSealed()` means "not sealed."
