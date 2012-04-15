package ac.uk.brunel.client.contextaware.presentation.callback;

import ac.uk.brunel.client.contextaware.integration.bluetooth.BluetoothDeviceHandler;
import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;
import ac.uk.brunel.client.contextaware.service.ParticipantService;
import ac.uk.brunel.client.contextaware.service.factory.PresentationDtoFactory;
import ac.uk.brunel.server.contextaware.presentation.swing.ServerGuiCallback;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 12, 2010
 * Time: 6:04:41 PM
 */
public class BluetoothDeviceCallbackImpl implements BluetoothDeviceCallback {
    private static final Logger logger = LoggerFactory.getLogger(BluetoothDeviceCallbackImpl.class);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final AtomicBoolean runInBackground = new AtomicBoolean(true);

    private final BluetoothDeviceHandler bluetoothDeviceHandler;
    private final ParticipantService participantService;

    private ServerGuiCallback serverGuiCallback;

    @Inject
    public BluetoothDeviceCallbackImpl(final BluetoothDeviceHandler bluetoothDeviceHandler, final ParticipantService participantService) {
        this.bluetoothDeviceHandler = bluetoothDeviceHandler;
        this.participantService = participantService;
    }

    public void startBackgroundThread(final String meetingId) {
        if (meetingId != null && !"".equals(meetingId) && !bluetoothDeviceHandler.isBluetoothDevicePingFinished()) {
            final CallbackBackgroundThread backgroundThread = new CallbackBackgroundThread(meetingId, serverGuiCallback, bluetoothDeviceHandler, participantService);
            executorService.execute(backgroundThread);

            if (logger.isInfoEnabled()) {
                logger.info("Starting callback background thread");
            }
        }
    }

    public void stopBackgroundThread() {
        runInBackground.set(false);
        executorService.shutdown();
    }

    public void setServerGuiCallback(final ServerGuiCallback serverGuiCallback) {
        this.serverGuiCallback = serverGuiCallback;
    }

    private static class CallbackBackgroundThread implements Runnable {
        private final String meetingId;
        private final ServerGuiCallback serverGuiCallback;
        private final BluetoothDeviceHandler bluetoothDeviceHandler;
        private final ParticipantService participantService;

        public CallbackBackgroundThread(final String meetingId, final ServerGuiCallback serverGuiCallback,
                                        final BluetoothDeviceHandler bluetoothDeviceHandler, final ParticipantService participantService) {
            this.meetingId = meetingId;
            this.serverGuiCallback = serverGuiCallback;
            this.bluetoothDeviceHandler = bluetoothDeviceHandler;
            this.participantService = participantService;
        }

        public void run() {
            while (runInBackground.get() && !bluetoothDeviceHandler.isBluetoothDevicePingFinished()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("The Bluetooth callback background thread was interrupted", ie);
                    }
                }

                sendUpdatedParticipantsList();
            }

            // If any devices have been found after the loop is finished they should be added to the UI and cloud server
            sendUpdatedParticipantsList();

            if (logger.isDebugEnabled() && bluetoothDeviceHandler.isBluetoothDevicePingFinished()) {
                logger.debug("Stopping CallbackBackgroundThread, there are no more Bluetooth devices not connected");
            }
        }

        private void sendUpdatedParticipantsList() {
            List<BluetoothDevice> bluetoothDevices = bluetoothDeviceHandler.getNewBluetoothDevices();

            if (bluetoothDevices.size() > 0) {
                serverGuiCallback.setConnectedClients(PresentationDtoFactory.createPresentationBluetoothDeviceList(bluetoothDevices));
                participantService.registerConnectedParticipants(meetingId, bluetoothDevices);
            }
        }
    }
}
