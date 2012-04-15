package com.googlecode.sc2dm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:09 PM - 9/14/11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnRegistered {
    String value() default "";
}
