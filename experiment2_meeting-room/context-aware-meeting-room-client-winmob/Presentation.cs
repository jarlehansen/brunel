using System;

using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using ac.uk.brunel.contextaware;
using System.Collections;

namespace CAMR_3
{
    public partial class Presentation : Form
    {
        private ArrayList meetingNotes;
        private Note newNote;
        private int pointer;
        private bool normalPresentation = true;

        public Presentation()
        {
            InitializeComponent();
            meetingNotes = new ArrayList();
        }

        private void pollServer()
        {
            //polling server at interval
            newNote = new Note(); //get from serverpoll
            meetingNotes.Add(newNote);
            if(normalPresentation)
                presentNote(newNote);
        }

        private void presentNote(Note newNote)
        {
            txtNote.Text = newNote.message;
        }

        private void Presentation_Closed(object sender, EventArgs e)
        {
            //save to file
            this.Close();
        }

       
        private void presentCurrent()
        {
            normalPresentation = true;
            presentNote((Note)meetingNotes[meetingNotes.Count-1]);
        }

        private void mnuBack_Click(object sender, EventArgs e)
        {
            //save to file
            this.Close();
        }

        private void mnuNext_Click(object sender, EventArgs e)
        {
            normalPresentation = false;

            if (pointer == meetingNotes.Count)
                presentNote((Note)meetingNotes[meetingNotes.Count-1]);
            else 
            {
                presentNote((Note)meetingNotes[pointer]);
                pointer--;
            }
        }

        private void mnuPrev_Click(object sender, EventArgs e)
        {
            normalPresentation = false;

            if (pointer <= 0)
                presentNote((Note)meetingNotes[0]);
            else 
            {
                presentNote((Note)meetingNotes[pointer]);
                pointer--;
            }
        }

        
    }
}