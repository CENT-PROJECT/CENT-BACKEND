package SPOTY.Backend.domain.mail;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MailRedisRepository extends CrudRepository<Mail, String> {
    Optional<Mail> findMailByIdAndCode(String id, String code);

}
