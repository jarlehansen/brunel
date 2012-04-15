package ac.uk.brunel.client.contextaware.service;

import ac.uk.brunel.server.contextaware.presentation.swing.dto.PresentationMeeting;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 6, 2010
 * Time: 10:01:39 PM
 */
public interface MeetingService {
    public List<PresentationMeeting> getPresenterMeetingList(final String email);
    public void registerMeeting(final PresentationMeeting presentationMeeting);
}
