package school_management.CRUD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private Role role;


    //@NotNull(message = "First Name cant be null")
    private String firstName = "";
    //@NotNull(message = "First Name cant be null")
    private String lastName = "";

    //@Email(message = "Email is invalid")
    private String email;

    @Column(unique = true)
    private String username;

    private String password;


    public static UserEntity makeRootAdmin(String username, String password){
        UserEntity user = new UserEntity();

        user.username = username;
        user.password = password;
        user.role = Role.ADMIN;

        return user;
    }
}