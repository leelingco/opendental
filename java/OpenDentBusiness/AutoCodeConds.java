//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:36 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AutoCodeCond;
import OpenDentBusiness.AutoCodeCondC;
import OpenDentBusiness.AutoCondition;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Tooth;

/**
* 
*/
public class AutoCodeConds   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from autocodecond ORDER BY cond";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "AutoCodeCond";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        AutoCodeCondC.setList(Crud.AutoCodeCondCrud.TableToList(table).ToArray());
    }

    /**
    * 
    */
    public static void clearCache() throws Exception {
        AutoCodeCondC.setList(null);
    }

    /**
    * 
    */
    public static long insert(AutoCodeCond Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.AutoCodeCondNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.AutoCodeCondNum;
        }
         
        return Crud.AutoCodeCondCrud.Insert(Cur);
    }

    /**
    * 
    */
    public static void update(AutoCodeCond Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.AutoCodeCondCrud.Update(Cur);
    }

    /**
    * 
    */
    public static void delete(AutoCodeCond Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
        }
         
        String command = "DELETE from autocodecond WHERE autocodecondnum = '" + POut.long(Cur.AutoCodeCondNum) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void deleteForItemNum(long itemNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), itemNum);
            return ;
        }
         
        String command = "DELETE from autocodecond WHERE autocodeitemnum = '" + POut.long(itemNum) + "'";
        //AutoCodeItems.Cur.AutoCodeItemNum)
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static List<AutoCodeCond> getListForItem(long autoCodeItemNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<AutoCodeCond> retVal = new List<AutoCodeCond>();
        for (int i = 0;i < AutoCodeCondC.getList().Length;i++)
        {
            if (AutoCodeCondC.getList()[i].AutoCodeItemNum == autoCodeItemNum)
            {
                retVal.Add(AutoCodeCondC.getList()[i]);
            }
             
        }
        return retVal;
    }

    /**
    * 
    */
    public static boolean isSurf(AutoCondition myAutoCondition) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(myAutoCondition)
        {
            case One_Surf: 
            case Two_Surf: 
            case Three_Surf: 
            case Four_Surf: 
            case Five_Surf: 
                return true;
            default: 
                return false;
        
        }
    }

    /**
    * 
    */
    public static boolean conditionIsMet(AutoCondition myAutoCondition, String toothNum, String surf, boolean isAdditional, boolean willBeMissing, int age) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(myAutoCondition)
        {
            case Anterior: 
                return Tooth.isAnterior(toothNum);
            case Posterior: 
                return Tooth.isPosterior(toothNum);
            case Premolar: 
                return Tooth.isPreMolar(toothNum);
            case Molar: 
                return Tooth.isMolar(toothNum);
            case One_Surf: 
                return surf.Length == 1;
            case Two_Surf: 
                return surf.Length == 2;
            case Three_Surf: 
                return surf.Length == 3;
            case Four_Surf: 
                return surf.Length == 4;
            case Five_Surf: 
                return surf.Length == 5;
            case First: 
                return !isAdditional;
            case EachAdditional: 
                return isAdditional;
            case Maxillary: 
                return Tooth.isMaxillary(toothNum);
            case Mandibular: 
                return !Tooth.isMaxillary(toothNum);
            case Primary: 
                return Tooth.isPrimary(toothNum);
            case Permanent: 
                return !Tooth.isPrimary(toothNum);
            case Pontic: 
                return willBeMissing;
            case Retainer: 
                return !willBeMissing;
            case AgeOver18: 
                return age > 18;
            default: 
                return false;
        
        }
    }

}


