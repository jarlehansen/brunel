package ac.uk.brunel.cloudhomescreen.persistence.configuration;

import ac.uk.brunel.cloudhomescreen.dto.UserConfiguration;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:07 PM - 1/9/11
 */
public interface UserConfigurationRetrieverDao {
    public UserConfiguration getConfiguration(final String user);
}
