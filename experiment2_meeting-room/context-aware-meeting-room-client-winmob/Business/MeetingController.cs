using System;

using System.Collections.Generic;
using System.Text;

namespace CAMR_3.Business
{
    class MeetingController
    {
        private List<Meeting> meetings;

        public MeetingController()
        {
            meetings = new List<Meeting>();
        }

        public List<Meeting> Meetings { get { return this.meetings; } set { meetings = null; } }

        public void AddMeeting(String date)
        {
            Meeting meeting = new Meeting();
            meeting.MeetingDate=date;

            meetings.Add(meeting);
        }

    }
}
