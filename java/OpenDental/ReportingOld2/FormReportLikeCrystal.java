//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.ReportingOld2;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.ReportingOld2.FieldDefKind;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.ReportingOld2.FormReportLikeCrystal;
import OpenDental.ReportingOld2.ReportLikeCrystal;
import OpenDental.ReportingOld2.ReportObject;
import OpenDental.ReportingOld2.ReportObjectKind;
import OpenDental.ReportingOld2.Section;
import OpenDental.ReportingOld2.SpecialFieldType;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;

//using Microsoft.Vsa;
/**
* 
*/
public class FormReportLikeCrystal  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butPrint;
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    /**
    * The report to display.
    */
    private ReportLikeCrystal MyReport;
    private OpenDental.UI.Button butSetup;
    private System.Windows.Forms.PageSetupDialog setupDialog2 = new System.Windows.Forms.PageSetupDialog();
    /**
    * The y position printed through so far in the current section.
    */
    //private int printedThroughYPos; For now, assume all sections must remain together.
    private OpenDental.UI.Button button1;
    private System.Windows.Forms.Label labelTotPages = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butBack;
    private OpenDental.UI.Button butFwd;
    /**
    * The name of the last section printed. It really only keeps track of whether the details section and the reportfooter have finished printing. This variable will be refined when groups are implemented.
    */
    private String lastSectionPrinted = new String();
    private int rowsPrinted = new int();
    private int totalPages = new int();
    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.ImageList imageListMain = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.PrintPreviewControl printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
    private int pagesPrinted = new int();
    /**
    * 
    */
    public FormReportLikeCrystal(ReportLikeCrystal myReport) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        MyReport = myReport;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReportLikeCrystal.class);
        this.butClose = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.panel1 = new System.Windows.Forms.Panel();
        this.button1 = new OpenDental.UI.Button();
        this.labelTotPages = new System.Windows.Forms.Label();
        this.butBack = new OpenDental.UI.Button();
        this.butFwd = new OpenDental.UI.Button();
        this.butSetup = new OpenDental.UI.Button();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.setupDialog2 = new System.Windows.Forms.PageSetupDialog();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
        this.panel1.SuspendLayout();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(239, 2);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 1;
        this.butClose.Text = "&Close";
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Location = new System.Drawing.Point(1, 2);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 23);
        this.butPrint.TabIndex = 2;
        this.butPrint.Text = "&Print";
        //
        // panel1
        //
        this.panel1.Controls.Add(this.button1);
        this.panel1.Controls.Add(this.labelTotPages);
        this.panel1.Controls.Add(this.butBack);
        this.panel1.Controls.Add(this.butFwd);
        this.panel1.Controls.Add(this.butSetup);
        this.panel1.Controls.Add(this.butPrint);
        this.panel1.Controls.Add(this.butClose);
        this.panel1.Location = new System.Drawing.Point(-1, 178);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(831, 35);
        this.panel1.TabIndex = 4;
        this.panel1.Visible = false;
        //
        // button1
        //
        this.button1.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button1.setAutosize(true);
        this.button1.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button1.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button1.setCornerRadius(4F);
        this.button1.Location = new System.Drawing.Point(501, 8);
        this.button1.Name = "button1";
        this.button1.Size = new System.Drawing.Size(75, 23);
        this.button1.TabIndex = 4;
        this.button1.Text = "Test";
        this.button1.Visible = false;
        this.button1.Click += new System.EventHandler(this.button1_Click);
        //
        // labelTotPages
        //
        this.labelTotPages.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTotPages.Location = new System.Drawing.Point(137, 4);
        this.labelTotPages.Name = "labelTotPages";
        this.labelTotPages.Size = new System.Drawing.Size(54, 18);
        this.labelTotPages.TabIndex = 19;
        this.labelTotPages.Text = "1 / 2";
        this.labelTotPages.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // butBack
        //
        this.butBack.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBack.setAutosize(true);
        this.butBack.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBack.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBack.setCornerRadius(4F);
        this.butBack.Image = Resources.getLeft();
        this.butBack.Location = new System.Drawing.Point(115, 1);
        this.butBack.Name = "butBack";
        this.butBack.Size = new System.Drawing.Size(18, 23);
        this.butBack.TabIndex = 20;
        //
        // butFwd
        //
        this.butFwd.setAdjustImageLocation(new System.Drawing.Point(1, 0));
        this.butFwd.setAutosize(true);
        this.butFwd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFwd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFwd.setCornerRadius(4F);
        this.butFwd.Image = Resources.getRight();
        this.butFwd.Location = new System.Drawing.Point(193, 1);
        this.butFwd.Name = "butFwd";
        this.butFwd.Size = new System.Drawing.Size(18, 23);
        this.butFwd.TabIndex = 21;
        //
        // butSetup
        //
        this.butSetup.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSetup.setAutosize(true);
        this.butSetup.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSetup.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSetup.setCornerRadius(4F);
        this.butSetup.Location = new System.Drawing.Point(590, 2);
        this.butSetup.Name = "butSetup";
        this.butSetup.Size = new System.Drawing.Size(75, 23);
        this.butSetup.TabIndex = 3;
        this.butSetup.Text = "&Setup";
        this.butSetup.Visible = false;
        this.butSetup.Click += new System.EventHandler(this.butSetup_Click);
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
        this.imageListMain.Images.SetKeyName(3, "");
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
        // FormReportLikeCrystal
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(831, 570);
        this.Controls.Add(this.ToolBarMain);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.printPreviewControl2);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormReportOld2";
        this.ShowInTaskbar = false;
        this.Text = "Report";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormReport_Load);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.FormReport_Layout);
        this.panel1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formReport_Load(Object sender, System.EventArgs e) throws Exception {
        layoutToolBar();
        resetPd2();
        labelTotPages.Text = "/ " + totalPages.ToString();
        if (MyReport.getIsLandscape())
        {
            printPreviewControl2.Zoom = ((double)printPreviewControl2.ClientSize.Height / (double)pd2.DefaultPageSettings.PaperSize.Width);
        }
        else
        {
            printPreviewControl2.Zoom = ((double)printPreviewControl2.ClientSize.Height / (double)pd2.DefaultPageSettings.PaperSize.Height);
        } 
        printPreviewControl2.Document = pd2;
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
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Export"), 3, "", "Export"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Close"), -1, "Close This Window", "Close"));
    }

    //ToolBarMain.Invalidate();
    private void formReport_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        printPreviewControl2.Location = new System.Drawing.Point(0, panel1.Height);
        printPreviewControl2.Height = ClientSize.Height - panel1.Height;
        printPreviewControl2.Width = ClientSize.Width;
    }

    private void resetPd2() throws Exception {
        pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        lastSectionPrinted = "";
        rowsPrinted = 0;
        pagesPrinted = 0;
        if (MyReport.getIsLandscape())
        {
            pd2.DefaultPageSettings.Landscape = true;
        }
         
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        pd2.OriginAtMargins = true;
        //the actual margins are taken into consideration in the printpage event, and if the user specifies 0,0 for margins, then the report will reliably print on a preprinted form. Origin is ALWAYS the corner of the paper.
        if (pd2.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd2.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
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
        else if (__dummyScrutVar0.equals("Export"))
        {
            onExport_Click();
        }
        else if (__dummyScrutVar0.equals("Close"))
        {
            onClose_Click();
        }
             
    }

    /**
    * 
    */
    private void printReport() throws Exception {
        try
        {
            if (PrinterL.SetPrinter(pd2, PrintSituation.Default, 0, "Report printed " + MyReport.getReportName()))
            {
                pd2.Print();
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    /**
    * raised for each page to be printed.
    */
    private void pd2_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        //Note that the locations of the reportObjects are not absolute.  They depend entirely upon the margins.  When the report is initially created, it is pushed up against the upper and the left.
        Graphics grfx = ev.Graphics;
        //xPos and yPos represent the upper left of current section after margins are accounted for.
        //All reportObjects are then placed relative to this origin.
        Margins currentMargins = null;
        Size paperSize = new Size();
        if (MyReport.getIsLandscape())
            paperSize = new Size(1100, 850);
        else
            paperSize = new Size(850, 1100); 
        //if(MyReport.ReportMargins==null){ //Crashes MONO. That's ok, because MyReport.ReportMargins is
        //always null anyway.
        if (MyReport.getIsLandscape())
            currentMargins = new Margins(50, 0, 30, 30);
        else
            currentMargins = new Margins(30, 0, 50, 50); 
        //}
        //else{
        //	currentMargins=MyReport.ReportMargins;
        //}
        int xPos = currentMargins.Left;
        int yPos = currentMargins.Top;
        int printableHeight = paperSize.Height - currentMargins.Top - currentMargins.Bottom;
        int yLimit = paperSize.Height - currentMargins.Bottom;
        //the largest yPos allowed
        //Now calculate and layout each section in sequence.
        Section section;
        while (true)
        {
            //for(int sectionIndex=0;sectionIndex<Report.Sections.Count;sectionIndex++){
            //will break out if no more room on page
            //if no sections have been printed yet, print a report header.
            if (StringSupport.equals(lastSectionPrinted, ""))
            {
                if (MyReport.getSections().contains("Report Header"))
                {
                    section = MyReport.getSections().get___idx("Report Header");
                    printSection(grfx,section,xPos,yPos);
                    yPos += section.getHeight();
                    if (section.getHeight() > printableHeight)
                    {
                        //this can happen if the reportHeader takes up the full page
                        //if there are no other sections to print
                        if (MyReport.getReportTable() == null)
                        {
                            //this will keep the second page from printing:
                            lastSectionPrinted = "Report Footer";
                        }
                         
                        break;
                    }
                     
                }
                else
                {
                } 
                //no report header
                //it will still be marked as printed on the next line
                lastSectionPrinted = "Report Header";
            }
             
            //If the size of pageheader+one detail+pagefooter is taller than page, then we might later display an error. But for now, they will all still be laid out, and whatever goes off the bottom edge will just not show.  This will not be an issue for normal reports:
            if (MyReport.getSectionHeight("Page Header") + MyReport.getSectionHeight("Detail") + MyReport.getSectionHeight("Page Footer") > printableHeight)
            {
            }
             
            //nothing for now.
            //If this is first page and not enough room to print reportheader+pageheader+detail+pagefooter.
            if (pagesPrinted == 0 && MyReport.getSectionHeight("Report Header") + MyReport.getSectionHeight("Page Header") + MyReport.getSectionHeight("Detail") + MyReport.getSectionHeight("Page Footer") > printableHeight)
            {
                break;
            }
             
            //always print a page header if it exists
            if (MyReport.getSections().contains("Page Header"))
            {
                section = MyReport.getSections().get___idx("Page Header");
                printSection(grfx,section,xPos,yPos);
                yPos += section.getHeight();
            }
             
            //calculate if there is room for all elements including the reportfooter on this page.
            int rowsRemaining = 0;
            if (MyReport.getReportTable() != null)
            {
                rowsRemaining = MyReport.getReportTable().Rows.Count - rowsPrinted;
            }
             
            int totalDetailsHeight = rowsRemaining * MyReport.getSectionHeight("Detail");
            boolean isRoomForReportFooter = true;
            if (yLimit - yPos - MyReport.getSectionHeight("Report Footer") - MyReport.getSectionHeight("Page Footer") - totalDetailsHeight < 0)
            {
                isRoomForReportFooter = false;
            }
             
            //calculate how many rows of detail to print
            int rowsToPrint = rowsRemaining;
            section = MyReport.getSections().get___idx("Detail");
            if (!isRoomForReportFooter)
            {
                int actualDetailsHeight = yLimit - yPos - MyReport.getSectionHeight("Report Footer") - MyReport.getSectionHeight("Page Footer");
                rowsToPrint = (int)(actualDetailsHeight / MyReport.getSectionHeight("Detail"));
                if (rowsToPrint < 1)
                    rowsToPrint = 1;
                 
            }
             
            //Always print at least one row.
            //print the detail section
            printDetailsSection(grfx,section,xPos,yPos,rowsToPrint);
            if (rowsToPrint == rowsRemaining)
                //if all remaining rows were printed
                lastSectionPrinted = "Detail";
             
            //mark this section as printed.
            yPos += section.getHeight() * rowsToPrint;
            //print the reportfooter section if there is room
            if (isRoomForReportFooter)
            {
                if (MyReport.getSections().contains("Report Footer"))
                {
                    section = MyReport.getSections().get___idx("Report Footer");
                    printSection(grfx,section,xPos,yPos);
                    yPos += section.getHeight();
                }
                 
                //mark the reportfooter as printed. This will prevent another loop.
                lastSectionPrinted = "Report Footer";
            }
             
            //print the pagefooter
            if (MyReport.getSections().contains("Page Footer"))
            {
                section = MyReport.getSections().get___idx("Page Footer");
                if (isRoomForReportFooter)
                {
                    //for the last page, this moves the pagefooter to the bottom of the page.
                    yPos = yLimit - section.getHeight();
                }
                 
                printSection(grfx,section,xPos,yPos);
                yPos += section.getHeight();
            }
             
            break;
        }
        //while
        pagesPrinted++;
        //if the reportfooter has been printed, then there are no more pages.
        if (StringSupport.equals(lastSectionPrinted, "Report Footer"))
        {
            ev.HasMorePages = false;
            totalPages = pagesPrinted;
            ToolBarMain.getButtons().get___idx("PageNum").setText("1 / " + totalPages.ToString());
            ToolBarMain.Invalidate();
        }
        else
        {
            //labelTotPages.Text="1 / "+totalPages.ToString();
            ev.HasMorePages = true;
        } 
    }

    /**
    * Prints one section other than details at the specified x and y position on the page.  The math to decide whether it will fit on the current page is done ahead of time. There is no mechanism for splitting a section across multiple pages.
    */
    private void printSection(Graphics g, Section section, int xPos, int yPos) throws Exception {
        ReportObject textObject;
        ReportObject fieldObject;
        //LineObject lineObject;
        //BoxObject boxObject;
        StringFormat strFormat = new StringFormat();
        //used each time text is drawn to handle alignment issues
        //string rawText="";//the raw text for a given field as taken from the database
        String displayText = "";
        for (Object __dummyForeachVar0 : MyReport.getReportObjects())
        {
            //The formatted text to print
            ReportObject reportObject = (ReportObject)__dummyForeachVar0;
            //todo later: check for lines and boxes that span multiple sections.
            if (!StringSupport.equals(reportObject.getSectionName(), section.getName()))
            {
                continue;
            }
             
            if (reportObject.getObjectKind() == ReportObjectKind.TextObject)
            {
                textObject = reportObject;
                strFormat = ReportObject.getStringFormat(textObject.getTextAlign());
                RectangleF layoutRect = new RectangleF(xPos + textObject.getLocation().X, yPos + textObject.getLocation().Y, textObject.getSize().Width, textObject.getSize().Height);
                g.DrawString(textObject.getStaticText(), textObject.getFont(), Brushes.Black, layoutRect, strFormat);
            }
            else if (reportObject.getObjectKind() == ReportObjectKind.FieldObject)
            {
                fieldObject = reportObject;
                strFormat = ReportObject.getStringFormat(fieldObject.getTextAlign());
                RectangleF layoutRect = new RectangleF(xPos + fieldObject.getLocation().X, yPos + fieldObject.getLocation().Y, fieldObject.getSize().Width, fieldObject.getSize().Height);
                displayText = "";
                if (fieldObject.getFieldKind() == FieldDefKind.SummaryField)
                {
                    displayText = fieldObject.GetSummaryValue(MyReport.getReportTable(), MyReport.getDataFields().IndexOf(fieldObject.getSummarizedField())).ToString(fieldObject.getFormatString());
                }
                else if (fieldObject.getFieldKind() == FieldDefKind.SpecialField)
                {
                    if (fieldObject.getSpecialType() == SpecialFieldType.PageNofM)
                    {
                    }
                    else //not functional yet
                    //displayText=Lan.g(this,"Page")+" "+(pagesPrinted+1).ToString()
                    //	+Lan.g(
                    if (fieldObject.getSpecialType() == SpecialFieldType.PageNumber)
                    {
                        displayText = Lan.g(this,"Page") + " " + (pagesPrinted + 1).ToString();
                    }
                      
                }
                  
                g.DrawString(displayText, fieldObject.getFont(), Brushes.Black, layoutRect, strFormat);
            }
              
        }
    }

    //incomplete: else if lines
    //incomplete: else if boxes.
    //foreach reportObject
    //sectionsPrinted=sectionIndex+1;//mark current section as printed.
    //MessageBox.Show(pagesPrinted.ToString()+","+sectionsPrinted.ToString());
    //yPos+=section.Height;//set current yPos to the bottom of the section just printed.
    /**
    * Prints some rows of the details section at the specified x and y position on the page.  The math to decide how many rows to print is done ahead of time.  The number of rows printed so far is kept global so that it can be used in calculating the layout of this section.
    */
    private void printDetailsSection(Graphics g, Section section, int xPos, int yPos, int rowsToPrint) throws Exception {
        ReportObject textObject;
        ReportObject fieldObject;
        //LineObject lineObject;
        //BoxObject boxObject;
        StringFormat strFormat = new StringFormat();
        //used each time text is drawn to handle alignment issues
        String rawText = "";
        //the raw text for a given field as taken from the database
        String displayText = "";
        //The formatted text to print
        String prevDisplayText = "";
        for (int i = rowsPrinted;i < rowsPrinted + rowsToPrint;i++)
        {
            for (Object __dummyForeachVar1 : MyReport.getReportObjects())
            {
                //The formatted text of the previous row. Used to test suppress dupl.
                //loop through each row in the table
                ReportObject reportObject = (ReportObject)__dummyForeachVar1;
                //todo later: check for lines and boxes that span multiple sections.
                if (!StringSupport.equals(reportObject.getSectionName(), section.getName()))
                {
                    continue;
                }
                 
                //skip any reportObjects that are not in this section
                if (reportObject.getObjectKind() == ReportObjectKind.TextObject)
                {
                    //not typical to print textobject in details section, but allowed
                    textObject = reportObject;
                    strFormat = ReportObject.getStringFormat(textObject.getTextAlign());
                    RectangleF layoutRect = new RectangleF(xPos + textObject.getLocation().X, yPos + textObject.getLocation().Y, textObject.getSize().Width, textObject.getSize().Height);
                    g.DrawString(textObject.getStaticText(), textObject.getFont(), new SolidBrush(textObject.getForeColor()), layoutRect, strFormat);
                }
                else if (reportObject.getObjectKind() == ReportObjectKind.FieldObject)
                {
                    fieldObject = reportObject;
                    strFormat = ReportObject.getStringFormat(fieldObject.getTextAlign());
                    RectangleF layoutRect = new RectangleF(xPos + fieldObject.getLocation().X, yPos + fieldObject.getLocation().Y, fieldObject.getSize().Width, fieldObject.getSize().Height);
                    if (fieldObject.getFieldKind() == FieldDefKind.DataTableField)
                    {
                        rawText = MyReport.getReportTable().Rows[i][MyReport.getDataFields().IndexOf(fieldObject.getDataField())].ToString();
                        displayText = rawText;
                        if (fieldObject.getValueType() == FieldValueType.Age)
                        {
                            displayText = Patients.AgeToString(Patients.DateToAge(PIn.Date(MyReport.getReportTable().Rows[i][MyReport.getDataFields().IndexOf(fieldObject.getDataField())].ToString())));
                        }
                        else //(fieldObject.FormatString);
                        if (fieldObject.getValueType() == FieldValueType.Boolean)
                        {
                            displayText = PIn.Bool(MyReport.getReportTable().Rows[i][MyReport.getDataFields().IndexOf(fieldObject.getDataField())].ToString()).ToString();
                            //(fieldObject.FormatString);
                            if (i > 0 && fieldObject.getSuppressIfDuplicate())
                            {
                                prevDisplayText = PIn.Bool(MyReport.getReportTable().Rows[i - 1][MyReport.getDataFields().IndexOf(fieldObject.getDataField())].ToString()).ToString();
                            }
                             
                        }
                        else if (fieldObject.getValueType() == FieldValueType.Date)
                        {
                            displayText = PIn.DateT(MyReport.getReportTable().Rows[i][MyReport.getDataFields().IndexOf(fieldObject.getDataField())].ToString()).ToString(fieldObject.getFormatString());
                            if (i > 0 && fieldObject.getSuppressIfDuplicate())
                            {
                                prevDisplayText = PIn.DateT(MyReport.getReportTable().Rows[i - 1][MyReport.getDataFields().IndexOf(fieldObject.getDataField())].ToString()).ToString(fieldObject.getFormatString());
                            }
                             
                        }
                        else if (fieldObject.getValueType() == FieldValueType.Integer)
                        {
                            displayText = PIn.Long(MyReport.getReportTable().Rows[i][MyReport.getDataFields().IndexOf(fieldObject.getDataField())].ToString()).ToString(fieldObject.getFormatString());
                            if (i > 0 && fieldObject.getSuppressIfDuplicate())
                            {
                                prevDisplayText = PIn.Long(MyReport.getReportTable().Rows[i - 1][MyReport.getDataFields().IndexOf(fieldObject.getDataField())].ToString()).ToString(fieldObject.getFormatString());
                            }
                             
                        }
                        else if (fieldObject.getValueType() == FieldValueType.Number)
                        {
                            displayText = PIn.Double(MyReport.getReportTable().Rows[i][MyReport.getDataFields().IndexOf(fieldObject.getDataField())].ToString()).ToString(fieldObject.getFormatString());
                            if (i > 0 && fieldObject.getSuppressIfDuplicate())
                            {
                                prevDisplayText = PIn.Double(MyReport.getReportTable().Rows[i - 1][MyReport.getDataFields().IndexOf(fieldObject.getDataField())].ToString()).ToString(fieldObject.getFormatString());
                            }
                             
                        }
                        else if (fieldObject.getValueType() == FieldValueType.String)
                        {
                            displayText = rawText;
                            if (i > 0 && fieldObject.getSuppressIfDuplicate())
                            {
                                prevDisplayText = MyReport.getReportTable().Rows[i - 1][MyReport.getDataFields().IndexOf(fieldObject.getDataField())].ToString();
                            }
                             
                        }
                              
                        //suppress if duplicate:
                        if (i > 0 && fieldObject.getSuppressIfDuplicate() && StringSupport.equals(displayText, prevDisplayText))
                        {
                            displayText = "";
                        }
                         
                    }
                    else if (fieldObject.getFieldKind() == FieldDefKind.FormulaField)
                    {
                    }
                    else //can't do formulas yet
                    if (fieldObject.getFieldKind() == FieldDefKind.SpecialField)
                    {
                    }
                    else if (fieldObject.getFieldKind() == FieldDefKind.SummaryField)
                    {
                    }
                        
                    g.DrawString(displayText, fieldObject.getFont(), new SolidBrush(fieldObject.getForeColor()), layoutRect, strFormat);
                }
                  
            }
            //incomplete: else if lines
            //incomplete: else if boxes.
            //foreach reportObject
            yPos += section.getHeight();
        }
        //for i rows
        rowsPrinted += rowsToPrint;
    }

    private void butSetup_Click(Object sender, System.EventArgs e) throws Exception {
        setupDialog2.AllowMargins = true;
        setupDialog2.AllowOrientation = true;
        setupDialog2.AllowPaper = false;
        setupDialog2.AllowPrinter = false;
        setupDialog2.Document = pd2;
        setupDialog2.ShowDialog();
    }

    private void onPrint_Click() throws Exception {
        resetPd2();
        printReport();
    }

    private void onBack_Click() throws Exception {
        if (printPreviewControl2.StartPage == 0)
            return ;
         
        printPreviewControl2.StartPage--;
        ToolBarMain.getButtons().get___idx("PageNum").setText((printPreviewControl2.StartPage + 1).ToString() + " / " + totalPages.ToString());
        ToolBarMain.Invalidate();
    }

    //labelTotPages.Text=
    private void onFwd_Click() throws Exception {
        if (printPreviewControl2.StartPage == totalPages - 1)
            return ;
         
        printPreviewControl2.StartPage++;
        ToolBarMain.getButtons().get___idx("PageNum").setText((printPreviewControl2.StartPage + 1).ToString() + " / " + totalPages.ToString());
        ToolBarMain.Invalidate();
    }

    private void onExport_Click() throws Exception {
        SaveFileDialog saveFileDialog2 = new SaveFileDialog();
        saveFileDialog2.AddExtension = true;
        //saveFileDialog2.Title=Lan.g(this,"Select Folder to Save File To");
        saveFileDialog2.FileName = MyReport.getReportName() + ".txt";
        if (!Directory.Exists(PrefC.getString(PrefName.ExportPath)))
        {
            try
            {
                Directory.CreateDirectory(PrefC.getString(PrefName.ExportPath));
                saveFileDialog2.InitialDirectory = PrefC.getString(PrefName.ExportPath);
            }
            catch (Exception __dummyCatchVar1)
            {
            }
        
        }
        else
        {
            //initialDirectory will be blank
            saveFileDialog2.InitialDirectory = PrefC.getString(PrefName.ExportPath);
        } 
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
                    String line = "";
                    for (int i = 0;i < MyReport.getReportTable().Columns.Count;i++)
                    {
                        line += MyReport.getReportTable().Columns[i].Caption;
                        if (i < MyReport.getReportTable().Columns.Count - 1)
                        {
                            line += "\t";
                        }
                         
                    }
                    sw.WriteLine(line);
                    String cell = new String();
                    for (int i = 0;i < MyReport.getReportTable().Rows.Count;i++)
                    {
                        line = "";
                        for (int j = 0;j < MyReport.getReportTable().Columns.Count;j++)
                        {
                            cell = MyReport.getReportTable().Rows[i][j].ToString();
                            cell = cell.Replace("\r", "");
                            cell = cell.Replace("\n", "");
                            cell = cell.Replace("\t", "");
                            cell = cell.Replace("\"", "");
                            line += cell;
                            if (j < MyReport.getReportTable().Columns.Count - 1)
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
        catch (Exception __dummyCatchVar2)
        {
            //using
            MessageBox.Show(Lan.g(this,"File in use by another program.  Close and try again."));
            return ;
        }

        MessageBox.Show(Lan.g(this,"File created successfully"));
    }

    private void onClose_Click() throws Exception {
        this.Close();
    }

    private void button1_Click(Object sender, System.EventArgs e) throws Exception {
    }

}


//ScriptEngine.FormulaCode =
/*string functionCode=
			@"using System.Windows.Forms;
				using System;
				public class Test{
					public static void Main(){
						MessageBox.Show(""This is a test"");
						Test2 two = new Test2();
						two.Stuff();
					}
				}
				public class Test2{
					public void Stuff(){
					}
				}";
			CodeDomProvider codeProvider=new CSharpCodeProvider();
			ICodeCompiler compiler = codeProvider.CreateCompiler();
			CompilerParameters compilerParams = new CompilerParameters();
			compilerParams.CompilerOptions = "/target:library /optimize";
			compilerParams.GenerateExecutable = false;
			compilerParams.GenerateInMemory = true;
			compilerParams.IncludeDebugInformation = false;
			compilerParams.ReferencedAssemblies.Add("mscorlib.dll");
			compilerParams.ReferencedAssemblies.Add("System.dll");
			compilerParams.ReferencedAssemblies.Add("System.Windows.Forms.dll");
			CompilerResults results = compiler.CompileAssemblyFromSource(
                             compilerParams,functionCode);
			if (results.Errors.Count > 0){
				MessageBox.Show(results.Errors[0].ErrorText);
				//foreach (CompilerError error in results.Errors)
				//	DotNetScriptEngine.LogAllErrMsgs("Compine Error:"+error.ErrorText); 
				return;
			}
			Assembly assembly = results.CompiledAssembly;	
			//Use reflection to call the Main function in the assembly
			ScriptEngine.RunScript(assembly, "Main");		
			*/