//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:04 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class PopupCrud   
{
    /**
    * Gets one Popup object from the database using the primary key.  Returns null if not found.
    */
    public static Popup selectOne(long popupNum) throws Exception {
        String command = "SELECT * FROM popup " + "WHERE PopupNum = " + POut.Long(popupNum);
        List<Popup> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Popup object from the database using a query.
    */
    public static Popup selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Popup> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Popup objects from the database using a query.
    */
    public static List<Popup> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Popup> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Popup> tableToList(DataTable table) throws Exception {
        List<Popup> retVal = new List<Popup>();
        Popup popup = new Popup();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            popup = new Popup();
            popup.PopupNum = PIn.Long(table.Rows[i]["PopupNum"].ToString());
            popup.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            popup.Description = PIn.String(table.Rows[i]["Description"].ToString());
            popup.IsDisabled = PIn.Bool(table.Rows[i]["IsDisabled"].ToString());
            popup.PopupLevel = (EnumPopupLevel)PIn.Int(table.Rows[i]["PopupLevel"].ToString());
            popup.UserNum = PIn.Long(table.Rows[i]["UserNum"].ToString());
            popup.DateTimeEntry = PIn.DateT(table.Rows[i]["DateTimeEntry"].ToString());
            popup.IsArchived = PIn.Bool(table.Rows[i]["IsArchived"].ToString());
            popup.PopupNumArchive = PIn.Long(table.Rows[i]["PopupNumArchive"].ToString());
            retVal.Add(popup);
        }
        return retVal;
    }

    /**
    * Inserts one Popup into the database.  Returns the new priKey.
    */
    public static long insert(Popup popup) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            popup.PopupNum = DbHelper.GetNextOracleKey("popup", "PopupNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(popup,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        popup.PopupNum++;
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
            return insert(popup,false);
        } 
    }

    /**
    * Inserts one Popup into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Popup popup, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            popup.PopupNum = ReplicationServers.GetKey("popup", "PopupNum");
        }
         
        String command = "INSERT INTO popup (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "PopupNum,";
        }
         
        command += "PatNum,Description,IsDisabled,PopupLevel,UserNum,DateTimeEntry,IsArchived,PopupNumArchive) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(popup.PopupNum) + ",";
        }
         
        command += POut.Long(popup.PatNum) + "," + "'" + POut.String(popup.Description) + "'," + POut.Bool(popup.IsDisabled) + "," + POut.Int((int)popup.PopupLevel) + "," + POut.Long(popup.UserNum) + "," + DbHelper.Now() + "," + POut.Bool(popup.IsArchived) + "," + POut.Long(popup.PopupNumArchive) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            popup.PopupNum = Db.NonQ(command, true);
        } 
        return popup.PopupNum;
    }

    /**
    * Updates one Popup in the database.
    */
    public static void update(Popup popup) throws Exception {
        //DateTimeEntry not allowed to change
        String command = "UPDATE popup SET " + "PatNum         =  " + POut.Long(popup.PatNum) + ", " + "Description    = '" + POut.String(popup.Description) + "', " + "IsDisabled     =  " + POut.Bool(popup.IsDisabled) + ", " + "PopupLevel     =  " + POut.Int((int)popup.PopupLevel) + ", " + "UserNum        =  " + POut.Long(popup.UserNum) + ", " + "IsArchived     =  " + POut.Bool(popup.IsArchived) + ", " + "PopupNumArchive=  " + POut.Long(popup.PopupNumArchive) + " " + "WHERE PopupNum = " + POut.Long(popup.PopupNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Popup in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Popup popup, Popup oldPopup) throws Exception {
        String command = "";
        if (popup.PatNum != oldPopup.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(popup.PatNum) + "";
        }
         
        if (popup.Description != oldPopup.Description)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.String(popup.Description) + "'";
        }
         
        if (popup.IsDisabled != oldPopup.IsDisabled)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsDisabled = " + POut.Bool(popup.IsDisabled) + "";
        }
         
        if (popup.PopupLevel != oldPopup.PopupLevel)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PopupLevel = " + POut.Int((int)popup.PopupLevel) + "";
        }
         
        if (popup.UserNum != oldPopup.UserNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UserNum = " + POut.Long(popup.UserNum) + "";
        }
         
        //DateTimeEntry not allowed to change
        if (popup.IsArchived != oldPopup.IsArchived)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsArchived = " + POut.Bool(popup.IsArchived) + "";
        }
         
        if (popup.PopupNumArchive != oldPopup.PopupNumArchive)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PopupNumArchive = " + POut.Long(popup.PopupNumArchive) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE popup SET " + command + " WHERE PopupNum = " + POut.Long(popup.PopupNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Popup from the database.
    */
    public static void delete(long popupNum) throws Exception {
        String command = "DELETE FROM popup " + "WHERE PopupNum = " + POut.Long(popupNum);
        Db.NonQ(command);
    }

}

