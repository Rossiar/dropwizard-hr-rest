package com.hr.db.mappers;

import com.hr.api.Employee;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hendersonra on 07/10/16.
 */
public class EmployeeMapper implements ResultSetMapper<Employee> {

    @Override
    public Employee map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Employee(resultSet.getLong("emp_no"),
                resultSet.getDate("birth_date").toLocalDate(),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("gender"),
                resultSet.getDate("hire_date").toLocalDate());
    }
}
