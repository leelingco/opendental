//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:57 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class CreditCardCrud   
{
    /**
    * Gets one CreditCard object from the database using the primary key.  Returns null if not found.
    */
    public static CreditCard selectOne(long creditCardNum) throws Exception {
        String command = "SELECT * FROM creditcard " + "WHERE CreditCardNum = " + POut.Long(creditCardNum);
        List<CreditCard> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one CreditCard object from the database using a query.
    */
    public static CreditCard selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<CreditCard> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of CreditCard objects from the database using a query.
    */
    public static List<CreditCard> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<CreditCard> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<CreditCard> tableToList(DataTable table) throws Exception {
        List<CreditCard> retVal = new List<CreditCard>();
        CreditCard creditCard = new CreditCard();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            creditCard = new CreditCard();
            creditCard.CreditCardNum = PIn.Long(table.Rows[i]["CreditCardNum"].ToString());
            creditCard.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            creditCard.Address = PIn.String(table.Rows[i]["Address"].ToString());
            creditCard.Zip = PIn.String(table.Rows[i]["Zip"].ToString());
            creditCard.XChargeToken = PIn.String(table.Rows[i]["XChargeToken"].ToString());
            creditCard.CCNumberMasked = PIn.String(table.Rows[i]["CCNumberMasked"].ToString());
            creditCard.CCExpiration = PIn.Date(table.Rows[i]["CCExpiration"].ToString());
            creditCard.ItemOrder = PIn.Int(table.Rows[i]["ItemOrder"].ToString());
            creditCard.ChargeAmt = PIn.Double(table.Rows[i]["ChargeAmt"].ToString());
            creditCard.DateStart = PIn.Date(table.Rows[i]["DateStart"].ToString());
            creditCard.DateStop = PIn.Date(table.Rows[i]["DateStop"].ToString());
            creditCard.Note = PIn.String(table.Rows[i]["Note"].ToString());
            creditCard.PayPlanNum = PIn.Long(table.Rows[i]["PayPlanNum"].ToString());
            retVal.Add(creditCard);
        }
        return retVal;
    }

    /**
    * Inserts one CreditCard into the database.  Returns the new priKey.
    */
    public static long insert(CreditCard creditCard) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            creditCard.CreditCardNum = DbHelper.GetNextOracleKey("creditcard", "CreditCardNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(creditCard,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        creditCard.CreditCardNum++;
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
            return insert(creditCard,false);
        } 
    }

    /**
    * Inserts one CreditCard into the database.  Provides option to use the existing priKey.
    */
    public static long insert(CreditCard creditCard, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            creditCard.CreditCardNum = ReplicationServers.GetKey("creditcard", "CreditCardNum");
        }
         
        String command = "INSERT INTO creditcard (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "CreditCardNum,";
        }
         
        command += "PatNum,Address,Zip,XChargeToken,CCNumberMasked,CCExpiration,ItemOrder,ChargeAmt,DateStart,DateStop,Note,PayPlanNum) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(creditCard.CreditCardNum) + ",";
        }
         
        command += POut.Long(creditCard.PatNum) + "," + "'" + POut.String(creditCard.Address) + "'," + "'" + POut.String(creditCard.Zip) + "'," + "'" + POut.String(creditCard.XChargeToken) + "'," + "'" + POut.String(creditCard.CCNumberMasked) + "'," + POut.Date(creditCard.CCExpiration) + "," + POut.Int(creditCard.ItemOrder) + "," + "'" + POut.Double(creditCard.ChargeAmt) + "'," + POut.Date(creditCard.DateStart) + "," + POut.Date(creditCard.DateStop) + "," + "'" + POut.String(creditCard.Note) + "'," + POut.Long(creditCard.PayPlanNum) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            creditCard.CreditCardNum = Db.NonQ(command, true);
        } 
        return creditCard.CreditCardNum;
    }

    /**
    * Updates one CreditCard in the database.
    */
    public static void update(CreditCard creditCard) throws Exception {
        String command = "UPDATE creditcard SET " + "PatNum        =  " + POut.Long(creditCard.PatNum) + ", " + "Address       = '" + POut.String(creditCard.Address) + "', " + "Zip           = '" + POut.String(creditCard.Zip) + "', " + "XChargeToken  = '" + POut.String(creditCard.XChargeToken) + "', " + "CCNumberMasked= '" + POut.String(creditCard.CCNumberMasked) + "', " + "CCExpiration  =  " + POut.Date(creditCard.CCExpiration) + ", " + "ItemOrder     =  " + POut.Int(creditCard.ItemOrder) + ", " + "ChargeAmt     = '" + POut.Double(creditCard.ChargeAmt) + "', " + "DateStart     =  " + POut.Date(creditCard.DateStart) + ", " + "DateStop      =  " + POut.Date(creditCard.DateStop) + ", " + "Note          = '" + POut.String(creditCard.Note) + "', " + "PayPlanNum    =  " + POut.Long(creditCard.PayPlanNum) + " " + "WHERE CreditCardNum = " + POut.Long(creditCard.CreditCardNum);
        Db.NonQ(command);
    }

    /**
    * Updates one CreditCard in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(CreditCard creditCard, CreditCard oldCreditCard) throws Exception {
        String command = "";
        if (creditCard.PatNum != oldCreditCard.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(creditCard.PatNum) + "";
        }
         
        if (creditCard.Address != oldCreditCard.Address)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Address = '" + POut.String(creditCard.Address) + "'";
        }
         
        if (creditCard.Zip != oldCreditCard.Zip)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Zip = '" + POut.String(creditCard.Zip) + "'";
        }
         
        if (creditCard.XChargeToken != oldCreditCard.XChargeToken)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "XChargeToken = '" + POut.String(creditCard.XChargeToken) + "'";
        }
         
        if (creditCard.CCNumberMasked != oldCreditCard.CCNumberMasked)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CCNumberMasked = '" + POut.String(creditCard.CCNumberMasked) + "'";
        }
         
        if (creditCard.CCExpiration != oldCreditCard.CCExpiration)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CCExpiration = " + POut.Date(creditCard.CCExpiration) + "";
        }
         
        if (creditCard.ItemOrder != oldCreditCard.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.Int(creditCard.ItemOrder) + "";
        }
         
        if (creditCard.ChargeAmt != oldCreditCard.ChargeAmt)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ChargeAmt = '" + POut.Double(creditCard.ChargeAmt) + "'";
        }
         
        if (creditCard.DateStart != oldCreditCard.DateStart)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateStart = " + POut.Date(creditCard.DateStart) + "";
        }
         
        if (creditCard.DateStop != oldCreditCard.DateStop)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateStop = " + POut.Date(creditCard.DateStop) + "";
        }
         
        if (creditCard.Note != oldCreditCard.Note)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Note = '" + POut.String(creditCard.Note) + "'";
        }
         
        if (creditCard.PayPlanNum != oldCreditCard.PayPlanNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PayPlanNum = " + POut.Long(creditCard.PayPlanNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE creditcard SET " + command + " WHERE CreditCardNum = " + POut.Long(creditCard.CreditCardNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one CreditCard from the database.
    */
    public static void delete(long creditCardNum) throws Exception {
        String command = "DELETE FROM creditcard " + "WHERE CreditCardNum = " + POut.Long(creditCardNum);
        Db.NonQ(command);
    }

}

