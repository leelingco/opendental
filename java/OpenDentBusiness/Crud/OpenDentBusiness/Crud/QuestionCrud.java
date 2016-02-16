//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:05 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class QuestionCrud   
{
    /**
    * Gets one Question object from the database using the primary key.  Returns null if not found.
    */
    public static Question selectOne(long questionNum) throws Exception {
        String command = "SELECT * FROM question " + "WHERE QuestionNum = " + POut.Long(questionNum);
        List<Question> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Question object from the database using a query.
    */
    public static Question selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Question> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Question objects from the database using a query.
    */
    public static List<Question> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Question> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Question> tableToList(DataTable table) throws Exception {
        List<Question> retVal = new List<Question>();
        Question question = new Question();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            question = new Question();
            question.QuestionNum = PIn.Long(table.Rows[i]["QuestionNum"].ToString());
            question.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            question.ItemOrder = PIn.Int(table.Rows[i]["ItemOrder"].ToString());
            question.Description = PIn.String(table.Rows[i]["Description"].ToString());
            question.Answer = PIn.String(table.Rows[i]["Answer"].ToString());
            question.FormPatNum = PIn.Long(table.Rows[i]["FormPatNum"].ToString());
            retVal.Add(question);
        }
        return retVal;
    }

    /**
    * Inserts one Question into the database.  Returns the new priKey.
    */
    public static long insert(Question question) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            question.QuestionNum = DbHelper.GetNextOracleKey("question", "QuestionNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(question,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        question.QuestionNum++;
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
            return insert(question,false);
        } 
    }

    /**
    * Inserts one Question into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Question question, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            question.QuestionNum = ReplicationServers.GetKey("question", "QuestionNum");
        }
         
        String command = "INSERT INTO question (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "QuestionNum,";
        }
         
        command += "PatNum,ItemOrder,Description,Answer,FormPatNum) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(question.QuestionNum) + ",";
        }
         
        command += POut.Long(question.PatNum) + "," + POut.Int(question.ItemOrder) + "," + "'" + POut.String(question.Description) + "'," + "'" + POut.String(question.Answer) + "'," + POut.Long(question.FormPatNum) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            question.QuestionNum = Db.NonQ(command, true);
        } 
        return question.QuestionNum;
    }

    /**
    * Updates one Question in the database.
    */
    public static void update(Question question) throws Exception {
        String command = "UPDATE question SET " + "PatNum     =  " + POut.Long(question.PatNum) + ", " + "ItemOrder  =  " + POut.Int(question.ItemOrder) + ", " + "Description= '" + POut.String(question.Description) + "', " + "Answer     = '" + POut.String(question.Answer) + "', " + "FormPatNum =  " + POut.Long(question.FormPatNum) + " " + "WHERE QuestionNum = " + POut.Long(question.QuestionNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Question in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Question question, Question oldQuestion) throws Exception {
        String command = "";
        if (question.PatNum != oldQuestion.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(question.PatNum) + "";
        }
         
        if (question.ItemOrder != oldQuestion.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.Int(question.ItemOrder) + "";
        }
         
        if (question.Description != oldQuestion.Description)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.String(question.Description) + "'";
        }
         
        if (question.Answer != oldQuestion.Answer)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Answer = '" + POut.String(question.Answer) + "'";
        }
         
        if (question.FormPatNum != oldQuestion.FormPatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FormPatNum = " + POut.Long(question.FormPatNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE question SET " + command + " WHERE QuestionNum = " + POut.Long(question.QuestionNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Question from the database.
    */
    public static void delete(long questionNum) throws Exception {
        String command = "DELETE FROM question " + "WHERE QuestionNum = " + POut.Long(questionNum);
        Db.NonQ(command);
    }

}

