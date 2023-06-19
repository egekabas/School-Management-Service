package school_management.CRUD.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import school_management.CRUD.entities.Role;
import school_management.CRUD.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findAll();

    List<UserEntity> findAllByRole(Role role);

    boolean existsByUsername(String username);

}
