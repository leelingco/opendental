//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.HL7MessageStatus;
import OpenDentBusiness.HL7Msg;
import OpenDentBusiness.TableBase;

/**
* HL7 messages sent and received.
*/
public class HL7Msg  extends TableBase 
{
    /**
    * Primary key.
    */
    public long HL7MsgNum = new long();
    /**
    * Enum:HL7MessageStatus Out/In are relative to Open Dental.  This is in contrast to the names of the old ecw folders, which were relative to the other program.  OutPending, OutSent, InReceived, InProcessed.
    */
    public HL7MessageStatus HL7Status = HL7MessageStatus.OutPending;
    /**
    * The actual HL7 message in its entirity.
    */
    public String MsgText = new String();
    /**
    * FK to appointment.AptNum.  Many of the messages contain "Visit ID" which is equivalent to our AptNum.
    */
    public long AptNum = new long();
    /**
    * Used to determine which messages are old so that they can be cleaned up.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * Patient number.
    */
    public long PatNum = new long();
    /**
    * Maximum size 2000 characters.
    */
    public String Note = new String();
    public HL7Msg copy() throws Exception {
        return (HL7Msg)this.MemberwiseClone();
    }

}


