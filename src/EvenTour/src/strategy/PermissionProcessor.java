package strategy;

import form.AdminFormStrategy;
import form.UserFormStrategy;
import model.*;

public class PermissionProcessor  {


    public PermissionProcessor(PermissionType permissionType, User user) {
        switch (permissionType) {
            case ADMIN :
                new AdminFormStrategy(user);
                break;
            case USER :
               new UserFormStrategy(user);
                break;
            default: break;
        }
    }

}
