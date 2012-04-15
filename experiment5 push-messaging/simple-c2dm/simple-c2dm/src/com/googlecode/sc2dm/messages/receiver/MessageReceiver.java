package com.googlecode.sc2dm.messages.receiver;

import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 12:53 PM - 10/6/11
 */
public interface MessageReceiver {
    public void onMessage(Context context, Intent intent);
    public void onError(Context context, String errorMsg);
}
