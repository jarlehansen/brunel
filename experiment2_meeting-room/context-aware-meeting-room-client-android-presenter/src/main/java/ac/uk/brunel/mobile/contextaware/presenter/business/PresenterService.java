package ac.uk.brunel.mobile.contextaware.presenter.business;

import ac.uk.brunel.mobile.contextaware.presenter.callback.PresentationCallback;

public interface PresenterService {
	public void initService(final String serverAddress, final PresentationCallback presentationCallback);
	public void sendConnectionPing();
	public void sendNextSlideEvent();
	public void sendPrevSlideEvent();
}