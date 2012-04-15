package com.googlecode.sc2dm.annotations.util;

import com.googlecode.sc2dm.annotations.OnError;
import com.googlecode.sc2dm.annotations.OnMessage;
import com.googlecode.sc2dm.annotations.OnRegistered;
import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:42 PM - 9/19/11
 */
public class AnnotatedOneArg2 {

    @OnRegistered
    public void reg(String registrationId) {
        SC2DMLogger.i("reg() called, registrationid: " + registrationId);
        MessageStorageUtil.REG_ID.setIdentifier(registrationId);
    }

    @OnMessage
    public void msg(String msg) {
        SC2DMLogger.i("msg() called, intent.getAction: " + msg);
        MessageStorageUtil.MSG.setIdentifier(msg);
    }

    @OnError
    public void error(String errorMsg) {
        SC2DMLogger.i("error() called, errorMsg: " + errorMsg);
        MessageStorageUtil.MSG.setIdentifier(errorMsg);
    }
}
