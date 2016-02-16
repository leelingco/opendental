//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.InsFilingCodeSubtype;
import OpenDentBusiness.InsFilingCodeSubtypeC;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class InsFilingCodeSubtypes   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM insfilingcodesubtype ORDER BY Descript";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "InsFilingCodeSubtype";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        InsFilingCodeSubtypeC.setListt(Crud.InsFilingCodeSubtypeCrud.TableToList(table));
    }

    /**
    * Gets one InsFilingCodeSubtype from the database.
    */
    public static InsFilingCodeSubtype getOne(long insFilingCodeSubtypeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<InsFilingCodeSubtype>GetObject(MethodBase.GetCurrentMethod(), insFilingCodeSubtypeNum);
        }
         
        return Crud.InsFilingCodeSubtypeCrud.SelectOne(insFilingCodeSubtypeNum);
    }

    /**
    * 
    */
    public static long insert(InsFilingCodeSubtype insFilingCodeSubtype) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            insFilingCodeSubtype.InsFilingCodeSubtypeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), insFilingCodeSubtype);
            return insFilingCodeSubtype.InsFilingCodeSubtypeNum;
        }
         
        return Crud.InsFilingCodeSubtypeCrud.Insert(insFilingCodeSubtype);
    }

    /**
    * 
    */
    public static void update(InsFilingCodeSubtype insFilingCodeSubtype) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), insFilingCodeSubtype);
            return ;
        }
         
        Crud.InsFilingCodeSubtypeCrud.Update(insFilingCodeSubtype);
    }

    /**
    * Surround with try/catch
    */
    public static void delete(long insFilingCodeSubtypeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), insFilingCodeSubtypeNum);
            return ;
        }
         
        String command = "SELECT COUNT(*) FROM insplan WHERE FilingCodeSubtype=" + POut.long(insFilingCodeSubtypeNum);
        if (!StringSupport.equals(Db.getScalar(command), "0"))
        {
            throw new ApplicationException(Lans.g("InsFilingCodeSubtype","Already in use by insplans."));
        }
         
        Crud.InsFilingCodeSubtypeCrud.Delete(insFilingCodeSubtypeNum);
    }

    public static List<InsFilingCodeSubtype> getForInsFilingCode(long insFilingCodeNum) throws Exception {
        List<InsFilingCodeSubtype> insFilingCodeSubtypes = new List<InsFilingCodeSubtype>();
        for (int i = 0;i < InsFilingCodeSubtypeC.getListt().Count;i++)
        {
            if (InsFilingCodeSubtypeC.getListt()[i].InsFilingCodeNum == insFilingCodeNum)
            {
                insFilingCodeSubtypes.Add(InsFilingCodeSubtypeC.getListt()[i]);
            }
             
        }
        return insFilingCodeSubtypes;
    }

    public static void deleteForInsFilingCode(long insFilingCodeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), insFilingCodeNum);
            return ;
        }
         
        String command = "DELETE FROM insfilingcodesubtype " + "WHERE InsFilingCodeNum=" + POut.long(insFilingCodeNum);
        Db.nonQ(command);
    }

}


