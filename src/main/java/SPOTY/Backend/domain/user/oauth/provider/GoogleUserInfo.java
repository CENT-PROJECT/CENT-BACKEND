package SPOTY.Backend.domain.user.oauth.provider;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;
    private String accessToken;

    @Value("${custom.spoty-server.google.api-key}")
    private String googleApiKey;


    public GoogleUserInfo(Map<String, Object> attributes, String accessToken) {
        this.attributes = attributes;
        this.accessToken = accessToken;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getBirthDate() {

        // google people api 붙이기
        // https://rkdxowhd98.tistory.com/170
        return null;
    }
}
