//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.RepeatCharge;
import OpenDentBusiness.RepeatCharges;
import OpenDentBusiness.TreatmentArea;
import OpenDental.FormNewCropBilling;

public class FormNewCropBilling  extends Form 
{

    public FormNewCropBilling() throws Exception {
        initializeComponent();
    }

    private void formBillingList_Resize(Object sender, EventArgs e) throws Exception {
        refreshGridColumns();
    }

    private void butBrowse_Click(Object sender, EventArgs e) throws Exception {
        if (openFileDialog1.ShowDialog() == DialogResult.OK)
        {
            textBillingXmlPath.Text = openFileDialog1.FileName;
        }
         
    }

    private void butLoad_Click(Object sender, EventArgs e) throws Exception {
        if (!File.Exists(textBillingXmlPath.Text))
        {
            MessageBox.Show("File does not exist or could not be accessed. Make sure the file is not open in another program and try again.");
            return ;
        }
         
        fillGrid();
    }

    private void refreshGridColumns() throws Exception {
        gridBillingList.beginUpdate();
        gridBillingList.getColumns().Clear();
        int gridWidth = this.Width - 50;
        int patNumWidth = 54;
        //fixed width
        int npiWidth = 70;
        //fixed width
        int yearMonthAddedWidth = 104;
        //fixed width
        int isNewWidth = 46;
        //fixed width
        int typeWidth = 40;
        //fixed width
        int variableWidth = gridWidth - patNumWidth - npiWidth - yearMonthAddedWidth - isNewWidth - typeWidth;
        int practiceTitleWidth = variableWidth / 2;
        //variable width
        int firstLastNameWidth = variableWidth - practiceTitleWidth;
        //variable width
        gridBillingList.getColumns().add(new ODGridColumn("PatNum", patNumWidth, HorizontalAlignment.Center));
        //0
        gridBillingList.getColumns().add(new ODGridColumn("NPI", npiWidth, HorizontalAlignment.Center));
        //1
        gridBillingList.getColumns().add(new ODGridColumn("YearMonthBilling", yearMonthAddedWidth, HorizontalAlignment.Center));
        //2
        gridBillingList.getColumns().add(new ODGridColumn("Type", typeWidth, HorizontalAlignment.Center));
        //3
        gridBillingList.getColumns().add(new ODGridColumn("IsNew", isNewWidth, HorizontalAlignment.Center));
        //4
        gridBillingList.getColumns().add(new ODGridColumn("PracticeTitle", practiceTitleWidth, HorizontalAlignment.Left));
        //5
        gridBillingList.getColumns().add(new ODGridColumn("FirstLastName", firstLastNameWidth, HorizontalAlignment.Left));
        //6
        gridBillingList.endUpdate();
    }

    private void fillGrid() throws Exception {
        try
        {
            String xmlData = File.ReadAllText(textBillingXmlPath.Text);
            xmlData = xmlData.Replace("&nbsp;", "");
            XmlDocument xml = new XmlDocument();
            xml.LoadXml(xmlData);
            XmlNode divNode = xml.FirstChild;
            XmlNode tableNode = divNode.FirstChild;
            refreshGridColumns();
            gridBillingList.beginUpdate();
            gridBillingList.getRows().Clear();
            for (int i = 1;i < tableNode.ChildNodes.Count;i++)
            {
                //Skip the first row, because it contains the column names.
                OpenDental.UI.ODGridRow gr = new OpenDental.UI.ODGridRow();
                XmlNode trNode = tableNode.ChildNodes[i];
                //0 PatNum
                String shortName = trNode.ChildNodes[1].InnerText;
                int accountIdStartIndex = shortName.IndexOf("-") + 1;
                int accountIdLength = shortName.Substring(accountIdStartIndex).LastIndexOf("-");
                String accountId = shortName.Substring(accountIdStartIndex, accountIdLength);
                int patNumLength = accountId.IndexOf("-");
                String patNumStr = PIn.String(accountId.Substring(0, patNumLength));
                if (StringSupport.equals(patNumStr, "6566"))
                {
                    continue;
                }
                 
                //Account 6566 corresponds to our software key in the training database. These accounts are test accounts.
                //Do not show OD test accounts.
                long patNum = PIn.Long(patNumStr);
                gr.getCells().Add(new OpenDental.UI.ODGridCell(patNumStr));
                //1 NPI
                String npi = PIn.String(trNode.ChildNodes[8].InnerText);
                gr.getCells().Add(new OpenDental.UI.ODGridCell(npi));
                //2 YearMonthAdded
                gr.getCells().Add(new OpenDental.UI.ODGridCell(trNode.ChildNodes[9].InnerText));
                //3 Type
                gr.getCells().Add(new OpenDental.UI.ODGridCell(trNode.ChildNodes[10].InnerText));
                //4 IsNew
                List<RepeatCharge> RepeatChargesCur = RepeatCharges.getForNewCrop(patNum);
                RepeatCharge repeatChargeForNpi = GetRepeatChargeForNPI(RepeatChargesCur, npi);
                gr.getCells().Add(new OpenDental.UI.ODGridCell((repeatChargeForNpi == null) ? "X" : ""));
                //5 PracticeTitle
                gr.getCells().Add(new OpenDental.UI.ODGridCell(trNode.ChildNodes[0].InnerText));
                //6 FirstLastName
                gr.getCells().Add(new OpenDental.UI.ODGridCell(trNode.ChildNodes[2].InnerText));
                gridBillingList.getRows().add(gr);
            }
            gridBillingList.endUpdate();
        }
        catch (Exception ex)
        {
            MessageBox.Show("There is something wrong with the input file. Try again. If issue persists, then contact a programmer: " + ex.Message);
        }
    
    }

