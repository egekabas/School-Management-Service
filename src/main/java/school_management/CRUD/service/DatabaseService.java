package school_management.CRUD.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import school_management.CRUD.entities.UserEntity;
import school_management.CRUD.repositories.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DatabaseService {

    private final UserRepository userRepository;

    public List<UserEntity> loadAll(){
        return userRepository.findAll();
    }

    public UserEntity loadCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                "Not currently logged in"));
    }
}
