package by.youdrive.mappers;

import by.youdrive.commons.User;
import by.youdrive.commons.support.LinkBuilder;
import by.youdrive.domain.UserEntity;
import by.youdrive.resources.AccountResource;

import javax.ws.rs.core.UriInfo;

public class UserMapper {

    public User fromEntity(UriInfo uriInfo, UserEntity entity) {
        User user = createUser(uriInfo);
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setContacts(entity.getContacts());
        user.setId(entity.getId().toString());
        user.setLocale(entity.getLocale().toLanguageTag());
        user.setCustomProperties(entity.getCustomProperties());

        return user;
    }

    public User fromEntityToResponse(UriInfo uriInfo, UserEntity entity) {
        User user = createUser(uriInfo);
        user.setId(entity.getId().toString());
        user.setLocale(entity.getLocale().toLanguageTag());

        return user;
    }

    private User createUser(UriInfo uriInfo) {
        User user = new User();
        // need UserResource for this link
//        user.addLink(new LinkBuilder(uriInfo).linkTo(AccountResource.class).withRel("self").build());
        user.addLink(new LinkBuilder(uriInfo).linkTo(AccountResource.class, "refreshToken").withRel("refreshToken").build());

        return user;
    }
}
