package com.googlecode.sc2dm.integration;

import android.content.Context;
import android.content.Intent;
import com.google.android.c2dm.C2DMBaseReceiver;
import com.googlecode.sc2dm.SC2DM;

import java.io.IOException;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:08 PM - 9/18/11
 */
public class SC2DMReceiver extends C2DMBaseReceiver {

    public SC2DMReceiver() {
        super(SC2DM.INSTANCE.email());
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        SC2DM.INSTANCE.messageReceiver().onMessage(context, intent);
    }

    @Override
    public void onError(Context context, String errorMsg) {
        SC2DM.INSTANCE.messageReceiver().onError(context, errorMsg);
    }

    @Override
    public void onRegistered(Context context, String registrationId) throws IOException {
        SC2DM.INSTANCE.deviceRegistration().onRegistered(context, registrationId);
    }
}
