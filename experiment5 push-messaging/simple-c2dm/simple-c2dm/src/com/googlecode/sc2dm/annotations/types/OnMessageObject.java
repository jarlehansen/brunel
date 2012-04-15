package com.googlecode.sc2dm.annotations.types;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.googlecode.sc2dm.SC2DMUtils;
import com.googlecode.sc2dm.activity.R;
import com.googlecode.sc2dm.log.SC2DMLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:47 PM - 9/19/11
 */
public class OnMessageObject extends AnnotatedObject {
    private final Class<? extends Activity> activityClass;

    public OnMessageObject(Class<?> clazz, Method method) {
        super(clazz, method);
        activityClass = null;
    }

    public OnMessageObject(Class<?> clazz, Method method, Class<? extends Activity> activityClass) {
        super(clazz, method);
        this.activityClass = activityClass;
    }

    public void invokeOnMessage(Context context, Intent intent) {
        Object instance = super.instance();

        try {
            if (intent.hasExtra("msg")) {
                String message = intent.getStringExtra("msg");

                if (activityClass != null)
                    SC2DMUtils.createNotification(context, R.drawable.icon, message, message, activityClass);

                method.invoke(instance, buildArgs(context, message));
            } else
                SC2DMLogger.w("Message was recevied but did not contain the 'msg' field");
        } catch (IllegalAccessException e) {
            SC2DMLogger.e("Unable to access onMessage", e);
        } catch (InvocationTargetException e) {
            SC2DMLogger.e("Unable to invoke onMessage", e);
        }
    }
}
