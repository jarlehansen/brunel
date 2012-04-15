package ac.uk.brunel.cloudhomescreen.dto;

import javax.persistence.Id;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 9:16 PM - 1/7/11
 */
public class DeviceId {
    @Id
    private String email;
    private String registrationId;


    public DeviceId() {
    }

    public DeviceId(final String email, final String registrationId) {
        this.email = email;
        this.registrationId = registrationId;
    }

    public String getEmail() {
        return email;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DeviceId");
        sb.append("{email='").append(email).append('\'');
        sb.append(", registrationId='").append(registrationId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
