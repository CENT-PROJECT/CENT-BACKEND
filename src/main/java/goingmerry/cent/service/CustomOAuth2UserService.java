package goingmerry.cent.service;

import goingmerry.cent.CustomUserDetails;
import goingmerry.cent.GoogleUserInfo;
import goingmerry.cent.NaverUserInfo;
import goingmerry.cent.OAuth2UserInfo;
import goingmerry.cent.domain.Role;
import goingmerry.cent.domain.User;
import goingmerry.cent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    //Request 에 대한 데이터 후처리 되는 함수
   public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();    //google
        System.out.println("provider : " + provider);
        if(provider.equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(provider.equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }

        //ID DB에 있는지 확인하는 로직 추가 (OAuth2 로만 회원가입이 되게 하고,
        // 바로 강제 회원가입이 되게 하려고 CustomUserDetails 를 썼지만 아니라면 안써도 된다.

        User user = User.builder()
                .userName(oAuth2UserInfo.getName())
                .socialType(oAuth2UserInfo.getProvider())
                .socialId(oAuth2UserInfo.getProviderId())
                .email(oAuth2UserInfo.getEmail())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return new CustomUserDetails(user,oAuth2User.getAttributes());
    }
}