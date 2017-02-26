package by.youdrive.security;

import by.youdrive.commons.TokenInfo;
import by.youdrive.domain.ID;
import by.youdrive.domain.UserEntity;
import by.youdrive.services.HttpService;
import by.youdrive.services.UserService;
import com.google.inject.Inject;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Optional;

public class YouDriveAuthenticator implements Authenticator<String, UserEntity> {

    private UserService userService;
    private HttpService httpService;

    @Inject
    public YouDriveAuthenticator(UserService userService, HttpService httpService) {
        this.userService = userService;
        this.httpService = httpService;
    }

    @Override
    public Optional<UserEntity> authenticate(String accessToken) throws AuthenticationException {
        TokenInfo tokenInfo = httpService.verifyToken(accessToken);
        if (tokenInfo.getStatusCode() == 0) {
            return userService.findById(ID.from(tokenInfo.getAudience()));
        }
        return Optional.empty();
    }
}
