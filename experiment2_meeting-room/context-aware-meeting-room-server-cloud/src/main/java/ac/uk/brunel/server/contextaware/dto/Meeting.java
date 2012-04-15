package ac.uk.brunel.server.contextaware.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 11:39:00 PM
 */
// TODO Serializable needed by Wicket, any way of working around this?
public final class Meeting implements Serializable {
    private final String meetingId;
    private final String presentationLink;
    private final String presenter;
    private final List<String> participants;
    private final String title;
    private final String description;
    private final String location;
    private final String startTime;
    private final String endTime;

    public Meeting(String meetingId, String presentationLink, String presenter, List<String> participants, String title,
                   String description, String location, String startTime, String endTime) {
        this.meetingId = meetingId;
        this.presentationLink = presentationLink;
        this.presenter = presenter;
        this.participants = participants;
        this.title = title;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getMeetingId() {
        return (meetingId == null) ? "" : meetingId;
    }

    public String getPresentationLink() {
        return (presentationLink == null) ? "" : presentationLink;
    }

    public String getPresenter() {
        return (presenter == null) ? "" : presenter;
    }

    public List<String> getParticipants() {
        return (participants == null) ? new ArrayList<String>() : participants;
    }

    public String getTitle() {
        return (title == null) ? "" : title;
    }

    public String getDescription() {
        return (description == null) ? "" : description;
    }

    public String getLocation() {
        return (location == null) ? "" : location;
    }

    public String getStartTime() {
        return (startTime == null) ? "" : startTime;
    }

    public String getEndTime() {
        return (endTime == null) ? "" : endTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Meeting");
        sb.append("{meetingId='").append(meetingId).append('\'');
        sb.append(", presentationLink='").append(presentationLink).append('\'');
        sb.append(", presenter='").append(presenter).append('\'');
        sb.append(", participants=").append(participants);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
