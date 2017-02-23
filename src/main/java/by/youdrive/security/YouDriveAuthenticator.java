package by.youdrive.security;

import by.youdrive.domain.UserEntity;
import by.youdrive.services.UserService;
import com.google.inject.Inject;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Optional;

public class YouDriveAuthenticator implements Authenticator<String, UserEntity> {

    private UserService userService;

    @Inject
    public YouDriveAuthenticator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<UserEntity> authenticate(String credentials) throws AuthenticationException {
        // TODO fill logic content
        UserEntity principal = null;
        return Optional.ofNullable(principal);
    }
}
