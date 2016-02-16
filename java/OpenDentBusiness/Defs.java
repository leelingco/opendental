//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:39 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Def;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Defs;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class Defs   
{
    /**
    * If using remoting, then the calling program is responsible for filling the arrays on the client since the automated part only happens on the server.  So there are TWO sets of arrays in a server situation, but only one in a small office that connects directly to the database.
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM definition ORDER BY Category,ItemOrder";
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Def";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Def> list = Crud.DefCrud.TableToList(table);
        DefC.setLong(new Def[Enum.GetValues(DefCat.class).Length]);
        for (int j = 0;j < Enum.GetValues(DefCat.class).Length;j++)
        {
            DefC.getLong()[j] = GetForCategory(j, true, list);
        }
        DefC.setShort(new Def[Enum.GetValues(DefCat.class).Length]);
        for (int j = 0;j < Enum.GetValues(DefCat.class).Length;j++)
        {
            DefC.getShort()[j] = GetForCategory(j, false, list);
        }
    }

    /**
    * Used by the refresh method above.
    */
    private static Def[] getForCategory(int catIndex, boolean includeHidden, List<Def> list) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Def> retVal = new List<Def>();
        for (int i = 0;i < list.Count;i++)
        {
            if ((int)list[i].Category != catIndex)
            {
                continue;
            }
             
            if (list[i].IsHidden && !includeHidden)
            {
                continue;
            }
             
            retVal.Add(list[i]);
        }
        return retVal.ToArray();
    }

    /**
    * Only used in FormDefinitions
    */
    public static Def[] getCatList(int myCat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Def[]>GetObject(MethodBase.GetCurrentMethod(), myCat);
        }
         
        String command = "SELECT * from definition" + " WHERE category = '" + myCat + "'" + " ORDER BY ItemOrder";
        return Crud.DefCrud.SelectMany(command).ToArray();
    }

    /**
    * 
    */
    public static void update(Def def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        Crud.DefCrud.Update(def);
    }

    /**
    * 
    */
    public static long insert(Def def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            def.DefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), def);
            return def.DefNum;
        }
         
        return Crud.DefCrud.Insert(def);
    }

    /**
    * CAUTION.  This does not perform all validations.  It only properly validates for two def types right now.
    */
    public static void delete(Def def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        if (def.Category != DefCat.SupplyCats && def.Category != DefCat.ClaimCustomTracking)
        {
            throw new ApplicationException("NOT Allowed to delete this type of def.");
        }
         
        String command = "";
        if (def.Category == DefCat.SupplyCats)
        {
            command = "SELECT COUNT(*) FROM supply WHERE Category=" + POut.long(def.DefNum);
        }
        else if (def.Category == DefCat.ClaimCustomTracking)
        {
            command = "SELECT COUNT(*) FROM claim WHERE CustomTracking=" + POut.long(def.DefNum);
        }
          
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("Defs","Def is in use.  Not allowed to delete."));
        }
         
        command = "DELETE FROM definition WHERE DefNum=" + POut.long(def.DefNum);
        Db.nonQ(command);
        command = "UPDATE definition SET ItemOrder=ItemOrder-1 " + "WHERE Category=" + POut.Long(((Enum)def.Category).ordinal()) + " AND ItemOrder > " + POut.Long(def.ItemOrder);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void hideDef(Def def) throws Exception {
        //No need to check RemotingRole; no call to db.
        def.IsHidden = true;
        Defs.update(def);
    }

    /**
    * 
    */
    public static void setOrder(int mySelNum, int myItemOrder, Def[] list) throws Exception {
        //No need to check RemotingRole; no call to db.
        Def def = list[mySelNum];
        def.ItemOrder = myItemOrder;
        //Cur=temp;
        Defs.update(def);
    }

}


