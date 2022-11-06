package SPOTY.Backend.domain.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MailTest {

    @Autowired
    MailRedisRepository mailRedisRepository;

    @Test
    @DisplayName("mail 필드들 확인")
    void checkMailEntityField() {
        //Hash 타입으로 저장.
        //when
        Mail mail = mailRedisRepository.save(new Mail("dlektl6@naver.com", "123456"));

        //then
        Optional<Mail> mailFindBy = mailRedisRepository.findById(mail.getId());

        assertThat(mailFindBy.isPresent()).isEqualTo(Boolean.TRUE);
        assertThat(mailFindBy.get().getCode()).isEqualTo("123456");
        assertThat(mailFindBy.get().getExpiration()).isEqualTo(300);
    }
}