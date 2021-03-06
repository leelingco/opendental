//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:55 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class ApptViewItemCrud   
{
    /**
    * Gets one ApptViewItem object from the database using the primary key.  Returns null if not found.
    */
    public static ApptViewItem selectOne(long apptViewItemNum) throws Exception {
        String command = "SELECT * FROM apptviewitem " + "WHERE ApptViewItemNum = " + POut.Long(apptViewItemNum);
        List<ApptViewItem> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one ApptViewItem object from the database using a query.
    */
    public static ApptViewItem selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ApptViewItem> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of ApptViewItem objects from the database using a query.
    */
    public static List<ApptViewItem> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ApptViewItem> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ApptViewItem> tableToList(DataTable table) throws Exception {
        List<ApptViewItem> retVal = new List<ApptViewItem>();
        ApptViewItem apptViewItem = new ApptViewItem();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            apptViewItem = new ApptViewItem();
            apptViewItem.ApptViewItemNum = PIn.Long(table.Rows[i]["ApptViewItemNum"].ToString());
            apptViewItem.ApptViewNum = PIn.Long(table.Rows[i]["ApptViewNum"].ToString());
            apptViewItem.OpNum = PIn.Long(table.Rows[i]["OpNum"].ToString());
            apptViewItem.ProvNum = PIn.Long(table.Rows[i]["ProvNum"].ToString());
            apptViewItem.ElementDesc = PIn.String(table.Rows[i]["ElementDesc"].ToString());
            apptViewItem.ElementOrder = PIn.Byte(table.Rows[i]["ElementOrder"].ToString());
            apptViewItem.ElementColor = Color.FromArgb(PIn.Int(table.Rows[i]["ElementColor"].ToString()));
            apptViewItem.ElementAlignment = (ApptViewAlignment)PIn.Int(table.Rows[i]["ElementAlignment"].ToString());
            apptViewItem.ApptFieldDefNum = PIn.Long(table.Rows[i]["ApptFieldDefNum"].ToString());
            apptViewItem.PatFieldDefNum = PIn.Long(table.Rows[i]["PatFieldDefNum"].ToString());
            retVal.Add(apptViewItem);
        }
        return retVal;
    }

    /**
    * Inserts one ApptViewItem into the database.  Returns the new priKey.
    */
    public static long insert(ApptViewItem apptViewItem) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            apptViewItem.ApptViewItemNum = DbHelper.GetNextOracleKey("apptviewitem", "ApptViewItemNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(apptViewItem,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        apptViewItem.ApptViewItemNum++;
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
            return insert(apptViewItem,false);
        } 
    }

    /**
    * Inserts one ApptViewItem into the database.  Provides option to use the existing priKey.
    */
    public static long insert(ApptViewItem apptViewItem, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            apptViewItem.ApptViewItemNum = ReplicationServers.GetKey("apptviewitem", "ApptViewItemNum");
        }
         
        String command = "INSERT INTO apptviewitem (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "ApptViewItemNum,";
        }
         
        command += "ApptViewNum,OpNum,ProvNum,ElementDesc,ElementOrder,ElementColor,ElementAlignment,ApptFieldDefNum,PatFieldDefNum) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(apptViewItem.ApptViewItemNum) + ",";
        }
         
        command += POut.Long(apptViewItem.ApptViewNum) + "," + POut.Long(apptViewItem.OpNum) + "," + POut.Long(apptViewItem.ProvNum) + "," + "'" + POut.String(apptViewItem.ElementDesc) + "'," + POut.Byte(apptViewItem.ElementOrder) + "," + POut.Int(apptViewItem.ElementColor.ToArgb()) + "," + POut.Int((int)apptViewItem.ElementAlignment) + "," + POut.Long(apptViewItem.ApptFieldDefNum) + "," + POut.Long(apptViewItem.PatFieldDefNum) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            apptViewItem.ApptViewItemNum = Db.NonQ(command, true);
        } 
        return apptViewItem.ApptViewItemNum;
    }

    /**
    * Updates one ApptViewItem in the database.
    */
    public static void update(ApptViewItem apptViewItem) throws Exception {
        String command = "UPDATE apptviewitem SET " + "ApptViewNum     =  " + POut.Long(apptViewItem.ApptViewNum) + ", " + "OpNum           =  " + POut.Long(apptViewItem.OpNum) + ", " + "ProvNum         =  " + POut.Long(apptViewItem.ProvNum) + ", " + "ElementDesc     = '" + POut.String(apptViewItem.ElementDesc) + "', " + "ElementOrder    =  " + POut.Byte(apptViewItem.ElementOrder) + ", " + "ElementColor    =  " + POut.Int(apptViewItem.ElementColor.ToArgb()) + ", " + "ElementAlignment=  " + POut.Int((int)apptViewItem.ElementAlignment) + ", " + "ApptFieldDefNum =  " + POut.Long(apptViewItem.ApptFieldDefNum) + ", " + "PatFieldDefNum  =  " + POut.Long(apptViewItem.PatFieldDefNum) + " " + "WHERE ApptViewItemNum = " + POut.Long(apptViewItem.ApptViewItemNum);
        Db.NonQ(command);
    }

    /**
    * Updates one ApptViewItem in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(ApptViewItem apptViewItem, ApptViewItem oldApptViewItem) throws Exception {
        String command = "";
        if (apptViewItem.ApptViewNum != oldApptViewItem.ApptViewNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ApptViewNum = " + POut.Long(apptViewItem.ApptViewNum) + "";
        }
         
        if (apptViewItem.OpNum != oldApptViewItem.OpNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OpNum = " + POut.Long(apptViewItem.OpNum) + "";
        }
         
        if (apptViewItem.ProvNum != oldApptViewItem.ProvNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProvNum = " + POut.Long(apptViewItem.ProvNum) + "";
        }
         
        if (apptViewItem.ElementDesc != oldApptViewItem.ElementDesc)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ElementDesc = '" + POut.String(apptViewItem.ElementDesc) + "'";
        }
         
        if (apptViewItem.ElementOrder != oldApptViewItem.ElementOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ElementOrder = " + POut.Byte(apptViewItem.ElementOrder) + "";
        }
         
        if (apptViewItem.ElementColor != oldApptViewItem.ElementColor)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ElementColor = " + POut.Int(apptViewItem.ElementColor.ToArgb()) + "";
        }
         
        if (apptViewItem.ElementAlignment != oldApptViewItem.ElementAlignment)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ElementAlignment = " + POut.Int((int)apptViewItem.ElementAlignment) + "";
        }
         
        if (apptViewItem.ApptFieldDefNum != oldApptViewItem.ApptFieldDefNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ApptFieldDefNum = " + POut.Long(apptViewItem.ApptFieldDefNum) + "";
        }
         
        if (apptViewItem.PatFieldDefNum != oldApptViewItem.PatFieldDefNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatFieldDefNum = " + POut.Long(apptViewItem.PatFieldDefNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE apptviewitem SET " + command + " WHERE ApptViewItemNum = " + POut.Long(apptViewItem.ApptViewItemNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one ApptViewItem from the database.
    */
    public static void delete(long apptViewItemNum) throws Exception {
        String command = "DELETE FROM apptviewitem " + "WHERE ApptViewItemNum = " + POut.Long(apptViewItemNum);
        Db.NonQ(command);
    }

}


