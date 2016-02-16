//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrLabSpecimenCondition;
import OpenDentBusiness.TableBase;

/**
* For EHR module, the specimen upon which the lab orders were/are to be performed on.  SPM.24
*/
public class EhrLabSpecimenCondition  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrLabSpecimenConditionNum = new long();
    /**
    * FK to EhrLabSpecimen.EhrLabSpecimenNum.
    */
    public long EhrLabSpecimenNum = new long();
    /**
    * SPM.24.1
    */
    public String SpecimenConditionID = new String();
    /**
    * Description of SpecimenConditionId.  SPM.24.2
    */
    public String SpecimenConditionText = new String();
    /**
    * CodeSystem that SpecimenConditionId came from.  SPM.24.3
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String SpecimenConditionCodeSystemName = new String();
    /**
    * SPM.24.4
    */
    public String SpecimenConditionIDAlt = new String();
    /**
    * Description of SpecimenConditionIdAlt.  SPM.24.5
    */
    public String SpecimenConditionTextAlt = new String();
    /**
    * CodeSystem that SpecimenConditionId came from.  SPM.24.6
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String SpecimenConditionCodeSystemNameAlt = new String();
    /**
    * Optional text that describes the original text used to encode the values above.  SPM.24.7
    */
    public String SpecimenConditionTextOriginal = new String();
    /**
    * 
    */
    public EhrLabSpecimenCondition copy() throws Exception {
        return (EhrLabSpecimenCondition)MemberwiseClone();
    }

}


