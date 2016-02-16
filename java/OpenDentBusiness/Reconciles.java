//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.Reconcile;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* The two lists get refreshed the first time they are needed rather than at startup.
*/
public class Reconciles   
{
    /**
    * 
    */
    public static Reconcile[] getList(long accountNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Reconcile[]>GetObject(MethodBase.GetCurrentMethod(), accountNum);
        }
         
        String command = "SELECT * FROM reconcile WHERE AccountNum=" + POut.long(accountNum) + " ORDER BY DateReconcile";
        return Crud.ReconcileCrud.SelectMany(command).ToArray();
    }

    /**
    * Gets one reconcile directly from the database.  Program will crash if reconcile not found.
    */
    public static Reconcile getOne(long reconcileNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Reconcile>GetObject(MethodBase.GetCurrentMethod(), reconcileNum);
        }
         
        String command = "SELECT * FROM reconcile WHERE ReconcileNum=" + POut.long(reconcileNum);
        return Crud.ReconcileCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static long insert(Reconcile reconcile) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            reconcile.ReconcileNum = Meth.GetLong(MethodBase.GetCurrentMethod(), reconcile);
            return reconcile.ReconcileNum;
        }
         
        return Crud.ReconcileCrud.Insert(reconcile);
    }

    /**
    * 
    */
    public static void update(Reconcile reconcile) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), reconcile);
            return ;
        }
         
        Crud.ReconcileCrud.Update(reconcile);
    }

    /**
    * Throws exception if Reconcile is in use.
    */
    public static void delete(Reconcile reconcile) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), reconcile);
            return ;
        }
         
        //check to see if any journal entries are attached to this Reconcile
        String command = "SELECT COUNT(*) FROM journalentry WHERE ReconcileNum=" + POut.long(reconcile.ReconcileNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("FormReconcileEdit","Not allowed to delete a Reconcile with existing journal entries."));
        }
         
        command = "DELETE FROM reconcile WHERE ReconcileNum = " + POut.long(reconcile.ReconcileNum);
        Db.nonQ(command);
    }

}


