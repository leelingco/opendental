//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Snomed;
import OpenDentBusiness.TableBase;

/**
* We do not import synonyms, only "Fully Specified Name records". Snomed for holding a large list of codes. Codes in use are copied into the DiseaseDef table.  SNOMED CT maintained, owned and copyright International Health Terminology Standards Development Organisation (IHTSDO).
*/
public class Snomed  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SnomedNum = new long();
    /**
    * Used as FK by other tables.  Also called the Concept ID.  Not allowed to edit this column once saved in the database.
    */
    public String SnomedCode = new String();
    /**
    * Also called "Term", "Name", or "Fully Specified Name".  Not editable and doesn't change.
    */
    public String Description = new String();
    //We might add IsActive later.  Adding a column to the text import would be backward compatible.
    /**
    * 
    */
    public Snomed copy() throws Exception {
        return (Snomed)this.MemberwiseClone();
    }

}


