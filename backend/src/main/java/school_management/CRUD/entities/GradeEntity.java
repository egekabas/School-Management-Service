package school_management.CRUD.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GradeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Integer grade;
    private boolean isGraded = false;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private UserEntity student;

    @ManyToOne
    @JoinColumn(name = "assignment_id", referencedColumnName = "id")
    private AssignmentEntity assignment;

    public void assignGrade(int grade){
        isGraded = true;
        this.grade = grade;
    }

    public GradeEntity(UserEntity student, AssignmentEntity assignment){
        this.student = student;
        this.assignment = assignment;
    }
}
