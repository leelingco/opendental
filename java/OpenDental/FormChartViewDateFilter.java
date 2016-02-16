//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.ChartViewDates;

public class FormChartViewDateFilter  extends Form 
{

    /**
    * Set this date before opening the form.  Also after OK, this date is available to the calling class.
    */
    public DateTime DateStart = new DateTime();
    /**
    * Set this date before opening the form.  Also after OK, this date is available to the calling class.
    */
    public DateTime DateEnd = new DateTime();
    public FormChartViewDateFilter() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formChartViewDateFilter_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < Enum.GetNames(ChartViewDates.class).Length;i++)
        {
            listPresetDateRanges.Items.Add(Enum.GetNames(ChartViewDates.class)[i]);
        }
        fillDateTextBoxesHelper();
    }

    private void listPresetDateRanges_MouseClick(Object sender, MouseEventArgs e) throws Exception {
        int selectedI = listPresetDateRanges.IndexFromPoint(e.Location);
        switch(ChartViewDates.values()[selectedI])
        {
            case All: 
                DateStart = DateTime.MinValue;
                DateEnd = DateTime.MinValue;
                break;
            case Today: 
                //interpreted as empty.  We want to show all future dates.
                DateStart = DateTime.Today;
                DateEnd = DateTime.Today;
                break;
            case Yesterday: 
                DateStart = DateTime.Today.AddDays(-1);
                DateEnd = DateTime.Today.AddDays(-1);
                break;
            case ThisYear: 
                DateStart = new DateTime(DateTime.Today.Year, 1, 1);
                DateEnd = new DateTime(DateTime.Today.Year, 12, 31);
                break;
            case LastYear: 
                DateStart = new DateTime(DateTime.Today.Year - 1, 1, 1);
                DateEnd = new DateTime(DateTime.Today.Year - 1, 12, 31);
                break;
        
        }
        fillDateTextBoxesHelper();
    }

    private void fillDateTextBoxesHelper() throws Exception {
        textDateStart.Text = DateStart.ToShortDateString();
        textDateEnd.Text = DateEnd.ToShortDateString();
        if (DateStart.Year < 1880)
        {
            textDateStart.Text = "";
        }
         
        if (DateEnd.Year < 1880)
        {
            textDateEnd.Text = "";
        }
         
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //validate the date boxes.
        if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        DateStart = PIn.Date(textDateStart.Text);
        DateEnd = PIn.Date(textDateEnd.Text);
        DialogResult = DialogResult.OK;
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
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.listPresetDateRanges = new System.Windows.Forms.ListBox();
        this.textDateEnd = new OpenDental.ValidDate();
        this.textDateStart = new OpenDental.ValidDate();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(19, 87);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(84, 16);
        this.label1.TabIndex = 6;
        this.label1.Text = "Start Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(19, 113);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(84, 16);
        this.label2.TabIndex = 7;
        this.label2.Text = "End Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listPresetDateRanges
        //
        this.listPresetDateRanges.FormattingEnabled = true;
        this.listPresetDateRanges.Location = new System.Drawing.Point(105, 12);
        this.listPresetDateRanges.Name = "listPresetDateRanges";
        this.listPresetDateRanges.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listPresetDateRanges.Size = new System.Drawing.Size(91, 69);
        this.listPresetDateRanges.TabIndex = 8;
        this.listPresetDateRanges.MouseClick += new System.Windows.Forms.MouseEventHandler(this.listPresetDateRanges_MouseClick);
        //
        // textDateEnd
        //
        this.textDateEnd.Location = new System.Drawing.Point(105, 112);
        this.textDateEnd.Name = "textDateEnd";
        this.textDateEnd.Size = new System.Drawing.Size(91, 20);
        this.textDateEnd.TabIndex = 5;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(105, 86);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(91, 20);
        this.textDateStart.TabIndex = 4;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(125, 168);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
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
        this.butCancel.Location = new System.Drawing.Point(206, 168);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormChartViewDateFilter
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(299, 206);
        this.Controls.Add(this.listPresetDateRanges);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textDateEnd);
        this.Controls.Add(this.textDateStart);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormChartViewDateFilter";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Progress Notes Date Range Filter";
        this.Load += new System.EventHandler(this.FormChartViewDateFilter_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.ValidDate textDateStart;
    private OpenDental.ValidDate textDateEnd;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listPresetDateRanges = new System.Windows.Forms.ListBox();
}


