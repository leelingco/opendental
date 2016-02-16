//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormProcCodes;
import OpenDental.FormProcEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Family;
import OpenDentBusiness.Fees;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.Tooth;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormApptProcs  extends Form 
{

    //public int PatNum;
    private List<Procedure> ProcList = new List<Procedure>();
    /**
    * If form closes with OK, this contains selected proc num.
    */
    public List<long> SelectedProcNums = new List<long>();
    /**
    * It's OK if AptCur is not completely up-to-date.  We are going to use PatNum, isPlanned, AptDateTime, AptStatus, and AptNum.
    */
    public Appointment AptCur;
    /**
    * Not currently used.  Might use again some day as a sub window of FormApptEdit.  Specify AptCur before opening this form.
    */
    public FormApptProcs() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formApptProcs_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        List<Procedure> entireList = Procedures.refresh(AptCur.PatNum);
        ProcList = new List<Procedure>();
        boolean isPlanned = AptCur.AptStatus == ApptStatus.Planned;
        ApptStatus apptStatus = AptCur.AptStatus;
        for (int i = 0;i < entireList.Count;i++)
        {
            //We want all unattached completed procs with same date as appt.
            //but only if one of these types
            if (apptStatus == ApptStatus.Scheduled || apptStatus == ApptStatus.Complete || apptStatus == ApptStatus.ASAP || apptStatus == ApptStatus.Broken)
            {
                if (entireList[i].AptNum == 0 && entireList[i].ProcStatus == ProcStat.C && entireList[i].ProcDate.Date == AptCur.AptDateTime.Date)
                {
                    ProcList.Add(entireList[i]);
                }
                 
            }
             
            //otherwise, we only want TP procs that are not attached to this appointment.
            //As for TP procs attached to other appointments, we will show this to the user and warn them about it,
            //but we won't filter them out.
            if (entireList[i].ProcStatus != ProcStat.TP)
            {
                continue;
            }
             
            if (isPlanned)
            {
                if (entireList[i].PlannedAptNum == AptCur.AptNum)
                {
                    continue;
                }
                 
            }
            else
            {
                if (entireList[i].AptNum == AptCur.AptNum)
                {
                    continue;
                }
                 
            } 
            ProcList.Add(entireList[i]);
        }
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g("TableProcSelect","OtherAppt"), 70, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcSelect","Code"),55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcSelect","Priority"),55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcSelect","Tooth"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcSelect","Description"),250);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcSelect","Fee"), 60, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < ProcList.Count;i++)
        {
            row = new ODGridRow();
            if (ProcList[i].ProcStatus == ProcStat.C)
            {
                //so unattached
                row.getCells().add("");
            }
            else if (isPlanned && ProcList[i].PlannedAptNum != 0)
            {
                row.getCells().add("X");
            }
            else if (!isPlanned && ProcList[i].AptNum != 0)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            }   
            row.getCells().Add(ProcedureCodes.GetStringProcCode(ProcList[i].CodeNum));
            row.getCells().Add(DefC.GetName(DefCat.TxPriorities, ProcList[i].Priority));
            row.getCells().Add(Tooth.ToInternat(ProcList[i].ToothNum));
            row.getCells().Add(ProcedureCodes.GetLaymanTerm(ProcList[i].CodeNum));
            row.getCells().Add(ProcList[i].ProcFee.ToString("F"));
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
    }

    //SelectedProcNums=new List<int>();
    //SelectedProcNums.Add(ProcList[e.Row].ProcNum);
    //DialogResult=DialogResult.OK;
    //Maybe edit proc?
    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormProcCodes FormP = new FormProcCodes();
        FormP.IsSelectionMode = true;
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Procedure ProcCur;
        ProcCur = new Procedure();
        //going to be an insert, so no need to set Procedures.CurOld
        ProcCur.CodeNum = FormP.SelectedCodeNum;
        //procnum
        ProcCur.PatNum = AptCur.PatNum;
        //aptnum
        //proccode
        //ProcCur.CodeNum=ProcedureCodes.GetProcCode(ProcCur.OldCode).CodeNum;//already set
        ProcCur.ProcDate = DateTime.Today;
        ProcCur.DateTP = ProcCur.ProcDate;
        //int totUnits = ProcCur.BaseUnits + ProcCur.UnitQty;
        InsPlan priplan = null;
        InsSub prisub = null;
        Family fam = Patients.getFamily(AptCur.PatNum);
        Patient pat = fam.getPatient(AptCur.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlanList = PatPlans.refresh(pat.PatNum);
        if (patPlanList.Count > 0)
        {
            prisub = InsSubs.GetSub(patPlanList[0].InsSubNum, subList);
            priplan = InsPlans.GetPlan(prisub.PlanNum, planList);
        }
         
        //Check if it's a medical procedure.
        double insfee = new double();
        boolean isMed = false;
        ProcCur.MedicalCode = ProcedureCodes.getProcCode(ProcCur.CodeNum).MedicalCode;
        if (ProcCur.MedicalCode != null && !StringSupport.equals(ProcCur.MedicalCode, ""))
        {
            isMed = true;
        }
         
        //Get fee schedule for medical or dental.
        long feeSch = new long();
        if (isMed)
        {
            feeSch = Fees.GetMedFeeSched(pat, planList, patPlanList, subList);
        }
        else
        {
            feeSch = Fees.GetFeeSched(pat, planList, patPlanList, subList);
        } 
        //Get the fee amount for medical or dental.
        if (PrefC.getBool(PrefName.MedicalFeeUsedForNewProcs) && isMed)
        {
            insfee = Fees.getAmount0(ProcedureCodes.getProcCode(ProcCur.MedicalCode).CodeNum,feeSch);
        }
        else
        {
            insfee = Fees.getAmount0(ProcCur.CodeNum,feeSch);
        } 
        if (priplan != null && StringSupport.equals(priplan.PlanType, "p"))
        {
            //PPO
            double standardfee = Fees.getAmount0(ProcCur.CodeNum,Providers.getProv(Patients.getProvNum(pat)).FeeSched);
            if (standardfee > insfee)
            {
                ProcCur.ProcFee = standardfee;
            }
            else
            {
                ProcCur.ProcFee = insfee;
            } 
        }
        else
        {
            ProcCur.ProcFee = insfee;
        } 
        //surf
        //ToothNum
        //Procedures.Cur.ToothRange
        //ProcCur.NoBillIns=ProcedureCodes.GetProcCode(ProcCur.ProcCode).NoBillIns;
        ProcCur.Priority = 0;
        ProcCur.ProcStatus = ProcStat.TP;
        if (ProcedureCodes.getProcCode(ProcCur.CodeNum).IsHygiene && pat.SecProv != 0)
        {
            ProcCur.ProvNum = pat.SecProv;
        }
        else
        {
            ProcCur.ProvNum = pat.PriProv;
        } 
        ProcCur.Note = "";
        ProcCur.ClinicNum = pat.ClinicNum;
        //dx
        //nextaptnum
        ProcCur.DateEntryC = DateTime.Now;
        ProcCur.BaseUnits = ProcedureCodes.getProcCode(ProcCur.CodeNum).BaseUnits;
        ProcCur.SiteNum = pat.SiteNum;
        ProcCur.RevCode = ProcedureCodes.getProcCode(ProcCur.CodeNum).RevenueCodeDefault;
        ProcCur.DiagnosticCode = PrefC.getString(PrefName.ICD9DefaultForNewProcs);
        Procedures.insert(ProcCur);
        List<Benefit> benefitList = Benefits.Refresh(patPlanList, subList);
        Procedures.ComputeEstimates(ProcCur, pat.PatNum, new List<ClaimProc>(), true, planList, patPlanList, benefitList, pat.getAge(), subList);
        FormProcEdit FormPE = new FormProcEdit(ProcCur,pat.copy(),fam);
        FormPE.IsNew = true;
        FormPE.ShowDialog();
        if (FormPE.DialogResult == DialogResult.Cancel)
        {
            try
            {
                //any created claimprocs are automatically deleted from within procEdit window.
                Procedures.delete(ProcCur.ProcNum);
            }
            catch (Exception ex)
            {
                //also deletes the claimprocs
                MessageBox.Show(ex.Message);
            }
        
        }
        else if (Programs.getUsingOrion())
        {
        }
        else
        {
            //No need to synch with Orion mode.
            //Default is set to TP, so Synch is usually not needed.
            if (ProcCur.ProcStatus == ProcStat.C || ProcCur.ProcStatus == ProcStat.EC || ProcCur.ProcStatus == ProcStat.EO)
            {
                Recalls.synch(pat.PatNum);
            }
             
        }  
        fillGrid();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select items first.");
            return ;
        }
         
        boolean isAttachedToOtherApt = false;
        boolean isPlanned = AptCur.AptStatus == ApptStatus.Planned;
        SelectedProcNums = new List<long>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            SelectedProcNums.Add(ProcList[gridMain.getSelectedIndices()[i]].ProcNum);
            if (isPlanned && ProcList[gridMain.getSelectedIndices()[i]].PlannedAptNum != 0)
            {
                isAttachedToOtherApt = true;
            }
             
            if (!isPlanned && ProcList[gridMain.getSelectedIndices()[i]].AptNum != 0)
            {
                isAttachedToOtherApt = true;
            }
             
        }
        if (isAttachedToOtherApt)
        {
            if (!MsgBox.show(this,true,"One or more of the procedures is already attached to another appointment.  Attach to this appointment instead?"))
            {
                SelectedProcNums = new List<long>();
                return ;
            }
             
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
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
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(32, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(559, 495);
        this.gridMain.TabIndex = 141;
        this.gridMain.setTitle("Procedures");
        this.gridMain.setTranslationName("TableApptProcs");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(625, 442);
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
        this.butCancel.Location = new System.Drawing.Point(625, 483);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(625, 295);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 153;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormApptProcs
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(725, 534);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormApptProcs";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Add Procedures";
        this.Load += new System.EventHandler(this.FormApptProcs_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
}


