package by.youdrive;

import by.youdrive.client.ClientModule;
import by.youdrive.domain.UserEntity;
import by.youdrive.jdbi.JDBIModule;
import by.youdrive.security.YouDriveAuthenticator;
import by.youdrive.security.YouDriveAuthorizer;
import by.youdrive.security.YouDriverAuthFilter;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class YouDriveApiApplication extends Application<YouDriveApiConfiguration> {

    private GuiceBundle<YouDriveApiConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        new YouDriveApiApplication().run(args);
    }

    @Override
    public void run(YouDriveApiConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new AuthDynamicFeature(
                new YouDriverAuthFilter.Builder<UserEntity>(configuration)
                .setAuthenticator(guiceBundle.getInjector().getInstance(YouDriveAuthenticator.class))
                .setAuthorizer(new YouDriveAuthorizer())
                .setPrefix("Bearer")
                .buildAuthFilter()));

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(UserEntity.class));
    }

    @Override
    public String getName() {
        return "youdrive-api";
    }

    @Override
    public void initialize(Bootstrap<YouDriveApiConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<YouDriveApiConfiguration>newBuilder()
                .addModule(new JDBIModule())
                .addModule(new ClientModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(YouDriveApiConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new MigrationsBundle<YouDriveApiConfiguration>() {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(YouDriveApiConfiguration configuration) {
                return configuration.getDatabase();
            }
        });
    }
}
