package ac.uk.brunel.mobile.contextaware.participant.business;

import ac.uk.brunel.mobile.contextaware.participant.callback.PresentationCallback;

public interface PresentationService {
	public void startReadingPresentationNotes(final PresentationCallback presentationCallback, final String serverAddress, final String bluetoothAddress);
	public void stopReadingPresentationNotes();
	public void addNote(final String note);
	public String getNextNote();
	public String getPreviousNote();
}
