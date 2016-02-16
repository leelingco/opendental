//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:51 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.FormClaimPrint;
import OpenDental.Lan;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.ProviderIdent;
import OpenDental.ProviderIdents;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimCondCodeLog;
import OpenDentBusiness.ClaimCondCodeLogs;
import OpenDentBusiness.ClaimForm;
import OpenDentBusiness.ClaimForms;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Claims;
import OpenDentBusiness.ClaimValCodeLog;
import OpenDentBusiness.ClaimValCodeLogs;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Employers;
import OpenDentBusiness.EnumProcDrugUnit;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.EtransType;
import OpenDentBusiness.Family;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InsFilingCodes;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.Relat;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothInitials;
import OpenDentBusiness.ToothPaintingType;
import OpenDentBusiness.TreatmentArea;
import OpenDentBusiness.X12Generator;

/**
* 
*/
public class FormClaimPrint  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.PrintPreviewControl Preview2 = new System.Windows.Forms.PrintPreviewControl();
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private OpenDental.UI.Button butPrint;
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public long ClaimNumCur = new long();
    /**
    * 
    */
    public long PatNumCur = new long();
    //<summary>This will be 0 unless the user is trying to print a batch e-claim with a predefined ClaimForm.</summary>
    //public int ClaimFormNum;
    /**
    * 
    */
    public boolean PrintBlank = new boolean();
    /**
    * 
    */
    public boolean PrintImmediately = new boolean();
    private String[] displayStrings = new String[]();
    /**
    * The claimprocs for this claim, not including payments and duplicates.
    */
    private List<ClaimProc> ListClaimProcs = new List<ClaimProc>();
    /**
    * For batch generic e-claims, this just prints the data and not the background.
    */
    public boolean HideBackground = new boolean();
    private System.Windows.Forms.Label labelTotPages = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butBack;
    private OpenDental.UI.Button butFwd;
    private int pagesPrinted = new int();
    private int totalPages = new int();
    //<summary>Set to true if using this class just to generate strings for the Renaissance link.</summary>
    //private bool IsRenaissance;
    private List<ClaimProc> ClaimProcsForClaim = new List<ClaimProc>();
    private List<ClaimProc> ClaimProcsForPat = new List<ClaimProc>();
    /**
    * All procedures for the patient.
    */
    private List<Procedure> ListProc = new List<Procedure>();
    /**
    * This is set externally for Renaissance and generic e-claims.  If it was not set ahead of time, it will set in FillDisplayStrings according to the insPlan.
    */
    public ClaimForm ClaimFormCur;
    private List<InsPlan> ListInsPlan = new List<InsPlan>();
    //private InsPlan[] MedPlanList;
    private List<PatPlan> ListPatPlans = new List<PatPlan>();
    private Claim ClaimCur;
    /**
    * Always length of 4.
    */
    private String[] diagnoses = new String[]();
    //private Claim[] ClaimsArray;
    //private Claim[] MedClaimsArray;
    private List<ClaimValCodeLog> ListClaimValCodeLog = new List<ClaimValCodeLog>();
    private Referral ClaimReferral;
    private List<InsSub> ListInsSub2 = new List<InsSub>();
    private InsSub subCur;
    private InsPlan planCur;
    private Carrier carrier;
    /**
    * 
    */
    public FormClaimPrint() throws Exception {
        initializeComponent();
        Lan.F(this, new Control[]{ this.labelTotPages });
    }

    //exclude
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimPrint.class);
        this.butClose = new OpenDental.UI.Button();
        this.Preview2 = new System.Windows.Forms.PrintPreviewControl();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.butPrint = new OpenDental.UI.Button();
        this.labelTotPages = new System.Windows.Forms.Label();
        this.butBack = new OpenDental.UI.Button();
        this.butFwd = new OpenDental.UI.Button();
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
        this.butClose.Location = new System.Drawing.Point(770, 768);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 25);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // Preview2
        //
        this.Preview2.AutoZoom = false;
        this.Preview2.Location = new System.Drawing.Point(0, 0);
        this.Preview2.Name = "Preview2";
        this.Preview2.Size = new System.Drawing.Size(738, 798);
        this.Preview2.TabIndex = 1;
        this.Preview2.Zoom = 1;
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Location = new System.Drawing.Point(769, 728);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 25);
        this.butPrint.TabIndex = 2;
        this.butPrint.Text = "&Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // labelTotPages
        //
        this.labelTotPages.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.labelTotPages.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTotPages.Location = new System.Drawing.Point(774, 679);
        this.labelTotPages.Name = "labelTotPages";
        this.labelTotPages.Size = new System.Drawing.Size(54, 18);
        this.labelTotPages.TabIndex = 22;
        this.labelTotPages.Text = "1 / 2";
        this.labelTotPages.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // butBack
        //
        this.butBack.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBack.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butBack.setAutosize(true);
        this.butBack.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBack.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBack.setCornerRadius(4F);
        this.butBack.Image = Resources.getLeft();
        this.butBack.Location = new System.Drawing.Point(752, 676);
        this.butBack.Name = "butBack";
        this.butBack.Size = new System.Drawing.Size(18, 23);
        this.butBack.TabIndex = 23;
        this.butBack.Click += new System.EventHandler(this.butBack_Click);
        //
        // butFwd
        //
        this.butFwd.setAdjustImageLocation(new System.Drawing.Point(1, 0));
        this.butFwd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butFwd.setAutosize(true);
        this.butFwd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFwd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFwd.setCornerRadius(4F);
        this.butFwd.Image = Resources.getRight();
        this.butFwd.Location = new System.Drawing.Point(830, 676);
        this.butFwd.Name = "butFwd";
        this.butFwd.Size = new System.Drawing.Size(18, 23);
        this.butFwd.TabIndex = 24;
        this.butFwd.Click += new System.EventHandler(this.butFwd_Click);
        //
        // FormClaimPrint
        //
        this.AcceptButton = this.butPrint;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(860, 816);
        this.Controls.Add(this.labelTotPages);
        this.Controls.Add(this.butBack);
        this.Controls.Add(this.butFwd);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.Preview2);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormClaimPrint";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Print Claim";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormClaimPrint_Load);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.FormClaimPrint_Layout);
        this.ResumeLayout(false);
    }

    private void formClaimPrint_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        Preview2.Height = ClientRectangle.Height;
        Preview2.Width = ClientRectangle.Width - 160;
    }

    //butClose.Location=new Point(ClientRectangle.Width-100,ClientRectangle.Height-70);
    //butPrint.Location=new Point(ClientRectangle.Width-100,ClientRectangle.Height-140);
    private void formClaimPrint_Load(Object sender, System.EventArgs e) throws Exception {
        if (PrinterSettings.InstalledPrinters.Count == 0)
        {
            MessageBox.Show(Lan.g(this,"No printer installed"));
            return ;
        }
         
        pd2 = new PrintDocument();
        pagesPrinted = 0;
        pd2.OriginAtMargins = true;
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        if (pd2.DefaultPageSettings.PrintableArea.Width == 0 || pd2.DefaultPageSettings.PrintableArea.Height < 400)
        {
            //some printers report page size of 0.
            //prevents bug in some printers that do not specify paper size
            pd2.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        Preview2.Document = pd2;
        //display document
        Preview2.InvalidatePreview();
    }

    /**
    * Only called from external forms without ever loading this form.  Prints without showing any print preview.  Returns true if printed successfully.  Make sure to call PrinterL.SetPrinter() before calling this method so that a printer name is set for when printing batch claims.
    */
    public boolean printImmediate(PrinterSettings ps) throws Exception {
        pd2 = new PrintDocument();
        pd2.PrinterSettings = ps;
        pagesPrinted = 0;
        pd2.OriginAtMargins = true;
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        if (pd2.DefaultPageSettings.PrintableArea.Width == 0 || pd2.DefaultPageSettings.PrintableArea.Height < 400)
        {
            //some printers report page size of 0.
            //prevents bug in some printers that do not specify paper size
            pd2.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        try
        {
            pd2.Print();
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return false;
        }

        return true;
    }

    //Lan.g("Printer","Printer not available"));
    private void pd2_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        //raised for each page to be printed.
        fillDisplayStrings(false);
        int procLimit = procLimitForFormat();
        //claimprocs is filled in FillDisplayStrings
        if (ListClaimProcs.Count == 0)
        {
            totalPages = 1;
        }
        else
        {
            //js Some other programmer added this variable and implemented it.  We couldn't figure out why.  For example, in the line above, why would claimprocs ever be zero?  Due to many style and logic issues, we were forced to remove the function of this variable.  It would have to be written from scratch to implement it in the future.  Claims were never designed for multi-page.
            //Jason - this code we don't understand worked so I added it back until we get time to rewrite it correctly.
            totalPages = (int)Math.Ceiling((double)ListClaimProcs.Count / (double)procLimit);
        } 
        boolean HasMedical = false;
        if (!PrintBlank)
        {
            fillProcStrings(pagesPrinted * procLimit,procLimit);
            for (int i = 0;i < ListInsPlan.Count;i++)
            {
                if (ListInsPlan[i].IsMedical)
                {
                    HasMedical = true;
                }
                 
            }
        }
         
        if (HasMedical)
        {
            fillMedInsStrings();
            fillMedValueCodes();
            fillMedCondCodes();
        }
         
        Graphics grfx = ev.Graphics;
        float xPosText = new float();
        for (int i = 0;i < ClaimFormCur.Items.Length;i++)
        {
            if (StringSupport.equals(ClaimFormCur.Items[i].ImageFileName, ""))
            {
                //field
                xPosText = ClaimFormCur.Items[i].XPos + ClaimFormCur.OffsetX;
                if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P1Fee") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P2Fee") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P3Fee") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P4Fee") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P5Fee") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P6Fee") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P7Fee") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P8Fee") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P9Fee") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P10Fee") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P1Lab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P2Lab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P3Lab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P4Lab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P5Lab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P6Lab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P7Lab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P8Lab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P9Lab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P10Lab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P1FeeMinusLab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P2FeeMinusLab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P3FeeMinusLab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P4FeeMinusLab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P5FeeMinusLab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P6FeeMinusLab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P7FeeMinusLab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P8FeeMinusLab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P9FeeMinusLab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "P10FeeMinusLab") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "TotalFee") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedInsAAmtDue") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedInsBAmtDue") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedInsCAmtDue") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedInsAPriorPmt") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedInsBPriorPmt") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedInsCPriorPmt") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount39a") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount39b") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount39c") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount39d") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount40a") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount40b") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount40c") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount40d") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount41a") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount41b") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount41c") || StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedValAmount41d"))
                {
                    //this aligns it to the right
                    xPosText -= grfx.MeasureString(displayStrings[i], new Font(ClaimFormCur.FontName, ClaimFormCur.FontSize)).Width;
                }
                 
                grfx.DrawString(displayStrings[i], new Font(ClaimFormCur.FontName, ClaimFormCur.FontSize), new SolidBrush(Color.Black), new RectangleF(xPosText, ClaimFormCur.Items[i].YPos + ClaimFormCur.OffsetY, ClaimFormCur.Items[i].Width, ClaimFormCur.Items[i].Height));
            }
            else
            {
                //image
                if (!ClaimFormCur.PrintImages)
                {
                    continue;
                }
                 
                if (HideBackground)
                {
                    continue;
                }
                 
                Image thisImage = new Image();
                String extension = new String();
                if (StringSupport.equals(ClaimFormCur.Items[i].ImageFileName, "ADA2006.gif"))
                {
                    thisImage = CDT.Class1.GetADA2006();
                    extension = ".gif";
                }
                else if (StringSupport.equals(ClaimFormCur.Items[i].ImageFileName, "ADA2012.gif"))
                {
                    thisImage = CDT.Class1.GetADA2012();
                    extension = ".gif";
                }
                else
                {
                    //In the case when the A to Z folders are not being used, an invalid form image path is returned
                    //and we simply print without the background image (just as if the background image were removed
                    //from the A to Z folders where it was expected.
                    String fileName = CodeBase.ODFileUtils.CombinePaths(ImageStore.getPreferredAtoZpath(), ClaimFormCur.Items[i].ImageFileName);
                    if (!File.Exists(fileName))
                    {
                        continue;
                    }
                     
                    //MessageBox.Show("File not found.");
                    thisImage = Image.FromFile(fileName);
                    extension = Path.GetExtension(fileName);
                }  
                if (StringSupport.equals(extension, ".jpg"))
                {
                    grfx.DrawImage(thisImage, ClaimFormCur.Items[i].XPos + ClaimFormCur.OffsetX, ClaimFormCur.Items[i].YPos + ClaimFormCur.OffsetY, (int)(thisImage.Width / thisImage.HorizontalResolution * 100), (int)(thisImage.Height / thisImage.VerticalResolution * 100));
                }
                else if (StringSupport.equals(extension, ".gif"))
                {
                    grfx.DrawImage(thisImage, ClaimFormCur.Items[i].XPos + ClaimFormCur.OffsetX, ClaimFormCur.Items[i].YPos + ClaimFormCur.OffsetY, ClaimFormCur.Items[i].Width, ClaimFormCur.Items[i].Height);
                }
                else if (StringSupport.equals(extension, ".emf"))
                {
                    grfx.DrawImage(thisImage, ClaimFormCur.Items[i].XPos + ClaimFormCur.OffsetX, ClaimFormCur.Items[i].YPos + ClaimFormCur.OffsetY, thisImage.Width, thisImage.Height);
                }
                   
            } 
        }
        pagesPrinted++;
        if (totalPages == pagesPrinted)
        {
            ev.HasMorePages = false;
            labelTotPages.Text = "1 / " + totalPages.ToString();
        }
        else
        {
            //MessageBox.Show(pagesPrinted.ToString()+","+totalPages.ToString());
            ev.HasMorePages = true;
        } 
    }

    /**
    * Only used when the print button is clicked from within this form during print preview.
    */
    public boolean printClaim() throws Exception {
        pd2.OriginAtMargins = true;
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        pagesPrinted = 0;
        if (PrinterL.SetPrinter(pd2, PrintSituation.Claim, PatNumCur, "Claim from " + ClaimCur.DateService.ToShortDateString() + " printed"))
        {
            try
            {
                pd2.Print();
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show(Lan.g("Printer","Printer not available."));
                return false;
            }
        
        }
        else
        {
            return false;
        } 
        return true;
    }

    //if they hit cancel
    /**
    * Called from Bridges.Renaissance, this takes the supplied ClaimFormItems.ListForForm, and generates an array of strings that will get saved into a text file.  First dimension of array is the pages. Second dimension is the lines in the page.
    */
    public String[][] fillRenaissance() throws Exception {
        //IsRenaissance=true;
        int procLimit = 8;
        fillDisplayStrings(true);
        //claimprocs is filled in FillDisplayStrings
        //, so this is just a little extra work
        totalPages = (int)Math.Ceiling((double)ListClaimProcs.Count / (double)procLimit);
        String[][] retVal = new String[totalPages];
        for (int i = 0;i < totalPages;i++)
        {
            pagesPrinted = i;
            //not sure if I also need to do FillDisplayStrings here
            fillProcStrings(pagesPrinted * procLimit,procLimit);
            retVal[i] = (String[])displayStrings.Clone();
        }
        return retVal;
    }

    /**
    * Gets all necessary info from db based on ThisPatNum and ThisClaimNum.  Then fills displayStrings with the actual text that will display on claim.  The isRenaissance flag is very temporary.
    */
    private void fillDisplayStrings(boolean isRenaissance) throws Exception {
        if (PrintBlank)
        {
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canadian. en-CA or fr-CA
                ClaimFormCur = ClaimForms.getClaimFormByUniqueId("OD6");
            }
            else
            {
                //CDA claim form
                //Assume USA
                ClaimFormCur = ClaimForms.getClaimFormByUniqueId("OD8");
            } 
            //ADA claim form
            //ClaimFormItems.GetListForForm(ClaimFormCur.ClaimFormNum);
            displayStrings = new String[ClaimFormCur.Items.Length];
            ListClaimProcs = new List<ClaimProc>();
            return ;
        }
         
        Family FamCur = Patients.getFamily(PatNumCur);
        Patient PatCur = FamCur.getPatient(PatNumCur);
        List<Claim> ClaimList = Claims.refresh(PatCur.PatNum);
        ClaimCur = Claims.GetFromList(ClaimList, ClaimNumCur);
        //((Claim)Claims.HList[ThisClaimNum]).Clone();
        ListInsSub2 = InsSubs.refreshForFam(FamCur);
        ListInsPlan = InsPlans.refreshForSubList(ListInsSub2);
        ListPatPlans = PatPlans.refresh(ClaimCur.PatNum);
        InsPlan otherPlan = InsPlans.getPlan(ClaimCur.PlanNum2,ListInsPlan);
        InsSub otherSub = InsSubs.getSub(ClaimCur.InsSubNum2,ListInsSub2);
        if (otherPlan == null)
        {
            otherPlan = new InsPlan();
        }
         
        //easier than leaving it null
        Carrier otherCarrier = new Carrier();
        if (otherPlan.PlanNum != 0)
        {
            otherCarrier = Carriers.getCarrier(otherPlan.CarrierNum);
        }
         
        //Employers.GetEmployer(otherPlan.EmployerNum);
        //Employer otherEmployer=Employers.Cur;//not actually used
        //then get the main plan
        subCur = InsSubs.getSub(ClaimCur.InsSubNum,ListInsSub2);
        planCur = InsPlans.getPlan(ClaimCur.PlanNum,ListInsPlan);
        Clinic clinic = Clinics.getClinic(ClaimCur.ClinicNum);
        carrier = Carriers.getCarrier(planCur.CarrierNum);
        //Employers.GetEmployer(InsPlans.Cur.EmployerNum);
        Patient subsc;
        if (FamCur.getIndex(subCur.Subscriber) == -1)
        {
            //from another family
            subsc = Patients.getPat(subCur.Subscriber);
        }
        else
        {
            //Patients.Cur;
            //Patients.GetFamily(ThisPatNum);//return to current family
            subsc = FamCur.ListPats[FamCur.getIndex(subCur.Subscriber)];
        } 
        Patient otherSubsc = new Patient();
        if (otherPlan.PlanNum != 0)
        {
            //if secondary insurance exists
            if (FamCur.getIndex(otherSub.Subscriber) == -1)
            {
                //from another family
                otherSubsc = Patients.getPat(otherSub.Subscriber);
            }
            else
            {
                //Patients.Cur;
                //Patients.GetFamily(ThisPatNum);//return to current family
                otherSubsc = FamCur.ListPats[FamCur.getIndex(otherSub.Subscriber)];
            } 
        }
         
        if (ClaimCur.ReferringProv > 0)
        {
            ClaimReferral = Referrals.getReferral(ClaimCur.ReferringProv);
        }
         
        ListProc = Procedures.refresh(PatCur.PatNum);
        List<ToothInitial> initialList = ToothInitials.refresh(PatCur.PatNum);
        //List<ClaimProc> ClaimProcList=ClaimProcs.Refresh(PatCur.PatNum);
        ClaimProcsForPat = ClaimProcs.refresh(ClaimCur.PatNum);
        ClaimProcsForClaim = ClaimProcs.refreshForClaim(ClaimCur.ClaimNum);
        ListClaimProcs = new List<ClaimProc>();
        boolean includeThis = new boolean();
        Procedure proc;
        for (int i = 0;i < ClaimProcsForClaim.Count;i++)
        {
            //fill the arraylist
            if (ClaimProcsForClaim[i].ProcNum == 0)
            {
                continue;
            }
             
            //skip payments
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canadian. en-CA or fr-CA
                proc = Procedures.GetProcFromList(ListProc, ClaimProcsForClaim[i].ProcNum);
                if (proc.ProcNumLab != 0)
                {
                    continue;
                }
                 
            }
             
            //This is a lab fee procedure.
            //skip lab fee procedures in Canada, because they will show up on the same line as the procedure that they are attached to.
            includeThis = true;
            for (int j = 0;j < ListClaimProcs.Count;j++)
            {
                //loop through existing claimprocs
                if (ListClaimProcs[j].ProcNum == ClaimProcsForClaim[i].ProcNum)
                {
                    includeThis = false;
                }
                 
            }
            //skip duplicate procedures
            if (includeThis)
            {
                ListClaimProcs.Add(ClaimProcsForClaim[i]);
            }
             
        }
        List<String> missingTeeth = ToothInitials.GetMissingOrHiddenTeeth(initialList);
        ProcedureCode procCode;
        for (int j = missingTeeth.Count - 1;j >= 0;j--)
        {
            for (int p = 0;p < ListClaimProcs.Count;p++)
            {
                //loop backwards to keep index accurate as items are removed
                //if the missing tooth is missing because of an extraction being billed here, then exclude it
                proc = Procedures.GetProcFromList(ListProc, ListClaimProcs[p].ProcNum);
                procCode = ProcedureCodes.getProcCode(proc.CodeNum);
                if (procCode.PaintType == ToothPaintingType.Extraction && StringSupport.equals(proc.ToothNum, missingTeeth[j]))
                {
                    missingTeeth.RemoveAt(j);
                    break;
                }
                 
            }
        }
        //diagnoses---------------------------------------------------------------------------------------
        diagnoses = new String[4];
        for (int i = 0;i < 4;i++)
        {
            diagnoses[i] = "";
        }
        for (int i = 0;i < ListClaimProcs.Count;i++)
        {
            proc = Procedures.GetProcFromList(ListProc, ListClaimProcs[i].ProcNum);
            if (StringSupport.equals(proc.DiagnosticCode, ""))
            {
                continue;
            }
             
            for (int d = 0;d < 4;d++)
            {
                if (StringSupport.equals(diagnoses[d], proc.DiagnosticCode))
                {
                    break;
                }
                 
                //if it's already been added
                if (StringSupport.equals(diagnoses[d], ""))
                {
                    //we're at the end of the list of existing diagnoses, and no match
                    diagnoses[d] = proc.DiagnosticCode;
                    break;
                }
                 
            }
        }
        //so add it.
        //There's still a chance that the diagnosis didn't get added, if there were more than 4.
        Provider treatDent = ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvTreat)];
        if (ClaimFormCur == null)
        {
            if (ClaimCur.ClaimForm > 0)
            {
                ClaimFormCur = ClaimForms.getClaimForm(ClaimCur.ClaimForm);
            }
            else
            {
                ClaimFormCur = ClaimForms.getClaimForm(planCur.ClaimFormNum);
            } 
        }
         
        List<PatPlan> patPlans = null;
        displayStrings = new String[ClaimFormCur.Items.Length];
        for (int i = 0;i < ClaimFormCur.Items.Length;i++)
        {
            //a value is set for every item, but not every case will have a matching claimform item.
            if (ClaimFormCur.Items[i] == null)
            {
                //Renaissance does not use [0]
                displayStrings[i] = "";
                continue;
            }
             
            FieldName __dummyScrutVar0 = ClaimFormCur.Items[i].FieldName;
            //image. or procedure which gets filled in FillProcStrings.
            if (__dummyScrutVar0.equals("FixedText"))
            {
                displayStrings[i] = ClaimFormCur.Items[i].FormatString;
            }
            else if (__dummyScrutVar0.equals("IsPreAuth"))
            {
                if (StringSupport.equals(ClaimCur.ClaimType, "PreAuth"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsStandardClaim"))
            {
                if (!StringSupport.equals(ClaimCur.ClaimType, "PreAuth"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("ShowPreauthorizationIfPreauth"))
            {
                if (StringSupport.equals(ClaimCur.ClaimType, "PreAuth"))
                {
                    displayStrings[i] = "Preauthorization";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsMedicaidClaim"))
            {
                //this should later be replaced with an insplan field.
                if (!StringSupport.equals(PatCur.MedicaidID, ""))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsGroupHealthPlan"))
            {
                String eclaimcode = InsFilingCodes.getEclaimCode(planCur.FilingCode);
                //medicaid
                //champus
                if (StringSupport.equals(PatCur.MedicaidID, "") && !StringSupport.equals(eclaimcode, "MC") && !StringSupport.equals(eclaimcode, "CH") && !StringSupport.equals(eclaimcode, "VA"))
                {
                    //veterans
                    //&& eclaimcode != ""//medicare?
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PreAuthString"))
            {
                displayStrings[i] = ClaimCur.PreAuthString;
            }
            else if (__dummyScrutVar0.equals("PriorAuthString"))
            {
                displayStrings[i] = ClaimCur.PriorAuthorizationNumber;
            }
            else if (__dummyScrutVar0.equals("PriInsCarrierName"))
            {
                displayStrings[i] = carrier.CarrierName;
            }
            else if (__dummyScrutVar0.equals("PriInsAddress"))
            {
                displayStrings[i] = carrier.Address;
            }
            else if (__dummyScrutVar0.equals("PriInsAddress2"))
            {
                displayStrings[i] = carrier.Address2;
            }
            else if (__dummyScrutVar0.equals("PriInsAddressComplete"))
            {
                displayStrings[i] = carrier.Address + " " + carrier.Address2;
            }
            else if (__dummyScrutVar0.equals("PriInsCity"))
            {
                displayStrings[i] = carrier.City;
            }
            else if (__dummyScrutVar0.equals("PriInsST"))
            {
                displayStrings[i] = carrier.State;
            }
            else if (__dummyScrutVar0.equals("PriInsZip"))
            {
                displayStrings[i] = carrier.Zip;
            }
            else if (__dummyScrutVar0.equals("OtherInsExists"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsNotExists"))
            {
                if (otherPlan.PlanNum == 0)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsExistsDent"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    if (!otherPlan.IsMedical)
                    {
                        displayStrings[i] = "X";
                    }
                     
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsExistsMed"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    if (otherPlan.IsMedical)
                    {
                        displayStrings[i] = "X";
                    }
                     
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsSubscrLastFirst"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    displayStrings[i] = otherSubsc.LName + ", " + otherSubsc.FName + " " + otherSubsc.MiddleI;
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsSubscrDOB"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                    {
                        displayStrings[i] = otherSubsc.Birthdate.ToShortDateString();
                    }
                    else
                    {
                        displayStrings[i] = otherSubsc.Birthdate.ToString(ClaimFormCur.Items[i].FormatString);
                    } 
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsSubscrIsMale"))
            {
                if (otherPlan.PlanNum != 0 && otherSubsc.Gender == PatientGender.Male)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsSubscrIsFemale"))
            {
                if (otherPlan.PlanNum != 0 && otherSubsc.Gender == PatientGender.Female)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsSubscrID"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    displayStrings[i] = otherSub.SubscriberID;
                }
                 
            }
            else //if(otherPlan.PlanNum!=0 && otherSubsc.SSN.Length==9){
            //	displayStrings[i]=otherSubsc.SSN.Substring(0,3)
            //		+"-"+otherSubsc.SSN.Substring(3,2)
            //		+"-"+otherSubsc.SSN.Substring(5);
            //}
            //break;
            if (__dummyScrutVar0.equals("OtherInsGroupNum"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    displayStrings[i] = otherPlan.GroupNum;
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsRelatIsSelf"))
            {
                if (otherPlan.PlanNum != 0 && ClaimCur.PatRelat2 == Relat.Self)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsRelatIsSpouse"))
            {
                if (otherPlan.PlanNum != 0 && ClaimCur.PatRelat2 == Relat.Spouse)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsRelatIsChild"))
            {
                if (otherPlan.PlanNum != 0 && ClaimCur.PatRelat2 == Relat.Child)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsRelatIsOther"))
            {
                if (otherPlan.PlanNum != 0 && (ClaimCur.PatRelat2 == Relat.Dependent || ClaimCur.PatRelat2 == Relat.Employee || ClaimCur.PatRelat2 == Relat.HandicapDep || ClaimCur.PatRelat2 == Relat.InjuredPlaintiff || ClaimCur.PatRelat2 == Relat.LifePartner || ClaimCur.PatRelat2 == Relat.SignifOther))
                    displayStrings[i] = "X";
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsCarrierName"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    displayStrings[i] = otherCarrier.CarrierName;
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsAddress"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    displayStrings[i] = otherCarrier.Address;
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsCity"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    displayStrings[i] = otherCarrier.City;
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsST"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    displayStrings[i] = otherCarrier.State;
                }
                 
            }
            else if (__dummyScrutVar0.equals("OtherInsZip"))
            {
                if (otherPlan.PlanNum != 0)
                {
                    displayStrings[i] = otherCarrier.Zip;
                }
                 
            }
            else if (__dummyScrutVar0.equals("SubscrLastFirst"))
            {
                displayStrings[i] = subsc.LName + ", " + subsc.FName + " " + subsc.MiddleI;
            }
            else if (__dummyScrutVar0.equals("SubscrAddress"))
            {
                displayStrings[i] = subsc.Address;
            }
            else if (__dummyScrutVar0.equals("SubscrAddress2"))
            {
                displayStrings[i] = subsc.Address2;
            }
            else if (__dummyScrutVar0.equals("SubscrAddressComplete"))
            {
                displayStrings[i] = subsc.Address + " " + subsc.Address2;
            }
            else if (__dummyScrutVar0.equals("SubscrCity"))
            {
                displayStrings[i] = subsc.City;
            }
            else if (__dummyScrutVar0.equals("SubscrST"))
            {
                displayStrings[i] = subsc.State;
            }
            else if (__dummyScrutVar0.equals("SubscrZip"))
            {
                displayStrings[i] = subsc.Zip;
            }
            else if (__dummyScrutVar0.equals("SubscrPhone"))
            {
                //needs work.  Only used for 1500
                if (isRenaissance)
                {
                    //Expecting (XXX)XXX-XXXX
                    displayStrings[i] = subsc.HmPhone;
                    if (subsc.HmPhone.Length > 14)
                    {
                        //Might have a note following the number.
                        displayStrings[i] = subsc.HmPhone.Substring(0, 14);
                    }
                     
                }
                else
                {
                    String phone = subsc.HmPhone.Replace("(", "");
                    phone = phone.Replace(")", "    ");
                    phone = phone.Replace("-", "  ");
                    displayStrings[i] = phone;
                } 
            }
            else if (__dummyScrutVar0.equals("SubscrDOB"))
            {
                if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                {
                    displayStrings[i] = subsc.Birthdate.ToShortDateString();
                }
                else
                {
                    //MM/dd/yyyy
                    displayStrings[i] = subsc.Birthdate.ToString(ClaimFormCur.Items[i].FormatString);
                } 
            }
            else if (__dummyScrutVar0.equals("SubscrIsMale"))
            {
                if (subsc.Gender == PatientGender.Male)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("SubscrIsFemale"))
            {
                if (subsc.Gender == PatientGender.Female)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("SubscrGender"))
            {
                if (subsc.Gender == PatientGender.Male)
                {
                    displayStrings[i] = "M";
                }
                else
                {
                    displayStrings[i] = "F";
                } 
            }
            else if (__dummyScrutVar0.equals("SubscrIsMarried"))
            {
                if (subsc.Position == PatientPosition.Married)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("SubscrIsSingle"))
            {
                if (subsc.Position == PatientPosition.Single || subsc.Position == PatientPosition.Child || subsc.Position == PatientPosition.Widowed)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("SubscrID"))
            {
                patPlans = PatPlans.refresh(PatNumCur);
                String patID = PatPlans.GetPatID(subCur.InsSubNum, patPlans);
                if (StringSupport.equals(patID, ""))
                {
                    displayStrings[i] = subCur.SubscriberID;
                }
                else
                {
                    displayStrings[i] = patID;
                } 
            }
            else if (__dummyScrutVar0.equals("SubscrIDStrict"))
            {
                displayStrings[i] = subCur.SubscriberID;
            }
            else if (__dummyScrutVar0.equals("SubscrIsFTStudent"))
            {
                if (StringSupport.equals(subsc.StudentStatus, "F"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("SubscrIsPTStudent"))
            {
                if (StringSupport.equals(subsc.StudentStatus, "P"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("GroupName"))
            {
                displayStrings[i] = planCur.GroupName;
            }
            else if (__dummyScrutVar0.equals("GroupNum"))
            {
                displayStrings[i] = planCur.GroupNum;
            }
            else if (__dummyScrutVar0.equals("DivisionNo"))
            {
                displayStrings[i] = planCur.DivisionNo;
            }
            else if (__dummyScrutVar0.equals("EmployerName"))
            {
                displayStrings[i] = Employers.getEmployer(planCur.EmployerNum).EmpName;
                    ;
            }
            else if (__dummyScrutVar0.equals("RelatIsSelf"))
            {
                if (ClaimCur.PatRelat == Relat.Self)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("RelatIsSpouse"))
            {
                if (ClaimCur.PatRelat == Relat.Spouse)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("RelatIsChild"))
            {
                if (ClaimCur.PatRelat == Relat.Child)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("RelatIsOther"))
            {
                if (ClaimCur.PatRelat == Relat.Dependent || ClaimCur.PatRelat == Relat.Employee || ClaimCur.PatRelat == Relat.HandicapDep || ClaimCur.PatRelat == Relat.InjuredPlaintiff || ClaimCur.PatRelat == Relat.LifePartner || ClaimCur.PatRelat == Relat.SignifOther)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Relationship"))
            {
                if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                {
                    //Canadian. en-CA or fr-CA
                    if (ClaimCur.PatRelat == Relat.Self)
                    {
                        displayStrings[i] = "Self";
                    }
                    else if (ClaimCur.PatRelat == Relat.Spouse)
                    {
                        displayStrings[i] = "Spouse";
                    }
                    else if (ClaimCur.PatRelat == Relat.Child)
                    {
                        displayStrings[i] = "Child";
                    }
                    else if (ClaimCur.PatRelat == Relat.SignifOther || ClaimCur.PatRelat == Relat.LifePartner)
                    {
                        displayStrings[i] = "Common Law Spouse";
                    }
                    else
                    {
                        displayStrings[i] = "Other";
                    }    
                }
                else
                {
                    displayStrings[i] = ClaimCur.PatRelat.ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("IsFTStudent"))
            {
                if (StringSupport.equals(PatCur.StudentStatus, "F"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsPTStudent"))
            {
                if (StringSupport.equals(PatCur.StudentStatus, "P"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsStudent"))
            {
                if (StringSupport.equals(PatCur.StudentStatus, "P") || StringSupport.equals(PatCur.StudentStatus, "F"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("CollegeName"))
            {
                displayStrings[i] = PatCur.SchoolName;
            }
            else if (__dummyScrutVar0.equals("PatientLastFirst"))
            {
                displayStrings[i] = PatCur.LName + ", " + PatCur.FName + " " + PatCur.MiddleI;
            }
            else if (__dummyScrutVar0.equals("PatientFirstMiddleLast"))
            {
                displayStrings[i] = PatCur.FName + " " + PatCur.MiddleI + " " + PatCur.LName;
            }
            else if (__dummyScrutVar0.equals("PatientFirstName"))
            {
                displayStrings[i] = PatCur.FName;
            }
            else if (__dummyScrutVar0.equals("PatientMiddleName"))
            {
                displayStrings[i] = PatCur.MiddleI;
            }
            else if (__dummyScrutVar0.equals("PatientLastName"))
            {
                displayStrings[i] = PatCur.LName;
            }
            else if (__dummyScrutVar0.equals("PatientAddress"))
            {
                displayStrings[i] = PatCur.Address;
            }
            else if (__dummyScrutVar0.equals("PatientAddress2"))
            {
                displayStrings[i] = PatCur.Address2;
            }
            else if (__dummyScrutVar0.equals("PatientAddressComplete"))
            {
                displayStrings[i] = PatCur.Address + " " + PatCur.Address2;
            }
            else if (__dummyScrutVar0.equals("PatientCity"))
            {
                displayStrings[i] = PatCur.City;
            }
            else if (__dummyScrutVar0.equals("PatientST"))
            {
                displayStrings[i] = PatCur.State;
            }
            else if (__dummyScrutVar0.equals("PatientZip"))
            {
                displayStrings[i] = PatCur.Zip;
            }
            else if (__dummyScrutVar0.equals("PatientPhone"))
            {
                //needs work.  Only used for 1500
                if (isRenaissance)
                {
                    //Expecting (XXX)XXX-XXXX
                    displayStrings[i] = PatCur.HmPhone;
                    if (PatCur.HmPhone.Length > 14)
                    {
                        //Might have a note following the number.
                        displayStrings[i] = PatCur.HmPhone.Substring(0, 14);
                    }
                     
                }
                else
                {
                    String phonep = PatCur.HmPhone.Replace("(", "");
                    phonep = phonep.Replace(")", "    ");
                    phonep = phonep.Replace("-", "  ");
                    displayStrings[i] = phonep;
                } 
            }
            else if (__dummyScrutVar0.equals("PatientDOB"))
            {
                if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                {
                    displayStrings[i] = PatCur.Birthdate.ToShortDateString();
                }
                else
                {
                    //MM/dd/yyyy
                    displayStrings[i] = PatCur.Birthdate.ToString(ClaimFormCur.Items[i].FormatString);
                } 
            }
            else if (__dummyScrutVar0.equals("PatientIsMale"))
            {
                if (PatCur.Gender == PatientGender.Male)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PatientIsFemale"))
            {
                if (PatCur.Gender == PatientGender.Female)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PatientGender"))
            {
                if (PatCur.Gender == PatientGender.Male)
                {
                    displayStrings[i] = "Male";
                }
                else if (PatCur.Gender == PatientGender.Female)
                {
                    displayStrings[i] = "Female";
                }
                  
            }
            else if (__dummyScrutVar0.equals("PatientGenderLetter"))
            {
                if (subsc.Gender == PatientGender.Male)
                {
                    displayStrings[i] = "M";
                }
                else
                {
                    displayStrings[i] = "F";
                } 
            }
            else if (__dummyScrutVar0.equals("PatientIsMarried"))
            {
                if (PatCur.Position == PatientPosition.Married)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PatientIsSingle"))
            {
                if (PatCur.Position == PatientPosition.Single || PatCur.Position == PatientPosition.Child || PatCur.Position == PatientPosition.Widowed)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PatIDFromPatPlan"))
            {
                //Dependant Code for Canada
                patPlans = PatPlans.refresh(PatNumCur);
                if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                {
                    //Canadian. en-CA or fr-CA
                    if (StringSupport.equals(carrier.ElectID, "000064"))
                    {
                        //Pacific Blue Cross (PBC)
                        displayStrings[i] = subCur.SubscriberID + "-" + PatPlans.GetPatID(subCur.InsSubNum, patPlans);
                    }
                     
                }
                else
                {
                    displayStrings[i] = PatPlans.GetPatID(subCur.InsSubNum, patPlans);
                } 
            }
            else if (__dummyScrutVar0.equals("PatientSSN"))
            {
                if (PatCur.SSN.Length == 9)
                {
                    displayStrings[i] = PatCur.SSN.Substring(0, 3) + "-" + PatCur.SSN.Substring(3, 2) + "-" + PatCur.SSN.Substring(5);
                }
                else
                {
                    displayStrings[i] = PatCur.SSN;
                } 
            }
            else if (__dummyScrutVar0.equals("PatientMedicaidID"))
            {
                displayStrings[i] = PatCur.MedicaidID;
            }
            else if (__dummyScrutVar0.equals("PatientID-MedicaidOrSSN"))
            {
                if (!StringSupport.equals(PatCur.MedicaidID, ""))
                {
                    displayStrings[i] = PatCur.MedicaidID;
                }
                else
                {
                    displayStrings[i] = PatCur.SSN;
                } 
            }
            else if (__dummyScrutVar0.equals("PatientChartNum"))
            {
                displayStrings[i] = PatCur.ChartNumber;
            }
            else if (__dummyScrutVar0.equals("PatientPatNum"))
            {
                displayStrings[i] = PatCur.PatNum.ToString();
            }
            else if (__dummyScrutVar0.equals("Diagnosis1"))
            {
                if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                {
                    displayStrings[i] = diagnoses[0];
                }
                else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                {
                    displayStrings[i] = diagnoses[0].Replace(".", "");
                }
                  
            }
            else if (__dummyScrutVar0.equals("Diagnosis2"))
            {
                if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                {
                    displayStrings[i] = diagnoses[1];
                }
                else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                {
                    displayStrings[i] = diagnoses[1].Replace(".", "");
                }
                  
            }
            else if (__dummyScrutVar0.equals("Diagnosis3"))
            {
                if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                {
                    displayStrings[i] = diagnoses[2];
                }
                else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                {
                    displayStrings[i] = diagnoses[2].Replace(".", "");
                }
                  
            }
            else if (__dummyScrutVar0.equals("Diagnosis4"))
            {
                if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                {
                    displayStrings[i] = diagnoses[3];
                }
                else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                {
                    displayStrings[i] = diagnoses[3].Replace(".", "");
                }
                  
            }
            else //this is where the procedures used to be
            if (__dummyScrutVar0.equals("Miss1"))
            {
                if (missingTeeth.Contains("1"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss2"))
            {
                if (missingTeeth.Contains("2"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss3"))
            {
                if (missingTeeth.Contains("3"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss4"))
            {
                if (missingTeeth.Contains("4"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss5"))
            {
                if (missingTeeth.Contains("5"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss6"))
            {
                if (missingTeeth.Contains("6"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss7"))
            {
                if (missingTeeth.Contains("7"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss8"))
            {
                if (missingTeeth.Contains("8"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss9"))
            {
                if (missingTeeth.Contains("9"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss10"))
            {
                if (missingTeeth.Contains("10"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss11"))
            {
                if (missingTeeth.Contains("11"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss12"))
            {
                if (missingTeeth.Contains("12"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss13"))
            {
                if (missingTeeth.Contains("13"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss14"))
            {
                if (missingTeeth.Contains("14"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss15"))
            {
                if (missingTeeth.Contains("15"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss16"))
            {
                if (missingTeeth.Contains("16"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss17"))
            {
                if (missingTeeth.Contains("17"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss18"))
            {
                if (missingTeeth.Contains("18"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss19"))
            {
                if (missingTeeth.Contains("19"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss20"))
            {
                if (missingTeeth.Contains("20"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss21"))
            {
                if (missingTeeth.Contains("21"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss22"))
            {
                if (missingTeeth.Contains("22"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss23"))
            {
                if (missingTeeth.Contains("23"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss24"))
            {
                if (missingTeeth.Contains("24"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss25"))
            {
                if (missingTeeth.Contains("25"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss26"))
            {
                if (missingTeeth.Contains("26"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss27"))
            {
                if (missingTeeth.Contains("27"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss28"))
            {
                if (missingTeeth.Contains("28"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss29"))
            {
                if (missingTeeth.Contains("29"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss30"))
            {
                if (missingTeeth.Contains("30"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss31"))
            {
                if (missingTeeth.Contains("31"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Miss32"))
            {
                if (missingTeeth.Contains("32"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("Remarks"))
            {
                displayStrings[i] = "";
                if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                {
                    //Canadian. en-CA or fr-CA
                    if (StringSupport.equals(carrier.ElectID, "000064"))
                    {
                        //Pacific Blue Cross (PBC)
                        if (StringSupport.equals(ClaimCur.ClaimType, "PreAuth"))
                        {
                            displayStrings[i] += "Predetermination only." + Environment.NewLine;
                        }
                        else
                        {
                            if (subCur.AssignBen)
                            {
                                displayStrings[i] += "Please pay provider." + Environment.NewLine;
                            }
                            else
                            {
                                displayStrings[i] += "Please pay patient." + Environment.NewLine;
                            } 
                        } 
                    }
                     
                }
                 
                if (!StringSupport.equals(ClaimCur.AttachmentID, "") && !ClaimCur.ClaimNote.StartsWith(ClaimCur.AttachmentID))
                {
                    displayStrings[i] = ClaimCur.AttachmentID + " ";
                }
                 
                displayStrings[i] += ClaimCur.ClaimNote;
            }
            else if (__dummyScrutVar0.equals("PatientRelease"))
            {
                if (subCur.ReleaseInfo)
                {
                    displayStrings[i] = "Signature on File";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PatientReleaseDate"))
            {
                if (subCur.ReleaseInfo && ClaimCur.DateSent.Year > 1860)
                {
                    if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                    {
                        displayStrings[i] = ClaimCur.DateSent.ToShortDateString();
                    }
                    else
                    {
                        displayStrings[i] = ClaimCur.DateSent.ToString(ClaimFormCur.Items[i].FormatString);
                    } 
                }
                 
            }
            else if (__dummyScrutVar0.equals("PatientAssignment"))
            {
                if (subCur.AssignBen)
                {
                    displayStrings[i] = "Signature on File";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PatientAssignmentDate"))
            {
                if (subCur.AssignBen && ClaimCur.DateSent.Year > 1860)
                {
                    if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                    {
                        displayStrings[i] = ClaimCur.DateSent.ToShortDateString();
                    }
                    else
                    {
                        displayStrings[i] = ClaimCur.DateSent.ToString(ClaimFormCur.Items[i].FormatString);
                    } 
                }
                 
            }
            else if (__dummyScrutVar0.equals("PlaceIsOffice"))
            {
                if (ClaimCur.PlaceService == PlaceOfService.Office)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PlaceIsHospADA2002"))
            {
                if (ClaimCur.PlaceService == PlaceOfService.InpatHospital || ClaimCur.PlaceService == PlaceOfService.OutpatHospital)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PlaceIsExtCareFacilityADA2002"))
            {
                if (ClaimCur.PlaceService == PlaceOfService.CustodialCareFacility || ClaimCur.PlaceService == PlaceOfService.SkilledNursFac)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PlaceIsOtherADA2002"))
            {
                if (ClaimCur.PlaceService == PlaceOfService.PatientsHome || ClaimCur.PlaceService == PlaceOfService.OtherLocation)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PlaceIsInpatHosp"))
            {
                if (ClaimCur.PlaceService == PlaceOfService.InpatHospital)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PlaceIsOutpatHosp"))
            {
                if (ClaimCur.PlaceService == PlaceOfService.OutpatHospital)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PlaceIsAdultLivCareFac"))
            {
                if (ClaimCur.PlaceService == PlaceOfService.CustodialCareFacility)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PlaceIsSkilledNursFac"))
            {
                if (ClaimCur.PlaceService == PlaceOfService.SkilledNursFac)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PlaceIsPatientsHome"))
            {
                if (ClaimCur.PlaceService == PlaceOfService.PatientsHome)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PlaceIsOtherLocation"))
            {
                if (ClaimCur.PlaceService == PlaceOfService.OtherLocation)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("PlaceNumericCode"))
            {
                displayStrings[i] = getPlaceOfServiceNum(ClaimCur.PlaceService);
            }
            else if (__dummyScrutVar0.equals("IsRadiographsAttached"))
            {
                if (ClaimCur.Radiographs > 0)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("RadiographsNumAttached"))
            {
                displayStrings[i] = ClaimCur.Radiographs.ToString();
            }
            else if (__dummyScrutVar0.equals("RadiographsNotAttached"))
            {
                if (ClaimCur.Radiographs == 0)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsEnclosuresAttached"))
            {
                if (ClaimCur.Radiographs > 0 || ClaimCur.AttachedImages > 0 || ClaimCur.AttachedModels > 0)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("AttachedImagesNum"))
            {
                displayStrings[i] = ClaimCur.AttachedImages.ToString();
            }
            else if (__dummyScrutVar0.equals("AttachedModelsNum"))
            {
                displayStrings[i] = ClaimCur.AttachedModels.ToString();
            }
            else if (__dummyScrutVar0.equals("IsNotOrtho"))
            {
                if (!ClaimCur.IsOrtho)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsOrtho"))
            {
                if (ClaimCur.IsOrtho)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("DateOrthoPlaced"))
            {
                if (ClaimCur.OrthoDate.Year > 1880)
                {
                    if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                    {
                        displayStrings[i] = ClaimCur.OrthoDate.ToShortDateString();
                    }
                    else
                    {
                        displayStrings[i] = ClaimCur.OrthoDate.ToString(ClaimFormCur.Items[i].FormatString);
                    } 
                }
                 
            }
            else if (__dummyScrutVar0.equals("MonthsOrthoRemaining"))
            {
                if (ClaimCur.OrthoRemainM > 0)
                {
                    displayStrings[i] = ClaimCur.OrthoRemainM.ToString();
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsNotProsth"))
            {
                if (StringSupport.equals(ClaimCur.IsProsthesis, "N"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsInitialProsth"))
            {
                if (StringSupport.equals(ClaimCur.IsProsthesis, "I"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsNotReplacementProsth"))
            {
                if (!StringSupport.equals(ClaimCur.IsProsthesis, "R"))
                {
                    //=='I'nitial or 'N'o
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsReplacementProsth"))
            {
                if (StringSupport.equals(ClaimCur.IsProsthesis, "R"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("DatePriorProsthPlaced"))
            {
                if (ClaimCur.PriorDate.Year > 1860)
                {
                    if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                    {
                        displayStrings[i] = ClaimCur.PriorDate.ToShortDateString();
                    }
                    else
                    {
                        displayStrings[i] = ClaimCur.PriorDate.ToString(ClaimFormCur.Items[i].FormatString);
                    } 
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsOccupational"))
            {
                if (StringSupport.equals(ClaimCur.AccidentRelated, "E"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsNotOccupational"))
            {
                if (!StringSupport.equals(ClaimCur.AccidentRelated, "E"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsAutoAccident"))
            {
                if (StringSupport.equals(ClaimCur.AccidentRelated, "A"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsNotAutoAccident"))
            {
                if (!StringSupport.equals(ClaimCur.AccidentRelated, "A"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsOtherAccident"))
            {
                if (StringSupport.equals(ClaimCur.AccidentRelated, "O"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsNotOtherAccident"))
            {
                if (!StringSupport.equals(ClaimCur.AccidentRelated, "O"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsNotAccident"))
            {
                if (!StringSupport.equals(ClaimCur.AccidentRelated, "O") && !StringSupport.equals(ClaimCur.AccidentRelated, "A"))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("IsAccident"))
            {
                if (!StringSupport.equals(ClaimCur.AccidentRelated, ""))
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("AccidentDate"))
            {
                if (ClaimCur.AccidentDate.Year > 1860)
                {
                    if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                    {
                        displayStrings[i] = ClaimCur.AccidentDate.ToShortDateString();
                    }
                    else
                    {
                        displayStrings[i] = ClaimCur.AccidentDate.ToString(ClaimFormCur.Items[i].FormatString);
                    } 
                }
                 
            }
            else if (__dummyScrutVar0.equals("AccidentST"))
            {
                displayStrings[i] = ClaimCur.AccidentST;
            }
            else if (__dummyScrutVar0.equals("BillingDentist"))
            {
                Provider P = ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvBill)];
                displayStrings[i] = P.FName + " " + P.MI + " " + P.LName + " " + P.Suffix;
            }
            else //case "BillingDentistAddress":
            //  if(PrefC.GetBool(PrefName.UseBillingAddressOnClaims)){
            //    displayStrings[i]=PrefC.GetString(PrefName.PracticeBillingAddress);
            //  }
            //  else if(clinic==null) {
            //    displayStrings[i]=PrefC.GetString(PrefName.PracticeAddress);
            //  }
            //  else {
            //    displayStrings[i]=clinic.Address;
            //  }
            //  break;
            //case "BillingDentistAddress2":
            //  if(PrefC.GetBool(PrefName.UseBillingAddressOnClaims)) {
            //    displayStrings[i]=PrefC.GetString(PrefName.PracticeBillingAddress2);
            //  }
            //  else if(clinic==null) {
            //    displayStrings[i]=PrefC.GetString(PrefName.PracticeAddress2);
            //  }
            //  else {
            //    displayStrings[i]=clinic.Address2;
            //  }
            //  break;
            //case "BillingDentistCity":
            //  if(PrefC.GetBool(PrefName.UseBillingAddressOnClaims)) {
            //    displayStrings[i]=PrefC.GetString(PrefName.PracticeBillingCity);
            //  }
            //  else if(clinic==null) {
            //    displayStrings[i]=PrefC.GetString(PrefName.PracticeCity);
            //  }
            //  else {
            //    displayStrings[i]=clinic.City;
            //  }
            //  break;
            //case "BillingDentistST":
            //  if(PrefC.GetBool(PrefName.UseBillingAddressOnClaims)) {
            //    displayStrings[i]=PrefC.GetString(PrefName.PracticeBillingST);
            //  }
            //  else if(clinic==null) {
            //    displayStrings[i]=PrefC.GetString(PrefName.PracticeST);
            //  }
            //  else {
            //    displayStrings[i]=clinic.State;
            //  }
            //  break;
            //case "BillingDentistZip":
            //  if(PrefC.GetBool(PrefName.UseBillingAddressOnClaims)) {
            //    displayStrings[i]=PrefC.GetString(PrefName.PracticeBillingZip);
            //  }
            //  else if(clinic==null) {
            //    displayStrings[i]=PrefC.GetString(PrefName.PracticeZip);
            //  }
            //  else {
            //    displayStrings[i]=clinic.Zip;
            //  }
            //  break;
            if (__dummyScrutVar0.equals("BillingDentistMedicaidID"))
            {
                displayStrings[i] = ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvBill)].MedicaidID;
            }
            else if (__dummyScrutVar0.equals("BillingDentistProviderID"))
            {
                ProviderIdent[] provIdents = ProviderIdents.GetForPayor(ClaimCur.ProvBill, carrier.ElectID);
                if (provIdents.Length > 0)
                {
                    displayStrings[i] = provIdents[0].IDNumber;
                }
                 
            }
            else //just use the first one we find
            if (__dummyScrutVar0.equals("BillingDentistNPI"))
            {
                displayStrings[i] = ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvBill)].NationalProvID;
                //Canadian. en-CA or fr-CA
                //Pacific Blue Cross (PBC)
                //Billing and treating providers are different
                if (CultureInfo.CurrentCulture.Name.EndsWith("CA") && StringSupport.equals(carrier.ElectID, "000064") && ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvBill)].NationalProvID != ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvTreat)].NationalProvID && displayStrings[i].Length == 9)
                {
                    //Only for provider numbers which have been entered correctly (to prevent and indexing exception).
                    displayStrings[i] = "00" + displayStrings[i].Substring(2, 5) + "00";
                }
                 
            }
            else if (__dummyScrutVar0.equals("BillingDentistLicenseNum"))
            {
                displayStrings[i] = ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvBill)].StateLicense;
            }
            else if (__dummyScrutVar0.equals("BillingDentistSSNorTIN"))
            {
                displayStrings[i] = ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvBill)].SSN;
            }
            else if (__dummyScrutVar0.equals("BillingDentistNumIsSSN"))
            {
                if (!ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvBill)].UsingTIN)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("BillingDentistNumIsTIN"))
            {
                if (ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvBill)].UsingTIN)
                {
                    displayStrings[i] = "X";
                }
                 
            }
            else if (__dummyScrutVar0.equals("BillingDentistPh123"))
            {
                if (clinic == null)
                {
                    if (PrefC.getString(PrefName.PracticePhone).Length == 10)
                    {
                        displayStrings[i] = PrefC.getString(PrefName.PracticePhone).Substring(0, 3);
                    }
                     
                }
                else
                {
                    if (clinic.Phone.Length == 10)
                    {
                        displayStrings[i] = clinic.Phone.Substring(0, 3);
                    }
                     
                } 
            }
            else if (__dummyScrutVar0.equals("BillingDentistPh456"))
            {
                if (clinic == null)
                {
                    if (PrefC.getString(PrefName.PracticePhone).Length == 10)
                    {
                        displayStrings[i] = PrefC.getString(PrefName.PracticePhone).Substring(3, 3);
                    }
                     
                }
                else
                {
                    if (clinic.Phone.Length == 10)
                    {
                        displayStrings[i] = clinic.Phone.Substring(3, 3);
                    }
                     
                } 
            }
            else if (__dummyScrutVar0.equals("BillingDentistPh78910"))
            {
                if (clinic == null)
                {
                    if (PrefC.getString(PrefName.PracticePhone).Length == 10)
                    {
                        displayStrings[i] = PrefC.getString(PrefName.PracticePhone).Substring(6);
                    }
                     
                }
                else
                {
                    if (clinic.Phone.Length == 10)
                    {
                        displayStrings[i] = clinic.Phone.Substring(6);
                    }
                     
                } 
            }
            else if (__dummyScrutVar0.equals("BillingDentistPhoneFormatted"))
            {
                if (clinic == null)
                {
                    if (PrefC.getString(PrefName.PracticePhone).Length == 10)
                    {
                        displayStrings[i] = "(" + PrefC.getString(PrefName.PracticePhone).Substring(0, 3) + ")" + PrefC.getString(PrefName.PracticePhone).Substring(3, 3) + "-" + PrefC.getString(PrefName.PracticePhone).Substring(6);
                    }
                     
                }
                else
                {
                    if (clinic.Phone.Length == 10)
                    {
                        displayStrings[i] = "(" + clinic.Phone.Substring(0, 3) + ")" + clinic.Phone.Substring(3, 3) + "-" + clinic.Phone.Substring(6);
                    }
                     
                } 
            }
            else if (__dummyScrutVar0.equals("BillingDentistPhoneRaw"))
            {
                if (clinic == null)
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticePhone);
                }
                else
                {
                    displayStrings[i] = clinic.Phone;
                } 
            }
            else if (__dummyScrutVar0.equals("PayToDentistAddress"))
            {
                //Behaves just like the old BillingDentistAddress field, but is overridden by the Pay-To address if the Pay-To address has been specified.
                if (!StringSupport.equals(PrefC.getString(PrefName.PracticePayToAddress), ""))
                {
                    //All Pay-To address fields are used in 5010 eclaims when Pay-To address line 1 is not blank.
                    displayStrings[i] = PrefC.getString(PrefName.PracticePayToAddress);
                }
                else if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeBillingAddress);
                }
                else if (clinic == null)
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeAddress);
                }
                else
                {
                    displayStrings[i] = clinic.Address;
                }   
            }
            else if (__dummyScrutVar0.equals("PayToDentistAddress2"))
            {
                //Behaves just like the old BillingDentistAddress2 field, but is overridden by the Pay-To address if the Pay-To address has been specified.
                if (!StringSupport.equals(PrefC.getString(PrefName.PracticePayToAddress), ""))
                {
                    //All Pay-To address fields are used in 5010 eclaims when Pay-To address line 1 is not blank.
                    displayStrings[i] = PrefC.getString(PrefName.PracticePayToAddress2);
                }
                else if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeBillingAddress2);
                }
                else if (clinic == null)
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeAddress2);
                }
                else
                {
                    displayStrings[i] = clinic.Address2;
                }   
            }
            else if (__dummyScrutVar0.equals("PayToDentistCity"))
            {
                //Behaves just like the old BillingDentistCity field, but is overridden by the Pay-To address if the Pay-To address has been specified.
                if (!StringSupport.equals(PrefC.getString(PrefName.PracticePayToAddress), ""))
                {
                    //All Pay-To address fields are used in 5010 eclaims when Pay-To address line 1 is not blank.
                    displayStrings[i] = PrefC.getString(PrefName.PracticePayToCity);
                }
                else if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeBillingCity);
                }
                else if (clinic == null)
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeCity);
                }
                else
                {
                    displayStrings[i] = clinic.City;
                }   
            }
            else if (__dummyScrutVar0.equals("PayToDentistST"))
            {
                //Behaves just like the old BillingDentistST field, but is overridden by the Pay-To address if the Pay-To address has been specified.
                if (!StringSupport.equals(PrefC.getString(PrefName.PracticePayToAddress), ""))
                {
                    //All Pay-To address fields are used in 5010 eclaims when Pay-To address line 1 is not blank.
                    displayStrings[i] = PrefC.getString(PrefName.PracticePayToST);
                }
                else if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeBillingST);
                }
                else if (clinic == null)
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeST);
                }
                else
                {
                    displayStrings[i] = clinic.State;
                }   
            }
            else if (__dummyScrutVar0.equals("PayToDentistZip"))
            {
                //Behaves just like the old BillingDentistZip field, but is overridden by the Pay-To address if the Pay-To address has been specified.
                if (!StringSupport.equals(PrefC.getString(PrefName.PracticePayToAddress), ""))
                {
                    //All Pay-To address fields are used in 5010 eclaims when Pay-To address line 1 is not blank.
                    displayStrings[i] = PrefC.getString(PrefName.PracticePayToZip);
                }
                else if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeBillingZip);
                }
                else if (clinic == null)
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeZip);
                }
                else
                {
                    displayStrings[i] = clinic.Zip;
                }   
            }
            else if (__dummyScrutVar0.equals("TreatingDentistFName"))
            {
                displayStrings[i] = treatDent.FName;
            }
            else if (__dummyScrutVar0.equals("TreatingDentistLName"))
            {
                displayStrings[i] = treatDent.LName;
            }
            else if (__dummyScrutVar0.equals("TreatingDentistSignature"))
            {
                if (treatDent.SigOnFile)
                {
                    if (PrefC.getBool(PrefName.ClaimFormTreatDentSaysSigOnFile))
                    {
                        displayStrings[i] = "Signature on File";
                    }
                    else
                    {
                        displayStrings[i] = treatDent.FName + " " + treatDent.MI + " " + treatDent.LName + " " + treatDent.Suffix;
                    } 
                }
                 
            }
            else if (__dummyScrutVar0.equals("TreatingDentistSigDate"))
            {
                if (treatDent.SigOnFile && ClaimCur.DateSent.Year > 1860)
                {
                    if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                    {
                        displayStrings[i] = ClaimCur.DateSent.ToShortDateString();
                    }
                    else
                    {
                        displayStrings[i] = ClaimCur.DateSent.ToString(ClaimFormCur.Items[i].FormatString);
                    } 
                }
                 
            }
            else if (__dummyScrutVar0.equals("TreatingDentistMedicaidID"))
            {
                displayStrings[i] = treatDent.MedicaidID;
            }
            else if (__dummyScrutVar0.equals("TreatingDentistProviderID"))
            {
                provIdents = ProviderIdents.GetForPayor(ClaimCur.ProvTreat, carrier.ElectID);
                if (provIdents.Length > 0)
                {
                    displayStrings[i] = provIdents[0].IDNumber;
                }
                 
            }
            else //just use the first one we find
            if (__dummyScrutVar0.equals("TreatingDentistNPI"))
            {
                displayStrings[i] = treatDent.NationalProvID;
            }
            else if (__dummyScrutVar0.equals("TreatingDentistLicense"))
            {
                displayStrings[i] = treatDent.StateLicense;
            }
            else if (__dummyScrutVar0.equals("TreatingDentistAddress"))
            {
                if (clinic == null)
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeAddress);
                }
                else
                {
                    displayStrings[i] = clinic.Address;
                } 
            }
            else if (__dummyScrutVar0.equals("TreatingDentistCity"))
            {
                if (clinic == null)
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeCity);
                }
                else
                {
                    displayStrings[i] = clinic.City;
                } 
            }
            else if (__dummyScrutVar0.equals("TreatingDentistST"))
            {
                if (clinic == null)
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeST);
                }
                else
                {
                    displayStrings[i] = clinic.State;
                } 
            }
            else if (__dummyScrutVar0.equals("TreatingDentistZip"))
            {
                if (clinic == null)
                {
                    displayStrings[i] = PrefC.getString(PrefName.PracticeZip);
                }
                else
                {
                    displayStrings[i] = clinic.Zip;
                } 
            }
            else if (__dummyScrutVar0.equals("TreatingDentistPh123"))
            {
                if (clinic == null)
                {
                    if (PrefC.getString(PrefName.PracticePhone).Length == 10)
                    {
                        displayStrings[i] = PrefC.getString(PrefName.PracticePhone).Substring(0, 3);
                    }
                     
                }
                else
                {
                    if (clinic.Phone.Length == 10)
                    {
                        displayStrings[i] = clinic.Phone.Substring(0, 3);
                    }
                     
                } 
            }
            else if (__dummyScrutVar0.equals("TreatingDentistPh456"))
            {
                if (clinic == null)
                {
                    if (PrefC.getString(PrefName.PracticePhone).Length == 10)
                    {
                        displayStrings[i] = PrefC.getString(PrefName.PracticePhone).Substring(3, 3);
                    }
                     
                }
                else
                {
                    if (clinic.Phone.Length == 10)
                    {
                        displayStrings[i] = clinic.Phone.Substring(3, 3);
                    }
                     
                } 
            }
            else if (__dummyScrutVar0.equals("TreatingDentistPh78910"))
            {
                if (clinic == null)
                {
                    if (PrefC.getString(PrefName.PracticePhone).Length == 10)
                    {
                        displayStrings[i] = PrefC.getString(PrefName.PracticePhone).Substring(6);
                    }
                     
                }
                else
                {
                    if (clinic.Phone.Length == 10)
                    {
                        displayStrings[i] = clinic.Phone.Substring(6);
                    }
                     
                } 
            }
            else if (__dummyScrutVar0.equals("TreatingProviderSpecialty"))
            {
                displayStrings[i] = X12Generator.GetTaxonomy(ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvTreat)]);
            }
            else if (__dummyScrutVar0.equals("TotalPages"))
            {
                displayStrings[i] = "";
            }
            else //totalPages.ToString();//bugs with this field that we can't fix since we didn't write that code.
            if (__dummyScrutVar0.equals("ReferringProvNPI"))
            {
                if (ClaimReferral == null)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = ClaimReferral.NationalProvID;
                } 
            }
            else if (__dummyScrutVar0.equals("ReferringProvNameFL"))
            {
                if (ClaimReferral == null)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = ClaimReferral.getNameFL();
                } 
            }
            else if (__dummyScrutVar0.equals("MedUniformBillType"))
            {
                displayStrings[i] = ClaimCur.UniformBillType;
            }
            else if (__dummyScrutVar0.equals("MedAdmissionTypeCode"))
            {
                displayStrings[i] = ClaimCur.AdmissionTypeCode;
            }
            else if (__dummyScrutVar0.equals("MedAdmissionSourceCode"))
            {
                displayStrings[i] = ClaimCur.AdmissionSourceCode;
            }
            else if (__dummyScrutVar0.equals("MedPatientStatusCode"))
            {
                displayStrings[i] = ClaimCur.PatientStatusCode;
            }
            else if (__dummyScrutVar0.equals("MedAccidentCode"))
            {
                //For UB04.
                if (StringSupport.equals(ClaimCur.AccidentRelated, "A"))
                {
                    //Auto accident
                    displayStrings[i] = "01";
                }
                else if (StringSupport.equals(ClaimCur.AccidentRelated, "E"))
                {
                    //Employment related accident
                    displayStrings[i] = "04";
                }
                  
            }
            else
            {
                displayStrings[i] = "";
            }                                                                                                                                                                                                                 
            //switch
            if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "nl-BE") && StringSupport.equals(displayStrings[i], ""))
            {
                //Dutch Belgium
                displayStrings[i] = "*   *   *";
            }
             
            //Renaissance eclaims only: Remove newlines from display strings to prevent formatting issues, because the .rss file format requires each field on a single line.
            if (isRenaissance && displayStrings[i] != null)
            {
                displayStrings[i] = displayStrings[i].Replace("\r", "").Replace("\n", "");
            }
             
        }
    }

    //for
    /**
    * 
    */
    private String getPlaceOfServiceNum(PlaceOfService place) throws Exception {
        switch(place)
        {
            default: 
                return "";
            case AmbulatorySurgicalCenter: 
                return "24";
            case CustodialCareFacility: 
                return "33";
            case EmergencyRoomHospital: 
                return "23";
            case InpatHospital: 
                return "21";
            case Office: 
                return "11";
            case OutpatHospital: 
                return "22";
            case PatientsHome: 
                return "12";
            case SkilledNursFac: 
                return "31";
            case MobileUnit: 
                return "15";
            case School: 
                return "03";
            case MilitaryTreatFac: 
                return "26";
            case FederalHealthCenter: 
                return "50";
            case PublicHealthClinic: 
                return "71";
            case RuralHealthClinic: 
                return "72";
        
        }
    }

    /**
    * @param startProc For page 1, this will be 0, otherwise it might be 10, 8, 20, or whatever.  It is the 0-based index of the first proc. Depends on how many procedures this claim format can display and which page we are on.
    *  @param totProcs The number of procedures that can be displayed or printed per claim form.  Depends on the individual claim format. For example, 10 on the ADA2002
    */
    private void fillProcStrings(int startProc, int totProcs) throws Exception {
        int qty = new int();
        for (int i = 0;i < ClaimFormCur.Items.Length;i++)
        {
            if (ClaimFormCur.Items[i] == null)
            {
                continue;
            }
             
            //Renaissance does not use [0]
            InsPlan planCur = InsPlans.getPlan(ClaimCur.PlanNum,ListInsPlan);
            qty = 0;
            FieldName __dummyScrutVar2 = ClaimFormCur.Items[i].FieldName;
            //there is no default, because any non-matches will remain as ""
            if (__dummyScrutVar2.equals("P1Date"))
            {
                displayStrings[i] = GetProcInfo("Date", 1 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P1Area"))
            {
                displayStrings[i] = getProcInfo("Area",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1System"))
            {
                displayStrings[i] = getProcInfo("System",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1ToothNumber"))
            {
                displayStrings[i] = getProcInfo("ToothNum",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1Surface"))
            {
                displayStrings[i] = getProcInfo("Surface",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1Code"))
            {
                displayStrings[i] = getProcInfo("Code",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1Description"))
            {
                displayStrings[i] = getProcInfo("Desc",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1Fee"))
            {
                displayStrings[i] = GetProcInfo("Fee", 1 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P1TreatDentMedicaidID"))
            {
                displayStrings[i] = getProcInfo("TreatDentMedicaidID",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1PlaceNumericCode"))
            {
                displayStrings[i] = getProcInfo("PlaceNumericCode",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1Diagnosis"))
            {
                displayStrings[i] = getProcInfo("Diagnosis",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1Lab"))
            {
                displayStrings[i] = getProcInfo("Lab",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1FeeMinusLab"))
            {
                displayStrings[i] = getProcInfo("FeeMinusLab",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1ToothNumOrArea"))
            {
                displayStrings[i] = getProcInfo("ToothNumOrArea",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1TreatProvNPI"))
            {
                displayStrings[i] = getProcInfo("TreatProvNPI",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1RevCode"))
            {
                displayStrings[i] = getProcInfo("RevCode",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1CodeMod1"))
            {
                displayStrings[i] = getProcInfo("CodeMod1",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1CodeMod2"))
            {
                displayStrings[i] = getProcInfo("CodeMod2",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1CodeMod3"))
            {
                displayStrings[i] = getProcInfo("CodeMod3",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P1CodeMod4"))
            {
                displayStrings[i] = getProcInfo("CodeMod4",1 + startProc);
            }
            else //case "P1UnitCode":
            //	displayStrings[i]=GetProcInfo("UnitCode",1+startProc);
            //	break;
            if (__dummyScrutVar2.equals("P1UnitQty"))
            {
                if (planCur.ShowBaseUnits)
                {
                    short bunit = new short();
                    RefSupport<short> refVar___0 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("BaseUnits",1 + startProc), refVar___0);
                    bunit = refVar___0.getValue();
                    short uqty = new short();
                    RefSupport<short> refVar___1 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("UnitQty",1 + startProc), refVar___1);
                    uqty = refVar___1.getValue();
                    qty = bunit + uqty;
                }
                else if (!StringSupport.equals(getProcInfo("UnitQty",1 + startProc), ""))
                {
                    qty = Int16.Parse(getProcInfo("UnitQty",1 + startProc));
                }
                else
                {
                    qty = 0;
                }  
                if (qty == 0)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = qty.ToString();
                } 
            }
            else if (__dummyScrutVar2.equals("P1CodeAndMods"))
            {
                displayStrings[i] = getProcInfo("Code",1 + startProc) + getProcInfo("CodeMod1",1 + startProc) + getProcInfo("CodeMod2",1 + startProc) + getProcInfo("CodeMod3",1 + startProc) + getProcInfo("CodeMod4",1 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2Date"))
            {
                displayStrings[i] = GetProcInfo("Date", 2 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P2Area"))
            {
                displayStrings[i] = getProcInfo("Area",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2System"))
            {
                displayStrings[i] = getProcInfo("System",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2ToothNumber"))
            {
                displayStrings[i] = getProcInfo("ToothNum",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2Surface"))
            {
                displayStrings[i] = getProcInfo("Surface",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2Code"))
            {
                displayStrings[i] = getProcInfo("Code",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2Description"))
            {
                displayStrings[i] = getProcInfo("Desc",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2Fee"))
            {
                displayStrings[i] = GetProcInfo("Fee", 2 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P2TreatDentMedicaidID"))
            {
                displayStrings[i] = getProcInfo("TreatDentMedicaidID",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2PlaceNumericCode"))
            {
                displayStrings[i] = getProcInfo("PlaceNumericCode",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2Diagnosis"))
            {
                displayStrings[i] = getProcInfo("Diagnosis",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2Lab"))
            {
                displayStrings[i] = getProcInfo("Lab",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2FeeMinusLab"))
            {
                displayStrings[i] = getProcInfo("FeeMinusLab",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2ToothNumOrArea"))
            {
                displayStrings[i] = getProcInfo("ToothNumOrArea",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2TreatProvNPI"))
            {
                displayStrings[i] = getProcInfo("TreatProvNPI",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2RevCode"))
            {
                displayStrings[i] = getProcInfo("RevCode",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2CodeMod1"))
            {
                displayStrings[i] = getProcInfo("CodeMod1",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2CodeMod2"))
            {
                displayStrings[i] = getProcInfo("CodeMod2",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2CodeMod3"))
            {
                displayStrings[i] = getProcInfo("CodeMod3",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P2CodeMod4"))
            {
                displayStrings[i] = getProcInfo("CodeMod4",2 + startProc);
            }
            else //case "P2UnitCode":
            //	displayStrings[i]=GetProcInfo("UnitCode",2+startProc);
            //	break;
            if (__dummyScrutVar2.equals("P2UnitQty"))
            {
                if (planCur.ShowBaseUnits)
                {
                    short bunit = new short();
                    RefSupport<short> refVar___2 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("BaseUnits",2 + startProc), refVar___2);
                    bunit = refVar___2.getValue();
                    short uqty = new short();
                    RefSupport<short> refVar___3 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("UnitQty",2 + startProc), refVar___3);
                    uqty = refVar___3.getValue();
                    qty = bunit + uqty;
                }
                else if (!StringSupport.equals(getProcInfo("UnitQty",2 + startProc), ""))
                {
                    qty = Int16.Parse(getProcInfo("UnitQty",2 + startProc));
                }
                else
                {
                    qty = 0;
                }  
                if (qty == 0)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = qty.ToString();
                } 
            }
            else if (__dummyScrutVar2.equals("P2CodeAndMods"))
            {
                displayStrings[i] = getProcInfo("Code",2 + startProc) + getProcInfo("CodeMod1",2 + startProc) + getProcInfo("CodeMod2",2 + startProc) + getProcInfo("CodeMod3",2 + startProc) + getProcInfo("CodeMod4",2 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3Date"))
            {
                displayStrings[i] = GetProcInfo("Date", 3 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P3Area"))
            {
                displayStrings[i] = getProcInfo("Area",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3System"))
            {
                displayStrings[i] = getProcInfo("System",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3ToothNumber"))
            {
                displayStrings[i] = getProcInfo("ToothNum",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3Surface"))
            {
                displayStrings[i] = getProcInfo("Surface",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3Code"))
            {
                displayStrings[i] = getProcInfo("Code",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3Description"))
            {
                displayStrings[i] = getProcInfo("Desc",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3Fee"))
            {
                displayStrings[i] = GetProcInfo("Fee", 3 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P3TreatDentMedicaidID"))
            {
                displayStrings[i] = getProcInfo("TreatDentMedicaidID",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3PlaceNumericCode"))
            {
                displayStrings[i] = getProcInfo("PlaceNumericCode",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3Diagnosis"))
            {
                displayStrings[i] = getProcInfo("Diagnosis",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3Lab"))
            {
                displayStrings[i] = getProcInfo("Lab",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3FeeMinusLab"))
            {
                displayStrings[i] = getProcInfo("FeeMinusLab",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3ToothNumOrArea"))
            {
                displayStrings[i] = getProcInfo("ToothNumOrArea",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3TreatProvNPI"))
            {
                displayStrings[i] = getProcInfo("TreatProvNPI",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3RevCode"))
            {
                displayStrings[i] = getProcInfo("RevCode",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3CodeMod1"))
            {
                displayStrings[i] = getProcInfo("CodeMod1",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3CodeMod2"))
            {
                displayStrings[i] = getProcInfo("CodeMod2",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3CodeMod3"))
            {
                displayStrings[i] = getProcInfo("CodeMod3",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P3CodeMod4"))
            {
                displayStrings[i] = getProcInfo("CodeMod4",3 + startProc);
            }
            else //case "P3UnitCode":
            //	displayStrings[i]=GetProcInfo("UnitCode",3+startProc);
            //	break;
            if (__dummyScrutVar2.equals("P3UnitQty"))
            {
                if (planCur.ShowBaseUnits)
                {
                    short bunit = new short();
                    RefSupport<short> refVar___4 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("BaseUnits",3 + startProc), refVar___4);
                    bunit = refVar___4.getValue();
                    short uqty = new short();
                    RefSupport<short> refVar___5 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("UnitQty",3 + startProc), refVar___5);
                    uqty = refVar___5.getValue();
                    qty = bunit + uqty;
                }
                else if (!StringSupport.equals(getProcInfo("UnitQty",3 + startProc), ""))
                {
                    qty = Int16.Parse(getProcInfo("UnitQty",3 + startProc));
                }
                else
                {
                    qty = 0;
                }  
                if (qty == 0)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = qty.ToString();
                } 
            }
            else if (__dummyScrutVar2.equals("P3CodeAndMods"))
            {
                displayStrings[i] = getProcInfo("Code",3 + startProc) + getProcInfo("CodeMod1",3 + startProc) + getProcInfo("CodeMod2",3 + startProc) + getProcInfo("CodeMod3",3 + startProc) + getProcInfo("CodeMod4",3 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4Date"))
            {
                displayStrings[i] = GetProcInfo("Date", 4 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P4Area"))
            {
                displayStrings[i] = getProcInfo("Area",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4System"))
            {
                displayStrings[i] = getProcInfo("System",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4ToothNumber"))
            {
                displayStrings[i] = getProcInfo("ToothNum",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4Surface"))
            {
                displayStrings[i] = getProcInfo("Surface",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4Code"))
            {
                displayStrings[i] = getProcInfo("Code",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4Description"))
            {
                displayStrings[i] = getProcInfo("Desc",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4Fee"))
            {
                displayStrings[i] = GetProcInfo("Fee", 4 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P4TreatDentMedicaidID"))
            {
                displayStrings[i] = getProcInfo("TreatDentMedicaidID",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4PlaceNumericCode"))
            {
                displayStrings[i] = getProcInfo("PlaceNumericCode",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4Diagnosis"))
            {
                displayStrings[i] = getProcInfo("Diagnosis",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4Lab"))
            {
                displayStrings[i] = getProcInfo("Lab",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4FeeMinusLab"))
            {
                displayStrings[i] = getProcInfo("FeeMinusLab",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4ToothNumOrArea"))
            {
                displayStrings[i] = getProcInfo("ToothNumOrArea",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4TreatProvNPI"))
            {
                displayStrings[i] = getProcInfo("TreatProvNPI",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4RevCode"))
            {
                displayStrings[i] = getProcInfo("RevCode",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4CodeMod1"))
            {
                displayStrings[i] = getProcInfo("CodeMod1",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4CodeMod2"))
            {
                displayStrings[i] = getProcInfo("CodeMod2",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4CodeMod3"))
            {
                displayStrings[i] = getProcInfo("CodeMod3",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P4CodeMod4"))
            {
                displayStrings[i] = getProcInfo("CodeMod4",4 + startProc);
            }
            else //case "P4UnitCode":
            //	displayStrings[i]=GetProcInfo("UnitCode",4+startProc);
            //	break;
            if (__dummyScrutVar2.equals("P4UnitQty"))
            {
                if (planCur.ShowBaseUnits)
                {
                    short bunit = new short();
                    RefSupport<short> refVar___6 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("BaseUnits",4 + startProc), refVar___6);
                    bunit = refVar___6.getValue();
                    short uqty = new short();
                    RefSupport<short> refVar___7 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("UnitQty",4 + startProc), refVar___7);
                    uqty = refVar___7.getValue();
                    qty = bunit + uqty;
                }
                else if (!StringSupport.equals(getProcInfo("UnitQty",4 + startProc), ""))
                {
                    qty = Int16.Parse(getProcInfo("UnitQty",4 + startProc));
                }
                else
                {
                    qty = 0;
                }  
                if (qty == 0)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = qty.ToString();
                } 
            }
            else if (__dummyScrutVar2.equals("P4CodeAndMods"))
            {
                displayStrings[i] = getProcInfo("Code",4 + startProc) + getProcInfo("CodeMod1",4 + startProc) + getProcInfo("CodeMod2",4 + startProc) + getProcInfo("CodeMod3",4 + startProc) + getProcInfo("CodeMod4",4 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5Date"))
            {
                displayStrings[i] = GetProcInfo("Date", 5 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P5Area"))
            {
                displayStrings[i] = getProcInfo("Area",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5System"))
            {
                displayStrings[i] = getProcInfo("System",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5ToothNumber"))
            {
                displayStrings[i] = getProcInfo("ToothNum",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5Surface"))
            {
                displayStrings[i] = getProcInfo("Surface",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5Code"))
            {
                displayStrings[i] = getProcInfo("Code",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5Description"))
            {
                displayStrings[i] = getProcInfo("Desc",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5Fee"))
            {
                displayStrings[i] = GetProcInfo("Fee", 5 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P5TreatDentMedicaidID"))
            {
                displayStrings[i] = getProcInfo("TreatDentMedicaidID",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5PlaceNumericCode"))
            {
                displayStrings[i] = getProcInfo("PlaceNumericCode",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5Diagnosis"))
            {
                displayStrings[i] = getProcInfo("Diagnosis",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5Lab"))
            {
                displayStrings[i] = getProcInfo("Lab",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5FeeMinusLab"))
            {
                displayStrings[i] = getProcInfo("FeeMinusLab",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5ToothNumOrArea"))
            {
                displayStrings[i] = getProcInfo("ToothNumOrArea",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5TreatProvNPI"))
            {
                displayStrings[i] = getProcInfo("TreatProvNPI",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5RevCode"))
            {
                displayStrings[i] = getProcInfo("RevCode",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5CodeMod1"))
            {
                displayStrings[i] = getProcInfo("CodeMod1",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5CodeMod2"))
            {
                displayStrings[i] = getProcInfo("CodeMod2",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5CodeMod3"))
            {
                displayStrings[i] = getProcInfo("CodeMod3",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P5CodeMod4"))
            {
                displayStrings[i] = getProcInfo("CodeMod4",5 + startProc);
            }
            else //case "P5UnitCode":
            //	displayStrings[i]=GetProcInfo("UnitCode",5+startProc);
            //	break;
            if (__dummyScrutVar2.equals("P5UnitQty"))
            {
                if (planCur.ShowBaseUnits)
                {
                    short bunit = new short();
                    RefSupport<short> refVar___8 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("BaseUnits",5 + startProc), refVar___8);
                    bunit = refVar___8.getValue();
                    short uqty = new short();
                    RefSupport<short> refVar___9 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("UnitQty",5 + startProc), refVar___9);
                    uqty = refVar___9.getValue();
                    qty = bunit + uqty;
                }
                else if (!StringSupport.equals(getProcInfo("UnitQty",5 + startProc), ""))
                {
                    qty = Int16.Parse(getProcInfo("UnitQty",5 + startProc));
                }
                else
                {
                    qty = 0;
                }  
                if (qty == 0)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = qty.ToString();
                } 
            }
            else if (__dummyScrutVar2.equals("P5CodeAndMods"))
            {
                displayStrings[i] = getProcInfo("Code",5 + startProc) + getProcInfo("CodeMod1",5 + startProc) + getProcInfo("CodeMod2",5 + startProc) + getProcInfo("CodeMod3",5 + startProc) + getProcInfo("CodeMod4",5 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6Date"))
            {
                displayStrings[i] = GetProcInfo("Date", 6 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P6Area"))
            {
                displayStrings[i] = getProcInfo("Area",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6System"))
            {
                displayStrings[i] = getProcInfo("System",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6ToothNumber"))
            {
                displayStrings[i] = getProcInfo("ToothNum",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6Surface"))
            {
                displayStrings[i] = getProcInfo("Surface",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6Code"))
            {
                displayStrings[i] = getProcInfo("Code",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6Description"))
            {
                displayStrings[i] = getProcInfo("Desc",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6Fee"))
            {
                displayStrings[i] = GetProcInfo("Fee", 6 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P6TreatDentMedicaidID"))
            {
                displayStrings[i] = getProcInfo("TreatDentMedicaidID",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6PlaceNumericCode"))
            {
                displayStrings[i] = getProcInfo("PlaceNumericCode",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6Diagnosis"))
            {
                displayStrings[i] = getProcInfo("Diagnosis",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6Lab"))
            {
                displayStrings[i] = getProcInfo("Lab",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6FeeMinusLab"))
            {
                displayStrings[i] = getProcInfo("FeeMinusLab",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6ToothNumOrArea"))
            {
                displayStrings[i] = getProcInfo("ToothNumOrArea",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6TreatProvNPI"))
            {
                displayStrings[i] = getProcInfo("TreatProvNPI",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6RevCode"))
            {
                displayStrings[i] = getProcInfo("RevCode",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6CodeMod1"))
            {
                displayStrings[i] = getProcInfo("CodeMod1",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6CodeMod2"))
            {
                displayStrings[i] = getProcInfo("CodeMod2",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6CodeMod3"))
            {
                displayStrings[i] = getProcInfo("CodeMod3",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P6CodeMod4"))
            {
                displayStrings[i] = getProcInfo("CodeMod4",6 + startProc);
            }
            else //case "P6UnitCode":
            //	displayStrings[i]=GetProcInfo("UnitCode",6+startProc);
            //	break;
            if (__dummyScrutVar2.equals("P6UnitQty"))
            {
                if (planCur.ShowBaseUnits)
                {
                    short bunit = new short();
                    RefSupport<short> refVar___10 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("BaseUnits",6 + startProc), refVar___10);
                    bunit = refVar___10.getValue();
                    short uqty = new short();
                    RefSupport<short> refVar___11 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("UnitQty",6 + startProc), refVar___11);
                    uqty = refVar___11.getValue();
                    qty = bunit + uqty;
                }
                else if (!StringSupport.equals(getProcInfo("UnitQty",6 + startProc), ""))
                {
                    qty = Int16.Parse(getProcInfo("UnitQty",6 + startProc));
                }
                else
                {
                    qty = 0;
                }  
                if (qty == 0)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = qty.ToString();
                } 
            }
            else if (__dummyScrutVar2.equals("P6CodeAndMods"))
            {
                displayStrings[i] = getProcInfo("Code",6 + startProc) + getProcInfo("CodeMod1",6 + startProc) + getProcInfo("CodeMod2",6 + startProc) + getProcInfo("CodeMod3",6 + startProc) + getProcInfo("CodeMod4",6 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7Date"))
            {
                displayStrings[i] = GetProcInfo("Date", 7 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P7Area"))
            {
                displayStrings[i] = getProcInfo("Area",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7System"))
            {
                displayStrings[i] = getProcInfo("System",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7ToothNumber"))
            {
                displayStrings[i] = getProcInfo("ToothNum",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7Surface"))
            {
                displayStrings[i] = getProcInfo("Surface",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7Code"))
            {
                displayStrings[i] = getProcInfo("Code",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7Description"))
            {
                displayStrings[i] = getProcInfo("Desc",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7Fee"))
            {
                displayStrings[i] = GetProcInfo("Fee", 7 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P7TreatDentMedicaidID"))
            {
                displayStrings[i] = getProcInfo("TreatDentMedicaidID",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7PlaceNumericCode"))
            {
                displayStrings[i] = getProcInfo("PlaceNumericCode",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7Diagnosis"))
            {
                displayStrings[i] = getProcInfo("Diagnosis",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7Lab"))
            {
                displayStrings[i] = getProcInfo("Lab",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7FeeMinusLab"))
            {
                displayStrings[i] = getProcInfo("FeeMinusLab",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7ToothNumOrArea"))
            {
                displayStrings[i] = getProcInfo("ToothNumOrArea",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7TreatProvNPI"))
            {
                displayStrings[i] = getProcInfo("TreatProvNPI",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7RevCode"))
            {
                displayStrings[i] = getProcInfo("RevCode",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7CodeMod1"))
            {
                displayStrings[i] = getProcInfo("CodeMod1",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7CodeMod2"))
            {
                displayStrings[i] = getProcInfo("CodeMod2",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7CodeMod3"))
            {
                displayStrings[i] = getProcInfo("CodeMod3",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P7CodeMod4"))
            {
                displayStrings[i] = getProcInfo("CodeMod4",7 + startProc);
            }
            else //case "P7UnitCode":
            //	displayStrings[i]=GetProcInfo("UnitCode",7+startProc);
            //	break;
            if (__dummyScrutVar2.equals("P7UnitQty"))
            {
                if (planCur.ShowBaseUnits)
                {
                    short bunit = new short();
                    RefSupport<short> refVar___12 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("BaseUnits",7 + startProc), refVar___12);
                    bunit = refVar___12.getValue();
                    short uqty = new short();
                    RefSupport<short> refVar___13 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("UnitQty",7 + startProc), refVar___13);
                    uqty = refVar___13.getValue();
                    qty = bunit + uqty;
                }
                else if (!StringSupport.equals(getProcInfo("UnitQty",7 + startProc), ""))
                {
                    qty = Int16.Parse(getProcInfo("UnitQty",7 + startProc));
                }
                else
                {
                    qty = 0;
                }  
                if (qty == 0)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = qty.ToString();
                } 
            }
            else if (__dummyScrutVar2.equals("P7CodeAndMods"))
            {
                displayStrings[i] = getProcInfo("Code",7 + startProc) + getProcInfo("CodeMod1",7 + startProc) + getProcInfo("CodeMod2",7 + startProc) + getProcInfo("CodeMod3",7 + startProc) + getProcInfo("CodeMod4",7 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8Date"))
            {
                displayStrings[i] = GetProcInfo("Date", 8 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P8Area"))
            {
                displayStrings[i] = getProcInfo("Area",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8System"))
            {
                displayStrings[i] = getProcInfo("System",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8ToothNumber"))
            {
                displayStrings[i] = getProcInfo("ToothNum",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8Surface"))
            {
                displayStrings[i] = getProcInfo("Surface",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8Code"))
            {
                displayStrings[i] = getProcInfo("Code",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8Description"))
            {
                displayStrings[i] = getProcInfo("Desc",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8Fee"))
            {
                displayStrings[i] = GetProcInfo("Fee", 8 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P8TreatDentMedicaidID"))
            {
                displayStrings[i] = getProcInfo("TreatDentMedicaidID",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8PlaceNumericCode"))
            {
                displayStrings[i] = getProcInfo("PlaceNumericCode",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8Diagnosis"))
            {
                displayStrings[i] = getProcInfo("Diagnosis",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8Lab"))
            {
                displayStrings[i] = getProcInfo("Lab",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8FeeMinusLab"))
            {
                displayStrings[i] = getProcInfo("FeeMinusLab",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8ToothNumOrArea"))
            {
                displayStrings[i] = getProcInfo("ToothNumOrArea",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8TreatProvNPI"))
            {
                displayStrings[i] = getProcInfo("TreatProvNPI",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8RevCode"))
            {
                displayStrings[i] = getProcInfo("RevCode",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8CodeMod1"))
            {
                displayStrings[i] = getProcInfo("CodeMod1",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8CodeMod2"))
            {
                displayStrings[i] = getProcInfo("CodeMod2",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8CodeMod3"))
            {
                displayStrings[i] = getProcInfo("CodeMod3",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P8CodeMod4"))
            {
                displayStrings[i] = getProcInfo("CodeMod4",8 + startProc);
            }
            else //case "P8UnitCode":
            //	displayStrings[i]=GetProcInfo("UnitCode",8+startProc);
            //	break;
            if (__dummyScrutVar2.equals("P8UnitQty"))
            {
                if (planCur.ShowBaseUnits)
                {
                    short bunit = new short();
                    RefSupport<short> refVar___14 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("BaseUnits",8 + startProc), refVar___14);
                    bunit = refVar___14.getValue();
                    short uqty = new short();
                    RefSupport<short> refVar___15 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("UnitQty",8 + startProc), refVar___15);
                    uqty = refVar___15.getValue();
                    qty = bunit + uqty;
                }
                else if (!StringSupport.equals(getProcInfo("UnitQty",8 + startProc), ""))
                {
                    qty = Int16.Parse(getProcInfo("UnitQty",8 + startProc));
                }
                else
                {
                    qty = 0;
                }  
                if (qty == 0)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = qty.ToString();
                } 
            }
            else if (__dummyScrutVar2.equals("P8CodeAndMods"))
            {
                displayStrings[i] = getProcInfo("Code",8 + startProc) + getProcInfo("CodeMod1",8 + startProc) + getProcInfo("CodeMod2",8 + startProc) + getProcInfo("CodeMod3",8 + startProc) + getProcInfo("CodeMod4",8 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9Date"))
            {
                displayStrings[i] = GetProcInfo("Date", 9 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P9Area"))
            {
                displayStrings[i] = getProcInfo("Area",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9System"))
            {
                displayStrings[i] = getProcInfo("System",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9ToothNumber"))
            {
                displayStrings[i] = getProcInfo("ToothNum",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9Surface"))
            {
                displayStrings[i] = getProcInfo("Surface",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9Code"))
            {
                displayStrings[i] = getProcInfo("Code",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9Description"))
            {
                displayStrings[i] = getProcInfo("Desc",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9Fee"))
            {
                displayStrings[i] = GetProcInfo("Fee", 9 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P9TreatDentMedicaidID"))
            {
                displayStrings[i] = getProcInfo("TreatDentMedicaidID",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9PlaceNumericCode"))
            {
                displayStrings[i] = getProcInfo("PlaceNumericCode",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9Diagnosis"))
            {
                displayStrings[i] = getProcInfo("Diagnosis",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9Lab"))
            {
                displayStrings[i] = getProcInfo("Lab",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9FeeMinusLab"))
            {
                displayStrings[i] = getProcInfo("FeeMinusLab",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9ToothNumOrArea"))
            {
                displayStrings[i] = getProcInfo("ToothNumOrArea",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9TreatProvNPI"))
            {
                displayStrings[i] = getProcInfo("TreatProvNPI",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9RevCode"))
            {
                displayStrings[i] = getProcInfo("RevCode",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9CodeMod1"))
            {
                displayStrings[i] = getProcInfo("CodeMod1",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9CodeMod2"))
            {
                displayStrings[i] = getProcInfo("CodeMod2",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9CodeMod3"))
            {
                displayStrings[i] = getProcInfo("CodeMod3",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P9CodeMod4"))
            {
                displayStrings[i] = getProcInfo("CodeMod4",9 + startProc);
            }
            else //case "P9UnitCode":
            //	displayStrings[i]=GetProcInfo("UnitCode",9+startProc);
            //	break;
            if (__dummyScrutVar2.equals("P9UnitQty"))
            {
                if (planCur.ShowBaseUnits)
                {
                    short bunit = new short();
                    RefSupport<short> refVar___16 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("BaseUnits",9 + startProc), refVar___16);
                    bunit = refVar___16.getValue();
                    short uqty = new short();
                    RefSupport<short> refVar___17 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("UnitQty",9 + startProc), refVar___17);
                    uqty = refVar___17.getValue();
                    qty = bunit + uqty;
                }
                else if (!StringSupport.equals(getProcInfo("UnitQty",9 + startProc), ""))
                {
                    qty = Int16.Parse(getProcInfo("UnitQty",9 + startProc));
                }
                else
                {
                    qty = 0;
                }  
                if (qty == 0)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = qty.ToString();
                } 
            }
            else if (__dummyScrutVar2.equals("P9CodeAndMods"))
            {
                displayStrings[i] = getProcInfo("Code",9 + startProc) + getProcInfo("CodeMod1",9 + startProc) + getProcInfo("CodeMod2",9 + startProc) + getProcInfo("CodeMod3",9 + startProc) + getProcInfo("CodeMod4",9 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10Date"))
            {
                displayStrings[i] = GetProcInfo("Date", 10 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P10Area"))
            {
                displayStrings[i] = getProcInfo("Area",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10System"))
            {
                displayStrings[i] = getProcInfo("System",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10ToothNumber"))
            {
                displayStrings[i] = getProcInfo("ToothNum",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10Surface"))
            {
                displayStrings[i] = getProcInfo("Surface",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10Code"))
            {
                displayStrings[i] = getProcInfo("Code",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10Description"))
            {
                displayStrings[i] = getProcInfo("Desc",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10Fee"))
            {
                displayStrings[i] = GetProcInfo("Fee", 10 + startProc, ClaimFormCur.Items[i].FormatString);
            }
            else if (__dummyScrutVar2.equals("P10TreatDentMedicaidID"))
            {
                displayStrings[i] = getProcInfo("TreatDentMedicaidID",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10PlaceNumericCode"))
            {
                displayStrings[i] = getProcInfo("PlaceNumericCode",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10Diagnosis"))
            {
                displayStrings[i] = getProcInfo("Diagnosis",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10Lab"))
            {
                displayStrings[i] = getProcInfo("Lab",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10FeeMinusLab"))
            {
                displayStrings[i] = getProcInfo("FeeMinusLab",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10ToothNumOrArea"))
            {
                displayStrings[i] = getProcInfo("ToothNumOrArea",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10TreatProvNPI"))
            {
                displayStrings[i] = getProcInfo("TreatProvNPI",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10RevCode"))
            {
                displayStrings[i] = getProcInfo("RevCode",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10CodeMod1"))
            {
                displayStrings[i] = getProcInfo("CodeMod1",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10CodeMod2"))
            {
                displayStrings[i] = getProcInfo("CodeMod2",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10CodeMod3"))
            {
                displayStrings[i] = getProcInfo("CodeMod3",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("P10CodeMod4"))
            {
                displayStrings[i] = getProcInfo("CodeMod4",10 + startProc);
            }
            else //case "P10UnitCode":
            //	displayStrings[i]=GetProcInfo("UnitCode",10+startProc);
            //	break;
            if (__dummyScrutVar2.equals("P10UnitQty"))
            {
                if (planCur.ShowBaseUnits)
                {
                    short bunit = new short();
                    RefSupport<short> refVar___18 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("BaseUnits",10 + startProc), refVar___18);
                    bunit = refVar___18.getValue();
                    short uqty = new short();
                    RefSupport<short> refVar___19 = new RefSupport<short>();
                    System.Int16.TryParse(getProcInfo("UnitQty",10 + startProc), refVar___19);
                    uqty = refVar___19.getValue();
                    qty = bunit + uqty;
                }
                else if (!StringSupport.equals(getProcInfo("UnitQty",10 + startProc), ""))
                {
                    qty = Int16.Parse(getProcInfo("UnitQty",10 + startProc));
                }
                else
                {
                    qty = 0;
                }  
                if (qty == 0)
                {
                    displayStrings[i] = "";
                }
                else
                {
                    displayStrings[i] = qty.ToString();
                } 
            }
            else if (__dummyScrutVar2.equals("P10CodeAndMods"))
            {
                displayStrings[i] = getProcInfo("Code",10 + startProc) + getProcInfo("CodeMod1",10 + startProc) + getProcInfo("CodeMod2",10 + startProc) + getProcInfo("CodeMod3",10 + startProc) + getProcInfo("CodeMod4",10 + startProc);
            }
            else if (__dummyScrutVar2.equals("TotalFee"))
            {
                double fee = 0;
                for (int f = startProc;f < startProc + totProcs;f++)
                {
                    //fee only for this page. Each page is treated like a separate claim.
                    //eg f=0;f<10;f++
                    if (f < ListClaimProcs.Count)
                    {
                        fee += (double)((ClaimProc)ListClaimProcs[f]).FeeBilled;
                        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                        {
                            //Canadian. en-CA or fr-CA
                            List<Procedure> labProcs = Procedures.GetCanadianLabFees(ListClaimProcs[f].ProcNum, ListProc);
                            for (int j = 0;j < labProcs.Count;j++)
                            {
                                fee += (double)labProcs[j].ProcFee;
                            }
                        }
                         
                    }
                     
                }
                if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                {
                    displayStrings[i] = fee.ToString("F");
                }
                else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                {
                    displayStrings[i] = fee.ToString("F").Replace(".", " ");
                }
                else
                {
                    displayStrings[i] = fee.ToString(ClaimFormCur.Items[i].FormatString);
                }  
            }
            else if (__dummyScrutVar2.equals("DateService"))
            {
                //only for this page, Earliest proc date.
                DateTime dateService = ((ClaimProc)ListClaimProcs[0]).ProcDate;
                for (int f = startProc;f < startProc + totProcs;f++)
                {
                    //eg f=0;f<10;f++
                    if (f < ListClaimProcs.Count && ((ClaimProc)ListClaimProcs[f]).ProcDate < dateService)
                        dateService = ((ClaimProc)ListClaimProcs[f]).ProcDate;
                     
                }
                if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                {
                    displayStrings[i] = dateService.ToShortDateString();
                }
                else
                {
                    displayStrings[i] = dateService.ToString(ClaimFormCur.Items[i].FormatString);
                } 
            }
                                                                                                                                                                                                                                          
            //switch
            if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "nl-BE") && StringSupport.equals(displayStrings[i], ""))
            {
                //Dutch Belgium
                displayStrings[i] = "*   *   *";
            }
             
        }
    }

    //for i
    private void fillMedValueCodes() throws Exception {
        ListClaimValCodeLog = ClaimValCodeLogs.getForClaim(ClaimCur.ClaimNum);
        if (ListClaimValCodeLog.Count > 0)
        {
            ClaimValCodeLog[] vcA = new ClaimValCodeLog[]();
            vcA = new ClaimValCodeLog[12];
            for (int i = 0;i < ListClaimValCodeLog.Count;i++)
            {
                vcA[i] = (ClaimValCodeLog)ListClaimValCodeLog[i];
            }
            for (int i = ListClaimValCodeLog.Count;i < 12;i++)
            {
                vcA[i] = new ClaimValCodeLog();
            }
            for (int i = 0;i < ClaimFormCur.Items.Length;i++)
            {
                FieldName __dummyScrutVar3 = ClaimFormCur.Items[i].FieldName;
                if (__dummyScrutVar3.equals("MedValCode39a"))
                {
                    displayStrings[i] = vcA[0].ValCode;
                }
                else if (__dummyScrutVar3.equals("MedValAmount39a"))
                {
                    if (vcA[0].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[0].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[0].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                else if (__dummyScrutVar3.equals("MedValCode39b"))
                {
                    if (vcA[3] != null)
                        displayStrings[i] = vcA[3].ValCode;
                     
                }
                else if (__dummyScrutVar3.equals("MedValAmount39b"))
                {
                    if (vcA[3].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[3].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[3].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                else if (__dummyScrutVar3.equals("MedValCode39c"))
                {
                    if (vcA[6] != null)
                        displayStrings[i] = vcA[6].ValCode;
                     
                }
                else if (__dummyScrutVar3.equals("MedValAmount39c"))
                {
                    if (vcA[6].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[6].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[6].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                else if (__dummyScrutVar3.equals("MedValCode39d"))
                {
                    if (vcA[9] != null)
                        displayStrings[i] = vcA[9].ValCode;
                     
                }
                else if (__dummyScrutVar3.equals("MedValAmount39d"))
                {
                    if (vcA[9].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[9].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[9].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                else if (__dummyScrutVar3.equals("MedValCode40a"))
                {
                    if (vcA[1] != null)
                    {
                        displayStrings[i] = vcA[1].ValCode;
                    }
                     
                }
                else if (__dummyScrutVar3.equals("MedValAmount40a"))
                {
                    if (vcA[1].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[1].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[1].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                else if (__dummyScrutVar3.equals("MedValCode40b"))
                {
                    if (vcA[4] != null)
                    {
                        displayStrings[i] = vcA[4].ValCode;
                    }
                     
                }
                else if (__dummyScrutVar3.equals("MedValAmount40b"))
                {
                    if (vcA[4].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[4].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[4].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                else if (__dummyScrutVar3.equals("MedValCode40c"))
                {
                    if (vcA[7] != null)
                    {
                        displayStrings[i] = vcA[7].ValCode;
                    }
                     
                }
                else if (__dummyScrutVar3.equals("MedValAmount40c"))
                {
                    if (vcA[7].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[7].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[7].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                else if (__dummyScrutVar3.equals("MedValCode40d"))
                {
                    if (vcA[10] != null)
                        displayStrings[i] = vcA[10].ValCode;
                     
                }
                else if (__dummyScrutVar3.equals("MedValAmount40d"))
                {
                    if (vcA[10].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[10].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[10].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                else if (__dummyScrutVar3.equals("MedValCode41a"))
                {
                    if (vcA[2] != null)
                        displayStrings[i] = vcA[2].ValCode;
                     
                }
                else if (__dummyScrutVar3.equals("MedValAmount41a"))
                {
                    if (vcA[2].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[2].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[2].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                else if (__dummyScrutVar3.equals("MedValCode41b"))
                {
                    if (vcA[5] != null)
                    {
                        displayStrings[i] = vcA[5].ValCode;
                    }
                     
                }
                else if (__dummyScrutVar3.equals("MedValAmount41b"))
                {
                    if (vcA[5].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[5].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[5].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                else if (__dummyScrutVar3.equals("MedValCode41c"))
                {
                    if (vcA[8] != null)
                    {
                        displayStrings[i] = vcA[8].ValCode;
                    }
                     
                }
                else if (__dummyScrutVar3.equals("MedValAmount41c"))
                {
                    if (vcA[8].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[8].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[8].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                else if (__dummyScrutVar3.equals("MedValCode41d"))
                {
                    if (vcA[11] != null)
                        displayStrings[i] = vcA[11].ValCode;
                     
                }
                else if (__dummyScrutVar3.equals("MedValAmount41d"))
                {
                    if (vcA[11].ValAmount == 0)
                    {
                        displayStrings[i] = "";
                    }
                    else if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, "NoDec"))
                    {
                        displayStrings[i] = vcA[11].ValAmount.ToString("F").Replace(".", " ");
                    }
                    else
                    {
                        displayStrings[i] = vcA[11].ValAmount.ToString(ClaimFormCur.Items[i].FormatString);
                    }  
                }
                                        
            }
        }
         
    }

    private void fillMedCondCodes() throws Exception {
        ClaimCondCodeLog claimCondCodeLog = ClaimCondCodeLogs.getByClaimNum(ClaimCur.ClaimNum);
        if (claimCondCodeLog == null)
        {
            return ;
        }
         
        for (int i = 0;i < ClaimFormCur.Items.Length;i++)
        {
            FieldName __dummyScrutVar4 = ClaimFormCur.Items[i].FieldName;
            if (__dummyScrutVar4.equals("MedConditionCode18"))
            {
                displayStrings[i] = claimCondCodeLog.Code0;
            }
            else if (__dummyScrutVar4.equals("MedConditionCode19"))
            {
                displayStrings[i] = claimCondCodeLog.Code1;
            }
            else if (__dummyScrutVar4.equals("MedConditionCode20"))
            {
                displayStrings[i] = claimCondCodeLog.Code2;
            }
            else if (__dummyScrutVar4.equals("MedConditionCode21"))
            {
                displayStrings[i] = claimCondCodeLog.Code3;
            }
            else if (__dummyScrutVar4.equals("MedConditionCode22"))
            {
                displayStrings[i] = claimCondCodeLog.Code4;
            }
            else if (__dummyScrutVar4.equals("MedConditionCode23"))
            {
                displayStrings[i] = claimCondCodeLog.Code5;
            }
            else if (__dummyScrutVar4.equals("MedConditionCode24"))
            {
                displayStrings[i] = claimCondCodeLog.Code6;
            }
            else if (__dummyScrutVar4.equals("MedConditionCode25"))
            {
                displayStrings[i] = claimCondCodeLog.Code7;
            }
            else if (__dummyScrutVar4.equals("MedConditionCode26"))
            {
                displayStrings[i] = claimCondCodeLog.Code8;
            }
            else if (__dummyScrutVar4.equals("MedConditionCode27"))
            {
                displayStrings[i] = claimCondCodeLog.Code9;
            }
            else if (__dummyScrutVar4.equals("MedConditionCode28"))
            {
                displayStrings[i] = claimCondCodeLog.Code10;
            }
                       
        }
    }

    /**
    * These fields are used for the UB04.
    */
    private void fillMedInsStrings() throws Exception {
        PatPlan patPlan = null;
        for (int i = 0;i < ListPatPlans.Count;i++)
        {
            if (ListPatPlans[i].InsSubNum == ClaimCur.InsSubNum)
            {
                patPlan = ListPatPlans[i];
                break;
            }
             
        }
        String insFilingCodeStr = InsFilingCodes.getEclaimCode(planCur.FilingCode);
        //Determine the slot (A, B or C) where the medical insurance information belongs.
        String insLine = "A";
        if (StringSupport.equals(insFilingCodeStr, "MC"))
        {
            //Medicaid
            insLine = "C";
        }
        else if (patPlan == null)
        {
        }
        else
        {
            //Plan was dropped.
            //We only support one medical plan right now so defaulting to primary should be fine.
            //If not Medicaid, then primary is on line A, secondary is on line B, and Tertiary is on line C.
            if (patPlan.Ordinal == 2)
            {
                insLine = "B";
            }
            else if (patPlan.Ordinal == 3)
            {
                insLine = "C";
            }
              
        }  
        //Determine the prior payments or total payments from other insurance companies.
        double priorPaymentsA = 0;
        double priorPaymentsB = 0;
        for (int i = 0;i < ClaimProcsForClaim.Count;i++)
        {
            for (int j = 0;j < ClaimProcsForPat.Count;j++)
            {
                if (!ClaimProcs.IsValidClaimAdj(ClaimProcsForPat[j], ClaimProcsForClaim[i].ProcNum, ClaimProcsForClaim[i].InsSubNum))
                {
                    continue;
                }
                 
                if (StringSupport.equals(insFilingCodeStr, "MC"))
                {
                    //Medicaid
                    String insFilingCodeClaimProcStr = "";
                    for (int k = 0;k < ListInsPlan.Count;k++)
                    {
                        if (ListInsPlan[k].PlanNum == ClaimProcsForPat[j].PlanNum)
                        {
                            insFilingCodeClaimProcStr = InsFilingCodes.GetEclaimCode(ListInsPlan[k].FilingCode);
                            break;
                        }
                         
                    }
                    if (StringSupport.equals(insFilingCodeClaimProcStr, "16") || StringSupport.equals(insFilingCodeClaimProcStr, "MB"))
                    {
                        //HMO_MedicareRisk and MedicarePartB
                        priorPaymentsA += (double)ClaimProcsForPat[j].InsPayAmt;
                    }
                    else
                    {
                        priorPaymentsB += (double)ClaimProcsForPat[j].InsPayAmt;
                    } 
                }
                else
                {
                    for (int k = 0;k < ListPatPlans.Count;k++)
                    {
                        if (ListPatPlans[k].InsSubNum == ClaimProcsForPat[j].InsSubNum)
                        {
                            if (ListPatPlans[k].Ordinal == 1)
                            {
                                priorPaymentsA += (double)ClaimProcsForPat[j].InsPayAmt;
                            }
                            else if (ListPatPlans[k].Ordinal == 2)
                            {
                                priorPaymentsB += (double)ClaimProcsForPat[j].InsPayAmt;
                            }
                              
                            break;
                        }
                         
                    }
                } 
            }
        }
        for (int i = 0;i < ClaimFormCur.Items.Length;i++)
        {
            String stringFormat = ClaimFormCur.Items[i].FormatString;
            if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedInsCrossoverIndicator"))
            {
                if (StringSupport.equals(insFilingCodeStr, "MB"))
                {
                    //Medicare Part B
                    displayStrings[i] = "XOVR";
                }
                 
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "Name"))
            {
                //MedInsAName, MedInsBName, MedInsCName
                displayStrings[i] = Carriers.getName(planCur.CarrierNum);
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "PlanID"))
            {
            }
            else //MedInsAPlanID, MedInsBPlanID, MedInsCPlanID
            //Not used. Leave blank.
            if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "RelInfo"))
            {
                //MedInsARelInfo, MedInsBRelInfo, MedInsCRelInfo
                displayStrings[i] = subCur.ReleaseInfo ? "X" : "";
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "AssignBen"))
            {
                //MedInsAAssignBen, MedInsBAssignBen, MedInsCAssignBen
                displayStrings[i] = subCur.AssignBen ? "X" : "";
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedInsAPriorPmt"))
            {
                if (priorPaymentsA == 0)
                {
                    continue;
                }
                 
                if (StringSupport.equals(stringFormat, ""))
                {
                    displayStrings[i] = priorPaymentsA.ToString("F");
                }
                else if (StringSupport.equals(stringFormat, "NoDec"))
                {
                    displayStrings[i] = priorPaymentsA.ToString("F").Replace(".", " ");
                }
                else
                {
                    displayStrings[i] = priorPaymentsA.ToString(stringFormat);
                }  
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedInsBPriorPmt"))
            {
                if (priorPaymentsB == 0)
                {
                    continue;
                }
                 
                if (StringSupport.equals(stringFormat, ""))
                {
                    displayStrings[i] = priorPaymentsB.ToString("F");
                }
                else if (StringSupport.equals(stringFormat, "NoDec"))
                {
                    displayStrings[i] = priorPaymentsB.ToString("F").Replace(".", " ");
                }
                else
                {
                    displayStrings[i] = priorPaymentsB.ToString(stringFormat);
                }  
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "AmtDue"))
            {
                //MedInsAAmtDue, MedInsBAmtDue, MedInsCAmtDue
                double totalValAmount = (double)ClaimValCodeLogs.getValAmountTotal(ClaimCur.ClaimNum,"23");
                double amtDue = ((double)ClaimCur.ClaimFee - priorPaymentsA - priorPaymentsB - totalValAmount);
                if (StringSupport.equals(stringFormat, ""))
                {
                    displayStrings[i] = amtDue.ToString("F");
                }
                else if (StringSupport.equals(stringFormat, "NoDec"))
                {
                    displayStrings[i] = amtDue.ToString("F").Replace(".", " ");
                }
                else
                {
                    displayStrings[i] = amtDue.ToString(stringFormat);
                }  
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "OtherProvID"))
            {
                //MedInsAOtherProvID, MedInsBOtherProvID, MedInsCOtherProvID
                String CarrierElectID = carrier.ElectID.ToString();
                Provider prov = ProviderC.getListLong()[Providers.getIndexLong(ClaimCur.ProvBill)];
                if (prov.ProvNum > 0 && !StringSupport.equals(CarrierElectID, "") && ProviderIdents.GetForPayor(prov.ProvNum, CarrierElectID).Length > 0)
                {
                    ProviderIdent provID = ProviderIdents.GetForPayor(prov.ProvNum, CarrierElectID)[0];
                    if (!StringSupport.equals(provID.IDNumber, ""))
                    {
                        displayStrings[i] = provID.IDNumber.ToString();
                    }
                    else
                    {
                        displayStrings[i] = "";
                    } 
                }
                else
                {
                    displayStrings[i] = "";
                } 
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "InsuredName"))
            {
                //MedInsAInsuredName, MedInsBInsuredName, MedInsCInsuredName
                displayStrings[i] = Patients.getPat(subCur.Subscriber).getNameFLFormal();
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "Relation"))
            {
                //MedInsARelation, MedInsBRelation, MedInsCRelation
                if (ClaimCur.PatRelat == Relat.Spouse)
                {
                    displayStrings[i] = "01";
                }
                else if (ClaimCur.PatRelat == Relat.Self)
                {
                    displayStrings[i] = "18";
                }
                else if (ClaimCur.PatRelat == Relat.Child)
                {
                    displayStrings[i] = "19";
                }
                else if (ClaimCur.PatRelat == Relat.Employee)
                {
                    displayStrings[i] = "20";
                }
                else if (ClaimCur.PatRelat == Relat.LifePartner)
                {
                    displayStrings[i] = "53";
                }
                else
                {
                    displayStrings[i] = "G8";
                }     
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "InsuredID"))
            {
                //MedInsAInsuredID, MedInsBInsuredID, MedInsCInsuredID
                displayStrings[i] = subCur.SubscriberID;
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "GroupName"))
            {
                //MedInsAGroupName, MedInsBGroupName, MedInsCGroupName
                displayStrings[i] = planCur.GroupName;
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "GroupNum"))
            {
                //MedInsAGroupNum, MedInsBGroupNum, MedInsCGroupNum
                displayStrings[i] = planCur.GroupNum;
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "AuthCode"))
            {
                //MedInsAAuthCode, MedInsBAuthCode, MedInsCAuthCode
                displayStrings[i] = ClaimCur.PreAuthString;
            }
            else if (StringSupport.equals(ClaimFormCur.Items[i].FieldName, "MedIns" + insLine + "Employer"))
            {
                //MedInsAEmployer, MedInsBEmployer, MedInsCEmployer
                displayStrings[i] = Employers.getName(planCur.EmployerNum);
            }
                            
        }
    }

    /**
    * Uses the fee field to determine how many procedures this claim will print.
    *  @return
    */
    private int procLimitForFormat() throws Exception {
        int retVal = 0;
        for (int i = 0;i < 15;i++)
        {
            for (int j = 0;j < ClaimFormCur.Items.Length;j++)
            {
                //loop until a match is not found.  The max of 10 is built in because of course it will never match to 11 since there is no such fieldName.
                if (StringSupport.equals(ClaimFormCur.Items[j].FieldName, "P" + i.ToString() + "Fee"))
                {
                    retVal = i;
                }
                 
            }
        }
        //for j
        if (retVal == 0)
        {
            for (int i = 0;i < 15;i++)
            {
                for (int j = 0;j < ClaimFormCur.Items.Length;j++)
                {
                    //if claimform doesn't use fees, use procedurecode
                    if (StringSupport.equals(ClaimFormCur.Items[j].FieldName, "P" + i.ToString() + "Code"))
                    {
                        retVal = i;
                    }
                     
                }
            }
        }
         
        //for j
        if (retVal == 0)
        {
            //if STILL zero
            retVal = 10;
        }
         
        return retVal;
    }

    /**
    * Overload that does not need a stringFormat
    *  @param field 
    *  @param procIndex 
    *  @return
    */
    private String getProcInfo(String field, int procIndex) throws Exception {
        return getProcInfo(field,procIndex,"");
    }

    /**
    * Gets the string to be used for this field and index.
    *  @param field 
    *  @param procIndex 
    *  @param stringFormat 
    *  @return
    */
    private String getProcInfo(String field, int procIndex, String stringFormat) throws Exception {
        //remember that procIndex is 1 based, not 0 based,
        procIndex--;
        //so convert to 0 based
        if (ListClaimProcs.Count <= procIndex)
        {
            return "";
        }
         
        //if(CultureInfo.CurrentCulture.Name=="nl-BE"){//Dutch Belgium
        //	return"*   *   *";
        //}
        //else{
        //}
        if (StringSupport.equals(field, "System"))
        {
            return "JP";
        }
         
        if (StringSupport.equals(field, "Code"))
        {
            return ListClaimProcs[procIndex].CodeSent;
        }
         
        if (StringSupport.equals(field, "System"))
        {
            return "JP";
        }
         
        if (StringSupport.equals(field, "Fee"))
        {
            double totalProcFees = (double)ListClaimProcs[procIndex].FeeBilled;
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canadian. en-CA or fr-CA
                List<Procedure> labProcs = Procedures.GetCanadianLabFees(ListClaimProcs[procIndex].ProcNum, ListProc);
                for (int i = 0;i < labProcs.Count;i++)
                {
                    totalProcFees += (double)labProcs[i].ProcFee;
                }
            }
             
            if (StringSupport.equals(stringFormat, ""))
            {
                return totalProcFees.ToString("F");
            }
            else if (StringSupport.equals(stringFormat, "NoDec"))
            {
                return totalProcFees.ToString("F").Replace(".", " ");
            }
              
            return totalProcFees.ToString(stringFormat);
        }
         
        Procedure ProcCur = Procedures.GetProcFromList(ListProc, ListClaimProcs[procIndex].ProcNum);
        ProcedureCode procCode = ProcedureCodes.getProcCode(ProcCur.CodeNum);
        if (StringSupport.equals(field, "RevCode"))
        {
            return ProcCur.RevCode;
        }
         
        if (StringSupport.equals(field, "CodeMod1"))
        {
            return ProcCur.CodeMod1;
        }
         
        if (StringSupport.equals(field, "CodeMod2"))
        {
            return ProcCur.CodeMod2;
        }
         
        if (StringSupport.equals(field, "CodeMod3"))
        {
            return ProcCur.CodeMod3;
        }
         
        if (StringSupport.equals(field, "CodeMod4"))
        {
            return ProcCur.CodeMod4;
        }
         
        //if(field=="UnitCode"){
        //	return ProcCur.UnitCode;
        //}
        if (StringSupport.equals(field, "UnitQty"))
        {
            return ProcCur.UnitQty.ToString();
        }
         
        if (StringSupport.equals(field, "BaseUnits"))
        {
            return ProcCur.BaseUnits.ToString();
        }
         
        if (StringSupport.equals(field, "Desc"))
        {
            if (!StringSupport.equals(procCode.DrugNDC, ""))
            {
                //For UB04, we must show the procedure description as a standard drug format so that the drug can be easily recognized.
                //The DrugNDC field is only used when medical features are turned on so this behavior won't take effect in many circumstances.
                String drugUnit = "UN";
                //Unit
                float drugQty = ProcCur.DrugQty;
                switch(ProcCur.DrugUnit)
                {
                    case Gram: 
                        drugUnit = "GR";
                        break;
                    case InternationalUnit: 
                        drugUnit = "F2";
                        break;
                    case Milligram: 
                        drugUnit = "GR";
                        drugQty = drugQty / 1000;
                        break;
                    case Milliliter: 
                        drugUnit = "ML";
                        break;
                
                }
                return "N4" + procCode.DrugNDC + drugUnit + drugQty.ToString("f3");
            }
            else
            {
                if (procCode.TreatArea == TreatmentArea.Quad)
                {
                    return ProcCur.Surf + " " + procCode.Descript;
                }
                else
                {
                    return procCode.Descript;
                } 
            } 
        }
         
        if (StringSupport.equals(field, "Date"))
        {
            if (StringSupport.equals(ClaimCur.ClaimType, "PreAuth"))
            {
                return "";
            }
             
            //no date on preauth procedures
            if (StringSupport.equals(stringFormat, ""))
            {
                return ListClaimProcs[procIndex].ProcDate.ToShortDateString();
            }
             
            return ListClaimProcs[procIndex].ProcDate.ToString(stringFormat);
        }
         
        if (StringSupport.equals(field, "TreatDentMedicaidID"))
        {
            if (ListClaimProcs[procIndex].ProvNum == 0)
            {
                return "";
            }
            else
                return ProviderC.getListLong()[Providers.GetIndexLong(ListClaimProcs[procIndex].ProvNum)].MedicaidID; 
        }
         
        if (StringSupport.equals(field, "TreatProvNPI"))
        {
            if (ListClaimProcs[procIndex].ProvNum == 0)
            {
                return "";
            }
            else
            {
                return ProviderC.getListLong()[Providers.GetIndexLong(ListClaimProcs[procIndex].ProvNum)].NationalProvID;
            } 
        }
         
        if (StringSupport.equals(field, "PlaceNumericCode"))
        {
            return getPlaceOfServiceNum(ClaimCur.PlaceService);
        }
         
        //(Procedure)Procedures.HList[ClaimProcsForClaim[procIndex].ProcNum];
        //Procedure ProcOld=ProcCur.Clone();
        if (StringSupport.equals(field, "Diagnosis"))
        {
            if (StringSupport.equals(ProcCur.DiagnosticCode, ""))
            {
                return "";
            }
             
            for (int d = 0;d < diagnoses.Length;d++)
            {
                //string diagstr="";//would concat them together, but OD only allows one diagnosis per code anyway.
                if (StringSupport.equals(diagnoses[d], ProcCur.DiagnosticCode))
                {
                    return (d + 1).ToString();
                }
                 
            }
            return ProcCur.DiagnosticCode;
        }
         
        if (StringSupport.equals(field, "Lab"))
        {
            // && ProcCur.LabFee>0) {
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canadian. en-CA or fr-CA
                List<Procedure> labProcs = Procedures.GetCanadianLabFees(ListClaimProcs[procIndex].ProcNum, ListProc);
                double totalLabFees = 0;
                for (int i = 0;i < labProcs.Count;i++)
                {
                    totalLabFees += (double)labProcs[i].ProcFee;
                }
                if (StringSupport.equals(stringFormat, ""))
                {
                    return totalLabFees.ToString("F");
                }
                else if (StringSupport.equals(stringFormat, "NoDec"))
                {
                    return totalLabFees.ToString("F").Replace(".", " ");
                }
                  
                return totalLabFees.ToString(stringFormat);
            }
             
            return "";
        }
         
        //ProcCur.LabFee.ToString("n");
        if (StringSupport.equals(field, "FeeMinusLab"))
        {
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canadian. en-CA or fr-CA
                if (StringSupport.equals(stringFormat, ""))
                {
                    return ListClaimProcs[procIndex].FeeBilled.ToString("F");
                }
                else if (StringSupport.equals(stringFormat, "NoDec"))
                {
                    double amt = ListClaimProcs[procIndex].FeeBilled * 100;
                    return amt.ToString();
                }
                else
                {
                    return ListClaimProcs[procIndex].FeeBilled.ToString(stringFormat);
                }  
            }
             
            return "";
        }
         
        //(((ClaimProc)claimprocs[procIndex]).FeeBilled-ProcCur.LabFee).ToString("n");
        String area = "";
        String toothNum = "";
        String surf = "";
        switch(ProcedureCodes.getProcCode(ProcCur.CodeNum).TreatArea)
        {
            case Surf: 
                //area blank
                toothNum = Tooth.toInternat(ProcCur.ToothNum);
                surf = Tooth.surfTidyForClaims(ProcCur.Surf,ProcCur.ToothNum);
                break;
            case Tooth: 
                //area blank
                toothNum = Tooth.toInternat(ProcCur.ToothNum);
                break;
            case Quad: 
                //surf blank
                area = areaToCode(ProcCur.Surf);
                break;
            case Sextant: 
                //"UL" etc -> 20 etc
                //num blank
                //surf blank
                area = "";
                break;
            case Arch: 
                //leave it blank.  Never used anyway.
                //area="S"+ProcCur.Surf;//area
                //num blank
                //surf blank
                area = areaToCode(ProcCur.Surf);
                break;
            case ToothRange: 
                //area "U", etc
                //num blank
                //surf blank
                //area blank
                toothNum = Tooth.formatRangeForDisplay(ProcCur.ToothRange);
                break;
            default: 
                break;
        
        }
        /*for(int i=0;i<ProcCur.ToothRange.Split(',').Length;i++){
        						if(!Tooth.IsValidDB(ProcCur.ToothRange.Split(',')[i])){
        							continue;
        						}
        						if(i>0){
        							toothNum+=",";
        						}
        						toothNum+=Tooth.ToInternat(ProcCur.ToothRange.Split(',')[i]);
        					}*/
        //surf blank
        //mouth
        //area?
        //switch treatarea
        System.String __dummyScrutVar7 = field;
        if (__dummyScrutVar7.equals("Area"))
        {
            return area;
        }
        else if (__dummyScrutVar7.equals("ToothNum"))
        {
            return toothNum;
        }
        else if (__dummyScrutVar7.equals("Surface"))
        {
            return surf;
        }
        else if (__dummyScrutVar7.equals("ToothNumOrArea"))
        {
            if (!StringSupport.equals(toothNum, ""))
            {
                return toothNum;
            }
            else
            {
                return area;
            } 
        }
            
        MessageBox.Show("error in getprocinfo");
        return "";
    }

    //should never get to here
    private String areaToCode(String area) throws Exception {
        System.String __dummyScrutVar8 = area;
        if (__dummyScrutVar8.equals("U"))
        {
            return "01";
        }
        else if (__dummyScrutVar8.equals("L"))
        {
            return "02";
        }
        else if (__dummyScrutVar8.equals("UR"))
        {
            return "10";
        }
        else if (__dummyScrutVar8.equals("UL"))
        {
            return "20";
        }
        else if (__dummyScrutVar8.equals("LL"))
        {
            return "30";
        }
        else if (__dummyScrutVar8.equals("LR"))
        {
            return "40";
        }
              
        return "";
    }

    private void butBack_Click(Object sender, System.EventArgs e) throws Exception {
        if (Preview2.StartPage == 0)
        {
            return ;
        }
         
        Preview2.StartPage--;
        labelTotPages.Text = (Preview2.StartPage + 1).ToString() + " / " + totalPages.ToString();
    }

    private void butFwd_Click(Object sender, System.EventArgs e) throws Exception {
        if (Preview2.StartPage == totalPages - 1)
        {
            return ;
        }
         
        Preview2.StartPage++;
        labelTotPages.Text = (Preview2.StartPage + 1).ToString() + " / " + totalPages.ToString();
    }

    private void butPrint_Click(Object sender, System.EventArgs e) throws Exception {
        if (printClaim())
        {
            Etranss.SetClaimSentOrPrinted(ClaimNumCur, ClaimCur.PatNum, 0, EtransType.ClaimPrinted, 0);
            //Claims.UpdateStatus(ThisClaimNum,"P");
            DialogResult = DialogResult.OK;
        }
        else
        {
            DialogResult = DialogResult.Cancel;
        } 
    }

    //Close();
    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

}


