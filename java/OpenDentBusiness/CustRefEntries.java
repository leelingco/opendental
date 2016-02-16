//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CustRefEntry;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class CustRefEntries   
{
    /**
    * Gets one CustRefEntry from the db.
    */
    public static CustRefEntry getOne(long custRefEntryNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<CustRefEntry>GetObject(MethodBase.GetCurrentMethod(), custRefEntryNum);
        }
         
        return Crud.CustRefEntryCrud.SelectOne(custRefEntryNum);
    }

    /**
    * 
    */
    public static long insert(CustRefEntry custRefEntry) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            custRefEntry.CustRefEntryNum = Meth.GetLong(MethodBase.GetCurrentMethod(), custRefEntry);
            return custRefEntry.CustRefEntryNum;
        }
         
        return Crud.CustRefEntryCrud.Insert(custRefEntry);
    }

    /**
    * 
    */
    public static void update(CustRefEntry custRefEntry) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), custRefEntry);
            return ;
        }
         
        Crud.CustRefEntryCrud.Update(custRefEntry);
    }

    /**
    * 
    */
    public static void delete(long custRefEntryNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), custRefEntryNum);
            return ;
        }
         
        String command = "DELETE FROM custrefentry WHERE CustRefEntryNum = " + POut.long(custRefEntryNum);
        Db.nonQ(command);
    }

    /**
    * Gets all the entries for the customer.
    */
    public static List<CustRefEntry> getEntryListForCustomer(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<CustRefEntry>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM custrefentry WHERE PatNumCust=" + POut.long(patNum) + " OR PatNumRef=" + POut.long(patNum);
        return Crud.CustRefEntryCrud.SelectMany(command);
    }

    /**
    * Gets all the entries for the reference.
    */
    public static List<CustRefEntry> getEntryListForReference(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<CustRefEntry>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM custrefentry WHERE PatNumRef=" + POut.long(patNum);
        return Crud.CustRefEntryCrud.SelectMany(command);
    }

}


