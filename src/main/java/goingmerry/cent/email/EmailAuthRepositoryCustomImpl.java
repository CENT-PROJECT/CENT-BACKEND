package goingmerry.cent.email;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

public class EmailAuthRepositoryCustomImpl implements EmailAuthRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public EmailAuthRepositoryCustomImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<EmailAuth> findValidAuthByEmail(String email, String authToken, LocalDateTime currentTime) {
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
