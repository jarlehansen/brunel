package com.googlecode.sc2dm.messages.register;

import android.content.Context;

import java.io.IOException;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 12:52 PM - 10/6/11
 */
public interface DeviceRegistration {
    public void register(Context context, String email);
    public void onRegistered(Context context, String registrationId) throws IOException;
}
