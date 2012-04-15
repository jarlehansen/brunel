package com.googlecode.sc2dm.activity.annotation;

import android.content.Context;
import com.googlecode.sc2dm.SC2DMUtils;
import com.googlecode.sc2dm.activity.AnnotatedMessagesActivity;
import com.googlecode.sc2dm.activity.R;
import com.googlecode.sc2dm.annotations.OnError;
import com.googlecode.sc2dm.annotations.OnMessage;
import com.googlecode.sc2dm.annotations.OnMessageNotification;
import com.googlecode.sc2dm.annotations.OnRegistered;
import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:20 PM - 9/14/11
 */
public class AnnotatedCallback {

    public AnnotatedCallback() {
        SC2DMLogger.i("Constructing object");
    }

    @OnRegistered("http://ping-servlet.appspot.com")
    public void registered(String registrationId) {
        SC2DMLogger.i("registered() called: ", registrationId);
    }

//    @OnMessage
//    public void message(Context context, String msg) {
//        SC2DMLogger.i("message() called: ", msg);
//        SC2DMUtils.createNotification(context, R.drawable.icon, "testing", msg, context.getClass());
//    }

    @OnMessageNotification(AnnotatedMessagesActivity.class)
    public void messageNotification(String msg) {
        SC2DMLogger.i("messageNotification() called: ", msg);
    }

    @OnError
    public void error(String errorMsg) {
        SC2DMLogger.i("error() called: " + errorMsg);
    }
}
