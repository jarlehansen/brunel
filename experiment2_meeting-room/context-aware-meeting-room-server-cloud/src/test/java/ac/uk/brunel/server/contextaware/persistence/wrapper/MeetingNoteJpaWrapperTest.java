package ac.uk.brunel.server.contextaware.persistence.wrapper;

import ac.uk.brunel.server.contextaware.dto.MeetingNote;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 31, 2010
 * Time: 4:26:45 PM
 */
public class MeetingNoteJpaWrapperTest {
    private MeetingNote emptyMeetingNote;
    private MeetingNote inputMeetingNote;

    @Before
    public void setup() {
        emptyMeetingNote = new MeetingNote.Builder("meetingId").build();

        Map<Integer, String> messagesMap = new HashMap<Integer, String>();
        messagesMap.put(1, "presentationNote1");
        messagesMap.put(2, "presentationNote2");
        inputMeetingNote = new MeetingNote.Builder("meetingId").setMessages(messagesMap).build();
    }

    @Test
    public void testConstructorInputEmptyMap() {
        MeetingNoteJpaWrapper meetingNoteJpaWrapper = new MeetingNoteJpaWrapper(emptyMeetingNote);

        assertTrue(meetingNoteJpaWrapper.getMessagesString().length() == 0);
    }

    @Test
    public void testConstructorInputMap() {
        MeetingNoteJpaWrapper meetingNoteJpaWrapper = new MeetingNoteJpaWrapper(inputMeetingNote);

        assertTrue(meetingNoteJpaWrapper.getMessagesString().length() > 0);
        assertEquals("1,presentationNote1;2,presentationNote2;", meetingNoteJpaWrapper.getMessagesString());
    }

    @Test
    public void getMeetingNoteEmptyMap() {
        MeetingNoteJpaWrapper meetingNoteJpaWrapper = new MeetingNoteJpaWrapper(emptyMeetingNote);
        MeetingNote meetingNote = meetingNoteJpaWrapper.getMeetingNote();

        assertTrue(meetingNote.getMessages().size() == 0);
    }

    @Test
    public void getMeetingNoteMap() {
        MeetingNoteJpaWrapper meetingNoteJpaWrapper = new MeetingNoteJpaWrapper(inputMeetingNote);
        MeetingNote meetingNote = meetingNoteJpaWrapper.getMeetingNote();

        assertTrue(meetingNote.getMessages().size() == 2);
        assertEquals("presentationNote1", meetingNote.getMessages().get(1));
        assertEquals("presentationNote2", meetingNote.getMessages().get(2));
    }
}
