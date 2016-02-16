//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DeletedObject;
import OpenDentBusiness.DeletedObjectType;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class DeletedObjectCrud   
{
    /**
    * Gets one DeletedObject object from the database using the primary key.  Returns null if not found.
    */
    public static DeletedObject selectOne(long deletedObjectNum) throws Exception {
        String command = "SELECT * FROM deletedobject " + "WHERE DeletedObjectNum = " + POut.long(deletedObjectNum);
        List<DeletedObject> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one DeletedObject object from the database using a query.
    */
    public static DeletedObject selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<DeletedObject> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of DeletedObject objects from the database using a query.
    */
    public static List<DeletedObject> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<DeletedObject> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<DeletedObject> tableToList(DataTable table) throws Exception {
        List<DeletedObject> retVal = new List<DeletedObject>();
        DeletedObject deletedObject;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            deletedObject = new DeletedObject();
            deletedObject.DeletedObjectNum = PIn.Long(table.Rows[i]["DeletedObjectNum"].ToString());
            deletedObject.ObjectNum = PIn.Long(table.Rows[i]["ObjectNum"].ToString());
            deletedObject.ObjectType = (DeletedObjectType)PIn.Int(table.Rows[i]["ObjectType"].ToString());
            deletedObject.DateTStamp = PIn.DateT(table.Rows[i]["DateTStamp"].ToString());
            retVal.Add(deletedObject);
        }
        return retVal;
    }

    /**
    * Inserts one DeletedObject into the database.  Returns the new priKey.
    */
    public static long insert(DeletedObject deletedObject) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            deletedObject.DeletedObjectNum = DbHelper.getNextOracleKey("deletedobject","DeletedObjectNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(deletedObject, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        deletedObject.DeletedObjectNum++;
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
            return Insert(deletedObject, false);
        } 
    }

    /**
    * Inserts one DeletedObject into the database.  Provides option to use the existing priKey.
    */
    public static long insert(DeletedObject deletedObject, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            deletedObject.DeletedObjectNum = ReplicationServers.getKey("deletedobject","DeletedObjectNum");
        }
         
        String command = "INSERT INTO deletedobject (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "DeletedObjectNum,";
        }
         
        command += "ObjectNum,ObjectType) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(deletedObject.DeletedObjectNum) + ",";
        }
         
        command += POut.long(deletedObject.ObjectNum) + "," + POut.int(((Enum)deletedObject.ObjectType).ordinal()) + ")";
        //DateTStamp can only be set by MySQL
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            deletedObject.DeletedObjectNum = Db.nonQ(command,true);
        } 
        return deletedObject.DeletedObjectNum;
    }

    /**
    * Updates one DeletedObject in the database.
    */
    public static void update(DeletedObject deletedObject) throws Exception {
        //DateTStamp can only be set by MySQL
        String command = "UPDATE deletedobject SET " + "ObjectNum       =  " + POut.long(deletedObject.ObjectNum) + ", " + "ObjectType      =  " + POut.int(((Enum)deletedObject.ObjectType).ordinal()) + " " + "WHERE DeletedObjectNum = " + POut.long(deletedObject.DeletedObjectNum);
        Db.nonQ(command);
    }

    /**
    * Updates one DeletedObject in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(DeletedObject deletedObject, DeletedObject oldDeletedObject) throws Exception {
        String command = "";
        if (deletedObject.ObjectNum != oldDeletedObject.ObjectNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ObjectNum = " + POut.long(deletedObject.ObjectNum) + "";
        }
         
        if (deletedObject.ObjectType != oldDeletedObject.ObjectType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ObjectType = " + POut.int(((Enum)deletedObject.ObjectType).ordinal()) + "";
        }
         
        //DateTStamp can only be set by MySQL
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE deletedobject SET " + command + " WHERE DeletedObjectNum = " + POut.long(deletedObject.DeletedObjectNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one DeletedObject from the database.
    */
    public static void delete(long deletedObjectNum) throws Exception {
        String command = "DELETE FROM deletedobject " + "WHERE DeletedObjectNum = " + POut.long(deletedObjectNum);
        Db.nonQ(command);
    }

}


