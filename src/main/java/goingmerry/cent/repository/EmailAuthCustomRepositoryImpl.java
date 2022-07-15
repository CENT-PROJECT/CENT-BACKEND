package goingmerry.cent.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import goingmerry.cent.domain.EmailAuth;
import goingmerry.cent.domain.QEmailAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class EmailAuthCustomRepositoryImpl implements EmailAuthCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public EmailAuthCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<EmailAuth> findValidAuthByEmail(String email, String authToken, LocalDateTime currentTime) {
        log.info("{}, {}, {}", email, authToken, currentTime);
        EmailAuth emailAuth = jpaQueryFactory
                .selectFrom(QEmailAuth.emailAuth)
                .where(QEmailAuth.emailAuth.email.eq(email),
                        QEmailAuth.emailAuth.authToken.eq(authToken),
                        QEmailAuth.emailAuth.expireDate.goe(currentTime),
                        QEmailAuth.emailAuth.expired.eq(false))
                .fetchFirst();

        return Optional.ofNullable(emailAuth);
    }
}
