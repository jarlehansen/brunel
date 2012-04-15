package ac.uk.brunel.mobile.contextaware.presentation.facade;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.util.Date;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import ac.uk.brunel.mobile.contextaware.dto.Meeting;
import ac.uk.brunel.mobile.contextaware.presentation.dto.PresentationMeeting;

public class PresentationObjectFactoryTest {
	private Vector<String> meetingNotes;
	private Vector<Meeting> meetingList;
	private Vector<PresentationMeeting> presentationList;
	
	@Before
	public void setup() {
		meetingNotes = new Vector<String>();
		meetingNotes.add("note1");
		meetingNotes.add("note2");
		meetingNotes.add("note3");
		meetingNotes.add("note4");
		meetingNotes.add("note5");
		
		Meeting meeting = new Meeting("meetingId", meetingNotes, new Date());
		meetingList = new Vector<Meeting>();
		meetingList.add(meeting);
		meetingList.add(meeting);
		
		PresentationMeeting presentationMeeting = new PresentationMeeting("meetingId", meetingNotes, new Date());
		presentationList = new Vector<PresentationMeeting>();
		presentationList.add(presentationMeeting);
		presentationList.add(presentationMeeting);
	}
	
	@Test
	public void testCreatePresentationMeetingList() {
		@SuppressWarnings("unchecked")
		Vector<PresentationMeeting> returnedPresentationList = PresentationObjectFactory.createPresentationMeetingList(meetingList);
		
		assertReflectionEquals(presentationList, returnedPresentationList);
	}
}
