package goingmerry.cent.repository;

import goingmerry.cent.domain.SocialType;
import goingmerry.cent.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findBySocialTypeAndSocialId(SocialType socialType , String socialId);
}
