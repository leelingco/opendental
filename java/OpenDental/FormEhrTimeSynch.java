//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.NTPv4;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDental.FormEhrTimeSynch;


/**
* Only shows for EHR users.  Synchronizes local time to specified NIST server.
*/
public class FormEhrTimeSynch  extends Form 
{

    private DateTime _timeNist = new DateTime();
    private DateTime _timeServer = new DateTime();
    private DateTime _timeLocal = new DateTime();
    /**
    * Set true when launched while OpenDental starts.  Will automatically check times and close form silently if server time is in synch with local time.
    */
    public boolean IsAutoLaunch = new boolean();
    public FormEhrTimeSynch() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEhrTime_Load(Object sender, EventArgs e) throws Exception {
        if (IsAutoLaunch)
        {
            //Already updated time fields. Dont need to check again, just open.
            refreshDisplays();
            return ;
        }
         
        textNistUrl.Text = PrefC.getString(PrefName.NistTimeServerUrl);
        synchTimes();
    }

    /**
    * Called from FormOpenDental.Load.  Updates local time and checks to see if server time is in synch, with a fast db call (only acurate to seconds, not miliseconds).
    */
    public boolean timesInSynchFast() throws Exception {
        this.Cursor = Cursors.WaitCursor;
        textNistUrl.Text = PrefC.getString(PrefName.NistTimeServerUrl);
        double nistOffset = getNistOffset();
        if (nistOffset == double.MaxValue)
        {
            //Timed out
            MsgBox.show(this,"No response received from NIST time server.  Click refresh time after four seconds.");
            this.Cursor = Cursors.Default;
            return false;
        }
         
        if (nistOffset == double.MinValue)
        {
            //Invalid Nist Server Address
            this.Cursor = Cursors.Default;
            return false;
        }
         
        //Get current times from offsets
        _timeLocal = DateTime.Now;
        Stopwatch stopwatch = new Stopwatch();
        //Used to keep NIST time in time even when system fails to set local machine time (Can't pull it later)
        stopwatch.Start();
        _timeNist = _timeLocal.AddMilliseconds(nistOffset);
        try
        {
            WindowsTime.setTime(_timeNist);
        }
        catch (Exception __dummyCatchVar0)
        {
            //Sets local machine time
            MsgBox.show(this,"Error setting local machine time.");
        }

        _timeLocal = DateTime.Now;
        //Update time since it has now been set
        double serverOffset = (MiscData.getNowDateTime() - DateTime.Now).TotalSeconds;
        //Cannot get milliseconds from Now() in Mysql Pre-5.6.4, Only gets whole seconds.
        _timeServer = _timeLocal.AddSeconds(serverOffset);
        if (serverInSynchFast() && localInSynch())
        {
            return true;
        }
         
        //All times in synch
        //Some times out of synch, so form will open, but we don't want to make another call to NIST server.
        serverOffset = (MiscData.getNowDateTimeWithMilli() - DateTime.Now).TotalSeconds;
        //_timeServer needs to be more accurate before displaying
        _timeLocal = DateTime.Now;
        stopwatch.Stop();
        _timeServer = _timeLocal.AddSeconds(serverOffset);
        _timeNist = _timeNist.AddMilliseconds(stopwatch.ElapsedMilliseconds);
        this.Cursor = Cursors.Default;
        return false;
    }

