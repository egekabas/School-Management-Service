package school_management.CRUD.controller.requestRecords;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
