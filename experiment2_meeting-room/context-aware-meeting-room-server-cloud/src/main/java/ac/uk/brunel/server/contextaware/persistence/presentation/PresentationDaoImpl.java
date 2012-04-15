package ac.uk.brunel.server.contextaware.persistence.presentation;

import ac.uk.brunel.server.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.server.contextaware.dto.MeetingNote;
import ac.uk.brunel.server.contextaware.persistence.PersistenceHelper;
import ac.uk.brunel.server.contextaware.persistence.wrapper.MeetingNoteJpaWrapper;
import ac.uk.brunel.server.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import ac.uk.brunel.server.contextaware.properties.QueriesConstants;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 31, 2010
 * Time: 2:43:23 PM
 */
@LoggingAspect
public class PresentationDaoImpl implements PresentationDao, PresentationDaoWicketFacade {
    private static final Logger logger = LoggerFactory.getLogger(PresentationDaoImpl.class);
    private final PersistenceHelper persistenceHelper;

    @Inject
    public PresentationDaoImpl(final PersistenceHelper persistenceHelper) {
        this.persistenceHelper = persistenceHelper;
    }

    public void persistStartedMeeting(final MeetingNote meetingNote) {
        if (meetingNote.getMeetingId() != null && !"".equals(meetingNote.getMeetingId())) {
            final EntityManager entityManager = persistenceHelper.get();

            try {
                entityManager.persist(new MeetingNoteJpaWrapper(meetingNote));
            } catch (PersistenceException pe) {
                if (logger.isErrorEnabled()) {
                    logger.error("Unable to persist MeetingNote: " + meetingNote, pe);
                }
            } finally {
                entityManager.close();
            }
        }
    }

    public MeetingNote findMeetingNote(final String meetingId) {
        MeetingNote meetingNote = MeetingNote.EMPTY_NOTE;
        final EntityManager entityManager = persistenceHelper.get();

        try {
            MeetingNoteJpaWrapper meetingNoteJpaWrapper = entityManager.find(MeetingNoteJpaWrapper.class, meetingId);

            if (meetingNoteJpaWrapper != null && meetingNoteJpaWrapper.getCurrentSlideNumber() > -1) {
                meetingNote = meetingNoteJpaWrapper.getMeetingNote();
            }
        } finally {
            entityManager.close();
        }

        return meetingNote;
    }

    public List<MeetingNote> findAllMeetingNotes() {
        final List<MeetingNote> meetingNotes = new ArrayList<MeetingNote>();
        final EntityManager entityManager = persistenceHelper.get();

        try {
            Query query = entityManager.createQuery(PropertiesReader.JPA_QUERIES.get(QueriesConstants.PRESENTATIONDAO_SELECTALL));

            @SuppressWarnings("unchecked")
            List<MeetingNoteJpaWrapper> tempList = query.getResultList();

            if (tempList != null) {
                for (MeetingNoteJpaWrapper tempMeetingNote : tempList) {
                    meetingNotes.add(tempMeetingNote.getMeetingNote());
                }
            }
        } finally {
            entityManager.close();
        }

        return meetingNotes;
    }

    public int getNumberOfMeetingNotes() {
        int numberOfMeetingNotes = -1;
        final EntityManager entityManager = persistenceHelper.get();

        try {
            Query query = entityManager.createQuery(PropertiesReader.JPA_QUERIES.get(QueriesConstants.PRESENTATIONDAO_SELECTALL));

            @SuppressWarnings("unchecked")
            List<MeetingNoteJpaWrapper> tempList = query.getResultList();
            if (tempList != null) {
                numberOfMeetingNotes = tempList.size();
            }
        } finally {
            entityManager.close();
        }

        return numberOfMeetingNotes;
    }

    public void deleteAllMeetingNotes(final String secret) {
        if (PropertiesReader.SERVER_APP.get(PropertiesConstants.ADMIN_SECRET).equals(secret)) {
            final EntityManager entityManager = persistenceHelper.get();

            try {
                Query query = entityManager.createQuery(PropertiesReader.JPA_QUERIES.get(QueriesConstants.PRESENTATIONDAO_DELETEALLMEETINGNOTES));
                int numOfRows = query.executeUpdate();

                if (logger.isInfoEnabled()) {
                    logger.info("Deleted all meeting notes, number of rows affected: " + numOfRows);
                }
            } finally {
                entityManager.close();
            }
        }
    }
}
