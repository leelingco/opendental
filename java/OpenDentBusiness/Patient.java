//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientGrade;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.PatientRaceOld;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.TreatmentUrgency;
import OpenDentBusiness.YN;

/**
* One row for each patient.  Includes deleted patients.
*/
public class Patient  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PatNum = new long();
    /**
    * Last name.
    */
    public String LName = new String();
    /**
    * First name.
    */
    public String FName = new String();
    /**
    * Middle initial or name.
    */
    public String MiddleI = new String();
    /**
    * Preferred name, aka nickname.
    */
    public String Preferred = new String();
    /**
    * Enum:PatientStatus
    */
    public PatientStatus PatStatus = PatientStatus.Patient;
    /**
    * Enum:PatientGender
    */
    public PatientGender Gender = PatientGender.Male;
    /**
    * Enum:PatientPosition Marital status would probably be a better name for this column.
    */
    public PatientPosition Position = PatientPosition.Single;
    /**
    * Age is not stored in the database.  Age is always calculated as needed from birthdate.
    */
    public DateTime Birthdate = new DateTime();
    /**
    * In the US, this is 9 digits, no dashes. For all other countries, any punctuation or format is allowed.
    */
    public String SSN = new String();
    /**
    * .
    */
    public String Address = new String();
    /**
    * Optional second address line.
    */
    public String Address2 = new String();
    /**
    * .
    */
    public String City = new String();
    /**
    * 2 Char in USA.  Used to store province for Canadian users.
    */
    public String State = new String();
    /**
    * Postal code.  For Canadian claims, it must be ANANAN.  No validation gets done except there.
    */
    public String Zip = new String();
    /**
    * Home phone. Includes any punctuation
    */
    public String HmPhone = new String();
    /**
    * .
    */
    public String WkPhone = new String();
    /**
    * .
    */
    public String WirelessPhone = new String();
    /**
    * FK to patient.PatNum.  Head of household.
    */
    public long Guarantor = new long();
    /**
    * Derived from Birthdate.  Not in the database table.
    */
    private int _age = new int();
    /**
    * Single char. Shows at upper right corner of appointments.  Suggested use is A,B,or C to designate creditworthiness, but it can actually be used for any purpose.
    */
    public String CreditType = new String();
    /**
    * .
    */
    public String Email = new String();
    /**
    * Dear __.  This field does not include the "Dear" or a trailing comma.  If this field is blank, then the typical salutation is FName.  Or, if a Preferred name is present, that is used instead of FName.
    */
    public String Salutation = new String();
    /**
    * Current patient balance.(not family). Never subtracts insurance estimates.
    */
    public double EstBalance = new double();
    /**
    * FK to provider.ProvNum.  The patient's primary provider.  Required.  The database maintenance tool ensures that every patient always has this number set, so the program no longer has to handle 0.
    */
    public long PriProv = new long();
    /**
    * FK to provider.ProvNum.  Secondary provider (hygienist). Optional.
    */
    public long SecProv = new long();
    /**
    * FK to feesched.FeeSchedNum.  Fee schedule for this patient.  Usually not used.  If missing, the practice default fee schedule is used. If patient has insurance, then the fee schedule for the insplan is used.
    */
    public long FeeSched = new long();
    /**
    * FK to definition.DefNum.  Must have a value, or the patient will not show on some reports.
    */
    public long BillingType = new long();
    /**
    * Name of folder where images will be stored. Not editable for now.
    */
    public String ImageFolder = new String();
    /**
    * Address or phone note.  Unlimited length in order to handle data from other programs during a conversion.
    */
    public String AddrNote = new String();
    /**
    * Family financial urgent note.  Only stored with guarantor, and shared for family.
    */
    public String FamFinUrgNote = new String();
    /**
    * Individual patient note for Urgent medical.
    */
    public String MedUrgNote = new String();
    /**
    * Individual patient note for Appointment module note.
    */
    public String ApptModNote = new String();
    /**
    * Single char for Nonstudent, Parttime, or Fulltime.  Blank also=Nonstudent
    */
    public String StudentStatus = new String();
    /**
    * College name.  If Canadian, then this is field C10 and must be filled if C9 (patient.CanadianEligibilityCode) is 1 and patient is 18 or older.
    */
    public String SchoolName = new String();
    /**
    * Max 15 char.  Used for reference to previous programs.
    */
    public String ChartNumber = new String();
    /**
    * Optional. The Medicaid ID for this patient.
    */
    public String MedicaidID = new String();
    /**
    * Aged balance from 0 to 30 days old. Aging numbers are for entire family.  Only stored with guarantor.
    */
    public double Bal_0_30 = new double();
    /**
    * Aged balance from 31 to 60 days old. Aging numbers are for entire family.  Only stored with guarantor.
    */
    public double Bal_31_60 = new double();
    /**
    * Aged balance from 61 to 90 days old. Aging numbers are for entire family.  Only stored with guarantor.
    */
    public double Bal_61_90 = new double();
    /**
    * Aged balance over 90 days old. Aging numbers are for entire family.  Only stored with guarantor.
    */
    public double BalOver90 = new double();
    /**
    * Insurance Estimate for entire family.
    */
    public double InsEst = new double();
    /**
    * Total balance for entire family before insurance estimate.  Not the same as the sum of the 4 aging balances because this can be negative.  Only stored with guarantor.
    */
    public double BalTotal = new double();
    /**
    * FK to employer.EmployerNum.
    */
    public long EmployerNum = new long();
    /**
    * Not used since version 2.8.
    */
    public String EmploymentNote = new String();
    /**
    * Enum:PatientRaceOld Race and ethnicity.  Mostly deprecated.  Only used in FormScreenGroup.  Was replaced by the patientrace table so that we could attach multiple races.
    */
    public PatientRaceOld Race = PatientRaceOld.Unknown;
    /**
    * FK to county.CountyName, although it will not crash if key absent.
    */
    public String County = new String();
    /**
    * Enum:PatientGrade Gradelevel.
    */
    public PatientGrade GradeLevel = PatientGrade.Unknown;
    /**
    * Enum:TreatmentUrgency Used in public health screenings.
    */
    public TreatmentUrgency Urgency = TreatmentUrgency.Unknown;
    /**
    * The date that the patient first visited the office.  Automated.
    */
    public DateTime DateFirstVisit = new DateTime();
    /**
    * FK to clinic.ClinicNum. Can be zero if not attached to a clinic or no clinics set up.
    */
    public long ClinicNum = new long();
    /**
    * For now, an 'I' indicates that the patient has insurance.  This is only used when displaying appointments.  It will later be expanded.  User can't edit.
    */
    public String HasIns = new String();
    /**
    * The Trophy bridge is inadequate.  This is an attempt to make it usable for offices that have already invested in Trophy hardware.
    */
    public String TrophyFolder = new String();
    /**
    * This simply indicates whether the 'done' box is checked in the chart module.  Used to be handled as a -1 in the NextAptNum field, but now that field is unsigned.
    */
    public boolean PlannedIsDone = new boolean();
    /**
    * Set to true if patient needs to be premedicated for appointments, includes PAC, halcion, etc.
    */
    public boolean Premed = new boolean();
    /**
    * Only used in hospitals.
    */
    public String Ward = new String();
    /**
    * Enum:ContactMethod
    */
    public ContactMethod PreferConfirmMethod = ContactMethod.None;
    /**
    * Enum:ContactMethod
    */
    public ContactMethod PreferContactMethod = ContactMethod.None;
    /**
    * Enum:ContactMethod
    */
    public ContactMethod PreferRecallMethod = ContactMethod.None;
    /**
    * .
    */
    public TimeSpan SchedBeforeTime = new TimeSpan();
    /**
    * .
    */
    public TimeSpan SchedAfterTime = new TimeSpan();
    /**
    * We do not use this, but some users do, so here it is. 0=none. Otherwise, 1-7 for day.
    */
    public byte SchedDayOfWeek = new byte();
    /**
    * The primary language of the patient.  Typically eng (English), fra (French), spa (Spanish), or similar.  If it's a custom language, then it might look like Tahitian.
    */
    public String Language = new String();
    /**
    * Used in hospitals.  It can be before the first visit date.  It typically gets set automatically by the hospital system.
    */
    public DateTime AdmitDate = new DateTime();
    /**
    * Includes any punctuation.  For example, Mr., Mrs., Miss, Dr., etc.  There is no selection mechanism yet for user; they must simply type it in.
    */
    public String Title = new String();
    /**
    * .
    */
    public double PayPlanDue = new double();
    /**
    * FK to site.SiteNum. Can be zero. Replaces the old GradeSchool field with a proper foreign key.
    */
    public long SiteNum = new long();
    /**
    * Automatically updated by MySQL every time a row is added or changed. Could be changed due to user editing, custom queries or program updates.  Not user editable.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * FK to patient.PatNum. Can be zero.  Person responsible for medical decisions rather than finances.  Guarantor is still responsible for finances.  This is useful for nursing home residents.  Part of public health.
    */
    public long ResponsParty = new long();
    /**
    * C09.  Eligibility Exception Code.  A single digit 1-4.  0 is not acceptable for e-claims. 1=FT student, 2=disabled, 3=disabled student, 4=code not applicable.  Warning.  4 is a 0 if using CDAnet version 02.
    */
    public byte CanadianEligibilityCode = new byte();
    /**
    * Number of minutes patient is asked to come early to appointments.
    */
    public int AskToArriveEarly = new int();
    /**
    * The hashed password for online access to patient portal for this patient.  Blank if no password set yet.  Blank password indicates no online access.
    */
    public String OnlinePassword = new String();
    /**
    * Enum:ContactMethod  Used for EHR.
    */
    public ContactMethod PreferContactConfidential = ContactMethod.None;
    /**
    * FK to patient.PatNum.  If this is the same as PatNum, then this is a SuperHead.  If zero, then not part of a superfamily.  Synched for entire family.  If family is part of a superfamily, then the guarantor for this family will show in the superfamily list in the Family module for anyone else who is in the superfamily.  Only a guarantor can be a superfamily head.
    */
    public long SuperFamily = new long();
    /**
    * Enum:YN
    */
    public YN TxtMsgOk = YN.Unknown;
    /**
    * EHR smoking status as a SNOMED code.  See SmokingSnoMed enum in approx line 1275 of Enumerations.cs.
    */
    public String SmokingSnoMed = new String();
    /**
    * Country name.  Only used by HQ to add country names to statements.
    */
    public String Country = new String();
    /**
    * Needed for EHR syndromic surveillance messaging.  Used in HL7 PID-29.  Also for feature request #3040.  Date and time because we need precision to the minute in syndromic surveillence messging.
    */
    public DateTime DateTimeDeceased = new DateTime();
    //<summary>Decided not to add since this data is already available and synchronizing would take too much time.  Will add later.  Not editable. If the patient happens to have a future appointment, this will contain the date of that appointment.  Once appointment is set complete, this date is deleted.  If there is more than one appointment scheduled, this will only contain the earliest one.  Used mostly to exclude patients from recall lists.  If you want all future appointments, use Appointments.GetForPat() instead. You can loop through that list and exclude appointments with dates earlier than today.</summary>
    //public DateTime DateScheduled;
    /**
    * Used only for serialization purposes
    */
    public long getSchedBeforeTimeXml() throws Exception {
        return SchedBeforeTime.Ticks;
    }

    public void setSchedBeforeTimeXml(long value) throws Exception {
        SchedBeforeTime = TimeSpan.FromTicks(value);
    }

    /**
    * Used only for serialization purposes
    */
    public long getSchedAfterTimeXml() throws Exception {
        return SchedAfterTime.Ticks;
    }

    public void setSchedAfterTimeXml(long value) throws Exception {
        SchedAfterTime = TimeSpan.FromTicks(value);
    }

    /**
    * Returns a copy of this Patient.
    */
    public Patient copy() throws Exception {
        return (Patient)this.MemberwiseClone();
    }

    /**
    * Calculated from birthdate.
    */
    public int getAge() throws Exception {
        _age = Patients.dateToAge(Birthdate);
        return _age;
    }

    public void setAge(int value) throws Exception {
        //for backwards cvompatibility
        _age = value;
    }

    public String toString() {
        try
        {
            return "Patient: " + getNameLF();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    /**
    * LName, 'Preferred' FName M
    */
    public String getNameLF() throws Exception {
        return Patients.getNameLF(LName,FName,Preferred,MiddleI);
    }

    /**
    * FName 'Preferred' M LName
    */
    public String getNameFL() throws Exception {
        return Patients.getNameFL(LName,FName,Preferred,MiddleI);
    }

    /**
    * FName M LName
    */
    public String getNameFLnoPref() throws Exception {
        return Patients.getNameFLnoPref(LName,FName,MiddleI);
    }

    /**
    * FName/Preferred LName
    */
    public String getNameFirstOrPrefL() throws Exception {
        return Patients.getNameFirstOrPrefL(LName,FName,Preferred);
    }

    /**
    * FName/Preferred M. LName
    */
    public String getNameFirstOrPrefML() throws Exception {
        return Patients.getNameFirstOrPrefML(LName,FName,Preferred,MiddleI);
    }

    /**
    * Title FName M LName
    */
    public String getNameFLFormal() throws Exception {
        return Patients.getNameFLFormal(LName,FName,MiddleI,Title);
    }

    /**
    * Includes preferred.
    */
    public String getNameFirst() throws Exception {
        return Patients.getNameFirst(FName,Preferred);
    }

    /**
    * 
    */
    public String getNameFirstOrPreferred() throws Exception {
        return Patients.getNameFirstOrPreferred(FName,Preferred);
    }

    /**
    * Dear __.  Does not include the "Dear" or the comma.
    */
    public String getSalutation() throws Exception {
        return Patients.getSalutation(Salutation,Preferred,FName);
    }

}


