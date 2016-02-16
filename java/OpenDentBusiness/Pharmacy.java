//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.Pharmacy;
import OpenDentBusiness.TableBase;

/**
* An individual pharmacy store.
*/
public class Pharmacy  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PharmacyNum = new long();
    /**
    * NCPDPID assigned by NCPDP.  Not used yet.
    */
    public String PharmID = new String();
    /**
    * For now, it can just be a common description.  Later, it might have to be an official designation.
    */
    public String StoreName = new String();
    /**
    * Includes all punctuation.
    */
    public String Phone = new String();
    /**
    * Includes all punctuation.
    */
    public String Fax = new String();
    /**
    * .
    */
    public String Address = new String();
    /**
    * Optional.
    */
    public String Address2 = new String();
    /**
    * .
    */
    public String City = new String();
    /**
    * Two char, uppercase.
    */
    public String State = new String();
    /**
    * .
    */
    public String Zip = new String();
    /**
    * A freeform note for any info that is needed about the pharmacy, such as hours.
    */
    public String Note = new String();
    /**
    * The last date and time this row was altered.  Not user editable.
    */
    public DateTime DateTStamp = new DateTime();
    public Pharmacy copy() throws Exception {
        return (Pharmacy)this.MemberwiseClone();
    }

}


