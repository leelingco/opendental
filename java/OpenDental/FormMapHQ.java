//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.FormMapHQ;
import OpenDental.FormMapSetup;
import OpenDental.MapAreaRoomControl;
import OpenDental.Properties.Resources;
import OpenDentBusiness.ClockStatusEnum;
import OpenDentBusiness.Employees;
import OpenDentBusiness.MapArea;
import OpenDentBusiness.MapAreas;
import OpenDentBusiness.MapItemType;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Phone;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.Phones;
import OpenDentBusiness.Phones.PhoneColorScheme;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;

public class FormMapHQ  extends Form 
{

    /**
    * Keep track of full screen state
    */
    private boolean _isFullScreen = new boolean();
    /**
    * This is the difference between server time and local computer time.  Used to ensure that times displayed are accurate to the second.  This value is usally just a few seconds, but possibly a few minutes.
    */
    private TimeSpan _timeDelta = new TimeSpan();
    public FormMapHQ() throws Exception {
        initializeComponent();
        //Do not do anything to do with database or control init here. We will be using this ctor later in order to create a temporary object so we can figure out what size the form should be when the user comes back from full screen mode. Wait until FormMapHQ_Load to do anything meaningful.
        _isFullScreen = false;
        _timeDelta = MiscData.getNowDateTime() - DateTime.Now;
    }

    private void formMapHQ_Load(Object sender, EventArgs e) throws Exception {
        fillMapAreaPanel();
        labelTriageOpsStaff.setInnerColor(PhoneColorScheme.COLOR_DUAL_InnerTriageHere);
        labelTriageOpsStaff.setOuterColor(PhoneColorScheme.COLOR_DUAL_OuterTriage);
    }

    /**
    * Setup the map panel with the cubicles and labels before filling with real-time data. Call this on load or anytime the cubicle layout has changed.
    */
    private void fillMapAreaPanel() throws Exception {
        mapAreaPanelHQ.Controls.Clear();
        //fill the panel
        List<MapArea> clinicMapItems = MapAreas.refresh();
        for (int i = 0;i < clinicMapItems.Count;i++)
        {
            if (clinicMapItems[i].ItemType == MapItemType.Room)
            {
                mapAreaPanelHQ.AddCubicle(clinicMapItems[i]);
            }
            else if (clinicMapItems[i].ItemType == MapItemType.DisplayLabel)
            {
                mapAreaPanelHQ.AddDisplayLabel(clinicMapItems[i]);
            }
              
        }
    }

