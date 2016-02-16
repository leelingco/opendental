//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:16 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormInsPlan;
import OpenDental.FormInsPlansMerge;
import OpenDental.Lan;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Employers;
import OpenDentBusiness.InsPlan;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormInsPlansMerge  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * After closing this form, if OK, then this will contain the Plan that the others will be merged into.
    */
    public InsPlan PlanToMergeTo;
    private OpenDental.UI.ODGrid gridMain;
    /**
    * This list must be set before loading the form.  All of the PlanNums will be 0.
    */
    public InsPlan[] ListAll = new InsPlan[]();
    private Label label1 = new Label();
    /**
    * 
    */
    public FormInsPlansMerge() throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsPlansMerge.class);
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(935, 27);
        this.label1.TabIndex = 20;
        this.label1.Text = "Please select one plan from the list and click OK.  All the other plans will be m" + "ade identical to the plan you select.  You can double click on a plan to view pe" + "rcentages, etc.";
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(11, 39);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(936, 591);
        this.gridMain.TabIndex = 19;
        this.gridMain.setTitle("Insurance Plans");
        this.gridMain.setTranslationName("TableTemplates");
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
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(776, 636);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(78, 26);
        this.butOK.TabIndex = 4;
        this.butOK.Text = "OK";
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
        this.butCancel.Location = new System.Drawing.Point(871, 636);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(77, 26);
        this.butCancel.TabIndex = 5;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormInsPlansMerge
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(962, 669);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormInsPlansMerge";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Combine Insurance Plans";
        this.Load += new System.EventHandler(this.FormInsPlansMerge_Load);
        this.ResumeLayout(false);
    }

    private void formInsPlansMerge_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    /**
    * 
    */
    private void fillGrid() throws Exception {
        Cursor = Cursors.WaitCursor;
        //ListAll: Set externally before loading.
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Employer",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Carrier",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Phone",82);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Address",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("City",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("ST",25);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Zip",50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Group#",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Group Name",90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Subs",40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Plan Note",180);
        gridMain.getColumns().add(col);
        //TrojanID and PlanNote not shown
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        Carrier carrier;
        for (int i = 0;i < ListAll.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Employers.GetName(ListAll[i].EmployerNum));
            carrier = Carriers.GetCarrier(ListAll[i].CarrierNum);
            row.getCells().add(carrier.CarrierName);
            row.getCells().add(carrier.Phone);
            row.getCells().add(carrier.Address);
            row.getCells().add(carrier.City);
            row.getCells().add(carrier.State);
            row.getCells().add(carrier.Zip);
            row.getCells().Add(ListAll[i].GroupNum);
            row.getCells().Add(ListAll[i].GroupName);
            row.getCells().Add(ListAll[i].NumberSubscribers.ToString());
            row.getCells().Add(ListAll[i].PlanNote);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setSelected(0,true);
        Cursor = Cursors.Default;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        InsPlan PlanCur = ListAll[e.getRow()].Copy();
        FormInsPlan FormIP = new FormInsPlan(PlanCur,null,null);
        //FormIP.IsForAll=true;
        //FormIP.IsReadOnly=true;
        FormIP.ShowDialog();
        if (FormIP.DialogResult == DialogResult.OK)
        {
            fillGrid();
        }
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        PlanToMergeTo = ListAll[gridMain.getSelectedIndex()].Copy();
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


