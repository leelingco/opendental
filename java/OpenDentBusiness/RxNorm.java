//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.RxNorm;
import OpenDentBusiness.TableBase;

/**
* RxNorm created from a zip file.
*/
public class RxNorm  extends TableBase 
{
    /**
    * Primary key.
    */
    public long RxNormNum = new long();
    /**
    * RxNorm Concept universal ID.  Throughout the program, this is actually used as the Primary Key of this table rather than the RxNormNum.
    */
    public String RxCui = new String();
    /**
    * Multum code.  Only used for crosscoding during import/export with electronic Rx program.  User cannot see multum codes.  Most of the rows in this table do not have an MmslCode and user searches ignore rows with an MmslCode.
    */
    public String MmslCode = new String();
    /**
    * Only used for RxNorms, not Multums.
    */
    public String Description = new String();
    /**
    * 
    */
    public RxNorm copy() throws Exception {
        return (RxNorm)this.MemberwiseClone();
    }

}


