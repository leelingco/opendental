//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemTypes;
import OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_ODSQLPullStrings_LedgerItem;

public class L2_PositiveAdjustments  extends PP_ODSQLPullStrings_LedgerItem 
{
    public String oDPullString(long Guarantor, boolean IsPulledFlag) throws Exception {
        //PosAdjustmentUnion
        //2
        //
        String rVal = "SELECT " + "\n '" + ((Enum)LedgerItemTypes.PosAdjustment).ordinal() + "', " + "\n      patient.Guarantor, " + "\n      patient.PatNum, " + "\n      adjustment.ProvNum, " + "\n      adjustment.ADjDate, " + "\n      adjustment.AdjAmt, " + "\n       adjustment.AdjNum," + "\n       " + ((Enum)OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled.Adjustment).ordinal() + "\n FROM adjustment, patient \n WHERE " + "\n      adjustment.PatNum = Patient.PatNum " + "\n      && adjustment.AdjAmt > 0";
        if (Guarantor != 0)
            rVal += "\n      && Patient.Guarantor = " + Guarantor;
         
        return rVal;
    }

}


//if (IsPulledFlag)
//    rVal += "\n    && adjustment." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//else
//    rVal += "\n    && adjustment." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";