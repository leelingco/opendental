//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* One item on one supply order.  This table links supplies to orders as well as storing a small amount of additional info.
*/
public class SupplyOrderItem  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SupplyOrderItemNum = new long();
    /**
    * FK to supplyorder.supplyOrderNum.
    */
    public long SupplyOrderNum = new long();
    /**
    * FK to supply.SupplyNum.
    */
    public long SupplyNum = new long();
    /**
    * How many were ordered.
    */
    public int Qty = new int();
    /**
    * Price per unit on this order.
    */
    public double Price = new double();
}


