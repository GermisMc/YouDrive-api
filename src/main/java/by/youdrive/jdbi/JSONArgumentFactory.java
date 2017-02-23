package by.youdrive.jdbi;

import by.youdrive.domain.JSON;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

public class JSONArgumentFactory implements ArgumentFactory<JSON> {
    @Override
    public boolean accepts(Class expectedType, Object value, StatementContext ctx) {
        return value instanceof JSON;
    }

    @Override
    public Argument build(Class expectedType, JSON value, StatementContext ctx) {
        return new JSONArgument(value);
    }
}
