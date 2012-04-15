package ac.uk.brunel.client.contextaware.integration.participant;

import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 16, 2010
 * Time: 8:38:29 PM
 */
public interface MeetingUsersRegistration {
    public void registerConnectedParticipants(final String meetingId, final List<BluetoothDevice> bluetoothDevices);
}
