import form.LoginForm;
import log.*;

public class Main {
    public static void main(String[] args) {
        logger.writeToLog("Application started with login window");
        new LoginForm(null);
    }
}