package by.youdrive.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

public class LocaleArgument implements Argument {

    private final Locale value;

    public LocaleArgument(Locale value) {
        this.value = value;
    }

    @Override
    public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
        statement.setString(position, value == null ? null : value.toLanguageTag());
    }
}
