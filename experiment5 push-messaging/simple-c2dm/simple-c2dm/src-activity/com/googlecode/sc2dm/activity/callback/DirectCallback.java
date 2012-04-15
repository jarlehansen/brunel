package com.googlecode.sc2dm.activity.callback;

import android.content.Context;
import com.googlecode.sc2dm.SC2DMCallback;
import com.googlecode.sc2dm.SC2DMUtils;
import com.googlecode.sc2dm.activity.DirectMessagesActivity;
import com.googlecode.sc2dm.activity.R;
import com.googlecode.sc2dm.log.SC2DMLogger;
import com.googlecode.sc2dm.sender.RegIdSenderUtil;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:34 PM - 10/5/11
 */
public class DirectCallback implements SC2DMCallback {

    @Override
    public void onRegistered(Context context, String registrationId) {
        SC2DMLogger.i("onRegistered: " + registrationId);
        RegIdSenderUtil.sendRegistrationId("http://ping-servlet.appspot.com/", registrationId);
    }

    @Override
    public void onMessage(Context context, String message) {
        SC2DMLogger.i("onMessage: " + message);
        SC2DMUtils.createNotification(context, R.drawable.icon, message, message, DirectMessagesActivity.class);
    }

    @Override
    public void onError(Context context, String errorMsg) {
        SC2DMLogger.i("onError: " + errorMsg);
    }
}
