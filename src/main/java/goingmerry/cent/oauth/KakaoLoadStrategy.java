package goingmerry.cent.oauth;

import com.google.gson.JsonObject;
import goingmerry.cent.domain.SocialType;
import goingmerry.cent.oauth.SocialLoadStrategy;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

@Slf4j
public class KakaoLoadStrategy extends SocialLoadStrategy {

    protected OAuth2UserDetails sendRequestToSocialSite(HttpEntity request){
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(SocialType.KAKAO.getUserInfoUrl(),// -> /v2/user/me
                    SocialType.KAKAO.getMethod(),
                    request,
                    RESPONSE_TYPE);
            Long userId = (Long) response.getBody().get("id");
            Map<String,Object> properties = (Map<String, Object>) response.getBody().get("properties");
            Map<String,Object> account = (Map<String, Object>) response.getBody().get("kakao_account");

            return OAuth2UserDetails.builder()
                    .userId(userId)
                    .username((String) properties.get("nickname"))
                    .socialType(SocialType.KAKAO)
                    .userImage((String)properties.get("profile_image"))
                    .ageRange((String)account.get("age_range"))
                    .gender((String)account.get("gender"))
                    .birth((String)account.get("birthday"))
                    .build();



        } catch (Exception e) {
            log.error("AccessToken을 사용하여 KAKAO 유저정보를 받아오던 중 예외가 발생했습니다 {}" ,e.getMessage() );
            throw e;
        }
    }
}
