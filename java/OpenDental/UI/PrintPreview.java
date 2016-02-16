//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.UI;

import java.util.ArrayList;
import java.util.List;
import OpenDental.Lan;
import OpenDental.PrinterL;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDental.UI.PrintPreview;
import OpenDentBusiness.PrintSituation;

//using Microsoft.Vsa;
/**
* 
*/
public class PrintPreview  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    /**
    * 
    */
    private int TotalPages = new int();
    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.ImageList imageListMain = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.PrintPreviewControl printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
    /**
    * 
    */
    private PrintDocument Document = new PrintDocument();
    /**
    * 
    */
    private PrintSituation Sit = PrintSituation.Default;
    /**
    * Must supply the printSituation so that when user clicks print, we know where to send it.  Must supply total pages
    */
    public PrintPreview(PrintSituation sit, PrintDocument document, int totalPages) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        Sit = sit;
        Document = document;
        TotalPages = totalPages;
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(PrintPreview.class);
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
        this.SuspendLayout();
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(831, 25);
        this.ToolBarMain.TabIndex = 5;
        this.ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "");
        this.imageListMain.Images.SetKeyName(1, "");
        this.imageListMain.Images.SetKeyName(2, "");
        //
        // printPreviewControl2
        //
        this.printPreviewControl2.AutoZoom = false;
        this.printPreviewControl2.Dock = System.Windows.Forms.DockStyle.Fill;
        this.printPreviewControl2.Location = new System.Drawing.Point(0, 0);
        this.printPreviewControl2.Name = "printPreviewControl2";
        this.printPreviewControl2.Size = new System.Drawing.Size(831, 570);
        this.printPreviewControl2.TabIndex = 6;
        //
        // PrintPreview
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(831, 570);
        this.Controls.Add(this.ToolBarMain);
        this.Controls.Add(this.printPreviewControl2);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "PrintPreview";
        this.ShowInTaskbar = false;
        this.Text = "Report";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormPrintPreview_Load);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.FormReport_Layout);
        this.ResumeLayout(false);
    }

    private void formPrintPreview_Load(Object sender, System.EventArgs e) throws Exception {
        layoutToolBar();
        if (Document.DefaultPageSettings.PrintableArea.Height == 0)
        {
            Document.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        //if document fits within window, then don't zoom it bigger; leave it at 100%
        if (Document.DefaultPageSettings.PaperSize.Height < printPreviewControl2.ClientSize.Height && Document.DefaultPageSettings.PaperSize.Width < printPreviewControl2.ClientSize.Width)
        {
            printPreviewControl2.Zoom = 1;
        }
        else //if document ratio is taller than screen ratio, shrink by height.
        if (Document.DefaultPageSettings.PaperSize.Height / Document.DefaultPageSettings.PaperSize.Width > printPreviewControl2.ClientSize.Height / printPreviewControl2.ClientSize.Width)
        {
            printPreviewControl2.Zoom = ((double)printPreviewControl2.ClientSize.Height / (double)Document.DefaultPageSettings.PaperSize.Height);
        }
        else
        {
            //otherwise, shrink by width
            printPreviewControl2.Zoom = ((double)printPreviewControl2.ClientSize.Width / (double)Document.DefaultPageSettings.PaperSize.Width);
        }  
        printPreviewControl2.Document = Document;
        ToolBarMain.getButtons().get___idx("PageNum").setText((printPreviewControl2.StartPage + 1).ToString() + " / " + TotalPages.ToString());
    }

    /**
    * Causes the toolbar to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Print"), 0, "", "Print"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 1, "Go Back One Page", "Back"));
        OpenDental.UI.ODToolBarButton button = new OpenDental.UI.ODToolBarButton("", -1, "", "PageNum");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.Label);
        ToolBarMain.getButtons().add(button);
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 2, "Go Forward One Page", "Fwd"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Close"), -1, "Close This Window", "Close"));
    }

    private void formReport_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        printPreviewControl2.Width = ClientSize.Width;
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        //MessageBox.Show(e.Button.Tag.ToString());
        Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
        if (__dummyScrutVar0.equals("Print"))
        {
            onPrint_Click();
        }
        else if (__dummyScrutVar0.equals("Back"))
        {
            onBack_Click();
        }
        else if (__dummyScrutVar0.equals("Fwd"))
        {
            onFwd_Click();
        }
        else if (__dummyScrutVar0.equals("Close"))
        {
            onClose_Click();
        }
            
    }

    private void onPrint_Click() throws Exception {
        if (!PrinterL.SetPrinter(Document, Sit))
        {
            return ;
        }
         
        if (Document.OriginAtMargins)
        {
            //In the sheets framework,we had to set margins to 20 because of a bug in their preview control.
            //We now need to set it back to 0 for the actual printing.
            //Hopefully, this doesn't break anything else.
            Document.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        }
         
        try
        {
            Document.Print();
        }
        catch (Exception e)
        {
            MessageBox.Show(Lan.g(this,"Error: ") + e.Message);
        }

        DialogResult = DialogResult.OK;
    }

    private void onClose_Click() throws Exception {
        this.Close();
    }

    private void onBack_Click() throws Exception {
        if (printPreviewControl2.StartPage == 0)
            return ;
         
        printPreviewControl2.StartPage--;
        ToolBarMain.getButtons().get___idx("PageNum").setText((printPreviewControl2.StartPage + 1).ToString() + " / " + TotalPages.ToString());
        ToolBarMain.Invalidate();
    }

    private void onFwd_Click() throws Exception {
        //if(printPreviewControl2.StartPage==totalPages-1) return;
        printPreviewControl2.StartPage++;
        ToolBarMain.getButtons().get___idx("PageNum").setText((printPreviewControl2.StartPage + 1).ToString() + " / " + TotalPages.ToString());
        ToolBarMain.Invalidate();
    }

}


