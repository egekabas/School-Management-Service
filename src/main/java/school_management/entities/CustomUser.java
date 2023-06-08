package school_management.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomUser implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new CustomAuthority(this.role.getRole()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static CustomUser makeRootAdmin(String username, String password){
        CustomUser user = new CustomUser();

        user.username = username;
        user.password = password;
        user.role = Role.ADMIN;

        return user;
    }
}