    /**
    * Searches the repeatChargesCur list for the NewCrop repeating charge related to the given npi.
    * A repeating charge is a match if the note beings with "NPIs=" followed by the given npi, or if the note simply starts with the npi.
    * Returns null if no match found.
    */
    private RepeatCharge getRepeatChargeForNPI(List<RepeatCharge> repeatChargesCur, String npi) throws Exception {
        for (int i = 0;i < repeatChargesCur.Count;i++)
        {
            RepeatCharge rc = repeatChargesCur[i];
            String note = rc.Note.Trim();
            if (note.ToUpper().StartsWith("NPI="))
            {
                //Case insensitive check
                note = note.Substring(4);
            }
             
            //Remove the leading NPI=
            if (note.StartsWith(npi))
            {
                return rc;
            }
             
        }
        return null;
    }

    /**
    * Returns the code NewCrop or a code like NewCrop##, depending on which codes are already in use for the current patnum.
    * The returned code is guaranteed to exist in the database, because codes are created if they do not exist.
    */
    private String getProcCodeForNewCharge(List<RepeatCharge> repeatChargesCur) throws Exception {
        //Locate a proc code for NewCrop which is not already in use.
        String procCode = "NewCrop";
        int attempts = 1;
        boolean procCodeInUse = new boolean();
        do
        {
            procCodeInUse = false;
            for (int i = 0;i < repeatChargesCur.Count;i++)
            {
                if (StringSupport.equals(repeatChargesCur[i].ProcCode, procCode))
                {
                    procCodeInUse = true;
                    break;
                }
                 
            }
            if (procCodeInUse)
            {
                attempts++;
                //Should start at 2. The Codes will be "NewCrop", "NewCrop02", "NewCrop03", etc...
                if (attempts > 99)
                {
                    throw new Exception("Cannot add more than 99 NewCrop repeating charges yet. Ask programmer to increase.");
                }
                 
                procCode = "NewCrop" + (attempts.ToString().PadLeft(2, '0'));
            }
             
        }
        while (procCodeInUse);
        //If the selected code is not in the database already, then add it automatically.
        long codeNum = ProcedureCodes.getCodeNum(procCode);
        if (codeNum == 0)
        {
            //The selected code does not exist, so we must add it.
            ProcedureCode code = new ProcedureCode();
            code.ProcCode = procCode;
            code.Descript = "NewCrop Rx";
            code.AbbrDesc = "NewCrop";
            code.ProcTime = "/X/";
            code.ProcCat = 162;
            //Software
            code.TreatArea = TreatmentArea.Mouth;
            ProcedureCodes.insert(code);
            ProcedureCodes.refreshCache();
        }
         
        return procCode;
    }

