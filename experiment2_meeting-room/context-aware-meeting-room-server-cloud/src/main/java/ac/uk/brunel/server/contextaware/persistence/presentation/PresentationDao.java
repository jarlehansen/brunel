package ac.uk.brunel.server.contextaware.persistence.presentation;

import ac.uk.brunel.server.contextaware.dto.MeetingNote;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 31, 2010
 * Time: 2:43:33 PM
 */
public interface PresentationDao {
    public void persistStartedMeeting(final MeetingNote meetingNote);
    public MeetingNote findMeetingNote(final String meetingId);
    public List<MeetingNote> findAllMeetingNotes();
}
