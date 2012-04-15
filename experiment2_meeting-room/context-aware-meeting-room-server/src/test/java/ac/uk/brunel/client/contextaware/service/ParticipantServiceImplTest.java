package ac.uk.brunel.client.contextaware.service;

import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;
import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.TestBluetoothDeviceImpl;
import ac.uk.brunel.client.contextaware.integration.participant.MeetingUsersRegistration;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 18, 2010
 * Time: 4:22:49 PM
 */
public class ParticipantServiceImplTest {
    private static final String meetingId = "meetingId";
    private ParticipantServiceImpl participantService;

    private MeetingUsersRegistration mockedMeetingUsersRegistration;

    @Before
    public void setup() {
        mockedMeetingUsersRegistration = mock(MeetingUsersRegistration.class);

        participantService = new ParticipantServiceImpl(mockedMeetingUsersRegistration);
    }

    @Test
    public void testRegisterConnectedParticipants() {
        final List<BluetoothDevice> bluetoothDevices = new ArrayList<BluetoothDevice>();
        bluetoothDevices.add(new TestBluetoothDeviceImpl("000000000000", "name"));

        participantService.registerConnectedParticipants(meetingId, bluetoothDevices);

        verify(mockedMeetingUsersRegistration).registerConnectedParticipants(meetingId, bluetoothDevices);
    }

    @Test
    public void testRegisterConnectedParticipantsEmptyList() {
        final List<BluetoothDevice> emptyList = Collections.<BluetoothDevice>emptyList();

        participantService.registerConnectedParticipants(meetingId, emptyList);

        verifyZeroInteractions(mockedMeetingUsersRegistration);
    }
}
