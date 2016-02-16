//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.PU;
import OpenDental.Reporting.Allocators.MyAllocator1.ReportUI.ReportStyles;
import OpenDental.Reporting.Allocators.MyAllocator1.SupportingCode.Report1_GuarantorAllocation;

public class FormReport_GuarantorAllocationCheck  extends Form 
{

    private Report1_GuarantorAllocation _Report_GA = null;
    private int _Guarantor = -1;
    public FormReport_GuarantorAllocationCheck() throws Exception {
        initializeComponent();
    }

    public FormReport_GuarantorAllocationCheck(int Guarantor) throws Exception {
        initializeComponent();
    }

    private void butFillReport_Click(Object sender, EventArgs e) throws Exception {
        try
        {
            int Guarantor = Int32.Parse(this.textBox1.Text);
            if (Guarantor < 1)
                throw new Exception("Invalid Guarantor Number");
             
            this._Report_GA = new OpenDental.Reporting.Allocators.MyAllocator1.SupportingCode.Report1_GuarantorAllocation();
            _Report_GA.FILL(Guarantor);
            this.lblTitle.Text = _Report_GA.TITLE;
            this.dgridView_ReportData.DataSource = _Report_GA.MAIN_REPORT;
            this.dgvSummary.DataSource = _Report_GA.SUMMARY;
            //DataGridViewCellStyle defaultCellStyle1 = new DataGridViewCellStyle();
            //defaultCellStyle1.Padding = new Padding(0,0,0,0);
            //defaultCellStyle1.Font = new Font("Arial", 8F);
            //DataGridViewCellStyle defaultCellStyle2 = new DataGridViewCellStyle(defaultCellStyle1);
            //defaultCellStyle2.BackColor = Color.LightCyan;
            //this.dgridView_ReportData.DefaultCellStyle = defaultCellStyle1;
            //this.dgridView_ReportData.AlternatingRowsDefaultCellStyle = defaultCellStyle2;
            //this.dgridView_ReportData.CellBorderStyle = DataGridViewCellBorderStyle.None;
            //this.dgridView_ReportData.RowHeadersVisible = false;
            // Above Code moved to Static Method
            ReportStyles.Set_DefaultDataGridViewStyle(dgridView_ReportData);
            int index = this.lblTitle.Text.IndexOf('\n');
            if (index > 0)
                this.Text = this.lblTitle.Text.Substring(0, index);
            else if (this.lblTitle.Text.Length > 0)
                this.Text = this.lblTitle.Text;
            else
                this.Text = "Allocation Report for Guarantor: " + Guarantor;  
        }
        catch (Exception exc)
        {
            PU.setMB(exc.Message);
        }
    
    }

    private void butPrintPreview_Click(Object sender, EventArgs e) throws Exception {
        if (this._Report_GA == null)
            _Report_GA.FILL(_Guarantor);
         
        _Report_GA.ShowReportPreview(_Guarantor);
    }

