//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Computer;
import OpenDentBusiness.Computers;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.Printer;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Handles all the business logic for printers.  Used heavily by the UI.  Every single function that makes changes to the database must be completely autonomous and do ALL validation itself.
*/
public class Printers   
{
    /**
    * List of all printers.  Because of cache refresh, this gets properly refreshed on both ends.
    */
    private static Printer[] list = new Printer[]();
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM printer";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Printer";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.PrinterCrud.TableToList(table).ToArray();
    }

    /**
    * Gets directly from database
    */
    public static Printer getOnePrinter(PrintSituation sit, long compNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Printer>GetObject(MethodBase.GetCurrentMethod(), sit, compNum);
        }
         
        String command = "SELECT * FROM printer WHERE " + "PrintSit = '" + POut.Long(((Enum)sit).ordinal()) + "' " + "AND ComputerNum ='" + POut.long(compNum) + "'";
        return Crud.PrinterCrud.SelectOne(command);
    }

    /**
    * 
    */
    private static long insert(Printer cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            cur.PrinterNum = Meth.GetLong(MethodBase.GetCurrentMethod(), cur);
            return cur.PrinterNum;
        }
         
        return Crud.PrinterCrud.Insert(cur);
    }

    /**
    * 
    */
    private static void update(Printer cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cur);
            return ;
        }
         
        Crud.PrinterCrud.Update(cur);
    }

    /**
    * 
    */
    private static void delete(Printer cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cur);
            return ;
        }
         
        String command = "DELETE FROM printer " + "WHERE PrinterNum = " + POut.long(cur.PrinterNum);
        Db.nonQ(command);
    }

    public static boolean printerIsInstalled(String name) throws Exception {
        for (int i = 0;i < PrinterSettings.InstalledPrinters.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(PrinterSettings.InstalledPrinters[i], name))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Gets the set printer whether or not it is valid.
    */
    public static Printer getForSit(PrintSituation sit) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (list == null)
        {
            refreshCache();
        }
         
        Computer compCur = Computers.getCur();
        for (int i = 0;i < list.Length;i++)
        {
            if (list[i].ComputerNum == compCur.ComputerNum && list[i].PrintSit == sit)
            {
                return list[i];
            }
             
        }
        return null;
    }

    /**
    * Either does an insert or an update to the database if need to create a Printer object.  Or it also deletes a printer object if needed.
    */
    public static void putForSit(PrintSituation sit, String computerName, String printerName, boolean displayPrompt) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sit, computerName, printerName, displayPrompt);
            return ;
        }
         
        //Computer[] compList=Computers.Refresh();
        //Computer compCur=Computers.GetCur();
        String command = "SELECT ComputerNum FROM computer " + "WHERE CompName = '" + POut.string(computerName) + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return ;
        }
         
        //computer not yet entered in db.
        long compNum = PIn.Long(table.Rows[0][0].ToString());
        //only called from PrinterSetup window. Get info directly from db, then refresh when closing window.
        Printer existing = getOnePrinter(sit,compNum);
        //GetForSit(sit);
        if (StringSupport.equals(printerName, "") && !displayPrompt)
        {
            //then should not be an entry in db
            if (existing != null)
            {
                //need to delete Printer
                delete(existing);
            }
             
        }
        else if (existing == null)
        {
            Printer cur = new Printer();
            cur.ComputerNum = compNum;
            cur.PrintSit = sit;
            cur.PrinterName = printerName;
            cur.DisplayPrompt = displayPrompt;
            insert(cur);
        }
        else
        {
            existing.PrinterName = printerName;
            existing.DisplayPrompt = displayPrompt;
            update(existing);
        }  
    }

    /**
    * Called from FormPrinterSetup if user selects the easy option.  Since the other options will be hidden, we have to clear them.  User should be sternly warned before this happens.
    */
    public static void clearAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        //first, delete all entries
        String command = "DELETE FROM printer";
        Db.nonQ(command);
        //then, add one printer for each computer. Default and show prompt
        Computers.refreshCache();
        Printer cur;
        for (int i = 0;i < Computers.getList().Length;i++)
        {
            cur = new Printer();
            cur.ComputerNum = Computers.getList()[i].ComputerNum;
            cur.PrintSit = PrintSituation.Default;
            cur.PrinterName = "";
            cur.DisplayPrompt = true;
            insert(cur);
        }
    }

}


