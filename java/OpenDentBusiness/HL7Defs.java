//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.HL7.InternalCentricity;
import OpenDentBusiness.HL7.InternalEcwFull;
import OpenDentBusiness.HL7.InternalEcwStandalone;
import OpenDentBusiness.HL7.InternalEcwTight;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7DefMessages;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class HL7Defs   
{
    /**
    * A list of all HL7Defs.
    */
    private static List<HL7Def> listt = new List<HL7Def>();
    /**
    * A list of all HL7Defs.
    */
    public static List<HL7Def> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<HL7Def> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM hl7def ORDER BY Description";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "HL7Def";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.HL7DefCrud.TableToList(table);
    }

    /**
    * Gets an internal HL7Def from the database of the specified type.
    */
    public static HL7Def getInternalFromDb(String internalType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<HL7Def>GetObject(MethodBase.GetCurrentMethod(), internalType);
        }
         
        String command = "SELECT * FROM hl7def WHERE IsInternal=1 " + "AND InternalType='" + POut.string(internalType) + "'";
        return Crud.HL7DefCrud.SelectOne(command);
    }

    /**
    * Gets from cache.  This will return null if no HL7defs are enabled.  Since only one can be enabled, this will return only the enabled one. No need to check RemotingRole, cache is filled by calling GetTableRemotelyIfNeeded.
    */
    public static HL7Def getOneDeepEnabled() throws Exception {
        HL7Def retval = null;
        for (int i = 0;i < getListt().Count;i++)
        {
            if (getListt()[i].IsEnabled)
            {
                retval = getListt()[i];
            }
             
        }
        if (retval == null)
        {
            return null;
        }
         
        if (retval.IsInternal)
        {
            //if internal, messages, segments, and fields will not be in the database
            getDeepForInternal(retval);
        }
        else
        {
            retval.hl7DefMessages = HL7DefMessages.getDeepFromCache(retval.HL7DefNum);
        } 
        return retval;
    }

    /**
    * Gets a full deep list of all internal defs.  If one is enabled, then it might be in database.
    */
    public static List<HL7Def> getDeepInternalList() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<HL7Def>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        List<HL7Def> listInternal = new List<HL7Def>();
        HL7Def def;
        def = getInternalFromDb("eCWFull");
        //might be null
        def = InternalEcwFull.GetDeepInternal(def);
        listInternal.Add(def);
        def = getInternalFromDb("eCWStandalone");
        def = InternalEcwStandalone.GetDeepInternal(def);
        listInternal.Add(def);
        def = getInternalFromDb("eCWTight");
        def = InternalEcwTight.GetDeepInternal(def);
        listInternal.Add(def);
        def = getInternalFromDb("Centricity");
        def = InternalCentricity.GetDeepInternal(def);
        listInternal.Add(def);
        return listInternal;
    }

    //Add defs for other companies like Centricity here later.
    /**
    * Gets from C# internal code rather than db
    */
    private static void getDeepForInternal(HL7Def def) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(def.InternalType, "eCWFull"))
        {
            def = InternalEcwFull.GetDeepInternal(def);
        }
        else //def that we're passing in is guaranteed to not be null
        if (StringSupport.equals(def.InternalType, "eCWStandalone"))
        {
            def = InternalEcwStandalone.GetDeepInternal(def);
        }
        else if (StringSupport.equals(def.InternalType, "eCWTight"))
        {
            def = InternalEcwTight.GetDeepInternal(def);
        }
        else if (StringSupport.equals(def.InternalType, "Centricity"))
        {
            def = InternalCentricity.GetDeepInternal(def);
        }
            
    }

    //no need to return a def because the original reference won't have been lost.
    /**
    * Tells us whether there is an existing enable HL7Def.
    */
    public static boolean isExistingHL7Enabled(long excludeHL7DefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), excludeHL7DefNum);
        }
         
        String command = "SELECT COUNT(*) FROM hl7def WHERE IsEnabled=1 AND HL7DefNum != " + POut.long(excludeHL7DefNum);
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * Tells us whether there is an existing enabled HL7Def.
    */
    public static boolean isExistingHL7Enabled() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT COUNT(*) FROM hl7def WHERE IsEnabled=1";
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * Gets a full deep list of all defs that are not internal from the database.
    */
    public static List<HL7Def> getDeepCustomList() throws Exception {
        List<HL7Def> customList = getShallowFromDb();
        for (int d = 0;d < customList.Count;d++)
        {
            customList[d].hl7DefMessages = HL7DefMessages.GetDeepFromDb(customList[d].HL7DefNum);
        }
        return customList;
    }

    /**
    * Gets shallow list of all defs that are not internal from the database
    */
    public static List<HL7Def> getShallowFromDb() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<HL7Def>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM hl7def WHERE IsInternal=0";
        return Crud.HL7DefCrud.SelectMany(command);
    }

    /**
    * Only used from Unit Tests.  Since we clear the db of hl7Defs we have to insert this internal def not update it.
    */
    public static void enableInternalForTests(String internalType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), internalType);
            return ;
        }
         
        HL7Def hl7Def = null;
        List<HL7Def> defList = getDeepInternalList();
        for (int i = 0;i < defList.Count;i++)
        {
            if (StringSupport.equals(defList[i].InternalType, internalType))
            {
                hl7Def = defList[i];
                break;
            }
             
        }
        if (hl7Def == null)
        {
            return ;
        }
         
        hl7Def.IsEnabled = true;
        insert(hl7Def);
    }

    /**
    * 
    */
    public static long insert(HL7Def hL7Def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            hL7Def.HL7DefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), hL7Def);
            return hL7Def.HL7DefNum;
        }
         
        return Crud.HL7DefCrud.Insert(hL7Def);
    }

    /**
    * 
    */
    public static void update(HL7Def hL7Def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), hL7Def);
            return ;
        }
         
        Crud.HL7DefCrud.Update(hL7Def);
    }

    /**
    * 
    */
    public static void delete(long hL7DefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), hL7DefNum);
            return ;
        }
         
        String command = "DELETE FROM hl7def WHERE HL7DefNum = " + POut.long(hL7DefNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<HL7Def> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<HL7Def>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM hl7def WHERE PatNum = "+POut.Long(patNum);
			return Crud.HL7DefCrud.SelectMany(command);
		}
		///<summary>Gets one HL7Def from the db.</summary>
		public static HL7Def GetOne(long hL7DefNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<HL7Def>(MethodBase.GetCurrentMethod(),hL7DefNum);
			}
			return Crud.HL7DefCrud.SelectOne(hL7DefNum);
		}
		
		
		*/