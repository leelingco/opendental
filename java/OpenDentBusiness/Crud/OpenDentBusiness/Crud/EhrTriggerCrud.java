//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrTriggerCrud   
{
    /**
    * Gets one EhrTrigger object from the database using the primary key.  Returns null if not found.
    */
    public static EhrTrigger selectOne(long ehrTriggerNum) throws Exception {
        String command = "SELECT * FROM ehrtrigger " + "WHERE EhrTriggerNum = " + POut.Long(ehrTriggerNum);
        List<EhrTrigger> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EhrTrigger object from the database using a query.
    */
    public static EhrTrigger selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrTrigger> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EhrTrigger objects from the database using a query.
    */
    public static List<EhrTrigger> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrTrigger> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrTrigger> tableToList(DataTable table) throws Exception {
        List<EhrTrigger> retVal = new List<EhrTrigger>();
        EhrTrigger ehrTrigger = new EhrTrigger();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ehrTrigger = new EhrTrigger();
            ehrTrigger.EhrTriggerNum = PIn.Long(table.Rows[i]["EhrTriggerNum"].ToString());
            ehrTrigger.Description = PIn.String(table.Rows[i]["Description"].ToString());
            ehrTrigger.ProblemSnomedList = PIn.String(table.Rows[i]["ProblemSnomedList"].ToString());
            ehrTrigger.ProblemIcd9List = PIn.String(table.Rows[i]["ProblemIcd9List"].ToString());
            ehrTrigger.ProblemIcd10List = PIn.String(table.Rows[i]["ProblemIcd10List"].ToString());
            ehrTrigger.ProblemDefNumList = PIn.String(table.Rows[i]["ProblemDefNumList"].ToString());
            ehrTrigger.MedicationNumList = PIn.String(table.Rows[i]["MedicationNumList"].ToString());
            ehrTrigger.RxCuiList = PIn.String(table.Rows[i]["RxCuiList"].ToString());
            ehrTrigger.CvxList = PIn.String(table.Rows[i]["CvxList"].ToString());
            ehrTrigger.AllergyDefNumList = PIn.String(table.Rows[i]["AllergyDefNumList"].ToString());
            ehrTrigger.DemographicsList = PIn.String(table.Rows[i]["DemographicsList"].ToString());
            ehrTrigger.LabLoincList = PIn.String(table.Rows[i]["LabLoincList"].ToString());
            ehrTrigger.VitalLoincList = PIn.String(table.Rows[i]["VitalLoincList"].ToString());
            ehrTrigger.Instructions = PIn.String(table.Rows[i]["Instructions"].ToString());
            ehrTrigger.Bibliography = PIn.String(table.Rows[i]["Bibliography"].ToString());
            ehrTrigger.Cardinality = (MatchCardinality)PIn.Int(table.Rows[i]["Cardinality"].ToString());
            retVal.Add(ehrTrigger);
        }
        return retVal;
    }

    /**
    * Inserts one EhrTrigger into the database.  Returns the new priKey.
    */
    public static long insert(EhrTrigger ehrTrigger) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            ehrTrigger.EhrTriggerNum = DbHelper.GetNextOracleKey("ehrtrigger", "EhrTriggerNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(ehrTrigger,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        ehrTrigger.EhrTriggerNum++;
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
            return insert(ehrTrigger,false);
        } 
    }

    /**
    * Inserts one EhrTrigger into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrTrigger ehrTrigger, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            ehrTrigger.EhrTriggerNum = ReplicationServers.GetKey("ehrtrigger", "EhrTriggerNum");
        }
         
        String command = "INSERT INTO ehrtrigger (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "EhrTriggerNum,";
        }
         
        command += "Description,ProblemSnomedList,ProblemIcd9List,ProblemIcd10List,ProblemDefNumList,MedicationNumList,RxCuiList,CvxList,AllergyDefNumList,DemographicsList,LabLoincList,VitalLoincList,Instructions,Bibliography,Cardinality) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(ehrTrigger.EhrTriggerNum) + ",";
        }
         
        command += "'" + POut.String(ehrTrigger.Description) + "'," + "'" + POut.String(ehrTrigger.ProblemSnomedList) + "'," + "'" + POut.String(ehrTrigger.ProblemIcd9List) + "'," + "'" + POut.String(ehrTrigger.ProblemIcd10List) + "'," + "'" + POut.String(ehrTrigger.ProblemDefNumList) + "'," + "'" + POut.String(ehrTrigger.MedicationNumList) + "'," + "'" + POut.String(ehrTrigger.RxCuiList) + "'," + "'" + POut.String(ehrTrigger.CvxList) + "'," + "'" + POut.String(ehrTrigger.AllergyDefNumList) + "'," + "'" + POut.String(ehrTrigger.DemographicsList) + "'," + "'" + POut.String(ehrTrigger.LabLoincList) + "'," + "'" + POut.String(ehrTrigger.VitalLoincList) + "'," + "'" + POut.String(ehrTrigger.Instructions) + "'," + "'" + POut.String(ehrTrigger.Bibliography) + "'," + POut.Int((int)ehrTrigger.Cardinality) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            ehrTrigger.EhrTriggerNum = Db.NonQ(command, true);
        } 
        return ehrTrigger.EhrTriggerNum;
    }

    /**
    * Updates one EhrTrigger in the database.
    */
    public static void update(EhrTrigger ehrTrigger) throws Exception {
        String command = "UPDATE ehrtrigger SET " + "Description      = '" + POut.String(ehrTrigger.Description) + "', " + "ProblemSnomedList= '" + POut.String(ehrTrigger.ProblemSnomedList) + "', " + "ProblemIcd9List  = '" + POut.String(ehrTrigger.ProblemIcd9List) + "', " + "ProblemIcd10List = '" + POut.String(ehrTrigger.ProblemIcd10List) + "', " + "ProblemDefNumList= '" + POut.String(ehrTrigger.ProblemDefNumList) + "', " + "MedicationNumList= '" + POut.String(ehrTrigger.MedicationNumList) + "', " + "RxCuiList        = '" + POut.String(ehrTrigger.RxCuiList) + "', " + "CvxList          = '" + POut.String(ehrTrigger.CvxList) + "', " + "AllergyDefNumList= '" + POut.String(ehrTrigger.AllergyDefNumList) + "', " + "DemographicsList = '" + POut.String(ehrTrigger.DemographicsList) + "', " + "LabLoincList     = '" + POut.String(ehrTrigger.LabLoincList) + "', " + "VitalLoincList   = '" + POut.String(ehrTrigger.VitalLoincList) + "', " + "Instructions     = '" + POut.String(ehrTrigger.Instructions) + "', " + "Bibliography     = '" + POut.String(ehrTrigger.Bibliography) + "', " + "Cardinality      =  " + POut.Int((int)ehrTrigger.Cardinality) + " " + "WHERE EhrTriggerNum = " + POut.Long(ehrTrigger.EhrTriggerNum);
        Db.NonQ(command);
    }

    /**
    * Updates one EhrTrigger in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EhrTrigger ehrTrigger, EhrTrigger oldEhrTrigger) throws Exception {
        String command = "";
        if (ehrTrigger.Description != oldEhrTrigger.Description)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.String(ehrTrigger.Description) + "'";
        }
         
        if (ehrTrigger.ProblemSnomedList != oldEhrTrigger.ProblemSnomedList)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProblemSnomedList = '" + POut.String(ehrTrigger.ProblemSnomedList) + "'";
        }
         
        if (ehrTrigger.ProblemIcd9List != oldEhrTrigger.ProblemIcd9List)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProblemIcd9List = '" + POut.String(ehrTrigger.ProblemIcd9List) + "'";
        }
         
        if (ehrTrigger.ProblemIcd10List != oldEhrTrigger.ProblemIcd10List)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProblemIcd10List = '" + POut.String(ehrTrigger.ProblemIcd10List) + "'";
        }
         
        if (ehrTrigger.ProblemDefNumList != oldEhrTrigger.ProblemDefNumList)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProblemDefNumList = '" + POut.String(ehrTrigger.ProblemDefNumList) + "'";
        }
         
        if (ehrTrigger.MedicationNumList != oldEhrTrigger.MedicationNumList)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MedicationNumList = '" + POut.String(ehrTrigger.MedicationNumList) + "'";
        }
         
        if (ehrTrigger.RxCuiList != oldEhrTrigger.RxCuiList)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RxCuiList = '" + POut.String(ehrTrigger.RxCuiList) + "'";
        }
         
        if (ehrTrigger.CvxList != oldEhrTrigger.CvxList)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CvxList = '" + POut.String(ehrTrigger.CvxList) + "'";
        }
         
        if (ehrTrigger.AllergyDefNumList != oldEhrTrigger.AllergyDefNumList)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AllergyDefNumList = '" + POut.String(ehrTrigger.AllergyDefNumList) + "'";
        }
         
        if (ehrTrigger.DemographicsList != oldEhrTrigger.DemographicsList)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DemographicsList = '" + POut.String(ehrTrigger.DemographicsList) + "'";
        }
         
        if (ehrTrigger.LabLoincList != oldEhrTrigger.LabLoincList)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LabLoincList = '" + POut.String(ehrTrigger.LabLoincList) + "'";
        }
         
        if (ehrTrigger.VitalLoincList != oldEhrTrigger.VitalLoincList)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "VitalLoincList = '" + POut.String(ehrTrigger.VitalLoincList) + "'";
        }
         
        if (ehrTrigger.Instructions != oldEhrTrigger.Instructions)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Instructions = '" + POut.String(ehrTrigger.Instructions) + "'";
        }
         
        if (ehrTrigger.Bibliography != oldEhrTrigger.Bibliography)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Bibliography = '" + POut.String(ehrTrigger.Bibliography) + "'";
        }
         
        if (ehrTrigger.Cardinality != oldEhrTrigger.Cardinality)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Cardinality = " + POut.Int((int)ehrTrigger.Cardinality) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE ehrtrigger SET " + command + " WHERE EhrTriggerNum = " + POut.Long(ehrTrigger.EhrTriggerNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one EhrTrigger from the database.
    */
    public static void delete(long ehrTriggerNum) throws Exception {
        String command = "DELETE FROM ehrtrigger " + "WHERE EhrTriggerNum = " + POut.Long(ehrTriggerNum);
        Db.NonQ(command);
    }

}


