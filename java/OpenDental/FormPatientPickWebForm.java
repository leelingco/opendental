//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormPatientSelect;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.SecurityLogs;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormPatientPickWebForm  extends Form 
{

    private List<Patient> listPats = new List<Patient>();
    /**
    * If OK.  Can be zero to indicate create new patient.  A result of Cancel indicates quit importing altogether.
    */
    public long SelectedPatNum = new long();
    public String LnameEntered = new String();
    public String FnameEntered = new String();
    public DateTime BdateEntered = new DateTime();
    public FormPatientPickWebForm() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formPatientPickWebForm_Load(Object sender, EventArgs e) throws Exception {
        textLName.Text = LnameEntered;
        textFName.Text = FnameEntered;
        textBirthdate.Text = BdateEntered.ToShortDateString();
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Last Name"),110);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"First Name"),110);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Birthdate"),110);
        gridMain.getColumns().add(col);
        listPats = Patients.getSimilarList(LnameEntered,FnameEntered,BdateEntered);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listPats.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listPats[i].LName);
            row.getCells().Add(listPats[i].FName);
            row.getCells().Add(listPats[i].Birthdate.ToShortDateString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SelectedPatNum = listPats[e.getRow()].PatNum;
        //Security log for patient select.
        Patient pat = Patients.getPat(SelectedPatNum);
        SecurityLogs.makeLogEntry(Permissions.SheetEdit,SelectedPatNum,"In the 'Pick Patient for Web Form', this user double clicked a name in the suggested list.  " + "This caused the web form for this patient: " + LnameEntered + ", " + FnameEntered + " " + BdateEntered.ToShortDateString() + "  " + "to be manually attached to this other patient: " + pat.LName + ", " + pat.FName + " " + pat.Birthdate.ToShortDateString());
        DialogResult = DialogResult.OK;
    }

    private void butSelect_Click(Object sender, EventArgs e) throws Exception {
        FormPatientSelect FormPs = new FormPatientSelect();
        FormPs.SelectionModeOnly = true;
        FormPs.ShowDialog();
        if (FormPs.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SelectedPatNum = FormPs.SelectedPatNum;
        //Security log for patient select.
        Patient pat = Patients.getPat(SelectedPatNum);
        SecurityLogs.makeLogEntry(Permissions.SheetEdit,SelectedPatNum,"In the 'Pick Patient for Web Form', this user clicked the 'Select' button.  " + "By clicking the 'Select' button, the web form for this patient: " + LnameEntered + ", " + FnameEntered + " " + BdateEntered.ToShortDateString() + "  " + "was manually attached to this other patient: " + pat.LName + ", " + pat.FName + " " + pat.Birthdate.ToShortDateString());
        DialogResult = DialogResult.OK;
    }

    private void butNew_Click(Object sender, EventArgs e) throws Exception {
        SelectedPatNum = 0;
        DialogResult = DialogResult.OK;
    }

    private void butSkip_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Ignore;
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
        this.butCancel = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textBirthdate = new OpenDental.ValidDate();
        this.label9 = new System.Windows.Forms.Label();
        this.textFName = new System.Windows.Forms.TextBox();
        this.textLName = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.butSelect = new OpenDental.UI.Button();
        this.butNew = new OpenDental.UI.Button();
        this.label6 = new System.Windows.Forms.Label();
        this.butSkip = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(539, 451);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(389, 20);
        this.label1.TabIndex = 4;
        this.label1.Text = "An exact matching patient could not be found for this submitted web form.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(15, 162);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(483, 161);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle("Close Matches");
        this.gridMain.setTranslationName(null);
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textBirthdate);
        this.groupBox1.Controls.Add(this.label9);
        this.groupBox1.Controls.Add(this.textFName);
        this.groupBox1.Controls.Add(this.textLName);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Location = new System.Drawing.Point(15, 35);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(350, 89);
        this.groupBox1.TabIndex = 6;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Submitted Values";
        //
        // textBirthdate
        //
        this.textBirthdate.Location = new System.Drawing.Point(114, 62);
        this.textBirthdate.Name = "textBirthdate";
        this.textBirthdate.ReadOnly = true;
        this.textBirthdate.Size = new System.Drawing.Size(82, 20);
        this.textBirthdate.TabIndex = 10;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(7, 66);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(105, 14);
        this.label9.TabIndex = 9;
        this.label9.Text = "Birthdate";
        this.label9.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textFName
        //
        this.textFName.Location = new System.Drawing.Point(114, 41);
        this.textFName.MaxLength = 100;
        this.textFName.Name = "textFName";
        this.textFName.ReadOnly = true;
        this.textFName.Size = new System.Drawing.Size(228, 20);
        this.textFName.TabIndex = 5;
        //
        // textLName
        //
        this.textLName.Location = new System.Drawing.Point(114, 20);
        this.textLName.MaxLength = 100;
        this.textLName.Name = "textLName";
        this.textLName.ReadOnly = true;
        this.textLName.Size = new System.Drawing.Size(228, 20);
        this.textLName.TabIndex = 3;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(4, 44);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(108, 16);
        this.label3.TabIndex = 6;
        this.label3.Text = "First Name";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 22);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(106, 16);
        this.label2.TabIndex = 4;
        this.label2.Text = "Last Name";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(12, 139);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(389, 20);
        this.label4.TabIndex = 7;
        this.label4.Text = "You can choose a patient by double clicking in this list";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(12, 344);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(389, 20);
        this.label5.TabIndex = 8;
        this.label5.Text = "Or, you can select a patient from the main list.";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butSelect
        //
        this.butSelect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSelect.setAutosize(true);
        this.butSelect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSelect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSelect.setCornerRadius(4F);
        this.butSelect.Location = new System.Drawing.Point(15, 368);
        this.butSelect.Name = "butSelect";
        this.butSelect.Size = new System.Drawing.Size(75, 24);
        this.butSelect.TabIndex = 9;
        this.butSelect.Text = "Select";
        this.butSelect.Click += new System.EventHandler(this.butSelect_Click);
        //
        // butNew
        //
        this.butNew.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNew.setAutosize(true);
        this.butNew.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNew.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNew.setCornerRadius(4F);
        this.butNew.Location = new System.Drawing.Point(15, 434);
        this.butNew.Name = "butNew";
        this.butNew.Size = new System.Drawing.Size(75, 24);
        this.butNew.TabIndex = 11;
        this.butNew.Text = "New";
        this.butNew.Click += new System.EventHandler(this.butNew_Click);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(12, 410);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(389, 20);
        this.label6.TabIndex = 10;
        this.label6.Text = "Or, you can create a new patient to attach this form to.";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butSkip
        //
        this.butSkip.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSkip.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSkip.setAutosize(true);
        this.butSkip.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSkip.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSkip.setCornerRadius(4F);
        this.butSkip.Location = new System.Drawing.Point(444, 451);
        this.butSkip.Name = "butSkip";
        this.butSkip.Size = new System.Drawing.Size(75, 24);
        this.butSkip.TabIndex = 12;
        this.butSkip.Text = "&Skip";
        this.butSkip.Click += new System.EventHandler(this.butSkip_Click);
        //
        // FormPatientPickWebForm
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(626, 487);
        this.Controls.Add(this.butSkip);
        this.Controls.Add(this.butNew);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.butSelect);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Name = "FormPatientPickWebForm";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Pick Patient for Web Form";
        this.Load += new System.EventHandler(this.FormPatientPickWebForm_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textFName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textLName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textBirthdate;
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSelect;
    private OpenDental.UI.Button butNew;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSkip;
}


