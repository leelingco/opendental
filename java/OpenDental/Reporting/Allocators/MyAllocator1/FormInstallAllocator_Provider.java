//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Lan;
import OpenDental.Reporting.Allocators.MyAllocator1.FormReport_GuarantorAllocationCheck;
import OpenDental.Reporting.Allocators.MyAllocator1.FormReport_ProviderIncomeReport;
import OpenDental.Reporting.Allocators.MyAllocator1.FormWarnToCloseComputers;
import OpenDental.Reporting.Allocators.MyAllocator1.PU;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.SecurityLogs;
import OpenDental.Reporting.Allocators.MyAllocator1.FormInstallAllocator_Provider;

public class FormInstallAllocator_Provider  extends Form 
{

    //private static string _InitialMessage = "Welcome to the Provider Allocation Setup\r\nPlease read the following:";
    public FormInstallAllocator_Provider() throws Exception {
        initializeComponent();
    }

    private void formInstallAllocator_Provider_Load(Object sender, EventArgs e) throws Exception {
        refreshForm();
    }

    private void refreshForm() throws Exception {
        Cache.refresh(InvalidType.Prefs);
        boolean toolRan = PrefC.containsKey(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_ToolHasRun);
        boolean isUsing = PrefC.containsKey(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_Use);
        this.lblAllocatorStatus.Text = "Current Tool Status: " + (toolRan ? "Tool has been run and " + (isUsing ? "\nsettings indicate that allocation is occuring." : "\nsettings indicate that allocation is not occuring.") : "Tool has not been run yet.");
        this.butGuarnDetailReport.Enabled = toolRan & isUsing;
        this.butProviderIncomeReport.Enabled = toolRan & isUsing;
        this.butUneardedIncomeReport.Enabled = toolRan & isUsing;
    }

    private void butProviderIncomeReport_Click(Object sender, EventArgs e) throws Exception {
        1.ToString();
        //DateTime dtFrom = new DateTime(2007,1,1);
        DateTime dtFrom = new DateTime(2007, 12, 31);
        DateTime dtTo = new DateTime(2007, 12, 31);
        //DateTime dtNow = DateTime.Now;
        //DateTime dtTo	 = new DateTime(dtNow.Year,dtNow.Month,dtNow.Day);
        FormReport_ProviderIncomeReport fpir = new FormReport_ProviderIncomeReport(dtFrom,dtTo);
        fpir.ShowDialog();
    }

    private void butUneardedIncomeReport_Click(Object sender, EventArgs e) throws Exception {
    }

    private void butGuarantorDetailReport_Click(Object sender, EventArgs e) throws Exception {
        FormReport_GuarantorAllocationCheck fgac = new FormReport_GuarantorAllocationCheck();
        fgac.ShowDialog();
    }

    private void butRunAllocatorTool_Click(Object sender, EventArgs e) throws Exception {
        if (!rbutIHaveRead.Checked)
        {
            PU.setMB(Lan.g(this,"You must indicate that you have read the text in the box!"));
            return ;
        }
         
        if (MessageBox.Show("Do you want to run the batch allocation?", "Please Respond", MessageBoxButtons.YesNo) == DialogResult.Yes)
        {
            FormWarnToCloseComputers fwc = new FormWarnToCloseComputers();
            if (fwc.ShowDialog() == DialogResult.Yes)
            {
                OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment allocator1 = new OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment();
                SecurityLogs.MakeLogEntry(OpenDentBusiness.Permissions.Setup, 0, "Started Batch Allocation For Provider Allocation Tool");
                allocator1.startBatchAllocation();
                SecurityLogs.MakeLogEntry(OpenDentBusiness.Permissions.Setup, 0, "Finished Batch Allocation For Provider Allocation Tool");
                List<String> commands = new List<String>();
                if (!PrefC.containsKey(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_ToolHasRun))
                {
                    commands.Add("INSERT INTO preference VALUES ('" + OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_ToolHasRun + "','0')");
                }
                 
                if (!PrefC.containsKey(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_Use))
                {
                    commands.Add("INSERT INTO preference VALUES ('" + OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_Use + "','0')");
                }
                 
                if (commands.Count != 0)
                {
                    Db.NonQOld(commands.ToArray());
                    Cache.refresh(InvalidType.Prefs);
                }
                 
                Prefs.updateRaw(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_ToolHasRun,"1");
                Prefs.updateRaw(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_Use,"1");
            }
             
        }
         
        refreshForm();
    }

