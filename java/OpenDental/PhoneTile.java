//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.PIn;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.ClockStatusEnum;
import OpenDentBusiness.Phone;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.Phones;
import OpenDentBusiness.Security;
import OpenDental.PhoneTile;

public class PhoneTile  extends UserControl 
{

    private Phone phoneCur;
    public TimeSpan TimeDelta = new TimeSpan();
    /**
    * 
    */
    public EventHandler GoToChanged = null;
    /**
    * 
    */
    public EventHandler SelectedTileChanged = null;
    /**
    * 
    */
    public EventHandler ScreenshotClick = null;
    /**
    * Object passed in from parent form.  Event will be fired from that form.
    */
    public ContextMenuStrip MenuNumbers = new ContextMenuStrip();
    /**
    * Object passed in from parent form.  Event will be fired from that form.
    */
    public ContextMenuStrip MenuStatus = new ContextMenuStrip();
    private boolean layoutHorizontal = new boolean();
    public boolean ShowImageForced = new boolean();
    public PhoneTile() throws Exception {
        initializeComponent();
    }

    /**
    * Set phone and triage flag to display. Get/Set accessor won't work here because we require 2 seperate fields in order to update the control properly.
    */
    public void setPhone(Phone phone, PhoneEmpDefault phoneEmpDefault, boolean isTriageOperator) throws Exception {
        phoneCur = phone;
        if (phoneCur == null)
        {
            //empty out everything and return
            this.Visible = false;
            pictureWebCam.Image = null;
            //or just make it not visible?
            pictureInUse.Visible = false;
            labelExtensionName.Text = "";
            labelStatusAndNote.Text = "";
            labelTime.Text = "";
            labelTime.BackColor = this.BackColor;
            labelCustomer.Text = "";
            return ;
        }
         
        this.Visible = true;
        if (ShowImageForced)
        {
            pictureWebCam.Image = PIn.Bitmap(phoneCur.WebCamImage);
            pictureWebCam.Visible = true;
        }
        else if (phoneCur.ClockStatus == ClockStatusEnum.Home || phoneCur.ClockStatus == ClockStatusEnum.None || phoneCur.ClockStatus == ClockStatusEnum.Off)
        {
            pictureWebCam.Image = null;
            pictureWebCam.Visible = false;
        }
        else if (phoneCur.ClockStatus == ClockStatusEnum.Break || phoneCur.ClockStatus == ClockStatusEnum.Lunch)
        {
            pictureWebCam.Visible = true;
            Bitmap bmp = new Bitmap(pictureWebCam.Width, pictureWebCam.Height);
            Graphics g = Graphics.FromImage(bmp);
            try
            {
                g.FillRectangle(SystemBrushes.Control, 0, 0, bmp.Width, bmp.Height);
                String strStat = phoneCur.ClockStatus.ToString();
                SizeF sizef = g.MeasureString(strStat, labelStatusAndNote.Font);
                g.DrawString(strStat, labelStatusAndNote.Font, SystemBrushes.GrayText, (bmp.Width - sizef.Width) / 2, (bmp.Height - sizef.Height) / 2);
                pictureWebCam.Image = (Image)bmp.Clone();
            }
            finally
            {
                g.Dispose();
                g = null;
                bmp.Dispose();
                bmp = null;
            }
        }
        else
        {
            pictureWebCam.Visible = true;
            pictureWebCam.Image = PIn.Bitmap(phoneCur.WebCamImage);
        }   
        if (StringSupport.equals(phoneCur.Description, ""))
        {
            pictureInUse.Visible = false;
        }
        else
        {
            pictureInUse.Visible = true;
        } 
        labelExtensionName.Text = "";
        String str = phoneCur.ClockStatus.ToString();
        //Check if the user is logged in.
        if (phoneCur.ClockStatus == ClockStatusEnum.None || phoneCur.ClockStatus == ClockStatusEnum.Home)
        {
            str = "Clock In";
        }
         
        //Always show ext and name, no matter if user is clocked in or not. This keeps phone tiles from appearing blank with no extension and name.
        String nameStr = "Vacant";
        if (!StringSupport.equals(phoneCur.EmployeeName, ""))
        {
            nameStr = phoneCur.EmployeeName;
        }
         
        labelExtensionName.Text = phoneCur.Extension.ToString() + " - " + nameStr;
        labelStatusAndNote.Text = str;
        DateTime dateTimeStart = phoneCur.DateTimeStart;
        if (dateTimeStart.Date == DateTime.Today)
        {
            TimeSpan span = DateTime.Now - dateTimeStart + TimeDelta;
            DateTime timeOfDay = DateTime.Today + span;
            labelTime.Text = timeOfDay.ToString("H:mm:ss");
        }
        else
        {
            labelTime.Text = "";
        } 
        if (phoneCur.ClockStatus == ClockStatusEnum.Home || phoneCur.ClockStatus == ClockStatusEnum.None || phoneCur.ClockStatus == ClockStatusEnum.Break)
        {
            labelTime.BackColor = this.BackColor;
        }
        else
        {
            //No color if employee is not currently working.
            Color outerColor = new Color();
            Color innerColor = new Color();
            Color fontColor = new Color();
            boolean isTriageOperatorOnTheClock = false;
            //get the cubicle color and triage status
            RefSupport<Color> refVar___0 = new RefSupport<Color>();
            RefSupport<Color> refVar___1 = new RefSupport<Color>();
            RefSupport<Color> refVar___2 = new RefSupport<Color>();
            RefSupport<boolean> refVar___3 = new RefSupport<boolean>();
            Phones.getPhoneColor(phone,phoneEmpDefault,false,refVar___0,refVar___1,refVar___2,refVar___3);
            outerColor = refVar___0.getValue();
            innerColor = refVar___1.getValue();
            fontColor = refVar___2.getValue();
            isTriageOperatorOnTheClock = refVar___3.getValue();
            if (!timerFlash.Enabled)
            {
                //if the control is already flashing then don't overwrite the colors. this would cause a "spastic" flash effect.
                labelTime.BackColor = outerColor;
            }
             
            if (phoneCur.ClockStatus == ClockStatusEnum.NeedsHelp)
            {
                if (!timerFlash.Enabled)
                {
                    //Only start the flash timer and color the control once. This prevents over-flashing effect.
                    labelTime.Tag = new Object[]{ false, labelTime.BackColor };
                    timerFlash.Start();
                }
                 
            }
             
        } 
        if (phoneCur.ClockStatus == ClockStatusEnum.Home || phoneCur.ClockStatus == ClockStatusEnum.None)
        {
            labelTime.BorderStyle = System.Windows.Forms.BorderStyle.None;
        }
        else
        {
            //Remove color box if employee is not currently working.
            labelTime.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        } 
        if (phoneCur.ClockStatus != ClockStatusEnum.NeedsHelp)
        {
            //Always assume the flash timer was previously turned on and turn it off here. No harm if it' already off.
            timerFlash.Stop();
        }
         
        labelCustomer.Text = phoneCur.CustomerNumber;
    }

