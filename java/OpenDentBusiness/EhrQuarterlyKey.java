//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrQuarterlyKey;
import OpenDentBusiness.TableBase;

/**
* Also used by OD customer support to store and track Ehr Quarterly Keys for customers.
*/
public class EhrQuarterlyKey  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrQuarterlyKeyNum = new long();
    /**
    * Example 11
    */
    public int YearValue = new int();
    /**
    * Example 2
    */
    public int QuarterValue = new int();
    /**
    * The customer must have this exact practice name entered in practice setup.
    */
    public String PracticeName = new String();
    /**
    * The calculated key value, tied to year, quarter, and practice name.
    */
    public String KeyValue = new String();
    /**
    * Always zero for customer databases.  When used by OD customer support, this is the customer num.
    */
    public long PatNum = new long();
    /**
    * Any notes that the tech wishes to include regarding this situation.
    */
    public String Notes = new String();
    /**
    * 
    */
    public EhrQuarterlyKey copy() throws Exception {
        return (EhrQuarterlyKey)MemberwiseClone();
    }

}


