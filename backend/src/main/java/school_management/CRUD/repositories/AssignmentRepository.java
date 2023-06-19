package school_management.CRUD.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import school_management.CRUD.entities.AssignmentEntity;

public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {
}
