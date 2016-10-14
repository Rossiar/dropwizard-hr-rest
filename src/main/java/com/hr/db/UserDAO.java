package com.hr.db;

import com.hr.api.SimpleUser;
import com.hr.api.User;
import com.hr.db.mappers.SimpleUserMapper;
import com.hr.db.mappers.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by hendersonra on 11/10/16.
 */
@RegisterMapper(UserMapper.class)
public interface UserDAO {

    @SqlQuery("select user_name from users")
    @Mapper(SimpleUserMapper.class)
    List<SimpleUser> findAll();

    @SqlQuery("select * from users where api_key = :it")
    User findByKey(@Bind String key);

    @SqlUpdate("insert into users (user_name, api_key) values (:user_name, :api_key)")
    void createUser(@Bind("user_name") String userName, @Bind("api_key") String apiKey);

    @SqlUpdate("delete from users where user_name = :it")
    void deleteUser(@Bind String userName);
}
