//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:56 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class BenefitCrud   
{
    /**
    * Gets one Benefit object from the database using the primary key.  Returns null if not found.
    */
    public static Benefit selectOne(long benefitNum) throws Exception {
        String command = "SELECT * FROM benefit " + "WHERE BenefitNum = " + POut.Long(benefitNum);
        List<Benefit> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Benefit object from the database using a query.
    */
    public static Benefit selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Benefit> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Benefit objects from the database using a query.
    */
    public static List<Benefit> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Benefit> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Benefit> tableToList(DataTable table) throws Exception {
        List<Benefit> retVal = new List<Benefit>();
        Benefit benefit = new Benefit();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            benefit = new Benefit();
            benefit.BenefitNum = PIn.Long(table.Rows[i]["BenefitNum"].ToString());
            benefit.PlanNum = PIn.Long(table.Rows[i]["PlanNum"].ToString());
            benefit.PatPlanNum = PIn.Long(table.Rows[i]["PatPlanNum"].ToString());
            benefit.CovCatNum = PIn.Long(table.Rows[i]["CovCatNum"].ToString());
            benefit.BenefitType = (InsBenefitType)PIn.Int(table.Rows[i]["BenefitType"].ToString());
            benefit.Percent = PIn.Int(table.Rows[i]["Percent"].ToString());
            benefit.MonetaryAmt = PIn.Double(table.Rows[i]["MonetaryAmt"].ToString());
            benefit.TimePeriod = (BenefitTimePeriod)PIn.Int(table.Rows[i]["TimePeriod"].ToString());
            benefit.QuantityQualifier = (BenefitQuantity)PIn.Int(table.Rows[i]["QuantityQualifier"].ToString());
            benefit.Quantity = PIn.Byte(table.Rows[i]["Quantity"].ToString());
            benefit.CodeNum = PIn.Long(table.Rows[i]["CodeNum"].ToString());
            benefit.CoverageLevel = (BenefitCoverageLevel)PIn.Int(table.Rows[i]["CoverageLevel"].ToString());
            retVal.Add(benefit);
        }
        return retVal;
    }

    /**
    * Inserts one Benefit into the database.  Returns the new priKey.
    */
    public static long insert(Benefit benefit) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            benefit.BenefitNum = DbHelper.GetNextOracleKey("benefit", "BenefitNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(benefit,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        benefit.BenefitNum++;
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
            return insert(benefit,false);
        } 
    }

    /**
    * Inserts one Benefit into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Benefit benefit, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            benefit.BenefitNum = ReplicationServers.GetKey("benefit", "BenefitNum");
        }
         
        String command = "INSERT INTO benefit (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "BenefitNum,";
        }
         
        command += "PlanNum,PatPlanNum,CovCatNum,BenefitType,Percent,MonetaryAmt,TimePeriod,QuantityQualifier,Quantity,CodeNum,CoverageLevel) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(benefit.BenefitNum) + ",";
        }
         
        command += POut.Long(benefit.PlanNum) + "," + POut.Long(benefit.PatPlanNum) + "," + POut.Long(benefit.CovCatNum) + "," + POut.Int((int)benefit.BenefitType) + "," + POut.Int(benefit.Percent) + "," + "'" + POut.Double(benefit.MonetaryAmt) + "'," + POut.Int((int)benefit.TimePeriod) + "," + POut.Int((int)benefit.QuantityQualifier) + "," + POut.Byte(benefit.Quantity) + "," + POut.Long(benefit.CodeNum) + "," + POut.Int((int)benefit.CoverageLevel) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            benefit.BenefitNum = Db.NonQ(command, true);
        } 
        return benefit.BenefitNum;
    }

    /**
    * Updates one Benefit in the database.
    */
    public static void update(Benefit benefit) throws Exception {
        String command = "UPDATE benefit SET " + "PlanNum          =  " + POut.Long(benefit.PlanNum) + ", " + "PatPlanNum       =  " + POut.Long(benefit.PatPlanNum) + ", " + "CovCatNum        =  " + POut.Long(benefit.CovCatNum) + ", " + "BenefitType      =  " + POut.Int((int)benefit.BenefitType) + ", " + "Percent          =  " + POut.Int(benefit.Percent) + ", " + "MonetaryAmt      = '" + POut.Double(benefit.MonetaryAmt) + "', " + "TimePeriod       =  " + POut.Int((int)benefit.TimePeriod) + ", " + "QuantityQualifier=  " + POut.Int((int)benefit.QuantityQualifier) + ", " + "Quantity         =  " + POut.Byte(benefit.Quantity) + ", " + "CodeNum          =  " + POut.Long(benefit.CodeNum) + ", " + "CoverageLevel    =  " + POut.Int((int)benefit.CoverageLevel) + " " + "WHERE BenefitNum = " + POut.Long(benefit.BenefitNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Benefit in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Benefit benefit, Benefit oldBenefit) throws Exception {
        String command = "";
        if (benefit.PlanNum != oldBenefit.PlanNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PlanNum = " + POut.Long(benefit.PlanNum) + "";
        }
         
        if (benefit.PatPlanNum != oldBenefit.PatPlanNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatPlanNum = " + POut.Long(benefit.PatPlanNum) + "";
        }
         
        if (benefit.CovCatNum != oldBenefit.CovCatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CovCatNum = " + POut.Long(benefit.CovCatNum) + "";
        }
         
        if (benefit.BenefitType != oldBenefit.BenefitType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "BenefitType = " + POut.Int((int)benefit.BenefitType) + "";
        }
         
        if (benefit.Percent != oldBenefit.Percent)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Percent = " + POut.Int(benefit.Percent) + "";
        }
         
        if (benefit.MonetaryAmt != oldBenefit.MonetaryAmt)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MonetaryAmt = '" + POut.Double(benefit.MonetaryAmt) + "'";
        }
         
        if (benefit.TimePeriod != oldBenefit.TimePeriod)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TimePeriod = " + POut.Int((int)benefit.TimePeriod) + "";
        }
         
        if (benefit.QuantityQualifier != oldBenefit.QuantityQualifier)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "QuantityQualifier = " + POut.Int((int)benefit.QuantityQualifier) + "";
        }
         
        if (benefit.Quantity != oldBenefit.Quantity)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Quantity = " + POut.Byte(benefit.Quantity) + "";
        }
         
        if (benefit.CodeNum != oldBenefit.CodeNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CodeNum = " + POut.Long(benefit.CodeNum) + "";
        }
         
        if (benefit.CoverageLevel != oldBenefit.CoverageLevel)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CoverageLevel = " + POut.Int((int)benefit.CoverageLevel) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE benefit SET " + command + " WHERE BenefitNum = " + POut.Long(benefit.BenefitNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Benefit from the database.
    */
    public static void delete(long benefitNum) throws Exception {
        String command = "DELETE FROM benefit " + "WHERE BenefitNum = " + POut.Long(benefitNum);
        Db.NonQ(command);
    }

}


