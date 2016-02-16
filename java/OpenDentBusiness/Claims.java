//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimAttaches;
import OpenDentBusiness.ClaimPaySplit;
import OpenDentBusiness.ClaimSendQueueItem;
import OpenDentBusiness.Clearinghouses;
import OpenDentBusiness.Db;
import OpenDentBusiness.EnumClaimMedType;
import OpenDentBusiness.Meth;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.PIn;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.POut;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.X12TransactionItem;

/**
* 
*/
public class Claims   
{
    /**
    * Gets claimpaysplits attached to a claimpayment with the associated patient, insplan, and carrier. If showUnattached it also shows all claimpaysplits that have not been attached to a claimpayment. Pass (0,true) to just get all unattached (outstanding) claimpaysplits.
    */
    public static List<ClaimPaySplit> refreshByCheckOld(long claimPaymentNum, boolean showUnattached) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimPaySplit>>GetObject(MethodBase.GetCurrentMethod(), claimPaymentNum, showUnattached);
        }
         
        //Changed from \"_patName\" to patName_ for MySQL 5.5. Also added checks for #<table> and $<table>
        //received or supplemental or capclaim
        String command = "SELECT claim.DateService,claim.ProvTreat,CONCAT(CONCAT(patient.LName,', '),patient.FName) patName_" + ",carrier.CarrierName,SUM(claimproc.FeeBilled) feeBilled_,SUM(claimproc.InsPayAmt) insPayAmt_,claim.ClaimNum" + ",claimproc.ClaimPaymentNum,(SELECT clinic.Description FROM clinic WHERE claimproc.ClinicNum = clinic.ClinicNum) Description,claim.PatNum,PaymentRow " + " FROM claim,patient,insplan,carrier,claimproc" + " WHERE claimproc.ClaimNum = claim.ClaimNum" + " AND patient.PatNum = claim.PatNum" + " AND insplan.PlanNum = claim.PlanNum" + " AND insplan.CarrierNum = carrier.CarrierNum" + " AND (claimproc.Status = '1' OR claimproc.Status = '4' OR claimproc.Status=5)" + " AND (claimproc.ClaimPaymentNum = '" + POut.long(claimPaymentNum) + "'";
        if (showUnattached)
        {
            command += " OR (claimproc.InsPayAmt != 0 AND claimproc.ClaimPaymentNum = '0')";
        }
         
        //else shows only items attached to this payment
        command += ")" + " GROUP BY claim.DateService,claim.ProvTreat,CONCAT(CONCAT(patient.LName,', '),patient.FName) " + ",carrier.CarrierName,claim.ClaimNum" + ",claimproc.ClaimPaymentNum,claim.PatNum";
        command += " ORDER BY patName_";
        DataTable table = Db.getTable(command);
        return claimPaySplitTableToList(table);
    }

    /**
    * 
    */
    public static List<Claim> getClaimsByCheck(long claimPaymentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Claim>>GetObject(MethodBase.GetCurrentMethod(), claimPaymentNum);
        }
         
        String command = "SELECT * " + "FROM claim " + "WHERE claim.ClaimNum IN " + "(SELECT DISTINCT claimproc.ClaimNum " + "FROM claimproc " + "WHERE claimproc.ClaimPaymentNum=" + claimPaymentNum + ")";
        return ClaimCrud.SelectMany(command);
    }

    /**
    * Gets all outstanding claims for the batch payment window.
    */
    public static List<ClaimPaySplit> getOutstandingClaims(String carrierName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimPaySplit>>GetObject(MethodBase.GetCurrentMethod(), carrierName);
        }
         
        //string command="SELECT claim.DateService,claim.ProvTreat,CONCAT(CONCAT(patient.LName,', '),patient.FName) patName_,"
        //  +"carrier.CarrierName,ClaimFee feeBilled_,SUM(claimproc.InsPayAmt) insPayAmt_,claim.ClaimNum,"//SUM(claimproc.FeeBilled) feeBilled_ was low if inspay 0 on proc
        //  +"claimproc.ClaimPaymentNum,(SELECT clinic.Description FROM clinic WHERE claimproc.ClinicNum = clinic.ClinicNum) Description,claim.PatNum,PaymentRow  "
        //  +"FROM claim,patient,insplan,carrier,claimproc "
        //  +"WHERE claimproc.ClaimNum = claim.ClaimNum "
        //  +"AND patient.PatNum = claim.PatNum "
        //  +"AND insplan.PlanNum = claim.PlanNum "
        //  +"AND insplan.CarrierNum = carrier.CarrierNum "
        //  +"AND (claim.ClaimStatus = 'S' "
        //    +"OR (claim.ClaimStatus='R' AND claimproc.InsPayAmt!=0)) "//certain (very few) received claims will have payment amounts entered but not attached to payment
        //  +"AND ClaimType != 'PreAuth' "
        //  +"AND claimproc.ClaimPaymentNum=0 "
        //  +"AND carrier.CarrierName LIKE '%"+POut.String(carrierName)+"%' ";
        //SUM(claimproc.FeeBilled) feeBilled_ was low if inspay 0 on proc
        String command = "SELECT claim.DateService,claim.ProvTreat,CONCAT(patient.LName,', ',patient.FName) patName_," + "carrierA.CarrierName,ClaimFee feeBilled_,SUM(claimproc.InsPayAmt) insPayAmt_,claim.ClaimNum," + "claimproc.ClaimPaymentNum,(SELECT clinic.Description FROM clinic WHERE claimproc.ClinicNum = clinic.ClinicNum) Description,claim.PatNum,PaymentRow " + "FROM (SELECT CarrierNum, CarrierName FROM carrier WHERE CarrierName LIKE '%" + POut.string(carrierName) + "%') carrierA " + "INNER JOIN insplan ON insplan.CarrierNum = carrierA.CarrierNum " + "INNER JOIN claim ON insplan.PlanNum = claim.PlanNum " + "INNER JOIN claimproc ON claimproc.ClaimNum = claim.ClaimNum " + "INNER JOIN patient ON patient.PatNum = claimproc.PatNum " + "WHERE (claim.ClaimStatus = 'S' " + "OR (claim.ClaimStatus='R' AND claimproc.InsPayAmt!=0)) " + "AND ClaimType != 'PreAuth' AND claimproc.ClaimPaymentNum=0 ";
        //certain (very few) received claims will have payment amounts entered but not attached to payment
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command += "GROUP BY claim.ClaimNum ";
        }
        else
        {
            //oracle
            //js This might need some help and some testing in Oracle
            command += "GROUP BY claim.DateService,claim.ProvTreat,CONCAT(CONCAT(patient.LName,', '),patient.FName)," + "carrierA.CarrierName,feeBilled_,claim.ClaimNum,claimproc.ClaimPaymentNum,Description,claim.PatNum,PaymentRow ";
        } 
        //+" HAVING SUM(claimproc.InsPayAmt)<SUM(claimproc.InsPayEst)"
        command += "ORDER BY CarrierName,patName_";
        DataTable table = Db.getTable(command);
        return claimPaySplitTableToList(table);
    }

    /**
    * Gets all 'claims' attached to the claimpayment.
    */
    public static List<ClaimPaySplit> getAttachedToPayment(long claimPaymentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimPaySplit>>GetObject(MethodBase.GetCurrentMethod(), claimPaymentNum);
        }
         
        String command = "SELECT claim.DateService,claim.ProvTreat,CONCAT(CONCAT(patient.LName,', '),patient.FName) patName_," + "carrier.CarrierName,ClaimFee feeBilled_,SUM(claimproc.InsPayAmt) insPayAmt_,claim.ClaimNum," + "claimproc.ClaimPaymentNum,(SELECT clinic.Description FROM clinic WHERE claimproc.ClinicNum = clinic.ClinicNum) Description,claim.PatNum,PaymentRow " + " FROM claim,patient,insplan,carrier,claimproc" + " WHERE claimproc.ClaimNum = claim.ClaimNum" + " AND patient.PatNum = claim.PatNum" + " AND insplan.PlanNum = claim.PlanNum" + " AND insplan.CarrierNum = carrier.CarrierNum" + " AND claimproc.ClaimPaymentNum = " + claimPaymentNum + " ";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command += "GROUP BY claim.ClaimNum ";
        }
        else
        {
            //oracle
            command += "GROUP BY claim.DateService,claim.ProvTreat,CONCAT(CONCAT(patient.LName,', '),patient.FName)" + ",carrier.CarrierName,claim.ClaimNum,claimproc.ClaimPaymentNum,claim.PatNum ";
        } 
        command += "ORDER BY claimproc.PaymentRow";
        DataTable table = Db.getTable(command);
        return claimPaySplitTableToList(table);
    }

    /**
    * Gets all secondary claims for the related ClaimPaySplits. Called after a payment has been received.
    */
    public static DataTable getSecondaryClaims(List<ClaimPaySplit> claimsAttached) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), claimsAttached);
        }
         
        String command = "SELECT DISTINCT ProcNum FROM claimproc WHERE ClaimNum IN (";
        String claimNums = "";
        for (int i = 0;i < claimsAttached.Count;i++)
        {
            //used twice
            if (i > 0)
            {
                claimNums += ",";
            }
             
            claimNums += claimsAttached[i].ClaimNum;
        }
        command += claimNums + ") AND ProcNum!=0";
        //List<ClaimProc> tempClaimProcs=ClaimProcCrud.SelectMany(command);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return new DataTable();
        }
         
        //No procedures are attached to these claims.  This frequently happens in conversions.  No need to look for related secondary claims.
        command = "SELECT claimproc.PatNum,claimproc.ProcDate" + " FROM claimproc" + " JOIN claim ON claimproc.ClaimNum=claim.ClaimNum" + " WHERE ProcNum IN (";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (i > 0)
            {
                command += ",";
            }
             
            command += table.Rows[i]["ProcNum"].ToString();
        }
        command += ") AND claimproc.ClaimNum NOT IN (" + claimNums + ")" + " AND ClaimType = 'S'" + " GROUP BY claimproc.ClaimNum,claimproc.PatNum,claimproc.ProcDate";
        DataTable secondaryClaims = Db.getTable(command);
        return secondaryClaims;
    }

    /**
    * 
    */
    public static List<ClaimPaySplit> getInsPayNotAttachedForFixTool() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimPaySplit>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT claim.DateService,claim.ProvTreat,CONCAT(CONCAT(patient.LName,', '),patient.FName) patName_" + ",carrier.CarrierName,SUM(claimproc.FeeBilled) feeBilled_,SUM(claimproc.InsPayAmt) insPayAmt_,claim.ClaimNum" + ",claimproc.ClaimPaymentNum,(SELECT clinic.Description FROM clinic WHERE claimproc.ClinicNum = clinic.ClinicNum) Description,claim.PatNum,PaymentRow " + " FROM claim,patient,insplan,carrier,claimproc" + " WHERE claimproc.ClaimNum = claim.ClaimNum" + " AND patient.PatNum = claim.PatNum" + " AND insplan.PlanNum = claim.PlanNum" + " AND insplan.CarrierNum = carrier.CarrierNum" + " AND (claimproc.Status = '1' OR claimproc.Status = '4' OR claimproc.Status=5)" + " AND (claimproc.InsPayAmt != 0 AND claimproc.ClaimPaymentNum = '0')" + " GROUP BY claim.DateService,claim.ProvTreat,CONCAT(CONCAT(patient.LName,', '),patient.FName)" + ",carrier.CarrierName,claim.ClaimNum,claimproc.ClaimPaymentNum,claim.PatNum" + " ORDER BY patName_";
        //received or supplemental or capclaim
        DataTable table = Db.getTable(command);
        return claimPaySplitTableToList(table);
    }

    /**
    * 
    */
    private static List<ClaimPaySplit> claimPaySplitTableToList(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<ClaimPaySplit> splits = new List<ClaimPaySplit>();
        ClaimPaySplit split;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            split = new ClaimPaySplit();
            split.DateClaim = PIn.Date(table.Rows[i]["DateService"].ToString());
            split.ProvAbbr = Providers.GetAbbr(PIn.Long(table.Rows[i]["ProvTreat"].ToString()));
            split.PatName = PIn.String(table.Rows[i]["patName_"].ToString());
            split.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            split.Carrier = PIn.String(table.Rows[i]["CarrierName"].ToString());
            split.FeeBilled = PIn.Double(table.Rows[i]["feeBilled_"].ToString());
            split.InsPayAmt = PIn.Double(table.Rows[i]["insPayAmt_"].ToString());
            split.ClaimNum = PIn.Long(table.Rows[i]["ClaimNum"].ToString());
            split.ClaimPaymentNum = PIn.Long(table.Rows[i]["ClaimPaymentNum"].ToString());
            split.PaymentRow = PIn.Int(table.Rows[i]["PaymentRow"].ToString());
            split.ClinicDesc = PIn.String(table.Rows[i]["Description"].ToString());
            splits.Add(split);
        }
        return splits;
    }

    /**
    * Gets the specified claim from the database.  Can be null.
    */
    public static Claim getClaim(long claimNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Claim>GetObject(MethodBase.GetCurrentMethod(), claimNum);
        }
         
        String command = "SELECT * FROM claim" + " WHERE ClaimNum = " + claimNum.ToString();
        Claim retClaim = Crud.ClaimCrud.SelectOne(command);
        if (retClaim == null)
        {
            return null;
        }
         
        command = "SELECT * FROM claimattach WHERE ClaimNum = " + POut.long(claimNum);
        retClaim.Attachments = Crud.ClaimAttachCrud.SelectMany(command);
        return retClaim;
    }

    /**
    * Gets all claims for the specified patient. But without any attachments.
    */
    public static List<Claim> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Claim>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM claim" + " WHERE PatNum = " + patNum.ToString() + " ORDER BY dateservice";
        return Crud.ClaimCrud.SelectMany(command);
    }

    public static Claim getFromList(List<Claim> list, long claimNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (list[i].ClaimNum == claimNum)
            {
                return list[i].Copy();
            }
             
        }
        return null;
    }

    /**
    * 
    */
    public static long insert(Claim claim) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            claim.ClaimNum = Meth.GetLong(MethodBase.GetCurrentMethod(), claim);
            return claim.ClaimNum;
        }
         
        return Crud.ClaimCrud.Insert(claim);
    }

    /**
    * 
    */
    public static void update(Claim claim) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), claim);
            return ;
        }
         
        Crud.ClaimCrud.Update(claim);
        //now, delete all attachments and recreate.
        String command = "DELETE FROM claimattach WHERE ClaimNum=" + POut.long(claim.ClaimNum);
        Db.nonQ(command);
        for (int i = 0;i < claim.Attachments.Count;i++)
        {
            claim.Attachments[i].ClaimNum = claim.ClaimNum;
            ClaimAttaches.Insert(claim.Attachments[i]);
        }
    }

    /**
    * 
    */
    public static void delete(Claim Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE FROM claim WHERE ClaimNum = '" + POut.long(Cur.ClaimNum) + "'";
        Db.nonQ(command);
    }

    //command = "DELETE FROM canadianclaim WHERE ClaimNum = '"+POut.Long(Cur.ClaimNum)+"'";
    //Db.NonQ(command);
    //command = "DELETE FROM canadianextract WHERE ClaimNum = '"+POut.Long(Cur.ClaimNum)+"'";
    //Db.NonQ(command);
    /**
    * 
    */
    public static void detachProcsFromClaim(Claim Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "UPDATE procedurelog SET " + "claimnum = '0' " + "WHERE claimnum = '" + POut.long(Cur.ClaimNum) + "'";
        //MessageBox.Show(string command);
        Db.nonQ(command);
    }

    /*
    		///<summary>Called from claimsend window and from Claim edit window.  Use 0 to get all waiting claims, or an actual claimnum to get just one claim.</summary>
    		public static ClaimSendQueueItem[] GetQueueList(){
    			return GetQueueList(0,0);
    		}*/
    /**
    * Called from FormRpOutIns. Gets outstanding insurance claims. Requires all fields. provNumList may be empty (but will return null if isAllProv is false). dateMin and dateMax will not be used if they are set to DateTime.MinValue() (01/01/0001). If isPreauth is true only claims of type preauth will be returned.
    */
    public static DataTable getOutInsClaims(boolean isAllProv, List<long> provNumList, DateTime dateMin, DateTime dateMax, boolean isPreauth) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), isAllProv, provNumList, dateMin, dateMax, isPreauth);
        }
         
        String command = new String();
        command = "SELECT carrier.CarrierName,carrier.Phone,claim.ClaimType,patient.FName,patient.LName,patient.MiddleI,patient.PatNum,claim.DateService,claim.DateSent,claim.ClaimFee,claim.ClaimNum " + "FROM carrier,patient,claim,insplan " + "WHERE carrier.CarrierNum = insplan.CarrierNum " + "AND claim.PlanNum = insplan.PlanNum " + "AND claim.PatNum = patient.PatNum " + "AND claim.ClaimStatus='S' ";
        if (dateMin != DateTime.MinValue)
        {
            command += "AND claim.DateSent <= " + POut.date(dateMin) + " ";
        }
         
        if (dateMax != DateTime.MinValue)
        {
            command += "AND claim.DateSent >= " + POut.date(dateMax) + " ";
        }
         
        if (!isAllProv)
        {
            if (provNumList.Count > 0)
            {
                command += "AND claim.ProvTreat IN (";
                command += "" + provNumList[0];
                for (int i = 1;i < provNumList.Count;i++)
                {
                    command += "," + provNumList[i];
                }
                command += ") ";
            }
             
        }
         
        if (!isPreauth)
        {
            command += "AND claim.ClaimType!='Preauth' ";
        }
         
        command += "ORDER BY carrier.Phone,insplan.PlanNum";
        Object[] parameters;
        if (RemotingClient.RemotingRole == RemotingRole.ClientDirect)
        {
            //this is a temporary safe fix
            Plugins.hookAddCode(null,"Claims.GetOutInsClaims_beforequeryrun",parameters);
        }
         
        command = (String)parameters[0];
        DataTable table = Db.getTable(command);
        return table;
    }

    /**
    * Called from claimsend window and from Claim edit window.  Use 0 to get all waiting claims, or an actual claimnum to get just one claim.
    */
    public static ClaimSendQueueItem[] getQueueList(long claimNum, long clinicNum, long customTracking) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ClaimSendQueueItem[]>GetObject(MethodBase.GetCurrentMethod(), claimNum, clinicNum, customTracking);
        }
         
        String command = "SELECT claim.ClaimNum,carrier.NoSendElect" + ",CONCAT(CONCAT(CONCAT(concat(patient.LName,', '),patient.FName),' '),patient.MiddleI)" + ",claim.ClaimStatus,carrier.CarrierName,patient.PatNum,carrier.ElectID,MedType,claim.DateService,claim.ClinicNum " + "FROM claim " + "Left join insplan on claim.PlanNum = insplan.PlanNum " + "Left join carrier on insplan.CarrierNum = carrier.CarrierNum " + "Left join patient on patient.PatNum = claim.PatNum ";
        if (claimNum == 0)
        {
            command += "WHERE (claim.ClaimStatus = 'W' OR claim.ClaimStatus = 'P') ";
        }
        else
        {
            command += "WHERE claim.ClaimNum=" + POut.long(claimNum) + " ";
        } 
        if (clinicNum > 0)
        {
            command += "AND claim.ClinicNum=" + POut.long(clinicNum) + " ";
        }
         
        if (customTracking > 0)
        {
            command += "AND claim.CustomTracking=" + POut.long(customTracking) + " ";
        }
         
        command += "ORDER BY claim.DateService,patient.LName";
        DataTable table = Db.getTable(command);
        ClaimSendQueueItem[] listQueue = new ClaimSendQueueItem[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            listQueue[i] = new ClaimSendQueueItem();
            listQueue[i].ClaimNum = PIn.Long(table.Rows[i][0].ToString());
            listQueue[i].NoSendElect = PIn.Bool(table.Rows[i][1].ToString());
            listQueue[i].PatName = PIn.String(table.Rows[i][2].ToString());
            listQueue[i].ClaimStatus = PIn.String(table.Rows[i][3].ToString());
            listQueue[i].Carrier = PIn.String(table.Rows[i][4].ToString());
            listQueue[i].PatNum = PIn.Long(table.Rows[i][5].ToString());
            String payorID = PIn.String(table.Rows[i]["ElectID"].ToString());
            EnumClaimMedType medType = (EnumClaimMedType)PIn.Int(table.Rows[i]["MedType"].ToString());
            listQueue[i].ClearinghouseNum = Clearinghouses.automateClearinghouseSelection(payorID,medType);
            listQueue[i].MedType = medType;
            listQueue[i].DateService = PIn.Date(table.Rows[i]["DateService"].ToString());
            listQueue[i].ClinicNum = PIn.Long(table.Rows[i]["ClinicNum"].ToString());
        }
        return listQueue;
    }

    /**
    * Supply claimnums. Called from X12 to begin the sorting process on claims going to one clearinghouse.
    */
    public static List<X12TransactionItem> getX12TransactionInfo(long claimNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<long> claimNums = new List<long>();
        claimNums.Add(claimNum);
        return GetX12TransactionInfo(claimNums);
    }

    /**
    * Supply claimnums. Called from X12 to begin the sorting process on claims going to one clearinghouse.
    */
    public static List<X12TransactionItem> getX12TransactionInfo(List<long> claimNums) throws Exception {
        //ArrayList queueItemss){
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<X12TransactionItem>>GetObject(MethodBase.GetCurrentMethod(), claimNums);
        }
         
        StringBuilder str = new StringBuilder();
        for (int i = 0;i < claimNums.Count;i++)
        {
            if (i > 0)
            {
                str.Append(" OR");
            }
             
            str.Append(" claim.ClaimNum=" + POut.Long(claimNums[i]));
        }
        //((ClaimSendQueueItem)queueItems[i]).ClaimNum.ToString());
        String command = new String();
        command = "SELECT carrier.ElectID,claim.ProvBill,inssub.Subscriber," + "claim.PatNum,claim.ClaimNum,CASE WHEN inssub.Subscriber!=claim.PatNum THEN 1 ELSE 0 END AS subscNotPatient " + "FROM claim,insplan,inssub,carrier " + "WHERE claim.PlanNum=insplan.PlanNum " + "AND claim.InsSubNum=inssub.InsSubNum " + "AND carrier.CarrierNum=insplan.CarrierNum " + "AND (" + str.ToString() + ") " + "ORDER BY carrier.ElectID,claim.ProvBill,inssub.Subscriber,subscNotPatient,claim.PatNum";
        DataTable table = Db.getTable(command);
        List<X12TransactionItem> retVal = new List<X12TransactionItem>();
        //object[,] myA=new object[5,table.Rows.Count];
        X12TransactionItem item;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            item = new X12TransactionItem();
            item.PayorId0 = PIn.String(table.Rows[i][0].ToString());
            item.ProvBill1 = PIn.Long(table.Rows[i][1].ToString());
            item.Subscriber2 = PIn.Long(table.Rows[i][2].ToString());
            item.PatNum3 = PIn.Long(table.Rows[i][3].ToString());
            item.ClaimNum4 = PIn.Long(table.Rows[i][4].ToString());
            retVal.Add(item);
        }
        return retVal;
    }

    /**
    * Also sets the DateSent to today.
    */
    public static void setCanadianClaimSent(long claimNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), claimNum);
            return ;
        }
         
        String command = "UPDATE claim SET ClaimStatus = 'S'," + "DateSent= " + POut.date(MiscData.getNowDateTime()) + " WHERE ClaimNum = " + POut.long(claimNum);
        Db.nonQ(command);
    }

    public static boolean claimIdentifierInUse(String claimIdentifier, long claimNumExclude) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), claimIdentifier);
        }
         
        String command = "SELECT COUNT(*) FROM claim WHERE ClaimIdentifier='" + POut.string(claimIdentifier) + "' AND ClaimNum<>" + POut.long(claimNumExclude);
        return (!StringSupport.equals(Db.getTable(command).Rows[0][0].ToString(), "0"));
    }

    /**
    * Returns the ClaimNum for the claim that has a claim identifier beginning with the specified claimIdentifier, but only if there is exactly one claim matched. Otherwise 0 is returned.
    */
    public static long getClaimNumForIdentifier(String claimIdentifier) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<long>GetObject(MethodBase.GetCurrentMethod(), claimIdentifier);
        }
         
        //Our claim identifiers can be longer than 20 characters (mostly when using replication). When the claim identifier is sent out on the claim, it is truncated to 20
        //characters. Therefore, if the claim identifier is longer than 20 characters, then it was truncated when sent out, so we have to look for claims beginning with the
        //claim identifier given if there is not an exact match.
        String command = "SELECT ClaimNum FROM claim WHERE ClaimIdentifier='" + POut.string(claimIdentifier) + "'";
        DataTable dtClaims = Db.getTable(command);
        if (dtClaims.Rows.Count == 0)
        {
            //No exact match for the claim identifier. This will happen with replication sometimes.
            command = "SELECT ClaimNum FROM claim WHERE ClaimIdentifier LIKE CONCAT('" + POut.string(claimIdentifier) + "','%')";
            dtClaims = Db.getTable(command);
            //There is a slight chance that we will have more than one match, and in this case we will return 0.
            if (dtClaims.Rows.Count != 1)
            {
                return 0;
            }
             
        }
         
        return PIn.Long(dtClaims.Rows[0][0].ToString());
    }

}


