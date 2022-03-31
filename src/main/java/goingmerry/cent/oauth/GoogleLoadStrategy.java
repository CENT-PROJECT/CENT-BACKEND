package goingmerry.cent.oauth;

import goingmerry.cent.domain.SocialType;
import goingmerry.cent.oauth.SocialLoadStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Slf4j
public class GoogleLoadStrategy extends SocialLoadStrategy {

    protected OAuth2UserDetails sendRequestToSocialSite(HttpEntity request){
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(SocialType.GOOGLE.getUserInfoUrl(),
                    SocialType.GOOGLE.getMethod(),
                    request,
                    RESPONSE_TYPE);

            return OAuth2UserDetails.builder().build();//구글은 email를 PK로 사용

        } catch (Exception e) {
            log.error("AccessToken을 사용하여 GOOGLE 유저정보를 받아오던 중 예외가 발생했습니다 {}" ,e.getMessage() );
            throw e;
        }
    }
}
