package com.hr.auth;

import com.hr.api.User;
import com.hr.db.UserDAO;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * Filters any request to hit an endpoint in this application that is marked with @Secured
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    /**
     * Cache users to avoid expensive database calls
     */
    private Map<String, User> cache;
    /**
     * Provides database access
     */
    private UserDAO dao;

    public AuthenticationFilter(UserDAO dao) {
        this.dao = dao;
        this.cache = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String apiKeyGuess = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (apiKeyGuess != null) {
            // check the cache
            User found = cache.get(apiKeyGuess);
            if (found == null) {
                found = dao.findByKey(apiKeyGuess);
            }

            // place the key in cache and allow the request through
            if (found != null && apiKeyGuess.equals(found.getApiKey())) {
                cache.put(apiKeyGuess, found);
                return;
            }
        }

        // if bad anything happened, deny the request
        containerRequestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
