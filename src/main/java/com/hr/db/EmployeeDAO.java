package com.hr.db;

import com.hr.api.Employee;
import com.hr.api.Salary;
import com.hr.api.Title;
import com.hr.db.mappers.EmployeeMapper;
import com.hr.db.mappers.SalaryMapper;
import com.hr.db.mappers.TitleMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by hendersonra on 07/10/16.
 */
@RegisterMapper(EmployeeMapper.class)
public interface EmployeeDAO {

    @SqlQuery("select * from employees order by emp_no limit :page_size")
    List<Employee> findAll(@Bind("page_size") int pageSize);

    @SqlQuery("select * from employees where emp_no = :it")
    Employee findById(@Bind Long employeeId);

    @Mapper(TitleMapper.class)
    @SqlQuery("select * from titles where emp_no = :it")
    List<Title> findTitles(@Bind Long employeeId);

    @Mapper(SalaryMapper.class)
    @SqlQuery("select * from salaries where emp_no = :it")
    List<Salary> findSalaries(@Bind Long employeeId);
}
