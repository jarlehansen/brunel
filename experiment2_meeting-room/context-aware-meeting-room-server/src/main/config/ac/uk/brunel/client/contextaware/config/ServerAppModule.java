package ac.uk.brunel.client.contextaware.config;

import ac.uk.brunel.client.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.client.contextaware.aop.AopLoggerInterceptor;
import ac.uk.brunel.client.contextaware.integration.bluetooth.BluetoothDeviceHandler;
import ac.uk.brunel.client.contextaware.integration.bluetooth.BluetoothDeviceHandlerImpl;
import ac.uk.brunel.client.contextaware.integration.bluetooth.ping.BluetoothDevicePing;
import ac.uk.brunel.client.contextaware.integration.bluetooth.ping.BluetoothDevicePingImpl;
import ac.uk.brunel.client.contextaware.integration.keyboardevent.KeyboardEventHandler;
import ac.uk.brunel.client.contextaware.integration.keyboardevent.KeyboardEventHandlerImpl;
import ac.uk.brunel.client.contextaware.integration.meeting.MeetingRegistration;
import ac.uk.brunel.client.contextaware.integration.meeting.MeetingRegistrationImpl;
import ac.uk.brunel.client.contextaware.integration.meeting.PresenterMeetingListReceiver;
import ac.uk.brunel.client.contextaware.integration.meeting.PresenterMeetingListReceiverImpl;
import ac.uk.brunel.client.contextaware.integration.participant.MeetingUsersRegistration;
import ac.uk.brunel.client.contextaware.integration.participant.MeetingUsersRegistrationImpl;
import ac.uk.brunel.client.contextaware.integration.presenter.PresenterEventSender;
import ac.uk.brunel.client.contextaware.integration.presenter.PresenterEventSenderImpl;
import ac.uk.brunel.client.contextaware.jetty.JettyServletConfig;
import ac.uk.brunel.client.contextaware.jetty.JettyServletConfigImpl;
import ac.uk.brunel.client.contextaware.presentation.callback.BluetoothDeviceCallback;
import ac.uk.brunel.client.contextaware.presentation.callback.BluetoothDeviceCallbackImpl;
import ac.uk.brunel.client.contextaware.presentation.facade.ServerGuiActionsImpl;
import ac.uk.brunel.client.contextaware.service.*;
import ac.uk.brunel.client.contextaware.transport.servlet.*;
import ac.uk.brunel.server.contextaware.presentation.swing.ServerGuiActions;
import com.google.inject.AbstractModule;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 6, 2010
 * Time: 6:47:00 PM
 */
public class ServerAppModule extends AbstractModule {

    @Override
    protected void configure() {
        // Logger
        bindInterceptor(annotatedWith(LoggingAspect.class), any(), new AopLoggerInterceptor());

        // Jetty config
        bind(JettyServletConfig.class).to(JettyServletConfigImpl.class);

        // GUI
        bind(ServerGuiActions.class).to(ServerGuiActionsImpl.class);

        // Presentation facade
        bind(BluetoothDeviceCallback.class).to(BluetoothDeviceCallbackImpl.class);

        // Meeting integration
        bind(PresenterMeetingListReceiver.class).to(PresenterMeetingListReceiverImpl.class);
        bind(MeetingRegistration.class).to(MeetingRegistrationImpl.class);
        bind(PresenterEventSender.class).to(PresenterEventSenderImpl.class);

        // Participant integration
        bind(MeetingUsersRegistration.class).to(MeetingUsersRegistrationImpl.class);

        // Bluetooth integration
        bind(HttpClient.class).to(DefaultHttpClient.class);
        bind(BluetoothDevicePing.class).to(BluetoothDevicePingImpl.class);
        bind(BluetoothDeviceHandler.class).to(BluetoothDeviceHandlerImpl.class);

        // Keyboardevent integration
        bind(KeyboardEventHandler.class).to(KeyboardEventHandlerImpl.class);

        // Service
        bind(MeetingService.class).to(MeetingServiceImpl.class);
        bind(ParticipantService.class).to(ParticipantServiceImpl.class);
        bind(PresenterService.class).to(PresenterServiceImpl.class);
        bind(MeetingStateHandler.class).to(MeetingStateHandlerImpl.class);

        // Transport / servlet
        bind(PresenterActions.class).to(PresenterActionsImpl.class);
        bind(PresenterActionsDemo.class).to(PresenterActionsDemoImpl.class);
        bind(PingServlet.class).to(PingServletImpl.class);
    }
}
