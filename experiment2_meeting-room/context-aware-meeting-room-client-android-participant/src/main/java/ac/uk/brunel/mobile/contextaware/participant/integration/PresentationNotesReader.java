package ac.uk.brunel.mobile.contextaware.participant.integration;

import ac.uk.brunel.mobile.contextaware.participant.callback.PresentationCallback;

public interface PresentationNotesReader {
	public void startCommunication(final PresentationCallback presentationCallback, final String serverAddress, final String bluetoothAddress);
	public void stopCommunication();
}
