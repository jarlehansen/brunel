package ac.uk.brunel.server.contextaware.persistence.meeting;

import ac.uk.brunel.server.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.persistence.PersistenceHelper;
import ac.uk.brunel.server.contextaware.persistence.wrapper.MeetingJpaWrapper;
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
 * Date: Jan 22, 2010
 * Time: 11:59:11 PM
 */
@LoggingAspect
public class MeetingDaoImpl implements MeetingDao, MeetingDaoWicketFacade {
    private static final Logger logger = LoggerFactory.getLogger(MeetingDaoImpl.class);

    private final PersistenceHelper persistenceHelper;

    @Inject
    public MeetingDaoImpl(final PersistenceHelper persistenceHelper) {
        this.persistenceHelper = persistenceHelper;
    }

    public void registerMeeting(final Meeting meeting) {
        final EntityManager entityManager = persistenceHelper.get();

        try {
            entityManager.persist(new MeetingJpaWrapper(meeting));

            if (logger.isDebugEnabled()) {
                logger.debug("Persisting meeting: " + meeting);
            }
        } catch (PersistenceException pe) {
            if (logger.isErrorEnabled()) {
                logger.error("Unable to persist meeting: " + meeting, pe);
            }
        } finally {
            entityManager.close();
        }
    }

    public List<Meeting> getTodaysMeetings(final String date) {
        final List<Meeting> meetingList = new ArrayList<Meeting>();
        final EntityManager entityManager = persistenceHelper.get();

        try {
            Query query = entityManager.createQuery(PropertiesReader.JPA_QUERIES.get(QueriesConstants.MEETINGDAO_TODAY_SELECTALL));
            query.setParameter(1, date + "%");

            // Convert from MeetingJpaWrapper to Meeting objects
            @SuppressWarnings("unchecked")
            final List<MeetingJpaWrapper> tempList = query.getResultList();

            if (tempList != null) {
                for (MeetingJpaWrapper tempMeeting : tempList) {
                    meetingList.add(tempMeeting.getMeeting());
                }
            }
        } finally {
            entityManager.close();
        }

        return meetingList;
    }

    public List<Meeting> getPresenterMeetings(final String email, final String date) {
        final List<Meeting> meetingList = new ArrayList<Meeting>();
        final EntityManager entityManager = persistenceHelper.get();

        try {
            // Attempt to find meetings for the specific e-mail
            List<MeetingJpaWrapper> tempList = queryPresenterMeetings(entityManager, date, email);

            // If no meetings are found try querying with an alias (email accounts can be converted to @googlemail.com)
            if (tempList == null || tempList.size() == 0) {
                tempList = queryEmailNamePresenterMeeting(entityManager, date, email);
            }

            if (tempList != null && tempList.size() > 0) {
                for (MeetingJpaWrapper tempMeeting : tempList) {
                    meetingList.add(tempMeeting.getMeeting());
                }
            }
        } finally {
            entityManager.close();
        }

        return meetingList;
    }

    @SuppressWarnings("unchecked")
    private List<MeetingJpaWrapper> queryPresenterMeetings(final EntityManager entityManager, final String date, final String email) {
        Query query = entityManager.createQuery(PropertiesReader.JPA_QUERIES.get(QueriesConstants.MEETINGDAO_PRESENTERMEETINGS));
        query.setParameter(1, date + "%");
        query.setParameter(2, email);

        return query.getResultList();
    }

    private List<MeetingJpaWrapper> queryEmailNamePresenterMeeting(final EntityManager entityManager, final String date, final String email) {
        List<MeetingJpaWrapper> tempList = new ArrayList<MeetingJpaWrapper>();
        // Try querying with an alias (email accounts can be converted to @googlemail.com)
        String[] emailArgs = email.split("@");
        String emailName = emailArgs[0];

        if (logger.isDebugEnabled()) {
            logger.debug("Initial query did not return any values, trying with alias: " + emailName);
        }

        /*
        Find all meetings that are registered for today and only save those with a presenter e-mail that starts with e-mail name.
        Do not take e-mail domain into account.
        */
        if (emailName != null && emailName.length() > 0) {
            Query queryAlias = entityManager.createQuery(PropertiesReader.JPA_QUERIES.get(QueriesConstants.MEETINGDAO_TODAY_SELECTALL));
            queryAlias.setParameter(1, date + "%");

            @SuppressWarnings("unchecked")
            List<MeetingJpaWrapper> allMeetings = queryAlias.getResultList();

            for (MeetingJpaWrapper meetingJpaWrapper : allMeetings) {
                if (meetingJpaWrapper.getPresenter().startsWith(emailName)) {
                    tempList.add(meetingJpaWrapper);
                }
            }
        }

        return tempList;
    }

    public void deleteAllMeetings(final String secret) {
        if (PropertiesReader.SERVER_APP.get(PropertiesConstants.ADMIN_SECRET).equals(secret)) {
            final EntityManager entityManager = persistenceHelper.get();

            try {
                Query query = entityManager.createQuery(PropertiesReader.JPA_QUERIES.get(QueriesConstants.MEETINGDAO_DELETEALLMEETINGS));
                int numOfRows = query.executeUpdate();

                if (logger.isInfoEnabled()) {
                    logger.info("Deleted all meetings, number of rows affected:" + numOfRows);
                }
            } finally {
                entityManager.close();
            }
        }
    }
}
