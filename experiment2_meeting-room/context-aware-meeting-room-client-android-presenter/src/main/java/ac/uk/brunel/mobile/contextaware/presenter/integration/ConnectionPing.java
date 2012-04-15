package ac.uk.brunel.mobile.contextaware.presenter.integration;

import ac.uk.brunel.mobile.contextaware.presenter.callback.PresentationCallback;

public interface ConnectionPing {
	public void setPresentationCallback(final PresentationCallback presentationCallback);
	public void setServerAddress(final String serverAddress);
	public void sendConnectionPing();
}
