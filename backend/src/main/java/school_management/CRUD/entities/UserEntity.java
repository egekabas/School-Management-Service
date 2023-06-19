package school_management.CRUD.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_management.CRUD.controller.requestRecords.NewUserRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(value = {"classesTaught", "classesTaken", "classesTAd", "password", "taApplications", "grades"})
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

//    @NotNull(message = "First Name cant be null")
    private String firstName;
//    @NotNull(message = "First Name cant be null")
    private String lastName;

//    @Email(message = "Email is invalid")
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectEntity> classesTaught = new ArrayList<>();

    @ManyToMany(mappedBy = "students")
    private List<SubjectEntity> classesTaken = new ArrayList<>();

    @ManyToMany(mappedBy = "tas")
    private List<SubjectEntity> classesTAd = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaApplicationEntity> taApplications = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradeEntity> grades = new ArrayList<>();

    public static UserEntity makeRootAdmin(String username, String password){
        UserEntity user = new UserEntity();

        user.username = username;
        user.password = password;
        user.role = Role.ADMIN;

        return user;
    }

    public UserEntity(NewUserRequest userRequest, String password){
        this.role = userRequest.role();
        this.firstName = userRequest.firstName();
        this.lastName = userRequest.lastName();
        this.email = userRequest.email();
        this.username = userRequest.username();
        this.password = password;
    }

    @PreRemove
    private void removeFromSubjects(){
        for(SubjectEntity subject : classesTaken){
            subject.getStudents().remove(this);
        }
        for(SubjectEntity subject : classesTAd){
            subject.getTas().remove(this);
        }
    }
}