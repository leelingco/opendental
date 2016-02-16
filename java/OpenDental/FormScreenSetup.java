//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetTypeEnum;

public class FormScreenSetup  extends Form 
{

    List<SheetDef> listSheets = new List<SheetDef>();
    public FormScreenSetup() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formScreenSetup_Load(Object sender, EventArgs e) throws Exception {
        checkUsePat.Checked = PrefC.getBool(PrefName.PublicHealthScreeningUsePat);
        listSheets = SheetDefs.getCustomForType(SheetTypeEnum.ExamSheet);
        for (int i = 0;i < listSheets.Count;i++)
        {
            comboExamSheets.Items.Add(listSheets[i].Description);
            if (PrefC.getLong(PrefName.PublicHealthScreeningSheet) == listSheets[i].SheetDefNum)
            {
                comboExamSheets.SelectedIndex = i;
            }
             
        }
    }

    private void checkUsePat_CheckedChanged(Object sender, EventArgs e) throws Exception {
        if (checkUsePat.Checked)
        {
            comboExamSheets.Enabled = true;
        }
        else
        {
            comboExamSheets.Enabled = false;
        } 
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        Prefs.UpdateBool(PrefName.PublicHealthScreeningUsePat, checkUsePat.Checked);
        if (comboExamSheets.SelectedIndex != -1)
        {
            Prefs.UpdateLong(PrefName.PublicHealthScreeningSheet, listSheets[comboExamSheets.SelectedIndex].SheetDefNum);
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.checkUsePat = new System.Windows.Forms.CheckBox();
        this.comboExamSheets = new System.Windows.Forms.ComboBox();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(239, 107);
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
        this.butCancel.Location = new System.Drawing.Point(331, 107);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // checkUsePat
        //
        this.checkUsePat.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkUsePat.Location = new System.Drawing.Point(3, 28);
        this.checkUsePat.Name = "checkUsePat";
        this.checkUsePat.Size = new System.Drawing.Size(224, 18);
        this.checkUsePat.TabIndex = 4;
        this.checkUsePat.Text = "Attach screenings to patient records";
        this.checkUsePat.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkUsePat.UseVisualStyleBackColor = true;
        this.checkUsePat.CheckedChanged += new System.EventHandler(this.checkUsePat_CheckedChanged);
        //
        // comboExamSheets
        //
        this.comboExamSheets.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboExamSheets.Enabled = false;
        this.comboExamSheets.FormattingEnabled = true;
        this.comboExamSheets.Location = new System.Drawing.Point(213, 50);
        this.comboExamSheets.Name = "comboExamSheets";
        this.comboExamSheets.Size = new System.Drawing.Size(153, 21);
        this.comboExamSheets.TabIndex = 5;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(115, 52);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(92, 14);
        this.label1.TabIndex = 6;
        this.label1.Text = "Exam Sheet";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormScreenSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(418, 143);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.comboExamSheets);
        this.Controls.Add(this.checkUsePat);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormScreenSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Screening Setup";
        this.Load += new System.EventHandler(this.FormScreenSetup_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.CheckBox checkUsePat = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.ComboBox comboExamSheets = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


