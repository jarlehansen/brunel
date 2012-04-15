package ac.uk.brunel.server.contextaware.service;

import ac.uk.brunel.server.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.dto.MeetingNote;
import ac.uk.brunel.server.contextaware.dto.User;
import ac.uk.brunel.server.contextaware.integration.googledocs.PresentationIntegration;
import ac.uk.brunel.server.contextaware.persistence.presentation.PresentationDao;
import ac.uk.brunel.server.contextaware.persistence.user.UserDao;
import ac.uk.brunel.server.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 30, 2010
 * Time: 10:13:50 PM
 */
@LoggingAspect
public class PresentationServiceImpl implements PresentationService {
    private static final Logger logger = LoggerFactory.getLogger(PresentationServiceImpl.class);
    
    private final PresentationIntegration presentationIntegration;
    private final UserDao userDao;
    private final PresentationDao presentationDao;

    @Inject
    public PresentationServiceImpl(final PresentationIntegration presentationIntegration, final UserDao userDao, final PresentationDao presentationDao) {
        this.presentationIntegration = presentationIntegration;
        this.userDao = userDao;
        this.presentationDao = presentationDao;
    }

    public MeetingNote registerStartedMeeting(final Meeting meeting) {
        final MeetingNote meetingNote = presentationIntegration.createPresentationNoteObject(meeting);

        StringBuilder participants = new StringBuilder();
        for (String email : meeting.getParticipants()) {
            final String btAddress = findUserBluetoothAddress(email);

            if (btAddress != null && btAddress.length() > 2) {
                participants.append(btAddress).append(";");
            }
        }
        // add the test Bluetooth address that is always accepted
        final String bluetoothAddressAlwaysAccept = PropertiesReader.SERVER_APP.get(PropertiesConstants.BLUETOOTH_ADDRESS_ALWAYS_ACCEPT);
        participants.append(bluetoothAddressAlwaysAccept).append(";");

        meetingNote.setParticipants(participants.toString());
        meetingNote.setConnectedParticipants(bluetoothAddressAlwaysAccept);

        if(logger.isDebugEnabled()) {
            logger.debug("Persisting started meeting: " + meetingNote.toString());
        }
        
        presentationDao.persistStartedMeeting(meetingNote);
        return meetingNote;
    }

    String findUserBluetoothAddress(final String email) {
        final User user = userDao.findUser(email);

        String btAddress = null;
        if (user != null) {
            btAddress = user.getBtAddress();
        }

        return btAddress;
    }

    public void registerConnectedUser(final String meetingId, final String btAddress) {
        final MeetingNote meetingNote = presentationDao.findMeetingNote(meetingId);
        meetingNote.setConnectedParticipants(meetingNote.getConnectedParticipants() + ";" + btAddress + ";");

        presentationDao.persistStartedMeeting(meetingNote);
    }

    public void updateCurrentSlideNumber(final String meetingId, final int currentSlideNumber) {
        MeetingNote meetingNote = presentationDao.findMeetingNote(meetingId);
        meetingNote.setCurrentSlideNumber(currentSlideNumber);

        presentationDao.persistStartedMeeting(meetingNote);
    }

    public String[] getCurrentSlideNote(final String participantBtAddress) {
        final String[] meetingIdAndSlideNote = new String[2];
        meetingIdAndSlideNote[0] = "";
        meetingIdAndSlideNote[1] = "";

        if (participantBtAddress != null && participantBtAddress.length() > 2) {
            List<MeetingNote> meetingNotes = presentationDao.findAllMeetingNotes();



            for (MeetingNote meetingNote : meetingNotes) {
                logger.debug("bt address: " + participantBtAddress + " - is registered: " + isRegisteredForMeeting(meetingNote, participantBtAddress));
                if (meetingNote != null && isRegisteredForMeeting(meetingNote, participantBtAddress)
                        && isConnectedToMeeting(meetingNote, participantBtAddress)) {
                    meetingIdAndSlideNote[0] = meetingNote.getMeetingId();
                    meetingIdAndSlideNote[1] = meetingNote.getCurrentSlideNote();
                    break;
                }
            }
        }

        if(logger.isDebugEnabled()) {
            logger.debug("Current slide note: " + Arrays.asList(meetingIdAndSlideNote));
        }

        return meetingIdAndSlideNote;
    }

    public String getCurrentSlideNote(final String meetingId, final String participantBtAddress) {
        MeetingNote meetingNote = presentationDao.findMeetingNote(meetingId);

        final String slideNote;
        if (meetingNote != null && participantBtAddress != null && participantBtAddress.length() > 1 && isRegisteredForMeeting(meetingNote, participantBtAddress)
                && isConnectedToMeeting(meetingNote, participantBtAddress)) {
            slideNote = meetingNote.getCurrentSlideNote();
        } else {
            slideNote = "Could not retrieve note for meeting";
        }

        return (slideNote == null) ? "" : slideNote;
    }

    private boolean isRegisteredForMeeting(final MeetingNote meetingNote, final String btAddress) {
        final boolean registered;
        if (meetingNote.getParticipants() != null && meetingNote.getParticipants().contains(btAddress)) {
            registered = true;
        } else {
            registered = false;
        }

        return registered;
    }

    private boolean isConnectedToMeeting(final MeetingNote meetingNote, final String btAddress) {
        final boolean connected = meetingNote.getConnectedParticipants() != null && meetingNote.getConnectedParticipants().contains(btAddress);

        return connected;
    }
}
