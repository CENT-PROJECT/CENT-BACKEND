package SPOTY.Backend.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init() {
        User user = new User();
        userRepository.save(user);
    }

    @Test
    @DisplayName("BaseTimeEntity 적용 확인 테스트")
    public void checkBaseTimeEntity() {
        List<User> users = userRepository.findAll();
        assertThat(users.get(0).getCreatedDate()).isNotNull();
        assertThat(users.get(0).getModifiedDate()).isNotNull();
    }
}