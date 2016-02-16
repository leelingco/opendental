//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:05 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Medication;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class MedicationCrud   
{
    /**
    * Gets one Medication object from the database using the primary key.  Returns null if not found.
    */
    public static Medication selectOne(long medicationNum) throws Exception {
        String command = "SELECT * FROM medication " + "WHERE MedicationNum = " + POut.long(medicationNum);
        List<Medication> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Medication object from the database using a query.
    */
    public static Medication selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Medication> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Medication objects from the database using a query.
    */
    public static List<Medication> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Medication> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Medication> tableToList(DataTable table) throws Exception {
        List<Medication> retVal = new List<Medication>();
        Medication medication;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            medication = new Medication();
            medication.MedicationNum = PIn.Long(table.Rows[i]["MedicationNum"].ToString());
            medication.MedName = PIn.String(table.Rows[i]["MedName"].ToString());
            medication.GenericNum = PIn.Long(table.Rows[i]["GenericNum"].ToString());
            medication.Notes = PIn.String(table.Rows[i]["Notes"].ToString());
            medication.DateTStamp = PIn.DateT(table.Rows[i]["DateTStamp"].ToString());
            medication.RxCui = PIn.Long(table.Rows[i]["RxCui"].ToString());
            retVal.Add(medication);
        }
        return retVal;
    }

    /**
    * Inserts one Medication into the database.  Returns the new priKey.
    */
    public static long insert(Medication medication) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            medication.MedicationNum = DbHelper.getNextOracleKey("medication","MedicationNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(medication, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        medication.MedicationNum++;
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
            return Insert(medication, false);
        } 
    }

    /**
    * Inserts one Medication into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Medication medication, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            medication.MedicationNum = ReplicationServers.getKey("medication","MedicationNum");
        }
         
        String command = "INSERT INTO medication (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "MedicationNum,";
        }
         
        command += "MedName,GenericNum,Notes,RxCui) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(medication.MedicationNum) + ",";
        }
         
        //DateTStamp can only be set by MySQL
        command += "'" + POut.string(medication.MedName) + "'," + POut.long(medication.GenericNum) + "," + "'" + POut.string(medication.Notes) + "'," + POut.long(medication.RxCui) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            medication.MedicationNum = Db.nonQ(command,true);
        } 
        return medication.MedicationNum;
    }

    /**
    * Updates one Medication in the database.
    */
    public static void update(Medication medication) throws Exception {
        //DateTStamp can only be set by MySQL
        String command = "UPDATE medication SET " + "MedName      = '" + POut.string(medication.MedName) + "', " + "GenericNum   =  " + POut.long(medication.GenericNum) + ", " + "Notes        = '" + POut.string(medication.Notes) + "', " + "RxCui        =  " + POut.long(medication.RxCui) + " " + "WHERE MedicationNum = " + POut.long(medication.MedicationNum);
        Db.nonQ(command);
    }

    /**
    * Updates one Medication in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Medication medication, Medication oldMedication) throws Exception {
        String command = "";
        if (!StringSupport.equals(medication.MedName, oldMedication.MedName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MedName = '" + POut.string(medication.MedName) + "'";
        }
         
        if (medication.GenericNum != oldMedication.GenericNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "GenericNum = " + POut.long(medication.GenericNum) + "";
        }
         
        if (!StringSupport.equals(medication.Notes, oldMedication.Notes))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Notes = '" + POut.string(medication.Notes) + "'";
        }
         
        //DateTStamp can only be set by MySQL
        if (medication.RxCui != oldMedication.RxCui)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RxCui = " + POut.long(medication.RxCui) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE medication SET " + command + " WHERE MedicationNum = " + POut.long(medication.MedicationNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Medication from the database.
    */
    public static void delete(long medicationNum) throws Exception {
        String command = "DELETE FROM medication " + "WHERE MedicationNum = " + POut.long(medicationNum);
        Db.nonQ(command);
    }

}

