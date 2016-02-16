//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.ElectID;
import OpenDentBusiness.ProviderSupplementalID;

public class ElectIDs   
{
    private static ElectID[] list = new ElectID[]();
    /**
    * This is the list of all electronic IDs.
    */
    //No need to check RemotingRole; no call to db.
    public static ElectID[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(ElectID[] value) throws Exception {
        list = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from electid ORDER BY CarrierName";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ElectID";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.ElectIDCrud.TableToList(table).ToArray();
    }

    public static void insert(ElectID electID) throws Exception {
        //No need to check RemotingRole; no call to db.
        Crud.ElectIDCrud.Insert(electID);
    }

    public static void update(ElectID electID) throws Exception {
        //No need to check RemotingRole; no call to db.
        Crud.ElectIDCrud.Update(electID);
    }

    /**
    * 
    */
    public static ProviderSupplementalID[] getRequiredIdents(String payorID) throws Exception {
        //No need to check RemotingRole; no call to db.
        ElectID electID = getID(payorID);
        if (electID == null)
        {
            return new ProviderSupplementalID[0];
        }
         
        if (StringSupport.equals(electID.ProviderTypes, ""))
        {
            return new ProviderSupplementalID[0];
        }
         
        String[] provTypes = electID.ProviderTypes.Split(',');
        if (provTypes.Length == 0)
        {
            return new ProviderSupplementalID[0];
        }
         
        ProviderSupplementalID[] retVal = new ProviderSupplementalID[provTypes.Length];
        for (int i = 0;i < provTypes.Length;i++)
        {
            retVal[i] = (ProviderSupplementalID)(Convert.ToInt32(provTypes[i]));
        }
        return retVal;
    }

    /*
    			if(electID=="SB601"){//BCBS of GA
    				retVal=new ProviderSupplementalID[2];
    				retVal[0]=ProviderSupplementalID.BlueShield;
    				retVal[1]=ProviderSupplementalID.SiteNumber;
    			}*/
    /**
    * Gets ONE ElectID that uses the supplied payorID. Even if there are multiple payors using that ID.  So use this carefully.
    */
    public static ElectID getID(String payorID) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList electIDs = getIDs(payorID);
        if (electIDs.Count == 0)
        {
            return null;
        }
         
        return (ElectID)electIDs[0];
    }

    //simply return the first one we encounter
    /**
    * Gets an arrayList of ElectID objects based on a supplied payorID. If no matches found, then returns array of 0 length. Used to display payors in FormInsPlan and also to get required idents.  This means that all payors with the same ID should have the same required idents and notes.
    */
    public static ArrayList getIDs(String payorID) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList retVal = new ArrayList();
        for (int i = 0;i < getList().Length;i++)
        {
            if (StringSupport.equals(getList()[i].PayorID, payorID))
            {
                retVal.Add(getList()[i]);
            }
             
        }
        return retVal;
    }

    /**
    * Gets the names of the payors to display based on the payorID.  Since carriers sometimes share payorIDs, there will often be multiple payor names returned.
    */
    public static String[] getDescripts(String payorID) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(payorID, ""))
        {
            return new String[]{  };
        }
         
        ArrayList electIDs = getIDs(payorID);
        String[] retVal = new String[electIDs.Count];
        for (int i = 0;i < retVal.Length;i++)
        {
            retVal[i] = ((ElectID)electIDs[i]).CarrierName;
        }
        return retVal;
    }

}


