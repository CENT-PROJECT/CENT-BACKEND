package goingmerry.cent.controller;

import goingmerry.cent.exception.AccountException;
import goingmerry.cent.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OAuthController {


    @GetMapping("/")
    public String getAccessCode(){
        return "index";
    }

//    @GetMapping("/login/oauth2/kakao")
//    public String getAccess(@RequestHeader String Authorization){
//        return "success";
//    }


    //Authentication 의존성 주입
    public @ResponseBody String testOAuthLogin(Authentication authentication ,@RequestHeader String Authorization) {


        return "OAuth 세션 정보 확인하기";

    }
}
