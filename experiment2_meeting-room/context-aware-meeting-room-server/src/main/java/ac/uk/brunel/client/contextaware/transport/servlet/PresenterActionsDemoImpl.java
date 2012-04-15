package ac.uk.brunel.client.contextaware.transport.servlet;

import ac.uk.brunel.client.contextaware.service.PresenterService;
import ac.uk.brunel.contextaware.network.generated.PresenterActionProtobuf;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 20, 2010
 * Time: 5:15:47 PM
 */
@Singleton
public class PresenterActionsDemoImpl extends HttpServlet implements PresenterActionsDemo {
    private static final Logger logger = LoggerFactory.getLogger(PresenterActionsDemoImpl.class);
    private static final String REQ_PARAM = "action";
    private static final String ACTION_NEXT = "next";
    private static final String ACTION_PREV = "prev";

    private final Provider<PresenterService> presenterServiceProvider;

    @Inject
    public PresenterActionsDemoImpl(final Provider<PresenterService> presenterServiceProvider) {
        this.presenterServiceProvider = presenterServiceProvider;
    }

    public HttpServlet getServletObject() {
        return this;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        createPresenterAction(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        createPresenterAction(request, response);
    }

    private void createPresenterAction(final HttpServletRequest request, final HttpServletResponse response) {
        createHtmlPage(response);
        createPresenterActions(request);
    }

    private void createHtmlPage(final HttpServletResponse response) {
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.println("<html><body>");

            out.println("<form action=\"/presenterActionsDemo\" method=\"GET\">");
            out.println("<input type=\"hidden\" name=\"action\" value=\"prev\" />");
            out.println("<input type=\"submit\" value=\"Previous slide\" style=\"height: 35px; width: 200px\" />");
            out.println("</form>");

            out.println("<form action=\"/presenterActionsDemo\" method=\"GET\">");
            out.println("<input type=\"hidden\" name=\"action\" value=\"next\" />");
            out.println("<input type=\"submit\" value=\"Next slide\" style=\"height: 35px; width: 200px\" />");
            out.println("</form>");

            out.println("</body></html>");
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("Unable to open PrintWriter in response object", io);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    void createPresenterActions(final HttpServletRequest request) {
        final String action = request.getParameter(REQ_PARAM);

        if (action != null) {
            final PresenterActionProtobuf.PresenterAction presenterAction =
                    PresenterActionProtobuf.PresenterAction.newBuilder().setAction(action).build();

            final PresenterService presenterService = presenterServiceProvider.get();
            presenterService.performPresenterAction(presenterAction);
        }
    }
}
