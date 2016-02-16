//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Supply;
import OpenDentBusiness.TableBase;

/**
* A dental supply or office supply item.
*/
public class Supply  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SupplyNum = new long();
    /**
    * FK to supplier.SupplierNum
    */
    public long SupplierNum = new long();
    /**
    * The catalog item number that the supplier uses to identify the supply.
    */
    public String CatalogNumber = new String();
    /**
    * The description can be similar to the catalog, but not required.  Typically includes qty per box/case, etc.
    */
    public String Descript = new String();
    /**
    * FK to definition.DefNum.  User can define their own categories for supplies.
    */
    public long Category = new long();
    /**
    * The zero-based order of this supply within it's category.
    */
    public int ItemOrder = new int();
    /**
    * The level that a fresh order should bring item back up to.  Can include fractions.  If this is 0, then it will be displayed as having this field blank rather than showing 0.  This simply gives a cleaner look.
    */
    public float LevelDesired = new float();
    /**
    * If hidden, then this supply item won't normally show in the main list.
    */
    public boolean IsHidden = new boolean();
    /**
    * The price per unit that the supplier charges for this supply.  If this is 0.00, then no price will be displayed.
    */
    public double Price = new double();
    /**
    * 
    */
    public Supply copy() throws Exception {
        return (Supply)this.MemberwiseClone();
    }

}


