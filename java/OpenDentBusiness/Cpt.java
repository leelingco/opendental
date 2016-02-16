//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cpt;
import OpenDentBusiness.TableBase;

/**
* Other tables generally use the CptCode as their foreign key.
*/
public class Cpt  extends TableBase 
{
    /**
    * Primary key. .
    */
    public long CptNum = new long();
    /**
    * Cvx code. Not allowed to edit this column once saved in the database.
    */
    public String CptCode = new String();
    /**
    * Short Description provided by Cvx documentation.
    */
    public String Description = new String();
    /**
    * 
    */
    public Cpt copy() throws Exception {
        return (Cpt)this.MemberwiseClone();
    }

}


