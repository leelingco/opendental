//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.CommSentOrReceived;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* Tracks all forms of communications with patients, including emails, phonecalls, postcards, etc.
*/
public class Commlog  extends TableBase 
{
    /**
    * Primary key.
    */
    public long CommlogNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Date and time of entry
    */
    public DateTime CommDateTime = new DateTime();
    /**
    * FK to definition.DefNum. This will be 0 if IsStatementSent.  Used to be an enumeration in previous versions.
    */
    public long CommType = new long();
    /**
    * Note for this commlog entry.
    */
    public String Note = new String();
    /**
    * Enum:CommItemMode Phone, email, etc.
    */
    public CommItemMode Mode_ = CommItemMode.None;
    /**
    * Enum:CommSentOrReceived Neither=0,Sent=1,Received=2.
    */
    public CommSentOrReceived SentOrReceived = CommSentOrReceived.Neither;
    /**
    * //No longer used.  Use the statement table instead.
    */
    //public bool IsStatementSent;
    /**
    * FK to userod.UserNum.
    */
    public long UserNum = new long();
    /**
    * Signature.  For details, see procedurelog.Signature.
    */
    public String Signature = new String();
    /**
    * True if signed using the Topaz signature pad, false otherwise.
    */
    public boolean SigIsTopaz = new boolean();
    /**
    * Automatically updated by MySQL every time a row is added or changed.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * Date and time when commlog ended.  Mainly for internal use.
    */
    public DateTime DateTimeEnd = new DateTime();
    /**
    * 
    */
    public Commlog copy() throws Exception {
        return (Commlog)this.MemberwiseClone();
    }

}


