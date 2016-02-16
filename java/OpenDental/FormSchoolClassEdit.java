//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:45 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormSchoolClassEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.SchoolClass;
import OpenDentBusiness.SchoolClasses;

/**
* Summary description for FormBasicTemplate.
*/
public class FormSchoolClassEdit  extends System.Windows.Forms.Form 
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
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textDescript = new System.Windows.Forms.TextBox();
    private OpenDental.ValidNumber textGradYear;
    private SchoolClass ClassCur;
    /**
    * 
    */
    public FormSchoolClassEdit(SchoolClass classCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        ClassCur = classCur.copy();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSchoolClassEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textDescript = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.textGradYear = new OpenDental.ValidNumber();
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
        this.butCancel.Location = new System.Drawing.Point(411, 171);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 4;
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
        this.butOK.Location = new System.Drawing.Point(411, 130);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(5, 22);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(199, 19);
        this.label1.TabIndex = 2;
        this.label1.Text = "Graduation Year";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescript
        //
        this.textDescript.Location = new System.Drawing.Point(206, 49);
        this.textDescript.Name = "textDescript";
        this.textDescript.Size = new System.Drawing.Size(196, 20);
        this.textDescript.TabIndex = 1;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(5, 49);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(199, 19);
        this.label2.TabIndex = 4;
        this.label2.Text = "Description (Dental or Hygiene)";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
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
        this.butDelete.Location = new System.Drawing.Point(50, 170);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(95, 26);
        this.butDelete.TabIndex = 2;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textGradYear
        //
        this.textGradYear.Location = new System.Drawing.Point(206, 20);
        this.textGradYear.setMaxVal(2075);
        this.textGradYear.setMinVal(1990);
        this.textGradYear.Name = "textGradYear";
        this.textGradYear.Size = new System.Drawing.Size(71, 20);
        this.textGradYear.TabIndex = 0;
        //
        // FormSchoolClassEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(538, 222);
        this.Controls.Add(this.textGradYear);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textDescript);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormSchoolClassEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Dental School Class";
        this.Load += new System.EventHandler(this.FormSchoolClassEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formSchoolClassEdit_Load(Object sender, System.EventArgs e) throws Exception {
        if (ClassCur.GradYear != 0)
        {
            textGradYear.Text = ClassCur.GradYear.ToString();
        }
         
        textDescript.Text = ClassCur.Descript;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            if (!MsgBox.show(this,true,"Delete this Dental School Class?"))
            {
                return ;
            }
             
            try
            {
                SchoolClasses.delete(ClassCur.SchoolClassNum);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return ;
            }
        
        } 
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textGradYear.errorProvider1.GetError(textGradYear), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (StringSupport.equals(textGradYear.Text, ""))
        {
            MsgBox.show(this,"Please enter a graduation year.");
            return ;
        }
         
        ClassCur.GradYear = PIn.Int(textGradYear.Text);
        ClassCur.Descript = textDescript.Text;
        SchoolClasses.insertOrUpdate(ClassCur,IsNew);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


