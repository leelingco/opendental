//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:57 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class ComputerPrefCrud   
{
    /**
    * Gets one ComputerPref object from the database using the primary key.  Returns null if not found.
    */
    public static ComputerPref selectOne(long computerPrefNum) throws Exception {
        String command = "SELECT * FROM computerpref " + "WHERE ComputerPrefNum = " + POut.Long(computerPrefNum);
        List<ComputerPref> list = TableToList(Db.GetTable(command));
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
         
        List<ComputerPref> list = TableToList(Db.GetTable(command));
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
         
        List<ComputerPref> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ComputerPref> tableToList(DataTable table) throws Exception {
        List<ComputerPref> retVal = new List<ComputerPref>();
        ComputerPref computerPref = new ComputerPref();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            computerPref = new ComputerPref();
            computerPref.ComputerPrefNum = PIn.Long(table.Rows[i]["ComputerPrefNum"].ToString());
            computerPref.ComputerName = PIn.String(table.Rows[i]["ComputerName"].ToString());
            computerPref.GraphicsUseHardware = PIn.Bool(table.Rows[i]["GraphicsUseHardware"].ToString());
            computerPref.GraphicsSimple = (DrawingMode)PIn.Int(table.Rows[i]["GraphicsSimple"].ToString());
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
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            computerPref.ComputerPrefNum = DbHelper.GetNextOracleKey("computerpref", "ComputerPrefNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(computerPref,true);
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
            return insert(computerPref,false);
        } 
    }

    /**
    * Inserts one ComputerPref into the database.  Provides option to use the existing priKey.
    */
    public static long insert(ComputerPref computerPref, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            computerPref.ComputerPrefNum = ReplicationServers.GetKey("computerpref", "ComputerPrefNum");
        }
         
        String command = "INSERT INTO computerpref (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "ComputerPrefNum,";
        }
         
        command += "ComputerName,GraphicsUseHardware,GraphicsSimple,SensorType,SensorBinned,SensorPort,SensorExposure,GraphicsDoubleBuffering,PreferredPixelFormatNum,AtoZpath,TaskKeepListHidden,TaskDock,TaskX,TaskY,DirectXFormat,RecentApptView,ScanDocSelectSource,ScanDocShowOptions,ScanDocDuplex,ScanDocGrayscale,ScanDocResolution,ScanDocQuality) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(computerPref.ComputerPrefNum) + ",";
        }
         
        command += "'" + POut.String(computerPref.ComputerName) + "'," + POut.Bool(computerPref.GraphicsUseHardware) + "," + POut.Int((int)computerPref.GraphicsSimple) + "," + "'" + POut.String(computerPref.SensorType) + "'," + POut.Bool(computerPref.SensorBinned) + "," + POut.Int(computerPref.SensorPort) + "," + POut.Int(computerPref.SensorExposure) + "," + POut.Bool(computerPref.GraphicsDoubleBuffering) + "," + POut.Int(computerPref.PreferredPixelFormatNum) + "," + "'" + POut.String(computerPref.AtoZpath) + "'," + POut.Bool(computerPref.TaskKeepListHidden) + "," + POut.Int(computerPref.TaskDock) + "," + POut.Int(computerPref.TaskX) + "," + POut.Int(computerPref.TaskY) + "," + "'" + POut.String(computerPref.DirectXFormat) + "'," + POut.Byte(computerPref.RecentApptView) + "," + POut.Bool(computerPref.ScanDocSelectSource) + "," + POut.Bool(computerPref.ScanDocShowOptions) + "," + POut.Bool(computerPref.ScanDocDuplex) + "," + POut.Bool(computerPref.ScanDocGrayscale) + "," + POut.Int(computerPref.ScanDocResolution) + "," + POut.Byte(computerPref.ScanDocQuality) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            computerPref.ComputerPrefNum = Db.NonQ(command, true);
        } 
        return computerPref.ComputerPrefNum;
    }

    /**
    * Updates one ComputerPref in the database.
    */
    public static void update(ComputerPref computerPref) throws Exception {
        String command = "UPDATE computerpref SET " + "ComputerName           = '" + POut.String(computerPref.ComputerName) + "', " + "GraphicsUseHardware    =  " + POut.Bool(computerPref.GraphicsUseHardware) + ", " + "GraphicsSimple         =  " + POut.Int((int)computerPref.GraphicsSimple) + ", " + "SensorType             = '" + POut.String(computerPref.SensorType) + "', " + "SensorBinned           =  " + POut.Bool(computerPref.SensorBinned) + ", " + "SensorPort             =  " + POut.Int(computerPref.SensorPort) + ", " + "SensorExposure         =  " + POut.Int(computerPref.SensorExposure) + ", " + "GraphicsDoubleBuffering=  " + POut.Bool(computerPref.GraphicsDoubleBuffering) + ", " + "PreferredPixelFormatNum=  " + POut.Int(computerPref.PreferredPixelFormatNum) + ", " + "AtoZpath               = '" + POut.String(computerPref.AtoZpath) + "', " + "TaskKeepListHidden     =  " + POut.Bool(computerPref.TaskKeepListHidden) + ", " + "TaskDock               =  " + POut.Int(computerPref.TaskDock) + ", " + "TaskX                  =  " + POut.Int(computerPref.TaskX) + ", " + "TaskY                  =  " + POut.Int(computerPref.TaskY) + ", " + "DirectXFormat          = '" + POut.String(computerPref.DirectXFormat) + "', " + "RecentApptView         =  " + POut.Byte(computerPref.RecentApptView) + ", " + "ScanDocSelectSource    =  " + POut.Bool(computerPref.ScanDocSelectSource) + ", " + "ScanDocShowOptions     =  " + POut.Bool(computerPref.ScanDocShowOptions) + ", " + "ScanDocDuplex          =  " + POut.Bool(computerPref.ScanDocDuplex) + ", " + "ScanDocGrayscale       =  " + POut.Bool(computerPref.ScanDocGrayscale) + ", " + "ScanDocResolution      =  " + POut.Int(computerPref.ScanDocResolution) + ", " + "ScanDocQuality         =  " + POut.Byte(computerPref.ScanDocQuality) + " " + "WHERE ComputerPrefNum = " + POut.Long(computerPref.ComputerPrefNum);
        Db.NonQ(command);
    }

    /**
    * Updates one ComputerPref in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(ComputerPref computerPref, ComputerPref oldComputerPref) throws Exception {
        String command = "";
        if (computerPref.ComputerName != oldComputerPref.ComputerName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ComputerName = '" + POut.String(computerPref.ComputerName) + "'";
        }
         
        if (computerPref.GraphicsUseHardware != oldComputerPref.GraphicsUseHardware)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "GraphicsUseHardware = " + POut.Bool(computerPref.GraphicsUseHardware) + "";
        }
         
        if (computerPref.GraphicsSimple != oldComputerPref.GraphicsSimple)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "GraphicsSimple = " + POut.Int((int)computerPref.GraphicsSimple) + "";
        }
         
        if (computerPref.SensorType != oldComputerPref.SensorType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SensorType = '" + POut.String(computerPref.SensorType) + "'";
        }
         
        if (computerPref.SensorBinned != oldComputerPref.SensorBinned)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SensorBinned = " + POut.Bool(computerPref.SensorBinned) + "";
        }
         
        if (computerPref.SensorPort != oldComputerPref.SensorPort)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SensorPort = " + POut.Int(computerPref.SensorPort) + "";
        }
         
        if (computerPref.SensorExposure != oldComputerPref.SensorExposure)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SensorExposure = " + POut.Int(computerPref.SensorExposure) + "";
        }
         
        if (computerPref.GraphicsDoubleBuffering != oldComputerPref.GraphicsDoubleBuffering)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "GraphicsDoubleBuffering = " + POut.Bool(computerPref.GraphicsDoubleBuffering) + "";
        }
         
        if (computerPref.PreferredPixelFormatNum != oldComputerPref.PreferredPixelFormatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PreferredPixelFormatNum = " + POut.Int(computerPref.PreferredPixelFormatNum) + "";
        }
         
        if (computerPref.AtoZpath != oldComputerPref.AtoZpath)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AtoZpath = '" + POut.String(computerPref.AtoZpath) + "'";
        }
         
        if (computerPref.TaskKeepListHidden != oldComputerPref.TaskKeepListHidden)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TaskKeepListHidden = " + POut.Bool(computerPref.TaskKeepListHidden) + "";
        }
         
        if (computerPref.TaskDock != oldComputerPref.TaskDock)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TaskDock = " + POut.Int(computerPref.TaskDock) + "";
        }
         
        if (computerPref.TaskX != oldComputerPref.TaskX)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TaskX = " + POut.Int(computerPref.TaskX) + "";
        }
         
        if (computerPref.TaskY != oldComputerPref.TaskY)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TaskY = " + POut.Int(computerPref.TaskY) + "";
        }
         
        if (computerPref.DirectXFormat != oldComputerPref.DirectXFormat)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DirectXFormat = '" + POut.String(computerPref.DirectXFormat) + "'";
        }
         
        if (computerPref.RecentApptView != oldComputerPref.RecentApptView)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RecentApptView = " + POut.Byte(computerPref.RecentApptView) + "";
        }
         
        if (computerPref.ScanDocSelectSource != oldComputerPref.ScanDocSelectSource)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocSelectSource = " + POut.Bool(computerPref.ScanDocSelectSource) + "";
        }
         
        if (computerPref.ScanDocShowOptions != oldComputerPref.ScanDocShowOptions)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocShowOptions = " + POut.Bool(computerPref.ScanDocShowOptions) + "";
        }
         
        if (computerPref.ScanDocDuplex != oldComputerPref.ScanDocDuplex)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocDuplex = " + POut.Bool(computerPref.ScanDocDuplex) + "";
        }
         
        if (computerPref.ScanDocGrayscale != oldComputerPref.ScanDocGrayscale)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocGrayscale = " + POut.Bool(computerPref.ScanDocGrayscale) + "";
        }
         
        if (computerPref.ScanDocResolution != oldComputerPref.ScanDocResolution)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocResolution = " + POut.Int(computerPref.ScanDocResolution) + "";
        }
         
        if (computerPref.ScanDocQuality != oldComputerPref.ScanDocQuality)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScanDocQuality = " + POut.Byte(computerPref.ScanDocQuality) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE computerpref SET " + command + " WHERE ComputerPrefNum = " + POut.Long(computerPref.ComputerPrefNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one ComputerPref from the database.
    */
    public static void delete(long computerPrefNum) throws Exception {
        String command = "DELETE FROM computerpref " + "WHERE ComputerPrefNum = " + POut.Long(computerPrefNum);
        Db.NonQ(command);
    }

}


