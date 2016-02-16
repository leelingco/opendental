//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:22 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPatFieldDateEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.PatField;
import OpenDentBusiness.PatFields;

/**
* Summary description for FormBasicTemplate.
*/
public class FormPatFieldDateEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private PatField Field;
    private OpenDental.ValidDate textFieldDate;
    private Label labelName = new Label();
    /**
    * 
    */
    public FormPatFieldDateEdit(PatField field) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        Field = field;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPatFieldDateEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.labelName = new System.Windows.Forms.Label();
        this.textFieldDate = new OpenDental.ValidDate();
        this.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(177, 93);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
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
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // labelName
        //
        this.labelName.Location = new System.Drawing.Point(19, 17);
        this.labelName.Name = "labelName";
        this.labelName.Size = new System.Drawing.Size(335, 20);
        this.labelName.TabIndex = 3;
        this.labelName.Text = "Field Name";
        this.labelName.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textFieldDate
        //
        this.textFieldDate.Location = new System.Drawing.Point(22, 43);
        this.textFieldDate.Name = "textFieldDate";
        this.textFieldDate.Size = new System.Drawing.Size(108, 20);
        this.textFieldDate.TabIndex = 4;
        //
        // FormPatFieldDateEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(272, 140);
        this.Controls.Add(this.textFieldDate);
        this.Controls.Add(this.labelName);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPatFieldDateEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Patient Field Date";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormPatFieldDefEdit_FormClosing);
        this.Load += new System.EventHandler(this.FormPatFieldDateEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPatFieldDateEdit_Load(Object sender, System.EventArgs e) throws Exception {
        labelName.Text = Field.FieldName;
        textFieldDate.Text = Field.FieldValue;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textFieldDate.errorProvider1.GetError(textFieldDate), ""))
        {
            MsgBox.show(this,"Invalid date");
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
         
        Field.FieldValue = textFieldDate.Text;
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

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formPatFieldDefEdit_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
    }

}


