package ac.uk.brunel.client.contextaware.integration.meeting;

import ac.uk.brunel.client.contextaware.dto.Meeting;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 6, 2010
 * Time: 10:14:40 PM
 */
public class PresenterMeetingListReceiverImplIntegrationTest {
    private PresenterMeetingListReceiverImpl presenterMeetingListReceiver;

    @Before
    public void setup() {
        presenterMeetingListReceiver = new PresenterMeetingListReceiverImpl(new DefaultHttpClient());
    }

    @Test
    public void testGetMeetingList() {
        List<Meeting> meetingList = presenterMeetingListReceiver.getPresenterMeetingList("hansjar@googlemail.com");

        for(Meeting meeting : meetingList) {
            System.out.println(meeting);
        }
    }

    @Test
    public void testGetMeetingListAlias() {
        List<Meeting> meetingList = presenterMeetingListReceiver.getPresenterMeetingList("hansjar@gmail.com");

        for(Meeting meeting : meetingList) {
            System.out.println(meeting);
        }
    }
}
