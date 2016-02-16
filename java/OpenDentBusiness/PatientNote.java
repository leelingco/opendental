//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* Essentially more columns in the patient table.  They are stored here because these fields can contain a lot of information, and we want to try to keep the size of the patient table a bit smaller.
*/
public class PatientNote  extends TableBase 
{
    /**
    * FK to patient.PatNum.  Also the primary key for this table. Always one to one relationship with patient table.  A new patient might not have an entry here until needed.
    */
    public long PatNum = new long();
    /**
    * Only one note per family stored with guarantor.
    */
    public String FamFinancial = new String();
    /**
    * No longer used.
    */
    public String ApptPhone = new String();
    /**
    * Medical Summary
    */
    public String Medical = new String();
    /**
    * Service notes
    */
    public String Service = new String();
    /**
    * Complete current Medical History
    */
    public String MedicalComp = new String();
    /**
    * Shows in the Chart module just below the graphical tooth chart.
    */
    public String Treatment = new String();
}


