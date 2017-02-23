package by.youdrive.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

import java.util.Locale;

public class LocaleArgumentFactory implements ArgumentFactory<Locale> {
    @Override
    public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
        return value instanceof Locale;
    }

    @Override
    public Argument build(Class<?> expectedType, Locale value, StatementContext ctx) {
        return new LocaleArgument(value);
    }
}
