//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Hcpcs;
import OpenDentBusiness.TableBase;

/**
* A code system used in EHR.  Healhtcare Common Procedure Coding System.  Another system used to describe procedure codes.
*/
public class Hcpcs  extends TableBase 
{
    /**
    * Primary key..
    */
    public long HcpcsNum = new long();
    /**
    * Examples: AQ, J1040
    */
    public String HcpcsCode = new String();
    /**
    * Short description.  This is the HCPCS supplied abbreviated description.
    */
    public String DescriptionShort = new String();
    /**
    * 
    */
    public Hcpcs clone() {
        try
        {
            return (Hcpcs)this.MemberwiseClone();
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


