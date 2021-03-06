//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:58 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class DiseaseCrud   
{
    /**
    * Gets one Disease object from the database using the primary key.  Returns null if not found.
    */
    public static Disease selectOne(long diseaseNum) throws Exception {
        String command = "SELECT * FROM disease " + "WHERE DiseaseNum = " + POut.Long(diseaseNum);
        List<Disease> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Disease object from the database using a query.
    */
    public static Disease selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Disease> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Disease objects from the database using a query.
    */
    public static List<Disease> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Disease> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Disease> tableToList(DataTable table) throws Exception {
        List<Disease> retVal = new List<Disease>();
        Disease disease = new Disease();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            disease = new Disease();
            disease.DiseaseNum = PIn.Long(table.Rows[i]["DiseaseNum"].ToString());
            disease.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            disease.DiseaseDefNum = PIn.Long(table.Rows[i]["DiseaseDefNum"].ToString());
            disease.PatNote = PIn.String(table.Rows[i]["PatNote"].ToString());
            disease.DateTStamp = PIn.DateT(table.Rows[i]["DateTStamp"].ToString());
            disease.ProbStatus = (ProblemStatus)PIn.Int(table.Rows[i]["ProbStatus"].ToString());
            disease.DateStart = PIn.Date(table.Rows[i]["DateStart"].ToString());
            disease.DateStop = PIn.Date(table.Rows[i]["DateStop"].ToString());
            disease.SnomedProblemType = PIn.String(table.Rows[i]["SnomedProblemType"].ToString());
            disease.FunctionStatus = (FunctionalStatus)PIn.Int(table.Rows[i]["FunctionStatus"].ToString());
            retVal.Add(disease);
        }
        return retVal;
    }

    /**
    * Inserts one Disease into the database.  Returns the new priKey.
    */
    public static long insert(Disease disease) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            disease.DiseaseNum = DbHelper.GetNextOracleKey("disease", "DiseaseNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(disease,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        disease.DiseaseNum++;
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
            return insert(disease,false);
        } 
    }

    /**
    * Inserts one Disease into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Disease disease, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            disease.DiseaseNum = ReplicationServers.GetKey("disease", "DiseaseNum");
        }
         
        String command = "INSERT INTO disease (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "DiseaseNum,";
        }
         
        command += "PatNum,DiseaseDefNum,PatNote,ProbStatus,DateStart,DateStop,SnomedProblemType,FunctionStatus) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(disease.DiseaseNum) + ",";
        }
         
        //DateTStamp can only be set by MySQL
        command += POut.Long(disease.PatNum) + "," + POut.Long(disease.DiseaseDefNum) + "," + "'" + POut.String(disease.PatNote) + "'," + POut.Int((int)disease.ProbStatus) + "," + POut.Date(disease.DateStart) + "," + POut.Date(disease.DateStop) + "," + "'" + POut.String(disease.SnomedProblemType) + "'," + POut.Int((int)disease.FunctionStatus) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            disease.DiseaseNum = Db.NonQ(command, true);
        } 
        return disease.DiseaseNum;
    }

    /**
    * Updates one Disease in the database.
    */
    public static void update(Disease disease) throws Exception {
        //DateTStamp can only be set by MySQL
        String command = "UPDATE disease SET " + "PatNum           =  " + POut.Long(disease.PatNum) + ", " + "DiseaseDefNum    =  " + POut.Long(disease.DiseaseDefNum) + ", " + "PatNote          = '" + POut.String(disease.PatNote) + "', " + "ProbStatus       =  " + POut.Int((int)disease.ProbStatus) + ", " + "DateStart        =  " + POut.Date(disease.DateStart) + ", " + "DateStop         =  " + POut.Date(disease.DateStop) + ", " + "SnomedProblemType= '" + POut.String(disease.SnomedProblemType) + "', " + "FunctionStatus   =  " + POut.Int((int)disease.FunctionStatus) + " " + "WHERE DiseaseNum = " + POut.Long(disease.DiseaseNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Disease in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Disease disease, Disease oldDisease) throws Exception {
        String command = "";
        if (disease.PatNum != oldDisease.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(disease.PatNum) + "";
        }
         
        if (disease.DiseaseDefNum != oldDisease.DiseaseDefNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DiseaseDefNum = " + POut.Long(disease.DiseaseDefNum) + "";
        }
         
        if (disease.PatNote != oldDisease.PatNote)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNote = '" + POut.String(disease.PatNote) + "'";
        }
         
        //DateTStamp can only be set by MySQL
        if (disease.ProbStatus != oldDisease.ProbStatus)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProbStatus = " + POut.Int((int)disease.ProbStatus) + "";
        }
         
        if (disease.DateStart != oldDisease.DateStart)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateStart = " + POut.Date(disease.DateStart) + "";
        }
         
        if (disease.DateStop != oldDisease.DateStop)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateStop = " + POut.Date(disease.DateStop) + "";
        }
         
        if (disease.SnomedProblemType != oldDisease.SnomedProblemType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SnomedProblemType = '" + POut.String(disease.SnomedProblemType) + "'";
        }
         
        if (disease.FunctionStatus != oldDisease.FunctionStatus)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FunctionStatus = " + POut.Int((int)disease.FunctionStatus) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE disease SET " + command + " WHERE DiseaseNum = " + POut.Long(disease.DiseaseNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Disease from the database.
    */
    public static void delete(long diseaseNum) throws Exception {
        String command = "DELETE FROM disease " + "WHERE DiseaseNum = " + POut.Long(diseaseNum);
        Db.NonQ(command);
    }

}


