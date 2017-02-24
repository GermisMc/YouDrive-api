package by.youdrive.services;

import by.youdrive.YouDriveApiConfiguration;
import by.youdrive.YouDriveApiConfiguration.OAuth2Config;
import by.youdrive.commons.OA2ClientReq;
import by.youdrive.commons.OA2ClientResp;
import by.youdrive.commons.OA2TokenResp;
import by.youdrive.domain.ID;
import by.youdrive.exceptions.Messages;
import by.youdrive.exceptions.YouDriveException;
import com.google.inject.Inject;
import org.glassfish.jersey.internal.util.Base64;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class HttpService {

    @Inject
    private Client client;

    @Inject
    private YouDriveApiConfiguration configuration;

    public OA2ClientResp registerClientInOAuth2(ID id, String name, String email, String[] scopes) throws Exception {
        OAuth2Config config = configuration.getoAuth2Config();

        OA2ClientReq oauthClient = new OA2ClientReq(id.toString(), name, email, true,
                config.isUseRefreshTokens(), config.getExpireDuration(), scopes);

        Response response = client.target(config.getoAuth2ServerUrl())
                .path(config.getoAuth2RegisterClientPath())
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + config.getBearerToken())
                .put(Entity.entity(oauthClient, MediaType.APPLICATION_JSON));

        if (response.getStatusInfo().getStatusCode() != Response.Status.CREATED.getStatusCode()) {
            throw new YouDriveException(YouDriveException.CannotCreateOAuthClient, Messages.CannotCreateOAuthClient);
        }

        return response.readEntity(OA2ClientResp.class);
    }

    public OA2TokenResp getToken(ID userId, String clientSecret) throws Exception {
        OAuth2Config config = configuration.getoAuth2Config();

        Form form = new Form();
        form.param("grant_type", "client_credentials");

        Response response = client.target(config.getoAuth2ServerUrl())
                .path(config.getoAuth2TokenPath())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeAsString(userId + ":" + clientSecret))
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        if (response.getStatusInfo().getStatusCode() != Response.Status.OK.getStatusCode()) {
            throw new YouDriveException(YouDriveException.CannotGetOAuthToken, Messages.CannotGetOAuthToken);
        }

        return response.readEntity(OA2TokenResp.class);
    }
}
