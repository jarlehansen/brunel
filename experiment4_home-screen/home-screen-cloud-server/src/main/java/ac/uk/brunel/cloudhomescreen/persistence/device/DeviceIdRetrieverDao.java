package ac.uk.brunel.cloudhomescreen.persistence.device;

import ac.uk.brunel.cloudhomescreen.dto.DeviceId;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:15 PM - 1/7/11
 */
public interface DeviceIdRetrieverDao {
    public DeviceId getDeviceId(final String email);
}
