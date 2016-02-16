//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrLabSpecimen;
import OpenDentBusiness.EhrLabSpecimenCondition;
import OpenDentBusiness.EhrLabSpecimenConditions;
import OpenDentBusiness.EhrLabSpecimenRejectReason;
import OpenDentBusiness.EhrLabSpecimenRejectReasons;
import OpenDentBusiness.TableBase;

/**
* For EHR module, the specimen upon which the lab orders were/are to be performed on.  NTE.*
*/
public class EhrLabSpecimen  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrLabSpecimenNum = new long();
    /**
    * FK to EhrLab.EhrLabNum.  May be 0.
    */
    public long EhrLabNum = new long();
    /**
    * Enumerates the SPM segments within a single message starting with 1.  SPM.1
    */
    public long SetIdSPM = new long();
    /**
    * SPM.1
    */
    public String SpecimenTypeID = new String();
    /**
    * Description of SpecimenTypeId.  SPM.2
    */
    public String SpecimenTypeText = new String();
    /**
    * CodeSystem that SpecimenTypeId came from.  SPM.3
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String SpecimenTypeCodeSystemName = new String();
    /**
    * SPM.4
    */
    public String SpecimenTypeIDAlt = new String();
    /**
    * Description of SpecimenTypeIdAlt.  SPM.5
    */
    public String SpecimenTypeTextAlt = new String();
    /**
    * CodeSystem that SpecimenTypeId came from.  SPM.6
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String SpecimenTypeCodeSystemNameAlt = new String();
    /**
    * Optional text that describes the original text used to encode the values above.  SPM.7
    */
    public String SpecimenTypeTextOriginal = new String();
    /**
    * Stored as string in the format YYYYMMDD[HH[MM[SS]]] where bracketed values are optional.  When time is not known will be valued "0000".  SPM.17.1.1
    */
    public String CollectionDateTimeStart = new String();
    /**
    * May be empty.  Stored as string in the format YYYYMMDD[HH[MM[SS]]] where bracketed values are optional.  SPM.17.2.1
    */
    public String CollectionDateTimeEnd = new String();
    /**
    * [0..*]This is not a data column but is stored in a seperate table named EhrLabSpecimenRejectReason.  SPM.21
    */
    private List<EhrLabSpecimenRejectReason> _listEhrLabSpecimenRejectReason = new List<EhrLabSpecimenRejectReason>();
    /**
    * [0..*]This is not a data column but is stored in a seperate table named EhrLabSpecimenCondition.  SPM.24
    */
    private List<EhrLabSpecimenCondition> _listEhrLabSpecimenCondition = new List<EhrLabSpecimenCondition>();
    /**
    * 
    */
    public EhrLabSpecimen copy() throws Exception {
        return (EhrLabSpecimen)MemberwiseClone();
    }

    public List<EhrLabSpecimenRejectReason> getListEhrLabSpecimenRejectReason() throws Exception {
        if (_listEhrLabSpecimenRejectReason == null)
        {
            if (EhrLabNum == 0)
            {
                _listEhrLabSpecimenRejectReason = new List<EhrLabSpecimenRejectReason>();
            }
            else
            {
                _listEhrLabSpecimenRejectReason = EhrLabSpecimenRejectReasons.getForEhrLabSpecimen(EhrLabSpecimenNum);
            } 
        }
         
        return _listEhrLabSpecimenRejectReason;
    }

    public void setListEhrLabSpecimenRejectReason(List<EhrLabSpecimenRejectReason> value) throws Exception {
        _listEhrLabSpecimenRejectReason = value;
    }

    /**
    * Only filled with EhrLabNotes when value is used.  To refresh ListEhrLabResults, set it equal to null or explicitly reassign it using EhrLabResults.GetForLab(EhrLabNum).
    */
    public List<EhrLabSpecimenCondition> getListEhrLabSpecimenCondition() throws Exception {
        if (_listEhrLabSpecimenCondition == null)
        {
            if (EhrLabNum == 0)
            {
                _listEhrLabSpecimenCondition = new List<EhrLabSpecimenCondition>();
            }
            else
            {
                _listEhrLabSpecimenCondition = EhrLabSpecimenConditions.getForEhrLabSpecimen(EhrLabSpecimenNum);
            } 
        }
         
        return _listEhrLabSpecimenCondition;
    }

    public void setListEhrLabSpecimenCondition(List<EhrLabSpecimenCondition> value) throws Exception {
        _listEhrLabSpecimenCondition = value;
    }

}


