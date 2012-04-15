package ac.uk.brunel.benchmark.server.persistence;

import javax.persistence.Id;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:08 PM - 10/6/11
 */
public class GoogleAuth {
    @Id
    private String id = "AUTH_TOKEN";
    private String token;

    public GoogleAuth() {
    }

    public GoogleAuth(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
