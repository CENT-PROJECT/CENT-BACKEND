package SPOTY.Backend.domain.user.controller;

import SPOTY.Backend.domain.mail.MailDto;
import SPOTY.Backend.domain.mail.MailService;
import SPOTY.Backend.domain.user.domain.User;
import SPOTY.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/join")
@RestController
public class JoinController {

    private final UserService userService;

    private final MailService mailService;

    @PostMapping
    public String join(@RequestBody UserDto.UserSaveDto dto) throws Exception {
        signService.join(dto);
        return "이메일 인증을 하신 뒤 로그인 해주세요!!!";
    }

    @PostMapping("/email")
    public String sendEmailCode(@RequestBody MailDto.MailRequestDto dto) throws Exception {
        mailService.send();
    }

}
