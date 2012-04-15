package ac.uk.brunel.client.contextaware.service;

import ac.uk.brunel.client.contextaware.dto.Meeting;
import ac.uk.brunel.client.contextaware.integration.bluetooth.ping.BluetoothDevicePing;
import ac.uk.brunel.client.contextaware.integration.meeting.MeetingRegistration;
import ac.uk.brunel.client.contextaware.integration.meeting.PresenterMeetingListReceiver;
import ac.uk.brunel.client.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.client.contextaware.properties.PropertiesReader;
import ac.uk.brunel.server.contextaware.presentation.swing.dto.PresentationMeeting;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 12, 2010
 * Time: 3:00:26 PM
 */
public class MeetingServiceImplTest {
    private MeetingServiceImpl meetingService;
    private PresenterMeetingListReceiver mockedPresenterMeetingListReceiver;
    private MeetingRegistration mockedMeetingRegistration;
    private BluetoothDevicePing mockedBluetoothDevicePing;
    private MeetingStateHandler mockedMeetingStateHandler;
    private Meeting meeting;

    @Before
    public void setup() {
        mockedPresenterMeetingListReceiver = mock(PresenterMeetingListReceiver.class);
        mockedMeetingRegistration = mock(MeetingRegistration.class);
        mockedBluetoothDevicePing = mock(BluetoothDevicePing.class);
        mockedMeetingStateHandler = mock(MeetingStateHandler.class);

        meeting = new Meeting("meetingId", "presentationLink", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");
        List<Meeting> meetingList = new ArrayList<Meeting>();
        meetingList.add(meeting);
        when(mockedPresenterMeetingListReceiver.getPresenterMeetingList("hansjar@gmail.com")).thenReturn(meetingList);

        meetingService = new MeetingServiceImpl(mockedPresenterMeetingListReceiver, mockedMeetingRegistration, mockedBluetoothDevicePing, mockedMeetingStateHandler);
    }

    @Test
    public void testGetPresenterMeetingList() {
        List<PresentationMeeting> presentationMeetings = meetingService.getPresenterMeetingList("hansjar@gmail.com");

        verify(mockedPresenterMeetingListReceiver).getPresenterMeetingList("hansjar@gmail.com");

        assertEquals(meeting.getMeetingId(), presentationMeetings.get(0).getMeetingId());
    }

    @Test
    public void testRegisterMeeting() {
        List<String> registeredParticipants = new ArrayList<String>();
        registeredParticipants.add("000000000000");
        registeredParticipants.add("000000000001");

        PresentationMeeting presentationMeeting = new PresentationMeeting("meetingId", "presentationLink", "presenter",
                new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");
        when(mockedMeetingRegistration.registerMeetingAndGetRegisteredParticipants(any(Meeting.class))).thenReturn(registeredParticipants);

        meetingService.registerMeeting(presentationMeeting);

        verify(mockedMeetingRegistration).registerMeetingAndGetRegisteredParticipants(any(Meeting.class));
        verify(mockedMeetingStateHandler).setStartedMeeting(any(Meeting.class));
        verify(mockedBluetoothDevicePing).startSearch(registeredParticipants);
    }

    @Test
    public void testRemoveAlwaysAcceptedBtAddress() {
        final String alwaysAcceptedBtAddress = PropertiesReader.SERVER_APP.get(PropertiesConstants.BLUETOOTH_ADDRESS_ALWAYS_ACCEPT);
        final List<String> registeredBtAddresses = new ArrayList<String>();
        registeredBtAddresses.add("000000000000");
        registeredBtAddresses.add("000000000123");
        registeredBtAddresses.add("000000000001");
        registeredBtAddresses.add("000000000003");
        registeredBtAddresses.add("000000000004");
        registeredBtAddresses.add(alwaysAcceptedBtAddress);
        registeredBtAddresses.add("000000000008");
        registeredBtAddresses.add("000000000009");
        final List<String> originalList = new ArrayList<String>(registeredBtAddresses);

        List<String> pingRegisteredBtAddresses = meetingService.removeAlwaysAcceptedBtAddress(registeredBtAddresses);

        Assert.assertEquals(originalList.size() - 1, pingRegisteredBtAddresses.size());
        originalList.remove(5);
        ReflectionAssert.assertReflectionEquals(originalList, pingRegisteredBtAddresses);
    }
}
