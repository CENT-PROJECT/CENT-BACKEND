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
        UserResponseDto.LoginResponseDto response = userService.login(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}