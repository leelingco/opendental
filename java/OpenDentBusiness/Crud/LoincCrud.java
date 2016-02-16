//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:05 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class LoincCrud   
{
    /**
    * Gets one Loinc object from the database using the primary key.  Returns null if not found.
    */
    public static Loinc selectOne(long loincNum) throws Exception {
        String command = "SELECT * FROM loinc " + "WHERE LoincNum = " + POut.long(loincNum);
        List<Loinc> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Loinc object from the database using a query.
    */
    public static Loinc selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Loinc> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Loinc objects from the database using a query.
    */
    public static List<Loinc> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Loinc> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Loinc> tableToList(DataTable table) throws Exception {
        List<Loinc> retVal = new List<Loinc>();
        Loinc loinc;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            loinc = new Loinc();
            loinc.LoincNum = PIn.Long(table.Rows[i]["LoincNum"].ToString());
            loinc.LoincCode = PIn.String(table.Rows[i]["LoincCode"].ToString());
            loinc.Component = PIn.String(table.Rows[i]["Component"].ToString());
            loinc.PropertyObserved = PIn.String(table.Rows[i]["PropertyObserved"].ToString());
            loinc.TimeAspct = PIn.String(table.Rows[i]["TimeAspct"].ToString());
            loinc.SystemMeasured = PIn.String(table.Rows[i]["SystemMeasured"].ToString());
            loinc.ScaleType = PIn.String(table.Rows[i]["ScaleType"].ToString());
            loinc.MethodType = PIn.String(table.Rows[i]["MethodType"].ToString());
            loinc.StatusOfCode = PIn.String(table.Rows[i]["StatusOfCode"].ToString());
            loinc.NameShort = PIn.String(table.Rows[i]["NameShort"].ToString());
            loinc.ClassType = PIn.String(table.Rows[i]["ClassType"].ToString());
            loinc.UnitsRequired = PIn.Bool(table.Rows[i]["UnitsRequired"].ToString());
            loinc.OrderObs = PIn.String(table.Rows[i]["OrderObs"].ToString());
            loinc.HL7FieldSubfieldID = PIn.String(table.Rows[i]["HL7FieldSubfieldID"].ToString());
            loinc.ExternalCopyrightNotice = PIn.String(table.Rows[i]["ExternalCopyrightNotice"].ToString());
            loinc.NameLongCommon = PIn.String(table.Rows[i]["NameLongCommon"].ToString());
            loinc.UnitsUCUM = PIn.String(table.Rows[i]["UnitsUCUM"].ToString());
            loinc.RankCommonTests = PIn.Int(table.Rows[i]["RankCommonTests"].ToString());
            loinc.RankCommonOrders = PIn.Int(table.Rows[i]["RankCommonOrders"].ToString());
            retVal.Add(loinc);
        }
        return retVal;
    }

    /**
    * Inserts one Loinc into the database.  Returns the new priKey.
    */
    public static long insert(Loinc loinc) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            loinc.LoincNum = DbHelper.getNextOracleKey("loinc","LoincNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(loinc, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        loinc.LoincNum++;
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
            return Insert(loinc, false);
        } 
    }

    /**
    * Inserts one Loinc into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Loinc loinc, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            loinc.LoincNum = ReplicationServers.getKey("loinc","LoincNum");
        }
         
        String command = "INSERT INTO loinc (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "LoincNum,";
        }
         
        command += "LoincCode,Component,PropertyObserved,TimeAspct,SystemMeasured,ScaleType,MethodType,StatusOfCode,NameShort,ClassType,UnitsRequired,OrderObs,HL7FieldSubfieldID,ExternalCopyrightNotice,NameLongCommon,UnitsUCUM,RankCommonTests,RankCommonOrders) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(loinc.LoincNum) + ",";
        }
         
        command += "'" + POut.string(loinc.LoincCode) + "'," + "'" + POut.string(loinc.Component) + "'," + "'" + POut.string(loinc.PropertyObserved) + "'," + "'" + POut.string(loinc.TimeAspct) + "'," + "'" + POut.string(loinc.SystemMeasured) + "'," + "'" + POut.string(loinc.ScaleType) + "'," + "'" + POut.string(loinc.MethodType) + "'," + "'" + POut.string(loinc.StatusOfCode) + "'," + "'" + POut.string(loinc.NameShort) + "'," + "'" + POut.string(loinc.ClassType) + "'," + POut.bool(loinc.UnitsRequired) + "," + "'" + POut.string(loinc.OrderObs) + "'," + "'" + POut.string(loinc.HL7FieldSubfieldID) + "'," + "'" + POut.string(loinc.ExternalCopyrightNotice) + "'," + "'" + POut.string(loinc.NameLongCommon) + "'," + "'" + POut.string(loinc.UnitsUCUM) + "'," + POut.int(loinc.RankCommonTests) + "," + POut.int(loinc.RankCommonOrders) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            loinc.LoincNum = Db.nonQ(command,true);
        } 
        return loinc.LoincNum;
    }

    /**
    * Updates one Loinc in the database.
    */
    public static void update(Loinc loinc) throws Exception {
        String command = "UPDATE loinc SET " + "LoincCode              = '" + POut.string(loinc.LoincCode) + "', " + "Component              = '" + POut.string(loinc.Component) + "', " + "PropertyObserved       = '" + POut.string(loinc.PropertyObserved) + "', " + "TimeAspct              = '" + POut.string(loinc.TimeAspct) + "', " + "SystemMeasured         = '" + POut.string(loinc.SystemMeasured) + "', " + "ScaleType              = '" + POut.string(loinc.ScaleType) + "', " + "MethodType             = '" + POut.string(loinc.MethodType) + "', " + "StatusOfCode           = '" + POut.string(loinc.StatusOfCode) + "', " + "NameShort              = '" + POut.string(loinc.NameShort) + "', " + "ClassType              = '" + POut.string(loinc.ClassType) + "', " + "UnitsRequired          =  " + POut.bool(loinc.UnitsRequired) + ", " + "OrderObs               = '" + POut.string(loinc.OrderObs) + "', " + "HL7FieldSubfieldID     = '" + POut.string(loinc.HL7FieldSubfieldID) + "', " + "ExternalCopyrightNotice= '" + POut.string(loinc.ExternalCopyrightNotice) + "', " + "NameLongCommon         = '" + POut.string(loinc.NameLongCommon) + "', " + "UnitsUCUM              = '" + POut.string(loinc.UnitsUCUM) + "', " + "RankCommonTests        =  " + POut.int(loinc.RankCommonTests) + ", " + "RankCommonOrders       =  " + POut.int(loinc.RankCommonOrders) + " " + "WHERE LoincNum = " + POut.long(loinc.LoincNum);
        Db.nonQ(command);
    }

    /**
    * Updates one Loinc in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Loinc loinc, Loinc oldLoinc) throws Exception {
        String command = "";
        if (!StringSupport.equals(loinc.LoincCode, oldLoinc.LoincCode))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LoincCode = '" + POut.string(loinc.LoincCode) + "'";
        }
         
        if (!StringSupport.equals(loinc.Component, oldLoinc.Component))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Component = '" + POut.string(loinc.Component) + "'";
        }
         
        if (!StringSupport.equals(loinc.PropertyObserved, oldLoinc.PropertyObserved))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PropertyObserved = '" + POut.string(loinc.PropertyObserved) + "'";
        }
         
        if (!StringSupport.equals(loinc.TimeAspct, oldLoinc.TimeAspct))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TimeAspct = '" + POut.string(loinc.TimeAspct) + "'";
        }
         
        if (!StringSupport.equals(loinc.SystemMeasured, oldLoinc.SystemMeasured))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SystemMeasured = '" + POut.string(loinc.SystemMeasured) + "'";
        }
         
        if (!StringSupport.equals(loinc.ScaleType, oldLoinc.ScaleType))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ScaleType = '" + POut.string(loinc.ScaleType) + "'";
        }
         
        if (!StringSupport.equals(loinc.MethodType, oldLoinc.MethodType))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MethodType = '" + POut.string(loinc.MethodType) + "'";
        }
         
        if (!StringSupport.equals(loinc.StatusOfCode, oldLoinc.StatusOfCode))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "StatusOfCode = '" + POut.string(loinc.StatusOfCode) + "'";
        }
         
        if (!StringSupport.equals(loinc.NameShort, oldLoinc.NameShort))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "NameShort = '" + POut.string(loinc.NameShort) + "'";
        }
         
        if (!StringSupport.equals(loinc.ClassType, oldLoinc.ClassType))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClassType = '" + POut.string(loinc.ClassType) + "'";
        }
         
        if (loinc.UnitsRequired != oldLoinc.UnitsRequired)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UnitsRequired = " + POut.bool(loinc.UnitsRequired) + "";
        }
         
        if (!StringSupport.equals(loinc.OrderObs, oldLoinc.OrderObs))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OrderObs = '" + POut.string(loinc.OrderObs) + "'";
        }
         
        if (!StringSupport.equals(loinc.HL7FieldSubfieldID, oldLoinc.HL7FieldSubfieldID))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "HL7FieldSubfieldID = '" + POut.string(loinc.HL7FieldSubfieldID) + "'";
        }
         
        if (!StringSupport.equals(loinc.ExternalCopyrightNotice, oldLoinc.ExternalCopyrightNotice))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ExternalCopyrightNotice = '" + POut.string(loinc.ExternalCopyrightNotice) + "'";
        }
         
        if (!StringSupport.equals(loinc.NameLongCommon, oldLoinc.NameLongCommon))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "NameLongCommon = '" + POut.string(loinc.NameLongCommon) + "'";
        }
         
        if (!StringSupport.equals(loinc.UnitsUCUM, oldLoinc.UnitsUCUM))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UnitsUCUM = '" + POut.string(loinc.UnitsUCUM) + "'";
        }
         
        if (loinc.RankCommonTests != oldLoinc.RankCommonTests)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RankCommonTests = " + POut.int(loinc.RankCommonTests) + "";
        }
         
        if (loinc.RankCommonOrders != oldLoinc.RankCommonOrders)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RankCommonOrders = " + POut.int(loinc.RankCommonOrders) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE loinc SET " + command + " WHERE LoincNum = " + POut.long(loinc.LoincNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Loinc from the database.
    */
    public static void delete(long loincNum) throws Exception {
        String command = "DELETE FROM loinc " + "WHERE LoincNum = " + POut.long(loincNum);
        Db.nonQ(command);
    }

}


