package ac.uk.brunel.mobile.contextaware.business;

import java.util.Vector;

import ac.uk.brunel.mobile.contextaware.input.MeetingInput;
import ac.uk.brunel.mobile.contextaware.integration.MeetingNoteReader;
import ac.uk.brunel.mobile.contextaware.output.MeetingActions;
import ac.uk.brunel.mobile.contextaware.persistence.ServerAddressDao;
import ac.uk.brunel.mobile.contextaware.presentation.ContextAwareMeetingRoomUi;

public class MeetingController implements MeetingInput, MeetingActions {
	private final String bluetoothAddress;
	private final ContextAwareMeetingRoomUi ui;
	private final MeetingNoteReader communication;
	private final ServerAddressDao serverAddressDao;
	private final MeetingHistory meetingHistory;
	
	
	public MeetingController(final String bluetoothAddress, final ContextAwareMeetingRoomUi ui, final MeetingNoteReader communication,
			final ServerAddressDao serverAddressDao, final MeetingHistory meetingHistory) {
		this.bluetoothAddress = bluetoothAddress;
		this.ui = ui;
		this.communication = communication;
		this.serverAddressDao = serverAddressDao;
		this.meetingHistory = meetingHistory;
	}
	
	public void startServerCommunication() {
		final String serverAddress = getServerConfig();
		communication.startServerCommunication(bluetoothAddress, serverAddress);
	}

	public void stopServerCommunication() {
		communication.stopCommunication();
		meetingHistory.saveAndResetCurrentMeetingHistory();
		ui.setScreenText("");
	}

	public void setNote(final String meetingId, final String message) {
		ui.setScreenText(message);
		meetingHistory.addNote(meetingId, message);
	}
	
	public void setErrorMessage(final String message) {
		ui.setErrorMessage(message);
	}

	public void exitApp() {
		serverAddressDao.closeRecordStore();
		meetingHistory.closeRecordStore();
	}

	public void setServerConfig(final String serverAddress) {
		serverAddressDao.saveServerAddress(serverAddress);
	}

	public String getServerConfig() {
		return serverAddressDao.getServerAddress();
	}
	
	public String getBluetoothAddress() {
		return bluetoothAddress;
	}

	public String getNextNote() {
		return meetingHistory.getNextNote();
	}

	public String getPreviousNote() {
		return meetingHistory.getPreviousNote();
	}

	public Vector getPreviousMeetings() {
		return meetingHistory.getPreviousMeetings();
	}
}
