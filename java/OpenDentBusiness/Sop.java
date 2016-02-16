//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Sop;
import OpenDentBusiness.TableBase;

/**
* Source of Payment Typology.  Used by EHR.  Examples: Medicaid, MedicaidPPO, NoFee, etc.  About 100 defined by govt.  Other tables generally use the SopCode as their foreign key.
*/
public class Sop  extends TableBase 
{
    /**
    * Primary key. .
    */
    public long SopNum = new long();
    /**
    * Sop code. Not allowed to edit this column once saved in the database.  Examples: 121, 3115, etc.
    */
    public String SopCode = new String();
    /**
    * Description provided by Sop documentation.  Examples: Medicare FFS, TRICARE Reserve Select
    */
    public String Description = new String();
    /**
    * 
    */
    public Sop copy() throws Exception {
        return (Sop)this.MemberwiseClone();
    }

}


