package by.youdrive.resources;

import by.youdrive.YouDriveApiConfiguration;
import by.youdrive.commons.*;
import by.youdrive.domain.UserEntity;
import by.youdrive.exceptions.Messages;
import by.youdrive.exceptions.YouDriveException;
import by.youdrive.mappers.UserMapper;
import by.youdrive.services.HttpService;
import by.youdrive.services.UserService;
import com.google.inject.Inject;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Locale;

@Path("/account")
public class AccountResource {

    static private Logger logger = LoggerFactory.getLogger(AccountResource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    private HttpService httpService;

    @Inject
    private UserService userService;

    @Inject
    private UserMapper userMapper;

    @Inject
    private YouDriveApiConfiguration configuration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@NotNull @HeaderParam("user-email") String email) { // TODO input third-party token from cookie
        OA2TokenResp tokenResp;
        YouDriveApiConfiguration.OAuth2Config oAuth2Config = configuration.getoAuth2Config();

        UserEntity userEntity = userService.findByEmail(email).orElseThrow(() -> new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessages(Messages.NoResourceFound)).build()));

        try {
            tokenResp = httpService.getToken(userEntity.getId(), userEntity.getSecret());
        }
        catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new YouDriveException(e);
        }

        User foundUser = userMapper.fromEntityToResponse(uriInfo, userEntity);
        if (tokenResp.getRefreshToken() != null) {
            foundUser.setRefreshToken(tokenResp.getRefreshToken());
        }

        return Response.status(Response.Status.OK).cookie(new NewCookie(
                oAuth2Config.getAuthToken(), tokenResp.getAccessToken())).entity(foundUser).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(@NotNull @Valid User user) {
        OA2TokenResp tokenResp;
        YouDriveApiConfiguration.OAuth2Config oAuth2Config = configuration.getoAuth2Config();

        Locale locale = user.getLocale() == null ? null : Locale.forLanguageTag(user.getLocale());

        UserEntity userEntity = userService.registerNewUser(user.getFirstName(), user.getLastName(),
                user.getContacts(), false, locale, "", user.getCustomProperties());
        try {

            OA2ClientResp clientResp = httpService.registerClientInOAuth2(userEntity.getId(), userEntity.getName(),
                    userEntity.getContacts().getEmails().get(0), null);

            String secret = clientResp.getSecret();
            userEntity = userService.updateUser(userEntity, secret);

            tokenResp = httpService.getToken(userEntity.getId(), userEntity.getSecret());
        }
        catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            userService.deleteUser(userEntity);
            logger.info("User removed", userEntity);
            throw new YouDriveException(e);
        }

        User createdUser = userMapper.fromEntityToResponse(uriInfo, userEntity);

        if (tokenResp.getRefreshToken() != null) {
            createdUser.setRefreshToken(tokenResp.getRefreshToken());
        }

        return Response.status(Response.Status.CREATED).cookie(new NewCookie(
                oAuth2Config.getAuthToken(), tokenResp.getAccessToken())).entity(createdUser).build();
    }

    @POST
    @Path("/refresh")
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshToken(@NotEmpty @QueryParam("refreshToken") String token) {
        OA2TokenResp tokenResp;
        YouDriveApiConfiguration.OAuth2Config oAuth2Config = configuration.getoAuth2Config();

        try {
            tokenResp = httpService.refreshToken(token);
        }
        catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new YouDriveException(e);
        }

        OA2Token refreshToken = new OA2Token();
        refreshToken.setRefreshToken(tokenResp.getRefreshToken());

        return Response.status(Response.Status.OK).cookie(new NewCookie(
                oAuth2Config.getAuthToken(), tokenResp.getAccessToken())).entity(refreshToken).build();
    }
}
