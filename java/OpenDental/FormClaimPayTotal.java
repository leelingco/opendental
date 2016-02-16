//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:50 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormClaimPayTotal;
import OpenDental.FormClaimProc;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.ClaimProcStatus;
import OpenDentBusiness.Family;
import OpenDentBusiness.Fee;
import OpenDentBusiness.Fees;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Providers;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Tooth;

/**
* Summary description for FormClaimPayTotal.
*/
public class FormClaimPayTotal  extends System.Windows.Forms.Form 
{
    private OpenDental.ValidDouble textWriteOff;
    private System.Windows.Forms.TextBox textInsPayAllowed = new System.Windows.Forms.TextBox();
    private OpenDental.ValidDouble textInsPayAmt;
    private System.Windows.Forms.TextBox textClaimFee = new System.Windows.Forms.TextBox();
    private OpenDental.ValidDouble textDedApplied;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDeductible;
    private OpenDental.UI.Button butWriteOff;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    /**
    * 
    */
    public ClaimProc[] ClaimProcsToEdit = new ClaimProc[]();
    private List<Procedure> ProcList = new List<Procedure>();
    private Patient PatCur;
    private Family FamCur;
    private OpenDental.UI.ODGrid gridMain;
    private List<InsPlan> PlanList = new List<InsPlan>();
    private List<PatPlan> PatPlanList = new List<PatPlan>();
    private List<InsSub> SubList = new List<InsSub>();
    /**
    * 
    */
    public FormClaimPayTotal(Patient patCur, Family famCur, List<InsPlan> planList, List<PatPlan> patPlanList, List<InsSub> subList) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        FamCur = famCur;
        PatCur = patCur;
        PlanList = planList;
        SubList = subList;
        PatPlanList = patPlanList;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimPayTotal.class);
        this.textInsPayAllowed = new System.Windows.Forms.TextBox();
        this.textClaimFee = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butWriteOff = new OpenDental.UI.Button();
        this.butDeductible = new OpenDental.UI.Button();
        this.textWriteOff = new OpenDental.ValidDouble();
        this.textInsPayAmt = new OpenDental.ValidDouble();
        this.textDedApplied = new OpenDental.ValidDouble();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textInsPayAllowed
        //
        this.textInsPayAllowed.Location = new System.Drawing.Point(444, 275);
        this.textInsPayAllowed.Name = "textInsPayAllowed";
        this.textInsPayAllowed.ReadOnly = true;
        this.textInsPayAllowed.Size = new System.Drawing.Size(55, 20);
        this.textInsPayAllowed.TabIndex = 116;
        this.textInsPayAllowed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textClaimFee
        //
        this.textClaimFee.Location = new System.Drawing.Point(334, 275);
        this.textClaimFee.Name = "textClaimFee";
        this.textClaimFee.ReadOnly = true;
        this.textClaimFee.Size = new System.Drawing.Size(55, 20);
        this.textClaimFee.TabIndex = 118;
        this.textClaimFee.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label1
        //
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label1.Location = new System.Drawing.Point(246, 278);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(84, 16);
        this.label1.TabIndex = 117;
        this.label1.Text = "Totals";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(337, 325);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(348, 39);
        this.label2.TabIndex = 122;
        this.label2.Text = "Before you click OK, the Deductible and the Ins Pay amounts should exactly match " + "the insurance EOB.";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(11, 283);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(116, 34);
        this.label3.TabIndex = 123;
        this.label3.Text = "Assign to selected procedure:";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(155, 289);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(108, 29);
        this.label4.TabIndex = 124;
        this.label4.Text = "On all unpaid amounts:";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(8, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.OneCell);
        this.gridMain.Size = new System.Drawing.Size(939, 257);
        this.gridMain.TabIndex = 125;
        this.gridMain.setTitle("Procedures");
        this.gridMain.setTranslationName("TableClaimProc");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridMain.CellTextChanged += new System.EventHandler(this.gridMain_CellTextChanged);
        //
        // butWriteOff
        //
        this.butWriteOff.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butWriteOff.setAutosize(true);
        this.butWriteOff.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butWriteOff.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butWriteOff.setCornerRadius(4F);
        this.butWriteOff.Location = new System.Drawing.Point(154, 324);
        this.butWriteOff.Name = "butWriteOff";
        this.butWriteOff.Size = new System.Drawing.Size(90, 25);
        this.butWriteOff.TabIndex = 121;
        this.butWriteOff.Text = "&Write Off";
        this.butWriteOff.Click += new System.EventHandler(this.butWriteOff_Click);
        //
        // butDeductible
        //
        this.butDeductible.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDeductible.setAutosize(true);
        this.butDeductible.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDeductible.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDeductible.setCornerRadius(4F);
        this.butDeductible.Location = new System.Drawing.Point(14, 324);
        this.butDeductible.Name = "butDeductible";
        this.butDeductible.Size = new System.Drawing.Size(92, 25);
        this.butDeductible.TabIndex = 120;
        this.butDeductible.Text = "&Deductible";
        this.butDeductible.Click += new System.EventHandler(this.butDeductible_Click);
        //
        // textWriteOff
        //
        this.textWriteOff.Location = new System.Drawing.Point(554, 275);
        this.textWriteOff.Name = "textWriteOff";
        this.textWriteOff.ReadOnly = true;
        this.textWriteOff.Size = new System.Drawing.Size(55, 20);
        this.textWriteOff.TabIndex = 119;
        this.textWriteOff.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textInsPayAmt
        //
        this.textInsPayAmt.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textInsPayAmt.Location = new System.Drawing.Point(499, 275);
        this.textInsPayAmt.Name = "textInsPayAmt";
        this.textInsPayAmt.ReadOnly = true;
        this.textInsPayAmt.Size = new System.Drawing.Size(55, 20);
        this.textInsPayAmt.TabIndex = 115;
        this.textInsPayAmt.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textDedApplied
        //
        this.textDedApplied.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textDedApplied.Location = new System.Drawing.Point(389, 275);
        this.textDedApplied.Name = "textDedApplied";
        this.textDedApplied.ReadOnly = true;
        this.textDedApplied.Size = new System.Drawing.Size(55, 20);
        this.textDedApplied.TabIndex = 114;
        this.textDedApplied.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(846, 324);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(757, 324);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormClaimPayTotal
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(948, 363);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butWriteOff);
        this.Controls.Add(this.butDeductible);
        this.Controls.Add(this.textWriteOff);
        this.Controls.Add(this.textInsPayAllowed);
        this.Controls.Add(this.textInsPayAmt);
        this.Controls.Add(this.textClaimFee);
        this.Controls.Add(this.textDedApplied);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClaimPayTotal";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Enter Payment";
        this.Load += new System.EventHandler(this.FormClaimPayTotal_Load);
        this.Shown += new System.EventHandler(this.FormClaimPayTotal_Shown);
        this.Activated += new System.EventHandler(this.FormClaimPayTotal_Activated);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formClaimPayTotal_Load(Object sender, System.EventArgs e) throws Exception {
        ProcList = Procedures.refresh(PatCur.PatNum);
        fillGrid();
    }

    private void formClaimPayTotal_Shown(Object sender, EventArgs e) throws Exception {
        InsPlan plan = InsPlans.GetPlan(ClaimProcsToEdit[0].PlanNum, PlanList);
        if (plan.AllowedFeeSched != 0)
        {
            //allowed fee sched
            gridMain.setSelected(new Point(7, 0));
        }
        else
        {
            //Allowed, first row.
            gridMain.setSelected(new Point(8, 0));
        } 
    }

    //InsPay, first row.
    private void fillGrid() throws Exception {
        //Changes made in this window do not get saved until after this window closes.
        //But if you double click on a row, then you will end up saving.  That shouldn't hurt anything, but could be improved.
        //also calculates totals for this "payment"
        //the payment itself is imaginary and is simply the sum of the claimprocs on this form
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableClaimProc","Date"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Prov"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Code"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Tth"),35);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Description"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Billed"), 55, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Deduct"), 55, HorizontalAlignment.Right, true);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Allowed"), 55, HorizontalAlignment.Right, true);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Ins Pay"), 55, HorizontalAlignment.Right, true);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Writeoff"), 55, HorizontalAlignment.Right, true);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Status"), 50, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Pmt"), 30, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Remarks"),170,true);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        Procedure ProcCur;
        for (int i = 0;i < ClaimProcsToEdit.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ClaimProcsToEdit[i].ProcDate.ToShortDateString());
            row.getCells().Add(Providers.GetAbbr(ClaimProcsToEdit[i].ProvNum));
            if (ClaimProcsToEdit[i].ProcNum == 0)
            {
                row.getCells().add("");
                row.getCells().add("");
                row.getCells().add(Lan.g(this,"Total Payment"));
            }
            else
            {
                ProcCur = Procedures.GetProcFromList(ProcList, ClaimProcsToEdit[i].ProcNum);
                row.getCells().add(ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode);
                row.getCells().add(Tooth.toInternat(ProcCur.ToothNum));
                row.getCells().add(ProcedureCodes.getProcCode(ProcCur.CodeNum).Descript);
            } 
            row.getCells().Add(ClaimProcsToEdit[i].FeeBilled.ToString("F"));
            row.getCells().Add(ClaimProcsToEdit[i].DedApplied.ToString("F"));
            if (ClaimProcsToEdit[i].AllowedOverride == -1)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ClaimProcsToEdit[i].AllowedOverride.ToString("F"));
            } 
            row.getCells().Add(ClaimProcsToEdit[i].InsPayAmt.ToString("F"));
            row.getCells().Add(ClaimProcsToEdit[i].WriteOff.ToString("F"));
            Status __dummyScrutVar0 = ClaimProcsToEdit[i].Status;
            if (__dummyScrutVar0.equals(ClaimProcStatus.Received))
            {
                row.getCells().add("Recd");
            }
            else if (__dummyScrutVar0.equals(ClaimProcStatus.NotReceived))
            {
                row.getCells().add("");
            }
            else //adjustment would never show here
            if (__dummyScrutVar0.equals(ClaimProcStatus.Preauth))
            {
                row.getCells().add("PreA");
            }
            else if (__dummyScrutVar0.equals(ClaimProcStatus.Supplemental))
            {
                row.getCells().add("Supp");
            }
            else if (__dummyScrutVar0.equals(ClaimProcStatus.CapClaim))
            {
                row.getCells().add("Cap");
            }
                 
            //Estimate would never show here
            //Cap would never show here
            if (ClaimProcsToEdit[i].ClaimPaymentNum > 0)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(ClaimProcsToEdit[i].Remarks);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        fillTotals();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        try
        {
            saveGridChanges();
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        List<ClaimProcHist> histList = null;
        List<ClaimProcHist> loopList = null;
        RefSupport<List<ClaimProcHist>> refVar___0 = new RefSupport<List<ClaimProcHist>>(loopList);
        FormClaimProc FormCP = new FormClaimProc(ClaimProcsToEdit[e.getRow()], null, FamCur, PatCur, PlanList, histList, refVar___0, PatPlanList, false, SubList);
        loopList = refVar___0.getValue();
        FormCP.IsInClaim = true;
        //no need to worry about permissions here
        FormCP.ShowDialog();
        if (FormCP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
        fillTotals();
    }

    private void gridMain_CellTextChanged(Object sender, EventArgs e) throws Exception {
        fillTotals();
    }

    /**
    * Fails silently if text is in invalid format.
    */
    private void fillTotals() throws Exception {
        double claimFee = 0;
        double dedApplied = 0;
        double insPayAmtAllowed = 0;
        double insPayAmt = 0;
        double writeOff = 0;
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            //double amt;
            claimFee += ClaimProcsToEdit[i].FeeBilled;
            try
            {
                //5
                //6.deduct
                dedApplied += Convert.ToDouble(gridMain.getRows().get___idx(i).getCells()[6].Text);
            }
            catch (Exception __dummyCatchVar0)
            {
            }

            try
            {
                //7.allowed
                insPayAmtAllowed += Convert.ToDouble(gridMain.getRows().get___idx(i).getCells()[7].Text);
            }
            catch (Exception __dummyCatchVar1)
            {
            }

            try
            {
                //8.inspayest
                insPayAmt += Convert.ToDouble(gridMain.getRows().get___idx(i).getCells()[8].Text);
            }
            catch (Exception __dummyCatchVar2)
            {
            }

            try
            {
                //9.writeoff
                writeOff += Convert.ToDouble(gridMain.getRows().get___idx(i).getCells()[9].Text);
            }
            catch (Exception __dummyCatchVar3)
            {
            }
        
        }
        textClaimFee.Text = claimFee.ToString("F");
        textDedApplied.Text = dedApplied.ToString("F");
        textInsPayAllowed.Text = insPayAmtAllowed.ToString("F");
        textInsPayAmt.Text = insPayAmt.ToString("F");
        textWriteOff.Text = writeOff.ToString("F");
    }

    /**
    * Surround with try-catch.
    */
    private void saveGridChanges() throws Exception {
        //validate all grid cells
        double dbl = new double();
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (!StringSupport.equals(gridMain.getRows().get___idx(i).getCells()[6].Text, ""))
            {
                try
                {
                    //deduct
                    dbl = Convert.ToDouble(gridMain.getRows().get___idx(i).getCells()[6].Text);
                }
                catch (Exception __dummyCatchVar4)
                {
                    throw new ApplicationException(Lan.g(this,"Deductible not valid: ") + gridMain.getRows().get___idx(i).getCells()[6].Text);
                }
            
            }
             
            if (!StringSupport.equals(gridMain.getRows().get___idx(i).getCells()[7].Text, ""))
            {
                try
                {
                    //allowed
                    dbl = Convert.ToDouble(gridMain.getRows().get___idx(i).getCells()[7].Text);
                }
                catch (Exception __dummyCatchVar5)
                {
                    throw new ApplicationException(Lan.g(this,"Allowed amt not valid: ") + gridMain.getRows().get___idx(i).getCells()[7].Text);
                }
            
            }
             
            if (!StringSupport.equals(gridMain.getRows().get___idx(i).getCells()[8].Text, ""))
            {
                try
                {
                    //inspay
                    dbl = Convert.ToDouble(gridMain.getRows().get___idx(i).getCells()[8].Text);
                }
                catch (Exception __dummyCatchVar6)
                {
                    throw new ApplicationException(Lan.g(this,"Ins Pay not valid: ") + gridMain.getRows().get___idx(i).getCells()[8].Text);
                }
            
            }
             
            if (!StringSupport.equals(gridMain.getRows().get___idx(i).getCells()[9].Text, ""))
            {
                try
                {
                    //writeoff
                    dbl = Convert.ToDouble(gridMain.getRows().get___idx(i).getCells()[9].Text);
                    if (dbl < 0)
                    {
                        throw new ApplicationException(Lan.g(this,"Writeoff cannot be negative: ") + gridMain.getRows().get___idx(i).getCells()[9].Text);
                    }
                     
                }
                catch (Exception __dummyCatchVar7)
                {
                    throw new ApplicationException(Lan.g(this,"Writeoff not valid: ") + gridMain.getRows().get___idx(i).getCells()[9].Text);
                }
            
            }
             
        }
        for (int i = 0;i < ClaimProcsToEdit.Length;i++)
        {
            ClaimProcsToEdit[i].DedApplied = PIn.Double(gridMain.getRows().get___idx(i).getCells()[6].Text);
            if (StringSupport.equals(gridMain.getRows().get___idx(i).getCells()[7].Text, ""))
            {
                ClaimProcsToEdit[i].AllowedOverride = -1;
            }
            else
            {
                ClaimProcsToEdit[i].AllowedOverride = PIn.Double(gridMain.getRows().get___idx(i).getCells()[7].Text);
            } 
            ClaimProcsToEdit[i].InsPayAmt = PIn.Double(gridMain.getRows().get___idx(i).getCells()[8].Text);
            ClaimProcsToEdit[i].WriteOff = PIn.Double(gridMain.getRows().get___idx(i).getCells()[9].Text);
            ClaimProcsToEdit[i].Remarks = gridMain.getRows().get___idx(i).getCells()[12].Text;
        }
    }

    private void butDeductible_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedCell().X == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select one procedure.  Then click this button to assign the deductible to that procedure."));
            return ;
        }
         
        try
        {
            saveGridChanges();
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        Double dedAmt = 0;
        for (int i = 0;i < ClaimProcsToEdit.Length;i++)
        {
            //remove the existing deductible from each procedure and move it to dedAmt.
            if (ClaimProcsToEdit[i].DedApplied > 0)
            {
                dedAmt += ClaimProcsToEdit[i].DedApplied;
                ClaimProcsToEdit[i].InsPayEst += ClaimProcsToEdit[i].DedApplied;
                //dedAmt might be more
                ClaimProcsToEdit[i].InsPayAmt += ClaimProcsToEdit[i].DedApplied;
                ClaimProcsToEdit[i].DedApplied = 0;
            }
             
        }
        if (dedAmt == 0)
        {
            MessageBox.Show(Lan.g(this,"There does not seem to be a deductible to apply.  You can still apply a deductible manually by double clicking on a procedure."));
            return ;
        }
         
        //then move dedAmt to the selected proc
        ClaimProcsToEdit[gridMain.getSelectedCell().Y].DedApplied = dedAmt;
        ClaimProcsToEdit[gridMain.getSelectedCell().Y].InsPayEst -= dedAmt;
        ClaimProcsToEdit[gridMain.getSelectedCell().Y].InsPayAmt -= dedAmt;
        fillGrid();
    }

    private void butWriteOff_Click(Object sender, System.EventArgs e) throws Exception {
        if (MessageBox.Show(Lan.g(this,"Write off unpaid amount on each procedure?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        try
        {
            saveGridChanges();
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        //fix later: does not take into account other payments.
        double unpaidAmt = 0;
        List<Procedure> ProcList = Procedures.refresh(PatCur.PatNum);
        for (int i = 0;i < ClaimProcsToEdit.Length;i++)
        {
            //((Procedure)Procedures.HList[ClaimProcsToEdit[i].ProcNum]).ProcFee
            unpaidAmt = Procedures.GetProcFromList(ProcList, ClaimProcsToEdit[i].ProcNum).ProcFee - ClaimProcsToEdit[i].DedApplied - ClaimProcsToEdit[i].InsPayAmt;
            if (unpaidAmt > 0)
            {
                ClaimProcsToEdit[i].WriteOff = unpaidAmt;
            }
             
        }
        fillGrid();
    }

    private void saveAllowedFees() throws Exception {
        //if no allowed fees entered, then nothing to do
        boolean allowedFeesEntered = false;
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (!StringSupport.equals(gridMain.getRows().get___idx(i).getCells()[7].Text, ""))
            {
                allowedFeesEntered = true;
                break;
            }
             
        }
        if (!allowedFeesEntered)
        {
            return ;
        }
         
        //if no allowed fee schedule, then nothing to do
        InsPlan plan = InsPlans.GetPlan(ClaimProcsToEdit[0].PlanNum, PlanList);
        if (plan.AllowedFeeSched == 0)
        {
            return ;
        }
         
        //no allowed fee sched
        //plan.PlanType!="p" && //not ppo, and
        //ask user if they want to save the fees
        if (!MsgBox.show(this,true,"Save the allowed amounts to the allowed fee schedule?"))
        {
            return ;
        }
         
        //select the feeSchedule
        long feeSched = -1;
        //if(plan.PlanType=="p"){//ppo
        //	feeSched=plan.FeeSched;
        //}
        //else if(plan.AllowedFeeSched!=0){//an allowed fee schedule exists
        feeSched = plan.AllowedFeeSched;
        //}
        if (FeeScheds.getIsHidden(feeSched))
        {
            MsgBox.show(this,"Allowed fee schedule is hidden, so no changes can be made.");
            return ;
        }
         
        Fee FeeCur = null;
        long codeNum = new long();
        List<Procedure> ProcList = Procedures.refresh(PatCur.PatNum);
        Procedure proc;
        for (int i = 0;i < ClaimProcsToEdit.Length;i++)
        {
            //this gives error message if proc not found:
            proc = Procedures.GetProcFromList(ProcList, ClaimProcsToEdit[i].ProcNum);
            codeNum = proc.CodeNum;
            if (codeNum == 0)
            {
                continue;
            }
             
            FeeCur = Fees.getFee(codeNum,feeSched);
            if (FeeCur == null)
            {
                FeeCur = new Fee();
                FeeCur.FeeSched = feeSched;
                FeeCur.CodeNum = codeNum;
                FeeCur.Amount = PIn.Double(gridMain.getRows().get___idx(i).getCells()[7].Text);
                Fees.insert(FeeCur);
            }
            else
            {
                FeeCur.Amount = PIn.Double(gridMain.getRows().get___idx(i).getCells()[7].Text);
                Fees.update(FeeCur);
            } 
            SecurityLogs.MakeLogEntry(Permissions.ProcFeeEdit, 0, Lan.g(this,"Procedure") + ": " + ProcedureCodes.getStringProcCode(FeeCur.CodeNum) + ", " + Lan.g(this,"Fee: ") + "" + FeeCur.Amount.ToString("c") + ", " + Lan.g(this,"Fee Schedule") + " " + FeeScheds.getDescription(FeeCur.FeeSched) + ". " + Lan.g(this,"Automatic change to allowed fee in Enter Payment window.  Confirmed by user."), FeeCur.CodeNum);
        }
        //Fees.Refresh();//redundant?
        DataValid.setInvalid(InvalidType.Fees);
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        try
        {
            saveGridChanges();
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        saveAllowedFees();
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formClaimPayTotal_Activated(Object sender, EventArgs e) throws Exception {
    }

}


