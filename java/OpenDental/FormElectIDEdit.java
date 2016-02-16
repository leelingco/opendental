//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.ElectID;
import OpenDentBusiness.ElectIDs;
import OpenDental.FormElectIDEdit;

public class FormElectIDEdit  extends Form 
{

    /**
    * Must be set before calling Show() or ShowDialog().
    */
    public ElectID electIDCur;
    public FormElectIDEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formElectIDEdit_Load(Object sender, EventArgs e) throws Exception {
        textPayerID.Text = electIDCur.PayorID;
        textCarrierName.Text = electIDCur.CarrierName;
        textComments.Text = electIDCur.Comments;
        checkIsMedicaid.Checked = electIDCur.IsMedicaid;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textPayerID.Text, ""))
        {
            MsgBox.show(this,"Payer ID cannot be blank.");
            return ;
        }
         
        if (StringSupport.equals(textCarrierName.Text, ""))
        {
            MsgBox.show(this,"Carrier name cannot be blank.");
            return ;
        }
         
        electIDCur.PayorID = textPayerID.Text;
        electIDCur.CarrierName = textCarrierName.Text;
        electIDCur.Comments = textComments.Text;
        electIDCur.IsMedicaid = checkIsMedicaid.Checked;
        if (electIDCur.ElectIDNum == 0)
        {
            ElectIDs.insert(electIDCur);
        }
        else
        {
            ElectIDs.update(electIDCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormElectIDEdit.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textPayerID = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textCarrierName = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.checkIsMedicaid = new System.Windows.Forms.CheckBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textComments = new System.Windows.Forms.TextBox();
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
        this.butOK.Location = new System.Drawing.Point(258, 246);
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
        this.butCancel.Location = new System.Drawing.Point(344, 246);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textPayerID
        //
        this.textPayerID.Location = new System.Drawing.Point(88, 5);
        this.textPayerID.Name = "textPayerID";
        this.textPayerID.Size = new System.Drawing.Size(111, 20);
        this.textPayerID.TabIndex = 4;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(0, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(82, 16);
        this.label1.TabIndex = 5;
        this.label1.Text = "Payer ID";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCarrierName
        //
        this.textCarrierName.Location = new System.Drawing.Point(88, 31);
        this.textCarrierName.Name = "textCarrierName";
        this.textCarrierName.Size = new System.Drawing.Size(331, 20);
        this.textCarrierName.TabIndex = 6;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(2, 32);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(80, 19);
        this.label2.TabIndex = 7;
        this.label2.Text = "Carrier Name";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsMedicaid
        //
        this.checkIsMedicaid.AutoSize = true;
        this.checkIsMedicaid.Location = new System.Drawing.Point(88, 232);
        this.checkIsMedicaid.Name = "checkIsMedicaid";
        this.checkIsMedicaid.Size = new System.Drawing.Size(80, 17);
        this.checkIsMedicaid.TabIndex = 8;
        this.checkIsMedicaid.Text = "Is Medicaid";
        this.checkIsMedicaid.UseVisualStyleBackColor = true;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(0, 58);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(82, 20);
        this.label3.TabIndex = 10;
        this.label3.Text = "Comments";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textComments
        //
        this.textComments.Location = new System.Drawing.Point(88, 57);
        this.textComments.Multiline = true;
        this.textComments.Name = "textComments";
        this.textComments.Size = new System.Drawing.Size(331, 169);
        this.textComments.TabIndex = 9;
        //
        // FormElectIDEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(439, 291);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textComments);
        this.Controls.Add(this.checkIsMedicaid);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textCarrierName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textPayerID);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormElectIDEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Load += new System.EventHandler(this.FormElectIDEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textPayerID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCarrierName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkIsMedicaid = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textComments = new System.Windows.Forms.TextBox();
}


