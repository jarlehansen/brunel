package ac.uk.brunel.server.contextaware.persistence.wrapper;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 22, 2010
 * Time: 11:34:17 PM
 */
public class MeetingJpaWrapperTest {
    private Meeting meeting;
    private MeetingJpaWrapper meetingJpaWrapper;

    private final String participant1 = "participant1";
    private final String participant2 = "participant2";

    @Before
    public void setup() {
        List<String> participants = new ArrayList<String>();
        participants.add(participant1);
        participants.add(participant2);

        meeting = new Meeting("meetingId", "presentationLink", "presenter", participants, "title", "description", "location", "startTime", "endTime");
        meetingJpaWrapper = new MeetingJpaWrapper(meeting);
    }

    @Test
    public void testConstructorInput() {
        assertEquals(participant1 + ";" + participant2 + ";", meetingJpaWrapper.getParticipantsString());
    }

    @Test
    public void testGetMeeting() {
        assertReflectionEquals(meeting, meetingJpaWrapper.getMeeting());
    }

    @Test
    public void testGetMeetingNoTokens() {
        meeting = new Meeting("meetingId", "presentationLink", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");
        meetingJpaWrapper = new MeetingJpaWrapper(meeting);
        
        assertTrue(meetingJpaWrapper.getMeeting().getParticipants().size() == 0);
    }
}
