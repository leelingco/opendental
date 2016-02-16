//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import CS2JNet.JavaSupport.language.RefSupport;
import OpenDental.Reporting.Allocators.MyAllocator1.L1_NegativeAdjustments;
import OpenDental.Reporting.Allocators.MyAllocator1.L2_PositiveAdjustments;
import OpenDental.Reporting.Allocators.MyAllocator1.L3_ClaimWriteOffAdjustments;
import OpenDental.Reporting.Allocators.MyAllocator1.L4b_PaymentsFromPayment;
import OpenDental.Reporting.Allocators.MyAllocator1.L5_PaymentsFromInsurance;
import OpenDental.Reporting.Allocators.MyAllocator1.L6_Charges;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_ODSQLPullStrings_LedgerItem;
import OpenDental.Reporting.Allocators.MyAllocator1.PU;

/**
* Used to Generate Query strings to pull the Ledger Data Required
* to equalize payments from Opendental.
*/
public class PP_ODSQLPullStrings_LedgerItem   
{
    /**
    * Can be used to check to make sure queries return correct headers.
    */
    public static final String[] HeaderOfQuery = { "LedgerItemType", "Guarantor", "PatNum", "ProvNum", "ItemDate", "Ammount", "ItemNum", "TableSource" };
    public enum HeaderOfQueryEnum
    {
        /**
        * Just for Reference Else where.  Should mathc HeaderOfQuery
        */
        LedgerItemType,
        Guarantor,
        PatNum,
        ProvNum,
        ItemDate,
        Ammount,
        ItemNum,
        TableSource
    }
    /**
    * Whole idea here is to have a method that changes the column names to match
    * what we believe the query should generate.  Throws an exception if
    * the Column Count does not match that linsted in HeaderOfQuery
    * 
    *  @param dt
    */
    public static void setColumnNames(RefSupport<System.Data.DataTable> dt) throws Exception {
        if (dt.getValue().Columns.Count != HeaderOfQuery.Length)
            PU.setEx("Column Count of DataTable passed to " + System.Reflection.MethodInfo.GetCurrentMethod().Name);
        else
            for (int i = 0;i < dt.getValue().Columns.Count;i++)
                dt.getValue().Columns[i].ColumnName = HeaderOfQuery[i]; 
    }

    /**
    * Provides a string that will pull ledger items for a specific guarantor.  If Guarantor is 0 it
    * will attempt to pull all items for all guarantors.
    * 
    *  @param Guarantor 
    *  @param IsPulledFlag 
    *  @return
    */
    public String oDPullString(long Guarantor, boolean IsPulledFlag) throws Exception {
        return "";
    }

    // just used to override
    /**
    * Generates a Query string that should pull all the ledger items for a specific Guarantor.
    * 
    * If Guarantor = 0 then it will pull for all Guarantors which is a lot of data.
    * So check for this condition.
    * 
    * <b>Note</b>: IsPulledFlag is currently depreciated 01-12-2008
    * 
    *  @param IsPulledFlag indicates whether you want to pull items with the IsPulled status or not
    *  @return
    */
    public static String pullAll_FromOD(long Guarantor, boolean IsPulledFlag) throws Exception {
        String rVal = "";
        L1_NegativeAdjustments L1 = new L1_NegativeAdjustments();
        L2_PositiveAdjustments L2 = new L2_PositiveAdjustments();
        L3_ClaimWriteOffAdjustments L3 = new L3_ClaimWriteOffAdjustments();
        //	L4_PaymentsFromPaySplit L4 = new L4_PaymentsFromPaySplit();
        // replaced with L4b_
        L4b_PaymentsFromPayment L4b = new L4b_PaymentsFromPayment();
        L5_PaymentsFromInsurance L5 = new L5_PaymentsFromInsurance();
        L6_Charges L6 = new L6_Charges();
        PP_ODSQLPullStrings_LedgerItem[] LArray;
        for (int i = 0;i < LArray.Length;i++)
        {
            rVal += LArray[i].ODPullString(Guarantor, IsPulledFlag);
            if (i != LArray.Length - 1)
                rVal += "\n UNION ALL\n";
             
        }
        return rVal;
    }

}


