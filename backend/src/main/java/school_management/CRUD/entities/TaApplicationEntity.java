package school_management.CRUD.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(value = {"subject"})
public class TaApplicationEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 1000)
    private String applicationText;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public TaApplicationEntity(String applicationText, SubjectEntity subject, UserEntity user) {
        this.applicationText = applicationText;
        this.subject = subject;
        this.user = user;
    }
}
