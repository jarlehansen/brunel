package ac.uk.brunel.server.contextaware.persistence.meeting;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.persistence.PersistenceHelper;
import ac.uk.brunel.server.contextaware.persistence.wrapper.MeetingJpaWrapper;
import ac.uk.brunel.server.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 24, 2010
 * Time: 5:51:48 PM
 */
public class MeetingDaoImplTest {
    private final Meeting meeting = new Meeting("meetingId", "presentationLink", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");

    private EntityManager mockedEntityManager;
    private Query mockedQuery;
    private MeetingDaoImpl meetingDao;

    @Before
    public void setup() {
        mockedEntityManager = mock(EntityManager.class);
        mockedQuery = mock(Query.class);

        final PersistenceHelper mockedPersistenceHelper = mock(PersistenceHelper.class);
        when(mockedPersistenceHelper.get()).thenReturn(mockedEntityManager);
        when(mockedEntityManager.createQuery(anyString())).thenReturn(mockedQuery);

        meetingDao = new MeetingDaoImpl(mockedPersistenceHelper);
    }

    @Test
    public void testRegisterMeeting() {
        meetingDao.registerMeeting(meeting);

        verify(mockedEntityManager).persist(any(MeetingJpaWrapper.class));
        verify(mockedEntityManager).close();
    }

    @Test
    public void testGetPersistedMeetings() {
        meetingDao.getTodaysMeetings("2010-01-29");

        verify(mockedEntityManager).createQuery(anyString());
        verify(mockedQuery).getResultList();
        verify(mockedEntityManager).close();
    }

    @Test
    public void testGetPresenterMeetings() {
        List<MeetingJpaWrapper> jpaList = new ArrayList<MeetingJpaWrapper>();
        jpaList.add(new MeetingJpaWrapper(meeting));
        when(mockedQuery.getResultList()).thenReturn(jpaList);

        List<Meeting> meetingList = meetingDao.getPresenterMeetings("hansjar@gmail.com", "2010-01-29");

        verify(mockedEntityManager).createQuery(anyString());
        verify(mockedQuery).getResultList();
        verify(mockedEntityManager).close();

        assertReflectionEquals(meeting, meetingList.get(0));
    }

    @Test
    public void testGetPresenterMeetingsEmptyQueryList() {
        List<MeetingJpaWrapper> emptyJpaList = new ArrayList<MeetingJpaWrapper>();
        when(mockedQuery.getResultList()).thenReturn(emptyJpaList);

        List<Meeting> meetingList = meetingDao.getPresenterMeetings("hansjar@gmail.com", "2010-01-29");

        verify(mockedEntityManager, times(2)).createQuery(anyString());
        verify(mockedQuery, times(2)).getResultList();
        verify(mockedEntityManager).close();

        assertEquals(0, meetingList.size());
    }

    @Test
    public void testDeleteAllMeetingsValidSecret() {
        when(mockedQuery.executeUpdate()).thenReturn(1);

        meetingDao.deleteAllMeetings(PropertiesReader.SERVER_APP.get(PropertiesConstants.ADMIN_SECRET));

        verify(mockedEntityManager).createQuery(anyString());
        verify(mockedQuery).executeUpdate();
        verify(mockedEntityManager).close();
    }

    @Test
    public void testDeleteAllMeetingsInvalidSecret() {
        meetingDao.deleteAllMeetings("Not correct");

        verifyZeroInteractions(mockedEntityManager, mockedQuery);
    }
}
