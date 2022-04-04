package goingmerry.cent.controller;

import goingmerry.cent.exception.AccountException;
import goingmerry.cent.exception.ErrorType;
import goingmerry.cent.jwt.JwtTokenProvider;
import goingmerry.cent.jwt.JwtTokenResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class OAuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenResolver jwtTokenResolver;

    @GetMapping("/")
    public String getAccessCode(){
        return "index";
    }

    @GetMapping("/test/oauth/login")


    //Authentication 의존성 주입
    public @ResponseBody String testOAuthLogin(Authentication authentication ,@RequestHeader String accessToken){

        if(jwtTokenProvider.validateAccessToken(accessToken)){
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            System.out.println(oAuth2User.getAttributes());
            return "OAuth 세션 정보 확인하기";
        }else throw new AccountException(ErrorType.JSON_WEB_TOKEN_IS_NOT_VALIDATED);
    }
}
