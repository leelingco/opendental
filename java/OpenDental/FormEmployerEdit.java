//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:08 PM
//

package OpenDental;

import OpenDental.FormEmployerEdit;
import OpenDental.Lan;
import OpenDentBusiness.Employer;
import OpenDentBusiness.Employers;

/**
* Summary description for FormBasicTemplate.
*/
public class FormEmployerEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.TextBox textEmp = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    public Employer EmployerCur;
    /**
    * 
    */
    public FormEmployerEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEmployerEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textEmp = new System.Windows.Forms.TextBox();
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
        this.butCancel.Location = new System.Drawing.Point(254, 154);
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
        this.butOK.Location = new System.Drawing.Point(254, 113);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textEmp
        //
        this.textEmp.Location = new System.Drawing.Point(20, 51);
        this.textEmp.Name = "textEmp";
        this.textEmp.Size = new System.Drawing.Size(308, 20);
        this.textEmp.TabIndex = 0;
        //
        // FormEmployerEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(349, 203);
        this.Controls.Add(this.textEmp);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormEmployerEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Employer";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormEmployerEdit_Closing);
        this.Load += new System.EventHandler(this.FormEmployerEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formEmployerEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textEmp.Text = EmployerCur.EmpName;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        EmployerCur.EmpName = textEmp.Text;
        if (IsNew)
        {
            Employers.insert(EmployerCur);
        }
        else
        {
            Employers.update(EmployerCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formEmployerEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
            return ;
         
    }

}


//if(IsNew)
//	Employers.Delete(EmployerCur);