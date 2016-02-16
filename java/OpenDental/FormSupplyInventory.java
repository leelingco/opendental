//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormDefinitions;
import OpenDental.FormEquipment;
import OpenDental.FormSuppliers;
import OpenDental.FormSupplies;
import OpenDental.FormSupplyNeededEdit;
import OpenDental.FormSupplyOrders;
import OpenDental.Lan;
import OpenDental.PrinterL;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.SupplyNeeded;
import OpenDentBusiness.SupplyNeededs;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormSupplyInventory  extends Form 
{

    private List<SupplyNeeded> listNeeded = new List<SupplyNeeded>();
    private int pagesPrinted = new int();
    private boolean headingPrinted = new boolean();
    private int headingPrintH = new int();
    public FormSupplyInventory() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formInventory_Load(Object sender, EventArgs e) throws Exception {
        fillGridNeeded();
    }

    private void fillGridNeeded() throws Exception {
        listNeeded = SupplyNeededs.createObjects();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date Added"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Description"),300);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listNeeded.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listNeeded[i].DateAdded.ToShortDateString());
            row.getCells().Add(listNeeded[i].Description);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridNeeded_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormSupplyNeededEdit FormS = new FormSupplyNeededEdit();
        FormS.Supp = listNeeded[e.getRow()];
        FormS.ShowDialog();
        if (FormS.DialogResult == DialogResult.OK)
        {
            fillGridNeeded();
        }
         
    }

    private void butAddNeeded_Click(Object sender, EventArgs e) throws Exception {
        SupplyNeeded supp = new SupplyNeeded();
        supp.setIsNew(true);
        supp.DateAdded = DateTime.Today;
        FormSupplyNeededEdit FormS = new FormSupplyNeededEdit();
        FormS.Supp = supp;
        FormS.ShowDialog();
        if (FormS.DialogResult == DialogResult.OK)
        {
            fillGridNeeded();
        }
         
    }

    private void menuItemSuppliers_Click(Object sender, EventArgs e) throws Exception {
        FormSuppliers FormS = new FormSuppliers();
        FormS.ShowDialog();
    }

    private void menuItemCategories_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormDefinitions FormD = new FormDefinitions(DefCat.SupplyCats);
        FormD.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Definitions.");
    }

    private void butEquipment_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.EquipmentSetup))
        {
            return ;
        }
         
        FormEquipment form = new FormEquipment();
        form.ShowDialog();
    }

    private void butOrders_Click(Object sender, EventArgs e) throws Exception {
        FormSupplyOrders FormSO = new FormSupplyOrders();
        FormSO.ShowDialog();
    }

    private void butSupplies_Click(Object sender, EventArgs e) throws Exception {
        FormSupplies FormSup = new FormSupplies();
        FormSup.ShowDialog();
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
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Supplies needed list printed"))
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
        //new Rectangle(50,40,800,1035);//Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = Lan.g(this,"Supplies Needed");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            //text=Lan.g(this,"Supplies Needed");
            //g.DrawString(text,headingFont,Brushes.Black,center-g.MeasureString(text,headingFont).Width/2,yPos);
            //yPos+=(int)g.MeasureString(text,headingFont).Height;
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
        } 
        g.Dispose();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
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
        this.menuStrip1 = new System.Windows.Forms.MenuStrip();
        this.menuItemSuppliers = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemCategories = new System.Windows.Forms.ToolStripMenuItem();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAddNeeded = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butEquipment = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.butOrders = new OpenDental.UI.Button();
        this.butSupplies = new OpenDental.UI.Button();
        this.menuStrip1.SuspendLayout();
        this.SuspendLayout();
        //
        // menuStrip1
        //
        this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.menuItemSuppliers, this.menuItemCategories });
        this.menuStrip1.Location = new System.Drawing.Point(0, 0);
        this.menuStrip1.Name = "menuStrip1";
        this.menuStrip1.Size = new System.Drawing.Size(474, 24);
        this.menuStrip1.TabIndex = 12;
        this.menuStrip1.Text = "menuStrip1";
        //
        // menuItemSuppliers
        //
        this.menuItemSuppliers.Name = "menuItemSuppliers";
        this.menuItemSuppliers.Size = new System.Drawing.Size(67, 20);
        this.menuItemSuppliers.Text = "Suppliers";
        this.menuItemSuppliers.Click += new System.EventHandler(this.menuItemSuppliers_Click);
        //
        // menuItemCategories
        //
        this.menuItemCategories.Name = "menuItemCategories";
        this.menuItemCategories.Size = new System.Drawing.Size(75, 20);
        this.menuItemCategories.Text = "Categories";
        this.menuItemCategories.Click += new System.EventHandler(this.menuItemCategories_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 117);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(450, 477);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Supplies Needed");
        this.gridMain.setTranslationName(null);
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridNeeded_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butAddNeeded
        //
        this.butAddNeeded.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddNeeded.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAddNeeded.setAutosize(true);
        this.butAddNeeded.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddNeeded.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddNeeded.setCornerRadius(4F);
        this.butAddNeeded.Image = Resources.getAdd();
        this.butAddNeeded.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddNeeded.Location = new System.Drawing.Point(12, 602);
        this.butAddNeeded.Name = "butAddNeeded";
        this.butAddNeeded.Size = new System.Drawing.Size(80, 26);
        this.butAddNeeded.TabIndex = 5;
        this.butAddNeeded.Text = "Add";
        this.butAddNeeded.Click += new System.EventHandler(this.butAddNeeded_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(387, 602);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butEquipment
        //
        this.butEquipment.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEquipment.setAutosize(true);
        this.butEquipment.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEquipment.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEquipment.setCornerRadius(4F);
        this.butEquipment.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEquipment.Location = new System.Drawing.Point(12, 27);
        this.butEquipment.Name = "butEquipment";
        this.butEquipment.Size = new System.Drawing.Size(80, 24);
        this.butEquipment.TabIndex = 15;
        this.butEquipment.Text = "Equipment";
        this.butEquipment.Click += new System.EventHandler(this.butEquipment_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(158, 602);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(80, 26);
        this.butPrint.TabIndex = 24;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butOrders
        //
        this.butOrders.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOrders.setAutosize(true);
        this.butOrders.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOrders.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOrders.setCornerRadius(4F);
        this.butOrders.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butOrders.Location = new System.Drawing.Point(12, 57);
        this.butOrders.Name = "butOrders";
        this.butOrders.Size = new System.Drawing.Size(80, 24);
        this.butOrders.TabIndex = 14;
        this.butOrders.Text = "Orders";
        this.butOrders.Click += new System.EventHandler(this.butOrders_Click);
        //
        // butSupplies
        //
        this.butSupplies.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSupplies.setAutosize(true);
        this.butSupplies.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSupplies.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSupplies.setCornerRadius(4F);
        this.butSupplies.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSupplies.Location = new System.Drawing.Point(12, 87);
        this.butSupplies.Name = "butSupplies";
        this.butSupplies.Size = new System.Drawing.Size(80, 24);
        this.butSupplies.TabIndex = 13;
        this.butSupplies.Text = "Supplies";
        this.butSupplies.Click += new System.EventHandler(this.butSupplies_Click);
        //
        // FormSupplyInventory
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
        this.ClientSize = new System.Drawing.Size(474, 638);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAddNeeded);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butEquipment);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butOrders);
        this.Controls.Add(this.butSupplies);
        this.Controls.Add(this.menuStrip1);
        this.MainMenuStrip = this.menuStrip1;
        this.Name = "FormSupplyInventory";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Supply Inventory";
        this.Load += new System.EventHandler(this.FormInventory_Load);
        this.menuStrip1.ResumeLayout(false);
        this.menuStrip1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAddNeeded;
    private System.Windows.Forms.MenuStrip menuStrip1 = new System.Windows.Forms.MenuStrip();
    private System.Windows.Forms.ToolStripMenuItem menuItemSuppliers = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemCategories = new System.Windows.Forms.ToolStripMenuItem();
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.Button butSupplies;
    private OpenDental.UI.Button butOrders;
    private OpenDental.UI.Button butEquipment;
    private OpenDental.UI.ODGrid gridMain;
}