    /**
    * use SetPhone function to set phone and triage flag
    */
    public Phone getPhoneCur() throws Exception {
        return phoneCur;
    }

    public boolean getLayoutHorizontal() throws Exception {
        return layoutHorizontal;
    }

    public void setLayoutHorizontal(boolean value) throws Exception {
        layoutHorizontal = value;
        if (layoutHorizontal)
        {
            //173,7
            pictureWebCam.Location = new Point(173, 7);
            pictureInUse.Location = new Point(224, 25);
            //51,18);
            labelExtensionName.Location = new Point(221, 9);
            //48,2);
            labelStatusAndNote.Location = new Point(249, 25);
            //76,18);
            labelStatusAndNote.TextAlign = ContentAlignment.MiddleLeft;
            labelStatusAndNote.Size = new Size(77, 16);
            labelTime.Location = new Point(329, 11);
            //156,4);
            labelTime.Size = new Size(56, 16);
            labelCustomer.Location = new Point(332, 27);
            //159,20);
            labelCustomer.Size = new Size(147, 16);
            labelCustomer.TextAlign = ContentAlignment.MiddleLeft;
        }
        else
        {
            //vertical
            pictureWebCam.Location = new Point(51, 3);
            pictureInUse.Location = new Point(14, 43);
            labelExtensionName.Location = new Point(37, 43);
            labelStatusAndNote.Location = new Point(0, 61);
            labelStatusAndNote.TextAlign = ContentAlignment.MiddleCenter;
            labelStatusAndNote.Size = new Size(150, 16);
            labelTime.Location = new Point(0, 81);
            labelTime.Size = new Size(150, 16);
            labelCustomer.Location = new Point(0, 99);
            labelCustomer.Size = new Size(150, 16);
            labelCustomer.TextAlign = ContentAlignment.MiddleCenter;
        } 
    }

