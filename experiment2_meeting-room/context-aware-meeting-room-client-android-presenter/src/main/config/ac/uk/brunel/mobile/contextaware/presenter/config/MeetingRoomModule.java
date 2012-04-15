package ac.uk.brunel.mobile.contextaware.presenter.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import ac.uk.brunel.mobile.contextaware.presenter.business.PresenterService;
import ac.uk.brunel.mobile.contextaware.presenter.business.PresenterServiceImpl;
import ac.uk.brunel.mobile.contextaware.presenter.integration.ConnectionPing;
import ac.uk.brunel.mobile.contextaware.presenter.integration.ConnectionPingImpl;
import ac.uk.brunel.mobile.contextaware.presenter.integration.PresenterEventSender;
import ac.uk.brunel.mobile.contextaware.presenter.integration.PresenterEventSenderImpl;

import com.google.inject.AbstractModule;

public class MeetingRoomModule extends AbstractModule {

	@Override
	protected void configure() {
		// business
		bind(PresenterService.class).to(PresenterServiceImpl.class);
		
		// integration
		bind(HttpClient.class).to(DefaultHttpClient.class);
		bind(ConnectionPing.class).to(ConnectionPingImpl.class);
		bind(PresenterEventSender.class).to(PresenterEventSenderImpl.class);
	}
	
}
