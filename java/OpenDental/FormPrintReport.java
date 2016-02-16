//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.FormPrintReport;
import OpenDental.Lan;
import OpenDental.Properties.Resources;


/**
* This class is pretty much only coded for 8.5 inch X 11 inch sheets of paper, but with a little work could be made for more general sizes of paper, perhaps sometime in the future.
*/
public class FormPrintReport  extends Form 
{

    /**
    * If pageNumberFont is set, then the page number is displayed using the page number information.
    */
    private Font pageNumberFont = null;
    private int totalPages = 0;
    /**
    * Is set to a non-null value only during printing to a physical printer.
    */
    private Graphics printerGraph = null;
    private Rectangle printerMargins = new Rectangle();
    private int pageHeight = 900;
    private int curPrintPage = 0;
    public static class __MultiPrintCallback   implements PrintCallback
    {
        public void invoke(FormPrintReport fpr) throws Exception {
            IList<PrintCallback> copy = new IList<PrintCallback>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<PrintCallback>(members);
            }
            for (Object __dummyForeachVar0 : copy)
            {
                PrintCallback d = (PrintCallback)__dummyForeachVar0;
                d.invoke(fpr);
            }
        }

        private System.Collections.Generic.IList<PrintCallback> _invocationList = new ArrayList<PrintCallback>();
        public static PrintCallback combine(PrintCallback a, PrintCallback b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiPrintCallback ret = new __MultiPrintCallback();
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static PrintCallback remove(PrintCallback a, PrintCallback b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<PrintCallback> aInvList = a.getInvocationList();
            System.Collections.Generic.IList<PrintCallback> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                __MultiPrintCallback ret = new __MultiPrintCallback();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        public System.Collections.Generic.IList<PrintCallback> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    //Call the custom printing code.
    //Print page numbers.
    public static interface PrintCallback   
    {
        void invoke(FormPrintReport fpr) throws Exception ;

        System.Collections.Generic.IList<PrintCallback> getInvocationList() throws Exception ;
    
    }

    public PrintCallback printGenerator = null;
    private boolean landscape = false;
    private int numTimesPrinted = 0;
    private int minimumTimesToPrint = 0;
    public FormPrintReport() throws Exception {
        initializeComponent();
    }

    private void formPrintReport_Load(Object sender, EventArgs e) throws Exception {
        printCustom();
        checkWindowSize();
    }

    public void usePageNumbers(Font font) throws Exception {
        pageNumberFont = font;
    }

    private void printCustom() throws Exception {
        if (printGenerator == null)
        {
            return ;
        }
         
        printPanel.clear();
        Invoke(printGenerator, new Object[]{ this });
        if (pageNumberFont != null)
        {
            for (int i = 0;i < totalPages;i++)
            {
                String text = "Page " + (i + 1);
                SizeF size = getGraph().MeasureString(text, pageNumberFont);
                getGraph().DrawString(text, pageNumberFont, Brushes.Black, new PointF(getGraphWidth() - size.Width, i * (pageHeight + getMarginBottom())));
            }
        }
         
    }

    private int curPage() throws Exception {
        return (vScroll.Value - vScroll.Minimum + 1) / pageHeight;
    }

    private void calculatePageOffset() throws Exception {
        printPanel.setOrigin(new Point(0, -vScroll.Value));
        labPageNum.Text = "Page: " + (curPage() + 1) + "\\" + totalPages;
    }

    private void calculateVScrollMax() throws Exception {
        if (totalPages > 1)
        {
            vScroll.Maximum = pageHeight * (totalPages - 1) - 1 + vScroll.Minimum;
        }
        else
        {
            vScroll.Maximum = vScroll.Minimum;
        } 
    }

    private void moveScrollBar(int amount) throws Exception {
        int val = vScroll.Value + amount;
        if (val < vScroll.Minimum)
        {
            val = vScroll.Minimum;
        }
        else if (val > vScroll.Maximum)
        {
            val = vScroll.Maximum;
        }
          
        vScroll.Value = val;
    }

    public int getScrollAmount() throws Exception {
        return vScroll.SmallChange;
    }

    public void setScrollAmount(int value) throws Exception {
        vScroll.SmallChange = value;
    }

    /**
    * Window size can change based on landscape mode and possibly other things in the future (perhaps different paper sizes).
    */
    private void checkWindowSize() throws Exception {
        if (landscape)
        {
            this.Width = 942;
            setPageHeight(650);
        }
        else
        {
            this.Width = 692;
            setPageHeight(900);
        } 
        printPanel.Width = this.Width - 42;
        vScroll.Location = new Point(this.Width - 29, 33);
    }

    public boolean getLandscape() throws Exception {
        return landscape;
    }

    public void setLandscape(boolean value) throws Exception {
        landscape = value;
        checkWindowSize();
    }

    public int getMarginBottom() throws Exception {
        return (printerGraph != null) ? (1100 - printerMargins.Bottom) : 0;
    }

    public Graphics getGraph() throws Exception {
        return (printerGraph != null) ? printerGraph : printPanel.backBuffer;
    }

    public int getPageHeight() throws Exception {
        return pageHeight;
    }

    public void setPageHeight(int value) throws Exception {
        pageHeight = value;
        calculateVScrollMax();
    }

    /**
    * Must be set by the external printing algorithm in order to get page numbers working properly.
    */
    public int getTotalPages() throws Exception {
        return totalPages;
    }

    public void setTotalPages(int value) throws Exception {
        totalPages = value;
        calculatePageOffset();
        labPageNum.Visible = (totalPages > 0);
        calculateVScrollMax();
    }

    public int getGraphWidth() throws Exception {
        return (printerGraph != null) ? printerMargins.Width : printPanel.Width;
    }

    public void setGraphWidth(int value) throws Exception {
        printPanel.Width = value;
    }

    public int getMinimumTimesToPrint() throws Exception {
        return minimumTimesToPrint;
    }

    public void setMinimumTimesToPrint(int value) throws Exception {
        minimumTimesToPrint = value;
    }

    private void display() throws Exception {
        calculatePageOffset();
        printCustom();
        printPanel.Invalidate(true);
    }

    private void butNextPage_Click(Object sender, EventArgs e) throws Exception {
        moveScrollBar((curPage() + 1) * pageHeight - vScroll.Value);
        display();
    }

    private void butPreviousPage_Click(Object sender, EventArgs e) throws Exception {
        if (vScroll.Value % pageHeight == 0)
        {
            moveScrollBar(-pageHeight);
        }
        else
        {
            moveScrollBar(curPage() * pageHeight - vScroll.Value);
        } 
        display();
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        curPrintPage = 0;
        pd1.OriginAtMargins = true;
        pd1.DefaultPageSettings.Landscape = landscape;
        try
        {
            pd1.Print();
            numTimesPrinted++;
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Failed to print") + ": " + ex.Message);
        }

        printerGraph = null;
        display();
    }

    private void pd1_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        printerGraph = e.Graphics;
        printerMargins = e.MarginBounds;
        printerGraph.TranslateTransform(0, -curPrintPage * (pageHeight + getMarginBottom()));
        printCustom();
        curPrintPage++;
        e.HasMorePages = (printGenerator != null) && (curPrintPage < totalPages);
    }

    private void vScroll_Scroll(Object sender, ScrollEventArgs e) throws Exception {
        display();
    }

    private void formPrintReport_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (numTimesPrinted < minimumTimesToPrint)
        {
            if (MessageBox.Show("WARNING: You should print this document at least " + (minimumTimesToPrint == 1 ? "one time." : (minimumTimesToPrint + " times.")) + "You may not be able to print this document again if you close it now. Are you sure you want to close this document?", "", MessageBoxButtons.YesNo) == DialogResult.No)
            {
                e.Cancel = true;
                return ;
            }
             
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
        this.printPanel = new CodeBase.PrintPanel();
        this.labPageNum = new System.Windows.Forms.Label();
        this.pd1 = new System.Drawing.Printing.PrintDocument();
        this.vScroll = new System.Windows.Forms.VScrollBar();
        this.butPrint = new OpenDental.UI.Button();
        this.butPreviousPage = new OpenDental.UI.Button();
        this.butNextPage = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // printPanel
        //
        this.printPanel.BackColor = System.Drawing.Color.White;
        this.printPanel.Location = new System.Drawing.Point(10, 33);
        this.printPanel.Name = "printPanel";
        this.printPanel.setOrigin(new System.Drawing.Point(0, 0));
        this.printPanel.Size = new System.Drawing.Size(650, 620);
        this.printPanel.TabIndex = 0;
        //
        // labPageNum
        //
        this.labPageNum.AutoSize = true;
        this.labPageNum.BackColor = System.Drawing.SystemColors.ButtonShadow;
        this.labPageNum.Location = new System.Drawing.Point(156, 9);
        this.labPageNum.Name = "labPageNum";
        this.labPageNum.Size = new System.Drawing.Size(38, 13);
        this.labPageNum.TabIndex = 4;
        this.labPageNum.Text = "Page: ";
        this.labPageNum.Visible = false;
        //
        // pd1
        //
        this.pd1.PrintPage += new System.Drawing.Printing.PrintPageEventHandler(this.pd1_PrintPage);
        //
        // vScroll
        //
        this.vScroll.LargeChange = 30;
        this.vScroll.Location = new System.Drawing.Point(663, 33);
        this.vScroll.Minimum = 1;
        this.vScroll.Name = "vScroll";
        this.vScroll.Size = new System.Drawing.Size(20, 620);
        this.vScroll.TabIndex = 5;
        this.vScroll.Value = 1;
        this.vScroll.Scroll += new System.Windows.Forms.ScrollEventHandler(this.vScroll_Scroll);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.Location = new System.Drawing.Point(101, 4);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(49, 23);
        this.butPrint.TabIndex = 3;
        this.butPrint.Text = "Print";
        this.butPrint.UseVisualStyleBackColor = true;
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butPreviousPage
        //
        this.butPreviousPage.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPreviousPage.setAutosize(false);
        this.butPreviousPage.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPreviousPage.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPreviousPage.setCornerRadius(4F);
        this.butPreviousPage.Image = Resources.getup();
        this.butPreviousPage.Location = new System.Drawing.Point(50, 4);
        this.butPreviousPage.Name = "butPreviousPage";
        this.butPreviousPage.Size = new System.Drawing.Size(45, 23);
        this.butPreviousPage.TabIndex = 2;
        this.butPreviousPage.Text = "Previous Page";
        this.butPreviousPage.UseVisualStyleBackColor = true;
        this.butPreviousPage.Click += new System.EventHandler(this.butPreviousPage_Click);
        //
        // butNextPage
        //
        this.butNextPage.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNextPage.setAutosize(false);
        this.butNextPage.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNextPage.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNextPage.setCornerRadius(4F);
        this.butNextPage.Image = Resources.getdown();
        this.butNextPage.Location = new System.Drawing.Point(5, 4);
        this.butNextPage.Name = "butNextPage";
        this.butNextPage.Size = new System.Drawing.Size(39, 23);
        this.butNextPage.TabIndex = 1;
        this.butNextPage.Text = "Next Page";
        this.butNextPage.UseVisualStyleBackColor = true;
        this.butNextPage.Click += new System.EventHandler(this.butNextPage_Click);
        //
        // FormPrintReport
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(686, 666);
        this.Controls.Add(this.vScroll);
        this.Controls.Add(this.labPageNum);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butPreviousPage);
        this.Controls.Add(this.butNextPage);
        this.Controls.Add(this.printPanel);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPrintReport";
        this.Text = "Print Report";
        this.Load += new System.EventHandler(this.FormPrintReport_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormPrintReport_FormClosing);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private CodeBase.PrintPanel printPanel;
    private OpenDental.UI.Button butNextPage;
    private OpenDental.UI.Button butPreviousPage;
    private OpenDental.UI.Button butPrint;
    private System.Windows.Forms.Label labPageNum = new System.Windows.Forms.Label();
    private System.Drawing.Printing.PrintDocument pd1 = new System.Drawing.Printing.PrintDocument();
    private System.Windows.Forms.VScrollBar vScroll = new System.Windows.Forms.VScrollBar();
}


