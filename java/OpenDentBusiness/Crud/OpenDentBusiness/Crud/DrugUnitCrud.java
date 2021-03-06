//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:58 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class DrugUnitCrud   
{
    /**
    * Gets one DrugUnit object from the database using the primary key.  Returns null if not found.
    */
    public static DrugUnit selectOne(long drugUnitNum) throws Exception {
        String command = "SELECT * FROM drugunit " + "WHERE DrugUnitNum = " + POut.Long(drugUnitNum);
        List<DrugUnit> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one DrugUnit object from the database using a query.
    */
    public static DrugUnit selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<DrugUnit> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of DrugUnit objects from the database using a query.
    */
    public static List<DrugUnit> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<DrugUnit> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<DrugUnit> tableToList(DataTable table) throws Exception {
        List<DrugUnit> retVal = new List<DrugUnit>();
        DrugUnit drugUnit = new DrugUnit();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            drugUnit = new DrugUnit();
            drugUnit.DrugUnitNum = PIn.Long(table.Rows[i]["DrugUnitNum"].ToString());
            drugUnit.UnitIdentifier = PIn.String(table.Rows[i]["UnitIdentifier"].ToString());
            drugUnit.UnitText = PIn.String(table.Rows[i]["UnitText"].ToString());
            retVal.Add(drugUnit);
        }
        return retVal;
    }

    /**
    * Inserts one DrugUnit into the database.  Returns the new priKey.
    */
    public static long insert(DrugUnit drugUnit) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            drugUnit.DrugUnitNum = DbHelper.GetNextOracleKey("drugunit", "DrugUnitNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(drugUnit,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        drugUnit.DrugUnitNum++;
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
            return insert(drugUnit,false);
        } 
    }

    /**
    * Inserts one DrugUnit into the database.  Provides option to use the existing priKey.
    */
    public static long insert(DrugUnit drugUnit, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            drugUnit.DrugUnitNum = ReplicationServers.GetKey("drugunit", "DrugUnitNum");
        }
         
        String command = "INSERT INTO drugunit (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "DrugUnitNum,";
        }
         
        command += "UnitIdentifier,UnitText) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(drugUnit.DrugUnitNum) + ",";
        }
         
        command += "'" + POut.String(drugUnit.UnitIdentifier) + "'," + "'" + POut.String(drugUnit.UnitText) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            drugUnit.DrugUnitNum = Db.NonQ(command, true);
        } 
        return drugUnit.DrugUnitNum;
    }

    /**
    * Updates one DrugUnit in the database.
    */
    public static void update(DrugUnit drugUnit) throws Exception {
        String command = "UPDATE drugunit SET " + "UnitIdentifier= '" + POut.String(drugUnit.UnitIdentifier) + "', " + "UnitText      = '" + POut.String(drugUnit.UnitText) + "' " + "WHERE DrugUnitNum = " + POut.Long(drugUnit.DrugUnitNum);
        Db.NonQ(command);
    }

    /**
    * Updates one DrugUnit in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(DrugUnit drugUnit, DrugUnit oldDrugUnit) throws Exception {
        String command = "";
        if (drugUnit.UnitIdentifier != oldDrugUnit.UnitIdentifier)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UnitIdentifier = '" + POut.String(drugUnit.UnitIdentifier) + "'";
        }
         
        if (drugUnit.UnitText != oldDrugUnit.UnitText)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UnitText = '" + POut.String(drugUnit.UnitText) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE drugunit SET " + command + " WHERE DrugUnitNum = " + POut.Long(drugUnit.DrugUnitNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one DrugUnit from the database.
    */
    public static void delete(long drugUnitNum) throws Exception {
        String command = "DELETE FROM drugunit " + "WHERE DrugUnitNum = " + POut.Long(drugUnitNum);
        Db.NonQ(command);
    }

}


