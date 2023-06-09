package school_management.CRUD.service;

import lombok.AllArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school_management.CRUD.controller.NewUserRequest;
import school_management.CRUD.entities.UserEntity;
import school_management.CRUD.repositories.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DatabaseService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public List<UserEntity> loadAll(){
        return userRepository.findAll();
    }

    public UserEntity loadCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                "Not currently logged in"));
    }

    public String addNewUser(NewUserRequest userRequest) {
        String password = randomPasswordGenerator(10);
        UserEntity user = new UserEntity(userRequest, passwordEncoder.encode(password));
        userRepository.save(user);
        return password;
    }

    private String randomPasswordGenerator(int length){
        RandomStringGenerator stringGenerator = new RandomStringGenerator.Builder().withinRange(33, 45).build();
        return stringGenerator.generate(length);
    }

    public String deleteByID(long id){
        userRepository.deleteById(id);
        return "Deleted Successfully";
    }

    public String changePassword(String oldPassword, String newPassword){
        UserEntity user = loadCurrentUser();

        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return "Password changed successfully";
        } else{
            return "Old Password entered incorrectly";
        }
    }
}
