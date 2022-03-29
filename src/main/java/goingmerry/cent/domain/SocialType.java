package goingmerry.cent.domain;

import org.springframework.http.HttpMethod;

public enum SocialType {
    KAKAO(
            "kakao",
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET
    ),

    GOOGLE(
            "google",
            "https://www.googleapis.com/oauth2/v3/userinfo",    //url person API 로 변경하면 거기로 뭐 바꿔질수 있다.
            HttpMethod.GET
    );


    private String socialName;
    private String userInfoUrl;
    private HttpMethod method;

    SocialType(String socialName, String userInfoUrl, HttpMethod method) {
        this.socialName = socialName;
        this.userInfoUrl = userInfoUrl;
        this.method = method;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getSocialName() {
        return socialName;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }
}
