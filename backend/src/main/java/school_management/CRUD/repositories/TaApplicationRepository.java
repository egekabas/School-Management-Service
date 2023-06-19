package school_management.CRUD.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import school_management.CRUD.entities.SubjectEntity;
import school_management.CRUD.entities.TaApplicationEntity;
import school_management.CRUD.entities.UserEntity;

public interface TaApplicationRepository extends JpaRepository<TaApplicationEntity, Long> {
    public boolean existsBySubjectAndUser(SubjectEntity subject, UserEntity user);
}
