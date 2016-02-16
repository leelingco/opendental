//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package DocumentationBuilder;

import CodeBase.MsgBoxCopyPaste;
import CodeBase.ODFileUtils;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import DocumentationBuilder.DataConnection;
import DocumentationBuilder.Form1;


//How to format comments to trigger links://FK to definition.DefNum is triggered by "FK to ".  It then looks for ".".  So anything can follow after.//and://"Enum:" Then, the enum name must follow.  It must then be followed by a space or by nothing at all.  NO PERIOD allowed.
public class Form1  extends Form 
{

    DataConnection dcon;
    String command = new String();
    XPathNavigator Navigator = new XPathNavigator();
    List<String> MissingTables = new List<String>();
    public Form1() throws Exception {
        dcon = new DataConnection();
        initializeComponent();
    }

    private void form1_Load(Object sender, EventArgs e) throws Exception {
        textConnStr.Text = dcon.ConnStr;
    }

    private void butBuild_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        MissingTables = new List<String>();
        //dcon=new DataConnection();
        command = "SHOW TABLES";
        DataTable table = dcon.getTable(command);
        String outputFile = ODFileUtils.CombinePaths(new String[]{ "..", "..", "OpenDentalDocumentation.xml" });
        XmlWriterSettings settings = new XmlWriterSettings();
        settings.Indent = true;
        settings.IndentChars = ("    ");
        //input:
        String inputFile = ODFileUtils.CombinePaths(new String[]{ "..", "..", "..", "OpenDentBusiness", "bin", "Release", "OpenDentBusiness.xml" });
        XmlDocument document = new XmlDocument();
        document.Load(inputFile);
        Navigator = document.CreateNavigator();
        XmlWriter writer = XmlWriter.Create(outputFile, settings);
        try
        {
            {
                //<?xml-stylesheet type="text/xsl" href="OpenDentalDocumentation.xsl"?>
                writer.WriteProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"OpenDentalDocumentation.xsl\"");
                //("<?xml-stylesheet type=\"text/xsl\" href=\"OpenDentalDocumentation.xsl\"?>");
                writer.WriteStartElement("database");
                writer.WriteAttributeString("version", textVersion.Text);
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    WriteTable(writer, table.Rows[i][0].ToString());
                }
                writer.WriteEndElement();
                writer.Flush();
            }
        }
        finally
        {
            if (writer != null)
                Disposable.mkDisposable(writer).dispose();
             
        }
        if (MissingTables.Count > 0)
        {
            String s = "";
            for (int i = 0;i < MissingTables.Count;i++)
            {
                if (i > 0)
                {
                    s += "\r\n";
                }
                 
                s += MissingTables[i];
            }
            MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(s);
            msgbox.ShowDialog();
            Application.Exit();
            return ;
        }
         
        //ProcessStartInfo startInfo=new ProcessStartInfo();
        //Process.Start("Notepad.exe",outputFile);
        //Process.Start("iexplore.exe",outputFile);
        Process.Start(outputFile);
        Application.Exit();
    }

    private void writeTable(XmlWriter writer, String tableName) throws Exception {
        if (StringSupport.equals(tableName, "anestheticdata") || StringSupport.equals(tableName, "anestheticrecord") || StringSupport.equals(tableName, "anesthmedsgiven") || StringSupport.equals(tableName, "anesthmedsintake") || StringSupport.equals(tableName, "anesthmedsinventory") || StringSupport.equals(tableName, "anesthmedsinventoryadj") || StringSupport.equals(tableName, "anesthmedsuppliers") || StringSupport.equals(tableName, "anesthscore") || StringSupport.equals(tableName, "anesthvsdata") || StringSupport.equals(tableName, "reseller") || StringSupport.equals(tableName, "resellerservice") || StringSupport.equals(tableName, ""))
        {
            return ;
        }
         
        writer.WriteStartElement("table");
        writer.WriteAttributeString("name", tableName);
        //table summary
        writer.WriteStartElement("summary");
        writer.WriteString(getSummary("T:OpenDentBusiness." + getTableName(tableName)));
        writer.WriteEndElement();
        command = "SHOW COLUMNS FROM " + tableName;
        DataTable table = dcon.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            WriteColumn(writer, i, tableName, table.Rows[i][0].ToString(), table.Rows[i][1].ToString());
        }
        writer.WriteEndElement();
    }

    private void writeColumn(XmlWriter writer, int order, String tableName, String colName, String sqlType) throws Exception {
        writer.WriteStartElement("column");
        writer.WriteAttributeString("order", order.ToString());
        writer.WriteAttributeString("name", colName);
        if (StringSupport.equals(sqlType, "tinyint(3) unsigned"))
        {
            sqlType = "tinyint";
        }
        else if (StringSupport.equals(sqlType, "tinyint(1) unsigned"))
        {
            //not used very much
            sqlType = "tinyint";
        }
        else if (StringSupport.equals(sqlType, "smallint(5) unsigned"))
        {
            sqlType = "smallint";
        }
        else if (StringSupport.equals(sqlType, "mediumint(8) unsigned"))
        {
            sqlType = "mediumint";
        }
        else if (sqlType.EndsWith(" unsigned"))
        {
            sqlType = sqlType.Substring(0, sqlType.Length - 9);
        }
             
        writer.WriteAttributeString("type", sqlType);
        String summary = getSummary("F:OpenDentBusiness." + getTableName(tableName) + "." + colName);
        if (StringSupport.equals(summary, ""))
        {
            //this deals with the situation where the new data access layer has public Properites instead of public Fields.
            summary = getSummary("P:OpenDentBusiness." + getTableName(tableName) + "." + colName);
        }
         
        if (summary.StartsWith("FK to "))
        {
            //eg FK to definition.DefNum
            int indexDot = summary.IndexOf(".");
            if (indexDot != -1)
            {
                String fkTable = summary.Substring(6, indexDot - 6);
                writer.WriteAttributeString("fk", fkTable);
            }
             
        }
         
        //column summary
        writer.WriteStartElement("summary");
        writer.WriteString(summary);
        writer.WriteEndElement();
        if (summary.StartsWith("Enum:"))
        {
            int indexSpace = summary.IndexOf(" ");
            //the space will be found after the name of the enum
            String enumName = "";
            if (indexSpace == -1 && summary.Length > 5)
            {
                //Enum is listed, but no other comments.
                enumName = summary.Substring(5);
            }
            else if (indexSpace > 5)
            {
                //This if statement just protects against a space right after the Enum:
                enumName = summary.Substring(5, indexSpace - 5);
            }
              
            if (!StringSupport.equals(enumName, ""))
            {
                writeEnum(writer,enumName);
            }
             
        }
         
        writer.WriteEndElement();
    }

    private void writeEnum(XmlWriter writer, String enumName) throws Exception {
        if (enumName.EndsWith("."))
        {
            throw new Exception("ERROR! enum: " + enumName + " ends with \".\" and this causes the documentation to fail.\r\nCorrect the enum summary in the table type and rebuild Open Dental in release mode to update the serialization file.");
        }
         
        String summary = "";
        //get an ordered list from OpenDental.xml
        //T:OpenDental.AccountType
        //first the summary for the enum itsef
        XPathNavigator navEnum = Navigator.SelectSingleNode("//member[@name='T:OpenDentBusiness." + enumName + "']");
        if (navEnum != null)
        {
            //Enumerations that are not in the Enumerations.cs file will be null.
            summary = navEnum.Value;
        }
         
        writer.WriteStartElement("Enumeration");
        writer.WriteAttributeString("name", enumName);
        writer.WriteElementString("summary", summary);
        //No summary if the enum node was not found.  This is for enumerations that are not present in Enumerations.cs
        //now, the individual enumsItems
        //F:OpenDental.AccountType.Asset
        //*[starts-with(name(),'B')]
        XPathNodeIterator nodes = Navigator.Select("//member[contains(@name,'F:OpenDentBusiness." + enumName + ".')]");
        if (nodes.Count == 0)
        {
            throw new Exception("ERROR! enum: " + enumName + " was not found.  Something is wrong with the serialized xml documentation.");
        }
         
        //("//member[@name='F:OpenDental."+enumName+".*']");
        String itemName = new String();
        int lastDot = new int();
        while (nodes.MoveNext())
        {
            writer.WriteStartElement("EnumValue");
            summary = nodes.Current.Value;
            //nodes.Current.MoveToAttribute("name",null);
            itemName = nodes.Current.GetAttribute("name", "");
            lastDot = itemName.LastIndexOf(".");
            itemName = itemName.Substring(lastDot + 1);
            writer.WriteAttributeString("name", itemName);
            writer.WriteString(summary);
            writer.WriteEndElement();
        }
        writer.WriteEndElement();
    }

    /**
    * Gets the tablename that's used in the program based on the database tablename.  They are usually the same, except for capitalization.
    */
    private String getTableName(String dbTable) throws Exception {
        System.String __dummyScrutVar0 = dbTable;
        //This section can be enabled temporarily to check for missing tables:
        /*
        				default:
        					if(!MissingTables.Contains(dbTable)){
        						MissingTables.Add(dbTable); 
        					}
        					return "";*/
        //The only classes that need to be included below are those that have a capital letter in addition to the first one
        //or those which are obsolete.
        if (__dummyScrutVar0.equals("accountingautopay"))
        {
            return "AccountingAutoPay";
        }
        else if (__dummyScrutVar0.equals("allergydef"))
        {
            return "AllergyDef";
        }
        else if (__dummyScrutVar0.equals("appointmentrule"))
        {
            return "AppointmentRule";
        }
        else if (__dummyScrutVar0.equals("apptfield"))
        {
            return "ApptField";
        }
        else if (__dummyScrutVar0.equals("apptfielddef"))
        {
            return "ApptFieldDef";
        }
        else if (__dummyScrutVar0.equals("apptview"))
        {
            return "ApptView";
        }
        else if (__dummyScrutVar0.equals("apptviewitem"))
        {
            return "ApptViewItem";
        }
        else if (__dummyScrutVar0.equals("autocode"))
        {
            return "AutoCode";
        }
        else if (__dummyScrutVar0.equals("autocodecond"))
        {
            return "AutoCodeCond";
        }
        else if (__dummyScrutVar0.equals("autocodeitem"))
        {
            return "AutoCodeItem";
        }
        else if (__dummyScrutVar0.equals("automationcondition"))
        {
            return "AutomationCondition";
        }
        else if (__dummyScrutVar0.equals("autonote"))
        {
            return "AutoNote";
        }
        else if (__dummyScrutVar0.equals("autonotecontrol"))
        {
            return "AutoNoteControl";
        }
        else if (__dummyScrutVar0.equals("canadianclaim"))
        {
            return "CanadianClaim";
        }
        else if (__dummyScrutVar0.equals("canadianextract"))
        {
            return "CanadianExtract";
        }
        else if (__dummyScrutVar0.equals("canadiannetwork"))
        {
            return "CanadianNetwork";
        }
        else if (__dummyScrutVar0.equals("centralconnection"))
        {
            return "CentralConnection";
        }
        else if (__dummyScrutVar0.equals("chartview"))
        {
            return "ChartView";
        }
        else if (__dummyScrutVar0.equals("claimattach"))
        {
            return "ClaimAttach";
        }
        else if (__dummyScrutVar0.equals("claimcondcodelog"))
        {
            return "ClaimCondCodeLog";
        }
        else if (__dummyScrutVar0.equals("claimform"))
        {
            return "ClaimForm";
        }
        else if (__dummyScrutVar0.equals("claimformitem"))
        {
            return "ClaimFormItem";
        }
        else if (__dummyScrutVar0.equals("claimpayment"))
        {
            return "ClaimPayment";
        }
        else if (__dummyScrutVar0.equals("claimproc"))
        {
            return "ClaimProc";
        }
        else if (__dummyScrutVar0.equals("claimvalcodelog"))
        {
            return "ClaimValCodeLog";
        }
        else if (__dummyScrutVar0.equals("clockevent"))
        {
            return "ClockEvent";
        }
        else if (__dummyScrutVar0.equals("computerpref"))
        {
            return "ComputerPref";
        }
        else if (__dummyScrutVar0.equals("covcat"))
        {
            return "CovCat";
        }
        else if (__dummyScrutVar0.equals("covspan"))
        {
            return "CovSpan";
        }
        else if (__dummyScrutVar0.equals("creditcard"))
        {
            return "CreditCard";
        }
        else if (__dummyScrutVar0.equals("custrefentry"))
        {
            return "CustRefEntry";
        }
        else if (__dummyScrutVar0.equals("custreference"))
        {
            return "CustReference";
        }
        else if (__dummyScrutVar0.equals("dashboardar"))
        {
            return "DashboardAR";
        }
        else if (__dummyScrutVar0.equals("definition"))
        {
            return "Def";
        }
        else if (__dummyScrutVar0.equals("deletedobject"))
        {
            return "DeletedObject";
        }
        else if (__dummyScrutVar0.equals("dictcustom"))
        {
            return "DictCustom";
        }
        else if (__dummyScrutVar0.equals("diseasedef"))
        {
            return "DiseaseDef";
        }
        else if (__dummyScrutVar0.equals("displayfield"))
        {
            return "DisplayField";
        }
        else if (__dummyScrutVar0.equals("docattach"))
        {
            return "DocAttach";
        }
        else if (__dummyScrutVar0.equals("documentmisc"))
        {
            return "DocumentMisc";
        }
        else if (__dummyScrutVar0.equals("drugmanufacturer"))
        {
            return "DrugManufacturer";
        }
        else if (__dummyScrutVar0.equals("drugunit"))
        {
            return "DrugUnit";
        }
        else if (__dummyScrutVar0.equals("eduresource"))
        {
            return "EduResource";
        }
        else if (__dummyScrutVar0.equals("ehrmeasure"))
        {
            return "EhrMeasure";
        }
        else if (__dummyScrutVar0.equals("ehrmeasureevent"))
        {
            return "EhrMeasureEvent";
        }
        else if (__dummyScrutVar0.equals("ehrprovkey"))
        {
            return "EhrProvKey";
        }
        else if (__dummyScrutVar0.equals("ehrquarterlykey"))
        {
            return "EhrQuarterlyKey";
        }
        else if (__dummyScrutVar0.equals("ehrsummaryccd"))
        {
            return "EhrSummaryCcd";
        }
        else if (__dummyScrutVar0.equals("electid"))
        {
            return "ElectID";
        }
        else if (__dummyScrutVar0.equals("emailaddress"))
        {
            return "EmailAddress";
        }
        else if (__dummyScrutVar0.equals("emailattach"))
        {
            return "EmailAttach";
        }
        else if (__dummyScrutVar0.equals("emailmessage"))
        {
            return "EmailMessage";
        }
        else if (__dummyScrutVar0.equals("emailtemplate"))
        {
            return "EmailTemplate";
        }
        else if (__dummyScrutVar0.equals("eobattach"))
        {
            return "EobAttach";
        }
        else if (__dummyScrutVar0.equals("etransmessagetext"))
        {
            return "EtransMessageText";
        }
        else if (__dummyScrutVar0.equals("erxlog"))
        {
            return "ErxLog";
        }
        else if (__dummyScrutVar0.equals("feesched"))
        {
            return "FeeSched";
        }
        else if (__dummyScrutVar0.equals("formpat"))
        {
            return "FormPat";
        }
        else if (__dummyScrutVar0.equals("formularymed"))
        {
            return "FormularyMed";
        }
        else if (__dummyScrutVar0.equals("graphicassembly"))
        {
            return "GraphicAssembly Not Used";
        }
        else if (__dummyScrutVar0.equals("graphicelement"))
        {
            return "graphicelement Not Used";
        }
        else if (__dummyScrutVar0.equals("graphicpoint"))
        {
            return "graphicpoint Not Used";
        }
        else if (__dummyScrutVar0.equals("graphicshape"))
        {
            return "graphicshape Not Used";
        }
        else if (__dummyScrutVar0.equals("graphictype"))
        {
            return "graphictype Not Used";
        }
        else if (__dummyScrutVar0.equals("grouppermission"))
        {
            return "GroupPermission";
        }
        else if (__dummyScrutVar0.equals("hl7def"))
        {
            return "HL7Def";
        }
        else if (__dummyScrutVar0.equals("hl7deffield"))
        {
            return "HL7DefField";
        }
        else if (__dummyScrutVar0.equals("hl7defmessage"))
        {
            return "HL7DefMessage";
        }
        else if (__dummyScrutVar0.equals("hl7defsegment"))
        {
            return "HL7DefSegment";
        }
        else if (__dummyScrutVar0.equals("hl7msg"))
        {
            return "HL7Msg";
        }
        else if (__dummyScrutVar0.equals("icd9"))
        {
            return "ICD9";
        }
        else if (__dummyScrutVar0.equals("insfilingcode"))
        {
            return "InsFilingCode";
        }
        else if (__dummyScrutVar0.equals("insfilingcodesubtype"))
        {
            return "InsFilingCodeSubtype";
        }
        else if (__dummyScrutVar0.equals("insplan"))
        {
            return "InsPlan";
        }
        else if (__dummyScrutVar0.equals("inssub"))
        {
            return "InsSub";
        }
        else if (__dummyScrutVar0.equals("installmentplan"))
        {
            return "InstallmentPlan";
        }
        else if (__dummyScrutVar0.equals("journalentry"))
        {
            return "JournalEntry";
        }
        else if (__dummyScrutVar0.equals("labcase"))
        {
            return "LabCase";
        }
        else if (__dummyScrutVar0.equals("labpanel"))
        {
            return "LabPanel";
        }
        else if (__dummyScrutVar0.equals("labresult"))
        {
            return "LabResult";
        }
        else if (__dummyScrutVar0.equals("labturnaround"))
        {
            return "LabTurnaround";
        }
        else if (__dummyScrutVar0.equals("languageforeign"))
        {
            return "LanguageForeign";
        }
        else if (__dummyScrutVar0.equals("lettermerge"))
        {
            return "LetterMerge";
        }
        else if (__dummyScrutVar0.equals("lettermergefield"))
        {
            return "LetterMergeField";
        }
        else if (__dummyScrutVar0.equals("medicalorder"))
        {
            return "MedicalOrder";
        }
        else if (__dummyScrutVar0.equals("medicationpat"))
        {
            return "MedicationPat";
        }
        else if (__dummyScrutVar0.equals("mountdef"))
        {
            return "MountDef";
        }
        else if (__dummyScrutVar0.equals("mountitem"))
        {
            return "MountItem";
        }
        else if (__dummyScrutVar0.equals("mountitemdef"))
        {
            return "MountItemDef";
        }
        else if (__dummyScrutVar0.equals("orthochart"))
        {
            return "OrthoChart";
        }
        else if (__dummyScrutVar0.equals("orionproc"))
        {
            return "OrionProc";
        }
        else if (__dummyScrutVar0.equals("patfield"))
        {
            return "PatField";
        }
        else if (__dummyScrutVar0.equals("patfielddef"))
        {
            return "PatFieldDef";
        }
        else if (__dummyScrutVar0.equals("patientnote"))
        {
            return "PatientNote";
        }
        else if (__dummyScrutVar0.equals("patientrace"))
        {
            return "PatientRace";
        }
        else if (__dummyScrutVar0.equals("patplan"))
        {
            return "PatPlan";
        }
        else if (__dummyScrutVar0.equals("payperiod"))
        {
            return "PayPeriod";
        }
        else if (__dummyScrutVar0.equals("payplan"))
        {
            return "PayPlan";
        }
        else if (__dummyScrutVar0.equals("payplancharge"))
        {
            return "PayPlanCharge";
        }
        else if (__dummyScrutVar0.equals("paysplit"))
        {
            return "PaySplit";
        }
        else if (__dummyScrutVar0.equals("perioexam"))
        {
            return "PerioExam";
        }
        else if (__dummyScrutVar0.equals("periomeasure"))
        {
            return "PerioMeasure";
        }
        else if (__dummyScrutVar0.equals("phonenumber"))
        {
            return "PhoneNumber";
        }
        else if (__dummyScrutVar0.equals("plannedappt"))
        {
            return "PlannedAppt";
        }
        else if (__dummyScrutVar0.equals("preference"))
        {
            return "Pref";
        }
        else if (__dummyScrutVar0.equals("procapptcolor"))
        {
            return "ProcApptColor";
        }
        else if (__dummyScrutVar0.equals("procbutton"))
        {
            return "ProcButton";
        }
        else if (__dummyScrutVar0.equals("procbuttonitem"))
        {
            return "ProcButtonItem";
        }
        else if (__dummyScrutVar0.equals("proccodenote"))
        {
            return "ProcCodeNote";
        }
        else if (__dummyScrutVar0.equals("procedurecode"))
        {
            return "ProcedureCode";
        }
        else if (__dummyScrutVar0.equals("procedurelog"))
        {
            return "Procedure";
        }
        else if (__dummyScrutVar0.equals("procgroupitem"))
        {
            return "ProcGroupItem";
        }
        else if (__dummyScrutVar0.equals("proclicense"))
        {
            return "proclicense not used";
        }
        else if (__dummyScrutVar0.equals("procnote"))
        {
            return "ProcNote";
        }
        else if (__dummyScrutVar0.equals("proctp"))
        {
            return "ProcTP";
        }
        else if (__dummyScrutVar0.equals("programproperty"))
        {
            return "ProgramProperty";
        }
        else if (__dummyScrutVar0.equals("providerident"))
        {
            return "ProviderIdent";
        }
        else if (__dummyScrutVar0.equals("questiondef"))
        {
            return "QuestionDef";
        }
        else if (__dummyScrutVar0.equals("quickpastecat"))
        {
            return "QuickPasteCat";
        }
        else if (__dummyScrutVar0.equals("quickpastenote"))
        {
            return "QuickPasteNote";
        }
        else if (__dummyScrutVar0.equals("refattach"))
        {
            return "RefAttach";
        }
        else if (__dummyScrutVar0.equals("registrationkey"))
        {
            return "RegistrationKey";
        }
        else if (__dummyScrutVar0.equals("recalltrigger"))
        {
            return "RecallTrigger";
        }
        else if (__dummyScrutVar0.equals("recalltype"))
        {
            return "RecallType";
        }
        else if (__dummyScrutVar0.equals("reminderrule"))
        {
            return "ReminderRule";
        }
        else if (__dummyScrutVar0.equals("repeatcharge"))
        {
            return "RepeatCharge";
        }
        else if (__dummyScrutVar0.equals("replicationserver"))
        {
            return "ReplicationServer";
        }
        else if (__dummyScrutVar0.equals("reqneeded"))
        {
            return "ReqNeeded";
        }
        else if (__dummyScrutVar0.equals("reqstudent"))
        {
            return "ReqStudent";
        }
        else if (__dummyScrutVar0.equals("rxalert"))
        {
            return "RxAlert";
        }
        else if (__dummyScrutVar0.equals("rxdef"))
        {
            return "RxDef";
        }
        else if (__dummyScrutVar0.equals("rxnorm"))
        {
            return "RxNorm";
        }
        else if (__dummyScrutVar0.equals("rxpat"))
        {
            return "RxPat";
        }
        else if (__dummyScrutVar0.equals("scheddefault"))
        {
            return "SchedDefault";
        }
        else if (__dummyScrutVar0.equals("scheduleop"))
        {
            return "ScheduleOp";
        }
        else if (__dummyScrutVar0.equals("schoolclass"))
        {
            return "SchoolClass";
        }
        else if (__dummyScrutVar0.equals("schoolcourse"))
        {
            return "SchoolCourse";
        }
        else if (__dummyScrutVar0.equals("screengroup"))
        {
            return "ScreenGroup";
        }
        else if (__dummyScrutVar0.equals("screenpat"))
        {
            return "ScreenPat";
        }
        else if (__dummyScrutVar0.equals("securitylog"))
        {
            return "SecurityLog";
        }
        else if (__dummyScrutVar0.equals("sheetdef"))
        {
            return "SheetDef";
        }
        else if (__dummyScrutVar0.equals("sheetfield"))
        {
            return "SheetField";
        }
        else if (__dummyScrutVar0.equals("sheetfielddef"))
        {
            return "SheetFieldDef";
        }
        else if (__dummyScrutVar0.equals("sigbutdef"))
        {
            return "SigButDef";
        }
        else if (__dummyScrutVar0.equals("sigbutdefelement"))
        {
            return "SigButDefElement";
        }
        else if (__dummyScrutVar0.equals("sigelement"))
        {
            return "SigElement";
        }
        else if (__dummyScrutVar0.equals("sigelementdef"))
        {
            return "SigElementDef";
        }
        else if (__dummyScrutVar0.equals("supplyneeded"))
        {
            return "SupplyNeeded";
        }
        else if (__dummyScrutVar0.equals("supplyorder"))
        {
            return "SupplyOrder";
        }
        else if (__dummyScrutVar0.equals("supplyorderitem"))
        {
            return "SupplyOrderItem";
        }
        else if (__dummyScrutVar0.equals("taskancestor"))
        {
            return "TaskAncestor";
        }
        else if (__dummyScrutVar0.equals("tasklist"))
        {
            return "TaskList";
        }
        else if (__dummyScrutVar0.equals("tasknote"))
        {
            return "TaskNote";
        }
        else if (__dummyScrutVar0.equals("tasksubscription"))
        {
            return "TaskSubscription";
        }
        else if (__dummyScrutVar0.equals("taskunread"))
        {
            return "TaskUnread";
        }
        else if (__dummyScrutVar0.equals("terminalactive"))
        {
            return "TerminalActive";
        }
        else if (__dummyScrutVar0.equals("timeadjust"))
        {
            return "TimeAdjust";
        }
        else if (__dummyScrutVar0.equals("timecardrule"))
        {
            return "TimeCardRule";
        }
        else if (__dummyScrutVar0.equals("toolbutitem"))
        {
            return "ToolButItem";
        }
        else if (__dummyScrutVar0.equals("toothgridcell"))
        {
            return "ToothGridCell";
        }
        else if (__dummyScrutVar0.equals("toothgridcol"))
        {
            return "ToothGridCol";
        }
        else if (__dummyScrutVar0.equals("toothgriddef"))
        {
            return "ToothGridDef";
        }
        else if (__dummyScrutVar0.equals("toothinitial"))
        {
            return "ToothInitial";
        }
        else if (__dummyScrutVar0.equals("treatplan"))
        {
            return "TreatPlan";
        }
        else if (__dummyScrutVar0.equals("usergroup"))
        {
            return "UserGroup";
        }
        else if (__dummyScrutVar0.equals("userquery"))
        {
            return "UserQuery";
        }
        else if (__dummyScrutVar0.equals("vaccinedef"))
        {
            return "VaccineDef";
        }
        else if (__dummyScrutVar0.equals("vaccinepat"))
        {
            return "VaccinePat";
        }
        else if (__dummyScrutVar0.equals("wikilistheaderwidth"))
        {
            return "WikiListHeaderWidth";
        }
        else if (__dummyScrutVar0.equals("wikipage"))
        {
            return "WikiPage";
        }
        else if (__dummyScrutVar0.equals("wikipagehist"))
        {
            return "WikiPageHist";
        }
        else if (__dummyScrutVar0.equals("xchargetransaction"))
        {
            return "XChargeTransaction";
        }
        else if (__dummyScrutVar0.equals("zipcode"))
        {
            return "ZipCode";
        }
                                                                                                                                                                                     
        return dbTable.Substring(0, 1).ToUpper() + dbTable.Substring(1);
    }

    /*single cap classes:
    			account
    			adjustment
    			appointment
    			benefit
    			carrier
    			claim
    			clearinghouse
    			clinic
    			commlog
    			computer
    			contact
    			county
    			deposit
    			disease
    			document
    			dunning
    			employee
    			employer
    			etrans
    			fee
    			instructor
    			laboratory
    			language
    			letter
    			medication
    			mount
    			operatory
    			patient
    			payment
    			preference
    			printer
    			program
    			provider
    			question
    			recall
    			reconcile
    			referral
    			schedule
    			school
    			screen
    			signal
    			task
    			transaction
    			userod
    			 */
    /**
    * Gets the summary from the xml file.  The full and correct member name must be supplied.
    */
    private String getSummary(String member) throws Exception {
        XPathNavigator navOne = Navigator.SelectSingleNode("//member[@name='" + member + "']");
        if (navOne == null)
        {
            return "";
        }
         
        XPathNavigator nav = navOne.SelectSingleNode("summary");
        if (nav == null)
        {
            return "";
        }
         
        return navOne.SelectSingleNode("summary").Value;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(Form1.class);
        this.label1 = new System.Windows.Forms.Label();
        this.butBuild = new System.Windows.Forms.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.textVersion = new System.Windows.Forms.TextBox();
        this.textConnStr = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(23, 25);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(558, 41);
        this.label1.TabIndex = 0;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // butBuild
        //
        this.butBuild.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butBuild.Location = new System.Drawing.Point(477, 279);
        this.butBuild.Name = "butBuild";
        this.butBuild.Size = new System.Drawing.Size(75, 24);
        this.butBuild.TabIndex = 1;
        this.butBuild.Text = "Build";
        this.butBuild.UseVisualStyleBackColor = true;
        this.butBuild.Click += new System.EventHandler(this.butBuild_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(23, 84);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(558, 41);
        this.label2.TabIndex = 2;
        this.label2.Text = "Step 1: Build the release of OpenDental, which also generates OpenDentBusiness.xm" + "l which contains all the comments for each database column.";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(23, 129);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(558, 33);
        this.label3.TabIndex = 3;
        this.label3.Text = "Step 2: Make sure you have run the exe so that the config file points to a runnin" + "g database of the same version as the program.  Connection string:";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(23, 224);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(558, 41);
        this.label4.TabIndex = 4;
        this.label4.Text = resources.GetString("label4.Text");
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(23, 194);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(140, 19);
        this.label6.TabIndex = 6;
        this.label6.Text = "Step 3: Specify the version:";
        //
        // textVersion
        //
        this.textVersion.Location = new System.Drawing.Point(160, 191);
        this.textVersion.Name = "textVersion";
        this.textVersion.Size = new System.Drawing.Size(59, 20);
        this.textVersion.TabIndex = 7;
        this.textVersion.Text = "7.5.1";
        //
        // textConnStr
        //
        this.textConnStr.Location = new System.Drawing.Point(26, 158);
        this.textConnStr.Name = "textConnStr";
        this.textConnStr.ReadOnly = true;
        this.textConnStr.Size = new System.Drawing.Size(541, 20);
        this.textConnStr.TabIndex = 8;
        //
        // Form1
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(612, 332);
        this.Controls.Add(this.textConnStr);
        this.Controls.Add(this.textVersion);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butBuild);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "Form1";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Documentation Builder";
        this.Load += new System.EventHandler(this.Form1_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butBuild = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textVersion = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textConnStr = new System.Windows.Forms.TextBox();
}
//F:OpenDental.ReportParameter.DefaultValues']").Value;

//F:OpenDental.ReportParameter.DefaultValues']").Value;