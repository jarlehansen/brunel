package ac.uk.brunel.cloudhomescreen.service;

import ac.uk.brunel.cloudhomescreen.dto.UserConfiguration;
import ac.uk.brunel.cloudhomescreen.integration.Cloud2DevicePush;
import ac.uk.brunel.cloudhomescreen.persistence.configuration.UserConfigurationDao;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 9:58 PM - 1/9/11
 */
public class UserConfigurationServiceImplTest {

    @Test
    public void testPushConfiguration() {
        UserConfigurationDao mockedUserConfigurationDao = mock(UserConfigurationDao.class);
        Cloud2DevicePush mockedCloud2DevicePush = mock(Cloud2DevicePush.class);

        UserConfiguration userConfiguration = new UserConfiguration();

        UserConfigurationService userConfigurationService = new UserConfigurationServiceImpl(mockedUserConfigurationDao, mockedCloud2DevicePush);
        userConfigurationService.pushConfiguration(userConfiguration);

        verify(mockedUserConfigurationDao).persistConfiguration(userConfiguration);
        verify(mockedCloud2DevicePush).pushConfiguration(userConfiguration);
    }

}
