//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import OpenDental.PhoneUI;
import OpenDentBusiness.ClockStatusEnum;
import OpenDentBusiness.Phone;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.Phones;

public class UserControlPhoneSmall  extends UserControl 
{

    private List<Phone> phoneList = new List<Phone>();
    private List<PhoneEmpDefault> phoneEmpDefaultList = new List<PhoneEmpDefault>();
    /**
    * When the GoToChanged event fires, this tells us which patnum.
    */
    public long GotoPatNum = new long();
    /**
    * 
    */
    public EventHandler GoToChanged = null;
    public int Extension = new int();
    /**
    * Set list of phones to display. Get/Set accessor won't work here because we require 2 seperate fields in order to update the control properly.
    */
    public void setPhoneList(List<PhoneEmpDefault> peds, List<Phone> phones) throws Exception {
        //create a new list so our sorting doesn't affect this list elsewhere
        phoneList = new List<Phone>(phones);
        phoneList.Sort(new OpenDentBusiness.Phones.PhoneComparer(OpenDentBusiness.Phones.PhoneComparer.SortBy.ext));
        phoneEmpDefaultList = peds;
        Invalidate();
    }

    /**
    * Set the phone which is linked to the extension at this desk. If phone==null then no phone info shown.
    */
    public void setPhone(Phone phone, PhoneEmpDefault phoneEmpDefault, boolean isTriageOperator) throws Exception {
        phoneTile.setPhone(phone,phoneEmpDefault,isTriageOperator);
    }

    public UserControlPhoneSmall() throws Exception {
        initializeComponent();
        phoneTile.GoToChanged += new System.EventHandler(this.phoneTile_GoToChanged);
        phoneTile.MenuNumbers = menuNumbers;
        phoneTile.MenuStatus = menuStatus;
    }

    private void fillTile() throws Exception {
        //Get the new phone list from the database and redraw control.
        setPhoneList(PhoneEmpDefaults.refresh(),Phones.getPhoneList());
        //Set the currently selected phone accordingly.
        if (phoneList == null)
        {
            //No phone list. Shouldn't get here.
            phoneTile.setPhone(null,null,false);
            return ;
        }
         
        Phone phone = Phones.getPhoneForExtension(phoneList,Extension);
        PhoneEmpDefault phoneEmpDefault = null;
        if (phone != null)
        {
            phoneEmpDefault = PhoneEmpDefaults.getEmpDefaultFromList(phone.EmployeeNum,phoneEmpDefaultList);
        }
         
        phoneTile.setPhone(phone,phoneEmpDefault,PhoneEmpDefaults.isTriageOperatorForExtension(Extension,phoneEmpDefaultList));
    }

