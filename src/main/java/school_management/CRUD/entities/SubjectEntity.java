package school_management.CRUD.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SubjectEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private boolean isMandatory;

    private int subjectCode;

    private String classRoom;

    @ManyToOne
    private UserEntity lecturer;

    @ManyToMany
    private List<UserEntity> TAs;

    @ManyToMany
    private List<UserEntity> students;
}
