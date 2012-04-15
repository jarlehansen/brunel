package ac.uk.brunel.client.contextaware.integration.meeting;

import ac.uk.brunel.client.contextaware.dto.Meeting;
import ac.uk.brunel.client.contextaware.integration.factory.ProtobufObjectFactory;
import ac.uk.brunel.client.contextaware.util.HttpTestFileInputUtil;
import ac.uk.brunel.contextaware.network.generated.MeetingBtAddressesProtobuf;
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
import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 15, 2010
 * Time: 8:17:15 PM
 */
public class MeetingRegistrationImplTest {
    private final MeetingProtobuf.Meeting protoMeeting = MeetingProtobuf.Meeting.newBuilder().setDescription("description")
            .setEndTime("endTime").setStartTime("startTime").setLocation("location").setMeetingId("meetingId")
            .addParticipants("participant").setPresentationLink("presentationLink").setPresenter("presenter")
            .setTitle("title").build();
    private final Meeting meeting = ProtobufObjectFactory.createDtoMeeting(protoMeeting);

    private static final String participant1 = "000000000000";
    private static final String participant2 = "000000000001";
    private final MeetingBtAddressesProtobuf.MeetingBtAddresses protoBtAddresses = MeetingBtAddressesProtobuf.MeetingBtAddresses
            .newBuilder().setBluetoothAddresses(participant1 + ";" + participant2).setMeetingId("meetingId").build();
    private final MeetingBtAddressesProtobuf.MeetingBtAddresses protoBtAddressesNoParticipants = MeetingBtAddressesProtobuf.MeetingBtAddresses
            .newBuilder().setBluetoothAddresses("").setMeetingId("meetingId").build();


    private HttpResponse mockedResponse;
    private HttpClient mockedClient;
    private HttpEntity mockedEntity;

    private MeetingRegistrationImpl meetingRegistration;

    @Before
    public void setup() throws IOException {
        InputStream input = HttpTestFileInputUtil.createTestInputStream(protoBtAddresses);

        mockedClient = mock(HttpClient.class);
        mockedResponse = mock(HttpResponse.class);
        mockedEntity = mock(HttpEntity.class);
        when(mockedClient.execute(any(HttpPost.class))).thenReturn(mockedResponse);
        when(mockedResponse.getEntity()).thenReturn(mockedEntity);
        when(mockedEntity.getContent()).thenReturn(input);

        meetingRegistration = new MeetingRegistrationImpl(mockedClient);
    }

    @Test
    public void testRegisterMeetingAndGetRegisteredParticipants() throws IOException {
        List<String> participants = meetingRegistration.registerMeetingAndGetRegisteredParticipants(meeting);

        verify(mockedClient).execute(any(HttpPost.class));
        verify(mockedResponse).getEntity();
        verify(mockedEntity).getContent();
        verify(mockedEntity).consumeContent();

        assertEquals(participant1, participants.get(0));
        assertEquals(participant2, participants.get(1));
    }

    @Test
    public void testRegisterMeetingAndGetRegisteredParticipantsExecuteException() throws IOException {
        when(mockedClient.execute(any(HttpPost.class))).thenThrow(new IOException("Test exception"));
        List<String> participants = meetingRegistration.registerMeetingAndGetRegisteredParticipants(meeting);

        verify(mockedClient).execute(any(HttpPost.class));

        assertEquals(0, participants.size());
    }

    @Test
    public void testRegisterMeetingAndGetRegisteredParticipantsEmptyList() throws IOException {
        InputStream input = HttpTestFileInputUtil.createTestInputStream(protoBtAddressesNoParticipants);
        when(mockedEntity.getContent()).thenReturn(input);

        List<String> participants = meetingRegistration.registerMeetingAndGetRegisteredParticipants(meeting);

        verify(mockedClient).execute(any(HttpPost.class));
        verify(mockedResponse).getEntity();
        verify(mockedEntity).getContent();
        verify(mockedEntity).consumeContent();

        assertEquals(0, participants.size());
    }

    @Test
    public void testRegisterMeetingAndGetRegisteredParticipantsNullInput() throws IOException {
        when(mockedEntity.getContent()).thenReturn(null);

        List<String> participants = meetingRegistration.registerMeetingAndGetRegisteredParticipants(meeting);

        verify(mockedClient).execute(any(HttpPost.class));
        verify(mockedResponse).getEntity();
        verify(mockedEntity).getContent();
        verify(mockedEntity).consumeContent();

        assertEquals(0, participants.size());
    }

    @Test
    public void testRegisterMeetingAndGetRegisteredParticipantsInputException() throws IOException {
        when(mockedEntity.getContent()).thenThrow(new IOException("Test exception"));

        List<String> participants = meetingRegistration.registerMeetingAndGetRegisteredParticipants(meeting);

        verify(mockedClient).execute(any(HttpPost.class));
        verify(mockedResponse).getEntity();
        verify(mockedEntity).getContent();
        verify(mockedEntity).consumeContent();

        assertEquals(0, participants.size());
    }
}
