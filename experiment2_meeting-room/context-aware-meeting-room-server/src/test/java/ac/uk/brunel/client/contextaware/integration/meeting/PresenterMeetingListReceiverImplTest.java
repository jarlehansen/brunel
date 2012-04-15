package ac.uk.brunel.client.contextaware.integration.meeting;

import ac.uk.brunel.client.contextaware.dto.Meeting;
import ac.uk.brunel.client.contextaware.integration.factory.ProtobufObjectFactory;
import ac.uk.brunel.client.contextaware.util.HttpTestFileInputUtil;
import ac.uk.brunel.contextaware.network.generated.MeetingProtobuf;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 15, 2010
 * Time: 9:28:37 PM
 */
public class PresenterMeetingListReceiverImplTest {
    private final MeetingProtobuf.Meeting protoMeeting = MeetingProtobuf.Meeting.newBuilder().setDescription("description")
            .setEndTime("endTime").setStartTime("startTime").setLocation("location").setMeetingId("meetingId")
            .addParticipants("participant").setPresentationLink("presentationLink").setPresenter("presenter")
            .setTitle("title").build();

    private final MeetingProtobuf.MeetingList protoMeetingList = MeetingProtobuf.MeetingList.newBuilder()
            .addMeeting(protoMeeting).build();
    private final MeetingProtobuf.MeetingList emptyProtoMeetingList = MeetingProtobuf.MeetingList.newBuilder()
            .build();


    private HttpResponse mockedResponse;
    private HttpClient mockedClient;
    private HttpEntity mockedEntity;

    private PresenterMeetingListReceiverImpl presenterMeetingListReceiver;

    @Before
    public void setup() throws IOException {
        InputStream input = HttpTestFileInputUtil.createTestInputStream(protoMeetingList);

        mockedClient = mock(HttpClient.class);
        mockedResponse = mock(HttpResponse.class);
        mockedEntity = mock(HttpEntity.class);
        when(mockedClient.execute(any(HttpPost.class))).thenReturn(mockedResponse);
        when(mockedResponse.getEntity()).thenReturn(mockedEntity);
        when(mockedEntity.getContent()).thenReturn(input);

        presenterMeetingListReceiver = new PresenterMeetingListReceiverImpl(mockedClient);
    }

    @Test
    public void testGetPresenterMeetingList() throws IOException {
        List<Meeting> meetingList = presenterMeetingListReceiver.getPresenterMeetingList("hansjar@gmail.com");

        verify(mockedClient).execute(any(HttpPost.class));
        verify(mockedResponse).getEntity();
        verify(mockedEntity).getContent();
        verify(mockedEntity).consumeContent();

        assertEquals(1, meetingList.size());
        assertReflectionEquals(ProtobufObjectFactory.createDtoMeeting(protoMeeting), meetingList.get(0));
    }

    @Test
    public void testGetPresenterMeetingListExecuteException() throws IOException {
        when(mockedClient.execute(any(HttpPost.class))).thenThrow(new IOException("Test exception"));
        List<Meeting> meetingList = presenterMeetingListReceiver.getPresenterMeetingList("hansjar@gmail.com");

        verify(mockedClient).execute(any(HttpPost.class));

        assertEquals(0, meetingList.size());
    }

    @Test
    public void testGetPresenterMeetingListEmptyList() throws IOException {
        InputStream input = HttpTestFileInputUtil.createTestInputStream(emptyProtoMeetingList);
        when(mockedEntity.getContent()).thenReturn(input);

        List<Meeting> meetingList = presenterMeetingListReceiver.getPresenterMeetingList("hansjar@gmail.com");

        verify(mockedClient).execute(any(HttpPost.class));
        verify(mockedResponse).getEntity();
        verify(mockedEntity).getContent();
        verify(mockedEntity).consumeContent();

        assertEquals(0, meetingList.size());
    }

    @Test
    public void testGetPresenterMeetingListInputException() throws IOException {
        when(mockedEntity.getContent()).thenThrow(new IOException("Test exception"));

        List<Meeting> meetingList = presenterMeetingListReceiver.getPresenterMeetingList("hansjar@gmail.com");

        verify(mockedClient).execute(any(HttpPost.class));
        verify(mockedResponse).getEntity();
        verify(mockedEntity).getContent();
        verify(mockedEntity).consumeContent();

        assertEquals(0, meetingList.size());
    }
}
