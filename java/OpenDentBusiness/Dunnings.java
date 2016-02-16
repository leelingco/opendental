//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Dunning;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.YN;

/**
* 
*/
public class Dunnings   
{
    /**
    * Gets a list of all dunnings.
    */
    public static Dunning[] refresh() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Dunning[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM dunning " + "ORDER BY BillingType,AgeAccount,InsIsPending";
        return Crud.DunningCrud.SelectMany(command).ToArray();
    }

    //DataTable table=Db.GetTable(command);
    //Dunning[] List=new Dunning[table.Rows.Count];
    //for(int i=0;i<table.Rows.Count;i++) {
    //	List[i]=new Dunning();
    //	List[i].DunningNum     = PIn.Long(table.Rows[i][0].ToString());
    //	List[i].DunMessage     = PIn.String(table.Rows[i][1].ToString());
    //	List[i].BillingType    = PIn.Long(table.Rows[i][2].ToString());
    //	List[i].AgeAccount     = PIn.Byte(table.Rows[i][3].ToString());
    //	List[i].InsIsPending   = (YN)PIn.Long(table.Rows[i][4].ToString());
    //	List[i].MessageBold    = PIn.String(table.Rows[i][5].ToString());
    //}
    /**
    * 
    */
    public static long insert(Dunning dun) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            dun.DunningNum = Meth.GetLong(MethodBase.GetCurrentMethod(), dun);
            return dun.DunningNum;
        }
         
        return Crud.DunningCrud.Insert(dun);
    }

    /**
    * 
    */
    public static void update(Dunning dun) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), dun);
            return ;
        }
         
        Crud.DunningCrud.Update(dun);
    }

    /**
    * 
    */
    public static void delete(Dunning dun) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), dun);
            return ;
        }
         
        String command = "DELETE FROM dunning" + " WHERE DunningNum = " + POut.long(dun.DunningNum);
        Db.nonQ(command);
    }

    /**
    * Will return null if no dunning matches the given criteria.
    */
    public static Dunning getDunning(Dunning[] dunList, long billingType, int ageAccount, YN insIsPending) throws Exception {
        for (int i = dunList.Length - 1;i >= 0;i--)
        {
            //No need to check RemotingRole; no call to db.
            //loop backwards through Dunning list and find the first dunning that matches criteria.
            //0 in the list matches all
            if (dunList[i].BillingType != 0 && dunList[i].BillingType != billingType)
            {
                continue;
            }
             
            if (ageAccount < dunList[i].AgeAccount)
            {
                continue;
            }
             
            //match if age is >= item in list
            //unknown in the list matches all
            if (dunList[i].InsIsPending != YN.Unknown && dunList[i].InsIsPending != insIsPending)
            {
                continue;
            }
             
            return dunList[i];
        }
        return null;
    }

}


