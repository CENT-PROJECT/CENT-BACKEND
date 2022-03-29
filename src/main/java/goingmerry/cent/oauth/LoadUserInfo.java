package goingmerry.cent.oauth;

import goingmerry.cent.domain.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoadUserInfo {

    private final RestTemplate restTemplate = new RestTemplate();

    public OAuth2UserDetails getOAuth2UserDetails(AccessTokenSocialTypeToken authentication)  {

        SocialType socialType = authentication.getSocialType();

        SocialLoadStrategy socialLoadStrategy = getSocialLoadStrategy(socialType);//SocialLoadStrategy 설정

        String socialPk = socialLoadStrategy.getSocialPk(authentication.getAccessToken());//PK 가져오기

        return OAuth2UserDetails.builder() //PK와 SocialType을 통해 회원 생성
                .socialId(socialPk)
                .socialType(socialType)
                .build();
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
