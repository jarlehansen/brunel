using System;

using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Collections;

namespace CAMR_3
{
    public partial class MeetingHistory : Form
    {
        //private System.Collections.Generic.List<Business.Meeting> meetings;
        private System.Collections.ArrayList meetings;

        public MeetingHistory()
        {
            InitializeComponent();
            //meetings = new List<CAMR_3.Business.Meeting>();
            meetings = new ArrayList();
            //lstMeetingNotes.DataSource = meetings;
            lstMeetingNotes.DisplayMember = "MeetingDate";

            
        }

        private void btnWrite_Click(object sender, EventArgs e)
        {
            Business.Meeting meet = (Business.Meeting)lstMeetingNotes.SelectedItem;
            new MeetingNotes(meet).ShowDialog();

        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
           // lstMeetingNotes.BeginUpdate();
            CAMR_3.Business.Meeting meeting = new CAMR_3.Business.Meeting();
            meeting.MeetingDate = txtDate.Text;
            ac.uk.brunel.contextaware.Note note1 = new ac.uk.brunel.contextaware.Note();
            note1.message = "abc";
            ac.uk.brunel.contextaware.Note note2 = new ac.uk.brunel.contextaware.Note();
            note2.message = "def";
            meeting.AddNote(note1);
            meeting.AddNote(note2);

            meetings.Add(meeting);
          //  lstMeetingNotes.EndUpdate();
            lstMeetingNotes.Items.Add(meeting);
            //lstMeetingNotes.DataSource = meetings;

        }

        private void mnuBack_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void mnuClear_Click(object sender, EventArgs e)
        {
            //empty list
            //clear saved file
        }

       
    }
}