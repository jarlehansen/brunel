package com.googlecode.sc2dm.annotations.util;

import com.googlecode.sc2dm.annotations.OnError;
import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:38 PM - 10/7/11
 */
public class OnErrorAnnotated {

    @OnError
    public void error(String errorMsg) {
        SC2DMLogger.i("error() called");
    }

}
