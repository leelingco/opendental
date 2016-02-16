//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.InsFilingCode;
import OpenDentBusiness.InsFilingCodeC;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class InsFilingCodes   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String c = "SELECT * FROM insfilingcode ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), c);
        table.TableName = "InsFilingCode";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        InsFilingCodeC.setListt(Crud.InsFilingCodeCrud.TableToList(table));
    }

    public static String getEclaimCode(long insFilingCodeNum) throws Exception {
        for (int i = 0;i < InsFilingCodeC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (InsFilingCodeC.getListt()[i].InsFilingCodeNum != insFilingCodeNum)
            {
                continue;
            }
             
            return InsFilingCodeC.getListt()[i].EclaimCode;
        }
        return "CI";
    }

    /**
    * 
    */
    public static long insert(InsFilingCode insFilingCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            insFilingCode.InsFilingCodeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), insFilingCode);
            return insFilingCode.InsFilingCodeNum;
        }
         
        return Crud.InsFilingCodeCrud.Insert(insFilingCode);
    }

    /**
    * 
    */
    public static void update(InsFilingCode insFilingCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), insFilingCode);
            return ;
        }
         
        Crud.InsFilingCodeCrud.Update(insFilingCode);
    }

    /**
    * Surround with try/catch
    */
    public static void delete(long insFilingCodeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), insFilingCodeNum);
            return ;
        }
         
        String command = "SELECT COUNT(*) FROM insplan WHERE FilingCode=" + POut.long(insFilingCodeNum);
        if (!StringSupport.equals(Db.getScalar(command), "0"))
        {
            throw new ApplicationException(Lans.g("InsFilingCode","Already in use by insplans."));
        }
         
        Crud.InsFilingCodeCrud.Delete(insFilingCodeNum);
    }

}


