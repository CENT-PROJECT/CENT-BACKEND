package goingmerry.cent.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmailAuthRepository extends JpaRepository<EmailAuth,Long>, EmailAuthRepositoryCustom {
    Optional<EmailAuth> findByEmail(String email);
}
