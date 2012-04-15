package ac.uk.brunel.server.contextaware.integration.googledocs;

import ac.uk.brunel.server.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.dto.MeetingNote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 30, 2010
 * Time: 2:09:23 PM
 */
@LoggingAspect
public class PresentationIntegrationImpl implements PresentationIntegration {
    private static final Logger logger = LoggerFactory.getLogger(PresentationIntegrationImpl.class);

    private Meeting meeting = null;
    private MeetingNote.Builder meetingNote = MeetingNote.EMPTY_BUILDER;
    private final List<String> notes;
    private final List<Integer> slideNumbers;

    public PresentationIntegrationImpl() {
        this.notes = new ArrayList<String>();
        this.slideNumbers = new ArrayList<Integer>();
    }

    /**
     * For tests only!!
     */
    MeetingNote getMeetingNote() {
        return meetingNote.build();
    }

    public MeetingNote createPresentationNoteObject(final Meeting meeting) {
        this.meeting = meeting;
        this.meetingNote = new MeetingNote.Builder(meeting.getMeetingId());

        BufferedReader reader = null;
        try {
            final String presentationLink = meeting.getPresentationLink();

            if (presentationLink != null && !"".equals(presentationLink)) {
                final URL url = new URL(presentationLink);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));

                StringBuilder inputPage = new StringBuilder();
                String inputLine;

                while ((inputLine = reader.readLine()) != null) {
                    inputPage.append(inputLine);
                }

                if (inputPage.length() > 0) {
                    parseAndAppendPresentationNotes(inputPage.toString());
                }
            }
        } catch (MalformedURLException mal) {
            if (logger.isErrorEnabled()) {
                logger.error("Malformed presentationLink url: " + meeting.getPresentationLink());
            }
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("IOException when reading input line from " + meeting.getPresentationLink());
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException io) {
                }
            }
        }

        return meetingNote.build();
    }

    /**
     * Parse each line and find the presentation notes id and message.
     * If both are found add them to the meeting object.
     *
     * @param inputLine
     */

    void parseAndAppendPresentationNotes(final String inputLine) {
        if (inputLine.contains(PresentationTags.START_TAG) && inputLine.contains(PresentationTags.END_TAG)) {
            String[] messageArray = inputLine.split(PresentationTags.NOTE_REGEX);
            String[] idArray = inputLine.split(PresentationTags.ID_REGEX);

            if (messageArray.length == idArray.length && messageArray.length >= 2) {
                for (int i = 0; i < messageArray.length; i++) {
                    final String message = messageArray[i];
                    final String id = idArray[i];
                    final int endIndex = id.indexOf("&gt;");

                    // Make sure the id is included in the start tag
                    if (endIndex > -1) {
                        String number = id.substring(0, endIndex).trim();
                        // Find the number and continue if the end tag is included, also makes sure id is a number
                        if (message.contains(PresentationTags.END_TAG) && number.matches("[0-9]*")) {
                            notes.add(message.substring(0, message.indexOf(PresentationTags.END_TAG)));
                            slideNumbers.add(Integer.parseInt(number));
                        }
                    }
                }

                // Finally add the notes to the meeting
                appendPresentationNotes();
            }
        }
    }

    /**
     * Appends presentation notes if both slideNumbers- and notes list are equal length.
     * All notes must have a slide number.
     */
    private void appendPresentationNotes() {
        if (notes.size() == slideNumbers.size() && meeting != null) {
            final Map<Integer, String> tempNotes = new HashMap<Integer, String>();

            for (int i = 0; i < notes.size(); i++) {
                tempNotes.put(slideNumbers.get(i), notes.get(i));
            }

            StringBuilder participantsBuilder = new StringBuilder();
            for (String participant : meeting.getParticipants()) {
                participantsBuilder.append(participant).append(";");
            }

            meetingNote.setMessages(tempNotes);
            // Makes it possible to add welcome messages to the meeting on slide number 0
            meetingNote.setCurrentSlideNumber(0);
        }
    }

}
