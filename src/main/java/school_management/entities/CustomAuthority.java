package school_management.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public class CustomAuthority implements GrantedAuthority {
    private String authority;
}
