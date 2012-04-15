package ac.uk.brunel.mobile.contextaware.integration;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class MeetingNoteReaderImplTest {
	private MeetingNoteReaderImpl meetingNoteReader;
	private NetworkCommunicationUtil mockedNetworkCommunicationUtil;

	@Before
	public void setup() {
		mockedNetworkCommunicationUtil = mock(NetworkCommunicationUtil.class);
		meetingNoteReader = new MeetingNoteReaderImpl(500, mockedNetworkCommunicationUtil);
	}
	
	@Test
	public void testStartServerCommunication() {
		meetingNoteReader.startServerCommunication("bluetoothAddress", "serverAddress");
		
		assertTrue(meetingNoteReader.communicationThread != null);
	}
	
	@Test
	public void testStopCommunication() {
		meetingNoteReader.stopCommunication();
		
		assertTrue(meetingNoteReader.communicationThread == null);
	}
}
