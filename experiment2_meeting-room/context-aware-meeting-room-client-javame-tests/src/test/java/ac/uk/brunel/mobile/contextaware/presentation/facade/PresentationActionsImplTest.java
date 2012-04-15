package ac.uk.brunel.mobile.contextaware.presentation.facade;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import ac.uk.brunel.mobile.contextaware.dto.Meeting;
import ac.uk.brunel.mobile.contextaware.output.MeetingActions;

public class PresentationActionsImplTest {
	private MeetingActions mockedMeetingActions;
	private PresentationActionsImpl presentationActions;
	
	@Before
	public void setup() {
		Meeting meeting = new Meeting("meetingId", new Vector<String>());
		mockedMeetingActions = mock(MeetingActions.class);
		Vector<Meeting> meetingList = new Vector<Meeting>();
		meetingList.add(meeting);
		
		when(mockedMeetingActions.getPreviousMeetings()).thenReturn(meetingList);
		
		presentationActions = new PresentationActionsImpl();
		presentationActions.setOutputActions(mockedMeetingActions);
	}
	
	@Test
	public void testStartServerCommunication() {
		presentationActions.startServerCommunication();
		
		verify(mockedMeetingActions).startServerCommunication();
	}
	
	@Test
	public void testStopServerCommunication() {
		presentationActions.stopServerCommunication();
		
		verify(mockedMeetingActions).stopServerCommunication();
	}
	
	@Test
	public void testGetNextNote() {
		presentationActions.getNextNote();
		
		verify(mockedMeetingActions).getNextNote();
	}
	
	@Test
	public void testGetPreviousNote() {
		presentationActions.getPreviousNote();
		
		verify(mockedMeetingActions).getPreviousNote();
	}
	
	@Test
	public void testGetPreviousMeetings() {
		presentationActions.getPreviousMeetings();
		
		verify(mockedMeetingActions).getPreviousMeetings();
	}
	
	@Test
	public void testShowInfoException() {
		presentationActions.showInfo();
		
		verify(mockedMeetingActions).getBluetoothAddress();
	}
	
	@Test
	public void testExitApp() {
		presentationActions.exitApp();
		
		verify(mockedMeetingActions).exitApp();
	}
	
	@Test
	public void testSetServerConfig() {
		presentationActions.setServerConfig("serverAddress");
		
		verify(mockedMeetingActions).setServerConfig("serverAddress");
	}
	
	@Test
	public void testGetServerConfig() {
		presentationActions.getServerConfig();
		
		verify(mockedMeetingActions).getServerConfig();
	}
}
