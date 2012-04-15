package ac.uk.brunel.client.contextaware.service;

import ac.uk.brunel.client.contextaware.integration.keyboardevent.KeyboardEventHandler;
import ac.uk.brunel.client.contextaware.integration.presenter.PresenterEventSender;
import ac.uk.brunel.contextaware.network.generated.PresenterActionProtobuf;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 18, 2010
 * Time: 9:00:04 PM
 */
public class PresenterServiceImplTest {
    private PresenterServiceImpl presenterService;

    private KeyboardEventHandler mockedKeyboardEventHandler;
    private PresenterEventSender mockedPresenterEventSender;
    private MeetingStateHandler mockedMeetingStateHandler;

    @Before
    public void setup() {
        mockedKeyboardEventHandler = mock(KeyboardEventHandler.class);
        mockedPresenterEventSender = mock(PresenterEventSender.class);
        mockedMeetingStateHandler = mock(MeetingStateHandler.class);

        presenterService = new PresenterServiceImpl(mockedKeyboardEventHandler, mockedPresenterEventSender, mockedMeetingStateHandler);
    }

    @Test
    public void testPerformPresenterActionNext() {
        final PresenterActionProtobuf.PresenterAction presenterAction = PresenterActionProtobuf.PresenterAction.newBuilder().setAction("next").build();
        presenterService.performPresenterAction(presenterAction);

        verify(mockedKeyboardEventHandler).performEvent("next");
        verify(mockedPresenterEventSender).sendPresenterEvent(anyString(), anyInt());
        verify(mockedMeetingStateHandler).increaseAndGetSlideNumber();
    }

    @Test
    public void testPerformPresenterActionPrev() {
        final PresenterActionProtobuf.PresenterAction presenterAction = PresenterActionProtobuf.PresenterAction.newBuilder().setAction("prev").build();
        presenterService.performPresenterAction(presenterAction);

        verify(mockedKeyboardEventHandler).performEvent("prev");
        verify(mockedPresenterEventSender).sendPresenterEvent(anyString(), anyInt());
        verify(mockedMeetingStateHandler).decreaseAndGetSlideNumber();
    }
}
