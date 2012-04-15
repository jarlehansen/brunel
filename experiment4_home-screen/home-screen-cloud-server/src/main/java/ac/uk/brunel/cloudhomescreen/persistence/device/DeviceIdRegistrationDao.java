package ac.uk.brunel.cloudhomescreen.persistence.device;

import ac.uk.brunel.cloudhomescreen.dto.DeviceId;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 9:15 PM - 1/7/11
 */
public interface DeviceIdRegistrationDao {
    public void persistDeviceId(final DeviceId deviceId);
}
