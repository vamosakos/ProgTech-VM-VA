package Strategy;
import Models.*;

public abstract class AbstractPermissionProcessor {
    public abstract void userDashboardLoad(User user);
    public abstract void adminDashboardLoad(User user);
}
