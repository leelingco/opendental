//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.LabPanel;
import OpenDentBusiness.TableBase;

/**
* One lab panel comes back from the lab with multiple lab results.  Multiple panels can come back in one HL7 message.  This table loosely corresponds to the OBR segment.
*/
public class LabPanel  extends TableBase 
{
    /**
    * Primary key.
    */
    public long LabPanelNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * The entire raw HL7 message.  Can contain other labpanels in addition to this one.
    */
    public String RawMessage = new String();
    /**
    * Both name and address in a single field.  OBR-20.
    */
    public String LabNameAddress = new String();
    /**
    * To be used for synch with web server.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * OBR-13.  Usually blank.  Example: hemolyzed.
    */
    public String SpecimenCondition = new String();
    /**
    * OBR-15.  Usually blank.  Example: LNA&Arterial Catheter&HL70070.
    */
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
    public LabPanel copy() throws Exception {
        return (LabPanel)this.MemberwiseClone();
    }

}


