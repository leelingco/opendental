//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.Pharmacym;
import OpenDentBusiness.TableBase;

public class Pharmacym  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long PharmacyNum = new long();
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
    * 
    */
    public String Address = new String();
    /**
    * Optional.
    */
    public String Address2 = new String();
    /**
    * 
    */
    public String City = new String();
    /**
    * Two char, uppercase.
    */
    public String State = new String();
    /**
    * 
    */
    public String Zip = new String();
    /**
    * A freeform note for any info that is needed about the pharmacy, such as hours.
    */
    public String Note = new String();
    public Pharmacym copy() throws Exception {
        return (Pharmacym)this.MemberwiseClone();
    }

}


