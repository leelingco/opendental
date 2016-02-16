//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:38 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormApptFieldEdit;
import OpenDental.Lan;
import OpenDentBusiness.ApptField;
import OpenDentBusiness.ApptFields;

/**
* Summary description for FormBasicTemplate.
*/
public class FormApptFieldEdit  extends System.Windows.Forms.Form 
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
    private ApptField Field;
    private Label labelName = new Label();
    private TextBox textValue = new TextBox();
    /**
    * 
    */
    public FormApptFieldEdit(ApptField field) throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormApptFieldEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.labelName = new System.Windows.Forms.Label();
        this.textValue = new System.Windows.Forms.TextBox();
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
        this.butCancel.Location = new System.Drawing.Point(280, 146);
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
        this.butOK.Location = new System.Drawing.Point(186, 146);
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
        // textValue
        //
        this.textValue.Location = new System.Drawing.Point(21, 40);
        this.textValue.Multiline = true;
        this.textValue.Name = "textValue";
        this.textValue.Size = new System.Drawing.Size(333, 74);
        this.textValue.TabIndex = 0;
        //
        // FormApptFieldEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(375, 193);
        this.Controls.Add(this.textValue);
        this.Controls.Add(this.labelName);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormApptFieldEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Appointment Field";
        this.Load += new System.EventHandler(this.FormApptFieldEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formApptFieldEdit_Load(Object sender, System.EventArgs e) throws Exception {
        labelName.Text = Field.FieldName;
        textValue.Text = Field.FieldValue;
    }

    /*private void buttonDelete_Click(object sender,EventArgs e) {
    			
    		}*/
    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        Field.FieldValue = textValue.Text;
        if (StringSupport.equals(Field.FieldValue, ""))
        {
            //if blank, then delete
            if (IsNew)
            {
                DialogResult = DialogResult.Cancel;
                return ;
            }
             
            ApptFields.delete(Field.ApptFieldNum);
            DialogResult = DialogResult.OK;
            return ;
        }
         
        if (IsNew)
        {
            ApptFields.insert(Field);
        }
        else
        {
            ApptFields.update(Field);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


