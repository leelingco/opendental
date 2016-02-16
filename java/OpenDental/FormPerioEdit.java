//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:29 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPerioEdit;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDentBusiness.PerioExam;
import OpenDentBusiness.PerioExams;
import OpenDentBusiness.ProviderC;

/**
* Summary description for FormBasicTemplate.
*/
public class FormPerioEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.ValidDate textDate;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listProv = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public PerioExam PerioExamCur;
    /**
    * 
    */
    public FormPerioEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPerioEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textDate = new OpenDental.ValidDate();
        this.label1 = new System.Windows.Forms.Label();
        this.listProv = new System.Windows.Forms.ListBox();
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
        this.butCancel.Location = new System.Drawing.Point(347, 164);
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
        this.butOK.Location = new System.Drawing.Point(347, 123);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(151, 18);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(100, 20);
        this.textDate.TabIndex = 2;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(14, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(136, 23);
        this.label1.TabIndex = 3;
        this.label1.Text = "Exam Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listProv
        //
        this.listProv.Location = new System.Drawing.Point(151, 55);
        this.listProv.Name = "listProv";
        this.listProv.Size = new System.Drawing.Size(120, 134);
        this.listProv.TabIndex = 4;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(13, 51);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(136, 23);
        this.label2.TabIndex = 5;
        this.label2.Text = "Provider";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormPerioEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(455, 215);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listProv);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPerioEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Perio Date";
        this.Load += new System.EventHandler(this.FormPerioEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPerioEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textDate.Text = PerioExamCur.ExamDate.ToShortDateString();
        listProv.Items.Clear();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            listProv.Items.Add(ProviderC.getListShort()[i].Abbr);
            if (ProviderC.getListShort()[i].ProvNum == PerioExamCur.ProvNum)
            {
                listProv.SelectedIndex = i;
            }
             
        }
        if (listProv.SelectedIndex == -1)
            listProv.SelectedIndex = 0;
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        PerioExamCur.ExamDate = PIn.Date(textDate.Text);
        PerioExamCur.ProvNum = ProviderC.getListShort()[listProv.SelectedIndex].ProvNum;
        PerioExams.update(PerioExamCur);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


