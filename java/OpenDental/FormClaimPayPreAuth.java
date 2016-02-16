//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:50 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormClaimPayPreAuth;
import OpenDental.FormClaimProc;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;

/**
* Summary description for FormClaimPayTotal.
*/
public class FormClaimPayPreAuth  extends System.Windows.Forms.Form 
{
    private OpenDental.ValidDouble textTotal;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    /**
    * 
    */
    public List<ClaimProc> ClaimProcsToEdit = new List<ClaimProc>();
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
    public FormClaimPayPreAuth(Patient patCur, Family famCur, List<InsPlan> planList, List<PatPlan> patPlanList, List<InsSub> subList) throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimPayPreAuth.class);
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textTotal = new OpenDental.ValidDouble();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label1.Location = new System.Drawing.Point(184, 278);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(84, 16);
        this.label1.TabIndex = 117;
        this.label1.Text = "Total";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(8, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.OneCell);
        this.gridMain.Size = new System.Drawing.Size(661, 257);
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
        // textTotal
        //
        this.textTotal.Location = new System.Drawing.Point(274, 275);
        this.textTotal.Name = "textTotal";
        this.textTotal.ReadOnly = true;
        this.textTotal.Size = new System.Drawing.Size(55, 20);
        this.textTotal.TabIndex = 119;
        this.textTotal.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(594, 331);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(513, 331);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormClaimPayPreAuth
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(686, 366);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.textTotal);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClaimPayPreAuth";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Enter Estimates for PreAuth";
        this.Load += new System.EventHandler(this.FormClaimPayPreAuth_Load);
        this.Shown += new System.EventHandler(this.FormClaimPayTotal_Shown);
        this.Activated += new System.EventHandler(this.FormClaimPayTotal_Activated);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formClaimPayPreAuth_Load(Object sender, System.EventArgs e) throws Exception {
        ProcList = Procedures.refresh(PatCur.PatNum);
        fillGrid();
    }

    private void formClaimPayTotal_Shown(Object sender, EventArgs e) throws Exception {
        InsPlan plan = InsPlans.GetPlan(ClaimProcsToEdit[0].PlanNum, PlanList);
        gridMain.setSelected(new Point(4, 0));
    }

    private void fillGrid() throws Exception {
        //Changes made in this window do not get saved until after this window closes.
        //But if you double click on a row, then you will end up saving.  That shouldn't hurt anything, but could be improved.
        //also calculates totals for this "payment"
        //the payment itself is imaginary and is simply the sum of the claimprocs on this form
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g(this,"Code"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Tth"),35);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Description"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Fee"), 55, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Estimate"), 55, HorizontalAlignment.Right, true);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Remarks"),170,true);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        Procedure ProcCur;
        for (int i = 0;i < ClaimProcsToEdit.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            ProcCur = Procedures.GetProcFromList(ProcList, ClaimProcsToEdit[i].ProcNum);
            row.getCells().add(ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode);
            row.getCells().add(ProcCur.ToothNum);
            row.getCells().add(ProcedureCodes.getProcCode(ProcCur.CodeNum).Descript);
            row.getCells().Add(ClaimProcsToEdit[i].FeeBilled.ToString("F"));
            row.getCells().Add(ClaimProcsToEdit[i].InsPayEst.ToString("F"));
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
        double insPayEst = 0;
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            try
            {
                insPayEst += Convert.ToDouble(gridMain.getRows().get___idx(i).getCells()[4].Text);
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
        textTotal.Text = insPayEst.ToString("F");
    }

    /**
    * Surround with try-catch.
    */
    private void saveGridChanges() throws Exception {
        //validate all grid cells
        double dbl = new double();
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (!StringSupport.equals(gridMain.getRows().get___idx(i).getCells()[4].Text, ""))
            {
                try
                {
                    dbl = Convert.ToDouble(gridMain.getRows().get___idx(i).getCells()[4].Text);
                }
                catch (Exception __dummyCatchVar1)
                {
                    throw new ApplicationException(Lan.g(this,"Amount not valid: ") + gridMain.getRows().get___idx(i).getCells()[4].Text);
                }
            
            }
             
        }
        for (int i = 0;i < ClaimProcsToEdit.Count;i++)
        {
            ClaimProcsToEdit[i].InsPayEst = PIn.Double(gridMain.getRows().get___idx(i).getCells()[4].Text);
            ClaimProcsToEdit[i].Remarks = gridMain.getRows().get___idx(i).getCells()[5].Text;
        }
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

        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formClaimPayTotal_Activated(Object sender, EventArgs e) throws Exception {
    }

}


