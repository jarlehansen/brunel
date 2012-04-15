package ac.uk.brunel.cloudhomescreen.dto;

import ac.uk.brunel.cloudhomescreen.constants.HomeScreenConstants;

import javax.persistence.Id;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:01 PM - 1/8/11
 */
public class AuthToken {
    @Id
    private String key = HomeScreenConstants.AUTH_TOKEN_KEY;
    private String token;

    public AuthToken() {
    }

    public AuthToken(final String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AuthToken");
        sb.append("{key='").append(key).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
