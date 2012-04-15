package ac.uk.brunel.cloudhomescreen.service;

import ac.uk.brunel.cloudhomescreen.config.sources.MessageType;
import ac.uk.brunel.cloudhomescreen.dto.UserConfiguration;
import ac.uk.brunel.cloudhomescreen.persistence.configuration.UserConfigurationRetrieverDao;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:47 PM - 1/9/11
 */
public class ManualPushServiceImplTest {
    private UserConfigurationRetrieverDao mockedUserConfigurationRetrieverDao;
    private ManualPushService manualPushService;

    private UserConfiguration userConfiguration;
    private final String email = "brunel.nith@gmail.com";

    @Before
    public void setUp() {
        userConfiguration = UserConfiguration.initializeDefaultUserConfiguration(email);

        mockedUserConfigurationRetrieverDao = mock(UserConfigurationRetrieverDao.class);
        when(mockedUserConfigurationRetrieverDao.getConfiguration(email)).thenReturn(userConfiguration);

        manualPushService = new ManualPushServiceImpl(mockedUserConfigurationRetrieverDao);
    }

    @Test
    public void testGetPersistedUserInfoUserApps() {
        String message = manualPushService.getPersistedUserInfo(Integer.toString(MessageType.APPLICATION.getType()), email);

        verify(mockedUserConfigurationRetrieverDao).getConfiguration(email);

        assertTrue(message.length() > 0);
        assertTrue(message.contains("type"));
        assertTrue(message.contains("apps"));
        assertTrue(message.contains("conf"));
    }

    @Test
    public void testGetPersistedUserInfoUserAppsInvalidType() {
        String message = manualPushService.getPersistedUserInfo("invalid type", email);

        assertEquals("", message);
    }

    @Test
    public void testGetPersistedUserInfoUserAppsEmptyEmail() {
        String message = manualPushService.getPersistedUserInfo("invalid type", null);

        assertEquals("", message);
    }
}
