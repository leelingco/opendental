//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:30 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormInsPlan;
import OpenDental.FormPlansForFamily;
import OpenDental.Lan;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.PatPlans;

/**
* Summary description for FormBasicTemplate.
*/
public class FormPlansForFamily  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private Label label1 = new Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * Set this externally.
    */
    public Family FamCur;
    private List<InsPlan> PlanList = new List<InsPlan>();
    private List<InsSub> SubList = new List<InsSub>();
    /**
    * 
    */
    public FormPlansForFamily() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPlansForFamily.class);
        this.butClose = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(475, 257);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(34, 57);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(516, 181);
        this.gridMain.TabIndex = 1;
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
        // label1
        //
        this.label1.Location = new System.Drawing.Point(31, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(462, 35);
        this.label1.TabIndex = 2;
        this.label1.Text = "This is a list of all insurance plans for the family.  The main purpose is to vie" + "w inactive plans that have been dropped.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // FormPlansForFamily
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(585, 300);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPlansForFamily";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Insurance Plans for Family";
        this.Load += new System.EventHandler(this.FormPlansForFamily_Load);
        this.ResumeLayout(false);
    }

    private void formPlansForFamily_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        SubList = InsSubs.refreshForFam(FamCur);
        PlanList = InsPlans.refreshForSubList(SubList);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        //=new ODGridColumn(Lan.g("TableInsPlans","#"),20);
        //gridMain.Columns.Add(col);
        col = new ODGridColumn(Lan.g("TableInsPlans","Subscriber"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableInsPlans","Ins Carrier"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableInsPlans","Date Effect."),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableInsPlans","Date Term."),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableInsPlans","Used By"),90);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        //PatPlan[] patPlanArray;
        InsPlan plan;
        for (int i = 0;i < SubList.Count;i++)
        {
            plan = InsPlans.GetPlan(SubList[i].PlanNum, PlanList);
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(FamCur.GetNameInFamLF(SubList[i].Subscriber));
            row.getCells().add(Carriers.getName(plan.CarrierNum));
            if (SubList[i].DateEffective.Year < 1880)
                row.getCells().add("");
            else
                row.getCells().Add(SubList[i].DateEffective.ToString("d")); 
            if (SubList[i].DateTerm.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(SubList[i].DateTerm.ToString("d"));
            } 
            int countPatPlans = PatPlans.GetCountBySubNum(SubList[i].InsSubNum);
            row.getCells().Add(countPatPlans.ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        InsPlan plan = InsPlans.GetPlan(SubList[e.getRow()].PlanNum, PlanList);
        FormInsPlan FormIP = new FormInsPlan(plan, null, SubList[e.getRow()]);
        FormIP.ShowDialog();
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

}