    /**
    * Updates local time and Refreshes all time variables.  Saves NIST server URL as preference.
    */
    private void synchTimes() throws Exception {
        this.Cursor = Cursors.WaitCursor;
        //Get NistTime Offset
        double nistOffset = getNistOffset();
        if (nistOffset == double.MaxValue)
        {
            //Timed out
            MsgBox.show(this,"No response received from NIST time server.  Click synch time after four seconds.");
            this.Cursor = Cursors.Default;
            return ;
        }
         
        if (nistOffset == double.MinValue)
        {
            //Invalid Nist Server Address
            this.Cursor = Cursors.Default;
            return ;
        }
         
        double serverOffset = (MiscData.getNowDateTimeWithMilli() - DateTime.Now).TotalMilliseconds;
        //Get current times from offsets
        _timeLocal = DateTime.Now;
        _timeServer = _timeLocal.AddMilliseconds(serverOffset);
        _timeNist = _timeLocal.AddMilliseconds(nistOffset);
        try
        {
            WindowsTime.setTime(_timeNist);
        }
        catch (Exception __dummyCatchVar1)
        {
            //Sets local machine time
            MsgBox.show(this,"Error setting local machine time.");
        }

        _timeLocal = DateTime.Now;
        //Update time since it has now been set
        this.Cursor = Cursors.Default;
        refreshDisplays();
    }

    /**
    * Get the offset from the nist server and DateTime.Now().  Returns double.MinValue if invalid NIST Server URL.  Returns double.MaxValue if request timed out.
    */
    private double getNistOffset() throws Exception {
        //NistTime
        NTPv4 ntp = new NTPv4();
        double nistOffset = new double();
        try
        {
            nistOffset = ntp.getTime(textNistUrl.Text);
        }
        catch (Exception __dummyCatchVar2)
        {
            MsgBox.show(this,"Invalid NIST Server URL");
            return double.MinValue;
        }

        timer1.Enabled = true;
        return nistOffset;
    }

    /**
    * Returns true if server time is out of synch with local machine.
    */
    private boolean serverInSynch() throws Exception {
        //Would be better to check against NIST time, but doing it this way to match 2014 EHR Proctor Sheet conditions.
        double difference = Math.Abs(_timeServer.Subtract(_timeLocal).TotalSeconds);
        if (difference > .99)
        {
            return false;
        }
         
        return true;
    }

    /**
    * Used when launching check automatically on startup.  Rounds to whole seconds.  Returns true if server time is out of synch with local machine.
    */
    private boolean serverInSynchFast() throws Exception {
        double difference = Math.Abs(_timeServer.Subtract(_timeLocal).TotalSeconds);
        if (Math.Floor(difference) > 1)
        {
            return false;
        }
         
        return true;
    }

    /**
    * Returns true if local time is out of synch with NIST server.  Should always be true since we just updated time.
    */
    private boolean localInSynch() throws Exception {
        double difference = Math.Abs(_timeLocal.Subtract(_timeNist).TotalSeconds);
        if (difference > .99)
        {
            return false;
        }
         
        return true;
    }

    /**
    * Refresh the time textboxes.  Stops users from sending requests to NIST server more than once every 4 seconds.
    */
    private void butRefreshTime_Click(Object sender, EventArgs e) throws Exception {
        if (timer1.Enabled)
        {
            MsgBox.show(this,"Cannot send a time request more than once every four seconds");
            return ;
        }
         
        synchTimes();
    }

    /**
    * Refresh all time displays and labels.
    */
    private void refreshDisplays() throws Exception {
        if (_timeNist == DateTime.MinValue || _timeServer == DateTime.MinValue || _timeLocal == DateTime.MinValue)
        {
            textLocalTime.Text = "";
            textNistTime.Text = "";
            textServerTime.Text = "";
            labelDatabaseOutOfSynch.Visible = false;
            labelLocalOutOfSynch.Visible = false;
            labelAllSynched.Visible = false;
            return ;
        }
         
        textNistTime.Text = _timeNist.ToString("hh:mm:ss.fff tt");
        textServerTime.Text = _timeServer.ToString("hh:mm:ss.fff tt");
        textLocalTime.Text = _timeLocal.ToString("hh:mm:ss.fff tt");
        //Update NistURL preference
        Prefs.UpdateString(PrefName.NistTimeServerUrl, textNistUrl.Text);
        //Display labels if out of synch.
        labelDatabaseOutOfSynch.Visible = !serverInSynch();
        labelLocalOutOfSynch.Visible = !localInSynch();
        if (labelDatabaseOutOfSynch.Visible || labelLocalOutOfSynch.Visible)
        {
            labelAllSynched.Visible = false;
        }
        else
        {
            labelAllSynched.Visible = true;
        } 
    }

