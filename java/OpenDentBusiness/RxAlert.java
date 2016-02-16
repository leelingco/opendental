//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.RxAlert;
import OpenDentBusiness.TableBase;

/**
* Many-to-many relationship connecting Rx with DiseaseDef, AllergyDef, or Medication.  Only one of those links may be specified in a single row; the other two will be 0.
*/
public class RxAlert  extends TableBase 
{
    /**
    * Primary key.
    */
    public long RxAlertNum = new long();
    /**
    * FK to rxdef.RxDefNum.  This alert is to be shown when user attempts to write an Rx for this RxDef.
    */
    public long RxDefNum = new long();
    /**
    * FK to diseasedef.DiseaseDefNum.  Only if DrugProblem interaction.  This is compared against disease.DiseaseDefNum using PatNum.  Drug-Problem (they call it Drug-Diagnosis) checking is also performed in NewCrop.
    */
    public long DiseaseDefNum = new long();
    /**
    * FK to allergydef.AllergyDefNum.  Only if DrugAllergy interaction.  Compared against allergy.AllergyDefNum using PatNum.  Drug-Allergy checking is also perfomed in NewCrop.
    */
    public long AllergyDefNum = new long();
    /**
    * FK to medication.MedicationNum.  Only if DrugDrug interaction.  This will be compared against medicationpat.MedicationNum using PatNum.  Drug-Drug checking is also performed in NewCrop.
    */
    public long MedicationNum = new long();
    /**
    * This is typically blank, so a default message will be displayed by OD.  But if this contains a message, then this message will be used instead.
    */
    public String NotificationMsg = new String();
    /**
    * False by default.  Set to true to flag the drug-drug or drug-allergy intervention as high significance.
    */
    public boolean IsHighSignificance = new boolean();
    /**
    * 
    */
    public RxAlert copy() throws Exception {
        return (RxAlert)this.MemberwiseClone();
    }

}


