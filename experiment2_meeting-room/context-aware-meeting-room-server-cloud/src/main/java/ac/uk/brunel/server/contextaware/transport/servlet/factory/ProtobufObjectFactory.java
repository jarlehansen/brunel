package ac.uk.brunel.server.contextaware.transport.servlet.factory;

import ac.uk.brunel.contextaware.network.generated.MeetingBtAddressesProtobuf;
import ac.uk.brunel.contextaware.network.generated.MeetingProtobuf;
import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.dto.MeetingNote;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 8:53:06 PM
 */
public enum ProtobufObjectFactory {
    ;

    public static MeetingProtobuf.MeetingList createProtoMeetingList(final List<Meeting> dtoMeetingList) {
        final MeetingProtobuf.MeetingList.Builder protoMeetingList = MeetingProtobuf.MeetingList.newBuilder();

        for (Meeting dtoMeeting : dtoMeetingList) {
            MeetingProtobuf.Meeting protoMeeting = MeetingProtobuf.Meeting.newBuilder()
                    .setMeetingId(dtoMeeting.getMeetingId())
                    .setPresentationLink(dtoMeeting.getPresentationLink())
                    .setPresenter(dtoMeeting.getPresenter())
                    .addAllParticipants(dtoMeeting.getParticipants())
                    .setTitle(dtoMeeting.getTitle())
                    .setDescription(dtoMeeting.getDescription())
                    .setLocation(dtoMeeting.getLocation())
                    .setStartTime(dtoMeeting.getStartTime())
                    .setEndTime(dtoMeeting.getEndTime()).build();
            protoMeetingList.addMeeting(protoMeeting);
        }

        return protoMeetingList.build();
    }

    public static Meeting createDtoMeeting(final MeetingProtobuf.Meeting protoMeeting) {
        return new Meeting(protoMeeting.getMeetingId(), protoMeeting.getPresentationLink(), protoMeeting.getPresenter(),
                protoMeeting.getParticipantsList(), protoMeeting.getTitle(), protoMeeting.getDescription(), protoMeeting.getLocation(),
                protoMeeting.getStartTime(), protoMeeting.getEndTime());
    }

    public static MeetingBtAddressesProtobuf.MeetingBtAddresses createMeetingBtAddresses(final MeetingNote meetingNote) {
        return MeetingBtAddressesProtobuf.MeetingBtAddresses.newBuilder()
                .setMeetingId(meetingNote.getMeetingId())
                .setBluetoothAddresses(meetingNote.getParticipants()).build();
    }
}
