//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormOpenDental;
import OpenDental.FormPhoneNumbersManage;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PhoneUI;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AsteriskRingGroups;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.ClockStatusEnum;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Phone;
import OpenDentBusiness.PhoneAsterisks;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.PhoneNumber;
import OpenDentBusiness.PhoneNumbers;
import OpenDentBusiness.Phones;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;
import OpenDentBusiness.TimeClockStatus;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class UserControlPhonePanel  extends UserControl 
{

    private List<Phone> PhoneList = new List<Phone>();
    /**
    * When the GoToChanged event fires, this tells us which patnum.
    */
    public long GotoPatNum = new long();
    /**
    * 
    */
    public EventHandler GoToChanged = null;
    private int rowI = new int();
    private int colI = new int();
    /**
    * This is the difference between server time and local computer time.  Used to ensure that times displayed are accurate to the second.  This value is usally just a few seconds, but possibly a few minutes.
    */
    private TimeSpan timeDelta = new TimeSpan();
    private int msgCount = new int();
    public UserControlPhonePanel() throws Exception {
        initializeComponent();
    }

    private void userControlPhonePanel_Load(Object sender, EventArgs e) throws Exception {
        timer1.Enabled = true;
        timerMsgs.Enabled = true;
        setLabelMsg();
        timeDelta = MiscData.getNowDateTime() - DateTime.Now;
        fillEmps();
    }

    private void setLabelMsg() throws Exception {
        if (!Directory.Exists(PhoneUI.PathPhoneMsg))
        {
            labelMsg.Text = "msg path not found";
            labelMsg.Font = new Font(FontFamily.GenericSansSerif, 8.5f, FontStyle.Regular);
            labelMsg.ForeColor = Color.Black;
            return ;
        }
         
        msgCount = Directory.GetFiles(PhoneUI.PathPhoneMsg, "*.txt").Length;
        if (msgCount == 0)
        {
            labelMsg.Text = "Phone Messages: 0";
            labelMsg.Font = new Font(FontFamily.GenericSansSerif, 8.5f, FontStyle.Regular);
            labelMsg.ForeColor = Color.Black;
        }
        else
        {
            labelMsg.Text = "Phone Messages: " + msgCount.ToString();
            labelMsg.Font = new Font(FontFamily.GenericSansSerif, 10f, FontStyle.Bold);
            labelMsg.ForeColor = Color.Firebrick;
        } 
    }

    protected void onGoToChanged() throws Exception {
        if (GoToChanged != null)
        {
            GoToChanged(this, new EventArgs());
        }
         
    }

    private void fillEmps() throws Exception {
        gridEmp.beginUpdate();
        gridEmp.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g("TableEmpClock","Ext"),25);
        gridEmp.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableEmpClock","Employee"),60);
        gridEmp.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableEmpClock","Status"),80);
        gridEmp.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableEmpClock","Phone"),50);
        gridEmp.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableEmpClock","InOut"),35);
        gridEmp.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableEmpClock","Customer"),90);
        gridEmp.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableEmpClock","Time"),70);
        gridEmp.getColumns().add(col);
        gridEmp.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        PhoneList = Phones.getPhoneList();
        DateTime dateTimeStart = new DateTime();
        TimeSpan span = new TimeSpan();
        DateTime timeOfDay = new DateTime();
        for (int i = 0;i < PhoneList.Count;i++)
        {
            //because TimeSpan does not have good formatting.
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(PhoneList[i].Extension.ToString());
            row.getCells().Add(PhoneList[i].EmployeeName);
            if (PhoneList[i].ClockStatus == ClockStatusEnum.None)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(PhoneList[i].ClockStatus.ToString());
            } 
            row.getCells().Add(PhoneList[i].Description);
            row.getCells().Add(PhoneList[i].InOrOut);
            row.getCells().Add(PhoneList[i].CustomerNumber);
            dateTimeStart = PhoneList[i].DateTimeStart;
            if (dateTimeStart.Date == DateTime.Today)
            {
                span = DateTime.Now - dateTimeStart + timeDelta;
                timeOfDay = DateTime.Today + span;
                row.getCells().Add(timeOfDay.ToString("H:mm:ss"));
            }
            else
            {
                row.getCells().add("");
            } 
            row.setColorBackG(PhoneList[i].ColorBar);
            row.setColorText(PhoneList[i].ColorText);
            gridEmp.getRows().add(row);
        }
        gridEmp.endUpdate();
        gridEmp.setSelected(false);
    }

    private void timer1_Tick(Object sender, EventArgs e) throws Exception {
        //For now, happens once per 1.6 seconds regardless of phone activity.
        //This might need improvement.
        fillEmps();
    }

    private void timerMsgs_Tick(Object sender, EventArgs e) throws Exception {
        //every 3 sec.
        setLabelMsg();
    }

    private void butOverride_Click(Object sender, EventArgs e) throws Exception {
        //FormPhoneOverrides FormO=new FormPhoneOverrides();
        //FormO.ShowDialog();
        MessageBox.Show("Not working right now.");
    }

    private void gridEmp_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if ((e.getButton() & MouseButtons.Right) == MouseButtons.Right)
        {
            return ;
        }
         
        long patNum = PhoneList[e.getRow()].PatNum;
        GotoPatNum = patNum;
        onGoToChanged();
    }

    private void menuItemManage_Click(Object sender, EventArgs e) throws Exception {
        long patNum = PhoneList[rowI].PatNum;
        if (patNum == 0)
        {
            MsgBox.show(this,"Please attach this number to a patient first.");
            return ;
        }
         
        FormPhoneNumbersManage FormM = new FormPhoneNumbersManage();
        FormM.PatNum = patNum;
        FormM.ShowDialog();
    }

    private void menuItemAdd_Click(Object sender, EventArgs e) throws Exception {
        if (FormOpenDental.CurPatNum == 0)
        {
            MsgBox.show(this,"Please select a patient in the main window first.");
            return ;
        }
         
        if (PhoneList[rowI].PatNum != 0)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"The current number is already attached to a patient. Attach it to this patient instead?"))
            {
                return ;
            }
             
            PhoneNumber ph = PhoneNumbers.GetByVal(PhoneList[rowI].CustomerNumber);
            ph.PatNum = FormOpenDental.CurPatNum;
            PhoneNumbers.update(ph);
        }
        else
        {
            String patName = Patients.getLim(FormOpenDental.CurPatNum).getNameLF();
            if (MessageBox.Show("Attach this phone number to " + patName + "?", "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
            PhoneNumber ph = new PhoneNumber();
            ph.PatNum = FormOpenDental.CurPatNum;
            ph.PhoneNumberVal = PhoneList[rowI].CustomerNumber;
            PhoneNumbers.insert(ph);
        } 
        //tell the phone server to refresh this row with the patient name and patnum
        DataValid.setInvalid(InvalidType.PhoneNumbers);
    }

    private void gridEmp_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button != MouseButtons.Right)
        {
            return ;
        }
         
        rowI = gridEmp.PointToRow(e.Y);
        colI = gridEmp.PointToCol(e.X);
        if (rowI == -1)
        {
            return ;
        }
         
        if (colI == 5)
        {
            menuNumbers.Show(gridEmp, e.Location);
        }
         
        if (colI == 2)
        {
            menuStatus.Show(gridEmp, e.Location);
        }
         
    }

    private void menuItemAvailable_Click(Object sender, EventArgs e) throws Exception {
        if (!clockIn())
        {
            return ;
        }
         
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        PhoneEmpDefaults.setAvailable(extension,employeeNum);
        Phones.setPhoneStatus(ClockStatusEnum.Available,extension);
        //green
        fillEmps();
    }

    private void menuItemTraining_Click(Object sender, EventArgs e) throws Exception {
        if (!clockIn())
        {
            return ;
        }
         
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        PhoneEmpDefaults.setAvailable(extension,employeeNum);
        Phones.setPhoneStatus(ClockStatusEnum.Training,extension);
        fillEmps();
    }

    private void menuItemTeamAssist_Click(Object sender, EventArgs e) throws Exception {
        if (!clockIn())
        {
            return ;
        }
         
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        PhoneEmpDefaults.setAvailable(extension,employeeNum);
        Phones.setPhoneStatus(ClockStatusEnum.TeamAssist,extension);
        fillEmps();
    }

    private void menuItemWrapUp_Click(Object sender, EventArgs e) throws Exception {
        if (!clockIn())
        {
            return ;
        }
         
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        PhoneEmpDefaults.setAvailable(extension,employeeNum);
        Phones.setPhoneStatus(ClockStatusEnum.WrapUp,extension);
        //this is usually an automatic status
        fillEmps();
    }

    private void menuItemOfflineAssist_Click(Object sender, EventArgs e) throws Exception {
        if (!clockIn())
        {
            return ;
        }
         
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        PhoneEmpDefaults.setAvailable(extension,employeeNum);
        Phones.setPhoneStatus(ClockStatusEnum.OfflineAssist,extension);
        fillEmps();
    }

    private void menuItemUnavailable_Click(Object sender, EventArgs e) throws Exception {
        MessageBox.Show("Not working right now.");
    }

    /*
    			if(!ClockIn()) {
    				return;
    			}
    			int extension=PhoneList[rowI].Extension;
    			long employeeNum=PhoneList[rowI].EmployeeNum;
    			//Employees.SetUnavailable(extension,employeeNum);
    			//Get an override if it exists
    			PhoneOverride phoneOR=PhoneOverrides.GetByExtAndEmp(extension,employeeNum);
    			if(phoneOR==null) {//there is no override for that extension/emp combo.
    				phoneOR=new PhoneOverride();
    				phoneOR.EmpCurrent=employeeNum;
    				phoneOR.Extension=extension;
    				phoneOR.IsAvailable=false;
    				FormPhoneOverrideEdit FormO=new FormPhoneOverrideEdit();
    				FormO.phoneCur=phoneOR;
    				FormO.IsNew=true;
    				FormO.ForceUnAndExplanation=true;
    				FormO.ShowDialog();
    				if(FormO.DialogResult!=DialogResult.OK) {
    					return;
    				}
    			}
    			else {
    				phoneOR.IsAvailable=false;
    				FormPhoneOverrideEdit FormO=new FormPhoneOverrideEdit();
    				FormO.phoneCur=phoneOR;
    				FormO.ForceUnAndExplanation=true;
    				FormO.ShowDialog();
    				if(FormO.DialogResult!=DialogResult.OK) {
    					return;
    				}
    			}
    			FillEmps();*/
    //RingGroups---------------------------------------------------
    private void menuItemRinggroupAll_Click(Object sender, EventArgs e) throws Exception {
        //This even works if the person is still clocked out.
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        PhoneAsterisks.setRingGroups(extension,AsteriskRingGroups.All);
    }

    private void menuItemRinggroupNone_Click(Object sender, EventArgs e) throws Exception {
        //This even works if the person is still clocked in.
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        PhoneAsterisks.setRingGroups(extension,AsteriskRingGroups.None);
    }

    private void menuItemRinggroupsDefault_Click(Object sender, EventArgs e) throws Exception {
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        PhoneAsterisks.setToDefaultRingGroups(extension,employeeNum);
    }

    private void menuItemBackup_Click(Object sender, EventArgs e) throws Exception {
        if (!clockIn())
        {
            return ;
        }
         
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        PhoneEmpDefaults.setAvailable(extension,employeeNum);
        PhoneAsterisks.setRingGroups(extension,AsteriskRingGroups.Backup);
        Phones.setPhoneStatus(ClockStatusEnum.Backup,extension);
        fillEmps();
    }

    //Timecard---------------------------------------------------
    private void menuItemLunch_Click(Object sender, EventArgs e) throws Exception {
        //verify that employee is logged in as user
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        if (PrefC.getBool(PrefName.TimecardSecurityEnabled))
        {
            if (Security.getCurUser().EmployeeNum != employeeNum)
            {
                if (!Security.isAuthorized(Permissions.TimecardsEditAll))
                {
                    MsgBox.show(this,"Not authorized.");
                    return ;
                }
                 
            }
             
        }
         
        try
        {
            ClockEvents.clockOut(employeeNum,TimeClockStatus.Lunch);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        //This message will tell user that they are already clocked out.
        PhoneEmpDefaults.setAvailable(extension,employeeNum);
        Employee EmpCur = Employees.getEmp(employeeNum);
        EmpCur.ClockStatus = Lan.g("enumTimeClockStatus", TimeClockStatus.Lunch.ToString());
        Employees.update(EmpCur);
        Phones.setPhoneStatus(ClockStatusEnum.Lunch,extension);
        fillEmps();
    }

    private void menuItemHome_Click(Object sender, EventArgs e) throws Exception {
        //verify that employee is logged in as user
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        if (PrefC.getBool(PrefName.TimecardSecurityEnabled))
        {
            if (Security.getCurUser().EmployeeNum != employeeNum)
            {
                if (!Security.isAuthorized(Permissions.TimecardsEditAll))
                {
                    MsgBox.show(this,"Not authorized.");
                    return ;
                }
                 
            }
             
        }
         
        try
        {
            ClockEvents.clockOut(employeeNum,TimeClockStatus.Home);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        //This message will tell user that they are already clocked out.
        PhoneEmpDefaults.setAvailable(extension,employeeNum);
        Employee EmpCur = Employees.getEmp(employeeNum);
        EmpCur.ClockStatus = Lan.g("enumTimeClockStatus", TimeClockStatus.Home.ToString());
        Employees.update(EmpCur);
        Phones.setPhoneStatus(ClockStatusEnum.Home,extension);
        fillEmps();
    }

    private void menuItemBreak_Click(Object sender, EventArgs e) throws Exception {
        //verify that employee is logged in as user
        int extension = PhoneList[rowI].Extension;
        long employeeNum = PhoneList[rowI].EmployeeNum;
        if (PrefC.getBool(PrefName.TimecardSecurityEnabled))
        {
            if (Security.getCurUser().EmployeeNum != employeeNum)
            {
                if (!Security.isAuthorized(Permissions.TimecardsEditAll))
                {
                    MsgBox.show(this,"Not authorized.");
                    return ;
                }
                 
            }
             
        }
         
        try
        {
            ClockEvents.clockOut(employeeNum,TimeClockStatus.Break);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        //This message will tell user that they are already clocked out.
        PhoneEmpDefaults.setAvailable(extension,employeeNum);
        Employee EmpCur = Employees.getEmp(employeeNum);
        EmpCur.ClockStatus = Lan.g("enumTimeClockStatus", TimeClockStatus.Break.ToString());
        Employees.update(EmpCur);
        Phones.setPhoneStatus(ClockStatusEnum.Break,extension);
        fillEmps();
    }

    /**
    * If already clocked in, this does nothing.  Returns false if not able to clock in due to security, or true if successful.
    */
    private boolean clockIn() throws Exception {
        long employeeNum = PhoneList[rowI].EmployeeNum;
        if (employeeNum == 0)
        {
            MsgBox.show(this,"No employee at that extension.");
            return false;
        }
         
        if (ClockEvents.isClockedIn(employeeNum))
        {
            return true;
        }
         
        if (PrefC.getBool(PrefName.TimecardSecurityEnabled))
        {
            if (Security.getCurUser().EmployeeNum != employeeNum)
            {
                if (!Security.isAuthorized(Permissions.TimecardsEditAll))
                {
                    MsgBox.show(this,"Not authorized.");
                    return false;
                }
                 
            }
             
        }
         
        try
        {
            ClockEvents.clockIn(employeeNum);
        }
        catch (Exception __dummyCatchVar0)
        {
            return true;
        }

        //This should never happen.  Fail silently.
        Employee EmpCur = Employees.getEmp(employeeNum);
        EmpCur.ClockStatus = Lan.g(this,"Working");
            ;
        Employees.update(EmpCur);
        return true;
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
        this.timer1 = new System.Windows.Forms.Timer(this.components);
        this.menuNumbers = new System.Windows.Forms.ContextMenuStrip(this.components);
        this.menuItemManage = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemAdd = new System.Windows.Forms.ToolStripMenuItem();
        this.menuStatus = new System.Windows.Forms.ContextMenuStrip(this.components);
        this.menuItemAvailable = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemTraining = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemTeamAssist = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemWrapUp = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemOfflineAssist = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemUnavailable = new System.Windows.Forms.ToolStripMenuItem();
        this.toolStripMenuItem2 = new System.Windows.Forms.ToolStripSeparator();
        this.menuItemRinggroupAll = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemRinggroupNone = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemRinggroupsDefault = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemBackup = new System.Windows.Forms.ToolStripMenuItem();
        this.toolStripMenuItem1 = new System.Windows.Forms.ToolStripSeparator();
        this.menuItemLunch = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemHome = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemBreak = new System.Windows.Forms.ToolStripMenuItem();
        this.labelMsg = new System.Windows.Forms.Label();
        this.timerMsgs = new System.Windows.Forms.Timer(this.components);
        this.butOverride = new OpenDental.UI.Button();
        this.gridEmp = new OpenDental.UI.ODGrid();
        this.menuNumbers.SuspendLayout();
        this.menuStatus.SuspendLayout();
        this.SuspendLayout();
        //
        // timer1
        //
        this.timer1.Interval = 1600;
        this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
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
        // menuStatus
        //
        this.menuStatus.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.menuItemAvailable, this.menuItemTraining, this.menuItemTeamAssist, this.menuItemWrapUp, this.menuItemOfflineAssist, this.menuItemUnavailable, this.toolStripMenuItem2, this.menuItemRinggroupAll, this.menuItemRinggroupNone, this.menuItemRinggroupsDefault, this.menuItemBackup, this.toolStripMenuItem1, this.menuItemLunch, this.menuItemHome, this.menuItemBreak });
        this.menuStatus.Name = "menuStatus";
        this.menuStatus.Size = new System.Drawing.Size(177, 302);
        //
        // menuItemAvailable
        //
        this.menuItemAvailable.Name = "menuItemAvailable";
        this.menuItemAvailable.Size = new System.Drawing.Size(176, 22);
        this.menuItemAvailable.Text = "Available";
        this.menuItemAvailable.Click += new System.EventHandler(this.menuItemAvailable_Click);
        //
        // menuItemTraining
        //
        this.menuItemTraining.Name = "menuItemTraining";
        this.menuItemTraining.Size = new System.Drawing.Size(176, 22);
        this.menuItemTraining.Text = "Training";
        this.menuItemTraining.Click += new System.EventHandler(this.menuItemTraining_Click);
        //
        // menuItemTeamAssist
        //
        this.menuItemTeamAssist.Name = "menuItemTeamAssist";
        this.menuItemTeamAssist.Size = new System.Drawing.Size(176, 22);
        this.menuItemTeamAssist.Text = "TeamAssist";
        this.menuItemTeamAssist.Click += new System.EventHandler(this.menuItemTeamAssist_Click);
        //
        // menuItemWrapUp
        //
        this.menuItemWrapUp.Name = "menuItemWrapUp";
        this.menuItemWrapUp.Size = new System.Drawing.Size(176, 22);
        this.menuItemWrapUp.Text = "WrapUp";
        this.menuItemWrapUp.Click += new System.EventHandler(this.menuItemWrapUp_Click);
        //
        // menuItemOfflineAssist
        //
        this.menuItemOfflineAssist.Name = "menuItemOfflineAssist";
        this.menuItemOfflineAssist.Size = new System.Drawing.Size(176, 22);
        this.menuItemOfflineAssist.Text = "OfflineAssist";
        this.menuItemOfflineAssist.Click += new System.EventHandler(this.menuItemOfflineAssist_Click);
        //
        // menuItemUnavailable
        //
        this.menuItemUnavailable.Name = "menuItemUnavailable";
        this.menuItemUnavailable.Size = new System.Drawing.Size(176, 22);
        this.menuItemUnavailable.Text = "Unavailable";
        this.menuItemUnavailable.Click += new System.EventHandler(this.menuItemUnavailable_Click);
        //
        // toolStripMenuItem2
        //
        this.toolStripMenuItem2.Name = "toolStripMenuItem2";
        this.toolStripMenuItem2.Size = new System.Drawing.Size(173, 6);
        //
        // menuItemRinggroupAll
        //
        this.menuItemRinggroupAll.Name = "menuItemRinggroupAll";
        this.menuItemRinggroupAll.Size = new System.Drawing.Size(176, 22);
        this.menuItemRinggroupAll.Text = "Ringgroups All";
        this.menuItemRinggroupAll.Click += new System.EventHandler(this.menuItemRinggroupAll_Click);
        //
        // menuItemRinggroupNone
        //
        this.menuItemRinggroupNone.Name = "menuItemRinggroupNone";
        this.menuItemRinggroupNone.Size = new System.Drawing.Size(176, 22);
        this.menuItemRinggroupNone.Text = "Ringgroups None";
        this.menuItemRinggroupNone.Click += new System.EventHandler(this.menuItemRinggroupNone_Click);
        //
        // menuItemRinggroupsDefault
        //
        this.menuItemRinggroupsDefault.Name = "menuItemRinggroupsDefault";
        this.menuItemRinggroupsDefault.Size = new System.Drawing.Size(176, 22);
        this.menuItemRinggroupsDefault.Text = "Ringgroups Default";
        this.menuItemRinggroupsDefault.Click += new System.EventHandler(this.menuItemRinggroupsDefault_Click);
        //
        // menuItemBackup
        //
        this.menuItemBackup.Name = "menuItemBackup";
        this.menuItemBackup.Size = new System.Drawing.Size(176, 22);
        this.menuItemBackup.Text = "Backup";
        this.menuItemBackup.Click += new System.EventHandler(this.menuItemBackup_Click);
        //
        // toolStripMenuItem1
        //
        this.toolStripMenuItem1.Name = "toolStripMenuItem1";
        this.toolStripMenuItem1.Size = new System.Drawing.Size(173, 6);
        //
        // menuItemLunch
        //
        this.menuItemLunch.Name = "menuItemLunch";
        this.menuItemLunch.Size = new System.Drawing.Size(176, 22);
        this.menuItemLunch.Text = "Lunch";
        this.menuItemLunch.Click += new System.EventHandler(this.menuItemLunch_Click);
        //
        // menuItemHome
        //
        this.menuItemHome.Name = "menuItemHome";
        this.menuItemHome.Size = new System.Drawing.Size(176, 22);
        this.menuItemHome.Text = "Home";
        this.menuItemHome.Click += new System.EventHandler(this.menuItemHome_Click);
        //
        // menuItemBreak
        //
        this.menuItemBreak.Name = "menuItemBreak";
        this.menuItemBreak.Size = new System.Drawing.Size(176, 22);
        this.menuItemBreak.Text = "Break";
        this.menuItemBreak.Click += new System.EventHandler(this.menuItemBreak_Click);
        //
        // labelMsg
        //
        this.labelMsg.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelMsg.ForeColor = System.Drawing.Color.Firebrick;
        this.labelMsg.Location = new System.Drawing.Point(141, 2);
        this.labelMsg.Name = "labelMsg";
        this.labelMsg.Size = new System.Drawing.Size(198, 20);
        this.labelMsg.TabIndex = 25;
        this.labelMsg.Text = "Phone Messages: 0";
        this.labelMsg.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // timerMsgs
        //
        this.timerMsgs.Interval = 3000;
        this.timerMsgs.Tick += new System.EventHandler(this.timerMsgs_Tick);
        //
        // butOverride
        //
        this.butOverride.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOverride.setAutosize(true);
        this.butOverride.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOverride.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOverride.setCornerRadius(4F);
        this.butOverride.Location = new System.Drawing.Point(0, 0);
        this.butOverride.Name = "butOverride";
        this.butOverride.Size = new System.Drawing.Size(75, 24);
        this.butOverride.TabIndex = 24;
        this.butOverride.Text = "Override";
        this.butOverride.Click += new System.EventHandler(this.butOverride_Click);
        //
        // gridEmp
        //
        this.gridEmp.setAllowSelection(false);
        this.gridEmp.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridEmp.setHScrollVisible(false);
        this.gridEmp.Location = new System.Drawing.Point(0, 24);
        this.gridEmp.Name = "gridEmp";
        this.gridEmp.setScrollValue(0);
        this.gridEmp.Size = new System.Drawing.Size(428, 299);
        this.gridEmp.TabIndex = 22;
        this.gridEmp.setTitle("Phones");
        this.gridEmp.setTranslationName("TableEmpClock");
        this.gridEmp.setWrapText(false);
        this.gridEmp.CellClick = __MultiODGridClickEventHandler.combine(this.gridEmp.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridEmp_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridEmp.MouseUp += new System.Windows.Forms.MouseEventHandler(this.gridEmp_MouseUp);
        //
        // UserControlPhonePanel
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.Controls.Add(this.labelMsg);
        this.Controls.Add(this.butOverride);
        this.Controls.Add(this.gridEmp);
        this.Name = "UserControlPhonePanel";
        this.Size = new System.Drawing.Size(428, 323);
        this.Load += new System.EventHandler(this.UserControlPhonePanel_Load);
        this.menuNumbers.ResumeLayout(false);
        this.menuStatus.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridEmp;
    private System.Windows.Forms.Timer timer1 = new System.Windows.Forms.Timer();
    private OpenDental.UI.Button butOverride;
    private System.Windows.Forms.ContextMenuStrip menuNumbers = new System.Windows.Forms.ContextMenuStrip();
    private System.Windows.Forms.ToolStripMenuItem menuItemManage = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemAdd = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ContextMenuStrip menuStatus = new System.Windows.Forms.ContextMenuStrip();
    private System.Windows.Forms.ToolStripMenuItem menuItemAvailable = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemTraining = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemTeamAssist = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemWrapUp = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemOfflineAssist = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemUnavailable = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemBreak = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemLunch = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemHome = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.Label labelMsg = new System.Windows.Forms.Label();
    private System.Windows.Forms.Timer timerMsgs = new System.Windows.Forms.Timer();
    private System.Windows.Forms.ToolStripMenuItem menuItemBackup = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripSeparator toolStripMenuItem1 = new System.Windows.Forms.ToolStripSeparator();
    private System.Windows.Forms.ToolStripSeparator toolStripMenuItem2 = new System.Windows.Forms.ToolStripSeparator();
    private System.Windows.Forms.ToolStripMenuItem menuItemRinggroupAll = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemRinggroupNone = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemRinggroupsDefault = new System.Windows.Forms.ToolStripMenuItem();
}


