package com.hr.resources;

import com.codahale.metrics.annotation.Timed;
import com.hr.api.Employee;
import com.hr.api.Salary;
import com.hr.api.Title;
import com.hr.auth.Secured;
import com.hr.db.EmployeeDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * REST endpoint for accessing employee information.
 */
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private EmployeeDAO dao;

    private final int DEFAULT_PAGE_SIZE = 100;


    public EmployeeResource(EmployeeDAO dao) {
        this.dao = dao;
    }

    @GET
    @Timed
    public List<Employee> employeeList(@QueryParam("pageSize") int page_size) {
        return dao.findAll(page_size != 0 ? page_size : DEFAULT_PAGE_SIZE);
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
