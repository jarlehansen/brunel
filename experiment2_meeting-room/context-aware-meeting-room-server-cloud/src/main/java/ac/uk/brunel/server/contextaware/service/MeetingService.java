package ac.uk.brunel.server.contextaware.service;

import ac.uk.brunel.server.contextaware.dto.Meeting;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 11:36:43 PM
 */
public interface MeetingService {
    public void refreshMeetingList();
    public List<Meeting> getTodaysPresenterMeetings(final String email);
}
