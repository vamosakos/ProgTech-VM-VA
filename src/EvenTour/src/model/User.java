package model;

public class User {
    private int id;
    private String full_name;
    private String email;
    private String password;
    private int permission;

    public User() {}

    //region properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    //endregion
}
