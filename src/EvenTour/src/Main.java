public class Main {
    public static void main(String[] args) {
        RegistrationFrom regFrom = new RegistrationFrom(null);

        User user = regFrom.user;
        if (user != null) {
            System.out.println("Successful registration of: " + user.full_name);
        }
        else {
            System.out.println("Registration canceled");
        }

    }
}