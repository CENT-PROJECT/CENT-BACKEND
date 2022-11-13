package SPOTY.Backend.domain.user.repository;

import SPOTY.Backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, UserQueryRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndProviderType(String email, String providerType);
}
