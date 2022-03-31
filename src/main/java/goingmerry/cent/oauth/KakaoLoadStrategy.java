package goingmerry.cent.oauth;

import goingmerry.cent.domain.SocialType;
import goingmerry.cent.oauth.SocialLoadStrategy;
import lombok.extern.slf4j.Slf4j;
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

            return OAuth2UserDetails.builder().build();//카카오는 id를 PK로 사용

        } catch (Exception e) {
            log.error("AccessToken을 사용하여 KAKAO 유저정보를 받아오던 중 예외가 발생했습니다 {}" ,e.getMessage() );
            throw e;
        }
    }
}
