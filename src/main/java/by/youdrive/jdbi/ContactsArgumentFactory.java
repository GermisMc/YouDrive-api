package by.youdrive.jdbi;

import by.youdrive.domain.UserEntity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

public class ContactsArgumentFactory implements ArgumentFactory<UserEntity.Contacts> {
    @Override
    public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
        return value instanceof UserEntity.Contacts;
    }

    @Override
    public Argument build(Class<?> expectedType, UserEntity.Contacts value, StatementContext ctx) {
        return new ContactsArgument(value);
    }
}
