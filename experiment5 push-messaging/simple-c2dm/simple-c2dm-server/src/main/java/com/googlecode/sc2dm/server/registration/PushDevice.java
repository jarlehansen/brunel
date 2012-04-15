package com.googlecode.sc2dm.server.registration;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:34 PM - 9/22/11
 */
public class PushDevice {
    private final String email;
    private final String registrationId;

    public PushDevice(String email, String registrationId) {
        this.email = email;
        this.registrationId = registrationId;
    }

    public String getEmail() {
        return email;
    }

    public String getRegistrationId() {
        return registrationId;
    }
}
