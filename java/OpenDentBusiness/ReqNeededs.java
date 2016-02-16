//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReqNeeded;

/**
* 
*/
public class ReqNeededs   
{
    public static DataTable refresh(long schoolClass, long schoolCourse) throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM reqneeded WHERE SchoolClassNum=" + POut.long(schoolClass) + " AND SchoolCourseNum=" + POut.long(schoolCourse) + " ORDER BY Descript";
        return Db.getTable(command);
    }

    public static ReqNeeded getReq(long reqNeededNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ReqNeeded>GetObject(MethodBase.GetCurrentMethod(), reqNeededNum);
        }
         
        String command = "SELECT * FROM reqneeded WHERE ReqNeededNum=" + POut.long(reqNeededNum);
        return Crud.ReqNeededCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static void update(ReqNeeded req) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), req);
            return ;
        }
         
        Crud.ReqNeededCrud.Update(req);
    }

    /**
    * 
    */
    public static long insert(ReqNeeded req) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            req.ReqNeededNum = Meth.GetLong(MethodBase.GetCurrentMethod(), req);
            return req.ReqNeededNum;
        }
         
        return Crud.ReqNeededCrud.Insert(req);
    }

    /**
    * Surround with try/catch.
    */
    public static void delete(long reqNeededNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), reqNeededNum);
            return ;
        }
         
        //still need to validate
        String command = "DELETE FROM reqneeded " + "WHERE ReqNeededNum = " + POut.long(reqNeededNum);
        Db.nonQ(command);
    }

}


