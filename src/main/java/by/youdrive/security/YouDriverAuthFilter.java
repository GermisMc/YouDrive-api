package by.youdrive.security;

import io.dropwizard.auth.AuthFilter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import java.io.IOException;
import java.security.Principal;

@Priority(Priorities.AUTHENTICATION)
public class YouDriverAuthFilter<P extends Principal> extends AuthFilter<String, P> {

    YouDriverAuthFilter() {}

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // TODO getting oAuth tokens from cookies
        final String header = requestContext.getCookies().get("SUB").getValue();
        throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
    }

    public static class Builder<P extends Principal> extends AuthFilterBuilder<String, P, YouDriverAuthFilter<P>> {
        @Override
        protected YouDriverAuthFilter<P> newInstance() {
            return new YouDriverAuthFilter<P>();
        }
    }
}
