package ac.uk.brunel.server.contextaware.presentation.notes;

import ac.uk.brunel.server.contextaware.persistence.presentation.PresentationDaoWicketFacade;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 28, 2010
 * Time: 2:27:36 PM
 */
public class DeleteMeetingNotesTest {
    private PresentationDaoWicketFacade mockedPresentationDaoWicketFacade;
    private WicketTester wicketTester;

    @Before
    public void setup() {
        mockedPresentationDaoWicketFacade = mock(PresentationDaoWicketFacade.class);
        wicketTester = new WicketTester();
    }

    @Test
    public void testRenderDeleteMeetingNotes() {
        wicketTester.startPage(new DeleteMeetingNotes(mockedPresentationDaoWicketFacade));

        wicketTester.assertRenderedPage(DeleteMeetingNotes.class);
        verify(mockedPresentationDaoWicketFacade).getNumberOfMeetingNotes();
    }
}
