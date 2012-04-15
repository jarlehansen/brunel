package com.googlecode.sc2dm.messages.register;

import android.content.Context;
import com.google.android.c2dm.C2DMessaging;
import com.googlecode.sc2dm.annotations.types.OnRegisteredObject;
import com.googlecode.sc2dm.log.SC2DMLogger;

import java.io.IOException;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:48 PM - 9/18/11
 */
public class AnnotationDeviceRegistration implements DeviceRegistration {
    private final OnRegisteredObject onRegisteredObj;

    public AnnotationDeviceRegistration(OnRegisteredObject onRegisteredObj) {
        this.onRegisteredObj = onRegisteredObj;
    }

    public void register(Context context, String email) {
        if (context != null)
            C2DMessaging.register(context, email);
        else
            SC2DMLogger.w("Context is null");
    }

    public void onRegistered(Context context, String registrationId) throws IOException {
        if (onRegisteredObj != null)
            onRegisteredObj.invokeOnRegistered(context, registrationId);
    }

}
