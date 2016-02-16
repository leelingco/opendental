//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Clinic;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.TableBase;

/**
* A clinic is usually a separate physical office location.  If multiple clinics are sharing one database, then this is used.  Patients, Operatories, Claims, and many other types of objects can be assigned to a clinic.
*/
public class Clinic  extends TableBase 
{
    /**
    * Primary key.  Used in patient,payment,claimpayment,appointment,procedurelog, etc.
    */
    public long ClinicNum = new long();
    /**
    * .
    */
    public String Description = new String();
    /**
    * .
    */
    public String Address = new String();
    /**
    * Second line of address.
    */
    public String Address2 = new String();
    /**
    * .
    */
    public String City = new String();
    /**
    * 2 char in the US.
    */
    public String State = new String();
    /**
    * .
    */
    public String Zip = new String();
    /**
    * Does not include any punctuation.  Exactly 10 digits or blank in USA and Canada.
    */
    public String Phone = new String();
    /**
    * The account number for deposits.
    */
    public String BankNumber = new String();
    /**
    * Enum:PlaceOfService Usually 0 unless a mobile clinic for instance.
    */
    public PlaceOfService DefaultPlaceService = PlaceOfService.Office;
    /**
    * FK to provider.ProvNum.  0=Default practice provider, -1=Treating provider.
    */
    public long InsBillingProv = new long();
    /**
    * Does not include any punctuation.  Exactly 10 digits or empty in USA and Canada.
    */
    public String Fax = new String();
    /**
    * FK to EmailAddress.EmailAddressNum.
    */
    public long EmailAddressNum = new long();
    /**
    * Returns a copy of this Clinic.
    */
    public Clinic copy() throws Exception {
        return (Clinic)this.MemberwiseClone();
    }

}


