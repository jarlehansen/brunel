using System;

using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace CAMR_3
{
    public partial class MeetingNotes : Form
    {
        private static String SEPARATOR = "\r\n ---- \r\n";
        public MeetingNotes()
        {
            InitializeComponent();
            
        }

        public MeetingNotes(Business.Meeting meeting)
        {
            InitializeComponent();
            InitData(meeting);
        }

        private void InitData(Business.Meeting meeting)
        {
            foreach (ac.uk.brunel.contextaware.Note noteItem in meeting.Slides)
            {
                txtMeetingNotes.Text += noteItem.message + SEPARATOR;
            }
        }


        private void btnClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}