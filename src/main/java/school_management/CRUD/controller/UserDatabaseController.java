package school_management.CRUD.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import school_management.CRUD.entities.UserEntity;
import school_management.CRUD.service.DatabaseService;
import school_management.security.service.CustomUserDetailsService;

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

}
