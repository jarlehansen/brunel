package com.googlecode.sc2dm.log;

import junit.framework.TestCase;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 12:11 PM - 10/6/11
 */
public class SC2DMLoggerTest extends TestCase {

    public void testAppendMessages() {
        String msg = SC2DMLogger.mergeMessages("string1 ", "string2 ", "string3");

        assertEquals(msg, "string1 string2 string3");
    }
}
