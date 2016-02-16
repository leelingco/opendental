//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDentBusiness.Def;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Family;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InstallmentPlan;
import OpenDentBusiness.InstallmentPlans;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Statement;

public class EHG_statements   
{
    //these are temporary:
    //private static string vendorID="68";
    //private static string vendorPMScode="144";
    //private static string clientAccountNumber="8011";//the dental office number set by EHG
    //private static string creditCardChoices="MC,D,V,A";//MasterCard,Discover,Visa,AmericanExpress
    //private static string userName="";
    //private static string password="";
    /**
    * Generates all the xml up to the point where the first statement would go.
    */
    public static void generatePracticeInfo(XmlWriter writer) throws Exception {
        writer.WriteProcessingInstruction("xml", "version = \"1.0\" standalone=\"yes\"");
        writer.WriteStartElement("EISStatementFile");
        writer.WriteAttributeString("VendorID", PrefC.getString(PrefName.BillingElectVendorId));
        writer.WriteAttributeString("OutputFormat", "StmOut_Blue6Col");
        writer.WriteAttributeString("Version", "2");
        writer.WriteElementString("SubmitDate", DateTime.Today.ToString("yyyy-MM-dd"));
        writer.WriteElementString("PrimarySubmitter", PrefC.getString(PrefName.BillingElectVendorPMSCode));
        writer.WriteElementString("Transmitter", "EHG");
        writer.WriteStartElement("Practice");
        writer.WriteAttributeString("AccountNumber", PrefC.getString(PrefName.BillingElectClientAcctNumber));
        //sender address----------------------------------------------------------
        writer.WriteStartElement("SenderAddress");
        writer.WriteElementString("Name", PrefC.getString(PrefName.PracticeTitle));
        writer.WriteElementString("Address1", PrefC.getString(PrefName.PracticeAddress));
        writer.WriteElementString("Address2", PrefC.getString(PrefName.PracticeAddress2));
        writer.WriteElementString("City", PrefC.getString(PrefName.PracticeCity));
        writer.WriteElementString("State", PrefC.getString(PrefName.PracticeST));
        writer.WriteElementString("Zip", PrefC.getString(PrefName.PracticeZip));
        writer.WriteElementString("Phone", PrefC.getString(PrefName.PracticePhone));
        //enforced to be 10 digit fairly rigidly by the UI
        writer.WriteEndElement();
        //senderAddress
        //remit address----------------------------------------------------------
        writer.WriteStartElement("RemitAddress");
        writer.WriteElementString("Name", PrefC.getString(PrefName.PracticeTitle));
        if (StringSupport.equals(PrefC.getString(PrefName.PracticeBillingAddress), ""))
        {
            //same as sender address
            writer.WriteElementString("Address1", PrefC.getString(PrefName.PracticeAddress));
            writer.WriteElementString("Address2", PrefC.getString(PrefName.PracticeAddress2));
            writer.WriteElementString("City", PrefC.getString(PrefName.PracticeCity));
            writer.WriteElementString("State", PrefC.getString(PrefName.PracticeST));
            writer.WriteElementString("Zip", PrefC.getString(PrefName.PracticeZip));
        }
        else
        {
            writer.WriteElementString("Address1", PrefC.getString(PrefName.PracticeBillingAddress));
            writer.WriteElementString("Address2", PrefC.getString(PrefName.PracticeBillingAddress2));
            writer.WriteElementString("City", PrefC.getString(PrefName.PracticeBillingCity));
            writer.WriteElementString("State", PrefC.getString(PrefName.PracticeBillingST));
            writer.WriteElementString("Zip", PrefC.getString(PrefName.PracticeBillingZip));
        } 
        writer.WriteElementString("Phone", PrefC.getString(PrefName.PracticePhone));
        //phone is same in either case
        writer.WriteEndElement();
        //remitAddress
        //Rendering provider------------------------------------------------------
        Provider prov = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
        writer.WriteStartElement("RenderingProvider");
        writer.WriteElementString("Name", prov.getFormalName());
        writer.WriteElementString("LicenseNumber", prov.StateLicense);
        writer.WriteElementString("State", PrefC.getString(PrefName.PracticeST));
        writer.WriteEndElement();
    }

