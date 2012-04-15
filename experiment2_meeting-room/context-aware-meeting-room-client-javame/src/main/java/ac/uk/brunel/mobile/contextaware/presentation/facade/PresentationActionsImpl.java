package ac.uk.brunel.mobile.contextaware.presentation.facade;

import java.util.Vector;

import ac.uk.brunel.mobile.contextaware.output.MeetingActions;
import ac.uk.brunel.mobile.contextaware.presentation.actions.PresentationActions;

public class PresentationActionsImpl implements PresentationActions {
	private MeetingActions meetingActions;

	public PresentationActionsImpl() {
	}

	public void setOutputActions(final MeetingActions meetingActions) {
		this.meetingActions = meetingActions;
	}
	
	public void startServerCommunication() {
		meetingActions.startServerCommunication();
	}

	public void stopServerCommunication() {
		meetingActions.stopServerCommunication();
	}

	public String getNextNote() {
		return meetingActions.getNextNote();
	}

	public String getPreviousNote() {
		return meetingActions.getPreviousNote();
	}

	public Vector getPreviousMeetings() {
		return PresentationObjectFactory.createPresentationMeetingList(meetingActions.getPreviousMeetings());
	}

	public String showInfo() {
		return meetingActions.getBluetoothAddress();
	}

	public void exitApp() {
		meetingActions.exitApp();
	}

	public void setServerConfig(String serverAddress) {
		meetingActions.setServerConfig(serverAddress);
	}

	public String getServerConfig() {
		return meetingActions.getServerConfig();
	}
}
