//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import CodeBase.Logger;
import CodeBase.Logger.Severity;
import OpenDentBusiness.ComputerPref;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class ComputerPrefs   
{
    /**
    * Only used by the client.
    */
    private static ComputerPref localComputer;
    public static ComputerPref getLocalComputer() throws Exception {
        if (localComputer == null)
        {
            localComputer = getForLocalComputer();
        }
         
        return localComputer;
    }

    //set {
    //I don't think this gets used.
    //}
    /**
    * Returns the computer preferences for the computer which this instance of Open Dental is running on.
    */
    private static ComputerPref getForLocalComputer() throws Exception {
        //No need to check RemotingRole; no call to db.
        String computerName = Dns.GetHostName();
        //local computer name
        ComputerPref computerPref = new ComputerPref();
        //OpenGL tooth chart not supported on Unix systems.
        if (Environment.OSVersion.Platform == PlatformID.Unix)
        {
            computerPref.GraphicsSimple = OpenDentBusiness.DrawingMode.Simple2D;
        }
         
        //Default sensor values to start
        computerPref.SensorType = "D";
        computerPref.SensorPort = 0;
        computerPref.SensorExposure = 1;
        computerPref.SensorBinned = false;
        //Default values to start
        computerPref.AtoZpath = "";
        computerPref.TaskKeepListHidden = false;
        //show docked task list on this computer
        computerPref.TaskDock = 0;
        //bottom
        computerPref.TaskX = 900;
        computerPref.TaskY = 625;
        computerPref.ComputerName = computerName;
        computerPref.DirectXFormat = "";
        computerPref.GraphicsSimple = OpenDentBusiness.DrawingMode.DirectX;
        DataTable table = getPrefsForComputer(computerName);
        if (table == null)
        {
            return computerPref;
        }
         
        //In case of database error, just use default graphics settings so that it is possible for the program to start.
        if (table.Rows.Count == 0)
        {
            //Computer pref row does not yet exist for this computer.
            insert(computerPref);
            return computerPref;
        }
         
        //Create default prefs for the specified computer. Also sets primary key in our computerPref object.
        if (table.Rows.Count > 1)
        {
            //Should never happen (would only happen if the primary key showed up more than once).
            Logger.openlog.logMB("Corrupt computerpref table in database. The computer name '" + POut.string(computerName) + "' is a primary key in multiple records. Please run the " + "database maintenance tool, then call us for help if you still get this message.",Severity.WARNING);
        }
         
        computerPref = Crud.ComputerPrefCrud.TableToList(table)[0];
        return computerPref;
    }

    /**
    * Should not be called by external classes.
    */
    public static DataTable getPrefsForComputer(String computerName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), computerName);
        }
         
        String command = "SELECT * FROM computerpref WHERE ComputerName='" + POut.string(computerName) + "'";
        try
        {
            return Db.getTable(command);
        }
        catch (Exception __dummyCatchVar0)
        {
            return null;
        }
    
    }

    /**
    * Should not be called by external classes.
    */
    public static long insert(ComputerPref computerPref) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            computerPref.ComputerPrefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), computerPref);
            return computerPref.ComputerPrefNum;
        }
         
        return Crud.ComputerPrefCrud.Insert(computerPref);
    }

    /**
    * Any time this is called, ComputerPrefs.LocalComputer will be passed in.  It will have already been changed for local use, and this saves it for next time.
    */
    public static void update(ComputerPref computerPref) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), computerPref);
            return ;
        }
         
        Crud.ComputerPrefCrud.Update(computerPref);
    }

    /**
    * Sets the GraphicsSimple column to 1.  Added to fix machines (lately tablets) that are having graphics problems and cannot start OpenDental.
    */
    public static void setToSimpleGraphics(String computerName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), computerName);
            return ;
        }
         
        String command = "UPDATE computerpref SET GraphicsSimple=1 WHERE ComputerName='" + POut.string(computerName) + "'";
        Db.nonQ(command);
    }

}


