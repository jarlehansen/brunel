package com.googlecode.sc2dm.annotations.util;

import com.googlecode.sc2dm.annotations.OnMessage;
import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:37 PM - 10/7/11
 */
public class OnMessageAnnotated {

    @OnMessage
    public void msg(String msg) {
        SC2DMLogger.i("msg() called");
    }
    
}
