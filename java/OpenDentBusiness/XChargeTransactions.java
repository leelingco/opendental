//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.XChargeTransaction;

public class XChargeTransactions   
{
    /**
    * 
    */
    public static long insert(XChargeTransaction xChargeTransaction) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            xChargeTransaction.XChargeTransactionNum = Meth.GetLong(MethodBase.GetCurrentMethod(), xChargeTransaction);
            return xChargeTransaction.XChargeTransactionNum;
        }
         
        return Crud.XChargeTransactionCrud.Insert(xChargeTransaction);
    }

    /**
    * Gets one XChargeTransaction from the db by batchNum and itemNum. For example: ("1515","0001").
    */
    public static XChargeTransaction checkByBatchItem(String batchNum, String itemNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<XChargeTransaction>GetObject(MethodBase.GetCurrentMethod(), batchNum, itemNum);
        }
         
        String command = "SELECT * FROM xchargetransaction WHERE BatchNum = '" + POut.string(batchNum) + "' AND ItemNum = '" + POut.string(itemNum) + "'";
        return Crud.XChargeTransactionCrud.SelectOne(command);
    }

    /*
    		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
    		///<summary></summary>
    		public static List<XChargeTransaction> Refresh(long patNum){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetObject<List<XChargeTransaction>>(MethodBase.GetCurrentMethod(),patNum);
    			}
    			string command="SELECT * FROM xchargetransaction WHERE PatNum = "+POut.Long(patNum);
    			return Crud.XChargeTransactionCrud.SelectMany(command);
    		}
    		///<summary></summary>
    		public static void Update(XChargeTransaction xChargeTransaction){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),xChargeTransaction);
    				return;
    			}
    			Crud.XChargeTransactionCrud.Update(xChargeTransaction);
    		}
    	*/
    /**
    * 
    */
    public static void delete(long xChargeTransactionNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), xChargeTransactionNum);
            return ;
        }
         
        String command = "DELETE FROM xchargetransaction WHERE XChargeTransactionNum = " + POut.long(xChargeTransactionNum);
        Db.nonQ(command);
    }

}


