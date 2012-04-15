package com.googlecode.sc2dm;

import com.googlecode.sc2dm.messages.receiver.MessageReceiver;
import com.googlecode.sc2dm.messages.register.DeviceRegistration;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:46 PM - 9/20/11
 */
public enum SC2DM {
    INSTANCE;

    private String email = "";

    private DeviceRegistration deviceRegistration = null;
    private MessageReceiver messageReceiver = null;

    void setEmail(String email) {
        this.email = email;
    }

    void setDeviceRegistration(DeviceRegistration deviceRegistration) {
        this.deviceRegistration = deviceRegistration;
    }

    void setMessageReceiver(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    void setSC2DMCallback(SC2DMCallback callbackClass) {
        SC2DMCallbackAdapter adapter = new SC2DMCallbackAdapter(callbackClass);

        deviceRegistration = adapter;
        messageReceiver = adapter;

        
    }

    public String email() {
        return email;
    }

    public DeviceRegistration deviceRegistration() {
        return deviceRegistration;
    }

    public MessageReceiver messageReceiver() {
        return messageReceiver;
    }

    public void reset() {
        email = "";
        deviceRegistration = null;
        messageReceiver = null;
    }
}
