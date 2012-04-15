package com.googlecode.sc2dm.annotations;

import android.app.Activity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:25 PM - 11/18/11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnMessageNotification {
    Class<? extends Activity> value();
}
