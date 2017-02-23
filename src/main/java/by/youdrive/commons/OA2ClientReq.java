package by.youdrive.commons;

import by.youdrive.commons.support.Representation;

public class OA2ClientReq extends Representation {

    private String name;
    private String contactName;
    private String contactEmail;
    private boolean allowedClientCredentials;
    private boolean useRefreshTokens;
    private int expireDuration;
    private String[] scopes;

    public OA2ClientReq(String name, String contactName, String contactEmail, boolean allowedClientCredentials,
                        boolean useRefreshTokens, int expireDuration, String[] scopes) {
        this.name = name;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.allowedClientCredentials = allowedClientCredentials;
        this.useRefreshTokens = useRefreshTokens;
        this.expireDuration = expireDuration;
        this.scopes = scopes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setAllowedClientCredentials(boolean allowedClientCredentials) {
        this.allowedClientCredentials = allowedClientCredentials;
    }

    public void setUseRefreshTokens(boolean useRefreshTokens) {
        this.useRefreshTokens = useRefreshTokens;
    }

    public void setExpireDuration(int expireDuration) {
        this.expireDuration = expireDuration;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    public String getName() {

        return name;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public boolean isAllowedClientCredentials() {
        return allowedClientCredentials;
    }

    public boolean isUseRefreshTokens() {
        return useRefreshTokens;
    }

    public int getExpireDuration() {
        return expireDuration;
    }

    public String[] getScopes() {
        return scopes;
    }
}
