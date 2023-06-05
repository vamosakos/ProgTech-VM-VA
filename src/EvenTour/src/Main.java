import form.LoginForm;
import util.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.writeToLog("Application started with login window");
        new LoginForm(null);
    }
}