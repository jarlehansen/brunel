package ac.uk.brunel.cloudhomescreen.integration;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:45 PM - 1/7/11
 */
public interface AppEngineAuthentication {
    public String getAuthToken();
    public void persistToken(final String updatedToken);
}
