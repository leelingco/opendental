//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:09 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormRpStatement;
import OpenDental.Lan;
import OpenDental.MigraDocHelper;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Documents;
import OpenDentBusiness.Family;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.ImageType;
import OpenDentBusiness.InstallmentPlan;
import OpenDentBusiness.InstallmentPlans;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Statement;
import OpenDentBusiness.Statements;
import OpenDentBusiness.YN;

/**
* 
*/
public class FormRpStatement  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.PrintPreviewControl printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.Panel panelZoom = new System.Windows.Forms.Panel();
    private OpenDental.UI.Button butFwd;
    private OpenDental.UI.Button butBack;
    private System.Windows.Forms.Label labelTotPages = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butZoomIn;
    private OpenDental.UI.Button butFullPage;
    private System.ComponentModel.Container components = null;
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private int totalPages = new int();
    /**
    * Holds the data for one statement.
    */
    private DataSet dataSett = new DataSet();
    private Statement Stmt;
    //private ImageStoreBase imageStore;
    /**
    * 
    */
    public FormRpStatement() throws Exception {
        initializeComponent();
        //exclude:
        Lan.F(this, new Control[]{ labelTotPages });
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpStatement.class);
        this.printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
        this.butPrint = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.panelZoom = new System.Windows.Forms.Panel();
        this.butFwd = new OpenDental.UI.Button();
        this.butBack = new OpenDental.UI.Button();
        this.labelTotPages = new System.Windows.Forms.Label();
        this.butZoomIn = new OpenDental.UI.Button();
        this.butFullPage = new OpenDental.UI.Button();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.panelZoom.SuspendLayout();
        this.SuspendLayout();
        //
        // printPreviewControl2
        //
        this.printPreviewControl2.AutoZoom = false;
        this.printPreviewControl2.Location = new System.Drawing.Point(-116, -7);
        this.printPreviewControl2.Name = "printPreviewControl2";
        this.printPreviewControl2.Size = new System.Drawing.Size(842, 538);
        this.printPreviewControl2.TabIndex = 6;
        this.printPreviewControl2.Zoom = 1;
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
        this.butPrint.Location = new System.Drawing.Point(340, 5);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(85, 27);
        this.butPrint.TabIndex = 8;
        this.butPrint.Text = "          Print";
        this.butPrint.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Visible = false;
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(434, 5);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 27);
        this.butClose.TabIndex = 7;
        this.butClose.Text = "Close";
        //
        // panelZoom
        //
        this.panelZoom.Controls.Add(this.butFwd);
        this.panelZoom.Controls.Add(this.butBack);
        this.panelZoom.Controls.Add(this.labelTotPages);
        this.panelZoom.Controls.Add(this.butZoomIn);
        this.panelZoom.Controls.Add(this.butFullPage);
        this.panelZoom.Controls.Add(this.butClose);
        this.panelZoom.Controls.Add(this.butPrint);
        this.panelZoom.Location = new System.Drawing.Point(35, 419);
        this.panelZoom.Name = "panelZoom";
        this.panelZoom.Size = new System.Drawing.Size(524, 37);
        this.panelZoom.TabIndex = 12;
        //
        // butFwd
        //
        this.butFwd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butFwd.setAutosize(true);
        this.butFwd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFwd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFwd.setCornerRadius(4F);
        this.butFwd.Image = Resources.getRight();
        this.butFwd.Location = new System.Drawing.Point(195, 7);
        this.butFwd.Name = "butFwd";
        this.butFwd.Size = new System.Drawing.Size(18, 22);
        this.butFwd.TabIndex = 13;
        this.butFwd.Click += new System.EventHandler(this.butFwd_Click);
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
        this.butBack.Size = new System.Drawing.Size(18, 22);
        this.butBack.TabIndex = 12;
        this.butBack.Click += new System.EventHandler(this.butBack_Click);
        //
        // labelTotPages
        //
        this.labelTotPages.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTotPages.Location = new System.Drawing.Point(143, 9);
        this.labelTotPages.Name = "labelTotPages";
        this.labelTotPages.Size = new System.Drawing.Size(47, 18);
        this.labelTotPages.TabIndex = 11;
        this.labelTotPages.Text = "1 / 2";
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
        this.butZoomIn.Size = new System.Drawing.Size(94, 27);
        this.butZoomIn.TabIndex = 10;
        this.butZoomIn.Text = "       Zoom In";
        this.butZoomIn.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butZoomIn.Visible = false;
        this.butZoomIn.Click += new System.EventHandler(this.butZoomIn_Click);
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
        this.butFullPage.Text = "Full Page";
        this.butFullPage.Visible = false;
        this.butFullPage.Click += new System.EventHandler(this.butFullPage_Click);
        //
        // FormRpStatement
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(787, 610);
        this.Controls.Add(this.panelZoom);
        this.Controls.Add(this.printPreviewControl2);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormRpStatement";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Statement Preview";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormRpStatement_Load);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.FormRpStatement_Layout);
        this.panelZoom.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formRpStatement_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        printPreviewControl2.Location = new Point(0, 0);
        printPreviewControl2.Height = ClientSize.Height - 39;
        printPreviewControl2.Width = ClientSize.Width;
        panelZoom.Location = new Point(ClientSize.Width - 620, ClientSize.Height - 38);
    }

    private void formRpStatement_Load(Object sender, System.EventArgs e) throws Exception {
        //this only happens during debugging
        labelTotPages.Text = "1 / " + totalPages.ToString();
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

    /**
    * Creates a new pdf, attaches it to a new doc, and attaches that to the statement.  If it cannot create a pdf, for example if no AtoZ folders, then it will simply result in a docnum of zero, so no attached doc.
    */
    public void createStatementPdf(Statement stmt, Patient pat, Family fam, DataSet dataSet) throws Exception {
        Stmt = stmt;
        dataSett = dataSet;
        //dataSet=AccountModuleL.GetStatement(stmt.PatNum,stmt.SinglePatient,stmt.DateRangeFrom,stmt.DateRangeTo,
        //	stmt.Intermingled);
        //if(ImageStore.UpdatePatient == null){
        //	ImageStore.UpdatePatient = new FileStore.UpdatePatientDelegate(Patients.Update);
        //}
        //The "pat2" could be either the pat or the guarantor
        Patient pat2 = fam.getPatient(stmt.PatNum);
        //Patient pat=Patients.GetPat(stmt.PatNum);
        //imageStore = ImageStore.GetImageStore(pat2);
        //Save to a temp pdf--------------------------------------------------------------------------
        String tempPath = CodeBase.ODFileUtils.CombinePaths(Path.GetTempPath(), pat2.PatNum.ToString() + ".pdf");
        PrintDocument pd = new PrintDocument();
        pd.DefaultPageSettings.Margins = new Margins(40, 40, 40, 60);
        if (CultureInfo.CurrentCulture.Name.EndsWith("CH"))
        {
            //CH is for switzerland. eg de-CH
            //leave a big margin on the bottom for the routing slip
            pd.DefaultPageSettings.Margins = new Margins(40, 40, 40, 440);
        }
         
        //4.4" from bottom
        //pd.OriginAtMargins=true;
        //fixes a bug if user has label printer as default printer on their computer:
        if (pd.DefaultPageSettings.PrintableArea.Height < 1000 || pd.DefaultPageSettings.PrintableArea.Width < 750)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        MigraDoc.DocumentObjectModel.Document doc = createDocument(pd,fam,pat,dataSet);
        MigraDoc.Rendering.PdfDocumentRenderer pdfRenderer = new MigraDoc.Rendering.PdfDocumentRenderer(true, PdfFontEmbedding.Always);
        pdfRenderer.Document = doc;
        pdfRenderer.RenderDocument();
        pdfRenderer.PdfDocument.Save(tempPath);
        //get the category-----------------------------------------------------------------------------
        long category = 0;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()].Length;i++)
        {
            if (Regex.IsMatch(DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][i].ItemValue, "S"))
            {
                category = DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][i].DefNum;
                break;
            }
             
        }
        if (category == 0)
        {
            category = DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][0].DefNum;
        }
         
        //put it in the first category.
        //create doc--------------------------------------------------------------------------------------
        OpenDentBusiness.Document docc = null;
        try
        {
            docc = ImageStore.import(tempPath,category,pat2);
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Error saving document.");
            return ;
        }

        //this.Cursor=Cursors.Default;
        docc.ImgType = ImageType.Document;
        if (Stmt.IsInvoice)
        {
            docc.Description = Lan.g(this,"Invoice");
        }
        else
        {
            if (Stmt.IsReceipt == true)
            {
                docc.Description = Lan.g(this,"Receipt");
            }
            else
            {
                docc.Description = Lan.g(this,"Statement");
            } 
        } 
        docc.DateCreated = stmt.DateSent;
        Documents.update(docc);
        stmt.DocNum = docc.DocNum;
        //this signals the calling class that the pdf was created successfully.
        Statements.attachDoc(stmt.StatementNum,docc.DocNum);
    }

    /**
    * Prints one statement to a specified printer which is passed in as a PrintDocument field.  Used when printer selection happens before a batch
    */
    public void printStatement(Statement stmt, PrintDocument pd, DataSet dataSet, Family fam, Patient pat) throws Exception {
        printStatement(stmt,false,pd,dataSet,fam,pat);
    }

    /**
    * Prints one statement.  Does not generate pdf or print from existing pdf.
    */
    public void printStatement(Statement stmt, boolean previewOnly, DataSet dataSet, Family fam, Patient pat) throws Exception {
        PrintDocument pd = new PrintDocument();
        if (previewOnly)
        {
        }
        else
        {
            //Don't want to have print dialogue come up when not printing.
            if (!PrinterL.setPrinter(pd,PrintSituation.Statement,pat.PatNum,"Statement from " + stmt.DateTStamp.ToShortDateString() + " printed"))
            {
                return ;
            }
             
        } 
        printStatement(stmt,previewOnly,pd,dataSet,fam,pat);
    }

    /**
    * Prints one statement.  Does not generate pdf or print from existing pdf.
    */
    public void printStatement(Statement stmt, boolean previewOnly, PrintDocument pd, DataSet dataSet, Family fam, Patient pat) throws Exception {
        Stmt = stmt;
        //dataSet=AccountModuleL.GetStatement(stmt.PatNum,stmt.SinglePatient,stmt.DateRangeFrom,stmt.DateRangeTo,
        //	stmt.Intermingled);
        pd.DefaultPageSettings.Margins = new Margins(40, 40, 40, 60);
        if (CultureInfo.CurrentCulture.Name.EndsWith("CH"))
        {
            //CH is for switzerland. eg de-CH
            //leave a big margin on the bottom for the routing slip
            pd.DefaultPageSettings.Margins = new Margins(40, 40, 40, 440);
        }
         
        //4.4" from bottom
        //pd.OriginAtMargins=true;
        if (pd.DefaultPageSettings.PaperSize.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        MigraDoc.DocumentObjectModel.Document doc = createDocument(pd,fam,pat,dataSet);
        MigraDoc.Rendering.Printing.MigraDocPrintDocument printdoc = new MigraDoc.Rendering.Printing.MigraDocPrintDocument();
        MigraDoc.Rendering.DocumentRenderer renderer = new MigraDoc.Rendering.DocumentRenderer(doc);
        renderer.PrepareDocument();
        totalPages = renderer.FormattedDocument.PageCount;
        labelTotPages.Text = "1 / " + totalPages.ToString();
        printdoc.Renderer = renderer;
        printdoc.PrinterSettings = pd.PrinterSettings;
        if (previewOnly)
        {
            printPreviewControl2.Document = printdoc;
        }
        else
        {
            try
            {
                printdoc.Print();
            }
            catch (Exception __dummyCatchVar1)
            {
                MessageBox.Show(Lan.g(this,"Printer not available"));
            }
        
        } 
    }

    /**
    * Supply pd so that we know the paper size and margins.
    */
    private MigraDoc.DocumentObjectModel.Document createDocument(PrintDocument pd, Family fam, Patient pat, DataSet dataSet) throws Exception {
        MigraDoc.DocumentObjectModel.Document doc = new MigraDoc.DocumentObjectModel.Document();
        if (Plugins.hookMethod(this,"FormRpStatement.CreateDocument",doc,pd,fam,pat,dataSet,Stmt))
        {
            return doc;
        }
         
        doc.DefaultPageSetup.PageWidth = Unit.FromInch((double)pd.DefaultPageSettings.PaperSize.Width / 100);
        doc.DefaultPageSetup.PageHeight = Unit.FromInch((double)pd.DefaultPageSettings.PaperSize.Height / 100);
        doc.DefaultPageSetup.TopMargin = Unit.FromInch((double)pd.DefaultPageSettings.Margins.Top / 100);
        doc.DefaultPageSetup.LeftMargin = Unit.FromInch((double)pd.DefaultPageSettings.Margins.Left / 100);
        doc.DefaultPageSetup.RightMargin = Unit.FromInch((double)pd.DefaultPageSettings.Margins.Right / 100);
        doc.DefaultPageSetup.BottomMargin = Unit.FromInch((double)pd.DefaultPageSettings.Margins.Bottom / 100);
        MigraDoc.DocumentObjectModel.Section section = doc.AddSection();
        //so that Swiss will have different footer for each patient.
        String text = new String();
        MigraDoc.DocumentObjectModel.Font font = new MigraDoc.DocumentObjectModel.Font();
        //GetPatGuar(PatNums[famIndex][0]);
        //Family fam=Patients.GetFamily(Stmt.PatNum);
        Patient PatGuar = fam.ListPats[0];
        //.Clone();
        //Patient pat=fam.GetPatient(Stmt.PatNum);
        DataTable tableMisc = dataSet.Tables["misc"];
        //HEADING------------------------------------------------------------------------------
        Paragraph par = section.AddParagraph();
        ParagraphFormat parformat = new ParagraphFormat();
        parformat.Alignment = ParagraphAlignment.Center;
        par.Format = parformat;
        font = MigraDocHelper.CreateFont(14, true);
        if (Stmt.IsInvoice)
        {
            if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-NZ") || StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-AU"))
            {
                //New Zealand and Australia
                text = Lan.g(this,"TAX INVOICE");
            }
            else
            {
                text = Lan.g(this,"INVOICE");
                text += " #" + Stmt.StatementNum.ToString();
            } 
        }
        else //Some larger customers of OD need this to show in order to properly pay.
        if (Stmt.IsReceipt)
        {
            text = Lan.g(this,"RECEIPT");
            if (CultureInfo.CurrentCulture.Name.EndsWith("SG"))
            {
                //SG=Singapore
                text += " #" + Stmt.StatementNum.ToString();
            }
             
        }
        else
        {
            text = Lan.g(this,"STATEMENT");
        }  
        par.AddFormattedText(text, font);
        text = DateTime.Today.ToShortDateString();
        font = MigraDocHelper.CreateFont(10);
        par.AddLineBreak();
        par.AddFormattedText(text, font);
        text = Lan.g(this,"Account Number") + " ";
        if (PrefC.getBool(PrefName.StatementAccountsUseChartNumber))
        {
            text += PatGuar.ChartNumber;
        }
        else
        {
            text += PatGuar.PatNum;
        } 
        par.AddLineBreak();
        par.AddFormattedText(text, font);
        TextFrame frame = new TextFrame();
        //"COPY" for foreign countries' TAX INVOICES----------------------------------------------
        if (Stmt.IsInvoiceCopy && !StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
        {
            //We don't show this for US.
            font = MigraDocHelper.CreateFont(28, true, System.Drawing.Color.Red);
            frame = section.AddTextFrame();
            frame.RelativeVertical = RelativeVertical.Page;
            frame.RelativeHorizontal = RelativeHorizontal.Page;
            frame.MarginLeft = Unit.Zero;
            frame.MarginTop = Unit.Zero;
            frame.Top = TopPosition.Parse("0.35 in");
            frame.Left = LeftPosition.Parse("5.4 in");
            frame.Width = Unit.FromInch(3);
            par = frame.AddParagraph();
            par.Format.Font = font;
            par.AddText("COPY");
        }
         
        //Practice Address----------------------------------------------------------------------
        if (PrefC.getBool(PrefName.StatementShowReturnAddress))
        {
            font = MigraDocHelper.CreateFont(10);
            frame = section.AddTextFrame();
            frame.RelativeVertical = RelativeVertical.Page;
            frame.RelativeHorizontal = RelativeHorizontal.Page;
            frame.MarginLeft = Unit.Zero;
            frame.MarginTop = Unit.Zero;
            frame.Top = TopPosition.Parse("0.5 in");
            frame.Left = LeftPosition.Parse("0.3 in");
            frame.Width = Unit.FromInch(3);
            //if using clinics
            if (!PrefC.getBool(PrefName.EasyNoClinics) && Clinics.getList().Length > 0 && Clinics.getClinic(PatGuar.ClinicNum) != null)
            {
                //and this patient assigned to a clinic
                Clinic clinic = Clinics.getClinic(PatGuar.ClinicNum);
                par = frame.AddParagraph();
                par.Format.Font = font;
                par.AddText(clinic.Description);
                par.AddLineBreak();
                if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-AU"))
                {
                    //Australia
                    Provider defaultProv = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
                    par.AddText("ABN: " + defaultProv.NationalProvID);
                    par.AddLineBreak();
                }
                 
                if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-NZ"))
                {
                    //New Zealand
                    Provider defaultProv = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
                    par.AddText("GST: " + defaultProv.SSN);
                    par.AddLineBreak();
                }
                 
                par.AddText(clinic.Address);
                par.AddLineBreak();
                if (!StringSupport.equals(clinic.Address2, ""))
                {
                    par.AddText(clinic.Address2);
                    par.AddLineBreak();
                }
                 
                if (CultureInfo.CurrentCulture.Name.EndsWith("CH"))
                {
                    //CH is for switzerland. eg de-CH
                    par.AddText(clinic.Zip + " " + clinic.City);
                }
                else if (CultureInfo.CurrentCulture.Name.EndsWith("SG"))
                {
                    //SG=Singapore
                    par.AddText(clinic.City + " " + clinic.Zip);
                }
                else
                {
                    par.AddText(clinic.City + ", " + clinic.State + " " + clinic.Zip);
                }  
                par.AddLineBreak();
                text = clinic.Phone;
                if (text.Length == 10)
                {
                    text = "(" + text.Substring(0, 3) + ")" + text.Substring(3, 3) + "-" + text.Substring(6);
                }
                 
                par.AddText(text);
                par.AddLineBreak();
            }
            else
            {
                par = frame.AddParagraph();
                par.Format.Font = font;
                par.AddText(PrefC.getString(PrefName.PracticeTitle));
                par.AddLineBreak();
                if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-AU"))
                {
                    //Australia
                    Provider defaultProv = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
                    par.AddText("ABN: " + defaultProv.NationalProvID);
                    par.AddLineBreak();
                }
                 
                if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-NZ"))
                {
                    //New Zealand
                    Provider defaultProv = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
                    par.AddText("GST: " + defaultProv.SSN);
                    par.AddLineBreak();
                }
                 
                par.AddText(PrefC.getString(PrefName.PracticeAddress));
                par.AddLineBreak();
                if (!StringSupport.equals(PrefC.getString(PrefName.PracticeAddress2), ""))
                {
                    par.AddText(PrefC.getString(PrefName.PracticeAddress2));
                    par.AddLineBreak();
                }
                 
                if (CultureInfo.CurrentCulture.Name.EndsWith("CH"))
                {
                    //CH is for switzerland. eg de-CH
                    par.AddText(PrefC.getString(PrefName.PracticeZip) + " " + PrefC.getString(PrefName.PracticeCity));
                }
                else if (CultureInfo.CurrentCulture.Name.EndsWith("SG"))
                {
                    //SG=Singapore
                    par.AddText(PrefC.getString(PrefName.PracticeCity) + " " + PrefC.getString(PrefName.PracticeZip));
                }
                else
                {
                    par.AddText(PrefC.getString(PrefName.PracticeCity) + ", " + PrefC.getString(PrefName.PracticeST) + " " + PrefC.getString(PrefName.PracticeZip));
                }  
                par.AddLineBreak();
                text = PrefC.getString(PrefName.PracticePhone);
                if (text.Length == 10)
                {
                    text = "(" + text.Substring(0, 3) + ")" + text.Substring(3, 3) + "-" + text.Substring(6);
                }
                 
                par.AddText(text);
                par.AddLineBreak();
            } 
        }
         
        //AMOUNT ENCLOSED------------------------------------------------------------------------------------------------------
        Table table = new Table();
        Column col = new Column();
        Row row = new Row();
        Cell cell = new Cell();
        frame = MigraDocHelper.CreateContainer(section, 450, 110, 330, 29);
        if (!Stmt.HidePayment)
        {
            table = MigraDocHelper.DrawTable(frame, 0, 0, 29);
            col = table.AddColumn(Unit.FromInch(1.1));
            col = table.AddColumn(Unit.FromInch(1.1));
            col = table.AddColumn(Unit.FromInch(1.1));
            row = table.AddRow();
            row.Format.Alignment = ParagraphAlignment.Center;
            row.Borders.Color = Colors.Black;
            row.Shading.Color = Colors.LightGray;
            row.TopPadding = Unit.FromInch(0);
            row.BottomPadding = Unit.FromInch(0);
            font = MigraDocHelper.CreateFont(8, true);
            cell = row.Cells[0];
            par = cell.AddParagraph();
            par.AddFormattedText(Lan.g(this,"Amount Due"), font);
            cell = row.Cells[1];
            par = cell.AddParagraph();
            par.AddFormattedText(Lan.g(this,"Date Due"), font);
            cell = row.Cells[2];
            par = cell.AddParagraph();
            par.AddFormattedText(Lan.g(this,"Amount Enclosed"), font);
            row = table.AddRow();
            row.Format.Alignment = ParagraphAlignment.Center;
            row.Borders.Left.Color = Colors.Gray;
            row.Borders.Bottom.Color = Colors.Gray;
            row.Borders.Right.Color = Colors.Gray;
            font = MigraDocHelper.CreateFont(9);
            double balTotal = PatGuar.BalTotal;
            if (!PrefC.getBool(PrefName.BalancesDontSubtractIns))
            {
                //this is typical
                balTotal -= PatGuar.InsEst;
            }
             
            for (int m = 0;m < tableMisc.Rows.Count;m++)
            {
                if (StringSupport.equals(tableMisc.Rows[m]["descript"].ToString(), "payPlanDue"))
                {
                    balTotal += PIn.Double(tableMisc.Rows[m]["value"].ToString());
                }
                 
            }
            //payPlanDue;//PatGuar.PayPlanDue;
            InstallmentPlan installPlan = InstallmentPlans.getOneForFam(PatGuar.PatNum);
            if (installPlan != null)
            {
                //show lesser of normal total balance or the monthly payment amount.
                if (installPlan.MonthlyPayment < balTotal)
                {
                    text = installPlan.MonthlyPayment.ToString("F");
                }
                else
                {
                    text = balTotal.ToString("F");
                } 
            }
            else
            {
                //no installmentplan
                text = balTotal.ToString("F");
            } 
            cell = row.Cells[0];
            par = cell.AddParagraph();
            par.AddFormattedText(text, font);
            if (PrefC.getLong(PrefName.StatementsCalcDueDate) == -1)
            {
                text = Lan.g(this,"Upon Receipt");
            }
            else
            {
                text = DateTime.Today.AddDays(PrefC.getLong(PrefName.StatementsCalcDueDate)).ToShortDateString();
            } 
            cell = row.Cells[1];
            par = cell.AddParagraph();
            par.AddFormattedText(text, font);
        }
         
        //Credit Card Info--------------------------------------------------------------------------------------------------------
        if (!Stmt.HidePayment)
        {
            if (PrefC.getBool(PrefName.StatementShowCreditCard))
            {
                float yPos = 60;
                font = MigraDocHelper.CreateFont(7, true);
                text = Lan.g(this,"CREDIT CARD TYPE");
                MigraDocHelper.DrawString(frame, text, font, 0, yPos);
                float rowHeight = 26;
                System.Drawing.Font wfont = new System.Drawing.Font("Arial", 7, FontStyle.Bold);
                Graphics g = this.CreateGraphics();
                //just to measure strings
                MigraDocHelper.DrawLine(frame, System.Drawing.Color.Black, g.MeasureString(text, wfont).Width, yPos + wfont.GetHeight(g), 326, yPos + wfont.GetHeight(g));
                yPos += rowHeight;
                text = Lan.g(this,"#");
                MigraDocHelper.DrawString(frame, text, font, 0, yPos);
                MigraDocHelper.DrawLine(frame, System.Drawing.Color.Black, g.MeasureString(text, wfont).Width, yPos + wfont.GetHeight(g), 326, yPos + wfont.GetHeight(g));
                yPos += rowHeight;
                text = Lan.g(this,"3 DIGIT CSV");
                MigraDocHelper.DrawString(frame, text, font, 0, yPos);
                MigraDocHelper.DrawLine(frame, System.Drawing.Color.Black, g.MeasureString(text, wfont).Width, yPos + wfont.GetHeight(g), 326, yPos + wfont.GetHeight(g));
                yPos += rowHeight;
                text = Lan.g(this,"EXPIRES");
                MigraDocHelper.DrawString(frame, text, font, 0, yPos);
                MigraDocHelper.DrawLine(frame, System.Drawing.Color.Black, g.MeasureString(text, wfont).Width, yPos + wfont.GetHeight(g), 326, yPos + wfont.GetHeight(g));
                yPos += rowHeight;
                text = Lan.g(this,"AMOUNT APPROVED");
                MigraDocHelper.DrawString(frame, text, font, 0, yPos);
                MigraDocHelper.DrawLine(frame, System.Drawing.Color.Black, g.MeasureString(text, wfont).Width, yPos + wfont.GetHeight(g), 326, yPos + wfont.GetHeight(g));
                yPos += rowHeight;
                text = Lan.g(this,"NAME");
                MigraDocHelper.DrawString(frame, text, font, 0, yPos);
                MigraDocHelper.DrawLine(frame, System.Drawing.Color.Black, g.MeasureString(text, wfont).Width, yPos + wfont.GetHeight(g), 326, yPos + wfont.GetHeight(g));
                yPos += rowHeight;
                text = Lan.g(this,"SIGNATURE");
                MigraDocHelper.DrawString(frame, text, font, 0, yPos);
                MigraDocHelper.DrawLine(frame, System.Drawing.Color.Black, g.MeasureString(text, wfont).Width, yPos + wfont.GetHeight(g), 326, yPos + wfont.GetHeight(g));
                yPos -= rowHeight;
                text = Lan.g(this,"(As it appears on card)");
                wfont = new System.Drawing.Font("Arial", 5);
                font = MigraDocHelper.CreateFont(5);
                MigraDocHelper.DrawString(frame, text, font, 625 - g.MeasureString(text, wfont).Width / 2 + 5, yPos + 13);
            }
             
        }
         
        //Patient's Billing Address---------------------------------------------------------------------------------------------
        font = MigraDocHelper.CreateFont(11);
        frame = MigraDocHelper.CreateContainer(section, 62.5f + 12.5f, 225 + 1, 300, 200);
        par = frame.AddParagraph();
        par.Format.Font = font;
        if (Stmt.SinglePatient)
        {
            par.AddText(fam.getNameInFamFLnoPref(Stmt.PatNum));
        }
        else
        {
            par.AddText(PatGuar.getNameFLFormal());
        } 
        par.AddLineBreak();
        par.AddText(PatGuar.Address);
        par.AddLineBreak();
        if (!StringSupport.equals(PatGuar.Address2, ""))
        {
            par.AddText(PatGuar.Address2);
            par.AddLineBreak();
        }
         
        if (CultureInfo.CurrentCulture.Name.EndsWith("CH"))
        {
            //CH is for switzerland. eg de-CH
            par.AddText(PatGuar.Zip + " " + PatGuar.City);
        }
        else if (CultureInfo.CurrentCulture.Name.EndsWith("SG"))
        {
            //SG=Singapore
            par.AddText(PatGuar.City + " " + PatGuar.Zip);
        }
        else
        {
            par.AddText(PatGuar.City + ", " + PatGuar.State + " " + PatGuar.Zip);
        }  
        if (!StringSupport.equals(PatGuar.Country, ""))
        {
            par.AddLineBreak();
            par.AddText(PatGuar.Country);
        }
         
        //perforated line------------------------------------------------------------------------------------------------------
        //yPos=350;//3.62 inches from top, 1/3 page down
        frame = MigraDocHelper.CreateContainer(section, 0, 350, 850, 30);
        if (!Stmt.HidePayment)
        {
            MigraDocHelper.DrawLine(frame, System.Drawing.Color.LightGray, 0, 0, 850, 0);
            text = Lan.g(this,"PLEASE DETACH AND RETURN THE UPPER PORTION WITH YOUR PAYMENT");
            font = MigraDocHelper.CreateFont(6, true, System.Drawing.Color.Gray);
            par = frame.AddParagraph();
            par.Format.Alignment = ParagraphAlignment.Center;
            par.Format.Font = font;
            par.AddText(text);
        }
         
        //Australian Provider Legend
        int legendOffset = 0;
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-AU"))
        {
            //English (Australia)
            Providers.refreshCache();
            legendOffset = 25 + 15 * (1 + ProviderC.getListShort().Count);
            MigraDocHelper.InsertSpacer(section, legendOffset);
            frame = MigraDocHelper.CreateContainer(section, 45, 390, 250, legendOffset);
            par = frame.AddParagraph();
            par.Format.Font = MigraDocHelper.CreateFont(8, true);
            par.AddLineBreak();
            par.AddText("PROVIDERS:");
            par = frame.AddParagraph();
            par.Format.Font = MigraDocHelper.CreateFont(8, false);
            for (int i = 0;i < ProviderC.getListShort().Count;i++)
            {
                //All non-hidden providers are added to the legend.
                Provider prov = ProviderC.getListShort()[i];
                String suffix = "";
                if (!StringSupport.equals(prov.Suffix.Trim(), ""))
                {
                    suffix = ", " + prov.Suffix.Trim();
                }
                 
                par.AddText(prov.Abbr + " - " + prov.FName + " " + prov.LName + suffix + " - " + prov.MedicaidID);
                par.AddLineBreak();
            }
            par.AddLineBreak();
        }
         
        //Aging-----------------------------------------------------------------------------------
        MigraDocHelper.InsertSpacer(section, 275);
        frame = MigraDocHelper.CreateContainer(section, 55, 390 + legendOffset, 250, 29);
        if (!Stmt.HidePayment)
        {
            table = MigraDocHelper.DrawTable(frame, 0, 0, 29);
            col = table.AddColumn(Unit.FromInch(1.1));
            col = table.AddColumn(Unit.FromInch(1.1));
            col = table.AddColumn(Unit.FromInch(1.1));
            col = table.AddColumn(Unit.FromInch(1.1));
            row = table.AddRow();
            row.Format.Alignment = ParagraphAlignment.Center;
            row.Borders.Color = Colors.Black;
            row.Shading.Color = Colors.LightGray;
            row.TopPadding = Unit.FromInch(0);
            row.BottomPadding = Unit.FromInch(0);
            font = MigraDocHelper.CreateFont(8, true);
            cell = row.Cells[0];
            par = cell.AddParagraph();
            par.AddFormattedText(Lan.g(this,"0-30"), font);
            cell = row.Cells[1];
            par = cell.AddParagraph();
            par.AddFormattedText(Lan.g(this,"31-60"), font);
            cell = row.Cells[2];
            par = cell.AddParagraph();
            par.AddFormattedText(Lan.g(this,"61-90"), font);
            cell = row.Cells[3];
            par = cell.AddParagraph();
            par.AddFormattedText(Lan.g(this,"over 90"), font);
            row = table.AddRow();
            row.Format.Alignment = ParagraphAlignment.Center;
            row.Borders.Left.Color = Colors.Gray;
            row.Borders.Bottom.Color = Colors.Gray;
            row.Borders.Right.Color = Colors.Gray;
            font = MigraDocHelper.CreateFont(9);
            text = PatGuar.Bal_0_30.ToString("F");
            cell = row.Cells[0];
            par = cell.AddParagraph();
            par.AddFormattedText(text, font);
            text = PatGuar.Bal_31_60.ToString("F");
            cell = row.Cells[1];
            par = cell.AddParagraph();
            par.AddFormattedText(text, font);
            text = PatGuar.Bal_61_90.ToString("F");
            cell = row.Cells[2];
            par = cell.AddParagraph();
            par.AddFormattedText(text, font);
            text = PatGuar.BalOver90.ToString("F");
            cell = row.Cells[3];
            par = cell.AddParagraph();
            par.AddFormattedText(text, font);
        }
         
        /*
        			ODGridColumn gcol;
        			ODGridRow grow;
        			if(!Stmt.HidePayment) {
        				ODGrid gridAging=new ODGrid();
        				this.Controls.Add(gridAging);
        				gridAging.BeginUpdate();
        				gridAging.Columns.Clear();
        				gcol=new ODGridColumn(Lan.g(this,"0-30"),70,HorizontalAlignment.Center);
        				gridAging.Columns.Add(gcol);
        				gcol=new ODGridColumn(Lan.g(this,"31-60"),70,HorizontalAlignment.Center);
        				gridAging.Columns.Add(gcol);
        				gcol=new ODGridColumn(Lan.g(this,"61-90"),70,HorizontalAlignment.Center);
        				gridAging.Columns.Add(gcol);
        				gcol=new ODGridColumn(Lan.g(this,"over 90"),70,HorizontalAlignment.Center);
        				gridAging.Columns.Add(gcol);
        				if(PrefC.GetBool(PrefName.BalancesDontSubtractIns")) {//less common
        					gcol=new ODGridColumn(Lan.g(this,"Balance"),70,HorizontalAlignment.Center);
        					gridAging.Columns.Add(gcol);
        					gcol=new ODGridColumn(Lan.g(this,"InsPending"),70,HorizontalAlignment.Center);
        					gridAging.Columns.Add(gcol);
        					gcol=new ODGridColumn(Lan.g(this,"AfterIns"),70,HorizontalAlignment.Center);
        					gridAging.Columns.Add(gcol);
        				}
        				else{//more common
        					gcol=new ODGridColumn(Lan.g(this,"Total"),70,HorizontalAlignment.Center);
        					gridAging.Columns.Add(gcol);
        					gcol=new ODGridColumn(Lan.g(this,"- InsEst"),70,HorizontalAlignment.Center);
        					gridAging.Columns.Add(gcol);
        					gcol=new ODGridColumn(Lan.g(this,"= Balance"),70,HorizontalAlignment.Center);
        					gridAging.Columns.Add(gcol);
        				}
        				gridAging.Rows.Clear();
        				//Annual max--------------------------
        				grow=new ODGridRow();
        				grow.Cells.Add(PatGuar.Bal_0_30.ToString("F"));
        				grow.Cells.Add(PatGuar.Bal_31_60.ToString("F"));
        				grow.Cells.Add(PatGuar.Bal_61_90.ToString("F"));
        				grow.Cells.Add(PatGuar.BalOver90.ToString("F"));
        				grow.Cells.Add(PatGuar.BalTotal.ToString("F"));
        				grow.Cells.Add(PatGuar.InsEst.ToString("F"));
        				grow.Cells.Add((PatGuar.BalTotal-PatGuar.InsEst).ToString("F"));
        				gridAging.Rows.Add(grow);
        				gridAging.EndUpdate();
        				MigraDocHelper.DrawGrid(section,gridAging);
        				gridAging.Dispose();
        			*/
        //Floating Balance, Ins info-------------------------------------------------------------------
        frame = MigraDocHelper.CreateContainer(section, 460, 380 + legendOffset, 250, 200);
        //table=MigraDocHelper.DrawTable(frame,0,0,90);
        par = frame.AddParagraph();
        parformat = new ParagraphFormat();
        parformat.Alignment = ParagraphAlignment.Right;
        par.Format = parformat;
        font = MigraDocHelper.CreateFont(10, false);
        MigraDoc.DocumentObjectModel.Font fontBold = MigraDocHelper.CreateFont(10, true);
        if (Stmt.IsInvoice)
        {
            text = Lan.g(this,"Procedures:");
            par.AddFormattedText(text, font);
            par.AddLineBreak();
            text = Lan.g(this,"Adjustments:");
            par.AddFormattedText(text, font);
            par.AddLineBreak();
            text = Lan.g(this,"Total:");
            par.AddFormattedText(text, font);
            par.AddLineBreak();
        }
        else if (PrefC.getBool(PrefName.BalancesDontSubtractIns))
        {
            text = Lan.g(this,"Balance:");
            par.AddFormattedText(text, fontBold);
        }
        else
        {
            //par.AddLineBreak();
            //text = Lan.g(this, "Ins Pending:");
            //par.AddFormattedText(text, font);
            //par.AddLineBreak();
            //text = Lan.g(this, "After Ins:");
            //par.AddFormattedText(text, font);
            //par.AddLineBreak();
            //this is more common
            if (PrefC.getBool(PrefName.FuchsOptionsOn))
            {
                text = Lan.g(this,"Balance:");
                par.AddFormattedText(text, font);
                par.AddLineBreak();
                text = Lan.g(this,"-Ins Estimate:");
                par.AddFormattedText(text, font);
                par.AddLineBreak();
                text = Lan.g(this,"=Owed Now:");
                par.AddFormattedText(text, fontBold);
                par.AddLineBreak();
            }
            else
            {
                text = Lan.g(this,"Total:");
                par.AddFormattedText(text, font);
                par.AddLineBreak();
                text = Lan.g(this,"-Ins Estimate:");
                par.AddFormattedText(text, font);
                par.AddLineBreak();
                text = Lan.g(this,"=Balance:");
                par.AddFormattedText(text, fontBold);
                par.AddLineBreak();
            } 
        }  
        frame = MigraDocHelper.CreateContainer(section, 730, 380 + legendOffset, 100, 200);
        //table=MigraDocHelper.DrawTable(frame,0,0,90);
        par = frame.AddParagraph();
        parformat = new ParagraphFormat();
        parformat.Alignment = ParagraphAlignment.Left;
        par.Format = parformat;
        font = MigraDocHelper.CreateFont(10, false);
        //numbers:
        if (Stmt.IsInvoice)
        {
            double adjAmt = 0;
            double procAmt = 0;
            DataTable tableAcct = new DataTable();
            String tableName = new String();
            for (int i = 0;i < dataSet.Tables.Count;i++)
            {
                tableAcct = dataSet.Tables[i];
                tableName = tableAcct.TableName;
                if (!tableName.StartsWith("account"))
                {
                    continue;
                }
                 
                for (int p = 0;p < tableAcct.Rows.Count;p++)
                {
                    if (!StringSupport.equals(tableAcct.Rows[p]["AdjNum"].ToString(), "0"))
                    {
                        adjAmt -= PIn.Double(tableAcct.Rows[p]["creditsDouble"].ToString());
                        adjAmt += PIn.Double(tableAcct.Rows[p]["chargesDouble"].ToString());
                    }
                    else
                    {
                        //must be a procedure
                        procAmt += PIn.Double(tableAcct.Rows[p]["chargesDouble"].ToString());
                    } 
                }
            }
            text = procAmt.ToString("c");
            par.AddFormattedText(text, font);
            par.AddLineBreak();
            text = adjAmt.ToString("c");
            par.AddFormattedText(text, font);
            par.AddLineBreak();
            text = (procAmt + adjAmt).ToString("c");
            par.AddFormattedText(text, fontBold);
        }
        else if (PrefC.getBool(PrefName.BalancesDontSubtractIns))
        {
            if (Stmt.SinglePatient)
            {
                //Show the current patient's balance without subtracting insurance estimates.
                text = pat.EstBalance.ToString("c");
                par.AddFormattedText(text, font);
            }
            else
            {
                //Show the current family's balance without subtracting insurance estimates.
                text = PatGuar.BalTotal.ToString("c");
                par.AddFormattedText(text, fontBold);
            } 
        }
        else
        {
            //more common
            if (Stmt.SinglePatient)
            {
                double patInsEst = 0;
                for (int m = 0;m < tableMisc.Rows.Count;m++)
                {
                    if (StringSupport.equals(tableMisc.Rows[m]["descript"].ToString(), "patInsEst"))
                    {
                        patInsEst = PIn.Double(tableMisc.Rows[m]["value"].ToString());
                    }
                     
                }
                double patBal = pat.EstBalance - patInsEst;
                text = pat.EstBalance.ToString("c");
                par.AddFormattedText(text, font);
                par.AddLineBreak();
                text = patInsEst.ToString("c");
                par.AddFormattedText(text, font);
                par.AddLineBreak();
                text = patBal.ToString("c");
                par.AddFormattedText(text, fontBold);
            }
            else
            {
                text = PatGuar.BalTotal.ToString("c");
                par.AddFormattedText(text, font);
                par.AddLineBreak();
                text = PatGuar.InsEst.ToString("c");
                par.AddFormattedText(text, font);
                par.AddLineBreak();
                text = (PatGuar.BalTotal - PatGuar.InsEst).ToString("c");
                par.AddFormattedText(text, fontBold);
                par.AddLineBreak();
            } 
        }  
        MigraDocHelper.InsertSpacer(section, 80);
        //Bold note-------------------------------------------------------------------------------
        if (!StringSupport.equals(Stmt.NoteBold, ""))
        {
            MigraDocHelper.InsertSpacer(section, 7);
            font = MigraDocHelper.CreateFont(10, true, System.Drawing.Color.DarkRed);
            par = section.AddParagraph();
            par.Format.Font = font;
            par.AddText(Stmt.NoteBold);
            MigraDocHelper.InsertSpacer(section, 8);
        }
         
        //Payment plan grid definition---------------------------------------------------------------------------------
        ODGridColumn gcol;
        OpenDental.UI.ODGridRow grow;
        OpenDental.UI.ODGrid gridPP = new OpenDental.UI.ODGrid();
        this.Controls.Add(gridPP);
        gridPP.beginUpdate();
        gridPP.getColumns().Clear();
        gcol = new ODGridColumn(Lan.g(this,"Date"),73);
        gridPP.getColumns().add(gcol);
        gcol = new ODGridColumn(Lan.g(this,"Description"),270);
        gridPP.getColumns().add(gcol);
        gcol = new ODGridColumn(Lan.g(this,"Charges"), 60, HorizontalAlignment.Right);
        gridPP.getColumns().add(gcol);
        gcol = new ODGridColumn(Lan.g(this,"Credits"), 60, HorizontalAlignment.Right);
        gridPP.getColumns().add(gcol);
        gcol = new ODGridColumn(Lan.g(this,"Balance"), 60, HorizontalAlignment.Right);
        gridPP.getColumns().add(gcol);
        gridPP.Width = gridPP.getWidthAllColumns() + 20;
        gridPP.endUpdate();
        //Payment plan grid.  There will be only one, if any-----------------------------------------------------------------
        DataTable tablePP = dataSet.Tables["payplan"];
        OpenDental.UI.ODGridCell gcell;
        if (tablePP.Rows.Count > 0)
        {
            //MigraDocHelper.InsertSpacer(section,5);
            par = section.AddParagraph();
            par.Format.Font = MigraDocHelper.CreateFont(10, true);
            par.Format.Alignment = ParagraphAlignment.Center;
            //par.Format.SpaceBefore=Unit.FromInch(.05);
            //par.Format.SpaceAfter=Unit.FromInch(.05);
            par.AddText(Lan.g(this,"Payment Plans"));
            MigraDocHelper.InsertSpacer(section, 2);
            gridPP.beginUpdate();
            gridPP.getRows().Clear();
            for (int p = 0;p < tablePP.Rows.Count;p++)
            {
                grow = new OpenDental.UI.ODGridRow();
                grow.getCells().Add(tablePP.Rows[p]["date"].ToString());
                grow.getCells().Add(tablePP.Rows[p]["description"].ToString());
                grow.getCells().Add(tablePP.Rows[p]["charges"].ToString());
                grow.getCells().Add(tablePP.Rows[p]["credits"].ToString());
                gcell = new OpenDental.UI.ODGridCell(tablePP.Rows[p]["balance"].ToString());
                if (p == tablePP.Rows.Count - 1)
                {
                    gcell.setBold(YN.Yes);
                }
                else if (StringSupport.equals(tablePP.Rows[p + 1]["balance"].ToString(), ""))
                {
                    //if next row balance is blank.
                    gcell.setBold(YN.Yes);
                }
                  
                grow.getCells().Add(gcell);
                gridPP.getRows().add(grow);
            }
            gridPP.endUpdate();
            MigraDocHelper.DrawGrid(section, gridPP);
            MigraDocHelper.InsertSpacer(section, 2);
            par = section.AddParagraph();
            par.Format.Font = MigraDocHelper.CreateFont(10, true);
            par.Format.Alignment = ParagraphAlignment.Right;
            par.Format.RightIndent = Unit.FromInch(0.25);
            double payPlanDue = 0;
            for (int m = 0;m < tableMisc.Rows.Count;m++)
            {
                if (StringSupport.equals(tableMisc.Rows[m]["descript"].ToString(), "payPlanDue"))
                {
                    payPlanDue = PIn.Double(tableMisc.Rows[m]["value"].ToString());
                }
                 
            }
            par.AddText(Lan.g(this,"Payment Plan Amount Due: ") + payPlanDue.ToString("c"));
            //PatGuar.PayPlanDue.ToString("c"));
            MigraDocHelper.InsertSpacer(section, 10);
        }
         
        //Body Table definition--------------------------------------------------------------------------------------------------------
        OpenDental.UI.ODGrid gridPat = new OpenDental.UI.ODGrid();
        this.Controls.Add(gridPat);
        gridPat.beginUpdate();
        gridPat.getColumns().Clear();
        gcol = new ODGridColumn(Lan.g(this,"Date"),73);
        gridPat.getColumns().add(gcol);
        gcol = new ODGridColumn(Lan.g(this,"Patient"),100);
        gridPat.getColumns().add(gcol);
        //prov
        gcol = new ODGridColumn(Lan.g(this,"Code"),45);
        gridPat.getColumns().add(gcol);
        gcol = new ODGridColumn(Lan.g(this,"Tooth"),42);
        gridPat.getColumns().add(gcol);
        gcol = new ODGridColumn(Lan.g(this,"Description"),270);
        gridPat.getColumns().add(gcol);
        gcol = new ODGridColumn(Lan.g(this,"Charges"), 60, HorizontalAlignment.Right);
        gridPat.getColumns().add(gcol);
        gcol = new ODGridColumn(Lan.g(this,"Credits"), 60, HorizontalAlignment.Right);
        gridPat.getColumns().add(gcol);
        if (Stmt.IsInvoice)
        {
            gcol = new ODGridColumn(Lan.g(this,"Total"), 60, HorizontalAlignment.Right);
            gridPat.getColumns().add(gcol);
        }
        else
        {
            gcol = new ODGridColumn(Lan.g(this,"Balance"), 60, HorizontalAlignment.Right);
            gridPat.getColumns().add(gcol);
        } 
        gridPat.Width = gridPat.getWidthAllColumns() + 20;
        gridPat.endUpdate();
        //Loop through each table.  Could be one intermingled, or one for each patient-----------------------------------------
        DataTable tableAccount = new DataTable();
        String tablename = new String();
        long patnum = new long();
        for (int i = 0;i < dataSet.Tables.Count;i++)
        {
            tableAccount = dataSet.Tables[i];
            tablename = tableAccount.TableName;
            if (!tablename.StartsWith("account"))
            {
                continue;
            }
             
            par = section.AddParagraph();
            par.Format.Font = MigraDocHelper.CreateFont(10, true);
            par.Format.SpaceBefore = Unit.FromInch(.05);
            par.Format.SpaceAfter = Unit.FromInch(.05);
            patnum = 0;
            if (!StringSupport.equals(tablename, "account"))
            {
                //account123 etc.
                patnum = PIn.Long(tablename.Substring(7));
            }
             
            if (patnum != 0)
            {
                par.AddText(fam.getNameInFamFLnoPref(patnum));
            }
             
            //if(FamilyStatementDataList[famIndex].PatAboutList[i].ApptDescript!=""){
            //	par=section.AddParagraph();
            //	par.Format.Font=MigraDocHelper.CreateFont(9);//same as body font
            //	par.AddText(FamilyStatementDataList[famIndex].PatAboutList[i].ApptDescript);
            //}
            gridPat.beginUpdate();
            gridPat.getRows().Clear();
            for (int p = 0;p < tableAccount.Rows.Count;p++)
            {
                //lineData=FamilyStatementDataList[famIndex].PatDataList[i].PatData;
                if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                {
                    //Canadian. en-CA or fr-CA
                    if (Stmt.IsReceipt)
                    {
                        if (!StringSupport.equals(tableAccount.Rows[p]["StatementNum"].ToString(), "0"))
                        {
                            continue;
                        }
                         
                        //Hide statement rows for Canadian receipts.
                        if (!StringSupport.equals(tableAccount.Rows[p]["ClaimNum"].ToString(), "0"))
                        {
                            continue;
                        }
                         
                    }
                     
                }
                 
                //Hide claim rows and claim payment rows for Canadian receipts.
                if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
                {
                    if (Stmt.IsReceipt)
                    {
                        if (StringSupport.equals(tableAccount.Rows[p]["PayNum"].ToString(), "0"))
                        {
                            continue;
                        }
                         
                    }
                     
                }
                 
                //Hide everything except patient payments
                //js Some additional features would be nice for receipts, such as hiding the bal column, the aging, and the amount due sections.
                grow = new OpenDental.UI.ODGridRow();
                grow.getCells().Add(tableAccount.Rows[p]["date"].ToString());
                grow.getCells().Add(tableAccount.Rows[p]["patient"].ToString());
                if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                {
                    //Canadian. en-CA or fr-CA
                    if (Stmt.IsReceipt)
                    {
                        grow.getCells().add("");
                        //Code: blank in Canada normally because this information is used on taxes and is considered a security concern.
                        grow.getCells().add("");
                    }
                    else
                    {
                        //Tooth: blank in Canada normally because this information is used on taxes and is considered a security concern.
                        grow.getCells().Add(tableAccount.Rows[p]["ProcCode"].ToString());
                        grow.getCells().Add(tableAccount.Rows[p]["tth"].ToString());
                    } 
                }
                else
                {
                    grow.getCells().Add(tableAccount.Rows[p]["ProcCode"].ToString());
                    grow.getCells().Add(tableAccount.Rows[p]["tth"].ToString());
                } 
                if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-AU"))
                {
                    //English (Australia)
                    if (!StringSupport.equals(tableAccount.Rows[p]["prov"].ToString().Trim(), ""))
                    {
                        grow.getCells().Add(tableAccount.Rows[p]["prov"].ToString() + " - " + tableAccount.Rows[p]["description"].ToString());
                    }
                    else
                    {
                        //No provider on this account row item, so don't put the extra leading characters.
                        grow.getCells().Add(tableAccount.Rows[p]["description"].ToString());
                    } 
                }
                else if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                {
                    //Canadian. en-CA or fr-CA
                    if (Stmt.IsReceipt)
                    {
                        if (PIn.Long(tableAccount.Rows[p]["ProcNum"].ToString()) == 0)
                        {
                            grow.getCells().Add(tableAccount.Rows[p]["description"].ToString());
                        }
                        else
                        {
                            //Only clear description for procedures.
                            grow.getCells().add("");
                        } 
                    }
                    else
                    {
                        //Description: blank in Canada normally because this information is used on taxes and is considered a security concern.
                        grow.getCells().Add(tableAccount.Rows[p]["description"].ToString());
                    } 
                }
                else
                {
                    //Assume English (United States)
                    grow.getCells().Add(tableAccount.Rows[p]["description"].ToString());
                }  
                grow.getCells().Add(tableAccount.Rows[p]["charges"].ToString());
                grow.getCells().Add(tableAccount.Rows[p]["credits"].ToString());
                grow.getCells().Add(tableAccount.Rows[p]["balance"].ToString());
                gridPat.getRows().add(grow);
            }
            gridPat.endUpdate();
            MigraDocHelper.DrawGrid(section, gridPat);
            //Total
            frame = MigraDocHelper.CreateContainer(section);
            font = MigraDocHelper.CreateFont(9, true);
            //-doc.DefaultPageSetup.LeftMargin.Inch
            //-doc.DefaultPageSetup.RightMargin.Inch)
            float totalPos = ((float)(doc.DefaultPageSetup.PageWidth.Inch)/* [UNSUPPORTED] the pointer indirection operator is not supported "*100f" */) / 2f + (float)gridPat.getWidthAllColumns() / 2f + 7;
            RectangleF rectF = new RectangleF(0, 0, totalPos, 16);
            if (patnum != 0)
            {
                //I decided this was unnecessary:
                //dataSet.Tables["patient"].Rows[fam.GetIndex(patnum)]["balance"].ToString(),
                MigraDocHelper.DrawString(frame, " ", font, rectF, ParagraphAlignment.Right);
            }
             
        }
        //MigraDocHelper.DrawString(frame,FamilyStatementDataList[famIndex].PatAboutList[i].Balance.ToString("F"),font,rectF,
        //	ParagraphAlignment.Right);
        gridPat.Dispose();
        //Future appointments---------------------------------------------------------------------------------------------
        if (!Stmt.IsReceipt && !Stmt.IsInvoice)
        {
            font = MigraDocHelper.CreateFont(9);
            DataTable tableAppt = dataSet.Tables["appts"];
            if (tableAppt.Rows.Count > 0)
            {
                par = section.AddParagraph();
                par.Format.Font = font;
                par.AddText(Lan.g(this,"Scheduled Appointments:"));
            }
             
            for (int i = 0;i < tableAppt.Rows.Count;i++)
            {
                par.AddLineBreak();
                par.AddText(tableAppt.Rows[i]["descript"].ToString());
            }
            if (tableAppt.Rows.Count > 0)
            {
                MigraDocHelper.InsertSpacer(section, 10);
            }
             
        }
         
        //Region specific static notes------------------------------------------------------------------------------------
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            if (Stmt.IsReceipt)
            {
                font = MigraDocHelper.CreateFont(9);
                par = section.AddParagraph();
                par.Format.Font = font;
                par.AddText("KEEP THIS RECEIPT FOR INCOME TAX PURPOSES");
                MigraDocHelper.InsertSpacer(section, 10);
            }
             
        }
         
        //Note------------------------------------------------------------------------------------------------------------
        font = MigraDocHelper.CreateFont(9);
        par = section.AddParagraph();
        par.Format.Font = font;
        par.AddText(Stmt.Note);
        //bold note
        if (!StringSupport.equals(Stmt.NoteBold, ""))
        {
            MigraDocHelper.InsertSpacer(section, 10);
            font = MigraDocHelper.CreateFont(10, true, System.Drawing.Color.DarkRed);
            par = section.AddParagraph();
            par.Format.Font = font;
            par.AddText(Stmt.NoteBold);
        }
         
        if (CultureInfo.CurrentCulture.Name.EndsWith("CH"))
        {
            //CH is for switzerland. eg de-CH
            //&& pagesPrinted==0)//only on the first page
            //{
            //float yred=744;//768;//660 for testing
            //Red line (just temp)
            //g.DrawLine(Pens.Red,0,yred,826,yred);
            MigraDoc.DocumentObjectModel.Font swfont = MigraDocHelper.CreateFont(10);
            //new Font(FontFamily.GenericSansSerif,10);
            //Bank Address---------------------------------------------------------
            HeaderFooter footer = section.Footers.Primary;
            footer.Format.Borders.Color = Colors.Black;
            //footer.AddParagraph(PrefC.GetString(PrefName.BankAddress"));
            frame = footer.AddTextFrame();
            frame.RelativeVertical = RelativeVertical.Line;
            frame.RelativeHorizontal = RelativeHorizontal.Page;
            frame.MarginLeft = Unit.Zero;
            frame.MarginTop = Unit.Zero;
            frame.Top = TopPosition.Parse("0 in");
            frame.Left = LeftPosition.Parse("0 in");
            frame.Width = Unit.FromInch(8.3);
            frame.Height = 300;
            //RectangleF=new RectangleF(0,0,
            MigraDocHelper.DrawString(frame, PrefC.getString(PrefName.BankAddress), swfont, 30, 30);
            MigraDocHelper.DrawString(frame, PrefC.getString(PrefName.BankAddress), swfont, 246, 30);
            //Office Name and Address----------------------------------------------
            text = PrefC.getString(PrefName.PracticeTitle) + "\r\n" + PrefC.getString(PrefName.PracticeAddress) + "\r\n";
            if (!StringSupport.equals(PrefC.getString(PrefName.PracticeAddress2), ""))
            {
                text += PrefC.getString(PrefName.PracticeAddress2) + "\r\n";
            }
             
            text += PrefC.getString(PrefName.PracticeZip) + " " + PrefC.getString(PrefName.PracticeCity);
            MigraDocHelper.DrawString(frame, text, swfont, 30, 89);
            MigraDocHelper.DrawString(frame, text, swfont, 246, 89);
            //Bank account number--------------------------------------------------
            String origBankNum = PrefC.getString(PrefName.PracticeBankNumber);
            //must be exactly 9 digits. 2+6+1.
            //the 6 digit portion might have 2 leading 0's which would not go into the dashed bank num.
            String dashedBankNum = "?";
            //examples: 01-200027-2
            //          01-4587-1  (from 010045871)
            if (origBankNum.Length == 9)
            {
                dashedBankNum = origBankNum.Substring(0, 2) + "-" + origBankNum.Substring(2, 6).TrimStart(new char[]{ '0' }) + "-" + origBankNum.Substring(8, 1);
            }
             
            swfont = MigraDocHelper.CreateFont(9, true);
            //new Font(FontFamily.GenericSansSerif,9,FontStyle.Bold);
            MigraDocHelper.DrawString(frame, dashedBankNum, swfont, 95, 169);
            MigraDocHelper.DrawString(frame, dashedBankNum, swfont, 340, 169);
            //Amount------------------------------------------------------------
            double amountdue = PatGuar.BalTotal - PatGuar.InsEst;
            text = amountdue.ToString("F2");
            text = text.Substring(0, text.Length - 3);
            swfont = MigraDocHelper.CreateFont(10);
            MigraDocHelper.DrawString(frame, text, swfont, new RectangleF(50, 205, 100, 25), ParagraphAlignment.Right);
            MigraDocHelper.DrawString(frame, text, swfont, new RectangleF(290, 205, 100, 25), ParagraphAlignment.Right);
            text = amountdue.ToString("F2");
            //eg 92.00
            text = text.Substring(text.Length - 2, 2);
            //eg 00
            MigraDocHelper.DrawString(frame, text, swfont, 185, 205);
            MigraDocHelper.DrawString(frame, text, swfont, 425, 205);
            //Patient Address-----------------------------------------------------
            String patAddress = PatGuar.FName + " " + PatGuar.LName + "\r\n" + PatGuar.Address + "\r\n";
            if (!StringSupport.equals(PatGuar.Address2, ""))
            {
                patAddress += PatGuar.Address2 + "\r\n";
            }
             
            patAddress += PatGuar.Zip + " " + PatGuar.City;
            patAddress += ((StringSupport.equals(PatGuar.Country, "")) ? "" : "\r\n" + PatGuar.Country);
            MigraDocHelper.DrawString(frame, text, swfont, 495, 218);
            //middle left
            MigraDocHelper.DrawString(frame, text, swfont, 30, 263);
            //Lower left
            //Compute Reference#------------------------------------------------------
            //Reference# has exactly 27 digits
            //First 6 numbers are what we are calling the BankRouting number.
            //Next 20 numbers represent the invoice #.
            //27th number is the checksum
            String referenceNum = PrefC.getString(PrefName.BankRouting);
            //6 digits
            if (referenceNum.Length != 6)
            {
                referenceNum = "000000";
            }
             
            //"000000000000"//12 0's
            referenceNum += PatGuar.PatNum.ToString().PadLeft(12, '0') + DateTime.Today.ToString("yyyyMMdd");
            //+8=20
            //for testing:
            //referenceNum+="09090271100000067534";
            //"00000000000000037112";
            referenceNum += modulo10(referenceNum).ToString();
            //at this point, the referenceNum will always be exactly 27 digits long.
            String spacedRefNum = referenceNum.Substring(0, 2) + " " + referenceNum.Substring(2, 5) + " " + referenceNum.Substring(7, 5) + " " + referenceNum.Substring(12, 5) + " " + referenceNum.Substring(17, 5) + " " + referenceNum.Substring(22, 5);
            //text=spacedRefNum.Substring(0,15)+"\r\n"+spacedRefNum.Substring(16)+"\r\n";
            //reference# at lower left above address.  Small
            swfont = MigraDocHelper.CreateFont(7);
            MigraDocHelper.DrawString(frame, spacedRefNum, swfont, 30, 243);
            //Reference# at upper right---------------------------------------------------------------
            swfont = MigraDocHelper.CreateFont(10);
            MigraDocHelper.DrawString(frame, spacedRefNum, swfont, 490, 140);
            //Big long number at the lower right--------------------------------------------------
            /*The very long number on the bottom has this format:
            				>13 numbers > 27 numbers + 9 numbers >
            				>Example: 0100000254306>904483000000000000000371126+ 010045871>
            				>
            				>The first group of 13 numbers would begin with either 01 or only have 
            				>042 without any other following numbers.  01 would be used if there is 
            				>a specific amount, and 042 would be used if there is not a specific 
            				>amount billed. So in the example, the billed amount is 254.30.  It has 
            				>01 followed by leading zeros to fill in the balance of the digits 
            				>required.  The last digit is a checksum done by the program.  If the 
            				>amount would be 1,254.30 then the example should read 0100001254306.
            				>
            				>There is a > separator, then the reference number made up previously.
            				>
            				>Then a + separator, followed by the bank account number.  Previously, 
            				>the number printed without the zeros, but in this case it has the zeros 
            				>and not the dashes.*/
            swfont = new MigraDoc.DocumentObjectModel.Font("OCR-B 10 BT", 12);
            text = "01" + amountdue.ToString("F2").Replace(".", "").PadLeft(10, '0');
            text += modulo10(text).ToString() + ">" + referenceNum + "+ " + origBankNum + ">";
            MigraDocHelper.DrawString(frame, text, swfont, 255, 345);
        }
         
        return doc;
    }

    /**
    * data may only contain numbers between 0 und 9
    */
    private int modulo10(String strNumber) throws Exception {
        //try{
        int[] intTable;
        int intTransfer = 0;
        for (int intIndex = 0;intIndex < strNumber.Length;intIndex++)
        {
            int digit = Convert.ToInt32(strNumber.Substring(intIndex, 1));
            int modulus = (intTransfer + digit) % 10;
            intTransfer = intTable[modulus];
        }
        return (10 - intTransfer) % 10;
    }

    //}
    //catch{
    //	return 0;
    //}
    private void butZoomIn_Click(Object sender, System.EventArgs e) throws Exception {
        butFullPage.Visible = true;
        butZoomIn.Visible = false;
        printPreviewControl2.Zoom = 1;
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

    private void butPrint_Click(Object sender, System.EventArgs e) throws Exception {
    }

    //just for debugging
    /*PrintReport(false);
    			DialogResult=DialogResult.Cancel;*/
    private void butFullPage_Click(Object sender, System.EventArgs e) throws Exception {
        butFullPage.Visible = false;
        butZoomIn.Visible = true;
        printPreviewControl2.Zoom = ((double)printPreviewControl2.ClientSize.Height / (double)pd2.DefaultPageSettings.PaperSize.Height);
    }

}


