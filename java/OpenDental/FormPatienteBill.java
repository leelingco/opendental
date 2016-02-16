//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DateTimeOD;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDentBusiness.AccountModules;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clearinghouses;
import OpenDentBusiness.EclaimsCommBridge;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Statement;
import OpenDentBusiness.Statements;


/*=================================================================
Created by Practice-Web Inc. (R) 2009. http://www.practice-web.com
Retain this text in redistributions.
==================================================================*/
/*=================================================================
Created by Practice-Web Inc. (R) 2009. http://www.practice-web.com
Retain this text in redistributions.
==================================================================*/
public class FormPatienteBill  extends Form 
{

    ArrayList PatientList = new ArrayList();
    XmlDocument Doc = new XmlDocument();
    String AuthenticationID = String.Empty;
    String fileName = new String();
    String PatienteBillServerAddress = new String();
    String finalResponse = String.Empty;
    String DentalxChangeContactInfo = " Call Dentalxchange at 800-576-6412.";
    String PWContactInfo = " Call Practice-Web Inc. at 800-845-9379";
    public FormPatienteBill(ArrayList _PatientList) throws Exception {
        initializeComponent();
        PatientList = _PatientList;
        Lan.F(this);
    }

    private void formPatienteBill_Load(Object sender, EventArgs e) throws Exception {
        // Make sure Practionar has valid credentials
        if (isValidCredentials() == false || isValidFolder() == false)
        {
            this.Close();
        }
        else
        {
            if (prepareEbill() == true)
            {
                // Transmit File
                transmit();
            }
             
        } 
    }

    protected boolean isValidCredentials() throws Exception {
        progress("Validate Clearing House Setup..");
        // Make Sure Clearing House is ClaimConnect
        Clearinghouse clearhouse = Clearinghouses.getDefaultDental();
        if (clearhouse == null)
        {
            error("No clearinghouse is set as default.");
            return false;
        }
         
        if (clearhouse.CommBridge != EclaimsCommBridge.ClaimConnect)
        {
            error("Your Clearinghouse does not offer Patient eBill functionality.");
            return false;
        }
         
        progress("Extract Login Credentials..");
        // Read LoginID & Password
        String loginID = new String();
        String passWord = new String();
        // Get Login / Password
        Clearinghouse dch = Clearinghouses.getDefaultDental();
        if (dch != null)
        {
            loginID = dch.LoginID;
            passWord = dch.Password;
        }
        else
        {
            loginID = "";
            passWord = "";
        } 
        if (StringSupport.equals(loginID, ""))
        {
            error("Missing Login ID/Password." + DentalxChangeContactInfo);
            Cursor = Cursors.Default;
            return false;
        }
         
        progress("Get Clearinghouse Authentication & Authorization..");
        // Make 1st HTTP Call And Get the authenticaton
        PatienteBillServerAddress = PrefC.getRaw("PatienteBillServerAddress");
        if (PatienteBillServerAddress.Length == 0)
        {
            error("Missing Patient eBill Server Information." + PWContactInfo);
            return false;
        }
         
        //
        String PatienteBillServer = PatienteBillServerAddress;
        String authRequest = "Function=Auth&Source=STM&Username=" + loginID + "&Password=" + passWord + "&UploaderName=Practice-Web&UploaderVersion=3.0";
        HttpUtility.UrlEncode(authRequest, System.Text.Encoding.Default);
        HttpWebRequest request = null;
        HttpWebResponse response = null;
        StreamReader sr = null;
        System.Diagnostics.ConsoleTraceListener trace = new System.Diagnostics.ConsoleTraceListener();
        request = (HttpWebRequest)WebRequest.Create(PatienteBillServer + '?' + authRequest);
        request.AllowAutoRedirect = true;
        request.Method = "GET";
        // Get The Respons
        response = (HttpWebResponse)request.GetResponse();
        sr = new StreamReader(response.GetResponseStream());
        String loginResponse = sr.ReadLine();
        sr.Close();
        String[] parseResponse = new String[]();
        char[] separator;
        parseResponse = loginResponse.Split(separator);
        int responseStatus = new int();
        String[] statusRespose = new String[]();
        char[] separatorequal;
        statusRespose = parseResponse[0].Split(separatorequal);
        responseStatus = PIn.Int(statusRespose[1]);
        progress("Process Clearinghouse Authorization Response..");
        String errormessage = String.Empty;
        switch(responseStatus)
        {
            case 0: 
                AuthenticationID = parseResponse[3].Remove(0, 17);
                break;
            case 1: 
                errormessage = "Authentication Failed.\r\n" + parseResponse[4].Remove(0, 13);
                break;
            case 2: 
                errormessage = "Cannot authorized at this time.\r\n" + parseResponse[4].Remove(0, 13);
                break;
            case 3: 
                errormessage = "Invalid Request.\r\n" + parseResponse[4].Remove(0, 13);
                break;
            case 4: 
                errormessage = "Invalid PMS Version.\r\n" + parseResponse[4].Remove(0, 13);
                break;
            case 5: 
                errormessage = "No Customer Contract.\r\n" + parseResponse[4].Remove(0, 13);
                break;
            default: 
                errormessage = "Unknown Status(" + responseStatus + ").\r\n" + parseResponse[4].Remove(0, 13);
                break;
        
        }
        if (errormessage.Length > 0)
        {
            error(errormessage + DentalxChangeContactInfo);
            return false;
        }
         
        return true;
    }

