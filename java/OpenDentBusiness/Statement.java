//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.Statement;
import OpenDentBusiness.StatementMode;
import OpenDentBusiness.TableBase;

/**
* Represents one statement for one family.  Usually already sent, but could still be waiting to send.
*/
public class Statement  extends TableBase 
{
    /**
    * Primary key.
    */
    public long StatementNum = new long();
    /**
    * FK to patient.PatNum. Typically the guarantor.  Can also be the patient for walkout statements.
    */
    public long PatNum = new long();
    /**
    * This will always be a valid and reasonable date regardless of whether it's actually been sent yet.
    */
    public DateTime DateSent = new DateTime();
    /**
    * Typically 45 days before dateSent
    */
    public DateTime DateRangeFrom = new DateTime();
    /**
    * Any date >= year 2200 is considered max val.  We generally try to automate this value to be the same date as the statement rather than the max val.  This is so that when payment plans are displayed, we can add approximately 10 days to effectively show the charge that will soon be due.  Adding the 10 days is not done until display time.
    */
    public DateTime DateRangeTo = new DateTime();
    /**
    * Can include line breaks.  This ordinary note will be in the standard font.
    */
    public String Note = new String();
    /**
    * More important notes may go here.  Font will be bold.  Color and size of text will be customizable in setup.
    */
    public String NoteBold = new String();
    /**
    * Enum:StatementMode Mail, InPerson, Email, Electronic.
    */
    public StatementMode Mode_ = StatementMode.Mail;
    /**
    * Set true to hide the credit card section, and the please pay box.
    */
    public boolean HidePayment = new boolean();
    /**
    * One patient on statement instead of entire family.
    */
    public boolean SinglePatient = new boolean();
    /**
    * If entire family, then this determines whether they are all intermingled into one big grid, or whether they are all listed in separate grids.
    */
    public boolean Intermingled = new boolean();
    /**
    * True
    */
    public boolean IsSent = new boolean();
    /**
    * FK to document.DocNum when a pdf has been archived.
    */
    public long DocNum = new long();
    /**
    * Date/time last altered.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * The only effect of this flag is to change the text at the top of a statement from "statement" to "receipt".  It might later do more.
    */
    public boolean IsReceipt = new boolean();
    /**
    * This flag is for marking a statement as Invoice.  In this case, it must have procedures and/or adjustments attached.
    */
    public boolean IsInvoice = new boolean();
    /**
    * Only used if IsInvoice=true.  The first printout will not be a copy.  Subsequent printouts will show "copy" on them.
    */
    public boolean IsInvoiceCopy = new boolean();
    /**
    * Empty string by default.  Only used to override BillingEmailSubject pref when emailing statements.  Only set when statements are created from the Billing Options window.  No UI for editing.
    */
    public String EmailSubject = new String();
    /**
    * Empty string by default.  Only used to override BillingEmailBodyText pref when emailing statements.  Only set when statements are created from the Billing Options window.  No UI for editing.  Limit in db: 16M char.
    */
    public String EmailBody = new String();
    public Statement copy() throws Exception {
        return (Statement)this.MemberwiseClone();
    }

}


