//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrPatientCrud   
{
    /**
    * Gets one EhrPatient object from the database using the primary key.  Returns null if not found.
    */
    public static EhrPatient selectOne(long patNum) throws Exception {
        String command = "SELECT * FROM ehrpatient " + "WHERE PatNum = " + POut.Long(patNum);
        List<EhrPatient> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EhrPatient object from the database using a query.
    */
    public static EhrPatient selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrPatient> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EhrPatient objects from the database using a query.
    */
    public static List<EhrPatient> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrPatient> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrPatient> tableToList(DataTable table) throws Exception {
        List<EhrPatient> retVal = new List<EhrPatient>();
        EhrPatient ehrPatient = new EhrPatient();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ehrPatient = new EhrPatient();
            ehrPatient.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            ehrPatient.MotherMaidenFname = PIn.String(table.Rows[i]["MotherMaidenFname"].ToString());
            ehrPatient.MotherMaidenLname = PIn.String(table.Rows[i]["MotherMaidenLname"].ToString());
            ehrPatient.VacShareOk = (YN)PIn.Int(table.Rows[i]["VacShareOk"].ToString());
            retVal.Add(ehrPatient);
        }
        return retVal;
    }

    /**
    * Inserts one EhrPatient into the database.  Returns the new priKey.
    */
    public static long insert(EhrPatient ehrPatient) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            ehrPatient.PatNum = DbHelper.GetNextOracleKey("ehrpatient", "PatNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(ehrPatient,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        ehrPatient.PatNum++;
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
            return insert(ehrPatient,false);
        } 
    }

    /**
    * Inserts one EhrPatient into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrPatient ehrPatient, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            ehrPatient.PatNum = ReplicationServers.GetKey("ehrpatient", "PatNum");
        }
         
        String command = "INSERT INTO ehrpatient (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "PatNum,";
        }
         
        command += "MotherMaidenFname,MotherMaidenLname,VacShareOk) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(ehrPatient.PatNum) + ",";
        }
         
        command += "'" + POut.String(ehrPatient.MotherMaidenFname) + "'," + "'" + POut.String(ehrPatient.MotherMaidenLname) + "'," + POut.Int((int)ehrPatient.VacShareOk) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            ehrPatient.PatNum = Db.NonQ(command, true);
        } 
        return ehrPatient.PatNum;
    }

    /**
    * Updates one EhrPatient in the database.
    */
    public static void update(EhrPatient ehrPatient) throws Exception {
        String command = "UPDATE ehrpatient SET " + "MotherMaidenFname= '" + POut.String(ehrPatient.MotherMaidenFname) + "', " + "MotherMaidenLname= '" + POut.String(ehrPatient.MotherMaidenLname) + "', " + "VacShareOk       =  " + POut.Int((int)ehrPatient.VacShareOk) + " " + "WHERE PatNum = " + POut.Long(ehrPatient.PatNum);
        Db.NonQ(command);
    }

    /**
    * Updates one EhrPatient in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EhrPatient ehrPatient, EhrPatient oldEhrPatient) throws Exception {
        String command = "";
        if (ehrPatient.MotherMaidenFname != oldEhrPatient.MotherMaidenFname)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MotherMaidenFname = '" + POut.String(ehrPatient.MotherMaidenFname) + "'";
        }
         
        if (ehrPatient.MotherMaidenLname != oldEhrPatient.MotherMaidenLname)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MotherMaidenLname = '" + POut.String(ehrPatient.MotherMaidenLname) + "'";
        }
         
        if (ehrPatient.VacShareOk != oldEhrPatient.VacShareOk)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "VacShareOk = " + POut.Int((int)ehrPatient.VacShareOk) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE ehrpatient SET " + command + " WHERE PatNum = " + POut.Long(ehrPatient.PatNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one EhrPatient from the database.
    */
    public static void delete(long patNum) throws Exception {
        String command = "DELETE FROM ehrpatient " + "WHERE PatNum = " + POut.Long(patNum);
        Db.NonQ(command);
    }

}

