package ac.uk.brunel.cloudhomescreen.persistence.auth;

import ac.uk.brunel.cloudhomescreen.dto.AuthToken;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 1:58 PM - 1/8/11
 */
public interface AuthTokenDao {
    public void persistToken(final AuthToken token);
    public AuthToken getToken();
}
