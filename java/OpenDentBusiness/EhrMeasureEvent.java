//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.TableBase;

/**
* Stores events for EHR that are needed for reporting purposes.
*/
public class EhrMeasureEvent  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrMeasureEventNum = new long();
    /**
    * Date and time of measure event.
    */
    public DateTime DateTEvent = new DateTime();
    /**
    * Enum:EhrMeasureEventType .
    */
    public EhrMeasureEventType EventType = EhrMeasureEventType.EducationProvided;
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * Only used for some types: EducationProvided, TobaccoCessation, TobaccoUseAssessed.
    */
    public String MoreInfo = new String();
    /**
    * The code for this event.  Example: TobaccoUseAssessed can be one of three LOINC codes: 11366-2 History of tobacco use Narrative, 68535-4 Have you used tobacco in the last 30 days, and 68536-2 Have you used smokeless tobacco product in the last 30 days.
    */
    public String CodeValueEvent = new String();
    /**
    * The code system name for the event code.  Examples: LOINC, SNOMEDCT.
    */
    public String CodeSystemEvent = new String();
    /**
    * The code for this event result.  Example: A TobaccoUseAssessed event type could result in a finding of SNOMED code 8517006 - Ex-smoker (finding).  There are 54 allowed tobacco user/non-user codes, and the user is allowed to select from any SNOMED code if they wish, for a TobaccoUseAssessed event.
    */
    public String CodeValueResult = new String();
    /**
    * The code system for this event result.  Example: SNOMEDCT,
    */
    public String CodeSystemResult = new String();
    /**
    * A foreign key to a table associated with the EventType.  0 indicates not in use.  Used to properly count denominators for specific measure types.
    */
    public long FKey = new long();
    /**
    * 
    */
    public EhrMeasureEvent copy() throws Exception {
        return (EhrMeasureEvent)this.MemberwiseClone();
    }

}


