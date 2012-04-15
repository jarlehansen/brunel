package com.googlecode.sc2dm.server.registration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:31 PM - 9/23/11
 */
@RunWith(MockitoJUnitRunner.class)
public class RegIdReceiverTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    public void createPushDevice_nullRequestValues() {
        when(request.getParameter("email")).thenReturn(null);
        when(request.getParameter("regId")).thenReturn(null);

        PushDevice pushDevice = RegIdReceiver.createPushDevice(request);

        assertEquals("", pushDevice.getEmail());
        assertEquals("", pushDevice.getRegistrationId());
    }

    @Test
    public void createPushDevice_validRequestValues() {
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("regId")).thenReturn("test-regid");

        PushDevice pushDevice = RegIdReceiver.createPushDevice(request);

        assertEquals("test@test.com", pushDevice.getEmail());
        assertEquals("test-regid", pushDevice.getRegistrationId());
    }
}
