package com.googlecode.sc2dm.server.registration;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:12 PM - 9/22/11
 */
public enum RegIdReceiver {
    ;

    private static final Logger logger = Logger.getLogger(RegIdReceiver.class.getName());

    public static PushDevice createPushDevice(HttpServletRequest request) {
        String email = request.getParameter("email");
        String registrationId = request.getParameter("regId");

        if(email == null) {
            email = "";
            logger.warning("The 'email' request parameter was null, setting it to empty string");
        } else
            logger.info("E-mail: " + email);

        if(registrationId == null) {
            registrationId = "";
            logger.warning("The 'regId' request parameter was null, setting it to empty string");
        } else
            logger.info("Registration id: " + registrationId);

        return new PushDevice(email, registrationId);
    }

}
