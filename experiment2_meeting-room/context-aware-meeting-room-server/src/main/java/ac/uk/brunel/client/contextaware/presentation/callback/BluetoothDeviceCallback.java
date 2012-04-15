package ac.uk.brunel.client.contextaware.presentation.callback;

import ac.uk.brunel.server.contextaware.presentation.swing.ServerGuiCallback;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 12, 2010
 * Time: 6:04:27 PM
 */
public interface BluetoothDeviceCallback {
    public void setServerGuiCallback(final ServerGuiCallback serverGuiCallback);
    public void startBackgroundThread(final String meetingId);
}
