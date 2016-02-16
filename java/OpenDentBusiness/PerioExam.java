//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* One perio exam for one patient on one date.  Has lots of periomeasures attached to it.
*/
public class PerioExam  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PerioExamNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * .
    */
    public DateTime ExamDate = new DateTime();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
}


