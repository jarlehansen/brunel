package ac.uk.brunel.client.contextaware.integration.meeting;

import ac.uk.brunel.client.contextaware.dto.Meeting;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 12, 2010
 * Time: 4:11:05 PM
 */
public class MeetingRegistrationImplIntegrationTest {
    private MeetingRegistrationImpl meetingRegistration;

    @Before
    public void setup() {
        meetingRegistration = new MeetingRegistrationImpl(new DefaultHttpClient());
    }

    @Test
    public void testRegisterMeeting() {
        List<String> participants = new ArrayList<String>();
        participants.add("brunel.nith@gmail.com");
        participants.add("hansjar@gmail.com");

        Meeting meeting = new Meeting("meetingId", "http://www.vg.no", "presenter", participants, "title", "description", "location", "startTime", "endTime");
        meetingRegistration.registerMeetingAndGetRegisteredParticipants(meeting);
    }
}
