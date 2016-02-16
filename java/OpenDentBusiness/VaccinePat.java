//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.VaccineAction;
import OpenDentBusiness.VaccineAdministrationNote;
import OpenDentBusiness.VaccineAdministrationRoute;
import OpenDentBusiness.VaccineAdministrationSite;
import OpenDentBusiness.VaccineCompletionStatus;
import OpenDentBusiness.VaccinePat;
import OpenDentBusiness.VaccineRefusalReason;

/**
* A vaccine given to a patient on a date.
*/
public class VaccinePat  extends TableBase 
{
    /**
    * Primary key.
    */
    public long VaccinePatNum = new long();
    /**
    * FK to vaccinedef.VaccineDefNum.  Can be 0 if and only if CompletionStatus=NotAdministered, in which case CVX code is assumed to be 998 (not administered) and there is no manufacturer.
    */
    public long VaccineDefNum = new long();
    /**
    * The datetime that the vaccine was administered.
    */
    public DateTime DateTimeStart = new DateTime();
    /**
    * Typically set to the same as DateTimeStart.  User can change.
    */
    public DateTime DateTimeEnd = new DateTime();
    /**
    * Size of the dose of the vaccine.  0 indicates unknown and gets converted to 999 on HL7 output.
    */
    public float AdministeredAmt = new float();
    /**
    * FK to drugunit.DrugUnitNum. Unit of measurement of the AdministeredAmt.  0 represents null.  When going out in HL7 RXA-7, the units must be valid UCUM or the export will be blocked.
    * Sometime in the future, we may want to convert this column to a string and name it "UcumCode".  For now left alone for backwards compatibility.
    */
    public long DrugUnitNum = new long();
    /**
    * Optional.  Used in HL7 RXA-9.1.
    */
    public String LotNumber = new String();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Documentation sometimes required.
    */
    public String Note = new String();
    /**
    * The city where the vaccine was filled.  This can be different than the practice office city for historical vaccine information.  Exported in HL7 ORC-3.
    */
    public String FilledCity = new String();
    /**
    * The state where the vaccine was filled.  This can be different than the practice office state for historical vaccine infromation.  Exported in HL7 ORC-3.
    */
    public String FilledST = new String();
    /**
    * Exported in HL7 RXA-20.  Corresponds to HL7 table 0322 (guide page 225).
    */
    public VaccineCompletionStatus CompletionStatus = VaccineCompletionStatus.Complete;
    /**
    * Exported in HL7 RXA-9.  Corresponds to CDC code set NIP001 (http://hl7v2-iz-testing.nist.gov/mu-immunization/).
    */
    public VaccineAdministrationNote AdministrationNoteCode = VaccineAdministrationNote.NewRecord;
    /**
    * FK to userod.UserNum.  The user that the vaccine was entered by.  May be 0 for vaccines added before this column was created.  Exported in HL7 ORD-10.
    */
    public long UserNum = new long();
    /**
    * FK to provider.ProvNum.  The provider who ordered the vaccine.  Exported in HL7 ORD-12.
    */
    public long ProvNumOrdering = new long();
    /**
    * FK to provider.ProvNum.  The provider who administered the vaccine.  Exported in HL7 RXA-10.
    */
    public long ProvNumAdminister = new long();
    /**
    * The date that the vaccine expires.  Exported in HL7 RXA-16.
    */
    public DateTime DateExpire = new DateTime();
    /**
    * Exported in HL7 RXA-18.  Corresponds to CDC code set NIP002 (http://hl7v2-iz-testing.nist.gov/mu-immunization/).
    */
    public VaccineRefusalReason RefusalReason = VaccineRefusalReason.None;
    /**
    * Exported in HL7 RXA-21.  Corresponds to HL7 table 0323 (guide page 225).
    */
    public VaccineAction ActionCode = VaccineAction.Add;
    /**
    * Exported in HL7 RXR-1.  Corresponds to HL7 table 0162 (guide page 200).
    */
    public VaccineAdministrationRoute AdministrationRoute = VaccineAdministrationRoute.None;
    /**
    * Exported in HL7 RXR-2.  Corresponds to HL7 table 0163 (guide page 201).
    */
    public VaccineAdministrationSite AdministrationSite = VaccineAdministrationSite.None;
    /**
    * 
    */
    public VaccinePat copy() throws Exception {
        return (VaccinePat)this.MemberwiseClone();
    }

}


