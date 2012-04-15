package ac.uk.brunel.server.contextaware.dto;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 22, 2010
 * Time: 10:09:17 PM
 */
public class MeetingTest {

    @Test
    public void testStringInputMeetingObject() {
        final String expectedMeetingId = "meetingId";
        final Meeting meeting = new Meeting(expectedMeetingId, "presentationLink", "presenter", new ArrayList<String>(), "title", "location", "description", "startTime", "endTime");
        assertEquals(expectedMeetingId, meeting.getMeetingId());
    }

    @Test
    public void testNullInputMeetingObject() {
        final Meeting meeting = new Meeting(null, "presentationLink", "presenter", new ArrayList<String>(), "title", "description", "location","startTime", "endTime");
        assertEquals("", meeting.getMeetingId());
    }

    @Test
    public void testNullListInputMeetingObject() {
        final Meeting meeting = new Meeting("meetingId", "presentationLink", "presenter", null, "title", "description", "location", "startTime", "endTime");
        assertEquals(0, meeting.getParticipants().size());
    }
}
