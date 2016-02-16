//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;

import OpenDentBusiness.VaccineCompletionStatus;

public class EhrCqmMedicationPat   
{
    public long EhrCqmMedicationPatNum = new long();
    //will either be a medicationpat or a vaccinepat, the other num will be set to 0
    public long EhrCqmVaccinePatNum = new long();
    public long PatNum = new long();
    public long RxCui = new long();
    public String CVXCode = new String();
    public String CodeSystemName = new String();
    public String CodeSystemOID = new String();
    public String Description = new String();
    public String ValueSetName = new String();
    public String ValueSetOID = new String();
    public String PatNote = new String();
    //will be blank if vaccinepat object
    public VaccineCompletionStatus CompletionStatus = VaccineCompletionStatus.Complete;
    public DateTime DateStart = new DateTime();
    public DateTime DateStop = new DateTime();
}


