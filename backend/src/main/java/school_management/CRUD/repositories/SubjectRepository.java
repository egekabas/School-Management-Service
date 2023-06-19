package school_management.CRUD.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import school_management.CRUD.entities.SubjectEntity;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    Optional<SubjectEntity> findByName(String name);

}
