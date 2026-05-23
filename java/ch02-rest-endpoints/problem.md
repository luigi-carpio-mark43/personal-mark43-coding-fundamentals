# Chapter 2: REST Endpoints

> **Goal**: Build a JAX-RS resource — the Java class that defines HTTP endpoints. You'll learn the annotation pattern Mark43's RMS backend uses for almost every REST API.

---

## Scenario

When a Support customer asks "why isn't my report loading in the UI?", the frontend hits an HTTP endpoint like `GET /reports/123`. On the server, that request lands in a Java class called a **Resource**. Resources are the entry point for every REST call.

In Mark43 RMS, resources look like this (real RMS code, simplified):

```java
@Path("/reports")
public class ReportResource {
    @GET
    @Path("/{id}")
    public Report getById(@PathParam("id") Long id) {
        return reportService.findById(id);
    }
}
```

You'll build a similar resource here.

## What to Build

Open `src/main/java/com/mark43/practice/ReportResource.java`. The pre-built `Report.java` and `ReportStore.java` are next to it — **do not edit those**. The store is a simple in-memory map of reports the resource can read from.

In `ReportResource.java`, you need to:

1. **Class-level annotation**: Add `@Path("/reports")` above the class declaration so the whole resource is mounted under `/reports`.
2. **`getById` method**: Annotate with `@GET` and `@Path("/{id}")` so a GET request to `/reports/{id}` routes here. Bind the path parameter with `@PathParam("id")`. Return the report from `store.findById(id)`.
3. **`listByDepartment` method**: Annotate with `@GET`. Bind the query string parameter `?departmentId=` with `@QueryParam("departmentId")`. Return `store.findByDepartmentId(departmentId)`.
4. **`createReport` method**: Annotate with `@POST`. Call `store.save(report)` and return the result.

## How to Run

From this directory:

```
mvn test
```

The tests use reflection to verify your annotations are present AND that the methods do the right thing. Both must pass.

## What This Teaches

- **Annotations as configuration** — `@Path`, `@GET`, `@POST` tell the framework how to route HTTP requests
- **Parameter binding** — `@PathParam` reads URL path segments; `@QueryParam` reads `?key=value`; method body parameters get the request payload
- **Resource = the boundary between HTTP and your code** — annotations on the outside, plain Java calls on the inside

## In Mark43 Terms

Every `*Resource.java` file in `rms-services/` follows this pattern. When you're tracing a customer issue from a URL, the URL maps to a `@Path` annotation on some resource class. Once you can read this pattern, you can find any endpoint in RMS in under a minute.

> **Note on namespaces**: RMS uses `javax.ws.rs.*` (older). Modern Java apps use `jakarta.ws.rs.*` (Jakarta EE rename). The annotation names are identical — only the import package differs. We use `javax` here to match RMS literally.

## Stuck?

Look at the imports already in the skeleton file — they tell you which annotations you have available. If a test fails, the message will name the missing annotation or wrong return value.
