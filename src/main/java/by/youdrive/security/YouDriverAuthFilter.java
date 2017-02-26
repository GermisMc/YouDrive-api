package by.youdrive.security;

import by.youdrive.YouDriveApiConfiguration;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Priority(Priorities.AUTHENTICATION)
public class YouDriverAuthFilter<P extends Principal> extends AuthFilter<String, P> {
    private static final Logger logger = LoggerFactory.getLogger(YouDriverAuthFilter.class);
    YouDriveApiConfiguration.OAuth2Config auth2Config;

    YouDriverAuthFilter(YouDriveApiConfiguration configuration) {
        auth2Config = configuration.getoAuth2Config();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final String accessToken = requestContext.getCookies().get(auth2Config.getAuthToken()).getValue();
        if (accessToken != null) {
            try {
                final Optional<P> principal = authenticator.authenticate(accessToken);
                if (principal.isPresent()) {
                    requestContext.setSecurityContext(new SecurityContext() {
                        @Override
                        public Principal getUserPrincipal() {
                            return principal.get();
                        }

                        @Override
                        public boolean isUserInRole(String role) {
                            return authorizer.authorize(principal.get(), role);
                        }

                        @Override
                        public boolean isSecure() {
                            return requestContext.getSecurityContext().isSecure();
                        }

                        @Override
                        public String getAuthenticationScheme() {
                            return auth2Config.getAuthToken();
                        }
                    });
                    return;
                }
            }
            catch (AuthenticationException e) {
                logger.warn("Error authenticating credentials", e);
                throw new InternalServerErrorException();
            }
        }

        throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
    }

    public static class Builder<P extends Principal> extends AuthFilterBuilder<String, P, YouDriverAuthFilter<P>> {
        YouDriveApiConfiguration configuration;

        public Builder(YouDriveApiConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        protected YouDriverAuthFilter<P> newInstance() {
            return new YouDriverAuthFilter<>(configuration);
        }
    }
}
