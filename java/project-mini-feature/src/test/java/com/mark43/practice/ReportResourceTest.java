package com.mark43.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportResourceTest {

    private ReportRepository repository;
    private ReportService service;

    @BeforeEach
    void setUp() {
        repository = new ReportRepository();
        service = new ReportService(repository);
    }

    private static User userWith(String... abilities) {
        Set<String> set = new HashSet<>();
        Collections.addAll(set, abilities);
        return new User(1L, "Alice", set);
    }

    // ----- annotation checks (Resource layer) -----

    @Test
    void resourceClassHasPathReports() {
        Path path = ReportResource.class.getAnnotation(Path.class);
        assertNotNull(path, "ReportResource should be annotated with @Path");
        assertEquals("/reports", path.value());
    }

    @Test
    void getByIdHasGetAndPath() throws Exception {
        Method method = ReportResource.class.getDeclaredMethod("getById", Long.class);
        assertNotNull(method.getAnnotation(GET.class), "getById should have @GET");
        Path p = method.getAnnotation(Path.class);
        assertNotNull(p, "getById should have @Path");
        assertEquals("/{id}", p.value());

        PathParam pp = method.getParameters()[0].getAnnotation(PathParam.class);
        assertNotNull(pp, "getById parameter should have @PathParam");
        assertEquals("id", pp.value());
    }

    @Test
    void listByDepartmentHasGetAndQueryParam() throws Exception {
        Method method = ReportResource.class.getDeclaredMethod("listByDepartment", Long.class);
        assertNotNull(method.getAnnotation(GET.class), "listByDepartment should have @GET");

        QueryParam qp = method.getParameters()[0].getAnnotation(QueryParam.class);
        assertNotNull(qp, "listByDepartment parameter should have @QueryParam");
        assertEquals("departmentId", qp.value());
    }

    @Test
    void createReportHasPost() throws Exception {
        Method method = ReportResource.class.getDeclaredMethod("createReport", Report.class);
        assertNotNull(method.getAnnotation(POST.class), "createReport should have @POST");
    }

    // ----- delegation checks (Resource → Service) -----

    @Test
    void getById_delegatesToServiceWithCurrentUser() {
        User user = userWith(Abilities.VIEW_REPORTS);
        Report report = new Report(1L, 100L, "RPT-001", "Theft", false, Instant.now());
        repository.save(report);

        ReportResource resource = new ReportResource(user, service);
        Report result = resource.getById(1L);

        assertEquals(report, result);
    }

    @Test
    void getById_propagatesPermissionDeniedFromService() {
        User user = userWith();
        ReportResource resource = new ReportResource(user, service);

        assertThrows(PermissionDeniedException.class, () -> resource.getById(1L));
    }

    @Test
    void getById_propagatesReportNotFoundFromService() {
        User user = userWith(Abilities.VIEW_REPORTS);
        ReportResource resource = new ReportResource(user, service);

        assertThrows(ReportNotFoundException.class, () -> resource.getById(999L));
    }

    @Test
    void listByDepartment_delegatesToService() {
        User user = userWith(Abilities.VIEW_REPORTS);
        Report r1 = new Report(1L, 100L, "RPT-001", "T", false, Instant.now());
        Report r2 = new Report(2L, 100L, "RPT-002", "A", false, Instant.now());
        repository.save(r1);
        repository.save(r2);

        ReportResource resource = new ReportResource(user, service);
        List<Report> result = resource.listByDepartment(100L);

        assertEquals(2, result.size());
        assertTrue(result.contains(r1));
        assertTrue(result.contains(r2));
    }

    @Test
    void createReport_delegatesToService() {
        User user = userWith(Abilities.EDIT_REPORTS);
        Report report = new Report(1L, 100L, "RPT-001", "Theft", false, Instant.now());

        ReportResource resource = new ReportResource(user, service);
        Report result = resource.createReport(report);

        assertEquals(report, result);
        assertTrue(repository.findById(1L).isPresent());
    }

    @Test
    void createReport_propagatesValidationErrorFromService() {
        User user = userWith(Abilities.EDIT_REPORTS);
        Report bad = new Report(1L, 100L, "RPT-001", "", false, Instant.now());

        ReportResource resource = new ReportResource(user, service);

        assertThrows(IllegalArgumentException.class, () -> resource.createReport(bad));
    }
}
