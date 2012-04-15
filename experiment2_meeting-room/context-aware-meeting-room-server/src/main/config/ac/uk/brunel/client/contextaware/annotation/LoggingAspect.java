package ac.uk.brunel.client.contextaware.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Logs method calls and all input and return values.
 * 
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 5:57:36 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoggingAspect {
}
