package by.youdrive.jdbi;

import by.youdrive.domain.JSON;
import by.youdrive.domain.UserEntity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactsArgument implements Argument {
    private final UserEntity.Contacts contacts;

    public ContactsArgument(UserEntity.Contacts contacts) {
        this.contacts = contacts;
    }

    @Override
    public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
        statement.setString(position, contacts == null ? null : JSON.fromObject(contacts).toString());
    }
}
