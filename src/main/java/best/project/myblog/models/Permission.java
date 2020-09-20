package best.project.myblog.models;

public enum Permission {
    USER_READ("users:read"),
    USER_WRITE("users:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
