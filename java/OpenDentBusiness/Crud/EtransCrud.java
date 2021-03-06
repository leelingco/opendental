//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:03 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransType;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EtransCrud   
{
    /**
    * Gets one Etrans object from the database using the primary key.  Returns null if not found.
    */
    public static Etrans selectOne(long etransNum) throws Exception {
        String command = "SELECT * FROM etrans " + "WHERE EtransNum = " + POut.long(etransNum);
        List<Etrans> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Etrans object from the database using a query.
    */
    public static Etrans selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Etrans> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Etrans objects from the database using a query.
    */
    public static List<Etrans> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Etrans> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Etrans> tableToList(DataTable table) throws Exception {
        List<Etrans> retVal = new List<Etrans>();
        Etrans etrans;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            etrans = new Etrans();
            etrans.EtransNum = PIn.Long(table.Rows[i]["EtransNum"].ToString());
            etrans.DateTimeTrans = PIn.DateT(table.Rows[i]["DateTimeTrans"].ToString());
            etrans.ClearingHouseNum = PIn.Long(table.Rows[i]["ClearingHouseNum"].ToString());
            etrans.Etype = (EtransType)PIn.Int(table.Rows[i]["Etype"].ToString());
            etrans.ClaimNum = PIn.Long(table.Rows[i]["ClaimNum"].ToString());
            etrans.OfficeSequenceNumber = PIn.Int(table.Rows[i]["OfficeSequenceNumber"].ToString());
            etrans.CarrierTransCounter = PIn.Int(table.Rows[i]["CarrierTransCounter"].ToString());
            etrans.CarrierTransCounter2 = PIn.Int(table.Rows[i]["CarrierTransCounter2"].ToString());
            etrans.CarrierNum = PIn.Long(table.Rows[i]["CarrierNum"].ToString());
            etrans.CarrierNum2 = PIn.Long(table.Rows[i]["CarrierNum2"].ToString());
            etrans.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            etrans.BatchNumber = PIn.Int(table.Rows[i]["BatchNumber"].ToString());
            etrans.AckCode = PIn.String(table.Rows[i]["AckCode"].ToString());
            etrans.TransSetNum = PIn.Int(table.Rows[i]["TransSetNum"].ToString());
            etrans.Note = PIn.String(table.Rows[i]["Note"].ToString());
            etrans.EtransMessageTextNum = PIn.Long(table.Rows[i]["EtransMessageTextNum"].ToString());
            etrans.AckEtransNum = PIn.Long(table.Rows[i]["AckEtransNum"].ToString());
            etrans.PlanNum = PIn.Long(table.Rows[i]["PlanNum"].ToString());
            etrans.InsSubNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
            retVal.Add(etrans);
        }
        return retVal;
    }

    /**
    * Inserts one Etrans into the database.  Returns the new priKey.
    */
    public static long insert(Etrans etrans) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            etrans.EtransNum = DbHelper.getNextOracleKey("etrans","EtransNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(etrans, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        etrans.EtransNum++;
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
            return Insert(etrans, false);
        } 
    }

    /**
    * Inserts one Etrans into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Etrans etrans, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            etrans.EtransNum = ReplicationServers.getKey("etrans","EtransNum");
        }
         
        String command = "INSERT INTO etrans (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "EtransNum,";
        }
         
        command += "DateTimeTrans,ClearingHouseNum,Etype,ClaimNum,OfficeSequenceNumber,CarrierTransCounter,CarrierTransCounter2,CarrierNum,CarrierNum2,PatNum,BatchNumber,AckCode,TransSetNum,Note,EtransMessageTextNum,AckEtransNum,PlanNum,InsSubNum) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(etrans.EtransNum) + ",";
        }
         
        command += DbHelper.now() + "," + POut.long(etrans.ClearingHouseNum) + "," + POut.int(((Enum)etrans.Etype).ordinal()) + "," + POut.long(etrans.ClaimNum) + "," + POut.int(etrans.OfficeSequenceNumber) + "," + POut.int(etrans.CarrierTransCounter) + "," + POut.int(etrans.CarrierTransCounter2) + "," + POut.long(etrans.CarrierNum) + "," + POut.long(etrans.CarrierNum2) + "," + POut.long(etrans.PatNum) + "," + POut.int(etrans.BatchNumber) + "," + "'" + POut.string(etrans.AckCode) + "'," + POut.int(etrans.TransSetNum) + "," + "'" + POut.string(etrans.Note) + "'," + POut.long(etrans.EtransMessageTextNum) + "," + POut.long(etrans.AckEtransNum) + "," + POut.long(etrans.PlanNum) + "," + POut.long(etrans.InsSubNum) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            etrans.EtransNum = Db.nonQ(command,true);
        } 
        return etrans.EtransNum;
    }

    /**
    * Updates one Etrans in the database.
    */
    public static void update(Etrans etrans) throws Exception {
        String command = "UPDATE etrans SET " + "DateTimeTrans       =  " + POut.dateT(etrans.DateTimeTrans) + ", " + "ClearingHouseNum    =  " + POut.long(etrans.ClearingHouseNum) + ", " + "Etype               =  " + POut.int(((Enum)etrans.Etype).ordinal()) + ", " + "ClaimNum            =  " + POut.long(etrans.ClaimNum) + ", " + "OfficeSequenceNumber=  " + POut.int(etrans.OfficeSequenceNumber) + ", " + "CarrierTransCounter =  " + POut.int(etrans.CarrierTransCounter) + ", " + "CarrierTransCounter2=  " + POut.int(etrans.CarrierTransCounter2) + ", " + "CarrierNum          =  " + POut.long(etrans.CarrierNum) + ", " + "CarrierNum2         =  " + POut.long(etrans.CarrierNum2) + ", " + "PatNum              =  " + POut.long(etrans.PatNum) + ", " + "BatchNumber         =  " + POut.int(etrans.BatchNumber) + ", " + "AckCode             = '" + POut.string(etrans.AckCode) + "', " + "TransSetNum         =  " + POut.int(etrans.TransSetNum) + ", " + "Note                = '" + POut.string(etrans.Note) + "', " + "EtransMessageTextNum=  " + POut.long(etrans.EtransMessageTextNum) + ", " + "AckEtransNum        =  " + POut.long(etrans.AckEtransNum) + ", " + "PlanNum             =  " + POut.long(etrans.PlanNum) + ", " + "InsSubNum           =  " + POut.long(etrans.InsSubNum) + " " + "WHERE EtransNum = " + POut.long(etrans.EtransNum);
        Db.nonQ(command);
    }

    /**
    * Updates one Etrans in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Etrans etrans, Etrans oldEtrans) throws Exception {
        String command = "";
        if (etrans.DateTimeTrans != oldEtrans.DateTimeTrans)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateTimeTrans = " + POut.dateT(etrans.DateTimeTrans) + "";
        }
         
        if (etrans.ClearingHouseNum != oldEtrans.ClearingHouseNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClearingHouseNum = " + POut.long(etrans.ClearingHouseNum) + "";
        }
         
        if (etrans.Etype != oldEtrans.Etype)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Etype = " + POut.int(((Enum)etrans.Etype).ordinal()) + "";
        }
         
        if (etrans.ClaimNum != oldEtrans.ClaimNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClaimNum = " + POut.long(etrans.ClaimNum) + "";
        }
         
        if (etrans.OfficeSequenceNumber != oldEtrans.OfficeSequenceNumber)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OfficeSequenceNumber = " + POut.int(etrans.OfficeSequenceNumber) + "";
        }
         
        if (etrans.CarrierTransCounter != oldEtrans.CarrierTransCounter)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CarrierTransCounter = " + POut.int(etrans.CarrierTransCounter) + "";
        }
         
        if (etrans.CarrierTransCounter2 != oldEtrans.CarrierTransCounter2)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CarrierTransCounter2 = " + POut.int(etrans.CarrierTransCounter2) + "";
        }
         
        if (etrans.CarrierNum != oldEtrans.CarrierNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CarrierNum = " + POut.long(etrans.CarrierNum) + "";
        }
         
        if (etrans.CarrierNum2 != oldEtrans.CarrierNum2)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CarrierNum2 = " + POut.long(etrans.CarrierNum2) + "";
        }
         
        if (etrans.PatNum != oldEtrans.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.long(etrans.PatNum) + "";
        }
         
        if (etrans.BatchNumber != oldEtrans.BatchNumber)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "BatchNumber = " + POut.int(etrans.BatchNumber) + "";
        }
         
        if (!StringSupport.equals(etrans.AckCode, oldEtrans.AckCode))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AckCode = '" + POut.string(etrans.AckCode) + "'";
        }
         
        if (etrans.TransSetNum != oldEtrans.TransSetNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TransSetNum = " + POut.int(etrans.TransSetNum) + "";
        }
         
        if (!StringSupport.equals(etrans.Note, oldEtrans.Note))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Note = '" + POut.string(etrans.Note) + "'";
        }
         
        if (etrans.EtransMessageTextNum != oldEtrans.EtransMessageTextNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EtransMessageTextNum = " + POut.long(etrans.EtransMessageTextNum) + "";
        }
         
        if (etrans.AckEtransNum != oldEtrans.AckEtransNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AckEtransNum = " + POut.long(etrans.AckEtransNum) + "";
        }
         
        if (etrans.PlanNum != oldEtrans.PlanNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PlanNum = " + POut.long(etrans.PlanNum) + "";
        }
         
        if (etrans.InsSubNum != oldEtrans.InsSubNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "InsSubNum = " + POut.long(etrans.InsSubNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE etrans SET " + command + " WHERE EtransNum = " + POut.long(etrans.EtransNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Etrans from the database.
    */
    public static void delete(long etransNum) throws Exception {
        String command = "DELETE FROM etrans " + "WHERE EtransNum = " + POut.long(etransNum);
        Db.nonQ(command);
    }

}


