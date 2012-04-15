package ac.uk.brunel.mobile.contextaware.business;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ac.uk.brunel.mobile.contextaware.integration.MeetingNoteReader;
import ac.uk.brunel.mobile.contextaware.persistence.ServerAddressDao;
import ac.uk.brunel.mobile.contextaware.presentation.ContextAwareMeetingRoomUi;

public class MeetingControllerTest {
	private static final String serverAddress = "testServerAddress";
	private static final String btAddress = "123456789012";
	
	private MeetingController meetingController;
	
	private ContextAwareMeetingRoomUi mockedContextAwareMeetingRoomUi;
	private MeetingNoteReader mockedMeetingNoteReader;
	private ServerAddressDao mockedServerAddressDao;
	private MeetingHistory mockedMeetingHistory;

	@Before
	public void setup() {
		mockedContextAwareMeetingRoomUi = mock(ContextAwareMeetingRoomUi.class);
		mockedMeetingNoteReader = mock(MeetingNoteReader.class);
		mockedServerAddressDao = mock(ServerAddressDao.class);
		mockedMeetingHistory = mock(MeetingHistory.class);
		
		when(mockedServerAddressDao.getServerAddress()).thenReturn(serverAddress);
		
		meetingController = new MeetingController(btAddress, mockedContextAwareMeetingRoomUi, mockedMeetingNoteReader, mockedServerAddressDao, mockedMeetingHistory);
	}
	
	@Test
	public void testStartNetworkCommunication() {
		meetingController.startServerCommunication();
		
		verify(mockedServerAddressDao).getServerAddress();
		verify(mockedMeetingNoteReader).startServerCommunication(btAddress, serverAddress);
	}
	
	@Test
	public void testStopServerCommunication() {
		meetingController.stopServerCommunication();
		
		verify(mockedMeetingNoteReader).stopCommunication();
		verify(mockedMeetingHistory).saveAndResetCurrentMeetingHistory();
		verify(mockedContextAwareMeetingRoomUi).setScreenText("");
	}
	
	@Test
	public void testSetNote() {
		meetingController.setNote("meetingId", "message");
		
		verify(mockedContextAwareMeetingRoomUi).setScreenText("message");
		verify(mockedMeetingHistory).addNote("meetingId", "message");
	}
	
	@Test
	public void testSetErrorMessage() {
		meetingController.setErrorMessage("");
		
		verify(mockedContextAwareMeetingRoomUi).setErrorMessage("");
	}
	
	@Test
	public void testExitApp() {
		meetingController.exitApp();
		
		verify(mockedServerAddressDao).closeRecordStore();
		verify(mockedMeetingHistory).closeRecordStore();
	}
	
	@Test
	public void testSetServerConfig() {
		meetingController.setServerConfig(serverAddress);
		
		verify(mockedServerAddressDao).saveServerAddress(serverAddress);
	}
	
	@Test
	public void testGetServerConfig() {
		meetingController.getServerConfig();
		
		verify(mockedServerAddressDao).getServerAddress();
	}
	
	@Test
	public void testGetNextNote() {
		meetingController.getNextNote();
		
		verify(mockedMeetingHistory).getNextNote();
	}
	
	@Test
	public void testGetPreviousNote() {
		meetingController.getPreviousNote();
		
		verify(mockedMeetingHistory).getPreviousNote();
	}
	
	@Test
	public void testGetPreviousMeetings() {
		meetingController.getPreviousMeetings();
		
		verify(mockedMeetingHistory).getPreviousMeetings();
	}
}
