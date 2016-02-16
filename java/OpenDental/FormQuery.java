//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:03 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.FormQuery;
import OpenDental.FormQueryEdit;
import OpenDental.FormQueryFavorites;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.ReportSimpleGrid;
import OpenDental.UserQuery;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.DentalSpecialty;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.Month;
import OpenDentBusiness.Operatories;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientGrade;
import OpenDentBusiness.PatientLogic;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.PatientRaceOld;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.Relat;
import OpenDentBusiness.ScheduleType;
import OpenDentBusiness.Security;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.TreatmentArea;
import OpenDentBusiness.TreatmentUrgency;
import OpenDentBusiness.YN;

//using Excel;
/**
* This is getting very outdated.  I realize it is difficult to use and will be phased out soon. The report displayed will be based on report.TableQ and report.
*/
public class FormQuery  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.DataGrid grid2 = new System.Windows.Forms.DataGrid();
    private System.Windows.Forms.Panel panelTop = new System.Windows.Forms.Panel();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butSubmit;
    private System.Windows.Forms.RadioButton radioRaw = new System.Windows.Forms.RadioButton();
    /**
    * 
    */
    public System.Windows.Forms.RadioButton radioHuman = new System.Windows.Forms.RadioButton();
    private OpenDental.UI.Button butFormulate;
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    private OpenDental.UI.Button butAdd;
    private DataGridTableStyle myGridTS = new DataGridTableStyle();
    private System.Windows.Forms.PrintPreviewDialog printPreviewDialog2 = new System.Windows.Forms.PrintPreviewDialog();
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private boolean totalsPrinted = new boolean();
    private boolean summaryPrinted = new boolean();
    private int linesPrinted = new int();
    private int pagesPrinted = new int();
    /**
    * 
    */
    public boolean IsReport = new boolean();
    private boolean headerPrinted = new boolean();
    private System.Windows.Forms.PrintPreviewControl printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
    private boolean tablePrinted = new boolean();
    private System.Drawing.Font titleFont = new System.Drawing.Font("Arial", 14, FontStyle.Bold);
    private System.Drawing.Font subtitleFont = new System.Drawing.Font("Arial", 8, FontStyle.Bold);
    private System.Drawing.Font colCaptFont = new System.Drawing.Font("Arial", 8, FontStyle.Bold);
    private System.Drawing.Font bodyFont = new System.Drawing.Font("Arial", 8);
    private OpenDental.UI.Button butFullPage;
    private System.Windows.Forms.Panel panelZoom = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Label labelTotPages = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    /**
    * 
    */
    public System.Windows.Forms.TextBox textTitle = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.SaveFileDialog saveFileDialog2 = new System.Windows.Forms.SaveFileDialog();
    private OpenDental.UI.Button butCopy;
    private OpenDental.UI.Button butPaste;
    private OpenDental.UI.Button butZoomIn;
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.Button butExport;
    private OpenDental.UI.Button butQView;
    private OpenDental.UI.Button butPrintPreview;
    private OpenDental.UI.Button butBack;
    private OpenDental.UI.Button butFwd;
    private OpenDental.UI.Button butExportExcel;
    /**
    * 
    */
    public OpenDental.ODtextBox textQuery;
    private int totalPages = 0;
    private static Hashtable hListPlans = new Hashtable();
    private UserQuery UserQueryCur = new UserQuery();
    //never gets used.  It's a holdover.
    private static Dictionary<long, String> patientNames = new Dictionary<long, String>();
    private ReportSimpleGrid report;
    /**
    * Can pass in null if not a report.
    */
    public FormQuery(ReportSimpleGrid report) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        //exclude:
        Lan.F(this, new System.Windows.Forms.Control[]{ labelTotPages });
        this.report = report;
    }

    /**
    * 
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormQuery.class);
        this.butClose = new OpenDental.UI.Button();
        this.grid2 = new System.Windows.Forms.DataGrid();
        this.panelTop = new System.Windows.Forms.Panel();
        this.textQuery = new OpenDental.ODtextBox();
        this.butExportExcel = new OpenDental.UI.Button();
        this.butPaste = new OpenDental.UI.Button();
        this.butCopy = new OpenDental.UI.Button();
        this.textTitle = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butAdd = new OpenDental.UI.Button();
        this.butFormulate = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.radioHuman = new System.Windows.Forms.RadioButton();
        this.radioRaw = new System.Windows.Forms.RadioButton();
        this.butSubmit = new OpenDental.UI.Button();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.printPreviewDialog2 = new System.Windows.Forms.PrintPreviewDialog();
        this.printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
        this.butFullPage = new OpenDental.UI.Button();
        this.panelZoom = new System.Windows.Forms.Panel();
        this.labelTotPages = new System.Windows.Forms.Label();
        this.butZoomIn = new OpenDental.UI.Button();
        this.butBack = new OpenDental.UI.Button();
        this.butFwd = new OpenDental.UI.Button();
        this.saveFileDialog2 = new System.Windows.Forms.SaveFileDialog();
        this.butPrint = new OpenDental.UI.Button();
        this.butExport = new OpenDental.UI.Button();
        this.butQView = new OpenDental.UI.Button();
        this.butPrintPreview = new OpenDental.UI.Button();
        ((System.ComponentModel.ISupportInitialize)(this.grid2)).BeginInit();
        this.panelTop.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.panelZoom.SuspendLayout();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(878, 755);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 27);
        this.butClose.TabIndex = 5;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // grid2
        //
        this.grid2.DataMember = "";
        this.grid2.HeaderForeColor = System.Drawing.SystemColors.ControlText;
        this.grid2.Location = new System.Drawing.Point(0, 150);
        this.grid2.Name = "grid2";
        this.grid2.ReadOnly = true;
        this.grid2.Size = new System.Drawing.Size(958, 590);
        this.grid2.TabIndex = 1;
        //
        // panelTop
        //
        this.panelTop.Controls.Add(this.textQuery);
        this.panelTop.Controls.Add(this.butExportExcel);
        this.panelTop.Controls.Add(this.butPaste);
        this.panelTop.Controls.Add(this.butCopy);
        this.panelTop.Controls.Add(this.textTitle);
        this.panelTop.Controls.Add(this.label1);
        this.panelTop.Controls.Add(this.butAdd);
        this.panelTop.Controls.Add(this.butFormulate);
        this.panelTop.Controls.Add(this.groupBox1);
        this.panelTop.Controls.Add(this.butSubmit);
        this.panelTop.Location = new System.Drawing.Point(0, 0);
        this.panelTop.Name = "panelTop";
        this.panelTop.Size = new System.Drawing.Size(956, 104);
        this.panelTop.TabIndex = 2;
        //
        // textQuery
        //
        this.textQuery.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textQuery.Location = new System.Drawing.Point(2, 3);
        this.textQuery.Name = "textQuery";
        this.textQuery.setQuickPasteType(OpenDentBusiness.QuickPasteType.Query);
        this.textQuery.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textQuery.Size = new System.Drawing.Size(551, 76);
        this.textQuery.setSpellCheckIsEnabled(false);
        this.textQuery.TabIndex = 16;
        this.textQuery.Text = "";
        //
        // butExportExcel
        //
        this.butExportExcel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExportExcel.setAutosize(true);
        this.butExportExcel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExportExcel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExportExcel.setCornerRadius(4F);
        this.butExportExcel.Image = Resources.getbutExportExcel();
        this.butExportExcel.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butExportExcel.Location = new System.Drawing.Point(712, 69);
        this.butExportExcel.Name = "butExportExcel";
        this.butExportExcel.Size = new System.Drawing.Size(79, 26);
        this.butExportExcel.TabIndex = 15;
        this.butExportExcel.Text = "Excel";
        this.butExportExcel.Visible = false;
        this.butExportExcel.Click += new System.EventHandler(this.butExportExcel_Click);
        //
        // butPaste
        //
        this.butPaste.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPaste.setAutosize(true);
        this.butPaste.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPaste.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPaste.setCornerRadius(4F);
        this.butPaste.Image = Resources.getbutPaste();
        this.butPaste.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPaste.Location = new System.Drawing.Point(631, 54);
        this.butPaste.Name = "butPaste";
        this.butPaste.Size = new System.Drawing.Size(65, 23);
        this.butPaste.TabIndex = 11;
        this.butPaste.Text = "Paste";
        this.butPaste.Click += new System.EventHandler(this.butPaste_Click);
        //
        // butCopy
        //
        this.butCopy.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopy.setAutosize(true);
        this.butCopy.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopy.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopy.setCornerRadius(4F);
        this.butCopy.Image = Resources.getbutCopy();
        this.butCopy.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCopy.Location = new System.Drawing.Point(556, 54);
        this.butCopy.Name = "butCopy";
        this.butCopy.Size = new System.Drawing.Size(72, 23);
        this.butCopy.TabIndex = 10;
        this.butCopy.Text = "Copy";
        this.butCopy.Click += new System.EventHandler(this.butCopy_Click);
        //
        // textTitle
        //
        this.textTitle.Location = new System.Drawing.Point(66, 82);
        this.textTitle.Name = "textTitle";
        this.textTitle.Size = new System.Drawing.Size(219, 20);
        this.textTitle.TabIndex = 1;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(7, 84);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(54, 13);
        this.label1.TabIndex = 9;
        this.label1.Text = "Title";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Location = new System.Drawing.Point(556, 30);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(140, 23);
        this.butAdd.TabIndex = 3;
        this.butAdd.Text = "Add To Favorites";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butFormulate
        //
        this.butFormulate.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butFormulate.setAutosize(true);
        this.butFormulate.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFormulate.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFormulate.setCornerRadius(4F);
        this.butFormulate.Location = new System.Drawing.Point(556, 6);
        this.butFormulate.Name = "butFormulate";
        this.butFormulate.Size = new System.Drawing.Size(140, 23);
        this.butFormulate.TabIndex = 2;
        this.butFormulate.Text = "Favorites";
        this.butFormulate.Click += new System.EventHandler(this.butFormulate_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.radioHuman);
        this.groupBox1.Controls.Add(this.radioRaw);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(715, 6);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(122, 58);
        this.groupBox1.TabIndex = 7;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Format";
        //
        // radioHuman
        //
        this.radioHuman.Checked = true;
        this.radioHuman.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioHuman.Location = new System.Drawing.Point(10, 16);
        this.radioHuman.Name = "radioHuman";
        this.radioHuman.Size = new System.Drawing.Size(108, 16);
        this.radioHuman.TabIndex = 0;
        this.radioHuman.TabStop = true;
        this.radioHuman.Text = "Human-readable";
        this.radioHuman.Click += new System.EventHandler(this.radioHuman_Click);
        //
        // radioRaw
        //
        this.radioRaw.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioRaw.Location = new System.Drawing.Point(10, 34);
        this.radioRaw.Name = "radioRaw";
        this.radioRaw.Size = new System.Drawing.Size(104, 16);
        this.radioRaw.TabIndex = 1;
        this.radioRaw.Text = "Raw";
        this.radioRaw.Click += new System.EventHandler(this.radioRaw_Click);
        //
        // butSubmit
        //
        this.butSubmit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSubmit.setAutosize(true);
        this.butSubmit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSubmit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSubmit.setCornerRadius(4F);
        this.butSubmit.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butSubmit.Location = new System.Drawing.Point(556, 78);
        this.butSubmit.Name = "butSubmit";
        this.butSubmit.Size = new System.Drawing.Size(102, 23);
        this.butSubmit.TabIndex = 6;
        this.butSubmit.Text = "&Submit Query";
        this.butSubmit.Click += new System.EventHandler(this.butSubmit_Click);
        //
        // printPreviewDialog2
        //
        this.printPreviewDialog2.AutoScrollMargin = new System.Drawing.Size(0, 0);
        this.printPreviewDialog2.AutoScrollMinSize = new System.Drawing.Size(0, 0);
        this.printPreviewDialog2.ClientSize = new System.Drawing.Size(400, 300);
        this.printPreviewDialog2.Enabled = true;
        this.printPreviewDialog2.Icon = ((System.Drawing.Icon)(resources.GetObject("printPreviewDialog2.Icon")));
        this.printPreviewDialog2.Name = "printPreviewDialog2";
        this.printPreviewDialog2.Visible = false;
        //
        // printPreviewControl2
        //
        this.printPreviewControl2.AutoZoom = false;
        this.printPreviewControl2.Location = new System.Drawing.Point(18, 136);
        this.printPreviewControl2.Name = "printPreviewControl2";
        this.printPreviewControl2.Size = new System.Drawing.Size(842, 538);
        this.printPreviewControl2.TabIndex = 5;
        this.printPreviewControl2.Zoom = 1D;
        //
        // butFullPage
        //
        this.butFullPage.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butFullPage.setAutosize(true);
        this.butFullPage.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFullPage.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFullPage.setCornerRadius(4F);
        this.butFullPage.Location = new System.Drawing.Point(9, 5);
        this.butFullPage.Name = "butFullPage";
        this.butFullPage.Size = new System.Drawing.Size(75, 27);
        this.butFullPage.TabIndex = 9;
        this.butFullPage.Text = "&Full Page";
        this.butFullPage.Visible = false;
        this.butFullPage.Click += new System.EventHandler(this.butFullPage_Click);
        //
        // panelZoom
        //
        this.panelZoom.Controls.Add(this.labelTotPages);
        this.panelZoom.Controls.Add(this.butFullPage);
        this.panelZoom.Controls.Add(this.butZoomIn);
        this.panelZoom.Controls.Add(this.butBack);
        this.panelZoom.Controls.Add(this.butFwd);
        this.panelZoom.Location = new System.Drawing.Point(336, 746);
        this.panelZoom.Name = "panelZoom";
        this.panelZoom.Size = new System.Drawing.Size(229, 37);
        this.panelZoom.TabIndex = 0;
        this.panelZoom.Visible = false;
        //
        // labelTotPages
        //
        this.labelTotPages.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTotPages.Location = new System.Drawing.Point(143, 10);
        this.labelTotPages.Name = "labelTotPages";
        this.labelTotPages.Size = new System.Drawing.Size(52, 18);
        this.labelTotPages.TabIndex = 11;
        this.labelTotPages.Text = "25 / 26";
        this.labelTotPages.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // butZoomIn
        //
        this.butZoomIn.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butZoomIn.setAutosize(true);
        this.butZoomIn.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butZoomIn.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butZoomIn.setCornerRadius(4F);
        this.butZoomIn.Image = Resources.getbutZoomIn();
        this.butZoomIn.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butZoomIn.Location = new System.Drawing.Point(9, 5);
        this.butZoomIn.Name = "butZoomIn";
        this.butZoomIn.Size = new System.Drawing.Size(91, 26);
        this.butZoomIn.TabIndex = 12;
        this.butZoomIn.Text = "Zoom In";
        this.butZoomIn.Click += new System.EventHandler(this.butZoomIn_Click);
        //
        // butBack
        //
        this.butBack.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBack.setAutosize(true);
        this.butBack.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBack.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBack.setCornerRadius(4F);
        this.butBack.Image = Resources.getLeft();
        this.butBack.Location = new System.Drawing.Point(123, 7);
        this.butBack.Name = "butBack";
        this.butBack.Size = new System.Drawing.Size(18, 23);
        this.butBack.TabIndex = 17;
        this.butBack.Click += new System.EventHandler(this.butBack_Click);
        //
        // butFwd
        //
        this.butFwd.setAdjustImageLocation(new System.Drawing.Point(1, 0));
        this.butFwd.setAutosize(true);
        this.butFwd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFwd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFwd.setCornerRadius(4F);
        this.butFwd.Image = Resources.getRight();
        this.butFwd.Location = new System.Drawing.Point(195, 7);
        this.butFwd.Name = "butFwd";
        this.butFwd.Size = new System.Drawing.Size(18, 23);
        this.butFwd.TabIndex = 18;
        this.butFwd.Click += new System.EventHandler(this.butFwd_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(784, 755);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(79, 26);
        this.butPrint.TabIndex = 13;
        this.butPrint.Text = "&Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butExport
        //
        this.butExport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExport.setAutosize(true);
        this.butExport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExport.setCornerRadius(4F);
        this.butExport.Image = Resources.getbutExport();
        this.butExport.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butExport.Location = new System.Drawing.Point(689, 755);
        this.butExport.Name = "butExport";
        this.butExport.Size = new System.Drawing.Size(79, 26);
        this.butExport.TabIndex = 14;
        this.butExport.Text = "&Export";
        this.butExport.Click += new System.EventHandler(this.butExport_Click);
        //
        // butQView
        //
        this.butQView.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butQView.setAutosize(true);
        this.butQView.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butQView.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butQView.setCornerRadius(4F);
        this.butQView.Image = Resources.getbutQView();
        this.butQView.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butQView.Location = new System.Drawing.Point(573, 741);
        this.butQView.Name = "butQView";
        this.butQView.Size = new System.Drawing.Size(104, 26);
        this.butQView.TabIndex = 15;
        this.butQView.Text = "&Query View";
        this.butQView.Click += new System.EventHandler(this.butQView_Click);
        //
        // butPrintPreview
        //
        this.butPrintPreview.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrintPreview.setAutosize(true);
        this.butPrintPreview.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrintPreview.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrintPreview.setCornerRadius(4F);
        this.butPrintPreview.Image = Resources.getbutPreview();
        this.butPrintPreview.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrintPreview.Location = new System.Drawing.Point(572, 755);
        this.butPrintPreview.Name = "butPrintPreview";
        this.butPrintPreview.Size = new System.Drawing.Size(113, 26);
        this.butPrintPreview.TabIndex = 16;
        this.butPrintPreview.Text = "P&rint Preview";
        this.butPrintPreview.Click += new System.EventHandler(this.butPrintPreview_Click);
        //
        // FormQuery
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(963, 788);
        this.Controls.Add(this.butPrintPreview);
        this.Controls.Add(this.butQView);
        this.Controls.Add(this.butExport);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.panelZoom);
        this.Controls.Add(this.printPreviewControl2);
        this.Controls.Add(this.grid2);
        this.Controls.Add(this.panelTop);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormQuery";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Query";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormQuery_Closing);
        this.Load += new System.EventHandler(this.FormQuery_Load);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.FormQuery_Layout);
        ((System.ComponentModel.ISupportInitialize)(this.grid2)).EndInit();
        this.panelTop.ResumeLayout(false);
        this.panelTop.PerformLayout();
        this.groupBox1.ResumeLayout(false);
        this.panelZoom.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formQuery_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        printPreviewControl2.Location = new System.Drawing.Point(0, 0);
        printPreviewControl2.Height = ClientSize.Height - 39;
        printPreviewControl2.Width = ClientSize.Width;
        panelTop.Width = ClientSize.Width;
        grid2.Location = new System.Drawing.Point(2, panelTop.Height);
        grid2.Height = ClientSize.Height - grid2.Location.Y - 39;
        grid2.Width = ClientSize.Width - 2;
        butClose.Location = new System.Drawing.Point(ClientSize.Width - 90, ClientSize.Height - 34);
        butExport.Location = new System.Drawing.Point(ClientSize.Width - 180, ClientSize.Height - 34);
        butPrint.Location = new System.Drawing.Point(ClientSize.Width - 270, ClientSize.Height - 34);
        butPrintPreview.Location = new System.Drawing.Point(ClientSize.Width - 385, ClientSize.Height - 34);
        butQView.Location = new System.Drawing.Point(ClientSize.Width - 385, ClientSize.Height - 34);
        panelZoom.Location = new System.Drawing.Point(ClientSize.Width - 620, ClientSize.Height - 38);
    }

    private void formQuery_Load(Object sender, System.EventArgs e) throws Exception {
        //report.TableQ=null;//this will crash the program
        grid2.Font = bodyFont;
        if (IsReport)
        {
            printPreviewControl2.Visible = true;
            Text = "Report";
            butPrintPreview.Visible = false;
            panelZoom.Visible = true;
            printReport(true);
            labelTotPages.Text = "/ " + totalPages.ToString();
            if (PrefC.getBool(PrefName.FuchsOptionsOn))
            {
                butFullPage.Visible = true;
                butZoomIn.Visible = false;
                printPreviewControl2.Zoom = 1;
            }
            else
            {
                printPreviewControl2.Zoom = ((double)printPreviewControl2.ClientSize.Height / (double)pd2.DefaultPageSettings.PaperSize.Height);
            } 
        }
        else
        {
            printPreviewControl2.Visible = false;
            Text = Lan.g(this,"Query");
        } 
    }

    private void butSubmit_Click(Object sender, System.EventArgs e) throws Exception {
        report = new ReportSimpleGrid();
        report.Query = textQuery.Text;
        submitQuery();
    }

    /**
    * This is used internally instead of SubmitReportQuery.  Can also be called externally if we want to automate a userquery.  Column names will be handled automatically.
    */
    public void submitQuery() throws Exception {
        patientNames = Patients.getAllPatientNames();
        try
        {
            //hListPlans=InsPlans.GetHListAll();
            report.submitQuery();
        }
        catch (Exception ex)
        {
            MsgBox.show(this,"Invalid query: " + ex.Message);
            return ;
        }

        /* (for later if more complex queries with loops:)
        			//SubmitQueryThread();
        			//Thread Thread2 = new Thread(new ThreadStart(SubmitQueryThread));
        			//Thread2.Start();
              //FormProcessWaiting fpw = new FormProcessWaiting();
        			//while(Thread2.ThreadState!=ThreadState.Stopped)				//{
        			//	;
        			//			if(!fpw.Created)  {
        			//		    fpw.ShowDialog();
        			//			}
        			//			if(fpw.DialogResult==DialogResult.Abort)  {
        			//				Thread2.Suspend();
        			//		  break;
        			//		}
        			//		}
        		  //  fpw.Close();
        			//Thread2.Abort();
        			//ThreadState.
        			//if(MessageBox.Show("Waiting for Server","",MessageBoxButtons.
        			//Wait for dialog result
        			//If abort, then skip the rest of this.*/
        grid2.TableStyles.Clear();
        grid2.SetDataBinding(report.TableQ, "");
        myGridTS = new DataGridTableStyle();
        grid2.TableStyles.Add(myGridTS);
        if (radioHuman.Checked)
        {
            report.TableQ = makeReadable(report.TableQ,report);
            grid2.TableStyles.Clear();
            grid2.SetDataBinding(report.TableQ, "");
            grid2.TableStyles.Add(myGridTS);
        }
         
        //if(!IsReport){
        autoSizeColumns();
        /*for(int i=0;i<doubleCount;i++){
        					int colTotal=0;
        					for(int iRow=0;iRow<report.TableQ.Rows.Count;i++){
        						
        					}
        					report.Summary[i]="TOTAL :"+;
        				}*/
        report.Title = textTitle.Text;
        report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
        for (int iCol = 0;iCol < report.TableQ.Columns.Count;iCol++)
        {
            report.getColCaption()[iCol] = report.TableQ.Columns[iCol].Caption;
            //myGridTS.GridColumnStyles[iCol].HeaderText;
            //again, I don't know why this would fail, so here's a check:
            if (myGridTS.GridColumnStyles.Count >= iCol + 1)
            {
                myGridTS.GridColumnStyles[iCol].Alignment = report.getColAlign()[iCol];
            }
             
        }
    }

    //}
    //private void SubmitQueryThread(){
    //Queries.SubmitCur();
    //}
    /**
    * When used as a report, this is called externally. Used instead of SubmitQuery(). Column names can be handled manually by the external form calling this report.
    */
    public void submitReportQuery() throws Exception {
        report.submitQuery();
        grid2.TableStyles.Clear();
        grid2.SetDataBinding(report.TableQ, "");
        myGridTS = new DataGridTableStyle();
        grid2.TableStyles.Add(myGridTS);
        report.TableQ = makeReadable(report.TableQ,report);
        //?
        grid2.SetDataBinding(report.TableQ, "");
    }

    //because MakeReadable trashes the TableQ
    /*
    		///<summary>When used as a report, this is called externally. Used instead of SubmitQuery(). Column names will be handled manually by the external form calling this report.</summary>
    		public void SubmitReportQuery(DataTable table) {
    			report.TableQ=table;
    			report.ColWidth=new int[report.TableQ.Columns.Count];
    			report.ColPos=new int[report.TableQ.Columns.Count+1];
    			report.ColPos[0]=0;
    			report.ColCaption=new string[report.TableQ.Columns.Count];
    			report.ColAlign=new HorizontalAlignment[report.TableQ.Columns.Count];
    			report.ColTotal=new double[report.TableQ.Columns.Count];
    			grid2.TableStyles.Clear();
    			grid2.SetDataBinding(report.TableQ,"");
    			myGridTS = new DataGridTableStyle();
    			grid2.TableStyles.Add(myGridTS);
    			report.TableQ=MakeReadable(report.TableQ);//?
    			grid2.SetDataBinding(report.TableQ,"");//because MakeReadable trashes the TableQ
    		}*/
    /**
    * 
    */
    public void resetGrid() throws Exception {
        grid2.TableStyles.Clear();
        grid2.SetDataBinding(report.TableQ, "");
        myGridTS = new DataGridTableStyle();
        grid2.TableStyles.Add(myGridTS);
    }

    private void autoSizeColumns() throws Exception {
        Graphics grfx = this.CreateGraphics();
        //int colWidth;
        int tempWidth = new int();
        for (int i = 0;i < report.getColWidth().Length;i++)
        {
            //for(int i=0;i<myGridTS.GridColumnStyles.Count;i++){
            report.getColWidth()[i] = (int)grfx.MeasureString(report.TableQ.Columns[i].Caption, grid2.Font).Width;
            for (int j = 0;j < report.TableQ.Rows.Count;j++)
            {
                //myGridTS.GridColumnStyles[i].HeaderText,grid2.Font).Width;
                tempWidth = (int)grfx.MeasureString(report.TableQ.Rows[j][i].ToString(), grid2.Font).Width;
                if (tempWidth > report.getColWidth()[i])
                    report.getColWidth()[i] = tempWidth;
                 
            }
            if (report.getColWidth()[i] > 400)
            {
                report.getColWidth()[i] = 400;
            }
             
            //I have no idea why this is failing, so we'll just do a check:
            if (myGridTS.GridColumnStyles.Count >= i + 1)
            {
                myGridTS.GridColumnStyles[i].Width = report.getColWidth()[i] + 12;
            }
             
            report.getColWidth()[i] += 6;
            //so the columns don't touch
            report.getColPos()[i + 1] = report.getColPos()[i] + report.getColWidth()[i];
        }
    }

    /**
    * Starting to use this externally as well.  OK to pass in a null report.
    */
    public static DataTable makeReadable(DataTable tableIn, ReportSimpleGrid reportIn) throws Exception {
        //this can probably be improved upon later for speed
        if (hListPlans == null)
        {
            hListPlans = InsPlans.getHListAll();
        }
         
        DataTable tableOut = tableIn.Clone();
        for (int j = 0;j < tableOut.Columns.Count;j++)
        {
            //copies just the structure
            tableOut.Columns[j].DataType = String.class;
        }
        DataRow thisRow = new DataRow();
        for (int i = 0;i < tableIn.Rows.Count;i++)
        {
            //copy data from tableInput to tableOutput while converting to strings
            //string str;
            //Type t;
            thisRow = tableOut.NewRow();
            for (int j = 0;j < tableIn.Columns.Count;j++)
            {
                //new row with new schema
                thisRow[j] = PIn.ByteArray(tableIn.Rows[i][j]);
            }
            //str=tableIn.Rows[i][j].ToString();
            //t=tableIn.Rows[i][j].GetType();
            //thisRow[j]=str;
            tableOut.Rows.Add(thisRow);
        }
        DateTime date = new DateTime();
        double[] colTotals = new double[tableOut.Columns.Count];
        for (int j = 0;j < tableOut.Columns.Count;j++)
        {
            for (int i = 0;i < tableOut.Rows.Count;i++)
            {
                try
                {
                    if (StringSupport.equals(tableOut.Columns[j].Caption.Substring(0, 1), "$"))
                    {
                        tableOut.Rows[i][j] = PIn.Double(tableOut.Rows[i][j].ToString()).ToString("F");
                        if (reportIn != null)
                        {
                            reportIn.getColAlign()[j] = HorizontalAlignment.Right;
                            colTotals[j] += PIn.Decimal(tableOut.Rows[i][j].ToString());
                        }
                         
                    }
                    else if (tableOut.Columns[j].Caption.ToLower().StartsWith("date"))
                    {
                        date = PIn.Date(tableOut.Rows[i][j].ToString());
                        if (date.Year < 1880)
                        {
                            tableOut.Rows[i][j] = "";
                        }
                        else
                        {
                            tableOut.Rows[i][j] = date.ToString("d");
                        } 
                    }
                    else
                    {
                        Caption.APPLY __dummyScrutVar0 = tableOut.Columns[j].Caption.ToLower();
                        //bool
                        if (__dummyScrutVar0.equals("isprosthesis") || __dummyScrutVar0.equals("ispreventive") || __dummyScrutVar0.equals("ishidden") || __dummyScrutVar0.equals("isrecall") || __dummyScrutVar0.equals("usedefaultfee") || __dummyScrutVar0.equals("usedefaultcov") || __dummyScrutVar0.equals("isdiscount") || __dummyScrutVar0.equals("removetooth") || __dummyScrutVar0.equals("setrecall") || __dummyScrutVar0.equals("nobillins") || __dummyScrutVar0.equals("isprosth") || __dummyScrutVar0.equals("ishygiene") || __dummyScrutVar0.equals("issecondary") || __dummyScrutVar0.equals("orpribool") || __dummyScrutVar0.equals("orsecbool") || __dummyScrutVar0.equals("issplit") || __dummyScrutVar0.equals("ispreauth") || __dummyScrutVar0.equals("isortho") || __dummyScrutVar0.equals("releaseinfo") || __dummyScrutVar0.equals("assignben") || __dummyScrutVar0.equals("enabled") || __dummyScrutVar0.equals("issystem") || __dummyScrutVar0.equals("usingtin") || __dummyScrutVar0.equals("sigonfile") || __dummyScrutVar0.equals("notperson") || __dummyScrutVar0.equals("isfrom"))
                        {
                            tableOut.Rows[i][j] = PIn.Bool(tableOut.Rows[i][j].ToString()).ToString();
                        }
                        else //date. Some of these are actually handled further up.
                        if (__dummyScrutVar0.equals("adjdate") || __dummyScrutVar0.equals("baldate") || __dummyScrutVar0.equals("dateservice") || __dummyScrutVar0.equals("datesent") || __dummyScrutVar0.equals("datereceived") || __dummyScrutVar0.equals("priordate") || __dummyScrutVar0.equals("date") || __dummyScrutVar0.equals("dateviewing") || __dummyScrutVar0.equals("datecreated") || __dummyScrutVar0.equals("dateeffective") || __dummyScrutVar0.equals("dateterm") || __dummyScrutVar0.equals("paydate") || __dummyScrutVar0.equals("procdate") || __dummyScrutVar0.equals("rxdate") || __dummyScrutVar0.equals("birthdate") || __dummyScrutVar0.equals("monthyear") || __dummyScrutVar0.equals("accidentdate") || __dummyScrutVar0.equals("orthodate") || __dummyScrutVar0.equals("checkdate") || __dummyScrutVar0.equals("screendate") || __dummyScrutVar0.equals("datedue") || __dummyScrutVar0.equals("dateduecalc") || __dummyScrutVar0.equals("datefirstvisit") || __dummyScrutVar0.equals("mydate"))
                        {
                            //this is a workaround for the daily payment report
                            tableOut.Rows[i][j] = PIn.Date(tableOut.Rows[i][j].ToString()).ToString("d");
                        }
                        else //age
                        if (__dummyScrutVar0.equals("birthdateforage"))
                        {
                            tableOut.Rows[i][j] = PatientLogic.DateToAgeString(PIn.Date(tableOut.Rows[i][j].ToString()));
                        }
                        else //time
                        if (__dummyScrutVar0.equals("aptdatetime") || __dummyScrutVar0.equals("nextschedappt") || __dummyScrutVar0.equals("starttime") || __dummyScrutVar0.equals("stoptime"))
                        {
                            tableOut.Rows[i][j] = PIn.DateT(tableOut.Rows[i][j].ToString()).ToString("t") + "   " + PIn.DateT(tableOut.Rows[i][j].ToString()).ToString("d");
                        }
                        else //TimeCardManage
                        if (__dummyScrutVar0.equals("adjevent") || __dummyScrutVar0.equals("adjreg") || __dummyScrutVar0.equals("adjotime") || __dummyScrutVar0.equals("breaktime") || __dummyScrutVar0.equals("temptotaltime") || __dummyScrutVar0.equals("tempreghrs") || __dummyScrutVar0.equals("tempovertime"))
                        {
                            if (PrefC.getBool(PrefName.TimeCardsUseDecimalInsteadOfColon))
                            {
                                tableOut.Rows[i][j] = PIn.Time(tableOut.Rows[i][j].ToString()).TotalHours.ToString("n");
                            }
                            else
                            {
                                tableOut.Rows[i][j] = PIn.Time(tableOut.Rows[i][j].ToString()).ToStringHmm();
                            } 
                        }
                        else //double
                        if (__dummyScrutVar0.equals("adjamt") || __dummyScrutVar0.equals("monthbalance") || __dummyScrutVar0.equals("claimfee") || __dummyScrutVar0.equals("inspayest") || __dummyScrutVar0.equals("inspayamt") || __dummyScrutVar0.equals("dedapplied") || __dummyScrutVar0.equals("amount") || __dummyScrutVar0.equals("payamt") || __dummyScrutVar0.equals("splitamt") || __dummyScrutVar0.equals("balance") || __dummyScrutVar0.equals("procfee") || __dummyScrutVar0.equals("overridepri") || __dummyScrutVar0.equals("overridesec") || __dummyScrutVar0.equals("priestim") || __dummyScrutVar0.equals("secestim") || __dummyScrutVar0.equals("procfees") || __dummyScrutVar0.equals("claimpays") || __dummyScrutVar0.equals("insest") || __dummyScrutVar0.equals("paysplits") || __dummyScrutVar0.equals("adjustments") || __dummyScrutVar0.equals("bal_0_30") || __dummyScrutVar0.equals("bal_31_60") || __dummyScrutVar0.equals("bal_61_90") || __dummyScrutVar0.equals("balover90") || __dummyScrutVar0.equals("baltotal"))
                        {
                            tableOut.Rows[i][j] = PIn.Double(tableOut.Rows[i][j].ToString()).ToString("F");
                            if (reportIn != null)
                            {
                                reportIn.getColAlign()[j] = HorizontalAlignment.Right;
                                colTotals[j] += PIn.Decimal(tableOut.Rows[i][j].ToString());
                            }
                             
                        }
                        else if (__dummyScrutVar0.equals("toothnum"))
                        {
                            tableOut.Rows[i][j] = Tooth.ToInternat(tableOut.Rows[i][j].ToString());
                        }
                        else //definitions:
                        if (__dummyScrutVar0.equals("adjtype"))
                        {
                            tableOut.Rows[i][j] = DefC.GetName(DefCat.AdjTypes, PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else if (__dummyScrutVar0.equals("confirmed"))
                        {
                            tableOut.Rows[i][j] = DefC.GetValue(DefCat.ApptConfirmed, PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else if (__dummyScrutVar0.equals("dx"))
                        {
                            tableOut.Rows[i][j] = DefC.GetName(DefCat.Diagnosis, PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else if (__dummyScrutVar0.equals("discounttype"))
                        {
                            tableOut.Rows[i][j] = DefC.GetName(DefCat.DiscountTypes, PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else if (__dummyScrutVar0.equals("doccategory"))
                        {
                            tableOut.Rows[i][j] = DefC.GetName(DefCat.ImageCats, PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else if (__dummyScrutVar0.equals("op"))
                        {
                            tableOut.Rows[i][j] = Operatories.GetAbbrev(PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else if (__dummyScrutVar0.equals("paytype"))
                        {
                            tableOut.Rows[i][j] = DefC.GetName(DefCat.PaymentTypes, PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else if (__dummyScrutVar0.equals("proccat"))
                        {
                            tableOut.Rows[i][j] = DefC.GetName(DefCat.ProcCodeCats, PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else if (__dummyScrutVar0.equals("unschedstatus") || __dummyScrutVar0.equals("recallstatus"))
                        {
                            tableOut.Rows[i][j] = DefC.GetName(DefCat.RecallUnschedStatus, PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else if (__dummyScrutVar0.equals("billingtype"))
                        {
                            tableOut.Rows[i][j] = DefC.GetName(DefCat.BillingTypes, PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else //patnums:
                        if (__dummyScrutVar0.equals("patnum") || __dummyScrutVar0.equals("guarantor") || __dummyScrutVar0.equals("pripatnum") || __dummyScrutVar0.equals("secpatnum") || __dummyScrutVar0.equals("subscriber") || __dummyScrutVar0.equals("withpat"))
                        {
                            if (patientNames.ContainsKey(PIn.Long(tableOut.Rows[i][j].ToString())))
                            {
                                //MessageBox.Show((string)Patients.HList[PIn.PInt(tableOut.Rows[i][j].ToString())]);
                                tableOut.Rows[i][j] = patientNames[PIn.Long(tableOut.Rows[i][j].ToString())];
                            }
                            else
                                tableOut.Rows[i][j] = ""; 
                        }
                        else //plannums:
                        if (__dummyScrutVar0.equals("plannum") || __dummyScrutVar0.equals("priplannum") || __dummyScrutVar0.equals("secplannum"))
                        {
                            if (hListPlans.ContainsKey(PIn.Long(tableOut.Rows[i][j].ToString())))
                                tableOut.Rows[i][j] = hListPlans[PIn.Long(tableOut.Rows[i][j].ToString())];
                            else
                                tableOut.Rows[i][j] = ""; 
                        }
                        else //referralnum
                        if (__dummyScrutVar0.equals("referralnum"))
                        {
                            if (PIn.Long(tableOut.Rows[i][j].ToString()) != 0)
                            {
                                Referral referral = Referrals.GetReferral(PIn.Long(tableOut.Rows[i][j].ToString()));
                                tableOut.Rows[i][j] = referral.LName + ", " + referral.FName + " " + referral.MName;
                            }
                            else
                                tableOut.Rows[i][j] = ""; 
                        }
                        else //enumerations:
                        if (__dummyScrutVar0.equals("aptstatus"))
                        {
                            tableOut.Rows[i][j] = ((ApptStatus)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("category"))
                        {
                            tableOut.Rows[i][j] = ((DefCat)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("renewmonth"))
                        {
                            tableOut.Rows[i][j] = ((Month)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("patstatus"))
                        {
                            tableOut.Rows[i][j] = ((PatientStatus)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("gender"))
                        {
                            tableOut.Rows[i][j] = ((PatientGender)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else //case "lab":
                        //	tableOut.Rows[i][j]
                        //		=((LabCaseOld)PIn.PInt(tableOut.Rows[i][j].ToString())).ToString();
                        //  break;
                        if (__dummyScrutVar0.equals("position"))
                        {
                            tableOut.Rows[i][j] = ((PatientPosition)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("deductwaivprev") || __dummyScrutVar0.equals("flocovered") || __dummyScrutVar0.equals("misstoothexcl") || __dummyScrutVar0.equals("procstatus"))
                        {
                            tableOut.Rows[i][j] = ((ProcStat)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("majorwait") || __dummyScrutVar0.equals("hascaries") || __dummyScrutVar0.equals("needssealants") || __dummyScrutVar0.equals("cariesexperience") || __dummyScrutVar0.equals("earlychildcaries") || __dummyScrutVar0.equals("existingsealants") || __dummyScrutVar0.equals("missingallteeth"))
                        {
                            tableOut.Rows[i][j] = ((YN)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("prirelationship") || __dummyScrutVar0.equals("secrelationship"))
                        {
                            tableOut.Rows[i][j] = ((Relat)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("treatarea"))
                        {
                            tableOut.Rows[i][j] = ((TreatmentArea)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("specialty"))
                        {
                            tableOut.Rows[i][j] = ((DentalSpecialty)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("placeservice"))
                        {
                            tableOut.Rows[i][j] = ((PlaceOfService)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("employrelated"))
                        {
                            tableOut.Rows[i][j] = ((YN)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("schedtype"))
                        {
                            tableOut.Rows[i][j] = ((ScheduleType)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("dayofweek"))
                        {
                            tableOut.Rows[i][j] = ((DayOfWeek)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("race"))
                        {
                            //TODO: Update this to return the patient's race(s) in a comma delimited list.  Very important to bring this to Jordan's attention when reviewing.
                            tableOut.Rows[i][j] = ((PatientRaceOld)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("gradelevel"))
                        {
                            tableOut.Rows[i][j] = ((PatientGrade)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else if (__dummyScrutVar0.equals("urgency"))
                        {
                            tableOut.Rows[i][j] = ((TreatmentUrgency)PIn.Long(tableOut.Rows[i][j].ToString())).ToString();
                        }
                        else //miscellaneous:
                        if (__dummyScrutVar0.equals("provnum") || __dummyScrutVar0.equals("provhyg") || __dummyScrutVar0.equals("priprov") || __dummyScrutVar0.equals("secprov") || __dummyScrutVar0.equals("provtreat") || __dummyScrutVar0.equals("provbill"))
                        {
                            tableOut.Rows[i][j] = Providers.GetAbbr(PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else if (__dummyScrutVar0.equals("covcatnum"))
                        {
                            tableOut.Rows[i][j] = CovCats.GetDesc(PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                        else if (__dummyScrutVar0.equals("referringprov"))
                        {
                        }
                        else //					  tableOut.Rows[i][j]=CovCats.GetDesc(PIn.PInt(tableOut.Rows[i][j].ToString()));
                        if (__dummyScrutVar0.equals("addtime"))
                        {
                            if (!StringSupport.equals(tableOut.Rows[i][j].ToString(), "0"))
                                tableOut.Rows[i][j] += "0";
                             
                        }
                        else if (__dummyScrutVar0.equals("feesched") || __dummyScrutVar0.equals("feeschednum"))
                        {
                            tableOut.Rows[i][j] = FeeScheds.GetDescription(PIn.Long(tableOut.Rows[i][j].ToString()));
                        }
                                                                   
                    }  
                }
                catch (Exception __dummyCatchVar0)
                {
                }
            
            }
        }
        //end switch column caption
        //end try
        //return tableOut;
        //end for i rows
        //end for j cols
        if (reportIn != null)
        {
            for (int k = 0;k < colTotals.Length;k++)
            {
                reportIn.ColTotal[k] = PIn.Decimal(colTotals[k].ToString("n"));
            }
        }
         
        return tableOut;
    }

    private void butFormulate_Click(Object sender, System.EventArgs e) throws Exception {
        //is now the 'Favorites' button
        FormQueryFavorites FormQF = new FormQueryFavorites();
        FormQF.UserQueryCur = UserQueryCur;
        FormQF.ShowDialog();
        if (FormQF.DialogResult == DialogResult.OK)
        {
            textQuery.Text = FormQF.UserQueryCur.QueryText;
            //grid2.CaptionText=UserQueries.Cur.Description;
            textTitle.Text = FormQF.UserQueryCur.Description;
            UserQueryCur = FormQF.UserQueryCur;
            report = new ReportSimpleGrid();
            report.Query = textQuery.Text;
            submitQuery();
        }
        else
        {
        } 
    }

    //this.butSaveChanges.Enabled=true;
    //butSaveChanges.Enabled=false;
    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormQueryEdit FormQE = new FormQueryEdit();
        FormQE.UserQueryCur = new UserQuery();
        FormQE.UserQueryCur.QueryText = textQuery.Text;
        FormQE.IsNew = true;
        FormQE.ShowDialog();
        if (FormQE.DialogResult == DialogResult.OK)
        {
            textQuery.Text = FormQE.UserQueryCur.QueryText;
            grid2.CaptionText = FormQE.UserQueryCur.Description;
        }
         
    }

    private void radioRaw_Click(Object sender, System.EventArgs e) throws Exception {
        submitQuery();
    }

    private void radioHuman_Click(Object sender, System.EventArgs e) throws Exception {
        submitQuery();
    }

    private void butPrint_Click(Object sender, System.EventArgs e) throws Exception {
        if (report.TableQ == null)
        {
            MessageBox.Show(Lan.g(this,"Please run query first"));
            return ;
        }
         
        printReport(false);
        if (IsReport)
        {
            DialogResult = DialogResult.Cancel;
        }
         
    }

    private void butPrintPreview_Click(Object sender, System.EventArgs e) throws Exception {
        if (report.TableQ == null)
        {
            MessageBox.Show(Lan.g(this,"Please run query first"));
            return ;
        }
         
        butFullPage.Visible = false;
        butZoomIn.Visible = true;
        printPreviewControl2.Visible = true;
        butPrintPreview.Visible = false;
        butQView.Visible = true;
        panelZoom.Visible = true;
        totalPages = 0;
        printPreviewControl2.Zoom = ((double)printPreviewControl2.ClientSize.Height / (double)pd2.DefaultPageSettings.PaperSize.Height);
        printReport(true);
        labelTotPages.Text = "/ " + totalPages.ToString();
    }

    private void butQView_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.UserQuery))
        {
            return ;
        }
         
        printPreviewControl2.Visible = false;
        panelZoom.Visible = false;
        butPrintPreview.Visible = true;
        butQView.Visible = false;
    }

    /**
    * 
    */
    public void printReport(boolean justPreview) throws Exception {
        pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        pd2.DefaultPageSettings.Margins = new Margins(25, 50, 50, 60);
        if (report.IsLandscape)
        {
            pd2.DefaultPageSettings.Landscape = true;
            pd2.DefaultPageSettings.Margins = new Margins(25, 120, 50, 60);
        }
         
        if (pd2.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd2.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        pagesPrinted = 0;
        linesPrinted = 0;
        try
        {
            if (justPreview)
            {
                printPreviewControl2.Document = pd2;
            }
            else if (PrinterL.SetPrinter(pd2, PrintSituation.Default, 0, "Query printed"))
            {
                pd2.Print();
            }
              
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    /**
    * raised for each page to be printed.
    */
    private void pd2_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        Rectangle bounds = ev.MarginBounds;
        float yPos = bounds.Top;
        if (!headerPrinted)
        {
            ev.Graphics.DrawString(report.Title, titleFont, Brushes.Black, bounds.Width / 2 - ev.Graphics.MeasureString(report.Title, titleFont).Width / 2, yPos);
            yPos += titleFont.GetHeight(ev.Graphics);
            for (int i = 0;i < report.SubTitle.Count;i++)
            {
                ev.Graphics.DrawString(report.SubTitle[i], subtitleFont, Brushes.Black, bounds.Width / 2 - ev.Graphics.MeasureString(report.SubTitle[i], subtitleFont).Width / 2, yPos);
                yPos += subtitleFont.GetHeight(ev.Graphics) + 2;
            }
            headerPrinted = true;
        }
         
        yPos += 10;
        ev.Graphics.DrawString(Lan.g(this,"Date:") + " " + DateTime.Today.ToString("d"), bodyFont, Brushes.Black, bounds.Left, yPos);
        //if(totalPages==0){
        ev.Graphics.DrawString(Lan.g(this,"Page:") + " " + (pagesPrinted + 1).ToString(), bodyFont, Brushes.Black, bounds.Right - ev.Graphics.MeasureString(Lan.g(this,"Page:") + " " + (pagesPrinted + 1).ToString(), bodyFont).Width, yPos);
        /*}
        			else{//maybe work on this later.  Need totalPages on first pass
        				ev.Graphics.DrawString("Page: "+(pagesPrinted+1).ToString()+" / "+totalPages.ToString()
        					,bodyFont,Brushes.Black
        					,bounds.Right
        					-ev.Graphics.MeasureString("Page: "+(pagesPrinted+1).ToString()+" / "
        					+totalPages.ToString(),bodyFont).Width
        					,yPos);
        			}*/
        yPos += bodyFont.GetHeight(ev.Graphics) + 10;
        ev.Graphics.DrawLine(new Pen(Color.Black), bounds.Left, yPos - 5, bounds.Right, yPos - 5);
        for (int i = 0;i < report.getColCaption().Length;i++)
        {
            //column captions:
            if (report.getColAlign()[i] == HorizontalAlignment.Right)
            {
                ev.Graphics.DrawString(report.getColCaption()[i], colCaptFont, Brushes.Black, new RectangleF(bounds.Left + report.getColPos()[i + 1] - ev.Graphics.MeasureString(report.getColCaption()[i], colCaptFont).Width, yPos, report.getColWidth()[i], colCaptFont.GetHeight(ev.Graphics)));
            }
            else
            {
                ev.Graphics.DrawString(report.getColCaption()[i], colCaptFont, Brushes.Black, bounds.Left + report.getColPos()[i], yPos);
            } 
        }
        yPos += bodyFont.GetHeight(ev.Graphics) + 5;
        while (yPos < bounds.Top + bounds.Height - 18 && linesPrinted < report.TableQ.Rows.Count)
        {
            for (int iCol = 0;iCol < report.TableQ.Columns.Count;iCol++)
            {
                //table:
                //The 18 is allowance for the line about to print.
                if (report.getColAlign()[iCol] == HorizontalAlignment.Right)
                {
                    ev.Graphics.DrawString(grid2[linesPrinted, iCol].ToString(), bodyFont, Brushes.Black, new RectangleF(bounds.Left + report.getColPos()[iCol + 1] - ev.Graphics.MeasureString(grid2[linesPrinted, iCol].ToString(), bodyFont).Width - 1, yPos, report.getColWidth()[iCol], bodyFont.GetHeight(ev.Graphics)));
                }
                else
                {
                    ev.Graphics.DrawString(grid2[linesPrinted, iCol].ToString(), bodyFont, Brushes.Black, new RectangleF(bounds.Left + report.getColPos()[iCol], yPos, report.getColPos()[iCol + 1] - report.getColPos()[iCol] + 6, bodyFont.GetHeight(ev.Graphics)));
                } 
            }
            yPos += bodyFont.GetHeight(ev.Graphics);
            linesPrinted++;
            if (linesPrinted == report.TableQ.Rows.Count)
            {
                tablePrinted = true;
            }
             
        }
        if (report.TableQ.Rows.Count == 0)
        {
            tablePrinted = true;
        }
         
        //totals:
        if (tablePrinted)
        {
            if (yPos < bounds.Bottom)
            {
                ev.Graphics.DrawLine(new Pen(Color.Black), bounds.Left, yPos + 3, bounds.Right, yPos + 3);
                yPos += 4;
                for (int iCol = 0;iCol < report.TableQ.Columns.Count;iCol++)
                {
                    if (report.getColAlign()[iCol] == HorizontalAlignment.Right)
                    {
                        if (report.TableQ.Columns[iCol].Caption.ToLower().StartsWith("count("))
                        {
                            continue;
                        }
                         
                        //"=="count(*)") {
                        float textWidth = (float)(ev.Graphics.MeasureString(report.ColTotal[iCol].ToString("n"), subtitleFont).Width);
                        //the 3 is arbitrary
                        ev.Graphics.DrawString(report.ColTotal[iCol].ToString("n"), subtitleFont, Brushes.Black, new RectangleF(bounds.Left + report.getColPos()[iCol + 1] - textWidth + 3, yPos, textWidth, subtitleFont.GetHeight(ev.Graphics)));
                    }
                     
                }
                //else{
                //	ev.Graphics.DrawString(grid2[linesPrinted,iCol].ToString()
                //		,bodyFont,Brushes.Black,new RectangleF(
                //		bounds.Left+report.ColPos[iCol],yPos
                //		,report.ColPos[iCol+1]-report.ColPos[iCol]
                //,bodyFont.GetHeight(ev.Graphics)));
                //}
                totalsPrinted = true;
                yPos += subtitleFont.GetHeight(ev.Graphics);
            }
             
        }
         
        //Summary
        if (totalsPrinted)
        {
            if (yPos + report.Summary.Count * subtitleFont.GetHeight(ev.Graphics) < bounds.Top + bounds.Height)
            {
                ev.Graphics.DrawLine(new Pen(Color.Black), bounds.Left, yPos + 2, bounds.Right, yPos + 2);
                yPos += bodyFont.GetHeight(ev.Graphics);
                for (int i = 0;i < report.Summary.Count;i++)
                {
                    if (report.SummaryFont != null && !StringSupport.equals(report.SummaryFont, ""))
                    {
                        Font acctnumFont = new Font(report.SummaryFont, 12);
                        ev.Graphics.DrawString(report.Summary[i], acctnumFont, Brushes.Black, bounds.Left, yPos);
                        yPos += acctnumFont.GetHeight(ev.Graphics);
                    }
                    else
                    {
                        ev.Graphics.DrawString(report.Summary[i], subtitleFont, Brushes.Black, bounds.Left, yPos);
                        yPos += subtitleFont.GetHeight(ev.Graphics);
                    } 
                }
                summaryPrinted = true;
            }
             
        }
         
        if (!summaryPrinted)
        {
            //linesPrinted < report.TableQ.Rows.Count){
            ev.HasMorePages = true;
            pagesPrinted++;
        }
        else
        {
            ev.HasMorePages = false;
            //UpDownPage.Maximum=pagesPrinted+1;
            totalPages = pagesPrinted + 1;
            labelTotPages.Text = "1 / " + totalPages.ToString();
            pagesPrinted = 0;
            linesPrinted = 0;
            headerPrinted = false;
            tablePrinted = false;
            totalsPrinted = false;
            summaryPrinted = false;
        } 
    }

    private void butZoomIn_Click(Object sender, System.EventArgs e) throws Exception {
        butFullPage.Visible = true;
        butZoomIn.Visible = false;
        printPreviewControl2.Zoom = 1;
    }

    private void butFullPage_Click(Object sender, System.EventArgs e) throws Exception {
        butFullPage.Visible = false;
        butZoomIn.Visible = true;
        printPreviewControl2.Zoom = ((double)printPreviewControl2.ClientSize.Height / (double)pd2.DefaultPageSettings.PaperSize.Height);
    }

    private void butBack_Click(Object sender, System.EventArgs e) throws Exception {
        if (printPreviewControl2.StartPage == 0)
            return ;
         
        printPreviewControl2.StartPage--;
        labelTotPages.Text = (printPreviewControl2.StartPage + 1).ToString() + " / " + totalPages.ToString();
    }

    private void butFwd_Click(Object sender, System.EventArgs e) throws Exception {
        if (printPreviewControl2.StartPage == totalPages - 1)
            return ;
         
        printPreviewControl2.StartPage++;
        labelTotPages.Text = (printPreviewControl2.StartPage + 1).ToString() + " / " + totalPages.ToString();
    }

    private void butExportExcel_Click(Object sender, System.EventArgs e) throws Exception {
    }

    /*
    			saveFileDialog2=new SaveFileDialog();
          saveFileDialog2.AddExtension=true;
    			saveFileDialog2.Title=Lan.g(this,"Select Folder to Save File To");
    		  if(IsReport){
    				saveFileDialog2.FileName=report.Title;
    			}
          else{
            saveFileDialog2.FileName=UserQueries.Cur.FileName;
    			}
    			if(!Directory.Exists( ((Pref)PrefC.HList["ExportPath"]).ValueString )){
    				try{
    					Directory.CreateDirectory( ((Pref)PrefC.HList["ExportPath"]).ValueString );
    					saveFileDialog2.InitialDirectory=((Pref)PrefC.HList["ExportPath"]).ValueString;
    				}
    				catch{
    					//initialDirectory will be blank
    				}
    			}
    			else saveFileDialog2.InitialDirectory=((Pref)PrefC.HList["ExportPath"]).ValueString;
    			//saveFileDialog2.DefaultExt="xls";
    			//saveFileDialog2.Filter="txt files(*.txt)|*.txt|All files(*.*)|*.*";
          //saveFileDialog2.FilterIndex=1;
    		  if(saveFileDialog2.ShowDialog()!=DialogResult.OK){
    	   	  return;
    			}
    			Excel.Application excel=new Excel.ApplicationClass();
    			excel.Workbooks.Add(Missing.Value);
    			Worksheet worksheet = (Worksheet) excel.ActiveSheet;
    			Range range=(Excel.Range)excel.Cells[1,1];
    			range.Value2="test";
    			range.Font.Bold=true;
    			range=(Excel.Range)excel.Cells[1,2];
    			range.ColumnWidth=30;
    			range.FormulaR1C1="12345";
    			excel.Save(saveFileDialog2.FileName);
    	//this test case worked, so now it is just a matter of finishing this off, and Excel export will be done.
    			MessageBox.Show(Lan.g(this,"File created successfully"));
    			*/
    private void butExport_Click(Object sender, System.EventArgs e) throws Exception {
        if (report == null || report.TableQ == null)
        {
            MessageBox.Show(Lan.g(this,"Please run query first"));
            return ;
        }
         
        saveFileDialog2 = new SaveFileDialog();
        saveFileDialog2.AddExtension = true;
        //saveFileDialog2.Title=Lan.g(this,"Select Folder to Save File To");
        if (IsReport)
        {
            saveFileDialog2.FileName = report.Title;
        }
        else
        {
            if (UserQueryCur == null || UserQueryCur.FileName == null || StringSupport.equals(UserQueryCur.FileName, ""))
                //.FileName==null)
                saveFileDialog2.FileName = report.Title;
            else
                saveFileDialog2.FileName = UserQueryCur.FileName; 
        } 
        if (!Directory.Exists(PrefC.getString(PrefName.ExportPath)))
        {
            try
            {
                Directory.CreateDirectory(PrefC.getString(PrefName.ExportPath));
                saveFileDialog2.InitialDirectory = PrefC.getString(PrefName.ExportPath);
            }
            catch (Exception __dummyCatchVar2)
            {
            }
        
        }
        else
            //initialDirectory will be blank
            saveFileDialog2.InitialDirectory = PrefC.getString(PrefName.ExportPath); 
        //saveFileDialog2.DefaultExt="txt";
        saveFileDialog2.Filter = "Text files(*.txt)|*.txt|Excel Files(*.xls)|*.xls|All files(*.*)|*.*";
        saveFileDialog2.FilterIndex = 0;
        if (saveFileDialog2.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        try
        {
            StreamWriter sw = new StreamWriter(saveFileDialog2.FileName, false);
            try
            {
                {
                    //new FileStream(,FileMode.Create,FileAccess.Write,FileShare.Read)))
                    String line = "";
                    for (int i = 0;i < report.getColCaption().Length;i++)
                    {
                        line += report.getColCaption()[i];
                        if (i < report.TableQ.Columns.Count - 1)
                        {
                            line += "\t";
                        }
                         
                    }
                    sw.WriteLine(line);
                    String cell = new String();
                    for (int i = 0;i < report.TableQ.Rows.Count;i++)
                    {
                        line = "";
                        for (int j = 0;j < report.TableQ.Columns.Count;j++)
                        {
                            cell = report.TableQ.Rows[i][j].ToString();
                            cell = cell.Replace("\r", "");
                            cell = cell.Replace("\n", "");
                            cell = cell.Replace("\t", "");
                            cell = cell.Replace("\"", "");
                            line += cell;
                            if (j < report.TableQ.Columns.Count - 1)
                            {
                                line += "\t";
                            }
                             
                        }
                        sw.WriteLine(line);
                    }
                }
            }
            finally
            {
                if (sw != null)
                    Disposable.mkDisposable(sw).dispose();
                 
            }
        }
        catch (Exception __dummyCatchVar3)
        {
            MessageBox.Show(Lan.g(this,"File in use by another program.  Close and try again."));
            return ;
        }

        MessageBox.Show(Lan.g(this,"File created successfully"));
    }

    private void butCopy_Click(Object sender, System.EventArgs e) throws Exception {
        Clipboard.SetDataObject(textQuery.Text);
    }

    private void butPaste_Click(Object sender, System.EventArgs e) throws Exception {
        IDataObject iData = Clipboard.GetDataObject();
        if (iData.GetDataPresent(DataFormats.Text))
        {
            textQuery.Text = (String)iData.GetData(DataFormats.Text);
        }
        else
        {
            MessageBox.Show(Lan.g(this,"Could not retrieve data off the clipboard."));
        } 
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formQuery_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
    }

}


//SecurityLogs.MakeLogEntry("User Query","");