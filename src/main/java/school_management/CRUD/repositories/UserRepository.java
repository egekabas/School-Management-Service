package school_management.CRUD.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import school_management.CRUD.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    void deleteById(Long aLong);
    List<UserEntity> findAll();

}
