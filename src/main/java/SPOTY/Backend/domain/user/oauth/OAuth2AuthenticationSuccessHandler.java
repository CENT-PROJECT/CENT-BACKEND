package SPOTY.Backend.domain.user.oauth;

import SPOTY.Backend.domain.user.domain.User;
import SPOTY.Backend.domain.user.dto.UserResponseDto;
import SPOTY.Backend.domain.user.repository.UserRepository;
import SPOTY.Backend.global.jwt.CreateTokenDto;
import SPOTY.Backend.global.jwt.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;

    private final ObjectMapper objectMapper;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("oAuth2User = " + oAuth2User);

        System.out.println(request.getUserPrincipal().getName());

        Optional<User> optionalUser = userRepository.findByEmailAndProviderType(null,null);

        User user = optionalUser.get();

        CreateTokenDto createTokenDto = new CreateTokenDto(
                user.getId(), user.getEmail(), user.getRole(), user.getProviderType());
        String accessToken = tokenService.createAccessToken(createTokenDto);
        String refreshToken = tokenService.createAccessToken(createTokenDto);

        UserResponseDto.LoginResponseDto token = new UserResponseDto.LoginResponseDto(accessToken, refreshToken);

        writeTokenResponse(response, token);

    }

    private void writeTokenResponse(HttpServletResponse response, UserResponseDto.LoginResponseDto responseDto) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("accessToken", responseDto.getAccessToken());
        response.addHeader("refreshToken", responseDto.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        // 반환 위치 어디로,,?
        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(responseDto));
        writer.flush();
    }

}
