package ac.uk.brunel.cloudhomescreen.persistence.configuration;

import ac.uk.brunel.cloudhomescreen.dto.UserConfiguration;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:29:47 PM - Nov 20, 2010
 */
public interface UserConfigurationDao {
    public void persistConfiguration(final UserConfiguration userConfiguration);
    public UserConfiguration getConfiguration(final String user);
    public void deleteAll();
}
