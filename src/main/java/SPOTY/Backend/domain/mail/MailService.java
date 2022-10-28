package SPOTY.Backend.domain.mail;

import SPOTY.Backend.global.exception.domain.mail.BadRequestMail;
import SPOTY.Backend.global.util.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;

@EnableAsync
@RequiredArgsConstructor
@Service
@Transactional
public class MailService {
    private final JavaMailSender mailSender;

    private final MailRedisRepository mailRedisRepository;

    private final OptionalUtil<Mail> optionalUtil;

    public String getRandom() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * 메일을 보내는데 우리가 전송하는 동안 기다리게 되어 그동안 블럭 상태에 놓이게 된다.
     * Async 를 통해 비동기 처리로 인한 성능개선.
     * @param mail
     * @param check
     */
    @Async
    public void send(String mail, String check) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("인증 코드 : " + check);

        mailSender.send(mailMessage);

        mailRedisRepository.save(new Mail(mail, check));
    }

    public boolean checkVerifiedEmail(String mail, String check) {
        Optional<Mail> optional = mailRedisRepository.findMailByIdAndCode(mail, check);
        optionalUtil.ifEmptyThrowError(optional, new BadRequestMail());

        return Boolean.TRUE;
    }

}
