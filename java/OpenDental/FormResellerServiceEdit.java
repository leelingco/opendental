//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormProcCodes;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.ResellerService;
import OpenDentBusiness.ResellerServices;
import OpenDental.Properties.Resources;

public class FormResellerServiceEdit  extends Form 
{

    private ResellerService ResellerServiceCur;
    public boolean IsNew = new boolean();
    public FormResellerServiceEdit(ResellerService resellerService) throws Exception {
        ResellerServiceCur = resellerService;
        initializeComponent();
        Lan.F(this);
    }

    private void formResellerServiceEdit_Load(Object sender, EventArgs e) throws Exception {
        if (!IsNew)
        {
            textCode.Text = ProcedureCodes.getStringProcCode(ResellerServiceCur.CodeNum);
            textDesc.Text = ProcedureCodes.getLaymanTerm(ResellerServiceCur.CodeNum);
            textFee.Text = ResellerServiceCur.Fee.ToString("F");
        }
         
    }

    private void butPick_Click(Object sender, EventArgs e) throws Exception {
        FormProcCodes FormPC = new FormProcCodes();
        FormPC.IsSelectionMode = true;
        FormPC.ShowDialog();
        if (FormPC.DialogResult == DialogResult.OK)
        {
            ResellerServiceCur.CodeNum = FormPC.SelectedCodeNum;
            textCode.Text = ProcedureCodes.getStringProcCode(ResellerServiceCur.CodeNum);
            textDesc.Text = ProcedureCodes.getLaymanTerm(ResellerServiceCur.CodeNum);
        }
         
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!IsNew)
        {
            MsgBox.show(this,"Deleting services not implemented yet.");
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (ResellerServiceCur.CodeNum == 0)
        {
            MsgBox.show(this,"Please pick a service from the list of procedure codes.");
            return ;
        }
         
        if (!StringSupport.equals(textFee.errorProvider1.GetError(textFee), ""))
        {
            MsgBox.show(this,"Please fix the service fee first.");
            return ;
        }
         
        ResellerServiceCur.Fee = PIn.Double(textFee.Text);
        if (IsNew)
        {
            ResellerServices.insert(ResellerServiceCur);
        }
        else
        {
            ResellerServices.update(ResellerServiceCur);
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
        this.menuRightClick = new System.Windows.Forms.ContextMenu();
        this.menuItemAccount = new System.Windows.Forms.MenuItem();
        this.butOK = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textDesc = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textCode = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.butPick = new OpenDental.UI.Button();
        this.textFee = new ODR.ValidDouble();
        this.SuspendLayout();
        //
        // menuRightClick
        //
        this.menuRightClick.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemAccount });
        //
        // menuItemAccount
        //
        this.menuItemAccount.Index = 0;
        this.menuItemAccount.Text = "Go to Account";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(254, 175);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 251;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
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
        this.butDelete.Location = new System.Drawing.Point(12, 175);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(84, 24);
        this.butDelete.TabIndex = 41;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(349, 175);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textDesc
        //
        this.textDesc.BackColor = System.Drawing.SystemColors.Control;
        this.textDesc.Location = new System.Drawing.Point(155, 72);
        this.textDesc.Name = "textDesc";
        this.textDesc.Size = new System.Drawing.Size(261, 20);
        this.textDesc.TabIndex = 255;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(26, 73);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(127, 16);
        this.label6.TabIndex = 254;
        this.label6.Text = "Service Description:";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCode
        //
        this.textCode.Location = new System.Drawing.Point(155, 39);
        this.textCode.MaxLength = 15;
        this.textCode.Name = "textCode";
        this.textCode.ReadOnly = true;
        this.textCode.Size = new System.Drawing.Size(100, 20);
        this.textCode.TabIndex = 253;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(86, 41);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(67, 16);
        this.label1.TabIndex = 252;
        this.label1.Text = "Code";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(82, 108);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(67, 16);
        this.label2.TabIndex = 256;
        this.label2.Text = "Fee";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butPick
        //
        this.butPick.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPick.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPick.setAutosize(true);
        this.butPick.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPick.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPick.setCornerRadius(4F);
        this.butPick.Location = new System.Drawing.Point(315, 36);
        this.butPick.Name = "butPick";
        this.butPick.Size = new System.Drawing.Size(101, 24);
        this.butPick.TabIndex = 258;
        this.butPick.Text = "Pick From List";
        this.butPick.Click += new System.EventHandler(this.butPick_Click);
        //
        // textFee
        //
        this.textFee.Location = new System.Drawing.Point(155, 107);
        this.textFee.Name = "textFee";
        this.textFee.Size = new System.Drawing.Size(100, 20);
        this.textFee.TabIndex = 259;
        //
        // FormResellerServiceEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(446, 211);
        this.Controls.Add(this.textFee);
        this.Controls.Add(this.butPick);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textDesc);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textCode);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butCancel);
        this.Name = "FormResellerServiceEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Reseller Service Edit";
        this.Load += new System.EventHandler(this.FormResellerServiceEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.ContextMenu menuRightClick = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.MenuItem menuItemAccount = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.TextBox textDesc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butPick;
    private ODR.ValidDouble textFee;
}


