package school_management.CRUD.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import school_management.CRUD.entities.AssignmentEntity;
import school_management.CRUD.entities.GradeEntity;
import school_management.CRUD.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<GradeEntity, Long> {

    Optional<GradeEntity> findByStudentAndAssignment(UserEntity student, AssignmentEntity assignment);
    void deleteAllByStudentAndAssignment(UserEntity student, AssignmentEntity assignment);

}