    //Rendering provider
    /**
    * Adds the xml for one statement. Validation is performed here. Throws an exception if there is a validation failure.
    */
    public static void generateOneStatement(XmlWriter writer, Statement stmt, Patient pat, Family fam, DataSet dataSet) throws Exception {
        Patient guar = fam.ListPats[0];
        if (!Regex.IsMatch(guar.State, "^[A-Z]{2}$"))
        {
            throw new ApplicationException(Lan.g("EHG_Statements","Guarantor state must be two uppercase characters.") + " " + guar.FName + " " + guar.LName + " #" + guar.PatNum);
        }
         
        writer.WriteStartElement("EisStatement");
        writer.WriteAttributeString("OutputFormat", "StmOut_Blue6Col");
        writer.WriteAttributeString("CreditCardChoice", PrefC.getString(PrefName.BillingElectCreditCardChoices));
        writer.WriteStartElement("Patient");
        writer.WriteElementString("Name", guar.getNameFLFormal());
        writer.WriteElementString("Account", guar.PatNum.ToString());
        writer.WriteElementString("Address1", guar.Address);
        writer.WriteElementString("Address2", guar.Address2);
        writer.WriteElementString("City", guar.City);
        writer.WriteElementString("State", guar.State);
        writer.WriteElementString("Zip", guar.Zip);
        String email = "";
        Def billingDef = DefC.getDef(DefCat.BillingTypes,guar.BillingType);
        if (StringSupport.equals(billingDef.ItemValue, "E"))
        {
            email = guar.Email;
        }
         
        writer.WriteElementString("EMail", email);
        //Account summary-----------------------------------------------------------------------
        writer.WriteStartElement("AccountSummary");
        if (stmt.DateRangeFrom.Year < 1880)
        {
            //make up a statement date.
            writer.WriteElementString("PriorStatementDate", DateTime.Today.AddMonths(-1).ToString("MM/dd/yyyy"));
        }
        else
        {
            writer.WriteElementString("PriorStatementDate", stmt.DateRangeFrom.AddDays(-1).ToString("MM/dd/yyyy"));
        } 
        DateTime dueDate = new DateTime();
        if (PrefC.getLong(PrefName.StatementsCalcDueDate) == -1)
        {
            dueDate = DateTime.Today.AddDays(10);
        }
        else
        {
            dueDate = DateTime.Today.AddDays(PrefC.getLong(PrefName.StatementsCalcDueDate));
        } 
        writer.WriteElementString("DueDate", dueDate.ToString("MM/dd/yyyy"));
        writer.WriteElementString("StatementDate", stmt.DateSent.ToString("MM/dd/yyyy"));
        double balanceForward = 0;
        for (int r = 0;r < dataSet.Tables["misc"].Rows.Count;r++)
        {
            if (StringSupport.equals(dataSet.Tables["misc"].Rows[r]["descript"].ToString(), "balanceForward"))
            {
                balanceForward = PIn.Double(dataSet.Tables["misc"].Rows[r]["value"].ToString());
            }
             
        }
        writer.WriteElementString("PriorBalance", balanceForward.ToString("F2"));
        writer.WriteElementString("RunningBalance", "");
        //for future use
        writer.WriteElementString("PerPayAdj", "");
        //optional
        writer.WriteElementString("InsPayAdj", "");
        //optional
        writer.WriteElementString("Adjustments", "");
        //for future use
        writer.WriteElementString("NewCharges", "");
        //optional
        writer.WriteElementString("FinanceCharges", "");
        //for future use
        DataTable tableAccount = null;
        for (int i = 0;i < dataSet.Tables.Count;i++)
        {
            if (dataSet.Tables[i].TableName.StartsWith("account"))
            {
                tableAccount = dataSet.Tables[i];
            }
             
        }
        double credits = 0;
        for (int i = 0;i < tableAccount.Rows.Count;i++)
        {
            credits += PIn.Double(tableAccount.Rows[i]["creditsDouble"].ToString());
        }
        writer.WriteElementString("Credits", credits.ToString("F2"));
        //on a regular printed statement, the amount due at the top might be different from the balance at the middle right.
        //This is because of payment plan balances.
        //But in e-bills, there is only one amount due.
        //Insurance estimate is already subtracted, and payment plan balance is already added.
        double amountDue = guar.BalTotal;
        for (int m = 0;m < dataSet.Tables["misc"].Rows.Count;m++)
        {
            //add payplan due amt:
            if (StringSupport.equals(dataSet.Tables["misc"].Rows[m]["descript"].ToString(), "payPlanDue"))
            {
                amountDue += PIn.Double(dataSet.Tables["misc"].Rows[m]["value"].ToString());
            }
             
        }
        if (PrefC.getBool(PrefName.BalancesDontSubtractIns))
        {
            writer.WriteElementString("EstInsPayments", "");
        }
        else
        {
            //optional.
            //this is typical
            writer.WriteElementString("EstInsPayments", guar.InsEst.ToString("F2"));
            //optional.
            amountDue -= guar.InsEst;
        } 
        InstallmentPlan installPlan = InstallmentPlans.getOneForFam(guar.PatNum);
        if (installPlan != null)
        {
            //show lesser of normal total balance or the monthly payment amount.
            if (installPlan.MonthlyPayment < amountDue)
            {
                amountDue = installPlan.MonthlyPayment;
            }
             
        }
         
        writer.WriteElementString("PatientShare", amountDue.ToString("F2"));
        writer.WriteElementString("CurrentBalance", amountDue.ToString("F2"));
        //this is ambiguous.  It seems to be AmountDue, but it could possibly be 0-30 days aging
        writer.WriteElementString("PastDue30", guar.Bal_31_60.ToString("F2"));
        //optional
        writer.WriteElementString("PastDue60", guar.Bal_61_90.ToString("F2"));
        //optional
        writer.WriteElementString("PastDue90", guar.BalOver90.ToString("F2"));
        //optional
        writer.WriteElementString("PastDue120", "");
        //optional
        writer.WriteEndElement();
        //AccountSummary
        //Notes-----------------------------------------------------------------------------------
        writer.WriteStartElement("Notes");
        if (!StringSupport.equals(stmt.NoteBold, ""))
        {
            writer.WriteStartElement("Note");
            writer.WriteAttributeString("FgColor", "Red");
            //ColorToHexString(Color.DarkRed));
            //writer.WriteAttributeString("BgColor",ColorToHexString(Color.White));
            writer.WriteString(tidy(stmt.NoteBold,500));
            //Limit of 500 char on notes.
            writer.WriteEndElement();
        }
         
        //Note
        if (!StringSupport.equals(stmt.Note, ""))
        {
            writer.WriteStartElement("Note");
            //writer.WriteAttributeString("FgColor",ColorToHexString(Color.Black));
            //writer.WriteAttributeString("BgColor",ColorToHexString(Color.White));
            writer.WriteString(tidy(stmt.Note,500));
            //Limit of 500 char on notes.
            writer.WriteEndElement();
        }
         
        //Note
        writer.WriteEndElement();
        //Notes
        //Detail items------------------------------------------------------------------------------
        writer.WriteStartElement("DetailItems");
        //string note;
        String descript = new String();
        String fulldesc = new String();
        String procCode = new String();
        String tth = new String();
        //string linedesc;
        String[] lineArray = new String[]();
        List<String> lines = new List<String>();
        DateTime date = new DateTime();
        int seq = 0;
        for (int i = 0;i < tableAccount.Rows.Count;i++)
        {
            procCode = tableAccount.Rows[i]["ProcCode"].ToString();
            tth = tableAccount.Rows[i]["tth"].ToString();
            descript = tableAccount.Rows[i]["description"].ToString();
            fulldesc = procCode + " " + tth + " " + descript;
            //There are frequently CRs within a procedure description for things like ins est.
            lineArray = fulldesc.Split(new String[]{ "\r\n" }, StringSplitOptions.RemoveEmptyEntries);
            lines = new List<String>(lineArray);
            //The specs say that the line limit is 30 char.  But in testing, it will take 50 char.
            //We will use 40 char to be safe.
            if (lines[0].Length > 40)
            {
                String newline = lines[0].Substring(40);
                lines[0] = lines[0].Substring(0, 40);
                //first half
                lines.Insert(1, newline);
            }
             
            for (int li = 0;li < lines.Count;li++)
            {
                //second half
                writer.WriteStartElement("DetailItem");
                //has a child item. We won't add optional child note
                writer.WriteAttributeString("sequence", seq.ToString());
                writer.WriteStartElement("Item");
                if (li == 0)
                {
                    date = (DateTime)tableAccount.Rows[i]["DateTime"];
                    writer.WriteElementString("Date", date.ToString("MM/dd/yyyy"));
                    writer.WriteElementString("PatientName", tableAccount.Rows[i]["patient"].ToString());
                }
                else
                {
                    writer.WriteElementString("Date", "");
                    writer.WriteElementString("PatientName", "");
                } 
                writer.WriteStartElement("Description");
                writer.WriteCData(Tidy(lines[li], 40));
                //Jessica at DentalXchange says limit is 120.  Docs say limit is 30. CData to allow any string.
                writer.WriteEndElement();
                //Description
                if (li == 0)
                {
                    writer.WriteElementString("Charges", tableAccount.Rows[i]["charges"].ToString());
                    writer.WriteElementString("Credits", tableAccount.Rows[i]["credits"].ToString());
                    writer.WriteElementString("Balance", tableAccount.Rows[i]["balance"].ToString());
                }
                else
                {
                    writer.WriteElementString("Charges", "");
                    writer.WriteElementString("Credits", "");
                    writer.WriteElementString("Balance", "");
                } 
                writer.WriteEndElement();
                //Item
                writer.WriteEndElement();
                //DetailItem
                seq++;
            }
        }
        /*The code below just didn't work because notes don't get displayed on the statement.
        				linedesc=lines[0];
        				note="";
        				if(linedesc.Length>30) {
        					note=linedesc.Substring(30);
        					linedesc=linedesc.Substring(0,30);
        				}
        				for(int l=1;l<lines.Length;l++) {
        					if(note!="") {
        						note+="\r\n";
        					}
        					note+=lines[l];
        				}
        				
        				if(note!="") {
        					writer.WriteStartElement("Note");
        					//we're not going to specify colors here since they're optional
        					writer.WriteCData(note);
        					writer.WriteEndElement();//Note
        				}*/
        writer.WriteEndElement();
        //DetailItems
        writer.WriteEndElement();
        //Patient
        writer.WriteEndElement();
    }

