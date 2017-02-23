package by.youdrive.jdbi;

import by.youdrive.domain.JSON;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JSONArgument implements Argument {

    private final JSON value;

    public JSONArgument(JSON value) {
        this.value = value;
    }

    @Override
    public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
        statement.setString(position, value.toString());
    }
}
