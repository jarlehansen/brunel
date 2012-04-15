package ac.uk.brunel.server.contextaware.transport.servlet.factory;

import ac.uk.brunel.contextaware.network.generated.MeetingProtobuf;
import ac.uk.brunel.server.contextaware.dto.Meeting;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 8:57:10 PM
 */
public class ProtobufObjectFactoryTest {
    private List<Meeting> dtoMeetingList;

    @Before
    public void setup() {
        dtoMeetingList = new ArrayList<Meeting>();
        dtoMeetingList.add(new Meeting("1", "presentationLink", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime"));
        dtoMeetingList.add(new Meeting("2", "presentationLink", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime"));
        dtoMeetingList.add(new Meeting("3", "presentationLink", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime"));
    }

    @Test
    public void testCreateProtoMeetingList() {
        MeetingProtobuf.MeetingList protoMeetingList = ProtobufObjectFactory.createProtoMeetingList(dtoMeetingList);

        assertEquals("1", protoMeetingList.getMeeting(0).getMeetingId());
        assertEquals("2", protoMeetingList.getMeeting(1).getMeetingId());
        assertEquals("3", protoMeetingList.getMeeting(2).getMeetingId());
        assertEquals("presenter", protoMeetingList.getMeeting(0).getPresenter());
        assertEquals("startTime", protoMeetingList.getMeeting(0).getStartTime());
    }
}
