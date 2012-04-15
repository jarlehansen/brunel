package ac.uk.brunel.server.contextaware.integration.calendar;

import ac.uk.brunel.server.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.EventWho;
import com.google.gdata.data.extensions.Where;
import com.google.gdata.util.ServiceException;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 22, 2010
 * Time: 3:58:56 PM
 */
@LoggingAspect
public class CalendarIntegrationImpl implements CalendarIntegration {
    private static final Logger logger = LoggerFactory.getLogger(CalendarIntegrationImpl.class);

    private final CalendarLogon calendarLogon;

    private final CalendarService calendarService = new CalendarService("Context-aware meeting room");

    @Inject
    public CalendarIntegrationImpl(final CalendarLogon calendarLogon) {
        this.calendarLogon = calendarLogon;
    }

    public List<Meeting> getMeetingList(final Date fromDate, final Date toDate) {
        final List<Meeting> meetings = new ArrayList<Meeting>();
        calendarLogon.accountLogon(calendarService);

        String feedUrl = PropertiesReader.SERVER_APP.get(PropertiesConstants.CALENDAR_FEED_URL);

        try {
            CalendarQuery query = new CalendarQuery(new URL(feedUrl));

            query.setMinimumStartTime(new DateTime(fromDate));
            query.setMaximumStartTime(new DateTime(toDate));

            CalendarEventFeed feed = calendarService.query(query, CalendarEventFeed.class);
            if (logger.isInfoEnabled()) {
                logger.info("Number of meetings found: " + feed.getEntries().size());
            }

            populateMeetingList(meetings, feed.getEntries());
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("IOException when running query on feedUrl: " + feedUrl, io);
            }
        } catch (ServiceException se) {
            if (logger.isErrorEnabled()) {
                logger.error("ServiceException when running query on feedUrl: " + feedUrl, se);
            }
        }

        return meetings;
    }

    private List<Meeting> populateMeetingList(final List<Meeting> meetings, final List<CalendarEventEntry> calendarEventEntries) {
        for (CalendarEventEntry entry : calendarEventEntries) {
            final String meetingId = entry.getId();
            final String presenter = entry.getAuthors().get(0).getEmail();
            final List<String> participants = new ArrayList<String>();

            final List<EventWho> eventWhoList = entry.getParticipants();
            for (EventWho participant : eventWhoList) {
                // Do not add the presenter as a participant
                if (!presenter.equals(participant.getEmail())) {
                    participants.add(participant.getEmail());
                }
            }

            final String title = entry.getTitle().getPlainText();
            final DescriptionInfo descriptionInfo = new DescriptionInfo(entry.getTextContent().getContent().getPlainText());
            final String description = descriptionInfo.getDescription();
            final String presentationLink = descriptionInfo.getPresentationLink();

            final List<Where> whereList = entry.getLocations();
            final StringBuilder locationBuilder = new StringBuilder();

            for(int i = 0; i < whereList.size(); i++) {
                locationBuilder.append(whereList.get(i).getValueString());

                if(i < whereList.size() -1) {
                    locationBuilder.append(", ");
                }
            }

            final String startTime = entry.getTimes().get(0).getStartTime().toUiString();
            final String endTime = entry.getTimes().get(0).getEndTime().toUiString();

            meetings.add(new Meeting(meetingId, presentationLink, presenter, participants, title, description, locationBuilder.toString(), startTime, endTime));
        }

        return meetings;
    }
}
