import com.mysql.cj.log.Log;

public class Main {
    public static void main(String[] args) {
        /*
        RegistrationFrom regFrom = new RegistrationFrom(null);

        User user = regFrom.user;
        if (user != null) {
            System.out.println("Successful registration of: " + user.full_name);
        }
        else {
            System.out.println("Registration canceled");
        }
         */

        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;

        if (user != null) {
            System.out.println("Successful Authentication of: " + user.full_name);
            System.out.println("            Email: " + user.email);
        }
        else {
            System.out.println("Authentication canceled");
        }
    }
}