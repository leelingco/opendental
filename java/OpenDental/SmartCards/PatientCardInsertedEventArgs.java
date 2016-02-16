//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;

import OpenDentBusiness.Patient;

public class PatientCardInsertedEventArgs  extends EventArgs 
{
    public PatientCardInsertedEventArgs(Patient patient) throws Exception {
        this.patient = patient;
    }

    private Patient patient;
    public Patient getPatient() throws Exception {
        return patient;
    }

}


