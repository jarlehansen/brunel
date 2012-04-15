package com.googlecode.sc2dm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 1:13 PM - 11/20/11
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface SC2DMAutomaticManifest {
    String value() default "";
}