    private int getChargeDayOfMonth(long patNum) throws Exception {
        //Match the day of the month for the NewCrop repeating charge to their existing monthly support charge (even if the monthly support is disabled).
        int day = 15;
        //Day 15 will be used if they do not have any existing repeating charges.
        RepeatCharge[] chargesForPat = RepeatCharges.refresh(patNum);
        boolean hasMaintCharge = false;
        for (int j = 0;j < chargesForPat.Length;j++)
        {
            if (StringSupport.equals(chargesForPat[j].ProcCode, "001"))
            {
                //Monthly maintenance repeating charge
                hasMaintCharge = true;
                day = chargesForPat[j].DateStart.Day;
                break;
            }
             
        }
        //The customer is not on monthly support, so use any other existing repeating charge day (example EHR Monthly and Mobile).
        if (!hasMaintCharge && chargesForPat.Length > 0)
        {
            day = chargesForPat[0].DateStart.Day;
        }
         
        return day;
    }

    private void butProcess_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"This will add a new repeating charge for each provider in the list above" + " who is new (does not already have a repeating charge), based on PatNum and NPI.  Continue?"))
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        int numChargesAdded = 0;
        int numSkipped = 0;
        for (int i = 0;i < gridBillingList.getRows().Count;i++)
        {
            long patNum = PIn.Long(gridBillingList.getRows().get___idx(i).getCells()[0].Text);
            String npi = PIn.String(gridBillingList.getRows().get___idx(i).getCells()[1].Text);
            String billingType = gridBillingList.getRows().get___idx(i).getCells()[3].Text;
            List<RepeatCharge> repeatChargesNewCrop = RepeatCharges.getForNewCrop(patNum);
            RepeatCharge repeatCur = GetRepeatChargeForNPI(repeatChargesNewCrop, npi);
            if (repeatCur == null)
            {
                //No such repeating charge exists yet for the given npi.
                //We consider the provider a new provider and create a new repeating charge.
                String yearMonth = gridBillingList.getRows().get___idx(i).getCells()[2].Text;
                int yearBilling = PIn.Int(yearMonth.Substring(0, 4));
                //The year chosen by the OD employee when running the NewCrop Billing report.
                int monthBilling = PIn.Int(yearMonth.Substring(4));
                //The month chosen by the OD employee when running the NewCrop Billing report.
                int dayOtherCharges = getChargeDayOfMonth(patNum);
                //The day of the month that the customer already has other repeating charges. Keeps their billing simple (one bill per month for all charges).
                DateTime dateNewCropCharge = new DateTime(yearBilling, monthBilling, dayOtherCharges);
                if (dateNewCropCharge < DateTime.Today.AddMonths(-3))
                {
                    //Just in case the user runs an older report.
                    numSkipped++;
                    continue;
                }
                 
                repeatCur = new RepeatCharge();
                repeatCur.setIsNew(true);
                repeatCur.PatNum = patNum;
                repeatCur.ProcCode = GetProcCodeForNewCharge(repeatChargesNewCrop);
                repeatCur.ChargeAmt = 15;
                //15$/month
                repeatCur.DateStart = dateNewCropCharge;
                repeatCur.Note = "NPI=" + npi;
                repeatCur.IsEnabled = true;
                RepeatCharges.insert(repeatCur);
                numChargesAdded++;
            }
            else
            {
                //The repeating charge for NewCrop billing already exists for the given npi.
                DateTime dateEndLastMonth = (new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1)).AddDays(-1);
                if (StringSupport.equals(billingType, "B") || StringSupport.equals(billingType, "N"))
                {
                    //The provider sent eRx last month.
                    if (repeatCur.DateStop.Year > 2010)
                    {
                        //NewCrop support for this provider was disabled at one point, but has been used since.
                        if (repeatCur.DateStop < dateEndLastMonth)
                        {
                            //If the stop date is in the future or already at the end of the month, then we cannot presume that there will be a charge next month.
                            repeatCur.DateStop = dateEndLastMonth;
                            //Make sure the recent use is reflected in the end date.
                            RepeatCharges.update(repeatCur);
                        }
                         
                    }
                     
                }
                else if (StringSupport.equals(billingType, "U"))
                {
                }
                else
                {
                    throw new Exception("Unknown NewCrop Billing type " + billingType);
                }  
            } 
        }
        //The provider did not send eRx last month, but did send eRx two months ago.
        //Customers must call in to disable repeating charges, they are not disabled automatically.
        fillGrid();
        Cursor = Cursors.Default;
        String msg = "Done. Number of provider charges added: " + numChargesAdded;
        if (numSkipped > 0)
        {
            msg += Environment.NewLine + "Number skipped due to old DateBilling (over 3 months ago): " + numSkipped;
        }
         
        MessageBox.Show(msg);
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormNewCropBilling.class);
        this.gridBillingList = new OpenDental.UI.ODGrid();
        this.textBillingXmlPath = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
        this.butLoad = new OpenDental.UI.Button();
        this.butBrowse = new OpenDental.UI.Button();
        this.butProcess = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridBillingList
        //
        this.gridBillingList.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridBillingList.setHScrollVisible(false);
        this.gridBillingList.Location = new System.Drawing.Point(12, 59);
        this.gridBillingList.Name = "gridBillingList";
        this.gridBillingList.setScrollValue(0);
        this.gridBillingList.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridBillingList.Size = new System.Drawing.Size(958, 604);
        this.gridBillingList.TabIndex = 0;
        this.gridBillingList.setTitle("Providers Using NewCrop");
        this.gridBillingList.setTranslationName(null);
        //
        // textBillingXmlPath
        //
        this.textBillingXmlPath.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textBillingXmlPath.Location = new System.Drawing.Point(12, 32);
        this.textBillingXmlPath.Name = "textBillingXmlPath";
        this.textBillingXmlPath.Size = new System.Drawing.Size(826, 20);
        this.textBillingXmlPath.TabIndex = 10;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(9, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(812, 16);
        this.label1.TabIndex = 9;
        this.label1.Text = "Billing.xml file path. Must be downloaded from NewCrop customer portal. See Wiki " + "for instructions. Use the Load button to populate the grid after a file is selec" + "ted.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // openFileDialog1
        //
        this.openFileDialog1.FileName = "Billing.xml";
        //
        // butLoad
        //
        this.butLoad.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLoad.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butLoad.setAutosize(true);
        this.butLoad.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLoad.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLoad.setCornerRadius(4F);
        this.butLoad.Location = new System.Drawing.Point(895, 30);
        this.butLoad.Name = "butLoad";
        this.butLoad.Size = new System.Drawing.Size(75, 23);
        this.butLoad.TabIndex = 12;
        this.butLoad.Text = "Load";
        this.butLoad.UseVisualStyleBackColor = true;
        this.butLoad.Click += new System.EventHandler(this.butLoad_Click);
        //
        // butBrowse
        //
        this.butBrowse.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowse.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butBrowse.setAutosize(true);
        this.butBrowse.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowse.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowse.setCornerRadius(4F);
        this.butBrowse.Location = new System.Drawing.Point(844, 30);
        this.butBrowse.Name = "butBrowse";
        this.butBrowse.Size = new System.Drawing.Size(32, 23);
        this.butBrowse.TabIndex = 11;
        this.butBrowse.Text = "...";
        this.butBrowse.UseVisualStyleBackColor = true;
        this.butBrowse.Click += new System.EventHandler(this.butBrowse_Click);
        //
        // butProcess
        //
        this.butProcess.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProcess.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butProcess.setAutosize(true);
        this.butProcess.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProcess.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProcess.setCornerRadius(4F);
        this.butProcess.Location = new System.Drawing.Point(814, 669);
        this.butProcess.Name = "butProcess";
        this.butProcess.Size = new System.Drawing.Size(75, 23);
        this.butProcess.TabIndex = 3;
        this.butProcess.Text = "Process";
        this.butProcess.UseVisualStyleBackColor = true;
        this.butProcess.Click += new System.EventHandler(this.butProcess_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(895, 669);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormNewCropBilling
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(982, 707);
        this.Controls.Add(this.butLoad);
        this.Controls.Add(this.butBrowse);
        this.Controls.Add(this.textBillingXmlPath);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butProcess);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridBillingList);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(950, 700);
        this.Name = "FormNewCropBilling";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "NewCrop Billing";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Resize += new System.EventHandler(this.FormBillingList_Resize);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.ODGrid gridBillingList;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butProcess;
    private OpenDental.UI.Button butBrowse;
    private System.Windows.Forms.TextBox textBillingXmlPath = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butLoad;
    private System.Windows.Forms.OpenFileDialog openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
}


