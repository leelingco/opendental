//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Def;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class DefCrud   
{
    /**
    * Gets one Def object from the database using the primary key.  Returns null if not found.
    */
    public static Def selectOne(long defNum) throws Exception {
        String command = "SELECT * FROM definition " + "WHERE DefNum = " + POut.long(defNum);
        List<Def> list = tableToList(Db.getTable(command));
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
         
        List<Def> list = tableToList(Db.getTable(command));
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
         
        List<Def> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Def> tableToList(DataTable table) throws Exception {
        List<Def> retVal = new List<Def>();
        Def def;
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
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            def.DefNum = DbHelper.getNextOracleKey("definition","DefNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(def, true);
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
            return Insert(def, false);
        } 
    }

    /**
    * Inserts one Def into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Def def, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            def.DefNum = ReplicationServers.getKey("definition","DefNum");
        }
         
        String command = "INSERT INTO definition (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "DefNum,";
        }
         
        command += "Category,ItemOrder,ItemName,ItemValue,ItemColor,IsHidden) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(def.DefNum) + ",";
        }
         
        command += POut.int(((Enum)def.Category).ordinal()) + "," + POut.int(def.ItemOrder) + "," + "'" + POut.string(def.ItemName) + "'," + "'" + POut.string(def.ItemValue) + "'," + POut.Int(def.ItemColor.ToArgb()) + "," + POut.bool(def.IsHidden) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            def.DefNum = Db.nonQ(command,true);
        } 
        return def.DefNum;
    }

    /**
    * Updates one Def in the database.
    */
    public static void update(Def def) throws Exception {
        String command = "UPDATE definition SET " + "Category =  " + POut.int(((Enum)def.Category).ordinal()) + ", " + "ItemOrder=  " + POut.int(def.ItemOrder) + ", " + "ItemName = '" + POut.string(def.ItemName) + "', " + "ItemValue= '" + POut.string(def.ItemValue) + "', " + "ItemColor=  " + POut.Int(def.ItemColor.ToArgb()) + ", " + "IsHidden =  " + POut.bool(def.IsHidden) + " " + "WHERE DefNum = " + POut.long(def.DefNum);
        Db.nonQ(command);
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
             
            command += "Category = " + POut.int(((Enum)def.Category).ordinal()) + "";
        }
         
        if (def.ItemOrder != oldDef.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.int(def.ItemOrder) + "";
        }
         
        if (!StringSupport.equals(def.ItemName, oldDef.ItemName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemName = '" + POut.string(def.ItemName) + "'";
        }
         
        if (!StringSupport.equals(def.ItemValue, oldDef.ItemValue))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemValue = '" + POut.string(def.ItemValue) + "'";
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
             
            command += "IsHidden = " + POut.bool(def.IsHidden) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE definition SET " + command + " WHERE DefNum = " + POut.long(def.DefNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Def from the database.
    */
    public static void delete(long defNum) throws Exception {
        String command = "DELETE FROM definition " + "WHERE DefNum = " + POut.long(defNum);
        Db.nonQ(command);
    }

}

