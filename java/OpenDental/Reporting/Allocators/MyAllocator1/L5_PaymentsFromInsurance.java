//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemTypes;
import OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_ODSQLPullStrings_LedgerItem;

public class L5_PaymentsFromInsurance  extends PP_ODSQLPullStrings_LedgerItem 
{
    public String oDPullString(long Guarantor, boolean IsPulledFlag) throws Exception {
        //Payments2InsuranceUnion
        //1
        //Assums there is no provider #0
        //
        String rVal = "SELECT " + "\n      '" + ((Enum)LedgerItemTypes.Payment).ordinal() + "', " + "\n      patient.Guarantor, " + "\n      patient.PatNum, " + "\n      0, " + "\n      claimpayment.CheckDate, " + "\n      -claimproc.InsPayAmt, " + "\n       claimproc.ClaimProcNum," + "\n       " + ((Enum)OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled.ClaimProcPayment).ordinal() + "\n FROM claimproc,patient,claimpayment \n WHERE " + "\n      claimproc.PatNum = Patient.PatNum " + "\n      && claimpayment.ClaimPaymentNum = claimproc.ClaimPaymentNum ";
        if (Guarantor != 0)
            rVal += "\n      && Patient.Guarantor = " + Guarantor;
         
        return rVal;
    }

}


//if (IsPulledFlag)
//    rVal += "\n    && claimproc." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//else
//    rVal += "\n    && claimproc." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";