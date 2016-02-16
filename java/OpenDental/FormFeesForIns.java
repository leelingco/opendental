//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:10 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormFeesForIns;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.FeeSched;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.FeeScheduleType;
import OpenDentBusiness.InsPlans;

/**
* Summary description for FormBasicTemplate.
*/
public class FormFeesForIns  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.ODGrid gridMain;
    private TextBox textCarrier = new TextBox();
    private Label label2 = new Label();
    private Label label1 = new Label();
    private ComboBox comboFeeSchedWithout = new ComboBox();
    private Label label3 = new Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private List<FeeSched> FeeSchedsForType = new List<FeeSched>();
    private ComboBox comboFeeSchedWith = new ComboBox();
    private Label label4 = new Label();
    private ComboBox comboFeeSchedNew = new ComboBox();
    private Label label5 = new Label();
    private TextBox textCarrierNot = new TextBox();
    private Label label6 = new Label();
    private ListBox listType = new ListBox();
    private OpenDental.UI.Button butSelectAll;
    private DataTable table = new DataTable();
    /**
    * 
    */
    public FormFeesForIns() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormFeesForIns.class);
        this.textCarrier = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.comboFeeSchedWithout = new System.Windows.Forms.ComboBox();
        this.label3 = new System.Windows.Forms.Label();
        this.comboFeeSchedWith = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.comboFeeSchedNew = new System.Windows.Forms.ComboBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textCarrierNot = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.listType = new System.Windows.Forms.ListBox();
        this.butSelectAll = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textCarrier
        //
        this.textCarrier.Location = new System.Drawing.Point(235, 26);
        this.textCarrier.Name = "textCarrier";
        this.textCarrier.Size = new System.Drawing.Size(180, 20);
        this.textCarrier.TabIndex = 0;
        this.textCarrier.TextChanged += new System.EventHandler(this.textCarrier_TextChanged);
        //
        // label2
        //
        this.label2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label2.Location = new System.Drawing.Point(136, 29);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(93, 17);
        this.label2.TabIndex = 19;
        this.label2.Text = "Carrier Like";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label1.Location = new System.Drawing.Point(416, 51);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(128, 17);
        this.label1.TabIndex = 20;
        this.label1.Text = "Without Fee Schedule";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboFeeSchedWithout
        //
        this.comboFeeSchedWithout.FormattingEnabled = true;
        this.comboFeeSchedWithout.Location = new System.Drawing.Point(550, 47);
        this.comboFeeSchedWithout.MaxDropDownItems = 40;
        this.comboFeeSchedWithout.Name = "comboFeeSchedWithout";
        this.comboFeeSchedWithout.Size = new System.Drawing.Size(228, 21);
        this.comboFeeSchedWithout.TabIndex = 1;
        this.comboFeeSchedWithout.SelectionChangeCommitted += new System.EventHandler(this.comboFeeSchedWithout_SelectionChangeCommitted);
        //
        // label3
        //
        this.label3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label3.Location = new System.Drawing.Point(13, 4);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(432, 15);
        this.label3.TabIndex = 22;
        this.label3.Text = "You are searching for Insurance Plans that might have the wrong fee schedule atta" + "ched.";
        //
        // comboFeeSchedWith
        //
        this.comboFeeSchedWith.FormattingEnabled = true;
        this.comboFeeSchedWith.Location = new System.Drawing.Point(550, 25);
        this.comboFeeSchedWith.MaxDropDownItems = 40;
        this.comboFeeSchedWith.Name = "comboFeeSchedWith";
        this.comboFeeSchedWith.Size = new System.Drawing.Size(228, 21);
        this.comboFeeSchedWith.TabIndex = 23;
        this.comboFeeSchedWith.SelectionChangeCommitted += new System.EventHandler(this.comboFeeSchedWith_SelectionChangeCommitted);
        //
        // label4
        //
        this.label4.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label4.Location = new System.Drawing.Point(416, 29);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(128, 17);
        this.label4.TabIndex = 24;
        this.label4.Text = "With Fee Schedule";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboFeeSchedNew
        //
        this.comboFeeSchedNew.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.comboFeeSchedNew.FormattingEnabled = true;
        this.comboFeeSchedNew.Location = new System.Drawing.Point(370, 633);
        this.comboFeeSchedNew.MaxDropDownItems = 40;
        this.comboFeeSchedNew.Name = "comboFeeSchedNew";
        this.comboFeeSchedNew.Size = new System.Drawing.Size(228, 21);
        this.comboFeeSchedNew.TabIndex = 25;
        //
        // label5
        //
        this.label5.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label5.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label5.Location = new System.Drawing.Point(215, 637);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(149, 17);
        this.label5.TabIndex = 26;
        this.label5.Text = "New Fee Schedule";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCarrierNot
        //
        this.textCarrierNot.Location = new System.Drawing.Point(235, 47);
        this.textCarrierNot.Name = "textCarrierNot";
        this.textCarrierNot.Size = new System.Drawing.Size(180, 20);
        this.textCarrierNot.TabIndex = 27;
        this.textCarrierNot.TextChanged += new System.EventHandler(this.textCarrierNot_TextChanged);
        //
        // label6
        //
        this.label6.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label6.Location = new System.Drawing.Point(136, 50);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(93, 17);
        this.label6.TabIndex = 28;
        this.label6.Text = "Carrier Not Like";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listType
        //
        this.listType.FormattingEnabled = true;
        this.listType.Location = new System.Drawing.Point(13, 25);
        this.listType.Name = "listType";
        this.listType.Size = new System.Drawing.Size(120, 43);
        this.listType.TabIndex = 29;
        this.listType.Click += new System.EventHandler(this.listType_Click);
        //
        // butSelectAll
        //
        this.butSelectAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSelectAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSelectAll.setAutosize(true);
        this.butSelectAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSelectAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSelectAll.setCornerRadius(4F);
        this.butSelectAll.Location = new System.Drawing.Point(12, 630);
        this.butSelectAll.Name = "butSelectAll";
        this.butSelectAll.Size = new System.Drawing.Size(75, 24);
        this.butSelectAll.TabIndex = 30;
        this.butSelectAll.Text = "Select All";
        this.butSelectAll.Click += new System.EventHandler(this.butSelectAll_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(13, 72);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(732, 552);
        this.gridMain.TabIndex = 2;
        this.gridMain.setTitle("Ins Plans that might need to be changed");
        this.gridMain.setTranslationName(null);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(614, 631);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 2;
        this.butOK.Text = "Change";
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
        this.butCancel.Location = new System.Drawing.Point(737, 631);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormFeesForIns
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(824, 668);
        this.Controls.Add(this.butSelectAll);
        this.Controls.Add(this.listType);
        this.Controls.Add(this.textCarrierNot);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.comboFeeSchedNew);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.comboFeeSchedWith);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.comboFeeSchedWithout);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textCarrier);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.KeyPreview = true;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormFeesForIns";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Check Insurance Plan Fees";
        this.Load += new System.EventHandler(this.FormFeesForIns_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formFeesForIns_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < Enum.GetNames(FeeScheduleType.class).Length;i++)
        {
            listType.Items.Add((FeeScheduleType.values()[i]).ToString());
        }
        listType.SelectedIndex = 0;
        resetSelections();
        fillGrid();
    }

    private void resetSelections() throws Exception {
        comboFeeSchedWithout.Items.Clear();
        comboFeeSchedWith.Items.Clear();
        comboFeeSchedNew.Items.Clear();
        comboFeeSchedWithout.Items.Add(Lan.g(this,"none"));
        comboFeeSchedWith.Items.Add(Lan.g(this,"none"));
        comboFeeSchedNew.Items.Add(Lan.g(this,"none"));
        comboFeeSchedWithout.SelectedIndex = 0;
        comboFeeSchedWith.SelectedIndex = 0;
        comboFeeSchedNew.SelectedIndex = 0;
        FeeSchedsForType = FeeScheds.getListForType((FeeScheduleType)(listType.SelectedIndex),false);
        for (int i = 0;i < FeeSchedsForType.Count;i++)
        {
            comboFeeSchedWithout.Items.Add(FeeSchedsForType[i].Description);
            comboFeeSchedWith.Items.Add(FeeSchedsForType[i].Description);
            comboFeeSchedNew.Items.Add(FeeSchedsForType[i].Description);
        }
    }

    private void listType_Click(Object sender, EventArgs e) throws Exception {
        resetSelections();
        fillGrid();
    }

    private void textCarrier_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textCarrierNot_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void comboFeeSchedWithout_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void comboFeeSchedWith_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        long feeSchedWithout = 0;
        long feeSchedWith = 0;
        if (comboFeeSchedWithout.SelectedIndex != 0)
        {
            feeSchedWithout = FeeSchedsForType[comboFeeSchedWithout.SelectedIndex - 1].FeeSchedNum;
        }
         
        if (comboFeeSchedWith.SelectedIndex != 0)
        {
            feeSchedWith = FeeSchedsForType[comboFeeSchedWith.SelectedIndex - 1].FeeSchedNum;
        }
         
        table = InsPlans.GetListFeeCheck(textCarrier.Text, textCarrierNot.Text, feeSchedWithout, feeSchedWith, (FeeScheduleType)(listType.SelectedIndex));
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Employer",170);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Carrier",170);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Group#",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Group Name",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Plan Type",65);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Fee Schedule",90);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        String planType = new String();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["EmpName"].ToString());
            row.getCells().Add(table.Rows[i]["CarrierName"].ToString());
            row.getCells().Add(table.Rows[i]["GroupNum"].ToString());
            row.getCells().Add(table.Rows[i]["GroupName"].ToString());
            planType = table.Rows[i]["PlanType"].ToString();
            if (StringSupport.equals(planType, "p"))
            {
                row.getCells().add("PPO");
            }
            else if (StringSupport.equals(planType, "f"))
            {
                row.getCells().add("FlatCopay");
            }
            else if (StringSupport.equals(planType, "c"))
            {
                row.getCells().add("Capitation");
            }
            else
            {
                row.getCells().add("Cat%");
            }   
            row.getCells().Add(table.Rows[i]["FeeSchedName"].ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setScrollValue(0);
    }

    private void butSelectAll_Click(Object sender, EventArgs e) throws Exception {
        gridMain.setSelected(true);
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getRows().Count == 0)
        {
            MsgBox.show(this,"No rows to fix.");
            return ;
        }
         
        //if(comboFeeSchedNew.SelectedIndex==0) {
        //	MsgBox.Show(this,"No rows to fix.");
        //	return;
        //}
        if (gridMain.getSelectedIndices().Length == 0)
        {
            gridMain.setSelected(true);
        }
         
        if (!MsgBox.show(this,true,"Change the fee schedule for all selected plans to the new fee schedule?"))
        {
            return ;
        }
         
        InputBox passBox = new InputBox("To prevent accidental changes, please enter password.  It can be found in our manual.");
        passBox.ShowDialog();
        //
        if (passBox.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (!StringSupport.equals(passBox.textResult.Text, "fee"))
        {
            MsgBox.show(this,"Incorrect password.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        long employerNum = new long();
        String carrierName = new String();
        String groupNum = new String();
        String groupName = new String();
        long newFeeSchedNum = 0;
        if (comboFeeSchedNew.SelectedIndex != 0)
        {
            newFeeSchedNum = FeeSchedsForType[comboFeeSchedNew.SelectedIndex - 1].FeeSchedNum;
        }
         
        long oldFeeSchedNum = new long();
        long rowsChanged = 0;
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            oldFeeSchedNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["feeSched"].ToString());
            if (oldFeeSchedNum == newFeeSchedNum)
            {
                continue;
            }
             
            employerNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["EmployerNum"].ToString());
            carrierName = table.Rows[gridMain.getSelectedIndices()[i]]["CarrierName"].ToString();
            groupNum = table.Rows[gridMain.getSelectedIndices()[i]]["GroupNum"].ToString();
            groupName = table.Rows[gridMain.getSelectedIndices()[i]]["GroupName"].ToString();
            rowsChanged += InsPlans.setFeeSched(employerNum,carrierName,groupNum,groupName,newFeeSchedNum,(FeeScheduleType)(listType.SelectedIndex));
        }
        fillGrid();
        Cursor = Cursors.Default;
        MessageBox.Show(Lan.g(this,"Plans changed: ") + rowsChanged.ToString());
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


