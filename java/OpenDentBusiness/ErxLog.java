//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.ErxLog;
import OpenDentBusiness.TableBase;

/**
* 
*/
public class ErxLog  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ErxLogNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Holds up to 16MB.
    */
    public String MsgText = new String();
    /**
    * Automatically updated by MySQL every time a row is added or changed.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * FK to provider.ProvNum. The provider that the prescription request was sent by or on behalf of.
    */
    public long ProvNum = new long();
    /**
    * 
    */
    public ErxLog clone() {
        try
        {
            return (ErxLog)this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


