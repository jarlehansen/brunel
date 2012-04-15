package ac.uk.brunel.server.contextaware.config;

import ac.uk.brunel.server.contextaware.presentation.HomePage;
import ac.uk.brunel.server.contextaware.presentation.error.ErrorPageTester;
import ac.uk.brunel.server.contextaware.presentation.find.FindUser;
import ac.uk.brunel.server.contextaware.presentation.list.ListUsers;
import ac.uk.brunel.server.contextaware.presentation.meeting.ListMeetings;
import ac.uk.brunel.server.contextaware.presentation.notes.DeleteMeetingNotes;
import ac.uk.brunel.server.contextaware.presentation.register.RegisterUser;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.session.ISessionStore;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 16, 2010
 * Time: 12:09:07 AM
 */
public class WicketWebApplicationSettings extends WebApplication {

    @Override
    protected void init() {
        super.init();
        getResourceSettings().setResourcePollFrequency(null);
        addComponentInstantiationListener(new GuiceComponentInjector(this));

        mountBookmarkablePage("home", HomePage.class);
        mountBookmarkablePage("registerUser", RegisterUser.class);
        mountBookmarkablePage("listUsers", ListUsers.class);
        mountBookmarkablePage("findUser", FindUser.class);
        mountBookmarkablePage("listMeetings", ListMeetings.class);
        mountBookmarkablePage("deleteMeetingNotes", DeleteMeetingNotes.class);

        mountBookmarkablePage("errorTester", ErrorPageTester.class);
    }

    @Override
    protected ISessionStore newSessionStore() {
        return new HttpSessionStore(this);
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    public RequestCycle newRequestCycle(Request request, Response response) {
        return new WicketRuntimeExceptionHandler(this, (WebRequest) request, response);
    }
}
