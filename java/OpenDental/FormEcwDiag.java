//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.JavaSupport.language.ReturnPreOrPostValue;
import CS2JNet.System.StringSupport;
import OpenDental.FormEcwDiagAdv;
import OpenDental.Lan;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

public class FormEcwDiag  extends Form 
{

    private String connString = new String();
    private String username = "ecwUser";
    private String password = "l69Rr4Rmj4CjiCTLxrIblg==";
    //encrypted
    private String server = new String();
    private String port = new String();
    private StringBuilder arbitraryStringName = new StringBuilder();
    public FormEcwDiag() throws Exception {
        initializeComponent();
        server = ProgramProperties.getPropVal(Programs.getProgramNum(ProgramName.eClinicalWorks),"eCWServer");
        port = ProgramProperties.getPropVal(Programs.getProgramNum(ProgramName.eClinicalWorks),"eCWPort");
        buildConnectionString();
        Lan.F(this);
    }

    private void formEcwDiag_Load(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        Application.DoEvents();
        verifyECW();
        Cursor = Cursors.Default;
        Application.DoEvents();
    }

    /**
    * Used to construct a default construction string.
    */
    private void buildConnectionString() throws Exception {
        //although this does seem to cause a bug in Mono.  We will revisit this bug if needed to exclude the port option only for Mono.
        //ecwMaster;"
        //+"Connect Timeout=20;"
        connString = "Server=" + server + ";" + "Port=" + port + ";" + "Database=mobiledoc;" + "User ID=" + username + ";" + "Password=" + CodeBase.MiscUtils.decrypt(password) + ";" + "CharSet=utf8;" + "Treat Tiny As Boolean=false;" + "Allow User Variables=true;" + "Default Command Timeout=300;" + "Pooling=false";
    }

