//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Account;
import OpenDentBusiness.ApptFieldType;
import OpenDentBusiness.TableBase;

/**
* These are the definitions for the custom patient fields added and managed by the user.
*/
public class ApptFieldDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ApptFieldDefNum = new long();
    /**
    * The name of the field that the user will be allowed to fill in the appt edit window.  Duplicates are prevented.
    */
    public String FieldName = new String();
    /**
    * Enum:ApptFieldType Text=0,PickList=1
    */
    public ApptFieldType FieldType = ApptFieldType.Text;
    /**
    * The text that contains pick list values.  Length 4000.
    */
    public String PickList = new String();
    /**
    * 
    */
    public Account clone() {
        try
        {
            return (Account)this.MemberwiseClone();
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