    private void userControlPhoneSmall_Paint(Object sender, PaintEventArgs e) throws Exception {
        Graphics g = e.Graphics;
        g.FillRectangle(SystemBrushes.Control, this.Bounds);
        if (phoneList == null)
        {
            return ;
        }
         
        int rows = 7;
        int columns = 7;
        float boxWidth = 21.4f;
        float boxHeight = 17f;
        float hTot = boxHeight * rows;
        float x = 0f;
        float y = 0f;
        //Create a white "background" rectangle so that any empty squares (no employees) will show as white boxes instead of no color.
        g.FillRectangle(new SolidBrush(Color.White), x, y, boxWidth * columns, boxHeight * rows);
        for (int i = 0;i < phoneList.Count;i++)
        {
            //Draw the extension number if a person is available at that extension.
            if (phoneList[i].ClockStatus != ClockStatusEnum.Home && phoneList[i].ClockStatus != ClockStatusEnum.None)
            {
                //Colors the box a color based on the corresponding phone's status.
                Color outerColor = new Color();
                Color innerColor = new Color();
                Color fontColor = new Color();
                boolean isTriageOperatorOnTheClock = false;
                //get the cubicle color and triage status
                PhoneEmpDefault ped = PhoneEmpDefaults.GetEmpDefaultFromList(phoneList[i].EmployeeNum, phoneEmpDefaultList);
                RefSupport<Color> refVar___0 = new RefSupport<Color>();
                RefSupport<Color> refVar___1 = new RefSupport<Color>();
                RefSupport<Color> refVar___2 = new RefSupport<Color>();
                RefSupport<boolean> refVar___3 = new RefSupport<boolean>();
                Phones.GetPhoneColor(phoneList[i], ped, false, refVar___0, refVar___1, refVar___2, refVar___3);
                outerColor = refVar___0.getValue();
                innerColor = refVar___1.getValue();
                fontColor = refVar___2.getValue();
                isTriageOperatorOnTheClock = refVar___3.getValue();
                Brush brush = new SolidBrush(outerColor);
                try
                {
                    {
                        g.FillRectangle(brush, x * boxWidth, y * boxHeight, boxWidth, boxHeight);
                    }
                }
                finally
                {
                    if (brush != null)
                        Disposable.mkDisposable(brush).dispose();
                     
                }
                Font baseFont = new Font("Arial", 7);
                SizeF extSize = g.MeasureString(phoneList[i].Extension.ToString(), baseFont);
                float padX = (boxWidth - extSize.Width) / 2;
                float padY = (boxHeight - extSize.Height) / 2;
                Brush brush = new SolidBrush(Color.Black);
                try
                {
                    {
                        g.DrawString(phoneList[i].Extension.ToString(), baseFont, brush, (x * boxWidth) + (padX), (y * boxHeight) + (padY));
                    }
                }
                finally
                {
                    if (brush != null)
                        Disposable.mkDisposable(brush).dispose();
                     
                }
            }
             
            x++;
            if (x >= columns)
            {
                x = 0f;
                y++;
            }
             
        }
        for (int i = 0;i < rows + 1;i++)
        {
            //horiz lines
            g.DrawLine(Pens.Black, 0, i * boxHeight, Width, i * boxHeight);
        }
        //Very bottom
        g.DrawLine(Pens.Black, 0, Height - 1, Width, Height - 1);
        for (int i = 0;i < columns;i++)
        {
            //vert
            g.DrawLine(Pens.Black, i * boxWidth, 0, i * boxWidth, hTot);
        }
        g.DrawLine(Pens.Black, Width - 1, 0, Width - 1, hTot);
    }

    private void phoneTile_GoToChanged(Object sender, EventArgs e) throws Exception {
        if (phoneTile.getPhoneCur() == null)
        {
            return ;
        }
         
        if (phoneTile.getPhoneCur().PatNum == 0)
        {
            return ;
        }
         
        GotoPatNum = phoneTile.getPhoneCur().PatNum;
        onGoToChanged();
    }

    protected void onGoToChanged() throws Exception {
        if (GoToChanged != null)
        {
            GoToChanged(this, new EventArgs());
        }
         
    }