    // No error hence return true
    private boolean isValidFolder() throws Exception {
        try
        {
            progress("Verify Folder Location..");
            if (Directory.Exists(PrefC.getRaw("PatienteBillPath")) == false)
            {
                // Create Directory
                Directory.CreateDirectory(PrefC.getRaw("PatienteBillPath"));
            }
             
        }
        catch (Exception ex)
        {
            error("Error Creating Folder " + ex.Message.ToString());
        }

        return true;
    }

    //This method creates XML document for Patient eBilling
    private boolean prepareEbill() throws Exception {
        try
        {
            progress("Process Dental office information..");
            //*******************************************************************************
            // EIStatementFile, SubmitDate, PrimarySubmitter, Transmitter, & Practice(Empry)
            //*******************************************************************************
            // Create EISStatementFile (upto <Practice> Tag)
            // Add Processing Instruction
            XmlProcessingInstruction ProcessingInstrction = Doc.CreateProcessingInstruction("xml", "version = \"1.0\" standalone=\"yes\"");
            Doc.AppendChild(ProcessingInstrction);
            // Prepare EISStatementFile Root Element
            XmlNode EISStatementFile = Doc.CreateNode(XmlNodeType.Element, "EISStatementFile", "");
            Doc.AppendChild(EISStatementFile);
            // Prepare VendorID Attribute
            XmlAttribute VendorID = Doc.CreateAttribute("VendorID");
            VendorID.Value = "18";
            EISStatementFile.Attributes.Append(VendorID);
            // Prepare EISStatementFileOutputFormat Attribute
            XmlAttribute EISStatementFileOutputFormat = Doc.CreateAttribute("OutputFormat");
            EISStatementFileOutputFormat.Value = "StmOut_Blue6Col";
            EISStatementFile.Attributes.Append(EISStatementFileOutputFormat);
            // Prepare SubmitDate Element
            XmlNode SubmitDate = Doc.CreateNode(XmlNodeType.Element, "SubmitDate", "");
            SubmitDate.InnerText = DateTime.Now.ToString("u");
            EISStatementFile.AppendChild(SubmitDate);
            // Prepare PrimarySubmitter Element
            XmlNode PrimarySubmitter = Doc.CreateNode(XmlNodeType.Element, "PrimarySubmitter", "");
            PrimarySubmitter.InnerText = "PWEB";
            EISStatementFile.AppendChild(PrimarySubmitter);
            // Prepare Transmitter Element
            XmlNode Transmitter = Doc.CreateNode(XmlNodeType.Element, "Transmitter", "");
            Transmitter.InnerText = "DXC";
            EISStatementFile.AppendChild(Transmitter);
            // Prepare Practice Element
            XmlNode Practice = Doc.CreateNode(XmlNodeType.Element, "Practice", "");
            EISStatementFile.AppendChild(Practice);
            // Prepare AccountNumber Attribute
            XmlAttribute AccountNumber = Doc.CreateAttribute("AccountNumber");
            AccountNumber.Value = PrefC.getRaw("PWClientAccountNumber");
            Practice.Attributes.Append(AccountNumber);
            //**********************************************
            // Invoke PreparePractice to Complete Practice XML (upto rendering Provider)
            //**********************************************
            preparePractice(Practice);
            return true;
        }
        catch (Exception ex)
        {
            // No Exception return true
            error("Exception Thown while preparing Patient Statement " + ex.Message);
            return false;
        }
    
    }

