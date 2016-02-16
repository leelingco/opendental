//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.ClaimL;
import OpenDental.DateTimeOD;
import OpenDental.FormRepeatChargesUpdate;
import OpenDental.Lan;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.ClaimProcStatus;
import OpenDentBusiness.Claims;
import OpenDentBusiness.EnumClaimMedType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PriSecMed;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Relat;
import OpenDentBusiness.RepeatCharge;
import OpenDentBusiness.RepeatCharges;
import OpenDentBusiness.YN;

/**
* Summary description for FormBasicTemplate.
*/
public class FormRepeatChargesUpdate  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormRepeatChargesUpdate() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRepeatChargesUpdate.class);
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textBox1
        //
        this.textBox1.BackColor = System.Drawing.SystemColors.Control;
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Location = new System.Drawing.Point(43, 13);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(426, 175);
        this.textBox1.TabIndex = 3;
        this.textBox1.Text = resources.GetString("textBox1.Text");
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(393, 197);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
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
        this.butCancel.Location = new System.Drawing.Point(393, 238);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormRepeatChargesUpdate
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(520, 289);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRepeatChargesUpdate";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Update Repeating Charges";
        this.Load += new System.EventHandler(this.FormRepeatChargesUpdate_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formRepeatChargesUpdate_Load(Object sender, System.EventArgs e) throws Exception {
    }

    private Claim createClaim(String claimType, List<PatPlan> patPlanList, List<InsPlan> planList, List<ClaimProc> claimProcList, Procedure proc, List<InsSub> subList) throws Exception {
        long claimFormNum = 0;
        InsPlan planCur = new InsPlan();
        InsSub subCur = new InsSub();
        Relat relatOther = Relat.Self;
        long clinicNum = proc.ClinicNum;
        PlaceOfService placeService = proc.PlaceService;
        System.String __dummyScrutVar0 = claimType;
        if (__dummyScrutVar0.equals("P"))
        {
            subCur = InsSubs.GetSub(PatPlans.GetInsSubNum(patPlanList, PatPlans.GetOrdinal(PriSecMed.Primary, patPlanList, planList, subList)), subList);
            planCur = InsPlans.GetPlan(subCur.PlanNum, planList);
        }
        else if (__dummyScrutVar0.equals("S"))
        {
            subCur = InsSubs.GetSub(PatPlans.GetInsSubNum(patPlanList, PatPlans.GetOrdinal(PriSecMed.Secondary, patPlanList, planList, subList)), subList);
            planCur = InsPlans.GetPlan(subCur.PlanNum, planList);
        }
        else if (__dummyScrutVar0.equals("Med"))
        {
            //It's already been verified that a med plan exists
            subCur = InsSubs.GetSub(PatPlans.GetInsSubNum(patPlanList, PatPlans.GetOrdinal(PriSecMed.Medical, patPlanList, planList, subList)), subList);
            planCur = InsPlans.GetPlan(subCur.PlanNum, planList);
        }
           
        ClaimProc claimProcCur = Procedures.GetClaimProcEstimate(proc.ProcNum, claimProcList, planCur, subCur.InsSubNum);
        if (claimProcCur == null)
        {
            claimProcCur = new ClaimProc();
            ClaimProcs.createEst(claimProcCur,proc,planCur,subCur);
        }
         
        Claim claimCur = new Claim();
        claimCur.PatNum = proc.PatNum;
        claimCur.DateService = proc.ProcDate;
        claimCur.ClinicNum = proc.ClinicNum;
        claimCur.PlaceService = proc.PlaceService;
        claimCur.ClaimStatus = "W";
        claimCur.DateSent = DateTimeOD.getToday();
        claimCur.PlanNum = planCur.PlanNum;
        claimCur.InsSubNum = subCur.InsSubNum;
        InsSub sub;
        System.String __dummyScrutVar1 = claimType;
        if (__dummyScrutVar1.equals("P"))
        {
            claimCur.PatRelat = PatPlans.GetRelat(patPlanList, PatPlans.GetOrdinal(PriSecMed.Primary, patPlanList, planList, subList));
            claimCur.ClaimType = "P";
            claimCur.InsSubNum2 = PatPlans.GetInsSubNum(patPlanList, PatPlans.GetOrdinal(PriSecMed.Secondary, patPlanList, planList, subList));
            sub = InsSubs.GetSub(claimCur.InsSubNum2, subList);
            if (sub.PlanNum > 0 && InsPlans.refreshOne(sub.PlanNum).IsMedical)
            {
                claimCur.PlanNum2 = 0;
                //no sec ins
                claimCur.PatRelat2 = Relat.Self;
            }
            else
            {
                claimCur.PlanNum2 = sub.PlanNum;
                //might be 0 if no sec ins
                claimCur.PatRelat2 = PatPlans.GetRelat(patPlanList, PatPlans.GetOrdinal(PriSecMed.Secondary, patPlanList, planList, subList));
            } 
        }
        else if (__dummyScrutVar1.equals("S"))
        {
            claimCur.PatRelat = PatPlans.GetRelat(patPlanList, PatPlans.GetOrdinal(PriSecMed.Secondary, patPlanList, planList, subList));
            claimCur.ClaimType = "S";
            claimCur.InsSubNum2 = PatPlans.GetInsSubNum(patPlanList, PatPlans.GetOrdinal(PriSecMed.Primary, patPlanList, planList, subList));
            sub = InsSubs.GetSub(claimCur.InsSubNum2, subList);
            claimCur.PlanNum2 = sub.PlanNum;
            claimCur.PatRelat2 = PatPlans.GetRelat(patPlanList, PatPlans.GetOrdinal(PriSecMed.Primary, patPlanList, planList, subList));
        }
        else if (__dummyScrutVar1.equals("Med"))
        {
            claimCur.PatRelat = PatPlans.GetFromList(patPlanList, subCur.InsSubNum).Relationship;
            claimCur.ClaimType = "Other";
            if (PrefC.getBool(PrefName.ClaimMedTypeIsInstWhenInsPlanIsMedical))
            {
                claimCur.MedType = EnumClaimMedType.Institutional;
            }
            else
            {
                claimCur.MedType = EnumClaimMedType.Medical;
            } 
        }
        else if (__dummyScrutVar1.equals("Other"))
        {
            claimCur.PatRelat = relatOther;
            claimCur.ClaimType = "Other";
            //plannum2 is not automatically filled in.
            claimCur.ClaimForm = claimFormNum;
            if (planCur.IsMedical)
            {
                if (PrefC.getBool(PrefName.ClaimMedTypeIsInstWhenInsPlanIsMedical))
                {
                    claimCur.MedType = EnumClaimMedType.Institutional;
                }
                else
                {
                    claimCur.MedType = EnumClaimMedType.Medical;
                } 
            }
             
        }
            
        if (StringSupport.equals(planCur.PlanType, "c"))
        {
            //if capitation
            claimCur.ClaimType = "Cap";
        }
         
        claimCur.ProvTreat = proc.ProvNum;
        if (Providers.getIsSec(proc.ProvNum))
        {
            claimCur.ProvTreat = Patients.getPat(proc.PatNum).PriProv;
        }
         
        //OK if zero, because auto select first in list when open claim
        claimCur.IsProsthesis = "N";
        claimCur.ProvBill = Providers.getBillingProvNum(claimCur.ProvTreat,claimCur.ClinicNum);
        //OK if zero, because it will get fixed in claim
        claimCur.EmployRelated = YN.No;
        claimCur.ClaimForm = planCur.ClaimFormNum;
        Claims.insert(claimCur);
        //attach procedure
        claimProcCur.ClaimNum = claimCur.ClaimNum;
        if (StringSupport.equals(planCur.PlanType, "c"))
        {
            //if capitation
            claimProcCur.Status = ClaimProcStatus.CapClaim;
        }
        else
        {
            claimProcCur.Status = ClaimProcStatus.NotReceived;
        } 
        if (planCur.UseAltCode && (!StringSupport.equals(ProcedureCodes.getProcCode(proc.CodeNum).AlternateCode1, "")))
        {
            claimProcCur.CodeSent = ProcedureCodes.getProcCode(proc.CodeNum).AlternateCode1;
        }
        else if (planCur.IsMedical && !StringSupport.equals(proc.MedicalCode, ""))
        {
            claimProcCur.CodeSent = proc.MedicalCode;
        }
        else
        {
            claimProcCur.CodeSent = ProcedureCodes.getProcCode(proc.CodeNum).ProcCode;
            if (claimProcCur.CodeSent.Length > 5 && StringSupport.equals(claimProcCur.CodeSent.Substring(0, 1), "D"))
            {
                claimProcCur.CodeSent = claimProcCur.CodeSent.Substring(0, 5);
            }
             
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canadian. en-CA or fr-CA
                if (claimProcCur.CodeSent.Length > 5)
                {
                    //In Canadian e-claims, codes can contain letters or numbers and cannot be longer than 5 characters.
                    claimProcCur.CodeSent = claimProcCur.CodeSent.Substring(0, 5);
                }
                 
            }
             
        }  
        claimProcCur.LineNumber = (byte)1;
        ClaimProcs.update(claimProcCur);
        return claimCur;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        RepeatCharge[] chargeList = RepeatCharges.Refresh(0);
        //Gets all repeating charges for all patients, they may be disabled
        int countAdded = 0;
        int claimsAdded = 0;
        DateTime startDate = new DateTime();
        Procedure proc;
        for (int i = 0;i < chargeList.Length;i++)
        {
            if (!chargeList[i].IsEnabled)
            {
                continue;
            }
             
            //first make sure it is not disabled
            if (chargeList[i].DateStart > DateTime.Today)
            {
                continue;
            }
             
            //not started yet
            //if(chargeList[i].DateStop.Year>1880//not blank
            //	&& chargeList[i].DateStop<DateTime.Today)//but already ended
            //{
            //	continue;
            //}
            //get a list dates of all completed procedures with this Code and patNum
            ArrayList ALdates = RepeatCharges.GetDates(ProcedureCodes.GetCodeNum(chargeList[i].ProcCode), chargeList[i].PatNum);
            startDate = chargeList[i].DateStart;
            //This is the repeating date using the old methodology.  It is necessary for checking if the repeating procedure was already added using the old methodology.
            DateTime possibleDateOld = startDate;
            //This is a more accurate repeating date which will allow procedures to be added on the 28th and later.
            DateTime possibleDateNew = startDate;
            int countMonths = 0;
            while (possibleDateNew <= DateTime.Today)
            {
                //start looping through possible dates, beginning with the start date of the repeating charge
                //Only allow back dating up to one month and 20 days.
                if (possibleDateNew < DateTime.Today.AddDays(-50))
                {
                    possibleDateOld = possibleDateOld.AddMonths(1);
                    countMonths++;
                    possibleDateNew = startDate.AddMonths(countMonths);
                    continue;
                }
                 
                //don't go back more than one month and 20 days
                //check to see if the possible date is present in the list
                if (ALdates.Contains(possibleDateNew) || ALdates.Contains(possibleDateOld))
                {
                    possibleDateOld = possibleDateOld.AddMonths(1);
                    countMonths++;
                    possibleDateNew = startDate.AddMonths(countMonths);
                    continue;
                }
                 
                //not blank
                if (chargeList[i].DateStop.Year > 1880 && chargeList[i].DateStop < possibleDateNew)
                {
                    break;
                }
                 
                //but already ended
                //otherwise, insert a procedure to db
                proc = new Procedure();
                proc.CodeNum = ProcedureCodes.GetCodeNum(chargeList[i].ProcCode);
                proc.DateEntryC = DateTimeOD.getToday();
                proc.PatNum = chargeList[i].PatNum;
                proc.ProcDate = possibleDateNew;
                proc.DateTP = possibleDateNew;
                proc.ProcFee = chargeList[i].ChargeAmt;
                proc.ProcStatus = ProcStat.C;
                proc.ProvNum = PrefC.getLong(PrefName.PracticeDefaultProv);
                proc.MedicalCode = ProcedureCodes.getProcCode(proc.CodeNum).MedicalCode;
                proc.BaseUnits = ProcedureCodes.getProcCode(proc.CodeNum).BaseUnits;
                proc.DiagnosticCode = PrefC.getString(PrefName.ICD9DefaultForNewProcs);
                //Check if the repeating charge has been flagged to copy it's note into the billing note of the procedure.
                if (chargeList[i].CopyNoteToProc)
                {
                    proc.BillingNote = chargeList[i].Note;
                }
                 
                Procedures.insert(proc);
                //no recall synch needed because dental offices don't use this feature
                countAdded++;
                possibleDateOld = possibleDateOld.AddMonths(1);
                countMonths++;
                possibleDateNew = startDate.AddMonths(countMonths);
                if (chargeList[i].CreatesClaim && !ProcedureCodes.GetProcCode(chargeList[i].ProcCode).NoBillIns)
                {
                    List<PatPlan> patPlanList = PatPlans.Refresh(chargeList[i].PatNum);
                    List<InsSub> subList = InsSubs.RefreshForFam(Patients.GetFamily(chargeList[i].PatNum));
                    List<InsPlan> insPlanList = InsPlans.RefreshForSubList(subList);
                        ;
                    List<Benefit> benefitList = Benefits.Refresh(patPlanList, subList);
                    Claim claimCur;
                    List<Procedure> procCurList = new List<Procedure>();
                    procCurList.Add(proc);
                    if (patPlanList.Count == 0)
                    {
                        continue;
                    }
                     
                    //no current insurance, do not create a claim
                    //create the claimprocs
                    Procedures.ComputeEstimates(proc, proc.PatNum, new List<ClaimProc>(), true, insPlanList, patPlanList, benefitList, Patients.getPat(proc.PatNum).getAge(), subList);
                    //get claimprocs for this proc, may be more than one
                    List<ClaimProc> claimProcList = ClaimProcs.getForProc(ClaimProcs.refresh(proc.PatNum),proc.ProcNum);
                    String claimType = "P";
                    if (patPlanList.Count == 1 && PatPlans.GetOrdinal(PriSecMed.Medical, patPlanList, insPlanList, subList) > 0)
                    {
                        //if there's exactly one medical plan
                        claimType = "Med";
                    }
                     
                    claimCur = CreateClaim(claimType, patPlanList, insPlanList, claimProcList, proc, subList);
                    claimProcList = ClaimProcs.refresh(proc.PatNum);
                    if (claimCur.ClaimNum == 0)
                    {
                        continue;
                    }
                     
                    claimsAdded++;
                    ClaimL.CalculateAndUpdate(procCurList, insPlanList, claimCur, patPlanList, benefitList, Patients.getPat(proc.PatNum).getAge(), subList);
                    //if there exists a secondary plan
                    if (PatPlans.GetOrdinal(PriSecMed.Secondary, patPlanList, insPlanList, subList) > 0 && !CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                    {
                        //and not canada (don't create secondary claim for canada)
                        claimCur = CreateClaim("S", patPlanList, insPlanList, claimProcList, proc, subList);
                        if (claimCur.ClaimNum == 0)
                        {
                            continue;
                        }
                         
                        claimsAdded++;
                        claimProcList = ClaimProcs.refresh(proc.PatNum);
                        claimCur.ClaimStatus = "H";
                        ClaimL.CalculateAndUpdate(procCurList, insPlanList, claimCur, patPlanList, benefitList, Patients.getPat(proc.PatNum).getAge(), subList);
                    }
                     
                }
                 
            }
        }
        //MessageBox.Show(countAdded.ToString()+" "+Lan.g(this,"procedures added."));
        MessageBox.Show(countAdded.ToString() + " " + Lan.g(this,"procedures added.") + "\r\n" + claimsAdded.ToString() + " " + Lan.g(this,"claims added."));
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