    /**
    * Refresh the phone panel every X seconds after it has already been setup.  Make sure to call FillMapAreaPanel before calling this the first time.
    */
    public void setPhoneList(List<PhoneEmpDefault> peds, List<Phone> phones) throws Exception {
        try
        {
            String title = "Call Center Status Map - Triage Coordinator - ";
            try
            {
                //get the triage coord label but don't fail just because we can't find it
                title += Employees.getNameFL(PrefC.getLong(PrefName.HQTriageCoordinator));
            }
            catch (Exception __dummyCatchVar0)
            {
                title += "Not Set";
            }

            labelTriageCoordinator.Text = title;
            labelCurrentTime.setText(DateTime.Now.ToShortTimeString());
            int triageStaffCount = 0;
            for (int i = 0;i < this.mapAreaPanelHQ.Controls.Count;i++)
            {
                //loop through all of our cubicles and labels and find the matches
                if (!(this.mapAreaPanelHQ.Controls[i] instanceof MapAreaRoomControl))
                {
                    continue;
                }
                 
                MapAreaRoomControl room = (MapAreaRoomControl)this.mapAreaPanelHQ.Controls[i];
                if (room.MapAreaItem.Extension == 0)
                {
                    //This cubicle has not been given an extension yet.
                    room.setEmpty(true);
                    continue;
                }
                 
                Phone phone = Phones.GetPhoneForExtension(phones, room.MapAreaItem.Extension);
                if (phone == null)
                {
                    //We have a cubicle with no corresponding phone entry.
                    room.setEmpty(true);
                    continue;
                }
                 
                PhoneEmpDefault phoneEmpDefault = PhoneEmpDefaults.GetEmpDefaultFromList(phone.EmployeeNum, peds);
                if (phoneEmpDefault == null)
                {
                    //We have a cubicle with no corresponding phone emp default entry.
                    room.setEmpty(true);
                    continue;
                }
                 
                //we got this far so we found a corresponding cubicle for this phone entry
                room.setEmployeeNum(phone.EmployeeNum);
                room.setEmployeeName(phone.EmployeeName);
                if (phone.DateTimeStart.Date == DateTime.Today)
                {
                    TimeSpan span = DateTime.Now - phone.DateTimeStart + _timeDelta;
                    DateTime timeOfDay = DateTime.Today + span;
                    room.setElapsed(timeOfDay.ToString("H:mm:ss"));
                }
                else
                {
                    room.setElapsed("");
                } 
                String status = phone.ClockStatus.ToString();
                //Check if the user is logged in.
                if (phone.ClockStatus == ClockStatusEnum.None || phone.ClockStatus == ClockStatusEnum.Home)
                {
                    status = "Home";
                }
                 
                room.setStatus(status);
                if (StringSupport.equals(phone.Description, ""))
                {
                    room.setPhoneImage(null);
                }
                else
                {
                    room.setPhoneImage(Resources.getphoneInUse());
                } 
                Color outerColor = new Color();
                Color innerColor = new Color();
                Color fontColor = new Color();
                boolean isTriageOperatorOnTheClock = false;
                //get the cubicle color and triage status
                RefSupport<Color> refVar___0 = new RefSupport<Color>();
                RefSupport<Color> refVar___1 = new RefSupport<Color>();
                RefSupport<Color> refVar___2 = new RefSupport<Color>();
                RefSupport<boolean> refVar___3 = new RefSupport<boolean>();
                Phones.getPhoneColor(phone,phoneEmpDefault,true,refVar___0,refVar___1,refVar___2,refVar___3);
                outerColor = refVar___0.getValue();
                innerColor = refVar___1.getValue();
                fontColor = refVar___2.getValue();
                isTriageOperatorOnTheClock = refVar___3.getValue();
                if (!room.getIsFlashing())
                {
                    //if the control is already flashing then don't overwrite the colors. this would cause a "spastic" flash effect.
                    room.setOuterColor(outerColor);
                    room.setInnerColor(innerColor);
                }
                 
                room.ForeColor = fontColor;
                if (isTriageOperatorOnTheClock)
                {
                    triageStaffCount++;
                }
                 
                if (phone.ClockStatus == ClockStatusEnum.NeedsHelp)
                {
                    //turn on flashing
                    room.startFlashing();
                }
                else
                {
                    //turn off flashing
                    room.stopFlashing();
                } 
                room.Invalidate(true);
            }
            this.labelTriageOpsStaff.setText(triageStaffCount.ToString());
        }
        catch (Exception __dummyCatchVar1)
        {
        }
    
    }

    //something failed unexpectedly
    public void setTriageUrgent(int calls, TimeSpan timeBehind) throws Exception {
        this.labelTriageRedCalls.setText(calls.ToString());
        if (timeBehind == TimeSpan.Zero)
        {
            //format the string special for this case
            this.labelTriageRedTimeSpan.setText("00:00");
        }
        else
        {
            this.labelTriageRedTimeSpan.setText(timeBehind.ToStringmmss());
        } 
        if (calls > 1)
        {
            //we are behind
            labelTriageRedCalls.setInnerColor(Color.Red);
            labelTriageRedCalls.ForeColor = Color.White;
        }
        else
        {
            //we are ok
            labelTriageRedCalls.setInnerColor(Color.White);
            labelTriageRedCalls.ForeColor = Color.Black;
        } 
        if (timeBehind > TimeSpan.FromMinutes(1))
        {
            //we are behind
            labelTriageRedTimeSpan.setInnerColor(Color.Red);
            labelTriageRedTimeSpan.ForeColor = Color.White;
        }
        else
        {
            //we are ok
            labelTriageRedTimeSpan.setInnerColor(Color.White);
            labelTriageRedTimeSpan.ForeColor = Color.Black;
        } 
    }

