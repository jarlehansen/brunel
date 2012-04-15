package ac.uk.brunel.mobile.contextaware.dto;

import java.util.Date;
import java.util.Vector;

public class Meeting {
	private final String meetingId;
	private final Vector meetingNotes;

	// mutable
	private Date savedDate = null;

	public Meeting(final String meetingId, final Vector meetingNotes) {
		this.meetingId = meetingId;
		this.meetingNotes = meetingNotes;
	}

	public Meeting(final String meetingId, final Vector meetingNotes, final Date savedDate) {
		this.meetingId = meetingId;
		this.meetingNotes = meetingNotes;
		this.savedDate = savedDate;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public Vector getMeetingNotes() {
		return meetingNotes;
	}

	public void updateSavedDate() {
		savedDate = new Date();
	}

	public Date getSavedDate() {
		return savedDate;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Meeting [meetingId=").append(meetingId).append(", meetingNotes=").append(meetingNotes).append(", savedDate=").append(savedDate).append(
				"]");
		return buffer.toString();
	}
}
