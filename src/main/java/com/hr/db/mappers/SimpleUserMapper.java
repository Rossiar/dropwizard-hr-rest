package com.hr.db.mappers;

import com.hr.api.SimpleUser;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hendersonra on 11/10/16.
 */
public class SimpleUserMapper implements ResultSetMapper<SimpleUser> {

    @Override
    public SimpleUser map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new SimpleUser(resultSet.getString("user_name"));
    }
}
