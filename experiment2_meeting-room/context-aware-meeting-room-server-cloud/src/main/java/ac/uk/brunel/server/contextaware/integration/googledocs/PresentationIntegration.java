package ac.uk.brunel.server.contextaware.integration.googledocs;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.dto.MeetingNote;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 30, 2010
 * Time: 2:09:33 PM
 */
public interface PresentationIntegration {
    public MeetingNote createPresentationNoteObject(final Meeting meeting);
}
