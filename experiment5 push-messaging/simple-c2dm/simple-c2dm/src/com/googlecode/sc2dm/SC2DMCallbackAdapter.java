package com.googlecode.sc2dm;

import android.content.Context;
import android.content.Intent;
import com.google.android.c2dm.C2DMessaging;
import com.googlecode.sc2dm.log.SC2DMLogger;
import com.googlecode.sc2dm.messages.receiver.MessageReceiver;
import com.googlecode.sc2dm.messages.register.DeviceRegistration;

import java.io.IOException;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 12:58 PM - 10/6/11
 */
class SC2DMCallbackAdapter implements DeviceRegistration, MessageReceiver {
    private SC2DMCallback callbackClass;

    SC2DMCallbackAdapter(SC2DMCallback callbackClass) {
        this.callbackClass = callbackClass;
    }

    @Override
    public void register(Context context, String email) {
        if (context != null)
            C2DMessaging.register(context, email);
        else
            SC2DMLogger.w("Context is null");
    }

    @Override
    public void onRegistered(Context context, String registrationId) throws IOException {
        callbackClass.onRegistered(context, registrationId);
    }

    @Override
    public void onMessage(Context context, Intent intent) {
        if (intent.hasExtra("msg")) {
            callbackClass.onMessage(context, intent.getStringExtra("msg"));
        }
    }

    @Override
    public void onError(Context context, String errorMsg) {
        callbackClass.onError(context, errorMsg);
    }
}
