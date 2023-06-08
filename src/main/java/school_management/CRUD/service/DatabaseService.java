package school_management.CRUD.service;

import lombok.AllArgsConstructor;
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
}
