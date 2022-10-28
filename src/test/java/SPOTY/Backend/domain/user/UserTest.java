package SPOTY.Backend.domain.user;

import SPOTY.Backend.domain.user.User;
import SPOTY.Backend.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User 도메인 속성에 대한
 */
@SpringBootTest
@Transactional
class UserTest {

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

    @Test
    @DisplayName("UUID 적용 확인 테스트")
    public void checkUUIDTest() {
        //c24de17c-0088-4686-8980-9e1f88601473
        List<User> users = userRepository.findAll();
    }
}