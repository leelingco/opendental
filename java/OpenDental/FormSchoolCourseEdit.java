//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:45 PM
//

package OpenDental;

import OpenDental.FormSchoolCourseEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.SchoolCourse;
import OpenDentBusiness.SchoolCourses;

/**
* Summary description for FormBasicTemplate.
*/
public class FormSchoolCourseEdit  extends System.Windows.Forms.Form 
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
    private System.Windows.Forms.TextBox textCourseID = new System.Windows.Forms.TextBox();
    private SchoolCourse CourseCur;
    /**
    * 
    */
    public FormSchoolCourseEdit(SchoolCourse courseCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        CourseCur = courseCur.copy();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSchoolCourseEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textDescript = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.textCourseID = new System.Windows.Forms.TextBox();
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
        this.butCancel.Location = new System.Drawing.Point(474, 162);
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
        this.butOK.Location = new System.Drawing.Point(474, 121);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(11, 21);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(155, 19);
        this.label1.TabIndex = 2;
        this.label1.Text = "Course ID";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescript
        //
        this.textDescript.Location = new System.Drawing.Point(168, 48);
        this.textDescript.Name = "textDescript";
        this.textDescript.Size = new System.Drawing.Size(381, 20);
        this.textDescript.TabIndex = 1;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(11, 48);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(155, 19);
        this.label2.TabIndex = 4;
        this.label2.Text = "Description";
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
        this.butDelete.Location = new System.Drawing.Point(50, 161);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(95, 26);
        this.butDelete.TabIndex = 2;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textCourseID
        //
        this.textCourseID.Location = new System.Drawing.Point(168, 19);
        this.textCourseID.Name = "textCourseID";
        this.textCourseID.Size = new System.Drawing.Size(144, 20);
        this.textCourseID.TabIndex = 0;
        //
        // FormSchoolCourseEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(601, 213);
        this.Controls.Add(this.textCourseID);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textDescript);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormSchoolCourseEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Dental School Course";
        this.Load += new System.EventHandler(this.FormSchoolCourseEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formSchoolCourseEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textCourseID.Text = CourseCur.CourseID;
        textDescript.Text = CourseCur.Descript;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            if (!MsgBox.show(this,true,"Delete this Course?"))
            {
                return ;
            }
             
            try
            {
                SchoolCourses.delete(CourseCur.SchoolCourseNum);
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
        CourseCur.CourseID = textCourseID.Text;
        CourseCur.Descript = textDescript.Text;
        SchoolCourses.insertOrUpdate(CourseCur,IsNew);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


