//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:04 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.OdDbType;
import OpenDentBusiness.OdSqlParameter;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class InsSubCrud   
{
    /**
    * Gets one InsSub object from the database using the primary key.  Returns null if not found.
    */
    public static InsSub selectOne(long insSubNum) throws Exception {
        String command = "SELECT * FROM inssub " + "WHERE InsSubNum = " + POut.long(insSubNum);
        List<InsSub> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one InsSub object from the database using a query.
    */
    public static InsSub selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<InsSub> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of InsSub objects from the database using a query.
    */
    public static List<InsSub> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<InsSub> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<InsSub> tableToList(DataTable table) throws Exception {
        List<InsSub> retVal = new List<InsSub>();
        InsSub insSub;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            insSub = new InsSub();
            insSub.InsSubNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
            insSub.PlanNum = PIn.Long(table.Rows[i]["PlanNum"].ToString());
            insSub.Subscriber = PIn.Long(table.Rows[i]["Subscriber"].ToString());
            insSub.DateEffective = PIn.Date(table.Rows[i]["DateEffective"].ToString());
            insSub.DateTerm = PIn.Date(table.Rows[i]["DateTerm"].ToString());
            insSub.ReleaseInfo = PIn.Bool(table.Rows[i]["ReleaseInfo"].ToString());
            insSub.AssignBen = PIn.Bool(table.Rows[i]["AssignBen"].ToString());
            insSub.SubscriberID = PIn.String(table.Rows[i]["SubscriberID"].ToString());
            insSub.BenefitNotes = PIn.String(table.Rows[i]["BenefitNotes"].ToString());
            insSub.SubscNote = PIn.String(table.Rows[i]["SubscNote"].ToString());
            retVal.Add(insSub);
        }
        return retVal;
    }

    /**
    * Inserts one InsSub into the database.  Returns the new priKey.
    */
    public static long insert(InsSub insSub) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            insSub.InsSubNum = DbHelper.getNextOracleKey("inssub","InsSubNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(insSub, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        insSub.InsSubNum++;
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
            return Insert(insSub, false);
        } 
    }

    /**
    * Inserts one InsSub into the database.  Provides option to use the existing priKey.
    */
    public static long insert(InsSub insSub, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            insSub.InsSubNum = ReplicationServers.getKey("inssub","InsSubNum");
        }
         
        String command = "INSERT INTO inssub (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "InsSubNum,";
        }
         
        command += "PlanNum,Subscriber,DateEffective,DateTerm,ReleaseInfo,AssignBen,SubscriberID,BenefitNotes,SubscNote) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(insSub.InsSubNum) + ",";
        }
         
        command += POut.long(insSub.PlanNum) + "," + POut.long(insSub.Subscriber) + "," + POut.date(insSub.DateEffective) + "," + POut.date(insSub.DateTerm) + "," + POut.bool(insSub.ReleaseInfo) + "," + POut.bool(insSub.AssignBen) + "," + "'" + POut.string(insSub.SubscriberID) + "'," + DbHelper.getParamChar() + "paramBenefitNotes," + "'" + POut.string(insSub.SubscNote) + "')";
        if (insSub.BenefitNotes == null)
        {
            insSub.BenefitNotes = "";
        }
         
        OdSqlParameter paramBenefitNotes = new OdSqlParameter("paramBenefitNotes", OdDbType.Text, insSub.BenefitNotes);
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command,paramBenefitNotes);
        }
        else
        {
            insSub.InsSubNum = Db.nonQ(command,true,paramBenefitNotes);
        } 
        return insSub.InsSubNum;
    }

    /**
    * Updates one InsSub in the database.
    */
    public static void update(InsSub insSub) throws Exception {
        String command = "UPDATE inssub SET " + "PlanNum      =  " + POut.long(insSub.PlanNum) + ", " + "Subscriber   =  " + POut.long(insSub.Subscriber) + ", " + "DateEffective=  " + POut.date(insSub.DateEffective) + ", " + "DateTerm     =  " + POut.date(insSub.DateTerm) + ", " + "ReleaseInfo  =  " + POut.bool(insSub.ReleaseInfo) + ", " + "AssignBen    =  " + POut.bool(insSub.AssignBen) + ", " + "SubscriberID = '" + POut.string(insSub.SubscriberID) + "', " + "BenefitNotes =  " + DbHelper.getParamChar() + "paramBenefitNotes, " + "SubscNote    = '" + POut.string(insSub.SubscNote) + "' " + "WHERE InsSubNum = " + POut.long(insSub.InsSubNum);
        if (insSub.BenefitNotes == null)
        {
            insSub.BenefitNotes = "";
        }
         
        OdSqlParameter paramBenefitNotes = new OdSqlParameter("paramBenefitNotes", OdDbType.Text, insSub.BenefitNotes);
        Db.nonQ(command,paramBenefitNotes);
    }

    /**
    * Updates one InsSub in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(InsSub insSub, InsSub oldInsSub) throws Exception {
        String command = "";
        if (insSub.PlanNum != oldInsSub.PlanNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PlanNum = " + POut.long(insSub.PlanNum) + "";
        }
         
        if (insSub.Subscriber != oldInsSub.Subscriber)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Subscriber = " + POut.long(insSub.Subscriber) + "";
        }
         
        if (insSub.DateEffective != oldInsSub.DateEffective)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateEffective = " + POut.date(insSub.DateEffective) + "";
        }
         
        if (insSub.DateTerm != oldInsSub.DateTerm)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateTerm = " + POut.date(insSub.DateTerm) + "";
        }
         
        if (insSub.ReleaseInfo != oldInsSub.ReleaseInfo)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ReleaseInfo = " + POut.bool(insSub.ReleaseInfo) + "";
        }
         
        if (insSub.AssignBen != oldInsSub.AssignBen)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AssignBen = " + POut.bool(insSub.AssignBen) + "";
        }
         
        if (!StringSupport.equals(insSub.SubscriberID, oldInsSub.SubscriberID))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SubscriberID = '" + POut.string(insSub.SubscriberID) + "'";
        }
         
        if (!StringSupport.equals(insSub.BenefitNotes, oldInsSub.BenefitNotes))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "BenefitNotes = " + DbHelper.getParamChar() + "paramBenefitNotes";
        }
         
        if (!StringSupport.equals(insSub.SubscNote, oldInsSub.SubscNote))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SubscNote = '" + POut.string(insSub.SubscNote) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (insSub.BenefitNotes == null)
        {
            insSub.BenefitNotes = "";
        }
         
        OdSqlParameter paramBenefitNotes = new OdSqlParameter("paramBenefitNotes", OdDbType.Text, insSub.BenefitNotes);
        command = "UPDATE inssub SET " + command + " WHERE InsSubNum = " + POut.long(insSub.InsSubNum);
        Db.nonQ(command,paramBenefitNotes);
    }

    /**
    * Deletes one InsSub from the database.
    */
    public static void delete(long insSubNum) throws Exception {
        String command = "DELETE FROM inssub " + "WHERE InsSubNum = " + POut.long(insSubNum);
        Db.nonQ(command);
    }

}


