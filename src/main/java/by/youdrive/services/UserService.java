package by.youdrive.services;

import by.youdrive.domain.ID;
import by.youdrive.domain.JSON;
import by.youdrive.domain.UserEntity;
import by.youdrive.jdbi.YouDriveDAO;
import com.google.inject.Inject;

import java.util.*;

public class UserService {

    @Inject
    private YouDriveDAO dao;

    public UserEntity registerNewUser(String firstName, String lastName, UserEntity.Contacts contacts, boolean admin, Locale locale,
                                      String secret, Object customProperties) {

        if (locale == null) {
            locale = Locale.US;
        }

        UserEntity userEntity = new UserEntity(ID.create(), firstName, lastName, contacts, admin, locale, secret,
                customProperties == null ? null : JSON.fromObject(customProperties));

        dao.userDAO().createUser(userEntity);
        return userEntity;
    }

    public UserEntity registerNewUser(UserEntity.Contacts contacts, boolean admin, String secret) {
        return registerNewUser(null, null, contacts, admin, null, secret, null);
    }

    public UserEntity updateUser(UserEntity userEntity, String firstName, String lastName, UserEntity.Contacts contacts, boolean admin,
                                 Locale locale, String secret, Object customProperties) {

        userEntity = new UserEntity(userEntity.getId(), firstName, lastName, contacts, admin, locale, secret,
                customProperties == null ? null : JSON.fromObject(customProperties));

        dao.userDAO().updateUser(userEntity);
        return userEntity;
    }

    public UserEntity updateUser(UserEntity entity, String secret) {
        ID userId = entity.getId();
        entity = new UserEntity(userId, entity.getFirstName(), entity.getLastName(), entity.getContacts(), entity.isAdmin(),
                entity.getLocale(), secret, entity.getCustomProperties());

        dao.userDAO().updateUser(userId, entity.getSecret());
        return entity;
    }

    public Optional<UserEntity> findById(ID id) {
        return Optional.ofNullable(dao.userDAO().findById(id));
    }

    public Optional<UserEntity> findByEmail(String email) {
        return Optional.ofNullable(dao.userDAO().findByEmail(email));
    }

    public List<UserEntity> getAll() {
        return dao.userDAO().getAll();
    }

    public void deleteUser(UserEntity entity) {
        dao.userDAO().deleteById(entity.getId());
    }
}
