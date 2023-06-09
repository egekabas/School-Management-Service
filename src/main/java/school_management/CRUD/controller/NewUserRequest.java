package school_management.CRUD.controller;

import school_management.CRUD.entities.Role;

public record NewUserRequest(
        Role role,
        String firstName,
        String lastName,
        String email,
        String username

        ) {
}
