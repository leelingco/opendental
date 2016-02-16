//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:55 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.FormDatabaseMaintenance;
import OpenDental.FormDatabaseMaintTemp;
import OpenDental.FormInnoDb;
import OpenDental.FormInsPayFix;
import OpenDental.FormXchargeTokenTool;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.DatabaseMaintenance;
import OpenDentBusiness.ProcApptColor;
import OpenDentBusiness.ProcApptColors;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Tooth;

/**
* Summary description for FormCheckDatabase.
*/
public class FormDatabaseMaintenance  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button buttonCheck;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private TextBox textLog = new TextBox();
    private Label label1 = new Label();
    private CheckBox checkShow = new CheckBox();
    private OpenDental.UI.Button butFix;
    private GroupBox groupBox1 = new GroupBox();
    private Label label6 = new Label();
    private OpenDental.UI.Button butConvertDb;
    private Label label5 = new Label();
    private Label label4 = new Label();
    private Label label3 = new Label();
    private Label label2 = new Label();
    private OpenDental.UI.Button butSpecChar;
    private OpenDental.UI.Button butApptProcs;
    private OpenDental.UI.Button butOptimize;
    private OpenDental.UI.Button butInsPayFix;
    private Label label7 = new Label();
    private OpenDental.UI.Button butTokens;
    private OpenDental.UI.Button butPrint;
    private Label label8 = new Label();
    private OpenDental.UI.Button butRemoveNulls;
    /**
    * Holds any text from the log that still needs to be printed when the log spans multiple pages.
    */
    private String LogTextPrint = new String();
    /**
    * 
    */
    public FormDatabaseMaintenance() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.C(this, new System.Windows.Forms.Control[]{ this.textBox1 });
        //this.textBox2
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDatabaseMaintenance.class);
        this.butClose = new OpenDental.UI.Button();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.buttonCheck = new OpenDental.UI.Button();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.textLog = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.checkShow = new System.Windows.Forms.CheckBox();
        this.butPrint = new OpenDental.UI.Button();
        this.butFix = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label8 = new System.Windows.Forms.Label();
        this.butRemoveNulls = new OpenDental.UI.Button();
        this.label7 = new System.Windows.Forms.Label();
        this.butTokens = new OpenDental.UI.Button();
        this.label6 = new System.Windows.Forms.Label();
        this.butConvertDb = new OpenDental.UI.Button();
        this.label5 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.butSpecChar = new OpenDental.UI.Button();
        this.butApptProcs = new OpenDental.UI.Button();
        this.butOptimize = new OpenDental.UI.Button();
        this.butInsPayFix = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(874, 671);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // textBox1
        //
        this.textBox1.BackColor = System.Drawing.SystemColors.Control;
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Location = new System.Drawing.Point(27, 12);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(847, 24);
        this.textBox1.TabIndex = 1;
        this.textBox1.Text = "This tool will check the entire database for any improper settings, inconsistenci" + "es, or corruption.  If any problems are found, they will be fixed.";
        //
        // buttonCheck
        //
        this.buttonCheck.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonCheck.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.buttonCheck.setAutosize(true);
        this.buttonCheck.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonCheck.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonCheck.setCornerRadius(4F);
        this.buttonCheck.Location = new System.Drawing.Point(670, 671);
        this.buttonCheck.Name = "buttonCheck";
        this.buttonCheck.Size = new System.Drawing.Size(75, 26);
        this.buttonCheck.TabIndex = 5;
        this.buttonCheck.Text = "C&heck";
        this.buttonCheck.Click += new System.EventHandler(this.buttonCheck_Click);
        //
        // textLog
        //
        this.textLog.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textLog.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textLog.Location = new System.Drawing.Point(27, 77);
        this.textLog.Multiline = true;
        this.textLog.Name = "textLog";
        this.textLog.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textLog.Size = new System.Drawing.Size(931, 419);
        this.textLog.TabIndex = 14;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(24, 53);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(361, 20);
        this.label1.TabIndex = 15;
        this.label1.Text = "Log (automatically saved in RepairLog.txt if user has permission)";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkShow
        //
        this.checkShow.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShow.Location = new System.Drawing.Point(27, 35);
        this.checkShow.Name = "checkShow";
        this.checkShow.Size = new System.Drawing.Size(847, 20);
        this.checkShow.TabIndex = 16;
        this.checkShow.Text = "Show me everything in the log  (only for advanced users)";
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(532, 671);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(87, 26);
        this.butPrint.TabIndex = 18;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butFix
        //
        this.butFix.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butFix.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butFix.setAutosize(true);
        this.butFix.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFix.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFix.setCornerRadius(4F);
        this.butFix.Location = new System.Drawing.Point(750, 671);
        this.butFix.Name = "butFix";
        this.butFix.Size = new System.Drawing.Size(75, 26);
        this.butFix.TabIndex = 20;
        this.butFix.Text = "Fix";
        this.butFix.Click += new System.EventHandler(this.butFix_Click);
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.groupBox1.Controls.Add(this.label8);
        this.groupBox1.Controls.Add(this.butRemoveNulls);
        this.groupBox1.Controls.Add(this.label7);
        this.groupBox1.Controls.Add(this.butTokens);
        this.groupBox1.Controls.Add(this.label6);
        this.groupBox1.Controls.Add(this.butConvertDb);
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.butSpecChar);
        this.groupBox1.Controls.Add(this.butApptProcs);
        this.groupBox1.Controls.Add(this.butOptimize);
        this.groupBox1.Controls.Add(this.butInsPayFix);
        this.groupBox1.Location = new System.Drawing.Point(27, 502);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(470, 204);
        this.groupBox1.TabIndex = 31;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Database Tools";
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(103, 176);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(355, 20);
        this.label8.TabIndex = 44;
        this.label8.Text = "Replace all null strings with empty strings.";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butRemoveNulls
        //
        this.butRemoveNulls.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRemoveNulls.setAutosize(true);
        this.butRemoveNulls.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRemoveNulls.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRemoveNulls.setCornerRadius(4F);
        this.butRemoveNulls.Location = new System.Drawing.Point(10, 172);
        this.butRemoveNulls.Name = "butRemoveNulls";
        this.butRemoveNulls.Size = new System.Drawing.Size(87, 26);
        this.butRemoveNulls.TabIndex = 43;
        this.butRemoveNulls.Text = "Remove Nulls";
        this.butRemoveNulls.Click += new System.EventHandler(this.butRemoveNulls_Click);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(103, 150);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(355, 20);
        this.label7.TabIndex = 42;
        this.label7.Text = "Validates tokens on file with the X-Charge server.";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butTokens
        //
        this.butTokens.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTokens.setAutosize(true);
        this.butTokens.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTokens.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTokens.setCornerRadius(4F);
        this.butTokens.Location = new System.Drawing.Point(10, 146);
        this.butTokens.Name = "butTokens";
        this.butTokens.Size = new System.Drawing.Size(87, 26);
        this.butTokens.TabIndex = 41;
        this.butTokens.Text = "Tokens";
        this.butTokens.Click += new System.EventHandler(this.butTokens_Click);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(103, 123);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(355, 20);
        this.label6.TabIndex = 40;
        this.label6.Text = "Converts database storage engine to/from InnoDb.";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butConvertDb
        //
        this.butConvertDb.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butConvertDb.setAutosize(true);
        this.butConvertDb.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butConvertDb.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butConvertDb.setCornerRadius(4F);
        this.butConvertDb.Location = new System.Drawing.Point(10, 120);
        this.butConvertDb.Name = "butConvertDb";
        this.butConvertDb.Size = new System.Drawing.Size(87, 26);
        this.butConvertDb.TabIndex = 39;
        this.butConvertDb.Text = "InnoDb";
        this.butConvertDb.Click += new System.EventHandler(this.butConvertDb_Click);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(103, 97);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(355, 20);
        this.label5.TabIndex = 38;
        this.label5.Text = "Removes special characters from appt notes and appt proc descriptions.";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(103, 71);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(355, 20);
        this.label4.TabIndex = 37;
        this.label4.Text = "Fixes procs in the Appt module that aren\'t correctly showing tooth nums.";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(103, 45);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(355, 20);
        this.label3.TabIndex = 36;
        this.label3.Text = "Back up, optimize, and repair tables.";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(103, 19);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(355, 20);
        this.label2.TabIndex = 35;
        this.label2.Text = "Creates checks for insurance payments that are not attached to a check.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butSpecChar
        //
        this.butSpecChar.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSpecChar.setAutosize(true);
        this.butSpecChar.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSpecChar.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSpecChar.setCornerRadius(4F);
        this.butSpecChar.Location = new System.Drawing.Point(10, 94);
        this.butSpecChar.Name = "butSpecChar";
        this.butSpecChar.Size = new System.Drawing.Size(87, 26);
        this.butSpecChar.TabIndex = 33;
        this.butSpecChar.Text = "Spec Char";
        this.butSpecChar.Click += new System.EventHandler(this.butSpecChar_Click);
        //
        // butApptProcs
        //
        this.butApptProcs.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butApptProcs.setAutosize(true);
        this.butApptProcs.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butApptProcs.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butApptProcs.setCornerRadius(4F);
        this.butApptProcs.Location = new System.Drawing.Point(10, 68);
        this.butApptProcs.Name = "butApptProcs";
        this.butApptProcs.Size = new System.Drawing.Size(87, 26);
        this.butApptProcs.TabIndex = 34;
        this.butApptProcs.Text = "Appt Procs";
        this.butApptProcs.Click += new System.EventHandler(this.butApptProcs_Click);
        //
        // butOptimize
        //
        this.butOptimize.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOptimize.setAutosize(true);
        this.butOptimize.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOptimize.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOptimize.setCornerRadius(4F);
        this.butOptimize.Location = new System.Drawing.Point(10, 42);
        this.butOptimize.Name = "butOptimize";
        this.butOptimize.Size = new System.Drawing.Size(87, 26);
        this.butOptimize.TabIndex = 32;
        this.butOptimize.Text = "Optimize";
        this.butOptimize.Click += new System.EventHandler(this.butOptimize_Click);
        //
        // butInsPayFix
        //
        this.butInsPayFix.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butInsPayFix.setAutosize(true);
        this.butInsPayFix.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butInsPayFix.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butInsPayFix.setCornerRadius(4F);
        this.butInsPayFix.Location = new System.Drawing.Point(10, 16);
        this.butInsPayFix.Name = "butInsPayFix";
        this.butInsPayFix.Size = new System.Drawing.Size(87, 26);
        this.butInsPayFix.TabIndex = 31;
        this.butInsPayFix.Text = "Ins Pay Fix";
        this.butInsPayFix.Click += new System.EventHandler(this.butInsPayFix_Click);
        //
        // FormDatabaseMaintenance
        //
        this.AcceptButton = this.buttonCheck;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(982, 707);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butFix);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.checkShow);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textLog);
        this.Controls.Add(this.buttonCheck);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.MinimumSize = new System.Drawing.Size(966, 434);
        this.Name = "FormDatabaseMaintenance";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Database Maintenance";
        this.Load += new System.EventHandler(this.FormDatabaseMaintenance_Load);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formDatabaseMaintenance_Load(Object sender, System.EventArgs e) throws Exception {
    }

    private void butOptimize_Click(Object sender, EventArgs e) throws Exception {
        if (MessageBox.Show(Lan.g("FormDatabaseMaintenance","This tool will backup, optimize, and repair all tables.") + "\r\n" + Lan.g("FormDatabaseMaintenance","Continue?"), Lan.g("FormDatabaseMaintenance","Backup Optimize Repair"), MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        textLog.Text = DateTime.Now.ToString() + "\r\n";
        try
        {
            DatabaseMaintenance.backupRepairAndOptimize();
        }
        catch (Exception ex)
        {
            if (!StringSupport.equals(ex.Message, ""))
            {
                textLog.Text += ex.Message + "\r\n";
            }
             
            textLog.Text += Lan.g("FormDatabaseMaintenance","Backup failed.  Your database has not been altered.") + "\r\n";
        }

        textLog.Text += Lan.g("FormDatabaseMaintenance","Optimization Done");
        saveLogToFile();
        Cursor = Cursors.Default;
    }

    private void buttonCheck_Click(Object sender, System.EventArgs e) throws Exception {
        run(true);
    }

    private void butFix_Click(Object sender, EventArgs e) throws Exception {
        run(false);
    }

    private void run(boolean isCheck) throws Exception {
        Cursor = Cursors.WaitCursor;
        boolean verbose = checkShow.Checked;
        StringBuilder strB = new StringBuilder();
        strB.Append('-', 65);
        textLog.Text = DateTime.Now.ToString() + strB.ToString() + "\r\n";
        Application.DoEvents();
        //#if DEBUG
        //      textLog.Text+=DatabaseMaintenance.AppointmentSpecialCharactersInNotes(verbose,isCheck);
        //      Application.DoEvents();
        //      textLog.Text+=Lan.g("FormDatabaseMaintenance","Done");
        //      SaveLogToFile();
        //      Cursor=Cursors.Default;
        //      return;
        //#endif
        textLog.Text += DatabaseMaintenance.mySQLTables(verbose,isCheck);
        if (!DatabaseMaintenance.getSuccess())
        {
            Cursor = Cursors.Default;
            return ;
        }
         
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.datesNoZeros(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.decimalValues(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.specialCharactersInNotes(verbose,isCheck);
        Application.DoEvents();
        //Now, methods that apply to specific tables----------------------------------------------------------------------------
        textLog.Text += DatabaseMaintenance.appointmentCompleteWithTpAttached(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.appointmentsNoPattern(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.appointmentsNoDateOrProcs(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.appointmentsNoPatients(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.appoitmentNoteTooManyNewLines(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.appointmentPlannedNoPlannedApt(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.appointmentScheduledWithCompletedProcs(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.autoCodeItemsWithNoAutoCode(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.autoCodesDeleteWithNoItems(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.automationTriggersWithNoSheetDefs(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.billingTypesInvalid(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.canadaCarriersCdaMissingInfo(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimDeleteWithNoClaimProcs(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimWithInvalidInsSubNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimWithInvalidPatNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimWriteoffSum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimPaymentCheckAmt(verbose,isCheck);
        //also fixes resulting deposit misbalances.
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimPaymentDetachMissingDeposit(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcDateNotMatchCapComplete(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcDateNotMatchPayment(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcWithInvalidClaimNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcDeleteDuplicateEstimateForSameInsPlan(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcDeleteWithInvalidClaimNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcDeleteMismatchPatNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcDeleteEstimateWithInvalidProcNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcDeleteCapEstimateWithProcComplete(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcEstNoBillIns(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcEstWithInsPaidAmt(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcPatNumMissing(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcProvNumMissing(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcPreauthNotMatchClaim(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcStatusNotMatchClaim(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcWithInvalidClaimPaymentNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.claimProcWriteOffNegative(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.clockEventInFuture(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.documentWithNoCategory(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.eduResourceInvalidDiseaseDefNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.feeDeleteDuplicates(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.groupNoteWithInvalidAptNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.groupNoteWithInvalidProcStatus(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.insPlanInvalidCarrier(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.insPlanNoClaimForm(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.insPlanInvalidNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.insSubInvalidSubscriber(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.insSubNumMismatchPlanNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.journalEntryInvalidAccountNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.labCaseWithInvalidLaboratory(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.laboratoryWithInvalidSlip(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.medicationPatDeleteWithInvalidMedNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.messageButtonDuplicateButtonIndex(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patFieldsDeleteDuplicates(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patientBadGuarantor(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patientBadGuarantorWithAnotherGuarantor(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patientDeletedWithClinicNumSet(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patientsNoClinicSet(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patientPriProvHidden(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patientPriProvMissing(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patientUnDeleteWithBalance(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patPlanDeleteWithInvalidInsSubNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patPlanDeleteWithInvalidPatNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patPlanOrdinalDuplicates(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patPlanOrdinalZeroToOne(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.patPlanOrdinalTwoToOne(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.paymentDetachMissingDeposit(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.paymentMissingPaySplit(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.payPlanChargeGuarantorMatch(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.payPlanChargeProvNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.payPlanSetGuarantorToPatForIns(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.paySplitWithInvalidPayNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.paySplitWithInvalidPayPlanNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.paySplitAttachedToPayPlan(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.perioMeasureWithInvalidIntTooth(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.preferenceAllergiesIndicateNone(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.preferenceDateDepositsStarted(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.preferenceMedicationsIndicateNone(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.preferenceProblemsIndicateNone(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.preferenceTimeCardOvertimeFirstDayOfWeek(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.preferencePracticeProv(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procButtonItemsDeleteWithInvalidAutoCode(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurecodeCategoryNotSet(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurelogAttachedToApptWithProcStatusDeleted(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurelogAttachedToWrongAppts(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurelogAttachedToWrongApptDate(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurelogBaseUnitsZero(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurelogCodeNumInvalid(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurelogLabAttachedToDeletedProc(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurelogProvNumMissing(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurelogToothNums(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurelogTpAttachedToClaim(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurelogTpAttachedToCompleteLabFeesCanada(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.procedurelogUnitQtyZero(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.providerHiddenWithClaimPayments(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.providerWithInvalidFeeSched(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.recallDuplicatesWarn(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.recallTriggerDeleteBadCodeNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.refAttachDeleteWithInvalidReferral(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.schedulesDeleteHiddenProviders(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.schedulesDeleteShort(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.schedulesDeleteProvClosed(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.signalInFuture(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.statementDateRangeMax(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.taskSubscriptionsInvalid(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.timeCardRuleEmployeeNumInvalid(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.userodDuplicateUser(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.userodInvalidClinicNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += DatabaseMaintenance.userodInvalidUserGroupNum(verbose,isCheck);
        Application.DoEvents();
        textLog.Text += Lan.g("FormDatabaseMaintenance","Done");
        saveLogToFile();
        Cursor = Cursors.Default;
    }

    private void saveLogToFile() throws Exception {
        String path = CodeBase.ODFileUtils.CombinePaths(Application.StartupPath, "RepairLog.txt");
        try
        {
            File.AppendAllText(path, textLog.Text);
        }
        catch (SecurityException __dummyCatchVar0)
        {
            MsgBox.show(this,"Log not saved to Repairlog.txt because user does not have permission to access that file.");
        }
        catch (UnauthorizedAccessException __dummyCatchVar1)
        {
            MsgBox.show(this,"Log not saved to Repairlog.txt because user does not have permission to access that file.");
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        LogTextPrint = textLog.Text;
        pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        pd2.DefaultPageSettings.Margins = new Margins(40, 50, 50, 60);
        try
        {
            pd2.Print();
        }
        catch (Exception __dummyCatchVar2)
        {
            MessageBox.Show("Printer not available");
        }
    
    }

    private void pd2_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        //raised for each page to be printed.
        int charsOnPage = 0;
        int linesPerPage = 0;
        Font font = new Font("Courier New", 10);
        RefSupport<int> refVar___0 = new RefSupport<int>();
        RefSupport<int> refVar___1 = new RefSupport<int>();
        ev.Graphics.MeasureString(LogTextPrint, font, ev.MarginBounds.Size, StringFormat.GenericTypographic, refVar___0, refVar___1);
        charsOnPage = refVar___0.getValue();
        linesPerPage = refVar___1.getValue();
        ev.Graphics.DrawString(LogTextPrint, font, Brushes.Black, ev.MarginBounds, StringFormat.GenericTypographic);
        LogTextPrint = LogTextPrint.Substring(charsOnPage);
        ev.HasMorePages = (LogTextPrint.Length > 0);
    }

    private void butTemp_Click(Object sender, EventArgs e) throws Exception {
        FormDatabaseMaintTemp form = new FormDatabaseMaintTemp();
        form.ShowDialog();
    }

    private void butInsPayFix_Click(Object sender, EventArgs e) throws Exception {
        FormInsPayFix formIns = new FormInsPayFix();
        formIns.ShowDialog();
    }

    private void butApptProcs_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"This will fix procedure descriptions in the Appt module that aren't correctly showing tooth numbers.\r\nContinue?"))
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        //The ApptProcDescript region is also in FormProcEdit.SaveAndClose() and FormApptEdit.UpdateToDB()  Make any changes there as well.
        List<long> aptNums = new List<long>();
        Appointment[] aptList = Appointments.GetForPeriod(DateTime.Now.Date.AddMonths(-6), DateTime.MaxValue.AddDays(-10));
        for (int i = 0;i < aptList.Length;i++)
        {
            aptNums.Add(aptList[i].AptNum);
        }
        for (int i = 0;i < aptList.Length;i++)
        {
            //This gets the list of procedures in the correct order.
            DataTable procTable = Appointments.GetProcTable(aptList[i].PatNum.ToString(), aptList[i].AptNum.ToString(), ((int)aptList[i].AptStatus).ToString(), aptList[i].AptDateTime.ToString());
            Appointment newApt = aptList[i].Clone();
            newApt.ProcDescript = "";
            newApt.ProcsColored = "";
            int count = 0;
            for (int j = 0;j < procTable.Rows.Count;j++)
            {
                if (!StringSupport.equals(procTable.Rows[j]["attached"].ToString(), "1"))
                {
                    continue;
                }
                 
                String procDescOne = "";
                String procCode = procTable.Rows[j]["ProcCode"].ToString();
                if (count > 0)
                {
                    newApt.ProcDescript += ", ";
                }
                 
                Rows.INDEXER.INDEXER.APPLY __dummyScrutVar0 = procTable.Rows[j]["TreatArea"].ToString();
                if (__dummyScrutVar0.equals("1"))
                {
                    //TreatmentArea.Surf:
                    procDescOne += "#" + Tooth.GetToothLabel(procTable.Rows[j]["ToothNum"].ToString()) + "-" + procTable.Rows[j]["Surf"].ToString() + "-";
                }
                else //""#12-MOD-"
                if (__dummyScrutVar0.equals("2"))
                {
                    //TreatmentArea.Tooth:
                    procDescOne += "#" + Tooth.GetToothLabel(procTable.Rows[j]["ToothNum"].ToString()) + "-";
                }
                else //"#12-"
                //area 3 or 0 (mouth)
                if (__dummyScrutVar0.equals("4"))
                {
                    //TreatmentArea.Quad:
                    procDescOne += procTable.Rows[j]["Surf"].ToString() + "-";
                }
                else //"UL-"
                if (__dummyScrutVar0.equals("5"))
                {
                    //TreatmentArea.Sextant:
                    procDescOne += "S" + procTable.Rows[j]["Surf"].ToString() + "-";
                }
                else //"S2-"
                if (__dummyScrutVar0.equals("6"))
                {
                    //TreatmentArea.Arch:
                    procDescOne += procTable.Rows[j]["Surf"].ToString() + "-";
                }
                else //"U-"
                if (__dummyScrutVar0.equals("7"))
                {
                }
                else
                {
                }      
                //TreatmentArea.ToothRange:
                //strLine+=table.Rows[j][13].ToString()+" ";//don't show range
                procDescOne += procTable.Rows[j]["AbbrDesc"].ToString();
                newApt.ProcDescript += procDescOne;
                //Color and previous date are determined by ProcApptColor object
                ProcApptColor pac = ProcApptColors.getMatch(procCode);
                System.Drawing.Color pColor = System.Drawing.Color.Black;
                String prevDateString = "";
                if (pac != null)
                {
                    pColor = pac.ColorText;
                    if (pac.ShowPreviousDate)
                    {
                        prevDateString = Procedures.getRecentProcDateString(newApt.PatNum,newApt.AptDateTime,pac.CodeRange);
                        if (!StringSupport.equals(prevDateString, ""))
                        {
                            prevDateString = " (" + prevDateString + ")";
                        }
                         
                    }
                     
                }
                 
                newApt.ProcsColored += "<span color=\"" + pColor.ToArgb().ToString() + "\">" + procDescOne + prevDateString + "</span>";
                count++;
            }
            Appointments.Update(newApt, aptList[i]);
        }
        Cursor = Cursors.Default;
        MsgBox.show(this,"Done. Please refresh Appt module to see the changes.");
    }

    private void butSpecChar_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"This is only used if your mobile synch or middle tier is failing.  This cannot be undone.  Do you wish to continue?"))
        {
            return ;
        }
         
        InputBox box = new InputBox("In our online manual, on the database maintenance page, look for the password and enter it below.");
        if (box.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        if (!StringSupport.equals(box.textResult.Text, "fix"))
        {
            MessageBox.Show("Wrong password.");
            return ;
        }
         
        StringBuilder strB = new StringBuilder();
        strB.Append('-', 65);
        textLog.Text = DateTime.Now.ToString() + strB.ToString() + "\r\n";
        Application.DoEvents();
        DatabaseMaintenance.fixSpecialCharacters();
        textLog.Text += Lan.g("FormDatabaseMaintenance","Done");
        Application.DoEvents();
        MsgBox.show(this,"Special Characters have been removed from Appointment Notes, Appointment Procedure Descriptions, Patient Address Notes, and Patient Family Financial Urgent Notes.  Invalid null characters have been removed from Adjustment Notes, Payment Notes, and Definition Names.");
    }

    private void butRemoveNulls_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"This will replace ALL null strings in your database with empty strings.  This cannot be undone.  Do you wish to continue?"))
        {
            return ;
        }
         
        MessageBox.Show(Lan.g(this,"Number of null strings replaced with empty strings") + ":" + DatabaseMaintenance.removeNullStrings());
    }

    private void butConvertDb_Click(Object sender, EventArgs e) throws Exception {
        FormInnoDb form = new FormInnoDb();
        form.ShowDialog();
    }

    private void butTokens_Click(Object sender, EventArgs e) throws Exception {
        FormXchargeTokenTool FormXCT = new FormXchargeTokenTool();
        FormXCT.ShowDialog();
    }

}


