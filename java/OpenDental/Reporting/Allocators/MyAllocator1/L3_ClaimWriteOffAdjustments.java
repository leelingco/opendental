//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemTypes;
import OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_ODSQLPullStrings_LedgerItem;

public class L3_ClaimWriteOffAdjustments  extends PP_ODSQLPullStrings_LedgerItem 
{
    public String oDPullString(long Guarantor, boolean IsPulledFlag) throws Exception {
        //ClaimWriteOFFUnion
        //2
        // Need to Check this to make sure includes 2ndary Provider
        //
        String rVal = "SELECT " + "\n      '" + ((Enum)LedgerItemTypes.NegAdjustment).ordinal() + "', " + "\n      patient.Guarantor, " + "\n      patient.PatNum, " + "\n      claimproc.ProvNum, " + "\n      claimpayment.CheckDate, " + "\n      -(claimproc.WriteOff), " + "\n       claimproc.ClaimProcNum," + "\n       " + ((Enum)OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled.ClaimProcWriteOff).ordinal() + "\n FROM claimpayment,claimproc, patient \n WHERE " + "\n      claimproc.PatNum = Patient.PatNum " + "\n      && claimproc.ClaimPaymentNum = claimpayment.ClaimPaymentNum " + "\n      && (claimproc.Status = 1 OR Claimproc.Status = 4)";
        if (Guarantor != 0)
            rVal += "\n      && Patient.Guarantor = " + Guarantor;
         
        return rVal;
    }

}


//if (IsPulledFlag)
//    rVal += "\n    && claimproc." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//else
//    rVal += "\n    && claimproc." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";