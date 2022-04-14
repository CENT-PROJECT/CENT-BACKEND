package goingmerry.cent.controller;

import goingmerry.cent.domain.User;
import goingmerry.cent.dto.LoginResponseDto;
import goingmerry.cent.exception.AccountException;
import goingmerry.cent.exception.ErrorType;
import goingmerry.cent.jwt.JwtTokenResolver;
import goingmerry.cent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class OAuthController {

    private final JwtTokenResolver jwtTokenResolver;
    private final UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<LoginResponseDto> index(@RequestHeader String Authorization,@RequestHeader String accessToken){
        System.out.println("111");
        System.out.println(Authorization);
        System.out.println(accessToken);
        Long id = jwtTokenResolver.getId(Authorization);
        System.out.println("222");
        Optional<User> user = userRepository.findById(id);
        LoginResponseDto dto = new LoginResponseDto(Authorization);
        if(user.get().getActivityArea().isEmpty()){
            dto.setJoined(false);
            return new ResponseEntity<LoginResponseDto>(dto, HttpStatus.OK);
        }else{
            return new ResponseEntity<LoginResponseDto>(dto, HttpStatus.OK);
        }
    }

}
