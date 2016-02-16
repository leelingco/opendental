//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemTypes;
import OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_ODSQLPullStrings_LedgerItem;

/**
* Used to replace L4_  when it was determined that we will not be using paysplit info
*/
public class L4b_PaymentsFromPayment  extends PP_ODSQLPullStrings_LedgerItem 
{
    public String oDPullString(long Guarantor, boolean IsPulledFlag) throws Exception {
        //Payments1Union
        //1
        //Assums there is no provider #0
        //
        String rVal = "SELECT " + "\n      '" + ((Enum)LedgerItemTypes.Payment).ordinal() + "', " + "\n      patient.Guarantor, " + "\n      patient.PatNum, " + "\n      0, " + "\n      payment.PayDate, " + "\n      -payment.PayAmt, " + "\n       payment.PayNum," + "\n       " + ((Enum)OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled.Payment).ordinal() + "\n FROM payment,patient \n WHERE " + "\n      payment.PatNum = Patient.PatNum ";
        if (Guarantor != 0)
            rVal += "\n      && Patient.Guarantor = " + Guarantor;
         
        return rVal;
    }

}


//if (IsPulledFlag)
//    rVal += "\n    && paysplit." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//else
//    rVal += "\n    && paysplit." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";