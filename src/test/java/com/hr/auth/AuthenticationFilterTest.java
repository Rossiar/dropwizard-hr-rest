package com.hr.auth;

import com.hr.api.User;
import com.hr.db.UserDAO;
import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by hendersonra on 11/10/16.
 */
public class AuthenticationFilterTest {


    @Test
    public void AuthSuccessApiKeyMatchedDatabase() throws Exception {
        String apiKey = "secret";
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(apiKey);
        doNothing().when(requestContext).abortWith(any(Response.class));
        UserDAO dao = mock(UserDAO.class);
        when(dao.findByKey(apiKey)).thenReturn(new User("name", apiKey));
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(dao);

        authenticationFilter.filter(requestContext);
        verify(requestContext, never()).abortWith(any(Response.class));
    }


    @Test
    public void AuthSuccessApiKeyMatchedCache() throws Exception {
        String apiKey = "secret";
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(apiKey);
        doNothing().when(requestContext).abortWith(any(Response.class));
        UserDAO dao = mock(UserDAO.class);
        when(dao.findByKey(apiKey)).thenReturn(new User("name", apiKey));
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(dao);

        authenticationFilter.filter(requestContext);
        authenticationFilter.filter(requestContext);

        verify(dao, times(1)).findByKey(apiKey);
        verify(requestContext, never()).abortWith(any(Response.class));
    }

    @Test
    public void AuthFailedApiKeyNotFoundInHeader() throws Exception {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        doNothing().when(requestContext).abortWith(any(Response.class));
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(mock(UserDAO.class));

        authenticationFilter.filter(requestContext);

        verify(requestContext).abortWith(any(Response.class));
    }

    @Test
    public void AuthFailedApiKeyNotFoundInDatabase() throws Exception {
        String apiKey = "secret";
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(apiKey);
        doNothing().when(requestContext).abortWith(any(Response.class));
        UserDAO dao = mock(UserDAO.class);
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(dao);

        authenticationFilter.filter(requestContext);

        verify(requestContext).abortWith(any(Response.class));
    }

}