package by.youdrive.resources;

import by.youdrive.YouDriveApiConfiguration;
import by.youdrive.commons.ErrorMessages;
import by.youdrive.commons.OA2ClientResp;
import by.youdrive.commons.OA2TokenResp;
import by.youdrive.commons.User;
import by.youdrive.domain.UserEntity;
import by.youdrive.exceptions.Messages;
import by.youdrive.exceptions.YouDriveException;
import by.youdrive.mappers.UserMapper;
import by.youdrive.services.HttpService;
import by.youdrive.services.UserService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

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
    public Response getAccount(@NotNull @HeaderParam("user-email") String email) {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);

        UserEntity userEntity = userService.findByEmail(email).orElseThrow(() -> new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessages(Messages.NoResourceFound)).build()));

        try {
            OA2TokenResp tokenResp = httpService.getToken(userEntity.getId(), userEntity.getSecret());
            response = userService.setAuthCookies(response, tokenResp);
        }
        catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new YouDriveException(e);
        }

        return response.build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(@NotNull @Valid User user) {
        YouDriveApiConfiguration.OAuth2Config oAuth2Config = configuration.getoAuth2Config();
        Response.ResponseBuilder response = Response.status(Response.Status.CREATED);

        UserEntity userEntity = userService.registerNewUser(user.getFirstName(), user.getLastName(),
                user.getContacts(), false, null, "", null);
        try {

            OA2ClientResp clientResp = httpService.registerClientInOAuth2(userEntity.getId(), userEntity.getName(),
                    userEntity.getContacts().getEmails().get(0), null);

            String secret = clientResp.getSecret();
            userService.updateUser(userEntity, secret);

            OA2TokenResp tokenResp = httpService.getToken(userEntity.getId(), secret);
            response = userService.setAuthCookies(response, tokenResp);
        }
        catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            userService.deleteUser(userEntity);
            logger.info("User removed", userEntity);
            throw new YouDriveException(e);
        }

        // for debug only
        List<UserEntity> entities = userService.getAll();
        UserEntity entity = entities.get(0);
        User createdUser = userMapper.fromEntity(uriInfo, entity);

        return response.entity(createdUser).build();
    }
}
