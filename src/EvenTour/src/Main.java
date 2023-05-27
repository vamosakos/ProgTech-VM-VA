public class Main {
    public static void main(String[] args) {
        /*
        RegistrationForm regForm = new RegistrationForm(null);

        User user = regForm.user;
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
            EvenTourFormA evenTourFormA = new EvenTourFormA(null);
        }
        else {
            System.out.println("Authentication canceled");
        }
    }
}