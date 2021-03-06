//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:08 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class ToothGridColCrud   
{
    /**
    * Gets one ToothGridCol object from the database using the primary key.  Returns null if not found.
    */
    public static ToothGridCol selectOne(long toothGridColNum) throws Exception {
        String command = "SELECT * FROM toothgridcol " + "WHERE ToothGridColNum = " + POut.Long(toothGridColNum);
        List<ToothGridCol> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one ToothGridCol object from the database using a query.
    */
    public static ToothGridCol selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ToothGridCol> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of ToothGridCol objects from the database using a query.
    */
    public static List<ToothGridCol> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ToothGridCol> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ToothGridCol> tableToList(DataTable table) throws Exception {
        List<ToothGridCol> retVal = new List<ToothGridCol>();
        ToothGridCol toothGridCol = new ToothGridCol();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            toothGridCol = new ToothGridCol();
            toothGridCol.ToothGridColNum = PIn.Long(table.Rows[i]["ToothGridColNum"].ToString());
            toothGridCol.SheetFieldNum = PIn.Long(table.Rows[i]["SheetFieldNum"].ToString());
            toothGridCol.NameItem = PIn.String(table.Rows[i]["NameItem"].ToString());
            toothGridCol.CellType = (ToothGridCellType)PIn.Int(table.Rows[i]["CellType"].ToString());
            toothGridCol.ItemOrder = PIn.Int(table.Rows[i]["ItemOrder"].ToString());
            toothGridCol.ColumnWidth = PIn.Int(table.Rows[i]["ColumnWidth"].ToString());
            toothGridCol.CodeNum = PIn.Long(table.Rows[i]["CodeNum"].ToString());
            toothGridCol.ProcStatus = (ProcStat)PIn.Int(table.Rows[i]["ProcStatus"].ToString());
            retVal.Add(toothGridCol);
        }
        return retVal;
    }

    /**
    * Inserts one ToothGridCol into the database.  Returns the new priKey.
    */
    public static long insert(ToothGridCol toothGridCol) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            toothGridCol.ToothGridColNum = DbHelper.GetNextOracleKey("toothgridcol", "ToothGridColNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(toothGridCol,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        toothGridCol.ToothGridColNum++;
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
            return insert(toothGridCol,false);
        } 
    }

    /**
    * Inserts one ToothGridCol into the database.  Provides option to use the existing priKey.
    */
    public static long insert(ToothGridCol toothGridCol, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            toothGridCol.ToothGridColNum = ReplicationServers.GetKey("toothgridcol", "ToothGridColNum");
        }
         
        String command = "INSERT INTO toothgridcol (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "ToothGridColNum,";
        }
         
        command += "SheetFieldNum,NameItem,CellType,ItemOrder,ColumnWidth,CodeNum,ProcStatus) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(toothGridCol.ToothGridColNum) + ",";
        }
         
        command += POut.Long(toothGridCol.SheetFieldNum) + "," + "'" + POut.String(toothGridCol.NameItem) + "'," + POut.Int((int)toothGridCol.CellType) + "," + POut.Int(toothGridCol.ItemOrder) + "," + POut.Int(toothGridCol.ColumnWidth) + "," + POut.Long(toothGridCol.CodeNum) + "," + POut.Int((int)toothGridCol.ProcStatus) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            toothGridCol.ToothGridColNum = Db.NonQ(command, true);
        } 
        return toothGridCol.ToothGridColNum;
    }

    /**
    * Updates one ToothGridCol in the database.
    */
    public static void update(ToothGridCol toothGridCol) throws Exception {
        String command = "UPDATE toothgridcol SET " + "SheetFieldNum  =  " + POut.Long(toothGridCol.SheetFieldNum) + ", " + "NameItem       = '" + POut.String(toothGridCol.NameItem) + "', " + "CellType       =  " + POut.Int((int)toothGridCol.CellType) + ", " + "ItemOrder      =  " + POut.Int(toothGridCol.ItemOrder) + ", " + "ColumnWidth    =  " + POut.Int(toothGridCol.ColumnWidth) + ", " + "CodeNum        =  " + POut.Long(toothGridCol.CodeNum) + ", " + "ProcStatus     =  " + POut.Int((int)toothGridCol.ProcStatus) + " " + "WHERE ToothGridColNum = " + POut.Long(toothGridCol.ToothGridColNum);
        Db.NonQ(command);
    }

    /**
    * Updates one ToothGridCol in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(ToothGridCol toothGridCol, ToothGridCol oldToothGridCol) throws Exception {
        String command = "";
        if (toothGridCol.SheetFieldNum != oldToothGridCol.SheetFieldNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SheetFieldNum = " + POut.Long(toothGridCol.SheetFieldNum) + "";
        }
         
        if (toothGridCol.NameItem != oldToothGridCol.NameItem)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "NameItem = '" + POut.String(toothGridCol.NameItem) + "'";
        }
         
        if (toothGridCol.CellType != oldToothGridCol.CellType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CellType = " + POut.Int((int)toothGridCol.CellType) + "";
        }
         
        if (toothGridCol.ItemOrder != oldToothGridCol.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.Int(toothGridCol.ItemOrder) + "";
        }
         
        if (toothGridCol.ColumnWidth != oldToothGridCol.ColumnWidth)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ColumnWidth = " + POut.Int(toothGridCol.ColumnWidth) + "";
        }
         
        if (toothGridCol.CodeNum != oldToothGridCol.CodeNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CodeNum = " + POut.Long(toothGridCol.CodeNum) + "";
        }
         
        if (toothGridCol.ProcStatus != oldToothGridCol.ProcStatus)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProcStatus = " + POut.Int((int)toothGridCol.ProcStatus) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE toothgridcol SET " + command + " WHERE ToothGridColNum = " + POut.Long(toothGridCol.ToothGridColNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one ToothGridCol from the database.
    */
    public static void delete(long toothGridColNum) throws Exception {
        String command = "DELETE FROM toothgridcol " + "WHERE ToothGridColNum = " + POut.Long(toothGridColNum);
        Db.NonQ(command);
    }

}


