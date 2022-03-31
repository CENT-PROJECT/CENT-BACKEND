package goingmerry.cent.oauth;

import goingmerry.cent.domain.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoadUserInfo {

    private final RestTemplate restTemplate = new RestTemplate();

    public OAuth2UserDetails getOAuth2UserDetails(AccessTokenSocialTypeToken authentication)  {

        SocialType socialType = authentication.getSocialType();

        SocialLoadStrategy socialLoadStrategy = getSocialLoadStrategy(socialType);//SocialLoadStrategy 설정

        OAuth2UserDetails oAuth2UserDetails = socialLoadStrategy.getOAuth2UserDetails(authentication.getAccessToken());//PK 가져오기

        //값들 더 불러오기 가능 (우리가 원하는 유저 정보들)

        return oAuth2UserDetails;
    }

    private SocialLoadStrategy getSocialLoadStrategy(SocialType socialType) {
         switch (socialType){
             case KAKAO :
                 return new KakaoLoadStrategy();
             case GOOGLE :
                 return new GoogleLoadStrategy();
             default :
                 throw new IllegalArgumentException("지원하지 않는 로그인 형식입니다");
        }
    }

}
