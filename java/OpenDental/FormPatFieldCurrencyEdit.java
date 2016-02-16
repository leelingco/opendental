//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.PatField;
import OpenDentBusiness.PatFields;

public class FormPatFieldCurrencyEdit  extends Form 
{

    public boolean IsNew = new boolean();
    private PatField Field;
    public FormPatFieldCurrencyEdit(PatField field) throws Exception {
        initializeComponent();
        Lan.F(this);
        Field = field;
    }

    private void formPatFieldCurrencyEdit_Load(Object sender, EventArgs e) throws Exception {
        labelName.Text = Field.FieldName;
        textFieldCurrency.Text = Field.FieldValue;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textFieldCurrency.errorProvider1.GetError(textFieldCurrency), ""))
        {
            MsgBox.show(this,"Invalid currency");
            return ;
        }
         
        if (StringSupport.equals(Field.FieldValue, ""))
        {
            //if blank, then delete
            if (IsNew)
            {
                DialogResult = DialogResult.Cancel;
                return ;
            }
             
            PatFields.delete(Field);
            DialogResult = DialogResult.OK;
            return ;
        }
         
        Field.FieldValue = textFieldCurrency.Text;
        if (IsNew)
        {
            PatFields.insert(Field);
        }
        else
        {
            PatFields.update(Field);
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
        this.labelName = new System.Windows.Forms.Label();
        this.textFieldCurrency = new ODR.ValidDouble();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // labelName
        //
        this.labelName.Location = new System.Drawing.Point(19, 17);
        this.labelName.Name = "labelName";
        this.labelName.Size = new System.Drawing.Size(335, 20);
        this.labelName.TabIndex = 5;
        this.labelName.Text = "Field Name";
        this.labelName.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textFieldCurrency
        //
        this.textFieldCurrency.Location = new System.Drawing.Point(22, 43);
        this.textFieldCurrency.Name = "textFieldCurrency";
        this.textFieldCurrency.Size = new System.Drawing.Size(108, 20);
        this.textFieldCurrency.TabIndex = 6;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(83, 93);
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
        this.butCancel.Location = new System.Drawing.Point(176, 93);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormPatFieldCurrencyEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(272, 140);
        this.Controls.Add(this.textFieldCurrency);
        this.Controls.Add(this.labelName);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormPatFieldCurrencyEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Patient Field Currency";
        this.Load += new System.EventHandler(this.FormPatFieldCurrencyEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label labelName = new System.Windows.Forms.Label();
    private ODR.ValidDouble textFieldCurrency;
}


