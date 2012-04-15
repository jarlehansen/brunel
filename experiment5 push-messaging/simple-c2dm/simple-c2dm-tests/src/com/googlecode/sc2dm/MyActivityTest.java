package com.googlecode.sc2dm;

import android.test.ActivityInstrumentationTestCase2;
import com.googlecode.sc2dm.activity.AnnotatedMessagesActivity;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.googlecode.sc2dm.MyActivityTest \
 * com.googlecode.sc2dm.tests/android.test.InstrumentationTestRunner
 */
public class MyActivityTest extends ActivityInstrumentationTestCase2<AnnotatedMessagesActivity> {

    public MyActivityTest() {
        super("com.googlecode.sc2dm.activity", AnnotatedMessagesActivity.class);
    }

    public void test() {
    }
}
