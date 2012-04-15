package ac.uk.brunel.client.contextaware.integration.bluetooth.ping;

import ac.uk.brunel.client.contextaware.integration.bluetooth.BluetoothDeviceHandler;
import ac.uk.brunel.client.contextaware.integration.bluetooth.BluetoothDeviceHandlerImpl;
import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * REMEMBER TO SET THE -d32 SYSTEM PROPERTY BEFORE RUNNING TESTS!
 *
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 13, 2010
 * Time: 4:21:46 PM
 */
public class BluetoothDeviceSearchImplIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(BluetoothDeviceSearchImplIntegrationTest.class);

    private BluetoothDeviceHandler bluetoothDeviceHandler;
    private BluetoothDevicePing bluetoothDevicePing;


    @Before
    public void setup() {
        bluetoothDeviceHandler = new BluetoothDeviceHandlerImpl();
        bluetoothDevicePing = new BluetoothDevicePingImpl(bluetoothDeviceHandler);
    }
    
    @Test
    public void testStartBluetoothDeviceSearch() {
        List<String> registeredBluetoothDevices = new ArrayList<String>();
//        registeredBluetoothDevices.add("000000000000");
        registeredBluetoothDevices.add("001FF3930131");
//        registeredBluetoothDevices.add("000000000001");
//        registeredBluetoothDevices.add("0022A5F7F19B");
//        registeredBluetoothDevices.add("000000000003");
//        registeredBluetoothDevices.add("000000000004");
//        registeredBluetoothDevices.add("000000000005");
//        registeredBluetoothDevices.add("000000000006");
//        registeredBluetoothDevices.add("0012D2ADE5F7");
//        registeredBluetoothDevices.add("000000000007");

        bluetoothDevicePing.startSearch(registeredBluetoothDevices);

        try {
//            Thread.sleep(100000);
            Thread.sleep(3500);
        } catch (InterruptedException ie) {
        }

        bluetoothDevicePing.stopSearch();

        List<BluetoothDevice> bluetoothDevices = bluetoothDeviceHandler.getNewBluetoothDevices();
        for (BluetoothDevice bluetoothDevice : bluetoothDevices) {
            logger.debug(bluetoothDevice.toString());
        }
    }
}
