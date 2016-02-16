//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:18 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormLabCaseEdit;
import OpenDental.FormLabCases;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.GridSortingStrategy;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.LabCases;
import OpenDentBusiness.PrintSituation;

/**
* Summary description for FormBasicTemplate.
*/
public class FormLabCases  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid gridMain;
    private Label label1 = new Label();
    private OpenDental.ValidDate textDateFrom;
    private OpenDental.ValidDate textDateTo;
    private Label label2 = new Label();
    private OpenDental.UI.Button butRefresh;
    // Required designer variable.
    private DataTable table = new DataTable();
    private CheckBox checkShowAll = new CheckBox();
    private ContextMenu contextMenu1 = new ContextMenu();
    private MenuItem menuItemGoTo = new MenuItem();
    private CheckBox checkShowUnattached = new CheckBox();
    private OpenDental.UI.Button butPrint;
    //<summary>Set this to the selected date on the schedule, and date range will start out based on this date.</summary>
    //public DateTime DateViewing;
    /**
    * If this is zero, then it's an ordinary close.
    */
    public long GoToAptNum = new long();
    public boolean headingPrinted = new boolean();
    public int headingPrintH = new int();
    public int pagesPrinted = new int();
    /**
    * 
    */
    public FormLabCases() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLabCases.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.checkShowAll = new System.Windows.Forms.CheckBox();
        this.contextMenu1 = new System.Windows.Forms.ContextMenu();
        this.menuItemGoTo = new System.Windows.Forms.MenuItem();
        this.checkShowUnattached = new System.Windows.Forms.CheckBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butRefresh = new OpenDental.UI.Button();
        this.textDateTo = new OpenDental.ValidDate();
        this.textDateFrom = new OpenDental.ValidDate();
        this.butClose = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 18);
        this.label1.TabIndex = 2;
        this.label1.Text = "From Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 35);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 18);
        this.label2.TabIndex = 4;
        this.label2.Text = "To Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkShowAll
        //
        this.checkShowAll.Location = new System.Drawing.Point(361, 37);
        this.checkShowAll.Name = "checkShowAll";
        this.checkShowAll.Size = new System.Drawing.Size(131, 18);
        this.checkShowAll.TabIndex = 7;
        this.checkShowAll.Text = "Show Completed";
        this.checkShowAll.UseVisualStyleBackColor = true;
        //
        // contextMenu1
        //
        this.contextMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemGoTo });
        //
        // menuItemGoTo
        //
        this.menuItemGoTo.Index = 0;
        this.menuItemGoTo.Text = "Go To Appointment";
        this.menuItemGoTo.Click += new System.EventHandler(this.menuItemGoTo_Click);
        //
        // checkShowUnattached
        //
        this.checkShowUnattached.Location = new System.Drawing.Point(361, 14);
        this.checkShowUnattached.Name = "checkShowUnattached";
        this.checkShowUnattached.Size = new System.Drawing.Size(131, 18);
        this.checkShowUnattached.TabIndex = 8;
        this.checkShowUnattached.Text = "Show Unattached";
        this.checkShowUnattached.UseVisualStyleBackColor = true;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(17, 67);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(916, 420);
        this.gridMain.TabIndex = 1;
        this.gridMain.setTitle("Lab Cases");
        this.gridMain.setTranslationName("TableLabCases");
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
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(226, 32);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(75, 24);
        this.butRefresh.TabIndex = 6;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(116, 35);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(100, 20);
        this.textDateTo.TabIndex = 5;
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(116, 9);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(100, 20);
        this.textDateFrom.TabIndex = 3;
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(858, 497);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
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
        this.butPrint.Location = new System.Drawing.Point(413, 497);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(79, 24);
        this.butPrint.TabIndex = 53;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // FormLabCases
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(945, 533);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.checkShowUnattached);
        this.Controls.Add(this.checkShowAll);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.textDateTo);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textDateFrom);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormLabCases";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Lab Cases";
        this.Load += new System.EventHandler(this.FormLabCases_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formLabCases_Load(Object sender, EventArgs e) throws Exception {
        gridMain.ContextMenu = contextMenu1;
        textDateFrom.Text = "";
        //DateViewing.ToShortDateString();
        textDateTo.Text = "";
        //DateViewing.AddDays(5).ToShortDateString();
        //checkShowAll.Checked=false
        fillGrid();
    }

    private void fillGrid() throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), ""))
        {
            return ;
        }
         
        //MsgBox.Show(this,"Please fix errors first.");
        DateTime dateMax = new DateTime(2100, 1, 1);
        if (!StringSupport.equals(textDateTo.Text, ""))
        {
            dateMax = PIn.Date(textDateTo.Text);
        }
         
        table = LabCases.Refresh(PIn.Date(textDateFrom.Text), dateMax, checkShowAll.Checked, checkShowUnattached.Checked);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g("TableLabCases","Appt Date Time"),120);
        col.setSortingStrategy(GridSortingStrategy.DateParse);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabCases","Procedures"),200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabCases","Patient"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabCases","Status"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabCases","Lab"),75);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabCases","Lab Phone"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabCases","Instructions"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["aptDateTime"].ToString());
            row.getCells().Add(table.Rows[i]["ProcDescript"].ToString());
            row.getCells().Add(table.Rows[i]["patient"].ToString());
            row.getCells().Add(table.Rows[i]["status"].ToString());
            row.getCells().Add(table.Rows[i]["lab"].ToString());
            row.getCells().Add(table.Rows[i]["phone"].ToString());
            row.getCells().Add(table.Rows[i]["Instructions"].ToString());
            row.setTag(table.Rows[i]);
            gridMain.getRows().add(row);
        }
        gridMain.setAllowSortingByColumn(true);
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        DataRow row = (DataRow)gridMain.getRows().get___idx(e.getRow()).getTag();
        long selectedLabCase = PIn.Long(row["LabCaseNum"].ToString());
        FormLabCaseEdit FormL = new FormLabCaseEdit();
        FormL.CaseCur = LabCases.getOne(selectedLabCase);
        FormL.ShowDialog();
        if (FormL.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //don't refresh unless we have to.  It messes up the user's ordering.
        fillGrid();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (table.Rows[i]["LabCaseNum"].ToString() == selectedLabCase.ToString())
            {
                gridMain.setSelected(i,true);
                break;
            }
             
        }
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), ""))
        {
            MsgBox.show(this,"Please fix errors first.");
            return ;
        }
         
        fillGrid();
    }

    private void menuItemGoTo_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a lab case first.");
            return ;
        }
         
        if (StringSupport.equals(table.Rows[gridMain.getSelectedIndex()]["AptNum"].ToString(), "0"))
        {
            MsgBox.show(this,"There are no appointments for unattached lab cases.");
            return ;
        }
         
        Appointment apt = Appointments.GetOneApt(PIn.Long(table.Rows[gridMain.getSelectedIndex()]["AptNum"].ToString()));
        if (apt.AptStatus == ApptStatus.UnschedList)
        {
            MsgBox.show(this,"Cannot go to an unscheduled appointment");
            return ;
        }
         
        GoToAptNum = apt.AptNum;
        Close();
    }

    private void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = e.MarginBounds;
        //new Rectangle(50,40,800,1035);//Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = Lan.g(this,"Lab Cases");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
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
        } 
        g.Dispose();
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getRows().Count < 1)
        {
            MsgBox.show(this,"Nothing to print.");
            return ;
        }
         
        pagesPrinted = 0;
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        //pd.OriginAtMargins=true;
        pd.DefaultPageSettings.Landscape = true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        try
        {
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Lab case list printed"))
            {
                pd.Print();
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


