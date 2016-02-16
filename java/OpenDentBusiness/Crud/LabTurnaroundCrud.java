//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:05 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.LabTurnaround;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class LabTurnaroundCrud   
{
    /**
    * Gets one LabTurnaround object from the database using the primary key.  Returns null if not found.
    */
    public static LabTurnaround selectOne(long labTurnaroundNum) throws Exception {
        String command = "SELECT * FROM labturnaround " + "WHERE LabTurnaroundNum = " + POut.long(labTurnaroundNum);
        List<LabTurnaround> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one LabTurnaround object from the database using a query.
    */
    public static LabTurnaround selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<LabTurnaround> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of LabTurnaround objects from the database using a query.
    */
    public static List<LabTurnaround> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<LabTurnaround> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<LabTurnaround> tableToList(DataTable table) throws Exception {
        List<LabTurnaround> retVal = new List<LabTurnaround>();
        LabTurnaround labTurnaround;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            labTurnaround = new LabTurnaround();
            labTurnaround.LabTurnaroundNum = PIn.Long(table.Rows[i]["LabTurnaroundNum"].ToString());
            labTurnaround.LaboratoryNum = PIn.Long(table.Rows[i]["LaboratoryNum"].ToString());
            labTurnaround.Description = PIn.String(table.Rows[i]["Description"].ToString());
            labTurnaround.DaysPublished = PIn.Int(table.Rows[i]["DaysPublished"].ToString());
            labTurnaround.DaysActual = PIn.Int(table.Rows[i]["DaysActual"].ToString());
            retVal.Add(labTurnaround);
        }
        return retVal;
    }

    /**
    * Inserts one LabTurnaround into the database.  Returns the new priKey.
    */
    public static long insert(LabTurnaround labTurnaround) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            labTurnaround.LabTurnaroundNum = DbHelper.getNextOracleKey("labturnaround","LabTurnaroundNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(labTurnaround, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        labTurnaround.LabTurnaroundNum++;
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
            return Insert(labTurnaround, false);
        } 
    }

    /**
    * Inserts one LabTurnaround into the database.  Provides option to use the existing priKey.
    */
    public static long insert(LabTurnaround labTurnaround, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            labTurnaround.LabTurnaroundNum = ReplicationServers.getKey("labturnaround","LabTurnaroundNum");
        }
         
        String command = "INSERT INTO labturnaround (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "LabTurnaroundNum,";
        }
         
        command += "LaboratoryNum,Description,DaysPublished,DaysActual) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(labTurnaround.LabTurnaroundNum) + ",";
        }
         
        command += POut.long(labTurnaround.LaboratoryNum) + "," + "'" + POut.string(labTurnaround.Description) + "'," + POut.int(labTurnaround.DaysPublished) + "," + POut.int(labTurnaround.DaysActual) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            labTurnaround.LabTurnaroundNum = Db.nonQ(command,true);
        } 
        return labTurnaround.LabTurnaroundNum;
    }

    /**
    * Updates one LabTurnaround in the database.
    */
    public static void update(LabTurnaround labTurnaround) throws Exception {
        String command = "UPDATE labturnaround SET " + "LaboratoryNum   =  " + POut.long(labTurnaround.LaboratoryNum) + ", " + "Description     = '" + POut.string(labTurnaround.Description) + "', " + "DaysPublished   =  " + POut.int(labTurnaround.DaysPublished) + ", " + "DaysActual      =  " + POut.int(labTurnaround.DaysActual) + " " + "WHERE LabTurnaroundNum = " + POut.long(labTurnaround.LabTurnaroundNum);
        Db.nonQ(command);
    }

    /**
    * Updates one LabTurnaround in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(LabTurnaround labTurnaround, LabTurnaround oldLabTurnaround) throws Exception {
        String command = "";
        if (labTurnaround.LaboratoryNum != oldLabTurnaround.LaboratoryNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LaboratoryNum = " + POut.long(labTurnaround.LaboratoryNum) + "";
        }
         
        if (!StringSupport.equals(labTurnaround.Description, oldLabTurnaround.Description))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.string(labTurnaround.Description) + "'";
        }
         
        if (labTurnaround.DaysPublished != oldLabTurnaround.DaysPublished)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DaysPublished = " + POut.int(labTurnaround.DaysPublished) + "";
        }
         
        if (labTurnaround.DaysActual != oldLabTurnaround.DaysActual)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DaysActual = " + POut.int(labTurnaround.DaysActual) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE labturnaround SET " + command + " WHERE LabTurnaroundNum = " + POut.long(labTurnaround.LabTurnaroundNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one LabTurnaround from the database.
    */
    public static void delete(long labTurnaroundNum) throws Exception {
        String command = "DELETE FROM labturnaround " + "WHERE LabTurnaroundNum = " + POut.long(labTurnaroundNum);
        Db.nonQ(command);
    }

}

