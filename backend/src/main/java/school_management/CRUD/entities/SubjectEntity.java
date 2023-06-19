package school_management.CRUD.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_management.CRUD.controller.requestRecords.NewSubjectRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SubjectEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", referencedColumnName = "id")
    private UserEntity lecturer;

    @ManyToMany
    @JoinTable(
            name = "subject_TAs",
            joinColumns = @JoinColumn(name = "TA_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id")
    )
    private List<UserEntity> tas = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "subject_students",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id")
    )
    private List<UserEntity> students = new ArrayList<>();

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaApplicationEntity> taApplications = new ArrayList<>();

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignmentEntity> assignments = new ArrayList<>();

    public SubjectEntity(NewSubjectRequest subjectRequest, UserEntity lecturer){
        this.name = subjectRequest.name();
        this.description = subjectRequest.description();
        this.lecturer = lecturer;
    }

    public ShallowSubject shallowInfo(){
        return new ShallowSubject(id, name, description, lecturer);
    }


}
