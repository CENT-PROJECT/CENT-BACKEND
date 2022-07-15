package goingmerry.cent.repository;

import goingmerry.cent.domain.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface EmailAuthRepository extends JpaRepository<EmailAuth,Long>, EmailAuthCustomRepository{
    @Query(value = "select p.* from email_auth p where p.email = ?1 AND p.auth_token = ?2", nativeQuery = true)
    EmailAuth findValidAuthByEmail(String email, String authToken);

    EmailAuth findByEmail(String email);
}
