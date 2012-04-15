package ac.uk.brunel.server.contextaware.presentation.error;

import ac.uk.brunel.server.contextaware.exception.MeetingRoomException;
import ac.uk.brunel.server.contextaware.exception.calendar.CalendarCommunicationException;
import ac.uk.brunel.server.contextaware.presentation.BasePage;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 4:10:36 PM
 */
public class ErrorPageTester extends BasePage {

    public ErrorPageTester() {
        throw new MeetingRoomException("This is added from the ErrorPageTester, this should be shown as the error message",
                new CalendarCommunicationException("This is the real cause"));
    }
}
