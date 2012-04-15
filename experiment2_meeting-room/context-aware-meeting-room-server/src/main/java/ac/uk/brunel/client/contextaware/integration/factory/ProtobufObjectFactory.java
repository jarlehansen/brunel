package ac.uk.brunel.client.contextaware.integration.factory;

import ac.uk.brunel.client.contextaware.dto.Meeting;
import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;
import ac.uk.brunel.contextaware.network.generated.MeetingProtobuf;
import ac.uk.brunel.contextaware.network.generated.UserListProtobuf;

import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 8, 2010
 * Time: 10:33:49 PM
 */
public enum ProtobufObjectFactory {
    ;

    public static Meeting createDtoMeeting(final MeetingProtobuf.Meeting protoMeeting) {
        return new Meeting(protoMeeting.getMeetingId(), protoMeeting.getPresentationLink(), protoMeeting.getPresenter(),
                protoMeeting.getParticipantsList(), protoMeeting.getTitle(), protoMeeting.getDescription(), protoMeeting.getLocation(),
                protoMeeting.getStartTime(), protoMeeting.getEndTime());
    }

    public static MeetingProtobuf.Meeting createProtoMeeting(Meeting meetingDto) {
        return MeetingProtobuf.Meeting.newBuilder()
                .setMeetingId(meetingDto.getMeetingId())
                .setPresentationLink(meetingDto.getPresentationLink())
                .setPresenter(meetingDto.getPresenter())
                .addAllParticipants(meetingDto.getParticipants())
                .setTitle(meetingDto.getTitle())
                .setDescription(meetingDto.getDescription())
                .setLocation(meetingDto.getLocation())
                .setStartTime(meetingDto.getStartTime())
                .setEndTime(meetingDto.getEndTime()).build();
    }

    public static UserListProtobuf.UserList createProtoUserList(final String meetingId, final List<BluetoothDevice> bluetoothDevices) {
        UserListProtobuf.UserList.Builder protoUserListBuilder = UserListProtobuf.UserList.newBuilder();
        protoUserListBuilder.setMeetingId(meetingId);

        for (BluetoothDevice bluetoothDevice : bluetoothDevices) {
            protoUserListBuilder.addBtaddress(bluetoothDevice.getBluetoothAddress());
        }

        return protoUserListBuilder.build();
    }
}
