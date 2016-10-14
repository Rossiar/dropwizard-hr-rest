package com.hr.resources;

import com.codahale.metrics.annotation.Timed;
import com.hr.api.SimpleUser;
import com.hr.api.User;
import com.hr.api.factory.UserFactory;
import com.hr.auth.Secured;
import com.hr.db.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * REST endpoint for accessing user information.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private UserDAO dao;

    public UserResource(UserDAO dao) {
        this.dao = dao;
    }

    @GET
    @Path("/{key}")
    @Secured
    @Timed
    public User findByKey(@PathParam("key") String key) {
        return dao.findByKey(key);
    }

    @GET
    @Secured
    @Timed
    public List<SimpleUser> userList() {
        return dao.findAll();
    }

    @POST
    @Secured
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(SimpleUser toAdd) {
        if (toAdd == null) {
            return Response.serverError()
                    .entity("Must provide a user name in the form {\"name\"=\"your-name\"}}")
                    .build();
        }

        String name = toAdd.getName();
        LOGGER.info("Creating new user {}", name);
        User user = UserFactory.createUser(name);
        dao.createUser(user.getName(), user.getApiKey());
        return Response.ok(new SimpleUser(name), MediaType.APPLICATION_JSON)
                .build();
    }

    @DELETE
    @Secured
    @Timed
    public Response removeUser(@QueryParam("name") String name) {
        if (name == null || name.isEmpty()) {
            return Response.noContent().entity(String.format("%s was not found", name)).build();
        }

        LOGGER.info("Deleting user {}", name);
        dao.deleteUser(name);
        return Response.ok().build();
    }
}
