package ac.uk.brunel.server.contextaware.config;

import ac.uk.brunel.server.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.server.contextaware.aop.AopLoggerInterceptor;
import ac.uk.brunel.server.contextaware.integration.calendar.CalendarIntegration;
import ac.uk.brunel.server.contextaware.integration.calendar.CalendarIntegrationImpl;
import ac.uk.brunel.server.contextaware.integration.calendar.CalendarLogon;
import ac.uk.brunel.server.contextaware.integration.calendar.CalendarLogonImpl;
import ac.uk.brunel.server.contextaware.integration.googledocs.PresentationIntegration;
import ac.uk.brunel.server.contextaware.integration.googledocs.PresentationIntegrationImpl;
import ac.uk.brunel.server.contextaware.persistence.meeting.MeetingDao;
import ac.uk.brunel.server.contextaware.persistence.meeting.MeetingDaoImpl;
import ac.uk.brunel.server.contextaware.persistence.presentation.PresentationDao;
import ac.uk.brunel.server.contextaware.persistence.presentation.PresentationDaoImpl;
import ac.uk.brunel.server.contextaware.service.MeetingService;
import ac.uk.brunel.server.contextaware.service.MeetingServiceImpl;
import ac.uk.brunel.server.contextaware.service.PresentationService;
import ac.uk.brunel.server.contextaware.service.PresentationServiceImpl;
import ac.uk.brunel.server.contextaware.transport.servlet.*;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 14, 2010
 * Time: 7:31:29 PM
 */
class MeetingUserModule extends ServletModule {

    @Override
    protected void configureServlets() {
        // Logger
        bindInterceptor(annotatedWith(LoggingAspect.class), any(), new AopLoggerInterceptor());

        // Service
        bind(MeetingService.class).to(MeetingServiceImpl.class);
        bind(PresentationService.class).to(PresentationServiceImpl.class).in(RequestScoped.class);

        // Integration layer
        bind(CalendarIntegration.class).to(CalendarIntegrationImpl.class);
        bind(CalendarLogon.class).to(CalendarLogonImpl.class);
        bind(PresentationIntegration.class).to(PresentationIntegrationImpl.class);

        // Persistence
        bind(MeetingDao.class).to(MeetingDaoImpl.class);
        bind(PresentationDao.class).to(PresentationDaoImpl.class);

        // Servlets
        serve("/meetingRefresh").with(MeetingScheduleRefresher.class);
        serve("/meetingUpdater").with(MeetingUpdater.class);
        serve("/meetingRegistration").with(MeetingRegistration.class);
        serve("/meetingRegisterUser").with(MeetingUsersRegistration.class);
        serve("/presentationNotes").with(PresentationNotesSender.class);
        serve("/presenterEvent").with(PresenterEventReceiver.class);
        serve("/presenterMeetingList").with(PresenterMeetingListSender.class);
    }
}
