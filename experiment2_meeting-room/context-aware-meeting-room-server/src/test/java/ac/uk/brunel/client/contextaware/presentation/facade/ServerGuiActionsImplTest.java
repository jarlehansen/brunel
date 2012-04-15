package ac.uk.brunel.client.contextaware.presentation.facade;

import ac.uk.brunel.client.contextaware.presentation.callback.BluetoothDeviceCallback;
import ac.uk.brunel.client.contextaware.service.MeetingService;
import ac.uk.brunel.server.contextaware.presentation.swing.dto.PresentationMeeting;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 18, 2010
 * Time: 9:03:26 PM
 */
public class ServerGuiActionsImplTest {
    private MeetingService mockedMeetingService;
    private BluetoothDeviceCallback mockedBluetoothDeviceCallback;

    private ServerGuiActionsImpl serverGuiActions;

    @Before
    public void setup() {
        mockedMeetingService = mock(MeetingService.class);
        mockedBluetoothDeviceCallback = mock(BluetoothDeviceCallback.class);
        when(mockedMeetingService.getPresenterMeetingList(any(String.class))).thenReturn(anyListOf(PresentationMeeting.class));

        serverGuiActions = new ServerGuiActionsImpl(mockedMeetingService, mockedBluetoothDeviceCallback);
    }

    @Test
    public void testGetMeetingList() {
        serverGuiActions.getMeetingList("e-mail");

        verify(mockedMeetingService).getPresenterMeetingList("e-mail");
    }

    @Test
    public void testRegisterMeeting() {
        PresentationMeeting presentationMeeting = new PresentationMeeting("meetingId", "presentationLink", "presenter",
                new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");

        serverGuiActions.registerMeeting(presentationMeeting);

        verify(mockedMeetingService).registerMeeting(presentationMeeting);
        verify(mockedBluetoothDeviceCallback).startBackgroundThread(presentationMeeting.getMeetingId());
    }
}
