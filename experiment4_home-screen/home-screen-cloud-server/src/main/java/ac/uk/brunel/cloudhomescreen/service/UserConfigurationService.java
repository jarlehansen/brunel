package ac.uk.brunel.cloudhomescreen.service;

import ac.uk.brunel.cloudhomescreen.dto.UserConfiguration;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:18 PM - 1/7/11
 */
public interface UserConfigurationService {
    public UserConfiguration getConfiguration(final String user);
    public void pushConfiguration(final UserConfiguration userConfiguration);
}
