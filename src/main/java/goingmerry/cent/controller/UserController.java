package goingmerry.cent.controller;

import goingmerry.cent.domain.EmailAuth;
import goingmerry.cent.domain.User;
import goingmerry.cent.dto.EmailAuthRequestDto;
import goingmerry.cent.dto.LoginResponseDto;
import goingmerry.cent.dto.UserSaveDto;
import goingmerry.cent.jwt.JwtTokenProvider;
import goingmerry.cent.repository.EmailAuthRepository;
import goingmerry.cent.repository.UserRepository;
import goingmerry.cent.service.ResponseService;
import goingmerry.cent.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    private final EmailAuthRepository emailAuthRepository;
    private final SignService signService;


    // 회원가입
    @PostMapping("/join")
    public String join(@RequestBody Map<String, String> user) {
        signService.registerMember(new UserSaveDto(user.get("email"), user.get("password")));
        return "success";
    }

    @PostMapping("/emailVerify")
    public String emailVerify(@RequestBody Map<String, String> user) {
        EmailAuth email = emailAuthRepository.findByEmail(user.get("email"));
        signService.confirmEmail(new EmailAuthRequestDto(email.getEmail(), email.getAuthToken()));
        return "success";
    }

    // 로그인
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        LoginResponseDto responseData = new LoginResponseDto(jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
        return responseData;
    }

    @GetMapping
    public String home() {
        return "home";
    }
}
