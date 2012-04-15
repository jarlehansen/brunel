package ac.uk.brunel.mobile.contextaware.persistence;

import java.util.Vector;

import ac.uk.brunel.mobile.contextaware.dto.Meeting;

public interface MeetingHistoryDao {
	public Vector getAllPersistedMeetings();
	public void saveMeetingHistory(final Meeting meetings) ;
	public void closeRecordStore();
}
