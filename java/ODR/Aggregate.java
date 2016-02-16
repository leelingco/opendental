//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;

import CS2JNet.System.StringSupport;
import ODR.TestValue;
import OpenDentBusiness.PIn;

/**
* 
*/
public class Aggregate   
{
    private static double runningSum = new double();
    private static String groupByVal = new String();
    /**
    * 
    */
    public static String runningSum(String groupBy, String addValue) throws Exception {
        double num = PIn.decimal(addValue);
        if (groupByVal == null || !StringSupport.equals(groupBy, groupByVal))
        {
            //if new or changed group
            runningSum = 0;
        }
         
        groupByVal = groupBy;
        runningSum += num;
        return runningSum.ToString("F");
    }

    /**
    * 
    */
    public static String runningSumForAccounts(Object groupBy, Object debitAmt, Object creditAmt, Object acctType) throws Exception {
        if (debitAmt == null || creditAmt == null)
        {
            return 0.ToString("N");
        }
         
        //Cannot read debitAmt and creditAmt as decimals because it makes the general ledger detail report fail.  Simply cast as decimals when doing mathematical operations.
        double debit = (Double)debitAmt;
        //PIn.PDouble(debitAmt);
        double credit = (Double)creditAmt;
        //PIn.PDouble(creditAmt);
        if (groupByVal == null || !StringSupport.equals(groupBy.ToString(), groupByVal))
        {
            //if new or changed group
            runningSum = 0;
        }
         
        groupByVal = groupBy.ToString();
        if (TestValue.AccountDebitIsPos(acctType.ToString()))
        {
            runningSum += (double)debit - (double)credit;
        }
        else
        {
            runningSum += (double)credit - (double)debit;
        } 
        return runningSum.ToString("N");
    }

}


