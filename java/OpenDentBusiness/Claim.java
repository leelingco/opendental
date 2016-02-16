//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimAttach;
import OpenDentBusiness.ClaimCorrectionType;
import OpenDentBusiness.EnumClaimMedType;
import OpenDentBusiness.EnumClaimSpecialProgram;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.Relat;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.YN;

/**
* The claim table holds information about individual claims.  Each row represents one claim.
*/
public class Claim  extends TableBase 
{
    /**
    * Primary key
    */
    public long ClaimNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    //
    /**
    * Usually the same date as the procedures, but it can be changed if you wish.
    */
    public DateTime DateService = new DateTime();
    /**
    * Usually the date it was created.  It might be sent a few days later if you don't send your e-claims every day.
    */
    public DateTime DateSent = new DateTime();
    /**
    * Single char: U,H,W,P,S,or R.  U=Unsent, H=Hold until pri received, W=Waiting in queue, S=Sent, R=Received.  A(adj) is no longer used.  P(prob sent) is no longer used.
    */
    public String ClaimStatus = new String();
    /**
    * Date the claim was received.
    */
    public DateTime DateReceived = new DateTime();
    /**
    * FK to insplan.PlanNum.  Every claim is attached to one plan.
    */
    public long PlanNum = new long();
    /**
    * FK to provider.ProvNum.  Treating provider for dental claims.  For institutional claims, this is called the attending provider.
    */
    public long ProvTreat = new long();
    /**
    * Total fee of claim.
    */
    public double ClaimFee = new double();
    /**
    * Amount insurance is estimated to pay on this claim.
    */
    public double InsPayEst = new double();
    /**
    * Amount insurance actually paid.
    */
    public double InsPayAmt = new double();
    /**
    * Deductible applied to this claim.
    */
    public double DedApplied = new double();
    /**
    * The predetermination of benefits number received from ins.  In X12, REF G3.
    */
    public String PreAuthString = new String();
    /**
    * Single char for No, Initial, or Replacement.
    */
    public String IsProsthesis = new String();
    /**
    * Date prior prosthesis was placed.  Note that this is only for paper claims.  E-claims have a date field on each individual procedure.
    */
    public DateTime PriorDate = new DateTime();
    /**
    * Note for patient for why insurance didn't pay as expected.
    */
    public String ReasonUnderPaid = new String();
    /**
    * Note to be sent to insurance. Max 255 char.  E-claims also have notes on each procedure.
    */
    public String ClaimNote = new String();
    /**
    * "P"=primary, "S"=secondary, "PreAuth"=preauth, "Other"=other, "Cap"=capitation.  Not allowed to be blank. Might need to add "Med"=medical claim.
    */
    public String ClaimType = new String();
    /**
    * FK to provider.ProvNum.  Billing provider.  Assignment can be automated from the setup section.
    */
    public long ProvBill = new long();
    /**
    * FK to referral.ReferralNum.
    */
    public long ReferringProv = new long();
    /**
    * Referral number for this claim.
    */
    public String RefNumString = new String();
    /**
    * Enum:PlaceOfService .
    */
    public PlaceOfService PlaceService = PlaceOfService.Office;
    /**
    * blank or A=Auto, E=Employment, O=Other.
    */
    public String AccidentRelated = new String();
    /**
    * Date of accident, if applicable.
    */
    public DateTime AccidentDate = new DateTime();
    /**
    * Accident state.
    */
    public String AccidentST = new String();
    /**
    * Enum:YN .
    */
    public YN EmployRelated = YN.Unknown;
    /**
    * True if is ortho.
    */
    public boolean IsOrtho = new boolean();
    /**
    * Remaining months of ortho. Valid values are 1-36.
    */
    public byte OrthoRemainM = new byte();
    /**
    * Date ortho appliance placed.
    */
    public DateTime OrthoDate = new DateTime();
    /**
    * Enum:Relat  Relationship to subscriber.  The relationship is copied from InsPlan when the claim is created.  It might need to be changed in both places.
    */
    public Relat PatRelat = Relat.Self;
    /**
    * FK to insplan.PlanNum.  Other coverage plan number.  0 if none.  This provides the user with total control over what other coverage shows. This obviously limits the coverage on a single claim to two insurance companies.
    */
    public long PlanNum2 = new long();
    /**
    * Enum:Relat  The relationship to the subscriber for other coverage on this claim.
    */
    public Relat PatRelat2 = Relat.Self;
    /**
    * Sum of ClaimProc.Writeoff for this claim.
    */
    public double WriteOff = new double();
    /**
    * The number of x-rays enclosed.
    */
    public byte Radiographs = new byte();
    /**
    * FK to clinic.ClinicNum.  0 if no clinic.  Since one claim cannot have procs from multiple clinics, the clinicNum is set when creating the claim and then cannot be changed.  The claim would have to be deleted and recreated.  Otherwise, if changing at the claim level, a feature would have to be added that synched all procs, claimprocs, and probably some other tables.
    */
    public long ClinicNum = new long();
    /**
    * FK to claimform.ClaimFormNum.  0 if not assigned to use the claimform for the insplan.
    */
    public long ClaimForm = new long();
    /**
    * The number of intraoral images attached.  Not the number of files attached.  This is the value that goes on the 2006 claimform.
    */
    public int AttachedImages = new int();
    /**
    * The number of models attached.
    */
    public int AttachedModels = new int();
    /**
    * A comma-delimited set of flag keywords.  Can have one or more of the following: EoB,Note,Perio,Misc.  Must also contain one of these: Mail or Elect.
    */
    public String AttachedFlags = new String();
    /**
    * Example: NEA#1234567.  If present, and if the claim note does not already start with this Id, then it will be prepended to the claim note for both e-claims and mail.  If using e-claims, this same ID will be used for all PWK segements.
    */
    public String AttachmentID = new String();
    /**
    * A08.  Any combination of E(email), C(correspondence), M(models), X(x-rays), and I(images).  So up to 5 char.  Gets converted to a single char A-Z for e-claims.
    */
    public String CanadianMaterialsForwarded = new String();
    /**
    * B05.  Optional. The 9-digit CDA number of the referring provider, or identifier of referring party up to 10 characters in length.
    */
    public String CanadianReferralProviderNum = new String();
    /**
    * B06.  A number 0(none) through 13.
    */
    public byte CanadianReferralReason = new byte();
    /**
    * F18.  Y, N, or X(not a lower denture, crown, or bridge).
    */
    public String CanadianIsInitialLower = new String();
    /**
    * F19.  Mandatory if F18 is N.
    */
    public DateTime CanadianDateInitialLower = new DateTime();
    /**
    * F21.  If crown, not required.  If denture or bridge, required if F18 is N.  Single digit number code, 0-6.  We added type 7, which is crown.
    */
    public byte CanadianMandProsthMaterial = new byte();
    /**
    * F15.  Y, N, or X(not an upper denture, crown, or bridge).
    */
    public String CanadianIsInitialUpper = new String();
    /**
    * F04.  Mandatory if F15 is N.
    */
    public DateTime CanadianDateInitialUpper = new DateTime();
    /**
    * F20.  If crown, not required.  If denture or bridge, required if F15 is N.  0 indicates empty response.  Single digit number code, 1-6.  We added type 7, which is crown.
    */
    public byte CanadianMaxProsthMaterial = new byte();
    /**
    * FK to inssub.InsSubNum.
    */
    public long InsSubNum = new long();
    /**
    * FK to inssub.InsSubNum.
    */
    public long InsSubNum2 = new long();
    /**
    * G01 assigned by carrier/network and returned in acks.  Used for claim reversal.
    */
    public String CanadaTransRefNum = new String();
    /**
    * F37 Used for predeterminations.
    */
    public DateTime CanadaEstTreatStartDate = new DateTime();
    /**
    * F28 Used for predeterminations.
    */
    public double CanadaInitialPayment = new double();
    /**
    * F29 Used for predeterminations.
    */
    public byte CanadaPaymentMode = new byte();
    /**
    * F30 Used for predeterminations.
    */
    public byte CanadaTreatDuration = new byte();
    /**
    * F31 Used for predeterminations.
    */
    public byte CanadaNumAnticipatedPayments = new byte();
    /**
    * F32 Used for predeterminations.
    */
    public double CanadaAnticipatedPayAmount = new double();
    /**
    * This is NOT the predetermination of benefits number.  In X12, this is REF G1.
    */
    public String PriorAuthorizationNumber = new String();
    /**
    * Enum:EnumClaimSpecialProgram  This is used to track EPSDT.
    */
    public EnumClaimSpecialProgram SpecialProgramCode = EnumClaimSpecialProgram.none;
    /**
    * A three digit number used on 837I.  Aka Bill Code.  UBO4 4.  Examples: 321,823,131,652.  The third digit is claim frequency code.  If this is used, then our CorrectionType should be 0=original.
    */
    public String UniformBillType = new String();
    /**
    * Enum:EnumClaimMedType 0=Dental, 1=Medical, 2=Institutional
    */
    public EnumClaimMedType MedType = EnumClaimMedType.Dental;
    /**
    * Used for inst claims. Single digit.  X12 2300 CL101.  UB04 14.  Should only be required for IP, but X12 clearly states required for all.
    */
    public String AdmissionTypeCode = new String();
    /**
    * Used for inst claims. Single char.  X12 2300 CL102.  UB04 15.  Should only be required for IP, but X12 clearly states required for all.
    */
    public String AdmissionSourceCode = new String();
    /**
    * Used for inst claims. Two digit.  X12 2300 CL103.  UB04 17.  Should only be required for IP, but X12 clearly states required for all.
    */
    public String PatientStatusCode = new String();
    /**
    * FK to definition.DefNum. Most users will leave this blank.  Some offices may set up tracking statuses such as 'review', 'hold', 'riskmanage', etc.
    */
    public long CustomTracking = new long();
    /**
    * Used for historical purposes only, not sent electronically. Automatically set when CorrectionType is not original and the claim is resent.
    */
    public DateTime DateResent = new DateTime();
    /**
    * X12 CLM05-3. Usually set to original, but can be used to resubmit claims.
    */
    public ClaimCorrectionType CorrectionType = ClaimCorrectionType.Original;
    /**
    * X12 CLM01. Unique identifier for the claim within the current database. Defaults to PatNum/ClaimNum, but can be edited by user.
    */
    public String ClaimIdentifier = new String();
    /**
    * X12 2300 REF (F8). Used when resending claims to refer to the original claim. The user must type this value in after reading it from the original claim response report.
    */
    public String OrigRefNum = new String();
    /**
    * Not a data column.
    */
    public List<ClaimAttach> Attachments = new List<ClaimAttach>();
    public Claim() throws Exception {
        AttachedFlags = "";
        CanadianMaterialsForwarded = "";
        CanadianIsInitialLower = "";
        CanadianIsInitialUpper = "";
        Attachments = new List<ClaimAttach>();
    }

    /**
    * Returns a copy of the claim.
    */
    public Claim copy() throws Exception {
        Claim c = (Claim)MemberwiseClone();
        c.Attachments = new List<ClaimAttach>();
        for (int i = 0;i < Attachments.Count;i++)
        {
            c.Attachments.Add(Attachments[i].Copy());
        }
        return c;
    }

    public boolean equals(Object obj) {
        try
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }
             
            Claim c = (Claim)obj;
            return (ClaimNum == c.ClaimNum);
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

    public int hashCode() {
        try
        {
            return super.GetHashCode();
        }
        catch (RuntimeException __dummyCatchVar1)
        {
            throw __dummyCatchVar1;
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new RuntimeException(__dummyCatchVar1);
        }
    
    }

}


