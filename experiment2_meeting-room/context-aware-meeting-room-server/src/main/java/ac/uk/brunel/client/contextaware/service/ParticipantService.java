package ac.uk.brunel.client.contextaware.service;

import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 6, 2010
 * Time: 10:03:06 PM
 */
public interface ParticipantService {
    public void registerConnectedParticipants(final String meetingId, final List<BluetoothDevice> bluetoothDevices);
}
