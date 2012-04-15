package ac.uk.brunel.mobile.contextaware.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.util.Date;
import java.util.Vector;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

import org.junit.Before;
import org.junit.Test;

import ac.uk.brunel.mobile.contextaware.dto.Meeting;

public class MeetingHistoryDaoImplTest {
	private Vector<String> meetingNotes;
	private Meeting meeting;

	private RecordStore mockedRecordStore;
	private MeetingHistoryDaoImpl meetingHistoryDao;

	@Before
	public void setup() {
		meetingNotes = new Vector<String>();
		meetingNotes.add("note1");
		meetingNotes.add("note2");
		meetingNotes.add("note3");
		meetingNotes.add("note4");
		meetingNotes.add("note5");
		meeting = new Meeting("meetingId", meetingNotes, new Date());

		mockedRecordStore = mock(RecordStore.class);
		meetingHistoryDao = new MeetingHistoryDaoImpl(mockedRecordStore);
	}

	@Test
	public void testSaveMeetingHistory() throws RecordStoreException {
		when(mockedRecordStore.addRecord(any(byte[].class), anyInt(), anyInt())).thenReturn(1);

		meetingHistoryDao.saveMeetingHistory(meeting);

		verify(mockedRecordStore).addRecord(any(byte[].class), anyInt(), anyInt());
	}
	
	@Test
	public void testGetAllPersistedMeetings() throws RecordStoreException {
		final StringBuilder meetingBuilder = new StringBuilder();
		meetingBuilder.append("meetingId").append(";");
		meetingBuilder.append(new Date().getTime()).append(";");
		
		for(String note : meetingNotes) {
			meetingBuilder.append(note).append(";");
		}
		
		RecordEnumeration mockedRecordEnumeration = mock(RecordEnumeration.class);
		when(mockedRecordEnumeration.hasNextElement()).thenReturn(true, false);
		when(mockedRecordEnumeration.nextRecord()).thenReturn(meetingBuilder.toString().getBytes());
		when(mockedRecordStore.enumerateRecords(null, null, false)).thenReturn(mockedRecordEnumeration);
		
		@SuppressWarnings("unchecked")
		Vector<Meeting> meetings = meetingHistoryDao.getAllPersistedMeetings();
		Meeting returnedMeeting = meetings.get(0);
		
		assertReflectionEquals(meeting, returnedMeeting);
	}
	
	@Test
	public void testCreateMeetingNoTokens() {
		String testMeetingString = "no tokens";
		
		Meeting returnedMeeting = meetingHistoryDao.createMeeting(testMeetingString);
		assertEquals("no tokens", returnedMeeting.getMeetingId());
		assertEquals(0, returnedMeeting.getMeetingNotes().size());
	}
}
