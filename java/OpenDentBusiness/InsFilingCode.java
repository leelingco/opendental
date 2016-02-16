//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.InsFilingCode;
import OpenDentBusiness.TableBase;

/**
* An optional field on insplan and claims.  This lets user customize so that they can track insurance types.
*/
public class InsFilingCode  extends TableBase 
{
    /**
    * Primary key.
    */
    public long InsFilingCodeNum = new long();
    /**
    * Description of the insurance filing code.
    */
    public String Descript = new String();
    /**
    * Code for electronic claim.
    */
    public String EclaimCode = new String();
    /**
    * Display order for this filing code within the UI.  0-indexed.
    */
    public int ItemOrder = new int();
    public InsFilingCode clone() {
        try
        {
            return (InsFilingCode)this.MemberwiseClone();
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


