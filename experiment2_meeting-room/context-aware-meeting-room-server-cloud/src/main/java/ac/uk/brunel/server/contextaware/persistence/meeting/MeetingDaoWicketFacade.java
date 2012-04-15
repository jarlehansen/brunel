package ac.uk.brunel.server.contextaware.persistence.meeting;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 30, 2010
 * Time: 12:13:21 AM
 */
@ImplementedBy(MeetingDaoImpl.class)
public interface MeetingDaoWicketFacade {
    public List<Meeting> getTodaysMeetings(final String date);
    public void deleteAllMeetings(final String secret);
}
