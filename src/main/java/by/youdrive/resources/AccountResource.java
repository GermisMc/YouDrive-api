package by.youdrive.resources;

import by.youdrive.commons.OA2ClientResp;
import by.youdrive.commons.User;
import by.youdrive.domain.UserEntity;
import by.youdrive.exceptions.YouDriveException;
import by.youdrive.mappers.UserMapper;
import by.youdrive.services.HttpService;
import by.youdrive.services.UserService;
import com.google.inject.Inject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/account")
public class AccountResource {

    @Context
    UriInfo uriInfo;

    @Inject
    private HttpService httpService;

    @Inject
    private UserService userService;

    @Inject
    private UserMapper userMapper;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User createAccount(@NotNull @Valid User user) {

        UserEntity userEntity = userService.registerNewUser(user.getFirstName(), user.getLastName(),
                user.getContacts(), false, null, "", null);
        try {

            OA2ClientResp response = httpService.registerClientInOAuth2(userEntity.getId(), userEntity.getName(),
                    userEntity.getContacts().getEmails().get(0), null);

            userService.updateUser(userEntity, response.getSecret());
        }
        catch (Exception e) {
            userService.deleteUser(userEntity);
            throw new YouDriveException(e);
        }

        // for debug only
        List<UserEntity> entities = userService.getAll();
        UserEntity entity = entities.get(0);
        User createdUser = userMapper.fromEntity(uriInfo, entity);

        return createdUser;
    }
}
