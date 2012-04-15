package ac.uk.brunel.server.contextaware.presentation.find;

import ac.uk.brunel.server.contextaware.dto.User;
import ac.uk.brunel.server.contextaware.persistence.user.UserDao;
import ac.uk.brunel.server.contextaware.presentation.BasePage;
import com.google.inject.Inject;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 16, 2010
 * Time: 2:51:09 PM
 */
public class FindUser extends BasePage {
    @Inject
    private UserDao userDao;

    private static final Logger logger = LoggerFactory.getLogger(FindUser.class);

    private static final List<User> userList = new ArrayList<User>();
    private final ListView<User> userListView;

    public FindUser() {
        add(new SearchForm("find_user_form"));

        add(userListView = new ListView<User>("users", userList) {
            @Override
            protected void populateItem(ListItem listItem) {
                final User user = (User) listItem.getModelObject();
                listItem.add(new Label("email", user.getEmail()));
                listItem.add(new Label("firstName", user.getFirstName()));
                listItem.add(new Label("lastName", user.getLastName()));
                listItem.add(new Label("btAddress", user.getBtAddress()));
            }
        });

        add(new FeedbackPanel("validation_messages"));
    }

    private final class SearchForm extends Form {
        private String inputEmail = "";

        public SearchForm(final String componentName) {
            super(componentName);

            TextField<String> input = new RequiredTextField<String>("inputEmail", new PropertyModel<String>(inputEmail, "inputEmail") {
                @Override
                public String getObject() {
                    return inputEmail;
                }

                @Override
                public void setObject(final String inputObject) {
                    inputEmail = inputObject;
                }
            }, String.class);
            input.add(EmailAddressValidator.getInstance());

            add(input);
        }

        @Override
        protected void onError() {
            userList.clear();
        }

        @Override
        protected void onSubmit() {
            User user = userDao.findUser(inputEmail);

            if (logger.isDebugEnabled()) {
                logger.debug("User found: " + user.toString());
            }

            userList.clear();
            userList.add(0, user);
            userListView.modelChanged();

            inputEmail = "";

            setResponsePage(FindUser.class);
        }
    }
}
