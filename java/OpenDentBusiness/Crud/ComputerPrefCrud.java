//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ComputerPref;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class ComputerPrefCrud   
{
    /**
    * Gets one ComputerPref object from the database using the primary key.  Returns null if not found.
    */
    public static ComputerPref selectOne(long computerPrefNum) throws Exception {
        String command = "SELECT * FROM computerpref " + "WHERE ComputerPrefNum = " + POut.long(computerPrefNum);
        List<ComputerPref> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one ComputerPref object from the database using a query.
    */
    public static ComputerPref selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ComputerPref> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of ComputerPref objects from the database using a query.
    */
    public static List<ComputerPref> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ComputerPref> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ComputerPref> tableToList(DataTable table) throws Exception {
        List<ComputerPref> retVal = new List<ComputerPref>();
        ComputerPref computerPref;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            computerPref = new ComputerPref();
            computerPref.ComputerPrefNum = PIn.Long(table.Rows[i]["ComputerPrefNum"].ToString());
            computerPref.ComputerName = PIn.String(table.Rows[i]["ComputerName"].ToString());
            computerPref.GraphicsUseHardware = PIn.Bool(table.Rows[i]["GraphicsUseHardware"].ToString());
            computerPref.GraphicsSimple = (OpenDentBusiness.DrawingMode)PIn.Int(table.Rows[i]["GraphicsSimple"].ToString());
            computerPref.SensorType = PIn.String(table.Rows[i]["SensorType"].ToString());
            computerPref.SensorBinned = PIn.Bool(table.Rows[i]["SensorBinned"].ToString());
            computerPref.SensorPort = PIn.Int(table.Rows[i]["SensorPort"].ToString());
            computerPref.SensorExposure = PIn.Int(table.Rows[i]["SensorExposure"].ToString());
            computerPref.GraphicsDoubleBuffering = PIn.Bool(table.Rows[i]["GraphicsDoubleBuffering"].ToString());
            computerPref.PreferredPixelFormatNum = PIn.Int(table.Rows[i]["PreferredPixelFormatNum"].ToString());
            computerPref.AtoZpath = PIn.String(table.Rows[i]["AtoZpath"].ToString());
            computerPref.TaskKeepListHidden = PIn.Bool(table.Rows[i]["TaskKeepListHidden"].ToString());
            computerPref.TaskDock = PIn.Int(table.Rows[i]["TaskDock"].ToString());
            computerPref.TaskX = PIn.Int(table.Rows[i]["TaskX"].ToString());
            computerPref.TaskY = PIn.Int(table.Rows[i]["TaskY"].ToString());
            computerPref.DirectXFormat = PIn.String(table.Rows[i]["DirectXFormat"].ToString());
            computerPref.RecentApptView = PIn.Byte(table.Rows[i]["RecentApptView"].ToString());
            computerPref.ScanDocSelectSource = PIn.Bool(table.Rows[i]["ScanDocSelectSource"].ToString());
            computerPref.ScanDocShowOptions = PIn.Bool(table.Rows[i]["ScanDocShowOptions"].ToString());
            computerPref.ScanDocDuplex = PIn.Bool(table.Rows[i]["ScanDocDuplex"].ToString());
            computerPref.ScanDocGrayscale = PIn.Bool(table.Rows[i]["ScanDocGrayscale"].ToString());
            computerPref.ScanDocResolution = PIn.Int(table.Rows[i]["ScanDocResolution"].ToString());
            computerPref.ScanDocQuality = PIn.Byte(table.Rows[i]["ScanDocQuality"].ToString());
            retVal.Add(computerPref);
        }
        return retVal;
    }

    /**
    * Inserts one ComputerPref into the database.  Returns the new priKey.
    */
    public static long insert(ComputerPref computerPref) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            computerPref.ComputerPrefNum = DbHelper.getNextOracleKey("computerpref","ComputerPrefNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(computerPref, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        computerPref.ComputerPrefNum++;
                        loopcount++;
                    }
                    else
                    {
                        throw ex;
                    } 
                }
            
            }
            throw new ApplicationException("Insert failed.  Could not generate primary key.");
        }
        else
        {
            return Insert(computerPref, false);
        } 
    }

    /**
    * Inserts one ComputerPref into the database.  Provides option to use the existing priKey.
    */
    public static long insert(ComputerPref computerPref, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            computerPref.ComputerPrefNum = ReplicationServers.getKey("computerpref","ComputerPrefNum");
        }
         
        String command = "INSERT INTO computerpref (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "ComputerPrefNum,";
        }
         
        command += "ComputerName,GraphicsUseHardware,GraphicsSimple,SensorType,SensorBinned,SensorPort,SensorExposure,GraphicsDoubleBuffering,PreferredPixelFormatNum,AtoZpath,TaskKeepListHidden,TaskDock,TaskX,TaskY,DirectXFormat,RecentApptView,ScanDocSelectSource,ScanDocShowOptions,ScanDocDuplex,ScanDocGrayscale,ScanDocResolution,ScanDocQuality) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(computerPref.ComputerPrefNum) + ",";
        }
         
        command += "'" + POut.string(computerPref.ComputerName) + "'," + POut.bool(computerPref.GraphicsUseHardware) + "," + POut.int(((Enum)computerPref.GraphicsSimple).ordinal()) + "," + "'" + POut.string(computerPref.SensorType) + "'," + POut.bool(computerPref.SensorBinned) + "," + POut.int(computerPref.SensorPort) + "," + POut.int(computerPref.SensorExposure) + "," + POut.bool(computerPref.GraphicsDoubleBuffering) + "," + POut.int(computerPref.PreferredPixelFormatNum) + "," + "'" + POut.string(computerPref.AtoZpath) + "'," + POut.bool(computerPref.TaskKeepListHidden) + "," + POut.int(computerPref.TaskDock) + "," + POut.int(computerPref.TaskX) + "," + POut.int(computerPref.TaskY) + "," + "'" + POut.string(computerPref.DirectXFormat) + "'," + POut.byte(computerPref.RecentApptView) + "," + POut.bool(computerPref.ScanDocSelectSource) + "," + POut.bool(computerPref.ScanDocShowOptions) + "," + POut.bool(computerPref.ScanDocDuplex) + "," + POut.bool(computerPref.ScanDocGrayscale) + "," + POut.int(computerPref.ScanDocResolution) + "," + POut.byte(computerPref.ScanDocQuality) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            computerPref.ComputerPrefNum = Db.nonQ(command,true);
        } 
        return computerPref.ComputerPrefNum;
    }

    /**
    * Updates one ComputerPref in the database.
    */
    public static void update(ComputerPref computerPref) throws Exception {
        String command = "UPDATE computerpref SET " + "ComputerName           = '" + POut.string(computerPref.ComputerName) + "', " + "GraphicsUseHardware    =  " + POut.bool(computerPref.GraphicsUseHardware) + ", " + "GraphicsSimple         =  " + POut.int(((Enum)computerPref.GraphicsSimple).ordinal()) + ", " + "SensorType             = '" + POut.string(computerPref.SensorType) + "', " + "SensorBinned           =  " + POut.bool(computerPref.SensorBinned) + ", " + "SensorPort             =  " + POut.int(computerPref.SensorPort) + ", " + "SensorExposure         =  " + POut.int(computerPref.SensorExposure) + ", " + "GraphicsDoubleBuffering=  " + POut.bool(computerPref.GraphicsDoubleBuffering) + ", " + "PreferredPixelFormatNum=  " + POut.int(computerPref.PreferredPixelFormatNum) + ", " + "AtoZpath               = '" + POut.string(computerPref.AtoZpath) + "', " + "TaskKeepListHidden     =  " + POut.bool(computerPref.TaskKeepListHidden) + ", " + "TaskDock               =  " + POut.int(computerPref.TaskDock) + ", " + "TaskX                  =  " + POut.int(computerPref.TaskX) + ", " + "TaskY                  =  " + POut.int(computerPref.TaskY) + ", " + "DirectXFormat          = '" + POut.string(computerPref.DirectXFormat) + "', " + "RecentApptView         =  " + POut.byte(computerPref.RecentApptView) + ", " + "ScanDocSelectSource    =  " + POut.bool(computerPref.ScanDocSelectSource) + ", " + "ScanDocShowOptions     =  " + POut.bool(computerPref.ScanDocShowOptions) + ", " + "ScanDocDuplex          =  " + POut.bool(computerPref.ScanDocDuplex) + ", " + "ScanDocGrayscale       =  " + POut.bool(computerPref.ScanDocGrayscale) + ", " + "ScanDocResolution      =  " + POut.int(computerPref.ScanDocResolution) + ", " + "ScanDocQuality         =  " + POut.byte(computerPref.ScanDocQuality) + " " + "WHERE ComputerPrefNum = " + POut.long(computerPref.ComputerPrefNum);
        Db.nonQ(command);
    }

    /**
    * Updates one ComputerPref in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(ComputerPref computerPref, ComputerPref oldComputerPref) throws Exception {
        String command = "";
        if (!StringSupport.equals(computerPref.ComputerName, oldComputerPref.ComputerName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ComputerName = '" + POut.string(computerPref.ComputerName) + "'";
        }
         
        if (computerPref.GraphicsUseHardware != oldComputerPref.GraphicsUseHardware)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "GraphicsUseHardware = " + POut.bool(computerPref.GraphicsUseHardware) + "";
        }
         
        if (computerPref.GraphicsSimple != oldComputerPref.GraphicsSimple)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "GraphicsSimple = " + POut.int(((Enum)computerPref.GraphicsSimple).ordinal()) + "";
        }
         
        if (!StringSupport.equals(computerPref.SensorType, oldComputerPref.SensorType))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SensorType = '" + POut.string(computerPref.SensorType) + "'";
        }
         
        if (computerPref.SensorBinned != oldComputerPref.SensorBinned)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SensorBinned = " + POut.bool(computerPref.SensorBinned) + "";
        }
         
        if (computerPref.SensorPort != oldComputerPref.SensorPort)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SensorPort = " + POut.int(computerPref.SensorPort) + "";
        }
         
        if (computerPref.SensorExposure != oldComputerPref.SensorExposure)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SensorExposure = " + POut.int(computerPref.SensorExposure) + "";
        }
         
        if (computerPref.GraphicsDoubleBuffering != oldComputerPref.GraphicsDoubleBuffering)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "GraphicsDoubleBuffering = " + POut.bool(computerPref.GraphicsDoubleBuffering) + "";
        }
         
        if (computerPref.PreferredPixelFormatNum != oldComputerPref.PreferredPixelFormatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PreferredPixelFormatNum = " + POut.int(computerPref.PreferredPixelFormatNum) + "";
        }
         
        if (!StringSupport.equals(computerPref.AtoZpath, oldComputerPref.AtoZpath))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AtoZpath = '" + POut.string(computerPref.AtoZpath) + "'";
        }
         
        if (computerPref.TaskKeepListHidden != oldComputerPref.TaskKeepListHidden)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TaskKeepListHidden = " + POut.bool(computerPref.TaskKeepListHidden) + "";
        }
         
        if (computerPref.TaskDock != oldComputerPref.TaskDock)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TaskDock = " + POut.int(computerPref.TaskDock) + "";
        }
         
        if (computerPref.TaskX != oldComputerPref.TaskX)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TaskX = " + POut.int(computerPref.TaskX) + "";
        }
         
        if (computerPref.TaskY != oldComputerPref.TaskY)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TaskY = " + POut.int(computerPref.TaskY) + "";
        }
         
        if (!StringSupport.equals(computerPref.DirectXFormat, oldComputerPref.DirectXFormat))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DirectXFormat = '" + POut.string(computerPref.DirectXFormat) + "'";
        }
         
        if (computerPref.RecentApptView != oldComputerPref.RecentApptView)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RecentApptView = " + POut.byte(computerPref.RecentApptView) + "";
        }
         
        if (computerPref.ScanDocSelectSource != oldComputerPref.ScanDocSelectSource)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocSelectSource = " + POut.bool(computerPref.ScanDocSelectSource) + "";
        }
         
        if (computerPref.ScanDocShowOptions != oldComputerPref.ScanDocShowOptions)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocShowOptions = " + POut.bool(computerPref.ScanDocShowOptions) + "";
        }
         
        if (computerPref.ScanDocDuplex != oldComputerPref.ScanDocDuplex)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocDuplex = " + POut.bool(computerPref.ScanDocDuplex) + "";
        }
         
        if (computerPref.ScanDocGrayscale != oldComputerPref.ScanDocGrayscale)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocGrayscale = " + POut.bool(computerPref.ScanDocGrayscale) + "";
        }
         
        if (computerPref.ScanDocResolution != oldComputerPref.ScanDocResolution)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocResolution = " + POut.int(computerPref.ScanDocResolution) + "";
        }
         
        if (computerPref.ScanDocQuality != oldComputerPref.ScanDocQuality)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocQuality = " + POut.byte(computerPref.ScanDocQuality) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE computerpref SET " + command + " WHERE ComputerPrefNum = " + POut.long(computerPref.ComputerPrefNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one ComputerPref from the database.
    */
    public static void delete(long computerPrefNum) throws Exception {
        String command = "DELETE FROM computerpref " + "WHERE ComputerPrefNum = " + POut.long(computerPrefNum);
        Db.nonQ(command);
    }

}


