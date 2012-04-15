package ac.uk.brunel.mobile.contextaware.presentation.facade;

import java.util.Vector;

import ac.uk.brunel.mobile.contextaware.dto.Meeting;
import ac.uk.brunel.mobile.contextaware.presentation.dto.PresentationMeeting;

public class PresentationObjectFactory {

	private PresentationObjectFactory() {
	}

	static Vector createPresentationMeetingList(final Vector meetingList) {
		Vector presentationMeetingList = new Vector();

		for (int i = 0; i < meetingList.size(); i++) {
			Meeting meeting = (Meeting) meetingList.elementAt(i);
			presentationMeetingList.addElement(new PresentationMeeting(meeting.getMeetingId(), meeting.getMeetingNotes(), meeting.getSavedDate()));
		}

		return presentationMeetingList;
	}
}
