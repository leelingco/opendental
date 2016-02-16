//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:36 PM
//

package OpenDentBusiness;

import CS2JNet.JavaSupport.language.RefSupport;
import OpenDentBusiness.AutoCode;
import OpenDentBusiness.AutoCodeC;
import OpenDentBusiness.AutoCodeCond;
import OpenDentBusiness.AutoCodeConds;
import OpenDentBusiness.AutoCodeItem;
import OpenDentBusiness.AutoCodeItemC;
import OpenDentBusiness.AutoCodeItems;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class AutoCodeItems   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM autocodeitem";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "AutoCodeItem";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        AutoCodeItemC.setHList(new Hashtable());
        AutoCodeItemC.setList(Crud.AutoCodeItemCrud.TableToList(table).ToArray());
        for (int i = 0;i < AutoCodeItemC.getList().Length;i++)
        {
            if (!AutoCodeItemC.getHList().ContainsKey(AutoCodeItemC.getList()[i].CodeNum))
            {
                AutoCodeItemC.getHList().Add(AutoCodeItemC.getList()[i].CodeNum, AutoCodeItemC.getList()[i].AutoCodeNum);
            }
             
        }
    }

    /**
    * 
    */
    public static void clearCache() throws Exception {
        AutoCodeItemC.setHList(null);
        AutoCodeItemC.setList(null);
    }

    /**
    * 
    */
    public static long insert(AutoCodeItem Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.AutoCodeItemNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.AutoCodeItemNum;
        }
         
        return Crud.AutoCodeItemCrud.Insert(Cur);
    }

    /**
    * 
    */
    public static void update(AutoCodeItem Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.AutoCodeItemCrud.Update(Cur);
    }

    /**
    * 
    */
    public static void delete(AutoCodeItem Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE FROM autocodeitem WHERE AutoCodeItemNum = '" + POut.long(Cur.AutoCodeItemNum) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void delete(long autoCodeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), autoCodeNum);
            return ;
        }
         
        String command = "DELETE FROM autocodeitem WHERE AutoCodeNum = '" + POut.long(autoCodeNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Gets from cache.  No call to db.
    */
    public static List<AutoCodeItem> getListForCode(long autoCodeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        //loop through AutoCodeItems.List to fill ListForCode
        List<AutoCodeItem> retVal = new List<AutoCodeItem>();
        for (int i = 0;i < AutoCodeItemC.getList().Length;i++)
        {
            if (AutoCodeItemC.getList()[i].AutoCodeNum == autoCodeNum)
            {
                retVal.Add(AutoCodeItemC.getList()[i]);
            }
             
        }
        return retVal;
    }

    //-----
    /**
    * Only called from ContrChart.listProcButtons_Click.  Called once for each tooth selected and for each autocode item attached to the button.
    */
    public static long getCodeNum(long autoCodeNum, String toothNum, String surf, boolean isAdditional, long patNum, int age) throws Exception {
        //No need to check RemotingRole; no call to db.
        boolean allCondsMet = new boolean();
        List<AutoCodeItem> listForCode = AutoCodeItems.getListForCode(autoCodeNum);
        if (listForCode.Count == 0)
        {
            return 0;
        }
         
        boolean willBeMissing = Procedures.willBeMissing(toothNum,patNum);
        List<AutoCodeCond> condList = new List<AutoCodeCond>();
        for (int i = 0;i < listForCode.Count;i++)
        {
            condList = AutoCodeConds.GetListForItem(listForCode[i].AutoCodeItemNum);
            allCondsMet = true;
            for (int j = 0;j < condList.Count;j++)
            {
                if (!AutoCodeConds.ConditionIsMet(condList[j].Cond, toothNum, surf, isAdditional, willBeMissing, age))
                {
                    allCondsMet = false;
                }
                 
            }
            if (allCondsMet)
            {
                return listForCode[i].CodeNum;
            }
             
        }
        return listForCode[0].CodeNum;
    }

    //if couldn't find a better match
    /**
    * Only called when closing the procedure edit window. Usually returns the supplied CodeNum, unless a better match is found.
    */
    public static long verifyCode(long codeNum, String toothNum, String surf, boolean isAdditional, long patNum, int age, RefSupport<AutoCode> AutoCodeCur) throws Exception {
        //No need to check RemotingRole; no call to db.
        boolean allCondsMet = new boolean();
        AutoCodeCur.setValue(null);
        if (!AutoCodeItemC.getHList().ContainsKey(codeNum))
        {
            return codeNum;
        }
         
        if (!AutoCodeC.getHList().ContainsKey((long)AutoCodeItemC.getHList()[codeNum]))
        {
            return codeNum;
        }
         
        //just in case.
        AutoCodeCur.setValue((AutoCode)AutoCodeC.getHList()[(long)AutoCodeItemC.getHList()[codeNum]]);
        if (AutoCodeCur.getValue().LessIntrusive)
        {
            return codeNum;
        }
         
        boolean willBeMissing = Procedures.willBeMissing(toothNum,patNum);
        List<AutoCodeItem> listForCode = AutoCodeItems.getListForCode((long)AutoCodeItemC.getHList()[codeNum]);
        List<AutoCodeCond> condList = new List<AutoCodeCond>();
        for (int i = 0;i < listForCode.Count;i++)
        {
            condList = AutoCodeConds.GetListForItem(listForCode[i].AutoCodeItemNum);
            allCondsMet = true;
            for (int j = 0;j < condList.Count;j++)
            {
                if (!AutoCodeConds.ConditionIsMet(condList[j].Cond, toothNum, surf, isAdditional, willBeMissing, age))
                {
                    allCondsMet = false;
                }
                 
            }
            if (allCondsMet)
            {
                return listForCode[i].CodeNum;
            }
             
        }
        return codeNum;
    }

}


//if couldn't find a better match