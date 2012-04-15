package ac.uk.brunel.cloudhomescreen.integration;

import ac.uk.brunel.cloudhomescreen.dto.AuthToken;
import ac.uk.brunel.cloudhomescreen.persistence.auth.AuthTokenDao;
import org.junit.Test;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertTrue;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:46 PM - 1/7/11
 */
public class AppEngineAuthenticationImplTest {

    @Test
    public void testGetAuthToken() {
        AuthTokenDao mockedTokenDao = mock(AuthTokenDao.class);

        AppEngineAuthentication auth = new AppEngineAuthenticationImpl(mockedTokenDao);
        String token = auth.getAuthToken();

        verify(mockedTokenDao).persistToken(any(AuthToken.class));

        assertTrue(token.length() > 0);
    }
}
