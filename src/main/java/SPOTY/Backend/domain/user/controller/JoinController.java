package SPOTY.Backend.domain.user.controller;

import SPOTY.Backend.domain.mail.MailService;
import SPOTY.Backend.domain.mail.dto.MailRequestDto;
import SPOTY.Backend.domain.user.dto.UserRequestDto;
import SPOTY.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/join")
@RestController
public class JoinController {

    private final UserService userService;

    private final MailService mailService;

    @PostMapping
    public ResponseEntity<String> join(@RequestBody UserRequestDto.JoinRequestDto dto) {
        userService.join(dto);
        return ResponseEntity.ok("회원가입 완료!");
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmailCode(@RequestBody MailRequestDto.SendMailDto dto) {
        mailService.send(dto);
        return ResponseEntity.ok("이메일을 확인해주세요!");
    }

}
