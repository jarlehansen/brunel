package com.googlecode.sc2dm.annotations.types;

import android.content.Context;
import com.googlecode.sc2dm.annotations.util.AnnotatedOneArg1;
import com.googlecode.sc2dm.annotations.util.AnnotatedOneArg2;
import com.googlecode.sc2dm.annotations.util.AnnotatedTwoArgs;
import junit.framework.TestCase;

import java.lang.reflect.Method;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 12:57 PM - 9/22/11
 */
public class AnnotatedObjectTest extends TestCase {

    private Method createMethodObject(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Method method = null;
        // Only use reg()
        for (Method m : methods) {

            if (m.toString().contains("reg"))
                method = m;
        }

        return method;
    }

    public void testNewInstance() {
        TestAnnotatedObject annotatedObject = new TestAnnotatedObject(AnnotatedTwoArgs.class, createMethodObject(AnnotatedTwoArgs.class));
        Object o = annotatedObject.instance();
        assertTrue(o instanceof AnnotatedTwoArgs);
    }

    public void testBuildArgs_twoArguments() {
        TestAnnotatedObject annotatedObject = new TestAnnotatedObject(AnnotatedTwoArgs.class, createMethodObject(AnnotatedTwoArgs.class));
        Object[] args = annotatedObject.buildArgs(null, "message");

        assertEquals(2, args.length);
    }

    public void testBuildArgs_oneArgument_context() {
        TestAnnotatedObject annotatedObject = new TestAnnotatedObject(AnnotatedOneArg1.class, createMethodObject(AnnotatedOneArg1.class));
        Object[] args = annotatedObject.buildArgs(null, "message");

        assertEquals(1, args.length);
        assertNull(args[0]); // This is the context object, first input argument to buildArgs()
    }

    public void testBuildArgs_oneArgument_registrationId() {
        TestAnnotatedObject annotatedObject = new TestAnnotatedObject(AnnotatedOneArg2.class, createMethodObject(AnnotatedOneArg2.class));
        Object[] args = annotatedObject.buildArgs(null, "registrationId");

        assertEquals(1, args.length);
        assertEquals("registrationId", args[0]);
    }
    


    /**
     * Makes it possible to test the abstract AnnotatedObject class
     */
    private static class TestAnnotatedObject extends AnnotatedObject {

        public TestAnnotatedObject(Class<?> clazz, Method method) {
            super(clazz, method);
        }

        @Override
        public Object instance() {
            return super.instance();
        }

        @Override
        public Object[] buildArgs(Context context, String msg) {
            return super.buildArgs(context, msg);
        }
    }
}
