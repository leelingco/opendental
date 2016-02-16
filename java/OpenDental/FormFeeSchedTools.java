//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:10 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormFeeSchedPickAuthOntario;
import OpenDental.FormFeeSchedPickRemote;
import OpenDental.FormFeeSchedTools;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDentBusiness.Fees;
import OpenDentBusiness.FeeSched;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.FeeScheduleType;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;
import OpenDentBusiness.SecurityLogs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormFeeSchedTools  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCopy;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.ComboBox comboCopyFrom = new System.Windows.Forms.ComboBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butClear;
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butIncrease;
    private System.Windows.Forms.TextBox textPercent = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox4 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioDollar = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioDime = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioPenny = new System.Windows.Forms.RadioButton();
    private GroupBox groupBox5 = new GroupBox();
    private OpenDental.UI.Button butExport;
    private OpenDental.UI.Button butImport;
    private GroupBox groupBox6 = new GroupBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butUpdate;
    private Label label5 = new Label();
    private OpenDental.UI.Button butImportEcw;
    private OpenDental.UI.Button butImportCanada;
    /**
    * The defNum of the fee schedule that is currently displayed in the main window.
    */
    private long SchedNum = new long();
    /**
    * Supply the fee schedule num(DefNum) to which all these changes will apply
    */
    public FormFeeSchedTools(long schedNum) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        SchedNum = schedNum;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormFeeSchedTools.class);
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butCopy = new OpenDental.UI.Button();
        this.comboCopyFrom = new System.Windows.Forms.ComboBox();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.butClear = new OpenDental.UI.Button();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.label5 = new System.Windows.Forms.Label();
        this.groupBox4 = new System.Windows.Forms.GroupBox();
        this.radioPenny = new System.Windows.Forms.RadioButton();
        this.radioDime = new System.Windows.Forms.RadioButton();
        this.radioDollar = new System.Windows.Forms.RadioButton();
        this.label3 = new System.Windows.Forms.Label();
        this.butIncrease = new OpenDental.UI.Button();
        this.textPercent = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.groupBox5 = new System.Windows.Forms.GroupBox();
        this.butImportCanada = new OpenDental.UI.Button();
        this.butImportEcw = new OpenDental.UI.Button();
        this.butImport = new OpenDental.UI.Button();
        this.butExport = new OpenDental.UI.Button();
        this.groupBox6 = new System.Windows.Forms.GroupBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butUpdate = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.groupBox4.SuspendLayout();
        this.groupBox5.SuspendLayout();
        this.groupBox6.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butCopy);
        this.groupBox1.Controls.Add(this.comboCopyFrom);
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(15, 80);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(205, 99);
        this.groupBox1.TabIndex = 2;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Copy";
        //
        // butCopy
        //
        this.butCopy.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopy.setAutosize(true);
        this.butCopy.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopy.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopy.setCornerRadius(4F);
        this.butCopy.Location = new System.Drawing.Point(10, 66);
        this.butCopy.Name = "butCopy";
        this.butCopy.Size = new System.Drawing.Size(75, 24);
        this.butCopy.TabIndex = 4;
        this.butCopy.Text = "Copy";
        this.butCopy.Click += new System.EventHandler(this.butCopy_Click);
        //
        // comboCopyFrom
        //
        this.comboCopyFrom.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCopyFrom.Location = new System.Drawing.Point(11, 40);
        this.comboCopyFrom.Name = "comboCopyFrom";
        this.comboCopyFrom.Size = new System.Drawing.Size(180, 21);
        this.comboCopyFrom.TabIndex = 0;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(10, 20);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 18);
        this.label1.TabIndex = 3;
        this.label1.Text = "From";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.butClear);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(15, 11);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(205, 59);
        this.groupBox2.TabIndex = 3;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Clear";
        //
        // butClear
        //
        this.butClear.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClear.setAutosize(true);
        this.butClear.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClear.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClear.setCornerRadius(4F);
        this.butClear.Location = new System.Drawing.Point(10, 25);
        this.butClear.Name = "butClear";
        this.butClear.Size = new System.Drawing.Size(75, 24);
        this.butClear.TabIndex = 4;
        this.butClear.Text = "Clear";
        this.butClear.Click += new System.EventHandler(this.butClear_Click);
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.label5);
        this.groupBox3.Controls.Add(this.groupBox4);
        this.groupBox3.Controls.Add(this.label3);
        this.groupBox3.Controls.Add(this.butIncrease);
        this.groupBox3.Controls.Add(this.textPercent);
        this.groupBox3.Controls.Add(this.label2);
        this.groupBox3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox3.Location = new System.Drawing.Point(236, 11);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(205, 168);
        this.groupBox3.TabIndex = 4;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Increase by %";
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(92, 142);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(90, 18);
        this.label5.TabIndex = 11;
        this.label5.Text = "(or decrease)";
        //
        // groupBox4
        //
        this.groupBox4.Controls.Add(this.radioPenny);
        this.groupBox4.Controls.Add(this.radioDime);
        this.groupBox4.Controls.Add(this.radioDollar);
        this.groupBox4.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox4.Location = new System.Drawing.Point(13, 47);
        this.groupBox4.Name = "groupBox4";
        this.groupBox4.Size = new System.Drawing.Size(169, 78);
        this.groupBox4.TabIndex = 10;
        this.groupBox4.TabStop = false;
        this.groupBox4.Text = "Round to nearest";
        //
        // radioPenny
        //
        this.radioPenny.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioPenny.Location = new System.Drawing.Point(14, 52);
        this.radioPenny.Name = "radioPenny";
        this.radioPenny.Size = new System.Drawing.Size(104, 17);
        this.radioPenny.TabIndex = 2;
        this.radioPenny.Text = "$.01";
        //
        // radioDime
        //
        this.radioDime.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioDime.Location = new System.Drawing.Point(14, 35);
        this.radioDime.Name = "radioDime";
        this.radioDime.Size = new System.Drawing.Size(104, 17);
        this.radioDime.TabIndex = 1;
        this.radioDime.Text = "$.10";
        //
        // radioDollar
        //
        this.radioDollar.Checked = true;
        this.radioDollar.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioDollar.Location = new System.Drawing.Point(14, 18);
        this.radioDollar.Name = "radioDollar";
        this.radioDollar.Size = new System.Drawing.Size(104, 17);
        this.radioDollar.TabIndex = 0;
        this.radioDollar.TabStop = true;
        this.radioDollar.Text = "$1";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(92, 24);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(109, 19);
        this.label3.TabIndex = 6;
        this.label3.Text = "for example: 5";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butIncrease
        //
        this.butIncrease.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butIncrease.setAutosize(true);
        this.butIncrease.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butIncrease.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butIncrease.setCornerRadius(4F);
        this.butIncrease.Location = new System.Drawing.Point(11, 135);
        this.butIncrease.Name = "butIncrease";
        this.butIncrease.Size = new System.Drawing.Size(75, 24);
        this.butIncrease.TabIndex = 4;
        this.butIncrease.Text = "Increase";
        this.butIncrease.Click += new System.EventHandler(this.butIncrease_Click);
        //
        // textPercent
        //
        this.textPercent.Location = new System.Drawing.Point(42, 23);
        this.textPercent.Name = "textPercent";
        this.textPercent.Size = new System.Drawing.Size(46, 20);
        this.textPercent.TabIndex = 5;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(3, 23);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(38, 19);
        this.label2.TabIndex = 5;
        this.label2.Text = "%";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox5
        //
        this.groupBox5.Controls.Add(this.butImportCanada);
        this.groupBox5.Controls.Add(this.butImportEcw);
        this.groupBox5.Controls.Add(this.butImport);
        this.groupBox5.Controls.Add(this.butExport);
        this.groupBox5.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox5.Location = new System.Drawing.Point(15, 188);
        this.groupBox5.Name = "groupBox5";
        this.groupBox5.Size = new System.Drawing.Size(205, 121);
        this.groupBox5.TabIndex = 5;
        this.groupBox5.TabStop = false;
        this.groupBox5.Text = "Export/Import";
        //
        // butImportCanada
        //
        this.butImportCanada.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImportCanada.setAutosize(true);
        this.butImportCanada.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImportCanada.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImportCanada.setCornerRadius(4F);
        this.butImportCanada.Location = new System.Drawing.Point(107, 85);
        this.butImportCanada.Name = "butImportCanada";
        this.butImportCanada.Size = new System.Drawing.Size(84, 24);
        this.butImportCanada.TabIndex = 7;
        this.butImportCanada.Text = "Import Canada";
        this.butImportCanada.Click += new System.EventHandler(this.butImportCanada_Click);
        //
        // butImportEcw
        //
        this.butImportEcw.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImportEcw.setAutosize(true);
        this.butImportEcw.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImportEcw.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImportEcw.setCornerRadius(4F);
        this.butImportEcw.Location = new System.Drawing.Point(107, 55);
        this.butImportEcw.Name = "butImportEcw";
        this.butImportEcw.Size = new System.Drawing.Size(84, 24);
        this.butImportEcw.TabIndex = 6;
        this.butImportEcw.Text = "Import eCW";
        this.butImportEcw.Click += new System.EventHandler(this.butImportEcw_Click);
        //
        // butImport
        //
        this.butImport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImport.setAutosize(true);
        this.butImport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImport.setCornerRadius(4F);
        this.butImport.Location = new System.Drawing.Point(107, 25);
        this.butImport.Name = "butImport";
        this.butImport.Size = new System.Drawing.Size(84, 24);
        this.butImport.TabIndex = 5;
        this.butImport.Text = "Import";
        this.butImport.Click += new System.EventHandler(this.butImport_Click);
        //
        // butExport
        //
        this.butExport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExport.setAutosize(true);
        this.butExport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExport.setCornerRadius(4F);
        this.butExport.Location = new System.Drawing.Point(10, 25);
        this.butExport.Name = "butExport";
        this.butExport.Size = new System.Drawing.Size(75, 24);
        this.butExport.TabIndex = 4;
        this.butExport.Text = "Export";
        this.butExport.Click += new System.EventHandler(this.butExport_Click);
        //
        // groupBox6
        //
        this.groupBox6.Controls.Add(this.label4);
        this.groupBox6.Controls.Add(this.butUpdate);
        this.groupBox6.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox6.Location = new System.Drawing.Point(236, 188);
        this.groupBox6.Name = "groupBox6";
        this.groupBox6.Size = new System.Drawing.Size(205, 59);
        this.groupBox6.TabIndex = 6;
        this.groupBox6.TabStop = false;
        this.groupBox6.Text = "Global Update Fees";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(91, 32);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(91, 18);
        this.label4.TabIndex = 5;
        this.label4.Text = "for all patients";
        //
        // butUpdate
        //
        this.butUpdate.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUpdate.setAutosize(true);
        this.butUpdate.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUpdate.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUpdate.setCornerRadius(4F);
        this.butUpdate.Location = new System.Drawing.Point(10, 25);
        this.butUpdate.Name = "butUpdate";
        this.butUpdate.Size = new System.Drawing.Size(75, 24);
        this.butUpdate.TabIndex = 4;
        this.butUpdate.Text = "Update";
        this.butUpdate.Click += new System.EventHandler(this.butUpdate_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(364, 305);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Cancel";
        this.butClose.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormFeeSchedTools
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(464, 356);
        this.Controls.Add(this.groupBox6);
        this.Controls.Add(this.groupBox5);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormFeeSchedTools";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Fee Tools";
        this.Load += new System.EventHandler(this.FormFeeSchedTools_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox3.ResumeLayout(false);
        this.groupBox3.PerformLayout();
        this.groupBox4.ResumeLayout(false);
        this.groupBox5.ResumeLayout(false);
        this.groupBox6.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formFeeSchedTools_Load(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < FeeSchedC.getListShort().Count;i++)
        {
            comboCopyFrom.Items.Add(FeeSchedC.getListShort()[i].Description);
        }
        if (!Programs.isEnabled(ProgramName.eClinicalWorks))
        {
            butImportEcw.Visible = false;
        }
         
        if (!CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            butImportCanada.Visible = false;
        }
         
    }

    private void butClear_Click(Object sender, System.EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"This will clear all values from the current fee schedule showing in the main window.  Are you sure you want to continue?"))
        {
            return ;
        }
         
        Fees.clearFeeSched(SchedNum);
        DialogResult = DialogResult.OK;
    }

    private void butCopy_Click(Object sender, System.EventArgs e) throws Exception {
        if (comboCopyFrom.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please pick a fee schedule first.");
            return ;
        }
         
        if (!MsgBox.show(this,true,"This will overwrite all values of the current fee schedule showing in the main window.  Are you sure you want to continue?"))
        {
            return ;
        }
         
        //clear current
        Fees.clearFeeSched(SchedNum);
        //copy any values over
        Fees.CopyFees(FeeSchedC.getListShort()[comboCopyFrom.SelectedIndex].FeeSchedNum, SchedNum);
        for (int i = 0;i < Fees.getListt().Count;i++)
        {
            //ignore all but the OLD fee schedule.
            if (Fees.getListt()[i].FeeSched != FeeSchedC.getListShort()[comboCopyFrom.SelectedIndex].FeeSchedNum)
            {
                continue;
            }
             
            SecurityLogs.MakeLogEntry(Permissions.ProcFeeEdit, 0, Lan.g(this,"Procedure") + ": " + ProcedureCodes.GetStringProcCode(Fees.getListt()[i].CodeNum) + ", " + Lan.g(this,"Fee") + ": " + Fees.getListt()[i].Amount.ToString("c") + ", " + Lan.g(this,"Fee Schedule") + ": " + FeeScheds.GetDescription(Fees.getListt()[i].FeeSched) + ". " + Lan.g(this,"Fee copied from") + " " + FeeScheds.GetDescription(FeeSchedC.getListShort()[comboCopyFrom.SelectedIndex].FeeSchedNum) + " " + Lan.g(this,"using Fee Tools."), Fees.getListt()[i].CodeNum);
        }
        DialogResult = DialogResult.OK;
    }

    private void butIncrease_Click(Object sender, System.EventArgs e) throws Exception {
        int percent = 0;
        if (StringSupport.equals(textPercent.Text, ""))
        {
            MsgBox.show(this,"Please enter a percent first.");
            return ;
        }
         
        try
        {
            percent = System.Convert.ToInt32(textPercent.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Percent is not a valid number.");
            return ;
        }

        if (percent < -99 || percent > 99)
        {
            MsgBox.show(this,"Percent must be between -99 and 99.");
            return ;
        }
         
        if (!MsgBox.show(this,true,"This will overwrite all values of the current fee schedule showing in the main window.  For this reason, you should be working on a copy.  Are you sure you want to continue?"))
        {
            return ;
        }
         
        int round = 0;
        if (radioDime.Checked)
        {
            round = 1;
        }
         
        if (radioPenny.Checked)
        {
            round = 2;
        }
         
        Fees.increase(SchedNum,percent,round);
        Fees.refreshCache();
        for (int i = 0;i < Fees.getListt().Count;i++)
        {
            if (Fees.getListt()[i].FeeSched != SchedNum)
            {
                continue;
            }
             
            if (Fees.getListt()[i].Amount == 0)
            {
                continue;
            }
             
            SecurityLogs.MakeLogEntry(Permissions.ProcFeeEdit, 0, Lan.g(this,"Procedure") + ": " + ProcedureCodes.GetStringProcCode(Fees.getListt()[i].CodeNum) + ", " + Lan.g(this,"Fee") + ": " + Fees.getListt()[i].Amount.ToString("c") + ", " + Lan.g(this,"Fee Schedule") + ": " + FeeScheds.GetDescription(Fees.getListt()[i].FeeSched) + ". " + Lan.g(this,"Fee increased by") + " " + ((float)percent / 100.0f).ToString("p") + " " + Lan.g(this," using the increase button in the Fee Tools window."), Fees.getListt()[i].CodeNum);
        }
        DialogResult = DialogResult.OK;
    }

    private void butExport_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        SaveFileDialog Dlg = new SaveFileDialog();
        if (Directory.Exists(PrefC.getString(PrefName.ExportPath)))
        {
            Dlg.InitialDirectory = PrefC.getString(PrefName.ExportPath);
        }
        else if (Directory.Exists("C:\\"))
        {
            Dlg.InitialDirectory = "C:\\";
        }
          
        Dlg.FileName = "Fees" + FeeScheds.getDescription(SchedNum) + ".txt";
        if (Dlg.ShowDialog() != DialogResult.OK)
        {
            Cursor = Cursors.Default;
            return ;
        }
         
        //MessageBox.Show(Dlg.FileName);//includes full path
        //OverwritePrompt is already set to true
        DataTable table = ProcedureCodes.GetProcTable("", "", "", new List<long>(), SchedNum, 0, 0);
        double fee = new double();
        StreamWriter sr = File.CreateText(Dlg.FileName);
        try
        {
            {
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    sr.Write(PIn.String(table.Rows[i]["ProcCode"].ToString()) + "\t");
                    fee = PIn.Double(table.Rows[i]["FeeAmt1"].ToString());
                    if (fee != -1)
                    {
                        sr.Write(fee.ToString("n"));
                    }
                     
                    sr.Write("\t");
                    sr.Write(PIn.String(table.Rows[i]["AbbrDesc"].ToString()) + "\t");
                    sr.WriteLine(PIn.String(table.Rows[i]["Descript"].ToString()));
                }
            }
        }
        finally
        {
            if (sr != null)
                Disposable.mkDisposable(sr).dispose();
             
        }
        Cursor = Cursors.Default;
        DialogResult = DialogResult.OK;
    }

    private void butImport_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"If you want a clean slate, the current fee schedule should be cleared first.  When imported, any fees that are found in the text file will overwrite values of the current fee schedule showing in the main window.  Are you sure you want to continue?"))
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        OpenFileDialog Dlg = new OpenFileDialog();
        if (Directory.Exists(PrefC.getString(PrefName.ExportPath)))
        {
            Dlg.InitialDirectory = PrefC.getString(PrefName.ExportPath);
        }
        else if (Directory.Exists("C:\\"))
        {
            Dlg.InitialDirectory = "C:\\";
        }
          
        if (Dlg.ShowDialog() != DialogResult.OK)
        {
            Cursor = Cursors.Default;
            return ;
        }
         
        if (!File.Exists(Dlg.FileName))
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"File not found");
            return ;
        }
         
        String[] fields = new String[]();
        double feeAmt = new double();
        StreamReader sr = new StreamReader(Dlg.FileName);
        try
        {
            {
                String line = sr.ReadLine();
                while (line != null)
                {
                    Cursor = Cursors.WaitCursor;
                    fields = line.Split(new String[]{ "\t" }, StringSplitOptions.None);
                    if (fields.Length > 1)
                    {
                        // && fields[1]!=""){//we no longer skip blank fees
                        if (StringSupport.equals(fields[1], ""))
                        {
                            feeAmt = -1;
                        }
                        else
                        {
                            //triggers deletion of existing fee, but no insert.
                            feeAmt = PIn.Double(fields[1]);
                        } 
                        Fees.Import(fields[0], feeAmt, SchedNum);
                        SecurityLogs.MakeLogEntry(Permissions.ProcFeeEdit, 0, Lan.g(this,"Procedure") + ": " + fields[0] + ", " + Lan.g(this,"Fee") + ": " + feeAmt.ToString("c") + ", " + Lan.g(this,"Fee Schedule") + ": " + FeeScheds.getDescription(SchedNum) + ". " + Lan.g(this,"Fee changed using the Import button in the Fee Tools window."), ProcedureCodes.GetCodeNum(fields[0]));
                    }
                     
                    line = sr.ReadLine();
                }
            }
        }
        finally
        {
            if (sr != null)
                Disposable.mkDisposable(sr).dispose();
             
        }
        DataValid.setInvalid(InvalidType.Fees);
        Cursor = Cursors.Default;
        DialogResult = DialogResult.OK;
    }

    private void butImportEcw_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        OpenFileDialog Dlg = new OpenFileDialog();
        if (Dlg.ShowDialog() != DialogResult.OK)
        {
            Cursor = Cursors.Default;
            return ;
        }
         
        if (!File.Exists(Dlg.FileName))
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"File not found");
            return ;
        }
         
        String extension = Path.GetExtension(Dlg.FileName);
        if (!StringSupport.equals(extension, ".csv"))
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"Only .csv files may be imported.");
            return ;
        }
         
        String[] lines = File.ReadAllLines(Dlg.FileName);
        if (lines.Length == 0 || (!StringSupport.equals(lines[0], "Code,Description,Unit Fee,Allowed Fee,POS,TOS,Modifier,RequiresCliaID,GlobalBillingDays,ChargeCode") && !StringSupport.equals(lines[0], "\"Code\",\"Description\",\"UnitFee\",\"AllowedFee\",\"POS\",\"TOS\",\"Modifier\",\"RequiresCliaID\",\"GlobalBillingDays\",\"ChargeCode\"")))
        {
            Cursor = Cursors.Default;
            MessageBox.Show("Unexpected file format. First line in file should be:\r\nCode,Description,Unit Fee,Allowed Fee,POS,TOS,Modifier,RequiresCliaID,GlobalBillingDays,ChargeCode\r\nor\r\n\"Code\",\"Description\",\"UnitFee\",\"AllowedFee\",\"POS\",\"TOS\",\"Modifier\",\"RequiresCliaID\",\"GlobalBillingDays\",\"ChargeCode\"");
            return ;
        }
         
        String feeSchedName = Path.GetFileNameWithoutExtension(Dlg.FileName);
        FeeSched feesched = FeeScheds.getByExactName(feeSchedName,FeeScheduleType.Normal);
        if (feesched == null)
        {
            feesched = new FeeSched();
            feesched.Description = feeSchedName;
            feesched.FeeSchedType = FeeScheduleType.Normal;
            feesched.ItemOrder = FeeSchedC.getListLong()[FeeSchedC.getListLong().Count - 1].ItemOrder + 1;
            feesched.IsHidden = false;
            //feesched.IsNew=true;
            FeeScheds.insert(feesched);
            DataValid.setInvalid(InvalidType.FeeScheds);
        }
        else
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Fee schedule already exists and all the fees will be overwritten.  Continue?"))
            {
                Cursor = Cursors.Default;
                return ;
            }
             
            Fees.clearFeeSched(feesched.FeeSchedNum);
        } 
        boolean importAllowed = false;
        if (MsgBox.show(this,MsgBoxButtons.YesNo,"Import Allowed Fee column instead of Unit Fee column?"))
        {
            importAllowed = true;
        }
         
        int imported = 0;
        int skippedCode = 0;
        int skippedMalformed = 0;
        String[] fieldArray = new String[]();
        List<String> fields = new List<String>();
        double feeAmt = 0;
        String codeText = "";
        boolean formatQuotes = false;
        if (lines.Length > 1)
        {
            if (StringSupport.equals(lines[1].Substring(0, 1), "\""))
            {
                formatQuotes = true;
            }
             
        }
         
        if (formatQuotes)
        {
            for (int i = 1;i < lines.Length;i++)
            {
                //Original format - fields are surrounded by quotes (except first row, above)
                //fieldArray=lines[i].Split(new string[1] { "\"" },StringSplitOptions.RemoveEmptyEntries);//Removing emtpy entries will misalign the columns
                fieldArray = lines[i].Split(new String[]{ "\"" }, StringSplitOptions.None);
                //half the 'fields' will be commas.
                fields = new List<String>();
                for (int f = 1;f < fieldArray.Length - 1;f++)
                {
                    //this loop skips the first and last elements because they are artifacts of the string splitting.
                    if (StringSupport.equals(fieldArray[f], ","))
                    {
                        continue;
                    }
                     
                    fields.Add(fieldArray[f]);
                }
                if (fields.Count < 4)
                {
                    skippedMalformed++;
                    continue;
                }
                 
                if (importAllowed)
                {
                    feeAmt = PIn.Double(fields[3]);
                }
                else
                {
                    feeAmt = PIn.Double(fields[2]);
                } 
                codeText = fields[0];
                if (!ProcedureCodes.isValidCode(codeText))
                {
                    skippedCode++;
                    continue;
                }
                 
                Fees.Import(fields[0], feeAmt, feesched.FeeSchedNum);
                imported++;
            }
        }
        else
        {
            for (int i = 1;i < lines.Length;i++)
            {
                //New format - fields are delimited by commas only (no quotes)
                fieldArray = lines[i].Split(new String[]{ "," }, StringSplitOptions.None);
                fields = new List<String>();
                for (int f = 0;f < fieldArray.Length;f++)
                {
                    fields.Add(fieldArray[f]);
                }
                if (fields.Count < 4)
                {
                    skippedMalformed++;
                    continue;
                }
                 
                if (fields.Count > 10)
                {
                    MsgBox.show(this,"Import aborted. Commas are not allowed in text fields. Check your descriptions for commas and try again.");
                    Cursor = Cursors.Default;
                    return ;
                }
                 
                if (importAllowed)
                {
                    feeAmt = PIn.Double(fields[3]);
                }
                else
                {
                    feeAmt = PIn.Double(fields[2]);
                } 
                codeText = fields[0];
                if (!ProcedureCodes.isValidCode(codeText))
                {
                    skippedCode++;
                    continue;
                }
                 
                Fees.Import(fields[0], feeAmt, feesched.FeeSchedNum);
                imported++;
            }
        } 
        DataValid.setInvalid(InvalidType.Fees);
        Cursor = Cursors.Default;
        String displayMsg = "Import complete.\r\nCodes imported: " + imported.ToString();
        if (skippedCode > 0)
        {
            displayMsg += "\r\nCodes skipped because not valid codes in Open Dental: " + skippedCode.ToString();
        }
         
        if (skippedMalformed > 0)
        {
            displayMsg += "\r\nCodes skipped because malformed line in text file: " + skippedMalformed.ToString();
        }
         
        MessageBox.Show(displayMsg);
        DialogResult = DialogResult.OK;
    }

    private void butImportCanada_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"If you want a clean slate, the current fee schedule should be cleared first.  When imported, any fees that are found in the text file will overwrite values of the current fee schedule showing in the main window.  Are you sure you want to continue?"))
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        FormFeeSchedPickRemote formPick = new FormFeeSchedPickRemote();
        formPick.Url = "http://www.opendental.com/feescanada/";
        //points to index.php file
        if (formPick.ShowDialog() != DialogResult.OK)
        {
            Cursor = Cursors.Default;
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        //original wait cursor seems to go away for some reason.
        Application.DoEvents();
        String feeData = "";
        if (formPick.getIsFileChosenProtected())
        {
            String memberNumberODA = "";
            String memberPasswordODA = "";
            if (formPick.getFileChosenName().StartsWith("ON_"))
            {
                //Any and all Ontario fee schedules
                FormFeeSchedPickAuthOntario formAuth = new FormFeeSchedPickAuthOntario();
                if (formAuth.ShowDialog() != DialogResult.OK)
                {
                    Cursor = Cursors.Default;
                    return ;
                }
                 
                memberNumberODA = formAuth.getODAMemberNumber();
                memberPasswordODA = formAuth.getODAMemberPassword();
            }
             
            //prepare the xml document to send--------------------------------------------------------------------------------------
            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.IndentChars = ("    ");
            StringBuilder strbuild = new StringBuilder();
            XmlWriter writer = XmlWriter.Create(strbuild, settings);
            try
            {
                {
                    writer.WriteStartElement("RequestFeeSched");
                    writer.WriteStartElement("RegistrationKey");
                    writer.WriteString(PrefC.getString(PrefName.RegistrationKey));
                    writer.WriteEndElement();
                    //RegistrationKey
                    writer.WriteStartElement("FeeSchedFileName");
                    writer.WriteString(formPick.getFileChosenName());
                    writer.WriteEndElement();
                    //FeeSchedFileName
                    if (!StringSupport.equals(memberNumberODA, ""))
                    {
                        writer.WriteStartElement("ODAMemberNumber");
                        writer.WriteString(memberNumberODA);
                        writer.WriteEndElement();
                        //ODAMemberNumber
                        writer.WriteStartElement("ODAMemberPassword");
                        writer.WriteString(memberPasswordODA);
                        writer.WriteEndElement();
                    }
                     
                    //ODAMemberPassword
                    writer.WriteEndElement();
                }
            }
            finally
            {
                if (writer != null)
                    Disposable.mkDisposable(writer).dispose();
                 
            }
            //RequestFeeSched
            OpenDental.customerUpdates.Service1 updateService = new OpenDental.customerUpdates.Service1();
            updateService.setUrl(PrefC.getString(PrefName.UpdateServerAddress));
            //Send the message and get the result-------------------------------------------------------------------------------------
            String result = "";
            try
            {
                result = updateService.RequestFeeSched(strbuild.ToString());
            }
            catch (Exception ex)
            {
                Cursor = Cursors.Default;
                MessageBox.Show("Error: " + ex.Message);
                return ;
            }

            Cursor = Cursors.Default;
            XmlDocument doc = new XmlDocument();
            doc.LoadXml(result);
            //Process errors------------------------------------------------------------------------------------------------------------
            XmlNode node = doc.SelectSingleNode("//Error");
            if (node != null)
            {
                MessageBox.Show(node.InnerText, "Error");
                return ;
            }
             
            node = doc.SelectSingleNode("//KeyDisabled");
            if (node == null)
            {
                //no error, and no disabled message
                if (Prefs.updateBool(PrefName.RegistrationKeyIsDisabled,false))
                {
                    //this is one of three places in the program where this happens.
                    DataValid.setInvalid(InvalidType.Prefs);
                }
                 
            }
            else
            {
                MessageBox.Show(node.InnerText);
                if (Prefs.updateBool(PrefName.RegistrationKeyIsDisabled,true))
                {
                    //this is one of three places in the program where this happens.
                    DataValid.setInvalid(InvalidType.Prefs);
                }
                 
                return ;
            } 
            //Process a valid return value------------------------------------------------------------------------------------------------
            node = doc.SelectSingleNode("//ResultCSV64");
            String feeData64 = node.InnerXml;
            byte[] feeDataBytes = Convert.FromBase64String(feeData64);
            feeData = Encoding.UTF8.GetString(feeDataBytes);
        }
        else
        {
            String tempFile = Path.GetTempFileName();
            WebClient myWebClient = new WebClient();
            try
            {
                myWebClient.DownloadFile(formPick.getFileChosenUrl(), tempFile);
            }
            catch (Exception ex)
            {
                MessageBox.Show(Lan.g(this,"Failed to download fee schedule file") + ": " + ex.Message);
                Cursor = Cursors.Default;
                return ;
            }

            feeData = File.ReadAllText(tempFile);
            File.Delete(tempFile);
        } 
        String[] feeLines = feeData.Split('\n');
        double feeAmt = new double();
        long numImported = 0;
        long numSkipped = 0;
        for (int i = 0;i < feeLines.Length;i++)
        {
            String[] fields = feeLines[i].Split('\t');
            if (fields.Length > 1)
            {
                // && fields[1]!=""){//we no longer skip blank fees
                String procCode = fields[0];
                if (ProcedureCodes.isValidCode(procCode))
                {
                    //The Fees.Import() function will not import fees for codes that do not exist.
                    if (StringSupport.equals(fields[1], ""))
                    {
                        feeAmt = -1;
                    }
                    else
                    {
                        //triggers deletion of existing fee, but no insert.
                        feeAmt = PIn.Double(fields[1]);
                    } 
                    Fees.import(procCode,feeAmt,SchedNum);
                    numImported++;
                }
                else
                {
                    numSkipped++;
                } 
            }
             
        }
        DataValid.setInvalid(InvalidType.Fees);
        Cursor = Cursors.Default;
        DialogResult = DialogResult.OK;
        String outputMessage = Lan.g(this,"Done. Number imported") + ": " + numImported;
        if (numSkipped > 0)
        {
            outputMessage += " " + Lan.g(this,"Number skipped") + ": " + numSkipped;
        }
         
        MessageBox.Show(outputMessage);
    }

    private void butUpdate_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"All treatment planned procedures for all patients will be updated.  Only the fee will be updated, not the insurance estimate.  It might take a few minutes.  Continue?"))
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        long rowsChanged = Procedures.globalUpdateFees();
        Cursor = Cursors.Default;
        MessageBox.Show(Lan.g(this,"Fees changed: ") + rowsChanged.ToString());
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


