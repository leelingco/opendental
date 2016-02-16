//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:35 PM
//

package OpenDental;

import OpenDental.FormProgramProperty;
import OpenDental.Lan;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;

/**
* Summary description for FormBasicTemplate.
*/
public class FormProgramProperty  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textProperty = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textValue = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public ProgramProperty ProgramPropertyCur;
    /**
    * 
    */
    public FormProgramProperty() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProgramProperty.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textProperty = new System.Windows.Forms.TextBox();
        this.textValue = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(406, 180);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(295, 180);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(7, 29);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(152, 21);
        this.label1.TabIndex = 2;
        this.label1.Text = "Property";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textProperty
        //
        this.textProperty.Location = new System.Drawing.Point(162, 31);
        this.textProperty.Name = "textProperty";
        this.textProperty.ReadOnly = true;
        this.textProperty.Size = new System.Drawing.Size(319, 20);
        this.textProperty.TabIndex = 3;
        //
        // textValue
        //
        this.textValue.Location = new System.Drawing.Point(162, 74);
        this.textValue.Name = "textValue";
        this.textValue.Size = new System.Drawing.Size(319, 20);
        this.textValue.TabIndex = 5;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 71);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(152, 21);
        this.label2.TabIndex = 4;
        this.label2.Text = "Value";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormProgramProperty
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(541, 230);
        this.Controls.Add(this.textValue);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textProperty);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProgramProperty";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Program Property";
        this.Load += new System.EventHandler(this.FormProgramProperty_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formProgramProperty_Load(Object sender, System.EventArgs e) throws Exception {
        textProperty.Text = ProgramPropertyCur.PropertyDesc;
        textValue.Text = ProgramPropertyCur.PropertyValue;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        ProgramPropertyCur.PropertyValue = textValue.Text;
        ProgramProperties.update(ProgramPropertyCur);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


