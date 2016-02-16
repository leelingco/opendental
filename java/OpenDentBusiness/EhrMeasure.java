//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrMeasure;
import OpenDentBusiness.EhrMeasureType;
import OpenDentBusiness.TableBase;

/**
* For EHR module, automate measure calculation.
*/
public class EhrMeasure  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrMeasureNum = new long();
    /**
    * Enum:EhrMeasureType
    */
    public EhrMeasureType MeasureType = EhrMeasureType.ProblemList;
    /**
    * 0-100, -1 indicates not entered yet.
    */
    public int Numerator = new int();
    /**
    * 0-100, -1 indicates not entered yet.
    */
    public int Denominator = new int();
    /**
    * Not a database column.
    */
    public String Objective = new String();
    /**
    * Not a database column.
    */
    public String Measure = new String();
    /**
    * Not a database column.  More than this percent for meaningful use.
    */
    public int PercentThreshold = new int();
    /**
    * Not a database column.  An explanation of which patients qualify for enumerator.
    */
    public String NumeratorExplain = new String();
    /**
    * Not a database column.  An explanation of which patients qualify for denominator.
    */
    public String DenominatorExplain = new String();
    /**
    * Not a database column.  Used for timing calculation of each measure.
    */
    public TimeSpan ElapsedTime = new TimeSpan();
    /**
    * Not a database column.  An explanation of the conditions which would allow a Provider to be excluded from this requirement.
    */
    public String ExclusionExplain = new String();
    /**
    * Not a database column.  Some exclusions have an associated count that the Provider must report if they are to attest to exclusion from this requirement.  Can be 0.
    */
    public int ExclusionCount = new int();
    /**
    * Not a database column.  A description of what the count is.  Example: If a Provider writes fewer than 100 Rx's during the reporting period, they can be excluded from reporting the ProvOrderEntry - CPOE measure.  The count would be the number of Rx's entered by the Provider during the reporting period and the label would identify the number as such.
    */
    public String ExclusionCountDescript = new String();
    /**
    * 
    */
    public EhrMeasure copy() throws Exception {
        return (EhrMeasure)MemberwiseClone();
    }

}


