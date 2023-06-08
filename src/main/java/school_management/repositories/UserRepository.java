package school_management.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import school_management.entities.CustomUser;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<CustomUser, Long> {

    Optional<CustomUser> findByUsername(String username);

    List<CustomUser> findAll();
}
