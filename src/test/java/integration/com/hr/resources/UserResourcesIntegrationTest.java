package integration.com.hr.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr.HumanResourcesApplication;
import com.hr.HumanResourcesConfiguration;
import com.hr.api.SimpleUser;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hendersonra on 13/10/16.
 */
public class UserResourcesIntegrationTest {

    @ClassRule
    public static final DropwizardAppRule<HumanResourcesConfiguration> RULE =
            new DropwizardAppRule<>(HumanResourcesApplication.class,
                    ResourceHelpers.resourceFilePath("test-db.yml"));

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    /**
     * Must match one of the keys in test-db.sql
     */
    private static final String API_KEY = "secret";

    @Test
    public void GetAllUsers() throws IOException {
        Client client = ClientBuilder.newClient();

        Response response = client.target(
                String.format("http://localhost:%d/users", RULE.getLocalPort()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, API_KEY)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        assertNotNull(response.getEntity());
    }

    @Test
    public void GetAllUsersUnauthorized() throws IOException {
        Client client = ClientBuilder.newClient();

        Response response = client.target(
                String.format("http://localhost:%d/users", RULE.getLocalPort()))
                .request()
                .get();

        assertEquals(401, response.getStatus());
    }

    @Test
    public void PostNewUser() throws IOException {
        Client client = ClientBuilder.newClient();
        SimpleUser user = new SimpleUser("sandra");
        Response response = client.target(
                String.format("http://localhost:%d/users", RULE.getLocalPort()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, API_KEY)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(200, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        assertNotNull(response.getEntity());
        SimpleUser created = MAPPER.readValue((InputStream) response.getEntity(), SimpleUser.class);
        assertEquals(user.getName(), created.getName());
    }

    @Test
    public void PostNewUserUnauthorized() throws IOException {
        Client client = ClientBuilder.newClient();
        SimpleUser user = new SimpleUser("sandra");
        Response response = client.target(
                String.format("http://localhost:%d/users", RULE.getLocalPort()))
                .request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(401, response.getStatus());
    }


    @Test
    public void PostEmptyUser() throws IOException {
        Client client = ClientBuilder.newClient();
        Response response = client.target(
                String.format("http://localhost:%d/users", RULE.getLocalPort()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, API_KEY)
                .post(null);

        assertEquals(500, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        assertNotNull(response.getEntity());
        String content = new Scanner(((InputStream) response.getEntity()), "utf-8")
                .useDelimiter("\\Z").next();
        System.out.println(content);
    }

    @Test
    public void RemoveUser() throws IOException {
        Client client = ClientBuilder.newClient();
        String name = "dave";
        Response response = client.target(
                String.format("http://localhost:%d/users?name=%s", RULE.getLocalPort(), name))
                .request()
                .header(HttpHeaders.AUTHORIZATION, API_KEY)
                .delete();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void RemoveUserEmptyName() throws IOException {
        Client client = ClientBuilder.newClient();
        String name = "";
        Response response = client.target(
                String.format("http://localhost:%d/users?name=%s", RULE.getLocalPort(), name))
                .request()
                .header(HttpHeaders.AUTHORIZATION, API_KEY)
                .delete();

        assertEquals(204, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        assertNotNull(response.getEntity());
    }

    @Test
    public void RemoveUserUnauthorized() throws IOException {
        Client client = ClientBuilder.newClient();
        String name = "dave";
        Response response = client.target(
                String.format("http://localhost:%d/users?name=%s", RULE.getLocalPort(), name))
                .request()
                .delete();

        assertEquals(401, response.getStatus());
    }
}
