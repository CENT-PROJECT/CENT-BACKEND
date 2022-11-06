package SPOTY.Backend.domain.user.oauth;

import SPOTY.Backend.domain.user.domain.Role;
import SPOTY.Backend.domain.user.domain.User;
import SPOTY.Backend.domain.user.oauth.provider.GoogleUserInfo;
import SPOTY.Backend.domain.user.oauth.provider.KakaoUserInfo;
import SPOTY.Backend.domain.user.oauth.provider.NaverUserInfo;
import SPOTY.Backend.domain.user.oauth.provider.OAuth2UserInfo;
import SPOTY.Backend.domain.user.repository.UserRepository;
import SPOTY.Backend.domain.user.service.UserService;
import SPOTY.Backend.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    private final UserRepository userRepository;

    // 구글 로그인 뒤 후처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = null;

        if(provider.equals("google")) {

            oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());

        } else if (provider.equals("naver")) {

            oAuth2UserInfo = new NaverUserInfo(oauth2User.getAttribute("response"));
            
        } else if (provider.equals("kakao")) {

            oAuth2UserInfo = new KakaoUserInfo(oauth2User.getAttributes());

        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String userName = oAuth2UserInfo.getName();
        String password = null;
        Role role = Role.ROLE_UNFINISHED_USER;

        UUID id = UUID.randomUUID();

        Optional<User> user = userRepository.findByEmailAndProviderType(oAuth2UserInfo.getEmail(), provider);
        // 신규 회원가입
        if(user.isEmpty()) {

            User entity = User.builder()
                    .id(id)
                    .username(userName)
                    .email(email)
                    .role(role)
                    .providerType(provider)
                    .providerId(providerId)
                    .password(password)
                    .build();

            userRepository.save(entity);

        }

        // authentication 객체에 넣어준다.
        return new CustomUserDetails(id, email, role.toString(), oauth2User.getAttributes());
    }
}
