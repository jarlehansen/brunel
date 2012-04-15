package ac.uk.brunel.benchmark.server.servlet;

import ac.uk.brunel.benchmark.server.sender.MessageSenderFacade;
import ac.uk.brunel.benchmark.server.sender.PushMessage;

import javax.inject.Provider;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:21 PM - 11/22/11
 */
public class AbstractMessageSenderServlet extends HttpServlet {
    protected Provider<MessageSenderFacade> messageSenderFacadeProvider;

    protected String tech;
    protected String email;
    protected String regId;

    public AbstractMessageSenderServlet(Provider<MessageSenderFacade> messageSenderFacadeProvider) {
        this.messageSenderFacadeProvider = messageSenderFacadeProvider;
    }

    protected void populateFields(HttpServletRequest request) {
        String tmpTech = request.getParameter("tech");
        if (tmpTech != null)
            tech = tmpTech;
        else
            tech = "";

        String tmpEmail = request.getParameter("email");
        if (tmpEmail != null)
            email = tmpEmail;
        else
            email = "";

        String tmpRegId = request.getParameter("regId");
        if (tmpRegId != null)
            regId = tmpRegId;
        else
            regId = "";
    }

    protected boolean validInput() {
        return (!"".equals(tech) && !"".equals(email));
    }

    protected void sendMessage(String msg) {
        messageSenderFacadeProvider.get().sendMessage(new PushMessage(tech, email, msg, regId));
    }
}
