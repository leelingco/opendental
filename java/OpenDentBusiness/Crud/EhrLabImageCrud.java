//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:02 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.EhrLabImage;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrLabImageCrud   
{
    /**
    * Gets one EhrLabImage object from the database using the primary key.  Returns null if not found.
    */
    public static EhrLabImage selectOne(long ehrLabImageNum) throws Exception {
        String command = "SELECT * FROM ehrlabimage " + "WHERE EhrLabImageNum = " + POut.long(ehrLabImageNum);
        List<EhrLabImage> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EhrLabImage object from the database using a query.
    */
    public static EhrLabImage selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrLabImage> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EhrLabImage objects from the database using a query.
    */
    public static List<EhrLabImage> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrLabImage> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrLabImage> tableToList(DataTable table) throws Exception {
        List<EhrLabImage> retVal = new List<EhrLabImage>();
        EhrLabImage ehrLabImage;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ehrLabImage = new EhrLabImage();
            ehrLabImage.EhrLabImageNum = PIn.Long(table.Rows[i]["EhrLabImageNum"].ToString());
            ehrLabImage.EhrLabNum = PIn.Long(table.Rows[i]["EhrLabNum"].ToString());
            ehrLabImage.DocNum = PIn.Long(table.Rows[i]["DocNum"].ToString());
            retVal.Add(ehrLabImage);
        }
        return retVal;
    }

    /**
    * Inserts one EhrLabImage into the database.  Returns the new priKey.
    */
    public static long insert(EhrLabImage ehrLabImage) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            ehrLabImage.EhrLabImageNum = DbHelper.getNextOracleKey("ehrlabimage","EhrLabImageNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(ehrLabImage, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        ehrLabImage.EhrLabImageNum++;
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
            return Insert(ehrLabImage, false);
        } 
    }

    /**
    * Inserts one EhrLabImage into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrLabImage ehrLabImage, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            ehrLabImage.EhrLabImageNum = ReplicationServers.getKey("ehrlabimage","EhrLabImageNum");
        }
         
        String command = "INSERT INTO ehrlabimage (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "EhrLabImageNum,";
        }
         
        command += "EhrLabNum,DocNum) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(ehrLabImage.EhrLabImageNum) + ",";
        }
         
        command += POut.long(ehrLabImage.EhrLabNum) + "," + POut.long(ehrLabImage.DocNum) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            ehrLabImage.EhrLabImageNum = Db.nonQ(command,true);
        } 
        return ehrLabImage.EhrLabImageNum;
    }

    /**
    * Updates one EhrLabImage in the database.
    */
    public static void update(EhrLabImage ehrLabImage) throws Exception {
        String command = "UPDATE ehrlabimage SET " + "EhrLabNum     =  " + POut.long(ehrLabImage.EhrLabNum) + ", " + "DocNum        =  " + POut.long(ehrLabImage.DocNum) + " " + "WHERE EhrLabImageNum = " + POut.long(ehrLabImage.EhrLabImageNum);
        Db.nonQ(command);
    }

    /**
    * Updates one EhrLabImage in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EhrLabImage ehrLabImage, EhrLabImage oldEhrLabImage) throws Exception {
        String command = "";
        if (ehrLabImage.EhrLabNum != oldEhrLabImage.EhrLabNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EhrLabNum = " + POut.long(ehrLabImage.EhrLabNum) + "";
        }
         
        if (ehrLabImage.DocNum != oldEhrLabImage.DocNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DocNum = " + POut.long(ehrLabImage.DocNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE ehrlabimage SET " + command + " WHERE EhrLabImageNum = " + POut.long(ehrLabImage.EhrLabImageNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one EhrLabImage from the database.
    */
    public static void delete(long ehrLabImageNum) throws Exception {
        String command = "DELETE FROM ehrlabimage " + "WHERE EhrLabImageNum = " + POut.long(ehrLabImageNum);
        Db.nonQ(command);
    }

}


