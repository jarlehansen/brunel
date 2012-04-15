package ac.uk.brunel.server.contextaware.persistence.wrapper;

import ac.uk.brunel.server.contextaware.dto.Meeting;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 22, 2010
 * Time: 10:28:36 PM
 */
@Entity
public class MeetingJpaWrapper {
    @Id
    private String meetingId;

    private String presentationLink;
    private String presenter;

    /* (;)-seperated list contained in one String. Is transformed when storing and retrieving the list of participants */
    private String participants;

    private String title;
    private String description;
    private String location;
    private String startTime;
    private String endTime;


    /**
     * Should only be used by JPA!
     */
    protected MeetingJpaWrapper() {
    }

    public MeetingJpaWrapper(final Meeting meeting) {
        this.meetingId = meeting.getMeetingId();
        this.presentationLink = meeting.getPresentationLink();
        this.presenter = meeting.getPresenter();
        this.participants = createParticipantsString(meeting.getParticipants());
        this.title = meeting.getTitle();
        this.description = meeting.getDescription();
        this.location = meeting.getLocation();
        this.startTime = meeting.getStartTime();
        this.endTime = meeting.getEndTime();
    }

    /**
     * For testing only!
     */
    String getParticipantsString() {
        return participants;
    }

    public String getPresenter() {
        return presenter;
    }

    private String createParticipantsString(final List<String> participants) {
        final StringBuilder participantsBuilder = new StringBuilder();
        for (String participant : participants) {
            participantsBuilder.append(participant).append(";");
        }

        return participantsBuilder.toString();
    }

    public Meeting getMeeting() {
        return new Meeting(meetingId, presentationLink, presenter, populateParticipantsList(), title,
                description, location, startTime, endTime);
    }

    private List<String> populateParticipantsList() {
        List<String> participantsList = new ArrayList<String>();

        final String[] participantsArray = participants.split(";");

        for (String participant : participantsArray) {
            if (participant != null && !"".equals(participant)) {
                participantsList.add(participant);
            }
        }

        return participantsList;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MeetingJpaWrapper");
        sb.append("{meetingId='").append(meetingId).append('\'');
        sb.append(", presentationLink='").append(presentationLink).append('\'');
        sb.append(", presenter='").append(presenter).append('\'');
        sb.append(", participants='").append(participants).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
