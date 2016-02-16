//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:23 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemTypes;
import OpenDental.Reporting.Allocators.MyAllocator1.ProviderBalance;
import OpenDental.Reporting.Allocators.MyAllocator1.PU;

/**
* Provides a snapshot for a provider's at any point in time.
* Ammounts is a decimal array holding the balances corresponding to LedgerItemTypes
*/
public class ProviderBalance  extends IComparable 
{
    public DateTime Date_of_Balance = DateTime.MinValue;
    /**
    * The for LedgerItemTypes form ammounts.
    */
    public double[] Ammounts = new double[Enum.GetNames(LedgerItemTypes.class).Length];
    public double getBALANCE() throws Exception {
        return Ammounts[((Enum)LedgerItemTypes.Charge).ordinal()] + Ammounts[((Enum)LedgerItemTypes.PosAdjustment).ordinal()] + Ammounts[((Enum)LedgerItemTypes.NegAdjustment).ordinal()] + Ammounts[((Enum)LedgerItemTypes.Payment).ordinal()];
    }

    public int ProvNum = int.MaxValue;
    public ProviderBalance(int Provider, DateTime DateOfBalance) throws Exception {
        ProvNum = Provider;
        Date_of_Balance = DateOfBalance;
    }

    public int compareTo(Object obj) throws Exception {
        ProviderBalance that = (ProviderBalance)obj;
        if (this.Date_of_Balance == that.Date_of_Balance)
            return this.ProvNum.CompareTo(that.ProvNum);
        else
            return this.Date_of_Balance.CompareTo(that.Date_of_Balance); 
    }

    /**
    * Give returned value the date of the left hand operand. Exception if ProvNums are not the same
    * 
    *  @param p1 
    *  @param p2 
    *  @return
    */

}


