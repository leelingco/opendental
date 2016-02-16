//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PayorType;
import OpenDentBusiness.TableBase;

/**
* Used to identify the source of payment for a given patient at a given point in time.  As insurance is added and removed, rows should be either automatically inserted into this table, or the user should be prompted to specify what the new payor type is.  The DateStart of one payor type is interpreted as the end date of the previous payor type.  Example: Patient with no insurance may have payortype.SopCode=81 ("SelfPay").  Patient then adds Medicaid insurance and gets a second new PayorType entry with SopCode=2 (Medicaid).
*/
public class PayorType  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PayorTypeNum = new long();
    /**
    * FK to Patient.
    */
    public long PatNum = new long();
    /**
    * Date of the beginning of new payor type.  End date is the DateStart of the next payor type entry.
    */
    public DateTime DateStart = new DateTime();
    /**
    * FK to sop.SopCode. Examples: 121, 3115, etc.
    */
    public String SopCode = new String();
    /**
    * Max length 2000.
    */
    public String Note = new String();
    /**
    * Returns a copy of this PayorType.
    */
    public PayorType clone() {
        try
        {
            return (PayorType)this.MemberwiseClone();
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


