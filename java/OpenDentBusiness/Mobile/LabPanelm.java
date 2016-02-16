//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.LabPanelm;
import OpenDentBusiness.TableBase;

public class LabPanelm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long LabPanelNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * Both name and address in a single field.  OBR-20
    */
    public String LabNameAddress = new String();
    /**
    * OBR-13.  Usually blank.  Example: hemolyzed.
    */
    public String SpecimenCondition = new String();
    ///<summary>OBR-15-1.  Usually blank.  Example: LNA&Arterial Catheter&HL70070.</summary>
    public String SpecimenSource = new String();
    /**
    * OBR-4-0, Service performed, id portion, LOINC.  For example, 24331-1.
    */
    public String ServiceId = new String();
    /**
    * OBR-4-1, Service performed description.  Example, Lipid Panel.
    */
    public String ServiceName = new String();
    /**
    * FK to medicalorder.MedicalOrderNum.  Used to attach in imported lab panel to a lab order.  Multiple panels may be attached to an order.
    */
    public long MedicalOrderNum = new long();
    /**
    * 
    */
    public LabPanelm copy() throws Exception {
        return (LabPanelm)this.MemberwiseClone();
    }

}


