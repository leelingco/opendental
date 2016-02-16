//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientGrade;
import OpenDentBusiness.PatientRaceOld;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.TreatmentUrgency;
import OpenDentBusiness.YN;

/**
* Used in public health.  This screening table is meant to be general purpose.  It is compliant with the popular Basic Screening Survey.  It is also designed with minimal foreign keys and can be easily adapted to a tablet PC.  This table can be used with only the screengroup table, but is more efficient if provider, school, and county tables are also available.
*/
public class Screen  extends TableBase 
{
    /**
    * Primary key
    */
    public long ScreenNum = new long();
    /**
    * The date of the screening.
    */
    public DateTime ScreenDate = new DateTime();
    /**
    * FK to site.Description, although it will not crash if key absent.
    */
    public String GradeSchool = new String();
    /**
    * FK to county.CountyName, although it will not crash if key absent.
    */
    public String County = new String();
    /**
    * Enum:PlaceOfService
    */
    public PlaceOfService PlaceService = PlaceOfService.Office;
    /**
    * FK to provider.ProvNum.  ProvNAME is always entered, but ProvNum supplements it by letting user select from list.  When entering a provNum, the name will be filled in automatically. Can be 0 if the provider is not in the list, but provName is required.
    */
    public long ProvNum = new long();
    /**
    * Required.
    */
    public String ProvName = new String();
    /**
    * Enum:PatientGender
    */
    public PatientGender Gender = PatientGender.Male;
    /**
    * Enum:PatientRaceOld and ethnicity.
    */
    public PatientRaceOld Race = PatientRaceOld.Unknown;
    /**
    * Enum:PatientGrade
    */
    public PatientGrade GradeLevel = PatientGrade.Unknown;
    /**
    * Age of patient at the time the screening was done. Faster than recording birthdates.
    */
    public byte Age = new byte();
    /**
    * Enum:TreatmentUrgency
    */
    public TreatmentUrgency Urgency = TreatmentUrgency.Unknown;
    /**
    * Enum:YN Set to true if patient has cavities.
    */
    public YN HasCaries = YN.Unknown;
    /**
    * Enum:YN Set to true if patient needs sealants.
    */
    public YN NeedsSealants = YN.Unknown;
    /**
    * Enum:YN
    */
    public YN CariesExperience = YN.Unknown;
    /**
    * Enum:YN
    */
    public YN EarlyChildCaries = YN.Unknown;
    /**
    * Enum:YN
    */
    public YN ExistingSealants = YN.Unknown;
    /**
    * Enum:YN
    */
    public YN MissingAllTeeth = YN.Unknown;
    /**
    * Optional
    */
    public DateTime Birthdate = new DateTime();
    /**
    * FK to screengroup.ScreenGroupNum.
    */
    public long ScreenGroupNum = new long();
    /**
    * The order of this item within its group.
    */
    public int ScreenGroupOrder = new int();
    /**
    * .
    */
    public String Comments = new String();
}


