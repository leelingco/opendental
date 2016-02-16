//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ClaimPayment;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ClaimPayments   
{
    /**
    * 
    */
    public static DataTable getForClaim(long claimNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), claimNum);
        }
         
        DataTable table = new DataTable();
        DataRow row = new DataRow();
        table.Columns.Add("amount");
        table.Columns.Add("BankBranch");
        table.Columns.Add("ClaimPaymentNum");
        table.Columns.Add("checkDate");
        table.Columns.Add("CheckNum");
        table.Columns.Add("Note");
        List<DataRow> rows = new List<DataRow>();
        String command = "SELECT BankBranch,claimpayment.ClaimPaymentNum,CheckNum,CheckDate," + "SUM(claimproc.InsPayAmt) amount,Note " + "FROM claimpayment,claimproc " + "WHERE claimpayment.ClaimPaymentNum = claimproc.ClaimPaymentNum " + "AND claimproc.ClaimNum = '" + POut.long(claimNum) + "' " + "GROUP BY claimpayment.ClaimPaymentNum, BankBranch, CheckDate, CheckNum, Note";
        DataTable rawT = Db.getTable(command);
        DateTime date = new DateTime();
        for (int i = 0;i < rawT.Rows.Count;i++)
        {
            row = table.NewRow();
            row["amount"] = PIn.Double(rawT.Rows[i]["amount"].ToString()).ToString("F");
            row["BankBranch"] = rawT.Rows[i]["BankBranch"].ToString();
            row["ClaimPaymentNum"] = rawT.Rows[i]["ClaimPaymentNum"].ToString();
            date = PIn.Date(rawT.Rows[i]["CheckDate"].ToString());
            row["checkDate"] = date.ToShortDateString();
            row["CheckNum"] = rawT.Rows[i]["CheckNum"].ToString();
            row["Note"] = rawT.Rows[i]["Note"].ToString();
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * Gets all claimpayments within the specified date range and from the specified clinic. 0 means all clinics selected.
    */
    public static List<ClaimPayment> getForDateRange(DateTime dateFrom, DateTime dateTo, long clinicNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimPayment>>GetObject(MethodBase.GetCurrentMethod(), dateFrom, dateTo, clinicNum);
        }
         
        //"SELECT ClaimPaymentNum,CheckDate,CheckAmt,"
        //+"Checknum,BankBranch,Note,DepositNum,"
        //+"ClinicNum,DepositNum,CarrierName,DateIssued "
        //+"FROM claimpayment "
        String command = "SELECT * FROM claimpayment " + "WHERE CheckDate >= " + POut.date(dateFrom) + "AND CheckDate <= " + POut.date(dateTo);
        if (clinicNum != 0)
        {
            command += " AND ClinicNum=" + POut.long(clinicNum);
        }
         
        command += " ORDER BY CheckDate";
        return Crud.ClaimPaymentCrud.SelectMany(command);
    }

    /**
    * Gets all unattached claimpayments for display in a new deposit.  Excludes payments before dateStart and partials.
    */
    public static ClaimPayment[] getForDeposit(DateTime dateStart, long clinicNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ClaimPayment[]>GetObject(MethodBase.GetCurrentMethod(), dateStart, clinicNum);
        }
         
        String command = "SELECT * FROM claimpayment " + "WHERE DepositNum = 0 " + "AND CheckDate >= " + POut.date(dateStart);
        if (clinicNum != 0)
        {
            command += " AND ClinicNum=" + POut.long(clinicNum);
        }
         
        command += " AND IsPartial=0" + " ORDER BY CheckDate";
        return Crud.ClaimPaymentCrud.SelectMany(command).ToArray();
    }

    //Don't let users attach partial insurance payments to deposits.
    /**
    * Gets all claimpayments for one specific deposit.
    */
    public static ClaimPayment[] getForDeposit(long depositNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ClaimPayment[]>GetObject(MethodBase.GetCurrentMethod(), depositNum);
        }
         
        String command = "SELECT * FROM claimpayment " + "WHERE DepositNum = " + POut.long(depositNum) + " ORDER BY CheckDate";
        return Crud.ClaimPaymentCrud.SelectMany(command).ToArray();
    }

    /**
    * Gets one claimpayment directly from database.
    */
    public static ClaimPayment getOne(long claimPaymentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ClaimPayment>GetObject(MethodBase.GetCurrentMethod(), claimPaymentNum);
        }
         
        String command = "SELECT * FROM claimpayment " + "WHERE ClaimPaymentNum = " + POut.long(claimPaymentNum);
        return Crud.ClaimPaymentCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static long insert(ClaimPayment cp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            cp.ClaimPaymentNum = Meth.GetLong(MethodBase.GetCurrentMethod(), cp);
            return cp.ClaimPaymentNum;
        }
         
        return Crud.ClaimPaymentCrud.Insert(cp);
    }

    /**
    * If trying to change the amount and attached to a deposit, it will throw an error, so surround with try catch.
    */
    public static void update(ClaimPayment cp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cp);
            return ;
        }
         
        String command = "SELECT DepositNum,CheckAmt FROM claimpayment " + "WHERE ClaimPaymentNum=" + POut.long(cp.ClaimPaymentNum);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return ;
        }
         
        //if claimpayment is already attached to a deposit
        if (!StringSupport.equals(table.Rows[0][0].ToString(), "0") && PIn.Double(table.Rows[0][1].ToString()) != cp.CheckAmt)
        {
            throw new ApplicationException(Lans.g("ClaimPayments","Not allowed to change the amount on checks attached to deposits."));
        }
         
        //and checkAmt changes
        Crud.ClaimPaymentCrud.Update(cp);
    }

    /**
    * Surround by try catch, because it will throw an exception if trying to delete a claimpayment attached to a deposit or if there are eobs attached.
    */
    public static void delete(ClaimPayment cp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cp);
            return ;
        }
         
        //validate deposits
        String command = "SELECT DepositNum FROM claimpayment " + "WHERE ClaimPaymentNum=" + POut.long(cp.ClaimPaymentNum);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return ;
        }
         
        if (!StringSupport.equals(table.Rows[0][0].ToString(), "0"))
        {
            throw new ApplicationException(Lans.g("ClaimPayments","Not allowed to delete a payment attached to a deposit."));
        }
         
        //if claimpayment is already attached to a deposit
        //validate eobs
        command = "SELECT COUNT(*) FROM eobattach WHERE ClaimPaymentNum=" + POut.long(cp.ClaimPaymentNum);
        if (!StringSupport.equals(Db.getScalar(command), "0"))
        {
            throw new ApplicationException(Lans.g("ClaimPayments","Not allowed to delete this payment because EOBs are attached."));
        }
         
        command = "UPDATE claimproc SET " + "ClaimPaymentNum=0 " + "WHERE claimpaymentNum=" + POut.long(cp.ClaimPaymentNum);
        //MessageBox.Show(string command);
        Db.nonQ(command);
        command = "DELETE FROM claimpayment " + "WHERE ClaimPaymentnum =" + POut.long(cp.ClaimPaymentNum);
        //MessageBox.Show(string command);
        Db.nonQ(command);
    }

}


