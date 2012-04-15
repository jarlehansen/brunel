package ac.uk.brunel.server.contextaware.dto;

import java.util.Collections;
import java.util.Map;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 29, 2010
 * Time: 6:41:20 PM
 */
public final class MeetingNote {
    // required immutable
    private final String meetingId;

    // optional immutable
    private final Map<Integer, String> messages;

    // mutable values
    private int currentSlideNumber = -1;
    private String participants = "";
    private String connectedParticipants = "";

    public static final MeetingNote EMPTY_NOTE = new MeetingNote.Builder("").build();
    public static final Builder EMPTY_BUILDER = new MeetingNote.Builder("");

    public static class Builder {
        // required
        private final String meetingId;

        // optional
        private Map<Integer, String> messages = Collections.emptyMap();
        private int currentSlideNumber = -1;

        public Builder(final String meetingId) {
            this.meetingId = meetingId;
        }

        public Builder setMessages(final Map<Integer, String> messages) {
            this.messages = messages;
            return this;
        }

        public Builder setCurrentSlideNumber(final int currentSlideNumber) {
            this.currentSlideNumber = currentSlideNumber;
            return this;
        }

        public MeetingNote build() {
            return new MeetingNote(this);
        }
    }

    private MeetingNote(final Builder builder) {
        this.meetingId = builder.meetingId;
        this.messages = builder.messages;
        this.currentSlideNumber = builder.currentSlideNumber;
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

    public String getCurrentSlideNote() {
        return messages.get(currentSlideNumber);
    }

    public String getParticipants() {
        return participants;
    }

    public String getConnectedParticipants() {
        return connectedParticipants;
    }

    public void setParticipants(final String participants) {
        this.participants = participants;
    }

    public void setConnectedParticipants(final String connectedParticipants) {
        this.connectedParticipants = connectedParticipants;
    }

    public void setCurrentSlideNumber(final int currentSlideNumber) {
        this.currentSlideNumber = currentSlideNumber;
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
