//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.HL7DefField;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class HL7DefFields   
{
    /**
    * A list of all HL7DefFields.
    */
    private static List<HL7DefField> listt = new List<HL7DefField>();
    /**
    * A list of all HL7DefFields.
    */
    public static List<HL7DefField> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<HL7DefField> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM hl7deffield ORDER BY OrdinalPos";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "HL7DefField";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.HL7DefFieldCrud.TableToList(table);
    }

    /**
    * Gets it straight from the database instead of from cache.
    */
    public static List<HL7DefField> getFromDb(long hl7DefSegmentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<HL7DefField>>GetObject(MethodBase.GetCurrentMethod(), hl7DefSegmentNum);
        }
         
        String command = "SELECT * FROM hl7deffield WHERE HL7DefSegmentNum='" + POut.long(hl7DefSegmentNum) + "' ORDER BY OrdinalPos";
        return Crud.HL7DefFieldCrud.SelectMany(command);
    }

    /**
    * Gets the field list from the cache.
    */
    public static List<HL7DefField> getFromCache(long hl7DefSegmentNum) throws Exception {
        List<HL7DefField> list = new List<HL7DefField>();
        for (int i = 0;i < getListt().Count;i++)
        {
            if (getListt()[i].HL7DefSegmentNum == hl7DefSegmentNum)
            {
                list.Add(getListt()[i]);
            }
             
        }
        return list;
    }

    /**
    * 
    */
    public static long insert(HL7DefField hL7DefField) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            hL7DefField.HL7DefFieldNum = Meth.GetLong(MethodBase.GetCurrentMethod(), hL7DefField);
            return hL7DefField.HL7DefFieldNum;
        }
         
        return Crud.HL7DefFieldCrud.Insert(hL7DefField);
    }

    /**
    * 
    */
    public static void update(HL7DefField hL7DefField) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), hL7DefField);
            return ;
        }
         
        Crud.HL7DefFieldCrud.Update(hL7DefField);
    }

    /**
    * 
    */
    public static void delete(long hL7DefFieldNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), hL7DefFieldNum);
            return ;
        }
         
        String command = "DELETE FROM hl7deffield WHERE HL7DefFieldNum = " + POut.long(hL7DefFieldNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<HL7DefField> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<HL7DefField>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM hl7deffield WHERE PatNum = "+POut.Long(patNum);
			return Crud.HL7DefFieldCrud.SelectMany(command);
		}
		///<summary>Gets one HL7DefField from the db.</summary>
		public static HL7DefField GetOne(long hL7DefFieldNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<HL7DefField>(MethodBase.GetCurrentMethod(),hL7DefFieldNum);
			}
			return Crud.HL7DefFieldCrud.SelectOne(hL7DefFieldNum);
		}
		*/