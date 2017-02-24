package by.youdrive;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class YouDriveApiConfiguration extends Configuration {

    private String appName;
    private String appUrl;

    public YouDriveApiConfiguration() {
    }

    public String getAppName() {
        return appName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    @Valid
    @NotNull
    @JsonPropertyDescription("database")
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDatabase() {
        return database;
    }

    @Valid
    @NotNull
    private JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClientConfiguration;
    }

    @Valid
    @NotNull
    @JsonProperty("oAuth2")
    private OAuth2Config oAuth2Config = new OAuth2Config();

    public OAuth2Config getoAuth2Config() {
        return oAuth2Config;
    }


    public static class OAuth2Config {
        private boolean useRefreshTokens;
        @Min(1)
        private int expireDuration;
        private String oAuth2ServerUrl;
        private String oAuth2RegisterClientPath;
        private String oAuth2TokenPath;
        private String oAuth2TokenInfo;
        private String bearerToken;
        private String authToken;
        private String refreshToken;

        public String getBearerToken() {
            return bearerToken;
        }

        public String getoAuth2ServerUrl() {
            return oAuth2ServerUrl;
        }

        public String getoAuth2RegisterClientPath() {
            return oAuth2RegisterClientPath;
        }

        public String getoAuth2TokenPath() {
            return oAuth2TokenPath;
        }

        public String getoAuth2TokenInfo() {
            return oAuth2TokenInfo;
        }

        public boolean isUseRefreshTokens() {

            return useRefreshTokens;
        }

        public int getExpireDuration() {
            return expireDuration;
        }

        public String getAuthToken() {
            return authToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
