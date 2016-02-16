//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemTypes;
import OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_LedgerItem;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_PaySplitItem;

//public int CompareTo(object obj)
// end class PP_LedgerItem
public class PP_PaymentItem  extends PP_LedgerItem 
{
    private List<PP_PaySplitItem> Splits = new List<PP_PaySplitItem>();
    public PP_PaymentItem(LedgerItemTypes cLedgerItemType, uint cGuarantor, uint cPatNum, int cProvNum, uint cProcNum, DateTime cDate, double cItemAmt, ulong cLedgerItemNumber, OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled cSourceTable) throws Exception {
        super(cLedgerItemType, cGuarantor, cPatNum, cProvNum, cProcNum, cDate, cItemAmt, cLedgerItemNumber, cSourceTable);
    }

    public void add(PP_PaySplitItem splitItem) throws Exception {
        Splits.Add(splitItem);
    }

    public void remove(PP_PaySplitItem splitItem) throws Exception {
        Splits.Remove(splitItem);
    }

    public List<PP_PaySplitItem> getPAYMENT_SPLITS() throws Exception {
        return this.Splits;
    }

}


