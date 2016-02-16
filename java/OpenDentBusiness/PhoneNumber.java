//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:59 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Only used internally by OpenDental, Inc.  Not used by anyone else.
*/
//It actually is present, but the s classs is in a weird place.
public class PhoneNumber  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PhoneNumberNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * The actual phone number for the patient.  Includes any punctuation.  No leading 1 or plus, so almost always 10 digits.
    */
    public String PhoneNumberVal = new String();
}


