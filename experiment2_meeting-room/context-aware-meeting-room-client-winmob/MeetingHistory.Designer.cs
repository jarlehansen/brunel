namespace CAMR_3
{
    partial class MeetingHistory
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.MainMenu mainMenu1;

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
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.lstMeetingNotes = new System.Windows.Forms.ListBox();
            this.btnWrite = new System.Windows.Forms.Button();
            this.btnAdd = new System.Windows.Forms.Button();
            this.txtDate = new System.Windows.Forms.TextBox();
            this.mnuBack = new System.Windows.Forms.MenuItem();
            this.mnuClear = new System.Windows.Forms.MenuItem();
            this.SuspendLayout();
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.mnuBack);
            this.mainMenu1.MenuItems.Add(this.mnuClear);
            // 
            // lstMeetingNotes
            // 
            this.lstMeetingNotes.BackColor = System.Drawing.SystemColors.Control;
            this.lstMeetingNotes.Location = new System.Drawing.Point(7, 35);
            this.lstMeetingNotes.Name = "lstMeetingNotes";
            this.lstMeetingNotes.Size = new System.Drawing.Size(226, 184);
            this.lstMeetingNotes.TabIndex = 1;
            // 
            // btnWrite
            // 
            this.btnWrite.Location = new System.Drawing.Point(86, 235);
            this.btnWrite.Name = "btnWrite";
            this.btnWrite.Size = new System.Drawing.Size(72, 20);
            this.btnWrite.TabIndex = 7;
            this.btnWrite.Text = "Write";
            this.btnWrite.Click += new System.EventHandler(this.btnWrite_Click);
            // 
            // btnAdd
            // 
            this.btnAdd.Location = new System.Drawing.Point(7, 235);
            this.btnAdd.Name = "btnAdd";
            this.btnAdd.Size = new System.Drawing.Size(72, 20);
            this.btnAdd.TabIndex = 6;
            this.btnAdd.Text = "Add";
            this.btnAdd.Click += new System.EventHandler(this.btnAdd_Click);
            // 
            // txtDate
            // 
            this.txtDate.Location = new System.Drawing.Point(7, 4);
            this.txtDate.Name = "txtDate";
            this.txtDate.Size = new System.Drawing.Size(100, 21);
            this.txtDate.TabIndex = 8;
            // 
            // mnuBack
            // 
            this.mnuBack.Text = "Back";
            this.mnuBack.Click += new System.EventHandler(this.mnuBack_Click);
            // 
            // mnuClear
            // 
            this.mnuClear.Text = "Clear";
            this.mnuClear.Click += new System.EventHandler(this.mnuClear_Click);
            // 
            // MeetingHistory
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.txtDate);
            this.Controls.Add(this.btnWrite);
            this.Controls.Add(this.btnAdd);
            this.Controls.Add(this.lstMeetingNotes);
            this.Menu = this.mainMenu1;
            this.Name = "MeetingHistory";
            this.Text = "MeetingHistory";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ListBox lstMeetingNotes;
        private System.Windows.Forms.Button btnWrite;
        private System.Windows.Forms.Button btnAdd;
        private System.Windows.Forms.TextBox txtDate;
        private System.Windows.Forms.MenuItem mnuBack;
        private System.Windows.Forms.MenuItem mnuClear;
    }
}