    //PrintPreviewControl ppc = new PrintPreviewControl();
    //ppc.Document = new System.Drawing.Printing.PrintDocument();
    /**
    * / Set the zoom to 100 percent.
    */
    //ppc.Zoom = 1;
    /**
    * / Set the UseAntiAlias property to true so fonts are smoothed
    * / by the operating system.
    */
    //ppc.UseAntiAlias = true;
    /**
    * /// Add the control to the form.
    * /this.Controls.Add(this.PrintPreviewControl1);
    * /// Associate the event-handling method with the
    * /// document's PrintPage event.
    * /this.docToPrint.PrintPage +=
    * /    new System.Drawing.Printing.PrintPageEventHandler(
    * /    docToPrint_PrintPage);
    */
    //ppc.Show();
    private void testForm_Load(Object sender, EventArgs e) throws Exception {
        if (_Report_GA != null)
        {
            _Report_GA.FILL(_Guarantor);
            this.textBox1.Text = _Guarantor.ToString();
            butFillReport_Click(this, EventArgs.Empty);
        }
         
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
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.butFillReport = new System.Windows.Forms.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.panel1 = new System.Windows.Forms.Panel();
        this.dgvSummary = new System.Windows.Forms.DataGridView();
        this.label3 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.dgridView_ReportData = new System.Windows.Forms.DataGridView();
        this.lblTitle = new System.Windows.Forms.Label();
        this.lblSummary = new System.Windows.Forms.Label();
        this.butPrintPreview = new System.Windows.Forms.Button();
        this.dateTimePicker1_FromDate = new System.Windows.Forms.DateTimePicker();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.dateTimePicker1 = new System.Windows.Forms.DateTimePicker();
        this.panel1.SuspendLayout();
        ((System.ComponentModel.ISupportInitialize)(this.dgvSummary)).BeginInit();
        ((System.ComponentModel.ISupportInitialize)(this.dgridView_ReportData)).BeginInit();
        this.SuspendLayout();
        //
        // textBox1
        //
        this.textBox1.Location = new System.Drawing.Point(864, 40);
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(116, 20);
        this.textBox1.TabIndex = 1;
        this.textBox1.Text = "3479";
        //
        // butFillReport
        //
        this.butFillReport.Location = new System.Drawing.Point(864, 66);
        this.butFillReport.Name = "butFillReport";
        this.butFillReport.Size = new System.Drawing.Size(116, 23);
        this.butFillReport.TabIndex = 2;
        this.butFillReport.Text = "Fill Report";
        this.butFillReport.UseVisualStyleBackColor = true;
        this.butFillReport.Click += new System.EventHandler(this.butFillReport_Click);
        //
        // label2
        //
        this.label2.BackColor = System.Drawing.Color.White;
        this.label2.Location = new System.Drawing.Point(861, 16);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(119, 21);
        this.label2.TabIndex = 3;
        this.label2.Text = "Guarantor";
        //
        // panel1
        //
        this.panel1.Controls.Add(this.dgvSummary);
        this.panel1.Controls.Add(this.label3);
        this.panel1.Controls.Add(this.label1);
        this.panel1.Controls.Add(this.dgridView_ReportData);
        this.panel1.Controls.Add(this.lblTitle);
        this.panel1.Controls.Add(this.lblSummary);
        this.panel1.Location = new System.Drawing.Point(12, 12);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(843, 949);
        this.panel1.TabIndex = 4;
        //
        // dgvSummary
        //
        this.dgvSummary.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
        this.dgvSummary.Location = new System.Drawing.Point(0, 613);
        this.dgvSummary.Name = "dgvSummary";
        this.dgvSummary.Size = new System.Drawing.Size(840, 316);
        this.dgvSummary.TabIndex = 4;
        //
        // label3
        //
        this.label3.BackColor = System.Drawing.Color.White;
        this.label3.Location = new System.Drawing.Point(34, 585);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(360, 25);
        this.label3.TabIndex = 3;
        this.label3.Text = "Summary Data - dgridView_ReportData       (this lable not visible)";
        this.label3.Visible = false;
        //
        // label1
        //
        this.label1.BackColor = System.Drawing.Color.White;
        this.label1.Location = new System.Drawing.Point(29, 143);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(360, 25);
        this.label1.TabIndex = 2;
        this.label1.Text = "Main Report Data - dgridView_ReportData       (this lable not visible)";
        this.label1.Visible = false;
        //
        // dgridView_ReportData
        //
        this.dgridView_ReportData.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
        this.dgridView_ReportData.Location = new System.Drawing.Point(0, 171);
        this.dgridView_ReportData.Name = "dgridView_ReportData";
        this.dgridView_ReportData.Size = new System.Drawing.Size(840, 411);
        this.dgridView_ReportData.TabIndex = 1;
        //
        // lblTitle
        //
        this.lblTitle.BackColor = System.Drawing.Color.LightCyan;
        this.lblTitle.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.lblTitle.Location = new System.Drawing.Point(2, 28);
        this.lblTitle.Name = "lblTitle";
        this.lblTitle.Size = new System.Drawing.Size(838, 85);
        this.lblTitle.TabIndex = 0;
        this.lblTitle.Text = "Put Title Text Here";
        this.lblTitle.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // lblSummary
        //
        this.lblSummary.Location = new System.Drawing.Point(0, 589);
        this.lblSummary.Name = "lblSummary";
        this.lblSummary.Size = new System.Drawing.Size(840, 21);
        this.lblSummary.TabIndex = 5;
        this.lblSummary.Text = "Summary";
        this.lblSummary.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // butPrintPreview
        //
        this.butPrintPreview.Location = new System.Drawing.Point(864, 95);
        this.butPrintPreview.Name = "butPrintPreview";
        this.butPrintPreview.Size = new System.Drawing.Size(116, 23);
        this.butPrintPreview.TabIndex = 5;
        this.butPrintPreview.Text = "Print Preview";
        this.butPrintPreview.UseVisualStyleBackColor = true;
        this.butPrintPreview.Click += new System.EventHandler(this.butPrintPreview_Click);
        //
        // dateTimePicker1_FromDate
        //
        this.dateTimePicker1_FromDate.Format = System.Windows.Forms.DateTimePickerFormat.Short;
        this.dateTimePicker1_FromDate.Location = new System.Drawing.Point(893, 124);
        this.dateTimePicker1_FromDate.Name = "dateTimePicker1_FromDate";
        this.dateTimePicker1_FromDate.Size = new System.Drawing.Size(87, 20);
        this.dateTimePicker1_FromDate.TabIndex = 6;
        //
        // label5
        //
        this.label5.AutoSize = true;
        this.label5.Location = new System.Drawing.Point(861, 128);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(33, 13);
        this.label5.TabIndex = 8;
        this.label5.Text = "From ";
        //
        // label6
        //
        this.label6.AutoSize = true;
        this.label6.Location = new System.Drawing.Point(861, 154);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(20, 13);
        this.label6.TabIndex = 10;
        this.label6.Text = "To";
        //
        // dateTimePicker1
        //
        this.dateTimePicker1.Format = System.Windows.Forms.DateTimePickerFormat.Short;
        this.dateTimePicker1.Location = new System.Drawing.Point(893, 150);
        this.dateTimePicker1.Name = "dateTimePicker1";
        this.dateTimePicker1.Size = new System.Drawing.Size(87, 20);
        this.dateTimePicker1.TabIndex = 9;
        //
        // FormReport_GuarantorAllocationCheck
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(1071, 973);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.dateTimePicker1);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.dateTimePicker1_FromDate);
        this.Controls.Add(this.butPrintPreview);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butFillReport);
        this.Controls.Add(this.textBox1);
        this.Name = "FormReport_GuarantorAllocationCheck";
        this.Text = "TestForm";
        this.Load += new System.EventHandler(this.TestForm_Load);
        this.panel1.ResumeLayout(false);
        ((System.ComponentModel.ISupportInitialize)(this.dgvSummary)).EndInit();
        ((System.ComponentModel.ISupportInitialize)(this.dgridView_ReportData)).EndInit();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butFillReport = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Button butPrintPreview = new System.Windows.Forms.Button();
    private System.Windows.Forms.DataGridView dgridView_ReportData = new System.Windows.Forms.DataGridView();
    private System.Windows.Forms.Label lblTitle = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.DataGridView dgvSummary = new System.Windows.Forms.DataGridView();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label lblSummary = new System.Windows.Forms.Label();
    private System.Windows.Forms.DateTimePicker dateTimePicker1_FromDate = new System.Windows.Forms.DateTimePicker();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.DateTimePicker dateTimePicker1 = new System.Windows.Forms.DateTimePicker();
}
/**
* /protected override void OnPaint(PaintEventArgs e)
* /{
* /    base.OnPaint(e);
* /}
*///void DrawForm(Graphics g, int resX, int resY)//{//    g.FillRectangle(new SolidBrush(this.BackColor), 0, 0, this.Width, this.Height);//    float scale = 1; // resX / ScreenResolution;//    // Cycle through each control on the form and paint it to the printe//    foreach (Control c in Controls)//    {//        // Get the time of the next control so we can unbox it//        string strType = c.GetType().ToString().Substring(c.GetType().ToString().LastIndexOf(".") + 1);//        switch (strType)//        {//            case "Button": Button b = (Button)c;//                // Use the ControlPaint method DrawButton in order to draw the button of the form//                ControlPaint.DrawButton(g, ((Button)c).Left, ((Button)c).Top, ((Button)c).Width, ((Button)c).Height, ButtonState.Normal);//                // We also need to draw the text//                g.DrawString(b.Text, b.Font, new SolidBrush(b.ForeColor), b.Left + b.Width / 2 - g.MeasureString(b.Text,//                b.Font).Width / 2, b.Top + b.Height / 2 - g.MeasureString("a", b.Font).Height / 2, new StringFormat());//                break;//            case "TextBox": TextBox t = (TextBox)c;//                // Draw a text box by drawing a pushed in button and filling the rectangle with the background color and the text//                // of the TextBox control//                // First the sunken border//                ControlPaint.DrawButton(g, t.Left, t.Top, t.Width, t.Height, ButtonState.Pushed);//                // Then fill it with the background of the textbox//                g.FillRectangle(new SolidBrush(t.BackColor), t.Left + 1, t.Top + 1, t.Width + 2, t.Height - 2);//                // Finally draw the string inside//                g.DrawString(t.Text, t.Font, new SolidBrush(t.ForeColor), t.Left + 2, t.Top + t.Height / 2 - g.MeasureString("a", t.Font).Height / 2, new StringFormat());//                break;//            case "CheckBox":// We have a checkbox to paint, unbox it//                CheckBox cb = (CheckBox)c;//                // Use the DrawCheckBox command to draw a checkbox and pass the button state to paint it checked or unchecked//                if (cb.Checked)//                    ControlPaint.DrawCheckBox(g, cb.Left, cb.Top, cb.Height / 2, cb.Height / 2, ButtonState.Checked);//                else//                    ControlPaint.DrawCheckBox(g, cb.Left, cb.Top, cb.Height / 2, cb.Height / 2, ButtonState.Normal);//                // Don't forget the checkbox text//                g.DrawString(cb.Text, cb.Font, new SolidBrush(cb.ForeColor), cb.Right - cb.Height - g.MeasureString(cb.Text, cb.Font).Width, cb.Top, new StringFormat());//                break;//        }//    }//}

