package by.youdrive.client;

import by.youdrive.YouDriveApiConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

public class ClientModule extends AbstractModule {

    private Client client;

    @Provides
    public Client prepareClient(Environment environment, YouDriveApiConfiguration configuration) throws ClassNotFoundException {
        if (client == null) {
            client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                    .build(environment.getName());
        }
        return client;
    }

    @Override
    protected void configure() {}
}
