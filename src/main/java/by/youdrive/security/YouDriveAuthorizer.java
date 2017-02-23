package by.youdrive.security;

import by.youdrive.domain.UserEntity;
import io.dropwizard.auth.Authorizer;

public class YouDriveAuthorizer implements Authorizer<UserEntity> {
    @Override
    public boolean authorize(UserEntity principal, String role) {
        return "ADMIN".equalsIgnoreCase(role) && principal.isAdmin();
    }
}