    //EisStatement
    /**
    * Converts a .net color to a hex string.  Includes the #.
    */
    private static String colorToHexString(Color color) throws Exception {
        char[] hexDigits;
        byte[] bytes = new byte[3];
        bytes[0] = color.R;
        bytes[1] = color.G;
        bytes[2] = color.B;
        char[] chars = new char[bytes.Length * 2];
        for (int i = 0;i < bytes.Length;i++)
        {
            int b = bytes[i];
            chars[i * 2] = hexDigits[b >> 4];
            chars[i * 2 + 1] = hexDigits[b & 0xF];
        }
        String retVal = new String(chars);
        retVal = "#" + retVal;
        return retVal;
    }

    /**
    * After statements are added, this adds the necessary closing xml elements.
    */
    public static void generateWrapUp(XmlWriter writer) throws Exception {
        writer.WriteEndElement();
        //Practice
        writer.WriteEndElement();
    }

    //EISStatementFile
    private static String tidy(String str, int len) throws Exception {
        if (str.Length > len)
        {
            return str.Substring(0, len);
        }
         
        return str;
    }

    /**
    * Surround with try catch.  The "data" is the previously constructed xml.
    */
    public static void send(String data) throws Exception {
        //Validate the structure of the XML before sending.
        StringReader sr = new StringReader(data);
        try
        {
            XmlReader xmlr = XmlReader.Create(sr);
            while (xmlr.Read())
            {
            }
        }
        catch (Exception ex)
        {
            throw new ApplicationException("Invalid XML in statement batch: " + ex.Message);
        }
        finally
        {
            //Read every node an ensure that there are no exceptions thrown.
            sr.Dispose();
        }
        String strHistoryFile = "";
        if (PrefC.getBool(PrefName.BillingElectSaveHistory))
        {
            String strHistoryDir = CodeBase.ODFileUtils.combinePaths(ImageStore.getPreferredAtoZpath(),"EHG_History");
            if (!Directory.Exists(strHistoryDir))
            {
                Directory.CreateDirectory(strHistoryDir);
            }
             
            strHistoryFile = CodeBase.ODFileUtils.createRandomFile(strHistoryDir,".txt");
            File.WriteAllText(strHistoryFile, data);
        }
         
        //Step 1: Post authentication request:
        Version myVersion = new Version(Application.ProductVersion);
        HttpWebRequest webReq = new HttpWebRequest();
        WebResponse response = new WebResponse();
        StreamReader readStream = new StreamReader();
        String str = new String();
        String[] responseParams = new String[]();
        String status = "";
        String group = "";
        String userid = "";
        String authid = "";
        String errormsg = "";
        String alertmsg = "";
        String curParam = "";
        String serverName = "https://claimconnect.dentalxchange.com/dci/upload.svl";
        //"https://prelive.dentalxchange.com/dci/upload.svl";
        webReq = (HttpWebRequest)WebRequest.Create(serverName);
        //CONSTANT; signifies that this is an authentication request
        //CONSTANT; file format
        //CONSTANT
        //eg 12.3.24
        String postData = "Function=Auth" + "&Source=STM" + "&UploaderName=OpenDental" + "&UploaderVersion=" + myVersion.Major.ToString() + "." + myVersion.Minor.ToString() + "." + myVersion.Build.ToString() + "&Username=" + PrefC.getString(PrefName.BillingElectUserName) + "&Password=" + PrefC.getString(PrefName.BillingElectPassword);
        webReq.KeepAlive = false;
        webReq.Method = "POST";
        webReq.ContentType = "application/x-www-form-urlencoded";
        webReq.ContentLength = postData.Length;
        ASCIIEncoding encoding = new ASCIIEncoding();
        byte[] bytes = encoding.GetBytes(postData);
        Stream streamOut = webReq.GetRequestStream();
        streamOut.Write(bytes, 0, bytes.Length);
        streamOut.Close();
        response = webReq.GetResponse();
        //Process the authentication response:
        readStream = new StreamReader(response.GetResponseStream(), Encoding.ASCII);
        str = readStream.ReadToEnd();
        readStream.Close();
        if (!StringSupport.equals(strHistoryFile, ""))
        {
            //Tack the response onto the end of the saved history file if one was created above.
            File.AppendAllText(strHistoryFile, "\r\n\r\nCONNECTION REQUEST: postData.Length=" + postData.Length + " bytes.Length=" + bytes.Length + "==============\r\n" + " RESPONSE TO CONNECTION REQUEST================================================================\r\n" + str);
        }
         
        //Debug.WriteLine(str);
        //MessageBox.Show(str);
        responseParams = str.Split('&');
        for (int i = 0;i < responseParams.Length;i++)
        {
            curParam = GetParam(responseParams[i]);
            System.String __dummyScrutVar0 = curParam;
            if (__dummyScrutVar0.equals("Status"))
            {
                status = GetParamValue(responseParams[i]);
            }
            else if (__dummyScrutVar0.equals("GROUP"))
            {
                group = GetParamValue(responseParams[i]);
            }
            else if (__dummyScrutVar0.equals("UserID"))
            {
                userid = GetParamValue(responseParams[i]);
            }
            else if (__dummyScrutVar0.equals("AuthenticationID"))
            {
                authid = GetParamValue(responseParams[i]);
            }
            else if (__dummyScrutVar0.equals("ErrorMessage"))
            {
                errormsg = GetParamValue(responseParams[i]);
            }
            else if (__dummyScrutVar0.equals("AlertMessage"))
            {
                alertmsg = GetParamValue(responseParams[i]);
            }
            else
            {
                throw new Exception("Unexpected parameter: " + curParam);
            }      
        }
        //Process response for errors:
        if (!StringSupport.equals(alertmsg, ""))
        {
            MessageBox.Show(alertmsg);
        }
         
        System.String __dummyScrutVar1 = status;
        if (__dummyScrutVar1.equals("0"))
        {
        }
        else //MessageBox.Show("Authentication successful.");
        if (__dummyScrutVar1.equals("1"))
        {
            throw new Exception("Authentication failed. " + errormsg);
        }
        else if (__dummyScrutVar1.equals("2"))
        {
            throw new Exception("Cannot authenticate at this time. " + errormsg);
        }
        else if (__dummyScrutVar1.equals("3"))
        {
            throw new Exception("Invalid authentication request. " + errormsg);
        }
        else if (__dummyScrutVar1.equals("4"))
        {
            throw new Exception("Invalid program version. " + errormsg);
        }
        else if (__dummyScrutVar1.equals("5"))
        {
            throw new Exception("No customer contract. " + errormsg);
        }
        else
        {
            throw new Exception("Error " + status + ". " + errormsg);
        }      
        //some as-yet-undefined error
        //Step 2: Post upload request:
        //string fileName=Directory.GetFiles(clearhouse.ExportPath)[0];
        String boundary = "------------7d13e425b00d0";
        //using(StreamReader sr=new StreamReader(fileName)) {
        //	postData+=sr.ReadToEnd()+"\r\n"
        postData = "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"Function\"\r\n" + "\r\n" + "Upload\r\n" + "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"Source\"\r\n" + "\r\n" + "STM\r\n" + "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"AuthenticationID\"\r\n" + "\r\n" + authid + "\r\n" + "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"File\"; filename=\"" + "stmt.xml" + "\"\r\n" + "Content-Type: text/plain\r\n" + "\r\n" + data + "\r\n" + "--" + boundary + "--";
        //}
        //Debug.WriteLine(postData);
        //MessageBox.Show(postData);
        webReq = (HttpWebRequest)WebRequest.Create(serverName);
        webReq.KeepAlive = false;
        webReq.Method = "POST";
        webReq.ContentType = "multipart/form-data; boundary=" + boundary;
        webReq.ContentLength = postData.Length;
        bytes = encoding.GetBytes(postData);
        streamOut = webReq.GetRequestStream();
        streamOut.Write(bytes, 0, bytes.Length);
        streamOut.Close();
        response = webReq.GetResponse();
        //Process the response
        readStream = new StreamReader(response.GetResponseStream(), Encoding.ASCII);
        str = readStream.ReadToEnd();
        readStream.Close();
        if (!StringSupport.equals(strHistoryFile, ""))
        {
            //Tack the response onto the end of the saved history file if one was created above.
            File.AppendAllText(strHistoryFile, "\r\n\r\nUPLOAD REQUEST: postData.Length=" + postData.Length + " bytes.Length=" + bytes.Length + "==============\r\n" + " RESPONSE TO DATA UPLOAD================================================================\r\n" + str);
        }
         
        errormsg = "";
        status = "";
        str = str.Replace("\r\n", "");
        //Debug.Write(str);
        if (str.Length > 300)
        {
            throw new Exception("Unknown lengthy error message received.");
        }
         
        responseParams = str.Split('&');
        for (int i = 0;i < responseParams.Length;i++)
        {
            curParam = GetParam(responseParams[i]);
            System.String __dummyScrutVar2 = curParam;
            if (__dummyScrutVar2.equals("Status"))
            {
                status = GetParamValue(responseParams[i]);
            }
            else if (__dummyScrutVar2.equals("Error Message") || __dummyScrutVar2.equals("ErrorMessage"))
            {
                errormsg = GetParamValue(responseParams[i]);
            }
            else if (__dummyScrutVar2.equals("Filename") || __dummyScrutVar2.equals("Timestamp"))
            {
            }
            else if (__dummyScrutVar2.equals(""))
            {
            }
            else
            {
                throw new Exception(str);
            }    
        }
        //errorMessage blank
        //"Unexpected parameter: "+str);//curParam+"*");
        System.String __dummyScrutVar3 = status;
        if (__dummyScrutVar3.equals("0"))
        {
        }
        else //MessageBox.Show("Upload successful.");
        if (__dummyScrutVar3.equals("1"))
        {
            throw new Exception("Authentication failed. " + errormsg);
        }
        else if (__dummyScrutVar3.equals("2"))
        {
            throw new Exception("Cannot upload at this time. " + errormsg);
        }
           
    }

    private static String getParam(String paramAndValue) throws Exception {
        if (StringSupport.equals(paramAndValue, ""))
        {
            return "";
        }
         
        String[] pair = paramAndValue.Split('=');
        return pair[0];
    }

    //if(pair.Length!=2){
    //	throw new Exception("Unexpected parameter from server: "+paramAndValue);
    private static String getParamValue(String paramAndValue) throws Exception {
        if (StringSupport.equals(paramAndValue, ""))
        {
            return "";
        }
         
        String[] pair = paramAndValue.Split('=');
        //if(pair.Length!=2){
        //	throw new Exception("Unexpected parameter from server: "+paramAndValue);
        //}
        if (pair.Length == 1)
        {
            return "";
        }
         
        return pair[1];
    }

}


