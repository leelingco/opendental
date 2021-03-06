//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:02 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.EhrPatient;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;
import OpenDentBusiness.YN;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrPatientCrud   
{
    /**
    * Gets one EhrPatient object from the database using the primary key.  Returns null if not found.
    */
    public static EhrPatient selectOne(long patNum) throws Exception {
        String command = "SELECT * FROM ehrpatient " + "WHERE PatNum = " + POut.long(patNum);
        List<EhrPatient> list = tableToList(Db.getTable(command));
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
         
        List<EhrPatient> list = tableToList(Db.getTable(command));
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
         
        List<EhrPatient> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrPatient> tableToList(DataTable table) throws Exception {
        List<EhrPatient> retVal = new List<EhrPatient>();
        EhrPatient ehrPatient;
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
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            ehrPatient.PatNum = DbHelper.getNextOracleKey("ehrpatient","PatNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(ehrPatient, true);
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
            return Insert(ehrPatient, false);
        } 
    }

    /**
    * Inserts one EhrPatient into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrPatient ehrPatient, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            ehrPatient.PatNum = ReplicationServers.getKey("ehrpatient","PatNum");
        }
         
        String command = "INSERT INTO ehrpatient (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "PatNum,";
        }
         
        command += "MotherMaidenFname,MotherMaidenLname,VacShareOk) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(ehrPatient.PatNum) + ",";
        }
         
        command += "'" + POut.string(ehrPatient.MotherMaidenFname) + "'," + "'" + POut.string(ehrPatient.MotherMaidenLname) + "'," + POut.int(((Enum)ehrPatient.VacShareOk).ordinal()) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            ehrPatient.PatNum = Db.nonQ(command,true);
        } 
        return ehrPatient.PatNum;
    }

    /**
    * Updates one EhrPatient in the database.
    */
    public static void update(EhrPatient ehrPatient) throws Exception {
        String command = "UPDATE ehrpatient SET " + "MotherMaidenFname= '" + POut.string(ehrPatient.MotherMaidenFname) + "', " + "MotherMaidenLname= '" + POut.string(ehrPatient.MotherMaidenLname) + "', " + "VacShareOk       =  " + POut.int(((Enum)ehrPatient.VacShareOk).ordinal()) + " " + "WHERE PatNum = " + POut.long(ehrPatient.PatNum);
        Db.nonQ(command);
    }

    /**
    * Updates one EhrPatient in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EhrPatient ehrPatient, EhrPatient oldEhrPatient) throws Exception {
        String command = "";
        if (!StringSupport.equals(ehrPatient.MotherMaidenFname, oldEhrPatient.MotherMaidenFname))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MotherMaidenFname = '" + POut.string(ehrPatient.MotherMaidenFname) + "'";
        }
         
        if (!StringSupport.equals(ehrPatient.MotherMaidenLname, oldEhrPatient.MotherMaidenLname))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MotherMaidenLname = '" + POut.string(ehrPatient.MotherMaidenLname) + "'";
        }
         
        if (ehrPatient.VacShareOk != oldEhrPatient.VacShareOk)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "VacShareOk = " + POut.int(((Enum)ehrPatient.VacShareOk).ordinal()) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE ehrpatient SET " + command + " WHERE PatNum = " + POut.long(ehrPatient.PatNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one EhrPatient from the database.
    */
    public static void delete(long patNum) throws Exception {
        String command = "DELETE FROM ehrpatient " + "WHERE PatNum = " + POut.long(patNum);
        Db.nonQ(command);
    }

}


