//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.AutoNoteControl;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class AutoNoteControls   
{
    /**
    * A list of all the Prompts.  Caching could be handled better for fewer refreshes.
    */
    public static List<AutoNoteControl> Listt = new List<AutoNoteControl>();
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM autonotecontrol ORDER BY Descript";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "AutoNoteControl";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        Listt = Crud.AutoNoteControlCrud.TableToList(table);
    }

    public static long insert(AutoNoteControl autoNoteControl) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            autoNoteControl.AutoNoteControlNum = Meth.GetLong(MethodBase.GetCurrentMethod(), autoNoteControl);
            return autoNoteControl.AutoNoteControlNum;
        }
         
        return Crud.AutoNoteControlCrud.Insert(autoNoteControl);
    }

    public static void update(AutoNoteControl autoNoteControl) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), autoNoteControl);
            return ;
        }
         
        Crud.AutoNoteControlCrud.Update(autoNoteControl);
    }

    public static void delete(long autoNoteControlNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), autoNoteControlNum);
            return ;
        }
         
        //no validation for now.
        String command = "DELETE FROM autonotecontrol WHERE AutoNoteControlNum=" + POut.long(autoNoteControlNum);
        Db.nonQ(command);
    }

    /**
    * Will return null if can't match.
    */
    public static AutoNoteControl getByDescript(String descript) throws Exception {
        for (int i = 0;i < Listt.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(Listt[i].Descript, descript))
            {
                return Listt[i];
            }
             
        }
        return null;
    }

}


