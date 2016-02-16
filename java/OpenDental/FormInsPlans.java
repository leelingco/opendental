//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:16 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormInsPlan;
import OpenDental.FormInsPlans;
import OpenDental.FormInsPlansMerge;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormInsPlans  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    //private InsTemplates InsTemplates;
    private OpenDental.UI.Button butBlank;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioOrderCarrier = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioOrderEmp = new System.Windows.Forms.RadioButton();
    //<summary>Set to true if we are only using the list to select a template to link to rather than creating a new plan. If this is true, then IsSelectMode will be ignored.</summary>
    //public bool IsLinkMode;
    /**
    * Set to true when selecting a plan for a patient and we want SelectedPlan to be filled upon closing.
    */
    public boolean IsSelectMode = new boolean();
    /**
    * After closing this form, if IsSelectMode, then this will contain the selected Plan.
    */
    public InsPlan SelectedPlan;
    private Label label1 = new Label();
    private TextBox textEmployer = new TextBox();
    private TextBox textCarrier = new TextBox();
    private Label label2 = new Label();
    private OpenDental.UI.ODGrid gridMain;
    //private InsPlan[] ListAll;
    /**
    * Supply a string here to start off the search with filtered employers.
    */
    public String empText = new String();
    private TextBox textGroupNum = new TextBox();
    private Label label3 = new Label();
    private TextBox textGroupName = new TextBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butMerge;
    /**
    * Supply a string here to start off the search with filtered carriers.
    */
    public String carrierText = new String();
    private TextBox textTrojanID = new TextBox();
    private Label labelTrojanID = new Label();
    private DataTable table = new DataTable();
    private CheckBox checkShowHidden = new CheckBox();
    private OpenDental.UI.Button butHide;
    private boolean trojan = new boolean();
    /**
    * Supply a string here to start off the search with filtered group names.
    */
    public String groupNameText = new String();
    /**
    * Supply a string here to start off the search with filtered group nums.
    */
    public String groupNumText = new String();
    /**
    * 
    */
    public FormInsPlans() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsPlans.class);
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.radioOrderCarrier = new System.Windows.Forms.RadioButton();
        this.radioOrderEmp = new System.Windows.Forms.RadioButton();
        this.label1 = new System.Windows.Forms.Label();
        this.textEmployer = new System.Windows.Forms.TextBox();
        this.textCarrier = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textGroupNum = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textGroupName = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textTrojanID = new System.Windows.Forms.TextBox();
        this.labelTrojanID = new System.Windows.Forms.Label();
        this.checkShowHidden = new System.Windows.Forms.CheckBox();
        this.butMerge = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butBlank = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butHide = new OpenDental.UI.Button();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.radioOrderCarrier);
        this.groupBox2.Controls.Add(this.radioOrderEmp);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(740, 3);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(207, 33);
        this.groupBox2.TabIndex = 2;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Order By";
        //
        // radioOrderCarrier
        //
        this.radioOrderCarrier.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioOrderCarrier.Location = new System.Drawing.Point(98, 13);
        this.radioOrderCarrier.Name = "radioOrderCarrier";
        this.radioOrderCarrier.Size = new System.Drawing.Size(84, 16);
        this.radioOrderCarrier.TabIndex = 1;
        this.radioOrderCarrier.Text = "Carrier";
        this.radioOrderCarrier.Click += new System.EventHandler(this.radioOrderCarrier_Click);
        //
        // radioOrderEmp
        //
        this.radioOrderEmp.Checked = true;
        this.radioOrderEmp.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioOrderEmp.Location = new System.Drawing.Point(9, 13);
        this.radioOrderEmp.Name = "radioOrderEmp";
        this.radioOrderEmp.Size = new System.Drawing.Size(83, 16);
        this.radioOrderEmp.TabIndex = 0;
        this.radioOrderEmp.TabStop = true;
        this.radioOrderEmp.Text = "Employer";
        this.radioOrderEmp.Click += new System.EventHandler(this.radioOrderEmp_Click);
        //
        // label1
        //
        this.label1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label1.Location = new System.Drawing.Point(7, 7);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 17);
        this.label1.TabIndex = 15;
        this.label1.Text = "Employer";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textEmployer
        //
        this.textEmployer.Location = new System.Drawing.Point(113, 4);
        this.textEmployer.Name = "textEmployer";
        this.textEmployer.Size = new System.Drawing.Size(140, 20);
        this.textEmployer.TabIndex = 1;
        this.textEmployer.TextChanged += new System.EventHandler(this.textEmployer_TextChanged);
        //
        // textCarrier
        //
        this.textCarrier.Location = new System.Drawing.Point(113, 25);
        this.textCarrier.Name = "textCarrier";
        this.textCarrier.Size = new System.Drawing.Size(140, 20);
        this.textCarrier.TabIndex = 0;
        this.textCarrier.TextChanged += new System.EventHandler(this.textCarrier_TextChanged);
        //
        // label2
        //
        this.label2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label2.Location = new System.Drawing.Point(7, 28);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 17);
        this.label2.TabIndex = 17;
        this.label2.Text = "Carrier";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textGroupNum
        //
        this.textGroupNum.Location = new System.Drawing.Point(372, 25);
        this.textGroupNum.Name = "textGroupNum";
        this.textGroupNum.Size = new System.Drawing.Size(140, 20);
        this.textGroupNum.TabIndex = 20;
        this.textGroupNum.TextChanged += new System.EventHandler(this.textGroupNum_TextChanged);
        //
        // label3
        //
        this.label3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label3.Location = new System.Drawing.Point(266, 28);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 17);
        this.label3.TabIndex = 23;
        this.label3.Text = "Group Num";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textGroupName
        //
        this.textGroupName.Location = new System.Drawing.Point(372, 4);
        this.textGroupName.Name = "textGroupName";
        this.textGroupName.Size = new System.Drawing.Size(140, 20);
        this.textGroupName.TabIndex = 21;
        this.textGroupName.TextChanged += new System.EventHandler(this.textGroupName_TextChanged);
        //
        // label4
        //
        this.label4.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label4.Location = new System.Drawing.Point(266, 7);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(100, 17);
        this.label4.TabIndex = 22;
        this.label4.Text = "Group Name";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTrojanID
        //
        this.textTrojanID.Location = new System.Drawing.Point(616, 4);
        this.textTrojanID.Name = "textTrojanID";
        this.textTrojanID.Size = new System.Drawing.Size(95, 20);
        this.textTrojanID.TabIndex = 25;
        this.textTrojanID.TextChanged += new System.EventHandler(this.textTrojanID_TextChanged);
        //
        // labelTrojanID
        //
        this.labelTrojanID.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.labelTrojanID.Location = new System.Drawing.Point(536, 7);
        this.labelTrojanID.Name = "labelTrojanID";
        this.labelTrojanID.Size = new System.Drawing.Size(74, 17);
        this.labelTrojanID.TabIndex = 26;
        this.labelTrojanID.Text = "Trojan ID";
        this.labelTrojanID.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkShowHidden
        //
        this.checkShowHidden.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShowHidden.Location = new System.Drawing.Point(526, 28);
        this.checkShowHidden.Name = "checkShowHidden";
        this.checkShowHidden.Size = new System.Drawing.Size(104, 20);
        this.checkShowHidden.TabIndex = 27;
        this.checkShowHidden.Text = "Show Hidden";
        this.checkShowHidden.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShowHidden.UseVisualStyleBackColor = true;
        this.checkShowHidden.CheckedChanged += new System.EventHandler(this.checkShowHidden_CheckedChanged);
        //
        // butMerge
        //
        this.butMerge.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMerge.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butMerge.setAutosize(true);
        this.butMerge.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMerge.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMerge.setCornerRadius(4F);
        this.butMerge.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butMerge.Location = new System.Drawing.Point(11, 637);
        this.butMerge.Name = "butMerge";
        this.butMerge.Size = new System.Drawing.Size(74, 24);
        this.butMerge.TabIndex = 24;
        this.butMerge.Text = "Combine";
        this.butMerge.Click += new System.EventHandler(this.butMerge_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(11, 51);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(936, 579);
        this.gridMain.TabIndex = 19;
        this.gridMain.setTitle("Insurance Plans");
        this.gridMain.setTranslationName("TableInsurancePlans");
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
        this.butOK.Location = new System.Drawing.Point(774, 637);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(78, 24);
        this.butOK.TabIndex = 4;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butBlank
        //
        this.butBlank.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBlank.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butBlank.setAutosize(true);
        this.butBlank.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBlank.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBlank.setCornerRadius(4F);
        this.butBlank.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butBlank.Location = new System.Drawing.Point(427, 637);
        this.butBlank.Name = "butBlank";
        this.butBlank.Size = new System.Drawing.Size(87, 24);
        this.butBlank.TabIndex = 3;
        this.butBlank.Text = "Blank Plan";
        this.butBlank.Visible = false;
        this.butBlank.Click += new System.EventHandler(this.butBlank_Click);
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
        this.butCancel.Location = new System.Drawing.Point(869, 637);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(78, 24);
        this.butCancel.TabIndex = 5;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butHide
        //
        this.butHide.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butHide.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butHide.setAutosize(true);
        this.butHide.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butHide.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butHide.setCornerRadius(4F);
        this.butHide.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butHide.Location = new System.Drawing.Point(104, 637);
        this.butHide.Name = "butHide";
        this.butHide.Size = new System.Drawing.Size(84, 24);
        this.butHide.TabIndex = 28;
        this.butHide.Text = "Hide Unused";
        this.butHide.Click += new System.EventHandler(this.butHide_Click);
        //
        // FormInsPlans
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(962, 669);
        this.Controls.Add(this.butHide);
        this.Controls.Add(this.checkShowHidden);
        this.Controls.Add(this.textTrojanID);
        this.Controls.Add(this.labelTrojanID);
        this.Controls.Add(this.butMerge);
        this.Controls.Add(this.textGroupNum);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textGroupName);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.textCarrier);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textEmployer);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butBlank);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormInsPlans";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Insurance Plans";
        this.Load += new System.EventHandler(this.FormInsTemplates_Load);
        this.groupBox2.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formInsTemplates_Load(Object sender, System.EventArgs e) throws Exception {
        if (!IsSelectMode)
        {
            butCancel.Text = Lan.g(this,"Close");
            butOK.Visible = false;
        }
         
        Program prog = Programs.getCur(ProgramName.Trojan);
        if (prog != null && prog.Enabled)
        {
            trojan = true;
        }
        else
        {
            labelTrojanID.Visible = false;
            textTrojanID.Visible = false;
        } 
        textEmployer.Text = empText;
        textCarrier.Text = carrierText;
        textGroupName.Text = groupNameText;
        textGroupNum.Text = groupNumText;
        fillGrid();
    }

    private void fillGrid() throws Exception {
        Cursor = Cursors.WaitCursor;
        table = InsPlans.GetBigList(radioOrderEmp.Checked, textEmployer.Text, textCarrier.Text, textGroupName.Text, textGroupNum.Text, textTrojanID.Text, checkShowHidden.Checked);
        if (IsSelectMode)
        {
            butBlank.Visible = true;
        }
         
        int selectedRow = new int();
        //preserves the selected row.
        if (gridMain.getSelectedIndices().Length == 1)
        {
            selectedRow = gridMain.getSelectedIndices()[0];
        }
        else
        {
            selectedRow = -1;
        } 
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lans.g("TableInsurancePlans","Employer"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableInsurancePlans","Carrier"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableInsurancePlans","Phone"),82);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableInsurancePlans","Address"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableInsurancePlans","City"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableInsurancePlans","ST"),25);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableInsurancePlans","Zip"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableInsurancePlans","Group#"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableInsurancePlans","Group Name"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableInsurancePlans","noE"),35);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableInsurancePlans","ElectID"),45);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableInsurancePlans","Subs"),40);
        gridMain.getColumns().add(col);
        if (trojan)
        {
            col = new ODGridColumn(Lans.g("TableInsurancePlans","TrojanID"),60);
            gridMain.getColumns().add(col);
        }
         
        //PlanNote not shown
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //Carrier carrier;
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["EmpName"].ToString());
            row.getCells().Add(table.Rows[i]["CarrierName"].ToString());
            row.getCells().Add(table.Rows[i]["Phone"].ToString());
            row.getCells().Add(table.Rows[i]["Address"].ToString());
            row.getCells().Add(table.Rows[i]["City"].ToString());
            row.getCells().Add(table.Rows[i]["State"].ToString());
            row.getCells().Add(table.Rows[i]["Zip"].ToString());
            row.getCells().Add(table.Rows[i]["GroupNum"].ToString());
            row.getCells().Add(table.Rows[i]["GroupName"].ToString());
            row.getCells().Add(table.Rows[i]["noSendElect"].ToString());
            row.getCells().Add(table.Rows[i]["ElectID"].ToString());
            row.getCells().Add(table.Rows[i]["subscribers"].ToString());
            if (trojan)
            {
                row.getCells().Add(table.Rows[i]["TrojanID"].ToString());
            }
             
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setSelected(selectedRow,true);
        Cursor = Cursors.Default;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        InsPlan plan = InsPlans.GetPlan(PIn.Long(table.Rows[e.getRow()]["PlanNum"].ToString()), null);
        if (IsSelectMode)
        {
            SelectedPlan = plan.copy();
            DialogResult = DialogResult.OK;
            return ;
        }
         
        FormInsPlan FormIP = new FormInsPlan(plan,null,null);
        FormIP.ShowDialog();
        if (FormIP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    private void radioOrderEmp_Click(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void radioOrderCarrier_Click(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void textEmployer_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textCarrier_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textGroupName_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textGroupNum_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textTrojanID_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void checkShowHidden_CheckedChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butMerge_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length < 2)
        {
            MessageBox.Show(Lan.g(this,"Please select at least two items first."));
            return ;
        }
         
        InsPlan[] listSelected = new InsPlan[gridMain.getSelectedIndices().Length];
        for (int i = 0;i < listSelected.Length;i++)
        {
            listSelected[i] = InsPlans.GetPlan(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["PlanNum"].ToString()), null);
            listSelected[i].NumberSubscribers = PIn.Int(table.Rows[gridMain.getSelectedIndices()[i]]["subscribers"].ToString());
        }
        FormInsPlansMerge FormI = new FormInsPlansMerge();
        FormI.ListAll = listSelected;
        FormI.ShowDialog();
        if (FormI.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //Do the merge.
        InsPlan planToMergeTo = FormI.PlanToMergeTo.copy();
        //List<Benefit> benList=Benefits.RefreshForPlan(planToMergeTo,0);
        Cursor = Cursors.WaitCursor;
        for (int i = 0;i < listSelected.Length;i++)
        {
            //loop through each selected plan
            //skip the planToMergeTo, because it's already correct
            if (planToMergeTo.PlanNum == listSelected[i].PlanNum)
            {
                continue;
            }
             
            //==Michael - We are changing plans here, but not carriers, so this is not needed:
            //SecurityLogs.MakeLogEntry(Permissions.InsPlanChangeCarrierName
            InsPlans.ChangeReferences(listSelected[i].PlanNum, planToMergeTo.PlanNum);
            Benefits.DeleteForPlan(listSelected[i].PlanNum);
            InsPlans.Delete(listSelected[i].PlanNum);
        }
        //for(int j=0;j<planNums.Count;j++) {
        //InsPlans.ComputeEstimatesForPlan(planNums[j]);
        //Eliminated in 5.0 for speed.
        //}
        fillGrid();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            for (int j = 0;j < listSelected.Length;j++)
            {
                //highlight the merged plan
                if (table.Rows[i]["PlanNum"].ToString() == listSelected[j].PlanNum.ToString())
                {
                    gridMain.setSelected(i,true);
                }
                 
            }
        }
        Cursor = Cursors.Default;
    }

    private void butBlank_Click(Object sender, System.EventArgs e) throws Exception {
        //this button is normally not visible.  It's only set visible when IsSelectMode.
        SelectedPlan = new InsPlan();
        DialogResult = DialogResult.OK;
    }

    private void butHide_Click(Object sender, EventArgs e) throws Exception {
        int unusedCount = InsPlans.unusedGetCount();
        String msgText = unusedCount.ToString() + " " + Lan.g(this,"plans found that are not in use by any subscribers.  Hide all of them?");
        if (MessageBox.Show(msgText, "", MessageBoxButtons.YesNo) != DialogResult.Yes)
        {
            return ;
        }
         
        InsPlans.unusedHideAll();
        fillGrid();
        MsgBox.show(this,"Done.");
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //only visible if IsSelectMode
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length > 1)
        {
            MessageBox.Show(Lan.g(this,"Please select only one item first."));
            return ;
        }
         
        SelectedPlan = InsPlans.GetPlan(PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["PlanNum"].ToString()), null).Copy();
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


