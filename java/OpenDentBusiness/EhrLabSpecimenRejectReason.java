//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrLabSpecimenRejectReason;
import OpenDentBusiness.TableBase;

/**
* For EHR module, the specimen upon which the lab orders were/are to be performed on.  (May Repeat) SPM.21
*/
public class EhrLabSpecimenRejectReason  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrLabSpecimenRejectReasonNum = new long();
    /**
    * FK to EhrLab.EhrLabNum.  May be 0.
    */
    public long EhrLabSpecimenNum = new long();
    /**
    * SPM.21.1
    */
    public String SpecimenRejectReasonID = new String();
    /**
    * Description of SpecimenRejectReasonId.   SPM.21.2
    */
    public String SpecimenRejectReasonText = new String();
    /**
    * CodeSystem that SpecimenRejectReasonId came from.   SPM.21.3
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String SpecimenRejectReasonCodeSystemName = new String();
    /**
    * SPM.21.4
    */
    public String SpecimenRejectReasonIDAlt = new String();
    /**
    * Description of SpecimenRejectReasonIdAlt.   SPM.21.5
    */
    public String SpecimenRejectReasonTextAlt = new String();
    /**
    * CodeSystem that SpecimenRejectReasonId came from.   SPM.21.6
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String SpecimenRejectReasonCodeSystemNameAlt = new String();
    /**
    * Optional text that describes the original text used to encode the values above.   SPM.21.7
    */
    public String SpecimenRejectReasonTextOriginal = new String();
    /**
    * 
    */
    public EhrLabSpecimenRejectReason copy() throws Exception {
        return (EhrLabSpecimenRejectReason)MemberwiseClone();
    }

}


