package com.hr.db;

import com.hr.api.Department;
import com.hr.api.Employee;
import com.hr.db.mappers.DepartmentMapper;
import com.hr.db.mappers.EmployeeMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by hendersonra on 10/10/16.
 */
@RegisterMapper(DepartmentMapper.class)
public interface DepartmentDAO {

    @SqlQuery("select * from departments;")
    List<Department> findAll();

    @SqlQuery("select * from departments where dept_no = :it")
    Department findById(@Bind String id);

    @Mapper(EmployeeMapper.class)
    @SqlQuery("select * from dept_emp d inner join employees e where d.emp_no = e.emp_no and d.dept_no = :it")
    List<Employee> findEmployees(@Bind String id);

}
