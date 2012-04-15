package ac.uk.brunel.server.contextaware.presentation.list;

import ac.uk.brunel.server.contextaware.persistence.user.UserDao;
import ac.uk.brunel.server.contextaware.presentation.BasePage;
import com.google.inject.Inject;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 16, 2010
 * Time: 7:04:01 PM
 */
public class DeleteUser extends BasePage {
    @Inject
    private UserDao userDao;

    private static final Logger logger = LoggerFactory.getLogger(DeleteUser.class);

    public DeleteUser(final PageParameters pageParams) {
        final String email = pageParams.getString("email");
        add(new Label("email", email));

        final TextField<String> input = new PasswordTextField("AdminPassword", new Model<String>());
        input.setRequired(true);

        final Form<String> deleteUserForm = new Form<String>("deleteUserForm") {
            @Override
            protected void onSubmit() {
                userDao.deleteUser(input.getInput(), email);
                setResponsePage(ListUsers.class);
            }
        };

        deleteUserForm.add(input);
        add(deleteUserForm);
        add(new FeedbackPanel("validation_messages"));
    }
}
