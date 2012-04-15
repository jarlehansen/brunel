package com.googlecode.sc2dm;

import android.content.Context;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:54 PM - 10/5/11
 */
public interface SC2DMCallback {
    public void onRegistered(Context context, String registrationId);

    public void onMessage(Context context, String message);

    public void onError(Context context, String errorMsg);
}
