package ac.uk.brunel.mobile.contextaware.business;

import java.util.Vector;

import ac.uk.brunel.mobile.contextaware.dto.Meeting;
import ac.uk.brunel.mobile.contextaware.persistence.MeetingHistoryDao;

public class MeetingHistoryImpl implements MeetingHistory {
	private final MeetingHistoryDao meetingHistoryDao;
	
	Meeting currentMeeting = null;
	Vector meetingNotes = new Vector();
	int historySlideNumber = 0;

	public MeetingHistoryImpl(final MeetingHistoryDao meetingHistoryDao) {
		this.meetingHistoryDao = meetingHistoryDao;
	}

	public void saveAndResetCurrentMeetingHistory() {
		meetingHistoryDao.saveMeetingHistory(currentMeeting);
		
		currentMeeting = null;
		meetingNotes = new Vector();
		historySlideNumber = 0;
	}

	public void addNote(final String meetingId, final String message) {
		if (message != null && !"".equals(message)) {
			if (currentMeeting == null) {
				currentMeeting = new Meeting(meetingId, meetingNotes);
			}
			
			if(isNewNote(message)) {
				meetingNotes.addElement(message);
				historySlideNumber = (meetingNotes.size() - 1);
			}
		}
	}

	boolean isNewNote(final String message) {
		boolean newNote = true;

		for (int i = 0; i < meetingNotes.size(); i++) {
			String savedNote = (String) meetingNotes.elementAt(i);
			if (savedNote.equals(message)) {
				newNote = false;
				break;
			}
		}

		return newNote;
	}

	public String getPreviousNote() {
		String previousNote = null;
		
		if (historySlideNumber > 0) {
			historySlideNumber--;
		}
		
		if(meetingNotes.size() > 0) {
			previousNote = (String) meetingNotes.elementAt(historySlideNumber);
		}
		
		return previousNote;
	}

	public String getNextNote() {
		String nextNote = null;
		
		if (historySlideNumber < (meetingNotes.size() -1)) {
			historySlideNumber++;
		}
		
		if(historySlideNumber <= meetingNotes.size()) {
			nextNote = (String) meetingNotes.elementAt(historySlideNumber);
		}
		
		return nextNote;
	}

	public Vector getPreviousMeetings() {
		return meetingHistoryDao.getAllPersistedMeetings();
	}

	public void closeRecordStore() {
		meetingHistoryDao.closeRecordStore();
	}
}
