using System;

using System.Collections.Generic;
using System.Text;
using ac.uk.brunel.contextaware;

namespace CAMR_3.Business
{
    public class Meeting
    {
        private String meetingDate;
        private System.Collections.ArrayList notes;

        public Meeting ()
	    {
            notes = new System.Collections.ArrayList();
	    }

        public String MeetingDate { get { return meetingDate; } set { this.meetingDate = value; } }

        public System.Collections.ArrayList Slides { get { return this.notes; } }

        public void AddNote(Note note)
        {
            this.notes.Add(note);
        }

        public String ToString()
        {
            return this.MeetingDate;
        }


            
    }
}
