//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AmendmentSource;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EhrAmendment;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.YN;

/**
* Used in EHR only.  Stores an entry indicating whether the office has accepted or denied the amendment.  Amendments can be verbal or written requests to add information to the patient's record.  The provider can either scan / import the document or create a detailed description that indicates what was verbally requested or where the document can be found.
*/
public class EhrAmendment  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrAmendmentNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * Enum:YN Y=accepted, N=denied, U=requested.
    */
    public YN IsAccepted = YN.Unknown;
    /**
    * Description or user-defined location of the amendment.  Max length 2000.
    */
    public String Description = new String();
    /**
    * Enum:AmendmentSource Patient, Provider, Organization, Other.  Required.
    */
    public AmendmentSource Source = AmendmentSource.Patient;
    /**
    * User-defined name of the amendment source.  For example, a patient name or organization name.  Max length 2000.
    */
    public String SourceName = new String();
    /**
    * The file is stored in the A-Z folder in 'EhrAmendments' folder.  This field stores the name of the file.  The files are named automatically based on Date/time along with EhrAmendmentNum for uniqueness.  This meets the requirement of "appending" to the patient's record.
    */
    public String FileName = new String();
    /**
    * The raw file data encoded as base64.  Only used if there is no AtoZ folder.  This meets the requirement of "appending" to the patient's record.
    */
    public String RawBase64 = new String();
    /**
    * Date and time of the amendment request.
    */
    public DateTime DateTRequest = new DateTime();
    /**
    * Date and time of the amendment acceptance or denial.  If there is a date here, then the IsAccepted will be set.
    */
    public DateTime DateTAcceptDeny = new DateTime();
    /**
    * Date and time of the file being appended to the amendment or a link provided.
    */
    public DateTime DateTAppend = new DateTime();
    /**
    * 
    */
    public EhrAmendment clone() {
        try
        {
            return (EhrAmendment)this.MemberwiseClone();
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


