package ac.uk.brunel.server.contextaware.presentation.propertymodel;

import ac.uk.brunel.server.contextaware.dto.User;

import java.io.Serializable;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 15, 2010
 * Time: 4:24:18 PM
 */
public class UserWicketPropertyModel implements Serializable {
    private String email = "";
    private String firstName = "";
    private String lastName = "";
    private String btAddress = "";

    public UserWicketPropertyModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBtAddress() {
        return btAddress;
    }

    public void setBtAddress(String btAddress) {
        this.btAddress = btAddress;
    }

    public User getUser() {
        return new User(email, firstName, lastName, btAddress);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UserWicketPropertyModel");
        sb.append("{email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", btAddress='").append(btAddress).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
