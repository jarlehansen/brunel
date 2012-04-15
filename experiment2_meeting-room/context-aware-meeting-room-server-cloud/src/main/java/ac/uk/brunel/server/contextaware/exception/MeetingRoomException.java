package ac.uk.brunel.server.contextaware.exception;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 22, 2010
 * Time: 6:28:09 PM
 */
public class MeetingRoomException extends RuntimeException {
    public MeetingRoomException(final String message) {
        super(message);
    }

    public MeetingRoomException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
