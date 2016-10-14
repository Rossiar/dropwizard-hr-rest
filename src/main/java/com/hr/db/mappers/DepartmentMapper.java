package com.hr.db.mappers;

import com.hr.api.Department;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hendersonra on 10/10/16.
 */
public class DepartmentMapper implements ResultSetMapper<Department> {


    @Override
    public Department map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Department(resultSet.getString("dept_no"), resultSet.getString("dept_name"));
    }
}
