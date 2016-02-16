//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrMeasureEvents   
{
    /**
    * Ordered by dateT
    */
    public static List<EhrMeasureEvent> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrMeasureEvent>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM ehrmeasureevent WHERE PatNum = " + POut.long(patNum) + " " + "ORDER BY DateTEvent";
        return Crud.EhrMeasureEventCrud.SelectMany(command);
    }

    /**
    * Ordered by dateT
    */
    public static List<EhrMeasureEvent> refreshByType(long patNum, EhrMeasureEventType... ehrMeasureEventTypes) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrMeasureEvent>>GetObject(MethodBase.GetCurrentMethod(), patNum, ehrMeasureEventTypes);
        }
         
        String command = "SELECT * FROM ehrmeasureevent WHERE (";
        for (int i = 0;i < ehrMeasureEventTypes.Length;i++)
        {
            if (i > 0)
            {
                command += "OR ";
            }
             
            command += "EventType = " + POut.int((int)ehrMeasureEventTypes[i]) + " ";
        }
        command += ") AND PatNum = " + POut.long(patNum) + " " + "ORDER BY DateTEvent";
        return Crud.EhrMeasureEventCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(EhrMeasureEvent ehrMeasureEvent) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrMeasureEvent.EhrMeasureEventNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrMeasureEvent);
            return ehrMeasureEvent.EhrMeasureEventNum;
        }
         
        return Crud.EhrMeasureEventCrud.Insert(ehrMeasureEvent);
    }

    /**
    * 
    */
    public static void update(EhrMeasureEvent ehrMeasureEvent) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrMeasureEvent);
            return ;
        }
         
        Crud.EhrMeasureEventCrud.Update(ehrMeasureEvent);
    }

    /**
    * 
    */
    public static void delete(long ehrMeasureEventNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrMeasureEventNum);
            return ;
        }
         
        String command = "DELETE FROM ehrmeasureevent WHERE EhrMeasureEventNum = " + POut.long(ehrMeasureEventNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static List<EhrMeasureEvent> getByType(List<EhrMeasureEvent> listMeasures, EhrMeasureEventType eventType) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<EhrMeasureEvent> retVal = new List<EhrMeasureEvent>();
        for (int i = 0;i < listMeasures.Count;i++)
        {
            if (listMeasures[i].EventType == eventType)
            {
                retVal.Add(listMeasures[i]);
            }
             
        }
        return retVal;
    }

}


/*
		
		///<summary>Gets one EhrMeasureEvent from the db.</summary>
		public static EhrMeasureEvent GetOne(long ehrMeasureEventNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrMeasureEvent>(MethodBase.GetCurrentMethod(),ehrMeasureEventNum);
			}
			return Crud.EhrMeasureEventCrud.SelectOne(ehrMeasureEventNum);
		}
		
		*/