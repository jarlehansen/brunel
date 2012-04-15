package ac.uk.brunel.server.contextaware.persistence.presentation;

import com.google.inject.ImplementedBy;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 28, 2010
 * Time: 1:55:04 PM
 */
@ImplementedBy(PresentationDaoImpl.class)
public interface PresentationDaoWicketFacade {
    public void deleteAllMeetingNotes(final String secret);
    public int getNumberOfMeetingNotes();
}
    