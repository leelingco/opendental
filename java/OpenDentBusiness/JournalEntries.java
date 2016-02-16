//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.JournalEntry;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.Reconciles;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class JournalEntries   
{
    /**
    * Used when displaying the splits for a transaction.
    */
    public static List<JournalEntry> getForTrans(long transactionNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<JournalEntry>>GetObject(MethodBase.GetCurrentMethod(), transactionNum);
        }
         
        String command = "SELECT * FROM journalentry " + "WHERE TransactionNum=" + POut.long(transactionNum);
        return Crud.JournalEntryCrud.SelectMany(command);
    }

    /**
    * Used to display a list of entries for one account.
    */
    public static List<JournalEntry> getForAccount(long accountNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<JournalEntry>>GetObject(MethodBase.GetCurrentMethod(), accountNum);
        }
         
        String command = "SELECT * FROM journalentry " + "WHERE AccountNum=" + POut.long(accountNum) + " ORDER BY DateDisplayed";
        return Crud.JournalEntryCrud.SelectMany(command);
    }

    /**
    * Used in reconcile window.
    */
    public static List<JournalEntry> getForReconcile(long accountNum, boolean includeUncleared, long reconcileNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<JournalEntry>>GetObject(MethodBase.GetCurrentMethod(), accountNum, includeUncleared, reconcileNum);
        }
         
        String command = "SELECT * FROM journalentry " + "WHERE AccountNum=" + POut.long(accountNum) + " AND (ReconcileNum=" + POut.long(reconcileNum);
        if (includeUncleared)
        {
            command += " OR ReconcileNum=0)";
        }
        else
        {
            command += ")";
        } 
        command += " ORDER BY DateDisplayed";
        return Crud.JournalEntryCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(JournalEntry je) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            je.JournalEntryNum = Meth.GetLong(MethodBase.GetCurrentMethod(), je);
            return je.JournalEntryNum;
        }
         
        if (je.DebitAmt < 0 || je.CreditAmt < 0)
        {
            throw new ApplicationException(Lans.g("JournalEntries","Error. Credit and debit must both be positive."));
        }
         
        return Crud.JournalEntryCrud.Insert(je);
    }

    /**
    * 
    */
    public static void update(JournalEntry je) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), je);
            return ;
        }
         
        if (je.DebitAmt < 0 || je.CreditAmt < 0)
        {
            throw new ApplicationException(Lans.g("JournalEntries","Error. Credit and debit must both be positive."));
        }
         
        Crud.JournalEntryCrud.Update(je);
    }

    /**
    * 
    */
    public static void delete(JournalEntry je) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), je);
            return ;
        }
         
        //This method is only used once in synch below.  Validation needs to be done, but doing it inside the loop would be dangerous.
        //So validation is done in the UI as follows:
        //1. Deleting an entire transaction is validated in business layer.
        //2. When editing a transaction attached to reconcile, simple view is blocked.
        //3. Double clicking on grid lets you change JEs not attached to reconcile.
        //4. Double clicking on grid lets you change notes even if attached to reconcile.
        String command = "DELETE FROM journalentry WHERE JournalEntryNum = " + POut.long(je.JournalEntryNum);
        Db.nonQ(command);
    }

    /**
    * Used in FormTransactionEdit to synch database with changes user made to the journalEntry list for a transaction.  Must supply an old list for comparison.  Only the differences are saved.  Surround with try/catch, because it will thrown an exception if any entries are negative.
    */
    public static void updateList(List<JournalEntry> oldJournalList, List<JournalEntry> newJournalList) throws Exception {
        for (int i = 0;i < newJournalList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (newJournalList[i].DebitAmt < 0 || newJournalList[i].CreditAmt < 0)
            {
                throw new ApplicationException(Lans.g("JournalEntries","Error. Credit and debit must both be positive."));
            }
             
        }
        JournalEntry newJournalEntry;
        for (int i = 0;i < oldJournalList.Count;i++)
        {
            //loop through the old list
            newJournalEntry = null;
            for (int j = 0;j < newJournalList.Count;j++)
            {
                if (newJournalList[j] == null || newJournalList[j].JournalEntryNum == 0)
                {
                    continue;
                }
                 
                if (oldJournalList[i].JournalEntryNum == newJournalList[j].JournalEntryNum)
                {
                    newJournalEntry = newJournalList[j];
                    break;
                }
                 
            }
            if (newJournalEntry == null)
            {
                //journalentry with matching journalEntryNum was not found, so it must have been deleted
                delete((JournalEntry)oldJournalList[i]);
                continue;
            }
             
            //journalentry was found with matching journalEntryNum, so check for changes
            if (newJournalEntry.AccountNum != oldJournalList[i].AccountNum || newJournalEntry.DateDisplayed != oldJournalList[i].DateDisplayed || newJournalEntry.DebitAmt != oldJournalList[i].DebitAmt || newJournalEntry.CreditAmt != oldJournalList[i].CreditAmt || !StringSupport.equals(newJournalEntry.Memo, oldJournalList[i].Memo) || !StringSupport.equals(newJournalEntry.Splits, oldJournalList[i].Splits) || !StringSupport.equals(newJournalEntry.CheckNumber, oldJournalList[i].CheckNumber))
            {
                update(newJournalEntry);
            }
             
        }
        for (int i = 0;i < newJournalList.Count;i++)
        {
            //loop through the new list
            if (newJournalList[i] == null)
            {
                continue;
            }
             
            if (newJournalList[i].JournalEntryNum != 0)
            {
                continue;
            }
             
            //entry with journalEntryNum=0, so it's new
            Insert(newJournalList[i]);
        }
    }

    /**
    * Called from FormTransactionEdit.
    */
    public static boolean attachedToReconcile(List<JournalEntry> journalList) throws Exception {
        for (int i = 0;i < journalList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (journalList[i].ReconcileNum != 0)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Called from FormTransactionEdit.
    */
    public static DateTime getReconcileDate(List<JournalEntry> journalList) throws Exception {
        for (int i = 0;i < journalList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (journalList[i].ReconcileNum != 0)
            {
                return Reconciles.GetOne(journalList[i].ReconcileNum).DateReconcile;
            }
             
        }
        return DateTime.MinValue;
    }

    /**
    * Called once from FormReconcileEdit when closing.  Saves the reconcileNum for every item in the list.
    */
    public static void saveList(List<JournalEntry> journalList, long reconcileNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), journalList, reconcileNum);
            return ;
        }
         
        String command = "UPDATE journalentry SET ReconcileNum=0 WHERE";
        String str = "";
        for (int i = 0;i < journalList.Count;i++)
        {
            if (journalList[i].ReconcileNum == 0)
            {
                if (!StringSupport.equals(str, ""))
                {
                    str += " OR";
                }
                 
                str += " JournalEntryNum=" + POut.Long(journalList[i].JournalEntryNum);
            }
             
        }
        if (!StringSupport.equals(str, ""))
        {
            command += str;
            Db.nonQ(command);
        }
         
        command = "UPDATE journalentry SET ReconcileNum=" + POut.long(reconcileNum) + " WHERE";
        str = "";
        for (int i = 0;i < journalList.Count;i++)
        {
            if (journalList[i].ReconcileNum == reconcileNum)
            {
                if (!StringSupport.equals(str, ""))
                {
                    str += " OR";
                }
                 
                str += " JournalEntryNum=" + POut.Long(journalList[i].JournalEntryNum);
            }
             
        }
        if (!StringSupport.equals(str, ""))
        {
            command += str;
            Db.nonQ(command);
        }
         
    }

}


/*//<summary>Attempts to delete all journal entries for one transaction.  Will later throw an error if attached to any reconciles.</summary>
		public static void DeleteForTrans(int transactionNum){
			string command="DELETE FROM journalentry WHERE TransactionNum="+POut.PInt(transactionNum);
			DataConnection dcon=new DataConnection();
			Db.NonQ(command);
		}*/