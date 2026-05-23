package com.mark43.practice;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.List;

/*
 * TODO 1: Add @Path("/reports") on the line directly above `public class ReportResource {`
 *         so this whole resource is mounted at /reports.
 */
public class ReportResource {

    private final ReportStore store;

    public ReportResource(ReportStore store) {
        this.store = store;
    }

    /*
     * TODO 2: Annotate this method with TWO annotations:
     *           @GET
     *           @Path("/{id}")
     *         And change the parameter to:
     *           @PathParam("id") Long id
     *         Then return store.findById(id) instead of null.
     */
    public Report getById(Long id) {
        return null;
    }

    /*
     * TODO 3: Annotate this method with @GET (no @Path - it inherits the class @Path).
     *         Change the parameter to:
     *           @QueryParam("departmentId") Long departmentId
     *         Return store.findByDepartmentId(departmentId).
     */
    public List<Report> listByDepartment(Long departmentId) {
        return null;
    }

    /*
     * TODO 4: Annotate this method with @POST.
     *         The Report body parameter is unannotated - JAX-RS treats unannotated
     *         method parameters on @POST as the request body.
     *         Call store.save(report) and return the result.
     */
    public Report createReport(Report report) {
        return null;
    }
}
