//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EhrSummaryCcd;
import OpenDentBusiness.TableBase;

/**
* Can also be a CCR.  Received CCDs/CCRs are stored both here and in emailattach.  Sent CCDs are not saved here, but are only stored in emailattach.  To display a saved Ccd, it is combined with an internal stylesheet.
*/
public class EhrSummaryCcd  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrSummaryCcdNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Date that this Ccd was received.
    */
    public DateTime DateSummary = new DateTime();
    /**
    * The xml content of the received text file.
    */
    public String ContentSummary = new String();
    /**
    * FK to emailattach.EmailAttachNum.  The Direct email attachment where the CCD xml message came from.  Needed to sync PatNum with the email PatNum if the PatNum is changed on the email.
    */
    public long EmailAttachNum = new long();
    /**
    * 
    */
    public EhrSummaryCcd copy() throws Exception {
        return (EhrSummaryCcd)MemberwiseClone();
    }

}


