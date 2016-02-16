//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:40 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormApptEdit;
import OpenDental.FormASAP;
import OpenDental.Lan;
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
* 
*/
public class FormASAP  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butClose;
    /**
    * 
    */
    public boolean PinClicked = false;
    /**
    * 
    */
    public static String procsForCur = new String();
    private OpenDental.UI.ODGrid grid;
    private OpenDental.UI.Button butPrint;
    private List<Appointment> ListASAP = new List<Appointment>();
    private PrintDocument pd = new PrintDocument();
    private boolean headingPrinted = new boolean();
    private int headingPrintH = new int();
    private int pagesPrinted = new int();
    /**
    * Only used if PinClicked=true
    */
    public long AptSelected = new long();
    private ComboBox comboProv = new ComboBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butRefresh;
    private ComboBox comboSite = new ComboBox();
    private Label labelSite = new Label();
    /**
    * When this form closes, this will be the patNum of the last patient viewed.  The calling form should then make use of this to refresh to that patient.  If 0, then calling form should not refresh.
    */
    public long SelectedPatNum = new long();
    private ComboBox comboClinic = new ComboBox();
    private Label labelClinic = new Label();
    private Dictionary<long, String> patientNames = new Dictionary<long, String>();
    /**
    * 
    */
    public FormASAP() throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormASAP.class);
        this.butClose = new OpenDental.UI.Button();
        this.grid = new OpenDental.UI.ODGrid();
        this.butPrint = new OpenDental.UI.Button();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butRefresh = new OpenDental.UI.Button();
        this.comboSite = new System.Windows.Forms.ComboBox();
        this.labelSite = new System.Windows.Forms.Label();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
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
        this.butClose.Location = new System.Drawing.Point(761, 631);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(87, 24);
        this.butClose.TabIndex = 7;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // grid
        //
        this.grid.setHScrollVisible(false);
        this.grid.Location = new System.Drawing.Point(10, 57);
        this.grid.Name = "grid";
        this.grid.setScrollValue(0);
        this.grid.Size = new System.Drawing.Size(734, 598);
        this.grid.TabIndex = 8;
        this.grid.setTitle("ASAP List");
        this.grid.setTranslationName("TableASAP");
        this.grid.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.grid.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.grid_CellDoubleClick(sender, e);
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
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(761, 583);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(87, 24);
        this.butPrint.TabIndex = 21;
        this.butPrint.Text = "Print List";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // comboProv
        //
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.Location = new System.Drawing.Point(319, 7);
        this.comboProv.MaxDropDownItems = 40;
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(181, 21);
        this.comboProv.TabIndex = 33;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(248, 11);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(69, 14);
        this.label4.TabIndex = 32;
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
        this.butRefresh.Location = new System.Drawing.Point(762, 6);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(86, 24);
        this.butRefresh.TabIndex = 31;
        this.butRefresh.Text = "&Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // comboSite
        //
        this.comboSite.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSite.Location = new System.Drawing.Point(584, 32);
        this.comboSite.MaxDropDownItems = 40;
        this.comboSite.Name = "comboSite";
        this.comboSite.Size = new System.Drawing.Size(160, 21);
        this.comboSite.TabIndex = 37;
        //
        // labelSite
        //
        this.labelSite.Location = new System.Drawing.Point(512, 36);
        this.labelSite.Name = "labelSite";
        this.labelSite.Size = new System.Drawing.Size(71, 14);
        this.labelSite.TabIndex = 36;
        this.labelSite.Text = "Site";
        this.labelSite.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(584, 7);
        this.comboClinic.MaxDropDownItems = 40;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(160, 21);
        this.comboClinic.TabIndex = 39;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(506, 11);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(77, 14);
        this.labelClinic.TabIndex = 38;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormASAP
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(858, 672);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.comboSite);
        this.Controls.Add(this.labelSite);
        this.Controls.Add(this.comboProv);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.grid);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormASAP";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "ASAP List";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormASAP_FormClosing);
        this.Load += new System.EventHandler(this.FormASAP_Load);
        this.ResumeLayout(false);
    }

    private void formASAP_Load(Object sender, System.EventArgs e) throws Exception {
        patientNames = Patients.getAllPatientNames();
        /*comboOrder.Items.Add(Lan.g(this,"Status"));
        			comboOrder.Items.Add(Lan.g(this,"Alphabetical"));
        			comboOrder.Items.Add(Lan.g(this,"Date"));
        			comboOrder.SelectedIndex=0;*/
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
        this.Cursor = Cursors.WaitCursor;
        /*string order="";
        			switch(comboOrder.SelectedIndex) {
        				case 0:
        					order="status";
        					break;
        				case 1:
        					order="alph";
        					break;
        				case 2:
        					order="date";
        					break;
        			}*/
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
         
        ListASAP = Appointments.refreshASAP(provNum,siteNum,clinicNum);
        int scrollVal = grid.getScrollValue();
        grid.beginUpdate();
        grid.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableASAP","Patient"),140);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableASAP","Date"),65);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableASAP","Status"),110);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableASAP","Prov"),50);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableASAP","Procedures"),150);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableASAP","Notes"),200);
        grid.getColumns().add(col);
        grid.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListASAP.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(patientNames[ListASAP[i].PatNum]);
            if (ListASAP[i].AptDateTime.Year < 1880)
            {
                //shouldn't be possible.
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListASAP[i].AptDateTime.ToShortDateString());
            } 
            row.getCells().Add(DefC.GetName(DefCat.RecallUnschedStatus, ListASAP[i].UnschedStatus));
            if (ListASAP[i].IsHygiene)
            {
                row.getCells().Add(Providers.GetAbbr(ListASAP[i].ProvHyg));
            }
            else
            {
                row.getCells().Add(Providers.GetAbbr(ListASAP[i].ProvNum));
            } 
            row.getCells().Add(ListASAP[i].ProcDescript);
            row.getCells().Add(ListASAP[i].Note);
            grid.getRows().add(row);
        }
        grid.endUpdate();
        grid.setScrollValue(scrollVal);
        Cursor = Cursors.Default;
    }

    private void grid_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        int currentSelection = e.getRow();
        int currentScroll = grid.getScrollValue();
        SelectedPatNum = ListASAP[e.getRow()].PatNum;
        FormApptEdit FormAE = new FormApptEdit(ListASAP[e.getRow()].AptNum);
        FormAE.PinIsVisible = true;
        FormAE.ShowDialog();
        if (FormAE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (FormAE.PinClicked)
        {
            PinClicked = true;
            AptSelected = ListASAP[e.getRow()].AptNum;
            DialogResult = DialogResult.OK;
        }
        else
        {
            fillGrid();
            grid.setSelected(currentSelection,true);
            grid.setScrollValue(currentScroll);
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
        if (!PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "ASAP list printed"))
        {
            return ;
        }
         
        try
        {
            pd.Print();
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = e.MarginBounds;
        //new Rectangle(50,40,800,1035);//Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int y = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        int headingPrintH = 0;
        if (!headingPrinted)
        {
            text = Lan.g(this,"ASAP List");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, y);
            //yPos+=(int)g.MeasureString(text,headingFont).Height;
            //text=textDateFrom.Text+" "+Lan.g(this,"to")+" "+textDateTo.Text;
            //g.DrawString(text,subHeadingFont,Brushes.Black,center-g.MeasureString(text,subHeadingFont).Width/2,yPos);
            y += 25;
            headingPrinted = true;
            headingPrintH = y;
        }
         
        y = grid.printPage(g,pagesPrinted,bounds,headingPrintH);
        pagesPrinted++;
        if (y == -1)
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

    //private void FormASAP_Closing(object sender, System.ComponentModel.CancelEventArgs e) {
    //}
    private void formASAP_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        //Patients.HList=null;
        if (grid.getSelectedIndices().Length == 1)
        {
            SelectedPatNum = ListASAP[grid.getSelectedIndices()[0]].PatNum;
        }
         
    }

}


