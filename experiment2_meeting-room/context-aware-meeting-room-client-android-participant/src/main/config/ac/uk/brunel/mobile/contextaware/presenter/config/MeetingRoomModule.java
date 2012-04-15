package ac.uk.brunel.mobile.contextaware.presenter.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import ac.uk.brunel.mobile.contextaware.participant.business.PresentationService;
import ac.uk.brunel.mobile.contextaware.participant.business.PresentationServiceImpl;
import ac.uk.brunel.mobile.contextaware.participant.integration.PresentationNotesReader;
import ac.uk.brunel.mobile.contextaware.participant.integration.PresentationNotesReaderImpl;

import com.google.inject.AbstractModule;

public class MeetingRoomModule extends AbstractModule {

	@Override
	protected void configure() {
		// Integration
		bind(HttpClient.class).to(DefaultHttpClient.class);
		bind(PresentationNotesReader.class).to(PresentationNotesReaderImpl.class);
		
		// Business
		bind(PresentationService.class).to(PresentationServiceImpl.class);
	}
}
