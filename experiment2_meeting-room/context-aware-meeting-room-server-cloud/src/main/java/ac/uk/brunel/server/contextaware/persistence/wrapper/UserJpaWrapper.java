package ac.uk.brunel.server.contextaware.persistence.wrapper;

import ac.uk.brunel.server.contextaware.dto.User;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 14, 2010
 * Time: 5:22:38 PM
 */
@Entity
public class UserJpaWrapper {
    @Id
    private String email;

    private String firstName;
    private String lastName;
    private String btAddress;

    /*
    * Should only be used by JPA!
    */
    protected UserJpaWrapper() {
    }

    public UserJpaWrapper(final User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.btAddress = user.getBtAddress();
    }

    public User getUser() {
        return new User(email, firstName, lastName, btAddress);
    }
}
