package school_management.security.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school_management.entities.CustomAuthority;
import school_management.entities.CustomUser;
import school_management.entities.Role;
import school_management.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        userRepository.save(CustomUser.makeRootAdmin("root", passwordEncoder.encode("root")));
    }

    public CustomUser loadUserByUsername(String username){
        return userRepository.findByUsername(username)
                             .orElseThrow(() -> new UsernameNotFoundException("User with username" + username + "not found"));
    }
}
