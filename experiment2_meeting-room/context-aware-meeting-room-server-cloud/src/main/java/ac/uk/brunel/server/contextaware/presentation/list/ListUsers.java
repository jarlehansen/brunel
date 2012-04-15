package ac.uk.brunel.server.contextaware.presentation.list;

import ac.uk.brunel.server.contextaware.dto.User;
import ac.uk.brunel.server.contextaware.persistence.user.UserDao;
import ac.uk.brunel.server.contextaware.presentation.BasePage;
import com.google.inject.Inject;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 16, 2010
 * Time: 12:19:41 AM
 */
public class ListUsers extends BasePage {
    @Inject
    private UserDao userDao;

    public ListUsers() {
        createPage();
    }

    /**
     * Should only be used for testing!
     */
    ListUsers(final UserDao userDao) {
        this.userDao = userDao;
        createPage();
    }

    private void createPage() {
        final List<User> userList = userDao.getAllUsers();

        ListView<User> userListView = new ListView<User>("user_list", userList) {
            @Override
            protected void populateItem(ListItem<User> userListItem) {
                final String email = userListItem.getModelObject().getEmail();
                final PageParameters pageParams = new PageParameters();
                pageParams.put("email", email);

                userListItem.add(new Label("email", email));
                userListItem.add(new Label("firstName", userListItem.getModelObject().getFirstName()));
                userListItem.add(new Label("lastName", userListItem.getModelObject().getLastName()));
                userListItem.add(new Label("btAddress", userListItem.getModelObject().getBtAddress()));
                userListItem.add(new BookmarkablePageLink<String>("deleteUserLink", DeleteUser.class, pageParams));
            }
        };

        add(userListView);
    }
}
