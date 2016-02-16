//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:01 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.AmendmentSource;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.EhrAmendment;
import OpenDentBusiness.OdDbType;
import OpenDentBusiness.OdSqlParameter;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;
import OpenDentBusiness.YN;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrAmendmentCrud   
{
    /**
    * Gets one EhrAmendment object from the database using the primary key.  Returns null if not found.
    */
    public static EhrAmendment selectOne(long ehrAmendmentNum) throws Exception {
        String command = "SELECT * FROM ehramendment " + "WHERE EhrAmendmentNum = " + POut.long(ehrAmendmentNum);
        List<EhrAmendment> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EhrAmendment object from the database using a query.
    */
    public static EhrAmendment selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrAmendment> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EhrAmendment objects from the database using a query.
    */
    public static List<EhrAmendment> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrAmendment> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrAmendment> tableToList(DataTable table) throws Exception {
        List<EhrAmendment> retVal = new List<EhrAmendment>();
        EhrAmendment ehrAmendment;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ehrAmendment = new EhrAmendment();
            ehrAmendment.EhrAmendmentNum = PIn.Long(table.Rows[i]["EhrAmendmentNum"].ToString());
            ehrAmendment.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            ehrAmendment.IsAccepted = (YN)PIn.Int(table.Rows[i]["IsAccepted"].ToString());
            ehrAmendment.Description = PIn.String(table.Rows[i]["Description"].ToString());
            ehrAmendment.Source = (AmendmentSource)PIn.Int(table.Rows[i]["Source"].ToString());
            ehrAmendment.SourceName = PIn.String(table.Rows[i]["SourceName"].ToString());
            ehrAmendment.FileName = PIn.String(table.Rows[i]["FileName"].ToString());
            ehrAmendment.RawBase64 = PIn.String(table.Rows[i]["RawBase64"].ToString());
            ehrAmendment.DateTRequest = PIn.DateT(table.Rows[i]["DateTRequest"].ToString());
            ehrAmendment.DateTAcceptDeny = PIn.DateT(table.Rows[i]["DateTAcceptDeny"].ToString());
            ehrAmendment.DateTAppend = PIn.DateT(table.Rows[i]["DateTAppend"].ToString());
            retVal.Add(ehrAmendment);
        }
        return retVal;
    }

    /**
    * Inserts one EhrAmendment into the database.  Returns the new priKey.
    */
    public static long insert(EhrAmendment ehrAmendment) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            ehrAmendment.EhrAmendmentNum = DbHelper.getNextOracleKey("ehramendment","EhrAmendmentNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(ehrAmendment, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        ehrAmendment.EhrAmendmentNum++;
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
            return Insert(ehrAmendment, false);
        } 
    }

    /**
    * Inserts one EhrAmendment into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrAmendment ehrAmendment, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            ehrAmendment.EhrAmendmentNum = ReplicationServers.getKey("ehramendment","EhrAmendmentNum");
        }
         
        String command = "INSERT INTO ehramendment (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "EhrAmendmentNum,";
        }
         
        command += "PatNum,IsAccepted,Description,Source,SourceName,FileName,RawBase64,DateTRequest,DateTAcceptDeny,DateTAppend) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(ehrAmendment.EhrAmendmentNum) + ",";
        }
         
        command += POut.long(ehrAmendment.PatNum) + "," + POut.int(((Enum)ehrAmendment.IsAccepted).ordinal()) + "," + "'" + POut.string(ehrAmendment.Description) + "'," + POut.int(((Enum)ehrAmendment.Source).ordinal()) + "," + "'" + POut.string(ehrAmendment.SourceName) + "'," + "'" + POut.string(ehrAmendment.FileName) + "'," + DbHelper.getParamChar() + "paramRawBase64," + POut.dateT(ehrAmendment.DateTRequest) + "," + POut.dateT(ehrAmendment.DateTAcceptDeny) + "," + POut.dateT(ehrAmendment.DateTAppend) + ")";
        if (ehrAmendment.RawBase64 == null)
        {
            ehrAmendment.RawBase64 = "";
        }
         
        OdSqlParameter paramRawBase64 = new OdSqlParameter("paramRawBase64", OdDbType.Text, ehrAmendment.RawBase64);
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command,paramRawBase64);
        }
        else
        {
            ehrAmendment.EhrAmendmentNum = Db.nonQ(command,true,paramRawBase64);
        } 
        return ehrAmendment.EhrAmendmentNum;
    }

    /**
    * Updates one EhrAmendment in the database.
    */
    public static void update(EhrAmendment ehrAmendment) throws Exception {
        String command = "UPDATE ehramendment SET " + "PatNum         =  " + POut.long(ehrAmendment.PatNum) + ", " + "IsAccepted     =  " + POut.int(((Enum)ehrAmendment.IsAccepted).ordinal()) + ", " + "Description    = '" + POut.string(ehrAmendment.Description) + "', " + "Source         =  " + POut.int(((Enum)ehrAmendment.Source).ordinal()) + ", " + "SourceName     = '" + POut.string(ehrAmendment.SourceName) + "', " + "FileName       = '" + POut.string(ehrAmendment.FileName) + "', " + "RawBase64      =  " + DbHelper.getParamChar() + "paramRawBase64, " + "DateTRequest   =  " + POut.dateT(ehrAmendment.DateTRequest) + ", " + "DateTAcceptDeny=  " + POut.dateT(ehrAmendment.DateTAcceptDeny) + ", " + "DateTAppend    =  " + POut.dateT(ehrAmendment.DateTAppend) + " " + "WHERE EhrAmendmentNum = " + POut.long(ehrAmendment.EhrAmendmentNum);
        if (ehrAmendment.RawBase64 == null)
        {
            ehrAmendment.RawBase64 = "";
        }
         
        OdSqlParameter paramRawBase64 = new OdSqlParameter("paramRawBase64", OdDbType.Text, ehrAmendment.RawBase64);
        Db.nonQ(command,paramRawBase64);
    }

    /**
    * Updates one EhrAmendment in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EhrAmendment ehrAmendment, EhrAmendment oldEhrAmendment) throws Exception {
        String command = "";
        if (ehrAmendment.PatNum != oldEhrAmendment.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.long(ehrAmendment.PatNum) + "";
        }
         
        if (ehrAmendment.IsAccepted != oldEhrAmendment.IsAccepted)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsAccepted = " + POut.int(((Enum)ehrAmendment.IsAccepted).ordinal()) + "";
        }
         
        if (!StringSupport.equals(ehrAmendment.Description, oldEhrAmendment.Description))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.string(ehrAmendment.Description) + "'";
        }
         
        if (ehrAmendment.Source != oldEhrAmendment.Source)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Source = " + POut.int(((Enum)ehrAmendment.Source).ordinal()) + "";
        }
         
        if (!StringSupport.equals(ehrAmendment.SourceName, oldEhrAmendment.SourceName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SourceName = '" + POut.string(ehrAmendment.SourceName) + "'";
        }
         
        if (!StringSupport.equals(ehrAmendment.FileName, oldEhrAmendment.FileName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FileName = '" + POut.string(ehrAmendment.FileName) + "'";
        }
         
        if (!StringSupport.equals(ehrAmendment.RawBase64, oldEhrAmendment.RawBase64))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RawBase64 = " + DbHelper.getParamChar() + "paramRawBase64";
        }
         
        if (ehrAmendment.DateTRequest != oldEhrAmendment.DateTRequest)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateTRequest = " + POut.dateT(ehrAmendment.DateTRequest) + "";
        }
         
        if (ehrAmendment.DateTAcceptDeny != oldEhrAmendment.DateTAcceptDeny)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateTAcceptDeny = " + POut.dateT(ehrAmendment.DateTAcceptDeny) + "";
        }
         
        if (ehrAmendment.DateTAppend != oldEhrAmendment.DateTAppend)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateTAppend = " + POut.dateT(ehrAmendment.DateTAppend) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (ehrAmendment.RawBase64 == null)
        {
            ehrAmendment.RawBase64 = "";
        }
         
        OdSqlParameter paramRawBase64 = new OdSqlParameter("paramRawBase64", OdDbType.Text, ehrAmendment.RawBase64);
        command = "UPDATE ehramendment SET " + command + " WHERE EhrAmendmentNum = " + POut.long(ehrAmendment.EhrAmendmentNum);
        Db.nonQ(command,paramRawBase64);
    }

    /**
    * Deletes one EhrAmendment from the database.
    */
    public static void delete(long ehrAmendmentNum) throws Exception {
        String command = "DELETE FROM ehramendment " + "WHERE EhrAmendmentNum = " + POut.long(ehrAmendmentNum);
        Db.nonQ(command);
    }

}

