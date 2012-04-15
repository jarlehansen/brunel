package ac.uk.brunel.mobile.contextaware.business;

import java.util.Vector;

public interface MeetingHistory {
	public void addNote(final String meetingId, final String message);
	public String getPreviousNote();
	public String getNextNote();
	public Vector getPreviousMeetings();
	public void closeRecordStore();
	public void saveAndResetCurrentMeetingHistory();
}
