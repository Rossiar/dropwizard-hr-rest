package com.hr.resources;

import com.hr.api.Department;
import com.hr.api.Employee;
import com.hr.db.DepartmentDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by hendersonra on 10/10/16.
 */
public class DepartmentResourceTest {

    public static final DepartmentDAO DAO = mock(DepartmentDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new DepartmentResource(DAO))
            .build();


    @After
    public void tearDown() throws Exception {
        reset(DAO);
    }

    @Test
    public void departmentList() throws Exception {
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            departments.add(new Department(Integer.toString(i), ""));
        }
        when(DAO.findAll()).thenReturn(departments);

        List jsonResponse = resources.client().target("/departments").request().get(List.class);

        assertEquals(departments.size(), jsonResponse.size());
        verify(DAO).findAll();
    }

    @Test
    public void departmentDetails() throws Exception {
        String id = "d009";
        Department expected = new Department(id, "Human Resources");
        when(DAO.findById(id)).thenReturn(expected);

        Department actual = resources.client().target("/departments/" + id).request().get(Department.class);

        assertEquals(expected, actual);
        verify(DAO).findById(id);
    }

    @Test
    public void departmentEmployees() throws Exception {
        String id = "d009";
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            employees.add(new Employee(i, LocalDate.now(), "", "", "", LocalDate.now()));
        }
        when(DAO.findEmployees(id)).thenReturn(employees);

        List<Employee> actual = resources.client().target("/departments/" + id + "/employees").request().get(List.class);

        assertEquals(employees.size(), actual.size());
        verify(DAO).findEmployees(id);
    }

}