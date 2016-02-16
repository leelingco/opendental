//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:46 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormClaimCreate;
import OpenDental.Lan;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Relat;

/**
* Lists all insurance plans for which the supplied patient is the subscriber. Lets you select an insurance plan based on a patNum. SelectedPlan will contain the plan selected.
*/
public class FormClaimCreate  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.ListBox listRelat = new System.Windows.Forms.ListBox();
    //private OpenDental.TableInsPlans tbPlans;
    /**
    * 
    */
    public Relat PatRelat = Relat.Self;
    private System.Windows.Forms.Label labelRelat = new System.Windows.Forms.Label();
    //<summary>Set to true to view the relationship selection</summary>
    //public bool ViewRelat;
    private Patient PatCur;
    private Family FamCur;
    /**
    * After closing this form, this will contain the selected plan.
    */
    public InsPlan SelectedPlan;
    public InsSub SelectedSub;
    private List<InsPlan> PlanList = new List<InsPlan>();
    private OpenDental.UI.ODGrid gridMain;
    private long PatNum = new long();
    //public long ClaimFormNum;
    private List<InsSub> SubList = new List<InsSub>();
    /**
    * 
    */
    public FormClaimCreate(long patNum) throws Exception {
        initializeComponent();
        PatNum = patNum;
        //tbPlans.CellDoubleClicked += new OpenDental.ContrTable.CellEventHandler(tbPlans_CellDoubleClicked);
        Lan.f(this);
    }

    /**
    * 
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimCreate.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.labelRelat = new System.Windows.Forms.Label();
        this.listRelat = new System.Windows.Forms.ListBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(618, 275);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(76, 26);
        this.butCancel.TabIndex = 6;
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
        this.butOK.Location = new System.Drawing.Point(525, 275);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(76, 26);
        this.butOK.TabIndex = 5;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // labelRelat
        //
        this.labelRelat.Location = new System.Drawing.Point(512, 16);
        this.labelRelat.Name = "labelRelat";
        this.labelRelat.Size = new System.Drawing.Size(206, 20);
        this.labelRelat.TabIndex = 8;
        this.labelRelat.Text = "Relationship to Subscriber";
        //
        // listRelat
        //
        this.listRelat.Location = new System.Drawing.Point(514, 38);
        this.listRelat.Name = "listRelat";
        this.listRelat.Size = new System.Drawing.Size(180, 186);
        this.listRelat.TabIndex = 9;
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(22, 38);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(459, 186);
        this.gridMain.TabIndex = 10;
        this.gridMain.setTitle("Insurance Plans for Family");
        this.gridMain.setTranslationName("TableInsPlans");
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
        //
        // FormClaimCreate
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(724, 319);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.listRelat);
        this.Controls.Add(this.labelRelat);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClaimCreate";
        this.RightToLeft = System.Windows.Forms.RightToLeft.No;
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Create New Claim";
        this.Load += new System.EventHandler(this.FormClaimCreate_Load);
        this.ResumeLayout(false);
    }

    private void formClaimCreate_Load(Object sender, System.EventArgs e) throws Exception {
        //usage: eg. from coverage.  Since can be totally new subscriber, get all plans for them.
        FamCur = Patients.getFamily(PatNum);
        PatCur = FamCur.getPatient(PatNum);
        SubList = InsSubs.refreshForFam(FamCur);
        PlanList = InsPlans.refreshForSubList(SubList);
        fillPlanData();
    }

    //FillClaimForms();
    /*
    		private void FillClaimForms(){
    			for(int i=0;i<ClaimForms.ListShort.Length;i++) {
    				comboClaimForm.Items.Add(ClaimForms.ListShort[i].Description);
    				if(ClaimForms.ListShort[i].ClaimFormNum==PrefC.GetLong(PrefName.DefaultClaimForm)) {
    					comboClaimForm.SelectedIndex=i;
    				}
    			}
    		}*/
    private void fillPlanData() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableInsPlans","#"),20);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableInsPlans","Subscriber"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableInsPlans","Ins Carrier"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableInsPlans","Date Effect."),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableInsPlans","Date Term."),90);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < SubList.Count;i++)
        {
            InsPlan plan = InsPlans.GetPlan(SubList[i].PlanNum, PlanList);
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add((i + 1).ToString());
            row.getCells().Add(FamCur.GetNameInFamLF(SubList[i].Subscriber));
            row.getCells().add(Carriers.getName(plan.CarrierNum));
            if (SubList[i].DateEffective.Year < 1880)
                row.getCells().add("");
            else
                row.getCells().Add(SubList[i].DateEffective.ToString("d")); 
            if (SubList[i].DateTerm.Year < 1880)
                row.getCells().add("");
            else
                row.getCells().Add(SubList[i].DateTerm.ToString("d")); 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        listRelat.Items.Clear();
        for (int i = 0;i < Enum.GetNames(Relat.class).Length;i++)
        {
            listRelat.Items.Add(Lan.g("enumRelat", Enum.GetNames(Relat.class)[i]));
        }
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (listRelat.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a relationship first."));
            return ;
        }
         
        PatRelat = (Relat)listRelat.SelectedIndex;
        SelectedSub = SubList[e.getRow()];
        SelectedPlan = InsPlans.GetPlan(SubList[e.getRow()].PlanNum, PlanList);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a plan first."));
            return ;
        }
         
        if (listRelat.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a relationship first."));
            return ;
        }
         
        //if(comboClaimForm.SelectedIndex==-1) {
        //	MessageBox.Show(Lan.g(this,"Please select a claimform first."));
        //	return;
        //}
        PatRelat = (Relat)listRelat.SelectedIndex;
        SelectedSub = SubList[gridMain.getSelectedIndex()];
        SelectedPlan = InsPlans.GetPlan(SubList[gridMain.getSelectedIndex()].PlanNum, PlanList);
        //ClaimFormNum=ClaimForms.ListShort[comboClaimForm.SelectedIndex].ClaimFormNum;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


//cancel already handled