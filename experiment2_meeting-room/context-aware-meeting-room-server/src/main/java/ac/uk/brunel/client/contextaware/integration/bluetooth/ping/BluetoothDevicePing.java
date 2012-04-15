package ac.uk.brunel.client.contextaware.integration.bluetooth.ping;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 12, 2010
 * Time: 6:00:21 PM
 */
public interface BluetoothDevicePing {
    public void startSearch(List<String> registeredBluetoothDevices);
    public void stopSearch();
}
