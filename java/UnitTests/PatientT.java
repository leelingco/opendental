//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package UnitTests;

import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

public class PatientT   
{
    public static Patient createPatient(String suffix) throws Exception {
        Patient pat = new Patient();
        pat.setIsNew(true);
        pat.LName = "LName" + suffix;
        pat.FName = "FName" + suffix;
        pat.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
        pat.PriProv = PrefC.getLong(PrefName.PracticeDefaultProv);
        //This causes standard fee sched to be 53.
        Patients.insert(pat,false);
        Patient oldPatient = pat.copy();
        pat.Guarantor = pat.PatNum;
        Patients.update(pat,oldPatient);
        return pat;
    }

    public static void setGuarantor(Patient pat, long guarantorNum) throws Exception {
        Patient oldPatient = pat.copy();
        pat.Guarantor = guarantorNum;
        Patients.update(pat,oldPatient);
    }

}


