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
public class ScreenGroups   
{
    /**
    * 
    */
    public static List<ScreenGroup> refresh(DateTime fromDate, DateTime toDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ScreenGroup>>GetObject(MethodBase.GetCurrentMethod(), fromDate, toDate);
        }
         
        String command = "SELECT * from screengroup " + "WHERE SGDate >= " + POut.dateT(fromDate) + " " + "AND SGDate < " + POut.DateT(toDate.AddDays(1)) + " " + "ORDER BY SGDate,ScreenGroupNum";
        return Crud.ScreenGroupCrud.SelectMany(command);
    }

    //Was including entries form the next day. Changed from <= to <.
    //added one day since it's calculated based on midnight.
    public static ScreenGroup getScreenGroup(long screenGroupNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ScreenGroup>GetObject(MethodBase.GetCurrentMethod(), screenGroupNum);
        }
         
        String command = "SELECT * FROM screengroup WHERE ScreenGroupNum=" + POut.long(screenGroupNum);
        return Crud.ScreenGroupCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static long insert(ScreenGroup Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.ScreenGroupNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.ScreenGroupNum;
        }
         
        return Crud.ScreenGroupCrud.Insert(Cur);
    }

    /**
    * 
    */
    public static void update(ScreenGroup Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.ScreenGroupCrud.Update(Cur);
    }

    /**
    * This will also delete all screen items, so may need to ask user first.
    */
    public static void delete(ScreenGroup Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from screen WHERE ScreenGroupNum ='" + POut.long(Cur.ScreenGroupNum) + "'";
        Db.nonQ(command);
        command = "DELETE from screengroup WHERE ScreenGroupNum ='" + POut.long(Cur.ScreenGroupNum) + "'";
        Db.nonQ(command);
    }

}


