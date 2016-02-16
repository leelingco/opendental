//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DentalSpecialty;
import OpenDentBusiness.Referral;
import OpenDentBusiness.TableBase;

/**
* All info about a referral is stored with that referral even if a patient.  That way, it's available for easy queries.
*/
public class Referral  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ReferralNum = new long();
    /**
    * Last name.
    */
    public String LName = new String();
    /**
    * First name.
    */
    public String FName = new String();
    /**
    * Middle name or initial.
    */
    public String MName = new String();
    /**
    * SSN or TIN, no punctuation.  For Canada, this holds the referring provider CDA num for claims.
    */
    public String SSN = new String();
    /**
    * Specificies if SSN is real SSN.
    */
    public boolean UsingTIN = new boolean();
    /**
    * Enum:DentalSpecialty -1 is allowed to indicate no specialty.  -1 is what all non-professionals will be set to.
    */
    public DentalSpecialty Specialty = DentalSpecialty.General;
    /**
    * State
    */
    public String ST = new String();
    /**
    * Primary phone, restrictive, must only be 10 digits and only numbers.
    */
    public String Telephone = new String();
    /**
    * .
    */
    public String Address = new String();
    /**
    * .
    */
    public String Address2 = new String();
    /**
    * .
    */
    public String City = new String();
    /**
    * .
    */
    public String Zip = new String();
    /**
    * Holds important info about the referral.
    */
    public String Note = new String();
    /**
    * Additional phone no restrictions
    */
    public String Phone2 = new String();
    /**
    * Can't delete a referral, but can hide if not needed any more.
    */
    public boolean IsHidden = new boolean();
    /**
    * Set to true for referralls such as Yellow Pages.
    */
    public boolean NotPerson = new boolean();
    /**
    * i.e. DMD or DDS
    */
    public String Title = new String();
    /**
    * .
    */
    public String EMail = new String();
    /**
    * FK to patient.PatNum for referrals that are patients.
    */
    public long PatNum = new long();
    /**
    * NPI for the referral
    */
    public String NationalProvID = new String();
    /**
    * FK to sheetdef.SheetDefNum.  Referral slips can be set for individual referral sources.  If zero, then the default internal referral slip will be used instead of a custom referral slip.
    */
    public long Slip = new long();
    /**
    * True if another dentist or physician.  Cannot be a patient.
    */
    public boolean IsDoctor = new boolean();
    /**
    * Returns a copy of this Referral.
    */
    public Referral copy() throws Exception {
        return (Referral)this.MemberwiseClone();
    }

    /**
    * Includes title, such as DMD.
    */
    public String getNameFL() throws Exception {
        String retVal = "";
        if (!StringSupport.equals(FName, ""))
        {
            retVal += FName + " ";
        }
         
        if (!StringSupport.equals(MName, ""))
        {
            retVal += MName + " ";
        }
         
        retVal += LName;
        if (!StringSupport.equals(Title, ""))
        {
            retVal += ", " + Title;
        }
         
        return retVal;
    }

}


