//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AccountingAutoPay;
import OpenDentBusiness.TableBase;

/**
* In the accounting section, this automates entries into the database when user enters a payment into a patient account.  This table presents the user with a picklist specific to that payment type.  For example, a cash payment would create a picklist of cashboxes for user to put the cash into.
*/
public class AccountingAutoPay  extends TableBase 
{
    /**
    * Primary key.
    */
    public long AccountingAutoPayNum = new long();
    /**
    * FK to definition.DefNum.
    */
    public long PayType = new long();
    /**
    * FK to account.AccountNum.  AccountNums separated by commas.  No spaces.
    */
    public String PickList = new String();
    /**
    * Returns a copy of this AccountingAutoPay.
    */
    public AccountingAutoPay clone() {
        try
        {
            return (AccountingAutoPay)this.MemberwiseClone();
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


