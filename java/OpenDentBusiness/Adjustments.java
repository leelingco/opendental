//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Adjustment;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Handles database commands related to the adjustment table in the db.
*/
public class Adjustments   
{
    /**
    * Gets all adjustments for a single patient.
    */
    public static Adjustment[] refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Adjustment[]>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM adjustment" + " WHERE PatNum = " + POut.long(patNum) + " ORDER BY AdjDate";
        return Crud.AdjustmentCrud.SelectMany(command).ToArray();
    }

    /**
    * Gets one adjustment from the db.
    */
    public static Adjustment getOne(long adjNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Adjustment>GetObject(MethodBase.GetCurrentMethod(), adjNum);
        }
         
        String command = "SELECT * FROM adjustment" + " WHERE AdjNum = " + POut.long(adjNum);
        return Crud.AdjustmentCrud.SelectOne(adjNum);
    }

    public static void detachFromInvoice(long statementNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), statementNum);
            return ;
        }
         
        String command = "UPDATE adjustment SET StatementNum=0 WHERE StatementNum='" + POut.long(statementNum) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void update(Adjustment adj) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), adj);
            return ;
        }
         
        Crud.AdjustmentCrud.Update(adj);
    }

    /**
    * 
    */
    public static long insert(Adjustment adj) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            adj.AdjNum = Meth.GetLong(MethodBase.GetCurrentMethod(), adj);
            return adj.AdjNum;
        }
         
        return Crud.AdjustmentCrud.Insert(adj);
    }

    /**
    * This will soon be eliminated or changed to only allow deleting on same day as EntryDate.
    */
    public static void delete(Adjustment adj) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), adj);
            return ;
        }
         
        Crud.AdjustmentCrud.Delete(adj.AdjNum);
    }

    /**
    * Loops through the supplied list of adjustments and returns an ArrayList of adjustments for the given proc.
    */
    public static ArrayList getForProc(long procNum, Adjustment[] List) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList retVal = new ArrayList();
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].ProcNum == procNum)
            {
                retVal.Add(List[i]);
            }
             
        }
        return retVal;
    }

    /**
    * Used from ContrAccount and ProcEdit to display and calculate adjustments attached to procs.
    */
    public static double getTotForProc(long procNum, Adjustment[] List) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].ProcNum == procNum)
            {
                retVal += List[i].AdjAmt;
            }
             
        }
        return retVal;
    }

    /*
    		///<summary>Must make sure Refresh is done first.  Returns the sum of all adjustments for this patient.  Amount might be pos or neg.</summary>
    		public static double ComputeBal(Adjustment[] List){
    			double retVal=0;
    			for(int i=0;i<List.Length;i++){
    				retVal+=List[i].AdjAmt;
    			}
    			return retVal;
    		}*/
    /**
    * Returns the number of finance charges deleted.
    */
    public static long undoFinanceCharges(DateTime dateUndo) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), dateUndo);
        }
         
        String command = new String();
        long numAdj = new long();
        DataTable table = new DataTable();
        command = "SELECT ValueString FROM preference WHERE PrefName = 'FinanceChargeAdjustmentType'";
        table = Db.getTable(command);
        numAdj = PIn.Long(table.Rows[0][0].ToString());
        command = "DELETE FROM adjustment WHERE AdjDate=" + POut.date(dateUndo) + " AND AdjType=" + POut.long(numAdj);
        return Db.nonQ(command);
    }

    /**
    * Returns the number of billing charges deleted.
    */
    public static long undoBillingCharges(DateTime dateUndo) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), dateUndo);
        }
         
        String command = new String();
        long numAdj = new long();
        DataTable table = new DataTable();
        command = "SELECT ValueString FROM preference WHERE PrefName = 'BillingChargeAdjustmentType'";
        table = Db.getTable(command);
        numAdj = PIn.Long(table.Rows[0][0].ToString());
        command = "DELETE FROM adjustment WHERE AdjDate=" + POut.date(dateUndo) + " AND AdjType=" + POut.long(numAdj);
        return Db.nonQ(command);
    }

}


