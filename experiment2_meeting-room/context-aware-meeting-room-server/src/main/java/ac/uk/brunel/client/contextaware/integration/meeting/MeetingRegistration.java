package ac.uk.brunel.client.contextaware.integration.meeting;

import ac.uk.brunel.client.contextaware.dto.Meeting;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 12, 2010
 * Time: 3:17:49 PM
 */
public interface MeetingRegistration {
    public List<String> registerMeetingAndGetRegisteredParticipants(final Meeting meeting);
}
