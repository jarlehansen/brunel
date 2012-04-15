package com.googlecode.sc2dm.annotations.util;

import android.content.Context;
import com.googlecode.sc2dm.annotations.OnError;
import com.googlecode.sc2dm.annotations.OnMessage;
import com.googlecode.sc2dm.annotations.OnRegistered;
import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:36 PM - 9/19/11
 */
public class AnnotatedOneArg1 {

    @OnRegistered("http://test")
    public void reg(Context context) {
        SC2DMLogger.i("reg() called");
    }

    @OnMessage
    public void msg(Context context) {
        SC2DMLogger.i("msg() called");
    }

    @OnError
    public void error(Context context) {
        SC2DMLogger.i("error() called");
    }
}
