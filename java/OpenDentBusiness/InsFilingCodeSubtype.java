//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.InsFilingCodeSubtype;
import OpenDentBusiness.TableBase;

/**
* Stores the list of insurance filing code subtypes.
*/
public class InsFilingCodeSubtype  extends TableBase 
{
    /**
    * Primary key.
    */
    public long InsFilingCodeSubtypeNum = new long();
    /**
    * FK to insfilingcode.insfilingcodenum
    */
    public long InsFilingCodeNum = new long();
    /**
    * The description of the insurance filing code subtype.
    */
    public String Descript = new String();
    public InsFilingCodeSubtype clone() {
        try
        {
            return (InsFilingCodeSubtype)this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


