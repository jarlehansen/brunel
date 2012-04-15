package com.googlecode.sc2dm.sender;

import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 1:35 PM - 10/6/11
 */
public enum RegIdSenderUtil {
    ;

    public static boolean sendRegistrationId(String serverUrl, String registrationId) {
        final boolean regIdSent;

        if (serverUrl != null && !"".equals(serverUrl) && registrationId != null && !"".equals(registrationId)) {
            new RegistrationIdSender(serverUrl, registrationId).sendInBackground();
            regIdSent = true;
        } else {
            SC2DMLogger.w("Unable to send registration id to server, incomplete input. serverUrl: ", serverUrl, ", registrationId: ", registrationId);
            regIdSent = false;
        }

        return regIdSent;
    }
}
