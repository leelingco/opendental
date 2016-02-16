//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Icd10;
import OpenDentBusiness.TableBase;

/**
* Other tables generally use the ICD10Code string as their foreign key.  It is implied that these are all ICD10CMs, although that may not be the case in the future.
*/
public class Icd10  extends TableBase 
{
    /**
    * Primary key. Also identical to "Order Number" column in ICD10 documentation.
    */
    public long Icd10Num = new long();
    /**
    * ICD-10-CM or ICD-10-PCS code. Dots are included. Not allowed to edit this column once saved in the database.
    */
    public String Icd10Code = new String();
    /**
    * Short Description provided by ICD10 documentation.
    */
    public String Description = new String();
    /**
    * 0 if the code is a “header” – not valid for submission on a UB04. 1 if the code is valid for submission on a UB04.
    */
    public String IsCode = new String();
    /**
    * 
    */
    public Icd10 copy() throws Exception {
        return (Icd10)this.MemberwiseClone();
    }

}


