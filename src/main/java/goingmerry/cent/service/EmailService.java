package goingmerry.cent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@EnableAsync
@RequiredArgsConstructor
@Service
@Transactional
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Async
    //메일을 보내는데 우리가 전송하는 동안 기다리게 되어 그동안 블럭 상태에 놓이게 된다.(이부분 방지)
    public void send(String email, String authToken) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo("whdwldnjs123@gmail.com");
//        +"@gmail.com"
        smm.setSubject("회원가입 이메일 인증");
        smm.setText("http://localhost:8080/sign/confirm-email?email="+email+"&authToken="+authToken);

        javaMailSender.send(smm);
    }
}
