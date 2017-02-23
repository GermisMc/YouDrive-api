package by.youdrive.mappers;

import by.youdrive.commons.User;
import by.youdrive.commons.support.LinkBuilder;
import by.youdrive.domain.UserEntity;
import by.youdrive.resources.AccountResource;

import javax.ws.rs.core.UriInfo;

public class UserMapper {

    public User fromEntity(UriInfo uriInfo, UserEntity entity) {
        User user = new User();
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setContacts(entity.getContacts());
        user.setId(entity.getId().toString());
        user.setLocale(entity.getLocale().toLanguageTag());
        user.addLink(new LinkBuilder(uriInfo).linkTo(AccountResource.class).withRel("self").build());

        return user;
    }
}
