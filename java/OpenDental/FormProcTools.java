//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:34 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormProcCodes;
import OpenDental.FormProcTools;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.AutoCodes;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.ProcButtons;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.RecallTypes;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.ToothPaintingType;
import OpenDentBusiness.TreatmentArea;

/**
* Summary description for FormBasicTemplate.
*/
public class FormProcTools  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private CheckBox checkAutocodes = new CheckBox();
    private CheckBox checkTcodes = new CheckBox();
    private CheckBox checkDcodes = new CheckBox();
    private CheckBox checkNcodes = new CheckBox();
    private Label label5 = new Label();
    private CheckBox checkProcButtons = new CheckBox();
    private OpenDental.UI.Button butRun;
    public boolean Changed = new boolean();
    private CheckBox checkApptProcsQuickAdd = new CheckBox();
    private CheckBox checkRecallTypes = new CheckBox();
    /**
    * The actual list of ADA codes as published by the ADA.  Only available on our compiled releases.  There is no other way to get this info.
    */
    private List<ProcedureCode> codeList = new List<ProcedureCode>();
    /**
    * 
    */
    public FormProcTools() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProcTools.class);
        this.checkAutocodes = new System.Windows.Forms.CheckBox();
        this.checkTcodes = new System.Windows.Forms.CheckBox();
        this.checkDcodes = new System.Windows.Forms.CheckBox();
        this.checkNcodes = new System.Windows.Forms.CheckBox();
        this.label5 = new System.Windows.Forms.Label();
        this.checkProcButtons = new System.Windows.Forms.CheckBox();
        this.checkApptProcsQuickAdd = new System.Windows.Forms.CheckBox();
        this.checkRecallTypes = new System.Windows.Forms.CheckBox();
        this.butRun = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // checkAutocodes
        //
        this.checkAutocodes.Location = new System.Drawing.Point(15, 190);
        this.checkAutocodes.Name = "checkAutocodes";
        this.checkAutocodes.Size = new System.Drawing.Size(646, 36);
        this.checkAutocodes.TabIndex = 43;
        this.checkAutocodes.Text = "Autocodes - Deletes all current autocodes and then adds the default autocodes.  P" + "rocedure codes must have already been entered or they cannot be added as an auto" + "code.";
        this.checkAutocodes.UseVisualStyleBackColor = true;
        //
        // checkTcodes
        //
        this.checkTcodes.Location = new System.Drawing.Point(15, 67);
        this.checkTcodes.Name = "checkTcodes";
        this.checkTcodes.Size = new System.Drawing.Size(646, 36);
        this.checkTcodes.TabIndex = 44;
        this.checkTcodes.Text = "T codes - Remove temp codes, codes that start with \"T\", which were only needed fo" + "r the trial version.  If a T code has already been used, then this moves it to t" + "he obsolete category.";
        this.checkTcodes.UseVisualStyleBackColor = true;
        //
        // checkDcodes
        //
        this.checkDcodes.Checked = true;
        this.checkDcodes.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkDcodes.Location = new System.Drawing.Point(15, 149);
        this.checkDcodes.Name = "checkDcodes";
        this.checkDcodes.Size = new System.Drawing.Size(646, 36);
        this.checkDcodes.TabIndex = 45;
        this.checkDcodes.Text = "D codes - Add any missing 2014 ADA codes and fix descriptions of existing codes. " + " This option does not work in the trial version or compiled version.";
        this.checkDcodes.UseVisualStyleBackColor = true;
        //
        // checkNcodes
        //
        this.checkNcodes.Location = new System.Drawing.Point(15, 108);
        this.checkNcodes.Name = "checkNcodes";
        this.checkNcodes.Size = new System.Drawing.Size(646, 36);
        this.checkNcodes.TabIndex = 46;
        this.checkNcodes.Text = "N codes - Add any missing no-fee codes.";
        this.checkNcodes.UseVisualStyleBackColor = true;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(12, 9);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(666, 54);
        this.label5.TabIndex = 48;
        this.label5.Text = resources.GetString("label5.Text");
        //
        // checkProcButtons
        //
        this.checkProcButtons.Location = new System.Drawing.Point(15, 231);
        this.checkProcButtons.Name = "checkProcButtons";
        this.checkProcButtons.Size = new System.Drawing.Size(646, 36);
        this.checkProcButtons.TabIndex = 49;
        this.checkProcButtons.Text = resources.GetString("checkProcButtons.Text");
        this.checkProcButtons.UseVisualStyleBackColor = true;
        //
        // checkApptProcsQuickAdd
        //
        this.checkApptProcsQuickAdd.Location = new System.Drawing.Point(15, 272);
        this.checkApptProcsQuickAdd.Name = "checkApptProcsQuickAdd";
        this.checkApptProcsQuickAdd.Size = new System.Drawing.Size(646, 36);
        this.checkApptProcsQuickAdd.TabIndex = 51;
        this.checkApptProcsQuickAdd.Text = "Appt Procs Quick Add - This is the list of procedures that you pick from within t" + "he appt edit window.  This resets the list to default.";
        this.checkApptProcsQuickAdd.UseVisualStyleBackColor = true;
        //
        // checkRecallTypes
        //
        this.checkRecallTypes.Location = new System.Drawing.Point(15, 314);
        this.checkRecallTypes.Name = "checkRecallTypes";
        this.checkRecallTypes.Size = new System.Drawing.Size(646, 36);
        this.checkRecallTypes.TabIndex = 52;
        this.checkRecallTypes.Text = "Recall Types - Resets the recall types and triggers to default.  Replaces any T c" + "odes with D codes.";
        this.checkRecallTypes.UseVisualStyleBackColor = true;
        //
        // butRun
        //
        this.butRun.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRun.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butRun.setAutosize(true);
        this.butRun.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRun.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRun.setCornerRadius(4F);
        this.butRun.Location = new System.Drawing.Point(477, 381);
        this.butRun.Name = "butRun";
        this.butRun.Size = new System.Drawing.Size(82, 26);
        this.butRun.TabIndex = 50;
        this.butRun.Text = "Run Now";
        this.butRun.Click += new System.EventHandler(this.butRun_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(586, 381);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(82, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormProcTools
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(698, 431);
        this.Controls.Add(this.checkRecallTypes);
        this.Controls.Add(this.checkApptProcsQuickAdd);
        this.Controls.Add(this.butRun);
        this.Controls.Add(this.checkProcButtons);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.checkNcodes);
        this.Controls.Add(this.checkDcodes);
        this.Controls.Add(this.checkTcodes);
        this.Controls.Add(this.checkAutocodes);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProcTools";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Procedure Code Tools";
        this.Load += new System.EventHandler(this.FormProcTools_Load);
        this.ResumeLayout(false);
    }

    private void formProcTools_Load(Object sender, EventArgs e) throws Exception {
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            //Tcodes remain enabled
            //Ncodes remain enabled
            checkDcodes.Enabled = false;
            //todo: enable this when ready
            checkDcodes.Text = "CDA codes - Add any missing 2012 CDA codes.  This option does not work in the trial version or compiled version.";
            checkAutocodes.Enabled = false;
            checkProcButtons.Enabled = false;
            checkApptProcsQuickAdd.Enabled = false;
            checkRecallTypes.Enabled = false;
            checkRecallTypes.Text = "Recall Types - Resets the recall types and triggers to default.  Replaces any T codes with CDA codes.";
            codeList = null;
        }
        else
        {
            //Is only filled when the code tool is run.
            //USA
            codeList = CDT.Class1.GetADAcodes();
        } 
        if (codeList == null || codeList.Count == 0)
        {
            checkDcodes.Checked = false;
            checkDcodes.Enabled = false;
        }
         
    }

    private void butUncheck_Click(Object sender, EventArgs e) throws Exception {
        checkTcodes.Checked = false;
        checkNcodes.Checked = false;
        checkDcodes.Checked = false;
        checkAutocodes.Checked = false;
        checkProcButtons.Checked = false;
        checkApptProcsQuickAdd.Checked = false;
        checkRecallTypes.Checked = false;
    }

    private void canadaDownloadProcedureCodes() throws Exception {
        Cursor = Cursors.WaitCursor;
        codeList = new List<ProcedureCode>();
        String url = "http://www.opendental.com/feescanada/procedurecodes.txt";
        String tempFile = Path.GetTempFileName();
        WebClient myWebClient = new WebClient();
        try
        {
            myWebClient.DownloadFile(url, tempFile);
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Failed to download fee schedule file") + ": " + ex.Message);
            Cursor = Cursors.Default;
            return ;
        }

        String codeData = File.ReadAllText(tempFile);
        File.Delete(tempFile);
        String[] codeLines = codeData.Split('\n');
        for (int i = 0;i < codeLines.Length;i++)
        {
            String[] fields = codeLines[i].Split('\t');
            if (fields.Length < 1)
            {
                continue;
            }
             
            //Skip blank lines if they exist.
            ProcedureCode procCode = new ProcedureCode();
            procCode.ProcCode = PIn.String(fields[0]);
            //0 ProcCode
            procCode.Descript = PIn.String(fields[1]);
            //1 Description
            procCode.TreatArea = (TreatmentArea)PIn.Int(fields[2]);
            //2 TreatArea
            procCode.NoBillIns = PIn.Bool(fields[3]);
            //3 NoBillIns
            procCode.IsProsth = PIn.Bool(fields[4]);
            //4 IsProsth
            procCode.IsHygiene = PIn.Bool(fields[5]);
            //5 IsHygiene
            procCode.PaintType = (ToothPaintingType)PIn.Int(fields[6]);
            //6 PaintType
            procCode.setProcCatDescript(PIn.String(fields[7]));
            //7 ProcCatDescript
            procCode.ProcTime = PIn.String(fields[8]);
            //8 ProcTime
            procCode.AbbrDesc = PIn.String(fields[9]);
            //9 AbbrDesc
            codeList.Add(procCode);
        }
        Cursor = Cursors.Default;
    }

    private void butRun_Click(Object sender, EventArgs e) throws Exception {
        if (!checkTcodes.Checked && !checkNcodes.Checked && !checkDcodes.Checked && !checkAutocodes.Checked && !checkProcButtons.Checked && !checkApptProcsQuickAdd.Checked && !checkRecallTypes.Checked)
        {
            MsgBox.show(this,"Please select at least one tool first.");
            return ;
        }
         
        Changed = true;
        int rowsInserted = 0;
        if (checkTcodes.Checked)
        {
            ProcedureCodes.tcodesClear();
            //yes, this really does refresh before moving on.
            DataValid.setInvalid(InvalidType.Defs,InvalidType.ProcCodes,InvalidType.Fees);
        }
         
        if (checkNcodes.Checked)
        {
            try
            {
                rowsInserted += FormProcCodes.importProcCodes("",null,Resources.getNoFeeProcCodes());
            }
            catch (ApplicationException ex)
            {
                MessageBox.Show(ex.Message);
            }

            DataValid.setInvalid(InvalidType.Defs,InvalidType.ProcCodes,InvalidType.Fees);
        }
         
        //fees are included because they are grouped by defs.
        if (checkDcodes.Checked)
        {
            try
            {
                if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                {
                    //Canadian. en-CA or fr-CA
                    if (codeList == null)
                    {
                        canadaDownloadProcedureCodes();
                    }
                     
                }
                 
                //Fill codeList with Canadian codes
                rowsInserted += FormProcCodes.importProcCodes("",codeList,"");
                int descriptionsFixed = ProcedureCodes.resetADAdescriptions();
                MessageBox.Show("Procedure code descriptions updated: " + descriptionsFixed.ToString());
            }
            catch (ApplicationException ex)
            {
                MessageBox.Show(ex.Message);
            }

            DataValid.setInvalid(InvalidType.Defs,InvalidType.ProcCodes,InvalidType.Fees);
        }
         
        if (checkNcodes.Checked || checkDcodes.Checked)
        {
            MessageBox.Show("Procedure codes inserted: " + rowsInserted);
        }
         
        if (checkAutocodes.Checked)
        {
            AutoCodes.setToDefault();
            DataValid.setInvalid(InvalidType.AutoCodes);
        }
         
        if (checkProcButtons.Checked)
        {
            ProcButtons.setToDefault();
            DataValid.setInvalid(InvalidType.ProcButtons,InvalidType.Defs);
        }
         
        if (checkApptProcsQuickAdd.Checked)
        {
            ProcedureCodes.resetApptProcsQuickAdd();
            DataValid.setInvalid(InvalidType.Defs);
        }
         
        if (checkRecallTypes.Checked)
        {
            RecallTypes.setToDefault();
            DataValid.setInvalid(InvalidType.RecallTypes,InvalidType.Prefs);
        }
         
        MessageBox.Show(Lan.g(this,"Done."));
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "New Customer Procedure codes tool was run.");
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


