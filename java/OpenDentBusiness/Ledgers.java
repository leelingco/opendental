//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* This does not correspond to any table in the database.  It works with a variety of tables to calculate aging.
*/
public class Ledgers   
{
    /**
    * This runs aging for all patients.  If using monthly aging, it always just runs the aging as of the last date again.  If using daily aging, it runs it as of today.  This logic used to be in FormAging, but is now centralized.
    */
    public static void runAging() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (PrefC.getBool(PrefName.AgingCalculatedMonthlyInsteadOfDaily))
        {
            ComputeAging(0, PrefC.getDate(PrefName.DateLastAging), false);
        }
        else
        {
            ComputeAging(0, DateTime.Today, false);
            if (PrefC.getDate(PrefName.DateLastAging) != DateTime.Today)
            {
                Prefs.UpdateString(PrefName.DateLastAging, POut.Date(DateTime.Today, false));
            }
             
        } 
    }

    //Since this is always called from UI, the above line works fine to keep the prefs cache current.
    /**
    * Computes aging for the family specified. Specify guarantor=0 in order to calculate aging for all families.
    * Gets all info from database.
    * The aging calculation will use the following rules within each family:
    * 1) The aging "buckets" (0 to 30, 31 to 60, 61 to 90 and Over 90) ONLY include account activity on or
    * before AsOfDate.
    * 2) BalTotal will always include all account activity, even future entries, except when in historical
    * mode, where BalTotal will exclude account activity after AsOfDate.
    * 3) InsEst will always include all insurance estimates, even future estimates, except when in
    * historical mode where InsEst excludes insurance estimates after AsOfDate.
    * 4) PayPlanDue will always include all payment plan charges minus credits, except when in
    * historical mode where PayPlanDue excludes payment plan charges and payments after AsOfDate.
    */
    public static void computeAging(long guarantor, DateTime AsOfDate, boolean historic) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), guarantor, AsOfDate, historic);
            return ;
        }
         
        //Zero out either entire database or entire family.
        //Need to zero everything out first to catch former guarantors.
        String command = "UPDATE patient SET " + "Bal_0_30   = 0" + ",Bal_31_60 = 0" + ",Bal_61_90 = 0" + ",BalOver90 = 0" + ",InsEst    = 0" + ",BalTotal  = 0" + ",PayPlanDue= 0";
        if (guarantor != 0)
        {
            command += " WHERE Guarantor=" + POut.long(guarantor);
        }
         
        Db.nonQ(command);
        if (AsOfDate.Year < 1880)
        {
            AsOfDate = DateTime.Today;
        }
         
        String asOfDate = POut.date(AsOfDate);
        String billInAdvanceDate = POut.Date(AsOfDate.AddDays(PrefC.getLong(PrefName.PayPlansBillInAdvanceDays)));
        if (historic)
        {
            billInAdvanceDate = POut.Date(DateTime.Today.AddDays(PrefC.getLong(PrefName.PayPlansBillInAdvanceDays)));
        }
         
        String thirtyDaysAgo = POut.Date(AsOfDate.AddDays(-30));
        String sixtyDaysAgo = POut.Date(AsOfDate.AddDays(-60));
        String ninetyDaysAgo = POut.Date(AsOfDate.AddDays(-90));
        String familyPatNums = "";
        Collection<String> familyPatNumList = new Collection<String>();
        if (guarantor != 0)
        {
            familyPatNums = "(";
            command = "SELECT p.PatNum FROM patient p WHERE p.Guarantor=" + guarantor;
            DataTable tFamilyPatNums = Db.getTable(command);
            for (int i = 0;i < tFamilyPatNums.Rows.Count;i++)
            {
                if (i > 0)
                {
                    familyPatNums += ",";
                }
                 
                String patNum = tFamilyPatNums.Rows[i][0].ToString();
                familyPatNums += patNum;
                familyPatNumList.Add(patNum);
            }
            familyPatNums += ")";
        }
         
        //We use temporary tables using the "CREATE TEMPORARY TABLE" syntax here so that any temporary
        //tables created are specific to the current database connection and no actual files are created
        //in the database. This will prevent rogue files from collecting in the live database, and will
        //prevent aging calculations on one computer from affecting the aging calculations on another computer.
        //Unfortunately, this has one side effect, which is that our connector reopens the
        //connection every time a command is run, so the temporary tables only last for a single
        //command. To get around this issue, we run the aging script as a single command/script.
        //Unfortunately, the "CREATE TEMPORARY TABLE" syntax gets replicated if MySQL replication is enabled,
        //which becomes a problem becauase the command is then no longer connection specific. Therefore,
        //to accomodate to the few offices using database replication with MySQL, when creating the temporary aging tables,
        //we append a random string to the temporary table names so the possibility to temporary table
        //name collision is practically zero.
        //Create a temporary table to calculate aging into temporarily, so that the patient table is
        //not being changed by multiple threads if more than one user is calculating aging.
        //Since a temporary table is dropped automatically only when the connection is closed,
        //and since we use connection pooling, drop them before using.
        String tempTableSuffix = CodeBase.MiscUtils.createRandomAlphaNumericString(14);
        //max size for a table name in oracle is 30 chars.
        String tempAgingTableName = "tempaging" + tempTableSuffix;
        String tempOdAgingTransTableName = "tempodagingtrans" + tempTableSuffix;
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            try
            {
                //We would use DROP TEMPORARY TABLE IF EXISTS syntax here but no such syntax exists in Oracle.
                command = "DROP TEMPORARY TABLE " + tempAgingTableName + ", " + tempOdAgingTransTableName;
                Db.nonQ(command);
            }
            catch (Exception __dummyCatchVar0)
            {
            }

            try
            {
                //The tables do not exist. Nothing to do.
                //We would use DROP TABLE IF EXISTS syntax here but no such syntax exists in Oracle.
                command = "DROP TABLE " + tempAgingTableName + ", " + tempOdAgingTransTableName;
                Db.nonQ(command);
            }
            catch (Exception __dummyCatchVar1)
            {
            }
        
        }
        else
        {
            //The tables do not exist. Nothing to do.
            command = "DROP TEMPORARY TABLE IF EXISTS " + tempAgingTableName + ", " + tempOdAgingTransTableName;
            Db.nonQ(command);
            command = "DROP TABLE IF EXISTS " + tempAgingTableName + ", " + tempOdAgingTransTableName;
            Db.nonQ(command);
        } 
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            command = "CREATE GLOBAL TEMPORARY TABLE " + tempAgingTableName + " (" + "PatNum NUMBER," + "Guarantor NUMBER," + "Charges_0_30 NUMBER(38,8) DEFAULT 0," + "Charges_31_60 NUMBER(38,8) DEFAULT 0," + "Charges_61_90 NUMBER(38,8) DEFAULT 0," + "ChargesOver90 NUMBER(38,8) DEFAULT 0," + "TotalCredits NUMBER(38,8) DEFAULT 0," + "InsEst NUMBER(38,8) DEFAULT 0," + "PayPlanDue NUMBER(38,8) DEFAULT 0," + "BalTotal NUMBER(38,8) DEFAULT 0" + ");";
        }
        else
        {
            command = "CREATE TEMPORARY TABLE " + tempAgingTableName + " (" + "PatNum bigint," + "Guarantor bigint," + "Charges_0_30 DOUBLE DEFAULT 0," + "Charges_31_60 DOUBLE DEFAULT 0," + "Charges_61_90 DOUBLE DEFAULT 0," + "ChargesOver90 DOUBLE DEFAULT 0," + "TotalCredits DOUBLE DEFAULT 0," + "InsEst DOUBLE DEFAULT 0," + "PayPlanDue DOUBLE DEFAULT 0," + "BalTotal DOUBLE DEFAULT 0" + ");";
        } 
        if (guarantor == 0)
        {
            //We insert all of the patient numbers and guarantor numbers only when we are running aging for everyone,
            //since we do not want to examine every patient record when running aging for a single family.
            command += "INSERT INTO " + tempAgingTableName + " (PatNum,Guarantor) " + "SELECT p.PatNum,p.Guarantor " + "FROM patient p;";
            //When there is only one patient that aging is being calculated for, then the indexes actually
            //slow the calculation down slightly, but they significantly improve the speed when aging is being
            //calculated for all familes.
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                command += "CREATE INDEX " + tempAgingTableName.ToUpper() + "_PATNUM ON " + tempAgingTableName + " (PatNum);";
                command += "CREATE INDEX " + tempAgingTableName.ToUpper() + "_GUAR ON " + tempAgingTableName + " (Guarantor);";
            }
            else
            {
                command += "ALTER TABLE " + tempAgingTableName + " ADD INDEX IDX_" + tempAgingTableName.ToUpper() + "_PATNUM (PatNum);";
                command += "ALTER TABLE " + tempAgingTableName + " ADD INDEX IDX_" + tempAgingTableName.ToUpper() + "_GUARANTOR (Guarantor);";
            } 
        }
        else
        {
            //Manually create insert statements to avoid having the database system visit every patient record again.
            //In my testing, this saves about 0.25 seconds on an individual family aging calculation on my machine in MySQL.
            command += "INSERT INTO " + tempAgingTableName + " (PatNum,Guarantor) VALUES ";
            for (int i = 0;i < familyPatNumList.Count;i++)
            {
                if (i > 0)
                {
                    command += ",";
                }
                 
                command += "(" + familyPatNumList[i] + "," + guarantor + ")";
            }
            command += ";";
        } 
        //Create another temporary table which holds a very concise summary of the entire office transaction history,
        //so that all transactions can be treated as either a general credit or a general charge in the aging calculation.
        //Since we are recreating a temporary table with the same name as last time aging was run,
        //the old temporary table gets wiped out.
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            command += "CREATE GLOBAL TEMPORARY TABLE " + tempOdAgingTransTableName + " (" + "PatNum NUMBER," + "TranDate DATE DEFAULT TO_DATE('0001-01-01', 'yyyy-mm-dd')," + "TranAmount NUMBER(38,8) DEFAULT 0" + ");";
        }
        else
        {
            command += "CREATE TEMPORARY TABLE " + tempOdAgingTransTableName + " (" + "PatNum bigint," + "TranDate DATE DEFAULT '0001-01-01'," + "TranAmount DOUBLE DEFAULT 0" + ");";
        } 
        //Get the completed procedure dates and charges for the entire office history.
        command += "INSERT INTO " + tempOdAgingTransTableName + " (PatNum,TranDate,TranAmount) " + "SELECT pl.PatNum PatNum," + "pl.ProcDate TranDate," + "pl.ProcFee*(pl.UnitQty+pl.BaseUnits) TranAmount " + "FROM procedurelog pl " + "WHERE pl.ProcStatus=2 " + (guarantor == 0 ? "" : (" AND pl.PatNum IN " + familyPatNums)) + ";";
        //Paysplits for the entire office history.
        //Only splits not attached to payment plans.
        command += "INSERT INTO " + tempOdAgingTransTableName + " (PatNum,TranDate,TranAmount) " + "SELECT ps.PatNum PatNum," + "ps.DatePay TranDate," + "-ps.SplitAmt TranAmount " + "FROM paysplit ps " + "WHERE ps.PayPlanNum=0 " + (guarantor == 0 ? "" : (" AND ps.PatNum IN " + familyPatNums)) + ";";
        //Get the adjustment dates and amounts for the entire office history.
        command += "INSERT INTO " + tempOdAgingTransTableName + " (PatNum,TranDate,TranAmount) " + "SELECT a.PatNum PatNum," + "a.AdjDate TranDate," + "a.AdjAmt TranAmount " + "FROM adjustment a " + "WHERE a.AdjAmt<>0 " + (guarantor == 0 ? "" : (" AND a.PatNum IN " + familyPatNums)) + ";";
        //Claim payments and capitation writeoffs for the entire office history.
        //Always use DateCP rather than ProcDate to calculate the date of a claim payment.
        //received, supplemental, CapClaim or CapComplete.
        command += "INSERT INTO " + tempOdAgingTransTableName + " (PatNum,TranDate,TranAmount) " + "SELECT cp.PatNum PatNum," + "cp.DateCp TranDate," + "-cp.InsPayAmt-cp.Writeoff TranAmount " + "FROM claimproc cp " + "WHERE cp.status IN (1,4,5,7) " + (guarantor == 0 ? "" : (" AND cp.PatNum IN " + familyPatNums)) + ";";
        //Payment plan principal for the entire office history.
        command += "INSERT INTO " + tempOdAgingTransTableName + " (PatNum,TranDate,TranAmount) " + "SELECT pp.PatNum PatNum," + "pp.PayPlanDate TranDate," + "-pp.CompletedAmt TranAmount " + "FROM payplan pp " + "WHERE pp.CompletedAmt<>0 " + (guarantor == 0 ? "" : (" AND pp.PatNum IN " + familyPatNums)) + ";";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            //The aging calculation buckets, insurance estimates, and payment plan due amounts are
            //not yet calculated for Oracle as they have not been needed yet. Just calculates
            //account balance totals.
            String tempTotalsTableName = "temptotals" + tempTableSuffix;
            command += "CREATE GLOBAL TEMPORARY TABLE " + tempTotalsTableName + " (" + "PatNum NUMBER DEFAULT 0," + "BalTotal NUMBER(38,8) DEFAULT 0" + ");";
            command += "CREATE INDEX " + tempTotalsTableName.ToUpper() + "_PATNU ON " + tempTotalsTableName + " (PatNum);";
            command += "INSERT INTO " + tempTotalsTableName + " " + "SELECT PatNum,ROUND(SUM(TranAmount),2) FROM " + tempOdAgingTransTableName + "GROUP BY PatNum;";
            command += "UPDATE patient p " + "SET p.BalTotal=(SELECT t.BalTotal FROM " + tempTotalsTableName + " t WHERE t.PatNum=p.PatNum " + DbHelper.limitAnd(1) + ");";
            Db.nonQ(command);
        }
        else
        {
            //Now that we have all of the pertinent transaction history, we will calculate all of the charges for
            //the associated patients.
            //Calculate over 90 day charges for all specified families.
            //Calculate the total charges for each patient during this time period and
            //place the results into memory table 'chargesOver90'.
            command += "UPDATE " + tempAgingTableName + " a," + "(SELECT t.PatNum,SUM(t.TranAmount) TotalCharges FROM " + tempOdAgingTransTableName + " t " + "WHERE t.TranAmount>0 AND t.TranDate<" + DbHelper.dateColumn(ninetyDaysAgo) + " GROUP BY t.PatNum) chargesOver90 " + "SET a.ChargesOver90=chargesOver90.TotalCharges " + "WHERE a.PatNum=chargesOver90.PatNum;";
            //Update the tempaging table with the caculated charges for the time period.
            //Calculate 61 to 90 day charges for all specified families.
            //Calculate the total charges for each patient during this time period and
            //place the results into memory table 'charges_61_90'.
            command += "UPDATE " + tempAgingTableName + " a," + "(SELECT t.PatNum,SUM(t.TranAmount) TotalCharges FROM " + tempOdAgingTransTableName + " t " + "WHERE t.TranAmount>0 AND t.TranDate<" + DbHelper.dateColumn(sixtyDaysAgo) + " AND " + "t.TranDate>=" + DbHelper.dateColumn(ninetyDaysAgo) + " GROUP BY t.PatNum) charges_61_90 " + "SET a.Charges_61_90=charges_61_90.TotalCharges " + "WHERE a.PatNum=charges_61_90.PatNum;";
            //Update the tempaging table with the caculated charges for the time period.
            //Calculate 31 to 60 day charges for all specified families.
            //Calculate the total charges for each patient during this time period and
            //place the results into memory table 'charges_31_60'.
            command += "UPDATE " + tempAgingTableName + " a," + "(SELECT t.PatNum,SUM(t.TranAmount) TotalCharges FROM " + tempOdAgingTransTableName + " t " + "WHERE t.TranAmount>0 AND t.TranDate<" + DbHelper.dateColumn(thirtyDaysAgo) + " AND " + "t.TranDate>=" + DbHelper.dateColumn(sixtyDaysAgo) + " GROUP BY t.PatNum) charges_31_60 " + "SET a.Charges_31_60=charges_31_60.TotalCharges " + "WHERE a.PatNum=charges_31_60.PatNum;";
            //Update the tempaging table with the caculated charges for the time period.
            //Calculate 0 to 30 day charges for all specified families.
            //Calculate the total charges for each patient during this time period and
            //place the results into memory table 'charges_0_30'.
            command += "UPDATE " + tempAgingTableName + " a," + "(SELECT t.PatNum,SUM(t.TranAmount) TotalCharges FROM " + tempOdAgingTransTableName + " t " + "WHERE t.TranAmount>0 AND t.TranDate<=" + DbHelper.dateColumn(asOfDate) + " AND " + "t.TranDate>=" + DbHelper.dateColumn(thirtyDaysAgo) + " GROUP BY t.PatNum) charges_0_30 " + "SET a.Charges_0_30=charges_0_30.TotalCharges " + "WHERE a.PatNum=charges_0_30.PatNum;";
            //Update the tempaging table with the caculated charges for the time period.
            //Calculate the total credits each patient has ever received so we can apply the credits to the aged charges below.
            //Calculate the total credits for each patient and store the results in memory table 'credits'.
            command += "UPDATE " + tempAgingTableName + " a," + "(SELECT t.PatNum,-SUM(t.TranAmount) TotalCredits FROM " + tempOdAgingTransTableName + " t " + "WHERE t.TranAmount<0 AND t.TranDate<=" + DbHelper.dateColumn(asOfDate) + " GROUP BY t.PatNum) credits " + "SET a.TotalCredits=credits.TotalCredits " + "WHERE a.PatNum=credits.PatNum;";
            //Update the total credit for each patient into the tempaging table.
            //Calculate claim estimates for each patient individually on or before the specified date.
            //Calculate the insurance estimates for each patient and store the results into
            //memory table 't'.
            command += "UPDATE " + tempAgingTableName + " a," + "(SELECT cp.PatNum,SUM(cp.InsPayEst+cp.Writeoff) InsEst " + "FROM claimproc cp " + "WHERE cp.PatNum<>0 " + (historic ? (" AND ((cp.Status=0 AND cp.ProcDate<=" + DbHelper.dateColumn(asOfDate) + ") OR " + "(cp.Status=1 AND cp.DateCP>" + DbHelper.dateColumn(asOfDate) + ")) AND cp.ProcDate<=" + DbHelper.dateColumn(asOfDate) + " ") : " AND cp.Status=0 ") + (guarantor == 0 ? "" : (" AND cp.PatNum IN " + familyPatNums + " ")) + "GROUP BY cp.PatNum) t " + "SET a.InsEst=t.InsEst " + "WHERE a.PatNum=t.PatNum;";
            //not received claims.
            //Update the tempaging table with the insurance estimates for each patient.
            //Calculate the payment plan charges for each payment plan guarantor
            //on or before the specified date (also considering the PayPlansBillInAdvanceDays setting).
            //We cannot exclude payments made outside the specified family, since payment plan
            //guarantors can be in another family.
            command += "UPDATE " + tempAgingTableName + " a," + "(SELECT ppc.Guarantor,IFNULL(SUM(ppc.Principal+ppc.Interest),0) PayPlanCharges " + "FROM payplancharge ppc " + "WHERE ppc.ChargeDate<=" + DbHelper.dateColumn(billInAdvanceDate) + " " + "GROUP BY ppc.Guarantor) c " + "SET a.PayPlanDue=c.PayPlanCharges " + "WHERE c.Guarantor=a.PatNum;";
            //bill in adv. date accounts for historic vs current because of how it is set above.
            //Calculate the total payments made to each payment plan
            //on or before the specified date and store the results in memory table 'p'.
            //We cannot exclude payments made outside the specified family, since payment plan
            //guarantors can be in another family.
            //only payments attached to payment plans.
            command += "UPDATE " + tempAgingTableName + " a," + "(SELECT ps.PatNum,SUM(ps.SplitAmt) PayPlanPayments " + "FROM paysplit ps " + "WHERE ps.PayPlanNum<>0 " + (historic ? (" AND ps.DatePay<=" + DbHelper.dateColumn(asOfDate) + " ") : "") + "GROUP BY ps.PatNum) p " + "SET a.PayPlanDue=a.PayPlanDue-p.PayPlanPayments " + "WHERE p.PatNum=a.PatNum;";
            //Calculate the total balance for each patient.
            //In historical mode, only transactions on or before AsOfDate will be included.
            //Calculate the total balance for each patient and
            //place the results into memory table 'totals'.
            command += "UPDATE " + tempAgingTableName + " a," + "(SELECT t.PatNum,SUM(t.TranAmount) BalTotal FROM " + tempOdAgingTransTableName + " t " + "WHERE t.TranAmount<>0 " + (historic ? (" AND t.TranDate<=" + DbHelper.dateColumn(asOfDate)) : "") + " GROUP BY t.PatNum) totals " + "SET a.BalTotal=totals.BalTotal " + "WHERE a.PatNum=totals.PatNum;";
            //Update the tempaging table with the caculated charges for the time period.
            //Update the family aged balances onto the guarantor rows of the patient table
            //by placing credits on oldest charges first, then on younger charges.
            //Sum each colum within each family group inside of the tempaging table so that we are now
            //using family amounts instead of individual patient amounts, and store the result into
            //memory table 'f'.
            command += "UPDATE patient p," + "(SELECT a.Guarantor,SUM(a.Charges_0_30) Charges_0_30,SUM(a.Charges_31_60) Charges_31_60," + "SUM(a.Charges_61_90) Charges_61_90,SUM(a.ChargesOver90) ChargesOver90," + "SUM(TotalCredits) TotalCredits,SUM(InsEst) InsEst,SUM(PayPlanDue) PayPlanDue," + "SUM(BalTotal) BalTotal " + "FROM " + tempAgingTableName + " a " + "GROUP BY a.Guarantor) f " + "SET " + "p.BalOver90=ROUND((CASE " + "WHEN f.TotalCredits>=f.ChargesOver90 THEN 0 " + "ELSE f.ChargesOver90-f.TotalCredits END),2)," + "p.Bal_61_90=ROUND((CASE " + "WHEN f.TotalCredits<=f.ChargesOver90 THEN f.Charges_61_90 " + "WHEN f.ChargesOver90+f.Charges_61_90<=f.TotalCredits THEN 0 " + "ELSE f.ChargesOver90+f.Charges_61_90-f.TotalCredits END),2)," + "p.Bal_31_60=ROUND((CASE " + "WHEN f.TotalCredits<f.ChargesOver90+f.Charges_61_90 THEN f.Charges_31_60 " + "WHEN f.ChargesOver90+f.Charges_61_90+f.Charges_31_60<=f.TotalCredits THEN 0 " + "ELSE f.ChargesOver90+f.Charges_61_90+f.Charges_31_60-f.TotalCredits END),2)," + "p.Bal_0_30=ROUND((CASE " + "WHEN f.TotalCredits<f.ChargesOver90+f.Charges_61_90+f.Charges_31_60 THEN f.Charges_0_30 " + "WHEN f.ChargesOver90+f.Charges_61_90+f.Charges_31_60+f.Charges_0_30<=f.TotalCredits THEN 0 " + "ELSE f.ChargesOver90+f.Charges_61_90+f.Charges_31_60+f.Charges_0_30-f.TotalCredits END),2)," + "p.BalTotal=ROUND(f.BalTotal,2)," + "p.InsEst=ROUND(f.InsEst,2)," + "p.PayPlanDue=ROUND(f.PayPlanDue,2) " + "WHERE p.PatNum=f.Guarantor;";
            //Perform the update of the patient table based on the family amounts summed into table 'f', and
            //distribute the payments into the oldest balances first.
            //over 90 balance paid in full.
            //over 90 balance partially paid or unpaid.
            //61 to 90 day balance unpaid.
            //61 to 90 day balance paid in full.
            //61 to 90 day balance partially paid.
            //31 to 60 day balance unpaid.
            //31 to 60 day balance paid in full.
            //31 to 60 day balance partially paid.
            //0 to 30 day balance unpaid.
            //0 to 30 day balance paid in full.
            //0 to 30 day balance partially paid.
            //Aging calculations only apply to guarantors.
            Db.nonQ(command);
        } 
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            try
            {
                //We would use DROP TEMPORARY TABLE IF EXISTS syntax here but no such syntax exists in Oracle.
                command = "DROP TEMPORARY TABLE " + tempAgingTableName + ", " + tempOdAgingTransTableName;
                Db.nonQ(command);
            }
            catch (Exception __dummyCatchVar2)
            {
            }

            try
            {
                //The tables do not exist. Nothing to do.
                //We would use DROP TABLE IF EXISTS syntax here but no such syntax exists in Oracle.
                command = "DROP TABLE " + tempAgingTableName + ", " + tempOdAgingTransTableName;
                Db.nonQ(command);
            }
            catch (Exception __dummyCatchVar3)
            {
            }
        
        }
        else
        {
            //The tables do not exist. Nothing to do.
            command = "DROP TEMPORARY TABLE IF EXISTS " + tempAgingTableName + ", " + tempOdAgingTransTableName;
            Db.nonQ(command);
            command = "DROP TABLE IF EXISTS " + tempAgingTableName + ", " + tempOdAgingTransTableName;
            Db.nonQ(command);
        } 
    }

}


