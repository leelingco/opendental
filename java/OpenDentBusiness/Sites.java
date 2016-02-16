//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Site;
import OpenDentBusiness.SiteC;

/**
* 
*/
public class Sites   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String c = "SELECT * FROM site ORDER BY Description";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), c);
        table.TableName = "Site";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        SiteC.setList(Crud.SiteCrud.TableToList(table).ToArray());
    }

    /**
    * Gets one Site from the database.
    */
    public static Site createObject(long siteNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Site>GetObject(MethodBase.GetCurrentMethod(), siteNum);
        }
         
        return Crud.SiteCrud.SelectOne(siteNum);
    }

    /**
    * 
    */
    public static long insert(Site site) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            site.SiteNum = Meth.GetLong(MethodBase.GetCurrentMethod(), site);
            return site.SiteNum;
        }
         
        return Crud.SiteCrud.Insert(site);
    }

    /**
    * 
    */
    public static void update(Site site) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), site);
            return ;
        }
         
        Crud.SiteCrud.Update(site);
    }

    /**
    * 
    */
    public static void deleteObject(long siteNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), siteNum);
            return ;
        }
         
        //validate that not already in use.
        String command = "SELECT LName,FName FROM patient WHERE SiteNum=" + POut.long(siteNum);
        DataTable table = Db.getTable(command);
        //int count=PIn.PInt(Db.GetCount(command));
        String pats = "";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (i > 0)
            {
                pats += ", ";
            }
             
            pats += table.Rows[i]["FName"].ToString() + " " + table.Rows[i]["LName"].ToString();
        }
        if (table.Rows.Count > 0)
        {
            throw new ApplicationException(Lans.g("Sites","Site is already in use by patient(s). Not allowed to delete. ") + pats);
        }
         
        Crud.SiteCrud.Delete(siteNum);
    }

    public static String getDescription(long siteNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (siteNum == 0)
        {
            return "";
        }
         
        for (int i = 0;i < SiteC.getList().Length;i++)
        {
            if (SiteC.getList()[i].SiteNum == siteNum)
            {
                return SiteC.getList()[i].Description;
            }
             
        }
        return "";
    }

    public static List<Site> getListFiltered(String snippet) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Site> retVal = new List<Site>();
        if (StringSupport.equals(snippet, ""))
        {
            return retVal;
        }
         
        for (int i = 0;i < SiteC.getList().Length;i++)
        {
            if (SiteC.getList()[i].Description.ToLower().Contains(snippet.ToLower()))
            {
                retVal.Add(SiteC.getList()[i]);
            }
             
        }
        return retVal;
    }

    /**
    * Will return -1 if no match.
    */
    public static long findMatchSiteNum(String description) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(description, ""))
        {
            return 0;
        }
         
        for (int i = 0;i < SiteC.getList().Length;i++)
        {
            if (SiteC.getList()[i].Description.ToLower() == description.ToLower())
            {
                return SiteC.getList()[i].SiteNum;
            }
             
        }
        return -1;
    }

}


