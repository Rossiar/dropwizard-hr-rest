package com.hr.resources;

import com.codahale.metrics.annotation.Timed;
import com.hr.api.Employee;
import com.hr.api.Salary;
import com.hr.api.Title;
import com.hr.auth.Secured;
import com.hr.db.EmployeeDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * REST endpoint for accessing employee information.
 */
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    public static final int MAX_PAGE_SIZE = 100000;
    private final int defaultPageSize;

    private EmployeeDAO dao;

    public EmployeeResource(EmployeeDAO dao, int defaultPageSize) {
        this.dao = dao;
        this.defaultPageSize = defaultPageSize;
    }

    @GET
    @Timed
    public Response employeeList(@QueryParam("pageSize") int page_size) {
        // takes care of pages that are too large
        if (page_size >= MAX_PAGE_SIZE) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Requested page size exceeds the limit of " + defaultPageSize)
                    .build();
        }

        // takes care of pages that are too small or if no query parameter was supplied (just defaults to 0)
        return Response.ok()
                .entity(dao.findAll(page_size > 0 ? page_size : defaultPageSize))
                .build();
    }

    @Path("/{id}")
    @GET
    @Timed
    public Employee employeeDetailed(@PathParam("id") Long employeeId) {
        return dao.findById(employeeId);
    }

    @Path("/{id}/titles")
    @GET
    @Timed
    public List<Title> employeeTitles(@PathParam("id") Long employeeId) {
        return dao.findTitles(employeeId);
    }

    @Path("/{id}/salaries")
    @GET
    @Secured
    @Timed
    public List<Salary> employeeSalaries(@PathParam("id") Long employeeId) {
        return dao.findSalaries(employeeId);
    }
}
