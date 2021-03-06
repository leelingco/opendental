//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:04 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class ProcCodeNoteCrud   
{
    /**
    * Gets one ProcCodeNote object from the database using the primary key.  Returns null if not found.
    */
    public static ProcCodeNote selectOne(long procCodeNoteNum) throws Exception {
        String command = "SELECT * FROM proccodenote " + "WHERE ProcCodeNoteNum = " + POut.Long(procCodeNoteNum);
        List<ProcCodeNote> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one ProcCodeNote object from the database using a query.
    */
    public static ProcCodeNote selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ProcCodeNote> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of ProcCodeNote objects from the database using a query.
    */
    public static List<ProcCodeNote> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ProcCodeNote> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ProcCodeNote> tableToList(DataTable table) throws Exception {
        List<ProcCodeNote> retVal = new List<ProcCodeNote>();
        ProcCodeNote procCodeNote = new ProcCodeNote();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            procCodeNote = new ProcCodeNote();
            procCodeNote.ProcCodeNoteNum = PIn.Long(table.Rows[i]["ProcCodeNoteNum"].ToString());
            procCodeNote.CodeNum = PIn.Long(table.Rows[i]["CodeNum"].ToString());
            procCodeNote.ProvNum = PIn.Long(table.Rows[i]["ProvNum"].ToString());
            procCodeNote.Note = PIn.String(table.Rows[i]["Note"].ToString());
            procCodeNote.ProcTime = PIn.String(table.Rows[i]["ProcTime"].ToString());
            retVal.Add(procCodeNote);
        }
        return retVal;
    }

    /**
    * Inserts one ProcCodeNote into the database.  Returns the new priKey.
    */
    public static long insert(ProcCodeNote procCodeNote) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            procCodeNote.ProcCodeNoteNum = DbHelper.GetNextOracleKey("proccodenote", "ProcCodeNoteNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(procCodeNote,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        procCodeNote.ProcCodeNoteNum++;
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
            return insert(procCodeNote,false);
        } 
    }

    /**
    * Inserts one ProcCodeNote into the database.  Provides option to use the existing priKey.
    */
    public static long insert(ProcCodeNote procCodeNote, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            procCodeNote.ProcCodeNoteNum = ReplicationServers.GetKey("proccodenote", "ProcCodeNoteNum");
        }
         
        String command = "INSERT INTO proccodenote (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "ProcCodeNoteNum,";
        }
         
        command += "CodeNum,ProvNum,Note,ProcTime) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(procCodeNote.ProcCodeNoteNum) + ",";
        }
         
        command += POut.Long(procCodeNote.CodeNum) + "," + POut.Long(procCodeNote.ProvNum) + "," + "'" + POut.String(procCodeNote.Note) + "'," + "'" + POut.String(procCodeNote.ProcTime) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            procCodeNote.ProcCodeNoteNum = Db.NonQ(command, true);
        } 
        return procCodeNote.ProcCodeNoteNum;
    }

    /**
    * Updates one ProcCodeNote in the database.
    */
    public static void update(ProcCodeNote procCodeNote) throws Exception {
        String command = "UPDATE proccodenote SET " + "CodeNum        =  " + POut.Long(procCodeNote.CodeNum) + ", " + "ProvNum        =  " + POut.Long(procCodeNote.ProvNum) + ", " + "Note           = '" + POut.String(procCodeNote.Note) + "', " + "ProcTime       = '" + POut.String(procCodeNote.ProcTime) + "' " + "WHERE ProcCodeNoteNum = " + POut.Long(procCodeNote.ProcCodeNoteNum);
        Db.NonQ(command);
    }

    /**
    * Updates one ProcCodeNote in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(ProcCodeNote procCodeNote, ProcCodeNote oldProcCodeNote) throws Exception {
        String command = "";
        if (procCodeNote.CodeNum != oldProcCodeNote.CodeNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CodeNum = " + POut.Long(procCodeNote.CodeNum) + "";
        }
         
        if (procCodeNote.ProvNum != oldProcCodeNote.ProvNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProvNum = " + POut.Long(procCodeNote.ProvNum) + "";
        }
         
        if (procCodeNote.Note != oldProcCodeNote.Note)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Note = '" + POut.String(procCodeNote.Note) + "'";
        }
         
        if (procCodeNote.ProcTime != oldProcCodeNote.ProcTime)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProcTime = '" + POut.String(procCodeNote.ProcTime) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE proccodenote SET " + command + " WHERE ProcCodeNoteNum = " + POut.Long(procCodeNote.ProcCodeNoteNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one ProcCodeNote from the database.
    */
    public static void delete(long procCodeNoteNum) throws Exception {
        String command = "DELETE FROM proccodenote " + "WHERE ProcCodeNoteNum = " + POut.Long(procCodeNoteNum);
        Db.NonQ(command);
    }

}


