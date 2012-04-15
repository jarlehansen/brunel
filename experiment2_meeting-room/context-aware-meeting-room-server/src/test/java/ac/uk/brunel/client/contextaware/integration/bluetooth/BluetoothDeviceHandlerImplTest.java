package ac.uk.brunel.client.contextaware.integration.bluetooth;

import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;
import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.TestBluetoothDeviceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 11, 2010
 * Time: 10:14:43 PM
 */
public class BluetoothDeviceHandlerImplTest {
    private BluetoothDeviceHandlerImpl bluetoothDeviceHandler;
    private final BluetoothDevice bluetoothDevice1 = new TestBluetoothDeviceImpl("123456789012", "");
    private final BluetoothDevice bluetoothDevice2 = new TestBluetoothDeviceImpl("000000000000", "bluetoothName");

    @Before
    public void setup() {
        bluetoothDeviceHandler = new BluetoothDeviceHandlerImpl();
    }

    @Test
    public void testAddFoundDevice() {
        bluetoothDeviceHandler.addFoundDevice(bluetoothDevice1);
        bluetoothDeviceHandler.addFoundDevice(bluetoothDevice2);

        assertEquals(2, bluetoothDeviceHandler.getFoundBluetoothDevices().size());
        assertEquals(2, bluetoothDeviceHandler.getNewBluetoothDevices().size());
    }

    @Test
    public void testAddFoundDeviceDuplicate() {
        bluetoothDeviceHandler.addFoundDevice(bluetoothDevice1);
        bluetoothDeviceHandler.addFoundDevice(bluetoothDevice2);
        bluetoothDeviceHandler.addFoundDevice(bluetoothDevice1);
        bluetoothDeviceHandler.addFoundDevice(bluetoothDevice2);

        assertEquals(2, bluetoothDeviceHandler.getFoundBluetoothDevices().size());
        assertEquals(2, bluetoothDeviceHandler.getNewBluetoothDevices().size());
    }

    @Test
    public void testGetNewBluetoothDevices() {
        bluetoothDeviceHandler.addFoundDevice(bluetoothDevice1);
        bluetoothDeviceHandler.addFoundDevice(bluetoothDevice2);

        // This will clear the new devices list, preparing it for new entries
        bluetoothDeviceHandler.getNewBluetoothDevices();

        assertEquals(2, bluetoothDeviceHandler.getFoundBluetoothDevices().size());
        assertEquals(0, bluetoothDeviceHandler.getNewBluetoothDevices().size());
    }

    
}
