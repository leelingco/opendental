//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrLabClinicalInfo;
import OpenDentBusiness.TableBase;

/**
* For EHR module, lab request that contains all required fields for HL7 Lab Reporting Interface (LRI).  OBR.13.*
*/
public class EhrLabClinicalInfo  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrLabClinicalInfoNum = new long();
    /**
    * FK to EhrLab.EhrLabNum.
    */
    public long EhrLabNum = new long();
    /**
    * OBR.13.*.1
    */
    public String ClinicalInfoID = new String();
    /**
    * Description of ClinicalInfoId.  OBR.13.*.2
    */
    public String ClinicalInfoText = new String();
    /**
    * CodeSystem that ClinicalInfoId came from.  OBR.13.*.3
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String ClinicalInfoCodeSystemName = new String();
    /**
    * OBR.13.*.4
    */
    public String ClinicalInfoIDAlt = new String();
    /**
    * Description of ClinicalInfoIdAlt.  OBR.13.*.5
    */
    public String ClinicalInfoTextAlt = new String();
    /**
    * CodeSystem that ClinicalInfoId came from.  OBR.13.*.6
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String ClinicalInfoCodeSystemNameAlt = new String();
    /**
    * Optional text that describes the original text used to encode the values above.  OBR.13.*.7
    */
    public String ClinicalInfoTextOriginal = new String();
    /**
    * 
    */
    public EhrLabClinicalInfo copy() throws Exception {
        return (EhrLabClinicalInfo)MemberwiseClone();
    }

}


