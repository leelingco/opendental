//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DrugManufacturer;
import OpenDentBusiness.TableBase;

/**
* Manufacturer of a vaccine.
*/
public class DrugManufacturer  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DrugManufacturerNum = new long();
    /**
    * .
    */
    public String ManufacturerName = new String();
    /**
    * An abbreviation of the manufacturer name.
    */
    public String ManufacturerCode = new String();
    //VARCHAR(20)/VARCHAR2(20).
    /**
    * 
    */
    public DrugManufacturer copy() throws Exception {
        return (DrugManufacturer)this.MemberwiseClone();
    }

}


