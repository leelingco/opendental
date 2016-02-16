//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EmailAttach;
import OpenDentBusiness.TableBase;

/**
* Keeps track of one file attached to an email.  Multiple files can be attached to an email using this method.
*/
public class EmailAttach  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EmailAttachNum = new long();
    /**
    * FK to emailmessage.EmailMessageNum
    */
    public long EmailMessageNum = new long();
    /**
    * The name of the file that shows on the email.  For example: tooth2.jpg.
    */
    public String DisplayedFileName = new String();
    /**
    * The actual file is stored in the A-Z folder in EmailAttachments.  This field stores the name of the file.  The files are named automatically based on Date/time along with a random number.  This ensures that they will be sequential as well as unique.
    */
    public String ActualFileName = new String();
    public EmailAttach copy() throws Exception {
        return (EmailAttach)MemberwiseClone();
    }

}


