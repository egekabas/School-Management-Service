package school_management.CRUD.controller;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
