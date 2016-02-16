//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:07 PM
//

package OpenDental;

import OpenDental.FormRpProcNote;
import OpenDental.POut;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.ReportingOld2.FormReportLikeCrystal;
import OpenDental.ReportingOld2.ReportLikeCrystal;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProcStat;

//using System.IO;
//using System.Text;
//using System.Xml.Serialization;
/**
* Summary description for FormBasicTemplate.
*/
public class FormRpProcNote  extends System.Windows.Forms.Form 
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
    public FormRpProcNote() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
    }

    //Lan.C("All", new System.Windows.Forms.Control[] {
    //	butOK,
    //	butCancel,
    //});
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpProcNote.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
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
        this.butCancel.Location = new System.Drawing.Point(510, 338);
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
        this.butOK.Location = new System.Drawing.Point(510, 297);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormRpProcNote
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(605, 386);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpProcNote";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Incomplete Procedure Notes Report";
        this.Load += new System.EventHandler(this.FormRpProcNote_Load);
        this.ResumeLayout(false);
    }

    private void formRpProcNote_Load(Object sender, System.EventArgs e) throws Exception {
        //the user never sees this dialog.
        executeReport();
        Close();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
    }

    private void executeReport() throws Exception {
        ReportLikeCrystal report = new ReportLikeCrystal();
        report.addTitle("INCOMPLETE PROCEDURE NOTES");
        report.addSubTitle(PrefC.getString(PrefName.PracticeTitle));
        //looks for ""
        report.setQuery("(SELECT procedurelog.ProcDate,\r\n" + 
        "\t\t\t\tCONCAT(CONCAT(patient.LName,\', \'),patient.FName),\r\n" + 
        "\t\t\t\tprocedurecode.ProcCode,procedurecode.Descript,\r\n" + 
        "\t\t\t\tprocedurelog.ToothNum,procedurelog.Surf\r\n" + 
        "\t\t\t\tFROM procedurelog,patient,procedurecode,procnote n1\r\n" + 
        "\t\t\t\tWHERE procedurelog.PatNum = patient.PatNum\r\n" + 
        "\t\t\t\tAND procedurelog.CodeNum = procedurecode.CodeNum\r\n" + 
        "\t\t\t\tAND procedurelog.ProcStatus = " + POut.Int(((Enum)ProcStat.C).ordinal()) + "\r\n" + 
        "\t\t\t\tAND procedurelog.ProcNum=n1.ProcNum " + "AND n1.Note LIKE '%\"\"%' " + "AND n1.EntryDateTime=(SELECT MAX(n2.EntryDateTime)\r\n" + 
        "\t\t\t\tFROM procnote n2\r\n" + 
        "\t\t\t\tWHERE n1.ProcNum = n2.ProcNum))\r\n" + 
        "\t\t\t\tUNION ALL\r\n" + 
        "\t\t\t\t(SELECT procedurelog.ProcDate,\r\n" + 
        "\t\t\t\tCONCAT(CONCAT(patient.LName,\', \'),patient.FName),\r\n" + 
        "\t\t\t\tprocedurecode.ProcCode,procedurecode.Descript,\r\n" + 
        "\t\t\t\tprocedurelog.ToothNum,procedurelog.Surf\r\n" + 
        "\t\t\t\tFROM procedurelog,patient,procedurecode,procnote n1\r\n" + 
        "\t\t\t\tWHERE procedurelog.PatNum = patient.PatNum\r\n" + 
        "\t\t\t\tAND procedurelog.CodeNum = procedurecode.CodeNum\r\n" + 
        "\t\t\t\tAND procedurelog.ProcStatus = " + POut.Int(((Enum)ProcStat.EC).ordinal()) + "\r\n" + 
        "\t\t\t\tAND procedurelog.ProcNum=n1.ProcNum " + "AND n1.Note LIKE '%\"\"%' " + "AND n1.EntryDateTime=(SELECT MAX(n2.EntryDateTime)\r\n" + 
        "\t\t\t\tFROM procnote n2\r\n" + 
        "\t\t\t\tWHERE n1.ProcNum = n2.ProcNum)\r\n" + 
        "\t\t\t\tAND procedurecode.ProcCode=\'~GRP~\')\r\n" + 
        "\t\t\t\tORDER BY ProcDate");
        //looks for ""
        report.addColumn("Date",80,FieldValueType.Date);
        report.addColumn("Patient",120,FieldValueType.String);
        report.addColumn("Code",50,FieldValueType.String);
        report.addColumn("Description",120,FieldValueType.String);
        report.addColumn("Tth",30,FieldValueType.String);
        report.addColumn("Surf",40,FieldValueType.String);
        report.addPageNum();
        if (!report.submitQuery())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        FormReportLikeCrystal FormR = new FormReportLikeCrystal(report);
        FormR.ShowDialog();
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


