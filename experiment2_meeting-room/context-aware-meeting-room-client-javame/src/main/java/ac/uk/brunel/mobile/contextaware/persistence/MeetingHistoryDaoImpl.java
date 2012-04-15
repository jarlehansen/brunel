package ac.uk.brunel.mobile.contextaware.persistence;

import java.util.Date;
import java.util.Vector;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

import net.mypapit.java.StringTokenizer;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;
import ac.uk.brunel.mobile.contextaware.dto.Meeting;

public class MeetingHistoryDaoImpl implements MeetingHistoryDao {
	private static final Logger logger = LoggerFactory.getLogger(MeetingHistoryDaoImpl.class);
	private final RecordStore recordStore;

	public MeetingHistoryDaoImpl(final RecordStore recordStore) {
		this.recordStore = recordStore;
	}

	public void saveMeetingHistory(final Meeting meeting) {
		try {
			meeting.updateSavedDate();

			final Vector notes = meeting.getMeetingNotes();
			final StringBuffer meetingBuilder = new StringBuffer(meeting.getMeetingNotes().size());
			meetingBuilder.append(meeting.getMeetingId()).append(";");
			meetingBuilder.append(meeting.getSavedDate().getTime()).append(";");

			for (int y = 0; y < notes.size(); y++) {
				meetingBuilder.append((String) notes.elementAt(y)).append(";");
			}

			byte[] meetingData = meetingBuilder.toString().getBytes();

			recordStore.addRecord(meetingData, 0, meetingData.length);
			if (logger.isDebugEnabled()) {
				logger.debug("Meeting saved: " + meeting);
			}
		} catch (RecordStoreException rse) {
			if (logger.isErrorEnabled()) {
				logger.error("RecordStoreException when trying to store meeting:" + meeting, rse);
			}
		}
	}

	public Vector getAllPersistedMeetings() {
		Vector meetings = new Vector();

		try {
			RecordEnumeration recordEnumeration = recordStore.enumerateRecords(null, null, false);

			while (recordEnumeration.hasNextElement()) {
				String meetingString = "";
				byte[] data = recordEnumeration.nextRecord();

				if (data != null && data.length > 0) {
					meetingString = new String(data);
					meetings.addElement(createMeeting(meetingString));
				}
			}
		} catch (RecordStoreException rse) {
			if (logger.isErrorEnabled()) {
				logger.error("RecordStoreException when trying to retrieve all persisted meetings", rse);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Returning persisted meetings: " + meetings);
		}

		return meetings;
	}

	Meeting createMeeting(final String meetingString) {
		String meetingId = null;
		Date savedDate = null;
		Vector meetingNotes = new Vector();

		StringTokenizer token = new StringTokenizer(meetingString, ";");
		int counter = 0;
		while (token.hasMoreTokens()) {
			String meetingData = token.nextToken();

			if (meetingData != null) {
				if (counter == 0) {
					meetingId = meetingData;
				} else if (counter == 1) {
					try {
						savedDate = new Date(Long.parseLong(meetingData));
					} catch (NumberFormatException nfe) {
						if (logger.isWarnEnabled()) {
							logger.warn("Unable to set the save date", nfe);
						}
					}
				} else {
					meetingNotes.addElement(meetingData);
				}

				counter++;
			} else {
				if (logger.isErrorEnabled()) {
					logger.error("Problem parsing meeting String saved in RMS, meetingId: " + meetingId + ", meetingNotes: " + meetingNotes + ", saved date: "
							+ savedDate);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Meeting values: " + meetingId + " - " + meetingNotes + " - " + savedDate);
		}

		return new Meeting(meetingId, meetingNotes, savedDate);
	}

	public void closeRecordStore() {
		try {
			recordStore.closeRecordStore();
		} catch (RecordStoreNotOpenException rsnoe) {
			if (logger.isWarnEnabled()) {
				logger.warn("RecordStoreNotOpenException when trying to close the MeetingHistory record store", rsnoe);
			}
		} catch (RecordStoreException rse) {
			if (logger.isWarnEnabled()) {
				logger.warn("RecordStoreException when trying to close the MeetingHistory record store", rse);
			}
		}
	}
}
