package ac.uk.brunel.server.contextaware.service;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.dto.MeetingNote;
import ac.uk.brunel.server.contextaware.dto.User;
import ac.uk.brunel.server.contextaware.integration.googledocs.PresentationIntegration;
import ac.uk.brunel.server.contextaware.persistence.presentation.PresentationDao;
import ac.uk.brunel.server.contextaware.persistence.user.UserDao;
import ac.uk.brunel.server.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 30, 2010
 * Time: 10:45:05 PM
 */
public class PresentationServiceImplTest {
    private PresentationServiceImpl presentationService;
    private PresentationIntegration mockedPresentationIntegration;
    private UserDao mockedUserDao;
    private PresentationDao mockedPresentationDao;

    private List<String> participants;
    private Meeting meeting;
    private MeetingNote meetingNote;

    @Before
    public void setup() {
        participants = new ArrayList<String>();
        participants.add("hansjar@gmail.com");
        participants.add("brunel.nith@gmail.com");
        meeting = new Meeting("meetingId", "presentationLink", "presenter", participants, "title", "description", "location", "startTime", "endTime");
        meetingNote = new MeetingNote.Builder("meetingId").build();
        meetingNote.setParticipants("123456789012");
        meetingNote.setConnectedParticipants("123456789012");

        mockedPresentationIntegration = mock(PresentationIntegration.class);
        when(mockedPresentationIntegration.createPresentationNoteObject(meeting)).thenReturn(meetingNote);

        mockedUserDao = mock(UserDao.class);
        when(mockedUserDao.findUser(anyString())).thenReturn(new User("", "", "", "123456789012"));
        mockedPresentationDao = mock(PresentationDao.class);
        when(mockedPresentationDao.findMeetingNote(anyString())).thenReturn(meetingNote);

        presentationService = new PresentationServiceImpl(mockedPresentationIntegration, mockedUserDao, mockedPresentationDao);
    }

    @Test
    public void testRegisterMeetingStarted() {
        MeetingNote meetingNote = presentationService.registerStartedMeeting(meeting);

        verify(mockedPresentationDao).persistStartedMeeting(any(MeetingNote.class));
        verify(mockedUserDao, times(2)).findUser(anyString());
        assertTrue(meetingNote.getParticipants().contains("123456789012"));
        assertTrue(meetingNote.getParticipants().contains(PropertiesReader.SERVER_APP.get(PropertiesConstants.BLUETOOTH_ADDRESS_ALWAYS_ACCEPT)));
    }

    @Test
    public void testUpdateCurrentSlideNumber() {
        presentationService.updateCurrentSlideNumber("meetingId", 5);

        verify(mockedPresentationDao).persistStartedMeeting(any(MeetingNote.class));
    }

    @Test
    public void testGetCurrentSlideNote() {
        Map<Integer, String> noteMap = new HashMap<Integer, String>();
        noteMap.put(1, "presentation note 1");
        meetingNote = new MeetingNote.Builder("meetingId").setMessages(noteMap).build();
        meetingNote.setCurrentSlideNumber(1);
        meetingNote.setParticipants("123456789012");
        meetingNote.setConnectedParticipants("123456789012");
        when(mockedPresentationDao.findMeetingNote("meetingId")).thenReturn(meetingNote);

        String note = presentationService.getCurrentSlideNote("meetingId", "123456789012");

        verify(mockedPresentationDao).findMeetingNote("meetingId");
        assertEquals("presentation note 1", note);
    }

    @Test
    public void testGetCurrentSlideNoteNoParticipants() {
        Map<Integer, String> noteMap = new HashMap<Integer, String>();
        noteMap.put(1, "presentation note 1");
        meetingNote = new MeetingNote.Builder("meetingId").setMessages(noteMap).build();
        meetingNote.setCurrentSlideNumber(1);
        when(mockedPresentationDao.findMeetingNote("meetingId")).thenReturn(meetingNote);

        String note = presentationService.getCurrentSlideNote("meetingId", "123456789012");

        verify(mockedPresentationDao).findMeetingNote("meetingId");
        assertTrue(note.contains("Could not retrieve note for meeting"));
    }

    @Test
    public void testGetCurrentSlideNoteNoMessage() {
        String note = presentationService.getCurrentSlideNote("meetingId", "123456789012");

        verify(mockedPresentationDao).findMeetingNote("meetingId");
        assertTrue(note.length() == 0);
    }

    @Test
    public void testFindUserBluetoothAddress() {
        when(mockedUserDao.findUser("hansjar@gmail.com")).thenReturn(new User("hansjar@gmail.com", "firstName", "lastName", "123456789012"));

        String btAddress = presentationService.findUserBluetoothAddress("hansjar@gmail.com");

        assertEquals("123456789012", btAddress);
    }

    @Test
    public void testFindUserBluetoothAddressNoUserFound() {
        when(mockedUserDao.findUser("hansjar@gmail.com")).thenReturn(null);

        String btAddress = presentationService.findUserBluetoothAddress("hansjar@gmail.com");

        assertEquals(null, btAddress);
    }

    @Test
    public void testRegisterConnectedUser() {
        presentationService.registerConnectedUser("meetingId", "000000000000");

        verify(mockedPresentationDao).findMeetingNote("meetingId");
        verify(mockedPresentationDao).persistStartedMeeting(any(MeetingNote.class));
    }
}
