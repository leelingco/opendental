//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Account;
import OpenDentBusiness.AccountType;
import OpenDentBusiness.TableBase;

/**
* Used in the accounting section in chart of accounts.  Not related to patient accounts in any way.
*/
public class Account  extends TableBase 
{
    /**
    * Primary key..
    */
    public long AccountNum = new long();
    /**
    * .
    */
    public String Description = new String();
    /**
    * Enum:AccountType Asset, Liability, Equity,Revenue, Expense
    */
    public AccountType AcctType = AccountType.Asset;
    /**
    * For asset accounts, this would be the bank account number for deposit slips.
    */
    public String BankNumber = new String();
    /**
    * Set to true to not normally view this account in the list.
    */
    public boolean Inactive = new boolean();
    /**
    * .
    */
    public Color AccountColor = new Color();
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


