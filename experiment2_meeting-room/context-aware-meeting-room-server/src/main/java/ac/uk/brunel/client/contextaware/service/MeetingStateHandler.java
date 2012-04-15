package ac.uk.brunel.client.contextaware.service;

import ac.uk.brunel.client.contextaware.dto.Meeting;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 28, 2010
 * Time: 3:57:10 PM
 */
public interface MeetingStateHandler {
    public void setStartedMeeting(final Meeting meeting);
    public int increaseAndGetSlideNumber();
    public int decreaseAndGetSlideNumber();
    public String getMeetingId();
}
