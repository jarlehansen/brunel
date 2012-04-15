package ac.uk.brunel.client.contextaware.integration.participant;

import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;
import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.TestBluetoothDeviceImpl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 18, 2010
 * Time: 4:34:13 PM
 */
public class MeetingUsersRegistrationImplTest {
    private MeetingUsersRegistrationImpl meetingUsersRegistration;

    private HttpClient mockedClient;
    private HttpResponse mockedResponse;
    private HttpEntity mockedEntity;

    @Before
    public void setup() throws IOException {
        mockedClient = mock(HttpClient.class);
        mockedResponse = mock(HttpResponse.class);
        mockedEntity = mock(HttpEntity.class);
        when(mockedClient.execute(any(HttpPost.class))).thenReturn(mockedResponse);
        when(mockedResponse.getEntity()).thenReturn(mockedEntity);


        meetingUsersRegistration = new MeetingUsersRegistrationImpl(mockedClient);
    }


    @Test
    public void testRegisterConnectedParticipants() throws IOException {
        final List<BluetoothDevice> bluetoothDevices = new ArrayList<BluetoothDevice>();
        bluetoothDevices.add(new TestBluetoothDeviceImpl("000000000000", "name"));

        meetingUsersRegistration.registerConnectedParticipants("meetingId", bluetoothDevices);

        verify(mockedClient).execute(any(HttpPost.class));
        verify(mockedResponse).getEntity();
        verify(mockedEntity).consumeContent();
    }

    @Test
    public void testRegisterConnectedParticipantsEmptyList() throws IOException {
        final List<BluetoothDevice> emptyList = Collections.<BluetoothDevice>emptyList();

        meetingUsersRegistration.registerConnectedParticipants("meetingId", emptyList);

        verify(mockedClient).execute(any(HttpPost.class));
        verify(mockedResponse).getEntity();
        verify(mockedEntity).consumeContent();
    }
}
