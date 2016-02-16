//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CreditCard;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Meth;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class CreditCards   
{
    /**
    * 
    */
    public static List<CreditCard> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<CreditCard>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM creditcard WHERE PatNum = " + POut.long(patNum) + " ORDER BY ItemOrder DESC";
        return Crud.CreditCardCrud.SelectMany(command);
    }

    /**
    * Gets one CreditCard from the db.
    */
    public static CreditCard getOne(long creditCardNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<CreditCard>GetObject(MethodBase.GetCurrentMethod(), creditCardNum);
        }
         
        return Crud.CreditCardCrud.SelectOne(creditCardNum);
    }

    /**
    * 
    */
    public static long insert(CreditCard creditCard) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            creditCard.CreditCardNum = Meth.GetLong(MethodBase.GetCurrentMethod(), creditCard);
            return creditCard.CreditCardNum;
        }
         
        return Crud.CreditCardCrud.Insert(creditCard);
    }

    /**
    * 
    */
    public static void update(CreditCard creditCard) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), creditCard);
            return ;
        }
         
        Crud.CreditCardCrud.Update(creditCard);
    }

    /**
    * 
    */
    public static void delete(long creditCardNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), creditCardNum);
            return ;
        }
         
        String command = "DELETE FROM creditcard WHERE CreditCardNum = " + POut.long(creditCardNum);
        Db.nonQ(command);
    }

    /**
    * Gets the masked CC# and exp date for all cards setup for monthly charges for the specified patient.  Only used for filling [CreditCardsOnFile] variable when emailing statements.
    */
    public static String getMonthlyCardsOnFile(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String result = "";
        String command = "SELECT * FROM creditcard WHERE PatNum=" + POut.long(patNum) + " AND (" + DbHelper.year("DateStop") + "<1880 OR DateStop>" + DbHelper.now() + ") " + " AND ChargeAmt>0";
        //Recurring card is active.
        List<CreditCard> monthlyCards = Crud.CreditCardCrud.SelectMany(command);
        for (int i = 0;i < monthlyCards.Count;i++)
        {
            if (i > 0)
            {
                result += ", ";
            }
             
            result += monthlyCards[i].CCNumberMasked + " exp:" + monthlyCards[i].CCExpiration.ToString("MM/yy");
        }
        return result;
    }

    /**
    * Returns list of credit cards that are ready for a recurring charge.
    */
    public static DataTable getRecurringChargeList() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        DataTable table = new DataTable();
        //This query will return patient information and the latest recurring payment whom:
        //	-have recurring charges setup and today's date falls within the start and stop range.
        //	-have a total balance >= recurring charge amount
        //NOTE: Query will return patients with or without payments regardless of when that payment occurred, filtering is done below.
        String command = "SELECT PatNum,PatName,FamBalTotal,LatestPayment,DateStart,Address,AddressPat,Zip,ZipPat,XChargeToken,CCNumberMasked,CCExpiration,ChargeAmt,PayPlanNum,ProvNum,ClinicNum " + "FROM (";
        //The 'SELECT 1' garuntees the UNION will not combine results with payment plans.
        command += "(SELECT 1,cc.PatNum," + DbHelper.concat("pat.LName","', '","pat.FName") + " PatName," + "guar.BalTotal-guar.InsEst FamBalTotal,CASE WHEN MAX(pay.PayDate) IS NULL THEN " + POut.date(new DateTime(1, 1, 1)) + " ELSE MAX(pay.PayDate) END LatestPayment," + "cc.DateStart,cc.Address,pat.Address AddressPat,cc.Zip,pat.Zip ZipPat,cc.XChargeToken,cc.CCNumberMasked,cc.CCExpiration,cc.ChargeAmt,cc.PayPlanNum,cc.DateStop,0 ProvNum,pat.ClinicNum " + "FROM (creditcard cc,patient pat,patient guar) " + "LEFT JOIN payment pay ON cc.PatNum=pay.PatNum AND pay.IsRecurringCC=1 " + "WHERE cc.PatNum=pat.PatNum " + "AND pat.Guarantor=guar.PatNum " + "AND cc.PayPlanNum=0 ";
        //Keeps card from showing up in case they have a balance AND is setup for payment plan.
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command += "GROUP BY cc.CreditCardNum) ";
        }
        else
        {
            //Oracle
            command += "GROUP BY cc.CreditCardNum,cc.PatNum," + DbHelper.concat("pat.LName","', '","pat.FName") + ",PatName,guar.BalTotal-guar.InsEst," + "cc.Address,cc.Zip,cc.XChargeToken,cc.CCNumberMasked,cc.CCExpiration,cc.ChargeAmt,cc.PayPlanNum,cc.DateStop) ";
        } 
        command += "UNION ";
        command += "(SELECT 2,cc.PatNum," + DbHelper.concat("pat.LName","', '","pat.FName") + " PatName,";
        //The 'SELECT 2' garuntees the UNION will not combine results with payments.
        //Special select statement to figure out how much is owed on a particular payment plan.  This total amount will be Labeled as FamBalTotal for UNION purposes.
        command += "ROUND((SELECT CASE WHEN SUM(ppc.Principal+ppc.Interest) IS NULL THEN 0 ELSE SUM(ppc.Principal+ppc.Interest) END " + "FROM PayPlanCharge ppc " + "WHERE ppc.ChargeDate <= " + DbHelper.curdate() + " AND ppc.PayPlanNum=cc.PayPlanNum) " + "- CASE WHEN SUM(ps.SplitAmt) IS NULL THEN 0 ELSE SUM(ps.SplitAmt) END,2) FamBalTotal,";
        command += "CASE WHEN MAX(ps.DatePay) IS NULL THEN " + POut.date(new DateTime(1, 1, 1)) + " ELSE MAX(pay.PayDate) END LatestPayment," + "cc.DateStart,cc.Address,pat.Address AddressPat,cc.Zip,pat.Zip ZipPat,cc.XChargeToken,cc.CCNumberMasked,cc.CCExpiration,cc.ChargeAmt,cc.PayPlanNum,cc.DateStop," + "(SELECT ppc1.ProvNum FROM payplancharge ppc1 WHERE ppc1.PayPlanNum=cc.PayPlanNum LIMIT 1) ProvNum," + "(SELECT ppc2.ClinicNum FROM payplancharge ppc2 WHERE ppc2.PayPlanNum=cc.PayPlanNum LIMIT 1) ClinicNum " + "FROM creditcard cc " + "INNER JOIN patient pat ON pat.PatNum=cc.PatNum " + "LEFT JOIN paysplit ps ON ps.PayPlanNum=cc.PayPlanNum AND ps.PayPlanNum<>0 " + "LEFT JOIN payment pay ON pay.PayNum=ps.PayNum AND pay.IsRecurringCC=1 " + "WHERE cc.PayPlanNum<>0 ";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command += "GROUP BY cc.CreditCardNum) ";
        }
        else
        {
            //Oracle
            command += "GROUP BY cc.CreditCardNum,cc.PatNum," + DbHelper.concat("pat.LName","', '","pat.FName") + ",PatName,guar.BalTotal-guar.InsEst," + "cc.Address,pat.Address,cc.Zip,pat.Zip,cc.XChargeToken,cc.CCNumberMasked,cc.CCExpiration,cc.ChargeAmt,cc.PayPlanNum,cc.DateStop) ";
        } 
        //Now we have all the results for payments and payment plans, so do an obvious filter. A more thorough filter happens later.
        command += ") due " + "WHERE FamBalTotal>=ChargeAmt " + "AND ChargeAmt>0 " + "AND DateStart<=" + DbHelper.curdate() + " " + "AND (DateStop>=" + DbHelper.curdate() + " OR YEAR(DateStop)<1880) ";
        table = Db.getTable(command);
        filterRecurringChargeList(table);
        return table;
    }

    /**
    * Talbe must include columns labeled LatestPayment and DateStart.
    */
    public static void filterRecurringChargeList(DataTable table) throws Exception {
        DateTime curDate = MiscData.getNowDateTime();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //Loop through table and remove patients that do not need to be charged yet.
            DateTime latestPayment = PIn.Date(table.Rows[i]["LatestPayment"].ToString());
            DateTime dateStart = PIn.Date(table.Rows[i]["DateStart"].ToString());
            if (curDate > latestPayment.AddDays(31))
            {
                continue;
            }
             
            //if it's been more than a month since they made any sort of payment
            //if we reduce the days below 31, then slighly more people will be charged, especially from Feb to March.  31 eliminates those false positives.
            //charge them
            //Not enough days in the current month so show on the last day of the month
            //Example: DateStart=8/31/2010 and the current month is February 2011 which does not have 31 days.
            //So the patient needs to show in list if current day is the 28th (or last day of the month).
            int daysInMonth = DateTime.DaysInMonth(curDate.Year, curDate.Month);
            if (daysInMonth <= dateStart.Day && daysInMonth == curDate.Day && curDate.Date != latestPayment.Date)
            {
                continue;
            }
             
            //if their recurring charge would fall on an invalid day of the month, and this is that last day of the month
            //we want them to show because the charge should go in on this date.
            if (curDate.Day >= dateStart.Day)
            {
                //If the recurring charge date was earlier in this month, then the recurring charge will go in for this month.
                if (curDate.Month > latestPayment.Month || curDate.Year > latestPayment.Year)
                {
                    continue;
                }
                 
            }
            else
            {
                //if the latest payment was last month (or earlier).  The year check catches December
                //No payments were made this month, so charge.
                //Else, current date is before the recurring date in the current month, so the recurring charge will be going in for last month
                //Check if payment didn't happen last month.
                if (curDate.AddMonths(-1).Month != latestPayment.Month && curDate.Date != latestPayment.Date)
                {
                    continue;
                }
                 
            } 
            //Charge did not happen last month so the patient needs to show up in list.
            //Example: Last month had a recurring charge set at the end of the month that fell on a weekend.
            //Today is the next month and still before the recurring charge date.
            //This will allow the charge for the previous month to happen if the 30 day check didn't catch it above.
            //Patient doesn't need to be charged yet so remove from the table.
            table.Rows.RemoveAt(i);
            i--;
        }
    }

    /**
    * Checks if token is in use.  This happened once and can cause the wrong card to be charged.
    */
    public static boolean isDuplicateXChargeToken(String token) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), token);
        }
         
        String command = "SELECT COUNT(*) FROM creditcard WHERE XChargeToken='" + POut.string(token) + "'";
        if (StringSupport.equals(Db.getCount(command), "1"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * Gets every credit card in the db with a token.
    */
    public static List<CreditCard> getCreditCardsWithTokens() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<CreditCard>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM creditcard WHERE XChargeToken!=\"\"";
        return Crud.CreditCardCrud.SelectMany(command);
    }

}


