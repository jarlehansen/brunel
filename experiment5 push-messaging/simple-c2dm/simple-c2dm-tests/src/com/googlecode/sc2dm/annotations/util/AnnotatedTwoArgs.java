package com.googlecode.sc2dm.annotations.util;

import android.content.Context;
import com.googlecode.sc2dm.annotations.OnError;
import com.googlecode.sc2dm.annotations.OnMessage;
import com.googlecode.sc2dm.annotations.OnRegistered;
import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:39 PM - 9/18/11
 */
public class AnnotatedTwoArgs {

    @OnRegistered("http://testingtesting")
    public void reg(Context context, String registrationId) {
        SC2DMLogger.i("reg() called, registrationid: " + registrationId);
        MessageStorageUtil.REG_ID.setIdentifier(registrationId);
    }

    @OnMessage
    public void msg(Context context, String msg) {
        SC2DMLogger.i("msg() called, msg: " + msg);
        MessageStorageUtil.MSG.setIdentifier(msg);
    }

    @OnError
    public void error(Context context, String errorMsg) {
        SC2DMLogger.i("error() called, errorMsg: " + errorMsg);
        MessageStorageUtil.MSG.setIdentifier(errorMsg);
    }
}
