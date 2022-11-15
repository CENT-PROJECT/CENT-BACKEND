package SPOTY.Backend.domain.user.oauth;

import SPOTY.Backend.domain.user.domain.User;
import SPOTY.Backend.domain.user.repository.UserRepository;
import SPOTY.Backend.global.jwt.CreateTokenDto;
import SPOTY.Backend.global.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    @Value("${custom.spoty-server.front.url}")
    private String spotyServerFrontUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());

        Optional<User> optionalUser = userRepository.findByEmail(oAuth2User.getName());

        User user = optionalUser.get();

        CreateTokenDto createTokenDto = new CreateTokenDto(
                user.getId(), user.getEmail(), user.getRole(), user.getProviderType());
        String accessToken = tokenService.createAccessToken(createTokenDto);
        String refreshToken = tokenService.createAccessToken(createTokenDto);

        log.info("access Token: {}", accessToken);
        log.info("refresh Token: {}", refreshToken);

        String url = spotyServerFrontUrl + "/api/token?accessToken={value}&refreshToken={value}";

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(accessToken, refreshToken);
        System.out.println("uri = " + uri);

        // 이렇게 반환할지
        getRedirectStrategy().sendRedirect(request, response, String.valueOf(uri));

    }

}
