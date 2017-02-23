package by.youdrive.commons;

import by.youdrive.commons.support.Representation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OA2ClientResp extends Representation {

    private String secret;
    private String[] scopes;

    public String getSecret() {
        return secret;
    }

    public String[] getScopes() {
        return scopes;
    }
}
