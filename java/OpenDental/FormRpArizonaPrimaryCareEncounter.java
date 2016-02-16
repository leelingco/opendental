//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.DatabaseType;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Reports;
import OpenDental.FormRpArizonaPrimaryCareEncounter;

public class FormRpArizonaPrimaryCareEncounter  extends Form 
{

    public FormRpArizonaPrimaryCareEncounter() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butBrowse_Click(Object sender, EventArgs e) throws Exception {
        if (folderEncounter.ShowDialog() == DialogResult.OK)
        {
            this.textEncounterFolder.Text = this.folderEncounter.SelectedPath;
        }
         
    }

    private void butCopy_Click(Object sender, EventArgs e) throws Exception {
        Clipboard.SetText(this.textLog.Text);
    }

    private void butRun_Click(Object sender, EventArgs e) throws Exception {
        //The encounter file is a list of all of the appointments provided for Arizona Primary Care patients within the specified
        //date range. Since each encounter/appointment is reimbursed by the local government at a flat rate, we only need to report
        //a single procedure for each appointment in the encounter file and if there is a question by the government as to the other
        //procedures that were performed during a particular appointment, then the dental office can simply look that information up
        //in Open Dental (but no such calls will likely happen). Thus we always use the same Diagnosis code corresponding to the single
        //ADA code that we emit in this flat file, just to keep things simple and workable.
        this.textLog.Text = "";
        String outFile = CodeBase.ODFileUtils.CombinePaths(this.textEncounterFolder.Text, this.textEncounterFile.Text);
        if (File.Exists(outFile))
        {
            if (MessageBox.Show("The file at " + outFile + " already exists. Overwrite?", "Overwrite File?", MessageBoxButtons.YesNo) != DialogResult.Yes)
            {
                return ;
            }
             
        }
         
        String command = "";
        //Locate the payment definition number for payments of patients using the Arizona Primary Care program.
        command = "SELECT DefNum FROM definition WHERE Category=" + POut.Long(((Enum)DefCat.PaymentTypes).ordinal()) + " AND IsHidden=0 AND LOWER(TRIM(ItemName))='noah'";
        DataTable payDefNumTab = Reports.getTable(command);
        if (payDefNumTab.Rows.Count != 1)
        {
            MessageBox.Show("You must define exactly one payment type with the name 'NOAH' before running this report. " + "This payment type must be used on payments made by Arizona Primary Care patients.");
            return ;
        }
         
        long payDefNum = PIn.Long(payDefNumTab.Rows[0][0].ToString());
        String outputText = "";
        String patientsIdNumberStr = "SPID#";
        //Only certain procedures can be billed to the Arizona Primary Care program.
        //Since the code list doesn't change often, it is simply hard coded here.
        String billableProcedures = "'D0120','D0140','D0150','D0160','D1110','D1120','D1201','D1203','D1204','D1205','D1208'," + "'D1351','D1510','D1515','D1520','D1525','D1550','D4341','D4355','D4910','D2140','D2150','D2160','D2161'," + "'D2330','D2331','D2332','D2335','D2390','D2391','D2392','D2393','D2394','D2910','D2920','D2930','D2931'," + "'D2932','D2940','D2950','D2970','D3110','D3120','D3220','D3221','D3230','D3240','D7140','D7210','D7220'," + "'D7270','D7285','D7286','D7510','D9110','D9310','D9610'";
        //Get the list of all Arizona Primary Care patients, based on the patients which have an insurance carrier named 'noah'
        command = "SELECT DISTINCT p.PatNum FROM patplan pp,inssub,insplan i,patient p,carrier c " + "WHERE p.PatNum=pp.PatNum AND inssub.InsSubNum=pp.InsSubNum AND inssub.PlanNum=i.PlanNum AND i.CarrierNum=c.CarrierNum " + "AND LOWER(TRIM(c.CarrierName))='noah'";
        DataTable primaryCarePatients = Reports.getTable(command);
        for (int i = 0;i < primaryCarePatients.Rows.Count;i++)
        {
            String patNum = POut.Long(PIn.Long(primaryCarePatients.Rows[i][0].ToString()));
            //Now that we have an Arizona Primary Care patient's patNum, we need to see if there are any appointments
            //that the patient has attented (completed) in the date range specified where there is at least one ADA coded procedure
            //associated with that appointment. If there are, then those appointments will be placed into the flat file.
            command = "SELECT a.AptNum FROM appointment a WHERE a.PatNum=" + patNum + " AND a.AptStatus=" + (((Enum)ApptStatus.Complete).ordinal()) + " AND " + "a.AptDateTime BETWEEN " + POut.Date(dateTimeFrom.Value) + " AND " + POut.Date(dateTimeTo.Value) + " AND " + "(SELECT COUNT(*) FROM procedurelog pl,procedurecode pc WHERE pl.AptNum=a.AptNum AND pc.CodeNum=pl.CodeNum AND " + "pc.ProcCode IN (" + billableProcedures + ") " + DbHelper.limitAnd(1) + ")>0";
            DataTable appointmentList = Reports.getTable(command);
            for (int j = 0;j < appointmentList.Rows.Count;j++)
            {
                String aptNum = POut.Long(PIn.Long(appointmentList.Rows[j][0].ToString()));
                String datesql = "CURDATE()";
                if (OpenDentBusiness.DataConnection.DBtype == DatabaseType.Oracle)
                {
                    datesql = "(SELECT CURRENT_DATE FROM dual)";
                }
                 
                //Patient's Care ID Number
                //birthdate
                //Gender
                //address
                //city
                //state
                //zipcode
                //Relationship to subscriber
                //Marital status
                //student status
                //insurance plan name
                //Name of referring physician
                //ID # of referring physician
                //Diagnosis Code 1. Always set to V72.2 for simplicity and workability
                //Diagnosis code 2
                //Diagnosis code 3
                //Diagnosis code 4
                //Date of encounter
                //Procedure modifier 1
                //Procedure modifier 2
                //Diagnosis code
                //2nd procedure cpt/hcpcs
                //2nd procedure modifier 1
                //2nd procedure modifier 2
                //Diagnosis code
                //charges
                //3rd procedure cpt/hcpcs
                //3rd procedure modifier 1
                //3rd procedure modifier 2
                //Diagnosis code
                //Charges
                //4th procedure cpt/hcpcs
                //4th procedure modifier 1
                //4th procedure modifier 2
                //Diagnosis code
                //Charges
                //5th procedure cpt/hcpcs
                //5th procedure modifier 1
                //5th procedure modifier 2
                //diagnosis code
                //Charges
                //6th procedure cpt/hcpcs
                //6th procedure modifier 1
                //6th procedure modifier 2
                //Diagnosis code
                //Charges
                //Total charges
                //Amount paid
                //Balance due
                //Physician's first name and middle initial
                //Physician's last name
                command = "SELECT " + "TRIM((SELECT f.FieldValue FROM patfield f WHERE f.PatNum=p.PatNum AND " + "LOWER(f.FieldName)=LOWER('" + patientsIdNumberStr + "') " + DbHelper.limitAnd(1) + ")) PCIN, " + "p.BirthDate," + "(CASE p.Gender WHEN 0 THEN 'M' WHEN 1 THEN 'F' ELSE '' END) Gender," + "CONCAT(CONCAT(p.Address,' '),p.Address2) Address," + "p.City," + "p.State," + "p.Zip," + "(SELECT CASE pp.Relationship WHEN 0 THEN 1 ELSE 0 END FROM patplan pp,inssub,insplan i,carrier c WHERE " + "pp.PatNum=" + patNum + " AND inssub.InsSubNum=pp.InsSubNum AND inssub.PlanNum=i.PlanNum AND i.CarrierNum=c.CarrierNum AND LOWER(TRIM(c.CarrierName))='noah' " + DbHelper.limitAnd(1) + ") InsRelat," + "(CASE p.Position WHEN 0 THEN 1 WHEN 1 THEN 2 ELSE 3 END) MaritalStatus," + "(CASE WHEN p.EmployerNum=0 THEN (CASE WHEN (" + DbHelper.dateAddYear("p.BirthDate","18") + ">" + datesql + ") THEN 3 ELSE 2 END) ELSE 1 END) EmploymentStatus," + "(CASE p.StudentStatus WHEN 'f' THEN 1 WHEN 'p' THEN 2 ELSE 3 END) StudentStatus," + "'ADHS PCP' InsurancePlanName," + "'' ReferringPhysicianName," + "'' ReferringPhysicianID," + "'V722' DiagnosisCode1," + "'' DiagnosisCode2," + "'' DiagnosisCode3," + "'' DiagnosisCode4," + "(SELECT a.AptDateTime FROM appointment a WHERE a.AptNum=" + aptNum + " " + DbHelper.limitAnd(1) + ") DateOfEncounter," + "(" + DbHelper.limitOrderBy("SELECT pc.ProcCode FROM procedurecode pc,procedurelog pl " + "WHERE pl.AptNum=" + aptNum + " AND pl.CodeNum=pc.CodeNum AND pc.ProcCode IN (" + billableProcedures + ") ORDER BY pl.ProcNum",1) + ") Procedure1," + "'' Procedure1Modifier1," + "'' Procedure1Modifier2," + "'' Procedure1DiagnosisCode," + "(" + DbHelper.limitOrderBy("SELECT pl.ProcFee FROM procedurecode pc,procedurelog pl " + "WHERE pl.AptNum=" + aptNum + " AND pl.CodeNum=pc.CodeNum AND pc.ProcCode IN (" + billableProcedures + ") ORDER BY pl.ProcNum",1) + ") Procedure1Charges," + "'' Procedure2," + "'' Procedure2Modifier1," + "'' Procedure2Modifier2," + "'' Procedure2DiagnosisCode," + "0 Procedure2Charges," + "'' Procedure3," + "'' Procedure3Modifier1," + "'' Procedure3Modifier2," + "'' Procedure3DiagnosisCode," + "0 Procedure3Charges," + "'' Procedure4," + "'' Procedure4Modifier1," + "'' Procedure4Modifier2," + "'' Procedure4DiagnosisCode," + "0 Procedure4Charges," + "'' Procedure5," + "'' Procedure5Modifier1," + "'' Procedure5Modifier2," + "'' Procedure5DiagnosisCode," + "0 Procedure5Charges," + "'' Procedure6," + "'' Procedure6Modifier1," + "'' Procedure6Modifier2," + "'' Procedure6DiagnosisCode," + "0 Procedure6Charges," + "(SELECT SUM(pl.ProcFee) FROM procedurelog pl WHERE pl.AptNum=" + aptNum + ") TotalCharges," + "(SELECT SUM(a.AdjAmt) FROM adjustment a WHERE a.PatNum=" + patNum + " AND a.AdjType=" + payDefNum + ") AmountPaid," + "0 BalanceDue," + "TRIM((SELECT cl.Description FROM appointment ap,clinic cl WHERE ap.AptNum=" + aptNum + " AND " + "ap.ClinicNum=cl.ClinicNum " + DbHelper.limitAnd(1) + ")) ClinicDescription," + "(SELECT pr.StateLicense FROM provider pr,appointment ap WHERE ap.AptNum=" + aptNum + " AND pr.ProvNum=ap.ProvNum " + DbHelper.limitAnd(1) + ") PhysicianID," + "(SELECT CONCAT(CONCAT(pr.FName,' '),pr.MI) FROM provider pr,appointment ap " + "WHERE ap.AptNum=" + aptNum + " AND pr.ProvNum=ap.ProvNum " + DbHelper.limitAnd(1) + ") PhysicianFAndMNames," + "(SELECT pr.LName FROM provider pr,appointment ap " + "WHERE ap.AptNum=" + aptNum + " AND pr.ProvNum=ap.ProvNum " + DbHelper.limitAnd(1) + ") PhysicianLName " + "FROM patient p WHERE " + "p.PatNum=" + patNum;
                DataTable primaryCareReportRow = Reports.getTable(command);
                String outputRow = "";
                String rowErrors = "";
                String rowWarnings = "";
                //Patient's ID Number
                String pcin = primaryCareReportRow.Rows[0]["PCIN"].ToString();
                if (pcin.Length < 9)
                {
                    rowErrors += "ERROR: Incorrectly formatted patient data for patient with patnum " + patNum + ". Patient ID Number '" + pcin + "' is not at least 9 characters long." + Environment.NewLine;
                }
                 
                outputRow += pcin.PadLeft(15, '0');
                //Patient's date of birth
                outputRow += PIn.Date(primaryCareReportRow.Rows[0]["Birthdate"].ToString()).ToString("MMddyyyy");
                //Patient's gender
                outputRow += PIn.String(primaryCareReportRow.Rows[0]["Gender"].ToString());
                //Patient's address
                String householdAddress = POut.String(PIn.String(primaryCareReportRow.Rows[0]["Address"].ToString()));
                if (householdAddress.Length > 29)
                {
                    String newHouseholdAddress = householdAddress.Substring(0, 29);
                    rowWarnings += "WARNING: Address for patient with patnum of " + patNum + " was longer than 29 characters and " + "was truncated in the report ouput. Address was changed from '" + householdAddress + "' to '" + newHouseholdAddress + "'" + Environment.NewLine;
                    householdAddress = newHouseholdAddress;
                }
                 
                outputRow += householdAddress.PadRight(29, ' ');
                //Patient's city
                String householdCity = POut.String(PIn.String(primaryCareReportRow.Rows[0]["City"].ToString()));
                if (householdCity.Length > 15)
                {
                    String newHouseholdCity = householdCity.Substring(0, 15);
                    rowWarnings += "WARNING: City name for patient with patnum of " + patNum + " was longer than 15 characters and " + "was truncated in the report ouput. City name was changed from '" + householdCity + "' to '" + newHouseholdCity + "'" + Environment.NewLine;
                    householdCity = newHouseholdCity;
                }
                 
                outputRow += householdCity.PadRight(15, ' ');
                //Patient's State
                String householdState = POut.String(PIn.String(primaryCareReportRow.Rows[0]["State"].ToString()));
                if (!StringSupport.equals(householdState.ToUpper(), "AZ"))
                {
                    rowErrors += "ERROR: State abbreviation for patient with patnum of " + patNum + " must be set to AZ." + Environment.NewLine;
                    householdState = "AZ";
                }
                 
                outputRow += householdState;
                //Patient's zip code
                String householdZip = POut.String(PIn.String(primaryCareReportRow.Rows[0]["Zip"].ToString()));
                if (householdZip.Length > 5)
                {
                    String newHouseholdZip = householdZip.Substring(0, 5);
                    rowWarnings += "WARNING: The zipcode for patient with patnum of " + patNum + " was longer than 5 characters in length and " + "was truncated in the report ouput. The zipcode was changed from '" + householdZip + "' to '" + newHouseholdZip + "'" + Environment.NewLine;
                    householdZip = newHouseholdZip;
                }
                 
                if (householdZip.Length < 5)
                {
                    rowWarnings += "WARNING: The zipcode for patient with patnum of " + patNum + " was shorter than 5 characters in length " + "(current zipcode is '" + householdZip + "')" + Environment.NewLine;
                    householdZip = householdZip.PadLeft(5, '0');
                }
                 
                outputRow += householdZip.PadRight(5, ' ');
                //Patient's relationship to insured.
                String insuranceRelationship = POut.String(PIn.String(primaryCareReportRow.Rows[0]["InsRelat"].ToString()));
                if (!StringSupport.equals(insuranceRelationship, "1"))
                {
                    //Not self?
                    rowWarnings += "WARNING: The patient insurance relationship is not 'self' for the patient with a patnum of " + patNum + Environment.NewLine;
                }
                 
                outputRow += insuranceRelationship;
                //Patient's marital status
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["MaritalStatus"].ToString()));
                //Patient's employment status
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["EmploymentStatus"].ToString()));
                //Patient's student status
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["StudentStatus"].ToString()));
                //Insurance plan name
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["InsurancePlanName"].ToString())).PadRight(25, ' ');
                //Name of referring physician.
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["ReferringPhysicianName"].ToString())).PadRight(26, ' ');
                //ID# of referring physician
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["ReferringPhysicianID"].ToString())).PadLeft(6, ' ');
                //Diagnosis code 1
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["DiagnosisCode1"].ToString())).PadRight(6, '0');
                //Diagnosis code 2
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["DiagnosisCode2"].ToString())).PadRight(6, '0');
                //Diagnosis code 3
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["DiagnosisCode3"].ToString())).PadRight(6, '0');
                //Diagnosis code 4
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["DiagnosisCode4"].ToString())).PadRight(6, '0');
                //Date of encounter
                outputRow += PIn.Date(primaryCareReportRow.Rows[0]["DateOfEncounter"].ToString()).ToString("MMddyyyy");
                //Procedure 1
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure1"].ToString())).PadRight(5, '0');
                //Procedure 1 modifier 1
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure1Modifier1"].ToString())).PadRight(2, '0');
                //Procedure 1 modifier 2
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure1Modifier2"].ToString())).PadRight(2, '0');
                //Procedure 1 diagnosis code
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure1DiagnosisCode"].ToString())).PadRight(4, '0');
                //Procedure 1 charges
                outputRow += Math.Round(PIn.Double(primaryCareReportRow.Rows[0]["Procedure1Charges"].ToString())).ToString().PadLeft(6, '0');
                //Procedure 2
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure2"].ToString())).PadRight(5, '0');
                //Procedure 2 modifier 1
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure2Modifier1"].ToString())).PadRight(2, '0');
                //Procedure 2 modifier 2
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure2Modifier2"].ToString())).PadRight(2, '0');
                //Procedure 2 diagnosis code
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure2DiagnosisCode"].ToString())).PadRight(4, '0');
                //Procedure 2 charges
                outputRow += Math.Round(PIn.Double(primaryCareReportRow.Rows[0]["Procedure2Charges"].ToString())).ToString().PadLeft(6, '0');
                //Procedure 3
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure3"].ToString())).PadRight(5, '0');
                //Procedure 3 modifier 1
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure3Modifier1"].ToString())).PadRight(2, '0');
                //Procedure 3 modifier 2
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure3Modifier2"].ToString())).PadRight(2, '0');
                //Procedure 3 diagnosis code
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure3DiagnosisCode"].ToString())).PadRight(4, '0');
                //Procedure 3 charges
                outputRow += Math.Round(PIn.Double(primaryCareReportRow.Rows[0]["Procedure3Charges"].ToString())).ToString().PadLeft(6, '0');
                //Procedure 4
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure4"].ToString())).PadRight(5, '0');
                //Procedure 4 modifier 1
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure4Modifier1"].ToString())).PadRight(2, '0');
                //Procedure 4 modifier 2
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure4Modifier2"].ToString())).PadRight(2, '0');
                //Procedure 4 diagnosis code
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure4DiagnosisCode"].ToString())).PadRight(4, '0');
                //Procedure 4 charges
                outputRow += Math.Round(PIn.Double(primaryCareReportRow.Rows[0]["Procedure4Charges"].ToString())).ToString().PadLeft(6, '0');
                //Procedure 5
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure5"].ToString())).PadRight(5, '0');
                //Procedure 5 modifier 1
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure5Modifier1"].ToString())).PadRight(2, '0');
                //Procedure 5 modifier 2
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure5Modifier2"].ToString())).PadRight(2, '0');
                //Procedure 5 diagnosis code
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure5DiagnosisCode"].ToString())).PadRight(4, '0');
                //Procedure 5 charges
                outputRow += Math.Round(PIn.Double(primaryCareReportRow.Rows[0]["Procedure5Charges"].ToString())).ToString().PadLeft(6, '0');
                //Procedure 6
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure6"].ToString())).PadRight(5, '0');
                //Procedure 6 modifier 1
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure6Modifier1"].ToString())).PadRight(2, '0');
                //Procedure 6 modifier 2
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure6Modifier2"].ToString())).PadRight(2, '0');
                //Procedure 6 diagnosis code
                outputRow += POut.String(PIn.String(primaryCareReportRow.Rows[0]["Procedure6DiagnosisCode"].ToString())).PadRight(4, '0');
                //Procedure 6 charges
                outputRow += Math.Round(PIn.Double(primaryCareReportRow.Rows[0]["Procedure6Charges"].ToString())).ToString().PadLeft(6, '0');
                //Total charges
                outputRow += Math.Round(PIn.Double(primaryCareReportRow.Rows[0]["TotalCharges"].ToString())).ToString().PadLeft(7, '0');
                //Amount paid
                outputRow += Math.Round(PIn.Double(primaryCareReportRow.Rows[0]["AmountPaid"].ToString())).ToString().PadLeft(7, '0');
                //Balance due
                outputRow += Math.Round(PIn.Double(primaryCareReportRow.Rows[0]["BalanceDue"].ToString())).ToString().PadLeft(7, '0');
                //Facility site number
                String siteId = PIn.String(primaryCareReportRow.Rows[0]["ClinicDescription"].ToString());
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
                //Physician ID
                String physicianId = PIn.String(primaryCareReportRow.Rows[0]["PhysicianID"].ToString());
                if (physicianId.Length > 12)
                {
                    String newPhysicianId = physicianId.Substring(0, 12);
                    rowWarnings += "WARNING: The physician ID '" + physicianId + "' of the provider associated to the patient with a patnum of '" + patNum + "' is longer than 12 digits. The physician id has been truncated from '" + physicianId + "' to '" + newPhysicianId + "'." + Environment.NewLine;
                    physicianId = newPhysicianId;
                }
                 
                outputRow += physicianId.PadLeft(12, '0');
                //Pysician's First Name and Middle Initial
                String physicianFirstAndMiddle = PIn.String(primaryCareReportRow.Rows[0]["PhysicianFAndMNames"].ToString());
                if (physicianFirstAndMiddle.Length > 12)
                {
                    String newPhysicianFirstAndMiddle = physicianFirstAndMiddle.Substring(0, 12);
                    rowWarnings += "WARNING: The physician first name and middle initial of the provider associated to the patient with " + "a patnum of '" + patNum + "' was truncated from '" + physicianFirstAndMiddle + "' to '" + newPhysicianFirstAndMiddle + "'." + Environment.NewLine;
                    physicianFirstAndMiddle = newPhysicianFirstAndMiddle;
                }
                 
                outputRow += physicianFirstAndMiddle.PadRight(12, ' ');
                //Physician's last name.
                String physicianLastName = PIn.String(primaryCareReportRow.Rows[0]["PhysicianLName"].ToString());
                if (physicianLastName.Length > 20)
                {
                    String newPhysicianLastName = physicianLastName.Substring(0, 20);
                    rowWarnings += "WARNING: The physician last name of the provider associated to the patient with a patnum of '" + patNum + "' " + "was truncated from '" + physicianLastName + "' to '" + newPhysicianLastName + "'." + Environment.NewLine;
                    physicianLastName = newPhysicianLastName;
                }
                 
                outputRow += physicianLastName.PadRight(20, ' ');
                //Finish adding the row to the output file and log warnings and errors.
                textLog.Text += rowErrors + rowWarnings;
                if (rowErrors.Length > 0)
                {
                    continue;
                }
                 
                outputText += outputRow + Environment.NewLine;
            }
        }
        //Only add the row to the output file if it is properly formatted.
        File.WriteAllText(outFile, outputText);
        MessageBox.Show("Done.");
    }

    private void butFinished_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click_1(Object sender, EventArgs e) throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpArizonaPrimaryCareEncounter.class);
        this.butRun = new OpenDental.UI.Button();
        this.butCopy = new OpenDental.UI.Button();
        this.textEncounterFile = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textLog = new System.Windows.Forms.TextBox();
        this.butBrowse = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textEncounterFolder = new System.Windows.Forms.TextBox();
        this.butFinished = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.folderEncounter = new System.Windows.Forms.FolderBrowserDialog();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label5 = new System.Windows.Forms.Label();
        this.dateTimeTo = new System.Windows.Forms.DateTimePicker();
        this.label4 = new System.Windows.Forms.Label();
        this.dateTimeFrom = new System.Windows.Forms.DateTimePicker();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // butRun
        //
        this.butRun.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRun.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butRun.setAutosize(true);
        this.butRun.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRun.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRun.setCornerRadius(4F);
        this.butRun.Location = new System.Drawing.Point(625, 371);
        this.butRun.Name = "butRun";
        this.butRun.Size = new System.Drawing.Size(75, 24);
        this.butRun.TabIndex = 26;
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
        this.butCopy.Location = new System.Drawing.Point(625, 325);
        this.butCopy.Name = "butCopy";
        this.butCopy.Size = new System.Drawing.Size(83, 24);
        this.butCopy.TabIndex = 25;
        this.butCopy.Text = "Copy Log Text";
        this.butCopy.Click += new System.EventHandler(this.butCopy_Click);
        //
        // textEncounterFile
        //
        this.textEncounterFile.Location = new System.Drawing.Point(12, 76);
        this.textEncounterFile.Name = "textEncounterFile";
        this.textEncounterFile.ReadOnly = true;
        this.textEncounterFile.Size = new System.Drawing.Size(164, 20);
        this.textEncounterFile.TabIndex = 24;
        this.textEncounterFile.Text = "ApcEncounter.txt";
        //
        // label3
        //
        this.label3.AutoSize = true;
        this.label3.Location = new System.Drawing.Point(13, 59);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(89, 13);
        this.label3.TabIndex = 23;
        this.label3.Text = "Output File Name";
        //
        // label2
        //
        this.label2.AutoSize = true;
        this.label2.Location = new System.Drawing.Point(9, 178);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(60, 13);
        this.label2.TabIndex = 22;
        this.label2.Text = "Report Log";
        //
        // textLog
        //
        this.textLog.Location = new System.Drawing.Point(12, 196);
        this.textLog.Multiline = true;
        this.textLog.Name = "textLog";
        this.textLog.ReadOnly = true;
        this.textLog.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textLog.Size = new System.Drawing.Size(607, 284);
        this.textLog.TabIndex = 21;
        //
        // butBrowse
        //
        this.butBrowse.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowse.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butBrowse.setAutosize(true);
        this.butBrowse.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowse.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowse.setCornerRadius(4F);
        this.butBrowse.Location = new System.Drawing.Point(625, 30);
        this.butBrowse.Name = "butBrowse";
        this.butBrowse.Size = new System.Drawing.Size(75, 24);
        this.butBrowse.TabIndex = 20;
        this.butBrowse.Text = "Browse";
        this.butBrowse.Click += new System.EventHandler(this.butBrowse_Click);
        //
        // label1
        //
        this.label1.AutoSize = true;
        this.label1.Location = new System.Drawing.Point(12, 14);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(77, 13);
        this.label1.TabIndex = 19;
        this.label1.Text = "Save report to:";
        //
        // textEncounterFolder
        //
        this.textEncounterFolder.Location = new System.Drawing.Point(12, 33);
        this.textEncounterFolder.Name = "textEncounterFolder";
        this.textEncounterFolder.Size = new System.Drawing.Size(607, 20);
        this.textEncounterFolder.TabIndex = 18;
        this.textEncounterFolder.Text = "C:\\Temp";
        //
        // butFinished
        //
        this.butFinished.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butFinished.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butFinished.setAutosize(true);
        this.butFinished.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFinished.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFinished.setCornerRadius(4F);
        this.butFinished.Location = new System.Drawing.Point(625, 415);
        this.butFinished.Name = "butFinished";
        this.butFinished.Size = new System.Drawing.Size(75, 24);
        this.butFinished.TabIndex = 17;
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
        this.butCancel.Location = new System.Drawing.Point(625, 456);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 16;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click_1);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.dateTimeTo);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.dateTimeFrom);
        this.groupBox1.Location = new System.Drawing.Point(12, 102);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(603, 71);
        this.groupBox1.TabIndex = 27;
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
        // dateTimeFrom
        //
        this.dateTimeFrom.Location = new System.Drawing.Point(6, 42);
        this.dateTimeFrom.Name = "dateTimeFrom";
        this.dateTimeFrom.Size = new System.Drawing.Size(200, 20);
        this.dateTimeFrom.TabIndex = 14;
        //
        // FormRpArizonaPrimaryCareEncounter
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(725, 534);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butRun);
        this.Controls.Add(this.butCopy);
        this.Controls.Add(this.textEncounterFile);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textLog);
        this.Controls.Add(this.butBrowse);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textEncounterFolder);
        this.Controls.Add(this.butFinished);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormRpArizonaPrimaryCareEncounter";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Arizona Primary Care Encounter Report";
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butRun;
    private OpenDental.UI.Button butCopy;
    private System.Windows.Forms.TextBox textEncounterFile = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textLog = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butBrowse;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textEncounterFolder = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butFinished;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.FolderBrowserDialog folderEncounter = new System.Windows.Forms.FolderBrowserDialog();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.DateTimePicker dateTimeTo = new System.Windows.Forms.DateTimePicker();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.DateTimePicker dateTimeFrom = new System.Windows.Forms.DateTimePicker();
}


