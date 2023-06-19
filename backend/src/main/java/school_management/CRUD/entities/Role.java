package school_management.CRUD.entities;


public enum Role {
    ADMIN("ADMIN"), STUDENT("STUDENT"), TA("TA"), LECTURER("LECTURER");

    private String role;
    Role(String role){
        this.role = role;
    }
    public String toString() {
        return role;
    }
}

