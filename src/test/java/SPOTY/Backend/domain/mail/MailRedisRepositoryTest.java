package SPOTY.Backend.domain.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MailRedisRepositoryTest {

    @Autowired
    MailRedisRepository mailRedisRepository;

    @Test
    @DisplayName("findMailByIdAndCode 테스트")
    void findMailByIdAndCode() {

        //when
        Mail mail = mailRedisRepository.save(new Mail("dlektl6@naver.com", "123456"));

        //then
        Optional<Mail> mailFindBy = mailRedisRepository.findMailByIdAndCode(mail.getId(), mail.getCode());

        assertThat(mailFindBy.isPresent()).isEqualTo(Boolean.TRUE);
        assertThat(mailFindBy.get().getCode()).isEqualTo("123456");
        assertThat(mailFindBy.get().getExpiration()).isEqualTo(300);
    }

}