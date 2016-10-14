package com.hr;

import com.hr.auth.AuthenticationFilter;
import com.hr.commands.CreateUserCommand;
import com.hr.db.DepartmentDAO;
import com.hr.db.EmployeeDAO;
import com.hr.db.UserDAO;
import com.hr.resources.DepartmentResource;
import com.hr.resources.EmployeeResource;
import com.hr.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

/**
 * Main application
 */
public class HumanResourcesApplication extends Application<HumanResourcesConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HumanResourcesApplication().run(args);
    }

    @Override
    public String getName() {
        return "HR";
    }

    @Override
    public void initialize(final Bootstrap<HumanResourcesConfiguration> bootstrap) {
        // unwraps exceptions from JDBI
        bootstrap.addBundle(new DBIExceptionsBundle());

        bootstrap.addCommand(new CreateUserCommand(this));
    }

    @Override
    public void run(final HumanResourcesConfiguration configuration,
                    final Environment environment) {
        // database
        final DBI jdbi = new DBIFactory().build(environment, configuration.getDatabase(), "mysql");
        environment.jersey().register(new EmployeeResource(jdbi.onDemand(EmployeeDAO.class)));
        environment.jersey().register(new DepartmentResource(jdbi.onDemand(DepartmentDAO.class)));
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);
        environment.jersey().register(new UserResource(userDAO));

        // security
        environment.jersey().register(new AuthenticationFilter(userDAO));
    }

}
