package fitenessTrackerApp.repository;

import fitenessTrackerApp.etities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    boolean existsByEmailAndUsernameNot(String email, String username);

    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    boolean existsByUsernameAndEmailNot(String username, String email);
}
