package SPOTY.Backend.domain.user.controller;

import SPOTY.Backend.domain.user.domain.User;
import SPOTY.Backend.domain.user.dto.UserRequestDto;
import SPOTY.Backend.domain.user.dto.UserResponseDto;
import SPOTY.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto.LoginResponseDto> login(
            @RequestBody UserRequestDto.LoginRequestDto dto) {
        userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

    }

    @GetMapping("/api/home")
    public String home() {
        return "home";
    }
}
