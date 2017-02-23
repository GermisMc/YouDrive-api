package by.youdrive.jdbi;

import by.youdrive.domain.ID;
import by.youdrive.domain.JSON;
import by.youdrive.domain.UserEntity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RegisterMapper(UserDAO.Mapper.class)
public interface UserDAO {

    @SqlQuery("select * from user")
    List<UserEntity> getAll();

    @SqlQuery("select * from user where id = :id")
    UserEntity findById(@Bind("id") ID id);

    @SqlQuery("select * from user where contacts like '%:email%'")
    UserEntity findByEmail(@Bind("email") String email);

    @SqlUpdate("insert into user (id, first_name, last_name, contacts, admin, locale, secret, custom_properties) values " +
            "(:id, :firstName, :lastName, :contacts, :admin, :locale, :secret, :customProperties)")
    void createUser(@BindBean UserEntity userEntity);

    @SqlUpdate("update user set first_name = :firstName, last_name = :lastName, contacts = :contacts, admin = :admin, " +
            "locale = :locale, custom_properties = :customProperties where id = :id")
    void updateUser(@BindBean UserEntity userEntity);

    @SqlUpdate("update user set secret = :secret where id = :id")
    void updateUser(@Bind("id") ID id, @Bind("secret") String secret);

    @SqlUpdate("delete from user where id = :id")
    void deleteById(@Bind("id") ID id);


    class Mapper implements ResultSetMapper<UserEntity> {

        @Override
        public UserEntity map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            JSON customProperties = r.getString("custom_properties") == null ? null :
                    JSON.fromString(r.getString("custom_properties"), Objects.class);

            JSON<UserEntity.Contacts> emails = JSON.fromString(r.getString("contacts"), UserEntity.Contacts.class);

            return new UserEntity(
                    ID.from(r.getString("id")),
                    r.getString("first_name"),
                    r.getString("last_name"),
                    emails == null ? null : emails.getObject(),
                    r.getBoolean("admin"),
                    r.getString("locale") == null ? null : Locale.forLanguageTag(r.getString("locale")),
                    r.getString("secret"),
                    customProperties
            );
        }
    }
}
