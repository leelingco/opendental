//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:02 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class LanguageCrud   
{
    /**
    * Gets one Language object from the database using the primary key.  Returns null if not found.
    */
    public static Language selectOne(long languageNum) throws Exception {
        String command = "SELECT * FROM language " + "WHERE LanguageNum = " + POut.Long(languageNum);
        List<Language> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Language object from the database using a query.
    */
    public static Language selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Language> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Language objects from the database using a query.
    */
    public static List<Language> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Language> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Language> tableToList(DataTable table) throws Exception {
        List<Language> retVal = new List<Language>();
        Language language = new Language();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            language = new Language();
            language.LanguageNum = PIn.Long(table.Rows[i]["LanguageNum"].ToString());
            language.EnglishComments = PIn.String(table.Rows[i]["EnglishComments"].ToString());
            language.ClassType = PIn.String(table.Rows[i]["ClassType"].ToString());
            language.English = PIn.String(table.Rows[i]["English"].ToString());
            language.IsObsolete = PIn.Bool(table.Rows[i]["IsObsolete"].ToString());
            retVal.Add(language);
        }
        return retVal;
    }

    /**
    * Inserts one Language into the database.  Returns the new priKey.
    */
    public static long insert(Language language) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            language.LanguageNum = DbHelper.GetNextOracleKey("language", "LanguageNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(language,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        language.LanguageNum++;
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
            return insert(language,false);
        } 
    }

    /**
    * Inserts one Language into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Language language, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            language.LanguageNum = ReplicationServers.GetKey("language", "LanguageNum");
        }
         
        String command = "INSERT INTO language (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "LanguageNum,";
        }
         
        command += "EnglishComments,ClassType,English,IsObsolete) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(language.LanguageNum) + ",";
        }
         
        command += "'" + POut.String(language.EnglishComments) + "'," + "'" + POut.String(language.ClassType) + "'," + "'" + POut.String(language.English) + "'," + POut.Bool(language.IsObsolete) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            language.LanguageNum = Db.NonQ(command, true);
        } 
        return language.LanguageNum;
    }

    /**
    * Updates one Language in the database.
    */
    public static void update(Language language) throws Exception {
        String command = "UPDATE language SET " + "EnglishComments= '" + POut.String(language.EnglishComments) + "', " + "ClassType      = '" + POut.String(language.ClassType) + "', " + "English        = '" + POut.String(language.English) + "', " + "IsObsolete     =  " + POut.Bool(language.IsObsolete) + " " + "WHERE LanguageNum = " + POut.Long(language.LanguageNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Language in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Language language, Language oldLanguage) throws Exception {
        String command = "";
        if (language.EnglishComments != oldLanguage.EnglishComments)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EnglishComments = '" + POut.String(language.EnglishComments) + "'";
        }
         
        if (language.ClassType != oldLanguage.ClassType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClassType = '" + POut.String(language.ClassType) + "'";
        }
         
        if (language.English != oldLanguage.English)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "English = '" + POut.String(language.English) + "'";
        }
         
        if (language.IsObsolete != oldLanguage.IsObsolete)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsObsolete = " + POut.Bool(language.IsObsolete) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE language SET " + command + " WHERE LanguageNum = " + POut.Long(language.LanguageNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Language from the database.
    */
    public static void delete(long languageNum) throws Exception {
        String command = "DELETE FROM language " + "WHERE LanguageNum = " + POut.Long(languageNum);
        Db.NonQ(command);
    }

}

