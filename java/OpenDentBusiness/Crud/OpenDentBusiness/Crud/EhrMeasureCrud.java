//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:59 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrMeasureCrud   
{
    /**
    * Gets one EhrMeasure object from the database using the primary key.  Returns null if not found.
    */
    public static EhrMeasure selectOne(long ehrMeasureNum) throws Exception {
        String command = "SELECT * FROM ehrmeasure " + "WHERE EhrMeasureNum = " + POut.Long(ehrMeasureNum);
        List<EhrMeasure> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EhrMeasure object from the database using a query.
    */
    public static EhrMeasure selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrMeasure> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EhrMeasure objects from the database using a query.
    */
    public static List<EhrMeasure> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrMeasure> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrMeasure> tableToList(DataTable table) throws Exception {
        List<EhrMeasure> retVal = new List<EhrMeasure>();
        EhrMeasure ehrMeasure = new EhrMeasure();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ehrMeasure = new EhrMeasure();
            ehrMeasure.EhrMeasureNum = PIn.Long(table.Rows[i]["EhrMeasureNum"].ToString());
            ehrMeasure.MeasureType = (EhrMeasureType)PIn.Int(table.Rows[i]["MeasureType"].ToString());
            ehrMeasure.Numerator = PIn.Int(table.Rows[i]["Numerator"].ToString());
            ehrMeasure.Denominator = PIn.Int(table.Rows[i]["Denominator"].ToString());
            retVal.Add(ehrMeasure);
        }
        return retVal;
    }

    /**
    * Inserts one EhrMeasure into the database.  Returns the new priKey.
    */
    public static long insert(EhrMeasure ehrMeasure) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            ehrMeasure.EhrMeasureNum = DbHelper.GetNextOracleKey("ehrmeasure", "EhrMeasureNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(ehrMeasure,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        ehrMeasure.EhrMeasureNum++;
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
            return insert(ehrMeasure,false);
        } 
    }

    /**
    * Inserts one EhrMeasure into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrMeasure ehrMeasure, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            ehrMeasure.EhrMeasureNum = ReplicationServers.GetKey("ehrmeasure", "EhrMeasureNum");
        }
         
        String command = "INSERT INTO ehrmeasure (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "EhrMeasureNum,";
        }
         
        command += "MeasureType,Numerator,Denominator) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(ehrMeasure.EhrMeasureNum) + ",";
        }
         
        command += POut.Int((int)ehrMeasure.MeasureType) + "," + POut.Int(ehrMeasure.Numerator) + "," + POut.Int(ehrMeasure.Denominator) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            ehrMeasure.EhrMeasureNum = Db.NonQ(command, true);
        } 
        return ehrMeasure.EhrMeasureNum;
    }

    /**
    * Updates one EhrMeasure in the database.
    */
    public static void update(EhrMeasure ehrMeasure) throws Exception {
        String command = "UPDATE ehrmeasure SET " + "MeasureType  =  " + POut.Int((int)ehrMeasure.MeasureType) + ", " + "Numerator    =  " + POut.Int(ehrMeasure.Numerator) + ", " + "Denominator  =  " + POut.Int(ehrMeasure.Denominator) + " " + "WHERE EhrMeasureNum = " + POut.Long(ehrMeasure.EhrMeasureNum);
        Db.NonQ(command);
    }

    /**
    * Updates one EhrMeasure in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EhrMeasure ehrMeasure, EhrMeasure oldEhrMeasure) throws Exception {
        String command = "";
        if (ehrMeasure.MeasureType != oldEhrMeasure.MeasureType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MeasureType = " + POut.Int((int)ehrMeasure.MeasureType) + "";
        }
         
        if (ehrMeasure.Numerator != oldEhrMeasure.Numerator)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Numerator = " + POut.Int(ehrMeasure.Numerator) + "";
        }
         
        if (ehrMeasure.Denominator != oldEhrMeasure.Denominator)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Denominator = " + POut.Int(ehrMeasure.Denominator) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE ehrmeasure SET " + command + " WHERE EhrMeasureNum = " + POut.Long(ehrMeasure.EhrMeasureNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one EhrMeasure from the database.
    */
    public static void delete(long ehrMeasureNum) throws Exception {
        String command = "DELETE FROM ehrmeasure " + "WHERE EhrMeasureNum = " + POut.Long(ehrMeasureNum);
        Db.NonQ(command);
    }

}

