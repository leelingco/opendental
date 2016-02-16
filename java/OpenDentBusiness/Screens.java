//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ScreenGroup;

/**
* 
*/
public class Screens   
{
    /**
    * 
    */
    public static OpenDentBusiness.Screen[] refresh(long screenGroupNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<OpenDentBusiness.Screen[]>GetObject(MethodBase.GetCurrentMethod(), screenGroupNum);
        }
         
        String command = "SELECT * from screen " + "WHERE ScreenGroupNum = '" + POut.long(screenGroupNum) + "' " + "ORDER BY ScreenGroupOrder";
        return Crud.ScreenCrud.SelectMany(command).ToArray();
    }

    /**
    * 
    */
    public static long insert(OpenDentBusiness.Screen Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.ScreenNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.ScreenNum;
        }
         
        return Crud.ScreenCrud.Insert(Cur);
    }

    /**
    * 
    */
    public static void update(OpenDentBusiness.Screen Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.ScreenCrud.Update(Cur);
    }

    /**
    * Updates all screens for a group with the date,prov, and location info of the current group.
    */
    public static void updateForGroup(ScreenGroup ScreenGroupCur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ScreenGroupCur);
            return ;
        }
         
        String command = "UPDATE screen SET " + "ScreenDate     =" + POut.date(ScreenGroupCur.SGDate) + ",GradeSchool ='" + POut.string(ScreenGroupCur.GradeSchool) + "'" + ",County ='" + POut.string(ScreenGroupCur.County) + "'" + ",PlaceService ='" + POut.Long(((Enum)ScreenGroupCur.PlaceService).ordinal()) + "'" + ",ProvNum ='" + POut.long(ScreenGroupCur.ProvNum) + "'" + ",ProvName ='" + POut.string(ScreenGroupCur.ProvName) + "'" + " WHERE ScreenGroupNum = '" + ScreenGroupCur.ScreenGroupNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void delete(OpenDentBusiness.Screen Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from screen WHERE ScreenNum = '" + POut.long(Cur.ScreenNum) + "'";
        Db.nonQ(command);
    }

}