    //default is 30seconds
    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butRunCheck_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        Application.DoEvents();
        verifyECW();
        Cursor = Cursors.Default;
        Application.DoEvents();
    }

    /**
    * Surround with wait cursor.
    */
    private void verifyECW() throws Exception {
        //buildConnectionString();
        boolean verbose = checkShow.Checked;
        StringBuilder strB = new StringBuilder();
        strB.Append('-', 90);
        textLog.Text = DateTime.Now.ToString() + strB.ToString() + "\r\n";
        Application.DoEvents();
        try
        {
            //--------eCW Function Tests Below This Line-------
            MySql.Data.MySqlClient.MySqlHelper.ExecuteDataRow(connString, "SELECT VERSION();");
        }
        catch (Exception ex)
        {
            //meaningless query to test connection.
            textLog.Text += "Cannot detect eCW server named \"" + server + "\".\r\n";
            Cursor = Cursors.Default;
            return ;
        }

        hL7Verification(verbose);
        //composite check
        Application.DoEvents();
        textLog.Text += checkDentalVisitTypes(verbose);
        Application.DoEvents();
        //textLog.Text+=appointmentTriggersForHl7(verbose);
        //Application.DoEvents();
        //textLog.Text+=Test1(verbose);
        //Application.DoEvents();
        textLog.Text += "\r\nDone.";
    }

    private String appointmentTriggersForHl7(boolean verbose) throws Exception {
        String retVal = "";
        DataTable appTriggers = new DataTable();
        try
        {
            appTriggers = MySqlHelper.ExecuteDataset(connString, "SELECT * FROM pmitemkeys WHERE name LIKE '%Filter_for_%';").Tables[0];
        }
        catch (Exception ex)
        {
            return ex.Message + "\r\n";
        }

        for (Object __dummyForeachVar0 : appTriggers.Rows)
        {
            DataRow trigger = (DataRow)__dummyForeachVar0;
            if (!StringSupport.equals(trigger["value"].ToString(), "no"))
            {
                if (verbose)
                {
                    retVal += trigger["name"].ToString().Split('_')[3] + " messages are configured to be sent based on " + trigger["name"].ToString().Split('_')[0] + " filter.\r\n";
                }
                 
                continue;
            }
             
            if (StringSupport.equals(trigger["value"].ToString(), "no") && verbose)
            {
                retVal += trigger["name"].ToString().Split('_')[3] + " messages are sent for any " + trigger["name"].ToString().Split('_')[0] + ".\r\n";
                continue;
            }
             
        }
        if (!StringSupport.equals(retVal, ""))
        {
            String header = "\r\n";
            header += "   HL7 Message Triggers\r\n";
            header += "".PadRight(90, '*') + "\r\n";
            retVal = header + retVal;
        }
         
        return retVal;
    }

    private void hL7Verification(boolean verbose) throws Exception {
        List<int> hl7InterfaceIDs = ColumnToListHelper(MySqlHelper.ExecuteDataset(connString, "SELECT DISTINCT InterfaceId FROM hl7segment_details;").Tables[0], "InterfaceId");
        List<int> interfaceErrorCount = new List<int>(hl7InterfaceIDs.Count);
        List<String> interfaceErrorLogs = new List<String>(hl7InterfaceIDs.Count);
        for (int ifaceIndex = 0;ifaceIndex < hl7InterfaceIDs.Count;ifaceIndex++)
        {
            //Cache error logs for each interface until we determine which interface to report on.
            //validate one interface at a time.
            int interfaceID = hl7InterfaceIDs[ifaceIndex];
            interfaceErrorLogs.Add("");
            //start each interface with a blank error log
            interfaceErrorCount.Add(0);
            //start each interface with 0 error count
            int errorsFromCurMessage = 0;
            //Itterate through and validate all messages defined on this interface.
            List<int> hl7MessageIDs = ColumnToListHelper(MySqlHelper.ExecuteDataset(connString, "SELECT DISTINCT Messageid FROM hl7segment_details WHERE InterfaceID=" + interfaceID + ";").Tables[0], "Messageid");
            for (Object __dummyForeachVar1 : hl7MessageIDs)
            {
                int messageID = (Integer)__dummyForeachVar1;
                //2, in our sample
                String messageType = MySqlHelper.ExecuteDataRow(connString, "SELECT DISTINCT MessageType FROM hl7message_types WHERE MessageTypeId=" + messageID)["MessageType"].ToString();
                //Validate each message individually if needed.
                errorsFromCurMessage = 0;
                if (messageType.Contains("ADT"))
                {
                    RefSupport<int> refVar___0 = new RefSupport<int>();
                    interfaceErrorLogs[ifaceIndex] += verifyAsADTMessage(interfaceID,messageID,refVar___0,verbose);
                    errorsFromCurMessage = refVar___0.getValue();
                }
                else if (messageType.Contains("SIU"))
                {
                    RefSupport<int> refVar___1 = new RefSupport<int>();
                    interfaceErrorLogs[ifaceIndex] += verifyAsSIUMessage(interfaceID,messageID,refVar___1,verbose);
                    errorsFromCurMessage = refVar___1.getValue();
                }
                  
            }
            interfaceErrorCount[ifaceIndex] += errorsFromCurMessage;
        }
        //end foreach interface
        int leastErrorIndex = 0;
        for (int i = 0;i < hl7InterfaceIDs.Count;i++)
        {
            if (interfaceErrorCount[i] < interfaceErrorCount[leastErrorIndex])
            {
                leastErrorIndex = i;
            }
             
        }
        if (!StringSupport.equals(interfaceErrorLogs[leastErrorIndex], "") || verbose)
        {
            textLog.Text += "\r\n";
            textLog.Text += "   HL7 Messages\r\n";
            textLog.Text += "".PadRight(90, '*') + "\r\n";
        }
         
        if (verbose)
        {
            textLog.Text += "HL7 Interface " + hl7InterfaceIDs[leastErrorIndex] + " had " + (interfaceErrorCount[leastErrorIndex] == 0 ? "no" : "the following") + " issues.\r\n";
        }
         
        textLog.Text += interfaceErrorLogs[leastErrorIndex];
        Application.DoEvents();
    }

    private String verifyAsADTMessage(int interfaceID, int messageID, RefSupport<int> errors, boolean verbose) throws Exception {
        String retVal = "";
        errors.setValue(0);
        boolean validMessage = true;
        List<String> segmentsContained = new List<String>();
        DataTable hl7Segments = MySqlHelper.ExecuteDataset(connString, "SELECT SegmentData FROM hl7segment_details WHERE InterfaceID=" + interfaceID + " AND Messageid=" + messageID + ";").Tables[0];
        for (Object __dummyForeachVar2 : hl7Segments.Rows)
        {
            //validate segments based on content
            DataRow segment = (DataRow)__dummyForeachVar2;
            String[] segmentValues = segment["SegmentData"].ToString().Split('|');
            segmentsContained.Add(segmentValues[0]);
            //used later to validate existance of segments.
            System.Array<System.String>.INDEXER __dummyScrutVar0 = segmentValues[0];
            if (__dummyScrutVar0.equals("EVN"))
            {
                continue;
            }
            else //We ignore this field
            if (__dummyScrutVar0.equals("PID"))
            {
                if (!StringSupport.equals(segmentValues[2], "{PID}"))
                {
                    retVal += "ADT HL7 message is not sending eCW's internal patient number in field PID.02\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (!StringSupport.equals(segmentValues[4], "{CONTNO}"))
                {
                    retVal += "ADT HL7 message is not sending eCW's account number in field PID.04\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                continue;
            }
            else if (__dummyScrutVar0.equals("GT1"))
            {
                if (!StringSupport.equals(segmentValues[2], "{GRID}"))
                {
                    retVal += "ADT HL7 message is not sending guarantor's id number in field GT1.02\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (!StringSupport.equals(segmentValues[3], "{GRLN}^{GRFN}^{GRMN}"))
                {
                    retVal += "ADT HL7 message is not sending eCW's guarantor's name in field GT1.03\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (!StringSupport.equals(segmentValues[11], "{GRREL}"))
                {
                    retVal += "ADT HL7 message is not sending guarantor's relationship to patient in field GT1.11\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                continue;
            }
            else
            {
                continue;
            }   
        }
        //Validate existance of segments
        //if(!segmentsContained.Contains("EVN")) { //We ignore this segment
        //  retVal+="No EVN segment found in ADT HL7 message.\r\n";
        //  errors+=3;//No segment +2 sub errors
        //  validMessage=false;
        //}
        if (!segmentsContained.Contains("PID"))
        {
            retVal += "No PID segment found in ADT HL7 message.\r\n";
            errors.setValue(errors.getValue() + 3);
            //No segment +2 sub errors
            validMessage = false;
        }
         
        if (!segmentsContained.Contains("GT1") && verbose)
        {
            retVal += "No GT1 segment found in ADT HL7 message. Guarantors for new patients will always be set to self.\r\n";
            validMessage = false;
        }
         
        //If everything above checks out return a success message
        if (validMessage && verbose)
        {
            retVal += "Found properly formed ADT HL7 message definition.\r\n";
        }
         
        return retVal;
    }

    private String verifyAsSIUMessage(int interfaceID, int messageID, RefSupport<int> errors, boolean verbose) throws Exception {
        String retVal = "";
        errors.setValue(0);
        boolean validMessage = true;
        List<String> segmentsContained = new List<String>();
        DataTable hl7Segments = MySqlHelper.ExecuteDataset(connString, "SELECT SegmentData FROM hl7segment_details WHERE InterfaceID=" + interfaceID + " AND Messageid=" + messageID + ";").Tables[0];
        for (Object __dummyForeachVar3 : hl7Segments.Rows)
        {
            //validate segments based on content
            DataRow segment = (DataRow)__dummyForeachVar3;
            String[] segmentFields = segment["SegmentData"].ToString().Split('|');
            segmentsContained.Add(segmentFields[0]);
            //used later to validate existance of segments.
            System.Array<System.String>.INDEXER __dummyScrutVar1 = segmentFields[0];
            if (__dummyScrutVar1.equals("MSH"))
            {
                continue;
            }
            else //validation?
            if (__dummyScrutVar1.equals("SCH"))
            {
                if (!StringSupport.equals(segmentFields[2], "{ENCID}"))
                {
                    //eCW's documentation is wrong. SCH.01 is not used as appointment num, instead SCH.02 is used for appointment num.
                    retVal += "SIU HL7 message is not sending visit number in field SCH.01\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (!StringSupport.equals(segmentFields[7], "{ENCREASON}"))
                {
                    retVal += "SIU HL7 message is not sending visit reason in field SCH.07\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (!StringSupport.equals(segmentFields[8], "{VISITTYPE}"))
                {
                    retVal += "SIU HL7 message is not sending visit type in field SCH.08\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (false)
                {
                    //segmentFields[9]!="{???}") { //Don't know what this should look like when properly configured. TODO
                    retVal += "SIU HL7 message is not sending appointment duration in minutes in field SCH.09\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (false)
                {
                    //segmentFields[10]!="{???}") { //Don't know what this should look like when properly configured. TODO
                    retVal += "SIU HL7 message is not sending appointment duration units in field SCH.10\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                String[] SCH11 = segmentFields[11].Split('^');
                if (false)
                {
                    //SCH11[2]!="{???}") { //Don't
                    retVal += "SIU HL7 message is not sending appointment duration in field SCH.11.02\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (!StringSupport.equals(SCH11[3], "{ENCSDATETIME}"))
                {
                    retVal += "SIU HL7 message is not sending appointment start time in field SCH.11.03\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (!StringSupport.equals(SCH11[4], "{ENCEDATETIME}"))
                {
                    retVal += "SIU HL7 message is not sending appointment end time in field SCH.11.04\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                continue;
            }
            else //if(segmentFields[25]!="{STATUS}") {//according to documentation, we need this, but actually we never try to reference it.
            //  retVal+="SIU HL7 message is not sending visit status in field SCH.25\r\n";
            //  errors++;
            //  validMessage=false;
            //}
            if (__dummyScrutVar1.equals("PID"))
            {
                if (!StringSupport.equals(segmentFields[2], "{PID}"))
                {
                    retVal += "SIU HL7 message is not sending eCW's internal patient number in field PID.02\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (!StringSupport.equals(segmentFields[4], "{CONTNO}") && !Programs.usingEcwTightOrFullMode())
                {
                    retVal += "SIU HL7 message is not sending eCW's account number in field PID.04\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (!StringSupport.equals(segmentFields[5], "{PLN}^{PFN}^{PMN}"))
                {
                    retVal += "SIU HL7 message is not sending patient's name correctly in field PID.05\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (!StringSupport.equals(segmentFields[7], "{PDOB}"))
                {
                    retVal += "SIU HL7 message is not sending patient's date of birth in field PID.07\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                if (!StringSupport.equals(segmentFields[8], "{PSEX}"))
                {
                    retVal += "SIU HL7 message is not sending patient's gender in field PID.08\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                continue;
            }
            else //No checking of optional fields.
            if (__dummyScrutVar1.equals("PV1"))
            {
                if (!StringSupport.equals(segmentFields[7], "{ODDRID}^{ODLN}^{ODFN}"))
                {
                    retVal += "SIU HL7 message is not sending provider id in field PV1.07\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                continue;
            }
            else if (__dummyScrutVar1.equals("AIG"))
            {
                if (!StringSupport.equals(segmentFields[3], "{RSDRID}^{RSLN}^{RSFN}"))
                {
                    retVal += "SIU HL7 message is not sending provider/resource id in field AIG.03\r\n";
                    errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
                    validMessage = false;
                }
                 
                continue;
            }
            else
            {
                continue;
            }     
        }
        //Validate existance of segments
        if (!segmentsContained.Contains("SCH"))
        {
            retVal += "No SCH segment found in SIU HL7 message.\r\n";
            errors.setValue(errors.getValue() + 7);
            //no segment plus 6 sub errors.
            validMessage = false;
        }
         
        if (!segmentsContained.Contains("PID"))
        {
            retVal += "No PID segment found in SIU HL7 message.\r\n";
            if (!Programs.usingEcwTightOrFullMode())
            {
                errors.setValue(errors.getValue() + 1, ReturnPreOrPostValue.POST);
            }
             
            //to account for not sending eCW's account number
            errors.setValue(errors.getValue() + 5);
            //no segment plus 4 sub errors.
            validMessage = false;
        }
         
        if (!segmentsContained.Contains("AIG") && !segmentsContained.Contains("PV1"))
        {
            retVal += "No AIG or PV1 segments found in SIU HL7 message. Appointments will use patient's default primary provider.\r\n";
            //ecwSIU.cs sets this when in-processing SIU message.
            validMessage = false;
        }
         
        //If everything above checks out return a success message
        if (validMessage && verbose)
        {
            retVal += "Found properly formed SIU HL7 message definition.\r\n";
        }
         
        return retVal;
    }

    /*private string verifyAsDFTMessage(int interfaceID,int messageID,bool verbose) {
    			string retVal="";
    			bool validMessage=true;
    			List<string> segmentsContained=new List<string>();
    			DataTable hl7Segments=MySqlHelper.ExecuteDataset(connString,"SELECT SegmentData FROM hl7segment_details WHERE InterfaceID="+interfaceID+" AND Messageid="+messageID+";").Tables[0];
    			//validate segments based on content
    			foreach(DataRow segment in hl7Segments.Rows) {
    				string[] segmentValues=segment["SegmentData"].ToString().Split('|');
    				segmentsContained.Add(segmentValues[0]);//used later to validate existance of segments.
    				switch(segmentValues[0]) {
    					case "PID":
    						if(segmentValues[2]!="{CONTNO}") {
    							retVal+="DFT HL7 message is not sending eCW's account number in field PID.2\r\n";
    							validMessage=false;
    						}
    						if(segmentValues[3]!="{PID}") {
    							retVal+="DFT HL7 message is not sending eCW's account number in field PID.4\r\n";
    							validMessage=false;
    						}
    						continue;
    					case "PV1":
    						//TODO: Need example of a valid DFT message to validate this segment.
    						continue;
    					case "ZX1":
    						//TODO: Need example of a valid DFT message to validate this segment.
    						continue;
    					default:
    						continue;
    				}
    			}
    			//Validate existance of segments
    			if(!segmentsContained.Contains("PID")) {
    				retVal+="No PID segment found in DFT HL7 message.\r\n";
    				validMessage=false;
    			}
    			if(!segmentsContained.Contains("PV1")) {
    				retVal+="No PV1 segment found in DFT HL7 message.\r\n";
    				validMessage=false;
    			}
    			if(!segmentsContained.Contains("FT1")) {
    				retVal+="No FT1 segment found in DFT HL7 message.\r\n";
    				validMessage=false;
    			}
    			if(!segmentsContained.Contains("ZX1")) {
    				retVal+="No ZX1 segment found in DFT HL7 message.\r\n";
    				validMessage=false;
    			}
    			//If everything above checks out return a success message
    			if(validMessage && verbose) {
    				retVal+="Found properly formed DFT HL7 message definition.\r\n";
    			}
    			return retVal;
    		}*/
    private String checkDentalVisitTypes(boolean verbose) throws Exception {
        String retval = "";
        DataTable tableVisitCodesJOINpmCodes = new DataTable();
        try
        {
            tableVisitCodesJOINpmCodes = MySqlHelper.ExecuteDataset(connString, "SELECT * FROM visitcodes LEFT OUTER JOIN pmcodes ON visitcodes.Name=pmcodes.ecwcode WHERE dentalvisit=1;").Tables[0];
        }
        catch (Exception ex)
        {
            return ex.Message + "\r\n";
        }

        //left outer join should show null in ecwcode if there is no corresponding pmcode for the visitcode.
        if (verbose || tableVisitCodesJOINpmCodes.Select("ecwcode is null").Length > 0 || tableVisitCodesJOINpmCodes.Rows.Count == 0)
        {
            retval += "\r\n";
            retval += "   Dental Visit Codes\r\n";
            retval += "".PadRight(90, '*') + "\r\n";
            for (Object __dummyForeachVar4 : tableVisitCodesJOINpmCodes.Rows)
            {
                DataRow dRow = (DataRow)__dummyForeachVar4;
                if (StringSupport.equals(dRow["ecwcode"].ToString(), ""))
                {
                    retval += "Dental visit code named \"" + dRow["Description"].ToString() + "\" found but not set up.\r\n";
                }
                else if (verbose)
                {
                    retval += "Dental visit code named \"" + dRow["Description"].ToString() + "\" found.\r\n";
                }
                  
            }
            if (tableVisitCodesJOINpmCodes.Rows.Count == 0)
            {
                retval += "No dental visit codes found or set up.\r\n";
            }
             
        }
         
        return retval;
    }

    private List<int> columnToListHelper(DataTable dataTable, String colName) throws Exception {
        List<int> retVal = new List<int>();
        for (Object __dummyForeachVar5 : dataTable.Rows)
        {
            DataRow dRow = (DataRow)__dummyForeachVar5;
            retVal.Add((int)dRow[colName]);
        }
        return retVal;
    }

    private String testTemplate(boolean verbose) throws Exception {
        StringBuilder retVal = new StringBuilder();
        boolean failed = true;
        String command = "SHOW TABLES;";
        DataTable qResult = MySql.Data.MySqlClient.MySqlHelper.ExecuteDataset(connString, command).Tables[0];
        //or
        MySql.Data.MySqlClient.MySqlDataReader mtDataReader = new MySql.Data.MySqlClient.MySqlDataReader();
        //Place check code here. Also, use a reader, table or both as shown above.
        if (verbose || failed)
        {
            retVal.Clear();
            retVal.Append("HL7 message definitions are not formed properly. //TODO: maybe add some more specific details here.");
        }
         
        return retVal.ToString();
    }

    private void checkShow_KeyPress(Object sender, KeyPressEventArgs e) throws Exception {
        KeysConverter kc = new KeysConverter();
        try
        {
            arbitraryStringName.Append(e.KeyChar);
        }
        catch (Exception ex)
        {
        }

        //fail VERY silently. Mwa Ha Ha.
        if (arbitraryStringName.ToString().EndsWith("X"))
        {
            //Clear string if (upper case) 'X' is pressed.
            arbitraryStringName.Clear();
        }
         
        if (StringSupport.equals(arbitraryStringName.ToString(), "open") || StringSupport.equals(arbitraryStringName.ToString(), "There is no cow level"))
        {
            FormEcwDiagAdv FormECWA = new FormEcwDiagAdv();
            FormECWA.ShowDialog();
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
        this.textLog = new System.Windows.Forms.TextBox();
        this.checkShow = new System.Windows.Forms.CheckBox();
        this.butRunCheck = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textLog
        //
        this.textLog.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textLog.Location = new System.Drawing.Point(12, 12);
        this.textLog.Multiline = true;
        this.textLog.Name = "textLog";
        this.textLog.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textLog.Size = new System.Drawing.Size(620, 510);
        this.textLog.TabIndex = 4;
        //
        // checkShow
        //
        this.checkShow.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkShow.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShow.Location = new System.Drawing.Point(638, 43);
        this.checkShow.Name = "checkShow";
        this.checkShow.Size = new System.Drawing.Size(75, 20);
        this.checkShow.TabIndex = 17;
        this.checkShow.Text = "Verbose";
        this.checkShow.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.checkShow_KeyPress);
        //
        // butRunCheck
        //
        this.butRunCheck.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRunCheck.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRunCheck.setAutosize(true);
        this.butRunCheck.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRunCheck.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRunCheck.setCornerRadius(4F);
        this.butRunCheck.Location = new System.Drawing.Point(638, 12);
        this.butRunCheck.Name = "butRunCheck";
        this.butRunCheck.Size = new System.Drawing.Size(75, 25);
        this.butRunCheck.TabIndex = 5;
        this.butRunCheck.Text = "Run Check";
        this.butRunCheck.Click += new System.EventHandler(this.butRunCheck_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(638, 498);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormEcwDiag
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(725, 534);
        this.Controls.Add(this.checkShow);
        this.Controls.Add(this.butRunCheck);
        this.Controls.Add(this.textLog);
        this.Controls.Add(this.butCancel);
        this.Name = "FormEcwDiag";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "eClinical Works Diagnostic";
        this.Load += new System.EventHandler(this.FormEcwDiag_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textLog = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butRunCheck;
    private System.Windows.Forms.CheckBox checkShow = new System.Windows.Forms.CheckBox();
}
//private string Test1(bool verbose) {//  StringBuilder retVal=new StringBuilder();//  bool failed=true;//  MySql.Data.MySqlClient.MySqlDataReader myDrTables;//  MySql.Data.MySqlClient.MySqlDataReader myDrFields;//  try {//    myDrTables=MySql.Data.MySqlClient.MySqlHelper.ExecuteReader(connString,"SHOW TABLES"+(textTables.Text==""?"":" LIKE '%"+POut.String(textTables.Text)+"%'"));//    if(myDrTables.HasRows) {//      failed=false;//    }//    while(myDrTables.Read()) {//      if(myDrTables.GetValue(0).ToString().Contains("copy")//copy is a reserved word//        ) {//        continue;//      }//      try {//        if(textValue.Text!="" && MySql.Data.MySqlClient.MySqlHelper.ExecuteDataset(connString,queryTableByValue(myDrTables.GetValue(0).ToString())).Tables[0].Rows.Count==0) {//          continue;//skip tables with no rows that match the match by value.//        }//      retVal.Append("**********************************************************\r\n"//                   +"**             "+myDrTables.GetValue(0).ToString()+" Rows:"+MySql.Data.MySqlClient.MySqlHelper.ExecuteDataset(connString,"SELECT COUNT(*) AS numRows FROM "+myDrTables.GetValue(0).ToString()).Tables[0].Rows[0]["numRows"].ToString()+"\r\n"//                   +"**********************************************************\r\n");//      myDrFields=MySql.Data.MySqlClient.MySqlHelper.ExecuteReader(connString,queryTableByValue(myDrTables.GetValue(0).ToString()));//"SELECT * FROM `"+POut.String(myDrTables.GetValue(0).ToString())+"` LIMIT 100");//        int row=0;//        while(myDrFields.Read()) {//          retVal.Append("Row "+row+":  ");//          row++;//          for(int i=0;i<myDrFields.FieldCount;i++) {//            retVal.Append(myDrFields.GetValue(i).ToString()+"  ||  ");//          }//          retVal.Append("\r\n");//        }//      }//      catch(Exception ex2) {//        retVal.Append("Error accesing table:"+myDrTables.GetValue(0).ToString()+"\r\nError Message:"+ex2.Message+"\r\n");//      }//    }//  }//  catch(Exception ex) {//  #if DEBUG//    retVal.Append(ex.Message+"\r\n");//    return retVal.ToString();//  #endif//  }//  //  if(myDrTables.HasRows) {//  //    failed=false;//  //  }//  //  while(myDrTables.Read()){//  //    for(int i=0;i<myDrTables.FieldCount;i++){//  //      retVal+=myDrTables.GetValue(i).ToString()+"  :  ";//  //    }//  //    retVal+="\r\n";//  //  }//  //  //DataTable myDT=MySql.Data.MySqlClient.MySqlHelper.ExecuteDataset(connString,"SELECT * FROM userod").Tables[0];//  //  //retVal+="UserOD Table Contents (abridged):\r\n";//  //  //for(int i=0;i<myDT.Rows.Count;i++) {//  //  //  retVal+="Username :"+myDT.Rows[i]["UserName"]+"\r\n     EmployeeNum :"+myDT.Rows[i]["EmployeeNum"]+"\r\n";//  //  //}//  //if(verbose || failed) {//  //  return retVal+"Test1 has "+(failed?"failed. Please do these things or tell your \"eCW Commander\", or whatever they are called, about this.":"passed.")+"\r\n";//  //}//  return retVal.ToString();//}/**
* //SELECT * FROM <tablename> WHERE <Any column contains textValue.text>
* //_
* //Used for searching all fields of a table for a specified value
* // 
*  @param tableName 
* // 
*  @return SQL query that selects applicable rows.
*///private string queryTableByValue(string tableName) {//  StringBuilder retVal=new StringBuilder();//  retVal.Append("SELECT * FROM "+tableName);//  if(textValue.Text=="") {//    return retVal.ToString()+" LIMIT 100;";//  }//  DataTable cols=MySql.Data.MySqlClient.MySqlHelper.ExecuteDataset(connString,"SHOW COLUMNS FROM "+tableName).Tables[0];//  retVal.Append(" WHERE ");//  for(int i=0;i<cols.Rows.Count;i++) {//    if(i!=0) {//      retVal.Append("OR");//    }//    retVal.Append(" `"+cols.Rows[i]["Field"].ToString()+"` LIKE '%"+POut.String(textValue.Text)+"%' ");//  }//  return retVal.ToString()+" LIMIT 100;";//}

//private string Test1(bool verbose) {//  StringBuilder retVal=new StringBuilder();//  bool failed=true;//  MySql.Data.MySqlClient.MySqlDataReader myDrTables;//  MySql.Data.MySqlClient.MySqlDataReader myDrFields;//  try {//    myDrTables=MySql.Data.MySqlClient.MySqlHelper.ExecuteReader(connString,"SHOW TABLES"+(textTables.Text==""?"":" LIKE '%"+POut.String(textTables.Text)+"%'"));//    if(myDrTables.HasRows) {//      failed=false;//    }//    while(myDrTables.Read()) {//      if(myDrTables.GetValue(0).ToString().Contains("copy")//copy is a reserved word//        ) {//        continue;//      }//      try {//        if(textValue.Text!="" && MySql.Data.MySqlClient.MySqlHelper.ExecuteDataset(connString,queryTableByValue(myDrTables.GetValue(0).ToString())).Tables[0].Rows.Count==0) {//          continue;//skip tables with no rows that match the match by value.//        }//      retVal.Append("**********************************************************\r\n"//                   +"**             "+myDrTables.GetValue(0).ToString()+" Rows:"+MySql.Data.MySqlClient.MySqlHelper.ExecuteDataset(connString,"SELECT COUNT(*) AS numRows FROM "+myDrTables.GetValue(0).ToString()).Tables[0].Rows[0]["numRows"].ToString()+"\r\n"//                   +"**********************************************************\r\n");//      myDrFields=MySql.Data.MySqlClient.MySqlHelper.ExecuteReader(connString,queryTableByValue(myDrTables.GetValue(0).ToString()));//"SELECT * FROM `"+POut.String(myDrTables.GetValue(0).ToString())+"` LIMIT 100");//        int row=0;//        while(myDrFields.Read()) {//          retVal.Append("Row "+row+":  ");//          row++;//          for(int i=0;i<myDrFields.FieldCount;i++) {//            retVal.Append(myDrFields.GetValue(i).ToString()+"  ||  ");//          }//          retVal.Append("\r\n");//        }//      }//      catch(Exception ex2) {//        retVal.Append("Error accesing table:"+myDrTables.GetValue(0).ToString()+"\r\nError Message:"+ex2.Message+"\r\n");//      }//    }//  }//  catch(Exception ex) {//  #if DEBUG//    retVal.Append(ex.Message+"\r\n");//    return retVal.ToString();//  #endif//  }//  //  if(myDrTables.HasRows) {//  //    failed=false;//  //  }//  //  while(myDrTables.Read()){//  //    for(int i=0;i<myDrTables.FieldCount;i++){//  //      retVal+=myDrTables.GetValue(i).ToString()+"  :  ";//  //    }//  //    retVal+="\r\n";//  //  }//  //  //DataTable myDT=MySql.Data.MySqlClient.MySqlHelper.ExecuteDataset(connString,"SELECT * FROM userod").Tables[0];//  //  //retVal+="UserOD Table Contents (abridged):\r\n";//  //  //for(int i=0;i<myDT.Rows.Count;i++) {//  //  //  retVal+="Username :"+myDT.Rows[i]["UserName"]+"\r\n     EmployeeNum :"+myDT.Rows[i]["EmployeeNum"]+"\r\n";//  //  //}//  //if(verbose || failed) {//  //  return retVal+"Test1 has "+(failed?"failed. Please do these things or tell your \"eCW Commander\", or whatever they are called, about this.":"passed.")+"\r\n";//  //}//  return retVal.ToString();//}/**
* //SELECT * FROM <tablename> WHERE <Any column contains textValue.text>
* //_
* //Used for searching all fields of a table for a specified value
* // 
*  @param tableName 
* // 
*  @return SQL query that selects applicable rows.
*///private string queryTableByValue(string tableName) {//  StringBuilder retVal=new StringBuilder();//  retVal.Append("SELECT * FROM "+tableName);//  if(textValue.Text=="") {//    return retVal.ToString()+" LIMIT 100;";//  }//  DataTable cols=MySql.Data.MySqlClient.MySqlHelper.ExecuteDataset(connString,"SHOW COLUMNS FROM "+tableName).Tables[0];//  retVal.Append(" WHERE ");//  for(int i=0;i<cols.Rows.Count;i++) {//    if(i!=0) {//      retVal.Append("OR");//    }//    retVal.Append(" `"+cols.Rows[i]["Field"].ToString()+"` LIKE '%"+POut.String(textValue.Text)+"%' ");//  }//  return retVal.ToString()+" LIMIT 100;";//}