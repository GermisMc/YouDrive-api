package by.youdrive.jdbi;

import by.youdrive.domain.ID;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

public class IDArgumentFactory implements ArgumentFactory<ID> {
    @Override
    public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
        return value instanceof ID;
    }

    @Override
    public Argument build(Class<?> expectedType, ID value, StatementContext ctx) {
        return new IDArgument(value);
    }
}
