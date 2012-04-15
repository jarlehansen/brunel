package ac.uk.brunel.server.contextaware.persistence.wrapper;

import ac.uk.brunel.server.contextaware.dto.MeetingNote;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 31, 2010
 * Time: 2:44:54 PM
 */
@Entity
public class MeetingNoteJpaWrapper {
    @Id
    private String meetingId;

    private String messages;
    private int currentSlideNumber;
    private String participants;
    private String connectedParticipants;

    /**
     * Should only be used by JPA!
     */
    protected MeetingNoteJpaWrapper() {
    }

    public MeetingNoteJpaWrapper(final MeetingNote meetingNote) {
        this.meetingId = meetingNote.getMeetingId();
        this.messages = createMessagesString(meetingNote.getMessages());
        this.currentSlideNumber = meetingNote.getCurrentSlideNumber();
        this.participants = meetingNote.getParticipants();
        this.connectedParticipants = meetingNote.getConnectedParticipants();
    }

    /**
     * For testing only!
     */
    String getMessagesString() {
        return messages;
    }

    private String createMessagesString(final Map<Integer, String> messagesMap) {
        final StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, String> entry : messagesMap.entrySet()) {
            builder.append(entry.getKey()).append(",").append(entry.getValue()).append(";");
        }

        return builder.toString();
    }

    private Map<Integer, String> populateMessagesMap() {
        final Map<Integer, String> tempMap = new HashMap<Integer, String>();

        final String[] messagesArray = messages.split(";");

        if (messagesArray != null) {
            for (String message : messagesArray) {
                final String[] tempArray = message.split(",");
                if (tempArray.length == 2) {
                    tempMap.put(Integer.parseInt(tempArray[0]), tempArray[1]);
                }
            }
        }

        return tempMap;
    }

    public int getCurrentSlideNumber() {
        return currentSlideNumber;
    }

    public MeetingNote getMeetingNote() {
        MeetingNote meetingNote = new MeetingNote.Builder(meetingId).setMessages(populateMessagesMap()).build();
        meetingNote.setParticipants(participants);
        meetingNote.setCurrentSlideNumber(currentSlideNumber);
        meetingNote.setConnectedParticipants(connectedParticipants);

        return meetingNote;
    }
}