    private void menuItemManage_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.manage(phoneTile);
    }

    private void menuItemAdd_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.add(phoneTile);
    }

    //Timecards-------------------------------------------------------------------------------------
    private void menuItemAvailable_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.available(phoneTile);
        fillTile();
    }

    private void menuItemTraining_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.training(phoneTile);
        fillTile();
    }

    private void menuItemTeamAssist_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.teamAssist(phoneTile);
        fillTile();
    }

    private void menuItemNeedsHelp_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.needsHelp(phoneTile);
        fillTile();
    }

    private void menuItemWrapUp_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.wrapUp(phoneTile);
        fillTile();
    }

    private void menuItemOfflineAssist_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.offlineAssist(phoneTile);
        fillTile();
    }

    private void menuItemUnavailable_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.unavailable(phoneTile);
        fillTile();
    }

    private void menuItemBackup_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.backup(phoneTile);
        fillTile();
    }

    //RingGroups---------------------------------------------------
    private void menuItemRinggroupAll_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.ringgroupAll(phoneTile);
    }

    private void menuItemRinggroupNone_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.ringgroupNone(phoneTile);
    }

    private void menuItemRinggroupsDefault_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.ringgroupsDefault(phoneTile);
    }

    private void menuItemRinggroupsBackup_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.ringgroupsBackup(phoneTile);
    }

    //Timecard---------------------------------------------------
    private void menuItemLunch_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.lunch(phoneTile);
        fillTile();
    }

    private void menuItemHome_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.home(phoneTile);
        fillTile();
    }

    private void menuItemBreak_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.break(phoneTile);
        fillTile();
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
        this.menuStatus = new System.Windows.Forms.ContextMenuStrip(this.components);
        this.menuItemStatusOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemAvailable = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemTraining = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemTeamAssist = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemNeedsHelp = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemWrapUp = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemOfflineAssist = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemUnavailable = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemBackup = new System.Windows.Forms.ToolStripMenuItem();
        this.toolStripSeparatorRingGroups = new System.Windows.Forms.ToolStripSeparator();
        this.menuItemRingGroupOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemRinggroupAll = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemRinggroupNone = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemRinggroupsDefault = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemRinggroupsBackup = new System.Windows.Forms.ToolStripMenuItem();
        this.toolStripSeparatorClockEvents = new System.Windows.Forms.ToolStripSeparator();
        this.menuItemClockOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemLunch = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemHome = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemBreak = new System.Windows.Forms.ToolStripMenuItem();
        this.menuNumbers = new System.Windows.Forms.ContextMenuStrip(this.components);
        this.menuItemManage = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemAdd = new System.Windows.Forms.ToolStripMenuItem();
        this.phoneTile = new OpenDental.PhoneTile();
        this.menuStatus.SuspendLayout();
        this.menuNumbers.SuspendLayout();
        this.SuspendLayout();
        //
        // menuStatus
        //
        this.menuStatus.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.menuItemStatusOnBehalf, this.menuItemAvailable, this.menuItemTraining, this.menuItemTeamAssist, this.menuItemNeedsHelp, this.menuItemWrapUp, this.menuItemOfflineAssist, this.menuItemUnavailable, this.menuItemBackup, this.toolStripSeparatorRingGroups, this.menuItemRingGroupOnBehalf, this.menuItemRinggroupAll, this.menuItemRinggroupNone, this.menuItemRinggroupsDefault, this.menuItemRinggroupsBackup, this.toolStripSeparatorClockEvents, this.menuItemClockOnBehalf, this.menuItemLunch, this.menuItemHome, this.menuItemBreak });
        this.menuStatus.Name = "menuStatus";
        this.menuStatus.Size = new System.Drawing.Size(215, 434);
        //
        // menuItemStatusOnBehalf
        //
        this.menuItemStatusOnBehalf.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Bold);
        this.menuItemStatusOnBehalf.Name = "menuItemStatusOnBehalf";
        this.menuItemStatusOnBehalf.Size = new System.Drawing.Size(214, 22);
        this.menuItemStatusOnBehalf.Text = "Status On Behalf Of";
        //
        // menuItemAvailable
        //
        this.menuItemAvailable.Name = "menuItemAvailable";
        this.menuItemAvailable.Size = new System.Drawing.Size(214, 22);
        this.menuItemAvailable.Text = "Available";
        this.menuItemAvailable.Click += new System.EventHandler(this.menuItemAvailable_Click);
        //
        // menuItemTraining
        //
        this.menuItemTraining.Name = "menuItemTraining";
        this.menuItemTraining.Size = new System.Drawing.Size(214, 22);
        this.menuItemTraining.Text = "Training";
        this.menuItemTraining.Click += new System.EventHandler(this.menuItemTraining_Click);
        //
        // menuItemTeamAssist
        //
        this.menuItemTeamAssist.Name = "menuItemTeamAssist";
        this.menuItemTeamAssist.Size = new System.Drawing.Size(214, 22);
        this.menuItemTeamAssist.Text = "TeamAssist";
        this.menuItemTeamAssist.Click += new System.EventHandler(this.menuItemTeamAssist_Click);
        //
        // menuItemNeedsHelp
        //
        this.menuItemNeedsHelp.Name = "menuItemNeedsHelp";
        this.menuItemNeedsHelp.Size = new System.Drawing.Size(214, 22);
        this.menuItemNeedsHelp.Text = "NeedsHelp";
        this.menuItemNeedsHelp.Click += new System.EventHandler(this.menuItemNeedsHelp_Click);
        //
        // menuItemWrapUp
        //
        this.menuItemWrapUp.Name = "menuItemWrapUp";
        this.menuItemWrapUp.Size = new System.Drawing.Size(214, 22);
        this.menuItemWrapUp.Text = "WrapUp";
        this.menuItemWrapUp.Click += new System.EventHandler(this.menuItemWrapUp_Click);
        //
        // menuItemOfflineAssist
        //
        this.menuItemOfflineAssist.Name = "menuItemOfflineAssist";
        this.menuItemOfflineAssist.Size = new System.Drawing.Size(214, 22);
        this.menuItemOfflineAssist.Text = "OfflineAssist";
        this.menuItemOfflineAssist.Click += new System.EventHandler(this.menuItemOfflineAssist_Click);
        //
        // menuItemUnavailable
        //
        this.menuItemUnavailable.Name = "menuItemUnavailable";
        this.menuItemUnavailable.Size = new System.Drawing.Size(214, 22);
        this.menuItemUnavailable.Text = "Unavailable";
        this.menuItemUnavailable.Click += new System.EventHandler(this.menuItemUnavailable_Click);
        //
        // menuItemBackup
        //
        this.menuItemBackup.Name = "menuItemBackup";
        this.menuItemBackup.Size = new System.Drawing.Size(214, 22);
        this.menuItemBackup.Text = "Backup";
        this.menuItemBackup.Click += new System.EventHandler(this.menuItemBackup_Click);
        //
        // toolStripSeparatorRingGroups
        //
        this.toolStripSeparatorRingGroups.Name = "toolStripSeparatorRingGroups";
        this.toolStripSeparatorRingGroups.Size = new System.Drawing.Size(211, 6);
        //
        // menuItemRingGroupOnBehalf
        //
        this.menuItemRingGroupOnBehalf.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Bold);
        this.menuItemRingGroupOnBehalf.Name = "menuItemRingGroupOnBehalf";
        this.menuItemRingGroupOnBehalf.Size = new System.Drawing.Size(214, 22);
        this.menuItemRingGroupOnBehalf.Text = "Ring Group On Behalf Of";
        //
        // menuItemRinggroupAll
        //
        this.menuItemRinggroupAll.Name = "menuItemRinggroupAll";
        this.menuItemRinggroupAll.Size = new System.Drawing.Size(214, 22);
        this.menuItemRinggroupAll.Text = "Ringgroups All";
        this.menuItemRinggroupAll.Click += new System.EventHandler(this.menuItemRinggroupAll_Click);
        //
        // menuItemRinggroupNone
        //
        this.menuItemRinggroupNone.Name = "menuItemRinggroupNone";
        this.menuItemRinggroupNone.Size = new System.Drawing.Size(214, 22);
        this.menuItemRinggroupNone.Text = "Ringgroups None";
        this.menuItemRinggroupNone.Click += new System.EventHandler(this.menuItemRinggroupNone_Click);
        //
        // menuItemRinggroupsDefault
        //
        this.menuItemRinggroupsDefault.Name = "menuItemRinggroupsDefault";
        this.menuItemRinggroupsDefault.Size = new System.Drawing.Size(214, 22);
        this.menuItemRinggroupsDefault.Text = "Ringgroups Default";
        this.menuItemRinggroupsDefault.Click += new System.EventHandler(this.menuItemRinggroupsDefault_Click);
        //
        // menuItemRinggroupsBackup
        //
        this.menuItemRinggroupsBackup.Name = "menuItemRinggroupsBackup";
        this.menuItemRinggroupsBackup.Size = new System.Drawing.Size(214, 22);
        this.menuItemRinggroupsBackup.Text = "Ringgroups Backup";
        this.menuItemRinggroupsBackup.Click += new System.EventHandler(this.menuItemRinggroupsBackup_Click);
        //
        // toolStripSeparatorClockEvents
        //
        this.toolStripSeparatorClockEvents.Name = "toolStripSeparatorClockEvents";
        this.toolStripSeparatorClockEvents.Size = new System.Drawing.Size(211, 6);
        //
        // menuItemClockOnBehalf
        //
        this.menuItemClockOnBehalf.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Bold);
        this.menuItemClockOnBehalf.Name = "menuItemClockOnBehalf";
        this.menuItemClockOnBehalf.Size = new System.Drawing.Size(214, 22);
        this.menuItemClockOnBehalf.Text = "Clock Event On Behalf Of";
        //
        // menuItemLunch
        //
        this.menuItemLunch.Name = "menuItemLunch";
        this.menuItemLunch.Size = new System.Drawing.Size(214, 22);
        this.menuItemLunch.Text = "Lunch";
        this.menuItemLunch.Click += new System.EventHandler(this.menuItemLunch_Click);
        //
        // menuItemHome
        //
        this.menuItemHome.Name = "menuItemHome";
        this.menuItemHome.Size = new System.Drawing.Size(214, 22);
        this.menuItemHome.Text = "Home";
        this.menuItemHome.Click += new System.EventHandler(this.menuItemHome_Click);
        //
        // menuItemBreak
        //
        this.menuItemBreak.Name = "menuItemBreak";
        this.menuItemBreak.Size = new System.Drawing.Size(214, 22);
        this.menuItemBreak.Text = "Break";
        this.menuItemBreak.Click += new System.EventHandler(this.menuItemBreak_Click);
        //
        // menuNumbers
        //
        this.menuNumbers.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.menuItemManage, this.menuItemAdd });
        this.menuNumbers.Name = "contextMenuStrip1";
        this.menuNumbers.Size = new System.Drawing.Size(291, 48);
        //
        // menuItemManage
        //
        this.menuItemManage.Name = "menuItemManage";
        this.menuItemManage.Size = new System.Drawing.Size(290, 22);
        this.menuItemManage.Text = "Manage Phone Numbers";
        this.menuItemManage.Click += new System.EventHandler(this.menuItemManage_Click);
        //
        // menuItemAdd
        //
        this.menuItemAdd.Name = "menuItemAdd";
        this.menuItemAdd.Size = new System.Drawing.Size(290, 22);
        this.menuItemAdd.Text = "Attach Phone Number to Current Patient";
        this.menuItemAdd.Click += new System.EventHandler(this.menuItemAdd_Click);
        //
        // phoneTile
        //
        this.phoneTile.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.phoneTile.setLayoutHorizontal(false);
        this.phoneTile.Location = new System.Drawing.Point(0, 124);
        this.phoneTile.Name = "phoneTile";
        this.phoneTile.Size = new System.Drawing.Size(150, 122);
        this.phoneTile.TabIndex = 0;
        //
        // UserControlPhoneSmall
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.Controls.Add(this.phoneTile);
        this.DoubleBuffered = true;
        this.Name = "UserControlPhoneSmall";
        this.Size = new System.Drawing.Size(150, 250);
        this.Paint += new System.Windows.Forms.PaintEventHandler(this.UserControlPhoneSmall_Paint);
        this.menuStatus.ResumeLayout(false);
        this.menuNumbers.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.PhoneTile phoneTile;
    private System.Windows.Forms.ContextMenuStrip menuStatus = new System.Windows.Forms.ContextMenuStrip();
    private System.Windows.Forms.ToolStripMenuItem menuItemAvailable = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemTraining = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemTeamAssist = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemWrapUp = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemOfflineAssist = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemUnavailable = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripSeparator toolStripSeparatorRingGroups = new System.Windows.Forms.ToolStripSeparator();
    private System.Windows.Forms.ToolStripMenuItem menuItemRinggroupAll = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemRinggroupNone = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemRinggroupsDefault = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemRinggroupsBackup = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripSeparator toolStripSeparatorClockEvents = new System.Windows.Forms.ToolStripSeparator();
    private System.Windows.Forms.ToolStripMenuItem menuItemLunch = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemHome = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemBreak = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ContextMenuStrip menuNumbers = new System.Windows.Forms.ContextMenuStrip();
    private System.Windows.Forms.ToolStripMenuItem menuItemManage = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemAdd = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemNeedsHelp = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemStatusOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemRingGroupOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemClockOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemBackup = new System.Windows.Forms.ToolStripMenuItem();
}


