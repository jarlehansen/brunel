namespace CAMR_3
{
    partial class Form1
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
            this.mnuExit = new System.Windows.Forms.MenuItem();
            this.mnuMenu = new System.Windows.Forms.MenuItem();
            this.menuItem3 = new System.Windows.Forms.MenuItem();
            this.menuItem6 = new System.Windows.Forms.MenuItem();
            this.button1 = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.textBox2 = new System.Windows.Forms.TextBox();
            this.mnuPresentation = new System.Windows.Forms.MenuItem();
            this.mnuHistory = new System.Windows.Forms.MenuItem();
            this.mnuDeviceInfo = new System.Windows.Forms.MenuItem();
            this.mnuConfig = new System.Windows.Forms.MenuItem();
            this.SuspendLayout();
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.mnuExit);
            this.mainMenu1.MenuItems.Add(this.mnuMenu);
            // 
            // mnuExit
            // 
            this.mnuExit.Text = "Exit";
            this.mnuExit.Click += new System.EventHandler(this.mnuExit_Click);
            // 
            // mnuMenu
            // 
            this.mnuMenu.MenuItems.Add(this.menuItem3);
            this.mnuMenu.MenuItems.Add(this.menuItem6);
            this.mnuMenu.MenuItems.Add(this.mnuPresentation);
            this.mnuMenu.MenuItems.Add(this.mnuHistory);
            this.mnuMenu.MenuItems.Add(this.mnuDeviceInfo);
            this.mnuMenu.MenuItems.Add(this.mnuConfig);
            this.mnuMenu.Text = "Menu";
            // 
            // menuItem3
            // 
            this.menuItem3.Text = "write";
            this.menuItem3.Click += new System.EventHandler(this.menuItem3_Click);
            // 
            // menuItem6
            // 
            this.menuItem6.Text = "open page";
            this.menuItem6.Click += new System.EventHandler(this.menuItem6_Click);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(4, 245);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(72, 20);
            this.button1.TabIndex = 0;
            this.button1.Text = "button1";
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(4, 4);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(233, 21);
            this.textBox1.TabIndex = 1;
            // 
            // textBox2
            // 
            this.textBox2.Location = new System.Drawing.Point(4, 32);
            this.textBox2.Name = "textBox2";
            this.textBox2.Size = new System.Drawing.Size(233, 21);
            this.textBox2.TabIndex = 2;
            // 
            // mnuPresentation
            // 
            this.mnuPresentation.Text = "Presentation";
            this.mnuPresentation.Click += new System.EventHandler(this.mnuPresentation_Click);
            // 
            // mnuHistory
            // 
            this.mnuHistory.Text = "History";
            this.mnuHistory.Click += new System.EventHandler(this.mnuHistory_Click);
            // 
            // mnuDeviceInfo
            // 
            this.mnuDeviceInfo.Text = "Device info";
            this.mnuDeviceInfo.Click += new System.EventHandler(this.mnuDeviceInfo_Click);
            // 
            // mnuConfig
            // 
            this.mnuConfig.Text = "Server config";
            this.mnuConfig.Click += new System.EventHandler(this.mnuConfig_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.textBox2);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.button1);
            this.Menu = this.mainMenu1;
            this.Name = "Form1";
            this.Text = "CAMR_0.9";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.MenuItem mnuExit;
        private System.Windows.Forms.TextBox textBox2;
        private System.Windows.Forms.MenuItem mnuMenu;
        private System.Windows.Forms.MenuItem menuItem3;
        private System.Windows.Forms.MenuItem menuItem6;
        private System.Windows.Forms.MenuItem mnuPresentation;
        private System.Windows.Forms.MenuItem mnuHistory;
        private System.Windows.Forms.MenuItem mnuDeviceInfo;
        private System.Windows.Forms.MenuItem mnuConfig;
    }
}

