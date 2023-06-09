package school_management.security.controller;


public record LoginRequest(
        String username,
        String password

) {
}