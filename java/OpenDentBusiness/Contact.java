//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Like a rolodex for businesses that the office interacts with.  Used to store pharmacies, etc.
*/
public class Contact  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ContactNum = new long();
    /**
    * Last name or, frequently, the entire name.
    */
    public String LName = new String();
    /**
    * First name is optional.
    */
    public String FName = new String();
    /**
    * Work phone.
    */
    public String WkPhone = new String();
    /**
    * Fax number.
    */
    public String Fax = new String();
    /**
    * FK to definition.DefNum
    */
    public long Category = new long();
    /**
    * Note for this contact.
    */
    public String Notes = new String();
}


