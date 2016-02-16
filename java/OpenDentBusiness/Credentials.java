//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;


/**
* The username and password are internal to OD.  They are not the MySQL username and password.
*/
public class Credentials   
{
    public String Username = new String();
    /**
    * If using Ecw, then the password is actually just a hash because we don't know the real password.
    */
    public String Password = new String();
}


