package ac.uk.brunel.server.contextaware.exception.calendar;

import ac.uk.brunel.server.contextaware.exception.MeetingRoomException;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 22, 2010
 * Time: 6:27:42 PM
 */
public class CalendarCommunicationException extends MeetingRoomException {
    
    public CalendarCommunicationException(final String message) {
        super(message);
    }

    public CalendarCommunicationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
