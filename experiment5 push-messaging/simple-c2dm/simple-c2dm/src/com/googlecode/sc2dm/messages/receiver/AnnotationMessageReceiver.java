package com.googlecode.sc2dm.messages.receiver;

import android.content.Context;
import android.content.Intent;
import com.googlecode.sc2dm.annotations.types.OnErrorObject;
import com.googlecode.sc2dm.annotations.types.OnMessageObject;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:35 PM - 9/18/11
 */
public class AnnotationMessageReceiver implements MessageReceiver {
    private OnMessageObject onMessageObj = null;
    private OnErrorObject onErrorObj = null;

    public AnnotationMessageReceiver(OnMessageObject onMessageObj, OnErrorObject onErrorObj) {
        this.onMessageObj = onMessageObj;
        this.onErrorObj = onErrorObj;
    }

    public void onMessage(Context context, Intent intent) {
        if (onMessageObj != null)
            onMessageObj.invokeOnMessage(context, intent);
    }

    public void onError(Context context, String errorMsg) {
        if (onErrorObj != null)
            onErrorObj.invokeOnError(context, errorMsg);
    }
}
