//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;

import CS2JNet.System.StringSupport;
import ODR.DataConnection;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;

/**
* 
*/
public class GetData   
{
    /**
    * 
    */
    public static String pref(String prefName) throws Exception {
        String command = "SELECT ValueString FROM preference WHERE PrefName='" + POut.string(prefName) + "'";
        DataConnection dcon = new DataConnection();
        DataTable table = dcon.getTable(command);
        return table.Rows[0][0].ToString();
    }

    /**
    * asOfDate is typically 12/31/...
    */
    public static float netIncomeThisYear(Object asOfDateObj) throws Exception {
        DateTime asOfDate = new DateTime();
        if (asOfDateObj.GetType() == String.class)
        {
            asOfDate = PIn.Date(asOfDateObj.ToString());
        }
        else if (asOfDateObj.GetType() == DateTime.class)
        {
            asOfDate = (DateTime)asOfDateObj;
        }
        else
        {
            return 0;
        }  
        DateTime firstOfYear = new DateTime(asOfDate.Year, 1, 1);
        String command = "SELECT SUM(CreditAmt), SUM(DebitAmt), AcctType " + "FROM journalentry,account " + "WHERE journalentry.AccountNum=account.AccountNum " + "AND DateDisplayed >= " + POut.date(firstOfYear) + " AND DateDisplayed <= " + POut.date(asOfDate) + " GROUP BY AcctType";
        DataConnection dcon = new DataConnection();
        DataTable table = dcon.getTable(command);
        float retVal = 0;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //income
            if (StringSupport.equals(table.Rows[i][2].ToString(), "3") || StringSupport.equals(table.Rows[i][2].ToString(), "4"))
            {
                //expense
                retVal += PIn.Float(table.Rows[i][0].ToString());
                //add credit
                retVal -= PIn.Float(table.Rows[i][1].ToString());
            }
             
        }
        return retVal;
    }

    //subtract debit
    //if it's an expense, we are subtracting (income-expense), but the signs cancel.
    /**
    * Gets sum of all income-expenses for all previous years. asOfDate could be any date
    */
    public static float retainedEarningsAuto(Object asOfDateObj) throws Exception {
        DateTime asOfDate = new DateTime();
        if (asOfDateObj.GetType() == String.class)
        {
            asOfDate = PIn.Date(asOfDateObj.ToString());
        }
        else if (asOfDateObj.GetType() == DateTime.class)
        {
            asOfDate = (DateTime)asOfDateObj;
        }
        else
        {
            return 0;
        }  
        DateTime firstOfYear = new DateTime(asOfDate.Year, 1, 1);
        String command = "SELECT SUM(CreditAmt), SUM(DebitAmt), AcctType " + "FROM journalentry,account " + "WHERE journalentry.AccountNum=account.AccountNum " + "AND DateDisplayed < " + POut.date(firstOfYear) + " GROUP BY AcctType";
        DataConnection dcon = new DataConnection();
        DataTable table = dcon.getTable(command);
        float retVal = 0;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //income
            if (StringSupport.equals(table.Rows[i][2].ToString(), "3") || StringSupport.equals(table.Rows[i][2].ToString(), "4"))
            {
                //expense
                retVal += PIn.Float(table.Rows[i][0].ToString());
                //add credit
                retVal -= PIn.Float(table.Rows[i][1].ToString());
            }
             
        }
        return retVal;
    }

}


//subtract debit
//if it's an expense, we are subtracting (income-expense), but the signs cancel.