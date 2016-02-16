//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:57:59 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ClaimForm;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class ClaimFormCrud   
{
    /**
    * Gets one ClaimForm object from the database using the primary key.  Returns null if not found.
    */
    public static ClaimForm selectOne(long claimFormNum) throws Exception {
        String command = "SELECT * FROM claimform " + "WHERE ClaimFormNum = " + POut.long(claimFormNum);
        List<ClaimForm> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one ClaimForm object from the database using a query.
    */
    public static ClaimForm selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ClaimForm> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of ClaimForm objects from the database using a query.
    */
    public static List<ClaimForm> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ClaimForm> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ClaimForm> tableToList(DataTable table) throws Exception {
        List<ClaimForm> retVal = new List<ClaimForm>();
        ClaimForm claimForm;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            claimForm = new ClaimForm();
            claimForm.ClaimFormNum = PIn.Long(table.Rows[i]["ClaimFormNum"].ToString());
            claimForm.Description = PIn.String(table.Rows[i]["Description"].ToString());
            claimForm.IsHidden = PIn.Bool(table.Rows[i]["IsHidden"].ToString());
            claimForm.FontName = PIn.String(table.Rows[i]["FontName"].ToString());
            claimForm.FontSize = PIn.Float(table.Rows[i]["FontSize"].ToString());
            claimForm.UniqueID = PIn.String(table.Rows[i]["UniqueID"].ToString());
            claimForm.PrintImages = PIn.Bool(table.Rows[i]["PrintImages"].ToString());
            claimForm.OffsetX = PIn.Int(table.Rows[i]["OffsetX"].ToString());
            claimForm.OffsetY = PIn.Int(table.Rows[i]["OffsetY"].ToString());
            retVal.Add(claimForm);
        }
        return retVal;
    }

    /**
    * Inserts one ClaimForm into the database.  Returns the new priKey.
    */
    public static long insert(ClaimForm claimForm) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            claimForm.ClaimFormNum = DbHelper.getNextOracleKey("claimform","ClaimFormNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(claimForm, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        claimForm.ClaimFormNum++;
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
            return Insert(claimForm, false);
        } 
    }

    /**
    * Inserts one ClaimForm into the database.  Provides option to use the existing priKey.
    */
    public static long insert(ClaimForm claimForm, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            claimForm.ClaimFormNum = ReplicationServers.getKey("claimform","ClaimFormNum");
        }
         
        String command = "INSERT INTO claimform (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "ClaimFormNum,";
        }
         
        command += "Description,IsHidden,FontName,FontSize,UniqueID,PrintImages,OffsetX,OffsetY) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(claimForm.ClaimFormNum) + ",";
        }
         
        command += "'" + POut.string(claimForm.Description) + "'," + POut.bool(claimForm.IsHidden) + "," + "'" + POut.string(claimForm.FontName) + "'," + POut.float(claimForm.FontSize) + "," + "'" + POut.string(claimForm.UniqueID) + "'," + POut.bool(claimForm.PrintImages) + "," + POut.int(claimForm.OffsetX) + "," + POut.int(claimForm.OffsetY) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            claimForm.ClaimFormNum = Db.nonQ(command,true);
        } 
        return claimForm.ClaimFormNum;
    }

    /**
    * Updates one ClaimForm in the database.
    */
    public static void update(ClaimForm claimForm) throws Exception {
        String command = "UPDATE claimform SET " + "Description = '" + POut.string(claimForm.Description) + "', " + "IsHidden    =  " + POut.bool(claimForm.IsHidden) + ", " + "FontName    = '" + POut.string(claimForm.FontName) + "', " + "FontSize    =  " + POut.float(claimForm.FontSize) + ", " + "UniqueID    = '" + POut.string(claimForm.UniqueID) + "', " + "PrintImages =  " + POut.bool(claimForm.PrintImages) + ", " + "OffsetX     =  " + POut.int(claimForm.OffsetX) + ", " + "OffsetY     =  " + POut.int(claimForm.OffsetY) + " " + "WHERE ClaimFormNum = " + POut.long(claimForm.ClaimFormNum);
        Db.nonQ(command);
    }

    /**
    * Updates one ClaimForm in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(ClaimForm claimForm, ClaimForm oldClaimForm) throws Exception {
        String command = "";
        if (!StringSupport.equals(claimForm.Description, oldClaimForm.Description))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.string(claimForm.Description) + "'";
        }
         
        if (claimForm.IsHidden != oldClaimForm.IsHidden)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsHidden = " + POut.bool(claimForm.IsHidden) + "";
        }
         
        if (!StringSupport.equals(claimForm.FontName, oldClaimForm.FontName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FontName = '" + POut.string(claimForm.FontName) + "'";
        }
         
        if (claimForm.FontSize != oldClaimForm.FontSize)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FontSize = " + POut.float(claimForm.FontSize) + "";
        }
         
        if (!StringSupport.equals(claimForm.UniqueID, oldClaimForm.UniqueID))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UniqueID = '" + POut.string(claimForm.UniqueID) + "'";
        }
         
        if (claimForm.PrintImages != oldClaimForm.PrintImages)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PrintImages = " + POut.bool(claimForm.PrintImages) + "";
        }
         
        if (claimForm.OffsetX != oldClaimForm.OffsetX)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OffsetX = " + POut.int(claimForm.OffsetX) + "";
        }
         
        if (claimForm.OffsetY != oldClaimForm.OffsetY)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OffsetY = " + POut.int(claimForm.OffsetY) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE claimform SET " + command + " WHERE ClaimFormNum = " + POut.long(claimForm.ClaimFormNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one ClaimForm from the database.
    */
    public static void delete(long claimFormNum) throws Exception {
        String command = "DELETE FROM claimform " + "WHERE ClaimFormNum = " + POut.long(claimFormNum);
        Db.nonQ(command);
    }

}


