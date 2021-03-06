//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:05 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.MedicalOrder;
import OpenDentBusiness.MedicalOrderType;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class MedicalOrderCrud   
{
    /**
    * Gets one MedicalOrder object from the database using the primary key.  Returns null if not found.
    */
    public static MedicalOrder selectOne(long medicalOrderNum) throws Exception {
        String command = "SELECT * FROM medicalorder " + "WHERE MedicalOrderNum = " + POut.long(medicalOrderNum);
        List<MedicalOrder> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one MedicalOrder object from the database using a query.
    */
    public static MedicalOrder selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<MedicalOrder> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of MedicalOrder objects from the database using a query.
    */
    public static List<MedicalOrder> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<MedicalOrder> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<MedicalOrder> tableToList(DataTable table) throws Exception {
        List<MedicalOrder> retVal = new List<MedicalOrder>();
        MedicalOrder medicalOrder;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            medicalOrder = new MedicalOrder();
            medicalOrder.MedicalOrderNum = PIn.Long(table.Rows[i]["MedicalOrderNum"].ToString());
            medicalOrder.MedOrderType = (MedicalOrderType)PIn.Int(table.Rows[i]["MedOrderType"].ToString());
            medicalOrder.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            medicalOrder.DateTimeOrder = PIn.DateT(table.Rows[i]["DateTimeOrder"].ToString());
            medicalOrder.Description = PIn.String(table.Rows[i]["Description"].ToString());
            medicalOrder.IsDiscontinued = PIn.Bool(table.Rows[i]["IsDiscontinued"].ToString());
            medicalOrder.ProvNum = PIn.Long(table.Rows[i]["ProvNum"].ToString());
            retVal.Add(medicalOrder);
        }
        return retVal;
    }

    /**
    * Inserts one MedicalOrder into the database.  Returns the new priKey.
    */
    public static long insert(MedicalOrder medicalOrder) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            medicalOrder.MedicalOrderNum = DbHelper.getNextOracleKey("medicalorder","MedicalOrderNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(medicalOrder, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        medicalOrder.MedicalOrderNum++;
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
            return Insert(medicalOrder, false);
        } 
    }

    /**
    * Inserts one MedicalOrder into the database.  Provides option to use the existing priKey.
    */
    public static long insert(MedicalOrder medicalOrder, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            medicalOrder.MedicalOrderNum = ReplicationServers.getKey("medicalorder","MedicalOrderNum");
        }
         
        String command = "INSERT INTO medicalorder (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "MedicalOrderNum,";
        }
         
        command += "MedOrderType,PatNum,DateTimeOrder,Description,IsDiscontinued,ProvNum) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(medicalOrder.MedicalOrderNum) + ",";
        }
         
        command += POut.int(((Enum)medicalOrder.MedOrderType).ordinal()) + "," + POut.long(medicalOrder.PatNum) + "," + POut.dateT(medicalOrder.DateTimeOrder) + "," + "'" + POut.string(medicalOrder.Description) + "'," + POut.bool(medicalOrder.IsDiscontinued) + "," + POut.long(medicalOrder.ProvNum) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            medicalOrder.MedicalOrderNum = Db.nonQ(command,true);
        } 
        return medicalOrder.MedicalOrderNum;
    }

    /**
    * Updates one MedicalOrder in the database.
    */
    public static void update(MedicalOrder medicalOrder) throws Exception {
        String command = "UPDATE medicalorder SET " + "MedOrderType   =  " + POut.int(((Enum)medicalOrder.MedOrderType).ordinal()) + ", " + "PatNum         =  " + POut.long(medicalOrder.PatNum) + ", " + "DateTimeOrder  =  " + POut.dateT(medicalOrder.DateTimeOrder) + ", " + "Description    = '" + POut.string(medicalOrder.Description) + "', " + "IsDiscontinued =  " + POut.bool(medicalOrder.IsDiscontinued) + ", " + "ProvNum        =  " + POut.long(medicalOrder.ProvNum) + " " + "WHERE MedicalOrderNum = " + POut.long(medicalOrder.MedicalOrderNum);
        Db.nonQ(command);
    }

    /**
    * Updates one MedicalOrder in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(MedicalOrder medicalOrder, MedicalOrder oldMedicalOrder) throws Exception {
        String command = "";
        if (medicalOrder.MedOrderType != oldMedicalOrder.MedOrderType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MedOrderType = " + POut.int(((Enum)medicalOrder.MedOrderType).ordinal()) + "";
        }
         
        if (medicalOrder.PatNum != oldMedicalOrder.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.long(medicalOrder.PatNum) + "";
        }
         
        if (medicalOrder.DateTimeOrder != oldMedicalOrder.DateTimeOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateTimeOrder = " + POut.dateT(medicalOrder.DateTimeOrder) + "";
        }
         
        if (!StringSupport.equals(medicalOrder.Description, oldMedicalOrder.Description))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.string(medicalOrder.Description) + "'";
        }
         
        if (medicalOrder.IsDiscontinued != oldMedicalOrder.IsDiscontinued)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsDiscontinued = " + POut.bool(medicalOrder.IsDiscontinued) + "";
        }
         
        if (medicalOrder.ProvNum != oldMedicalOrder.ProvNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProvNum = " + POut.long(medicalOrder.ProvNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE medicalorder SET " + command + " WHERE MedicalOrderNum = " + POut.long(medicalOrder.MedicalOrderNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one MedicalOrder from the database.
    */
    public static void delete(long medicalOrderNum) throws Exception {
        String command = "DELETE FROM medicalorder " + "WHERE MedicalOrderNum = " + POut.long(medicalOrderNum);
        Db.nonQ(command);
    }

}


