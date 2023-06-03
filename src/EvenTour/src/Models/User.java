package Models;

public class User {
    private int id;
    private String full_name;
    private String email;
    private String password;
    private int permission;

    public User() {}
    public User(int id, String full_name, String email, String password, int permission) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.permission = permission;
    }

    //region properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
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
