package ac.uk.brunel.mobile.contextaware.presenter.business;

import ac.uk.brunel.mobile.contextaware.presenter.callback.PresentationCallback;
import ac.uk.brunel.mobile.contextaware.presenter.integration.ConnectionPing;
import ac.uk.brunel.mobile.contextaware.presenter.integration.PresenterEventSender;

import com.google.inject.Inject;

public class PresenterServiceImpl implements PresenterService {
	private final ConnectionPing connectionPing;
	private final PresenterEventSender presenterEventSender;
	
	@Inject
	public PresenterServiceImpl(final ConnectionPing connectionPing, final PresenterEventSender presenterEventSender) {
		this.connectionPing = connectionPing;
		this.presenterEventSender = presenterEventSender;
	}
	
	/* (non-Javadoc)
	 * @see ac.uk.brunel.mobile.contextaware.business.PresenterService2#initService(java.lang.String, ac.uk.brunel.mobile.contextaware.presentation.PresentationCallback)
	 */
	public void initService(final String serverAddress, final PresentationCallback presentationCallback) {
		connectionPing.setServerAddress(serverAddress);
		connectionPing.setPresentationCallback(presentationCallback);
		
		presenterEventSender.setServerAddress(serverAddress);
		presenterEventSender.setPresentationCallback(presentationCallback);
	}
	
	/* (non-Javadoc)
	 * @see ac.uk.brunel.mobile.contextaware.business.PresenterService2#sendConnectionPing()
	 */
	public void sendConnectionPing() {
		connectionPing.sendConnectionPing();
	}
	
	/* (non-Javadoc)
	 * @see ac.uk.brunel.mobile.contextaware.business.PresenterService2#sendNextSlideEvent()
	 */
	public void sendNextSlideEvent() {
		presenterEventSender.sendNextSlideEvent();
	}
	
	/* (non-Javadoc)
	 * @see ac.uk.brunel.mobile.contextaware.business.PresenterService2#sendPrevSlideEvent()
	 */
	public void sendPrevSlideEvent() {
		presenterEventSender.sendPrevSlideEvent();
	}
}