    protected Size getDefaultSize() throws Exception {
        if (layoutHorizontal)
        {
            return new Size(595, 37);
        }
        else
        {
            return new Size(150, 122);
        } 
    }

    //vertical
    private void labelCustomer_MouseClick(Object sender, MouseEventArgs e) throws Exception {
        if ((e.Button & MouseButtons.Right) == MouseButtons.Right)
        {
            return ;
        }
         
        onGoToChanged();
    }

    protected void onGoToChanged() throws Exception {
        if (GoToChanged != null)
        {
            GoToChanged(this, new EventArgs());
        }
         
    }

    private void labelCustomer_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button != MouseButtons.Right)
        {
            return ;
        }
         
        if (phoneCur == null)
        {
            return ;
        }
         
        onSelectedTileChanged();
        MenuNumbers.Show(labelCustomer, e.Location);
    }

    private void labelStatusAndNote_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button != MouseButtons.Right)
        {
            return ;
        }
         
        if (phoneCur == null)
        {
            return ;
        }
         
        //Jason - Allowed to be 0 here.  The Security.UserCur.EmpNum will be used when they go to clock in and that is where the 0 check needs to be.
        //if(phoneCur.EmployeeNum==0) {
        //  return;
        //}
        onSelectedTileChanged();
        boolean allowStatusEdit = ClockEvents.isClockedIn(getPhoneCur().EmployeeNum);
        if (getPhoneCur().EmployeeNum == Security.getCurUser().EmployeeNum)
        {
            //Always allow status edit for yourself
            allowStatusEdit = true;
        }
         
        if (getPhoneCur().ClockStatus == ClockStatusEnum.NeedsHelp)
        {
            //Always allow any employee to change any other employee from NeedsAssistance to Available
            allowStatusEdit = true;
        }
         
        String statusOnBehalfOf = getPhoneCur().EmployeeName;
        boolean allowSetSelfAvailable = false;
        //No one is clocked in at this extension.
        if (!ClockEvents.isClockedIn(getPhoneCur().EmployeeNum) && !ClockEvents.isClockedIn(Security.getCurUser().EmployeeNum))
        {
            //This user is not clocked in either.
            //Vacant extension and this user is not clocked in so allow this user to clock in at this extension.
            statusOnBehalfOf = Security.getCurUser().UserName;
            allowSetSelfAvailable = true;
        }
         
        addToolstripGroup("menuItemStatusOnBehalf","Status for: " + statusOnBehalfOf);
        addToolstripGroup("menuItemRingGroupOnBehalf","Ringgroup for ext: " + getPhoneCur().Extension.ToString());
        addToolstripGroup("menuItemClockOnBehalf","Clock event for: " + getPhoneCur().EmployeeName);
        setToolstripItemText("menuItemAvailable",allowStatusEdit || allowSetSelfAvailable);
        setToolstripItemText("menuItemTraining",allowStatusEdit);
        setToolstripItemText("menuItemTeamAssist",allowStatusEdit);
        setToolstripItemText("menuItemNeedsHelp",allowStatusEdit);
        setToolstripItemText("menuItemWrapUp",allowStatusEdit);
        setToolstripItemText("menuItemOfflineAssist",allowStatusEdit);
        setToolstripItemText("menuItemUnavailable",allowStatusEdit);
        setToolstripItemText("menuItemBackup",allowStatusEdit);
        setToolstripItemText("menuItemLunch",allowStatusEdit);
        setToolstripItemText("menuItemHome",allowStatusEdit);
        setToolstripItemText("menuItemBreak",allowStatusEdit);
        MenuStatus.Show(labelStatusAndNote, e.Location);
    }

    private void addToolstripGroup(String groupName, String itemText) throws Exception {
        ToolStripItem[] tsiFound = MenuStatus.Items.Find(groupName, false);
        if (tsiFound == null || tsiFound.Length <= 0)
        {
            return ;
        }
         
        tsiFound[0].Text = itemText;
    }

    private void setToolstripItemText(String toolStripItemName, boolean isClockedIn) throws Exception {
        ToolStripItem[] tsiFound = MenuStatus.Items.Find(toolStripItemName, false);
        if (tsiFound == null || tsiFound.Length <= 0)
        {
            return ;
        }
         
        //set back to default
        tsiFound[0].Text = tsiFound[0].Text.Replace(" (Not Clocked In)", "");
        if (isClockedIn)
        {
            tsiFound[0].Enabled = true;
        }
        else
        {
            tsiFound[0].Enabled = false;
            tsiFound[0].Text = tsiFound[0].Text + " (Not Clocked In)";
        } 
    }

    protected void onSelectedTileChanged() throws Exception {
        if (SelectedTileChanged != null)
        {
            SelectedTileChanged(this, new EventArgs());
        }
         
    }

    private void phoneTile_Click(Object sender, EventArgs e) throws Exception {
        if (ScreenshotClick != null)
        {
            ScreenshotClick(this, new EventArgs());
        }
         
    }

    private void timerFlash_Tick(Object sender, EventArgs e) throws Exception {
        boolean isColored = true;
        Color flashColor = SystemColors.Control;
        if (labelTime.Tag != null && labelTime.Tag instanceof Object[] && ((Object[])labelTime.Tag).Length >= 2)
        {
            if (((Object[])labelTime.Tag)[0] instanceof boolean)
            {
                isColored = (boolean)((Object[])labelTime.Tag)[0];
            }
             
            if (((Object[])labelTime.Tag)[1] instanceof Color)
            {
                flashColor = (Color)((Object[])labelTime.Tag)[1];
            }
             
        }
         
        labelTime.BackColor = isColored ? this.BackColor : flashColor;
        labelTime.Tag = new Object[]{ !isColored, flashColor };
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(PhoneTile.class);
        this.pictureWebCam = new System.Windows.Forms.PictureBox();
        this.labelExtensionName = new System.Windows.Forms.Label();
        this.labelStatusAndNote = new System.Windows.Forms.Label();
        this.pictureInUse = new System.Windows.Forms.PictureBox();
        this.labelCustomer = new System.Windows.Forms.Label();
        this.labelTime = new System.Windows.Forms.Label();
        this.service11 = new OpenDental.localhost.Service1();
        this.timerFlash = new System.Windows.Forms.Timer(this.components);
        ((System.ComponentModel.ISupportInitialize)(this.pictureWebCam)).BeginInit();
        ((System.ComponentModel.ISupportInitialize)(this.pictureInUse)).BeginInit();
        this.SuspendLayout();
        //
        // pictureWebCam
        //
        this.pictureWebCam.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.pictureWebCam.Image = ((System.Drawing.Image)(resources.GetObject("pictureWebCam.Image")));
        this.pictureWebCam.Location = new System.Drawing.Point(4, 7);
        this.pictureWebCam.Name = "pictureWebCam";
        this.pictureWebCam.Size = new System.Drawing.Size(50, 37);
        this.pictureWebCam.TabIndex = 0;
        this.pictureWebCam.TabStop = false;
        this.pictureWebCam.Click += new System.EventHandler(this.phoneTile_Click);
        //
        // labelExtensionName
        //
        this.labelExtensionName.BackColor = System.Drawing.Color.Transparent;
        this.labelExtensionName.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelExtensionName.Location = new System.Drawing.Point(52, 9);
        this.labelExtensionName.Name = "labelExtensionName";
        this.labelExtensionName.Size = new System.Drawing.Size(105, 16);
        this.labelExtensionName.TabIndex = 1;
        this.labelExtensionName.Text = "104 - Jordan";
        this.labelExtensionName.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelStatusAndNote
        //
        this.labelStatusAndNote.BackColor = System.Drawing.Color.Transparent;
        this.labelStatusAndNote.Location = new System.Drawing.Point(80, 25);
        this.labelStatusAndNote.Name = "labelStatusAndNote";
        this.labelStatusAndNote.Size = new System.Drawing.Size(77, 16);
        this.labelStatusAndNote.TabIndex = 2;
        this.labelStatusAndNote.Text = "Available";
        this.labelStatusAndNote.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.labelStatusAndNote.MouseUp += new System.Windows.Forms.MouseEventHandler(this.labelStatusAndNote_MouseUp);
        //
        // pictureInUse
        //
        this.pictureInUse.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pictureInUse.BackgroundImage")));
        this.pictureInUse.Location = new System.Drawing.Point(55, 25);
        this.pictureInUse.Name = "pictureInUse";
        this.pictureInUse.Size = new System.Drawing.Size(21, 17);
        this.pictureInUse.TabIndex = 3;
        this.pictureInUse.TabStop = false;
        //
        // labelCustomer
        //
        this.labelCustomer.BackColor = System.Drawing.Color.Transparent;
        this.labelCustomer.Location = new System.Drawing.Point(160, 27);
        this.labelCustomer.Name = "labelCustomer";
        this.labelCustomer.Size = new System.Drawing.Size(147, 16);
        this.labelCustomer.TabIndex = 4;
        this.labelCustomer.Text = "Customer phone #";
        this.labelCustomer.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.labelCustomer.MouseClick += new System.Windows.Forms.MouseEventHandler(this.labelCustomer_MouseClick);
        this.labelCustomer.MouseUp += new System.Windows.Forms.MouseEventHandler(this.labelCustomer_MouseUp);
        //
        // labelTime
        //
        this.labelTime.BackColor = System.Drawing.Color.Lime;
        this.labelTime.Location = new System.Drawing.Point(160, 11);
        this.labelTime.Name = "labelTime";
        this.labelTime.Size = new System.Drawing.Size(56, 16);
        this.labelTime.TabIndex = 5;
        this.labelTime.Text = "01:10:13";
        this.labelTime.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // service11
        //
        this.service11.setUrl("http://localhost:3824/Service1.asmx");
        this.service11.setUseDefaultCredentials(true);
        //
        // timerFlash
        //
        this.timerFlash.Interval = 300;
        this.timerFlash.Tick += new System.EventHandler(this.timerFlash_Tick);
        //
        // PhoneTile
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.Controls.Add(this.pictureInUse);
        this.Controls.Add(this.pictureWebCam);
        this.Controls.Add(this.labelTime);
        this.Controls.Add(this.labelCustomer);
        this.Controls.Add(this.labelStatusAndNote);
        this.Controls.Add(this.labelExtensionName);
        this.DoubleBuffered = true;
        this.Name = "PhoneTile";
        this.Size = new System.Drawing.Size(310, 50);
        ((System.ComponentModel.ISupportInitialize)(this.pictureWebCam)).EndInit();
        ((System.ComponentModel.ISupportInitialize)(this.pictureInUse)).EndInit();
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.PictureBox pictureWebCam = new System.Windows.Forms.PictureBox();
    private System.Windows.Forms.Label labelExtensionName = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelStatusAndNote = new System.Windows.Forms.Label();
    private System.Windows.Forms.PictureBox pictureInUse = new System.Windows.Forms.PictureBox();
    private System.Windows.Forms.Label labelCustomer = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelTime = new System.Windows.Forms.Label();
    private OpenDental.localhost.Service1 service11;
    private System.Windows.Forms.Timer timerFlash = new System.Windows.Forms.Timer();
}


