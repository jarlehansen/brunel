package ac.uk.brunel.server.contextaware.integration.calendar;

import ac.uk.brunel.server.contextaware.dto.Meeting;

import java.util.Date;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 22, 2010
 * Time: 3:58:47 PM
 */
public interface CalendarIntegration {
    public List<Meeting> getMeetingList(Date fromDate, Date toDate);
}
