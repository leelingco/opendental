//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEquipmentEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EnumEquipmentDisplayMode;
import OpenDentBusiness.Equipment;
import OpenDentBusiness.Equipments;
import OpenDentBusiness.PrintSituation;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEquipment  extends Form 
{

    private List<Equipment> listEquip = new List<Equipment>();
    private int pagesPrinted = new int();
    private boolean headingPrinted = new boolean();
    private int headingPrintH = new int();
    public FormEquipment() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEquipment_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void radioPurchased_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void radioSold_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void radioAll_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textSn_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            MsgBox.show(this,"Invalid date.");
            return ;
        }
         
        fillGrid();
    }

    private void fillGrid() throws Exception {
        if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            return ;
        }
         
        DateTime fromDate = new DateTime();
        DateTime toDate = new DateTime();
        if (StringSupport.equals(textDateStart.Text, ""))
        {
            fromDate = DateTime.MinValue.AddDays(1);
        }
        else
        {
            //because we don't want to include 010101
            fromDate = PIn.Date(textDateStart.Text);
        } 
        if (StringSupport.equals(textDateEnd.Text, ""))
        {
            toDate = DateTime.MaxValue;
        }
        else
        {
            toDate = PIn.Date(textDateEnd.Text);
        } 
        EnumEquipmentDisplayMode display = EnumEquipmentDisplayMode.All;
        if (radioPurchased.Checked)
        {
            display = EnumEquipmentDisplayMode.Purchased;
        }
         
        if (radioSold.Checked)
        {
            display = EnumEquipmentDisplayMode.Sold;
        }
         
        listEquip = Equipments.GetList(fromDate, toDate, display, textSnDesc.Text);
        gridMain.beginUpdate();
        if (radioPurchased.Checked)
        {
            gridMain.setHScrollVisible(true);
        }
        else
        {
            gridMain.setHScrollVisible(false);
        } 
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Description"),150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"SerialNumber"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Yr"),40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"DatePurchased"),90);
        gridMain.getColumns().add(col);
        if (display != EnumEquipmentDisplayMode.Purchased)
        {
            //Purchased mode is designed for submission to tax authority, only certain columns
            col = new ODGridColumn(Lan.g(this,"DateSold"),90);
            gridMain.getColumns().add(col);
        }
         
        col = new ODGridColumn(Lan.g(this,"Cost"), 80, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Est Value"), 80, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        if (display != EnumEquipmentDisplayMode.Purchased)
        {
            col = new ODGridColumn(Lan.g(this,"Location"),80);
            gridMain.getColumns().add(col);
        }
         
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listEquip.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listEquip[i].Description);
            row.getCells().Add(listEquip[i].SerialNumber);
            row.getCells().Add(listEquip[i].ModelYear);
            row.getCells().Add(listEquip[i].DatePurchased.ToShortDateString());
            if (display != EnumEquipmentDisplayMode.Purchased)
            {
                if (listEquip[i].DateSold.Year < 1880)
                {
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().Add(listEquip[i].DateSold.ToShortDateString());
                } 
            }
             
            row.getCells().Add(listEquip[i].PurchaseCost.ToString("f"));
            row.getCells().Add(listEquip[i].MarketValue.ToString("f"));
            if (display != EnumEquipmentDisplayMode.Purchased)
            {
                row.getCells().Add(listEquip[i].Location);
            }
             
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        Equipment equip = new Equipment();
        equip.SerialNumber = Equipments.generateSerialNum();
        equip.DateEntry = DateTime.Today;
        equip.DatePurchased = DateTime.Today;
        FormEquipmentEdit form = new FormEquipmentEdit();
        form.IsNew = true;
        form.Equip = equip;
        form.ShowDialog();
        if (form.DialogResult == DialogResult.OK)
        {
            fillGrid();
        }
         
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEquipmentEdit form = new FormEquipmentEdit();
        form.Equip = listEquip[e.getRow()];
        form.ShowDialog();
        if (form.DialogResult == DialogResult.OK)
        {
            fillGrid();
        }
         
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        pagesPrinted = 0;
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        //pd.OriginAtMargins=true;
        //pd.DefaultPageSettings.Landscape=true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        try
        {
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Equipment list printed"))
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
            text = Lan.g(this,"Equipment List");
            if (radioPurchased.Checked)
            {
                text += " - " + Lan.g(this,"Purchased");
            }
             
            if (radioSold.Checked)
            {
                text += " - " + Lan.g(this,"Sold");
            }
             
            if (radioAll.Checked)
            {
                text += " - " + Lan.g(this,"All");
            }
             
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            text = textDateStart.Text + " " + Lan.g(this,"to") + " " + textDateEnd.Text;
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
            double total = 0;
            for (int i = 0;i < listEquip.Count;i++)
            {
                total += listEquip[i].MarketValue;
            }
            g.DrawString(Lan.g(this,"Total Est Value:") + " " + total.ToString("c"), Font, Brushes.Black, 550, yPos);
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
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.radioPurchased = new System.Windows.Forms.RadioButton();
        this.radioSold = new System.Windows.Forms.RadioButton();
        this.radioAll = new System.Windows.Forms.RadioButton();
        this.textSnDesc = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.butRefresh = new OpenDental.UI.Button();
        this.textDateStart = new OpenDental.ValidDate();
        this.textDateEnd = new OpenDental.ValidDate();
        this.butPrint = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 6);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(170, 39);
        this.label1.TabIndex = 7;
        this.label1.Text = "This list tracks equipment for payment of property taxes.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butRefresh);
        this.groupBox1.Controls.Add(this.textDateStart);
        this.groupBox1.Controls.Add(this.textDateEnd);
        this.groupBox1.Location = new System.Drawing.Point(424, 3);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(257, 42);
        this.groupBox1.TabIndex = 23;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Date Range";
        //
        // radioPurchased
        //
        this.radioPurchased.Location = new System.Drawing.Point(695, 3);
        this.radioPurchased.Name = "radioPurchased";
        this.radioPurchased.Size = new System.Drawing.Size(93, 18);
        this.radioPurchased.TabIndex = 23;
        this.radioPurchased.Text = "Purchased";
        this.radioPurchased.UseVisualStyleBackColor = true;
        this.radioPurchased.Click += new System.EventHandler(this.radioPurchased_Click);
        //
        // radioSold
        //
        this.radioSold.Location = new System.Drawing.Point(695, 18);
        this.radioSold.Name = "radioSold";
        this.radioSold.Size = new System.Drawing.Size(93, 18);
        this.radioSold.TabIndex = 24;
        this.radioSold.Text = "Sold";
        this.radioSold.UseVisualStyleBackColor = true;
        this.radioSold.Click += new System.EventHandler(this.radioSold_Click);
        //
        // radioAll
        //
        this.radioAll.Checked = true;
        this.radioAll.Location = new System.Drawing.Point(695, 33);
        this.radioAll.Name = "radioAll";
        this.radioAll.Size = new System.Drawing.Size(93, 18);
        this.radioAll.TabIndex = 25;
        this.radioAll.TabStop = true;
        this.radioAll.Text = "All";
        this.radioAll.UseVisualStyleBackColor = true;
        this.radioAll.Click += new System.EventHandler(this.radioAll_Click);
        //
        // textSnDesc
        //
        this.textSnDesc.Location = new System.Drawing.Point(285, 19);
        this.textSnDesc.Name = "textSnDesc";
        this.textSnDesc.Size = new System.Drawing.Size(133, 20);
        this.textSnDesc.TabIndex = 39;
        this.textSnDesc.TextChanged += new System.EventHandler(this.textSn_TextChanged);
        //
        // label7
        //
        this.label7.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.label7.Location = new System.Drawing.Point(174, 19);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(105, 20);
        this.label7.TabIndex = 38;
        this.label7.Text = "SN/Descipt/Loc";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(172, 12);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(78, 24);
        this.butRefresh.TabIndex = 23;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(6, 16);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(77, 20);
        this.textDateStart.TabIndex = 21;
        //
        // textDateEnd
        //
        this.textDateEnd.Location = new System.Drawing.Point(89, 15);
        this.textDateEnd.Name = "textDateEnd";
        this.textDateEnd.Size = new System.Drawing.Size(77, 20);
        this.textDateEnd.TabIndex = 22;
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(385, 550);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 24);
        this.butPrint.TabIndex = 8;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(141, 550);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 6;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 51);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(777, 490);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle("Equipment");
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
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(714, 550);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormEquipment
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(801, 585);
        this.Controls.Add(this.textSnDesc);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.radioAll);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.radioSold);
        this.Controls.Add(this.radioPurchased);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Name = "FormEquipment";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Equipment";
        this.Load += new System.EventHandler(this.FormEquipment_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butPrint;
    private OpenDental.ValidDate textDateEnd;
    private OpenDental.ValidDate textDateStart;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioPurchased = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioSold = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioAll = new System.Windows.Forms.RadioButton();
    private OpenDental.UI.Button butRefresh;
    private System.Windows.Forms.TextBox textSnDesc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
}


