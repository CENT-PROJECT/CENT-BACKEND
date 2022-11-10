package SPOTY.Backend.global.security;

import SPOTY.Backend.domain.user.domain.Role;
import SPOTY.Backend.domain.user.domain.User;
import SPOTY.Backend.domain.user.oauth.provider.GoogleUserInfo;
import SPOTY.Backend.domain.user.oauth.provider.KakaoUserInfo;
import SPOTY.Backend.domain.user.oauth.provider.NaverUserInfo;
import SPOTY.Backend.domain.user.oauth.provider.OAuth2UserInfo;
import SPOTY.Backend.domain.user.repository.UserRepository;
import SPOTY.Backend.global.exception.domain.user.NotFoundUser;
import SPOTY.Backend.global.util.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;


import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;


@Transactional
@RequiredArgsConstructor
public class CustomUserDetailService extends DefaultOAuth2UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final OptionalUtil optionalUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        optionalUtil.ifEmptyThrowError(optionalUser, new NotFoundUser());

        User user = optionalUser.get();
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getRole().toString());
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = null;

        System.out.println("#### userRequest.getAdditionalParameters() : " + userRequest.getAdditionalParameters());

        System.out.println("## provider ## : " + provider);
        System.out.println(oauth2User.getAttributes());
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

        } else {

            role = user.get().getRole();

        }

        // authentication 객체에 넣어준다.
        return new CustomUserDetails(id, email, role.toString(), oauth2User.getAttributes());
    }
}
