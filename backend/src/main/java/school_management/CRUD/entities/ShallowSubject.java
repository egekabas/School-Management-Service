package school_management.CRUD.entities;

import org.springframework.boot.autoconfigure.security.SecurityProperties;

public record ShallowSubject (
        Long id,
        String name,
        String description,
        UserEntity lecturer
) {
}
