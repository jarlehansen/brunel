package ac.uk.brunel.cloudhomescreen.integration;

import ac.uk.brunel.cloudhomescreen.dto.UserConfiguration;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:07 PM - 1/8/11
 */
public interface Cloud2DevicePush {
    public void pushConfiguration(final UserConfiguration userConfiguration);
}
