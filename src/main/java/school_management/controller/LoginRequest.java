package school_management.controller;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty
        String username,
        @NotEmpty
        String password

) {
}