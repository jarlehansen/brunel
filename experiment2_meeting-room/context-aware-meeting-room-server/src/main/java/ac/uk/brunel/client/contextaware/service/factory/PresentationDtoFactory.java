package ac.uk.brunel.client.contextaware.service.factory;

import ac.uk.brunel.client.contextaware.dto.Meeting;
import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;
import ac.uk.brunel.server.contextaware.presentation.swing.dto.PresentationBluetoothDevice;
import ac.uk.brunel.server.contextaware.presentation.swing.dto.PresentationMeeting;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 11, 2010
 * Time: 5:27:20 PM
 */
public enum PresentationDtoFactory {
    ;

    public static PresentationMeeting createPresentationMeeting(Meeting meetingDto) {
        return new PresentationMeeting(meetingDto.getMeetingId(), meetingDto.getPresentationLink(),
                meetingDto.getPresenter(), meetingDto.getParticipants(), meetingDto.getTitle(),
                meetingDto.getDescription(), meetingDto.getLocation(), meetingDto.getStartTime(), meetingDto.getEndTime());
    }

    public static Meeting createDtoMeeting(PresentationMeeting presentationMeeting) {
        return new Meeting(presentationMeeting.getMeetingId(), presentationMeeting.getPresentationLink(),
                presentationMeeting.getPresenter(), presentationMeeting.getParticipants(), presentationMeeting.getTitle(),
                presentationMeeting.getDescription(), presentationMeeting.getLocation(), presentationMeeting.getStartTime(),
                presentationMeeting.getEndTime());
    }

    public static List<PresentationBluetoothDevice> createPresentationBluetoothDeviceList(List<BluetoothDevice> bluetoothDevices) {
        List<PresentationBluetoothDevice> presentationBluetoothDevices = new ArrayList<PresentationBluetoothDevice>();

        for (BluetoothDevice bluetoothDevice : bluetoothDevices) {
            presentationBluetoothDevices.add(new PresentationBluetoothDevice(bluetoothDevice.getBluetoothAddress(), bluetoothDevice.getName()));
        }

        return presentationBluetoothDevices;
    }
}
