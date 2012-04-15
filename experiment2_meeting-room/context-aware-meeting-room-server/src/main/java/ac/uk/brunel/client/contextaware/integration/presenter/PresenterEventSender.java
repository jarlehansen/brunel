package ac.uk.brunel.client.contextaware.integration.presenter;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 28, 2010
 * Time: 4:11:38 PM
 */
public interface PresenterEventSender {
    public void sendPresenterEvent(final String meetingId, final int currentSlideNumber);
}
