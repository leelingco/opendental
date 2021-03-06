//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrSummaryCcdCrud   
{
    /**
    * Gets one EhrSummaryCcd object from the database using the primary key.  Returns null if not found.
    */
    public static EhrSummaryCcd selectOne(long ehrSummaryCcdNum) throws Exception {
        String command = "SELECT * FROM ehrsummaryccd " + "WHERE EhrSummaryCcdNum = " + POut.Long(ehrSummaryCcdNum);
        List<EhrSummaryCcd> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EhrSummaryCcd object from the database using a query.
    */
    public static EhrSummaryCcd selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrSummaryCcd> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EhrSummaryCcd objects from the database using a query.
    */
    public static List<EhrSummaryCcd> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrSummaryCcd> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrSummaryCcd> tableToList(DataTable table) throws Exception {
        List<EhrSummaryCcd> retVal = new List<EhrSummaryCcd>();
        EhrSummaryCcd ehrSummaryCcd = new EhrSummaryCcd();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ehrSummaryCcd = new EhrSummaryCcd();
            ehrSummaryCcd.EhrSummaryCcdNum = PIn.Long(table.Rows[i]["EhrSummaryCcdNum"].ToString());
            ehrSummaryCcd.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            ehrSummaryCcd.DateSummary = PIn.Date(table.Rows[i]["DateSummary"].ToString());
            ehrSummaryCcd.ContentSummary = PIn.String(table.Rows[i]["ContentSummary"].ToString());
            ehrSummaryCcd.EmailAttachNum = PIn.Long(table.Rows[i]["EmailAttachNum"].ToString());
            retVal.Add(ehrSummaryCcd);
        }
        return retVal;
    }

    /**
    * Inserts one EhrSummaryCcd into the database.  Returns the new priKey.
    */
    public static long insert(EhrSummaryCcd ehrSummaryCcd) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            ehrSummaryCcd.EhrSummaryCcdNum = DbHelper.GetNextOracleKey("ehrsummaryccd", "EhrSummaryCcdNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(ehrSummaryCcd,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        ehrSummaryCcd.EhrSummaryCcdNum++;
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
            return insert(ehrSummaryCcd,false);
        } 
    }

    /**
    * Inserts one EhrSummaryCcd into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrSummaryCcd ehrSummaryCcd, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            ehrSummaryCcd.EhrSummaryCcdNum = ReplicationServers.GetKey("ehrsummaryccd", "EhrSummaryCcdNum");
        }
         
        String command = "INSERT INTO ehrsummaryccd (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "EhrSummaryCcdNum,";
        }
         
        command += "PatNum,DateSummary,ContentSummary,EmailAttachNum) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(ehrSummaryCcd.EhrSummaryCcdNum) + ",";
        }
         
        command += POut.Long(ehrSummaryCcd.PatNum) + "," + POut.Date(ehrSummaryCcd.DateSummary) + "," + DbHelper.ParamChar + "paramContentSummary," + POut.Long(ehrSummaryCcd.EmailAttachNum) + ")";
        if (ehrSummaryCcd.ContentSummary == null)
        {
            ehrSummaryCcd.ContentSummary = "";
        }
         
        OdSqlParameter paramContentSummary = new OdSqlParameter("paramContentSummary", OdDbType.Text, ehrSummaryCcd.ContentSummary);
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command, paramContentSummary);
        }
        else
        {
            ehrSummaryCcd.EhrSummaryCcdNum = Db.NonQ(command, true, paramContentSummary);
        } 
        return ehrSummaryCcd.EhrSummaryCcdNum;
    }

    /**
    * Updates one EhrSummaryCcd in the database.
    */
    public static void update(EhrSummaryCcd ehrSummaryCcd) throws Exception {
        String command = "UPDATE ehrsummaryccd SET " + "PatNum          =  " + POut.Long(ehrSummaryCcd.PatNum) + ", " + "DateSummary     =  " + POut.Date(ehrSummaryCcd.DateSummary) + ", " + "ContentSummary  =  " + DbHelper.ParamChar + "paramContentSummary, " + "EmailAttachNum  =  " + POut.Long(ehrSummaryCcd.EmailAttachNum) + " " + "WHERE EhrSummaryCcdNum = " + POut.Long(ehrSummaryCcd.EhrSummaryCcdNum);
        if (ehrSummaryCcd.ContentSummary == null)
        {
            ehrSummaryCcd.ContentSummary = "";
        }
         
        OdSqlParameter paramContentSummary = new OdSqlParameter("paramContentSummary", OdDbType.Text, ehrSummaryCcd.ContentSummary);
        Db.NonQ(command, paramContentSummary);
    }

    /**
    * Updates one EhrSummaryCcd in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EhrSummaryCcd ehrSummaryCcd, EhrSummaryCcd oldEhrSummaryCcd) throws Exception {
        String command = "";
        if (ehrSummaryCcd.PatNum != oldEhrSummaryCcd.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(ehrSummaryCcd.PatNum) + "";
        }
         
        if (ehrSummaryCcd.DateSummary != oldEhrSummaryCcd.DateSummary)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateSummary = " + POut.Date(ehrSummaryCcd.DateSummary) + "";
        }
         
        if (ehrSummaryCcd.ContentSummary != oldEhrSummaryCcd.ContentSummary)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ContentSummary = " + DbHelper.ParamChar + "paramContentSummary";
        }
         
        if (ehrSummaryCcd.EmailAttachNum != oldEhrSummaryCcd.EmailAttachNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EmailAttachNum = " + POut.Long(ehrSummaryCcd.EmailAttachNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (ehrSummaryCcd.ContentSummary == null)
        {
            ehrSummaryCcd.ContentSummary = "";
        }
         
        OdSqlParameter paramContentSummary = new OdSqlParameter("paramContentSummary", OdDbType.Text, ehrSummaryCcd.ContentSummary);
        command = "UPDATE ehrsummaryccd SET " + command + " WHERE EhrSummaryCcdNum = " + POut.Long(ehrSummaryCcd.EhrSummaryCcdNum);
        Db.NonQ(command, paramContentSummary);
    }

    /**
    * Deletes one EhrSummaryCcd from the database.
    */
    public static void delete(long ehrSummaryCcdNum) throws Exception {
        String command = "DELETE FROM ehrsummaryccd " + "WHERE EhrSummaryCcdNum = " + POut.Long(ehrSummaryCcdNum);
        Db.NonQ(command);
    }

}


