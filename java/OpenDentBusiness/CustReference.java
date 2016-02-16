//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CustReference;
import OpenDentBusiness.TableBase;

/**
* One to one relation with the patient table representing each customer as a reference.
*/
public class CustReference  extends TableBase 
{
    /**
    * Primary key.
    */
    public long CustReferenceNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Most recent date the reference was used, loosely kept updated.
    */
    public DateTime DateMostRecent = new DateTime();
    /**
    * Notes specific to this customer as a reference.
    */
    public String Note = new String();
    /**
    * Set to true if this customer was a bad reference.
    */
    public boolean IsBadRef = new boolean();
    /**
    * 
    */
    public CustReference clone() {
        try
        {
            return (CustReference)this.MemberwiseClone();
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


