package integration.com.hr.commands;

import com.hr.HumanResourcesApplication;
import com.hr.HumanResourcesConfiguration;
import com.hr.commands.CreateUserCommand;
import io.dropwizard.cli.Cli;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.util.JarLocation;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by hendersonra on 14/10/16.
 */
public class CreateUserCommandIntegrationTest {

    private Cli cli;

    @Before
    public void setUp() throws Exception {
        // Setup necessary mock
        final JarLocation location = mock(JarLocation.class);
        when(location.getVersion()).thenReturn(Optional.of("1.0.0"));

        // Add commands you want to test
        HumanResourcesApplication app = new HumanResourcesApplication();
        final Bootstrap<HumanResourcesConfiguration> bootstrap = new Bootstrap<>(app);
        bootstrap.addCommand(new CreateUserCommand(app));

        // Build what'll run the command and interpret arguments
        cli = new Cli(location, bootstrap, System.out, System.err);
    }

    @Test
    public void run() throws Exception {
        assertTrue(cli.run("create-admin", "--username", "carl", "src/test/resources/test-db.yml"));
    }

}