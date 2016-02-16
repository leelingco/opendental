//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EnumProcDrugUnit;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcUnitQtyType;
import OpenDentBusiness.TableBase;

/**
* Database table is procedurelog.  A procedure for a patient.  Can be treatment planned or completed.  Once it's completed, it gets tracked more closely be the security portion of the program.  A procedure can NEVER be deleted.  Status can just be changed to "deleted".
*/
public class Procedure  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ProcNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * FK to appointment.AptNum.  Only allowed to attach proc to one appt(not counting planned appt)
    */
    public long AptNum = new long();
    /**
    * No longer used.
    */
    public String OldCode = new String();
    /**
    * Procedure date that will show in the account as the date performed.  If just treatment planned, the date can be the date it was tp'd, or the date can be min val if we don't care.  Also see ProcTime column.
    */
    public DateTime ProcDate = new DateTime();
    /**
    * Procedure fee.
    */
    public double ProcFee = new double();
    /**
    * Surfaces, or use "UL" etc for quadrant, "2" etc for sextant, "U","L" for arches.
    */
    public String Surf = new String();
    /**
    * May be blank, otherwise 1-32, 51-82, A-T, or AS-TS, 1 or 2 char.
    */
    public String ToothNum = new String();
    /**
    * May be blank, otherwise is series of toothnumbers separated by commas.
    */
    public String ToothRange = new String();
    /**
    * FK to definition.DefNum, which contains the text of the priority.
    */
    public long Priority = new long();
    /**
    * Enum:ProcStat TP=1,Complete=2,Existing Cur Prov=3,Existing Other Prov=4,Referred=5,Deleted=6,Condition=7.
    */
    public OpenDentBusiness.ProcStat ProcStatus = OpenDentBusiness.ProcStat.TP;
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * FK to definition.DefNum, which contains text of the Diagnosis.
    */
    public long Dx = new long();
    /**
    * FK to appointment.AptNum.  Was called NextAptNum in older versions.  Allows this procedure to be attached to a Planned appointment as well as a standard appointment.
    */
    public long PlannedAptNum = new long();
    /**
    * Enum:PlaceOfService  Only used in Public Health. Zero(Office) until procedure set complete. Then it's set to the value of the DefaultProcedurePlaceService preference.
    */
    public PlaceOfService PlaceService = PlaceOfService.Office;
    /**
    * Single char. Blank=no, I=Initial, R=Replacement.
    */
    public String Prosthesis = new String();
    /**
    * For a prosthesis Replacement, this is the original date.
    */
    public DateTime DateOriginalProsth = new DateTime();
    /**
    * This note goes out on e-claims.  Not visible in Canada.
    */
    public String ClaimNote = new String();
    /**
    * This is the date this procedure was entered or set complete.  If not status C, then the value is ignored.  This date is set automatically when Insert, but older data or converted data might not have this value set.  It gets updated when set complete.  User never allowed to edit.  This will be enhanced later.
    */
    public DateTime DateEntryC = new DateTime();
    /**
    * FK to clinic.ClinicNum.  0 if no clinic.
    */
    public long ClinicNum = new long();
    /**
    * FK to procedurecode.ProcCode. Optional.
    */
    public String MedicalCode = new String();
    /**
    * Simple text for ICD-9 code. Gets sent with medical claims.
    */
    public String DiagnosticCode = new String();
    /**
    * Set true if this medical diagnostic code is the principal diagnosis for the visit.  If no principal diagnosis is marked for any procedures on a medical e-claim, then it won't be allowed to be sent.  If more than one is marked, then it will just use one at random.
    */
    public boolean IsPrincDiag = new boolean();
    /**
    * FK to procedurelog.ProcNum. Only used in Canada. If not zero, then this proc is a lab fee and this indicates to which actual procedure the lab fee is attached.  For ordinary use, they are treated like two separate procedures.  It's only for insurance claims that we need to know which lab fee belongs to which procedure.  Two lab fees may be attached to one procedure.
    */
    public long ProcNumLab = new long();
    /**
    * FK to definition.DefNum. Lets some users track charges for certain types of reports.  For example, a Medicaid billing type could be assigned to a procedure, flagging it for inclusion in a report mandated by goverment.  Would be more useful if it was automated to flow down based on insurance plan type, but that can be added later.  Not visible if prefs.EasyHideMedicaid is true.
    */
    public long BillingTypeOne = new long();
    /**
    * FK to definition.DefNum.  Same as BillingTypeOne, but used when there is a secondary billing type to account for.
    */
    public long BillingTypeTwo = new long();
    /**
    * FK to procedurecode.CodeNum
    */
    public long CodeNum = new long();
    /**
    * Modifier for certain CPT codes.
    */
    public String CodeMod1 = new String();
    /**
    * Modifier for certain CPT codes.
    */
    public String CodeMod2 = new String();
    /**
    * Modifier for certain CPT codes.
    */
    public String CodeMod3 = new String();
    /**
    * Modifier for certain CPT codes.
    */
    public String CodeMod4 = new String();
    /**
    * NUBC Revenue Code for medical/inst billing. Used on UB04 and 837I.
    */
    public String RevCode = new String();
    /**
    * Default is 1.  Becomes Service Unit Count on institutional UB claimforms SV205.  Becomes Service Unit Count on medical 1500 claimforms SV104.  Becomes procedure count on dental claims SV306.  Gets multiplied by fee in all accounting calculations.
    */
    public int UnitQty = new int();
    /**
    * Base units used for some billing codes.  Default is 0.  No UI for this field.  It is only edited in the ProcedureCode window.  The database maint tool changes BaseUnits of all procedures to match that of the procCode.  Not sure yet what it's for.
    */
    public int BaseUnits = new int();
    /**
    * Start time in military.  No longer used, but not deleting just in case someone has critical information stored here.
    */
    public int StartTime = new int();
    /**
    * Stop time in military.  No longer used, but not deleting just in case someone has critical information stored here.
    */
    public int StopTime = new int();
    /**
    * The date that the procedure was originally treatment planned.  Does not change when marked complete.
    */
    public DateTime DateTP = new DateTime();
    /**
    * FK to site.SiteNum.
    */
    public long SiteNum = new long();
    /**
    * Set to true to hide the chart graphics for this procedure.  For example, a crown was done, but then tooth extracted.
    */
    public boolean HideGraphics = new boolean();
    /**
    * F16, up to 5 char. One or more of the following: A=Repair of a prior service, B=Temporary placement, C=TMJ, E=Implant, L=Appliance lost, S=Appliance stolen, X=none of the above.  Blank is equivalent to X for claim output, but one value will not be automatically converted to the other in this table.  That will allow us to track user entry for procedurecode.IsProsth.
    */
    public String CanadianTypeCodes = new String();
    /**
    * Used to be part of the ProcDate, but that was causing reporting issues.
    */
    public TimeSpan ProcTime = new TimeSpan();
    /**
    * Marks the time a procedure was finished.
    */
    public TimeSpan ProcTimeEnd = new TimeSpan();
    /**
    * Automatically updated by MySQL every time a row is added or changed.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * FK to definition.DefNum, which contains text of the Prognosis.
    */
    public long Prognosis = new long();
    /**
    * Enum:EnumProcDrugUnit For 837I and UB04
    */
    public EnumProcDrugUnit DrugUnit = EnumProcDrugUnit.None;
    /**
    * Includes fractions. For 837I
    */
    public float DrugQty = new float();
    /**
    * Enum:ProcUnitQtyType For dental, the type is always sent electronically as MultiProcs. For institutional SV204, Days will be sent electronically if chosen, otherwise ServiceUnits will be sent. For medical SV103, MinutesAnesth will be sent electronically if chosen, otherwise ServiceUnits will be sent.
    */
    public ProcUnitQtyType UnitQtyType = ProcUnitQtyType.MultProcs;
    /**
    * FK to statement.StatementNum.  Only used when the statement in an invoice.
    */
    public long StatementNum = new long();
    /**
    * If this flag is set, then the proc is locked down tight.  No changes at all can be made except to append, sign, or invalidate. Invalidate really just sets the proc to status 'deleted'.  An invalidated proc retains its IsLocked status.  All locked procs will be status of C or D.  Locked group notes will be status of EC or D.
    */
    public boolean IsLocked = new boolean();
    /**
    * A note that will show directly in the Account module.  Also used for repeating charges. Helps distinguish between charges for the same proccode in the same month.
    */
    public String BillingNote = new String();
    /**
    * FK to repeatcharge.RepeatChargeNum.  Used in repeating charges to determine which procedures belong to each repeating charge. This column
    * makes is possible to have two or more repeating charges for the same exact procedure code.
    */
    public long RepeatChargeNum = new long();
    /**
    * Not a database column.  Saved in database in the procnote table.  This note is only the most recent note from that table.  If user changes it, then the business layer handles it by adding another procnote to that table.
    */
    public String Note = new String();
    /**
    * Not a database column.  Just used for now to set the user so that it can be saved with the ProcNote.
    */
    public long UserNum = new long();
    /**
    * Not a database column.  If viewing an individual procedure, then this will contain the encrypted signature.  If viewing a procedure list, this will typically just contain an "X" if a signature is present.  If user signs note, the signature will be encrypted before placing into this field.  Then it will be passed down and saved directly as is.
    */
    public String Signature = new String();
    /**
    * Not a database column.
    */
    public boolean SigIsTopaz = new boolean();
    /**
    * Some procedures require a SNOMED code which indicates that site on the body at which this procedure was performed.
    */
    public String SnomedBodySite = new String();
    /**
    * Used only for serialization purposes
    */
    public long getProcTimeXml() throws Exception {
        return ProcTime.Ticks;
    }

    public void setProcTimeXml(long value) throws Exception {
        ProcTime = TimeSpan.FromTicks(value);
    }

    /**
    * Used only for serialization purposes
    */
    public long getProcTimeEndXml() throws Exception {
        return ProcTimeEnd.Ticks;
    }

    public void setProcTimeEndXml(long value) throws Exception {
        ProcTimeEnd = TimeSpan.FromTicks(value);
    }

    public Procedure() throws Exception {
        UnitQty = 1;
    }

    /**
    * Returns a copy of the procedure.
    */
    public Procedure copy() throws Exception {
        return (Procedure)this.MemberwiseClone();
    }

}