    /**
    * This Method will prepare XML for Provider
    */
    private void preparePractice(XmlNode Practice) throws Exception {
        //****************************************************************
        // Practice Name,Address and Phone
        //****************************************************************
        // Prepare Name Element for Practice Element
        XmlNode PracticeName = Doc.CreateNode(XmlNodeType.Element, "Name", "");
        XmlCDataSection CDataPracticeName = new XmlCDataSection();
        CDataPracticeName = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeTitle));
        PracticeName.AppendChild(CDataPracticeName);
        Practice.AppendChild(PracticeName);
        // Prepare Address1 Element for Practice Element
        XmlNode PracticeAddress1 = Doc.CreateNode(XmlNodeType.Element, "Address1", "");
        XmlCDataSection CDataPracticeAddress1 = new XmlCDataSection();
        CDataPracticeAddress1 = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeAddress));
        PracticeAddress1.AppendChild(CDataPracticeAddress1);
        Practice.AppendChild(PracticeAddress1);
        // Prepare Address2 Element for Practice Element
        XmlNode PracticeAddress2 = Doc.CreateNode(XmlNodeType.Element, "Address2", "");
        XmlCDataSection CDataPracticeAddress2 = new XmlCDataSection();
        CDataPracticeAddress2 = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeAddress2));
        PracticeAddress2.AppendChild(CDataPracticeAddress2);
        Practice.AppendChild(PracticeAddress2);
        // Prepare City Element for Practice Element
        XmlNode PracticeCity = Doc.CreateNode(XmlNodeType.Element, "City", "");
        XmlCDataSection CDataPracticeCity = new XmlCDataSection();
        CDataPracticeCity = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeCity));
        PracticeCity.AppendChild(CDataPracticeCity);
        Practice.AppendChild(PracticeCity);
        // Prepare State Element for Practice Element
        XmlNode PracticeState = Doc.CreateNode(XmlNodeType.Element, "State", "");
        PracticeState.InnerText = PrefC.getString(PrefName.PracticeST);
        Practice.AppendChild(PracticeState);
        // Prepare Zip Element for Practice Element
        XmlNode PracticeZip = Doc.CreateNode(XmlNodeType.Element, "Zip", "");
        PracticeZip.InnerText = PrefC.getString(PrefName.PracticeZip);
        Practice.AppendChild(PracticeZip);
        // Format Phone -- Start
        String formatPhone = PrefC.getString(PrefName.PracticePhone);
        if (formatPhone.Length > 0)
            formatPhone = "(" + formatPhone.Substring(0, 3) + ")" + formatPhone.Substring(3, 3) + "-" + formatPhone.Substring(6);
         
        // Format Phone -- End
        // Prepare Phone Element for Practice Element
        XmlNode PracticePhone = Doc.CreateNode(XmlNodeType.Element, "Phone", "");
        PracticePhone.InnerText = formatPhone;
        Practice.AppendChild(PracticePhone);
        //****************************************************************
        // Rendering Provider
        //****************************************************************
        // Prepare RemitAddress Element for Practice Element
        XmlNode PracticeRemitAddress = Doc.CreateNode(XmlNodeType.Element, "RemitAddress", "");
        Practice.AppendChild(PracticeRemitAddress);
        // If Billing Address is blank then Remit address is same as Practionar address
        if (PrefC.getString(PrefName.PracticeBillingAddress).Length == 0)
        {
            // Append Name, Address and Phone of Practice
            // Prepare Name, Address, and Phone for Remit Address
            XmlNode RemitAddressName = Doc.CreateNode(XmlNodeType.Element, "Name", "");
            XmlCDataSection CDataRemitAddressName = new XmlCDataSection();
            CDataRemitAddressName = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeTitle));
            RemitAddressName.AppendChild(CDataRemitAddressName);
            PracticeRemitAddress.AppendChild(RemitAddressName);
            // Prepare Address1 Element for RemitAddress Element
            XmlNode RemitAddressAddress1 = Doc.CreateNode(XmlNodeType.Element, "Address1", "");
            XmlCDataSection CDataRemitAddressAddress1 = new XmlCDataSection();
            CDataRemitAddressAddress1 = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeAddress));
            RemitAddressAddress1.AppendChild(CDataRemitAddressAddress1);
            PracticeRemitAddress.AppendChild(RemitAddressAddress1);
            // Prepare Address2 Element for RemitAddress Element
            XmlNode RemitAddressAddress2 = Doc.CreateNode(XmlNodeType.Element, "Address2", "");
            XmlCDataSection CDataRemitAddressAddress2 = new XmlCDataSection();
            CDataRemitAddressAddress2 = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeAddress2));
            RemitAddressAddress2.AppendChild(CDataRemitAddressAddress2);
            PracticeRemitAddress.AppendChild(RemitAddressAddress2);
            // Prepare City Element for RemitAddress Element
            XmlNode RemitAddressCity = Doc.CreateNode(XmlNodeType.Element, "City", "");
            XmlCDataSection CDataRemitAddressCity = new XmlCDataSection();
            CDataRemitAddressCity = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeCity));
            RemitAddressCity.AppendChild(CDataRemitAddressCity);
            PracticeRemitAddress.AppendChild(RemitAddressCity);
            // Prepare State Element for RemitAddress Element
            XmlNode RemitAddressState = Doc.CreateNode(XmlNodeType.Element, "State", "");
            RemitAddressState.InnerText = PrefC.getString(PrefName.PracticeST);
            PracticeRemitAddress.AppendChild(RemitAddressState);
            // Prepare Zip Element for RemitAddress Element
            XmlNode RemitAddressZip = Doc.CreateNode(XmlNodeType.Element, "Zip", "");
            RemitAddressZip.InnerText = PrefC.getString(PrefName.PracticeZip);
            PracticeRemitAddress.AppendChild(RemitAddressZip);
            // Prepare Phone Element for RemitAddress Element
            XmlNode RemitAddressPhone = Doc.CreateNode(XmlNodeType.Element, "Phone", "");
            RemitAddressPhone.InnerText = formatPhone;
            PracticeRemitAddress.AppendChild(RemitAddressPhone);
        }
        else
        {
            // Prepare Name, Address, and Phone for Remit Address
            XmlNode RemitAddressName = Doc.CreateNode(XmlNodeType.Element, "Name", "");
            XmlCDataSection CDataRemitAddressName = new XmlCDataSection();
            CDataRemitAddressName = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeTitle));
            RemitAddressName.AppendChild(CDataRemitAddressName);
            PracticeRemitAddress.AppendChild(RemitAddressName);
            // Prepare Address1 Element for RemitAddress Element
            XmlNode RemitAddressAddress1 = Doc.CreateNode(XmlNodeType.Element, "Address", "");
            XmlCDataSection CDataRemitAddressAddress1 = new XmlCDataSection();
            CDataRemitAddressAddress1 = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeBillingAddress));
            RemitAddressAddress1.AppendChild(CDataRemitAddressAddress1);
            PracticeRemitAddress.AppendChild(RemitAddressAddress1);
            // Prepare Address2 Element for RemitAddress Element
            XmlNode RemitAddressAddress2 = Doc.CreateNode(XmlNodeType.Element, "Address2", "");
            XmlCDataSection CDataRemitAddressAddress2 = new XmlCDataSection();
            CDataRemitAddressAddress2 = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeBillingAddress2));
            RemitAddressAddress2.AppendChild(CDataRemitAddressAddress2);
            PracticeRemitAddress.AppendChild(RemitAddressAddress2);
            // Prepare City Element for RemitAddress Element
            XmlNode RemitAddressCity = Doc.CreateNode(XmlNodeType.Element, "City", "");
            XmlCDataSection CDataRemitAddressCity = new XmlCDataSection();
            CDataRemitAddressCity = Doc.CreateCDataSection(PrefC.getString(PrefName.PracticeBillingCity));
            RemitAddressCity.AppendChild(CDataRemitAddressCity);
            PracticeRemitAddress.AppendChild(RemitAddressCity);
            // Prepare State Element for RemitAddress Element
            XmlNode RemitAddressState = Doc.CreateNode(XmlNodeType.Element, "State", "");
            RemitAddressState.InnerText = PrefC.getString(PrefName.PracticeBillingST);
            PracticeRemitAddress.AppendChild(RemitAddressState);
            // Prepare Zip Element for RemitAddress Element
            XmlNode RemitAddressZip = Doc.CreateNode(XmlNodeType.Element, "Zip", "");
            RemitAddressZip.InnerText = PrefC.getString(PrefName.PracticeBillingZip);
            PracticeRemitAddress.AppendChild(RemitAddressZip);
            // Prepare Phone Element for RemitAddress Element
            XmlNode RemitAddressPhone = Doc.CreateNode(XmlNodeType.Element, "Phone", "");
            RemitAddressPhone.InnerText = formatPhone;
            PracticeRemitAddress.AppendChild(RemitAddressPhone);
        } 
        // Get Rendering Provider Information
        preapreRendringProvider(Practice);
        //************************************************
        // get Patient Information
        //************************************************
        preparePatient(Practice);
    }

    /**
    * This Method will populate Rendering Provider Information
    */
    private void preapreRendringProvider(XmlNode Practice) throws Exception {
        String RPName = new String();
        String RPLicense = new String();
        if (PrefC.getString(PrefName.PracticeDefaultProv).Length > 0)
        {
            DataTable RenderingTable = Providers.getDefaultPracticeProvider();
            if (RenderingTable.Rows.Count > 0)
            {
                RPName = RenderingTable.Rows[0]["FName"].ToString() + " " + RenderingTable.Rows[0]["LName"].ToString() + " " + RenderingTable.Rows[0]["Suffix"].ToString();
                RPLicense = RenderingTable.Rows[0]["StateLicense"].ToString();
            }
            else
            {
                RPName = PrefC.getString(PrefName.PracticeTitle).ToString();
                RPLicense = "";
            } 
        }
        else
        {
            RPName = PrefC.getString(PrefName.PracticeTitle).ToString();
            RPLicense = "";
        } 
        // Prepare RenderingProvider Element for Practice Element
        XmlNode PracticeRenderingProvider = Doc.CreateNode(XmlNodeType.Element, "RenderingProvider", "");
        Practice.AppendChild(PracticeRenderingProvider);
        // Prepare Name Element for RenderingProvider Element
        XmlNode RenderingProviderName = Doc.CreateNode(XmlNodeType.Element, "Name", "");
        RenderingProviderName.InnerText = RPName;
        PracticeRenderingProvider.AppendChild(RenderingProviderName);
        // Prepare LicenseNumber Element for RenderingProvider Element
        XmlNode RenderingProviderLicenseNumber = Doc.CreateNode(XmlNodeType.Element, "LicenseNumber", "");
        RenderingProviderLicenseNumber.InnerText = RPLicense;
        PracticeRenderingProvider.AppendChild(RenderingProviderLicenseNumber);
        // Prepare State Element for RenderingProvider Element
        XmlNode RenderingProviderState = Doc.CreateNode(XmlNodeType.Element, "State", "");
        RenderingProviderState.InnerText = PrefC.getString(PrefName.PracticeST);
        PracticeRenderingProvider.AppendChild(RenderingProviderState);
    }

    /**
    * This Method will prepare Patient XML
    */
    private void preparePatient(XmlNode Practice) throws Exception {
        int PatientID = new int();
        String FName = new String(), MiddleI = new String(), LName = new String(), PatientNm = new String(), Guarantor = new String(), Address = new String();
        String Address2 = new String(), City = new String(), State = new String(), Zip = new String(), Email = new String(), EstBalance = new String();
        String BalTotal = new String(), Bal_0_30 = new String(), Bal_31_60 = new String(), Bal_61_90 = new String(), BalOver90 = new String();
        int StmtCalcDueDate = new int();
        progress("Process Patient Information..");
        for (int i = 0;i < PatientList.Count;i++)
        {
            PatientID = (int)PatientList[i];
            DataTable PatientTable = Patients.GetGuarantorInfo(PatientID);
            if (PatientTable.Rows.Count > 0)
            {
                FName = PatientTable.Rows[0]["FName"].ToString();
                MiddleI = PatientTable.Rows[0]["MiddleI"].ToString();
                LName = PatientTable.Rows[0]["LName"].ToString();
                if (MiddleI.Length > 0)
                    PatientNm = FName + " " + MiddleI + " " + LName;
                else
                    PatientNm = FName + " " + LName; 
                Guarantor = PatientTable.Rows[0]["Guarantor"].ToString();
                Address = PatientTable.Rows[0]["Address"].ToString();
                Address2 = PatientTable.Rows[0]["Address2"].ToString();
                City = PatientTable.Rows[0]["City"].ToString();
                State = PatientTable.Rows[0]["State"].ToString();
                Zip = PatientTable.Rows[0]["Zip"].ToString();
                Email = PatientTable.Rows[0]["Email"].ToString();
                EstBalance = PatientTable.Rows[0]["EstBalance"].ToString();
                BalTotal = PatientTable.Rows[0]["BalTotal"].ToString();
                Bal_0_30 = PatientTable.Rows[0]["Bal_0_30"].ToString();
                Bal_31_60 = PatientTable.Rows[0]["Bal_31_60"].ToString();
                Bal_61_90 = PatientTable.Rows[0]["Bal_61_90"].ToString();
                BalOver90 = PatientTable.Rows[0]["BalOver90"].ToString();
            }
            else
            {
                continue;
            } 
            // Skip it because Patient is not a guarantor
            progress(PatientNm + "..");
            // Prepare EisStatement Element for Practice Element
            XmlNode PracticeEisStatement = Doc.CreateNode(XmlNodeType.Element, "EisStatement", "");
            Practice.AppendChild(PracticeEisStatement);
            // Prepare EISStatementOutputFormat Attribute
            XmlAttribute EISStatementOutputFormat = Doc.CreateAttribute("OutputFormat");
            EISStatementOutputFormat.Value = "StmOut_Blue6Col";
            PracticeEisStatement.Attributes.Append(EISStatementOutputFormat);
            // Prepare CreditCardChoice Attribute
            XmlAttribute EISStatementCreditCardChoice = Doc.CreateAttribute("CreditCardChoice");
            EISStatementCreditCardChoice.Value = "MC,V,D,A";
            PracticeEisStatement.Attributes.Append(EISStatementCreditCardChoice);
            // Prepare Patient Element For EisStatement Element
            XmlNode EisStatementPatient = Doc.CreateNode(XmlNodeType.Element, "Patient", "");
            PracticeEisStatement.AppendChild(EisStatementPatient);
            // Prepare Name Element For Patient Element
            XmlNode PatientName = Doc.CreateNode(XmlNodeType.Element, "Name", "");
            XmlCDataSection CDataPatientName = new XmlCDataSection();
            CDataPatientName = Doc.CreateCDataSection(PatientNm);
            PatientName.AppendChild(CDataPatientName);
            EisStatementPatient.AppendChild(PatientName);
            // Prepare Account Element For Patient Element
            XmlNode PatientAccount = Doc.CreateNode(XmlNodeType.Element, "Account", "");
            PatientAccount.InnerText = Guarantor;
            // Append Account Element to Patiend Element
            EisStatementPatient.AppendChild(PatientAccount);
            // Prepare Address1 Element For Patient Element
            XmlNode PatientAddress1 = Doc.CreateNode(XmlNodeType.Element, "Address1", "");
            XmlCDataSection CDataPatientAddress1 = new XmlCDataSection();
            CDataPatientAddress1 = Doc.CreateCDataSection(Address);
            PatientAddress1.AppendChild(CDataPatientAddress1);
            EisStatementPatient.AppendChild(PatientAddress1);
            // Prepare Address2 Element For Patient Element
            XmlNode PatientAddress2 = Doc.CreateNode(XmlNodeType.Element, "Address2", "");
            XmlCDataSection CDataPatientAddress2 = new XmlCDataSection();
            CDataPatientAddress2 = Doc.CreateCDataSection(Address2);
            PatientAddress2.AppendChild(CDataPatientAddress2);
            EisStatementPatient.AppendChild(PatientAddress2);
            // Prepare City Element For Patient Element
            XmlNode PatientCity = Doc.CreateNode(XmlNodeType.Element, "City", "");
            XmlCDataSection CDataPatientCity = new XmlCDataSection();
            CDataPatientCity = Doc.CreateCDataSection(City);
            PatientCity.AppendChild(CDataPatientCity);
            EisStatementPatient.AppendChild(PatientCity);
            // Prepare State Element For Patient Element
            XmlNode PatientState = Doc.CreateNode(XmlNodeType.Element, "State", "");
            PatientState.InnerText = State;
            EisStatementPatient.AppendChild(PatientState);
            // Prepare Zip Element For Patient Element
            XmlNode PatientZip = Doc.CreateNode(XmlNodeType.Element, "Zip", "");
            PatientZip.InnerText = Zip;
            EisStatementPatient.AppendChild(PatientZip);
            // Prepare Email Element For Patient Element
            XmlNode PatientEmail = Doc.CreateNode(XmlNodeType.Element, "Email", "");
            XmlCDataSection CDataPatientEmail = new XmlCDataSection();
            CDataPatientEmail = Doc.CreateCDataSection(Email);
            PatientEmail.AppendChild(CDataPatientEmail);
            EisStatementPatient.AppendChild(PatientEmail);
            //************************************************************
            // Prepare Account Summary
            //************************************************************
            // Prepare AccountSummary Element For Patient Element
            XmlNode PatientAccountSummary = Doc.CreateNode(XmlNodeType.Element, "AccountSummary", "");
            EisStatementPatient.AppendChild(PatientAccountSummary);
            // Prepare PriorStatementDate Element For AccountSummary Element
            XmlNode PriorStatementDate = Doc.CreateNode(XmlNodeType.Element, "PriorStatementDate", "");
            PriorStatementDate.InnerText = DateTime.Now.AddDays(-30).ToString("d");
            PatientAccountSummary.AppendChild(PriorStatementDate);
            // Prepare DueDate Element For AccountSummary Element
            XmlNode DueDate = Doc.CreateNode(XmlNodeType.Element, "DueDate", "");
            StmtCalcDueDate = PrefC.getInt(PrefName.StatementsCalcDueDate);
            if (StmtCalcDueDate != -1)
                DueDate.InnerText = DateTime.Now.AddDays(StmtCalcDueDate).ToString("d");
            else
                DueDate.InnerText = DateTime.Now.AddDays(10).ToString("d"); 
            PatientAccountSummary.AppendChild(DueDate);
            // Prepare StatementDate Element For AccountSummary Element
            XmlNode StatementDate = Doc.CreateNode(XmlNodeType.Element, "StatementDate", "");
            StatementDate.InnerText = DateTime.Now.ToString("d");
                ;
            PatientAccountSummary.AppendChild(StatementDate);
            // Prepare PriorBalance Element For AccountSummary Element
            XmlNode PriorBalance = Doc.CreateNode(XmlNodeType.Element, "PriorBalance", "");
            PriorBalance.InnerText = "0.00";
            PatientAccountSummary.AppendChild(PriorBalance);
            // Prepare RunningBalance Element For AccountSummary Element
            XmlNode RunningBalance = Doc.CreateNode(XmlNodeType.Element, "RunningBalance", "");
            RunningBalance.InnerText = "0.00";
            PatientAccountSummary.AppendChild(RunningBalance);
            // Prepare Adjustments Element For AccountSummary Element
            XmlNode Adjustments = Doc.CreateNode(XmlNodeType.Element, "Adjustments", "");
            Adjustments.InnerText = "0.00";
            PatientAccountSummary.AppendChild(Adjustments);
            // Prepare NewCharges Element For AccountSummary Element
            XmlNode NewCharges = Doc.CreateNode(XmlNodeType.Element, "NewCharges", "");
            NewCharges.InnerText = "0.00";
            PatientAccountSummary.AppendChild(NewCharges);
            // Prepare FinanceCharges Element For AccountSummary Element
            XmlNode FinanceCharges = Doc.CreateNode(XmlNodeType.Element, "FinanceCharges", "");
            FinanceCharges.InnerText = "0.00";
            PatientAccountSummary.AppendChild(FinanceCharges);
            // Prepare Credits Element For AccountSummary Element
            XmlNode Credits = Doc.CreateNode(XmlNodeType.Element, "Credits", "");
            Credits.InnerText = "0.00";
            PatientAccountSummary.AppendChild(Credits);
            // Prepare EstInsPayments Element For AccountSummary Element
            XmlNode EstInsPayments = Doc.CreateNode(XmlNodeType.Element, "EstInsPayments", "");
            EstInsPayments.InnerText = "0.00";
            PatientAccountSummary.AppendChild(EstInsPayments);
            // Prepare PatientShare Element For AccountSummary Element
            XmlNode PatientShare = Doc.CreateNode(XmlNodeType.Element, "PatientShare", "");
            PatientShare.InnerText = EstBalance;
            PatientAccountSummary.AppendChild(PatientShare);
            // Prepare CurrentBalance Element For AccountSummary Element
            XmlNode CurrentBalance = Doc.CreateNode(XmlNodeType.Element, "CurrentBalance", "");
            CurrentBalance.InnerText = BalTotal;
            PatientAccountSummary.AppendChild(CurrentBalance);
            // Prepare PastDue30 Element For AccountSummary Element
            XmlNode PastDue30 = Doc.CreateNode(XmlNodeType.Element, "PastDue30", "");
            PastDue30.InnerText = Bal_31_60;
            PatientAccountSummary.AppendChild(PastDue30);
            // Prepare PastDue60 Element For AccountSummary Element
            XmlNode PastDue60 = Doc.CreateNode(XmlNodeType.Element, "PastDue60", "");
            PastDue60.InnerText = Bal_61_90;
            PatientAccountSummary.AppendChild(PastDue60);
            // Prepare PastDue90 Element For AccountSummary Element
            XmlNode PastDue90 = Doc.CreateNode(XmlNodeType.Element, "PastDue90", "");
            PastDue90.InnerText = BalOver90;
            PatientAccountSummary.AppendChild(PastDue90);
            // Prepare PastDue120 Element For AccountSummary Element
            XmlNode PastDue120 = Doc.CreateNode(XmlNodeType.Element, "PastDue120", "");
            PastDue120.InnerText = "0.00";
            PatientAccountSummary.AppendChild(PastDue120);
            //****************************************************************
            // Add Notes
            //****************************************************************
            prepareNotes(PatientID,EisStatementPatient);
            //****************************************************************
            // Add Detail Detail Items
            //****************************************************************
            prepareDetailItems(PatientID,EisStatementPatient);
        }
    }

    /**
    * This Method will prepare XML for Notes
    */
    private void prepareNotes(int PatientID, XmlNode EisStatementPatient) throws Exception {
        String note = String.Empty;
        DataTable NoteTable = Statements.GetStatementNotesPracticeWeb(PatientID);
        if (NoteTable.Rows.Count > 0)
        {
            note = NoteTable.Rows[0]["Note"].ToString();
        }
         
        // Prepare Notes Element For Patient Element
        XmlNode PatientNotes = Doc.CreateNode(XmlNodeType.Element, "Notes", "");
        EisStatementPatient.AppendChild(PatientNotes);
        // Prepare Note1 Element For Notes Element
        XmlNode Note1 = Doc.CreateNode(XmlNodeType.Element, "Note1", "");
        XmlCDataSection CDataNote1 = new XmlCDataSection();
        CDataNote1 = Doc.CreateCDataSection(note);
        Note1.AppendChild(CDataNote1);
        PatientNotes.AppendChild(Note1);
    }

    /**
    * This Method will prepare XML for Detail items
    */
    private void prepareDetailItems(int PatientID, XmlNode EisStatementPatient) throws Exception {
        // Prepare DetailItems Element For Patient Element
        XmlNode PatientDetailItems = Doc.CreateNode(XmlNodeType.Element, "DetailItems", "");
        EisStatementPatient.AppendChild(PatientDetailItems);
        //js 2/13/11, this next line is flawed.  You wouldn't get a statement by using pk of the patient.
        //But that's the way PW wrote it, so I'm leaving it alone.
        Statement stmt = Statements.GetStatementInfoPracticeWeb(PatientID);
        //js I had to add this section in 7.8 to make it compile.  Not tested.
        if (stmt == null)
        {
            stmt.SinglePatient = true;
            stmt.DateRangeFrom = DateTimeOD.getToday();
            stmt.DateRangeTo = DateTimeOD.getToday();
            stmt.Intermingled = true;
            stmt.PatNum = (long)PatientID;
        }
         
        DataSet dataSet = new DataSet();
        dataSet = AccountModules.getStatementDataSet(stmt);
        DataTable tableAccount = dataSet.Tables["account"];
        String tablename = new String();
        for (int i = 0;i < dataSet.Tables.Count;i++)
        {
            // accounttable name is account+patientID
            // Iterate through all table and pickup table
            tablename = dataSet.Tables[i].TableName;
            if (tablename.StartsWith("account"))
            {
                tableAccount = dataSet.Tables[i];
            }
             
        }
        String date = new String(), patient = new String(), ProcCode = new String(), tth = new String(), description = new String(), fulldesc = new String(), charges = new String(), credits = new String(), balance = new String();
        for (int p = 0;p < tableAccount.Rows.Count;p++)
        {
            date = tableAccount.Rows[p]["date"].ToString();
            patient = tableAccount.Rows[p]["patient"].ToString();
            ProcCode = tableAccount.Rows[p]["ProcCode"].ToString();
            tth = tableAccount.Rows[p]["tth"].ToString();
            description = tableAccount.Rows[p]["description"].ToString();
            fulldesc = ProcCode + " " + tth + " " + description;
            charges = tableAccount.Rows[p]["charges"].ToString();
            credits = tableAccount.Rows[p]["credits"].ToString();
            balance = tableAccount.Rows[p]["balance"].ToString();
            //add Appripriate Tags
            // Prepare Item Element For DetailItem Element
            XmlNode Item = Doc.CreateNode(XmlNodeType.Element, "Item", "");
            PatientDetailItems.AppendChild(Item);
            // Prepare Date Element For Item Element
            XmlNode ItemDate = Doc.CreateNode(XmlNodeType.Element, "Date", "");
            ItemDate.InnerText = date;
            Item.AppendChild(ItemDate);
            // Prepare PatientName Element For Item Element
            XmlNode ItemPatientName = Doc.CreateNode(XmlNodeType.Element, "PatientName", "");
            XmlCDataSection CDataItemPatientName = new XmlCDataSection();
            CDataItemPatientName = Doc.CreateCDataSection(patient);
            ItemPatientName.AppendChild(CDataItemPatientName);
            Item.AppendChild(ItemPatientName);
            // Prepare Description Element For Item Element
            XmlNode ItemDescription = Doc.CreateNode(XmlNodeType.Element, "Description", "");
            XmlCDataSection CDataItemDescription = new XmlCDataSection();
            CDataItemDescription = Doc.CreateCDataSection(fulldesc);
            ItemDescription.AppendChild(CDataItemDescription);
            Item.AppendChild(ItemDescription);
            // Prepare Charges Element For Item Element
            XmlNode ItemCharges = Doc.CreateNode(XmlNodeType.Element, "Charges", "");
            ItemCharges.InnerText = charges;
            Item.AppendChild(ItemCharges);
            // Prepare Credits Element For Item Element
            XmlNode ItemCredits = Doc.CreateNode(XmlNodeType.Element, "Credits", "");
            ItemCredits.InnerText = credits;
            Item.AppendChild(ItemCredits);
            // Prepare Balance Element For Item Element
            XmlNode ItemBalance = Doc.CreateNode(XmlNodeType.Element, "Balance", "");
            ItemBalance.InnerText = balance;
            Item.AppendChild(ItemBalance);
        }
    }

    // Store file
    private boolean isPatientFileCreated() throws Exception {
        progress("Store Statements for Transmission..");
        fileName = PrefC.getRaw("PatienteBillPath") + "\\patientebill.xml";
        try
        {
            // remove any prior trasmitted file
            if (File.Exists(fileName))
            {
                File.Delete(fileName);
            }
             
            Doc.Save(fileName);
            return true;
        }
        catch (Exception ex)
        {
            error("Patient eBill File Create Error " + ex.Message.ToString());
            return false;
        }
    
    }

    // Transmit file
    private void transmit() throws Exception {
        if (isPatientFileCreated() == true)
        {
            try
            {
                progress("Begin Transmit..");
                String gc_MimeSep = "---------------------------7d13e425b00d0";
                String gc_MimeFunction = "Content-Disposition: form-data; name=\"Function\"";
                String gc_MimeSource = "Content-Disposition: form-data; name=\"Source\"";
                String gc_MimeAuth = "Content-Disposition: form-data; name=\"AuthenticationID\"";
                String gc_MimeFile = "Content-Disposition: form-data; name=\"File\"; filename=\"";
                String tempFile = PrefC.getRaw("PatienteBillPath") + "\\tmp.xml";
                String RequestStr = "--" + gc_MimeSep + "\r\n" + gc_MimeFunction + "\r\n\r\n" + "Upload\r\n" + "--" + gc_MimeSep + "\r\n" + gc_MimeSource + "\r\n\r\n" + "STM\r\n" + "--" + gc_MimeSep + "\r\n" + gc_MimeAuth + "\r\n\r\n" + AuthenticationID + "\r\n" + "--" + gc_MimeSep + "\r\n" + gc_MimeFile + tempFile + '\"' + "\r\n" + "Content-Type: text/plain" + "\r\n\r\n";
                StreamReader xmlStream = new StreamReader(fileName);
                StreamWriter xmlTmp = new StreamWriter(tempFile);
                xmlTmp.Write(RequestStr);
                xmlTmp.Write(xmlStream.ReadToEnd());
                xmlTmp.Close();
                xmlStream.Close();
                StreamReader xmlUpload = new StreamReader(tempFile);
                byte[] fileContents = Encoding.Default.GetBytes(xmlUpload.ReadToEnd());
                xmlUpload.Close();
                HttpWebRequest request1 = (HttpWebRequest)WebRequest.Create(PatienteBillServerAddress + "?" + RequestStr);
                request1.ContentType = "multipart/form-data; boundary=" + gc_MimeSep;
                request1.Method = "POST";
                request1.ContentLength = fileContents.Length;
                request1.AllowAutoRedirect = false;
                Stream requestStream = request1.GetRequestStream();
                requestStream.Write(fileContents, 0, fileContents.Length);
                requestStream.Close();
                /* ------------------------- Get Response -----------------------*/
                HttpWebResponse response = (HttpWebResponse)request1.GetResponse();
                StreamReader sr = new StreamReader(response.GetResponseStream());
                finalResponse = sr.ReadToEnd();
                // Delete temp file
                FileInfo trASH = new FileInfo(tempFile);
                trASH.Delete();
                progress("End Transmit..");
                processFinalResponse(finalResponse);
            }
            catch (Exception ex)
            {
                error("Transmit failed. " + ex.Message.ToString());
            }
        
        }
         
    }

    private void processFinalResponse(String finalResponse) throws Exception {
        try
        {
            progress("Process Clearinghouse Response..");
            String[] parseResponse = new String[]();
            char[] separator;
            parseResponse = finalResponse.Split(separator);
            int responseStatus = new int();
            String[] statusRespose = new String[]();
            char[] separatorequal;
            statusRespose = parseResponse[0].Split(separatorequal);
            responseStatus = PIn.Int(statusRespose[1]);
            String errormessage = String.Empty;
            switch(responseStatus)
            {
                case 0: 
                    progress("Patient eBill transmission completed Successfully.");
                    MessageBox.Show(this, "Patient eBill transmission completed Successfully.");
                    this.Close();
                    break;
                case 1: 
                    errormessage = "Authentication Failed.\r\n" + parseResponse[3].Remove(0, 13);
                    break;
                case 2: 
                    errormessage = "Cannot upload at this time.\r\n" + parseResponse[3].Remove(0, 13);
                    break;
                default: 
                    errormessage = "Unknown Status(" + responseStatus + ").\r\n" + parseResponse[3].Remove(0, 13);
                    break;
            
            }
            if (errormessage.Length > 0)
                error(errormessage + DentalxChangeContactInfo);
             
        }
        catch (Exception ex)
        {
            error("Error Parsing Final response " + ex.Message.ToString());
        }
    
    }

    private void progress(String statusMsg) throws Exception {
        this.tBStatus.Text = this.tBStatus.Text + statusMsg + "\r\n";
    }

    private void error(String statusMsg) throws Exception {
        this.tBError.Text = this.tBStatus.Text + statusMsg + "\r\n";
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
        this.tBStatus = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.tBError = new System.Windows.Forms.TextBox();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // tBStatus
        //
        this.tBStatus.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.tBStatus.ForeColor = System.Drawing.Color.Blue;
        this.tBStatus.Location = new System.Drawing.Point(7, 23);
        this.tBStatus.Multiline = true;
        this.tBStatus.Name = "tBStatus";
        this.tBStatus.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        this.tBStatus.Size = new System.Drawing.Size(458, 197);
        this.tBStatus.TabIndex = 2;
        //
        // label4
        //
        this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label4.ForeColor = System.Drawing.Color.Blue;
        this.label4.Location = new System.Drawing.Point(7, 6);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(66, 14);
        this.label4.TabIndex = 41;
        this.label4.Text = "Progress...";
        //
        // label1
        //
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label1.ForeColor = System.Drawing.Color.Red;
        this.label1.Location = new System.Drawing.Point(7, 223);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(46, 14);
        this.label1.TabIndex = 43;
        this.label1.Text = "Error...";
        //
        // tBError
        //
        this.tBError.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.tBError.ForeColor = System.Drawing.Color.Red;
        this.tBError.Location = new System.Drawing.Point(7, 240);
        this.tBError.Multiline = true;
        this.tBError.Name = "tBError";
        this.tBError.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        this.tBError.Size = new System.Drawing.Size(458, 115);
        this.tBError.TabIndex = 44;
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
        this.butClose.Location = new System.Drawing.Point(199, 361);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 42;
        this.butClose.Text = "Close";
        //
        // FormPatienteBill
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(475, 391);
        this.Controls.Add(this.tBError);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.tBStatus);
        this.Name = "FormPatienteBill";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "FormPatienteBill";
        this.Load += new System.EventHandler(this.FormPatienteBill_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox tBStatus = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tBError = new System.Windows.Forms.TextBox();
}