    //All times in synch
    /**
    * Do not allow user to send another request until timer has ticked.
    */
    private void timer1_Tick(Object sender, EventArgs e) throws Exception {
        timer1.Enabled = false;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrTimeSynch.class);
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.timer1 = new System.Windows.Forms.Timer(this.components);
        this.label6 = new System.Windows.Forms.Label();
        this.labelDatabaseOutOfSynch = new System.Windows.Forms.Label();
        this.labelLocalOutOfSynch = new System.Windows.Forms.Label();
        this.labelAllSynched = new System.Windows.Forms.Label();
        this.textLocalTime = new OpenDental.ODtextBox();
        this.textServerTime = new OpenDental.ODtextBox();
        this.textNistTime = new OpenDental.ODtextBox();
        this.butRefreshTime = new OpenDental.UI.Button();
        this.textNistUrl = new OpenDental.ODtextBox();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(25, 78);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(106, 20);
        this.label2.TabIndex = 81;
        this.label2.Text = "NIST server address";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(22, 104);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(109, 20);
        this.label1.TabIndex = 82;
        this.label1.Text = "NIST server";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(25, 130);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(106, 20);
        this.label3.TabIndex = 83;
        this.label3.Text = "Database server";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(28, 156);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(103, 20);
        this.label4.TabIndex = 84;
        this.label4.Text = "Local machine";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(28, 209);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(374, 111);
        this.label5.TabIndex = 85;
        this.label5.Text = resources.GetString("label5.Text");
        //
        // timer1
        //
        this.timer1.Interval = 4000;
        this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(25, 21);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(456, 44);
        this.label6.TabIndex = 87;
        this.label6.Text = resources.GetString("label6.Text");
        //
        // labelDatabaseOutOfSynch
        //
        this.labelDatabaseOutOfSynch.ForeColor = System.Drawing.Color.DarkRed;
        this.labelDatabaseOutOfSynch.Location = new System.Drawing.Point(309, 134);
        this.labelDatabaseOutOfSynch.Name = "labelDatabaseOutOfSynch";
        this.labelDatabaseOutOfSynch.Size = new System.Drawing.Size(187, 19);
        this.labelDatabaseOutOfSynch.TabIndex = 88;
        this.labelDatabaseOutOfSynch.Text = "Database time out of synch with local";
        this.labelDatabaseOutOfSynch.Visible = false;
        //
        // labelLocalOutOfSynch
        //
        this.labelLocalOutOfSynch.ForeColor = System.Drawing.Color.DarkRed;
        this.labelLocalOutOfSynch.Location = new System.Drawing.Point(326, 160);
        this.labelLocalOutOfSynch.Name = "labelLocalOutOfSynch";
        this.labelLocalOutOfSynch.Size = new System.Drawing.Size(187, 19);
        this.labelLocalOutOfSynch.TabIndex = 89;
        this.labelLocalOutOfSynch.Text = "Local time out of synch with NIST";
        this.labelLocalOutOfSynch.Visible = false;
        //
        // labelAllSynched
        //
        this.labelAllSynched.ForeColor = System.Drawing.Color.DarkRed;
        this.labelAllSynched.Location = new System.Drawing.Point(294, 108);
        this.labelAllSynched.Name = "labelAllSynched";
        this.labelAllSynched.Size = new System.Drawing.Size(200, 19);
        this.labelAllSynched.TabIndex = 90;
        this.labelAllSynched.Text = "All times synchronized within one second";
        this.labelAllSynched.Visible = false;
        //
        // textLocalTime
        //
        this.textLocalTime.AcceptsTab = true;
        this.textLocalTime.DetectUrls = false;
        this.textLocalTime.Location = new System.Drawing.Point(137, 156);
        this.textLocalTime.Multiline = false;
        this.textLocalTime.Name = "textLocalTime";
        this.textLocalTime.setQuickPasteType(OpenDentBusiness.QuickPasteType.MedicationEdit);
        this.textLocalTime.ReadOnly = true;
        this.textLocalTime.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textLocalTime.Size = new System.Drawing.Size(111, 20);
        this.textLocalTime.TabIndex = 79;
        this.textLocalTime.Text = "";
        //
        // textServerTime
        //
        this.textServerTime.AcceptsTab = true;
        this.textServerTime.DetectUrls = false;
        this.textServerTime.Location = new System.Drawing.Point(137, 130);
        this.textServerTime.Multiline = false;
        this.textServerTime.Name = "textServerTime";
        this.textServerTime.setQuickPasteType(OpenDentBusiness.QuickPasteType.MedicationEdit);
        this.textServerTime.ReadOnly = true;
        this.textServerTime.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textServerTime.Size = new System.Drawing.Size(111, 20);
        this.textServerTime.TabIndex = 78;
        this.textServerTime.Text = "";
        //
        // textNistTime
        //
        this.textNistTime.AcceptsTab = true;
        this.textNistTime.DetectUrls = false;
        this.textNistTime.Location = new System.Drawing.Point(137, 104);
        this.textNistTime.Multiline = false;
        this.textNistTime.Name = "textNistTime";
        this.textNistTime.setQuickPasteType(OpenDentBusiness.QuickPasteType.MedicationEdit);
        this.textNistTime.ReadOnly = true;
        this.textNistTime.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNistTime.Size = new System.Drawing.Size(111, 20);
        this.textNistTime.TabIndex = 77;
        this.textNistTime.Text = "";
        //
        // butRefreshTime
        //
        this.butRefreshTime.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefreshTime.setAutosize(true);
        this.butRefreshTime.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefreshTime.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefreshTime.setCornerRadius(4F);
        this.butRefreshTime.Location = new System.Drawing.Point(398, 76);
        this.butRefreshTime.Name = "butRefreshTime";
        this.butRefreshTime.Size = new System.Drawing.Size(83, 24);
        this.butRefreshTime.TabIndex = 76;
        this.butRefreshTime.Text = "Synch Time";
        this.butRefreshTime.Click += new System.EventHandler(this.butRefreshTime_Click);
        //
        // textNistUrl
        //
        this.textNistUrl.AcceptsTab = true;
        this.textNistUrl.DetectUrls = false;
        this.textNistUrl.Location = new System.Drawing.Point(137, 78);
        this.textNistUrl.Multiline = false;
        this.textNistUrl.Name = "textNistUrl";
        this.textNistUrl.setQuickPasteType(OpenDentBusiness.QuickPasteType.MedicationEdit);
        this.textNistUrl.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNistUrl.Size = new System.Drawing.Size(255, 20);
        this.textNistUrl.TabIndex = 75;
        this.textNistUrl.Text = "";
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(438, 291);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormEhrTimeSynch
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(538, 342);
        this.Controls.Add(this.labelAllSynched);
        this.Controls.Add(this.labelLocalOutOfSynch);
        this.Controls.Add(this.labelDatabaseOutOfSynch);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textLocalTime);
        this.Controls.Add(this.textServerTime);
        this.Controls.Add(this.textNistTime);
        this.Controls.Add(this.butRefreshTime);
        this.Controls.Add(this.textNistUrl);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.label5);
        this.Name = "FormEhrTimeSynch";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Time Synchronization";
        this.Load += new System.EventHandler(this.FormEhrTime_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.ODtextBox textNistUrl;
    private OpenDental.UI.Button butRefreshTime;
    private OpenDental.ODtextBox textNistTime;
    private OpenDental.ODtextBox textServerTime;
    private OpenDental.ODtextBox textLocalTime;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Timer timer1 = new System.Windows.Forms.Timer();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelDatabaseOutOfSynch = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelLocalOutOfSynch = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelAllSynched = new System.Windows.Forms.Label();
}


