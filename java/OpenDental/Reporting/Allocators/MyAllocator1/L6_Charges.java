//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemTypes;
import OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_ODSQLPullStrings_LedgerItem;

public class L6_Charges  extends PP_ODSQLPullStrings_LedgerItem 
{
    public String oDPullString(long Guarantor, boolean IsPulledFlag) throws Exception {
        //ChargesCommandUnion
        //
        String rVal = "SELECT " + "\n    " + ((Enum)LedgerItemTypes.Charge).ordinal() + "," + "\n    Patient.Guarantor," + "\n    Patient.PatNum," + "\n    Procedurelog.ProvNum,  " + "\n    Procedurelog.ProcDate ,  " + "\n    Procedurelog.ProcFee, " + "\n    Procedurelog.ProcNum ," + "\n       " + ((Enum)OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled.ProcedureLog).ordinal() + "\nFROM " + "\n     Procedurelog, Patient" + "\nWHERE " + "\n     Patient.PatNum = Procedurelog.Patnum " + "\n     && Procedurelog.ProcStatus = " + (((Enum)OpenDentBusiness.ProcStat.C).ordinal()).ToString();
            ;
        if (Guarantor != 0)
            rVal += "\n      && Patient.Guarantor = " + Guarantor;
         
        return rVal;
    }

}


//if (IsPulledFlag)
//    rVal += "\n    && Procedurelog." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//else
//    rVal += "\n    && Procedurelog." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";