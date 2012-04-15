package com.googlecode.sc2dm.annotations;

import com.googlecode.sc2dm.annotations.util.AnnotatedTwoArgs;
import com.googlecode.sc2dm.annotations.util.OnErrorAnnotated;
import com.googlecode.sc2dm.annotations.util.OnMessageAnnotated;
import com.googlecode.sc2dm.annotations.util.OnRegisteredAnnotated;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:31 PM - 10/7/11
 */
public class AnnotatedClassTest extends TestCase {

    public void testInit() {
        AnnotatedClasses annotatedClasses = new AnnotatedClasses(null);

        assertNull(annotatedClasses.getOnRegisteredObj());
        assertNull(annotatedClasses.getOnMessageObj());
        assertNull(annotatedClasses.getOnErrorObj());
    }

    public void testCreateAnnotationsObjects_withAllAnnotations() {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        classes.add(AnnotatedTwoArgs.class);

        AnnotatedClasses annotatedClasses = new AnnotatedClasses(classes);
        annotatedClasses.createAnnotationsObjects();

        assertNotNull(annotatedClasses.getOnRegisteredObj());
        assertNotNull(annotatedClasses.getOnMessageObj());
        assertNotNull(annotatedClasses.getOnErrorObj());
    }

    public void testCreateAnnotationsObjects_withOnRegistered() {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        classes.add(OnRegisteredAnnotated.class);

        AnnotatedClasses annotatedClasses = new AnnotatedClasses(classes);
        annotatedClasses.createAnnotationsObjects();

        assertNotNull(annotatedClasses.getOnRegisteredObj());
        assertNull(annotatedClasses.getOnMessageObj());
        assertNull(annotatedClasses.getOnErrorObj());
    }

    public void testCreateAnnotationsObjects_withOnMessage() {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        classes.add(OnMessageAnnotated.class);

        AnnotatedClasses annotatedClasses = new AnnotatedClasses(classes);
        annotatedClasses.createAnnotationsObjects();

        assertNull(annotatedClasses.getOnRegisteredObj());
        assertNotNull(annotatedClasses.getOnMessageObj());
        assertNull(annotatedClasses.getOnErrorObj());
    }

    public void testCreateAnnotationsObjects_withOnError() {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        classes.add(OnErrorAnnotated.class);

        AnnotatedClasses annotatedClasses = new AnnotatedClasses(classes);
        annotatedClasses.createAnnotationsObjects();

        assertNull(annotatedClasses.getOnRegisteredObj());
        assertNull(annotatedClasses.getOnMessageObj());
        assertNotNull(annotatedClasses.getOnErrorObj());
    }
}
