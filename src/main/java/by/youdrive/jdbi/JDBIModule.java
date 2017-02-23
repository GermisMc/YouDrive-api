package by.youdrive.jdbi;

import by.youdrive.YouDriveApiConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class JDBIModule extends AbstractModule {

    private DBI jdbi;

    @Provides
    public DBI prepareJDBI(Environment environment, YouDriveApiConfiguration configuration) throws ClassNotFoundException {
         /*
             setup DB access including DAOs
             implementing a singleton pattern here but avoiding
             Guice to initialize DB connection too early
         */
        if (jdbi == null) {
            final DBIFactory factory = new DBIFactory();
            jdbi = factory.build(environment, configuration.getDatabase(), "youdrive");
            jdbi.registerArgumentFactory(new IDArgumentFactory());
            jdbi.registerArgumentFactory(new JSONArgumentFactory());
            jdbi.registerArgumentFactory(new LocaleArgumentFactory());
            jdbi.registerArgumentFactory(new ContactsArgumentFactory());
        }
        return jdbi;
    }

    @Provides
    public YouDriveDAO prepareYouDriveDAO(DBI jdbi) {
        YouDriveDAO youDriveDAO = jdbi.onDemand(YouDriveDAO.class);
        youDriveDAO.setDbi(jdbi);
        return youDriveDAO;
    }

    @Override
    protected void configure() {
    }
}
