package by.youdrive.jdbi;

import by.youdrive.domain.ID;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IDArgument implements Argument {

    private final ID value;

    IDArgument(ID value) {
        this.value = value;
    }

    @Override
    public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
        statement.setString(position, value.toString());
    }
}
