package ac.uk.brunel.server.contextaware.service;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.dto.MeetingNote;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 30, 2010
 * Time: 10:14:02 PM
 */
public interface PresentationService {
    public MeetingNote registerStartedMeeting(final Meeting meeting);
    public void registerConnectedUser(final String meetingId, final String btAddress);
    public void updateCurrentSlideNumber(final String meetingId, final int currentSlideNumber);
    public String getCurrentSlideNote(final String meetingId, final String participant);
    public String[] getCurrentSlideNote(final String participantBtAddress);
}
