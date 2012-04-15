package ac.uk.brunel.client.contextaware.integration.bluetooth.ping;

import ac.uk.brunel.client.contextaware.integration.bluetooth.BluetoothDeviceHandler;
import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;
import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDeviceImpl;
import ac.uk.brunel.client.contextaware.properties.BluetoothConstants;
import ac.uk.brunel.client.contextaware.properties.PropertiesReader;
import com.google.inject.Inject;
import com.intel.bluetooth.BlueCoveConfigProperties;
import com.intel.bluetooth.BlueCoveImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.bluetooth.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 12, 2010
 * Time: 6:00:28 PM
 */
public class BluetoothDevicePingImpl implements BluetoothDevicePing {
    private static final UUID BT_SERVICE = new UUID(Long.parseLong(PropertiesReader.BT_SERVICE.get(BluetoothConstants.SERVICE_UUID), 16));

    private static final AtomicBoolean continueSearch = new AtomicBoolean(true);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final BluetoothDeviceHandler bluetoothDeviceHandler;

    @Inject
    public BluetoothDevicePingImpl(final BluetoothDeviceHandler bluetoothDeviceHandler) {
        this.bluetoothDeviceHandler = bluetoothDeviceHandler;

        BlueCoveImpl.setConfigProperty(BlueCoveConfigProperties.PROPERTY_DEBUG_STDOUT, "false");
        BlueCoveImpl.setConfigProperty(BlueCoveConfigProperties.PROPERTY_DEBUG_LOG4J, "false");
        BlueCoveImpl.setConfigProperty(BlueCoveConfigProperties.PROPERTY_DEBUG, "false");

        verify32BitMode();
    }

    private void verify32BitMode() {
        String os = System.getProperty("os.name");

        if (os != null && os.contains("Mac")) {
            String arch = System.getProperty("os.arch");
            if (!"i386".equals(arch)) {
                throw new IllegalStateException("Unable to run Bluetooth under 64bit mode, remember to set VM parameter: -d32");
            }
        }
    }

    public void startSearch(final List<String> registeredBluetoothDevices) {
        if (registeredBluetoothDevices.size() == 0) {
            bluetoothDeviceHandler.bluetoothDevicePingFinished();
        } else {
            executorService.execute(new BluetoothDevicePingInternal(bluetoothDeviceHandler, registeredBluetoothDevices));
        }
    }

    public void stopSearch() {
        continueSearch.set(false);
        executorService.shutdown();
    }

    private static class BluetoothDevicePingInternal implements Runnable, DiscoveryListener {
        private static final Logger logger = LoggerFactory.getLogger(BluetoothDevicePingInternal.class);

        private final BluetoothDeviceHandler bluetoothDeviceHandler;
        private final List<String> bluetoothDevices;

        private final Object lock = new Object();
        private final AtomicBoolean devicePingCompleted = new AtomicBoolean();


        public BluetoothDevicePingInternal(final BluetoothDeviceHandler bluetoothDeviceHandler, final List<String> bluetoothDevices) {
            this.bluetoothDeviceHandler = bluetoothDeviceHandler;
            this.bluetoothDevices = bluetoothDevices;
        }

        public void run() {
            if (logger.isInfoEnabled()) {
                logger.info("Starting Bluetooth device search");
            }

            int counter = 0;
            while (continueSearch.get() && bluetoothDevices.size() > 0) {
                try {
                    if (counter >= bluetoothDevices.size()) {
                        counter = 0;
                    }

                    devicePingCompleted.set(false);
                    String btAddress = bluetoothDevices.get(counter);

                    LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(null, new UUID[]{BT_SERVICE}, new BluetoothDeviceImpl(btAddress), this);

                    if (logger.isDebugEnabled()) {
                        logger.debug("Searching for: " + btAddress);
                    }


                    while (!devicePingCompleted.get()) {
                        synchronized (lock) {
                            if (!devicePingCompleted.get()) {
                                lock.wait();
                            }
                        }
                    }
                } catch (InterruptedException ie) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Interrupted exception when waiting for device search thread to notify", ie);
                    }
                } catch (BluetoothStateException bse) {
                    if (logger.isErrorEnabled()) {
                        logger.error("Bluetooth exception during service search", bse);
                    }
                } finally {
                    counter++;
                }
            }

            if (bluetoothDevices.size() == 0) {
                bluetoothDeviceHandler.bluetoothDevicePingFinished();

                if (logger.isInfoEnabled()) {
                    logger.info("No more registered participants, ending search for BluetoothDevices");
                }
            }
        }

        public void servicesDiscovered(int transactionId, ServiceRecord[] serviceRecords) {
            if (serviceRecords.length > 0) {
                final RemoteDevice remoteDevice = serviceRecords[0].getHostDevice();

                if (logger.isDebugEnabled()) {
                    logger.debug("Bluetooth device and service found: " + remoteDevice.getBluetoothAddress());
                }

                final BluetoothDevice bluetoothDevice = createBluetoothDevice(remoteDevice);
                bluetoothDeviceHandler.addFoundDevice(bluetoothDevice);
                removeFoundBluetoothDeviceFromPingList(bluetoothDevice.getBluetoothAddress());
            }
        }

        private BluetoothDevice createBluetoothDevice(final RemoteDevice remoteDevice) {
            String name = "";
            try {
                name = remoteDevice.getFriendlyName(true);

                if (logger.isDebugEnabled()) {
                    logger.debug("Bluetooth name found: " + name);
                }
            } catch (IOException io) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Unable to retrieve friendly name for " + remoteDevice.getBluetoothAddress());
                }
            }

            final BluetoothDevice bluetoothDevice;
            if (name == null || "".equals(name)) {
                bluetoothDevice = new BluetoothDeviceImpl(remoteDevice.getBluetoothAddress());
            } else {
                bluetoothDevice = new BluetoothDeviceImpl(remoteDevice.getBluetoothAddress(), name);
            }

            return bluetoothDevice;
        }

        private synchronized void removeFoundBluetoothDeviceFromPingList(String foundBtAddress) {
            foundBtAddress = foundBtAddress.toUpperCase();

            for (int i = 0; i < bluetoothDevices.size(); i++) {
                String btAddress = bluetoothDevices.get(i).toUpperCase();

                if (btAddress.equals(foundBtAddress)) {
                    bluetoothDevices.remove(i);

                    if (logger.isDebugEnabled()) {
                        logger.debug("Removed " + foundBtAddress + " from Bluetooth devices list");
                    }

                    break;
                }
            }
        }

        public void serviceSearchCompleted(int transactionId, int responseCode) {
            synchronized (lock) {
                devicePingCompleted.set(true);
                lock.notifyAll();
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Bluetooth device search completed");
            }
        }


        public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
        }

        public void inquiryCompleted(int discType) {
        }
    }
}
