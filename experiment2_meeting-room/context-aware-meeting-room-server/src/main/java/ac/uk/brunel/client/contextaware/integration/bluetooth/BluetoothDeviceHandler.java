package ac.uk.brunel.client.contextaware.integration.bluetooth;

import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 11, 2010
 * Time: 9:42:15 PM
 */
public interface BluetoothDeviceHandler {
    public void addFoundDevice(final BluetoothDevice bluetoothDevice);
    public List<BluetoothDevice> getNewBluetoothDevices();
    public void bluetoothDevicePingFinished();
    public boolean isBluetoothDevicePingFinished();
}
