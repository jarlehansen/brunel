namespace CAMR_3
{
    partial class MeetingNotes
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.txtMeetingNotes = new System.Windows.Forms.TextBox();
            this.btnClose = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // txtMeetingNotes
            // 
            this.txtMeetingNotes.BackColor = System.Drawing.SystemColors.Control;
            this.txtMeetingNotes.Location = new System.Drawing.Point(3, 3);
            this.txtMeetingNotes.Multiline = true;
            this.txtMeetingNotes.Name = "txtMeetingNotes";
            this.txtMeetingNotes.ReadOnly = true;
            this.txtMeetingNotes.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.txtMeetingNotes.Size = new System.Drawing.Size(234, 262);
            this.txtMeetingNotes.TabIndex = 0;
            // 
            // btnClose
            // 
            this.btnClose.Location = new System.Drawing.Point(4, 271);
            this.btnClose.Name = "btnClose";
            this.btnClose.Size = new System.Drawing.Size(233, 20);
            this.btnClose.TabIndex = 1;
            this.btnClose.Text = "Close";
            this.btnClose.Click += new System.EventHandler(this.btnClose_Click);
            // 
            // MeetingNotes
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 294);
            this.Controls.Add(this.btnClose);
            this.Controls.Add(this.txtMeetingNotes);
            this.Name = "MeetingNotes";
            this.Text = "MeetingNotes";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TextBox txtMeetingNotes;
        private System.Windows.Forms.Button btnClose;
    }
}