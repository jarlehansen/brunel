package ac.uk.brunel.server.contextaware.persistence.meeting;

import ac.uk.brunel.server.contextaware.dto.Meeting;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 22, 2010
 * Time: 11:59:19 PM
 */
public interface MeetingDao {
    public void registerMeeting(final Meeting meeting);
    public List<Meeting> getPresenterMeetings(final String email, final String date);
}
