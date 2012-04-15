package com.googlecode.sc2dm.annotations;

import com.googlecode.sc2dm.annotations.types.AnnotatedObject;
import com.googlecode.sc2dm.annotations.types.OnRegisteredObject;
import com.googlecode.sc2dm.annotations.util.AnnotatedOneArg1;
import com.googlecode.sc2dm.annotations.util.AnnotatedOneArg2;
import com.googlecode.sc2dm.annotations.util.AnnotatedTwoArgs;
import junit.framework.TestCase;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:46 PM - 9/18/11
 */
public class AnnotationFinderTest extends TestCase {

    public void testFindOnRegistered() {
        AnnotatedObject obj = AnnotationFinder.findOnRegistered(AnnotatedTwoArgs.class);

        assertEquals("reg", obj.getMethod().getName());
    }

    public void testOnRegisteredWithoutServerUrl() {
        OnRegisteredObject obj = AnnotationFinder.findOnRegistered(AnnotatedOneArg2.class);
        String serverUrl = obj.getServerUrl();

        assertEquals("", serverUrl);
    }

    public void testOnRegisteredWithServerUrl() {
        OnRegisteredObject obj = AnnotationFinder.findOnRegistered(AnnotatedOneArg1.class);
        String serverUrl = obj.getServerUrl();

        assertEquals("http://test", serverUrl);
    }

    public void testFindOnMessage() {
        AnnotatedObject obj = AnnotationFinder.findOnMessage(AnnotatedTwoArgs.class);

        assertEquals("msg", obj.getMethod().getName());
    }

    public void testFindOnError() {
        AnnotatedObject obj = AnnotationFinder.findOnError(AnnotatedTwoArgs.class);

        assertEquals("error", obj.getMethod().getName());
    }

    public void testInvalidInput() {
        assertNull(AnnotationFinder.findOnRegistered(String.class));
    }
}
