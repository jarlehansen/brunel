package ac.uk.brunel.client.contextaware.service;

import ac.uk.brunel.client.contextaware.integration.keyboardevent.KeyboardEventHandler;
import ac.uk.brunel.client.contextaware.integration.keyboardevent.ValidKeyboardEvents;
import ac.uk.brunel.client.contextaware.integration.presenter.PresenterEventSender;
import ac.uk.brunel.contextaware.network.generated.PresenterActionProtobuf;
import com.google.inject.Inject;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 18, 2010
 * Time: 7:12:26 PM
 */
public class PresenterServiceImpl implements PresenterService {
    private final KeyboardEventHandler keyboardEventHandler;
    private final PresenterEventSender presenterEventSender;
    private final MeetingStateHandler meetingStateHandler;

    @Inject
    public PresenterServiceImpl(final KeyboardEventHandler keyboardEventHandler, final PresenterEventSender presenterEventSender,
                                final MeetingStateHandler meetingStateHandler) {
        this.keyboardEventHandler = keyboardEventHandler;
        this.presenterEventSender = presenterEventSender;
        this.meetingStateHandler = meetingStateHandler;
    }

    public void performPresenterAction(final PresenterActionProtobuf.PresenterAction presenterAction) {
        final String action = presenterAction.getAction();
        keyboardEventHandler.performEvent(action);

        if(ValidKeyboardEvents.DEFAULT.isNext(action)) {
            presenterEventSender.sendPresenterEvent(meetingStateHandler.getMeetingId(), meetingStateHandler.increaseAndGetSlideNumber());
        } else if(ValidKeyboardEvents.DEFAULT.isPrevious(action)) {
            presenterEventSender.sendPresenterEvent(meetingStateHandler.getMeetingId(), meetingStateHandler.decreaseAndGetSlideNumber());
        }
    }
}
