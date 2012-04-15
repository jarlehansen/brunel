package ac.uk.brunel.client.contextaware.dto;

import java.util.Collections;
import java.util.Map;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 29, 2010
 * Time: 6:41:20 PM
 */
public final class MeetingNote {
    // required
    private final String meetingId;
    private final Map<Integer, String> messages;

    private int currentSlideNumber = -1;
    private String participants = "";
    private String connectedParticipants = "";

    public static final MeetingNote EMPTY_NOTE = new MeetingNote("", Collections.<Integer, String>emptyMap());

    public MeetingNote(final String meetingId, final Map<Integer, String> messages) {
        this.meetingId = meetingId;
        this.messages = messages;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public Map<Integer, String> getMessages() {
        return messages;
    }

    public int getCurrentSlideNumber() {
        return currentSlideNumber;
    }

    public void setParticipants(final String participants) {
        this.participants = participants;
    }

    public String getParticipants() {
        return participants;
    }

    public void setConnectedParticipants(final String connectedParticipants) {
        this.connectedParticipants = connectedParticipants;
    }

    public String getConnectedParticipants() {
        return connectedParticipants;
    }

    public void setCurrentSlideNumber(final int currentSlideNumber) {
        this.currentSlideNumber = currentSlideNumber;
    }

    public String getCurrentSlideNote() {
        return messages.get(currentSlideNumber);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MeetingNote");
        sb.append("{meetingId='").append(meetingId).append('\'');
        sb.append(", messages=").append(messages);
        sb.append(", currentSlideNumber=").append(currentSlideNumber);
        sb.append(", participants='").append(participants).append('\'');
        sb.append(", connectedParticipants='").append(connectedParticipants).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
