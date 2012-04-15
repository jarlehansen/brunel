package ac.uk.brunel.client.contextaware.integration.bluetooth.dto;

import javax.bluetooth.RemoteDevice;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 11, 2010
 * Time: 9:44:01 PM
 */
public class BluetoothDeviceImpl extends RemoteDevice implements BluetoothDevice {
    private String name = "";

    public BluetoothDeviceImpl(String btAddress) {
        super(btAddress);
    }

    public BluetoothDeviceImpl(final String btAddress, final String name) {
        super(btAddress);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("BluetoothRemoteDevice");
        builder.append("{BluetoothAddress='").append(super.getBluetoothAddress()).append('\'');

        if(name != null && !"".equals(name)) {
            builder.append(", name='").append(name).append('\'');
        }

        builder.append('}');

        return builder.toString();
    }
}
