//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CustRefEntry;
import OpenDentBusiness.TableBase;

/**
* For internal use only.
*/
public class CustRefEntry  extends TableBase 
{
    /**
    * Primary key.
    */
    public long CustRefEntryNum = new long();
    /**
    * FK to patient.PatNum.  The customer seeking a reference.
    */
    public long PatNumCust = new long();
    /**
    * FK to patient.PatNum.  The chosen reference.  This is the customer who was given as a reference to the new customer.
    */
    public long PatNumRef = new long();
    /**
    * Date the reference was chosen.
    */
    public DateTime DateEntry = new DateTime();
    /**
    * Notes specific to this particular reference entry, mostly for a special reference situation.
    */
    public String Note = new String();
    /**
    * 
    */
    public CustRefEntry clone() {
        try
        {
            return (CustRefEntry)this.MemberwiseClone();
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


