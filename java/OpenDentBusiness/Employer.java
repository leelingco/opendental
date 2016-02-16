//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Most insurance plans are organized by employer.  This table keeps track of the list of employers.  The address fields were added at one point, but I don't know why they don't show in the program in order to edit.  Nobody has noticed their absence even though it's been a few years, so for now we are just using the EmpName and not the address.
*/
public class Employer  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EmployerNum = new long();
    /**
    * Name of the employer.
    */
    public String EmpName = new String();
    /**
    * .
    */
    public String Address = new String();
    /**
    * Second line of address.
    */
    public String Address2 = new String();
    /**
    * .
    */
    public String City = new String();
    /**
    * 2 char in the US.
    */
    public String State = new String();
    /**
    * .
    */
    public String Zip = new String();
    /**
    * Includes any punctuation.
    */
    public String Phone = new String();
}