/**
* /protected override void OnPaint(PaintEventArgs e)
* /{
* /    base.OnPaint(e);
* /}
*///void DrawForm(Graphics g, int resX, int resY)//{//    g.FillRectangle(new SolidBrush(this.BackColor), 0, 0, this.Width, this.Height);//    float scale = 1; // resX / ScreenResolution;//    // Cycle through each control on the form and paint it to the printe//    foreach (Control c in Controls)//    {//        // Get the time of the next control so we can unbox it//        string strType = c.GetType().ToString().Substring(c.GetType().ToString().LastIndexOf(".") + 1);//        switch (strType)//        {//            case "Button": Button b = (Button)c;//                // Use the ControlPaint method DrawButton in order to draw the button of the form//                ControlPaint.DrawButton(g, ((Button)c).Left, ((Button)c).Top, ((Button)c).Width, ((Button)c).Height, ButtonState.Normal);//                // We also need to draw the text//                g.DrawString(b.Text, b.Font, new SolidBrush(b.ForeColor), b.Left + b.Width / 2 - g.MeasureString(b.Text,//                b.Font).Width / 2, b.Top + b.Height / 2 - g.MeasureString("a", b.Font).Height / 2, new StringFormat());//                break;//            case "TextBox": TextBox t = (TextBox)c;//                // Draw a text box by drawing a pushed in button and filling the rectangle with the background color and the text//                // of the TextBox control//                // First the sunken border//                ControlPaint.DrawButton(g, t.Left, t.Top, t.Width, t.Height, ButtonState.Pushed);//                // Then fill it with the background of the textbox//                g.FillRectangle(new SolidBrush(t.BackColor), t.Left + 1, t.Top + 1, t.Width + 2, t.Height - 2);//                // Finally draw the string inside//                g.DrawString(t.Text, t.Font, new SolidBrush(t.ForeColor), t.Left + 2, t.Top + t.Height / 2 - g.MeasureString("a", t.Font).Height / 2, new StringFormat());//                break;//            case "CheckBox":// We have a checkbox to paint, unbox it//                CheckBox cb = (CheckBox)c;//                // Use the DrawCheckBox command to draw a checkbox and pass the button state to paint it checked or unchecked//                if (cb.Checked)//                    ControlPaint.DrawCheckBox(g, cb.Left, cb.Top, cb.Height / 2, cb.Height / 2, ButtonState.Checked);//                else//                    ControlPaint.DrawCheckBox(g, cb.Left, cb.Top, cb.Height / 2, cb.Height / 2, ButtonState.Normal);//                // Don't forget the checkbox text//                g.DrawString(cb.Text, cb.Font, new SolidBrush(cb.ForeColor), cb.Right - cb.Height - g.MeasureString(cb.Text, cb.Font).Width, cb.Top, new StringFormat());//                break;//        }//    }//}