    public void setVoicemailRed(int calls, TimeSpan timeBehind) throws Exception {
        this.labelVoicemailCalls.setText(calls.ToString());
        if (timeBehind == TimeSpan.Zero)
        {
            //format the string special for this case
            this.labelVoicemailTimeSpan.setText("00:00");
        }
        else
        {
            this.labelVoicemailTimeSpan.setText(timeBehind.ToStringmmss());
        } 
        if (calls > 5)
        {
            //we are behind
            labelVoicemailCalls.setInnerColor(Color.Red);
            labelVoicemailCalls.ForeColor = Color.White;
        }
        else
        {
            //we are ok
            labelVoicemailCalls.setInnerColor(Color.White);
            labelVoicemailCalls.ForeColor = Color.Black;
        } 
        if (timeBehind > TimeSpan.FromMinutes(5))
        {
            //we are behind
            labelVoicemailTimeSpan.setInnerColor(Color.Red);
            labelVoicemailTimeSpan.ForeColor = Color.White;
        }
        else
        {
            //we are ok
            labelVoicemailTimeSpan.setInnerColor(Color.White);
            labelVoicemailTimeSpan.ForeColor = Color.Black;
        } 
    }

    public void setTriageNormal(int callsWithNotes, int callsWithNoNotes, TimeSpan timeBehind) throws Exception {
        if (timeBehind == TimeSpan.Zero)
        {
            //format the string special for this case
            this.labelTriageTimeSpan.setText("0");
        }
        else
        {
            this.labelTriageTimeSpan.setText(((int)timeBehind.TotalMinutes).ToString());
        } 
        if (callsWithNoNotes > 0)
        {
            //we have calls which don't have notes so display that number
            this.labelTriageCalls.setText(callsWithNoNotes.ToString());
        }
        else
        {
            //we don't have any calls with no notes so display count of total tasks
            this.labelTriageCalls.setText("(" + callsWithNotes.ToString() + ")");
        } 
        if (callsWithNoNotes > 10)
        {
            //we are behind
            labelTriageCalls.setInnerColor(Color.Red);
            labelTriageCalls.ForeColor = Color.White;
        }
        else
        {
            //we are ok
            labelTriageCalls.setInnerColor(Color.White);
            labelTriageCalls.ForeColor = Color.Black;
        } 
        if (timeBehind > TimeSpan.FromMinutes(19))
        {
            //we are behind
            labelTriageTimeSpan.ForeColor = Color.Red;
            labelTriageTimeSpan.setOuterColor(Color.Red);
        }
        else
        {
            //we are ok
            labelTriageTimeSpan.ForeColor = Color.Black;
            labelTriageTimeSpan.setOuterColor(Color.Black);
            labelTriageTimeSpan.setInnerColor(Color.White);
        } 
    }

    private void fullScreenToolStripMenuItem_Click(Object sender, EventArgs e) throws Exception {
        _isFullScreen = !_isFullScreen;
        if (_isFullScreen)
        {
            //switch to full screen
            this.fullScreenToolStripMenuItem.Text = "Restore";
            this.setupToolStripMenuItem.Visible = false;
            this.WindowState = FormWindowState.Normal;
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Bounds = System.Windows.Forms.Screen.FromControl(this).Bounds;
            this.mapAreaPanelHQ.setPixelsPerFoot(18);
        }
        else
        {
            //set back to defaults
            this.fullScreenToolStripMenuItem.Text = "Full Screen";
            FormMapHQ FormCMS = new FormMapHQ();
            this.FormBorderStyle = FormCMS.FormBorderStyle;
            this.Size = FormCMS.Size;
            this.CenterToScreen();
            this.setupToolStripMenuItem.Visible = true;
            this.mapAreaPanelHQ.setPixelsPerFoot(17);
        } 
    }