    private void rbutIHaveRead_CheckedChanged(Object sender, EventArgs e) throws Exception {
        this.butRunAllocatorTool.Enabled = this.rbutIHaveRead.Checked;
        if (this.butRunAllocatorTool.Enabled)
            this.butRunAllocatorTool.BackColor = Color.White;
        else
            this.butRunAllocatorTool.BackColor = this.BackColor; 
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInstallAllocator_Provider.class);
        this.label1 = new System.Windows.Forms.Label();
        this.richTextBox1 = new System.Windows.Forms.RichTextBox();
        this.panel1 = new System.Windows.Forms.Panel();
        this.button4 = new System.Windows.Forms.Button();
        this.butProviderIncomeReport = new System.Windows.Forms.Button();
        this.butGuarnDetailReport = new System.Windows.Forms.Button();
        this.butUneardedIncomeReport = new System.Windows.Forms.Button();
        this.butRunAllocatorTool = new System.Windows.Forms.Button();
        this.rbIhaveNotRead = new System.Windows.Forms.RadioButton();
        this.panel2 = new System.Windows.Forms.Panel();
        this.rbutIHaveRead = new System.Windows.Forms.RadioButton();
        this.lblAllocatorStatus = new System.Windows.Forms.Label();
        this.panel1.SuspendLayout();
        this.panel2.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.AutoSize = true;
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label1.ForeColor = System.Drawing.Color.Maroon;
        this.label1.Location = new System.Drawing.Point(9, 23);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(244, 26);
        this.label1.TabIndex = 0;
        this.label1.Text = "Welcome to the Provider Allocation Setup\r\nPlease read the following:";
        //
        // richTextBox1
        //
        this.richTextBox1.BackColor = System.Drawing.Color.White;
        this.richTextBox1.ForeColor = System.Drawing.Color.Maroon;
        this.richTextBox1.Location = new System.Drawing.Point(12, 69);
        this.richTextBox1.Name = "richTextBox1";
        this.richTextBox1.Size = new System.Drawing.Size(383, 164);
        this.richTextBox1.TabIndex = 1;
        this.richTextBox1.Text = resources.GetString("richTextBox1.Text");
        //
        // panel1
        //
        this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.panel1.Controls.Add(this.button4);
        this.panel1.Controls.Add(this.butProviderIncomeReport);
        this.panel1.Controls.Add(this.butGuarnDetailReport);
        this.panel1.Controls.Add(this.butUneardedIncomeReport);
        this.panel1.Location = new System.Drawing.Point(475, 69);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(145, 94);
        this.panel1.TabIndex = 2;
        //
        // button4
        //
        this.button4.BackColor = System.Drawing.Color.Maroon;
        this.button4.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.button4.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
        this.button4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.button4.ForeColor = System.Drawing.Color.White;
        this.button4.Location = new System.Drawing.Point(0, 0);
        this.button4.Name = "button4";
        this.button4.Size = new System.Drawing.Size(143, 23);
        this.button4.TabIndex = 3;
        this.button4.Text = "REPORTS";
        this.button4.UseVisualStyleBackColor = false;
        //
        // butProviderIncomeReport
        //
        this.butProviderIncomeReport.BackColor = System.Drawing.Color.White;
        this.butProviderIncomeReport.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.butProviderIncomeReport.Enabled = false;
        this.butProviderIncomeReport.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butProviderIncomeReport.ForeColor = System.Drawing.Color.Maroon;
        this.butProviderIncomeReport.Location = new System.Drawing.Point(0, 23);
        this.butProviderIncomeReport.Name = "butProviderIncomeReport";
        this.butProviderIncomeReport.Size = new System.Drawing.Size(143, 23);
        this.butProviderIncomeReport.TabIndex = 2;
        this.butProviderIncomeReport.Text = "Provider Income";
        this.butProviderIncomeReport.UseVisualStyleBackColor = false;
        this.butProviderIncomeReport.Click += new System.EventHandler(this.butProviderIncomeReport_Click);
        //
        // butGuarnDetailReport
        //
        this.butGuarnDetailReport.BackColor = System.Drawing.Color.White;
        this.butGuarnDetailReport.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.butGuarnDetailReport.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butGuarnDetailReport.ForeColor = System.Drawing.Color.Maroon;
        this.butGuarnDetailReport.Location = new System.Drawing.Point(0, 46);
        this.butGuarnDetailReport.Name = "butGuarnDetailReport";
        this.butGuarnDetailReport.Size = new System.Drawing.Size(143, 23);
        this.butGuarnDetailReport.TabIndex = 1;
        this.butGuarnDetailReport.Text = "Guarantor Detail";
        this.butGuarnDetailReport.UseVisualStyleBackColor = false;
        this.butGuarnDetailReport.Click += new System.EventHandler(this.butGuarantorDetailReport_Click);
        //
        // butUneardedIncomeReport
        //
        this.butUneardedIncomeReport.BackColor = System.Drawing.Color.White;
        this.butUneardedIncomeReport.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.butUneardedIncomeReport.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butUneardedIncomeReport.ForeColor = System.Drawing.Color.Maroon;
        this.butUneardedIncomeReport.Location = new System.Drawing.Point(0, 69);
        this.butUneardedIncomeReport.Name = "butUneardedIncomeReport";
        this.butUneardedIncomeReport.Size = new System.Drawing.Size(143, 23);
        this.butUneardedIncomeReport.TabIndex = 0;
        this.butUneardedIncomeReport.Text = "Unerarned Income";
        this.butUneardedIncomeReport.UseVisualStyleBackColor = false;
        this.butUneardedIncomeReport.Click += new System.EventHandler(this.butUneardedIncomeReport_Click);
        //
        // butRunAllocatorTool
        //
        this.butRunAllocatorTool.Enabled = false;
        this.butRunAllocatorTool.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butRunAllocatorTool.ForeColor = System.Drawing.Color.Maroon;
        this.butRunAllocatorTool.Location = new System.Drawing.Point(252, 239);
        this.butRunAllocatorTool.Name = "butRunAllocatorTool";
        this.butRunAllocatorTool.Size = new System.Drawing.Size(143, 23);
        this.butRunAllocatorTool.TabIndex = 4;
        this.butRunAllocatorTool.Text = "Run Allocator Tool";
        this.butRunAllocatorTool.UseVisualStyleBackColor = false;
        this.butRunAllocatorTool.Click += new System.EventHandler(this.butRunAllocatorTool_Click);
        //
        // rbIhaveNotRead
        //
        this.rbIhaveNotRead.AutoSize = true;
        this.rbIhaveNotRead.Checked = true;
        this.rbIhaveNotRead.Location = new System.Drawing.Point(3, 3);
        this.rbIhaveNotRead.Name = "rbIhaveNotRead";
        this.rbIhaveNotRead.Size = new System.Drawing.Size(192, 17);
        this.rbIhaveNotRead.TabIndex = 5;
        this.rbIhaveNotRead.TabStop = true;
        this.rbIhaveNotRead.Text = "I Have NOT Read The Above Text";
        this.rbIhaveNotRead.UseVisualStyleBackColor = true;
        //
        // panel2
        //
        this.panel2.Controls.Add(this.rbutIHaveRead);
        this.panel2.Controls.Add(this.rbIhaveNotRead);
        this.panel2.Location = new System.Drawing.Point(12, 239);
        this.panel2.Name = "panel2";
        this.panel2.Size = new System.Drawing.Size(234, 57);
        this.panel2.TabIndex = 6;
        //
        // rbutIHaveRead
        //
        this.rbutIHaveRead.AutoSize = true;
        this.rbutIHaveRead.Location = new System.Drawing.Point(3, 22);
        this.rbutIHaveRead.Name = "rbutIHaveRead";
        this.rbutIHaveRead.Size = new System.Drawing.Size(162, 17);
        this.rbutIHaveRead.TabIndex = 6;
        this.rbutIHaveRead.Text = "I Have Read the Above Text";
        this.rbutIHaveRead.UseVisualStyleBackColor = true;
        this.rbutIHaveRead.CheckedChanged += new System.EventHandler(this.rbutIHaveRead_CheckedChanged);
        //
        // lblAllocatorStatus
        //
        this.lblAllocatorStatus.AutoSize = true;
        this.lblAllocatorStatus.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.lblAllocatorStatus.ForeColor = System.Drawing.Color.Maroon;
        this.lblAllocatorStatus.Location = new System.Drawing.Point(253, 269);
        this.lblAllocatorStatus.Name = "lblAllocatorStatus";
        this.lblAllocatorStatus.Size = new System.Drawing.Size(121, 13);
        this.lblAllocatorStatus.TabIndex = 7;
        this.lblAllocatorStatus.Text = "Current Tool Status:";
        //
        // FormInstallAllocator_Provider
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(229)))), ((int)(((byte)(222)))), ((int)(((byte)(222)))));
        this.ClientSize = new System.Drawing.Size(649, 308);
        this.Controls.Add(this.lblAllocatorStatus);
        this.Controls.Add(this.panel2);
        this.Controls.Add(this.butRunAllocatorTool);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.richTextBox1);
        this.Controls.Add(this.label1);
        this.Name = "FormInstallAllocator_Provider";
        this.Text = "Allocator Provider Setup";
        this.Load += new System.EventHandler(this.FormInstallAllocator_Provider_Load);
        this.panel1.ResumeLayout(false);
        this.panel2.ResumeLayout(false);
        this.panel2.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.RichTextBox richTextBox1 = new System.Windows.Forms.RichTextBox();
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Button butProviderIncomeReport = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butGuarnDetailReport = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butUneardedIncomeReport = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button button4 = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butRunAllocatorTool = new System.Windows.Forms.Button();
    private System.Windows.Forms.RadioButton rbIhaveNotRead = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Panel panel2 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.RadioButton rbutIHaveRead = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label lblAllocatorStatus = new System.Windows.Forms.Label();
}


