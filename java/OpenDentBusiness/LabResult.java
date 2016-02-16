//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.LabAbnormalFlag;
import OpenDentBusiness.LabResult;
import OpenDentBusiness.TableBase;

/**
* Medical labs, not dental labs.  Multiple labresults are attached to a labpanel.  Loosely corresponds to the OBX segment in HL7.
*/
public class LabResult  extends TableBase 
{
    /**
    * Primary key.
    */
    public long LabResultNum = new long();
    /**
    * FK to labpanel.LabPanelNum.
    */
    public long LabPanelNum = new long();
    /**
    * OBX-14.
    */
    public DateTime DateTimeTest = new DateTime();
    /**
    * OBX-3-1, text portion.
    */
    public String TestName = new String();
    /**
    * To be used for synch with web server.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * OBX-3-0, id portion, LOINC.  For example, 10676-5.
    */
    public String TestID = new String();
    /**
    * OBX-5. Value always stored as a string because the type might vary in the future.
    */
    public String ObsValue = new String();
    /**
    * OBX-6  For example, mL.  Was FK to drugunit.DrugUnitNum, but that would make reliable import problematic, so now it's just text.
    */
    public String ObsUnits = new String();
    /**
    * OBX-7  For example, <200 or >=40.
    */
    public String ObsRange = new String();
    /**
    * Enum:LabAbnormalFlag 0-None, 1-Below, 2-Normal, 3-Above.
    */
    public LabAbnormalFlag AbnormalFlag = LabAbnormalFlag.None;
    /**
    * 
    */
    public LabResult copy() throws Exception {
        return (LabResult)this.MemberwiseClone();
    }

}


