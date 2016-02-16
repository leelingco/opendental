//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormDrugUnitEdit;
import OpenDental.Lan;
import OpenDentBusiness.DrugUnit;
import OpenDentBusiness.DrugUnits;
import OpenDental.Properties.Resources;

public class FormDrugUnitSetup  extends Form 
{

    public FormDrugUnitSetup() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formDrugUnitSetup_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        DrugUnits.refreshCache();
        listMain.Items.Clear();
        for (int i = 0;i < DrugUnits.getListt().Count;i++)
        {
            listMain.Items.Add(DrugUnits.getListt()[i].UnitIdentifier + " - " + DrugUnits.getListt()[i].UnitText);
        }
    }

    private void listMain_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            return ;
        }
         
        FormDrugUnitEdit FormD = new FormDrugUnitEdit();
        FormD.DrugUnitCur = DrugUnits.getListt()[listMain.SelectedIndex];
        FormD.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormDrugUnitEdit FormD = new FormDrugUnitEdit();
        FormD.DrugUnitCur = new DrugUnit();
        FormD.IsNew = true;
        FormD.ShowDialog();
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.listMain = new System.Windows.Forms.ListBox();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listMain
        //
        this.listMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.listMain.Location = new System.Drawing.Point(28, 32);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(268, 251);
        this.listMain.TabIndex = 12;
        this.listMain.DoubleClick += new System.EventHandler(this.listMain_DoubleClick);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(28, 310);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 24);
        this.butAdd.TabIndex = 13;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(221, 310);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 11;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormDrugUnitSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(324, 362);
        this.Controls.Add(this.listMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Name = "FormDrugUnitSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Drug Unit Setup";
        this.Load += new System.EventHandler(this.FormDrugUnitSetup_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
}


