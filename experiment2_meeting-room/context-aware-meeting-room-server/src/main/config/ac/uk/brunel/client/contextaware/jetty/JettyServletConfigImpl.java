package ac.uk.brunel.client.contextaware.jetty;

import ac.uk.brunel.client.contextaware.properties.JettyConstants;
import ac.uk.brunel.client.contextaware.properties.PropertiesReader;
import ac.uk.brunel.client.contextaware.transport.servlet.PingServlet;
import ac.uk.brunel.client.contextaware.transport.servlet.PresenterActions;
import ac.uk.brunel.client.contextaware.transport.servlet.PresenterActionsDemo;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 18, 2010
 * Time: 5:23:15 PM
 */
@Singleton
public class JettyServletConfigImpl implements JettyServletConfig {
    private static final Logger logger = LoggerFactory.getLogger(JettyServletConfigImpl.class);
    private final int port = Integer.parseInt(PropertiesReader.JETTY.get(JettyConstants.PORT));

    private final PresenterActions presenterActions;
    private final PingServlet pingServlet;
    private final PresenterActionsDemo presenterActionsDemo;

    private Server server;

    @Inject
    public JettyServletConfigImpl(final PresenterActions presenterActions, final PingServlet pingServlet, final PresenterActionsDemo presenterActionsDemo) {
        this.presenterActions = presenterActions;
        this.pingServlet = pingServlet;
        this.presenterActionsDemo = presenterActionsDemo;
    }

    public void startJettyServer() {
        server = new Server(port);
        Context servletContext = new Context(server, "/");

        servletContext.addServlet(new ServletHolder(presenterActions.getServletObject()), "/presenterActions");
        servletContext.addServlet(new ServletHolder(pingServlet.getServletObject()), "/connectionPing");
        servletContext.addServlet(new ServletHolder(presenterActionsDemo.getServletObject()), "/presenterActionsDemo");

        try {
            server.start();

            if(logger.isInfoEnabled()) {
                logger.info("Jetty server started on port: " + port);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Exception in the embedded Jetty server", e);
            }

            throw new RuntimeException("Exception in the embedded Jetty server", e);
        }
    }
}
