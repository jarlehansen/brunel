package ac.uk.brunel.mobile.contextaware.servicelocator;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;
import ac.uk.brunel.mobile.contextaware.business.MeetingController;
import ac.uk.brunel.mobile.contextaware.business.MeetingHistory;
import ac.uk.brunel.mobile.contextaware.business.MeetingHistoryImpl;
import ac.uk.brunel.mobile.contextaware.integration.MeetingNoteReader;
import ac.uk.brunel.mobile.contextaware.integration.MeetingNoteReaderImpl;
import ac.uk.brunel.mobile.contextaware.integration.NetworkCommunicationUtil;
import ac.uk.brunel.mobile.contextaware.integration.NetworkCommunicationUtilImpl;
import ac.uk.brunel.mobile.contextaware.persistence.MeetingHistoryDao;
import ac.uk.brunel.mobile.contextaware.persistence.MeetingHistoryDaoImpl;
import ac.uk.brunel.mobile.contextaware.persistence.ServerAddressDao;
import ac.uk.brunel.mobile.contextaware.persistence.ServerAddressDaoImpl;
import ac.uk.brunel.mobile.contextaware.presentation.ContextAwareMeetingRoomUi;
import ac.uk.brunel.mobile.contextaware.presentation.ContextAwareMeetingRoomUiImpl;
import ac.uk.brunel.mobile.contextaware.presentation.facade.PresentationActionsImpl;

public class AppConfigImpl implements AppConfig {
	private static final Logger logger = LoggerFactory.getLogger(AppConfigImpl.class);
	// jad-file server address property
	private static final String DEFAULT_SERVER_ADDRESS = "default-server-address";
	private static final String SERVER_POLL_FREQUENCY = "server-poll-frequency";

	// general error message
	private static final String ERROR_MSG = "Class dependency was not set: ";

	// RMS
	private static final String recordStoreNameHistory = "MeetingHistoryRms";
	private static final String recordStoreNameServerAddress = "ServerAddressRms";

	// Application variables
	private MIDlet midlet = null;
	private String defaultServerAddress = null;
	private int serverPollFrequency = -1;

	// Internal application dependencies
	private PresentationActionsImpl presentationActions = null;
	private ContextAwareMeetingRoomUi contextAwareMeetingRoomUi = null;
	private NetworkCommunicationUtil networkCommunicationUtil = null;
	private MeetingNoteReader meetingNoteReader = null;
	private ServerAddressDao serverAddressDao = null;
	private MeetingHistoryDao meetingHistoryDao = null;
	private MeetingHistory notesHistory = null;

	public ContextAwareMeetingRoomUi init(final MIDlet midlet) {
		this.midlet = midlet;
		if (midlet == null) {
			throw new IllegalArgumentException(ERROR_MSG + " MIDlet");
		}

		defaultServerAddress = midlet.getAppProperty(DEFAULT_SERVER_ADDRESS);
		if (defaultServerAddress == null) {
			throw new IllegalArgumentException(ERROR_MSG + DEFAULT_SERVER_ADDRESS);
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("Default server address: " + defaultServerAddress);
			}
		}

		serverPollFrequency = Integer.parseInt(midlet.getAppProperty(SERVER_POLL_FREQUENCY));

		if (serverPollFrequency == -1) {
			throw new IllegalArgumentException(ERROR_MSG + SERVER_POLL_FREQUENCY);
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("Server poll frequency: " + serverPollFrequency);
			}
		}

		createPresentationActions();
		createContextAwareMeetingRoomUi();
		createNetworkCommunicationUtil();
		createMeetingNoteReader();
		createServerAddressDao();
		createMeetingHistoryDao();
		createNotesHistory();
		createMeetingController();

		return contextAwareMeetingRoomUi;
	}

	private void createPresentationActions() {
		presentationActions = new PresentationActionsImpl();
	}

	private void createContextAwareMeetingRoomUi() {
		contextAwareMeetingRoomUi = new ContextAwareMeetingRoomUiImpl(midlet, presentationActions);
	}

	private void createNetworkCommunicationUtil() {
		networkCommunicationUtil = new NetworkCommunicationUtilImpl();
	}

	private void createMeetingNoteReader() {
		meetingNoteReader = new MeetingNoteReaderImpl(serverPollFrequency, networkCommunicationUtil);
	}

	private void createServerAddressDao() {
		try {
			RecordStore recordStore = RecordStore.openRecordStore(recordStoreNameServerAddress, true);
			serverAddressDao = new ServerAddressDaoImpl(defaultServerAddress, recordStore);
		} catch (RecordStoreException rse) {
			if (logger.isErrorEnabled()) {
				logger.error("Unable to create the " + recordStoreNameServerAddress, rse);
			}

			throw new IllegalStateException("Unable to create the " + recordStoreNameServerAddress + ", " + rse.getMessage());
		}
	}

	private void createMeetingHistoryDao() {
		try {
			RecordStore recordStore = RecordStore.openRecordStore(recordStoreNameHistory, true);
			meetingHistoryDao = new MeetingHistoryDaoImpl(recordStore);
		} catch (RecordStoreException rse) {
			if (logger.isErrorEnabled()) {
				logger.error("Unable to create the " + recordStoreNameHistory, rse);
			}

			throw new IllegalStateException("Unable to create the " + recordStoreNameHistory + ", " + rse.getMessage());
		}
	}

	private void createNotesHistory() {
		notesHistory = new MeetingHistoryImpl(meetingHistoryDao);
	}

	private void createMeetingController() {
		String bluetoothAddress = "";

		try {
			bluetoothAddress = LocalDevice.getLocalDevice().getBluetoothAddress();
		} catch (BluetoothStateException bse) {
			if (logger.isErrorEnabled()) {
				logger.error("Unable to retrieve Bluetooth address");
			}

			throw new IllegalStateException("Unable to retrieve Bluetooth address, " + bse.getMessage());
		}

		MeetingController meetingController = new MeetingController(bluetoothAddress, contextAwareMeetingRoomUi, meetingNoteReader, serverAddressDao,
				notesHistory);

		meetingNoteReader.setMeetingInput(meetingController);
		presentationActions.setOutputActions(meetingController);
	}
}
