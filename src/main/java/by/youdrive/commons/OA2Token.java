package by.youdrive.commons;

import by.youdrive.commons.support.Representation;

public class OA2Token extends Representation {
    private String accessToken;
    private String refreshToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {

        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
