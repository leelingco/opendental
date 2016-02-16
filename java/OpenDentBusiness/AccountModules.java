//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:52 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.AccountLineComparer;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.ChartModuleComponentsToLoad;
import OpenDentBusiness.ChartModules;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Family;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Ledgers;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Resellers;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.Statement;
import OpenDentBusiness.StatementMode;
import OpenDentBusiness.Tooth;

public class AccountModules   
{
    //These static variables are only used on either client or server.  Should work fine as written.
    private static DataSet retVal = new DataSet();
    private static Family fam;
    private static Patient pat;
    /**
    * Gets filled in GetPayPlansForStatement, then gets added to the misc table.
    */
    private static double payPlanDue = new double();
    /**
    * Value will be set toward the bottom of GetAccount.  It will then go into the misc table.
    */
    private static double balanceForward = new double();
    /**
    * If intermingled=true, the patnum of any family member will get entire family intermingled.
    */
    public static DataSet getAll(long patNum, boolean viewingInRecall, DateTime fromDate, DateTime toDate, boolean intermingled, boolean showProcBreakdown, boolean showPayNotes, boolean showAdjNotes) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetDS(MethodBase.GetCurrentMethod(), patNum, viewingInRecall, fromDate, toDate, intermingled, showProcBreakdown, showPayNotes, showAdjNotes);
        }
         
        fam = Patients.getFamily(patNum);
        if (intermingled)
        {
            patNum = fam.ListPats[0].PatNum;
        }
         
        //guarantor
        pat = fam.getPatient(patNum);
        retVal = new DataSet();
        if (viewingInRecall)
        {
            //always false
            retVal.Tables.Add(ChartModules.getProgNotes(patNum,false,new ChartModuleComponentsToLoad()));
        }
        else
        {
            getCommLog(patNum);
        } 
        boolean singlePatient = !intermingled;
        //so one or the other will be true
        payPlanDue = 0;
        //Gets 3 tables: account(or account###,account###,etc), patient, payplan.
        GetAccount(patNum, fromDate, toDate, intermingled, singlePatient, 0, showProcBreakdown, showPayNotes, false, showAdjNotes);
        getMisc(fam,patNum);
        return retVal;
    }

    //table = misc.  Just holds a few bits of info that we can't find anywhere else.
    /**
    * If intermingled=true the patnum of any family member will get entire family intermingled.  toDate should not be Max, or PayPlan amort will include too many charges.  The 10 days will not be added to toDate until creating the actual amortization schedule.
    */
    public static DataSet getStatementDataSet(Statement stmt) throws Exception {
        //long patNum,bool singlePatient,DateTime fromDate,DateTime toDate,bool intermingled) {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetDS(MethodBase.GetCurrentMethod(), stmt);
        }
         
        long patNum = stmt.PatNum;
        fam = Patients.getFamily(patNum);
        if (stmt.Intermingled)
        {
            patNum = fam.ListPats[0].PatNum;
        }
         
        //guarantor
        pat = fam.getPatient(patNum);
        retVal = new DataSet();
        payPlanDue = 0;
        balanceForward = 0;
        //Gets 3 tables: account(or account###,account###,etc), patient, payplan.
        boolean showProcBreakdown = PrefC.getBool(PrefName.StatementShowProcBreakdown);
        if (stmt.IsInvoice)
        {
            showProcBreakdown = false;
        }
         
        getAccount(patNum,stmt.DateRangeFrom,stmt.DateRangeTo,stmt.Intermingled,stmt.SinglePatient,stmt.StatementNum,showProcBreakdown,PrefC.getBool(PrefName.StatementShowNotes),stmt.IsInvoice,PrefC.getBool(PrefName.StatementShowAdjNotes));
        getApptTable(fam,stmt.SinglePatient,patNum);
        //table= appts
        getMisc(fam,patNum);
        return retVal;
    }

    /**
    * Gets a table of charges mixed with payments to show in the payplan edit window.  Parameters: 0:payPlanNum
    */
    public static DataSet getPayPlanAmort(long payPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetDS(MethodBase.GetCurrentMethod(), payPlanNum);
        }
         
        retVal = new DataSet();
        DataTable table = getPayPlanAmortTable(payPlanNum);
        retVal.Tables.Add(table);
        return retVal;
    }

    private static DataTable getPayPlanAmortTable(long payPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), payPlanNum);
        }
         
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = new DataTable("payplanamort");
        DataRow row = new DataRow();
        setTableColumns(table);
        List<DataRow> rows = new List<DataRow>();
        String command = "SELECT ChargeDate,Interest,Note,PayPlanChargeNum,Principal,ProvNum " + "FROM payplancharge WHERE PayPlanNum=" + POut.long(payPlanNum);
        DataTable rawCharge = dcon.getTable(command);
        DateTime dateT = new DateTime();
        double principal = new double();
        double interest = new double();
        double total = new double();
        for (int i = 0;i < rawCharge.Rows.Count;i++)
        {
            interest = PIn.Decimal(rawCharge.Rows[i]["Interest"].ToString());
            principal = PIn.Decimal(rawCharge.Rows[i]["Principal"].ToString());
            total = principal + interest;
            row = table.NewRow();
            row["AdjNum"] = "0";
            row["balance"] = "";
            //fill this later
            row["balanceDouble"] = 0;
            //fill this later
            row["chargesDouble"] = total;
            row["charges"] = ((double)row["chargesDouble"]).ToString("n");
            row["ClaimNum"] = "0";
            row["ClaimPaymentNum"] = "0";
            row["colorText"] = Color.Black.ToArgb().ToString();
            row["creditsDouble"] = 0;
            row["credits"] = "";
            //((double)row["creditsDouble"]).ToString("n");
            dateT = PIn.DateT(rawCharge.Rows[i]["ChargeDate"].ToString());
            row["DateTime"] = dateT;
            row["date"] = dateT.ToShortDateString();
            row["description"] = "";
            //"Princ: "+principal.ToString("n")+
            if (interest != 0)
            {
                row["description"] += "Interest: " + interest.ToString("n");
            }
             
            //+"Princ: "+principal.ToString("n")+;
            if (!StringSupport.equals(rawCharge.Rows[i]["Note"].ToString(), ""))
            {
                if (!StringSupport.equals(row["description"].ToString(), ""))
                {
                    row["description"] += "  ";
                }
                 
                row["description"] += rawCharge.Rows[i]["Note"].ToString();
            }
             
            //row["extraDetail"]="";
            row["patient"] = "";
            row["PatNum"] = "0";
            row["PayNum"] = "0";
            row["PayPlanNum"] = "0";
            row["PayPlanChargeNum"] = rawCharge.Rows[i]["PayPlanChargeNum"].ToString();
            row["ProcCode"] = Lans.g("AccountModule","PPcharge");
            row["ProcNum"] = "0";
            row["procsOnObj"] = "";
            row["prov"] = Providers.GetAbbr(PIn.Long(rawCharge.Rows[i]["ProvNum"].ToString()));
            row["StatementNum"] = "0";
            row["tth"] = "";
            rows.Add(row);
        }
        //Paysplits
        command = "SELECT CheckNum,DatePay,paysplit.PatNum,PayAmt,paysplit.PayNum,PayPlanNum," + "PayType,ProcDate,ProvNum,SplitAmt " + "FROM paysplit " + "LEFT JOIN payment ON paysplit.PayNum=payment.PayNum " + "WHERE (" + "paysplit.PayPlanNum=" + POut.long(payPlanNum);
        /*for(int i=0;i<fam.List.Length;i++){
        				if(i!=0){
        					command+="OR ";
        				}
        				command+="paysplit.PatNum ="+POut.PInt(fam.List[i].PatNum)+" ";
        			}*/
        command += ") ORDER BY ProcDate";
        DataTable rawPay = dcon.getTable(command);
        double payamt = new double();
        double amt = new double();
        for (int i = 0;i < rawPay.Rows.Count;i++)
        {
            row = table.NewRow();
            row["AdjNum"] = "0";
            row["balance"] = "";
            //fill this later
            row["balanceDouble"] = 0;
            //fill this later
            row["chargesDouble"] = 0;
            row["charges"] = "";
            row["ClaimNum"] = "0";
            row["ClaimPaymentNum"] = "0";
            row["colorText"] = DefC.getLong()[((Enum)DefCat.AccountColors).ordinal()][3].ItemColor.ToArgb().ToString();
            amt = PIn.Decimal(rawPay.Rows[i]["SplitAmt"].ToString());
            row["creditsDouble"] = amt;
            row["credits"] = ((double)row["creditsDouble"]).ToString("n");
            dateT = PIn.DateT(rawPay.Rows[i]["ProcDate"].ToString());
            row["DateTime"] = dateT;
            row["date"] = dateT.ToShortDateString();
            row["description"] = DefC.GetName(DefCat.PaymentTypes, PIn.Long(rawPay.Rows[i]["PayType"].ToString()));
            if (!StringSupport.equals(rawPay.Rows[i]["CheckNum"].ToString(), ""))
            {
                row["description"] += " #" + rawPay.Rows[i]["CheckNum"].ToString();
            }
             
            payamt = PIn.Decimal(rawPay.Rows[i]["PayAmt"].ToString());
            row["description"] += " " + payamt.ToString("c");
            if (payamt != amt)
            {
                row["description"] += " " + Lans.g("ContrAccount","(split)");
            }
             
            //we might use DatePay here to add to description
            //row["extraDetail"]="";
            row["patient"] = "";
            row["PatNum"] = rawPay.Rows[i]["PatNum"].ToString();
            row["PayNum"] = rawPay.Rows[i]["PayNum"].ToString();
            row["PayPlanNum"] = "0";
            row["PayPlanChargeNum"] = "0";
            row["ProcCode"] = Lans.g("AccountModule","Pay");
            row["ProcNum"] = "0";
            row["procsOnObj"] = "";
            row["prov"] = Providers.GetAbbr(PIn.Long(rawPay.Rows[i]["ProvNum"].ToString()));
            row["StatementNum"] = "0";
            row["tth"] = "";
            rows.Add(row);
        }
        //Sorting-----------------------------------------------------------------------------------------
        rows.Sort(new AccountLineComparer());
        //Add # indicators to charges
        int num = 1;
        for (int i = 0;i < rows.Count;i++)
        {
            if (StringSupport.equals(rows[i]["PayPlanChargeNum"].ToString(), "0"))
            {
                continue;
            }
             
            //if not a payplancharge
            rows[i]["description"] = "#" + num.ToString() + " " + rows[i]["description"].ToString();
            num++;
        }
        //Compute balances-------------------------------------------------------------------------------------
        double bal = 0;
        for (int i = 0;i < rows.Count;i++)
        {
            bal += (double)rows[i]["chargesDouble"];
            bal -= (double)rows[i]["creditsDouble"];
            rows[i]["balanceDouble"] = bal;
            //if(rows[i]["ClaimPaymentNum"].ToString()=="0" && rows[i]["ClaimNum"].ToString()!="0"){//claims
            //	rows[i]["balance"]="";
            //}
            //else{
            rows[i]["balance"] = bal.ToString("n");
        }
        for (int i = 0;i < rows.Count;i++)
        {
            //}
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /*private static void GetPayPlanCharges(){
    		  string datesql="CURDATE()";
    		  if(DataConnection.Dbtype==DatabaseType.Oracle){
    				datesql="(SELECT CURRENT_DATE FROM dual)";
    		  }
    			string command="SELECT "
    				+"(SELECT SUM(Principal) FROM payplancharge WHERE payplancharge.PayPlanNum=payplan.PayPlanNum) principal_,"
    				+"(SELECT SUM(Interest) FROM payplancharge WHERE payplancharge.PayPlanNum=payplan.PayPlanNum) interest_,"
    				+"(SELECT SUM(Principal) FROM payplancharge WHERE payplancharge.PayPlanNum=payplan.PayPlanNum "
    					+"AND ChargeDate <= "+datesql+@") principalDue_,"
    				+"(SELECT SUM(Interest) FROM payplancharge WHERE payplancharge.PayPlanNum=payplan.PayPlanNum "
    					+"AND ChargeDate <= "+datesql+@") interestDue_,"
    				+"CarrierName,payplan.Guarantor,"
    				+"payplan.PatNum,PayPlanDate,payplan.PayPlanNum,"
    				+"payplan.PlanNum "
    				+"FROM payplan "
    				+"LEFT JOIN insplan ON insplan.PlanNum=payplan.PlanNum "
    				+"LEFT JOIN carrier ON carrier.CarrierNum=insplan.CarrierNum "
    				+"WHERE  (";
    			for(int i=0;i<fam.List.Length;i++){
    				if(i!=0){
    					command+="OR ";
    				}
    				command+="payplan.Guarantor ="+POut.PInt(fam.List[i].PatNum)+" "
    					+"OR payplan.PatNum ="+POut.PInt(fam.List[i].PatNum)+" ";
    			}
    			command+=") GROUP BY payplan.PayPlanNum ORDER BY PayPlanDate";
    			DataTable rawPayPlan=dcon.GetTable(command);
    		}*/
    private static void getCommLog(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = new DataTable("Commlog");
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("CommDateTime", DateTime.class);
        table.Columns.Add("commDate");
        table.Columns.Add("commTime");
        table.Columns.Add("CommlogNum");
        table.Columns.Add("commType");
        table.Columns.Add("EmailMessageNum");
        table.Columns.Add("FormPatNum");
        table.Columns.Add("mode");
        table.Columns.Add("Note");
        table.Columns.Add("patName");
        table.Columns.Add("SheetNum");
        //table.Columns.Add("sentOrReceived");
        //table.Columns.Add("");
        //but we won't actually fill this table with rows until the very end.  It's more useful to use a List<> for now.
        List<DataRow> rows = new List<DataRow>();
        //Commlog------------------------------------------------------------------------------------------
        String command = "SELECT CommDateTime,CommType,Mode_,SentOrReceived,Note,CommlogNum,p1.FName,commlog.PatNum " + "FROM commlog,patient p1,patient p2 " + "WHERE commlog.PatNum=p1.PatNum " + "AND p1.Guarantor=p2.Guarantor " + "AND p2.PatNum =" + POut.long(patNum) + " ORDER BY CommDateTime";
        DataTable rawComm = dcon.getTable(command);
        DateTime dateT = new DateTime();
        for (int i = 0;i < rawComm.Rows.Count;i++)
        {
            //if(rawComm.Rows[i]["IsStatementSent"].ToString()=="1"){
            //  continue;
            //}
            row = table.NewRow();
            dateT = PIn.DateT(rawComm.Rows[i]["CommDateTime"].ToString());
            row["CommDateTime"] = dateT;
            row["commDate"] = dateT.ToString(Lans.getShortDateTimeFormat());
            row["commTime"] = "";
            if (dateT.TimeOfDay != TimeSpan.Zero)
            {
                row["commTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
            }
             
            row["CommlogNum"] = rawComm.Rows[i]["CommlogNum"].ToString();
            row["commType"] = DefC.GetName(DefCat.CommLogTypes, PIn.Long(rawComm.Rows[i]["CommType"].ToString()));
            row["EmailMessageNum"] = "0";
            row["FormPatNum"] = "0";
            row["mode"] = "";
            if (!StringSupport.equals(rawComm.Rows[i]["Mode_"].ToString(), "0"))
            {
                //anything except none
                row["mode"] = Lans.g("enumCommItemMode", ((CommItemMode)PIn.Long(rawComm.Rows[i]["Mode_"].ToString())).ToString());
            }
             
            row["Note"] = rawComm.Rows[i]["Note"].ToString();
            //row["patName"]="";
            //if(rawComm.Rows[i]["PatNum"].ToString()!=patNum.ToString()){
            row["patName"] = rawComm.Rows[i]["FName"].ToString();
            //}
            row["SheetNum"] = "0";
            //row["sentOrReceived"]=Lans.g("enumCommSentOrReceived",
            //	((CommSentOrReceived)PIn.PInt(rawComm.Rows[i]["SentOrReceived"].ToString())).ToString());
            rows.Add(row);
        }
        //emailmessage---------------------------------------------------------------------------------------
        command = "SELECT p1.FName,MsgDateTime,SentOrReceived,Subject,EmailMessageNum " + "FROM emailmessage,patient p1,patient p2 " + "WHERE emailmessage.PatNum=p1.PatNum " + "AND p1.Guarantor=p2.Guarantor " + "AND p2.PatNum=" + POut.long(patNum) + " ORDER BY MsgDateTime";
        DataTable rawEmail = dcon.getTable(command);
        String txt = new String();
        for (int i = 0;i < rawEmail.Rows.Count;i++)
        {
            row = table.NewRow();
            dateT = PIn.DateT(rawEmail.Rows[i]["MsgDateTime"].ToString());
            row["CommDateTime"] = dateT;
            row["commDate"] = dateT.ToShortDateString();
            if (dateT.TimeOfDay != TimeSpan.Zero)
            {
                row["commTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
            }
             
            row["CommlogNum"] = "0";
            //type
            row["EmailMessageNum"] = rawEmail.Rows[i]["EmailMessageNum"].ToString();
            row["FormPatNum"] = "0";
            row["mode"] = Lans.g("enumCommItemMode", CommItemMode.Email.ToString());
            txt = "";
            if (StringSupport.equals(rawEmail.Rows[i]["SentOrReceived"].ToString(), "0"))
            {
                txt = "(" + Lans.g("AccountModule","Unsent") + ") ";
            }
             
            row["Note"] = txt + rawEmail.Rows[i]["Subject"].ToString();
            row["patName"] = rawEmail.Rows[i]["FName"].ToString();
            row["SheetNum"] = "0";
            //if(rawEmail.Rows[i]["SentOrReceived"].ToString()=="0") {
            //	row["sentOrReceived"]=Lans.g("AccountModule","Unsent");
            //}
            //else {
            //	row["sentOrReceived"]=Lans.g("enumCommSentOrReceived",
            //		((CommSentOrReceived)PIn.PInt(rawEmail.Rows[i]["SentOrReceived"].ToString())).ToString());
            //}
            rows.Add(row);
        }
        //formpat---------------------------------------------------------------------------------------
        command = "SELECT FormDateTime,FormPatNum " + "FROM formpat WHERE PatNum ='" + POut.long(patNum) + "' ORDER BY FormDateTime";
        DataTable rawForm = dcon.getTable(command);
        for (int i = 0;i < rawForm.Rows.Count;i++)
        {
            row = table.NewRow();
            dateT = PIn.DateT(rawForm.Rows[i]["FormDateTime"].ToString());
            row["CommDateTime"] = dateT;
            row["commDate"] = dateT.ToShortDateString();
            if (dateT.TimeOfDay != TimeSpan.Zero)
            {
                row["commTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
            }
             
            row["CommlogNum"] = "0";
            row["commType"] = Lans.g("AccountModule","Questionnaire");
            row["EmailMessageNum"] = "0";
            row["FormPatNum"] = rawForm.Rows[i]["FormPatNum"].ToString();
            row["mode"] = "";
            row["Note"] = "";
            row["patName"] = "";
            row["SheetNum"] = "0";
            //row["sentOrReceived"]="";
            rows.Add(row);
        }
        //sheet---------------------------------------------------------------------------------------
        command = "SELECT p1.FName,DateTimeSheet,SheetNum,SheetType,Description " + "FROM sheet,patient p1,patient p2 " + "WHERE sheet.PatNum =p1.PatNum " + "AND p1.Guarantor=p2.Guarantor " + "AND p2.PatNum=" + POut.long(patNum) + " AND SheetType!=" + POut.Long(((Enum)SheetTypeEnum.Rx).ordinal()) + " ORDER BY DateTimeSheet";
        //rx are only accesssible from within Rx edit window.
        DataTable rawSheet = dcon.getTable(command);
        for (int i = 0;i < rawSheet.Rows.Count;i++)
        {
            //SheetTypeEnum sheetType;
            row = table.NewRow();
            dateT = PIn.DateT(rawSheet.Rows[i]["DateTimeSheet"].ToString());
            row["CommDateTime"] = dateT;
            row["commDate"] = dateT.ToShortDateString();
            if (dateT.TimeOfDay != TimeSpan.Zero)
            {
                row["commTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
            }
             
            row["CommlogNum"] = "0";
            row["commType"] = Lans.g("AccountModule","Sheet");
            //
            row["EmailMessageNum"] = "0";
            row["FormPatNum"] = "0";
            row["mode"] = "";
            //sheetType=(SheetTypeEnum)PIn.Long(rawSheet.Rows[i]["SheetType"].ToString());
            row["Note"] = rawSheet.Rows[i]["Description"].ToString();
            row["patName"] = rawSheet.Rows[i]["FName"].ToString();
            row["SheetNum"] = rawSheet.Rows[i]["SheetNum"].ToString();
            //row["sentOrReceived"]="";
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            //Sorting
            //rows.Sort(CompareCommRows);
            table.Rows.Add(rows[i]);
        }
        DataView view = table.DefaultView;
        view.Sort = "CommDateTime";
        table = view.ToTable();
        //return table;
        retVal.Tables.Add(table);
    }

    private static void setTableColumns(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("AdjNum");
        table.Columns.Add("balance");
        table.Columns.Add("balanceDouble", double.class);
        table.Columns.Add("charges");
        table.Columns.Add("chargesDouble", double.class);
        table.Columns.Add("ClaimNum");
        table.Columns.Add("ClaimPaymentNum");
        //if this is set, also set ClaimNum
        table.Columns.Add("clinic");
        table.Columns.Add("colorText");
        table.Columns.Add("credits");
        table.Columns.Add("creditsDouble", double.class);
        table.Columns.Add("date");
        table.Columns.Add("DateTime", DateTime.class);
        table.Columns.Add("description");
        //table.Columns.Add("extraDetail");
        table.Columns.Add("patient");
        table.Columns.Add("PatNum");
        table.Columns.Add("PayNum");
        //even though we only show split objects
        table.Columns.Add("PayPlanNum");
        table.Columns.Add("PayPlanChargeNum");
        table.Columns.Add("ProcCode");
        table.Columns.Add("ProcNum");
        table.Columns.Add("ProcNumLab");
        table.Columns.Add("procsOnObj");
        //for a claim or payment, the ProcNums, comma delimited.
        table.Columns.Add("prov");
        table.Columns.Add("StatementNum");
        table.Columns.Add("ToothNum");
        table.Columns.Add("ToothRange");
        table.Columns.Add("tth");
    }

    /**
    * Also gets the patient table, which has one row for each family member. Also currently runs aging.  Also gets payplan table.  If StatementNum is not zero, then it's for a statement, and the resulting payplan table looks totally different.  If IsInvoice, this does some extra filtering.
    */
    private static void getAccount(long patNum, DateTime fromDate, DateTime toDate, boolean intermingled, boolean singlePatient, long statementNum, boolean showProcBreakdown, boolean showPayNotes, boolean isInvoice, boolean showAdjNotes) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum, fromDate, toDate, intermingled, singlePatient, statementNum, showProcBreakdown, showPayNotes, isInvoice, showAdjNotes);
            return ;
        }
         
        boolean isReseller = false;
        //Used to display data in the account module differently when patient is a reseller.
        //HQ only, find out if this patient is a reseller.
        if (PrefC.getBool(PrefName.DockPhonePanelShow) && Resellers.IsResellerFamily(fam.ListPats[0].PatNum))
        {
            isReseller = true;
        }
         
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = new DataTable("account");
        //run aging.-------------------------------------------------------
        if (PrefC.getBool(PrefName.AgingCalculatedMonthlyInsteadOfDaily))
        {
            Ledgers.computeAging(pat.Guarantor,PIn.date(PrefC.getString(PrefName.DateLastAging)),false);
        }
        else
        {
            Ledgers.ComputeAging(pat.Guarantor, DateTime.Today, false);
        } 
        //Now, back to getting the tables------------------------------------------------------------------
        DataRow row = new DataRow();
        setTableColumns(table);
        //but we won't actually fill this table with rows until the very end.  It's more useful to use a List<> for now.
        List<DataRow> rows = new List<DataRow>();
        DateTime dateT = new DateTime();
        double qty = new double();
        double amt = new double();
        String command = new String();
        //claimprocs (ins payments)----------------------------------------------------------------------------
        command = "SELECT ClaimNum,MAX(ClaimPaymentNum) ClaimPaymentNum,MAX(ClinicNum) ClinicNum,DateCP,SUM(InsPayAmt) InsPayAmt_,MAX(PatNum) PatNum,MAX(ProcDate) ProcDate," + "SUM(WriteOff) WriteOff_, " + "(SELECT ProvTreat FROM claim WHERE claimproc.ClaimNum=claim.ClaimNum) provNum_ " + "FROM claimproc " + "WHERE (Status=1 OR Status=4 OR Status=5) " + "AND (WriteOff>0 OR InsPayAmt!=0) " + "AND (";
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            //MAX functions added to preserve behavior in Oracle.
            //+"MAX(ProvNum) ProvNum,
            //js 1/28/13  The following line has been the source of many complaints in the past.
            //When it was claim.ProvBill, it didn't match daily payment report or the account Claim row entry.
            //When it was MAX(claimproc.ProvNum), the user had no control over it because it was one prov at random.
            //By switching to claim.ProvTreat, we are more closely matching the P&I report and the account Claim row.  ProvBill is not very meaningful outside of the claim itself.
            //received or supplemental or capclaim
            if (i != 0)
            {
                command += "OR ";
            }
             
            command += "PatNum =" + POut.Long(fam.ListPats[i].PatNum) + " ";
        }
        command += ") GROUP BY ClaimNum,DateCP " + "ORDER BY DateCP";
        DataTable rawClaimPay = dcon.getTable(command);
        DateTime procdate = new DateTime();
        double writeoff = new double();
        for (int i = 0;i < rawClaimPay.Rows.Count;i++)
        {
            if (isInvoice)
            {
                break;
            }
             
            //this could possibly be optimized later by not running the query in the first place.
            row = table.NewRow();
            row["AdjNum"] = "0";
            row["balance"] = "";
            //fill this later
            row["balanceDouble"] = 0;
            //fill this later
            row["chargesDouble"] = 0;
            row["charges"] = "";
            row["ClaimNum"] = rawClaimPay.Rows[i]["ClaimNum"].ToString();
            row["ClaimPaymentNum"] = "1";
            //this is now just a boolean flag indicating that it is a payment.
            //this is because it will frequently not be attached to an actual claim payment.
            row["clinic"] = Clinics.GetDesc(PIn.Long(rawClaimPay.Rows[i]["ClinicNum"].ToString()));
            row["colorText"] = DefC.getLong()[((Enum)DefCat.AccountColors).ordinal()][7].ItemColor.ToArgb().ToString();
            amt = PIn.Decimal(rawClaimPay.Rows[i]["InsPayAmt_"].ToString());
            writeoff = PIn.Decimal(rawClaimPay.Rows[i]["WriteOff_"].ToString());
            row["creditsDouble"] = amt + writeoff;
            row["credits"] = ((double)row["creditsDouble"]).ToString("n");
            dateT = PIn.DateT(rawClaimPay.Rows[i]["DateCP"].ToString());
            row["DateTime"] = dateT;
            row["date"] = dateT.ToString(Lans.getShortDateTimeFormat());
            procdate = PIn.DateT(rawClaimPay.Rows[i]["ProcDate"].ToString());
            row["description"] = Lans.g("AccountModule","Insurance Payment for Claim ") + procdate.ToShortDateString();
            if (writeoff != 0)
            {
                row["description"] += "\r\n" + Lans.g("AccountModule","Payment:") + " " + amt.ToString("c") + "\r\n" + Lans.g("AccountModule","Writeoff:") + " " + writeoff.ToString("c");
            }
             
            //row["extraDetail"]="";
            row["patient"] = fam.GetNameInFamFirst(PIn.Long(rawClaimPay.Rows[i]["PatNum"].ToString()));
            row["PatNum"] = rawClaimPay.Rows[i]["PatNum"].ToString();
            row["PayNum"] = "0";
            row["PayPlanNum"] = "0";
            row["PayPlanChargeNum"] = "0";
            row["ProcCode"] = Lans.g("AccountModule","InsPay");
            row["ProcNum"] = "0";
            row["ProcNumLab"] = "";
            row["procsOnObj"] = "";
            row["prov"] = Providers.GetAbbr(PIn.Long(rawClaimPay.Rows[i]["provNum_"].ToString()));
            row["StatementNum"] = "0";
            row["ToothNum"] = "";
            row["ToothRange"] = "";
            row["tth"] = "";
            rows.Add(row);
        }
        String familyPatNums = "";
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            if (i != 0)
            {
                familyPatNums += ", ";
            }
             
            familyPatNums += POut.Long(fam.ListPats[i].PatNum);
        }
        //Procedures------------------------------------------------------------------------------------------
        //Prevents long load time in a patient with thousands of entries.  Example: customer was using a dummy patient.
        //only include estimates for pending claims
        //Prevents long load time in a patient with thousands of entries.
        //CapComplete (CapClaim handled on claimproc row)
        //complete
        command = "SELECT " + "(SELECT SUM(AdjAmt) FROM adjustment WHERE procedurelog.ProcNum=adjustment.ProcNum " + "AND adjustment.ProcNum<>0 " + "AND adjustment.PatNum IN (" + familyPatNums + ")" + ") adj_, " + "procedurelog.BaseUnits,procedurelog.BillingNote,procedurelog.ClinicNum,procedurecode.CodeNum,Descript," + "(SELECT SUM(InsPayAmt) FROM claimproc cp5 WHERE procedurelog.ProcNum=cp5.ProcNum " + "AND cp5.PatNum IN (" + familyPatNums + ")" + ") insPayAmt_," + "(SELECT SUM(InsPayEst) FROM claimproc cp4 WHERE procedurelog.ProcNum=cp4.ProcNum " + "AND cp4.Status=0 " + "AND cp4.PatNum IN (" + familyPatNums + ")" + ") insPayEst_," + "LaymanTerm,procedurelog.MedicalCode,MAX(cp1.NoBillIns) noBillIns_,procedurelog.PatNum," + "(SELECT SUM(paysplit.SplitAmt) FROM paysplit WHERE procedurelog.ProcNum=paysplit.ProcNum " + "AND paysplit.ProcNum<>0 " + "AND paysplit.PatNum IN (" + familyPatNums + ")" + ") patPay_," + "ProcCode," + DbHelper.dateColumn("procedurelog.ProcDate") + " procDate_,ProcFee,procedurelog.ProcNum,procedurelog.ProcNumLab," + "procedurelog.ProvNum,procedurelog.Surf,ToothNum,ToothRange,UnitQty," + "SUM(cp1.WriteOff) writeOff_, " + "(SELECT MIN(ClaimNum) FROM claimproc cp3,insplan WHERE procedurelog.ProcNum=cp3.ProcNum " + "AND insplan.PlanNum=cp3.PlanNum AND insplan.IsMedical=(CASE WHEN procedurelog.MedicalCode<>'' THEN 1 ELSE 0 END) AND cp3.Status!=7) unsent_," + "(SELECT SUM(WriteOff) FROM claimproc cp2 WHERE procedurelog.ProcNum=cp2.ProcNum " + "AND cp2.Status=7) writeOffCap_ " + "FROM procedurelog " + "LEFT JOIN procedurecode ON procedurelog.CodeNum=procedurecode.CodeNum " + "LEFT JOIN claimproc cp1 ON procedurelog.ProcNum=cp1.ProcNum " + "WHERE ProcStatus=2 " + "AND procedurelog.PatNum IN (" + familyPatNums;
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            command += ") GROUP BY procedurelog.ClinicNum,procedurelog.BaseUnits,Descript,LaymanTerm,procedurelog.MedicalCode,procedurelog.PatNum,ProcCode," + DbHelper.dateColumn("procedurelog.ProcDate") + ",ProcFee,procedurelog.ProcNum,procedurelog.ProcNumLab,procedurelog.ProvNum,ToothNum," + "ToothRange,UnitQty ";
        }
        else
        {
            //mysql. Including Descript in the GROUP BY causes mysql to lock up sometimes.  Unsure why.
            command += ") GROUP BY procedurelog.ProcNum ";
        } 
        command += "ORDER BY procDate_";
        if (isInvoice)
        {
            //different query here.  Include all column names.
            command = "SELECT '' AS adj_,procedurelog.BaseUnits,procedurelog.BillingNote,procedurelog.ClinicNum,procedurecode.CodeNum,procedurecode.Descript," + "'' AS insPayAmt_,'' AS insPayEst_,procedurecode.LaymanTerm,procedurelog.MedicalCode,'' AS noBillIns_,procedurelog.PatNum," + "'' AS patPay_,procedurecode.ProcCode," + DbHelper.dateColumn("procedurelog.ProcDate") + " procDate_,procedurelog.ProcFee,procedurelog.ProcNum,procedurelog.ProcNumLab," + "procedurelog.ProvNum,procedurelog.Surf,procedurelog.ToothNum,procedurelog.ToothRange,procedurelog.UnitQty," + "'' AS writeOff_,'' AS unsent_,'' AS writeOffCap_ " + "FROM procedurelog " + "LEFT JOIN procedurecode ON procedurelog.CodeNum=procedurecode.CodeNum " + "WHERE StatementNum='" + POut.long(statementNum) + "' " + "ORDER BY procDate_";
        }
         
        DataTable rawProc = dcon.getTable(command);
        double insPayAmt = new double();
        double insPayEst = new double();
        double writeOff = new double();
        double writeOffCap = new double();
        double patPort = new double();
        double patPay = new double();
        boolean isNoBill = new boolean();
        double adjAmt = new double();
        String extraDetail = new String();
        List<DataRow> labRows = new List<DataRow>();
        for (int i = 0;i < rawProc.Rows.Count;i++)
        {
            //Canadian lab procs, which must be added in a loop at the very end.
            row = table.NewRow();
            row["AdjNum"] = "0";
            row["balance"] = "";
            //fill this later
            row["balanceDouble"] = 0;
            //fill this later
            qty = PIn.Long(rawProc.Rows[i]["UnitQty"].ToString()) + PIn.Long(rawProc.Rows[i]["BaseUnits"].ToString());
            if (qty == 0)
            {
                qty = 1;
            }
             
            amt = PIn.Decimal(rawProc.Rows[i]["ProcFee"].ToString()) * qty;
            writeOffCap = PIn.Decimal(rawProc.Rows[i]["writeOffCap_"].ToString());
            amt -= writeOffCap;
            row["chargesDouble"] = amt;
            //*qty;
            row["charges"] = ((double)row["chargesDouble"]).ToString("n");
            row["ClaimNum"] = "0";
            row["ClaimPaymentNum"] = "0";
            row["clinic"] = Clinics.GetDesc(PIn.Long(rawProc.Rows[i]["ClinicNum"].ToString()));
            row["colorText"] = DefC.getLong()[((Enum)DefCat.AccountColors).ordinal()][0].ItemColor.ToArgb().ToString();
            row["creditsDouble"] = 0;
            row["credits"] = "";
            dateT = PIn.DateT(rawProc.Rows[i]["procDate_"].ToString());
            row["DateTime"] = dateT;
            row["date"] = dateT.ToString(Lans.getShortDateTimeFormat());
            //row["description"]="";
            long codeNum = PIn.Long(rawProc.Rows[i]["CodeNum"].ToString());
            String surf = rawProc.Rows[i]["Surf"].ToString();
            String toothNum = rawProc.Rows[i]["ToothNum"].ToString();
            row["description"] = Procedures.convertProcToString(codeNum,surf,toothNum,true) + " ";
            if (!StringSupport.equals(rawProc.Rows[i]["MedicalCode"].ToString(), ""))
            {
                row["description"] += Lans.g("ContrAccount","(medical)") + " ";
            }
             
            //row["description"]+=rawProc.Rows[i]["Descript"].ToString();
            //if(rawProc.Rows[i]["LaymanTerm"].ToString()!=""){
            //	row["description"]+=rawProc.Rows[i]["LaymanTerm"].ToString();
            //}
            if (!StringSupport.equals(rawProc.Rows[i]["ToothRange"].ToString(), ""))
            {
                row["description"] += " #" + Tooth.FormatRangeForDisplay(rawProc.Rows[i]["ToothRange"].ToString());
            }
             
            isNoBill = false;
            if (!StringSupport.equals(rawProc.Rows[i]["noBillIns_"].ToString(), "") && !StringSupport.equals(rawProc.Rows[i]["noBillIns_"].ToString(), "0"))
            {
                isNoBill = true;
            }
             
            if (isNoBill)
            {
                row["description"] += " " + Lans.g("ContrAccount","(NoBillIns)");
            }
             
            boolean isShowUnsent = false;
            if (StringSupport.equals(rawProc.Rows[i]["unsent_"].ToString(), "0") && !isNoBill)
            {
                //no claim attached and marked to bill insurance
                isShowUnsent = true;
            }
             
            String strProcNumLab = rawProc.Rows[i]["ProcNumLab"].ToString();
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA") && !StringSupport.equals(strProcNumLab, "0"))
            {
                //Canadian. en-CA or fr-CA, lab fee.
                long procNumLab = PIn.long(strProcNumLab);
                //Locate the parent proc. Since the lab is attached to a proc, the parent proc should be in the raw proc list.
                isShowUnsent = false;
                for (int j = 0;j < rawProc.Rows.Count;j++)
                {
                    long procNumParent = PIn.Long(rawProc.Rows[j]["ProcNum"].ToString());
                    if (procNumParent != procNumLab)
                    {
                        continue;
                    }
                     
                    //skip
                    if (StringSupport.equals(rawProc.Rows[j]["unsent_"].ToString(), "0") && !isNoBill)
                    {
                        //parent proc, no claim attached and marked to bill insurance (unsent)
                        isShowUnsent = true;
                        break;
                    }
                     
                }
            }
             
            //Since the attached parent procedure is unsent, then the lab fee is also unsent.
            if (isShowUnsent)
            {
                row["description"] += " " + Lans.g("ContrAccount","(unsent)");
            }
             
            insPayAmt = PIn.Decimal(rawProc.Rows[i]["insPayAmt_"].ToString());
            insPayEst = PIn.Decimal(rawProc.Rows[i]["insPayEst_"].ToString());
            writeOff = PIn.Decimal(rawProc.Rows[i]["writeOff_"].ToString());
            patPort = amt - insPayAmt - insPayEst - writeOff;
            patPay = PIn.Decimal(rawProc.Rows[i]["patPay_"].ToString());
            adjAmt = PIn.Decimal(rawProc.Rows[i]["adj_"].ToString());
            extraDetail = "";
            if (patPay > 0)
            {
                extraDetail += Lans.g("AccountModule","Pat Paid: ") + patPay.ToString("c");
            }
             
            if (adjAmt != 0)
            {
                if (!StringSupport.equals(extraDetail, ""))
                {
                    extraDetail += ", ";
                }
                 
                extraDetail += Lans.g("AccountModule","Adj: ") + adjAmt.ToString("c");
            }
             
            if (insPayAmt > 0 || writeOff > 0)
            {
                if (!StringSupport.equals(extraDetail, ""))
                {
                    extraDetail += ", ";
                }
                 
                extraDetail += Lans.g("AccountModule","Ins Paid: ") + insPayAmt.ToString("c");
                if (writeOff > 0)
                {
                    extraDetail += ", " + Lans.g("AccountModule","Writeoff: ") + writeOff.ToString("c");
                }
                 
            }
             
            if (insPayEst > 0)
            {
                if (!StringSupport.equals(extraDetail, ""))
                {
                    extraDetail += ", ";
                }
                 
                extraDetail += Lans.g("AccountModule","Ins Est: ") + insPayEst.ToString("c");
            }
             
            if (patPort > 0 && writeOffCap == 0)
            {
                //if there is a cap writeoff, showing a patient portion would calculate wrong.
                if (!StringSupport.equals(extraDetail, ""))
                {
                    extraDetail += ", ";
                }
                 
                extraDetail += Lans.g("AccountModule","Pat Port: ") + patPort.ToString("c");
            }
             
            if (showProcBreakdown)
            {
                if (!StringSupport.equals(extraDetail, ""))
                {
                    row["description"] += "\r\n" + extraDetail;
                }
                 
            }
             
            String billingNote = PIn.String(rawProc.Rows[i]["BillingNote"].ToString());
            if (!StringSupport.equals(billingNote, ""))
            {
                row["description"] += "\r\n" + billingNote;
            }
             
            String patname = fam.GetNameInFamFirst(PIn.Long(rawProc.Rows[i]["PatNum"].ToString()));
            if (isReseller)
            {
                patname = fam.GetNameInFamLF(PIn.Long(rawProc.Rows[i]["PatNum"].ToString()));
            }
             
            row["patient"] = patname;
            row["PatNum"] = rawProc.Rows[i]["PatNum"].ToString();
            row["PayNum"] = "0";
            row["PayPlanNum"] = "0";
            row["PayPlanChargeNum"] = "0";
            row["ProcCode"] = rawProc.Rows[i]["ProcCode"].ToString();
            row["ProcNum"] = rawProc.Rows[i]["ProcNum"].ToString();
            row["ProcNumLab"] = rawProc.Rows[i]["ProcNumLab"].ToString();
            row["procsOnObj"] = "";
            row["prov"] = Providers.GetAbbr(PIn.Long(rawProc.Rows[i]["ProvNum"].ToString()));
            row["StatementNum"] = "0";
            row["ToothNum"] = rawProc.Rows[i]["ToothNum"].ToString();
            row["ToothRange"] = rawProc.Rows[i]["ToothRange"].ToString();
            row["tth"] = Tooth.GetToothLabel(rawProc.Rows[i]["ToothNum"].ToString());
            if (StringSupport.equals(rawProc.Rows[i]["ProcNumLab"].ToString(), "0"))
            {
                //normal proc
                rows.Add(row);
            }
            else
            {
                row["description"] = "^ ^ " + row["description"].ToString();
                labRows.Add(row);
            } 
        }
        //these will be added in the loop at the end
        //Adjustments---------------------------------------------------------------------------------------
        command = "SELECT AdjAmt,AdjDate,AdjNum,AdjType,ClinicNum,PatNum,ProvNum,AdjNote " + "FROM adjustment " + "WHERE (";
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            if (i != 0)
            {
                command += "OR ";
            }
             
            command += "PatNum =" + POut.Long(fam.ListPats[i].PatNum) + " ";
        }
        command += ") ORDER BY AdjDate";
        if (isInvoice)
        {
            //different query here.  Include all column names.
            command = "SELECT AdjAmt,AdjDate,AdjNum,AdjType,ClinicNum,PatNum,ProvNum,AdjNote " + "FROM adjustment " + "WHERE StatementNum='" + POut.long(statementNum) + "' " + "ORDER BY AdjDate";
        }
         
        DataTable rawAdj = dcon.getTable(command);
        for (int i = 0;i < rawAdj.Rows.Count;i++)
        {
            row = table.NewRow();
            row["AdjNum"] = rawAdj.Rows[i]["AdjNum"].ToString();
            row["balance"] = "";
            //fill this later
            row["balanceDouble"] = 0;
            //fill this later
            amt = PIn.Decimal(rawAdj.Rows[i]["AdjAmt"].ToString());
            if (amt < 0)
            {
                row["chargesDouble"] = 0;
                row["charges"] = "";
                row["creditsDouble"] = -amt;
                row["credits"] = (-amt).ToString("n");
            }
            else
            {
                row["chargesDouble"] = amt;
                row["charges"] = amt.ToString("n");
                row["creditsDouble"] = 0;
                row["credits"] = "";
            } 
            row["ClaimNum"] = "0";
            row["ClaimPaymentNum"] = "0";
            row["clinic"] = Clinics.GetDesc(PIn.Long(rawAdj.Rows[i]["ClinicNum"].ToString()));
            row["colorText"] = DefC.getLong()[((Enum)DefCat.AccountColors).ordinal()][1].ItemColor.ToArgb().ToString();
            dateT = PIn.DateT(rawAdj.Rows[i]["AdjDate"].ToString());
            row["DateTime"] = dateT;
            row["date"] = dateT.ToString(Lans.getShortDateTimeFormat());
            row["description"] = DefC.GetName(DefCat.AdjTypes, PIn.Long(rawAdj.Rows[i]["AdjType"].ToString()));
            if (!StringSupport.equals(rawAdj.Rows[i]["AdjNote"].ToString(), "") && showAdjNotes)
            {
                //row["extraDetail"] = rawAdj.Rows[i]["AdjNote"].ToString();
                row["description"] += "\r\n" + rawAdj.Rows[i]["AdjNote"].ToString();
            }
             
            String patname = fam.GetNameInFamFirst(PIn.Long(rawAdj.Rows[i]["PatNum"].ToString()));
            if (isReseller)
            {
                patname = fam.GetNameInFamLF(PIn.Long(rawAdj.Rows[i]["PatNum"].ToString()));
            }
             
            row["patient"] = patname;
            row["PatNum"] = rawAdj.Rows[i]["PatNum"].ToString();
            row["PayNum"] = "0";
            row["PayPlanNum"] = "0";
            row["PayPlanChargeNum"] = "0";
            row["ProcCode"] = Lans.g("AccountModule","Adjust");
            row["ProcNum"] = "0";
            row["ProcNumLab"] = "";
            row["procsOnObj"] = "";
            row["prov"] = Providers.GetAbbr(PIn.Long(rawAdj.Rows[i]["ProvNum"].ToString()));
            row["StatementNum"] = "0";
            row["ToothNum"] = "";
            row["ToothRange"] = "";
            row["tth"] = "";
            rows.Add(row);
        }
        //paysplits-----------------------------------------------------------------------------------------
        DataTable rawPay = new DataTable();
        //MAX function used to preserve behavior in Oracle.
        command = "SELECT MAX(CheckNum) CheckNum,paysplit.ClinicNum,MAX(DatePay) DatePay,paysplit.PatNum,MAX(payment.PatNum) patNumPayment_,MAX(PayAmt) PayAmt," + "paysplit.PayNum,paysplit.PayPlanNum," + "MAX(PayType) PayType,MAX(ProcDate) ProcDate," + DbHelper.groupConcat("ProcNum") + " ProcNums_, " + "MAX(ProvNum) ProvNum,SUM(SplitAmt) splitAmt_,MAX(payment.PayNote) PayNote,MAX(paysplit.UnearnedType) UnearnedType " + "FROM paysplit " + "LEFT JOIN payment ON paysplit.PayNum=payment.PayNum " + "WHERE (";
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            //Column names with MAX left the same as they should not be considered aggregate (even though they are).
            if (i != 0)
            {
                command += "OR ";
            }
             
            command += "paysplit.PatNum =" + POut.Long(fam.ListPats[i].PatNum) + " ";
        }
        command += ") GROUP BY DatePay,paysplit.PayPlanNum,paysplit.PayNum,paysplit.PatNum,paysplit.ClinicNum ORDER BY DatePay";
        //ProcDate ORDER BY ProcDate";
        rawPay = dcon.getTable(command);
        double payamt = new double();
        for (int i = 0;i < rawPay.Rows.Count;i++)
        {
            if (isInvoice)
            {
                break;
            }
             
            //do not add rows that are attached to payment plans
            if (!StringSupport.equals(rawPay.Rows[i]["PayPlanNum"].ToString(), "0"))
            {
                continue;
            }
             
            row = table.NewRow();
            row["AdjNum"] = "0";
            row["balance"] = "";
            //fill this later
            row["balanceDouble"] = 0;
            //fill this later
            row["chargesDouble"] = 0;
            row["charges"] = "";
            row["ClaimNum"] = "0";
            row["ClaimPaymentNum"] = "0";
            row["clinic"] = Clinics.GetDesc(PIn.Long(rawPay.Rows[i]["ClinicNum"].ToString()));
            row["colorText"] = DefC.getLong()[((Enum)DefCat.AccountColors).ordinal()][3].ItemColor.ToArgb().ToString();
            amt = PIn.Decimal(rawPay.Rows[i]["splitAmt_"].ToString());
            row["creditsDouble"] = amt;
            row["credits"] = ((double)row["creditsDouble"]).ToString("n");
            dateT = PIn.DateT(rawPay.Rows[i]["DatePay"].ToString());
            //was ProcDate in earlier versions
            row["DateTime"] = dateT;
            row["date"] = dateT.ToString(Lans.getShortDateTimeFormat());
            row["description"] = DefC.GetName(DefCat.PaymentTypes, PIn.Long(rawPay.Rows[i]["PayType"].ToString()));
            if (!StringSupport.equals(rawPay.Rows[i]["CheckNum"].ToString(), ""))
            {
                row["description"] += " #" + rawPay.Rows[i]["CheckNum"].ToString();
            }
             
            payamt = PIn.Decimal(rawPay.Rows[i]["PayAmt"].ToString());
            row["description"] += " " + payamt.ToString("c");
            if (rawPay.Rows[i]["PatNum"].ToString() != rawPay.Rows[i]["patNumPayment_"].ToString())
            {
                row["description"] += " (" + Lans.g("ContrAccount","Paid by ") + fam.GetNameInFamFirst(PIn.Long(rawPay.Rows[i]["patNumPayment_"].ToString())) + ")";
            }
             
            if (payamt != amt)
            {
                row["description"] += " " + Lans.g("ContrAccount","(split)");
            }
             
            if (!StringSupport.equals(rawPay.Rows[i]["UnearnedType"].ToString(), "0"))
            {
                row["description"] += " - " + DefC.GetName(DefCat.PaySplitUnearnedType, PIn.Long(rawPay.Rows[i]["UnearnedType"].ToString()));
            }
             
            if (StringSupport.equals(rawPay.Rows[i]["PayType"].ToString(), "0"))
            {
                //if a txfr, clear the description
                row["description"] = "";
            }
             
            //we might use DatePay here to add to description
            if (!StringSupport.equals(rawPay.Rows[i]["PayNote"].ToString(), "") && showPayNotes)
            {
                if (!StringSupport.equals(rawPay.Rows[i]["PayType"].ToString(), "0"))
                {
                    //if not a txfr
                    row["description"] += "\r\n";
                }
                 
                row["description"] += rawPay.Rows[i]["PayNote"].ToString();
            }
             
            if (PrefC.getBool(PrefName.AccountShowPaymentNums))
            {
                row["description"] += "\r\n" + Lans.g("AccountModule","Payment Number: ") + rawPay.Rows[i]["PayNum"].ToString();
            }
             
            String patname = fam.GetNameInFamFirst(PIn.Long(rawPay.Rows[i]["PatNum"].ToString()));
            if (isReseller)
            {
                patname = fam.GetNameInFamLF(PIn.Long(rawPay.Rows[i]["PatNum"].ToString()));
            }
             
            row["patient"] = patname;
            row["patient"] = fam.GetNameInFamFirst(PIn.Long(rawPay.Rows[i]["PatNum"].ToString()));
            row["PatNum"] = rawPay.Rows[i]["PatNum"].ToString();
            row["PayNum"] = rawPay.Rows[i]["PayNum"].ToString();
            row["PayPlanNum"] = "0";
            row["PayPlanChargeNum"] = "0";
            if (StringSupport.equals(rawPay.Rows[i]["PayType"].ToString(), "0"))
            {
                //if a txfr
                row["ProcCode"] = Lans.g("AccountModule","Txfr");
            }
            else
            {
                row["ProcCode"] = Lans.g("AccountModule","Pay");
            } 
            row["ProcNum"] = "0";
            row["ProcNumLab"] = "";
            row["procsOnObj"] = PIn.byteArray(rawPay.Rows[i]["ProcNums_"]);
            row["prov"] = Providers.GetAbbr(PIn.Long(rawPay.Rows[i]["ProvNum"].ToString()));
            row["StatementNum"] = "0";
            row["ToothNum"] = "";
            row["ToothRange"] = "";
            row["tth"] = "";
            rows.Add(row);
        }
        //claims (do not affect balance)-------------------------------------------------------------------------
        DataTable rawClaim = new DataTable();
        command = "SELECT CarrierName,ClaimFee,claim.ClaimNum,ClaimStatus,ClaimType,claim.ClinicNum,DateReceived,DateService," + "claim.DedApplied,claim.InsPayEst," + "claim.InsPayAmt,claim.PatNum,SUM(ProcFee*(BaseUnits+(CASE WHEN UnitQty<1 THEN 1 ELSE UnitQty END))) procAmt_," + DbHelper.groupConcat("claimproc.ProcNum") + " ProcNums_,ProvTreat," + "claim.ReasonUnderPaid,claim.WriteOff " + "FROM claim " + "LEFT JOIN insplan ON claim.PlanNum=insplan.PlanNum " + "LEFT JOIN carrier ON carrier.CarrierNum=insplan.CarrierNum " + "INNER JOIN claimproc ON claimproc.ClaimNum=claim.ClaimNum " + "LEFT JOIN procedurelog ON claimproc.ProcNum=procedurelog.ProcNum " + "WHERE ClaimType != 'PreAuth' " + "AND (";
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            if (i != 0)
            {
                command += "OR ";
            }
             
            command += "claim.PatNum =" + POut.Long(fam.ListPats[i].PatNum) + " ";
        }
        command += ") GROUP BY CarrierName,claim.ClaimNum,ClaimFee,claim.ClaimNum,ClaimStatus,ClaimType,claim.ClinicNum,DateReceived,DateService," + "claim.DedApplied,claim.InsPayEst," + "claim.InsPayAmt,claim.PatNum,ProvTreat," + "claim.ReasonUnderPaid,claim.WriteOff " + "ORDER BY DateService";
        rawClaim = dcon.getTable(command);
        DateTime daterec = new DateTime();
        double amtpaid = new double();
        //can be different than amt if claims show UCR.
        double procAmt = new double();
        double insest = new double();
        double deductible = new double();
        double patport = new double();
        String claimStatus = new String();
        for (int i = 0;i < rawClaim.Rows.Count;i++)
        {
            if (isInvoice)
            {
                break;
            }
             
            row = table.NewRow();
            row["AdjNum"] = "0";
            row["balance"] = "";
            //fill this later
            row["balanceDouble"] = 0;
            //fill this later
            row["chargesDouble"] = 0;
            row["charges"] = "";
            row["ClaimNum"] = rawClaim.Rows[i]["ClaimNum"].ToString();
            row["ClaimPaymentNum"] = "0";
            row["clinic"] = Clinics.GetDesc(PIn.Long(rawClaim.Rows[i]["ClinicNum"].ToString()));
            row["colorText"] = DefC.getLong()[((Enum)DefCat.AccountColors).ordinal()][4].ItemColor.ToArgb().ToString();
            //might be changed lower down based on claim status
            row["creditsDouble"] = 0;
            row["credits"] = "";
            dateT = PIn.DateT(rawClaim.Rows[i]["DateService"].ToString());
            row["DateTime"] = dateT;
            row["date"] = dateT.ToString(Lans.getShortDateTimeFormat());
            if (StringSupport.equals(rawClaim.Rows[i]["ClaimType"].ToString(), "P"))
            {
                row["description"] = Lans.g("ContrAccount","Pri") + " ";
            }
            else if (StringSupport.equals(rawClaim.Rows[i]["ClaimType"].ToString(), "S"))
            {
                row["description"] = Lans.g("ContrAccount","Sec") + " ";
            }
            else //else if(rawClaim.Rows[i]["ClaimType"].ToString()=="PreAuth"){
            //	row["description"]=Lans.g("ContrAccount","PreAuth")+" ";
            //}
            if (StringSupport.equals(rawClaim.Rows[i]["ClaimType"].ToString(), "Other"))
            {
                row["description"] = "";
            }
            else if (StringSupport.equals(rawClaim.Rows[i]["ClaimType"].ToString(), "Cap"))
            {
                row["description"] = Lans.g("ContrAccount","Cap") + " ";
            }
                
            amt = PIn.Decimal(rawClaim.Rows[i]["ClaimFee"].ToString());
            row["description"] += Lans.g("ContrAccount","Claim") + " " + amt.ToString("c") + " " + rawClaim.Rows[i]["CarrierName"].ToString();
            daterec = PIn.DateT(rawClaim.Rows[i]["DateReceived"].ToString());
            claimStatus = rawClaim.Rows[i]["ClaimStatus"].ToString();
            if (StringSupport.equals(claimStatus, "R"))
            {
                row["description"] += "\r\n" + Lans.g("ContrAccount","Received") + " ";
                if (daterec.Year < 1880)
                {
                    row["description"] += Lans.g("ContrAccount","(no date)");
                }
                else
                {
                    //although I don't think UI allows this
                    row["description"] += daterec.ToShortDateString();
                } 
                row["colorText"] = DefC.getLong()[((Enum)DefCat.AccountColors).ordinal()][8].ItemColor.ToArgb().ToString();
            }
            else if (StringSupport.equals(claimStatus, "U"))
            {
                row["description"] += "\r\n" + Lans.g("ContrAccount","Unsent");
            }
            else if (StringSupport.equals(claimStatus, "H"))
            {
                row["description"] += "\r\n" + Lans.g("ContrAccount","Hold until Pri received");
            }
            else if (StringSupport.equals(claimStatus, "W"))
            {
                row["description"] += "\r\n" + Lans.g("ContrAccount","Waiting to Send");
            }
            else if (StringSupport.equals(claimStatus, "S"))
            {
                row["description"] += "\r\n" + Lans.g("ContrAccount","Sent");
            }
                 
            double claimLabFeeTotalAmt = 0;
            //For Canada, add lab fee amounts into total claim amount.
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                String[] arrayProcNumsForClaim = PIn.byteArray(rawClaim.Rows[i]["ProcNums_"]).Split(',');
                for (int j = 0;j < arrayProcNumsForClaim.Length;j++)
                {
                    long procNum = PIn.Long(arrayProcNumsForClaim[j]);
                    if (procNum == 0)
                    {
                        continue;
                    }
                     
                    for (int k = 0;k < rawProc.Rows.Count;k++)
                    {
                        //ProcNum will be 0 for Total Payments on claims.
                        //For each procedure attached to the claim, add the lab fees into the total amount. The lab fees show in the account because they are complete.
                        long procNumLab = PIn.Long(rawProc.Rows[k]["ProcNumLab"].ToString());
                        if (procNumLab == procNum)
                        {
                            claimLabFeeTotalAmt += PIn.Decimal(rawProc.Rows[k]["ProcFee"].ToString());
                        }
                         
                    }
                }
            }
             
            if (claimLabFeeTotalAmt > 0)
            {
                row["description"] += "\r\n" + Lans.g("ContrAccount","Lab Fees") + " " + claimLabFeeTotalAmt.ToString("c");
            }
             
            procAmt = PIn.Decimal(rawClaim.Rows[i]["procAmt_"].ToString());
            insest = PIn.Decimal(rawClaim.Rows[i]["InsPayEst"].ToString());
            amtpaid = PIn.Decimal(rawClaim.Rows[i]["InsPayAmt"].ToString());
            writeoff = PIn.Decimal(rawClaim.Rows[i]["WriteOff"].ToString());
            deductible = PIn.Decimal(rawClaim.Rows[i]["DedApplied"].ToString());
            if (!PrefC.getBool(PrefName.BalancesDontSubtractIns) && (StringSupport.equals(claimStatus, "W") || StringSupport.equals(claimStatus, "S")) && !StringSupport.equals(rawClaim.Rows[i]["ClaimType"].ToString(), "Cap"))
            {
                if (amtpaid != 0 && ((insest - amtpaid) >= 0))
                {
                    //show additional info on resubmits
                    row["description"] += "\r\n" + Lans.g("ContrAccount","Remaining Est. Payment Pending:") + " " + (insest - amtpaid).ToString("c");
                }
                else
                {
                    row["description"] += "\r\n" + Lans.g("ContrAccount","Estimated Payment Pending:") + " " + insest.ToString("c");
                } 
            }
             
            if (!StringSupport.equals(rawClaim.Rows[i]["ClaimType"].ToString(), "Cap"))
            {
                if (amtpaid != 0)
                {
                    row["description"] += "\r\n" + Lans.g("ContrAccount","Payment:") + " " + amtpaid.ToString("c");
                }
                else if (amtpaid == 0 && (StringSupport.equals(claimStatus, "R")))
                {
                    row["description"] += "\r\n" + Lans.g("ContrAccount","NO PAYMENT");
                }
                  
            }
             
            if (writeoff != 0)
            {
                row["description"] += "\r\n" + Lans.g("ContrAccount","Writeoff:") + " " + writeoff.ToString("c");
            }
             
            if (deductible != 0)
            {
                row["description"] += "\r\n" + Lans.g("ContrAccount","Deductible Applied:") + " " + deductible.ToString("c");
            }
             
            if (!PrefC.getBool(PrefName.BalancesDontSubtractIns) && (StringSupport.equals(claimStatus, "W") || StringSupport.equals(claimStatus, "S")) && !StringSupport.equals(rawClaim.Rows[i]["ClaimType"].ToString(), "Cap"))
            {
                patport = procAmt - insest - writeoff;
                if (patport < 0)
                {
                    patport = 0;
                }
                 
                row["description"] += "\r\n" + Lans.g("ContrAccount","Est. Patient Portion:") + " " + patport.ToString("c");
            }
             
            if (!StringSupport.equals(rawClaim.Rows[i]["ReasonUnderPaid"].ToString(), ""))
            {
                row["description"] += "\r\n" + rawClaim.Rows[i]["ReasonUnderPaid"].ToString();
            }
             
            //row["extraDetail"]="";
            row["patient"] = fam.GetNameInFamFirst(PIn.Long(rawClaim.Rows[i]["PatNum"].ToString()));
            row["PatNum"] = rawClaim.Rows[i]["PatNum"].ToString();
            row["PayNum"] = "0";
            row["PayPlanNum"] = "0";
            row["PayPlanChargeNum"] = "0";
            row["ProcCode"] = Lans.g("AccountModule","Claim");
            row["ProcNum"] = "0";
            row["ProcNumLab"] = "";
            row["procsOnObj"] = PIn.byteArray(rawClaim.Rows[i]["ProcNums_"]);
            row["prov"] = Providers.GetAbbr(PIn.Long(rawClaim.Rows[i]["ProvTreat"].ToString()));
            row["StatementNum"] = "0";
            row["ToothNum"] = "";
            row["ToothRange"] = "";
            row["tth"] = "";
            rows.Add(row);
        }
        //Statement----------------------------------------------------------------------------------------
        command = "SELECT DateSent,IsSent,Mode_,StatementNum,PatNum,Note,NoteBold,IsInvoice " + "FROM statement " + "WHERE (";
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            if (i != 0)
            {
                command += "OR ";
            }
             
            command += "PatNum =" + POut.Long(fam.ListPats[i].PatNum) + " ";
        }
        command += ") ";
        if (statementNum > 0)
        {
            command += "AND StatementNum != " + POut.long(statementNum) + " ";
        }
         
        command += "ORDER BY DateSent";
        DataTable rawState = dcon.getTable(command);
        StatementMode _mode = StatementMode.Mail;
        for (int i = 0;i < rawState.Rows.Count;i++)
        {
            if (isInvoice)
            {
                break;
            }
             
            row = table.NewRow();
            row["AdjNum"] = "0";
            row["balance"] = "";
            //fill this later
            row["balanceDouble"] = 0;
            //fill this later
            row["chargesDouble"] = 0;
            row["charges"] = "";
            row["ClaimNum"] = "0";
            row["ClaimPaymentNum"] = "0";
            row["clinic"] = "";
            row["colorText"] = DefC.getLong()[((Enum)DefCat.AccountColors).ordinal()][5].ItemColor.ToArgb().ToString();
            row["creditsDouble"] = 0;
            row["credits"] = "";
            dateT = PIn.DateT(rawState.Rows[i]["DateSent"].ToString());
            row["DateTime"] = dateT;
            row["date"] = dateT.ToString(Lans.getShortDateTimeFormat());
            if (StringSupport.equals(rawState.Rows[i]["IsInvoice"].ToString(), "0"))
            {
                //not an invoice
                row["description"] += Lans.g("ContrAccount","Statement");
            }
            else
            {
                //Must be invoice
                row["description"] += Lans.g("ContrAccount","Invoice");
            } 
            _mode = (StatementMode)PIn.Long(rawState.Rows[i]["Mode_"].ToString());
            row["description"] += "-" + Lans.g("enumStatementMode", _mode.ToString());
            if (StringSupport.equals(rawState.Rows[i]["IsSent"].ToString(), "0"))
            {
                row["description"] += " " + Lans.g("ContrAccount","(unsent)");
            }
             
            /*We're not doing extra detail for statements unless we add a new pref for it.
            				extraDetail="";
            				if(rawState.Rows[i]["NoteBold"].ToString() !=""){
            					extraDetail+= rawState.Rows[i]["NoteBold"].ToString();
            				}
            				if(rawState.Rows[i]["Note"].ToString() !="") {
            					if(extraDetail!=""){
            						extraDetail+="\r\n";
            					}
            					extraDetail+=rawState.Rows[i]["Note"].ToString();
            				}
            				if(extraDetail!="" && showNotes) {
            					//row["description"]+="\r\n"+extraDetail;
            					//I don't think anyone wants to see this clutter.
            				}*/
            String patname = fam.GetNameInFamFirst(PIn.Long(rawState.Rows[i]["PatNum"].ToString()));
            if (isReseller)
            {
                patname = fam.GetNameInFamLF(PIn.Long(rawState.Rows[i]["PatNum"].ToString()));
            }
             
            row["patient"] = patname;
            row["PatNum"] = rawState.Rows[i]["PatNum"].ToString();
            row["PayNum"] = "0";
            row["PayPlanNum"] = "0";
            row["PayPlanChargeNum"] = "0";
            row["ProcCode"] = Lans.g("AccountModule","Stmt");
            row["ProcNum"] = "0";
            row["ProcNumLab"] = "";
            row["procsOnObj"] = "";
            row["prov"] = "";
            row["StatementNum"] = rawState.Rows[i]["StatementNum"].ToString();
            row["ToothNum"] = "";
            row["ToothRange"] = "";
            row["tth"] = "";
            rows.Add(row);
        }
        //Payment plans----------------------------------------------------------------------------------
        String datesql = "CURDATE()";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            datesql = "(SELECT CURRENT_DATE FROM dual)";
        }
         
        command = "SELECT " + "(SELECT SUM(Principal) FROM payplancharge WHERE payplancharge.PayPlanNum=payplan.PayPlanNum) principal_," + "(SELECT SUM(Interest) FROM payplancharge WHERE payplancharge.PayPlanNum=payplan.PayPlanNum) interest_," + "(SELECT SUM(Principal) FROM payplancharge WHERE payplancharge.PayPlanNum=payplan.PayPlanNum " + "AND ChargeDate <= " + datesql + ") principalDue_," + "(SELECT SUM(Interest) FROM payplancharge WHERE payplancharge.PayPlanNum=payplan.PayPlanNum " + "AND ChargeDate <= " + datesql + ") interestDue_," + "MAX(CarrierName) CarrierName,CompletedAmt,payplan.Guarantor," + "payplan.PatNum,PayPlanDate,payplan.PayPlanNum," + "payplan.PlanNum " + "FROM payplan " + "LEFT JOIN insplan ON insplan.PlanNum=payplan.PlanNum " + "LEFT JOIN carrier ON carrier.CarrierNum=insplan.CarrierNum " + "WHERE  (";
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            if (i != 0)
            {
                command += "OR ";
            }
             
            command += "payplan.Guarantor =" + POut.Long(fam.ListPats[i].PatNum) + " " + "OR payplan.PatNum =" + POut.Long(fam.ListPats[i].PatNum) + " ";
        }
        command += ") GROUP BY CompletedAmt,payplan.Guarantor," + "payplan.PatNum,PayPlanDate,payplan.PayPlanNum," + "payplan.PlanNum ";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            command += ",CarrierName,payplan.Guarantor,payplan.PatNum,PayPlanDate,payplan.PlanNum ";
        }
         
        command += "ORDER BY PayPlanDate";
        DataTable rawPayPlan = dcon.getTable(command);
        for (int i = 0;i < rawPayPlan.Rows.Count;i++)
        {
            if (isInvoice)
            {
                break;
            }
             
            row = table.NewRow();
            row["AdjNum"] = "0";
            row["balance"] = "";
            //fill this later
            row["balanceDouble"] = 0;
            //fill this later
            row["chargesDouble"] = 0;
            row["charges"] = "";
            row["ClaimNum"] = "0";
            row["ClaimPaymentNum"] = "0";
            row["clinic"] = "";
            row["colorText"] = DefC.getLong()[((Enum)DefCat.AccountColors).ordinal()][6].ItemColor.ToArgb().ToString();
            //amt=PIn.PDouble(rawPayPlan.Rows[i]["principal_"].ToString());
            amt = PIn.Decimal(rawPayPlan.Rows[i]["CompletedAmt"].ToString());
            row["creditsDouble"] = amt;
            row["credits"] = ((double)row["creditsDouble"]).ToString("n");
            dateT = PIn.DateT(rawPayPlan.Rows[i]["PayPlanDate"].ToString());
            row["DateTime"] = dateT;
            row["date"] = dateT.ToString(Lans.getShortDateTimeFormat());
            if (StringSupport.equals(rawPayPlan.Rows[i]["PlanNum"].ToString(), "0"))
            {
                row["description"] = Lans.g("ContrAccount","Payment Plan");
            }
            else
            {
                row["description"] = Lans.g("ContrAccount","Expected payments from ") + rawPayPlan.Rows[i]["CarrierName"].ToString();
            } 
            //row["extraDetail"]="";
            String patname = fam.GetNameInFamFirst(PIn.Long(rawPayPlan.Rows[i]["PatNum"].ToString()));
            if (isReseller)
            {
                patname = fam.GetNameInFamLF(PIn.Long(rawPayPlan.Rows[i]["PatNum"].ToString()));
            }
             
            row["patient"] = patname;
            row["PatNum"] = rawPayPlan.Rows[i]["PatNum"].ToString();
            row["PayNum"] = "0";
            row["PayPlanNum"] = rawPayPlan.Rows[i]["PayPlanNum"].ToString();
            row["PayPlanChargeNum"] = "0";
            row["ProcCode"] = Lans.g("AccountModule","PayPln");
            row["ProcNum"] = "0";
            row["ProcNumLab"] = "";
            row["procsOnObj"] = "";
            row["prov"] = "";
            row["StatementNum"] = "0";
            row["ToothNum"] = "";
            row["ToothRange"] = "";
            row["tth"] = "";
            rows.Add(row);
        }
        //Installment plans----------------------------------------------------------------------------------
        command = "SELECT * FROM installmentplan WHERE ";
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            if (i != 0)
            {
                command += "OR ";
            }
             
            command += "PatNum =" + POut.Long(fam.ListPats[i].PatNum) + " ";
        }
        DataTable rawInstall = Db.getTable(command);
        if (statementNum == 0)
        {
            getPayPlans(rawPayPlan,rawPay,rawInstall);
        }
        else
        {
            getPayPlansForStatement(rawPayPlan,rawPay,fromDate,toDate,singlePatient);
        } 
        //Sorting-----------------------------------------------------------------------------------------
        rows.Sort(new AccountLineComparer());
        for (int i = 0;i < labRows.Count;i++)
        {
            for (int r = 0;r < rows.Count;r++)
            {
                //Canadian lab procedures need to come immediately after their corresponding proc---------------------------------
                if (rows[r]["ProcNum"].ToString() == labRows[i]["ProcNumLab"].ToString())
                {
                    rows.Insert(r + 1, labRows[i]);
                    break;
                }
                 
            }
        }
        //rows.Sort(CompareCommRows);
        //Pass off all the rows for the whole family in order to compute the patient balances----------------
        GetPatientTable(fam, rows, isInvoice);
        //Regroup rows by patient---------------------------------------------------------------------------
        DataTable[] rowsByPat = null;
        //will only used if multiple patients not intermingled
        if (singlePatient)
        {
            for (int i = rows.Count - 1;i >= 0;i--)
            {
                //This is usually used for Account module grid.
                //go backwards and remove from end
                if (rows[i]["PatNum"].ToString() != patNum.ToString())
                {
                    rows.RemoveAt(i);
                }
                 
            }
        }
        else if (intermingled)
        {
        }
        else
        {
            for (int i = 0;i < rows.Count;i++)
            {
                //leave the rows alone
                //multiple patients not intermingled.  This is most common for an ordinary statement.
                table.Rows.Add(rows[i]);
            }
            rowsByPat = new DataTable[fam.ListPats.Length];
            for (int p = 0;p < rowsByPat.Length;p++)
            {
                rowsByPat[p] = new DataTable();
                SetTableColumns(rowsByPat[p]);
                for (int i = 0;i < rows.Count;i++)
                {
                    if (rows[i]["PatNum"].ToString() == fam.ListPats[p].PatNum.ToString())
                    {
                        rowsByPat[p].ImportRow(rows[i]);
                    }
                     
                }
            }
        }  
        //Compute balances-------------------------------------------------------------------------------------
        double bal = new double();
        if (rowsByPat == null)
        {
            //just one table
            bal = 0;
            for (int i = 0;i < rows.Count;i++)
            {
                bal += (double)rows[i]["chargesDouble"];
                bal -= (double)rows[i]["creditsDouble"];
                rows[i]["balanceDouble"] = bal;
                if (StringSupport.equals(rows[i]["ClaimPaymentNum"].ToString(), "0") && !StringSupport.equals(rows[i]["ClaimNum"].ToString(), "0"))
                {
                    //claims
                    rows[i]["balance"] = "";
                }
                else if (!StringSupport.equals(rows[i]["StatementNum"].ToString(), "0"))
                {
                }
                else
                {
                    rows[i]["balance"] = bal.ToString("n");
                }  
            }
        }
        else
        {
            for (int p = 0;p < rowsByPat.Length;p++)
            {
                bal = 0;
                for (int i = 0;i < rowsByPat[p].Rows.Count;i++)
                {
                    bal += (double)rowsByPat[p].Rows[i]["chargesDouble"];
                    bal -= (double)rowsByPat[p].Rows[i]["creditsDouble"];
                    rowsByPat[p].Rows[i]["balanceDouble"] = bal;
                    if (StringSupport.equals(rowsByPat[p].Rows[i]["ClaimPaymentNum"].ToString(), "0") && !StringSupport.equals(rowsByPat[p].Rows[i]["ClaimNum"].ToString(), "0"))
                    {
                        //claims
                        rowsByPat[p].Rows[i]["balance"] = "";
                    }
                    else if (!StringSupport.equals(rowsByPat[p].Rows[i]["StatementNum"].ToString(), "0"))
                    {
                    }
                    else
                    {
                        rowsByPat[p].Rows[i]["balance"] = bal.ToString("n");
                    }  
                }
            }
        } 
        //Remove rows outside of daterange-------------------------------------------------------------------
        boolean foundBalForward = new boolean();
        if (rowsByPat == null)
        {
            foundBalForward = false;
            for (int i = rows.Count - 1;i >= 0;i--)
            {
                //go backwards and remove from end
                if (((DateTime)rows[i]["DateTime"]) > toDate)
                {
                    rows.RemoveAt(i);
                }
                else if (((DateTime)rows[i]["DateTime"]) < fromDate)
                {
                    if (!foundBalForward)
                    {
                        foundBalForward = true;
                        balanceForward = (double)rows[i]["balanceDouble"];
                    }
                     
                    rows.RemoveAt(i);
                }
                  
            }
            //Add balance forward row
            if (foundBalForward)
            {
                //add a balance forward row
                row = table.NewRow();
                setBalForwardRow(row,balanceForward);
                rows.Insert(0, row);
            }
             
        }
        else
        {
            for (int p = 0;p < rowsByPat.Length;p++)
            {
                balanceForward = 0;
                foundBalForward = false;
                for (int i = rowsByPat[p].Rows.Count - 1;i >= 0;i--)
                {
                    //go backwards and remove from end
                    if (((DateTime)rowsByPat[p].Rows[i]["DateTime"]) > toDate)
                    {
                        rowsByPat[p].Rows.RemoveAt(i);
                    }
                    else if (((DateTime)rowsByPat[p].Rows[i]["DateTime"]) < fromDate)
                    {
                        if (!foundBalForward)
                        {
                            foundBalForward = true;
                            balanceForward = (double)rowsByPat[p].Rows[i]["balanceDouble"];
                        }
                         
                        rowsByPat[p].Rows.RemoveAt(i);
                    }
                      
                }
                //Add balance forward row
                if (foundBalForward)
                {
                    //add a balance forward row
                    row = rowsByPat[p].NewRow();
                    setBalForwardRow(row,balanceForward);
                    rowsByPat[p].Rows.InsertAt(row, 0);
                }
                 
            }
        } 
        //Finally, add rows to new table(s)-----------------------------------------------------------------------
        if (rowsByPat == null)
        {
            table.Rows.Clear();
            for (int i = 0;i < rows.Count;i++)
            {
                table.Rows.Add(rows[i]);
            }
            retVal.Tables.Add(table);
        }
        else
        {
            for (int p = 0;p < rowsByPat.Length;p++)
            {
                if (p > 0 && statementNum > 0 && fam.ListPats[p].PatStatus != PatientStatus.Patient && fam.ListPats[p].EstBalance == 0)
                {
                    continue;
                }
                 
                DataTable tablep = new DataTable("account" + fam.ListPats[p].PatNum.ToString());
                setTableColumns(tablep);
                for (int i = 0;i < rowsByPat[p].Rows.Count;i++)
                {
                    tablep.ImportRow(rowsByPat[p].Rows[i]);
                }
                retVal.Tables.Add(tablep);
            }
        } 
    }

    //DataView view = table.DefaultView;
    //view.Sort = "DateTime";
    //table = view.ToTable();
    //return table;
    private static void setBalForwardRow(DataRow row, double amt) throws Exception {
        //No need to check RemotingRole; no call to db.
        row["AdjNum"] = "0";
        row["balance"] = amt.ToString("n");
        row["balanceDouble"] = amt;
        row["chargesDouble"] = 0;
        row["charges"] = "";
        row["ClaimNum"] = "0";
        row["ClaimPaymentNum"] = "0";
        row["colorText"] = Color.Black.ToArgb().ToString();
        row["creditsDouble"] = "0";
        row["credits"] = "";
        row["DateTime"] = DateTime.MinValue;
        row["date"] = "";
        row["description"] = Lans.g("AccountModule","Balance Forward");
        row["patient"] = "";
        row["PatNum"] = "0";
        row["PayNum"] = "0";
        row["PayPlanNum"] = "0";
        row["PayPlanChargeNum"] = "0";
        row["ProcCode"] = "";
        row["ProcNum"] = "0";
        row["procsOnObj"] = "";
        row["prov"] = "";
        row["StatementNum"] = "0";
        row["tth"] = "";
    }

    /**
    * Gets payment plans for the family.  RawPay will include any paysplits for anyone in the family, so it's guaranteed to include all paysplits for a given payplan since payplans only show in the guarantor's family.  Database maint tool enforces paysplit.patnum=payplan.guarantor just in case.
    */
    private static void getPayPlans(DataTable rawPayPlan, DataTable rawPay, DataTable rawInstall) throws Exception {
        //No need to check RemotingRole; no call to db.
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = new DataTable("payplan");
        DataRow row = new DataRow();
        table.Columns.Add("accumDue");
        table.Columns.Add("balance");
        table.Columns.Add("date");
        table.Columns.Add("DateTime", DateTime.class);
        table.Columns.Add("due");
        table.Columns.Add("guarantor");
        table.Columns.Add("InstallmentPlanNum");
        table.Columns.Add("paid");
        table.Columns.Add("patient");
        table.Columns.Add("PatNum");
        table.Columns.Add("PayPlanNum");
        table.Columns.Add("principal");
        table.Columns.Add("princPaid");
        table.Columns.Add("totalCost");
        table.Columns.Add("type");
        List<DataRow> rows = new List<DataRow>();
        DateTime dateT = new DateTime();
        double paid = new double();
        double princ = new double();
        double princDue = new double();
        double interestDue = new double();
        double accumDue = new double();
        double princPaid = new double();
        double totCost = new double();
        double due = new double();
        double balance = new double();
        for (int i = 0;i < rawPayPlan.Rows.Count;i++)
        {
            //if the guarantor is not in this family, don't continue--------------------------------
            if (fam.GetIndex(PIn.Long(rawPayPlan.Rows[i]["Guarantor"].ToString())) == -1)
            {
                continue;
            }
             
            //first, calculate the numbers-------------------------------------------------------------
            paid = 0;
            for (int p = 0;p < rawPay.Rows.Count;p++)
            {
                if (rawPay.Rows[p]["PayPlanNum"].ToString() == rawPayPlan.Rows[i]["PayPlanNum"].ToString())
                {
                    paid += PIn.Decimal(rawPay.Rows[p]["splitAmt_"].ToString());
                }
                 
            }
            princ = PIn.Decimal(rawPayPlan.Rows[i]["principal_"].ToString());
            princDue = PIn.Decimal(rawPayPlan.Rows[i]["principalDue_"].ToString());
            interestDue = PIn.Decimal(rawPayPlan.Rows[i]["interestDue_"].ToString());
            accumDue = princDue + interestDue;
            princPaid = paid - interestDue;
            if (princPaid < 0)
            {
                princPaid = 0;
            }
             
            totCost = princ + PIn.Decimal(rawPayPlan.Rows[i]["interest_"].ToString());
            due = accumDue - paid;
            balance = princ - princPaid;
            //then fill the row----------------------------------------------------------------------
            row = table.NewRow();
            row["accumDue"] = accumDue.ToString("n");
            row["balance"] = balance.ToString("n");
            dateT = PIn.DateT(rawPayPlan.Rows[i]["PayPlanDate"].ToString());
            row["DateTime"] = dateT;
            row["date"] = dateT.ToShortDateString();
            row["due"] = due.ToString("n");
            row["guarantor"] = fam.GetNameInFamLF(PIn.Long(rawPayPlan.Rows[i]["Guarantor"].ToString()));
            row["InstallmentPlanNum"] = "0";
            row["paid"] = paid.ToString("n");
            row["patient"] = fam.GetNameInFamLF(PIn.Long(rawPayPlan.Rows[i]["PatNum"].ToString()));
            row["PatNum"] = rawPayPlan.Rows[i]["PatNum"].ToString();
            row["PayPlanNum"] = rawPayPlan.Rows[i]["PayPlanNum"].ToString();
            row["principal"] = princ.ToString("n");
            row["princPaid"] = princPaid.ToString("n");
            row["totalCost"] = totCost.ToString("n");
            if (StringSupport.equals(rawPayPlan.Rows[i]["PlanNum"].ToString(), "0"))
            {
                row["type"] = "PP";
            }
            else
            {
                row["type"] = "Ins";
            } 
            rows.Add(row);
        }
        for (int i = 0;i < rawInstall.Rows.Count;i++)
        {
            //Installment plans-------------------------------------------------------------------------
            row = table.NewRow();
            row["accumDue"] = "";
            row["balance"] = "";
            dateT = PIn.DateT(rawInstall.Rows[i]["DateAgreement"].ToString());
            row["DateTime"] = dateT;
            row["date"] = dateT.ToShortDateString();
            row["due"] = PIn.Decimal(rawInstall.Rows[i]["MonthlyPayment"].ToString()).ToString("f");
            row["guarantor"] = "";
            row["InstallmentPlanNum"] = PIn.Long(rawInstall.Rows[i]["InstallmentPlanNum"].ToString());
            row["paid"] = "";
            row["patient"] = fam.GetNameInFamLF(PIn.Long(rawInstall.Rows[i]["PatNum"].ToString()));
            row["PatNum"] = rawInstall.Rows[i]["PatNum"].ToString();
            row["PayPlanNum"] = "0";
            row["principal"] = "";
            row["princPaid"] = "";
            row["totalCost"] = "";
            row["type"] = "IP";
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        retVal.Tables.Add(table);
    }

    /**
    * Gets payment plans for the family.  RawPay will include any paysplits for anyone in the family, so it's guaranteed to include all paysplits for a given payplan since payplans only show in the guarantor's family.  Database maint tool enforces paysplit.patnum=payplan.guarantor just in case.  fromDate and toDate are only used if isForStatement.  From date lets us restrict how many amortization items to show.  toDate is typically 10 days in the future.
    */
    private static void getPayPlansForStatement(DataTable rawPayPlan, DataTable rawPay, DateTime fromDate, DateTime toDate, boolean singlePatient) throws Exception {
        //No need to check RemotingRole; no call to db.
        //We may need to add installment plans to this grid some day.  No time right now.
        DataTable table = new DataTable("payplan");
        DataRow row = new DataRow();
        setTableColumns(table);
        //this will allow it to later be fully integrated into a single grid.
        List<DataRow> rows = new List<DataRow>();
        double princ = new double();
        double bal = new double();
        DataTable rawAmort = new DataTable();
        long payPlanNum = new long();
        for (int i = 0;i < rawPayPlan.Rows.Count;i++)
        {
            //loop through the payment plans (usually zero or one)
            princ = PIn.Decimal(rawPayPlan.Rows[i]["principal_"].ToString());
            bal = princ;
            for (int p = 0;p < rawPay.Rows.Count;p++)
            {
                if (rawPay.Rows[p]["PayPlanNum"].ToString() == rawPayPlan.Rows[i]["PayPlanNum"].ToString())
                {
                    bal -= PIn.Decimal(rawPay.Rows[p]["splitAmt_"].ToString());
                }
                 
            }
            //summary row----------------------------------------------------------------------
            row = table.NewRow();
            row["AdjNum"] = "0";
            row["balance"] = "";
            row["balanceDouble"] = 0;
            row["chargesDouble"] = 0;
            row["charges"] = "";
            row["ClaimNum"] = "0";
            row["ClaimPaymentNum"] = "0";
            row["colorText"] = Color.Black.ToArgb().ToString();
            row["creditsDouble"] = 0;
            row["credits"] = "";
            row["DateTime"] = DateTime.MinValue;
            row["date"] = "";
            row["description"] = Lans.g("AccountModule","Payment Plan.") + "\r\n" + Lans.g("AccountModule","Total loan amount: ") + princ.ToString("c") + "\r\n" + Lans.g("AccountModule","Principal Remaining: ") + bal.ToString("c");
            if (!StringSupport.equals(rawPayPlan.Rows[i]["PlanNum"].ToString(), "0"))
            {
                continue;
            }
             
            //row["description"]+="\r\n"+Lans.g("AccountModule","This 'payment plan' is only used ");
            //don't show insurance payment plans on statements.
            //Although if they are properly deleting insurance pp charges, no such pp's will be here anyway.
            //so all payment plans will have a patient.
            if (singlePatient && rawPayPlan.Rows[i]["PatNum"].ToString() != pat.PatNum.ToString())
            {
                continue;
            }
             
            row["description"] += "\r\nPatient: " + fam.GetNameInFamLF(PIn.Long(rawPayPlan.Rows[i]["PatNum"].ToString()));
            row["patient"] = "";
            row["PatNum"] = "0";
            row["PayNum"] = "0";
            row["PayPlanNum"] = "0";
            row["PayPlanChargeNum"] = "0";
            row["ProcCode"] = "";
            row["ProcNum"] = "0";
            row["procsOnObj"] = "";
            row["prov"] = "";
            row["StatementNum"] = "0";
            row["tth"] = "";
            rows.Add(row);
            //detail rows-------------------------------------------------------------------------------
            payPlanNum = PIn.Long(rawPayPlan.Rows[i]["PayPlanNum"].ToString());
            rawAmort = getPayPlanAmortTable(payPlanNum);
            for (int d = rawAmort.Rows.Count - 1;d >= 0;d--)
            {
                //remove future entries, going backwards
                if ((DateTime)rawAmort.Rows[d]["DateTime"] > toDate.AddDays(PrefC.getLong(PrefName.PayPlansBillInAdvanceDays)))
                {
                    rawAmort.Rows.RemoveAt(d);
                }
                 
            }
            //grab the payPlanDue amount from the last row
            if (rawAmort.Rows.Count > 0)
            {
                payPlanDue += (double)rawAmort.Rows[rawAmort.Rows.Count - 1]["balanceDouble"];
            }
             
            for (int d = rawAmort.Rows.Count - 1;d >= 0;d--)
            {
                //remove old entries, going backwards
                if ((DateTime)rawAmort.Rows[d]["DateTime"] < fromDate)
                {
                    rawAmort.Rows.RemoveAt(d);
                }
                 
            }
            for (int d = 0;d < rawAmort.Rows.Count;d++)
            {
                row = table.NewRow();
                row["AdjNum"] = "0";
                row["balance"] = rawAmort.Rows[d]["balance"];
                row["balanceDouble"] = rawAmort.Rows[d]["balanceDouble"];
                row["chargesDouble"] = rawAmort.Rows[d]["chargesDouble"];
                row["charges"] = rawAmort.Rows[d]["charges"];
                row["ClaimNum"] = "0";
                row["ClaimPaymentNum"] = "0";
                row["colorText"] = Color.Black.ToArgb().ToString();
                row["creditsDouble"] = rawAmort.Rows[d]["creditsDouble"];
                row["credits"] = rawAmort.Rows[d]["credits"];
                row["DateTime"] = rawAmort.Rows[d]["DateTime"];
                row["date"] = rawAmort.Rows[d]["date"];
                row["description"] = rawAmort.Rows[d]["description"];
                row["patient"] = rawAmort.Rows[d]["patient"];
                row["PatNum"] = rawAmort.Rows[d]["PatNum"];
                row["PayNum"] = rawAmort.Rows[d]["PayNum"];
                row["PayPlanNum"] = "0";
                row["PayPlanChargeNum"] = rawAmort.Rows[d]["PayPlanChargeNum"];
                row["ProcCode"] = "";
                row["ProcNum"] = "0";
                row["procsOnObj"] = "";
                row["prov"] = rawAmort.Rows[d]["prov"];
                row["StatementNum"] = "0";
                row["tth"] = "";
                rows.Add(row);
            }
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        retVal.Tables.Add(table);
    }

    /**
    * All rows for the entire family are getting passed in here.  (Except Invoices)  The rows have already been sorted.  Balances have not been computed, and we will do that here, separately for each patient (except invoices).
    */
    private static void getPatientTable(Family fam, List<DataRow> rows, boolean isInvoice) throws Exception {
        //No need to check RemotingRole; no call to db.
        //DataConnection dcon=new DataConnection();
        DataTable table = new DataTable("patient");
        DataRow row = new DataRow();
        table.Columns.Add("balance");
        table.Columns.Add("balanceDouble", double.class);
        table.Columns.Add("name");
        table.Columns.Add("PatNum");
        List<DataRow> rowspat = new List<DataRow>();
        double bal = new double();
        double balfam = 0;
        for (int p = 0;p < fam.ListPats.Length;p++)
        {
            row = table.NewRow();
            bal = 0;
            for (int i = 0;i < rows.Count;i++)
            {
                if (fam.ListPats[p].PatNum.ToString() == rows[i]["PatNum"].ToString())
                {
                    bal += (double)rows[i]["chargesDouble"];
                    bal -= (double)rows[i]["creditsDouble"];
                }
                 
            }
            balfam += bal;
            row["balanceDouble"] = bal;
            row["balance"] = bal.ToString("n");
            row["name"] = fam.ListPats[p].GetNameLF();
            row["PatNum"] = fam.ListPats[p].PatNum.ToString();
            rowspat.Add(row);
            if (isInvoice)
            {
            }
            else
            {
                //we don't have all the rows, so we don't want to try to compute balance
                if ((double)bal != fam.ListPats[p].EstBalance)
                {
                    Patient patnew = fam.ListPats[p].Copy();
                    patnew.EstBalance = (double)bal;
                    Patients.Update(patnew, fam.ListPats[p]);
                }
                 
            } 
        }
        //Row for entire family
        row = table.NewRow();
        row["balanceDouble"] = balfam;
        row["balance"] = balfam.ToString("f");
        row["name"] = Lans.g("AccountModule","Entire Family");
        row["PatNum"] = fam.ListPats[0].PatNum.ToString();
        rowspat.Add(row);
        for (int i = 0;i < rowspat.Count;i++)
        {
            table.Rows.Add(rowspat[i]);
        }
        retVal.Tables.Add(table);
    }

    /**
    * Future appointments.
    */
    private static void getApptTable(Family fam, boolean singlePatient, long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), fam, singlePatient, patNum);
            return ;
        }
         
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = new DataTable("appts");
        DataRow row = new DataRow();
        table.Columns.Add("descript");
        table.Columns.Add("PatNum");
        List<DataRow> rows = new List<DataRow>();
        //Today.AddDays(1) midnight tonight
        String command = "SELECT AptDateTime,PatNum,ProcDescript " + "FROM appointment " + "WHERE AptDateTime > " + POut.DateT(DateTime.Now) + " " + "AND AptStatus !=" + POut.Long(((Enum)ApptStatus.PtNote).ordinal()) + " " + "AND AptStatus !=" + POut.Long(((Enum)ApptStatus.PtNoteCompleted).ordinal()) + " " + "AND AptStatus !=" + POut.Long(((Enum)ApptStatus.UnschedList).ordinal()) + " " + "AND (";
        if (singlePatient)
        {
            command += "PatNum =" + POut.long(patNum);
        }
        else
        {
            for (int i = 0;i < fam.ListPats.Length;i++)
            {
                if (i != 0)
                {
                    command += "OR ";
                }
                 
                command += "PatNum =" + POut.Long(fam.ListPats[i].PatNum) + " ";
            }
        } 
        command += ") ORDER BY PatNum,AptDateTime";
        DataTable raw = dcon.getTable(command);
        DateTime dateT = new DateTime();
        long patNumm = new long();
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            row = table.NewRow();
            patNumm = PIn.Long(raw.Rows[i]["PatNum"].ToString());
            dateT = PIn.DateT(raw.Rows[i]["AptDateTime"].ToString());
            row["descript"] = fam.getNameInFamFL(patNumm) + ":  " + dateT.ToString("dddd") + ",  " + dateT.ToShortDateString() + ",  " + dateT.ToShortTimeString() + ",  " + raw.Rows[i]["ProcDescript"].ToString();
            row["PatNum"] = patNumm.ToString();
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        retVal.Tables.Add(table);
    }

    private static void getMisc(Family fam, long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), fam, patNum);
            return ;
        }
         
        DataTable table = new DataTable("misc");
        DataRow row = new DataRow();
        table.Columns.Add("descript");
        table.Columns.Add("value");
        List<DataRow> rows = new List<DataRow>();
        //FamFinancial note--------------------
        String command = "SELECT FamFinancial " + "FROM patientnote WHERE patnum =" + POut.Long(fam.ListPats[0].PatNum);
        DataTable raw = Db.getTable(command);
        row = table.NewRow();
        row["descript"] = "FamFinancial";
        row["value"] = "";
        if (raw.Rows.Count == 1)
        {
            row["value"] = PIn.String(raw.Rows[0][0].ToString());
        }
         
        rows.Add(row);
        //payPlanDue---------------------------
        row = table.NewRow();
        row["descript"] = "payPlanDue";
        row["value"] = POut.double((double)payPlanDue);
        rows.Add(row);
        //balanceForward-----------------------
        row = table.NewRow();
        row["descript"] = "balanceForward";
        row["value"] = POut.double((double)balanceForward);
        rows.Add(row);
        //patInsEst----------------------------
        //not received
        command = "SELECT SUM(inspayest+writeoff) FROM claimproc " + "WHERE status = 0 " + "AND PatNum=" + POut.long(patNum);
        raw = Db.getTable(command);
        row = table.NewRow();
        row["descript"] = "patInsEst";
        row["value"] = raw.Rows[0][0].ToString();
        rows.Add(row);
        //Unearned income----------------------
        command = "SELECT SUM(SplitAmt) FROM paysplit WHERE " + "UnearnedType>0 AND (";
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            if (i > 0)
            {
                command += " OR ";
            }
             
            command += "PatNum= " + POut.Long(fam.ListPats[i].PatNum);
        }
        command += ")";
        double unearnedAmt = PIn.double(Db.getScalar(command));
        row = table.NewRow();
        row["descript"] = "unearnedIncome";
        row["value"] = unearnedAmt;
        rows.Add(row);
        for (int i = 0;i < rows.Count;i++)
        {
            //final prep:
            table.Rows.Add(rows[i]);
        }
        retVal.Tables.Add(table);
    }

}


