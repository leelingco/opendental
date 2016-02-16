//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcApptColor;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ProcApptColors   
{
    /**
    * A list of all ProcApptColors.
    */
    private static List<ProcApptColor> listt = new List<ProcApptColor>();
    /**
    * A list of all ProcApptColors.
    */
    public static List<ProcApptColor> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<ProcApptColor> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM procapptcolor ORDER BY CodeRange";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ProcApptColor";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.ProcApptColorCrud.TableToList(table);
    }

    /**
    * 
    */
    public static List<ProcApptColor> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ProcApptColor>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM procapptcolor WHERE PatNum = " + POut.long(patNum);
        return Crud.ProcApptColorCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(ProcApptColor procApptColor) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            procApptColor.ProcApptColorNum = Meth.GetLong(MethodBase.GetCurrentMethod(), procApptColor);
            return procApptColor.ProcApptColorNum;
        }
         
        return Crud.ProcApptColorCrud.Insert(procApptColor);
    }

    /**
    * 
    */
    public static void update(ProcApptColor procApptColor) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procApptColor);
            return ;
        }
         
        Crud.ProcApptColorCrud.Update(procApptColor);
    }

    /**
    * 
    */
    public static void delete(long procApptColorNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procApptColorNum);
            return ;
        }
         
        String command = "DELETE FROM procapptcolor WHERE ProcApptColorNum = " + POut.long(procApptColorNum);
        Db.nonQ(command);
    }

    /*
    		///<summary>Gets one ProcApptColor from the db.</summary>
    		public static ProcApptColor GetOne(long procApptColorNum){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
    				return Meth.GetObject<ProcApptColor>(MethodBase.GetCurrentMethod(),procApptColorNum);
    			}
    			return Crud.ProcApptColorCrud.SelectOne(procApptColorNum);
    		}*/
    /**
    * Supply code such as D####.  Returns null if no match
    */
    public static ProcApptColor getMatch(String procCode) throws Exception {
        String code1 = "";
        String code2 = "";
        for (int i = 0;i < getListt().Count;i++)
        {
            //using public property to trigger refresh if needed.
            if (getListt()[i].CodeRange.Contains("-"))
            {
                String[] codeSplit = getListt()[i].CodeRange.Split('-');
                code1 = codeSplit[0].Trim();
                code2 = codeSplit[1].Trim();
            }
            else
            {
                code1 = getListt()[i].CodeRange.Trim();
                code2 = getListt()[i].CodeRange.Trim();
            } 
            if (procCode.CompareTo(code1) < 0 || procCode.CompareTo(code2) > 0)
            {
                continue;
            }
             
            return listt[i];
        }
        return null;
    }

}


