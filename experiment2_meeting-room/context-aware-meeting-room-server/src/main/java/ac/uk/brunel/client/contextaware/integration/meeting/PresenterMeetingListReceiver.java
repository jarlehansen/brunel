package ac.uk.brunel.client.contextaware.integration.meeting;

import ac.uk.brunel.client.contextaware.dto.Meeting;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 2, 2010
 * Time: 8:41:33 PM
 */
public interface PresenterMeetingListReceiver {
    public List<Meeting> getPresenterMeetingList(final String email);
}
