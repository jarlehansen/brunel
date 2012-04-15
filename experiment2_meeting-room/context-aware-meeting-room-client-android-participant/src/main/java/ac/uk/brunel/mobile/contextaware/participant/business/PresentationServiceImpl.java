package ac.uk.brunel.mobile.contextaware.participant.business;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import ac.uk.brunel.mobile.contextaware.participant.callback.PresentationCallback;
import ac.uk.brunel.mobile.contextaware.participant.integration.PresentationNotesReader;
import android.util.Log;

public class PresentationServiceImpl implements PresentationService {
	private static final String TAG = PresentationServiceImpl.class.getName();

	private final PresentationNotesReader presentationNotesReader;

	private List<String> meetingNotes;
	private int historySlideNumber;

	@Inject
	public PresentationServiceImpl(final PresentationNotesReader presentationNotesReader) {
		this.presentationNotesReader = presentationNotesReader;
	}

	public void startReadingPresentationNotes(final PresentationCallback presentationCallback, final String serverAddress,
			final String bluetoothAddress) {
		Log.d(TAG, "Starting to read presentation notes from: " + serverAddress + ", " + bluetoothAddress);

		historySlideNumber = -1;
		meetingNotes = new ArrayList<String>();

		presentationNotesReader.startCommunication(presentationCallback, serverAddress, bluetoothAddress);
	}

	public void stopReadingPresentationNotes() {
		Log.d(TAG, "Stopping the notes reader thread");
		presentationNotesReader.stopCommunication();
	}

	public void addNote(final String note) {
		meetingNotes.add(note);
		historySlideNumber = meetingNotes.size() - 1;
	}

	public String getNextNote() {
		String note = null;

		if (historySlideNumber < (meetingNotes.size() - 1)) {
			historySlideNumber++;
		}

		if (historySlideNumber <= meetingNotes.size()) {
			note = meetingNotes.get(historySlideNumber);
		}

		return note;
	}

	public String getPreviousNote() {
		String note = null;

		if (historySlideNumber > 0) {
			historySlideNumber--;
		}

		if (meetingNotes.size() > 0) {
			note = meetingNotes.get(historySlideNumber);
		}

		return note;
	}
}
