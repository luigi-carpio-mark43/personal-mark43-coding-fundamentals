# Mark43 Coding Fundamentals

**Hands-on coding companion to [mark43-support-fundamentals](../mark43-support-fundamentals).** Where that repo teaches you to *read* Mark43 code, this one teaches you to *write* it — same chapter structure, same Mark43 flavor (reports, cases, arrests, persons, permissions), but you build the code yourself and a test suite tells you when you got it right.

## Prerequisites

You'll need these tools installed and on your system `PATH`:

- **JDK 17+** — verify with `java -version` (Java + SQL chapters)
- **Maven 3.6+** — verify with `mvn -version` (Java + SQL chapters)
- **Node.js 18+** — verify with `node -v` and `npm -v` (TypeScript chapters)
- A text editor or IDE (IntelliJ IDEA Community is free and what Mark43 uses)

> **On a Mark43-issued machine?** All of these are available through the **Mark43 Company Portal** — search for *JDK*, *Maven*, *Node*, *IntelliJ*, *VS Code*, *Python*, etc. and install from there. The Company Portal is the canonical source for dev tools.
>
> After installing, **open a new terminal window** so `PATH` picks up the new tools — existing terminals won't see them.

If something doesn't work after installing, ask your tutor (Claude) to walk you through verifying the setup.

## How an Exercise Works

Each chapter is a self-contained Maven project under `java/chXX-name/`:

```
java/ch01-classes/
  problem.md                     read this first — the exercise scenario
  pom.xml                        Maven config (don't touch)
  src/main/java/...Report.java   skeleton with TODO markers — YOU edit this
  src/test/java/...ReportTest.java   pre-written tests that grade your work
```

The loop:
1. Read `problem.md`
2. Open the skeleton file and fill in the `// TODO` markers
3. From the chapter directory, run `mvn test`
4. Red = tests failed (read the error). Green = you're done.

## Curriculum (Java, in order)

| Chapter | Topic | What You'll Build |
|---------|-------|-------------------|
| 01 | Classes | A `Report` class with fields, constructor, getters, and a `seal()` method |
| 02 | REST Endpoints | A JAX-RS resource: `@Path`, `@GET`, `@POST`, `@PathParam`, `@QueryParam` |
| 03 | Services & Logic | A service method with validation rules and an `IllegalArgumentException` guard |
| 04 | Permissions | `PermissionChecker.ensurePermission()` + a gated `sealReport(...)` method |
| 05 | Data & Entities | An in-memory repository with Stream-based queries and `Optional` returns |
| 06 | Errors & Exceptions | A custom `ReportNotFoundException` + a `try/catch` to convert it to `Optional` |
| Project | Mini-feature | Reports CRUD end-to-end: Resource → Service → Repository with permissions and error handling |

All chapters are scaffolded and ready. Work through them in order — each builds on concepts from the previous ones. The capstone project assumes you understand patterns from ch02 through ch06.

## Running Tests

From a chapter directory:

```
cd java/ch01-classes
mvn test
```

Or from the root (runs all chapters):

```
mvn test
```

## Companion Repo

When you're stuck on a concept, the reading repo (`../mark43-support-fundamentals`) has exercises that explain the patterns. Use them as a reference — every coding chapter here has a matching reading chapter there.
