package ac.uk.brunel.mobile.contextaware.business;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import ac.uk.brunel.mobile.contextaware.dto.Meeting;
import ac.uk.brunel.mobile.contextaware.persistence.MeetingHistoryDao;


public class MeetingHistoryImplTest {
	private Vector<String> notes;
	private Meeting meeting;
	
	private MeetingHistoryImpl meetingHistory;
	
	private MeetingHistoryDao mockedMeetingHistoryDao;
	
	@Before
	public void setup() {
		notes = new Vector<String>();
		meeting = new Meeting("meetingId", notes);
		
		mockedMeetingHistoryDao = mock(MeetingHistoryDao.class);
		
		meetingHistory = new MeetingHistoryImpl(mockedMeetingHistoryDao);
	}
	
	@Test
	public void testAddNote() {
		notes.add("note1");
		
		meetingHistory.addNote("meetingId", "note1");
		
		assertReflectionEquals(meeting, meetingHistory.currentMeeting);
		assertEquals(0, meetingHistory.historySlideNumber);
		assertEquals("note1", meetingHistory.meetingNotes.get(0));
	}
	
	@Test
	public void testAddNoteSecondValue() {
		notes.add("note1");
		
		meetingHistory.addNote("meetingId", "note1");
		meetingHistory.addNote("meetingId", "note2");
		
		assertEquals(1, meetingHistory.historySlideNumber);
		assertEquals(2, meetingHistory.meetingNotes.size());
		assertEquals("note2", meetingHistory.meetingNotes.get(1));
	}
	
	@Test
	public void testGetPreviousNote() {
		notes.add("note1");
		notes.add("note2");
		meetingHistory.meetingNotes = notes;
		meetingHistory.historySlideNumber = 0;
		
		String prevNote = meetingHistory.getPreviousNote();
		
		assertEquals("note1", prevNote);
		assertEquals(0, meetingHistory.historySlideNumber);
	}
	
	@Test
	public void testGetPreviousNoteNoValue() {
		meetingHistory.historySlideNumber = 0;
		
		String prevNote = meetingHistory.getPreviousNote();
		
		assertEquals(null, prevNote);
		assertEquals(0, meetingHistory.historySlideNumber);
	}
	
	@Test
	public void testGetNextNote() {
		notes.add("note1");
		notes.add("note2");
		meetingHistory.meetingNotes = notes;
		meetingHistory.historySlideNumber = 0;
		
		String nextNote = meetingHistory.getNextNote();
		
		assertEquals("note2", nextNote);
		assertEquals(1, meetingHistory.historySlideNumber);
	}
	
	@Test
	public void testGetNextNoteNoValue() {
		meetingHistory.historySlideNumber = 1;
		
		String nextNote = meetingHistory.getNextNote();
		
		assertEquals(null, nextNote);
		assertEquals(1, meetingHistory.historySlideNumber);
	}
	
	@Test
	public void testGetPreviousMeetings() {
		meetingHistory.getPreviousMeetings();
		
		verify(mockedMeetingHistoryDao).getAllPersistedMeetings();
	}
	
	@Test
	public void testCloseRecordStore() {
		meetingHistory.closeRecordStore();
		
		verify(mockedMeetingHistoryDao).closeRecordStore();
	}
	
	@Test
	public void testResetCurrentMeetingHistory() {
		meetingHistory.saveAndResetCurrentMeetingHistory();
		
		verify(mockedMeetingHistoryDao).saveMeetingHistory(any(Meeting.class));
	}
}
