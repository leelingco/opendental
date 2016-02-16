//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.TerminalActive;
import OpenDentBusiness.TerminalStatusEnum;

/**
* 
*/
public class TerminalActives   
{
    /**
    * Gets a list of all TerminalActives.  Used by the terminal monitor window and by the terminal window itself.  Data is retrieved at regular short intervals, so functions as a messaging system.
    */
    public static TerminalActive[] refresh() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<TerminalActive[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM terminalactive ORDER BY ComputerName";
        return Crud.TerminalActiveCrud.SelectMany(command).ToArray();
    }

    /**
    * 
    */
    public static TerminalActive getTerminal(String computerName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<TerminalActive>GetObject(MethodBase.GetCurrentMethod(), computerName);
        }
         
        String command = "SELECT * FROM terminalactive WHERE ComputerName ='" + POut.string(computerName) + "'";
        return Crud.TerminalActiveCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static void update(TerminalActive te) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), te);
            return ;
        }
         
        Crud.TerminalActiveCrud.Update(te);
    }

    /**
    * 
    */
    public static long insert(TerminalActive te) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            te.TerminalActiveNum = Meth.GetLong(MethodBase.GetCurrentMethod(), te);
            return te.TerminalActiveNum;
        }
         
        return Crud.TerminalActiveCrud.Insert(te);
    }

    /**
    * 
    */
    public static void delete(TerminalActive te) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), te);
            return ;
        }
         
        String command = "DELETE FROM terminalactive WHERE TerminalActiveNum =" + POut.long(te.TerminalActiveNum);
        Db.nonQ(command);
    }

    /**
    * Run when starting up a terminal window to delete any previous entries for this computer that didn't get deleted properly.
    */
    public static void deleteAllForComputer(String computerName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), computerName);
            return ;
        }
         
        String command = "DELETE FROM terminalactive WHERE ComputerName ='" + POut.string(computerName) + "'";
        Db.nonQ(command);
    }

    /**
    * Called whenever user wants to edit patient info.  Not allowed to if patient edit window is open at a terminal.  Once patient is done at terminal, then staff allowed back into patient edit window.
    */
    public static boolean patIsInUse(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT COUNT(*) FROM terminalactive WHERE PatNum=" + POut.long(patNum) + " AND (TerminalStatus=" + POut.Long(((Enum)TerminalStatusEnum.PatientInfo).ordinal()) + " OR TerminalStatus=" + POut.Long(((Enum)TerminalStatusEnum.UpdateOnly).ordinal()) + ")";
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

}


