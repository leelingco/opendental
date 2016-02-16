//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPatientSelect;
import OpenDental.FormReqStudentEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.ReqStudent;
import OpenDentBusiness.ReqStudents;
import OpenDentBusiness.SchoolCourses;
import OpenDentBusiness.Security;

/**
* Summary description for FormBasicTemplate.
*/
public class FormReqStudentEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textStudent = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butDelete;
    public ReqStudent ReqCur;
    private TextBox textAppointment = new TextBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butDetachApt;
    private OpenDental.UI.Button butDetachPat;
    private TextBox textPatient = new TextBox();
    private Label label5 = new Label();
    private Label label6 = new Label();
    private TextBox textDateCompleted = new TextBox();
    private ComboBox comboInstructor = new ComboBox();
    private Label label11 = new Label();
    private OpenDental.UI.Button butNow;
    private TextBox textDescription = new TextBox();
    private Label label2 = new Label();
    private TextBox textCourse = new TextBox();
    private Label label10 = new Label();
    private OpenDental.UI.Button butSelectPat;
    /**
    * 
    */
    public FormReqStudentEdit() throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReqStudentEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textStudent = new System.Windows.Forms.TextBox();
        this.textAppointment = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textPatient = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.textDateCompleted = new System.Windows.Forms.TextBox();
        this.comboInstructor = new System.Windows.Forms.ComboBox();
        this.label11 = new System.Windows.Forms.Label();
        this.butNow = new OpenDental.UI.Button();
        this.butDetachPat = new OpenDental.UI.Button();
        this.butDetachApt = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textCourse = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.butSelectPat = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(44, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(89, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Student";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textStudent
        //
        this.textStudent.Location = new System.Drawing.Point(133, 8);
        this.textStudent.Name = "textStudent";
        this.textStudent.ReadOnly = true;
        this.textStudent.Size = new System.Drawing.Size(319, 20);
        this.textStudent.TabIndex = 0;
        //
        // textAppointment
        //
        this.textAppointment.Location = new System.Drawing.Point(133, 133);
        this.textAppointment.Name = "textAppointment";
        this.textAppointment.ReadOnly = true;
        this.textAppointment.Size = new System.Drawing.Size(319, 20);
        this.textAppointment.TabIndex = 103;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(44, 134);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(89, 17);
        this.label4.TabIndex = 104;
        this.label4.Text = "Appointment";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPatient
        //
        this.textPatient.Location = new System.Drawing.Point(133, 108);
        this.textPatient.Name = "textPatient";
        this.textPatient.ReadOnly = true;
        this.textPatient.Size = new System.Drawing.Size(319, 20);
        this.textPatient.TabIndex = 106;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(44, 109);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(89, 17);
        this.label5.TabIndex = 107;
        this.label5.Text = "Patient";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(8, 84);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(123, 17);
        this.label6.TabIndex = 110;
        this.label6.Text = "Date Completed";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateCompleted
        //
        this.textDateCompleted.Location = new System.Drawing.Point(133, 83);
        this.textDateCompleted.Name = "textDateCompleted";
        this.textDateCompleted.Size = new System.Drawing.Size(114, 20);
        this.textDateCompleted.TabIndex = 111;
        //
        // comboInstructor
        //
        this.comboInstructor.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboInstructor.FormattingEnabled = true;
        this.comboInstructor.Location = new System.Drawing.Point(133, 158);
        this.comboInstructor.MaxDropDownItems = 25;
        this.comboInstructor.Name = "comboInstructor";
        this.comboInstructor.Size = new System.Drawing.Size(158, 21);
        this.comboInstructor.TabIndex = 121;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(44, 161);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(89, 17);
        this.label11.TabIndex = 120;
        this.label11.Text = "Instructor";
        this.label11.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butNow
        //
        this.butNow.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNow.setAutosize(true);
        this.butNow.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNow.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNow.setCornerRadius(4F);
        this.butNow.Location = new System.Drawing.Point(249, 81);
        this.butNow.Name = "butNow";
        this.butNow.Size = new System.Drawing.Size(75, 24);
        this.butNow.TabIndex = 118;
        this.butNow.Text = "Now";
        this.butNow.Click += new System.EventHandler(this.butNow_Click);
        //
        // butDetachPat
        //
        this.butDetachPat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDetachPat.setAutosize(true);
        this.butDetachPat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDetachPat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDetachPat.setCornerRadius(4F);
        this.butDetachPat.Location = new System.Drawing.Point(458, 105);
        this.butDetachPat.Name = "butDetachPat";
        this.butDetachPat.Size = new System.Drawing.Size(75, 24);
        this.butDetachPat.TabIndex = 108;
        this.butDetachPat.Text = "Detach";
        this.butDetachPat.Click += new System.EventHandler(this.butDetachPat_Click);
        //
        // butDetachApt
        //
        this.butDetachApt.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDetachApt.setAutosize(true);
        this.butDetachApt.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDetachApt.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDetachApt.setCornerRadius(4F);
        this.butDetachApt.Location = new System.Drawing.Point(458, 130);
        this.butDetachApt.Name = "butDetachApt";
        this.butDetachApt.Size = new System.Drawing.Size(75, 24);
        this.butDetachApt.TabIndex = 105;
        this.butDetachApt.Text = "Detach";
        this.butDetachApt.Click += new System.EventHandler(this.butDetachApt_Click);
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
        this.butDelete.Location = new System.Drawing.Point(27, 245);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 26);
        this.butDelete.TabIndex = 4;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(458, 245);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 2;
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
        this.butCancel.Location = new System.Drawing.Point(536, 245);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 9;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(133, 58);
        this.textDescription.Name = "textDescription";
        this.textDescription.ReadOnly = true;
        this.textDescription.Size = new System.Drawing.Size(319, 20);
        this.textDescription.TabIndex = 123;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 59);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(121, 17);
        this.label2.TabIndex = 124;
        this.label2.Text = "Requirement";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCourse
        //
        this.textCourse.Location = new System.Drawing.Point(133, 33);
        this.textCourse.Name = "textCourse";
        this.textCourse.ReadOnly = true;
        this.textCourse.Size = new System.Drawing.Size(319, 20);
        this.textCourse.TabIndex = 125;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(44, 34);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(89, 17);
        this.label10.TabIndex = 126;
        this.label10.Text = "Course";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butSelectPat
        //
        this.butSelectPat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSelectPat.setAutosize(true);
        this.butSelectPat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSelectPat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSelectPat.setCornerRadius(4F);
        this.butSelectPat.Location = new System.Drawing.Point(536, 105);
        this.butSelectPat.Name = "butSelectPat";
        this.butSelectPat.Size = new System.Drawing.Size(75, 24);
        this.butSelectPat.TabIndex = 127;
        this.butSelectPat.Text = "Select";
        this.butSelectPat.Click += new System.EventHandler(this.butSelectPat_Click);
        //
        // FormReqStudentEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(657, 289);
        this.Controls.Add(this.butSelectPat);
        this.Controls.Add(this.textCourse);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butNow);
        this.Controls.Add(this.textDateCompleted);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.comboInstructor);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.butDetachPat);
        this.Controls.Add(this.textPatient);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.butDetachApt);
        this.Controls.Add(this.textAppointment);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textStudent);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormReqStudentEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Student Requirement";
        this.Load += new System.EventHandler(this.FormReqStudentEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formReqStudentEdit_Load(Object sender, System.EventArgs e) throws Exception {
        //There should only be two types of users who are allowed to get this far:
        //Students editing their own req, and users with setup perm.  But we will double check.
        Provider provUser = Providers.getProv(Security.getCurUser().ProvNum);
        if (provUser != null && provUser.SchoolClassNum != 0)
        {
            //A student is logged in
            //the student only has permission to view/attach/detach their own requirements
            if (provUser.ProvNum != ReqCur.ProvNum)
            {
                //but this should never happen
                MsgBox.show(this,"Students may only edit their own requirements.");
                butDelete.Enabled = false;
                butOK.Enabled = false;
            }
            else
            {
                //the student matches
                butDelete.Enabled = false;
                textDateCompleted.Enabled = false;
                butNow.Enabled = false;
                comboInstructor.Enabled = false;
            } 
        }
        else
        {
            //a student is only allowed to change the patient and appointment.
            //A student is not logged in
            if (!Security.IsAuthorized(Permissions.Setup, DateTime.MinValue, true))
            {
                //suppress message
                butDelete.Enabled = false;
                butOK.Enabled = false;
            }
             
        } 
        textStudent.Text = Providers.getLongDesc(ReqCur.ProvNum);
        textCourse.Text = SchoolCourses.getDescript(ReqCur.SchoolCourseNum);
        textDescription.Text = ReqCur.Descript;
        if (ReqCur.DateCompleted.Year > 1880)
        {
            textDateCompleted.Text = ReqCur.DateCompleted.ToShortDateString();
        }
         
        //if an apt is attached, then the same pat must be attached.
        Patient pat = Patients.getPat(ReqCur.PatNum);
        if (pat != null)
        {
            textPatient.Text = pat.getNameFL();
        }
         
        Appointment apt = Appointments.getOneApt(ReqCur.AptNum);
        if (apt != null)
        {
            if (apt.AptStatus == ApptStatus.UnschedList)
            {
                textAppointment.Text = Lan.g(this,"Unscheduled");
            }
            else
            {
                textAppointment.Text = apt.AptDateTime.ToShortDateString() + " " + apt.AptDateTime.ToShortTimeString();
            } 
            textAppointment.Text += ", " + apt.ProcDescript;
        }
         
        comboInstructor.Items.Add(Lan.g(this,"None"));
        comboInstructor.SelectedIndex = 0;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboInstructor.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            if (ProviderC.getListShort()[i].ProvNum == ReqCur.InstructorNum)
            {
                comboInstructor.SelectedIndex = i + 1;
            }
             
        }
    }

    private void butDetachApt_Click(Object sender, EventArgs e) throws Exception {
        ReqCur.AptNum = 0;
        textAppointment.Text = "";
    }

    private void butDetachPat_Click(Object sender, EventArgs e) throws Exception {
        ReqCur.PatNum = 0;
        textPatient.Text = "";
    }

    private void butNow_Click(Object sender, EventArgs e) throws Exception {
        textDateCompleted.Text = MiscData.getNowDateTime().ToShortDateString();
    }

    private void butSelectPat_Click(Object sender, EventArgs e) throws Exception {
        FormPatientSelect FormP = new FormPatientSelect();
        FormP.SelectionModeOnly = true;
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ReqCur.PatNum = FormP.SelectedPatNum;
        textPatient.Text = Patients.getPat(ReqCur.PatNum).getNameFL();
        //if the patient changed, then the appointment must be detached.
        ReqCur.AptNum = 0;
        textAppointment.Text = "";
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        try
        {
            //if(!MsgBox.Show(this,true,"Delete this student requirement?  This is typically handled by using the synch feature from requirements needed.")){
            //	return;
            //}
            ReqStudents.delete(ReqCur.ReqStudentNum);
            //disallows deleting req that exists in reqNeeded.
            DialogResult = DialogResult.OK;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateCompleted.Text, ""))
        {
            try
            {
                DateTime.Parse(textDateCompleted.Text);
            }
            catch (Exception __dummyCatchVar0)
            {
                MsgBox.show(this,"Date is invalid.");
                return ;
            }
        
        }
         
        //ReqNeededNum-not editable
        //Descript-not editable
        //SchoolCourseNum-not editable
        //ProvNum-Student not editable
        //AptNum-handled earlier
        //PatNum-handled earlier
        //InstructorNum
        if (comboInstructor.SelectedIndex == 0)
        {
            ReqCur.InstructorNum = 0;
        }
        else
        {
            ReqCur.InstructorNum = ProviderC.getListShort()[comboInstructor.SelectedIndex - 1].ProvNum;
        } 
        //DateCompleted
        ReqCur.DateCompleted = PIn.Date(textDateCompleted.Text);
        try
        {
            //if(IsNew){//inserting is always done as part of reqneededs.synch
            //	LabCases.Insert(CaseCur);
            //}
            //else{
            ReqStudents.update(ReqCur);
        }
        catch (ApplicationException ex)
        {
            //}
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


