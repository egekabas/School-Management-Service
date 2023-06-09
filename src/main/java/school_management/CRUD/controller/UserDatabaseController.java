package school_management.CRUD.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import school_management.CRUD.entities.UserEntity;
import school_management.CRUD.service.DatabaseService;
import school_management.security.service.CustomUserDetailsService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserDatabaseController {

    private final DatabaseService databaseService;

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public List<UserEntity> getAllUsers(){
        return databaseService.loadAll();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addUser(@RequestBody @Valid NewUserRequest userRequest) { return databaseService.addNewUser(userRequest); }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable("id") @Valid long id){ return databaseService.deleteByID(id);}

    @GetMapping("/getCurrentAuthorities")
    @ResponseBody
    public Object getCurrentAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @GetMapping("/getIfAuthenticated")
    public boolean getIfAuthenticated(Authentication authentication){
        return authentication != null;
    }

    @GetMapping("/getCurrentUser")
    @ResponseBody
    public UserEntity getCurrentUser(){
        return databaseService.loadCurrentUser();
    }


    @PostMapping("/changePassword")
    @ResponseBody
    public String changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return databaseService.changePassword(request.oldPassword(), request.newPassword());
    }

}
