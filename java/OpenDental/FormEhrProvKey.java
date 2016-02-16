//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEHR;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.Provider;
import OpenDental.FormEhrProvKey;
import OpenDental.Properties.Resources;

public class FormEhrProvKey  extends Form 
{

    public Provider ProvCur;
    public FormEhrProvKey() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEhrProvKey_Load(Object sender, EventArgs e) throws Exception {
        textLName.Text = ProvCur.LName;
        textFName.Text = ProvCur.FName;
        checkHasReportAccess.Checked = ProvCur.EhrHasReportAccess;
        textEhrKey.Text = ProvCur.EhrKey;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(ProvCur.EhrKey, ""))
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        //Nothing to delete.
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        ProvCur.EhrKey = "";
        DialogResult = DialogResult.OK;
    }

    //So that the change will be made in the provider edit window (parent window).
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!FormEHR.ProvKeyIsValid(ProvCur.LName, ProvCur.FName, checkHasReportAccess.Checked, textEhrKey.Text))
        {
            MsgBox.show(this,"Invalid provider key.  Check capitalization, exact spelling, and report access status.");
            return ;
        }
         
        ProvCur.EhrHasReportAccess = checkHasReportAccess.Checked;
        ProvCur.EhrKey = textEhrKey.Text;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrProvKey.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textEhrKey = new System.Windows.Forms.TextBox();
        this.labelEhrKey = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textLName = new System.Windows.Forms.TextBox();
        this.textFName = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.checkHasReportAccess = new System.Windows.Forms.CheckBox();
        this.butDelete = new OpenDental.UI.Button();
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
        this.butOK.Location = new System.Drawing.Point(276, 233);
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
        this.butCancel.Location = new System.Drawing.Point(366, 233);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textEhrKey
        //
        this.textEhrKey.Location = new System.Drawing.Point(143, 179);
        this.textEhrKey.MaxLength = 15;
        this.textEhrKey.Name = "textEhrKey";
        this.textEhrKey.Size = new System.Drawing.Size(161, 20);
        this.textEhrKey.TabIndex = 105;
        //
        // labelEhrKey
        //
        this.labelEhrKey.Location = new System.Drawing.Point(55, 183);
        this.labelEhrKey.Name = "labelEhrKey";
        this.labelEhrKey.Size = new System.Drawing.Size(88, 14);
        this.labelEhrKey.TabIndex = 106;
        this.labelEhrKey.Text = "EHR Key";
        this.labelEhrKey.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 15);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(422, 89);
        this.label1.TabIndex = 107;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // textLName
        //
        this.textLName.Location = new System.Drawing.Point(143, 110);
        this.textLName.MaxLength = 100;
        this.textLName.Name = "textLName";
        this.textLName.ReadOnly = true;
        this.textLName.Size = new System.Drawing.Size(161, 20);
        this.textLName.TabIndex = 108;
        //
        // textFName
        //
        this.textFName.Location = new System.Drawing.Point(143, 133);
        this.textFName.MaxLength = 100;
        this.textFName.Name = "textFName";
        this.textFName.ReadOnly = true;
        this.textFName.Size = new System.Drawing.Size(161, 20);
        this.textFName.TabIndex = 109;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(9, 114);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(132, 14);
        this.label10.TabIndex = 111;
        this.label10.Text = "Last Name";
        this.label10.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(15, 137);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(127, 14);
        this.label8.TabIndex = 110;
        this.label8.Text = "First Name";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // checkHasReportAccess
        //
        this.checkHasReportAccess.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkHasReportAccess.Location = new System.Drawing.Point(18, 157);
        this.checkHasReportAccess.Name = "checkHasReportAccess";
        this.checkHasReportAccess.Size = new System.Drawing.Size(139, 18);
        this.checkHasReportAccess.TabIndex = 128;
        this.checkHasReportAccess.Text = "Has Report Access";
        this.checkHasReportAccess.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkHasReportAccess.UseVisualStyleBackColor = true;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(15, 233);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(80, 24);
        this.butDelete.TabIndex = 130;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormEhrProvKey
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(453, 269);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.checkHasReportAccess);
        this.Controls.Add(this.textLName);
        this.Controls.Add(this.textFName);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textEhrKey);
        this.Controls.Add(this.labelEhrKey);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormEhrProvKey";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "EHR Provider Key";
        this.Load += new System.EventHandler(this.FormEhrProvKey_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textEhrKey = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelEhrKey = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textLName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textFName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkHasReportAccess = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butDelete;
}


