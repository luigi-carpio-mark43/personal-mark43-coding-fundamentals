package com.mark43.practice;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * The Resource layer - JAX-RS annotations, delegates to ReportService.
 *
 * Build by following the TODOs. The Resource should do NO logic itself - just delegate.
 */
/*
 * TODO 1: Add @Path("/reports") on the line directly above the class declaration.
 */
public class ReportResource {

    private final User currentUser;
    private final ReportService service;

    public ReportResource(User currentUser, ReportService service) {
        this.currentUser = currentUser;
        this.service = service;
    }

    /*
     * TODO 2: Annotate with @GET and @Path("/{id}").
     *         Change parameter to: @PathParam("id") Long id
     *         Return: service.getReport(currentUser, id)
     */
    public Report getById(Long id) {
        return null;
    }

    /*
     * TODO 3: Annotate with @GET.
     *         Change parameter to: @QueryParam("departmentId") Long departmentId
     *         Return: service.listReportsForDepartment(currentUser, departmentId)
     */
    public List<Report> listByDepartment(Long departmentId) {
        return null;
    }

    /*
     * TODO 4: Annotate with @POST.
     *         Return: service.createReport(currentUser, report)
     */
    public Report createReport(Report report) {
        return null;
    }
}
