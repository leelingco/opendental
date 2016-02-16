//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;

public class FormProcEditAll  extends Form 
{

    public List<Procedure> ProcList = new List<Procedure>();
    private List<Procedure> ProcOldList = new List<Procedure>();
    //private bool StartedAttachedToClaim;
    private boolean AnyAreC = new boolean();
    public FormProcEditAll() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formProcEditAll_Load(Object sender, EventArgs e) throws Exception {
        ProcOldList = new List<Procedure>();
        for (int i = 0;i < ProcList.Count;i++)
        {
            ProcOldList.Add(ProcList[i].Copy());
        }
        AnyAreC = false;
        DateTime oldestDateEntryC = DateTime.Today;
        boolean datesAreSame = true;
        for (int i = 0;i < ProcList.Count;i++)
        {
            if (ProcList[i].ProcStatus == ProcStat.C)
            {
                AnyAreC = true;
                if (ProcList[i].ProcDate < oldestDateEntryC)
                {
                    oldestDateEntryC = ProcList[i].ProcDate;
                }
                 
            }
             
            if (ProcList[0].ProcDate != ProcList[i].ProcDate)
            {
                datesAreSame = false;
            }
             
        }
        if (AnyAreC)
        {
            if (!Security.isAuthorized(Permissions.ProcComplEdit,oldestDateEntryC))
            {
                butOK.Enabled = false;
                butEditAnyway.Enabled = false;
            }
             
        }
         
        List<ClaimProc> ClaimProcList = ClaimProcs.Refresh(ProcList[0].PatNum);
        if (Procedures.IsAttachedToClaim(ProcList, ClaimProcList))
        {
            //StartedAttachedToClaim=true;
            //however, this doesn't stop someone from creating a claim while this window is open,
            //so this is checked at the end, too.
            textDate.Enabled = false;
            butToday.Enabled = false;
            butEditAnyway.Visible = true;
            labelClaim.Visible = true;
        }
         
        if (datesAreSame)
        {
            textDate.Text = ProcList[0].ProcDate.ToShortDateString();
        }
         
    }

    private void butToday_Click(Object sender, EventArgs e) throws Exception {
        if (textDate.Enabled)
        {
            textDate.Text = DateTime.Today.ToShortDateString();
        }
         
    }

    private void butEditAnyway_Click(Object sender, EventArgs e) throws Exception {
        textDate.Enabled = true;
        butToday.Enabled = true;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (!StringSupport.equals(textDate.Text, ""))
        {
            DateTime procDate = PIn.Date(textDate.Text);
            Appointment apt;
            for (int i = 0;i < ProcList.Count;i++)
            {
                if (ProcList[i].AptNum == 0)
                {
                    continue;
                }
                 
                apt = Appointments.GetOneApt(ProcList[i].AptNum);
                if (ProcList[i].ProcDate != procDate)
                {
                    if (!MsgBox.show(this,true,"Date does not match appointment date.  Continue anyway?"))
                    {
                        return ;
                    }
                     
                    break;
                }
                 
            }
            for (int i = 0;i < ProcList.Count;i++)
            {
                ProcList[i].ProcDate = procDate;
                Procedures.Update(ProcList[i], ProcOldList[i]);
            }
            Recalls.Synch(ProcList[0].PatNum);
            if (AnyAreC)
            {
                Patient pat = Patients.GetPat(ProcList[0].PatNum);
                String codes = "";
                ProcedureCode ProcedureCode2;
                for (int i = 0;i < ProcList.Count;i++)
                {
                    if (i > 0)
                    {
                        codes += ", ";
                    }
                     
                    ProcedureCode2 = ProcedureCodes.GetProcCode(ProcList[i].CodeNum);
                    codes += ProcedureCode2.ProcCode;
                }
                SecurityLogs.MakeLogEntry(Permissions.ProcComplEdit, ProcList[0].PatNum, pat.getNameLF() + codes + ", New date:" + procDate.ToShortDateString());
            }
             
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textDate = new OpenDental.ValidDate();
        this.label1 = new System.Windows.Forms.Label();
        this.butEditAnyway = new OpenDental.UI.Button();
        this.labelClaim = new System.Windows.Forms.Label();
        this.butToday = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(440, 129);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(440, 159);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(107, 29);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(76, 20);
        this.textDate.TabIndex = 5;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(9, 32);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(96, 14);
        this.label1.TabIndex = 4;
        this.label1.Text = "Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butEditAnyway
        //
        this.butEditAnyway.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEditAnyway.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butEditAnyway.setAutosize(true);
        this.butEditAnyway.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEditAnyway.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEditAnyway.setCornerRadius(4F);
        this.butEditAnyway.Location = new System.Drawing.Point(288, 159);
        this.butEditAnyway.Name = "butEditAnyway";
        this.butEditAnyway.Size = new System.Drawing.Size(96, 24);
        this.butEditAnyway.TabIndex = 53;
        this.butEditAnyway.Text = "&Edit Anyway";
        this.butEditAnyway.Visible = false;
        this.butEditAnyway.Click += new System.EventHandler(this.butEditAnyway_Click);
        //
        // labelClaim
        //
        this.labelClaim.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelClaim.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelClaim.Location = new System.Drawing.Point(9, 110);
        this.labelClaim.Name = "labelClaim";
        this.labelClaim.Size = new System.Drawing.Size(295, 73);
        this.labelClaim.TabIndex = 52;
        this.labelClaim.Text = "A procedure is attached to a claim, so certain fields should not be edited.  You " + "should reprint the claim if any significant changes are made.";
        this.labelClaim.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        this.labelClaim.Visible = false;
        //
        // butToday
        //
        this.butToday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToday.setAutosize(true);
        this.butToday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToday.setCornerRadius(4F);
        this.butToday.Location = new System.Drawing.Point(208, 27);
        this.butToday.Name = "butToday";
        this.butToday.Size = new System.Drawing.Size(72, 24);
        this.butToday.TabIndex = 54;
        this.butToday.Text = "Today";
        this.butToday.Visible = false;
        this.butToday.Click += new System.EventHandler(this.butToday_Click);
        //
        // FormProcEditAll
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(540, 210);
        this.Controls.Add(this.butToday);
        this.Controls.Add(this.butEditAnyway);
        this.Controls.Add(this.labelClaim);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormProcEditAll";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit All Procs";
        this.Load += new System.EventHandler(this.FormProcEditAll_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.ValidDate textDate;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butEditAnyway;
    private System.Windows.Forms.Label labelClaim = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butToday;
}


