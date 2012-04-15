package ac.uk.brunel.client.contextaware.presentation.callback;

import ac.uk.brunel.client.contextaware.integration.bluetooth.BluetoothDeviceHandler;
import ac.uk.brunel.client.contextaware.service.ParticipantService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 18, 2010
 * Time: 5:03:41 PM
 */
public class BluetoothDeviceCallbackImplTest {
    private BluetoothDeviceCallbackImpl bluetoothDeviceCallback;

    private BluetoothDeviceHandler mockedBluetoothDeviceHandler;
    private ParticipantService mockedParticipantService;

    @Before
    public void setup() {
        mockedBluetoothDeviceHandler = mock(BluetoothDeviceHandler.class);
        mockedParticipantService = mock(ParticipantService.class);

        bluetoothDeviceCallback = new BluetoothDeviceCallbackImpl(mockedBluetoothDeviceHandler, mockedParticipantService);
    }

    @Test
    public void testStartBackgroundThreadEmptyMeetingId() {
        final String meetingId = "";

        bluetoothDeviceCallback.startBackgroundThread(meetingId);

        verifyZeroInteractions(mockedBluetoothDeviceHandler, mockedParticipantService);
    }

    @Test
    public void testStartBackgroundThreadDevicePingFinished() {
        final String meetingId = "meetingId";
        when(mockedBluetoothDeviceHandler.isBluetoothDevicePingFinished()).thenReturn(true);

        bluetoothDeviceCallback.startBackgroundThread(meetingId);

        verify(mockedBluetoothDeviceHandler).isBluetoothDevicePingFinished();
    }
}
