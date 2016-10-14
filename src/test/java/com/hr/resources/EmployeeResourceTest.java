package com.hr.resources;

import com.hr.api.Employee;
import com.hr.api.Salary;
import com.hr.api.Title;
import com.hr.db.EmployeeDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link EmployeeResource}
 */
public class EmployeeResourceTest {

    public static final EmployeeDAO DAO = mock(EmployeeDAO.class);

    public static int DEFAULT_PAGE_SIZE = 1000;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new EmployeeResource(DAO, DEFAULT_PAGE_SIZE))
            .build();


    @After
    public void tearDown() throws Exception {
        reset(DAO);
    }

    @Test
    public void findAllDefaultPageSize() throws Exception {
        int pageSize = 20;
        List<Employee> totalEmployees = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            totalEmployees.add(new Employee(1L, LocalDate.of(1947, Month.MARCH, 24), "Alan", "Sugar", "M", LocalDate.now()));
        }
        when(DAO.findAll(pageSize)).thenReturn(totalEmployees.subList(0, pageSize));

        Response actual = resources.client().target("/employees")
                .request()
                .get();

        verify(DAO).findAll(DEFAULT_PAGE_SIZE);
        assertEquals(200, actual.getStatus());
    }

    @Test
    public void findAllValidPageSize() throws Exception {
        int pageSize = 20;
        List<Employee> totalEmployees = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            totalEmployees.add(new Employee(1L, LocalDate.of(1947, Month.MARCH, 24), "Alan", "Sugar", "M", LocalDate.now()));
        }
        when(DAO.findAll(pageSize)).thenReturn(totalEmployees.subList(0, pageSize));

        Response actual = resources.client().target(String.format("/employees?%s=%d", "pageSize", pageSize))
                .request()
                .get();

        verify(DAO).findAll(pageSize);
        assertEquals(200, actual.getStatus());
    }

    @Test
    public void findAllInvalidPageSize() throws Exception {
        int pageSize = EmployeeResource.MAX_PAGE_SIZE;

        Response actual = resources.client().target(String.format("/employees?%s=%d", "pageSize", pageSize))
                .request()
                .get();

        verify(DAO, never()).findAll(anyInt());
        assertEquals(400, actual.getStatus());
    }

    @Test
    public void findById() throws Exception {
        Long id = 99999999999L;
        Employee expected = new Employee(id, LocalDate.of(1947, Month.MARCH, 24), "Alan", "Sugar", "M", LocalDate.now());
        when(DAO.findById(id)).thenReturn(expected);

        Employee actual = resources.client().target("/employees/" + id).request().get(Employee.class);

        assertEquals(expected, actual);
        verify(DAO).findById(id);
    }

    @Test
    public void employeeTitles() throws Exception {
        Long id = 1234L;
        List<Title> titles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            titles.add(new Title(Integer.toString(i), LocalDate.now(), LocalDate.now()));
        }
        when(DAO.findTitles(id)).thenReturn(titles);

        List<Title> actual = resources.client().target("/employees/" + id + "/titles").request().get(List.class);

        assertEquals(titles.size(), actual.size());
        verify(DAO).findTitles(id);
    }

    @Test
    public void employeeSalaries() throws Exception {
        Long id = 1234L;
        List<Salary> salaries = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            salaries.add(new Salary(i, LocalDate.now(), LocalDate.now()));
        }
        when(DAO.findSalaries(id)).thenReturn(salaries);

        List<Title> actual = resources.client().target("/employees/" + id + "/salaries").request().get(List.class);

        assertEquals(salaries.size(), actual.size());
        verify(DAO).findSalaries(id);
    }
}