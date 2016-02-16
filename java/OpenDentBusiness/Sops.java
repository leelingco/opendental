//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.Meth;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Sop;

/**
* 
*/
public class Sops   
{
    /**
    * A list of all Sops.
    */
    private static List<Sop> listt = new List<Sop>();
    /**
    * A list of all Sops.
    */
    public static List<Sop> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<Sop> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM sop";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Sop";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.SopCrud.TableToList(table);
    }

    /**
    * 
    */
    public static long insert(Sop sop) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            sop.SopNum = Meth.GetLong(MethodBase.GetCurrentMethod(), sop);
            return sop.SopNum;
        }
         
        return Crud.SopCrud.Insert(sop);
    }

    /**
    * Returns one SopNum. If SopCode not found returns 0.
    */
    public static long getOneNum(String SopCode) throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            if (StringSupport.equals(getListt()[i].SopCode, SopCode))
            {
                return i;
            }
             
        }
        return 0;
    }

    /**
    * Returns a list of just the codes for use in update or insert logic.
    */
    public static List<String> getAllCodes() throws Exception {
        List<String> retVal = new List<String>();
        for (int i = 0;i < getListt().Count;i++)
        {
            retVal.Add(getListt()[i].SopCode);
        }
        return retVal;
    }

    /**
    * Returns an Sop description based on the given payor type info.
    */
    public static String getDescriptionFromCode(String sopCode) throws Exception {
        String desc = "";
        for (int i = 0;i < getListt().Count;i++)
        {
            if (StringSupport.equals(sopCode, getListt()[i].SopCode))
            {
                desc = getListt()[i].Description;
            }
             
        }
        return desc;
    }

    /**
    * Returns one code for use in update or insert logic.
    */
    public static String getOneCode(long SopNum) throws Exception {
        int i = (int)SopNum;
        return getListt()[i].SopCode;
    }

    /**
    * Returns a list of just the descriptions for use in update or insert logic.
    */
    public static List<String> getAllDescriptions() throws Exception {
        List<String> retVal = new List<String>();
        for (int i = 0;i < getListt().Count;i++)
        {
            retVal[i] = getListt()[i].Description;
        }
        return retVal;
    }

    /**
    * Returns the description for the specified SopCode.  Returns an empty string if no code is found.
    */
    public static String getOneDescription(String SopCode) throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            if (StringSupport.equals(getListt()[i].SopCode, SopCode))
            {
                return getListt()[i].Description;
            }
             
        }
        return "";
    }

    /**
    * 
    */
    public static void truncateAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "TRUNCATE TABLE sop";
        //Oracle compatible
        DataCore.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Sop> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<Sop>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM sop WHERE PatNum = "+POut.Long(patNum);
			return Crud.SopCrud.SelectMany(command);
		}
		///<summary>Gets one Sop from the db.</summary>
		public static Sop GetOne(long sopNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<Sop>(MethodBase.GetCurrentMethod(),sopNum);
			}
			return Crud.SopCrud.SelectOne(sopNum);
		}
		///<summary></summary>
		public static void Update(Sop sop){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),sop);
				return;
			}
			Crud.SopCrud.Update(sop);
		}
		///<summary></summary>
		public static void Delete(long sopNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),sopNum);
				return;
			}
			string command= "DELETE FROM sop WHERE SopNum = "+POut.Long(sopNum);
			Db.NonQ(command);
		}
		*/