//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CDSPermission;
import OpenDentBusiness.TableBase;

/**
* User to specify user level permissions used for CDS interventions.  Unlike normal permissions and security, each permission has its own column and each employee has their own row.
*/
public class CDSPermission  extends TableBase 
{
    /**
    * Primary key.
    */
    public long CDSPermissionNum = new long();
    /**
    * FK to userod.UserNum.
    */
    public long UserNum = new long();
    /**
    * True if allowed to edit EHR Triggers.
    */
    public boolean SetupCDS = new boolean();
    /**
    * True if user should see EHR triggers that are enabled.  If false, no CDS interventions will show.
    */
    public boolean ShowCDS = new boolean();
    /**
    * True if user can see Infobutton.
    */
    public boolean ShowInfobutton = new boolean();
    /**
    * True if user can edit to bibliographic information.
    */
    public boolean EditBibliography = new boolean();
    /**
    * True to enable Problem based CDS interventions for this user.
    */
    public boolean ProblemCDS = new boolean();
    /**
    * True to enable Medication based CDS interventions for this user.
    */
    public boolean MedicationCDS = new boolean();
    /**
    * True to enable Allergy based CDS interventions for this user.
    */
    public boolean AllergyCDS = new boolean();
    /**
    * True to enable Demographic based CDS interventions for this user.
    */
    public boolean DemographicCDS = new boolean();
    /**
    * True to enable Lab Test based CDS interventions for this user.
    */
    public boolean LabTestCDS = new boolean();
    /**
    * True to enable Vital Sign based CDS interventions for this user.
    */
    public boolean VitalCDS = new boolean();
    /**
    * 
    */
    public CDSPermission copy() throws Exception {
        return (CDSPermission)this.MemberwiseClone();
    }

}


