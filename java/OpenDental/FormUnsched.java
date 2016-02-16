//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:57 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormApptEdit;
import OpenDental.FormUnsched;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
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
public class FormUnsched  extends System.Windows.Forms.Form 
{
    private IContainer components = new IContainer();
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
    private Appointment[] ListUn = new Appointment[]();
    private PrintDocument pd = new PrintDocument();
    private boolean headingPrinted = new boolean();
    private int headingPrintH = new int();
    private int pagesPrinted = new int();
    /**
    * Only used if PinClicked=true
    */
    public long AptSelected = new long();
    private ComboBox comboOrder = new ComboBox();
    private Label label1 = new Label();
    private ComboBox comboProv = new ComboBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butRefresh;
    private ComboBox comboSite = new ComboBox();
    private Label labelSite = new Label();
    /**
    * When this form closes, this will be the patNum of the last patient viewed.  The calling form should then make use of this to refresh to that patient.  If 0, then calling form should not refresh.
    */
    public long SelectedPatNum = new long();
    private CheckBox checkBrokenAppts = new CheckBox();
    private ContextMenuStrip menuDelete = new ContextMenuStrip();
    private ComboBox comboClinic = new ComboBox();
    private Label labelClinic = new Label();
    private Dictionary<long, String> patientNames = new Dictionary<long, String>();
    /**
    * 
    */
    public FormUnsched() throws Exception {
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormUnsched.class);
        this.butClose = new OpenDental.UI.Button();
        this.grid = new OpenDental.UI.ODGrid();
        this.butPrint = new OpenDental.UI.Button();
        this.comboOrder = new System.Windows.Forms.ComboBox();
        this.label1 = new System.Windows.Forms.Label();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butRefresh = new OpenDental.UI.Button();
        this.comboSite = new System.Windows.Forms.ComboBox();
        this.labelSite = new System.Windows.Forms.Label();
        this.checkBrokenAppts = new System.Windows.Forms.CheckBox();
        this.menuDelete = new System.Windows.Forms.ContextMenuStrip(this.components);
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
        this.grid.Location = new System.Drawing.Point(10, 56);
        this.grid.Name = "grid";
        this.grid.setScrollValue(0);
        this.grid.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.grid.Size = new System.Drawing.Size(734, 599);
        this.grid.TabIndex = 8;
        this.grid.setTitle("Unscheduled List");
        this.grid.setTranslationName("TableUnsched");
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
        this.grid.MouseDown += new System.Windows.Forms.MouseEventHandler(this.grid_MouseDown);
        this.grid.MouseUp += new System.Windows.Forms.MouseEventHandler(this.grid_MouseUp);
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
        // comboOrder
        //
        this.comboOrder.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboOrder.Location = new System.Drawing.Point(97, 6);
        this.comboOrder.MaxDropDownItems = 40;
        this.comboOrder.Name = "comboOrder";
        this.comboOrder.Size = new System.Drawing.Size(133, 21);
        this.comboOrder.TabIndex = 35;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(23, 10);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(72, 14);
        this.label1.TabIndex = 34;
        this.label1.Text = "Order by";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboProv
        //
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.Location = new System.Drawing.Point(319, 6);
        this.comboProv.MaxDropDownItems = 40;
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(181, 21);
        this.comboProv.TabIndex = 33;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(248, 10);
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
        this.comboSite.Location = new System.Drawing.Point(584, 31);
        this.comboSite.MaxDropDownItems = 40;
        this.comboSite.Name = "comboSite";
        this.comboSite.Size = new System.Drawing.Size(160, 21);
        this.comboSite.TabIndex = 37;
        //
        // labelSite
        //
        this.labelSite.Location = new System.Drawing.Point(506, 35);
        this.labelSite.Name = "labelSite";
        this.labelSite.Size = new System.Drawing.Size(77, 14);
        this.labelSite.TabIndex = 36;
        this.labelSite.Text = "Site";
        this.labelSite.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkBrokenAppts
        //
        this.checkBrokenAppts.AutoSize = true;
        this.checkBrokenAppts.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkBrokenAppts.Location = new System.Drawing.Point(65, 33);
        this.checkBrokenAppts.Name = "checkBrokenAppts";
        this.checkBrokenAppts.Size = new System.Drawing.Size(165, 17);
        this.checkBrokenAppts.TabIndex = 38;
        this.checkBrokenAppts.Text = "Include Broken Appointments";
        this.checkBrokenAppts.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkBrokenAppts.UseVisualStyleBackColor = true;
        //
        // menuDelete
        //
        this.menuDelete.Name = "menuDelete";
        this.menuDelete.Size = new System.Drawing.Size(61, 4);
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(584, 6);
        this.comboClinic.MaxDropDownItems = 40;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(160, 21);
        this.comboClinic.TabIndex = 40;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(506, 10);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(77, 14);
        this.labelClinic.TabIndex = 39;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormUnsched
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(858, 672);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.checkBrokenAppts);
        this.Controls.Add(this.comboOrder);
        this.Controls.Add(this.comboSite);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.labelSite);
        this.Controls.Add(this.comboProv);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.grid);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormUnsched";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Unscheduled List";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormUnsched_Closing);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormUnsched_FormClosing);
        this.Load += new System.EventHandler(this.FormUnsched_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formUnsched_Load(Object sender, System.EventArgs e) throws Exception {
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
        menuDelete.Items.Clear();
        menuDelete.Items.Add(Lan.g(this,"Delete"), null, new EventHandler(menuDelete_click));
    }

    private void menuDelete_click(Object sender, System.EventArgs e) throws Exception {
        Items.APPLY __dummyScrutVar0 = menuDelete.Items.IndexOf((ToolStripMenuItem)sender);
        if (__dummyScrutVar0.equals(0))
        {
            delete_Click();
        }
         
    }

    private void grid_MouseDown(Object sender, MouseEventArgs e) throws Exception {
    }

    private void grid_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button == MouseButtons.Right)
        {
            if (grid.getSelectedIndices().Length > 0)
            {
                menuDelete.Show(grid, new Point(e.X, e.Y));
            }
             
        }
         
    }

    private void delete_Click() throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete appointments?"))
        {
            return ;
        }
         
        for (int i = 0;i < grid.getSelectedIndices().Length;i++)
        {
            Appointments.Delete(ListUn[grid.getSelectedIndices()[i]].AptNum);
        }
        fillGrid();
    }

    private void fillGrid() throws Exception {
        this.Cursor = Cursors.WaitCursor;
        String order = "";
        SelectedIndex __dummyScrutVar1 = comboOrder.SelectedIndex;
        if (__dummyScrutVar1.equals(0))
        {
            order = "status";
        }
        else if (__dummyScrutVar1.equals(1))
        {
            order = "alph";
        }
        else if (__dummyScrutVar1.equals(2))
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
         
        boolean showBrokenAppts = new boolean();
        showBrokenAppts = checkBrokenAppts.Checked;
        ListUn = Appointments.refreshUnsched(order,provNum,siteNum,showBrokenAppts,clinicNum);
        int scrollVal = grid.getScrollValue();
        grid.beginUpdate();
        grid.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableUnsched","Patient"),140);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableUnsched","Date"),65);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableUnsched","Status"),110);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableUnsched","Prov"),50);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableUnsched","Procedures"),150);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableUnsched","Notes"),200);
        grid.getColumns().add(col);
        grid.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListUn.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(patientNames[ListUn[i].PatNum]);
            if (ListUn[i].AptDateTime.Year < 1880)
                row.getCells().add("");
            else
                row.getCells().Add(ListUn[i].AptDateTime.ToShortDateString()); 
            row.getCells().Add(DefC.GetName(DefCat.RecallUnschedStatus, ListUn[i].UnschedStatus));
            row.getCells().Add(Providers.GetAbbr(ListUn[i].ProvNum));
            row.getCells().Add(ListUn[i].ProcDescript);
            row.getCells().Add(ListUn[i].Note);
            grid.getRows().add(row);
        }
        grid.endUpdate();
        grid.setScrollValue(scrollVal);
        Cursor = Cursors.Default;
    }

    private void grid_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        int currentSelection = e.getRow();
        //tbApts.SelectedRow;
        int currentScroll = grid.getScrollValue();
        //tbApts.ScrollValue;
        SelectedPatNum = ListUn[e.getRow()].PatNum;
        FormApptEdit FormAE = new FormApptEdit(ListUn[e.getRow()].AptNum);
        FormAE.PinIsVisible = true;
        FormAE.ShowDialog();
        if (FormAE.DialogResult != DialogResult.OK)
            return ;
         
        if (FormAE.PinClicked)
        {
            PinClicked = true;
            AptSelected = ListUn[e.getRow()].AptNum;
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
        if (!PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Unscheduled appointment list printed"))
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
            text = Lan.g(this,"Unscheduled List");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            //yPos+=(int)g.MeasureString(text,headingFont).Height;
            //text=textDateFrom.Text+" "+Lan.g(this,"to")+" "+textDateTo.Text;
            //g.DrawString(text,subHeadingFont,Brushes.Black,center-g.MeasureString(text,subHeadingFont).Width/2,yPos);
            yPos += 25;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = grid.printPage(g,pagesPrinted,bounds,headingPrintH);
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

    private void formUnsched_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
    }

    //Patients.HList=null;
    private void formUnsched_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (grid.getSelectedIndices().Length == 1)
        {
            SelectedPatNum = ListUn[grid.getSelectedIndices()[0]].PatNum;
        }
         
    }

}


