package com.hr.commands;

import com.hr.HumanResourcesConfiguration;
import com.hr.api.User;
import com.hr.api.factory.UserFactory;
import com.hr.db.UserDAO;
import io.dropwizard.Application;
import io.dropwizard.cli.EnvironmentCommand;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Run before the main application, creates the initial admin user that can then create other users
 * using their api key and the REST API.
 */
public class CreateUserCommand extends EnvironmentCommand<HumanResourcesConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserCommand.class);

    public CreateUserCommand(Application<HumanResourcesConfiguration> application) {
        super(application, "create-admin", "Creates a new admin user");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);

        subparser.addArgument("-u", "--username")
                .dest("username")
                .type(String.class)
                .required(true)
                .help("Username");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void run(Environment environment, Namespace namespace,
                       HumanResourcesConfiguration humanResourcesConfiguration) throws Exception {
        User newAdmin = UserFactory.createUser(namespace.getString("username"));
        String newApiKey = newAdmin.getApiKey();

        final DBI jdbi = new DBIFactory().build(environment, humanResourcesConfiguration.getDatabase(), "mysql");
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);
        userDAO.createUser(newAdmin.getName(), newApiKey);
        LOGGER.info("New admin user created, api key is {}", newApiKey);
    }
}
