package by.youdrive.commons;

import by.youdrive.commons.support.Representation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class TokenInfo extends Representation {
    @NotEmpty
    private String audience;
    private String[] scopes;
    private Object principal;
    @JsonProperty("expires_in")
    private long expiresIn;
    @JsonIgnore
    private int statusCode;

    public String getAudience() {
        return audience;
    }

    public String[] getScopes() {
        return scopes;
    }

    public Object getPrincipal() {
        return principal;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
