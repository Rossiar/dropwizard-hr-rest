package com.hr.resources;

import com.codahale.metrics.annotation.Timed;
import com.hr.api.Department;
import com.hr.api.Employee;
import com.hr.db.DepartmentDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * REST endpoint for accessing department information.
 */
@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    private DepartmentDAO dao;

    public DepartmentResource(DepartmentDAO dao) {
        this.dao = dao;
    }

    @GET
    @Timed
    public List<Department> departmentList() {
        return dao.findAll();
    }

    @Path("/{id}")
    @GET
    @Timed
    public Department departmentDetails(@PathParam("id") String id) {
        return dao.findById(id);
    }

    @Path("/{id}/employees")
    @GET
    @Timed
    public List<Employee> departmentEmployees(@PathParam("id") String id) {
        return dao.findEmployees(id);
    }
}
