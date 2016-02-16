//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package OpenDental;


/**
* Used in public health.
*/
public class School   
{
    public School() {
    }

    /**
    * Primary key, but allowed to change.  Change is programmatically synchronized so that keys stay intact.
    */
    public String SchoolName = new String();
    /**
    * Optional. Usage varies.
    */
    public String SchoolCode = new String();
    /**
    * Not a database field. This is the unaltered SchoolName. Used for Update.
    */
    public String OldSchoolName = new String();
}


