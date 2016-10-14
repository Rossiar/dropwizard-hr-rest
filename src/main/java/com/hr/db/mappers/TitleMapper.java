package com.hr.db.mappers;

import com.hr.api.Title;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hendersonra on 10/10/16.
 */
public class TitleMapper implements ResultSetMapper<Title> {

    @Override
    public Title map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Title(resultSet.getString("title"), resultSet.getDate("from_date").toLocalDate(),
                resultSet.getDate("to_date").toLocalDate());
    }
}
