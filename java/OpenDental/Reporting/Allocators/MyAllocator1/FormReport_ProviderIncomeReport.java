//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.ReportUI.ReportStyles;

public class FormReport_ProviderIncomeReport  extends Form 
{

    private DateTime _FromDate = new DateTime();
    private DateTime _ToDate = new DateTime();
    public FormReport_ProviderIncomeReport(DateTime fromdate, DateTime todate) throws Exception {
        initializeComponent();
        _FromDate = fromdate;
        _ToDate = todate;
    }

    private void formReport_ProviderIncomeReport_Load(Object sender, EventArgs e) throws Exception {
        this.Text = "Provider Income Report From " + _FromDate.ToShortDateString() + " To " + _ToDate.ToShortDateString();
        this.lblDgvHeader.Text = this.Text;
        this.dataGridView1.DataSource = OpenDental.Reporting.Allocators.MyAllocator1.SupportingCode.Report1_GuarantorAllocation.generateSummaryTable(-1,_FromDate,_ToDate);
        ReportStyles.Set_DefaultDataGridViewStyle(this.dataGridView1);
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
        this.dataGridView1 = new System.Windows.Forms.DataGridView();
        this.panel1 = new System.Windows.Forms.Panel();
        this.lblDgvHeader = new System.Windows.Forms.Label();
        ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
        this.panel1.SuspendLayout();
        this.SuspendLayout();
        //
        // dataGridView1
        //
        this.dataGridView1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
        this.dataGridView1.Location = new System.Drawing.Point(0, 13);
        this.dataGridView1.Name = "dataGridView1";
        this.dataGridView1.Size = new System.Drawing.Size(797, 483);
        this.dataGridView1.TabIndex = 0;
        //
        // panel1
        //
        this.panel1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.panel1.Controls.Add(this.dataGridView1);
        this.panel1.Controls.Add(this.lblDgvHeader);
        this.panel1.Location = new System.Drawing.Point(12, 12);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(797, 496);
        this.panel1.TabIndex = 1;
        //
        // lblDgvHeader
        //
        this.lblDgvHeader.Dock = System.Windows.Forms.DockStyle.Top;
        this.lblDgvHeader.Location = new System.Drawing.Point(0, 0);
        this.lblDgvHeader.Name = "lblDgvHeader";
        this.lblDgvHeader.Size = new System.Drawing.Size(797, 13);
        this.lblDgvHeader.TabIndex = 1;
        this.lblDgvHeader.Text = "Provider Income Report";
        //
        // FormReport_ProviderIncomeReport
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(229)))), ((int)(((byte)(222)))), ((int)(((byte)(222)))));
        this.ClientSize = new System.Drawing.Size(821, 520);
        this.Controls.Add(this.panel1);
        this.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.ForeColor = System.Drawing.Color.Maroon;
        this.Name = "FormReport_ProviderIncomeReport";
        this.Text = "Provider Income Report";
        this.Load += new System.EventHandler(this.FormReport_ProviderIncomeReport_Load);
        ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
        this.panel1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.DataGridView dataGridView1 = new System.Windows.Forms.DataGridView();
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Label lblDgvHeader = new System.Windows.Forms.Label();
}


