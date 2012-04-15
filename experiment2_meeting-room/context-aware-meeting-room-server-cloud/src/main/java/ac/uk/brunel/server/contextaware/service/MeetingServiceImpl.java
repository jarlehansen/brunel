package ac.uk.brunel.server.contextaware.service;

import ac.uk.brunel.server.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.integration.calendar.CalendarIntegration;
import ac.uk.brunel.server.contextaware.persistence.meeting.MeetingDao;
import ac.uk.brunel.server.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 11:36:55 PM
 */
@LoggingAspect
public class MeetingServiceImpl implements MeetingService {
    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);

    private final SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat todayQueryFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat queryDate = new SimpleDateFormat("HH:mm:ss-dd/MM/yyyy");

    private final CalendarIntegration calendarIntegration;
    private final MeetingDao meetingDao;

    @Inject
    public MeetingServiceImpl(final CalendarIntegration calendarIntegration, final MeetingDao meetingDao) {
        this.calendarIntegration = calendarIntegration;
        this.meetingDao = meetingDao;
    }

    public void refreshMeetingList() {
        try {
            final String dayTime = day.format(new Date());
            final Date fromDate = createMeetingScheduleFromDate(dayTime);
            final Date toDate = createMeetingScheduleToDate(dayTime);

            final List<Meeting> meetingList = calendarIntegration.getMeetingList(fromDate, toDate);
            
            for (Meeting meeting : meetingList) {
                meetingDao.registerMeeting(meeting);
            }
        } catch (ParseException pe) {
            if (logger.isErrorEnabled()) {
                logger.error("Unable to retrieve the meeting schedule from Google calendar", pe);
            }
        }
    }

    public List<Meeting> getTodaysPresenterMeetings(final String email) {
        return meetingDao.getPresenterMeetings(email, todayQueryFormat.format(new Date()));
    }

    private Date createMeetingScheduleFromDate(final String dayTime) throws ParseException {
        String fromTime = PropertiesReader.SERVER_APP.get(PropertiesConstants.CALENDAR_LIST_DATE_FROM);
        return queryDate.parse(fromTime + "-" + dayTime);
    }

    private Date createMeetingScheduleToDate(final String dayTime) throws ParseException {
        String toTime = PropertiesReader.SERVER_APP.get(PropertiesConstants.CALENDAR_LIST_DATE_TO);
        return queryDate.parse(toTime + "-" + dayTime);
    }
}
