//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormRefAttachEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.RefAttach;
import OpenDental.RefAttaches;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ReferralToStatus;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormReferralProcTrack  extends Form 
{

    DataTable Table = new DataTable();
    List<RefAttach> RefAttachList = new List<RefAttach>();
    DateTime DateFrom = new DateTime();
    DateTime DateTo = new DateTime();
    int pagesPrinted = new int();
    boolean headingPrinted = new boolean();
    int headingPrintH = new int();
    public FormReferralProcTrack() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formReferralProcTrack_Load(Object sender, EventArgs e) throws Exception {
        DateFrom = DateTime.Now.AddMonths(-1);
        textDateFrom.Text = DateFrom.ToShortDateString();
        DateTo = DateTime.Now;
        textDateTo.Text = DateTo.ToShortDateString();
        fillGrid();
    }

    private void fillGrid() throws Exception {
        if (PIn.Date(textDateFrom.Text) == DateTime.MinValue || PIn.Date(textDateTo.Text) == DateTime.MinValue)
        {
            MsgBox.show(this,"Please enter valid To and From dates.");
            return ;
        }
         
        DateFrom = PIn.Date(textDateFrom.Text);
        DateTo = PIn.Date(textDateTo.Text);
        //todo: checkbox
        RefAttachList = RefAttaches.RefreshForReferralProcTrack(DateFrom, DateTo, checkComplete.Checked);
        Table = Procedures.GetReferred(DateFrom, DateTo, checkComplete.Checked);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g(this,"Patient"),125);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Referred To"),125);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Description"),125);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Note"),125);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Date Referred"),86);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Date Done"),86);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Status"),84);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        DateTime date = new DateTime();
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Patients.GetPat(PIn.Long(Table.Rows[i]["PatNum"].ToString())).GetNameLF());
            row.getCells().Add(Table.Rows[i]["LName"].ToString() + ", " + Table.Rows[i]["FName"].ToString() + " " + Table.Rows[i]["MName"].ToString());
            row.getCells().Add(ProcedureCodes.GetLaymanTerm(PIn.Long(Table.Rows[i]["CodeNum"].ToString())));
            row.getCells().Add(Table.Rows[i]["Note"].ToString());
            date = PIn.Date(Table.Rows[i]["RefDate"].ToString());
            if (date.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(date.ToShortDateString());
            } 
            date = PIn.Date(Table.Rows[i]["DateProcComplete"].ToString());
            if (date.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(date.ToShortDateString());
            } 
            ReferralToStatus refStatus = (ReferralToStatus)PIn.Int(Table.Rows[i]["RefToStatus"].ToString());
            if (refStatus == ReferralToStatus.None)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(refStatus.ToString());
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormRefAttachEdit FormRAE2 = new FormRefAttachEdit();
        RefAttach refattach = RefAttachList[e.getRow()].Copy();
        FormRAE2.RefAttachCur = refattach;
        FormRAE2.ShowDialog();
        fillGrid();
        for (int i = 0;i < RefAttachList.Count;i++)
        {
            //reselect
            if (RefAttachList[i].RefAttachNum == refattach.RefAttachNum)
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void checkComplete_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        pagesPrinted = 0;
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        //pd.OriginAtMargins=true;
        pd.DefaultPageSettings.Landscape = false;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        try
        {
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Referred procedure tracking list printed"))
            {
                pd.Print();
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = e.MarginBounds;
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = Lan.g(this,"Referred Procedure Tracking");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            if (checkComplete.Checked)
            {
                text = "Including Incomplete Procedures";
            }
            else
            {
                text = "Including Only Complete Procedures";
            } 
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += 20;
            text = "From " + DateFrom.ToShortDateString() + " to " + DateTo.ToShortDateString();
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += 20;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = gridMain.printPage(g,pagesPrinted,bounds,headingPrintH);
        pagesPrinted++;
        if (yPos == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
            text = "Total Referrals: " + RefAttachList.Count;
            g.DrawString(text, subHeadingFont, Brushes.Black, center + gridMain.Width / 2 - g.MeasureString(text, subHeadingFont).Width - 10, yPos);
        } 
        g.Dispose();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.textDateFrom = new System.Windows.Forms.TextBox();
        this.textDateTo = new System.Windows.Forms.TextBox();
        this.checkComplete = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butPrint = new OpenDental.UI.Button();
        this.butRefresh = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(110, 19);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(100, 20);
        this.textDateFrom.TabIndex = 5;
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(311, 19);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(100, 20);
        this.textDateTo.TabIndex = 5;
        //
        // checkComplete
        //
        this.checkComplete.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkComplete.Location = new System.Drawing.Point(441, 21);
        this.checkComplete.Name = "checkComplete";
        this.checkComplete.Size = new System.Drawing.Size(178, 17);
        this.checkComplete.TabIndex = 6;
        this.checkComplete.Text = "Show Completed Procedures";
        this.checkComplete.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkComplete.UseVisualStyleBackColor = true;
        this.checkComplete.Click += new System.EventHandler(this.checkComplete_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(4, 22);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 13);
        this.label1.TabIndex = 7;
        this.label1.Text = "Date From";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(216, 22);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(89, 13);
        this.label2.TabIndex = 7;
        this.label2.Text = "Date To";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(23, 55);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(762, 411);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Referred Procedures");
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
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(23, 488);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(79, 24);
        this.butPrint.TabIndex = 53;
        this.butPrint.Text = "&Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(710, 15);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(75, 24);
        this.butRefresh.TabIndex = 3;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(710, 488);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormReferralProcTrack
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(810, 534);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkComplete);
        this.Controls.Add(this.textDateTo);
        this.Controls.Add(this.textDateFrom);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.butClose);
        this.Name = "FormReferralProcTrack";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Referred Procedure Tracking";
        this.Load += new System.EventHandler(this.FormReferralProcTrack_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.TextBox textDateFrom = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDateTo = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkComplete = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butRefresh;
    private OpenDental.UI.Button butPrint;
}


