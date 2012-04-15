package ac.uk.brunel.mobile.contextaware.integration;

import ac.uk.brunel.mobile.contextaware.input.MeetingInput;

public interface MeetingNoteReader {
	public void setMeetingInput(final MeetingInput controller);
	public void startServerCommunication(final String bluetoothAddress, final String serverAddress);
	public void stopCommunication();
}
