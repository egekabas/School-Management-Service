package school_management.CRUD.controller.requestRecords;

public record AddAssignmentRequest(
        long subjectId,
        String description
) {
}
