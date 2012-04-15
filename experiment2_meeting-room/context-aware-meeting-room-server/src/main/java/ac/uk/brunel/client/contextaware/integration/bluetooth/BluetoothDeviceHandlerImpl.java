package ac.uk.brunel.client.contextaware.integration.bluetooth;

import ac.uk.brunel.client.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 11, 2010
 * Time: 9:42:24 PM
 */
@LoggingAspect
@Singleton
public class BluetoothDeviceHandlerImpl implements BluetoothDeviceHandler {
    private static final Logger logger = LoggerFactory.getLogger(BluetoothDeviceHandlerImpl.class);

    private final List<BluetoothDevice> foundBluetoothDevices = new ArrayList<BluetoothDevice>();
    private final List<BluetoothDevice> newBluetoothDevices = new ArrayList<BluetoothDevice>();
    private final Lock bluetoothDevicesLock = new ReentrantLock();

    private final AtomicBoolean devicePingFinished = new AtomicBoolean(false);

    public BluetoothDeviceHandlerImpl() {
    }

    /**
     * For unit tests only!
     */
    List<BluetoothDevice> getFoundBluetoothDevices() {
        return foundBluetoothDevices;
    }

    public void addFoundDevice(final BluetoothDevice bluetoothDevice) {
        bluetoothDevicesLock.lock();

        try {
            if (isNewDevice(bluetoothDevice.getBluetoothAddress())) {
                foundBluetoothDevices.add(bluetoothDevice);
                newBluetoothDevices.add(bluetoothDevice);
            }
        } finally {
            bluetoothDevicesLock.unlock();
        }
    }

    private boolean isNewDevice(final String btAddress) {
        boolean isNewDevice = true;

        for (BluetoothDevice tempDevice : foundBluetoothDevices) {
            if (tempDevice.getBluetoothAddress().equals(btAddress)) {
                isNewDevice = false;
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("The device: " + btAddress + " is new? " + isNewDevice);
        }

        return isNewDevice;
    }

    public List<BluetoothDevice> getNewBluetoothDevices() {
        final List<BluetoothDevice> copyOfNewBluetoothDevices;

        bluetoothDevicesLock.lock();
        try {
            copyOfNewBluetoothDevices = new ArrayList<BluetoothDevice>(newBluetoothDevices);
            newBluetoothDevices.clear();
        } finally {
            bluetoothDevicesLock.unlock();
        }

        return copyOfNewBluetoothDevices;
    }

    public void bluetoothDevicePingFinished() {
        devicePingFinished.set(true);
    }

    public boolean isBluetoothDevicePingFinished() {
        return devicePingFinished.get();
    }
}
