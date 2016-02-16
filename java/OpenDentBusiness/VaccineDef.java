//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.VaccineDef;

/**
* A vaccine definition.  Should not be altered once linked to VaccinePat.
*/
public class VaccineDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long VaccineDefNum = new long();
    /**
    * RXA-5-1.
    */
    public String CVXCode = new String();
    /**
    * Name of vaccine.  RXA-5-2.
    */
    public String VaccineName = new String();
    /**
    * FK to drugmanufacturer.DrugManufacturerNum.
    */
    public long DrugManufacturerNum = new long();
    /**
    * 
    */
    public VaccineDef copy() throws Exception {
        return (VaccineDef)this.MemberwiseClone();
    }

}


