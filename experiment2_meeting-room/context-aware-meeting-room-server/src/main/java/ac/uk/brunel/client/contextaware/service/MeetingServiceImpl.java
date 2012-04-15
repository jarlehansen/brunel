package ac.uk.brunel.client.contextaware.service;

import ac.uk.brunel.client.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.client.contextaware.dto.Meeting;
import ac.uk.brunel.client.contextaware.integration.bluetooth.ping.BluetoothDevicePing;
import ac.uk.brunel.client.contextaware.integration.meeting.MeetingRegistration;
import ac.uk.brunel.client.contextaware.integration.meeting.PresenterMeetingListReceiver;
import ac.uk.brunel.client.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.client.contextaware.properties.PropertiesReader;
import ac.uk.brunel.client.contextaware.service.factory.PresentationDtoFactory;
import ac.uk.brunel.server.contextaware.presentation.swing.dto.PresentationMeeting;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 6, 2010
 * Time: 10:01:50 PM
 */
@LoggingAspect
public class MeetingServiceImpl implements MeetingService {
    private final PresenterMeetingListReceiver presenterMeetingListReceiver;
    private final MeetingRegistration meetingRegistration;
    private final BluetoothDevicePing bluetoothDevicePing;
    private final MeetingStateHandler meetingStateHandler;

    @Inject
    public MeetingServiceImpl(final PresenterMeetingListReceiver presenterMeetingListReceiver, final MeetingRegistration meetingRegistration,
                              final BluetoothDevicePing bluetoothDevicePing, final MeetingStateHandler meetingStateHandler) {
        this.presenterMeetingListReceiver = presenterMeetingListReceiver;
        this.meetingRegistration = meetingRegistration;
        this.bluetoothDevicePing = bluetoothDevicePing;
        this.meetingStateHandler = meetingStateHandler;
    }

    public List<PresentationMeeting> getPresenterMeetingList(final String email) {
        final List<Meeting> meetingList = presenterMeetingListReceiver.getPresenterMeetingList(email);
        final List<PresentationMeeting> presentationMeetingList = new ArrayList<PresentationMeeting>();

        for (Meeting meeting : meetingList) {
            presentationMeetingList.add(PresentationDtoFactory.createPresentationMeeting(meeting));
        }

        return presentationMeetingList;
    }

    public void registerMeeting(final PresentationMeeting presentationMeeting) {
        final Meeting meeting = PresentationDtoFactory.createDtoMeeting(presentationMeeting);
        final List<String> registeredBtAddresses = meetingRegistration.registerMeetingAndGetRegisteredParticipants(meeting);
        meetingStateHandler.setStartedMeeting(meeting);
        bluetoothDevicePing.startSearch(removeAlwaysAcceptedBtAddress(registeredBtAddresses));
    }

    List<String> removeAlwaysAcceptedBtAddress(final List<String> registeredBtAddresses) {
        final String btAddressAlwaysAccept = PropertiesReader.SERVER_APP.get(PropertiesConstants.BLUETOOTH_ADDRESS_ALWAYS_ACCEPT);

        if (!"".equals(btAddressAlwaysAccept)) {
            for (int i = 0; i < registeredBtAddresses.size(); i++) {
                final String btAddress = registeredBtAddresses.get(i);
                if (btAddressAlwaysAccept.equals(btAddress)) {
                    registeredBtAddresses.remove(i);
                    break;
                }
            }
        }

        return registeredBtAddresses;
    }
}
