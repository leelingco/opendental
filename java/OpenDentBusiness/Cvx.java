//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cvx;
import OpenDentBusiness.TableBase;

/**
* Vaccines administered.  Other tables generally use the CvxCode as their foreign key.
*/
public class Cvx  extends TableBase 
{
    /**
    * Primary key. .
    */
    public long CvxNum = new long();
    /**
    * Cvx code. Not allowed to edit this column once saved in the database.
    */
    public String CvxCode = new String();
    /**
    * Short Description provided by Cvx documentation.
    */
    public String Description = new String();
    /**
    * 1 if the code is an active code, 0 if the code is inactive.
    */
    public String IsActive = new String();
    //might not need this column.
    /**
    * 
    */
    public Cvx copy() throws Exception {
        return (Cvx)this.MemberwiseClone();
    }

}


