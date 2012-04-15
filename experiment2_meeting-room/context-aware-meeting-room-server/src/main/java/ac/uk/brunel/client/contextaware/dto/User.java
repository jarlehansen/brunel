package ac.uk.brunel.client.contextaware.dto;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 14, 2010
 * Time: 3:55:37 PM
 */
public final class User {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String btAddress;

    public static final User EMPTY_USER = new User("", "", "", "");

    public User(final String email, final String firstName, final String lastName, final String btAddress) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.btAddress = btAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBtAddress() {
        return btAddress;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("User");
        sb.append("{email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", btAddress='").append(btAddress).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
