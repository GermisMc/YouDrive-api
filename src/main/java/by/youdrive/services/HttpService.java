package by.youdrive.services;

import by.youdrive.YouDriveApiConfiguration;
import by.youdrive.YouDriveApiConfiguration.OAuth2Config;
import by.youdrive.commons.OA2ClientReq;
import by.youdrive.commons.OA2ClientResp;
import by.youdrive.commons.OA2TokenResp;
import by.youdrive.commons.TokenInfo;
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

        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
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

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new YouDriveException(YouDriveException.CannotGetOAuthToken, Messages.CannotGetOAuthToken);
        }

        return response.readEntity(OA2TokenResp.class);
    }

    public TokenInfo verifyToken(String token) {
        OAuth2Config config = configuration.getoAuth2Config();

        Response response = client.target(config.getoAuth2ServerUrl())
                .path(config.getoAuth2TokenInfo())
                .queryParam("access_token", token)
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Basic " + config.getBase64())
                .get();

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            TokenInfo failedTokenInfo = new TokenInfo();
            failedTokenInfo.setStatusCode(response.getStatus());
            return failedTokenInfo;
        }

        return response.readEntity(TokenInfo.class);
    }

    public OA2TokenResp refreshToken(String token) {
        OAuth2Config config = configuration.getoAuth2Config();

        Form form = new Form();
        form.param("grant_type", "refresh_token");
        form.param("refresh_token", token);

        Response response = client.target(config.getoAuth2ServerUrl())
                .path(config.getoAuth2TokenPath())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            throw new YouDriveException(YouDriveException.CannotGetOAuthToken, Messages.CannotGetOAuthToken);
        }
        else if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new YouDriveException(0, Messages.CannotGetOAuthToken);
        }

        return response.readEntity(OA2TokenResp.class);
    }
}
