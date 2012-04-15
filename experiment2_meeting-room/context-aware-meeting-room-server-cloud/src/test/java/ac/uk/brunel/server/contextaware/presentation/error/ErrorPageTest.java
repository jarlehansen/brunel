package ac.uk.brunel.server.contextaware.presentation.error;

import ac.uk.brunel.server.contextaware.exception.MeetingRoomException;
import ac.uk.brunel.server.contextaware.exception.calendar.CalendarCommunicationException;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 4:03:20 PM
 */
public class ErrorPageTest {
    private static final String errorMsg = "This is a test of the ErrorPage";
    private WicketTester tester;

    @Before
    public void setup() {
        tester = new WicketTester();
    }

    @Test
    public void testRenderErrorPage() {
        final MeetingRoomException exception = new MeetingRoomException(errorMsg);
        tester.startPage(new ErrorPage(new DummyWebPage(), exception));

        tester.assertRenderedPage(ErrorPage.class);
    }

    @Test
    public void testRenderErrorPageWithCauseException() {
        final MeetingRoomException exception =
                new MeetingRoomException(errorMsg, new CalendarCommunicationException("Test of the ErrorPage"));
        tester.startPage(new ErrorPage(new DummyWebPage(), exception));

        tester.assertRenderedPage(ErrorPage.class);
    }
}
