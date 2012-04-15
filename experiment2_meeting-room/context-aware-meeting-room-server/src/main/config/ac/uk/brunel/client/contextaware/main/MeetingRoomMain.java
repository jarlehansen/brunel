package ac.uk.brunel.client.contextaware.main;

import ac.uk.brunel.client.contextaware.config.ServerAppModule;
import ac.uk.brunel.client.contextaware.jetty.JettyServletConfig;
import ac.uk.brunel.server.contextaware.presentation.swing.ServerGuiActions;
import ac.uk.brunel.server.contextaware.presentation.swing.impl.ServerGuiImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 6, 2010
 * Time: 6:50:27 PM
 */
public class MeetingRoomMain {

    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new ServerAppModule());
        final JettyServletConfig jettyServletConfig = injector.getInstance(JettyServletConfig.class);
        final ServerGuiActions serverGuiActions = injector.getInstance(ServerGuiActions.class);

        jettyServletConfig.startJettyServer();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerGuiImpl(serverGuiActions);
            }
        });
    }
}
