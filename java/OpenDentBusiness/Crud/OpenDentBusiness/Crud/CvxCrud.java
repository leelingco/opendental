//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:58 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class CvxCrud   
{
    /**
    * Gets one Cvx object from the database using the primary key.  Returns null if not found.
    */
    public static Cvx selectOne(long cvxNum) throws Exception {
        String command = "SELECT * FROM cvx " + "WHERE CvxNum = " + POut.Long(cvxNum);
        List<Cvx> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Cvx object from the database using a query.
    */
    public static Cvx selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Cvx> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Cvx objects from the database using a query.
    */
    public static List<Cvx> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Cvx> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Cvx> tableToList(DataTable table) throws Exception {
        List<Cvx> retVal = new List<Cvx>();
        Cvx cvx = new Cvx();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            cvx = new Cvx();
            cvx.CvxNum = PIn.Long(table.Rows[i]["CvxNum"].ToString());
            cvx.CvxCode = PIn.String(table.Rows[i]["CvxCode"].ToString());
            cvx.Description = PIn.String(table.Rows[i]["Description"].ToString());
            cvx.IsActive = PIn.String(table.Rows[i]["IsActive"].ToString());
            retVal.Add(cvx);
        }
        return retVal;
    }

    /**
    * Inserts one Cvx into the database.  Returns the new priKey.
    */
    public static long insert(Cvx cvx) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            cvx.CvxNum = DbHelper.GetNextOracleKey("cvx", "CvxNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(cvx,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        cvx.CvxNum++;
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
            return insert(cvx,false);
        } 
    }

    /**
    * Inserts one Cvx into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Cvx cvx, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            cvx.CvxNum = ReplicationServers.GetKey("cvx", "CvxNum");
        }
         
        String command = "INSERT INTO cvx (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "CvxNum,";
        }
         
        command += "CvxCode,Description,IsActive) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(cvx.CvxNum) + ",";
        }
         
        command += "'" + POut.String(cvx.CvxCode) + "'," + "'" + POut.String(cvx.Description) + "'," + "'" + POut.String(cvx.IsActive) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            cvx.CvxNum = Db.NonQ(command, true);
        } 
        return cvx.CvxNum;
    }

    /**
    * Updates one Cvx in the database.
    */
    public static void update(Cvx cvx) throws Exception {
        String command = "UPDATE cvx SET " + "CvxCode    = '" + POut.String(cvx.CvxCode) + "', " + "Description= '" + POut.String(cvx.Description) + "', " + "IsActive   = '" + POut.String(cvx.IsActive) + "' " + "WHERE CvxNum = " + POut.Long(cvx.CvxNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Cvx in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Cvx cvx, Cvx oldCvx) throws Exception {
        String command = "";
        if (cvx.CvxCode != oldCvx.CvxCode)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CvxCode = '" + POut.String(cvx.CvxCode) + "'";
        }
         
        if (cvx.Description != oldCvx.Description)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.String(cvx.Description) + "'";
        }
         
        if (cvx.IsActive != oldCvx.IsActive)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsActive = '" + POut.String(cvx.IsActive) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE cvx SET " + command + " WHERE CvxNum = " + POut.Long(cvx.CvxNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Cvx from the database.
    */
    public static void delete(long cvxNum) throws Exception {
        String command = "DELETE FROM cvx " + "WHERE CvxNum = " + POut.Long(cvxNum);
        Db.NonQ(command);
    }

}

