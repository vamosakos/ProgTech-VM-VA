package Strategy;

import Forms.AdminFormStrategy;
import Forms.UserFormStrategy;
import Models.*;

public class PermissionProcessor extends AbstractPermissionProcessor {

    private UserDashboardLoadStrategy userDashboardLoadStrategy;
    private AdminDashboardLoadStrategy adminDashboardLoadStrategy;
    private UserFormStrategy userFormStrategy;
    private AdminFormStrategy adminFormStrategy;

    public PermissionProcessor(PermissionType permissionType, User user) {
        switch (permissionType) {
            case ADMIN :
                adminFormStrategy = new AdminFormStrategy(user);
                break;
            case USER :
                userFormStrategy = new UserFormStrategy(user);
                break;
            default: break;
        }
    }
    @Override
    public void userDashboardLoad(User user) {
        userDashboardLoadStrategy.userDashboardLoad(user);
    }

    @Override
    public void adminDashboardLoad(User user) {
        adminDashboardLoadStrategy.adminDashboardLoad(user);
    }
}
