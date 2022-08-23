package goingmerry.cent.user;

import goingmerry.cent.SingleResponseData;
import goingmerry.cent.email.EmailAuthRequestDto;
import goingmerry.cent.jwt.JwtTokenProvider;
import goingmerry.cent.email.EmailAuthRepository;
import goingmerry.cent.email.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    private final EmailAuthRepository emailAuthRepository;
    private final SignService signService;


    // 회원가입 - 임시 권한
    @PostMapping("api/join")
    public String join(@RequestBody UserDto.UserSaveDto dto) throws Exception {
        signService.join(dto);
        return "이메일 인증을 하신 뒤 로그인 해주세요!!!";
    }

    @GetMapping("api/sign/verify-email")
    public String emailVerify(@RequestParam String email, @RequestParam String authToken) throws Exception {
        signService.confirmEmail(new EmailAuthRequestDto(email,authToken));
        return "이메일 인증 성공";
    }

    // 로그인
    @PostMapping("api/login")
    public ResponseEntity<SingleResponseData> login(@RequestBody UserDto.LoginRequestDto dto) {
        User member = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        SingleResponseData data = new SingleResponseData(jwtTokenProvider.createToken(member.getEmail(), member.getRole()));
        return new ResponseEntity<SingleResponseData>(data, HttpStatus.OK);
    }

    @GetMapping("api/home")
    public String home() {
        return "home";
    }
}
