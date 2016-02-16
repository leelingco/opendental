//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:01 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class DiseaseDefCrud   
{
    /**
    * Gets one DiseaseDef object from the database using the primary key.  Returns null if not found.
    */
    public static DiseaseDef selectOne(long diseaseDefNum) throws Exception {
        String command = "SELECT * FROM diseasedef " + "WHERE DiseaseDefNum = " + POut.long(diseaseDefNum);
        List<DiseaseDef> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one DiseaseDef object from the database using a query.
    */
    public static DiseaseDef selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<DiseaseDef> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of DiseaseDef objects from the database using a query.
    */
    public static List<DiseaseDef> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<DiseaseDef> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<DiseaseDef> tableToList(DataTable table) throws Exception {
        List<DiseaseDef> retVal = new List<DiseaseDef>();
        DiseaseDef diseaseDef;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            diseaseDef = new DiseaseDef();
            diseaseDef.DiseaseDefNum = PIn.Long(table.Rows[i]["DiseaseDefNum"].ToString());
            diseaseDef.DiseaseName = PIn.String(table.Rows[i]["DiseaseName"].ToString());
            diseaseDef.ItemOrder = PIn.Int(table.Rows[i]["ItemOrder"].ToString());
            diseaseDef.IsHidden = PIn.Bool(table.Rows[i]["IsHidden"].ToString());
            diseaseDef.DateTStamp = PIn.DateT(table.Rows[i]["DateTStamp"].ToString());
            diseaseDef.ICD9Code = PIn.String(table.Rows[i]["ICD9Code"].ToString());
            diseaseDef.SnomedCode = PIn.String(table.Rows[i]["SnomedCode"].ToString());
            diseaseDef.Icd10Code = PIn.String(table.Rows[i]["Icd10Code"].ToString());
            retVal.Add(diseaseDef);
        }
        return retVal;
    }

    /**
    * Inserts one DiseaseDef into the database.  Returns the new priKey.
    */
    public static long insert(DiseaseDef diseaseDef) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            diseaseDef.DiseaseDefNum = DbHelper.getNextOracleKey("diseasedef","DiseaseDefNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(diseaseDef, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        diseaseDef.DiseaseDefNum++;
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
            return Insert(diseaseDef, false);
        } 
    }

    /**
    * Inserts one DiseaseDef into the database.  Provides option to use the existing priKey.
    */
    public static long insert(DiseaseDef diseaseDef, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            diseaseDef.DiseaseDefNum = ReplicationServers.getKey("diseasedef","DiseaseDefNum");
        }
         
        String command = "INSERT INTO diseasedef (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "DiseaseDefNum,";
        }
         
        command += "DiseaseName,ItemOrder,IsHidden,ICD9Code,SnomedCode,Icd10Code) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(diseaseDef.DiseaseDefNum) + ",";
        }
         
        //DateTStamp can only be set by MySQL
        command += "'" + POut.string(diseaseDef.DiseaseName) + "'," + POut.int(diseaseDef.ItemOrder) + "," + POut.bool(diseaseDef.IsHidden) + "," + "'" + POut.string(diseaseDef.ICD9Code) + "'," + "'" + POut.string(diseaseDef.SnomedCode) + "'," + "'" + POut.string(diseaseDef.Icd10Code) + "')";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            diseaseDef.DiseaseDefNum = Db.nonQ(command,true);
        } 
        return diseaseDef.DiseaseDefNum;
    }

    /**
    * Updates one DiseaseDef in the database.
    */
    public static void update(DiseaseDef diseaseDef) throws Exception {
        //DateTStamp can only be set by MySQL
        String command = "UPDATE diseasedef SET " + "DiseaseName  = '" + POut.string(diseaseDef.DiseaseName) + "', " + "ItemOrder    =  " + POut.int(diseaseDef.ItemOrder) + ", " + "IsHidden     =  " + POut.bool(diseaseDef.IsHidden) + ", " + "ICD9Code     = '" + POut.string(diseaseDef.ICD9Code) + "', " + "SnomedCode   = '" + POut.string(diseaseDef.SnomedCode) + "', " + "Icd10Code    = '" + POut.string(diseaseDef.Icd10Code) + "' " + "WHERE DiseaseDefNum = " + POut.long(diseaseDef.DiseaseDefNum);
        Db.nonQ(command);
    }

    /**
    * Updates one DiseaseDef in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(DiseaseDef diseaseDef, DiseaseDef oldDiseaseDef) throws Exception {
        String command = "";
        if (!StringSupport.equals(diseaseDef.DiseaseName, oldDiseaseDef.DiseaseName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DiseaseName = '" + POut.string(diseaseDef.DiseaseName) + "'";
        }
         
        if (diseaseDef.ItemOrder != oldDiseaseDef.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.int(diseaseDef.ItemOrder) + "";
        }
         
        if (diseaseDef.IsHidden != oldDiseaseDef.IsHidden)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsHidden = " + POut.bool(diseaseDef.IsHidden) + "";
        }
         
        //DateTStamp can only be set by MySQL
        if (!StringSupport.equals(diseaseDef.ICD9Code, oldDiseaseDef.ICD9Code))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ICD9Code = '" + POut.string(diseaseDef.ICD9Code) + "'";
        }
         
        if (!StringSupport.equals(diseaseDef.SnomedCode, oldDiseaseDef.SnomedCode))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SnomedCode = '" + POut.string(diseaseDef.SnomedCode) + "'";
        }
         
        if (!StringSupport.equals(diseaseDef.Icd10Code, oldDiseaseDef.Icd10Code))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Icd10Code = '" + POut.string(diseaseDef.Icd10Code) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE diseasedef SET " + command + " WHERE DiseaseDefNum = " + POut.long(diseaseDef.DiseaseDefNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one DiseaseDef from the database.
    */
    public static void delete(long diseaseDefNum) throws Exception {
        String command = "DELETE FROM diseasedef " + "WHERE DiseaseDefNum = " + POut.long(diseaseDefNum);
        Db.nonQ(command);
    }

}


