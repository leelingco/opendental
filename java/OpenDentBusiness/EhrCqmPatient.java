//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrCqmPatient;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientRace;

/**
* This is all of the patient data required for QRDA category 1 reporting (in the patient recordTarget section).  If the patient is in ListEhrPats for this EhrCqmData object, the patient is part of the initial patient population.  The patient will also be placed into the 'Numerator', 'Exclusion', or 'Exception' category, for counting up results for the QRDA category 3 report for this measure.  A short explanation will be provided if the patient is not in the 'Numerator' to help the user improve their percentage.
*/
public class EhrCqmPatient   
{
    public Patient EhrCqmPat;
    public List<PatientRace> ListPatientRaces = new List<PatientRace>();
    public PatientRace Ethnicity;
    public String PayorSopCode = new String();
    public String PayorDescription = new String();
    public String PayorValueSetOID = new String();
    public boolean IsNumerator = new boolean();
    public boolean IsExclusion = new boolean();
    public boolean IsException = new boolean();
    //for all but one measure denominator=initial patient population.  The influenza vaccine measure says denominator is IPP with some additional restrictions that are not exclusions or exceptions.  In this case we will set this bool to false, so patient will be in IPP but not in denominator.  This will almost always be true since the list of EhrCqmPatients for each measure is the IPP and the denominator.
    public boolean IsDenominator = new boolean();
    public String Explanation = new String();
    public EhrCqmPatient copy() throws Exception {
        EhrCqmPatient ehrCqmPatient = (EhrCqmPatient)this.MemberwiseClone();
        ehrCqmPatient.ListPatientRaces = new List<PatientRace>();
        for (int i = 0;i < ListPatientRaces.Count;i++)
        {
            ehrCqmPatient.ListPatientRaces.Add(ListPatientRaces[i].Clone());
        }
        return ehrCqmPatient;
    }

    public EhrCqmPatient() throws Exception {
        ListPatientRaces = new List<PatientRace>();
    }

}


