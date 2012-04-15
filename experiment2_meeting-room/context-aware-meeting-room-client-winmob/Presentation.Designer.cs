namespace CAMR_3
{
    partial class Presentation
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
            this.mnuBack = new System.Windows.Forms.MenuItem();
            this.mnuMenu = new System.Windows.Forms.MenuItem();
            this.txtNote = new System.Windows.Forms.TextBox();
            this.mnuNext = new System.Windows.Forms.MenuItem();
            this.mnuPrev = new System.Windows.Forms.MenuItem();
            this.SuspendLayout();
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.mnuBack);
            this.mainMenu1.MenuItems.Add(this.mnuMenu);
            // 
            // mnuBack
            // 
            this.mnuBack.Text = "Back";
            this.mnuBack.Click += new System.EventHandler(this.mnuBack_Click);
            // 
            // mnuMenu
            // 
            this.mnuMenu.MenuItems.Add(this.mnuNext);
            this.mnuMenu.MenuItems.Add(this.mnuPrev);
            this.mnuMenu.Text = "Menu";
            // 
            // txtNote
            // 
            this.txtNote.BackColor = System.Drawing.SystemColors.Control;
            this.txtNote.Location = new System.Drawing.Point(4, 4);
            this.txtNote.Multiline = true;
            this.txtNote.Name = "txtNote";
            this.txtNote.ReadOnly = true;
            this.txtNote.Size = new System.Drawing.Size(233, 261);
            this.txtNote.TabIndex = 0;
            // 
            // mnuNext
            // 
            this.mnuNext.Text = "Next Note";
            this.mnuNext.Click += new System.EventHandler(this.mnuNext_Click);
            // 
            // mnuPrev
            // 
            this.mnuPrev.Text = "Prev Note";
            this.mnuPrev.Click += new System.EventHandler(this.mnuPrev_Click);
            // 
            // Presentation
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.txtNote);
            this.Menu = this.mainMenu1;
            this.Name = "Presentation";
            this.Text = "Presentation";
            this.Closed += new System.EventHandler(this.Presentation_Closed);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MenuItem mnuBack;
        private System.Windows.Forms.MenuItem mnuMenu;
        private System.Windows.Forms.TextBox txtNote;
        private System.Windows.Forms.MenuItem mnuNext;
        private System.Windows.Forms.MenuItem mnuPrev;
    }
}