//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:55 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormApptEdit;
import OpenDental.FormTrackNext;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.SiteC;

/**
* The Next appoinment tracking tool.
*/
public class FormTrackNext  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * Passes the pinclicked result down from the appointment to the parent form.
    */
    public boolean PinClicked = new boolean();
    private List<Appointment> AptList = new List<Appointment>();
    /**
    * When this form closes, this will be the patNum of the last patient viewed.  The calling form should then make use of this to refresh to that patient.  If 0, then calling form should not refresh.
    */
    public long SelectedPatNum = new long();
    private OpenDental.UI.ODGrid gridMain;
    private ComboBox comboProv = new ComboBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butRefresh;
    private ComboBox comboOrder = new ComboBox();
    private Label label1 = new Label();
    private OpenDental.UI.Button butPrint;
    /**
    * Only used if PinClicked=true
    */
    public long AptSelected = new long();
    private int pagesPrinted = new int();
    private boolean headingPrinted = new boolean();
    private PrintDocument pd = new PrintDocument();
    private ComboBox comboSite = new ComboBox();
    private Label labelSite = new Label();
    private int headingPrintH = new int();
    private ComboBox comboClinic = new ComboBox();
    private Label labelClinic = new Label();
    private Dictionary<long, String> patientNames = new Dictionary<long, String>();
    /**
    * 
    */
    public FormTrackNext() throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTrackNext.class);
        this.butClose = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butRefresh = new OpenDental.UI.Button();
        this.comboOrder = new System.Windows.Forms.ComboBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butPrint = new OpenDental.UI.Button();
        this.comboSite = new System.Windows.Forms.ComboBox();
        this.labelSite = new System.Windows.Forms.Label();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
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
        this.butClose.Location = new System.Drawing.Point(755, 600);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(87, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 53);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(734, 571);
        this.gridMain.TabIndex = 2;
        this.gridMain.setTitle("Planned Appointments");
        this.gridMain.setTranslationName("FormTrackNext");
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
        // comboProv
        //
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.Location = new System.Drawing.Point(336, 5);
        this.comboProv.MaxDropDownItems = 40;
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(181, 21);
        this.comboProv.TabIndex = 26;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(244, 9);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(91, 14);
        this.label4.TabIndex = 25;
        this.label4.Text = "Provider";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(755, 5);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(87, 24);
        this.butRefresh.TabIndex = 24;
        this.butRefresh.Text = "&Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // comboOrder
        //
        this.comboOrder.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboOrder.Location = new System.Drawing.Point(104, 5);
        this.comboOrder.MaxDropDownItems = 40;
        this.comboOrder.Name = "comboOrder";
        this.comboOrder.Size = new System.Drawing.Size(133, 21);
        this.comboOrder.TabIndex = 30;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(11, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(91, 14);
        this.label1.TabIndex = 29;
        this.label1.Text = "Order by";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(755, 552);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(87, 24);
        this.butPrint.TabIndex = 31;
        this.butPrint.Text = "Print List";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // comboSite
        //
        this.comboSite.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSite.Location = new System.Drawing.Point(613, 29);
        this.comboSite.MaxDropDownItems = 40;
        this.comboSite.Name = "comboSite";
        this.comboSite.Size = new System.Drawing.Size(133, 21);
        this.comboSite.TabIndex = 33;
        //
        // labelSite
        //
        this.labelSite.Location = new System.Drawing.Point(520, 31);
        this.labelSite.Name = "labelSite";
        this.labelSite.Size = new System.Drawing.Size(91, 14);
        this.labelSite.TabIndex = 32;
        this.labelSite.Text = "Site";
        this.labelSite.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(613, 5);
        this.comboClinic.MaxDropDownItems = 40;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(133, 21);
        this.comboClinic.TabIndex = 35;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(520, 9);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(91, 14);
        this.labelClinic.TabIndex = 34;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormTrackNext
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(852, 640);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.comboSite);
        this.Controls.Add(this.labelSite);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.comboOrder);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.comboProv);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTrackNext";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Track Planned Appointments";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormTrackNext_FormClosing);
        this.Load += new System.EventHandler(this.FormTrackNext_Load);
        this.ResumeLayout(false);
    }

    private void formTrackNext_Load(Object sender, System.EventArgs e) throws Exception {
        patientNames = Patients.getAllPatientNames();
        comboOrder.Items.Add(Lan.g(this,"Status"));
        comboOrder.Items.Add(Lan.g(this,"Alphabetical"));
        comboOrder.Items.Add(Lan.g(this,"Date"));
        comboOrder.SelectedIndex = 0;
        comboProv.Items.Add(Lan.g(this,"All"));
        comboProv.SelectedIndex = 0;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
        }
        if (PrefC.getBool(PrefName.EasyHidePublicHealth))
        {
            comboSite.Visible = false;
            labelSite.Visible = false;
        }
        else
        {
            comboSite.Items.Add(Lan.g(this,"All"));
            comboSite.SelectedIndex = 0;
            for (int i = 0;i < SiteC.getList().Length;i++)
            {
                comboSite.Items.Add(SiteC.getList()[i].Description);
            }
        } 
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            comboClinic.Visible = false;
            labelClinic.Visible = false;
        }
        else
        {
            comboClinic.Items.Add(Lan.g(this,"All"));
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
            }
        } 
        fillGrid();
    }

    private void fillGrid() throws Exception {
        Cursor = Cursors.WaitCursor;
        String order = "";
        SelectedIndex __dummyScrutVar0 = comboOrder.SelectedIndex;
        if (__dummyScrutVar0.equals(0))
        {
            order = "status";
        }
        else if (__dummyScrutVar0.equals(1))
        {
            order = "alph";
        }
        else if (__dummyScrutVar0.equals(2))
        {
            order = "date";
        }
           
        long provNum = 0;
        if (comboProv.SelectedIndex != 0)
        {
            provNum = ProviderC.getListShort()[comboProv.SelectedIndex - 1].ProvNum;
        }
         
        long siteNum = 0;
        if (!PrefC.getBool(PrefName.EasyHidePublicHealth) && comboSite.SelectedIndex != 0)
        {
            siteNum = SiteC.getList()[comboSite.SelectedIndex - 1].SiteNum;
        }
         
        long clinicNum = 0;
        if (comboClinic.SelectedIndex > 0)
        {
            clinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        }
         
        AptList = Appointments.refreshPlannedTracker(order,provNum,siteNum,clinicNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Patient"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Date"),65);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Status"),110);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Prov"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Procedures"),150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Notes"),200);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < AptList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(patientNames[AptList[i].PatNum]);
            if (AptList[i].AptDateTime.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(AptList[i].AptDateTime.ToShortDateString());
            } 
            row.getCells().Add(DefC.GetName(DefCat.RecallUnschedStatus, AptList[i].UnschedStatus));
            if (AptList[i].IsHygiene)
            {
                row.getCells().Add(Providers.GetAbbr(AptList[i].ProvHyg));
            }
            else
            {
                row.getCells().Add(Providers.GetAbbr(AptList[i].ProvNum));
            } 
            row.getCells().Add(AptList[i].ProcDescript);
            row.getCells().Add(AptList[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        Cursor = Cursors.Default;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SelectedPatNum = AptList[e.getRow()].PatNum;
        int currentSelection = gridMain.getSelectedIndex();
        int currentScroll = gridMain.getScrollValue();
        FormApptEdit FormAE = new FormApptEdit(AptList[e.getRow()].AptNum);
        FormAE.PinIsVisible = true;
        FormAE.ShowDialog();
        if (FormAE.DialogResult != DialogResult.OK)
            return ;
         
        if (FormAE.PinClicked)
        {
            PinClicked = true;
            AptSelected = AptList[e.getRow()].AptNum;
            DialogResult = DialogResult.OK;
            return ;
        }
        else
        {
            fillGrid();
            gridMain.setSelected(currentSelection,true);
            gridMain.setScrollValue(currentScroll);
        } 
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        pagesPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        //pd.OriginAtMargins=true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        if (!PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Planned appointment tracker list printed"))
        {
            return ;
        }
         
        try
        {
            pd.Print();
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Printer not available");
        }
    
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
            text = Lan.g(this,"Planned Appointment Tracker");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            //yPos+=(int)g.MeasureString(text,headingFont).Height;
            //text=textDateFrom.Text+" "+Lan.g(this,"to")+" "+textDateTo.Text;
            //g.DrawString(text,subHeadingFont,Brushes.Black,center-g.MeasureString(text,subHeadingFont).Width/2,yPos);
            yPos += 25;
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

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formTrackNext_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        //Patients.HList=null;
        if (gridMain.getSelectedIndex() != -1)
        {
            SelectedPatNum = AptList[gridMain.getSelectedIndex()].PatNum;
        }
         
    }

}


