package com.googlecode.sc2dm.annotations.util;

import com.googlecode.sc2dm.annotations.OnRegistered;
import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:37 PM - 10/7/11
 */
public class OnRegisteredAnnotated {

    @OnRegistered("http://test")
    public void reg(String registrationId) {
        SC2DMLogger.i("reg() called");
    }
    
}
