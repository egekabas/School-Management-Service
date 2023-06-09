package school_management.CRUD.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/getCurrentAuthorities")
    @ResponseBody
    public Object getCurrentAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @GetMapping("/getIfAuthenticated")
    @ResponseBody
    public boolean getIfAuthenticated(Authentication authentication){
        return authentication != null;
    }

    @GetMapping("/getCurrentUser")
    @ResponseBody
    public UserEntity getCurrentUser(){
        return databaseService.loadCurrentUser();
    }

}
