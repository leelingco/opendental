//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:56 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class ClaimPaymentCrud   
{
    /**
    * Gets one ClaimPayment object from the database using the primary key.  Returns null if not found.
    */
    public static ClaimPayment selectOne(long claimPaymentNum) throws Exception {
        String command = "SELECT * FROM claimpayment " + "WHERE ClaimPaymentNum = " + POut.Long(claimPaymentNum);
        List<ClaimPayment> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one ClaimPayment object from the database using a query.
    */
    public static ClaimPayment selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ClaimPayment> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of ClaimPayment objects from the database using a query.
    */
    public static List<ClaimPayment> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ClaimPayment> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ClaimPayment> tableToList(DataTable table) throws Exception {
        List<ClaimPayment> retVal = new List<ClaimPayment>();
        ClaimPayment claimPayment = new ClaimPayment();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            claimPayment = new ClaimPayment();
            claimPayment.ClaimPaymentNum = PIn.Long(table.Rows[i]["ClaimPaymentNum"].ToString());
            claimPayment.CheckDate = PIn.Date(table.Rows[i]["CheckDate"].ToString());
            claimPayment.CheckAmt = PIn.Double(table.Rows[i]["CheckAmt"].ToString());
            claimPayment.CheckNum = PIn.String(table.Rows[i]["CheckNum"].ToString());
            claimPayment.BankBranch = PIn.String(table.Rows[i]["BankBranch"].ToString());
            claimPayment.Note = PIn.String(table.Rows[i]["Note"].ToString());
            claimPayment.ClinicNum = PIn.Long(table.Rows[i]["ClinicNum"].ToString());
            claimPayment.DepositNum = PIn.Long(table.Rows[i]["DepositNum"].ToString());
            claimPayment.CarrierName = PIn.String(table.Rows[i]["CarrierName"].ToString());
            claimPayment.DateIssued = PIn.Date(table.Rows[i]["DateIssued"].ToString());
            claimPayment.IsPartial = PIn.Bool(table.Rows[i]["IsPartial"].ToString());
            retVal.Add(claimPayment);
        }
        return retVal;
    }

    /**
    * Inserts one ClaimPayment into the database.  Returns the new priKey.
    */
    public static long insert(ClaimPayment claimPayment) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            claimPayment.ClaimPaymentNum = DbHelper.GetNextOracleKey("claimpayment", "ClaimPaymentNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(claimPayment,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        claimPayment.ClaimPaymentNum++;
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
            return insert(claimPayment,false);
        } 
    }

    /**
    * Inserts one ClaimPayment into the database.  Provides option to use the existing priKey.
    */
    public static long insert(ClaimPayment claimPayment, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            claimPayment.ClaimPaymentNum = ReplicationServers.GetKey("claimpayment", "ClaimPaymentNum");
        }
         
        String command = "INSERT INTO claimpayment (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "ClaimPaymentNum,";
        }
         
        command += "CheckDate,CheckAmt,CheckNum,BankBranch,Note,ClinicNum,DepositNum,CarrierName,DateIssued,IsPartial) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(claimPayment.ClaimPaymentNum) + ",";
        }
         
        command += POut.Date(claimPayment.CheckDate) + "," + "'" + POut.Double(claimPayment.CheckAmt) + "'," + "'" + POut.String(claimPayment.CheckNum) + "'," + "'" + POut.String(claimPayment.BankBranch) + "'," + "'" + POut.String(claimPayment.Note) + "'," + POut.Long(claimPayment.ClinicNum) + "," + POut.Long(claimPayment.DepositNum) + "," + "'" + POut.String(claimPayment.CarrierName) + "'," + POut.Date(claimPayment.DateIssued) + "," + POut.Bool(claimPayment.IsPartial) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            claimPayment.ClaimPaymentNum = Db.NonQ(command, true);
        } 
        return claimPayment.ClaimPaymentNum;
    }

    /**
    * Updates one ClaimPayment in the database.
    */
    public static void update(ClaimPayment claimPayment) throws Exception {
        String command = "UPDATE claimpayment SET " + "CheckDate      =  " + POut.Date(claimPayment.CheckDate) + ", " + "CheckAmt       = '" + POut.Double(claimPayment.CheckAmt) + "', " + "CheckNum       = '" + POut.String(claimPayment.CheckNum) + "', " + "BankBranch     = '" + POut.String(claimPayment.BankBranch) + "', " + "Note           = '" + POut.String(claimPayment.Note) + "', " + "ClinicNum      =  " + POut.Long(claimPayment.ClinicNum) + ", " + "DepositNum     =  " + POut.Long(claimPayment.DepositNum) + ", " + "CarrierName    = '" + POut.String(claimPayment.CarrierName) + "', " + "DateIssued     =  " + POut.Date(claimPayment.DateIssued) + ", " + "IsPartial      =  " + POut.Bool(claimPayment.IsPartial) + " " + "WHERE ClaimPaymentNum = " + POut.Long(claimPayment.ClaimPaymentNum);
        Db.NonQ(command);
    }

    /**
    * Updates one ClaimPayment in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(ClaimPayment claimPayment, ClaimPayment oldClaimPayment) throws Exception {
        String command = "";
        if (claimPayment.CheckDate != oldClaimPayment.CheckDate)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CheckDate = " + POut.Date(claimPayment.CheckDate) + "";
        }
         
        if (claimPayment.CheckAmt != oldClaimPayment.CheckAmt)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CheckAmt = '" + POut.Double(claimPayment.CheckAmt) + "'";
        }
         
        if (claimPayment.CheckNum != oldClaimPayment.CheckNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CheckNum = '" + POut.String(claimPayment.CheckNum) + "'";
        }
         
        if (claimPayment.BankBranch != oldClaimPayment.BankBranch)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "BankBranch = '" + POut.String(claimPayment.BankBranch) + "'";
        }
         
        if (claimPayment.Note != oldClaimPayment.Note)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Note = '" + POut.String(claimPayment.Note) + "'";
        }
         
        if (claimPayment.ClinicNum != oldClaimPayment.ClinicNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClinicNum = " + POut.Long(claimPayment.ClinicNum) + "";
        }
         
        if (claimPayment.DepositNum != oldClaimPayment.DepositNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DepositNum = " + POut.Long(claimPayment.DepositNum) + "";
        }
         
        if (claimPayment.CarrierName != oldClaimPayment.CarrierName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CarrierName = '" + POut.String(claimPayment.CarrierName) + "'";
        }
         
        if (claimPayment.DateIssued != oldClaimPayment.DateIssued)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateIssued = " + POut.Date(claimPayment.DateIssued) + "";
        }
         
        if (claimPayment.IsPartial != oldClaimPayment.IsPartial)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsPartial = " + POut.Bool(claimPayment.IsPartial) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE claimpayment SET " + command + " WHERE ClaimPaymentNum = " + POut.Long(claimPayment.ClaimPaymentNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one ClaimPayment from the database.
    */
    public static void delete(long claimPaymentNum) throws Exception {
        String command = "DELETE FROM claimpayment " + "WHERE ClaimPaymentNum = " + POut.Long(claimPaymentNum);
        Db.NonQ(command);
    }

}


