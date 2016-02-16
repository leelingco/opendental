//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.HL7DefFields;
import OpenDentBusiness.HL7DefSegment;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class HL7DefSegments   
{
    /**
    * A list of all HL7DefSegments.
    */
    private static List<HL7DefSegment> listt = new List<HL7DefSegment>();
    /**
    * A list of all HL7DefSegments.
    */
    public static List<HL7DefSegment> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<HL7DefSegment> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM hl7defsegment ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "HL7DefSegment";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.HL7DefSegmentCrud.TableToList(table);
    }

    /**
    * Gets it straight from the database instead of from cache. No child objects included.
    */
    public static List<HL7DefSegment> getShallowFromDb(long hl7DefMessageNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<HL7DefSegment>>GetObject(MethodBase.GetCurrentMethod(), hl7DefMessageNum);
        }
         
        String command = "SELECT * FROM hl7defsegment WHERE HL7DefMessageNum='" + POut.long(hl7DefMessageNum) + "' ORDER BY ItemOrder";
        return Crud.HL7DefSegmentCrud.SelectMany(command);
    }

    /**
    * Gets deep list from cache.
    */
    public static List<HL7DefSegment> getDeepFromCache(long hl7DefMessageNum) throws Exception {
        List<HL7DefSegment> list = new List<HL7DefSegment>();
        for (int i = 0;i < getListt().Count;i++)
        {
            if (getListt()[i].HL7DefMessageNum == hl7DefMessageNum)
            {
                list.Add(getListt()[i]);
                list[list.Count - 1].hl7DefFields = HL7DefFields.GetFromCache(getListt()[i].HL7DefSegmentNum);
            }
             
        }
        return list;
    }

    /**
    * Gets a full deep list of all Segments for this message from the database.
    */
    public static List<HL7DefSegment> getDeepFromDb(long hl7DefMessageNum) throws Exception {
        List<HL7DefSegment> hl7defsegs = new List<HL7DefSegment>();
        hl7defsegs = getShallowFromDb(hl7DefMessageNum);
        for (Object __dummyForeachVar0 : hl7defsegs)
        {
            HL7DefSegment s = (HL7DefSegment)__dummyForeachVar0;
            s.hl7DefFields = HL7DefFields.getFromDb(s.HL7DefSegmentNum);
        }
        return hl7defsegs;
    }

    /**
    * 
    */
    public static long insert(HL7DefSegment hL7DefSegment) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            hL7DefSegment.HL7DefSegmentNum = Meth.GetLong(MethodBase.GetCurrentMethod(), hL7DefSegment);
            return hL7DefSegment.HL7DefSegmentNum;
        }
         
        return Crud.HL7DefSegmentCrud.Insert(hL7DefSegment);
    }

    /**
    * 
    */
    public static void update(HL7DefSegment hL7DefSegment) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), hL7DefSegment);
            return ;
        }
         
        Crud.HL7DefSegmentCrud.Update(hL7DefSegment);
    }

    /**
    * 
    */
    public static void delete(long hL7DefSegmentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), hL7DefSegmentNum);
            return ;
        }
         
        String command = "DELETE FROM hl7defsegment WHERE HL7DefSegmentNum = " + POut.long(hL7DefSegmentNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<HL7DefSegment> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<HL7DefSegment>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM hl7defsegment WHERE PatNum = "+POut.Long(patNum);
			return Crud.HL7DefSegmentCrud.SelectMany(command);
		}
		///<summary>Gets one HL7DefSegment from the db.</summary>
		public static HL7DefSegment GetOne(long hL7DefSegmentNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<HL7DefSegment>(MethodBase.GetCurrentMethod(),hL7DefSegmentNum);
			}
			return Crud.HL7DefSegmentCrud.SelectOne(hL7DefSegmentNum);
		}
		*/