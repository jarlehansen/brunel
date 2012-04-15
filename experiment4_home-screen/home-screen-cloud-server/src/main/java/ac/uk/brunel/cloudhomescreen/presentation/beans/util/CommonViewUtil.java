package ac.uk.brunel.cloudhomescreen.presentation.beans.util;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:22:26 PM - Nov 20, 2010
 */
public enum CommonViewUtil {
    ;
    private static UserService userService = UserServiceFactory.getUserService();

    public static String createLogoutURL() {
        return userService.createLogoutURL("../");
    }
}
