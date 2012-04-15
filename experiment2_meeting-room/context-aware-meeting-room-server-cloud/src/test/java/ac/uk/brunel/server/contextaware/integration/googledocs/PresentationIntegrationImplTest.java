package ac.uk.brunel.server.contextaware.integration.googledocs;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.dto.MeetingNote;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 30, 2010
 * Time: 8:28:09 PM
 */
public class PresentationIntegrationImplTest {
    private PresentationIntegrationImpl presentationIntegration;

    @Before
    public void setup() {
        presentationIntegration = new PresentationIntegrationImpl();
    }

    @Test
    public void testPopulateMeetingPresentationNotesInvalidPresentationLink() {
        final Meeting meeting = new Meeting("meetingId", "presentationLink", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");
        MeetingNote meetingNote = presentationIntegration.createPresentationNoteObject(meeting);

        assertTrue(meetingNote.getMessages().size() == 0);
    }

    @Test
    public void testPopulateMeetingPresentationNotesEmptyPresentationLink() {
        final Meeting meeting = new Meeting("meetingId", "", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");
        MeetingNote meetingNote = presentationIntegration.createPresentationNoteObject(meeting);

        assertTrue(meetingNote.getMessages().size() == 0);
    }

    @Test
    public void testPopulateMeetingPresentationNotesNullPresentationLink() {
        final Meeting meeting = new Meeting("meetingId", null, "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");
        MeetingNote meetingNote = presentationIntegration.createPresentationNoteObject(meeting);

        assertTrue(meetingNote.getMessages().size() == 0);
    }

    @Test
    public void testParseAndAppendPresentationNotesValidInput() {
        final Meeting meeting = new Meeting("meetingId", "", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");
        final String testInput = "{\"attributes\":{\"title\":\"test-presentation\",\"theme\":\"blank\",\"revision\":\"" +
                "dg76hx25_67ghmb28vb:22\",\"cancelRevision\":\"dg76hx25_67ghmb28vb:22\",\"lastEdited\":\"edited on 30. " +
                "januar 2010 19.37 by Jarle Hansen\",\"blobs\":{}},\"children\":{\"a_chzkbjw_0\":{\"attributes\":{\"simpl" +
                "eSpeakerNotes\":\"<div dir=\\\"ltr\\\"><div dir=\\\"ltr\\\"><div dir=\\\"ltr\\\">&lt;shared-notes " +
                "id=1&gt;presentationNote1&lt;\\/shared-notes&gt;<\\/div><\\/div><\\/div>\",\"layout\"" +
                ":\"LAYOUT_TITLE_SLIDE\"},\"children\":{\"a_chzkbjw_1\":{\"attributes\":{\"contents\":\"<div dir=\\\"lt" +
                "r\\\"><div>Test presentation<br clear=\\\"none\\\"><\\/div><\\/div>\",\"contentType\":\"text\",\"type\"" +
                ":\"centeredTitle\",\"fontSize\":1.8},\"action\":\"create\",\"index\":0},\"a_chzkbjw_2\":{\"attributes\"" +
                ":{\"contentType\":\"text\",\"type\":\"subtitle\",\"fontSize\":1.2},\"action\":\"create\",\"index\":1}}" +
                ",\"action\":\"create\",\"index\":0},\"a_d5s2md7_0\":{\"attributes\":{\"simpleSpeakerNotes\":\"<div dir=" +
                "\\\"ltr\\\"><div>&lt;shared-notes id=2&gt;presentationNote2&lt;\\/shared-notes&gt;<\\/div><\\/div>\",\"" +
                "layout\":\"LAYOUT_TITLE_SLIDE\"},\"children\":{\"a_d5s2md7_1\":{\"attributes\":{\"bounds\":[8.993399620" +
                "056152,39.97797393798828,82.01319885253906,18.612335205078125],\"minHeight\":15.969162995594713,\"cont" +
                "ents\":\"<div dir=\\\"ltr\\\"><div>Tesst 2<\\/div><div><br clear=\\\"none\\\"><\\/div><\\/div>\",\"cont" +
                "entType\":\"text\",\"fontSize\":1.8,\"type\":\"centeredTitle\"},\"action\":\"create\",\"index\":0},\"a" +
                "_d5s2md7_2\":{\"attributes\":{\"contents\":\" \",\"contentType\":\"text\",\"fontSize\":1.2,\"type\":\"s" +
                "ubtitle\"},\"action\":\"create\",\"index\":1}},\"action\":\"create\",\"index\":1},\"a_dhn5hx8_0\":{\"att" +
                "ributes\":{\"simpleSpeakerNotes\":\"<div dir=\\\"ltr\\\"><div dir=\\\"ltr\\\"><div>&lt;shared-notes id=" +
                "3&gt;presentationNote3&lt;\\/shared-notes&gt;<\\/div><\\/div><\\/div>\",\"layout\":\"LAYOUT_TITLE_BODY_SL" +
                "IDE\"},\"children\":{\"a_dhn5hx8_1\":{\"attributes\":{\"contents\":\"<div dir=\\\"ltr\\\"><div>asdasdasd<b" +
                "r clear=\\\"none\\\"><\\/div><\\/div>\",\"contentType\":\"text\",\"fontSize\":1.6,\"type\":\"title\"},\"act" +
                "ion\":\"create\",\"index\":0},\"a_dhn5hx8_2\":{\"attributes\":{\"contents\":\" \",\"contentType\":\"text\"" +
                ",\"type\":\"body\"},\"action\":\"create\",\"index\":1}},\"action\":\"create\",\"index\":2},\"a_dhn5hx8_3\"" +
                ":{\"attributes\":{\"simpleSpeakerNotes\":\"<div dir=\\\"ltr\\\"><div>&lt;shared-notes id=4&gt;" +
                "presentationNote4&lt;\\/shared-notes&gt;<\\/div><\\/div>\",\"layout\":\"LAYOUT_TITLE_BODY_SLIDE\"},\"chil" +
                "dren\":{\"a_dhn5hx8_4\":{\"attributes\":{\"contents\":\"<div dir=\\\"ltr\\\"><div>asdasd<br clear=\\\"non" +
                "e\\\"><\\/div><\\/div>\",\"contentType\":\"text\",\"fontSize\":1.6,\"type\":\"title\"},\"action\":\"creat" +
                "e\",\"index\":0},\"a_dhn5hx8_5\":{\"attributes\":{\"contents\":\" \",\"contentType\":\"text\",\"type\":\"" +
                "body\"},\"action\":\"create\",\"index\":1}},\"action\":\"create\",\"index\":3},\"a_dhn5hx8_6\":{\"attribut" +
                "es\":{\"simpleSpeakerNotes\":\"<div dir=\\\"ltr\\\"><div>&lt;shared-notes id=5&gt;presentationNote5&lt;\\/sha" +
                "red-notes&gt;<\\/div><\\/div>\",\"layout\":\"LAYOUT_TITLE_BODY_SLIDE\"},\"children\":{\"a_dhn5hx8_7\":{\"attr" +
                "ibutes\":{\"contents\":\"<div dir=\\\"ltr\\\"><div>asdasd<br clear=\\\"none\\\"><\\/div><\\/div>\",\"contentT" +
                "ype\":\"text\",\"fontSize\":1.6,\"type\":\"title\"},\"action\":\"create\",\"index\":0},\"a_dhn5hx8_8\":{\"a" +
                "ttributes\":{\"contents\":\" \",\"contentType\":\"text\",\"type\":\"body\"},\"action\":\"create\",\"index\"" +
                ":1}},\"action\":\"create\",\"index\":4},\"a_f6b866p_0\":{\"attributes\":{\"simpleSpeakerNotes\":\"<div dir=" +
                "\\\"ltr\\\">asdasdasdsadad<br clear=\\\"none\\\"><div>&lt;shared-notes id=6&gt;presentationNote6&lt;\\/share" +
                "d-notes&gt;<\\/div><div>hipp hipph ihkasgepoksa <br clear=\\\"none\\\"><\\/div><\\/div>\",\"layout\":\"LAYOUT" +
                "_TITLE_BODY_SLIDE\"},\"children\":{\"a_f6b866p_1\":{\"attributes\":{\"contents\":\"<div dir=\\\"ltr\\\"><div>as" +
                "dasdsad<br clear=\\\"none\\\"><\\/div><\\/div>\",\"contentType\":\"text\",\"type\":\"title\",\"fontSize\":1.6}," +
                "\"action\":\"create\",\"index\":0},\"a_f6b866p_2\":{\"attributes\":{\"contents\":\" \",\"contentType\":\"text\"," +
                "\"type\":\"body\"},\"action\":\"create\",\"index\":1}},\"action\":\"create\",\"index\":5}},\"protocol\":2},";

        presentationIntegration.createPresentationNoteObject(meeting);
        presentationIntegration.parseAndAppendPresentationNotes(testInput);
        MeetingNote meetingNote = presentationIntegration.getMeetingNote();

        assertTrue(meetingNote.getMessages().size() == 6);



        assertEquals("presentationNote1", meetingNote.getMessages().get(1));
        assertEquals("presentationNote2", meetingNote.getMessages().get(2));
        assertEquals("presentationNote3", meetingNote.getMessages().get(3));
        assertEquals("presentationNote4", meetingNote.getMessages().get(4));
        assertEquals("presentationNote5", meetingNote.getMessages().get(5));
        assertEquals("presentationNote6", meetingNote.getMessages().get(6));
    }

    @Test
    public void testParseAndAppendPresentationNotesInvalidInput() {
        final Meeting meeting = new Meeting("meetingId", "", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");
        final String testInput = "                  {\"attributes\":{\"title\":\"test-presentation2\",\"theme\":\"blank" +
                "\",\"revision\":\"dg76hx25_68f28qb7c4:6\",\"cancelRevision\":\"dg76hx25_68f28qb7c4:6\",\"lastEdited\":" +
                "\"edited on 30. januar 2010 19.49 by Jarle Hansen\",\"blobs\":{}},\"children\":{\"a_j8b3k_0\":{\"attrib" +
                "utes\":{\"simpleSpeakerNotes\":\"<div dir=\\\"ltr\\\">&lt;shared-notes&gt;presentationNote1&lt;\\/share" +
                "d-notes&gt;<br clear=\\\"none\\\"><\\/div>\",\"layout\":\"LAYOUT_TITLE_SLIDE\"},\"children\":{\"a_j8b3k_1\"" +
                ":{\"attributes\":{\"contents\":\"<div dir=\\\"ltr\\\"><div>test<br clear=\\\"none\\\"><\\/div><\\/div>\",\"" +
                "contentType\":\"text\",\"type\":\"centeredTitle\",\"fontSize\":1.8},\"action\":\"create\",\"index\":0},\"a" +
                "_j8b3k_2\":{\"attributes\":{\"contentType\":\"text\",\"type\":\"subtitle\",\"fontSize\":1.2},\"action\":\"" +
                "create\",\"index\":1}},\"action\":\"create\",\"index\":0},\"a_g8nb58n_0\":{\"attributes\":{\"simpleSpeaker" +
                "Notes\":\"<div dir=\\\"ltr\\\">&lt;shared-notes id=3&gt;presentationNotes3&lt;\\/shared-notes&gt;<br clear=\\" +
                "\"none\\\"><\\/div>\",\"layout\":\"LAYOUT_TITLE_BODY_SLIDE\"},\"children\":{\"a_g8nb58n_1\":{\"attributes\":" +
                "{\"contents\":\"<div dir=\\\"ltr\\\">asfasf<\\/div>\",\"contentType\":\"text\",\"fontSize\":1.6,\"type\":\"ti" +
                "tle\"},\"action\":\"create\",\"index\":0},\"a_g8nb58n_2\":{\"attributes\":{\"contents\":\" \",\"contentType\"" +
                ":\"text\",\"type\":\"body\"},\"action\":\"create\",\"index\":1}},\"action\":\"create\",\"index\":1},\"a_j8b3k" +
                "_3\":{\"attributes\":{\"simpleSpeakerNotes\":\"<div dir=\\\"ltr\\\"><div>presentationNotes2<br clear=\\\"none" +
                "\\\"><\\/div><\\/div>\",\"layout\":\"LAYOUT_TITLE_BODY_SLIDE\"},\"children\":{\"a_j8b3k_4\":{\"attributes\":{\"" +
                "bounds\":[2.97029709815979,3.9647576808929443,93.97689819335938,16.51982307434082],\"minHeight\":12.00440528634" +
                "3612,\"contents\":\"<div dir=\\\"ltr\\\">setsetsets<div><br clear=\\\"none\\\"><\\/div><\\/div>\",\"contentType" +
                "\":\"text\",\"type\":\"title\",\"fontSize\":1.6},\"action\":\"create\",\"index\":0},\"a_j8b3k_5\":{\"attributes\"" +
                ":{\"contents\":\" \",\"contentType\":\"text\",\"type\":\"body\"},\"action\":\"create\",\"index\":1}},\"action\":" +
                "\"create\",\"index\":2}},\"protocol\":2},";

        presentationIntegration.createPresentationNoteObject(meeting);
        presentationIntegration.parseAndAppendPresentationNotes(testInput);
        MeetingNote meetingNote = presentationIntegration.getMeetingNote();

        assertTrue(meetingNote.getMessages().size() == 1);
    }
}
