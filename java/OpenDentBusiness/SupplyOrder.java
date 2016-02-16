//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.SupplyOrder;
import OpenDentBusiness.TableBase;

/**
* One supply order to one supplier.  Contains SupplyOrderItems.
*/
public class SupplyOrder  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SupplyOrderNum = new long();
    /**
    * FK to supplier.SupplierNum.
    */
    public long SupplierNum = new long();
    /**
    * A date greater than 2200 (eg 2500), is considered a max date.  A max date is used for an order that was started but has not yet been placed.  This puts it at the end of the list where it belongs, but it will display as blank.  Only one unplaced order is allowed per supplier.
    */
    public DateTime DatePlaced = new DateTime();
    /**
    * .
    */
    public String Note = new String();
    /**
    * The sum of all the amounts of each item on the order.  If any of the item prices are zero, then it won't auto calculate this total.  This will allow the user to manually put in the total without having it get deleted.
    */
    public double AmountTotal = new double();
    public SupplyOrder copy() throws Exception {
        return (SupplyOrder)this.MemberwiseClone();
    }

}


