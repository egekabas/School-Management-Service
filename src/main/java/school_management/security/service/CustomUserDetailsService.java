package school_management.security.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school_management.CRUD.entities.UserEntity;
import school_management.CRUD.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        userRepository.save(UserEntity.makeRootAdmin("root", passwordEncoder.encode("root")));
    }

    public User loadUserByUsername(String username){
        return makeUser(userRepository.findByUsername(username)
                             .orElseThrow(() -> new UsernameNotFoundException("User with username" + username + "not found")));
    }


    private User makeUser(UserEntity userEntity){
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().toString())));
    }
}