    private void setupToolStripMenuItem_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormMapSetup FormMS = new FormMapSetup();
        FormMS.ShowDialog();
        fillMapAreaPanel();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "MapHQ layout changed");
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
        this.label1 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.label11 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.label14 = new System.Windows.Forms.Label();
        this.labelTriageCoordinator = new System.Windows.Forms.Label();
        this.menuStrip = new System.Windows.Forms.MenuStrip();
        this.setupToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        this.fullScreenToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        this.groupPhoneMetrics = new System.Windows.Forms.GroupBox();
        this.labelCurrentTime = new OpenDental.MapAreaRoomControl();
        this.labelTriageOpsStaff = new OpenDental.MapAreaRoomControl();
        this.labelTriageTimeSpan = new OpenDental.MapAreaRoomControl();
        this.labelTriageRedCalls = new OpenDental.MapAreaRoomControl();
        this.labelTriageRedTimeSpan = new OpenDental.MapAreaRoomControl();
        this.labelVoicemailTimeSpan = new OpenDental.MapAreaRoomControl();
        this.labelTriageCalls = new OpenDental.MapAreaRoomControl();
        this.labelVoicemailCalls = new OpenDental.MapAreaRoomControl();
        this.mapAreaPanelHQ = new OpenDental.MapAreaPanel();
        this.menuStrip.SuspendLayout();
        this.groupPhoneMetrics.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.AutoSize = true;
        this.label1.Font = new System.Drawing.Font("Calibri", 28F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label1.Location = new System.Drawing.Point(4, 76);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(182, 46);
        this.label1.TabIndex = 6;
        this.label1.Text = "Triage Red";
        //
        // label4
        //
        this.label4.AutoSize = true;
        this.label4.Font = new System.Drawing.Font("Calibri", 28F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label4.Location = new System.Drawing.Point(4, 175);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(170, 46);
        this.label4.TabIndex = 8;
        this.label4.Text = "Voicemail";
        //
        // label6
        //
        this.label6.AutoSize = true;
        this.label6.Font = new System.Drawing.Font("Calibri", 28F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label6.Location = new System.Drawing.Point(4, 274);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(114, 46);
        this.label6.TabIndex = 10;
        this.label6.Text = "Triage";
        //
        // label10
        //
        this.label10.AutoSize = true;
        this.label10.Font = new System.Drawing.Font("Calibri", 28F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label10.Location = new System.Drawing.Point(172, 16);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(119, 46);
        this.label10.TabIndex = 15;
        this.label10.Text = "# Calls";
        //
        // label11
        //
        this.label11.AutoSize = true;
        this.label11.Font = new System.Drawing.Font("Calibri", 28F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label11.Location = new System.Drawing.Point(343, 15);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(97, 46);
        this.label11.TabIndex = 16;
        this.label11.Text = "Time";
        //
        // label12
        //
        this.label12.AutoSize = true;
        this.label12.Font = new System.Drawing.Font("Calibri", 28F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label12.Location = new System.Drawing.Point(193, 469);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(90, 46);
        this.label12.TabIndex = 19;
        this.label12.Text = "Staff";
        //
        // label14
        //
        this.label14.AutoSize = true;
        this.label14.Font = new System.Drawing.Font("Calibri", 28F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label14.Location = new System.Drawing.Point(-2, 529);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(183, 46);
        this.label14.TabIndex = 17;
        this.label14.Text = "Triage Ops";
        //
        // labelTriageCoordinator
        //
        this.labelTriageCoordinator.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.labelTriageCoordinator.Font = new System.Drawing.Font("Calibri", 36F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTriageCoordinator.Location = new System.Drawing.Point(4, -3);
        this.labelTriageCoordinator.Name = "labelTriageCoordinator";
        this.labelTriageCoordinator.Size = new System.Drawing.Size(1875, 79);
        this.labelTriageCoordinator.TabIndex = 22;
        this.labelTriageCoordinator.Text = "Call Center Status Map - Triage Coordinator - Jim Smith";
        this.labelTriageCoordinator.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // menuStrip
        //
        this.menuStrip.Dock = System.Windows.Forms.DockStyle.None;
        this.menuStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.setupToolStripMenuItem, this.fullScreenToolStripMenuItem });
        this.menuStrip.Location = new System.Drawing.Point(0, 0);
        this.menuStrip.Name = "menuStrip";
        this.menuStrip.Size = new System.Drawing.Size(133, 24);
        this.menuStrip.TabIndex = 24;
        this.menuStrip.Text = "menuStrip1";
        //
        // setupToolStripMenuItem
        //
        this.setupToolStripMenuItem.Name = "setupToolStripMenuItem";
        this.setupToolStripMenuItem.Size = new System.Drawing.Size(49, 20);
        this.setupToolStripMenuItem.Text = "Setup";
        this.setupToolStripMenuItem.Click += new System.EventHandler(this.setupToolStripMenuItem_Click);
        //
        // fullScreenToolStripMenuItem
        //
        this.fullScreenToolStripMenuItem.Name = "fullScreenToolStripMenuItem";
        this.fullScreenToolStripMenuItem.Size = new System.Drawing.Size(76, 20);
        this.fullScreenToolStripMenuItem.Text = "Full Screen";
        this.fullScreenToolStripMenuItem.Click += new System.EventHandler(this.fullScreenToolStripMenuItem_Click);
        //
        // groupPhoneMetrics
        //
        this.groupPhoneMetrics.Controls.Add(this.labelTriageTimeSpan);
        this.groupPhoneMetrics.Controls.Add(this.labelTriageRedCalls);
        this.groupPhoneMetrics.Controls.Add(this.label1);
        this.groupPhoneMetrics.Controls.Add(this.labelTriageRedTimeSpan);
        this.groupPhoneMetrics.Controls.Add(this.label4);
        this.groupPhoneMetrics.Controls.Add(this.labelVoicemailTimeSpan);
        this.groupPhoneMetrics.Controls.Add(this.label11);
        this.groupPhoneMetrics.Controls.Add(this.label6);
        this.groupPhoneMetrics.Controls.Add(this.label10);
        this.groupPhoneMetrics.Controls.Add(this.labelTriageCalls);
        this.groupPhoneMetrics.Controls.Add(this.labelVoicemailCalls);
        this.groupPhoneMetrics.Location = new System.Drawing.Point(4, 62);
        this.groupPhoneMetrics.Name = "groupPhoneMetrics";
        this.groupPhoneMetrics.Size = new System.Drawing.Size(499, 353);
        this.groupPhoneMetrics.TabIndex = 25;
        this.groupPhoneMetrics.TabStop = false;
        //
        // labelCurrentTime
        //
        this.labelCurrentTime.setAllowDragging(false);
        this.labelCurrentTime.setAllowEdit(false);
        this.labelCurrentTime.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.labelCurrentTime.setBorderThickness(2);
        this.labelCurrentTime.setElapsed(null);
        this.labelCurrentTime.setEmployeeName(null);
        this.labelCurrentTime.setEmployeeNum(((long)(0)));
        this.labelCurrentTime.setEmpty(false);
        this.labelCurrentTime.setExtension(null);
        this.labelCurrentTime.Font = new System.Drawing.Font("Agency FB", 39.75F);
        this.labelCurrentTime.setFontHeader(new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.labelCurrentTime.ForeColor = System.Drawing.SystemColors.ControlText;
        this.labelCurrentTime.setInnerColor(System.Drawing.SystemColors.Control);
        this.labelCurrentTime.Location = new System.Drawing.Point(1717, 6);
        this.labelCurrentTime.Name = "labelCurrentTime";
        this.labelCurrentTime.setOuterColor(System.Drawing.SystemColors.Control);
        this.labelCurrentTime.setPhoneImage(null);
        this.labelCurrentTime.Size = new System.Drawing.Size(182, 61);
        this.labelCurrentTime.setStatus(null);
        this.labelCurrentTime.TabIndex = 32;
        this.labelCurrentTime.setText("12:45 PM");
        //
        // labelTriageOpsStaff
        //
        this.labelTriageOpsStaff.setAllowDragging(false);
        this.labelTriageOpsStaff.setAllowEdit(false);
        this.labelTriageOpsStaff.setBorderThickness(1);
        this.labelTriageOpsStaff.setElapsed(null);
        this.labelTriageOpsStaff.setEmployeeName(null);
        this.labelTriageOpsStaff.setEmployeeNum(((long)(0)));
        this.labelTriageOpsStaff.setEmpty(false);
        this.labelTriageOpsStaff.setExtension(null);
        this.labelTriageOpsStaff.Font = new System.Drawing.Font("Calibri", 40F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTriageOpsStaff.setFontHeader(new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.labelTriageOpsStaff.setInnerColor(System.Drawing.Color.LightCyan);
        this.labelTriageOpsStaff.Location = new System.Drawing.Point(182, 517);
        this.labelTriageOpsStaff.Name = "labelTriageOpsStaff";
        this.labelTriageOpsStaff.setOuterColor(System.Drawing.Color.Blue);
        this.labelTriageOpsStaff.setPhoneImage(null);
        this.labelTriageOpsStaff.Size = new System.Drawing.Size(107, 70);
        this.labelTriageOpsStaff.setStatus(null);
        this.labelTriageOpsStaff.TabIndex = 31;
        this.labelTriageOpsStaff.setText("0");
        //
        // labelTriageTimeSpan
        //
        this.labelTriageTimeSpan.setAllowDragging(false);
        this.labelTriageTimeSpan.setAllowEdit(false);
        this.labelTriageTimeSpan.setBorderThickness(1);
        this.labelTriageTimeSpan.setElapsed(null);
        this.labelTriageTimeSpan.setEmployeeName(null);
        this.labelTriageTimeSpan.setEmployeeNum(((long)(0)));
        this.labelTriageTimeSpan.setEmpty(false);
        this.labelTriageTimeSpan.setExtension(null);
        this.labelTriageTimeSpan.Font = new System.Drawing.Font("Calibri", 56F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTriageTimeSpan.setFontHeader(new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.labelTriageTimeSpan.setInnerColor(System.Drawing.Color.White);
        this.labelTriageTimeSpan.Location = new System.Drawing.Point(291, 262);
        this.labelTriageTimeSpan.Name = "labelTriageTimeSpan";
        this.labelTriageTimeSpan.setOuterColor(System.Drawing.Color.Black);
        this.labelTriageTimeSpan.setPhoneImage(null);
        this.labelTriageTimeSpan.Size = new System.Drawing.Size(202, 70);
        this.labelTriageTimeSpan.setStatus(null);
        this.labelTriageTimeSpan.TabIndex = 33;
        this.labelTriageTimeSpan.setText("0000");
        //
        // labelTriageRedCalls
        //
        this.labelTriageRedCalls.setAllowDragging(false);
        this.labelTriageRedCalls.setAllowEdit(false);
        this.labelTriageRedCalls.BackColor = System.Drawing.Color.White;
        this.labelTriageRedCalls.setBorderThickness(1);
        this.labelTriageRedCalls.setElapsed(null);
        this.labelTriageRedCalls.setEmployeeName(null);
        this.labelTriageRedCalls.setEmployeeNum(((long)(0)));
        this.labelTriageRedCalls.setEmpty(false);
        this.labelTriageRedCalls.setExtension(null);
        this.labelTriageRedCalls.Font = new System.Drawing.Font("Calibri", 40F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTriageRedCalls.setFontHeader(new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.labelTriageRedCalls.setInnerColor(System.Drawing.Color.White);
        this.labelTriageRedCalls.Location = new System.Drawing.Point(178, 64);
        this.labelTriageRedCalls.Name = "labelTriageRedCalls";
        this.labelTriageRedCalls.setOuterColor(System.Drawing.Color.Black);
        this.labelTriageRedCalls.setPhoneImage(null);
        this.labelTriageRedCalls.Size = new System.Drawing.Size(107, 70);
        this.labelTriageRedCalls.setStatus(null);
        this.labelTriageRedCalls.TabIndex = 12;
        this.labelTriageRedCalls.setText("0");
        //
        // labelTriageRedTimeSpan
        //
        this.labelTriageRedTimeSpan.setAllowDragging(false);
        this.labelTriageRedTimeSpan.setAllowEdit(false);
        this.labelTriageRedTimeSpan.BackColor = System.Drawing.Color.White;
        this.labelTriageRedTimeSpan.setBorderThickness(1);
        this.labelTriageRedTimeSpan.setElapsed(null);
        this.labelTriageRedTimeSpan.setEmployeeName(null);
        this.labelTriageRedTimeSpan.setEmployeeNum(((long)(0)));
        this.labelTriageRedTimeSpan.setEmpty(false);
        this.labelTriageRedTimeSpan.setExtension(null);
        this.labelTriageRedTimeSpan.Font = new System.Drawing.Font("Calibri", 40F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTriageRedTimeSpan.setFontHeader(new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.labelTriageRedTimeSpan.setInnerColor(System.Drawing.Color.White);
        this.labelTriageRedTimeSpan.Location = new System.Drawing.Point(291, 64);
        this.labelTriageRedTimeSpan.Name = "labelTriageRedTimeSpan";
        this.labelTriageRedTimeSpan.setOuterColor(System.Drawing.Color.Black);
        this.labelTriageRedTimeSpan.setPhoneImage(null);
        this.labelTriageRedTimeSpan.Size = new System.Drawing.Size(203, 70);
        this.labelTriageRedTimeSpan.setStatus(null);
        this.labelTriageRedTimeSpan.TabIndex = 7;
        this.labelTriageRedTimeSpan.setText("00:00");
        //
        // labelVoicemailTimeSpan
        //
        this.labelVoicemailTimeSpan.setAllowDragging(false);
        this.labelVoicemailTimeSpan.setAllowEdit(false);
        this.labelVoicemailTimeSpan.BackColor = System.Drawing.Color.White;
        this.labelVoicemailTimeSpan.setBorderThickness(1);
        this.labelVoicemailTimeSpan.setElapsed(null);
        this.labelVoicemailTimeSpan.setEmployeeName(null);
        this.labelVoicemailTimeSpan.setEmployeeNum(((long)(0)));
        this.labelVoicemailTimeSpan.setEmpty(false);
        this.labelVoicemailTimeSpan.setExtension(null);
        this.labelVoicemailTimeSpan.Font = new System.Drawing.Font("Calibri", 40F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelVoicemailTimeSpan.setFontHeader(new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.labelVoicemailTimeSpan.setInnerColor(System.Drawing.Color.White);
        this.labelVoicemailTimeSpan.Location = new System.Drawing.Point(291, 163);
        this.labelVoicemailTimeSpan.Name = "labelVoicemailTimeSpan";
        this.labelVoicemailTimeSpan.setOuterColor(System.Drawing.Color.Black);
        this.labelVoicemailTimeSpan.setPhoneImage(null);
        this.labelVoicemailTimeSpan.Size = new System.Drawing.Size(202, 70);
        this.labelVoicemailTimeSpan.setStatus(null);
        this.labelVoicemailTimeSpan.TabIndex = 9;
        this.labelVoicemailTimeSpan.setText("00:00");
        //
        // labelTriageCalls
        //
        this.labelTriageCalls.setAllowDragging(false);
        this.labelTriageCalls.setAllowEdit(false);
        this.labelTriageCalls.BackColor = System.Drawing.Color.White;
        this.labelTriageCalls.setBorderThickness(1);
        this.labelTriageCalls.setElapsed(null);
        this.labelTriageCalls.setEmployeeName(null);
        this.labelTriageCalls.setEmployeeNum(((long)(0)));
        this.labelTriageCalls.setEmpty(false);
        this.labelTriageCalls.setExtension(null);
        this.labelTriageCalls.Font = new System.Drawing.Font("Calibri", 40F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTriageCalls.setFontHeader(new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.labelTriageCalls.setInnerColor(System.Drawing.Color.White);
        this.labelTriageCalls.Location = new System.Drawing.Point(178, 262);
        this.labelTriageCalls.Name = "labelTriageCalls";
        this.labelTriageCalls.setOuterColor(System.Drawing.Color.Black);
        this.labelTriageCalls.setPhoneImage(null);
        this.labelTriageCalls.Size = new System.Drawing.Size(107, 70);
        this.labelTriageCalls.setStatus(null);
        this.labelTriageCalls.TabIndex = 14;
        this.labelTriageCalls.setText("0");
        //
        // labelVoicemailCalls
        //
        this.labelVoicemailCalls.setAllowDragging(false);
        this.labelVoicemailCalls.setAllowEdit(false);
        this.labelVoicemailCalls.BackColor = System.Drawing.Color.White;
        this.labelVoicemailCalls.setBorderThickness(1);
        this.labelVoicemailCalls.setElapsed(null);
        this.labelVoicemailCalls.setEmployeeName(null);
        this.labelVoicemailCalls.setEmployeeNum(((long)(0)));
        this.labelVoicemailCalls.setEmpty(false);
        this.labelVoicemailCalls.setExtension(null);
        this.labelVoicemailCalls.Font = new System.Drawing.Font("Calibri", 40F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelVoicemailCalls.setFontHeader(new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.labelVoicemailCalls.setInnerColor(System.Drawing.Color.White);
        this.labelVoicemailCalls.Location = new System.Drawing.Point(178, 163);
        this.labelVoicemailCalls.Name = "labelVoicemailCalls";
        this.labelVoicemailCalls.setOuterColor(System.Drawing.Color.Black);
        this.labelVoicemailCalls.setPhoneImage(null);
        this.labelVoicemailCalls.Size = new System.Drawing.Size(107, 70);
        this.labelVoicemailCalls.setStatus(null);
        this.labelVoicemailCalls.TabIndex = 13;
        this.labelVoicemailCalls.setText("0");
        //
        // mapAreaPanelHQ
        //
        this.mapAreaPanelHQ.setAllowDragging(false);
        this.mapAreaPanelHQ.setAllowEditing(false);
        this.mapAreaPanelHQ.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.mapAreaPanelHQ.AutoScroll = true;
        this.mapAreaPanelHQ.AutoScrollMinSize = new System.Drawing.Size(1326, 935);
        this.mapAreaPanelHQ.setFloorColor(System.Drawing.Color.White);
        this.mapAreaPanelHQ.setFloorHeightFeet(55);
        this.mapAreaPanelHQ.setFloorWidthFeet(78);
        this.mapAreaPanelHQ.Font = new System.Drawing.Font("Calibri", 25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.mapAreaPanelHQ.setFontCubicle(new System.Drawing.Font("Calibri", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.mapAreaPanelHQ.setFontCubicleHeader(new System.Drawing.Font("Calibri", 19F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.mapAreaPanelHQ.setFontLabel(new System.Drawing.Font("Calibri", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.mapAreaPanelHQ.setGridColor(System.Drawing.Color.LightGray);
        this.mapAreaPanelHQ.Location = new System.Drawing.Point(505, 67);
        this.mapAreaPanelHQ.Name = "mapAreaPanelHQ";
        this.mapAreaPanelHQ.setPixelsPerFoot(17);
        this.mapAreaPanelHQ.setShowGrid(false);
        this.mapAreaPanelHQ.setShowOutline(true);
        this.mapAreaPanelHQ.Size = new System.Drawing.Size(1374, 973);
        this.mapAreaPanelHQ.TabIndex = 5;
        //
        // FormMapHQ
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(1884, 1042);
        this.Controls.Add(this.labelCurrentTime);
        this.Controls.Add(this.labelTriageOpsStaff);
        this.Controls.Add(this.groupPhoneMetrics);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.label14);
        this.Controls.Add(this.mapAreaPanelHQ);
        this.Controls.Add(this.menuStrip);
        this.Controls.Add(this.labelTriageCoordinator);
        this.MainMenuStrip = this.menuStrip;
        this.Name = "FormMapHQ";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "HQ";
        this.Load += new System.EventHandler(this.FormMapHQ_Load);
        this.menuStrip.ResumeLayout(false);
        this.menuStrip.PerformLayout();
        this.groupPhoneMetrics.ResumeLayout(false);
        this.groupPhoneMetrics.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.MapAreaPanel mapAreaPanelHQ;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.MapAreaRoomControl labelTriageRedTimeSpan;
    private OpenDental.MapAreaRoomControl labelVoicemailTimeSpan;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.MapAreaRoomControl labelTriageCalls;
    private OpenDental.MapAreaRoomControl labelVoicemailCalls;
    private OpenDental.MapAreaRoomControl labelTriageRedCalls;
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelTriageCoordinator = new System.Windows.Forms.Label();
    private System.Windows.Forms.MenuStrip menuStrip = new System.Windows.Forms.MenuStrip();
    private System.Windows.Forms.ToolStripMenuItem setupToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.GroupBox groupPhoneMetrics = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.ToolStripMenuItem fullScreenToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
    private OpenDental.MapAreaRoomControl labelTriageOpsStaff;
    private OpenDental.MapAreaRoomControl labelCurrentTime;
    private OpenDental.MapAreaRoomControl labelTriageTimeSpan;
}


