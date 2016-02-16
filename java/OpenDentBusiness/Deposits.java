//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:39 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Deposit;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Deposits   
{
    /**
    * Gets all Deposits, ordered by date.
    */
    public static Deposit[] refresh() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Deposit[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM deposit " + "ORDER BY DateDeposit";
        return Crud.DepositCrud.SelectMany(command).ToArray();
    }

    /**
    * Gets only Deposits which are not attached to transactions.
    */
    public static Deposit[] getUnattached() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Deposit[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM deposit " + "WHERE NOT EXISTS(SELECT * FROM transaction WHERE deposit.DepositNum=transaction.DepositNum) " + "ORDER BY DateDeposit";
        return Crud.DepositCrud.SelectMany(command).ToArray();
    }

    /**
    * Gets a single deposit directly from the database.
    */
    public static Deposit getOne(long depositNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Deposit>GetObject(MethodBase.GetCurrentMethod(), depositNum);
        }
         
        return Crud.DepositCrud.SelectOne(depositNum);
    }

    /**
    * 
    */
    public static void update(Deposit dep) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), dep);
            return ;
        }
         
        Crud.DepositCrud.Update(dep);
    }

    /**
    * 
    */
    public static long insert(Deposit dep) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            dep.DepositNum = Meth.GetLong(MethodBase.GetCurrentMethod(), dep);
            return dep.DepositNum;
        }
         
        return Crud.DepositCrud.Insert(dep);
    }

    /**
    * Also handles detaching all payments and claimpayments.  Throws exception if deposit is attached as a source document to a transaction.  The program should have detached the deposit from the transaction ahead of time, so I would never expect the program to throw this exception unless there was a bug.
    */
    public static void delete(Deposit dep) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), dep);
            return ;
        }
         
        //check dependencies
        String command = "";
        if (dep.DepositNum != 0)
        {
            command = "SELECT COUNT(*) FROM transaction WHERE DepositNum =" + POut.long(dep.DepositNum);
            if (PIn.long(Db.getCount(command)) > 0)
            {
                throw new ApplicationException(Lans.g("Deposits","Cannot delete deposit because it is attached to a transaction."));
            }
             
        }
         
        /*/check claimpayment 
        			command="SELECT COUNT(*) FROM claimpayment WHERE DepositNum ="+POut.PInt(DepositNum);
        			if(PIn.PInt(Db.GetCount(command))>0){
        				throw new InvalidProgramException(Lans.g("Deposits","Cannot delete deposit because it has payments attached"));
        			}*/
        //ready to delete
        command = "UPDATE payment SET DepositNum=0 WHERE DepositNum=" + POut.long(dep.DepositNum);
        Db.nonQ(command);
        command = "UPDATE claimpayment SET DepositNum=0 WHERE DepositNum=" + POut.long(dep.DepositNum);
        Db.nonQ(command);
        Crud.DepositCrud.Delete(dep.DepositNum);
    }

}


