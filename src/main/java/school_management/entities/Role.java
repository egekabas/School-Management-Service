package school_management.entities;


public enum Role {
    ADMIN("Admin"), STUDENT("Student"), TA("TA"), LECTURER("LECTURER");

    private String role;
    Role(String role){
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}

