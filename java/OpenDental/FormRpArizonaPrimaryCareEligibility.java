//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.PatientRaceOld;
import OpenDentBusiness.PatientRaces;
import OpenDentBusiness.Reports;
import OpenDental.FormRpArizonaPrimaryCareEligibility;

public class FormRpArizonaPrimaryCareEligibility  extends Form 
{

    public FormRpArizonaPrimaryCareEligibility() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formRpArizonaPrimaryCareEligibility_Load(Object sender, EventArgs e) throws Exception {
        dateTimeTo.Value = DateTime.Today;
        dateTimeFrom.Value = DateTime.Today.AddMonths(-1);
    }

    private void butFinished_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butBrowse_Click(Object sender, EventArgs e) throws Exception {
        if (folderEligibilityPath.ShowDialog() == DialogResult.OK)
        {
            textEligibilityFolder.Text = folderEligibilityPath.SelectedPath;
        }
         
    }

    private void butCopy_Click(Object sender, EventArgs e) throws Exception {
        Clipboard.SetText(this.textLog.Text);
    }

    private void butRun_Click(Object sender, EventArgs e) throws Exception {
        this.textLog.Text = "";
        String outFile = CodeBase.ODFileUtils.CombinePaths(textEligibilityFolder.Text, textEligibilityFile.Text);
        if (File.Exists(outFile))
        {
            if (MessageBox.Show("The file at " + outFile + " already exists. Overwrite?", "Overwrite File?", MessageBoxButtons.YesNo) != DialogResult.Yes)
            {
                return ;
            }
             
        }
         
        String outputText = "";
        String patientsIdNumberStr = "SPID#";
        String householdGrossIncomeStr = "Household Gross Income";
        String householdPercentOfPovertyStr = "Household % of Poverty";
        String statusStr = "Eligibility Status";
        String command = "";
        //Locate the payment definition number for copayments of patients using the Arizona Primary Care program.
        command = "SELECT DefNum FROM definition WHERE Category=" + POut.Long(((Enum)DefCat.PaymentTypes).ordinal()) + " AND IsHidden=0 AND LOWER(TRIM(ItemName))='noah'";
        DataTable copayDefNumTab = Reports.getTable(command);
        if (copayDefNumTab.Rows.Count != 1)
        {
            MessageBox.Show("You must define exactly one payment type with the name 'NOAH' before running this report. " + "This payment type must be used on payments made by Arizona Primary Care patients.");
            return ;
        }
         
        long copayDefNum = PIn.Long(copayDefNumTab.Rows[0][0].ToString());
        //Get the list of all Arizona Primary Care patients, based on the patients which have an insurance carrier named 'noah'
        command = "SELECT DISTINCT p.PatNum FROM patplan pp,inssub,insplan i,patient p,carrier c " + "WHERE p.PatNum=pp.PatNum AND inssub.InsSubNum=pp.InsSubNum AND inssub.PlanNum=i.PlanNum AND i.CarrierNum=c.CarrierNum " + "AND LOWER(TRIM(c.CarrierName))='noah' AND " + "(SELECT MAX(a.AptDateTime) FROM appointment a WHERE a.PatNum=p.PatNum AND a.AptStatus=" + (((Enum)ApptStatus.Complete).ordinal()) + ") BETWEEN " + POut.Date(dateTimeFrom.Value) + " AND " + POut.Date(dateTimeTo.Value);
        DataTable primaryCarePatients = Reports.getTable(command);
        for (int i = 0;i < primaryCarePatients.Rows.Count;i++)
        {
            String patNum = POut.Long(PIn.Long(primaryCarePatients.Rows[i][0].ToString()));
            //Patient's Care ID Number
            //Marital status
            //"CASE p.Race WHEN "+((int)PatientRaceOld.Asian)+" THEN 'A' WHEN "+((int)PatientRaceOld.HispanicLatino)+" THEN 'H' "+
            //  "WHEN "+((int)PatientRaceOld.HawaiiOrPacIsland)+" THEN 'P' WHEN "+((int)PatientRaceOld.AfricanAmerican)+" THEN 'B' "+
            //  "WHEN "+((int)PatientRaceOld.AmericanIndian)+" THEN 'I' WHEN "+((int)PatientRaceOld.White)+" THEN 'W' ELSE 'O' END PatRace,"+
            //Patient address
            //Household residence city
            //Household residence state
            //Household residence zip code
            //Household gross income
            //Household % of poverty
            //Household sliding fee scale
            //Date of eligibility status
            //Status
            command = "SELECT " + "TRIM((SELECT f.FieldValue FROM patfield f WHERE f.PatNum=p.PatNum AND " + "LOWER(f.FieldName)=LOWER('" + patientsIdNumberStr + "') " + DbHelper.limitAnd(1) + ")) PCIN, " + "TRIM((" + DbHelper.limitOrderBy("SELECT cl.Description FROM appointment ap,clinic cl WHERE ap.PatNum=" + patNum + " AND " + "ap.AptStatus=" + (((Enum)ApptStatus.Complete).ordinal()) + " AND ap.ClinicNum=cl.ClinicNum ORDER BY ap.AptDateTime DESC",1) + ")) SiteIDNumber," + "p.BirthDate," + "CASE p.Position WHEN " + (((Enum)PatientPosition.Single).ordinal()) + " THEN 1 " + "WHEN " + (((Enum)PatientPosition.Married).ordinal()) + " THEN 2 ELSE 3 END MaritalStatus," + "CONCAT(CONCAT(TRIM(p.Address),' '),TRIM(p.Address2)) HouseholdAddress," + "p.City HouseholdCity," + "p.State HouseholdState," + "p.Zip HouseholdZip," + "TRIM((SELECT f.FieldValue FROM patfield f WHERE f.PatNum=p.PatNum AND " + "LOWER(f.FieldName)=LOWER('" + householdGrossIncomeStr + "') " + DbHelper.limitAnd(1) + ")) HGI, " + "TRIM((SELECT f.FieldValue FROM patfield f WHERE f.PatNum=p.PatNum AND " + "LOWER(f.FieldName)=LOWER('" + householdPercentOfPovertyStr + "') " + DbHelper.limitAnd(1) + ")) HPP, " + "(" + DbHelper.limitOrderBy("SELECT a.AdjAmt FROM adjustment a WHERE a.PatNum=" + patNum + " AND a.AdjType=" + copayDefNum + " ORDER BY AdjDate DESC",1) + ") HSFS," + "(SELECT i.DateEffective FROM insplan i,inssub,patplan pp WHERE pp.PatNum=" + patNum + " AND inssub.InsSubNum=pp.InsSubNum AND inssub.PlanNum=i.PlanNum " + DbHelper.limitAnd(1) + ") DES," + "TRIM((SELECT f.FieldValue FROM patfield f WHERE f.PatNum=p.PatNum AND " + "LOWER(f.FieldName)=LOWER('" + statusStr + "') " + DbHelper.limitAnd(1) + ")) CareStatus " + "FROM patient p WHERE " + "p.PatNum=" + patNum;
            DataTable primaryCareReportRow = Reports.getTable(command);
            if (primaryCareReportRow.Rows.Count != 1)
            {
                continue;
            }
             
            //Either the results are ambiguous or for some reason, the patient number listed in the patfield table
            //does not actually exist. In either of these cases, it makes the most sense to just skip this patient
            //and continue with the rest of the reporting.
            String outputRow = "";
            String rowErrors = "";
            String rowWarnings = "";
            String pcin = PIn.String(primaryCareReportRow.Rows[0]["PCIN"].ToString());
            if (pcin.Length < 9)
            {
                rowErrors += "ERROR: Incorrectly formatted patient data for patient with patnum " + patNum + ". Patient ID Number '" + pcin + "' is not at least 9 characters long." + Environment.NewLine;
            }
             
            outputRow += pcin.PadLeft(15, '0');
            //Patient's ID Number
            String siteId = primaryCareReportRow.Rows[0]["SiteIDNumber"].ToString();
            if (StringSupport.equals(siteId, "null"))
            {
                siteId = "";
            }
             
            if (!Regex.IsMatch(siteId, "^.*_[0-9]{5}$"))
            {
                rowErrors += "ERROR: The clinic description for the clinic associated with the last completed appointment " + "for the patient with a patnum of " + patNum + " must be the clinic name, follwed by a '_', followed by the 5-digit Site ID Number " + "for the clinic. i.e. ClinicName_12345. The current clinic description is '" + siteId + "'." + Environment.NewLine;
            }
            else
            {
                siteId = siteId.Substring(siteId.Length - 5);
            } 
            outputRow += siteId;
            outputRow += PIn.Date(primaryCareReportRow.Rows[0]["Birthdate"].ToString()).ToString("MMddyyyy");
            //Patient's Date of Birth
            outputRow += POut.Long(PIn.Long(primaryCareReportRow.Rows[0]["MaritalStatus"].ToString()));
            //outputRow+=primaryCareReportRow.Rows[0]["PatRace"].ToString();
            //Gets the old patient race enum based on the PatientRace entries in the db and displays the corresponding letters.
            OpenDentBusiness.PatientRaces.APPLY __dummyScrutVar0 = PatientRaces.GetPatientRaceOldFromPatientRaces(PIn.Long(patNum));
            if (__dummyScrutVar0.equals(PatientRaceOld.Asian))
            {
                outputRow += 'A';
            }
            else if (__dummyScrutVar0.equals(PatientRaceOld.HispanicLatino))
            {
                outputRow += 'H';
            }
            else if (__dummyScrutVar0.equals(PatientRaceOld.HawaiiOrPacIsland))
            {
                outputRow += 'P';
            }
            else if (__dummyScrutVar0.equals(PatientRaceOld.AfricanAmerican))
            {
                outputRow += 'B';
            }
            else if (__dummyScrutVar0.equals(PatientRaceOld.AmericanIndian))
            {
                outputRow += 'I';
            }
            else if (__dummyScrutVar0.equals(PatientRaceOld.White))
            {
                outputRow += 'W';
            }
            else
            {
                outputRow += 'O';
            }      
            //Household residence address
            String householdAddress = POut.String(PIn.String(primaryCareReportRow.Rows[0]["HouseholdAddress"].ToString()));
            if (householdAddress.Length > 29)
            {
                String newHouseholdAddress = householdAddress.Substring(0, 29);
                rowWarnings += "WARNING: Address for patient with patnum of " + patNum + " was longer than 29 characters and " + "was truncated in the report ouput. Address was changed from '" + householdAddress + "' to '" + newHouseholdAddress + "'" + Environment.NewLine;
                householdAddress = newHouseholdAddress;
            }
             
            outputRow += householdAddress.PadRight(29, ' ');
            //Household residence city
            String householdCity = POut.String(PIn.String(primaryCareReportRow.Rows[0]["HouseholdCity"].ToString()));
            if (householdCity.Length > 15)
            {
                String newHouseholdCity = householdCity.Substring(0, 15);
                rowWarnings += "WARNING: City name for patient with patnum of " + patNum + " was longer than 15 characters and " + "was truncated in the report ouput. City name was changed from '" + householdCity + "' to '" + newHouseholdCity + "'" + Environment.NewLine;
                householdCity = newHouseholdCity;
            }
             
            outputRow += householdCity.PadRight(15, ' ');
            //Household residence state
            String householdState = POut.String(PIn.String(primaryCareReportRow.Rows[0]["HouseholdState"].ToString()));
            if (householdState.Length > 2)
            {
                String newHouseholdState = householdState.Substring(0, 2);
                rowWarnings += "WARNING: State abbreviation for patient with patnum of " + patNum + " was longer than 2 characters and " + "was truncated in the report ouput. State abbreviation was changed from '" + householdState + "' to '" + newHouseholdState + "'" + Environment.NewLine;
                householdState = newHouseholdState;
            }
             
            outputRow += householdState.PadRight(2, ' ');
            //Household residence zip code
            String householdZip = POut.String(PIn.String(primaryCareReportRow.Rows[0]["HouseholdZip"].ToString()));
            if (householdZip.Length > 5)
            {
                String newHouseholdZip = householdZip.Substring(0, 5);
                rowWarnings += "WARNING: The zipcode for patient with patnum of " + patNum + " was longer than 5 characters and " + "was truncated in the report ouput. The zipcode was changed from '" + householdZip + "' to '" + newHouseholdZip + "'" + Environment.NewLine;
                householdZip = newHouseholdZip;
            }
             
            outputRow += householdZip.PadRight(5, ' ');
            //Household gross income
            String householdGrossIncome = POut.String(PIn.String(primaryCareReportRow.Rows[0]["HGI"].ToString()));
            if (StringSupport.equals(householdGrossIncome, "") || StringSupport.equals(householdGrossIncome, "null"))
            {
                householdGrossIncome = "0";
            }
             
            //Remove any character that is not a digit or a decimal.
            String newHouseholdGrossIncome = Math.Round(Convert.ToDouble(Regex.Replace(householdGrossIncome, "[^0-9\\.]", "")), 0).ToString();
            if (!StringSupport.equals(householdGrossIncome, newHouseholdGrossIncome))
            {
                rowWarnings += "WARNING: The household gross income for patient with patnum " + patNum + " contained invalid characters " + "and was changed in the output report from '" + householdGrossIncome + "' to '" + newHouseholdGrossIncome + "'." + Environment.NewLine;
            }
             
            householdGrossIncome = newHouseholdGrossIncome.PadLeft(7, '0');
            if (householdGrossIncome.Length > 7)
            {
                rowErrors += "ERROR: Abnormally large household gross income of '" + householdGrossIncome + "' for patient with patnum of " + patNum + "." + Environment.NewLine;
            }
             
            outputRow += householdGrossIncome;
            //Household percent of poverty
            String householdPercentPoverty = POut.String(PIn.String(primaryCareReportRow.Rows[0]["HPP"].ToString()));
            if (StringSupport.equals(householdPercentPoverty, "") || StringSupport.equals(householdPercentPoverty, "null"))
            {
                householdPercentPoverty = "0";
            }
             
            String newHouseholdPercentPoverty = Regex.Replace(householdPercentPoverty, "[^0-9]", "");
            //Remove anything that is not a digit.
            if (!StringSupport.equals(newHouseholdPercentPoverty, householdPercentPoverty))
            {
                rowWarnings += "WARNING: Household percent poverty for the patient with a patnum of " + patNum + " had to be modified in the output report from '" + householdPercentPoverty + "' to '" + newHouseholdPercentPoverty + "' based on output requirements." + Environment.NewLine;
            }
             
            householdPercentPoverty = newHouseholdPercentPoverty.PadLeft(3, '0');
            if (householdPercentPoverty.Length > 3 || Convert.ToInt16(householdPercentPoverty) > 200)
            {
                rowErrors += "ERROR: Household percent poverty must be between 0 and 200 percent, but is set to '" + householdPercentPoverty + "' for the patient with the patnum of " + patNum + Environment.NewLine;
            }
             
            outputRow += householdPercentPoverty;
            String householdSlidingFeeScale = POut.String(PIn.String(primaryCareReportRow.Rows[0]["HSFS"].ToString()));
            if (householdSlidingFeeScale.Length == 0)
            {
                householdSlidingFeeScale = "0";
            }
             
            String newHouseholdSlidingFeeScale = Regex.Replace(householdSlidingFeeScale, "[^0-9]", "");
            if (!StringSupport.equals(newHouseholdSlidingFeeScale, householdSlidingFeeScale))
            {
                rowWarnings += "WARNING: The household sliding fee scale (latest NOAH copay amount) for the patient with a patnum of " + patNum + " contains invalid characters and was changed from '" + householdSlidingFeeScale + "' to '" + newHouseholdSlidingFeeScale + "'." + Environment.NewLine;
                householdSlidingFeeScale = newHouseholdSlidingFeeScale;
            }
             
            if (householdSlidingFeeScale.Length > 3 || Convert.ToInt16(householdSlidingFeeScale) > 100)
            {
                rowWarnings += "WARNING: The household sliding fee scale (latest NOAH copay amount) for the patient with a patnum of " + patNum + " is '" + householdSlidingFeeScale + "', but will be reported as 100." + Environment.NewLine;
                householdSlidingFeeScale = "100";
            }
             
            outputRow += householdSlidingFeeScale.PadLeft(3, '0');
            String dateOfEligibilityStatusStr = primaryCareReportRow.Rows[0]["DES"].ToString();
            DateTime dateOfEligibilityStatus = DateTime.MinValue;
            if (!StringSupport.equals(dateOfEligibilityStatusStr, "") && !StringSupport.equals(dateOfEligibilityStatusStr, "null"))
            {
                dateOfEligibilityStatus = PIn.Date(dateOfEligibilityStatusStr);
            }
             
            outputRow += dateOfEligibilityStatus.ToString("MMddyyyy");
            //Primary care status
            String primaryCareStatus = POut.String(PIn.String(primaryCareReportRow.Rows[0]["CareStatus"].ToString())).ToUpper();
            if (!StringSupport.equals(primaryCareStatus, "A") && !StringSupport.equals(primaryCareStatus, "B") && !StringSupport.equals(primaryCareStatus, "C") && !StringSupport.equals(primaryCareStatus, "D"))
            {
                rowErrors += "ERROR: The primary care status of the patient with a patnum of " + patNum + " is set to '" + primaryCareStatus + "', but must be set to A, B, C or D. " + Environment.NewLine;
            }
             
            outputRow += primaryCareStatus;
            textLog.Text += rowErrors + rowWarnings;
            if (rowErrors.Length > 0)
            {
                continue;
            }
             
            outputText += outputRow + Environment.NewLine;
        }
        //Only add the row to the output file if it is properly formatted.
        File.WriteAllText(outFile, outputText);
        MessageBox.Show("Done.");
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpArizonaPrimaryCareEligibility.class);
        this.textEligibilityFolder = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textLog = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.folderEligibilityPath = new System.Windows.Forms.FolderBrowserDialog();
        this.label3 = new System.Windows.Forms.Label();
        this.textEligibilityFile = new System.Windows.Forms.TextBox();
        this.dateTimeFrom = new System.Windows.Forms.DateTimePicker();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label5 = new System.Windows.Forms.Label();
        this.dateTimeTo = new System.Windows.Forms.DateTimePicker();
        this.label4 = new System.Windows.Forms.Label();
        this.butRun = new OpenDental.UI.Button();
        this.butCopy = new OpenDental.UI.Button();
        this.butBrowse = new OpenDental.UI.Button();
        this.butFinished = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // textEligibilityFolder
        //
        this.textEligibilityFolder.Location = new System.Drawing.Point(12, 31);
        this.textEligibilityFolder.Name = "textEligibilityFolder";
        this.textEligibilityFolder.Size = new System.Drawing.Size(607, 20);
        this.textEligibilityFolder.TabIndex = 4;
        this.textEligibilityFolder.Text = "C:\\Temp";
        //
        // label1
        //
        this.label1.AutoSize = true;
        this.label1.Location = new System.Drawing.Point(12, 12);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(77, 13);
        this.label1.TabIndex = 6;
        this.label1.Text = "Save report to:";
        //
        // textLog
        //
        this.textLog.Location = new System.Drawing.Point(12, 194);
        this.textLog.Multiline = true;
        this.textLog.Name = "textLog";
        this.textLog.ReadOnly = true;
        this.textLog.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textLog.Size = new System.Drawing.Size(607, 284);
        this.textLog.TabIndex = 8;
        //
        // label2
        //
        this.label2.AutoSize = true;
        this.label2.Location = new System.Drawing.Point(13, 176);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(60, 13);
        this.label2.TabIndex = 9;
        this.label2.Text = "Report Log";
        //
        // label3
        //
        this.label3.AutoSize = true;
        this.label3.Location = new System.Drawing.Point(13, 57);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(89, 13);
        this.label3.TabIndex = 10;
        this.label3.Text = "Output File Name";
        //
        // textEligibilityFile
        //
        this.textEligibilityFile.Location = new System.Drawing.Point(12, 74);
        this.textEligibilityFile.Name = "textEligibilityFile";
        this.textEligibilityFile.ReadOnly = true;
        this.textEligibilityFile.Size = new System.Drawing.Size(164, 20);
        this.textEligibilityFile.TabIndex = 11;
        this.textEligibilityFile.Text = "ApcEligibility.txt";
        //
        // dateTimeFrom
        //
        this.dateTimeFrom.Location = new System.Drawing.Point(6, 42);
        this.dateTimeFrom.Name = "dateTimeFrom";
        this.dateTimeFrom.Size = new System.Drawing.Size(200, 20);
        this.dateTimeFrom.TabIndex = 14;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.dateTimeTo);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.dateTimeFrom);
        this.groupBox1.Location = new System.Drawing.Point(12, 100);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(603, 71);
        this.groupBox1.TabIndex = 15;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Date of Last Completed Appointment";
        //
        // label5
        //
        this.label5.AutoSize = true;
        this.label5.Location = new System.Drawing.Point(262, 22);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(20, 13);
        this.label5.TabIndex = 17;
        this.label5.Text = "To";
        //
        // dateTimeTo
        //
        this.dateTimeTo.Location = new System.Drawing.Point(262, 41);
        this.dateTimeTo.Name = "dateTimeTo";
        this.dateTimeTo.Size = new System.Drawing.Size(200, 20);
        this.dateTimeTo.TabIndex = 16;
        //
        // label4
        //
        this.label4.AutoSize = true;
        this.label4.Location = new System.Drawing.Point(7, 23);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(30, 13);
        this.label4.TabIndex = 15;
        this.label4.Text = "From";
        //
        // butRun
        //
        this.butRun.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRun.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butRun.setAutosize(true);
        this.butRun.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRun.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRun.setCornerRadius(4F);
        this.butRun.Location = new System.Drawing.Point(625, 369);
        this.butRun.Name = "butRun";
        this.butRun.Size = new System.Drawing.Size(75, 24);
        this.butRun.TabIndex = 13;
        this.butRun.Text = "Run";
        this.butRun.Click += new System.EventHandler(this.butRun_Click);
        //
        // butCopy
        //
        this.butCopy.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopy.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCopy.setAutosize(true);
        this.butCopy.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopy.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopy.setCornerRadius(4F);
        this.butCopy.Location = new System.Drawing.Point(625, 323);
        this.butCopy.Name = "butCopy";
        this.butCopy.Size = new System.Drawing.Size(83, 24);
        this.butCopy.TabIndex = 12;
        this.butCopy.Text = "Copy Log Text";
        this.butCopy.Click += new System.EventHandler(this.butCopy_Click);
        //
        // butBrowse
        //
        this.butBrowse.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowse.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butBrowse.setAutosize(true);
        this.butBrowse.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowse.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowse.setCornerRadius(4F);
        this.butBrowse.Location = new System.Drawing.Point(625, 28);
        this.butBrowse.Name = "butBrowse";
        this.butBrowse.Size = new System.Drawing.Size(75, 24);
        this.butBrowse.TabIndex = 7;
        this.butBrowse.Text = "Browse";
        this.butBrowse.Click += new System.EventHandler(this.butBrowse_Click);
        //
        // butFinished
        //
        this.butFinished.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butFinished.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butFinished.setAutosize(true);
        this.butFinished.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFinished.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFinished.setCornerRadius(4F);
        this.butFinished.Location = new System.Drawing.Point(625, 413);
        this.butFinished.Name = "butFinished";
        this.butFinished.Size = new System.Drawing.Size(75, 24);
        this.butFinished.TabIndex = 3;
        this.butFinished.Text = "&Finished";
        this.butFinished.Click += new System.EventHandler(this.butFinished_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(625, 454);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormRpArizonaPrimaryCareEligibility
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(725, 505);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butRun);
        this.Controls.Add(this.butCopy);
        this.Controls.Add(this.textEligibilityFile);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textLog);
        this.Controls.Add(this.butBrowse);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textEligibilityFolder);
        this.Controls.Add(this.butFinished);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormRpArizonaPrimaryCareEligibility";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Arizona Primary Care Eligibility File Report";
        this.Load += new System.EventHandler(this.FormRpArizonaPrimaryCareEligibility_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butFinished;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textEligibilityFolder = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butBrowse;
    private System.Windows.Forms.TextBox textLog = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.FolderBrowserDialog folderEligibilityPath = new System.Windows.Forms.FolderBrowserDialog();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textEligibilityFile = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butCopy;
    private OpenDental.UI.Button butRun;
    private System.Windows.Forms.DateTimePicker dateTimeFrom = new System.Windows.Forms.DateTimePicker();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.DateTimePicker dateTimeTo = new System.Windows.Forms.DateTimePicker();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
}


