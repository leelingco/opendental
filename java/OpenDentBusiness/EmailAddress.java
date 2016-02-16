//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.TableBase;

/**
* Stores all the connection info for one email address.  Linked to clinic by clinic.EmailAddressNum.  Sends email based on patient's clinic.
*/
public class EmailAddress  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EmailAddressNum = new long();
    /**
    * For example smtp.gmail.com
    */
    public String SMTPserver = new String();
    /**
    * .
    */
    public String EmailUsername = new String();
    /**
    * .
    */
    public String EmailPassword = new String();
    /**
    * Usually 587, sometimes 25 or 465.
    */
    public int ServerPort = new int();
    /**
    * .
    */
    public boolean UseSSL = new boolean();
    /**
    * The email address of the sender as it should appear to the recipient.
    */
    public String SenderAddress = new String();
    /**
    * For example pop.gmail.com
    */
    public String Pop3ServerIncoming = new String();
    /**
    * Usually 110, sometimes 995.
    */
    public int ServerPortIncoming = new int();
    /**
    * 
    */
    public EmailAddress clone() {
        try
        {
            return (EmailAddress)this.MemberwiseClone();
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


