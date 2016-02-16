//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.TableBase;

/**
* A list of diseases that can be assigned to patients.  Cannot be deleted if in use by any patients.
*/
public class DiseaseDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DiseaseDefNum = new long();
    /**
    * .
    */
    public String DiseaseName = new String();
    /**
    * 0-based.  The order that the diseases will show in various lists.
    */
    public int ItemOrder = new int();
    /**
    * If hidden, the disease will still show on any patient that it was previously attached to, but it will not be available for future patients.
    */
    public boolean IsHidden = new boolean();
    /**
    * The last date and time this row was altered.  Not user editable.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * FK icd9.Icd9Code.  Example: 250.00 for diabetes.  User not allowed to enter any string anymore, must pick one from the Icd9Code table.  Some may exist in the databases without linking to a valid Icd9Code table entry if the ConvertDatabase could not find the user typed string in the list of valid Icd9Codes.
    */
    public String ICD9Code = new String();
    /**
    * FK snomed.SnomedCode.  Example: 230572002 for diabetic neuropathy.  User not allowed to enter any string anymore, must pick from the Snomed table.  Some may exist in the databases without linking to a valid Snomed table entry if the ConvertDatabase could find the user typed string in the list of valid SnomedCodes.
    */
    public String SnomedCode = new String();
    /**
    * FK icd10.Icd10Code.  Example: E10.1 for 'Type 1 diabetes mellitus with ketoacidosis'. User not allowed to enter any string anymore, must pick one from the Icd10Code table.
    */
    public String Icd10Code = new String();
    /**
    * 
    */
    public DiseaseDef copy() throws Exception {
        return (DiseaseDef)this.MemberwiseClone();
    }

}


