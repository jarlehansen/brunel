package ac.uk.brunel.server.contextaware.integration.googledocs;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.dto.MeetingNote;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 30, 2010
 * Time: 2:09:59 PM
 */
public class PresentationIntegrationImplIntegrationTest {
    private static final String presentationLink = "http://docs.google.com/present/view?id=dg76hx25_67ghmb28vb";
    private Meeting meeting;

    @Before
    public void setup() {
        meeting = new Meeting("meetingId", presentationLink, "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");
    }

    @Test
    public void testPopulateMeetingPresentationNotes() {
        PresentationIntegration presentationIntegration = new PresentationIntegrationImpl();
        MeetingNote meetingNote = presentationIntegration.createPresentationNoteObject(meeting);

        for(Map.Entry<Integer, String> entry : meetingNote.getMessages().entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }
}
