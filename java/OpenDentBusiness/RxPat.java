//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.RxPat;
import OpenDentBusiness.RxSendStatus;
import OpenDentBusiness.TableBase;

/**
* One Rx for one patient. Copied from rxdef rather than linked to it.
*/
public class RxPat  extends TableBase 
{
    /**
    * Primary key.
    */
    public long RxNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Date of Rx.
    */
    public DateTime RxDate = new DateTime();
    /**
    * Drug name. Example: PenVK 500 mg capsules. Example: Percocet 5/500 tablets.
    */
    public String Drug = new String();
    /**
    * Directions. Example: Take 2 tablets qid. (qid means 4 times a day)
    */
    public String Sig = new String();
    /**
    * Amount to dispense. Example: 12 (twelve)
    */
    public String Disp = new String();
    /**
    * Number of refills. Example: 3.  Example: 1 per month.
    */
    public String Refills = new String();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * Notes specific to this Rx.  Will not show on the printout.  For staff use only.
    */
    public String Notes = new String();
    /**
    * FK to pharmacy.PharmacyNum.
    */
    public long PharmacyNum = new long();
    /**
    * Is a controlled substance.  This will affect the way it prints.
    */
    public boolean IsControlled = new boolean();
    /**
    * The last date and time this row was altered.  Not user editable.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * Enum:RxSendStatus
    */
    public RxSendStatus SendStatus = RxSendStatus.Unsent;
    /**
    * Deprecated.  RxNorm Code identifier.  Was used in FormRxSend for EHR 2011, but FormRxSend has been deleted.  No longer in use anywhere.  Still exists in db for now.
    */
    public long RxCui = new long();
    /**
    * NCI Pharmaceutical Dosage Form code.  Only used with ehr.  For example, C48542 is the code for “Tablet dosing unit”.  User enters code manually, and it's only used for Rx Send, which will be deprecated with 2014 cert.  Guaranteed that nobody actually uses or cares about this field.
    */
    public String DosageCode = new String();
    /**
    * NewCrop returns this unique identifier to use for electronic Rx.
    */
    public String NewCropGuid = new String();
    /**
    * 
    */
    public RxPat copy() throws Exception {
        return (RxPat)this.MemberwiseClone();
    }

}


