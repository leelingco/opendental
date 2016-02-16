//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;


public enum LedgerItemTypes
{
    /**
    * A charge item in the ledger = 0
    */
    Charge,
    /**
    * A payment item in the ledger  = 1
    */
    Payment,
    /**
    * A Negative Adjustment item in the ledger = 2
    */
    NegAdjustment,
    /**
    * A Positive Adjustment item in the ledger = 3.  ie a Refund
    * for an overpayment will have a positive adjustment to it.
    */
    PosAdjustment
}

