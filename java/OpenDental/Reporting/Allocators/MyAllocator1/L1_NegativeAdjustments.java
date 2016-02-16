//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemTypes;
import OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_ODSQLPullStrings_LedgerItem;

public class L1_NegativeAdjustments  extends PP_ODSQLPullStrings_LedgerItem 
{
    /**
    * Provides the String to select NegativeAdjustments from OD Adjustment Table
    * 
    *  @param Guarantor 
    *  @param IsPulledFlag Which state of flag do you want to pull
    *  @return
    */
    public String oDPullString(long Guarantor, boolean IsPulledFlag) throws Exception {
        // string NegAdjustmentUnion -- From GuarantorLedgerItem
        //2
        // AS 'ItemDate', " --was a problem if not first in union
        // ProcNum not used For this Union
        String rVal = "SELECT " + "\n '" + ((Enum)LedgerItemTypes.NegAdjustment).ordinal() + "', " + "\n      patient.Guarantor, " + "\n      patient.PatNum, " + "\n      adjustment.ProvNum, " + "\n      adjustment.ADjDate, " + "\n      adjustment.AdjAmt, " + "\n       Adjustment.AdjNum," + "\n       " + ((Enum)OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled.Adjustment).ordinal() + "\n FROM adjustment, patient \n WHERE " + "\n      adjustment.PatNum = Patient.PatNum " + "\n      && adjustment.AdjAmt <= 0";
        if (Guarantor != 0)
            rVal += "\n      && Patient.Guarantor = " + Guarantor;
         
        return rVal;
    }

}


//if (IsPulledFlag)
//    rVal += "\n    && adjustment." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//else
//    rVal += "\n    && adjustment." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";