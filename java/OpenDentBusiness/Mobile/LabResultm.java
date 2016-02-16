//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.LabAbnormalFlag;
import OpenDentBusiness.Mobile.LabResultm;
import OpenDentBusiness.TableBase;

public class LabResultm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
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
    * OBX-3, text portion.
    */
    public String TestName = new String();
    /**
    * OBX-3 id portion, LOINC.  For example, 10676-5.
    */
    public String TestID = new String();
    /**
    * Value always stored as a string because the type can vary.
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
    * Enum:LabAbnormalFlag 0-No value, 1-Below normal, 2-Normal, 3-Above high normal.
    */
    public LabAbnormalFlag AbnormalFlag = LabAbnormalFlag.None;
    /**
    * 
    */
    public LabResultm copy() throws Exception {
        return (LabResultm)this.MemberwiseClone();
    }

}


