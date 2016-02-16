//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:58 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class DefCrud   
{
    /**
    * Gets one Def object from the database using the primary key.  Returns null if not found.
    */
    public static Def selectOne(long defNum) throws Exception {
        String command = "SELECT * FROM definition " + "WHERE DefNum = " + POut.Long(defNum);
        List<Def> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Def object from the database using a query.
    */
    public static Def selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Def> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Def objects from the database using a query.
    */
    public static List<Def> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Def> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Def> tableToList(DataTable table) throws Exception {
        List<Def> retVal = new List<Def>();
        Def def = new Def();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            def = new Def();
            def.DefNum = PIn.Long(table.Rows[i]["DefNum"].ToString());
            def.Category = (DefCat)PIn.Int(table.Rows[i]["Category"].ToString());
            def.ItemOrder = PIn.Int(table.Rows[i]["ItemOrder"].ToString());
            def.ItemName = PIn.String(table.Rows[i]["ItemName"].ToString());
            def.ItemValue = PIn.String(table.Rows[i]["ItemValue"].ToString());
            def.ItemColor = Color.FromArgb(PIn.Int(table.Rows[i]["ItemColor"].ToString()));
            def.IsHidden = PIn.Bool(table.Rows[i]["IsHidden"].ToString());
            retVal.Add(def);
        }
        return retVal;
    }

    /**
    * Inserts one Def into the database.  Returns the new priKey.
    */
    public static long insert(Def def) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            def.DefNum = DbHelper.GetNextOracleKey("definition", "DefNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(def,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        def.DefNum++;
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
            return insert(def,false);
        } 
    }

    /**
    * Inserts one Def into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Def def, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            def.DefNum = ReplicationServers.GetKey("definition", "DefNum");
        }
         
        String command = "INSERT INTO definition (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "DefNum,";
        }
         
        command += "Category,ItemOrder,ItemName,ItemValue,ItemColor,IsHidden) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(def.DefNum) + ",";
        }
         
        command += POut.Int((int)def.Category) + "," + POut.Int(def.ItemOrder) + "," + "'" + POut.String(def.ItemName) + "'," + "'" + POut.String(def.ItemValue) + "'," + POut.Int(def.ItemColor.ToArgb()) + "," + POut.Bool(def.IsHidden) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            def.DefNum = Db.NonQ(command, true);
        } 
        return def.DefNum;
    }

    /**
    * Updates one Def in the database.
    */
    public static void update(Def def) throws Exception {
        String command = "UPDATE definition SET " + "Category =  " + POut.Int((int)def.Category) + ", " + "ItemOrder=  " + POut.Int(def.ItemOrder) + ", " + "ItemName = '" + POut.String(def.ItemName) + "', " + "ItemValue= '" + POut.String(def.ItemValue) + "', " + "ItemColor=  " + POut.Int(def.ItemColor.ToArgb()) + ", " + "IsHidden =  " + POut.Bool(def.IsHidden) + " " + "WHERE DefNum = " + POut.Long(def.DefNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Def in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Def def, Def oldDef) throws Exception {
        String command = "";
        if (def.Category != oldDef.Category)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Category = " + POut.Int((int)def.Category) + "";
        }
         
        if (def.ItemOrder != oldDef.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.Int(def.ItemOrder) + "";
        }
         
        if (def.ItemName != oldDef.ItemName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemName = '" + POut.String(def.ItemName) + "'";
        }
         
        if (def.ItemValue != oldDef.ItemValue)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemValue = '" + POut.String(def.ItemValue) + "'";
        }
         
        if (def.ItemColor != oldDef.ItemColor)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemColor = " + POut.Int(def.ItemColor.ToArgb()) + "";
        }
         
        if (def.IsHidden != oldDef.IsHidden)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsHidden = " + POut.Bool(def.IsHidden) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE definition SET " + command + " WHERE DefNum = " + POut.Long(def.DefNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Def from the database.
    */
    public static void delete(long defNum) throws Exception {
        String command = "DELETE FROM definition " + "WHERE DefNum = " + POut.Long(defNum);
        Db.NonQ(command);
    }

}

